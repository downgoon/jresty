package io.downgoon.jresty.rest.struts2.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import io.downgoon.jresty.rest.model.UnifiedResponseCode;
import io.downgoon.jresty.rest.view.DefaultHttpHeaders;

public class RestExceptionHandlerAction extends UnifiedRestAction implements UnifiedResponseCode {

	private static final long serialVersionUID = 7565185212732665186L;

	private String defaultExtension = "json";

	public String handleException() {
		try {
			// getURIExtension();
			responseModel.setStatus(RC_ERROR);
			responseModel.setMessage("系统繁忙");

			// 从ValueStack中提取出Action中抛出的异常
			Exception exception = (Exception) ActionContext.getContext().getValueStack().findValue("exception");
			if (exception != null) {
				log.warn("异常拦截器捕获Action抛出的异常：日志索引=" + getRefNum4Log() + ",异常信息=" + exception.getMessage(), exception);
				responseModel.setDebug(getRefNum4Log(), "exception but not memcached");

			} else {
				log.warn("异常拦截器捕获Action抛出的异常：日志索引=" + getRefNum4Log(), exception);
			}

			return REST(new DefaultHttpHeaders(ERROR));

		} catch (Exception e) {// 再有异常，直接捕获
			log.warn("异常拦截器捕获异常时出现异常：日志索引=" + getRefNum4Log(), e);
			ServletActionContext.getResponse().addHeader("refnum", getRefNum4Log());// 将日志索引号返给前端浏览器，以便查找原因
			String extension = getURIExtension();
			if (extension == null || extension.equals("")) {
				extension = this.defaultExtension;
			}
			if ("json".equalsIgnoreCase(extension)) {// 直接返回String逻辑视图，不再由超类的representation方法捕获
				return "json";
			} else if ("jsonp".equalsIgnoreCase(extension)) {
				return "jsonp";
			} else if ("xml".equalsIgnoreCase(extension)) {
				return "xml";
			} else if ("jsp".equalsIgnoreCase(extension)) {
				return "jsp";
			} else {
				return "html";
			}
		}
	}

	public String getDefaultExtension() {
		return defaultExtension;
	}

	public void setDefaultExtension(String defaultExtension) {
		this.defaultExtension = defaultExtension;
	}

}
