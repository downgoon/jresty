package io.downgoon.jresty.rest.view;

import java.util.LinkedHashMap;
import java.util.Map;

import io.downgoon.jresty.rest.model.UnifiedResponse;

public class DefaultRedirectTypeHandler implements RedirectTypeHandler {

	@Override
	public Map<String, String> toRedirectParam(Object resultBean) {
		Map<String, String> params = new LinkedHashMap<String,String>();
		if (resultBean == null) {
			return params;
		} 
		if (resultBean instanceof UnifiedResponse) {
			UnifiedResponse mode = (UnifiedResponse) resultBean;
			params.put("status", mode.getStatus()+"");
			params.put("message", mode.getMessage()); // URLEncode
			return params;
		}
		params.put("params", resultBean.toString());
		return params;
	}

	@Override
	public boolean isNeedRedirect(String extension) {
		return "html".equals(extension) || "jsp".equals(extension);
	}
	
	
}
