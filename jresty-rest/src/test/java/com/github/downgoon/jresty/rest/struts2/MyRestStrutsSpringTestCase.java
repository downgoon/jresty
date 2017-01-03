package com.github.downgoon.jresty.rest.struts2;
//package com.github.downgoon.jresty.rest.struts2.rest;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.StrutsSpringTestCase;
//import org.apache.struts2.dispatcher.mapper.ActionMapping;
//import org.junit.Test;
//
//import com.opensymphony.xwork2.ActionContext;
//import com.opensymphony.xwork2.ActionProxy;
//import com.opensymphony.xwork2.ActionProxyFactory;
//import com.opensymphony.xwork2.config.Configuration;
//
//public class MyRestStrutsSpringTestCase extends StrutsSpringTestCase {
//
//	@SuppressWarnings("unchecked")
//	@Override
//	protected ActionProxy getActionProxy(String uri) {
//		request.setRequestURI(uri);
//        ActionMapping mapping = getActionMapping(request);
//        String namespace = mapping.getNamespace();
//        String name = mapping.getName();
//        String method = mapping.getMethod();
//
//        Configuration config = configurationManager.getConfiguration();
//        ActionProxy proxy = config.getContainer().getInstance(ActionProxyFactory.class).createActionProxy(
//                namespace, name, method, new HashMap<String, Object>(), true, false);
//
//        ActionContext invocationContext = proxy.getInvocation().getInvocationContext();
////      invocationContext.setParameters(new HashMap(request.getParameterMap()));
//        
//        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
//        params.putAll(mapping.getParams());//把URI上的静态参数加入
//        params.putAll(request.getParameterMap());//把查询字符串的参数加入
//        invocationContext.setParameters(params);
//        
//        // set the action context to the one used by the proxy
//        ActionContext.setContext(invocationContext);
//
//        // this is normaly done in onSetUp(), but we are using Struts internal
//        // objects (proxy and action invocation)
//        // so we have to hack around so it works
//        ServletActionContext.setServletContext(servletContext);
//        ServletActionContext.setRequest(request);
//        ServletActionContext.setResponse(response);
//
//        return proxy;
//	}
//
//	@Test
//	public void testNull() {
//		
//	}
//}
