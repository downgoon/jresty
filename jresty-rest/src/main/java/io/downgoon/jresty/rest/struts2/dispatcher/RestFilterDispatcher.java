package io.downgoon.jresty.rest.struts2.dispatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.FilterDispatcher;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

@SuppressWarnings("deprecation")
public class RestFilterDispatcher extends FilterDispatcher {
	private static final Log log = LogFactory.getLog(RestFilterDispatcher.class);
	
	private FilterConfig filterConfig;

	private String defaultEncoding = "UTF-8";
	private String defaultLocale = "zh-CN";
	private String paramsWorkaroundEnabled = "false";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.filterConfig = filterConfig;
		
		if(filterConfig.getInitParameter("defaultEncoding")!=null) {
			this.defaultEncoding = filterConfig.getInitParameter("defaultEncoding");
			log.debug("读取配置的默认编码参数：defaultEncoding="+this.defaultEncoding);
		} else {
			log.debug("使用硬编码的默认编码参数：defaultEncoding="+this.defaultEncoding);
		}
		if(filterConfig.getInitParameter("defaultLocale")!=null) {
			this.defaultLocale = filterConfig.getInitParameter("defaultLocale");
			log.debug("读取配置的默认本地化参数：defaultLocale="+this.defaultLocale);
		} else {
			log.debug("使用硬编码的默认本地化参数：defaultLocale="+this.defaultLocale);
		}
		if(filterConfig.getInitParameter("paramsWorkaroundEnabled")!=null) {
			this.defaultLocale = filterConfig.getInitParameter("paramsWorkaroundEnabled");
			log.debug("读取配置的默认开关参数：paramsWorkaroundEnabled="+this.paramsWorkaroundEnabled);
		} else {
			log.debug("使用硬编码的默认开关参数：paramsWorkaroundEnabled="+this.paramsWorkaroundEnabled);
		}
		
	}

	@Override
	protected HttpServletRequest prepareDispatcherAndWrapRequest(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		Dispatcher du = Dispatcher.getInstance();

		if (du == null) {
			Dispatcher.setInstance(dispatcher);
			prepare(request, response);
		} else {
			dispatcher = du;
		}

		try {
			request = dispatcher.wrapRequest(request, getServletContext());
		} catch (IOException e) {
			String message = "Could not wrap servlet request with MultipartRequestWrapper!";
			log.error(message, e);
			throw new ServletException(message, e);
		}

		return request;
	}

	private String negotiateCharacterEncoding(HttpServletRequest request,Map<String,String> outParam) {
		String clientEncoding = request.getCharacterEncoding();//HTTP标准：客户端声称的编码（但是目前大多数浏览器并未实施该标准）
		outParam.put("point","HTTP标准");
		//协商过程：
		//1. 通过HTTP标准指定客户端编码；（在HTTP头中设置：Content-Type=[application/x-www-form-urlencoded; charset=UTF-8]）
		//2. 通过自定义HTTP头（ClientCharset）指定客户端编码；
		//3. 通过自定义HTTP查询参数（ClientCharset）指定客户端编码（只针对HTTP-GET方法）。（以免动态script标签发起请求时设置不了HTTP头；编码数值都是英文字符，提取编码数值跟编码无关。）
		//4. 如果所有的协商都没有，那么服务端强制使用配置：defaultEncoding
		//5. 如果服务端没有配置defaultEncoding，那么使用容器默认的ISO-8...（如果上述指定的编码不被支持，那么依然使用容器默认的）
		if(clientEncoding==null || clientEncoding.trim().equals("")) {
			clientEncoding = request.getHeader("ClientCharset");
			outParam.put("point","自定义HTTP头");
			if(clientEncoding==null || clientEncoding.trim().equals("")) {
//				clientEncoding = request.getParameter("ClientCharset");//不能通过该方式提取ClientCharset参数
//request.setCharacterEncoding(encoding);发挥作用的前提是：调用setCharacterEncoding之前不能执行任何request.getParameter
				if("GET".equalsIgnoreCase(request.getMethod())) {
					String queryString = request.getQueryString();
					if(queryString!=null && !queryString.equals("")) {
						//定位参数[ClientCharset]的起始和终止位置
						int startIndex = queryString.indexOf("ClientCharset=");
						int endIndex = -1;
						if(startIndex!=-1) {
							startIndex = startIndex+"ClientCharset=".length();
							endIndex = queryString.indexOf("&", startIndex);
							if(endIndex==-1) {//ClientCharset是最后一个参数
								int sessionidIndex = queryString.indexOf(";", startIndex);//去掉基于URL的SessionID
								if(sessionidIndex!=-1) {
									endIndex = sessionidIndex;
								} else {
									endIndex = queryString.length();
								}
							}
						}
						if(startIndex<endIndex) {
							clientEncoding = queryString.substring(startIndex, endIndex);
							outParam.put("point","自定义HTTP查询参数");
						}
					}
				}
			}
			if(clientEncoding==null || clientEncoding.trim().equals("")) {
				clientEncoding = defaultEncoding;
				outParam.put("point","服务端配置");
			}
			
		}
		return clientEncoding;
	}
	
	private void prepare(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,String> outParam = new HashMap<String,String>();
		String encoding = negotiateCharacterEncoding(request,outParam);
		log.debug("协商或配置的编码："+encoding+"，协商源："+outParam.get("point"));
		
		Locale locale = null;
		if (defaultLocale != null) {
			locale = LocalizedTextUtil.localeFromString(defaultLocale, request
					.getLocale());
		}
		if (encoding != null) {
			try {
				request.setCharacterEncoding(encoding);//注：被强制认为是GBK编码，好处在于客户端在提交GET请求时不再需要做URLEncode处理了。不好的是，如果客户端提交以UTF-8的编码，则编码出错了。
				//http://www.junlu.com/msg/125726.html
			} catch (Exception e) {
				log.error("Error setting character encoding to '" + encoding
						+ "' - ignoring.", e);
			}
		}

		if (locale != null) {
			response.setLocale(locale);
		}
		if (isParamsWorkaroundEnabled()) {
			request.getParameter("foo");
		}
	}

	public boolean isParamsWorkaroundEnabled() {
		ServletContext servletContext = filterConfig.getServletContext();
		return servletContext != null && servletContext.getServerInfo() != null
				&& servletContext.getServerInfo().indexOf("WebLogic") >= 0
				|| paramsWorkaroundEnabled.equalsIgnoreCase("true");
	}
}
