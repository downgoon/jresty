package com.github.downgoon.jresty.commons.security;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsaKeyIOTest {

	private static final Logger LOG = LoggerFactory.getLogger(RsaKeyIOTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFrBigintToPem() throws Exception {
		String modulusBigIntHex = "dc2322ad9ff64ae4afbe7d219528c7e7d66c71e007b30a60341328916ce4e5f4b5e9adf9339eea7db2b1bafba5fee1565c222f202dc91cf4864c1655e5809cd5";
		String publicExponentBigIntHex = "10001"; // 通常是常量
		String privateExponentBigIntHex = "6258f4223b87ce37d2e838812b6157c614bec8353d5b58b4582f82fd4d7809df7061f274d2309412cd79501c985114ec70ec796a609f68d1dcb7455edf675c15";

		PublicKey pubKey = RsaKeyIO.importPublicFrBigint(modulusBigIntHex, publicExponentBigIntHex);
		LOG.info("pubKey: {}", pubKey);

		PrivateKey priKey = RsaKeyIO.importPrivateFrBigint(modulusBigIntHex, privateExponentBigIntHex);
		LOG.info("priKey: {}", priKey);

		
		// RsaKeyIO.exportPublicToPem(pubKey, "/Users/liwei/tmp/t4/passport-pub-2.pem");
	}

}
