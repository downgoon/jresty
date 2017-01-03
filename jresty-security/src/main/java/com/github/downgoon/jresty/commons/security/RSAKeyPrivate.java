package com.github.downgoon.jresty.commons.security;

/**
 * RSA 私钥 两个大整数表示法 （大整数用十六进制表示，不用十进制表示，以免太长）
 * 
 * 注意：私钥的两个大整数 与 公钥的两个大整数的关系
 * [1] 有一个是相同的；
 * [2] 另一个是私钥能推导出公钥。
 *  
 * */
public class RSAKeyPrivate extends RSAKeyPublic {

	private String privateExponentBigIntHex;

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
	
	
}
