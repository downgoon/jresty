package io.downgoon.jresty.rest.struts2.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.config.entities.ActionConfig;

import io.downgoon.jresty.rest.util.UrlUtil;
import io.downgoon.jresty.rest.view.ContentTypeHandlerManager;
import io.downgoon.jresty.rest.view.HttpHeaders;
import io.downgoon.jresty.rest.view.RedirectTypeHandler;

public class RestResultsHandler implements RestResults {
	
	private static Log log = LogFactory.getLog(RestResultsHandler.class);
	
	private ContentTypeHandlerManager contentTypeHandlerManager;
	
	private RedirectTypeHandler redirectTypeHandler;
	

	@Override
	public final String REST(String methodResult){
		return intercepteBetweenActionAndResult(methodResult,null);
	}
	
	@Override
	public final String REST(String methodResult,Object responseMessageOverwrite){
		return intercepteBetweenActionAndResult(methodResult,responseMessageOverwrite);
	}
	
	@Override
	public final String REST(HttpHeaders methodResult){
		return intercepteBetweenActionAndResult(methodResult,null);
	}
	
	@Override
	public final String REST(HttpHeaders methodResult,Object responseMessageOverwrite){
		return intercepteBetweenActionAndResult(methodResult,responseMessageOverwrite);
	}

	/** 在Action和Result之间执行的逻辑
	 * @param	responseMessageOverwrite	响应报文
	 * 对于JSON/XML格式的响应，需要指定待序列化的响应报文。程序选择的顺序是：
	 * 1、优先考虑用户专门指定的 responseMessageOverwrite 报文；
	 * 2、如果用户没有专门指定  responseMessageOverwrite ，再考虑Action是否实现ModelDriven；
	 * 3、如果Action还未实现ModelDriven，则整个Action对象被作为响应报文被序列化。
	 * */
	private String intercepteBetweenActionAndResult(Object methodResult,Object responseMessageOverwrite) {
		ActionConfig actionConfig = ActionContext.getContext().getActionInvocation().getProxy().getConfig();
		
		Object resultBean = getRestResultBean(responseMessageOverwrite);
		
		try {
			if (doRedirectIfNeed(resultBean)) {
				return null;
			}
			
			return contentTypeHandlerManager.handleResult(actionConfig, methodResult, resultBean);
		} catch (IOException ioe) {
			log.warn("依据拓展名进行内容处理失败",ioe);
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setStatus(500);//500表示系统异常
			response.addHeader("prompt", "extension handle error");
			
			response.setContentLength(0);
			response.setContentType(contentTypeHandlerManager.getHandlerForResponse(request, response).getContentType());
			try {
				response.getOutputStream().write(new byte[]{});
				response.getOutputStream().close();
			}catch (Exception e) {
				log.warn("客户端连接已经断开，响应数据无法发送。",e);
			} 
			return null;
		}
	}

	
	/**
	 * 
	 * @param	responseMessageOverwrite	通常是 Interceptor 里面覆盖 Action 内容时用到。
	 * */
	private Object getRestResultBean(Object responseMessageOverwrite) {
		if(responseMessageOverwrite != null) {
			return responseMessageOverwrite;
		} 
		
		Object action = ActionContext.getContext().getActionInvocation().getAction();
		if(action instanceof ModelDriven) {
			return ((ModelDriven<?>) action).getModel();
		} 
		return action;
	}
	
	
	protected boolean doRedirectIfNeed(Object resultBean) throws IOException {
		String redirectURL = ServletActionContext.getRequest().getParameter("redirect");
		boolean isRedirect = (redirectTypeHandler != null && 
				redirectURL != null && !"null".equals(redirectURL) && redirectURL.startsWith("http"));
		
		if(! isRedirect) {
			return false;
		}
		
		ActionMapping mapping = (ActionMapping) ActionContext.getContext().get(ServletActionContext.ACTION_MAPPING);
		String extension = mapping.getExtension();
		
		if (! redirectTypeHandler.isNeedRedirect(extension)) {
			return false;
		}
		
		// Append Query String
		Map<String,String> redirectAQS = redirectTypeHandler.toRedirectParam(resultBean);
		
		redirectURL = UrlUtil.appendQS(redirectURL, redirectAQS);
		
		ServletActionContext.getResponse().sendRedirect(redirectURL);
		
		return isRedirect;
	}
	
	
	 protected static final String REDIRECT_AQS_MAP = RestResultsHandler.class.getSimpleName()+"#REDIRECT_AQS_MAP";
	
	
	
	public ContentTypeHandlerManager getContentTypeHandlerManager() {
		return contentTypeHandlerManager;
	}

	public void setContentTypeHandlerManager(
			ContentTypeHandlerManager contentTypeHandlerManager) {
		this.contentTypeHandlerManager = contentTypeHandlerManager;
	}

	public RedirectTypeHandler getRedirectTypeHandler() {
		return redirectTypeHandler;
	}

	public void setRedirectTypeHandler(RedirectTypeHandler redirectTypeHandler) {
		this.redirectTypeHandler = redirectTypeHandler;
	}
	
	
}
