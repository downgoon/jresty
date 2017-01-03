package com.github.downgoon.jresty.rest.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * AJAX 跨域设置
 * */
public class CrossDomainInterceptor  implements Interceptor {
	
	private static final long serialVersionUID = -7937450389827212785L;

	@Override
	public void destroy() {
		
	}

	@Override
	public void init() {
		
	}

	/**
	 * REFER: http://www.ruanyifeng.com/blog/2016/04/cors.html
	 * */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String ajaxOrigin = request.getHeader("Origin");
		if (ajaxOrigin != null && isAllowedOrigin(ajaxOrigin)) {
			response.setHeader("Access-Control-Allow-Origin", ajaxOrigin);
		} else {
			response.setHeader("Access-Control-Allow-Origin", "*");
		}
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return invocation.invoke();
	}
	
	protected boolean isAllowedOrigin(String ajaxOrigin) {
		return true;
	}
	
}
