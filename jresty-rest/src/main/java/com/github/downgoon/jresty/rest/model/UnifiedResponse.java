package com.github.downgoon.jresty.rest.model;

/**
 * 开放API所使用的统一的响应数据格式
 * */
public class UnifiedResponse implements UnifiedResponseCode {

	/** 响应码（必选字段）*/
	private int status = RC_SUCC;
	
	/** 响应码提示信息，一般可以直接显示给用户看（必选字段）*/
	private String message = "OK";
	
	/** optional argument for debugging */
	private String debug;
	
	/** 附件信息，其他拓展信息，可选字段，一般可以是MAP*/
	private Object attachment;
	
	public UnifiedResponse() {
		this.status = RC_SUCC;
		this.message = "OK";
	}
	
	public UnifiedResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public UnifiedResponse(int status, String message, Object attachment) {
		this.status = status;
		this.message = message;
		this.attachment = attachment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}
	

	/**
	 * @param	refNum	log reference number
	 * */
	public void setDebug(String refNum, String debugInfo) {
		this.debug = refNum + ":" + debugInfo;
	}


	public Object getAttachment() {
		return attachment;
	}

	public void setAttachment(Object data) {
		this.attachment = data;
	}

	
	public String toString() {
		return "UnifiedResponse [status=" + status + ", message=" + message + ", debug=" + debug + ", attachment="
				+ attachment + "]";
	}

}
