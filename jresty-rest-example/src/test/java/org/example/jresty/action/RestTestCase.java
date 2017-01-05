package org.example.jresty.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsSpringTestCase;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

public abstract class RestTestCase extends StrutsSpringTestCase {
	
	/**
     * Finds an ActionMapping for a given request
     */
    protected ActionMapping getActionMapping(HttpServletRequest request) {
    	
    	// ActionMapper.class
    	// com.github.downgoon.jresty.rest.struts2.mapper.RestActionMapper;
        return Dispatcher.getInstance().getContainer().getInstance(ActionMapper.class).getMapping(request,
                Dispatcher.getInstance().getConfigurationManager());
    }
	
}
