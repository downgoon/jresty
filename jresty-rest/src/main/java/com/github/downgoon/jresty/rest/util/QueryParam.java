package com.github.downgoon.jresty.rest.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

public class QueryParam {

	private static final Logger LOG = LoggerFactory.getLogger(QueryParam.class);
	
	private static final QueryParser queryParser = new QueryParser();
	
	private Map<String, String[]> queryMap; 
	
	private Map<String, String[]> bodyMap; // maybe null
	
	private boolean isShowLog = true;
	
	@SuppressWarnings("unchecked")
	public QueryParam(HttpServletRequest httpRequest) {
		if (isShowLog) {
			LOG.info(">> query param: {}", httpRequest.getQueryString());
		}
		this.queryMap = (Map<String, String[]>) httpRequest.getParameterMap();
		this.bodyMap = null;
		if (httpRequest.getContentLength() > 0 && ! "POST".equalsIgnoreCase(httpRequest.getMethod())) { // POST body content has bean read EOF by framework 
			try {
				DataInputStream dis = new DataInputStream(httpRequest.getInputStream());
				byte[] contentBytes = new byte[httpRequest.getContentLength()];
				dis.readFully(contentBytes);
				String contentText = new String(contentBytes, "UTF-8");
				if (isShowLog) {
					LOG.info(">> body param: {}", contentText);
				}
				this.bodyMap = queryParser.queryString(contentText);
				
			} catch (IOException ioEx) {
				// throw new IllegalStateException("request body read exception", ioEx); // maybe body has bean read out by other places
				// LOG.warn("http request body has bean read out by other places", ioEx);
				LOG.warn("http request body has bean read out by other places: {}", httpRequest.getRequestURI());
			}
		}
		
		if (isShowLog) {
			Iterator<Entry<String, String[]>>  qes = queryMap.entrySet().iterator();
			while (qes.hasNext()) {
				Entry<String, String[]> qe = qes.next();
				LOG.info(">> query param: {}={}",  qe.getKey(), Arrays.toString(qe.getValue()));
			}
			if (bodyMap != null) {
				Iterator<Entry<String, String[]>>  bes = queryMap.entrySet().iterator();
				while (bes.hasNext()) {
					Entry<String, String[]> be = bes.next();
					LOG.info(">> body param: {}={}",  be.getKey(), Arrays.toString(be.getValue()));
				}
			}
			
		}
		
		/* Quick-And-Dirty: add api operator (global argument)*/
		String operator = ( getValue0("operator") != null ? getValue0("operator") : getValue0("createBy") );
		if (operator != null) {
			ActionContext.getContext().put("operator", operator);
		}
	}
	
	/**
	 * queryMap FIRST (ORDER: queryMap > bodyMap)
	 * */
	public String[] getValue(String fname) {
		String[] fargu = queryMap.get(fname);
		if (fargu != null) {
			return fargu;
		}
		if (bodyMap != null) {
			fargu = bodyMap.get(fname);
		}
		return fargu;
	}
	
	public String getValue0(String fname) {
		String[] values = getValue(fname);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}
	
}
