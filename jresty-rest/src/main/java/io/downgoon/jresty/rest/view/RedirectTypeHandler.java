package io.downgoon.jresty.rest.view;

import java.util.Map;

public interface RedirectTypeHandler {

	public boolean isNeedRedirect(String extension);
	
	public Map<String,String> toRedirectParam(Object resultBean);
	
}
