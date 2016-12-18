package io.downgoon.jresty.rest.struts2.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;

public class RestParamsHandler implements RestParams {

	@Override
	public String getURIExtension() {
		ActionMapping mapping = (ActionMapping) ActionContext.getContext().get(ServletActionContext.ACTION_MAPPING);
		return mapping.getExtension();
	}

	@Override
	public Map<String, Object> getURIParams() {
		ActionMapping mapping = (ActionMapping) ActionContext.getContext().get(ServletActionContext.ACTION_MAPPING);
		return mapping.getParams();
	}


	
	private static final String _URIParamsSequential = RestParamsHandler.class.getName()+"#"+"URIParamsSequential";
	
	@SuppressWarnings("unchecked")
	public List<String> getURIParamsSequential() {
		List<String> seqParamsItem = (List<String>)ActionContext.getContext().get(_URIParamsSequential);
		if(seqParamsItem == null) {
			seqParamsItem = new ArrayList<String>();
			Map<String,Object> uriParamsPair = getURIParams();
			Iterator<Map.Entry<String, Object>> entries = uriParamsPair.entrySet().iterator();
			while(entries.hasNext()) {
				Map.Entry<String, Object> pair = entries.next();
				seqParamsItem.add(pair.getKey());
				seqParamsItem.add(pair.getValue().toString());
			}
			ActionContext.getContext().put(_URIParamsSequential, seqParamsItem);
		}
		
		return seqParamsItem;
	}
	
	@Override
	public String getParam(int index) {
		List<String> list = getURIParamsSequential();
		if(index < 0 || index >= list.size()) {
			return null;
		}
		return list.get(index);
	}
	

	@Override
	public String getParam(String name) {
		Object value =  getURIParamsAndQSParams().get(name);
		if(value == null) {
			return null;
		}
		return value.toString();
	}
	
	
	
	public String getParam(int index, String name) {
		String pIndex = getParam(index);
		if(pIndex!=null) {
			return pIndex;
		}
		return getParam(name);
	}
	
	
	public String getParam(String name, int index) {
		String pName = getParam(name);
		if(pName!=null) {
			return pName;
		}
		return getParam(index);
	}
	
	
	
	private static final String _URIParamsAndQSParams = RestParamsHandler.class.getName()+"#"+"URIParamsAndQSParams";
	
	/** 获取参数
	 * 综合URI静态化参数和查询字符串参数
	 * */
	@SuppressWarnings("unchecked")
	protected final Map<String,Object> getURIParamsAndQSParams() {
		Map<String,Object> params = (Map<String,Object>)ActionContext.getContext().get(_URIParamsAndQSParams);
		if(params == null) {
			params = new LinkedHashMap<String, Object>();
			
			Map<String,Object> uriParams = getURIParams();
			if(uriParams != null && uriParams.size() > 0) {//先填充URI上的参数
				params.putAll(uriParams);
			}
			
			HttpServletRequest request = ServletActionContext.getRequest();
			//http://yybob.javaeye.com/blog/524747
			//实际上：request.getParameterMap().get(key)返回的是String[]
			Map<String,Object> qsParams = request.getParameterMap();
			if(qsParams != null && qsParams.size() > 0) {
//				params.putAll(qsParams);
				Iterator iterator = qsParams.keySet().iterator();
				while(iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = qsParams.get(key);
					if(value instanceof String[] &&
							((String[])value).length == 1) {
						params.put(key, ((String[])value)[0]);
					} else {
						params.put(key, value);
					}
				}
			}
			
			ActionContext.getContext().put(_URIParamsAndQSParams, params);
		}
		
		return params; 
	}
	
	
	
	
}
