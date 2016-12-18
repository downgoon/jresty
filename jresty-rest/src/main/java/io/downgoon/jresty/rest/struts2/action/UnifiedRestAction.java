package io.downgoon.jresty.rest.struts2.action;

import com.opensymphony.xwork2.ModelDriven;

import io.downgoon.jresty.rest.model.UnifiedResponse;

public class UnifiedRestAction extends RestActionSupport implements ModelDriven<UnifiedResponse> {

	private static final long serialVersionUID = -8943394302081438939L;
	
	protected UnifiedResponse responseModel = new UnifiedResponse();
	
	@Override
	public UnifiedResponse getModel() {
		return this.responseModel;
	}
	
}
