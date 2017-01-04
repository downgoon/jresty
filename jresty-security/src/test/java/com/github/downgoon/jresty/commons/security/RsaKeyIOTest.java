package com.github.downgoon.jresty.commons.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Assert;
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
	public void testFrBigint() throws Exception {
		String modulusBigIntHex = "dc2322ad9ff64ae4afbe7d219528c7e7d66c71e007b30a60341328916ce4e5f4b5e9adf9339eea7db2b1bafba5fee1565c222f202dc91cf4864c1655e5809cd5";
		String publicExponentBigIntHex = "10001"; // 通常是常量
		String privateExponentBigIntHex = "6258f4223b87ce37d2e838812b6157c614bec8353d5b58b4582f82fd4d7809df7061f274d2309412cd79501c985114ec70ec796a609f68d1dcb7455edf675c15";

		PublicKey pubKey = RsaKeyIO.importPublicFrBigint(modulusBigIntHex, publicExponentBigIntHex);
		LOG.info("pubKey: {}", pubKey);
		LOG.info("public key class: {}", pubKey.getClass().getName());

		PrivateKey priKey = RsaKeyIO.importPrivateFrBigint(modulusBigIntHex, privateExponentBigIntHex);
		LOG.info("priKey: {}", priKey);
		LOG.info("private key class: {}", priKey.getClass().getName());
		
		String tmpdir = System.getProperty("java.io.tmpdir");
		
		LOG.info("java tmpdir: {}", tmpdir);
		
		String pubFileName = tmpdir + File.separator + "tmp_rsa_public.pem";
		String priFileName = tmpdir + File.separator + "tmp_rsa_private.pem";
		
		RsaKeyIO.exportPublicToPem(pubKey, pubFileName);
		RsaKeyIO.exportPrivateToPem(priKey, priFileName);
		
		String priKeyRead = readFileContent(new File(priFileName));
		LOG.info("priKeyRead: {}", priKeyRead );
		
//		String priKeyExpected = 
//				"-----BEGIN RSA PRIVATE KEY-----" 
//				+ "MIGaAgEAAkEA3CMirZ/2SuSvvn0hlSjH59ZsceAHswpgNBMokWzk5fS16a35M57q" + "\n"
//				+ "fbKxuvul/uFWXCIvIC3JHPSGTBZV5YCc1QIBAAJAYlj0IjuHzjfS6DiBK2FXxhS+" + "\n"
//				+ "yDU9W1i0WC+C/U14Cd9wYfJ00jCUEs15UByYURTscOx5amCfaNHct0Ve32dcFQIB" + "\n"
//				+ "AAIBAAIBAAIBAAIBAA==" + "\n"
//				+ "-----END RSA PRIVATE KEY-----";
		
		String priKeySlice = "fbKxuvul/uFWXCIvIC3JHPSGTBZV5YCc1QIBAAJAYlj0IjuHzjfS6DiBK2FXxhS+";

		int idxFound = priKeyRead.indexOf(priKeySlice);
		Assert.assertTrue(idxFound != -1);
		
		deleteFile(pubFileName);
		deleteFile(priFileName);
	}
	
	

	@Test
	public void testFrPem() throws Exception {

		PrivateKey priKey = RsaKeyIO.importPrivateFrPem(new File("src/test/resources/prikey.pem"));
		PublicKey pubKey = RsaKeyIO.importPublicFrPem(new File("src/test/resources/pubkey.pem"));
		
		LOG.info("private key class: {}", priKey.getClass().getName());
		LOG.info("public key class: {}", pubKey.getClass().getName());
		
		String[] priKeyBigint = RsaKeyIO.exportPrivateToBigint(priKey);
		String[] pubKeyBigint = RsaKeyIO.exportPublicToBigint(pubKey);
		
		LOG.info("modulusBigIntHex: {}", priKeyBigint[0]);
		LOG.info("privateExponentBigIntHex: {}", priKeyBigint[1]);
		LOG.info("publicExponentBigIntHex: {}", pubKeyBigint[1]); // 10001
		
		Assert.assertEquals("10001", pubKeyBigint[1]);
		
		Assert.assertEquals("a7bbfebec94d13d8eee4efed0f8dcdc28dd569be7534954f7380bbc056aa201398019d50262943f99dc5cbbb18ae6169613d164aa6f39de2755f1756ac8c84102ef5b2b8330630c79eb1187931879efcb29d2d90169d41b8e3ff1a1b1ff32c2a7a91fcd74a7018be308b9a45d1e2a85bdfc88afe36f441241017c1c9e67c60cd", 
				priKeyBigint[0]);
		
		Assert.assertTrue(priKeyBigint[0].equals(pubKeyBigint[0]));
		
		Assert.assertEquals("7010b070b379a5cb084138f37fb2d4482a0eede96bd147f89f48e127ee3f17d33f79c4aae9f42410401308bce60e8bd2a63ef407c8677792f7ac22a1ac02edaff4d7008e27ad6ac4f1ef3338c61202c207d4d96ac7bccd9e211bdda96a16bd583566225e80caa7d6ea6713c5e267b06a01c9cde9f4ccf22d1dc6e864efc94299", 
				priKeyBigint[1]);
		
		
		RSACodecPublic rsa = new RSACodecPublic(pubKey);
		
		String signBase64 = readFileContent(new File("src/test/resources/sign-of-abcd.txt"));
		byte[] signBytes = Base64Codec.decode(signBase64);
		byte[] plainBytes = rsa.unsignByPublic(signBytes);
		Assert.assertEquals("abcd\n", new String(plainBytes));
		
		boolean verified = rsa.verifySignByPublic(signBytes, "abcd\n".getBytes());
		Assert.assertTrue(verified);
		
		boolean verified2 = rsa.verifySignByPublic(signBytes, "other".getBytes());
		Assert.assertFalse(verified2);
		
		
	}
	
	private static String readFileContent(File file) throws Exception {
		StringBuffer content = new StringBuffer();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				content.append(line);
				content.append("\n");
				line = reader.readLine();
			}
			return content.toString();
			
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		
	}
	
	
	private static boolean deleteFile(String fileName) {
		try {
			File file = new File(fileName);
			if (file.exists() && file.canWrite()) {
				return file.delete();
			} else {
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
		
	}
}
