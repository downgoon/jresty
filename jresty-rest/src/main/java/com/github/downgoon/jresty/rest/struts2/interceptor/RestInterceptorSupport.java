package com.github.downgoon.jresty.rest.struts2.interceptor;

import com.github.downgoon.jresty.rest.struts2.action.RandTimeTracer;
import com.github.downgoon.jresty.rest.struts2.action.RestResults;
import com.github.downgoon.jresty.rest.struts2.action.Traceable;
import com.github.downgoon.jresty.rest.view.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public abstract class RestInterceptorSupport extends MethodFilterInterceptor implements RestResults, Traceable {
	
	private static final long serialVersionUID = 829958808852676788L;
	
	private RestResults restResultsHandler;
	
	private Traceable tracer = new RandTimeTracer();

	@Override
	public String REST(HttpHeaders methodResult,
			Object responseMessageOverwrite) {
		return restResultsHandler.REST(methodResult, responseMessageOverwrite);
	}

	@Override
	public String REST(HttpHeaders methodResult) {
		return restResultsHandler.REST(methodResult);	
	}

	@Override
	public String REST(String methodResult,
			Object responseMessageOverwrite) {
		
		Object responseModel = responseMessageOverwrite;
		Object action = ActionContext.getContext().getActionInvocation().getAction();
		if(action instanceof InterceptorModelOverwritten) {
			responseModel = ((InterceptorModelOverwritten<?>) action).overwriteInterceptorModel(responseMessageOverwrite, this.getClass());
		} 
		return restResultsHandler.REST(methodResult, responseModel);
	}

	@Override
	public String REST(String methodResult) {
		return restResultsHandler.REST(methodResult);
	}

	
	
	
	@Override
	public String getRefNum4Log() {
		return tracer.getRefNum4Log();
	}

	public RestResults getRestResultsHandler() {
		return restResultsHandler;
	}

	public void setRestResultsHandler(RestResults restResultsHandler) {
		this.restResultsHandler = restResultsHandler;
	}
	
	
	
	/**
	 * 提取Action描述信息
	 * */
	protected final String getActionNameDesciption(ActionInvocation invocation) {
		return invocation.getProxy().getNamespace()+"/"+invocation.getProxy().getActionName()+"-"+invocation.getProxy().getMethod();
	}

	
	
}
