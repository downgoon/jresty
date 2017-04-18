package com.github.downgoon.jresty.rest.auto.naming;

public interface FieldNameMappingRule {
	
	public String javaAttributeName(String dbFieldName);
	
	public String javaPojoName(String dbTableName);
	
}
