package io.downgoon.jresty.commons.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLEncodec {

	public static String encodeUTF8(String text) {
		try {
			return URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF8 Not Supported");
		}
	}
	
	public static String decodeUTF8(String text) {
		try {
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF8 Not Supported");
		}
	}
	
}
