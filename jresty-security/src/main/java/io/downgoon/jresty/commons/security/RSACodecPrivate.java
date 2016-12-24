package io.downgoon.jresty.commons.security;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 */
public class RSACodecPrivate extends RSACodecPublic {

	/**
	 * reference http://en.wikipedia.org/wiki/RSA publicKey :: (n=modulus,
	 * e=10001 publicExponentHex) privateKey :: (n=modulus, d
	 * privateExponentHex)
	 */
	protected String privateExponentBigIntHex;

	public RSACodecPrivate(String modulusBigIntHex, String publicExponentBigIntHex, String privateExponentBigIntHex)
			throws Exception {

		super(modulusBigIntHex, publicExponentBigIntHex);

		this.privateExponentBigIntHex = privateExponentBigIntHex;

		int radix = 16;
		BigInteger n = new BigInteger(this.modulusBigIntHex, radix);
		BigInteger e = new BigInteger(this.publicExponentBigIntHex, radix);
		BigInteger d = new BigInteger(this.privateExponentBigIntHex, radix);

		// 1. 生成密钥的表现形式（跟语言无关）
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(n, e);
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(n, d);

		// 2. 生成密钥对象（跟语言相关）
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		this.rsaPubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
		this.rsaPriKey = (RSAPrivateKey) keyFactory.generatePrivate(priKeySpec);
	}

	public RSACodecPrivate(RSAKeyPublic rsaKeyPublic, RSAKeyPrivate rsaKeyPrivate) throws Exception {
		this(rsaKeyPrivate.getModulusBigIntHex(), rsaKeyPublic.getPublicExponentBigIntHex(),
				rsaKeyPrivate.getPrivateExponentBigIntHex());
	}

	/** RSA私钥 */
	protected RSAPrivateKey rsaPriKey;

	public RSACodecPrivate(RSAPublicKey rsaPubKey, RSAPrivateKey rsaPriKey) {
		super(rsaPubKey);

		this.rsaPriKey = rsaPriKey;
		this.privateExponentBigIntHex = rsaPriKey.getPrivateExponent().toString(16);
	}

	/**
	 * M1: 生成密钥对
	 */
	public static RSACodecPrivate generateKeyPairs() throws Exception {
		final int keyLength = 512;
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keyLength);// 1024 or 512 (flash player noly
											// support
											// 512)//http://crypto.hurlant.com/demo/
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		RSACodecPrivate coder = new RSACodecPrivate(publicKey, privateKey);
		// 计算输出KEY
		return coder;
	}

	/**
	 * 应用场景：数据加密/解密，在加密/解密领域是公钥加密，私钥解密。 私钥解密方法
	 * 
	 * @param cipherBytes
	 *            密文字节数组
	 * @return 明文字节数组 如果需要把明文字节数组，编码成字符串，可以选择
	 *         {@link io.downgoon.jresty.commons.security000.IRSACoder#decryptByPrivate(byte[], ByteEncodeWay)}
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
	 * {@link io.downgoon.jresty.commons.RSACodecPrivate.RSACoder#signByPrivate(byte[], ByteEncodeWay)}
	 * 的别名
	 */
	public byte[] encryptByPrivate(byte[] plainBytes) throws Exception {
		return signByPrivate(plainBytes);
	}

	public RSAPrivateKey getRsaPriKey() {
		return rsaPriKey;
	}

	public String getPrivateExponentBigIntHex() {
		return privateExponentBigIntHex;
	}

}
