package com.github.downgoon.jresty.rest.struts2.interceptor;

public interface InterceptorModelOverwritten<T> {

	/**
	 * 返回由Action自定义的Model
	 * 
	 * @param intercetorModel	Interceptor中提供的响应结果数据
	 * @param	interceptorClass	Interceptor实际的类
	 * */
	public T overwriteInterceptorModel(Object intercetorModel, Class<?> interceptorClass);
}
