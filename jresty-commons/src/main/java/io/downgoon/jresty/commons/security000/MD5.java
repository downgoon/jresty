package io.downgoon.jresty.commons.security000;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Md5加密
 * 
 */

public class MD5 {

	private static final Logger LOG = LoggerFactory.getLogger(MD5.class);

	public MD5() {
		
	}

	

	/**
	 * md5加密
	 * 
	 * @param origin
	 *            要加密的字符串
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = HexCodec.byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			LOG.error("对字符串" + origin + "进行md5加密时发生错误", e);
		}
		return resultString;
	}

	public static String MD5Encode(String origin, String charset) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				resultString = HexCodec.byteArrayToHexString(md.digest(resultString.getBytes(charset)));
			} catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException("UnsupportedEncodingException: " + charset);
			}

		} catch (NoSuchAlgorithmException e) {
			LOG.error("对字符串" + origin + "进行md5加密时发生错误", e);
		}
		return resultString;
	}

//	public static String urlEncode(String plain, String charset) {
//		try {
//			return URLEncoder.encode(plain, charset);
//		} catch (UnsupportedEncodingException e) {
//			throw new IllegalStateException("charset type " + charset + " not supported", e);
//		}
//	}
//
//	public static String urlEncode(String plain) {
//		return urlEncode(plain, "UTF-8");
//	}

	/**
	 * 计算MD5摘要
	 */
	public static byte[] MD5Encode(byte[] plainBytes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return md.digest(plainBytes);
	}
}
