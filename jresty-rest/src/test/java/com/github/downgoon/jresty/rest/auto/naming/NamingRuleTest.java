package com.github.downgoon.jresty.rest.auto.naming;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamingRuleTest {
	
	protected static final Logger LOG = LoggerFactory.getLogger(NamingRuleTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNameMapping() {
		DefaultFieldNameMapping nameMapping = new DefaultFieldNameMapping();
		
		Assert.assertEquals("applicationName", nameMapping.javaAttributeName("ApplicationName"));
		Assert.assertEquals("applicationName", nameMapping.javaAttributeName("Application_Name"));
		Assert.assertEquals("applicationName", nameMapping.javaAttributeName("Application_name"));
		Assert.assertEquals("applicationName", nameMapping.javaAttributeName("application_name"));
		Assert.assertEquals("applicationName", nameMapping.javaAttributeName("_application__name"));
		Assert.assertEquals("applicationname", nameMapping.javaAttributeName("applicationname")); // NOT smart
	}
	
	@Test
	public void testTypeMapping() {
		DefaultFieldTypeMapping typeMapping = new DefaultFieldTypeMapping();
		
		Assert.assertEquals("String", typeMapping.javaAttributeType("varchar"));
		Assert.assertEquals("String", typeMapping.javaAttributeType("VARCHAR"));
		Assert.assertEquals("String", typeMapping.javaAttributeType("varchar(100)"));
		Assert.assertEquals("Integer", typeMapping.javaAttributeType("int"));
		Assert.assertEquals("Integer", typeMapping.javaAttributeType("int(11)"));
		Assert.assertEquals("Long", typeMapping.javaAttributeType("bigint(20)"));
		Assert.assertEquals("Long", typeMapping.javaAttributeType("bigint"));
		Assert.assertEquals("Short", typeMapping.javaAttributeType("tinyint"));
		Assert.assertEquals("Short", typeMapping.javaAttributeType("tinyint(12)"));
		Assert.assertEquals("Short", typeMapping.javaAttributeType("TINYINT(12)"));
	}

	@Test
	public void testStrutsAction() {
		LayerNaming layerNaming = new LayerNaming();
		Assert.assertEquals("applications", layerNaming.strutsActionName("Application"));
		Assert.assertEquals("application-cates", layerNaming.strutsActionName("ApplicationCate"));
		Assert.assertEquals("application-cates", layerNaming.strutsActionName("ApplicationCate"));
		Assert.assertEquals("blue-boxes", layerNaming.strutsActionName("BlueBox"));
		
	}
	
}
