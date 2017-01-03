/**
 * 
 */
package com.github.downgoon.jresty.data.orm.dao.sql;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liwei
 *
 */
public class SQLEscapeUtilsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEscape() {
		Assert.assertEquals("{''name'':''liwei217''}", SQLEscapeUtils.escapeSql("{'name':'liwei217'}"));
		Assert.assertEquals("{''name'':''liwei217''}", SQLEscapeUtils.escapeSql("{'name':'liwei217'}"));
		Assert.assertEquals("{\"name\":\"liwei217\"}", SQLEscapeUtils.escapeSql("{\"name\":\"liwei217\"}"));
	}

}
