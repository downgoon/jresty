package com.github.downgoon.jresty.commons.security;

/**
 * URL 字符转义
 * */
public class Base64UrlEscape {
	
	/** original chars */
	private static final char[] rchar = {'+', '/', '='}; 
	
	/** escaped chars */
	private static final char[] cchar = {'-', '_', '.'};
	
	/*
	 * Base编码使用 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"，再加上补齐长度的1个或2个"="号，共计65种字符。
	 * 然而，这三个特殊字符 “+/=” 在 URL 中是保留字，为了在URL中传递Base64，把它们依次替换成 “-_.” 。
	 * 如果需要把两个Base64连接起来，以便后期切割，可以采用“*”星号字符，因为星号既不出现在Base64，又不是URL保留字。
	 * 
	 * 附录：URL保留字
	 * ;/?:@&=+$,
	 * */
	
	public static String escape(String ambiguousText) {
		String escaped = new String(ambiguousText);
		for (int i = 0; i < rchar.length; i++) {
			escaped = escaped.replace(rchar[i], cchar[i]);
		}
		return escaped;
	}

	public static String unescape(String escapedText) {
		String original = new String(escapedText);
		for (int i = 0; i < cchar.length; i++) {
			original = original.replace(cchar[i], rchar[i]);
		}
		return original;
	}
	
}
