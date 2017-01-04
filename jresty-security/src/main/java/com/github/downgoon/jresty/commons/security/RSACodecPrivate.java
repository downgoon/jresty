package com.github.downgoon.jresty.commons.security;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 */
public class RSACodecPrivate extends RSACodecPublic {

	/** RSA私钥 */
	protected PrivateKey rsaPriKey;

	public RSACodecPrivate(String modulusBigIntHex, String privateExponentBigIntHex) {
		super(modulusBigIntHex);

		this.rsaPriKey = new RSAKeyPrivate(modulusBigIntHex, privateExponentBigIntHex).toPrivateKey();

	}

	public RSACodecPrivate(String modulusBigIntHex, String publicExponentBigIntHex, String privateExponentBigIntHex) {
		super(modulusBigIntHex, publicExponentBigIntHex);

		this.rsaPriKey = new RSAKeyPrivate(modulusBigIntHex, publicExponentBigIntHex, privateExponentBigIntHex)
				.toPrivateKey();
	}

	public RSACodecPrivate(PublicKey rsaPubKey, PrivateKey rsaPriKey) {
		super(rsaPubKey);
		this.rsaPriKey = rsaPriKey;
	}


	/**
	 * 应用场景：数据加密/解密，在加密/解密领域是公钥加密，私钥解密。 私钥解密方法
	 * 
	 * @param cipherBytes
	 *            密文字节数组
	 * @return 明文字节数组 如果需要把明文字节数组，编码成字符串，可以选择
	 *         {@link com.github.downgoon.jresty.commons.security000.IRSACoder#decryptByPrivate(byte[], ByteEncodeWay)}
	 */
	public final byte[] decryptByPrivate(byte[] cipherBytes) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, this.rsaPriKey);
		return cipher.doFinal(cipherBytes);
	}

	/**
	 * 应用场景：数字签名，在数字签名领域是私钥签名，公钥验签。 私钥签名方法
	 * 
	 * @param plainBytes
	 *            等待签名的字节数组，一般会是另一个明文的摘要
	 * @param encodeWay
	 *            由于签名的结果是不可见的字符数组，要想显示为字符串，必须Hex或Base64编码
	 * @return 签名结果字节数组Hex或Base64编码后的字符串
	 */
	public final byte[] signByPrivate(byte[] plainBytes) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, this.rsaPriKey);// 私钥加密
		return cipher.doFinal(plainBytes);
	}

	/**
	 * {@link com.github.downgoon.jresty.commons.RSACodecPrivate.RSACoder#signByPrivate(byte[], ByteEncodeWay)}
	 * 的别名
	 */
	public byte[] encryptByPrivate(byte[] plainBytes) throws Exception {
		return signByPrivate(plainBytes);
	}

}
