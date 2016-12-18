/**
 * 
 */
package io.downgoon.jresty.data.orm.dao.sql;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.downgoon.jresty.data.orm.dao.example.pojo.EmployeeAnnotation;

/**
 * @author liwei
 *
 */
public class WhereTest {

	private static final Logger LOG = LoggerFactory.getLogger(WhereTest.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToSql() {
		EmployeeAnnotation e = new EmployeeAnnotation();
		String where = new Where(e).toWhereSQL();
		LOG.info("where {}: {}", where.length(), where);
		Assert.assertEquals(0, where.length());
		
		e.setAge(10);
		where = new Where(e).toWhereSQL();
		LOG.info("where {}: {}", where.length(), where);
		Assert.assertEquals("`my_age_field`=10", where);
		
		e.setName("your-name");
		where = new Where(e).toWhereSQL();
		LOG.info("where {}: {}", where.length(), where);
		Assert.assertTrue(where.indexOf("and") != -1);
	}

}
