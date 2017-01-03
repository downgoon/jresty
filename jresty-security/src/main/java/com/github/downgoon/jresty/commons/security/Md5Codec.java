package com.github.downgoon.jresty.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 摘要
 * */
public class Md5Codec {

	public static byte[] digest(byte[] data) {
		try {
			return MessageDigest.getInstance("MD5").digest(data);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 Digest NotFound");
		}
	}
	
	public static byte[] encode(byte[] data) {
		return digest(data);
	}
	
	public static byte[] decode(byte[] data) {
		throw new IllegalStateException("MD5 irreversible");
	}
}
