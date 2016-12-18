/*
 * $Id: HtmlHandler.java 651946 2008-04-27 13:41:38Z apetrelli $
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

package io.downgoon.jresty.rest.view;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Handles HTML content, usually just a simple passthrough to the framework
 */
public class HtmlHandler implements ContentTypeHandler {

    @Override
	public String fromObject(Object obj, String resultCode, Writer out) throws IOException {
    	return resultCode;
    }

    public void toObject(Reader in, Object target) {
    }
    
    private String contentType="application/xhtml+xml";
    
    private String contentEncode = "UTF-8";
    
    private String extension = "html";

    @Override
	public String getExtension() {
        return this.extension;
    }

    @Override
	public String getContentType() {
        return this.contentType;
    }

	@Override
	public String getContentEncode() {
		return this.contentEncode;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentEncode(String contentEncode) {
		this.contentEncode = contentEncode;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	private Object attachement;
	@Override
	public Object getAttachment() {
		return attachement;
	}

	@Override
	public void setAttachment(Object attachment) {
		this.attachement = attachment;
	}

	private int cacheControlSecond = -1;
	@Override
	public int getCacheControlSecond() {
		return cacheControlSecond;
	}

	public void setCacheControlSecond(int cacheControlSecond) {
		this.cacheControlSecond = cacheControlSecond;
	}
	
}
