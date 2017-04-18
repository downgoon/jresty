package com.github.downgoon.jresty.rest.auto.naming;


public class NamingRules {
	
	private static FieldNameMappingRule nameMapping = new DefaultFieldNameMapping();
	private static FieldTypeMappingRule typeMapping = new DefaultFieldTypeMapping();
	private static LayerNaming layerNaming = new LayerNaming();

	public static FieldNameMappingRule getFieldNameMapping() {
		return nameMapping;
	}
	
	public static FieldTypeMappingRule getFieldTypeMapping() {
		return typeMapping;
	}
	
	public static LayerNaming getLayerNaming() {
		return layerNaming;
	}
	
}
