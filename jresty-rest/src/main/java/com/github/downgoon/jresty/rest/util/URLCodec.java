package com.github.downgoon.jresty.rest.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLCodec {

	public static String encode(String s, String enc) {
		try {
			return URLEncoder.encode(s, enc);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(String.format("url encoder %s not supported", enc));
		}
	}
	
	public static String encodeUTF8(String s) {
		return encode(s, "UTF-8");
	}
	
	public static String decode(String s, String enc) {
		try {
			return URLDecoder.decode(s, enc);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(String.format("url encoder %s not supported", enc));
		}
	}
	
	public static String decodeUTF8(String s) {
		return decode(s, "UTF-8");
	}
	
}
