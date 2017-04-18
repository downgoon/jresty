package com.github.downgoon.jresty.rest.auto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.github.downgoon.jresty.rest.auto.MysqlTable.KeyType;

public class MysqlSchemaParser {
	
	private TableCreationEventHadler tableCreationEventHadler = new DefaultTableCreationEventHadler();
	
	/**
	 * @param	mysqlSchemaFile	file name in classpath
	 * @return	line count of file
	 * */
	public List<MysqlTable> parseSchema(String mysqlSchemaFile, boolean isClassPath) throws IOException {
		/*
CREATE TABLE `application` (
  `AppId` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '应用Id',
  `ApplicationName` varchar(64) NOT NULL,
  `ApplicationPinyin` varchar(64) NOT NULL,
  `ApplicationIntro` varchar(255) NOT NULL COMMENT '应用描述',
  `CateId` int(11) NOT NULL COMMENT '所属分类',
  `CreateById` bigint(18) NOT NULL,
  `CreateTime` int(11) NOT NULL,
  `Status` tinyint(2) NOT NULL COMMENT '状态：待审核|已通过|已拒绝|删除',
  PRIMARY KEY (`AppId`),
  KEY `CateId` (`CateId`, `ApplicationName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 * */
		
        List<MysqlTable> tables = new ArrayList<MysqlTable>();
		boolean inFieldBlock = false; // state var
        
		int rowNum = 0;
        BufferedReader reader = null;
        
        try {
        	InputStream inputStream = isClassPath ? MysqlSchemaParser.class.getClassLoader()
            		.getResourceAsStream(mysqlSchemaFile) : new FileInputStream(mysqlSchemaFile);
            		
        	reader = new BufferedReader(new InputStreamReader(inputStream));
            
        	MysqlTable curTable = null;
            String lineText;
            while ((lineText = reader.readLine()) != null) {
            	lineText = lineText.trim();
            	String lineUpper = lineText.toUpperCase(); // ignore performance cost
            	try {
            		if (lineUpper.startsWith("CREATE TABLE")) { // Table Begin
                		if (! inFieldBlock) { // step into Fields Define Block
                			inFieldBlock = true;
                			curTable = new MysqlTable();
                			tableCreationEventHadler.onTableBegin(lineText, rowNum, curTable);
                		} else {
                			throw new IllegalStateException("Nested CREATE TABLE");
                		}
                		
                	} else if (lineUpper.startsWith(") ENGINE")) { // Table End
                		if (inFieldBlock) {
                			inFieldBlock = false;
                			tableCreationEventHadler.onTableEnd(lineText, rowNum, curTable);
                			tables.add(curTable); // finish one table
                			curTable = null;
                		} else {
                			throw new IllegalStateException("CREATE TABLE end required");
                		}
                	} else if (inFieldBlock && !lineUpper.startsWith("--") ) { // Field Def
                		// skip non-field def line and comment line
                		if (lineUpper.startsWith("PRIMARY KEY")) {
                			tableCreationEventHadler.onKeyLine(lineText, rowNum, MysqlTable.KeyType.PRIMARY, curTable);
                		} else if (lineUpper.startsWith("UNIQUE KEY")) {
                			tableCreationEventHadler.onKeyLine(lineText, rowNum, MysqlTable.KeyType.UNIQUE, curTable);
                		} else if (lineUpper.startsWith("KEY")) {
                			tableCreationEventHadler.onKeyLine(lineText, rowNum, MysqlTable.KeyType.NORMAR, curTable);
                		} else {
                			tableCreationEventHadler.onFiledLine(lineText, rowNum, curTable);
                		}
                		
                	}
            	} catch (Throwable t) {
            		throw new IllegalStateException(String.format("bad line %d: '%s'", rowNum, lineText), t);
            	}
            	
            	rowNum ++;
            }
            return tables;
            
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

    }
	
	private static interface TableCreationEventHadler {

		/**
		 * @return parse and return table name in mysql db
		 * */
		public String onTableBegin(String tableBeginLine, int rowNum, MysqlTable table);
		
		/**
		 * @return parse and return filed name
		 * */
		public String onFiledLine(String filedLine, int rowNum, MysqlTable table);
		
		/**
		 * @return parse and return field names for key def
		 * */
		public List<String> onKeyLine(String keyLine, int rowNum, MysqlTable.KeyType keyType, MysqlTable table);
		
		public String onTableEnd(String tableEndLine, int rowNum, MysqlTable table);
		
	}
	
	private static class DefaultTableCreationEventHadler implements TableCreationEventHadler {

		@Override
		public String onTableBegin(String tableBeginLine, int rowNum, MysqlTable table) {
			// CREATE TABLE `application` ( -- SOME COMMENTS
			String tableName = unwrap(tableBeginLine.substring("CREATE TABLE".length(), tableBeginLine.indexOf("(")).trim());
			table.setTableName(tableName);
			return tableName;
		}

		@Override
		public String onFiledLine(String filedLine, int rowNum, MysqlTable table) {
			// `AppId` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '应用Id',
			StringTokenizer st = new StringTokenizer(filedLine);
			if (st.countTokens() < 2) {
				throw new IllegalArgumentException(String.format("bad field def '%s' on %d", filedLine, rowNum));
			} 
			String fname = unwrap(st.nextToken());
			String ftype = unwrap(st.nextToken());
			MysqlTable.MysqlField field = new MysqlTable.MysqlField(fname, ftype);
			field.setAutoIncrement(filedLine.toUpperCase().indexOf("AUTO_INCREMENT") != -1); // AUTO_INCREMENT tag
			table.appendFiled(field);
			if (! table.isTimeFieldFound() && "timestamp".equalsIgnoreCase(ftype)) {
				table.setTimeFieldFound(true);
			}
			return fname;
		}
		

		@Override
		public List<String> onKeyLine(String keyLine, int rowNum, MysqlTable.KeyType keyType, MysqlTable table) {
			// PRIMARY KEY (`AppId`)
			// UNIQUE KEY `TopicId` (`TopicId`,`AppId`)
			int sidx = keyLine.indexOf("(");
			int eidx = keyLine.indexOf(")", sidx + 1);
			if (sidx == -1 || eidx == -1) {
				throw new IllegalArgumentException(String.format("bad key def '%s' on %d", keyLine, rowNum));
			}
			
			String keyFieldsNames = keyLine.substring(sidx + 1, eidx).trim();
			
			StringTokenizer st = new StringTokenizer(keyFieldsNames, ",");
			List<String> fieldNames = new ArrayList<String>();
			while (st.hasMoreTokens()) {
				String fname = unwrap(st.nextToken().trim());

				fieldNames.add(fname);
				if (keyType == KeyType.PRIMARY) {
					table.getField(fname).setPrimaryKeyPart(true); // fillback field's primary key tag
				}
				
			}
			table.appendKey(new MysqlTable.MysqlKey(fieldNames, keyType));
			
			return fieldNames;
		}

		@Override
		public String onTableEnd(String tableEndLine, int rowNum, MysqlTable table) {
			// ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
			return "";
		}
		
		protected String unwrap(String mysqlName) { // trim ``
			if (mysqlName.startsWith("`")) {
				return mysqlName.substring(1, mysqlName.length() - 1);
			} else {
				return mysqlName;
			}
		}
	}	
}
