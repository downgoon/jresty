package io.downgoon.jresty.commons.security000;

import io.downgoon.jresty.commons.security000.Coder.ByteEncodeWay;

public interface IRSACoderPublic {

	/**
	 * 应用场景：数据加密/解密，在加密/解密领域是公钥加密，私钥解密。
	 * 公钥加密方法
	 * @param	plainBytes	等待加密的明文字节
	 * @param	encodeWay	加密后密文字节编码成字符串的编码方式，可供选择的用HEX和BASE64
	 * @return	密文字节编码后的字符串
	 * */
	public abstract String encryptByPublic(byte[] plainBytes,
			ByteEncodeWay encodeWay) throws Exception;

	/**
	 * 应用场景：数字签名，在数字签名领域是私钥签名，公钥验签。
	 * 公钥验签/解签方法  （解签的本质也是公钥解密）
	 * @param	digitalSignBytes	数字签名字节数组
	 * @return	解签后的字节数组（由于签名一般是对摘要签名，因此该方法返回的结果是不可见字符）
	 * */
	public abstract byte[] unsignByPublic(byte[] digitalSignBytes)
			throws Exception;

}
