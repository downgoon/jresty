/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import junit.framework.Assert;

/**
 * @title AssertSortor
 * @description 规避反射出的方法在不同JVM下顺序不同的问题 
 * @author liwei39
 * @date 2014-8-18
 * @version 1.0
 */
public class AssertSortor {
	
	public static void assertEqualsSQL(String expected, String actual) {
		List<String> el = splitTokenSorted(expected);
		List<String> al = splitTokenSorted(actual);
		Assert.assertEquals(el.size(), al.size());
		for (int i = 0; i < el.size(); i++ ) {
			Assert.assertEquals(el.get(i), al.get(i));
		}
	}
	
	private static List<String> splitTokenSorted(String text) {
		ArrayList<String> tokens = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(text);
		while (tokenizer.hasMoreTokens()) {
			tokens.add(tokenizer.nextToken());
		}
		Collections.sort(tokens);
		return tokens;
	}

    public static SortedMap<String, String> sortMapString(Map<String, String> map) {
        SortedMap<String, String> sm = new TreeMap<String, String>();
        sm.putAll(map);
        return sm;
    }

    public static SortedMap<String, Object> sortMapObject(Map<String, Object> map) {
        SortedMap<String, Object> sm = new TreeMap<String, Object>();
        sm.putAll(map);
        return sm;
    }

    public static String sortString(String sql) {
        StringTokenizer st = new StringTokenizer(sql);
        List<String> words = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            words.add(st.nextToken());
        }
        System.out.println(words);
        Collections.sort(words);
        System.out.println(words);
        StringBuilder statement = new StringBuilder();
        for (String word : words) {
            statement.append(word).append(" ");
        }
        return statement.toString();
    }

    public static String sortByChar(String sql) {
        return sortByChar(sql, '`');
    }

    public static String sortByChar(String sql, char skipChar) {
        List<Character> chars = new ArrayList<Character>();
        for (int i = 0; i < sql.length(); i++) {
            chars.add(sql.charAt(i));
        }
        Collections.sort(chars);
        StringBuilder statement = new StringBuilder();
        for (Character c : chars) {
            if (c != skipChar) {
                statement.append(c);
            }
        }
        return statement.toString();
    }

}
