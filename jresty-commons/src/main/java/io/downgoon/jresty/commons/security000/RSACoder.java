package io.downgoon.jresty.commons.security000;

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
public  class RSACoder extends RSACoderPublic implements IRSACoder {
	
	/**
	 * M1: 生成密钥对
	 * */
	public static IRSACoder generateKeyPairs() throws Exception {
		final int keyLength = 512;
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(keyLength);//1024 or 512 (flash player noly support 512)//http://crypto.hurlant.com/demo/
		KeyPair keyPair = keyPairGen.generateKeyPair();
		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		IRSACoder coder = new RSACoder(publicKey, privateKey);
		//计算输出KEY
		return coder;
	}
	
	/**
	 * reference http://en.wikipedia.org/wiki/RSA 
	 * publicKey :: (n=modulus, e=10001 publicExponentHex)
	 * privateKey :: (n=modulus, d privateExponentHex)
	 * */
	protected String privateExponentBigIntHex;
	
	
	/** RSA私钥*/
	protected RSAPrivateKey rsaPriKey;
	
	public RSACoder(RSAPublicKey rsaPubKey,RSAPrivateKey rsaPriKey) {
		super(rsaPubKey);
		
		this.rsaPriKey = rsaPriKey;
		this.privateExponentBigIntHex = rsaPriKey.getPrivateExponent().toString(16);
	}
	
	public RSACoder(String modulusBigIntHex, String publicExponentBigIntHex,
			String privateExponentBigIntHex) throws Exception {
		super();
		
		this.modulusBigIntHex = modulusBigIntHex;
		this.publicExponentBigIntHex = publicExponentBigIntHex;
		this.privateExponentBigIntHex = privateExponentBigIntHex;
		
		int radix = 16;
		BigInteger n = new BigInteger(this.modulusBigIntHex,radix);
		BigInteger e =new BigInteger(this.publicExponentBigIntHex,radix);
		BigInteger d = new BigInteger(this.privateExponentBigIntHex,radix);
		//1、生成密钥的表现形式（跟语言无关）
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(n,e);
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(n, d);
		//2、生成密钥对象（跟语言相关）
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		this.rsaPubKey = (RSAPublicKey)keyFactory.generatePublic(pubKeySpec);
		this.rsaPriKey = (RSAPrivateKey)keyFactory.generatePrivate(priKeySpec);
	}
	
	
	
	
	@Override
	public final byte[] decryptByPrivate(byte[] cipherBytes) throws Exception 
	{
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, this.rsaPriKey);
		return cipher.doFinal(cipherBytes);
	}

	@Override
	public final String decryptByPrivate(byte[] cipherBytes,ByteEncodeWay encodeWay) throws Exception {
		byte[] plainTextBytes = decryptByPrivate(cipherBytes);
		
		if(encodeWay == null) {//如果明文全都是可见字符，那么可以直接显示为字符串
			return new String(plainTextBytes);
		} 
		else  //明文包含不可见字符，需要编码才能显示为字符串
		if(encodeWay == ByteEncodeWay.base64) {
			return encryptBASE64(plainTextBytes);
		} else if(encodeWay == ByteEncodeWay.hex) {
			return bytes2hexs(plainTextBytes);
		}
		else {
			return bytes2hexs(plainTextBytes);
		}
	}

	@Override
	public final String signByPrivate(byte[] plainBytes,ByteEncodeWay encodeWay) throws Exception {			
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, this.rsaPriKey);//私钥加密
		byte[] cipherTextBytes = cipher.doFinal(plainBytes);
		if(encodeWay == ByteEncodeWay.base64) {
			return encryptBASE64(cipherTextBytes);
		} else {
			return bytes2hexs(cipherTextBytes);
		}
	}
	/** {@link io.downgoon.jresty.commons.security000.RSACoder#signByPrivate(byte[], ByteEncodeWay)} 的别名 */
	public String encryptByPrivate(byte[] plainBytes,ByteEncodeWay encodeWay) throws Exception {
		return signByPrivate(plainBytes,encodeWay);
	}
	
	public RSAPrivateKey getRsaPriKey() {
		return rsaPriKey;
	}

	public String getPrivateExponentBigIntHex() {
		return privateExponentBigIntHex;
	}

}
