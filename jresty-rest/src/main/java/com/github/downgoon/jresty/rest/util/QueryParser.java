package com.github.downgoon.jresty.rest.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class QueryParser {

	
	public Map<String, List<String>> queryStringList(String queryString) {
		/*
		 * RESTfm is able to handle data encoded in either
		 * application/x-www-form-urlencoded or multipart/form-data formats.
		 * http://www.restfm.com/restfm-manual/web-api-reference-documentation/
		 * submitting-data/applicationx-www-form-urlencoded-and
		 */
		/* k1=v1&k2=v2&k1=v3 */
		
		Map<String, List<String>> params = new LinkedHashMap<String, List<String>>();
		StringTokenizer tokenizer = new StringTokenizer(queryString, "&");
		while (tokenizer.hasMoreTokens()) {
			String[] pair = doPair(tokenizer.nextToken());
			if (pair == null) {
				continue; // skip illegal pair
			}
			push(pair, params);
		}
		return params;
	}
	
	
	
	/**
	 * support http request body 'application/x-www-form-urlencoded'
	 */
	public Map<String, String[]> queryString(String queryString) {
		Map<String, String[]> result = new LinkedHashMap<String, String[]>();
		Map<String, List<String>> params = queryStringList(queryString);
		
		Iterator<Entry<String, List<String>>> es = params.entrySet().iterator();
		while (es.hasNext()) {
			Entry<String, List<String>> e = es.next();
			String[] varr = new String[e.getValue().size()];
			e.getValue().toArray(varr);
			result.put(e.getKey(), varr);
		}
		return result;
	}

	/**
	 * k1=v1
	 * 
	 * @return null if illegal argument; else return `[0] is k, [1] is v`
	 */
	protected String[] doPair(String pair) {
		int idx = pair.indexOf("=");
		if (idx == -1) {
			return null;
		}
		if (idx < pair.length() - 1) {
			return new String[] { pair.substring(0, idx), pair.substring(idx + 1) }; // key & value
		}
		return new String[] { pair.substring(0, idx), "" }; // no value
		
	}
	
	protected void push(String[] pair, Map<String, List<String>> params) {
		List<String> values = params.get(pair[0]);
		if (values == null) {
			values = new ArrayList<String>();
			params.put(pair[0], values); // pair[0] is KEY
		}
		values.add(URLCodec.decodeUTF8(pair[1])); // pair[1] is VALUE
	} 
	
}
