/*
 * $Id: DefaultHttpHeaders.java 666756 2008-06-11 18:11:00Z hermanns $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.downgoon.jresty.rest.view;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_MODIFIED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default implementation of rest info that uses fluent-style construction
 */
public class DefaultHttpHeaders implements HttpHeaders {
    String resultCode;
    int status = SC_OK;
    Object etag;
    Object locationId;
    String location;
    boolean disableCaching;
    boolean noETag = false;
    Date lastModified;
    
    public DefaultHttpHeaders() {}
    
    public DefaultHttpHeaders(String result) {
        resultCode = result;
    }
    
    public DefaultHttpHeaders renderResult(String code) {
        this.resultCode = code;
        return this;
    }
    
    public DefaultHttpHeaders withStatus(int code) {
        this.status = code;
        return this;
    }
    
    public DefaultHttpHeaders withETag(Object etag) {
        this.etag = etag;
        return this;
    }

    public DefaultHttpHeaders withNoETag() {
        this.noETag = true;
        return this;
    }
    
    public DefaultHttpHeaders setLocationId(Object id) {
        this.locationId = id;
        return this;
    }
    
    public DefaultHttpHeaders setLocation(String loc) {
        this.location = loc;
        return this;
    }
    
    public DefaultHttpHeaders lastModified(Date date) {
        this.lastModified = date;
        return this;
    }
    
    public DefaultHttpHeaders disableCaching() {
        this.disableCaching = true;
        return this;
    }
    
    private LinkedHashMap<String,List<String>> httpHeaders = new LinkedHashMap<String,List<String>>();
    
    
    public DefaultHttpHeaders addHeader(String name,String value) {
    	List<String> valueList = httpHeaders.get(name);
    	if(valueList==null) {
    		valueList = new ArrayList<String>();
    		httpHeaders.put(name, valueList);
    	}
    	valueList.add(value);
    	return this;
    }
    
    
    @Override
	public String apply(HttpServletRequest request, HttpServletResponse response, Object target) {
    	
        if (disableCaching) {
            response.setHeader("Cache-Control", "no-cache");
        }
        if (lastModified != null) {
            response.setDateHeader("Last-Modified", lastModified.getTime());
        }
        if (etag == null && !noETag && target != null) {
            etag = String.valueOf(target.hashCode());
        }
        if (etag != null) {
            response.setHeader("ETag", etag.toString());
        }

        if (locationId != null) {
            String url = request.getRequestURL().toString();
            int lastSlash = url.lastIndexOf("/");
            int lastDot = url.lastIndexOf(".");
            if (lastDot > lastSlash && lastDot > -1) {
                url = url.substring(0, lastDot)+"/"+locationId+url.substring(lastDot);
            } else {
                url += "/"+locationId;
            }
            response.setHeader("Location", url);
            status = SC_CREATED;
        } else if (location != null) {
            response.setHeader("Location", location);
            status = SC_CREATED;
        }

        if (status == SC_OK && !disableCaching) {
            boolean etagNotChanged = false;
            boolean lastModifiedNotChanged = false;
            String reqETag = request.getHeader("If-None-Match");
            if (etag != null) {
                if (etag.equals(reqETag)) {
                    etagNotChanged = true;
                }
            }

            String reqLastModified = request.getHeader("If-Modified-Since");
            if (lastModified != null) {
                if (String.valueOf(lastModified.getTime()).equals(reqLastModified)) {
                    lastModifiedNotChanged = true;
                }

            }

            if ((etagNotChanged && lastModifiedNotChanged) ||
                (etagNotChanged && reqLastModified == null) ||
                (lastModifiedNotChanged && reqETag == null)) {
                status = SC_NOT_MODIFIED;
            }
        }
        //追加些HTTP包头信息
        Iterator<Entry<String, List<String>>> headerEntries = httpHeaders.entrySet().iterator();
        while(headerEntries.hasNext()) {
        	Entry<String, List<String>> entry = headerEntries.next();
        	Iterator<String> values = entry.getValue().iterator();
        	while(values.hasNext()) {
        		response.addHeader(entry.getKey(), values.next());
        	}
        }
        
        response.setStatus(status);
        return resultCode;
    }

    @Override
	public int getStatus() {
        return status;
    }
    
}
