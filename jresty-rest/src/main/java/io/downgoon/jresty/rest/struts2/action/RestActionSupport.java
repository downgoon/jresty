package io.downgoon.jresty.rest.struts2.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import io.downgoon.jresty.rest.view.HttpHeaders;


public class RestActionSupport extends ActionSupport implements RestMethods, RestResults, RestParams, Traceable {
	
	private static final long serialVersionUID = 829958808852676788L;
	
	private RestResults restResultsHandler;
	
	private RestParams restParamsHandler = new RestParamsHandler();
	
	private Traceable tracer = new RandTimeTracer();
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	@Override
	public String create() throws Exception {
		return SUCCESS;
	}

	@Override
	public String index() throws Exception {
		return SUCCESS;
	}

	@Override
	public String remove() throws Exception {
		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		return SUCCESS;
	}

	@Override
	public String view() throws Exception {
		return SUCCESS;
	}

	
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
		return restResultsHandler.REST(methodResult, responseMessageOverwrite);
	}

	@Override
	public String REST(String methodResult) {
		return restResultsHandler.REST(methodResult);
	}

	
	@Override
	public String getURIExtension() {
		return restParamsHandler.getURIExtension();
	}
	
	@Override
	public Map<String, Object> getURIParams() {
		return restParamsHandler.getURIParams();
	}
	
	
	@Override
	public List<String> getURIParamsSequential() {
		return restParamsHandler.getURIParamsSequential();
	}
	
	
	
	@Override
	public String getParam(int index) {
		return restParamsHandler.getParam(index);
	}
	
	@Override
	public String getParam(String name) {
		return restParamsHandler.getParam(name);
	}
	
	
	@Override
	public String getParam(int index, String name) {
		return restParamsHandler.getParam(index, name);
	}
	
	
	@Override 
	public String getParam(String name, int index) {
		return restParamsHandler.getParam(name, index);
	}
	
	public String getRefNum4Log() {
		return tracer.getRefNum4Log();
	}
	

	public RestResults getRestResultsHandler() {
		return restResultsHandler;
	}

	public void setRestResultsHandler(RestResults restResultsHandler) {
		this.restResultsHandler = restResultsHandler;
	}

	protected final HttpServletRequest getHttpRequest(){		 
		return (HttpServletRequest)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
	}
	
	
	protected final HttpServletResponse getHttpResponse(){		 
		return (HttpServletResponse)ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
	}
	
	protected static final String REDIRECT_AQS_MAP = RestActionSupport.class.getSimpleName()+"#REDIRECT_AQS_MAP";
	
	
}
