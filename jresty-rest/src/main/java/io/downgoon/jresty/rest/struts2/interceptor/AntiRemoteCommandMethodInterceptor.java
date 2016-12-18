package io.downgoon.jresty.rest.struts2.interceptor;

import com.opensymphony.xwork2.ActionInvocation;

import io.downgoon.jresty.rest.view.DefaultHttpHeaders;

public class AntiRemoteCommandMethodInterceptor extends RestInterceptorSupport {

	private static final long serialVersionUID = 3435044517305700407L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//404表示您访问的URL地址不存在
		return REST(new DefaultHttpHeaders().withStatus(404));
	}
	
}
