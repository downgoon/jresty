package com.github.downgoon.jresty.rest.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MysqlTable {

	private String tableName;
	
	private List<MysqlField> fields = new ArrayList<MysqlTable.MysqlField>();
	private Map<String, MysqlField> fieldIndex = new HashMap<String, MysqlTable.MysqlField>();
	
	private List<MysqlKey> keys = new ArrayList<MysqlTable.MysqlKey>();
	
	private boolean timeFieldFound = false;
	
	public void appendKey(MysqlKey key) {
		keys.add(key);
	}
	
	public void appendFiled(MysqlField field) {
		fields.add(field);
		fieldIndex.put(field.getFieldName(), field);
	}
	
	public MysqlField getField(String fieldName) {
		return fieldIndex.get(fieldName);
	}
	
	public Iterator<MysqlField> getFields() {
		return fields.iterator();
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * time field
	 * */
	public boolean isTimeFieldFound() {
		return timeFieldFound;
	}

	public void setTimeFieldFound(boolean timeFieldFound) {
		this.timeFieldFound = timeFieldFound;
	}

	@Override
	public String toString() {
		return "MysqlTable [tableName=" + tableName + ", fields=" + fields + ", keys=" + keys + "]";
	}

	public static class MysqlKey {
		private List<String> fieldNames;
		private KeyType keyType;
		
		public MysqlKey(List<String> fieldNames, KeyType keyType) {
			super();
			this.fieldNames = fieldNames;
			this.keyType = keyType;
		}

		public KeyType getKeyType() {
			return keyType;
		}

		public List<String> getFieldNames() {
			return fieldNames;
		}

		@Override
		public String toString() {
			return "MysqlKey [fieldNames=" + fieldNames + ", keyType=" + keyType + "]";
		}
		
	}
	
	public static enum KeyType {
		PRIMARY, UNIQUE, NORMAR
	}
	
	public static class MysqlField {
		private String fieldName;
		private String fieldType;
		
		private boolean primaryKeyPart = false;
		private boolean autoIncrement = false;
		
		public MysqlField(String fieldName, String fieldType) {
			super();
			this.fieldName = fieldName;
			this.fieldType = fieldType;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
		public String getFieldType() {
			return fieldType;
		}
		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}
		
		public boolean isPrimaryKeyPart() {
			return primaryKeyPart;
		}
		
		public void setPrimaryKeyPart(boolean isPrimaryKey) {
			this.primaryKeyPart = isPrimaryKey;
		}
		
		public boolean isAutoIncrement() {
			return autoIncrement;
		}
		public void setAutoIncrement(boolean autoIncrement) {
			this.autoIncrement = autoIncrement;
		}
		
		@Override
		public String toString() {
			return "MysqlField [fieldName=" + fieldName + ", fieldType=" + fieldType + ", primaryKeyPart="
					+ primaryKeyPart + ", autoIncrement=" + autoIncrement + "]";
		}
		
		
	}
}
