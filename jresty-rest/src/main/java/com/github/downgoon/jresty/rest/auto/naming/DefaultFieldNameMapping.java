package com.github.downgoon.jresty.rest.auto.naming;


public class DefaultFieldNameMapping implements FieldNameMappingRule {

	@Override
	public String javaAttributeName(String dbFieldName) {
		return NamingConvert.javaAttriName(dbFieldName);
	}

	@Override
	public String javaPojoName(String dbTableName) {
		return NamingConvert.javaClassName(dbTableName);
	}
	
}