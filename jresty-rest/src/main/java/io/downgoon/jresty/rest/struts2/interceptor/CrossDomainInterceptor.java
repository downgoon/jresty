package io.downgoon.jresty.rest.struts2.interceptor;

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

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
		return invocation.invoke();
	}
	
}
