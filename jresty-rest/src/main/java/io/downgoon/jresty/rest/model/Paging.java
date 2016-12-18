/**
 * 
 */
package io.downgoon.jresty.rest.model;

import java.util.Map;

/**
 * @author liwei
 *	paging info about rest list api according to API Spec http://wiki.dianshang.wanda.cn/pages/viewpage.action?pageId=10011570#Rule3
 */
public class Paging {
	
	private static final int DEFAULT_LIMIT = 600;
	private static final int DEFAULT_OFFSET = 0;

	private Integer offset = DEFAULT_OFFSET;

	private Integer limit = DEFAULT_LIMIT;
	
	private Integer totalCount;

	public static Paging parse(Map<String, String[]> httpParams) {
		return new Paging(Integer.parseInt(getParam(httpParams, "offset", DEFAULT_OFFSET + "")), 
				Integer.parseInt(getParam(httpParams,"limit",  DEFAULT_LIMIT + "")));
	}

	private static String getParam(Map<String, String[]> httpParams, String name) {
		String[] value = httpParams.get(name);
		if (value == null || value[0] == null || value[0].trim().length() == 0) {
			return null;
		}
		return value[0].trim();
	}
	
	private static String getParam(Map<String, String[]> httpParams, String name, String defaultValue) {
		String value = getParam(httpParams, name);
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public Paging(Integer offset, Integer limit) {
		super();
		this.offset = offset;
		this.limit = limit;
		this.totalCount = offset + limit;
	}

	public Paging(Integer offset, Integer limit, Integer totalCount) {
		super();
		this.offset = offset;
		this.limit = limit;
		this.totalCount = totalCount;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
