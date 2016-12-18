/*
 * $Id: ContentTypeHandler.java 666756 2008-06-11 18:11:00Z hermanns $
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
import java.io.Writer;

/**
 * Handles transferring content to and from objects for a specific content type
 */
public interface ContentTypeHandler {
    
    /**
     * Writes content to the stream
     * 
     * @param obj The object to write to the stream, usually the Action class
     * @param resultCode The original result code
     * @param stream The output stream, usually the response
     * @return The new result code
     * @throws IOException If unable to write to the output stream
     */
    String fromObject(Object obj, String resultCode, Writer stream) throws IOException;
    
    /**
     * Gets the content type for this handler
     * 
     * @return The mime type
     */
    String getContentType();
    
    /**
     * 
     * */
    String getContentEncode();
    
    /**
     * Gets the extension this handler supports
     * 
     * @return The extension
     */
    String getExtension();
    
    /**
     * 缓存时间
     * */
    int getCacheControlSecond();
    
    /** 跟HTTP请求相关的附件  (为支持 JSONP )*/
    Object getAttachment();
    
    void setAttachment(Object attachment);
    
   
}
