package com.github.downgoon.jresty.rest.auto.naming;

import java.util.HashMap;
import java.util.Map;


public class DefaultFieldTypeMapping implements FieldTypeMappingRule {
	
	/** from db type name to java type name e.g. bigint(20) => Long */
	static Map<String, String> TYPE_MAPPING = new HashMap<String, String>();
	static {
		TYPE_MAPPING.put("varchar", "String");
		TYPE_MAPPING.put("int", "Integer");
		TYPE_MAPPING.put("bigint", "Long");
		TYPE_MAPPING.put("tinyint", "Short");
		TYPE_MAPPING.put("timestamp", "Date"); // java.util.Date
		TYPE_MAPPING.put("tinytext", "String");
	}

	@Override
	public String javaAttributeType(String dbFileldType) {
		/* trim length part e.g. varchar(20) => varchar ; bigint(20) => bigint */
		int typeLengthIdx = dbFileldType.indexOf("(");
		if (typeLengthIdx != -1) {
			dbFileldType = dbFileldType.substring(0, typeLengthIdx); 
		}
		dbFileldType = dbFileldType.toLowerCase();
		String javaType = TYPE_MAPPING.get(dbFileldType);
		if (javaType == null) {
			throw new IllegalStateException("Filed Type Mapping Not Found: " + dbFileldType);
		}
		return javaType;
	}
	
}
