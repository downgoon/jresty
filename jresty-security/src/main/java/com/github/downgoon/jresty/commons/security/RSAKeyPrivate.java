package com.github.downgoon.jresty.commons.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;

/**
 * RSA 私钥 两个大整数表示法 （大整数用十六进制表示，不用十进制表示，以免太长）
 * 
 * 注意：私钥的两个大整数 与 公钥的两个大整数的关系 [1] 有一个是相同的； [2] 另一个是私钥能推导出公钥。
 * 
 */
public class RSAKeyPrivate extends RSAKeyPublic {

	private String privateExponentBigIntHex;
	
	public RSAKeyPrivate(String modulusBigIntHex, String privateExponentBigIntHex) {
		super(modulusBigIntHex);
		this.privateExponentBigIntHex = privateExponentBigIntHex;
	}

	public RSAKeyPrivate(String modulusBigIntHex, String publicExponentBigIntHex, String privateExponentBigIntHex) {
		super(modulusBigIntHex, publicExponentBigIntHex);
		this.privateExponentBigIntHex = privateExponentBigIntHex;
	}

	public String getPrivateExponentBigIntHex() {
		return privateExponentBigIntHex;
	}

	public void setPrivateExponentBigIntHex(String privateExponentBigIntHex) {
		this.privateExponentBigIntHex = privateExponentBigIntHex;
	}

	public PrivateKey toPrivateKey() {

		int radix = 16;
		BigInteger n = new BigInteger(this.modulusBigIntHex, radix);
		BigInteger d = new BigInteger(this.privateExponentBigIntHex, radix);

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(n, d);

		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(priKeySpec);

		} catch (Exception ex) {
			throw new IllegalStateException("No RSA Provider", ex);
		}

	}

}
