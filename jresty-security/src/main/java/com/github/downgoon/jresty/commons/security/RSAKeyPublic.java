package com.github.downgoon.jresty.commons.security;

/**
 * RSA 公钥 两个大整数表示法 （大整数用十六进制表示，不用十进制表示，以免太长） 
 * */
public class RSAKeyPublic {

	protected String modulusBigIntHex;
	
	protected String publicExponentBigIntHex;

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
	
	
	
}
