/*
 * $Id: DefaultContentTypeHandlerManager.java 780096 2009-05-29 20:22:09Z wesw $
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

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.config.entities.ActionConfig;

/**
 * Manages {@link ContentTypeHandler} instances and uses them to
 * process results
 */
public class DefaultContentTypeHandlerManager implements ContentTypeHandlerManager {

    /** ContentTypeHandlers keyed by the extension */
    Map<String,ContentTypeHandler> handlersByExtension = new HashMap<String,ContentTypeHandler>();

    String defaultExtension;
    
    String jsCallbackMethodName = "callback";

	/**
     * Gets the handler for the request by looking at the extension of the request
     * @param req The request
     * @return The appropriate handler
     */
    @Override
	public ContentTypeHandler getHandlerForRequest(HttpServletRequest req) {
        String extension = findExtension(req.getRequestURI());
        if (extension == null || extension.equals("")) {
             extension = defaultExtension;
        }
        ContentTypeHandler handler = handlersByExtension.get(extension);
        if(req.getParameter(jsCallbackMethodName)!=null) {
        	handler.setAttachment(req.getParameter(jsCallbackMethodName).toString());
        }
        return  handler;
    }

    /**
     * Gets the handler for the response by looking at the extension of the request
     * @param req The request
     * @return The appropriate handler
     */
    @Override
	public ContentTypeHandler getHandlerForResponse(HttpServletRequest req, HttpServletResponse res) {
        String extension = findExtension(req.getRequestURI());
        if (extension == null || extension.equals("")) {
            extension = defaultExtension;
        }
        ContentTypeHandler handler = handlersByExtension.get(extension);
        if(req.getParameter(jsCallbackMethodName)!=null) {
        	handler.setAttachment(req.getParameter(jsCallbackMethodName).toString());
        }
        return  handler;
    }

    /**
     * Handles the result using handlers to generate content type-specific content
     * 
     * @param actionConfig The action config for the current request
     * @param methodResult The object returned from the action method
     * @param resultBean The object to return, usually the action object
     * @return The new result code to process
     * @throws IOException If unable to write to the response
     */
    @Override
	public String handleResult(ActionConfig actionConfig, Object methodResult, Object resultBean)
            throws IOException {
        String resultCode = null;
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpServletResponse res = ServletActionContext.getResponse();
        
//        if (target instanceof ModelDriven) {
//            target = ((ModelDriven<?>)target).getModel();
//        }

//        boolean statusNotOk = false;
        if (methodResult instanceof HttpHeaders) {
            HttpHeaders info = (HttpHeaders) methodResult;
            resultCode = info.apply(req, res, resultBean);
        } else {
            resultCode = (String) methodResult;
        }
        
        ContentTypeHandler handler = getHandlerForResponse(req, res);
        if (handler != null) {
            String extCode = resultCode+"-"+handler.getExtension();
            if (actionConfig.getResults().get(extCode) != null) {
                resultCode = extCode;
            } else {
                StringWriter writer = new StringWriter();
                resultCode = handler.fromObject(resultBean, resultCode, writer); // Bean Serialization
                String text = writer.toString();
                if (text.length() > 0) {
                    byte[] data = text.getBytes(handler.getContentEncode());
                    res.setContentLength(data.length);
                    res.setContentType(handler.getContentType());//设置类型
                    res.setCharacterEncoding(handler.getContentEncode());//设置编码
                    if(handler.getCacheControlSecond() >= 0) {//小于0表示不考虑Cache
                    	res.addHeader("Cache-Control", "max-age="+handler.getCacheControlSecond());
                    }
                    res.getOutputStream().write(data);
                    res.getOutputStream().close();
                } else {//HTMLHandler
//                	res.setContentType(handler.getContentType());//设置类型
//                  res.setCharacterEncoding(handler.getContentEncode());//设置编码
                	if(handler.getCacheControlSecond() >= 0) {//小于0表示不考虑Cache
                    	res.addHeader("Cache-Control", "max-age="+handler.getCacheControlSecond());
                    }
                	req.setAttribute("restResultBean", resultBean); // get it in jsp
                	
                	// redirect support
                }
            }
        }
        return resultCode; // NULL if json or xml
        
    }
    
    /**
     * Finds the extension in the url
     * 
     * @param url The url
     * @return The extension
     */
    protected String findExtension(String url) {
        int dotPos = url.lastIndexOf('.');
        int slashPos = url.lastIndexOf('/');
        if (dotPos > slashPos && dotPos > -1) {
            return url.substring(dotPos+1);
        }
        return null;
    }
    
    @Override
	public String getDefaultExtension() {
		return defaultExtension;
	}
    
    public void setDefaultExtension(String name) {
        this.defaultExtension = name;
    }

    public Map<String, ContentTypeHandler> getHandlersByExtension() {
		return handlersByExtension;
	}

    public void setHandlersByExtension(
			Map<String, ContentTypeHandler> handlersByExtension) {
		this.handlersByExtension = handlersByExtension;
	}

	public String getJsCallbackMethodName() {
		return jsCallbackMethodName;
	}

	public void setJsCallbackMethodName(String jsCallbackMethodName) {
		this.jsCallbackMethodName = jsCallbackMethodName;
	}
    
    
}
