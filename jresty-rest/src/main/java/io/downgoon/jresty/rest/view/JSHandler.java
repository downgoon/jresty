package io.downgoon.jresty.rest.view;

import java.io.IOException;
import java.io.Writer;

public class JSHandler implements ContentTypeHandler {

	private ContentTypeHandler delegate;

	public ContentTypeHandler getDelegate() {
		return delegate;
	}

	public void setDelegate(ContentTypeHandler delegate) {
		this.delegate = delegate;
	}

	/** 把一个对象序列化成到Writer里面去*/
	@Override
	public String fromObject(Object obj, String resultCode, Writer stream)
			throws IOException {
		stream.write(callbackMethod);
		stream.write("(");
		delegate.fromObject(obj, resultCode, stream);
		stream.write(")");
		return null;
	}
	
	 private String contentType="text/javascript";
	    
	 private String contentEncode = "UTF-8";
	    
	 private String extension = "js";

	@Override
	public String getContentEncode() {
		return this.contentEncode;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public String getExtension() {
		return this.extension;
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

	private String callbackMethod = "callbackMethod";
	
	@Override
	public Object getAttachment() {
		return callbackMethod;
	}

	@Override
	public void setAttachment(Object attachment) {
		this.callbackMethod = attachment.toString();
	}

	private int cacheControlSecond = -1;
	@Override
	public int getCacheControlSecond() {
		return cacheControlSecond;
	}

	public void setCacheControlSecond(int cacheControlSecond) {
		this.cacheControlSecond = cacheControlSecond;
	}

	public String getCallbackMethod() {
		return callbackMethod;
	}

	public void setCallbackMethod(String callbackMethod) {
		this.callbackMethod = callbackMethod;
	}
	
}
