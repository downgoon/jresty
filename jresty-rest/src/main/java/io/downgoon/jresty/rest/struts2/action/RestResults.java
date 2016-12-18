package io.downgoon.jresty.rest.struts2.action;

import io.downgoon.jresty.rest.view.HttpHeaders;

public interface RestResults {

	/**
	 * 内容的表现形式
	 * */
	public abstract String REST(String methodResult);

	public abstract String REST(String methodResult,
			Object responseMessageOverwrite);

	/**
	 * 内容的表现形式
	 * */
	public abstract String REST(HttpHeaders methodResult);

	public abstract String REST(HttpHeaders methodResult,
			Object responseMessageOverwrite);
	

}
