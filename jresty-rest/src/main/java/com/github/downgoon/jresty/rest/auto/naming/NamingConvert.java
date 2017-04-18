/**
 * 
 */
package com.github.downgoon.jresty.rest.auto.naming;

import java.util.StringTokenizer;

public class NamingConvert {

	public static String javaAttriName(String dbFieldName) {
		/* ApplicationName => applicationName 
		 * Application_Name => applicationName
		 * Application_name => applicationName
		 * application_name => applicationName
		 * */
		StringBuffer attriName = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(dbFieldName, "_");
		int tn = 0; // token number
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			// skip empty string and change the first char of words to upper case
			if (token.length() == 0) {
				continue;
			}
			boolean toUpperCase = ( tn > 0 && (token.charAt(0) >= 'a' && token.charAt(0) <= 'z') );
			boolean toLowerCase = ( tn == 0 && (token.charAt(0) >= 'A' && token.charAt(0) <= 'Z') );
			if (toUpperCase || toLowerCase) {
				attriName.append(toUpperCase ? (token.charAt(0) + "").toUpperCase() : (token.charAt(0) + "").toLowerCase());
				if (token.length() > 1) {
					attriName.append(token.substring(1));
				}
			} else {
				attriName.append(token);
			}
			tn ++;
		}
		
		return attriName.toString();
	}
	
	
	public static String javaClassName(String dbTableName) {
		String token = javaAttriName(dbTableName);
		if (token.charAt(0) >= 'a' && token.charAt(0) <= 'z') {
			if (token.length() > 1) {
				return (token.charAt(0) + "").toUpperCase() + token.substring(1);
			} else {
				return (token.charAt(0) + "").toUpperCase();
			}
		}
		return token;
	}
	
	/**
	 * Application => application
	 * AppName => appName
	 * */
	public static String javaObjectName(String javaClassName) {
		if (javaClassName.charAt(0) >= 'A' && javaClassName.charAt(0) <= 'Z') {
			if (javaClassName.length() > 1) {
				return (javaClassName.charAt(0) + "").toLowerCase() + javaClassName.substring(1);
			} else {
				return (javaClassName.charAt(0) + "").toLowerCase();
			}
		}
		return javaClassName;
	}
	
	public static String unwrap(String mysqlName) { // trim ``
		if (mysqlName.startsWith("`")) {
			return mysqlName.substring(1, mysqlName.length() - 1);
		} else {
			return mysqlName;
		}
	}
	
	
}
