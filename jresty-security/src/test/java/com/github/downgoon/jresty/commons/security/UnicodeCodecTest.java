package com.github.downgoon.jresty.commons.security;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class UnicodeCodecTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEncode() {
		String unicode = UnicodeCodec.encode("张柏芝");
		// \u5F20\u67CF\u829D
		Assert.assertEquals("\\u5F20\\u67CF\\u829D", unicode);
		
	}

}
