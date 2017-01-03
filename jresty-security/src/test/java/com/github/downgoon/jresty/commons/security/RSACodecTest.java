package com.github.downgoon.jresty.commons.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSACodecTest {

	private static RSACodecPublic rsaCodecPub;

	private static RSACodecPrivate rsaCodecPri;

	private static final Logger LOG = LoggerFactory.getLogger(RSACodecTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String modulusBigIntHex = "dc2322ad9ff64ae4afbe7d219528c7e7d66c71e007b30a60341328916ce4e5f4b5e9adf9339eea7db2b1bafba5fee1565c222f202dc91cf4864c1655e5809cd5";
		String publicExponentBigIntHex = "10001"; // 通常是常量
		String privateExponentBigIntHex = "6258f4223b87ce37d2e838812b6157c614bec8353d5b58b4582f82fd4d7809df7061f274d2309412cd79501c985114ec70ec796a609f68d1dcb7455edf675c15";

		RSAKeyPrivate rsaKeyPrivate = new RSAKeyPrivate(modulusBigIntHex, publicExponentBigIntHex,
				privateExponentBigIntHex);
		RSAKeyPublic rsaKeyPublic = new RSAKeyPublic(modulusBigIntHex, publicExponentBigIntHex);

		rsaCodecPub = new RSACodecPublic(rsaKeyPublic);
		rsaCodecPri = new RSACodecPrivate(rsaKeyPublic, rsaKeyPrivate);
	}

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testEncryptDecrypt() throws Exception {
		String splain = "Hello World";
		byte[] bplain = splain.getBytes();

		// 公钥加密
		byte[] bcipher = rsaCodecPub.encryptByPublic(bplain);

		String cipherBase64Time1 = Base64Codec.encode(bcipher);
		String cipherBase64Time2 = Base64Codec.encode(bcipher);

		LOG.info("cipher base64 time1: {}", cipherBase64Time1);
		LOG.info("cipher base64 time2: {}", cipherBase64Time2);

		/*
		 * RSA 两次加密的结果往往是不一样的（但同一个机器时间和对象，连续两次往往是一样的）： 
		 * [1]
		 * wTSV9AjYC1pwEwc+gK+QiXjFdAxwgRhV1Yy2Lp3+xWO6r+WIjpgfPeG/
		 * n1RHGkk74qoNIvy8aew0uZ9ndsm2WA== 
		 * [2]
		 * d084iD5UjcZtLWf2i4YOJYUK7Y3Oh8JSBAORmPUOt6sGeQxMHNQae+
		 * ZBMgvmHA1WjpyZduR9SVd6/0fAymLKCw==
		 */
		boolean isEqual = cipherBase64Time1.equals(cipherBase64Time2);
		// Assert.assertFalse(isEqual);
		LOG.info("cipher equal: {}", isEqual);

		// 私钥解密
		byte[] bplainRecovery = rsaCodecPri.decryptByPrivate(bcipher);
		String splainRecovery = new String(bplainRecovery);
		LOG.info("plain recovery: {}", splainRecovery);

		Assert.assertEquals(splain, splainRecovery);

	}

	@Test
	public void testSignCheck() throws Exception {

		// 如果待签名的信息过长，可以先计算摘要，再对摘要签名。
		byte[] dataToBeSigned = new byte[] { (byte) 0x6F, (byte) 0xAE, (byte) 0x12 };
		byte[] dataDoctored = new byte[] { (byte) 0x6F, (byte) 0xAE, (byte) 0x13 }; // 被篡改过的数据
		
		byte[] signBytes = rsaCodecPri.signByPrivate(dataToBeSigned);

		// RSA 私钥签名 两次签名的结果是相同的
		String signBase64 = Base64Codec.encode(signBytes);

		Assert.assertEquals("EJMtDbyzaOHhaC1iMG0efodvjJLKcxOTEuIYNXvPd29U0A7L+UiWjEbBJre6xFgbOs1/ntFJ86qaioLb8CHBaA==",
				signBase64);
		
		boolean verified = rsaCodecPub.verifySignByPublic(signBytes, dataToBeSigned);
		Assert.assertTrue(verified);
		
		// 篡改原始信息
		boolean verified2 = rsaCodecPub.verifySignByPublic(signBytes, dataDoctored);
		Assert.assertFalse(verified2);
		
		// 假冒的签名
		byte[] signFake = Base64Codec.decode("wTSV9AjYC1pwEwc+gK+QiXjFdAxwgRhV1Yy2Lp3+xWO6r+WIjpgfPeG/n1RHGkk74qoNIvy8aew0uZ9ndsm2WA==");
		
		Exception exception = null;
		try {
			rsaCodecPub.verifySignByPublic(signFake, dataToBeSigned);
		} catch (Exception ex) {
			exception = ex;
		}
		Assert.assertNotNull(exception);
			

	}

}
