package com.github.downgoon.jresty.commons.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA 公钥 两个大整数表示法 （大整数用十六进制表示，不用十进制表示，以免太长） 
 * */
public class RSAKeyPublic {

	protected String modulusBigIntHex;
	
	protected String publicExponentBigIntHex;
	
	public RSAKeyPublic(String modulusBigIntHex) {
		this(modulusBigIntHex, "10001");
	}
	
	public RSAKeyPublic(String modulusBigIntHex, String publicExponentBigIntHex) {
		super();
		this.modulusBigIntHex = modulusBigIntHex;
		this.publicExponentBigIntHex = publicExponentBigIntHex;
	}

	public String getModulusBigIntHex() {
		return modulusBigIntHex;
	}

	public void setModulusBigIntHex(String modulusBigIntHex) {
		this.modulusBigIntHex = modulusBigIntHex;
	}

	public String getPublicExponentBigIntHex() {
		return publicExponentBigIntHex;
	}

	public void setPublicExponentBigIntHex(String publicExponentBigIntHex) {
		this.publicExponentBigIntHex = publicExponentBigIntHex;
	}
	
	
	/**
	 * @return	JCE Public Key
	 * */
	public PublicKey toPublicKey() {
		
		int radix = 16;
		BigInteger n = new BigInteger(modulusBigIntHex, radix);
		BigInteger e = new BigInteger(publicExponentBigIntHex, radix);

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(n, e);

		KeyFactory keyFactory;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(pubKeySpec);
			
		} catch (Exception ex) {
			throw new IllegalStateException("No RSA Provider", ex);
		}
		
	}
	
}
