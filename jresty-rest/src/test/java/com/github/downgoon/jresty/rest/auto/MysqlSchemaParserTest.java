package com.github.downgoon.jresty.rest.auto;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlSchemaParserTest {

	protected static final Logger LOG = LoggerFactory.getLogger(MysqlSchemaParserTest.class);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParseSchema() throws Exception {
		MysqlSchemaParser parser = new MysqlSchemaParser();
		List<MysqlTable> tables = parser.parseSchema("mysql-schema.sql", true);
		for (MysqlTable t : tables) {
			LOG.info(">> {}", t);
		}
		Assert.assertEquals(9, tables.size());
		Assert.assertEquals("application", tables.get(0).getTableName());
		
	}

}
