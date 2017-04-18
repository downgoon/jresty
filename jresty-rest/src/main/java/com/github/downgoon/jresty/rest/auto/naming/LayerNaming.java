/**
 * 
 */
package com.github.downgoon.jresty.rest.auto.naming;

public class LayerNaming {
	
	private String prefix;
	
	public LayerNaming() {
		this("io.downgoon.api");
	}
	
	public LayerNaming(String prefix) {
		super();
		this.prefix = prefix;
	}

	public String actionPackage() {
		return prefix + ".action";
	}
	
	public String actionClass(String modelClass) {
		return modelClass + "Action";
	}
	
	public String daoIntfPackage() {
		return prefix + ".dao";
	}
	
	public String daoIntfClass(String modelClass) {
		return modelClass + "Dao";
	} 
	
	public String daoImplPackage() {
		return daoIntfPackage() + ".impl";
	}
	
	public String daoImplClass(String modelClass) {
		return daoIntfClass(modelClass) + "Impl";
	}
	
	/**
	 * Application => application
	 * AppName => appName
	 * */
	public String objectName(String className) {
		return NamingConvert.javaObjectName(className);
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Application => applications
	 * Applications => applications
	 * 
	 * <action name="applications" class="applicationAction" />
	 * */
	public String strutsActionName(String modelClass) {
		StringBuffer actionName = new StringBuffer();
		for (int i = 0; i < modelClass.length(); i++) {
			char ch = modelClass.charAt(i);
			if (ch >= 'A' && ch <= 'Z') {
				if (i > 0) {
					actionName.append("-");
				}
				actionName.append( (ch+"").toLowerCase()); // to lower case
			} else {
				actionName.append(ch);
			}
		}
		return pluralFormat(actionName.toString());
	}
	
	/**
	 * Application => applications
	 * Applications => applications
	 * */
	protected String pluralFormat(String word) {
		
		if (word.endsWith("s") || word.endsWith("es")) {
			return word;
		} else {
			return appendTail(word);
		}
	}
	
	private static final String[] ES_POST = new String[] {"s", "o", "x", "sh", "ch"};
	private String appendTail(String word) {
		// REFER: http://www.zybang.com/question/11904dffe65cc1249a9a434d5c1efa91.html
		for (String p : ES_POST) {
			if (word.endsWith(p)) {
				return word + "es";
			}
		}
		return word + "s";
	}
	
}
