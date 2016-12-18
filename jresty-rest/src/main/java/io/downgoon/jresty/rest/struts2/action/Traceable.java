package io.downgoon.jresty.rest.struts2.action;

public interface Traceable {

	/**
	 * 生成错误日志索引号（14位时间+6位随机数），保证一个HTTP请求，只会产生一个日志索引号（即使多次调用）。
	* */
	public String getRefNum4Log();
	
}
