package com.github.downgoon.jresty.rest.struts2.action;

import java.util.List;
import java.util.Map;

public interface RestParams {

	
	/**
	 * 获取当前HTTP请求中URI上携带的拓展名
	 * */
	 public String getURIExtension();
	 
	 
	 /**
		 * 获取当前HTTP请求中URI上携带的参数（而且默认实现时，参数是有序的）
		 * */
	 public Map<String, Object> getURIParams();
	 
	 
	 /**
		 * 把URI的参数顺序化成一个List，以支持类似：/2011/01/stat.xml 的URL，表示2011年01月份的统计数据，更加静态化。
		 * 但是这种静态化的参数个数必须是偶数。例如：/2011/01/12/stat.xml  试图表达2011年01月分的12号统计数据，URL是解析不了的。
		 * */
	 public List<String> getURIParamsSequential();
	 
	 
	 
	 /** 
		 *  从静态化的URL上提取指定序号的参数
		 *  @param  index	从0开始编号
		 * */
	 public String getParam(int index);
	 
	 
	 
	 /**
		 * 从静态化URL或者查询字符串上提取指定参数名的参数
		 * @param	name	参数名
		 * */
	 public String getParam(String name);
	 
	 
	 
	 /**
		 * 提取指定序号或指定名称的参数，序号优先
		 * @param	index	参数序号
		 * @param	name	参数名称
		 * */
	 public String getParam(int index, String name);
	 
	 
	 /**
		 * 提取指定序号或指定名称的参数，名称优先
		 * @param	name	参数名称
		 * @param	index	参数序号
		 * */
	 public String getParam(String name, int index);
	 
	 
}

