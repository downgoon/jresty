package org.example.jresty.action;

import com.github.downgoon.jresty.rest.struts2.action.UnifiedRestAction;

public class PingAction extends UnifiedRestAction {

	private static final long serialVersionUID = 6572622442036315342L;

	@Override
	public String index() throws Exception {
		responseModel.setDebug(getRefNum4Log(), "系统正在运行");
		log.info(getRefNum4Log() + ":" + " 系统正在运行 ... ");
		return REST("index");
	}
	
}
