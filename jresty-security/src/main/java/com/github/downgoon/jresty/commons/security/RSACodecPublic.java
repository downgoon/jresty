package com.github.downgoon.jresty.commons.security;

import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSACodecPublic {

	/** RSA公钥 */
	protected PublicKey rsaPubKey;

	public RSACodecPublic(String modulusBigIntHex) {
		this.rsaPubKey = new RSAKeyPublic(modulusBigIntHex).toPublicKey();
	}

	public RSACodecPublic(String modulusBigIntHex, String publicExponentBigIntHex) {
		this.rsaPubKey = new RSAKeyPublic(modulusBigIntHex, publicExponentBigIntHex).toPublicKey();
	}

	public RSACodecPublic(PublicKey rsaPubKey) {
		this.rsaPubKey = rsaPubKey;
	}


	/**
	 * 应用场景：数据加密/解密，在加密/解密领域是公钥加密，私钥解密。 公钥加密方法
	 * 
	 * @param bplain
	 *            等待加密的明文字节
	 * @param encodeWay
	 *            加密后密文字节编码成字符串的编码方式，可供选择的用HEX和BASE64
	 * @return 密文字节编码后的字符串
	 */
	public byte[] encryptByPublic(byte[] bplain) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, this.rsaPubKey);
		return cipher.doFinal(bplain);

	}

	/**
	 * 应用场景：数字签名，在数字签名领域是私钥签名，公钥验签。 公钥验签/解签方法 （解签的本质也是公钥解密）
	 * 
	 * @param bsign
	 *            数字签名字节数组
	 * @return 解签后的字节数组（由于签名一般是对摘要签名，因此该方法返回的结果是不可见字符）
	 */
	public final byte[] unsignByPublic(byte[] bsign) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, this.rsaPubKey);
		return cipher.doFinal(bsign);
	}

	/**
	 * @param bsign
	 *            数字签名的字节数组表示形式
	 * @param dataSigned
	 *            被签名的原始数据
	 * @return 返回验签是否正确。
	 * @throws 如果签名解签过程异常，则会异常。异常和返回False都表示验签失败。
	 */
	public final boolean verifySignByPublic(byte[] bsign, byte[] dataSigned) throws Exception {
		byte[] unsignData = unsignByPublic(bsign);
		return isEqualBytes(dataSigned, unsignData);
	}

	private static boolean isEqualBytes(byte[] a, byte[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	/** {@link #unsignByPublic(byte[])} 的别名 */
	public byte[] decryByPublic(byte[] cipherBytes) throws Exception {
		return unsignByPublic(cipherBytes);
	}

}
