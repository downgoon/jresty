package io.downgoon.jresty.commons.utils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class HumanizedFormatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testHuman() {

		Assert.assertEquals("0B", HumanizedFormator.bytes(0));
		Assert.assertEquals("1023B", HumanizedFormator.bytes(1023));
		Assert.assertEquals("1K", HumanizedFormator.bytes(1024));
		Assert.assertEquals("1M", HumanizedFormator.bytes(1024 * 1024));
		Assert.assertEquals("1G", HumanizedFormator.bytes(1024 * 1024 * 1024));
		Assert.assertEquals("1G0M0K5B", HumanizedFormator.bytes(1024 * 1024 * 1024 + 5));
		Assert.assertEquals("1G5M6K56B", HumanizedFormator.bytes(1024 * 1024 * 1024 + 1024 * 1024 * 5 + 1024 * 6 + 56));

		final long[] radix = new long[] { 60L * 60L * 24, 60L * 60L, 60L, 1L };
		final String[] unitname = new String[] { "天", "小时", "分", "秒" };
		Assert.assertEquals("0秒", HumanizedFormator.humanized(0, radix, unitname));
		Assert.assertEquals("35秒", HumanizedFormator.humanized(35, radix, unitname));
		Assert.assertEquals("1分", HumanizedFormator.humanized(60, radix, unitname));
		Assert.assertEquals("1小时", HumanizedFormator.humanized(60 * 60, radix, unitname));
		Assert.assertEquals("1天", HumanizedFormator.humanized(60 * 60 * 24, radix, unitname));
		Assert.assertEquals("2天12小时", HumanizedFormator.humanized(60 * 60 * 60, radix, unitname));
	}

}
