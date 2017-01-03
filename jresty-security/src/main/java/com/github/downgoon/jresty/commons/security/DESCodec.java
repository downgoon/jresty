package com.github.downgoon.jresty.commons.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES 加密/解密算法
 * */
public class DESCodec {

	/**
	 * DES Padding: DES/ECB/PKCS5Padding
	 * */
	public static byte[] decrypt(byte[] bcipher, byte[] bkey)
	throws Exception
	{
		DESKeySpec desKeySpec = new DESKeySpec(bkey);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = keyFactory.generateSecret(desKeySpec);	
		
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");		
		desCipher.init(Cipher.DECRYPT_MODE, desKey);

		return desCipher.doFinal(bcipher);
	}

	/**
	 * DES Padding: DES/ECB/PKCS5Padding
	 * */
	public static byte[] encrypt(byte[] bplain, byte[]	bkey)
	throws Exception
	{
		DESKeySpec desKeySpec = new DESKeySpec(bkey);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = keyFactory.generateSecret(desKeySpec);
		
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");		
		desCipher.init(Cipher.ENCRYPT_MODE, desKey);
		return desCipher.doFinal(bplain);
	}

}
