package io.downgoon.jresty.commons.security;

import io.downgoon.jresty.commons.security.Coder.ByteEncodeWay;

public interface IRSACoder extends IRSACoderPublic {

	/**
	 * 应用场景：数据加密/解密，在加密/解密领域是公钥加密，私钥解密。
	 * 私钥解密方法
	 * @param	cipherBytes	密文字节数组
	 * @return	明文字节数组
	 * 如果需要把明文字节数组，编码成字符串，可以选择  {@link io.downgoon.jresty.commons.security.IRSACoder#decryptByPrivate(byte[], ByteEncodeWay)}
	 */
	public abstract byte[] decryptByPrivate(byte[] cipherBytes)
			throws Exception;

	/**
	 * 应用场景：数据加密/解密，在加密/解密领域是公钥加密，私钥解密。
	 * 私钥解密方法
	 * @see #decryptByPrivate(byte[])	在这个方法的基础上，对不可见的明文字节，通过Hex或Base64编码成字符串
	 * */
	public abstract String decryptByPrivate(byte[] cipherBytes,
			ByteEncodeWay encodeWay) throws Exception;

	/**
	 * 应用场景：数字签名，在数字签名领域是私钥签名，公钥验签。
	 * 私钥签名方法
	 * @param	plainBytes	等待签名的字节数组，一般会是另一个明文的摘要
	 * @param	encodeWay	由于签名的结果是不可见的字符数组，要想显示为字符串，必须Hex或Base64编码
	 * @return	签名结果字节数组Hex或Base64编码后的字符串 
	 * */
	public abstract String signByPrivate(byte[] plainBytes,
			ByteEncodeWay encodeWay) throws Exception;

}
