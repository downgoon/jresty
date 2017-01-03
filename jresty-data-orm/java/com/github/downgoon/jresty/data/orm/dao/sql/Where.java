/**
 * 
 */
package com.github.downgoon.jresty.data.orm.dao.sql;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author liwei
 *
 */
public class Where {

	public Where(Object objectBean) {
		super();
		ORMBuilderFacade ormBuilderFacade = new ORMBuilderFacade(objectBean);
//      ORFieldMapping fieldMapping = ormBuilderFacade.buildFieldMapping();
      ORValueMapping valueMapping = ormBuilderFacade.buildValueMapping();
      this.dbTableName = valueMapping.getDbTalbeName();
      this.dbKeys = valueMapping.getDbKeysKV();
      this.dbFields = valueMapping.getDbFieldKV();
	}
	
	private String dbTableName;
	private Map<String, Object> dbKeys;
    private Map<String, Object> dbFields;
	
	protected Where(String dbTableName, Map<String, Object> dbKeys, Map<String, Object> dbFields) {
		this.dbTableName = dbTableName;
		this.dbKeys = dbKeys;
		this.dbFields = dbFields;
	}
	
	protected String toSelectSQL(String selectFieldList) {
		StringBuffer sqlBeforeWhere = new StringBuffer("SELECT " + selectFieldList + " FROM ");
		sqlBeforeWhere.append(SQLWrapper.wrapTableName(dbTableName));
        String whereCause = toWhereSQL();
        if (whereCause.length() > 0) {
        	sqlBeforeWhere.append(" WHERE ").append(whereCause);
        } else {
        	sqlBeforeWhere.append(whereCause);
        }
        return sqlBeforeWhere.toString();
	}
	
	public String toSelectSQL() {
		return toSelectSQL("*");
	}
	
	public String toWhereSQL() {
		return toWhereSQL(null);
	}
	
	public String toWhereSQL(final String tableAlias) {
        
        StringBuffer sqlBeforeWhere = new StringBuffer();
        
        StringBuffer sqlKeysCondi = new StringBuffer();
        int keysAdded = genSQLEachField(tableAlias, dbKeys, " and ", sqlKeysCondi, "=", sqlKeysCondi);

        StringBuffer sqlFieldsCondi = new StringBuffer();
        int fieldsAdded = genSQLEachField(tableAlias, dbFields, " and ", sqlFieldsCondi, "=", sqlFieldsCondi);

        if (keysAdded > 0 && sqlKeysCondi.length() > 0) { // primaryKey = value
        	sqlBeforeWhere.append(sqlKeysCondi);
        }
        
        if (keysAdded > 0 && fieldsAdded > 0 ) { 
            sqlBeforeWhere.append(" and ");
        }
        
        if (fieldsAdded > 0 && sqlFieldsCondi.length() > 0) { // somefield = value
        	sqlBeforeWhere.append(sqlFieldsCondi);
        }
         
        return sqlBeforeWhere.toString();
	}
	
	
	/**
     * 字段迭代
     * 
     * @param dbFields 数据库字段列表
     * @param catFields 字段间的链接符
     * @param sqlFieldName 字段名部分的SQL
     * @param catNameValue 字段内的链接符：INSERT语句时，为NULL；Update语句时为"="；
     * @param sqlFieldValue 字段值部分的SQL：INSERT语句时，不等于sqlFieldName；Update时，两者相等。
     * @return 返回有效Field的个数
     * */
    private static int genSQLEachField(final String tableAlias, final Map<String, Object> dbFields, final String catFields,
            StringBuffer sqlFieldName, String catNameValue, StringBuffer sqlFieldValue) {
        int fieldCntAppended = 0;
        Iterator<Entry<String, Object>> kvs = dbFields.entrySet().iterator();
        boolean isDivNeed = false;
        while (kvs.hasNext()) {
            Entry<String, Object> e = kvs.next();
            if (e.getValue() == null) {
                continue;
            }
            if (isDivNeed) {
                sqlFieldName.append(catFields);
                /* 不是同一个对象时，则两部分都追加。e.g. INSERT语句 */
                if (sqlFieldName != sqlFieldValue) {
                    sqlFieldValue.append(catFields);
                }
            }
            if (tableAlias != null) {
            	sqlFieldName.append(tableAlias).append(".");
            }
            sqlFieldName.append("`").append(e.getKey()).append("`");
            if (catNameValue != null) {
                /* FieldName与FieldValue之间是否需要插入链接符：UPDATE的SET和WHERE字句都需要；但INSERT语句不需要 */
                sqlFieldName.append(catNameValue);
            }
            sqlFieldValue.append(SQLWrapper.wrapDbFieldValue(e.getValue()));
            fieldCntAppended++;
            isDivNeed = true;
        }
        return fieldCntAppended;
    }
    
}
