package com.github.downgoon.jresty.commons.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HexCodecTest {

	private static Logger LOG = LoggerFactory.getLogger(HexCodecTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testByte2Hex() {
		String hexUpper = HexCodec.b2HEX("ABab".getBytes(), ":");
		LOG.info("hexUpper: {}", hexUpper);

		Assert.assertEquals(":41:42:61:62", hexUpper);

		byte[] bytes = HexCodec.hex2b("41426162");
		Assert.assertEquals("ABab", new String(bytes));
	}

	@Test
	public void testHexAscii() {
		String hexAscii = HexCodec.hexUpperAscii(" 测试 & 数据 Test Data".getBytes());
		LOG.info("hex and ascii: {}", hexAscii);
	}
}
