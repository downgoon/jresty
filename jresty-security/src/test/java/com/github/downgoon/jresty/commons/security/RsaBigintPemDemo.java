package com.github.downgoon.jresty.commons.security;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsaBigintPemDemo {

	private static Logger LOG = LoggerFactory.getLogger(RsaBigintPemDemo.class);

	public static void main(String[] args) throws Exception {

		// exportPem();

		importPem();
	}

	public static void importPem() throws Exception {
		PublicKey pubKey = RsaKeyIO.importPublicFrPem(new File("src/test/resources/passport-pub-1024-test.pem"));
		PrivateKey priKey = RsaKeyIO.importPrivateFrPem(new File("src/test/resources/passport-pri-1024-test.pem"));
		RSACodecPrivate rsa = new RSACodecPrivate(pubKey, priKey);

		byte[] cipherBytes = rsa.signByPrivate(new String("abcd").getBytes());
		String cipherBase64 = Base64Codec.encode(cipherBytes);

		LOG.info("sign of `abcd` with key passport-pri-1024-test: {}", cipherBase64);
		
		LOG.info("passport-pri-1024-test key in BigInt Hex Format: \n {}", priKey);
		
	}

	public static void exportPem() throws Exception {
		String modulusBigIntHex = "dc2322ad9ff64ae4afbe7d219528c7e7d66c71e007b30a60341328916ce4e5f4b5e9adf9339eea7db2b1bafba5fee1565c222f202dc91cf4864c1655e5809cd5";
		String publicExponentBigIntHex = "10001"; // 通常是常量
		String privateExponentBigIntHex = "6258f4223b87ce37d2e838812b6157c614bec8353d5b58b4582f82fd4d7809df7061f274d2309412cd79501c985114ec70ec796a609f68d1dcb7455edf675c15";

		PublicKey pubKey = new RSAKeyPublic(modulusBigIntHex, publicExponentBigIntHex).toPublicKey();
		PrivateKey priKey = new RSAKeyPrivate(modulusBigIntHex, publicExponentBigIntHex, privateExponentBigIntHex)
				.toPrivateKey();

		RsaKeyIO.exportPublicToPem(pubKey, "/tmp/passport-pub.pem");
		RsaKeyIO.exportPrivateToPem(priKey, "/tmp/passport-pri.pem");
	}

}
