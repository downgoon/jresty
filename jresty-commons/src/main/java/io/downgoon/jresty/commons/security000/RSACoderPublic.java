package io.downgoon.jresty.commons.security000;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSACoderPublic extends Coder implements IRSACoderPublic {
	/** 给子类用的*/
	protected RSACoderPublic() {
		
	}
	
	/** RSA公钥*/
	protected RSAPublicKey rsaPubKey;
	
	public RSACoderPublic(RSAPublicKey rsaPubKey) {
		this.rsaPubKey = rsaPubKey;
		this.modulusBigIntHex = rsaPubKey.getModulus().toString(16);
		this.publicExponentBigIntHex = rsaPubKey.getPublicExponent().toString(16);
	}
	
	/** RSA公钥的跨平台（语言无关）形式 */
	protected String modulusBigIntHex;
	protected String publicExponentBigIntHex;
	
	
	public RSACoderPublic(String modulusBigIntHex, String publicExponentBigIntHex) 
	throws Exception 
	{
		super();
		this.modulusBigIntHex = modulusBigIntHex;
		this.publicExponentBigIntHex = publicExponentBigIntHex;
		
		int radix = 16;
		BigInteger n = new BigInteger(this.modulusBigIntHex,radix);
		BigInteger e =new BigInteger(this.publicExponentBigIntHex,radix);
		//1、生成密钥的表现形式（跟语言无关）
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(n,e);
		//2、生成密钥对象（跟语言相关）
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		this.rsaPubKey = (RSAPublicKey)keyFactory.generatePublic(pubKeySpec);
	}
	
	
	
	@Override
	public String encryptByPublic(byte[] plainBytes,ByteEncodeWay encodeWay) throws Exception 
	{			
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, this.rsaPubKey);//加密
		byte[] cipherTextBytes = cipher.doFinal(plainBytes);
		if(encodeWay == ByteEncodeWay.base64) {
			return encryptBASE64(cipherTextBytes);
		} else {
			return bytes2hexs(cipherTextBytes);
		}
	}
	
	
	
	
	@Override
	public final byte[] unsignByPublic(byte[] digitalSignBytes) 
	throws Exception
	{
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, this.rsaPubKey);
		return cipher.doFinal(digitalSignBytes);
	}
	/** {@link #unsignByPublic(byte[])} 的别名*/
	public byte[] decryByPublic(byte[] cipherBytes)
	throws Exception
	{
		return unsignByPublic(cipherBytes);
	}
	
	
	public RSAPublicKey getRsaPubKey() {
		return rsaPubKey;
	}



	public String getModulusBigIntHex() {
		return modulusBigIntHex;
	}



	public String getPublicExponentBigIntHex() {
		return publicExponentBigIntHex;
	}
	
}
