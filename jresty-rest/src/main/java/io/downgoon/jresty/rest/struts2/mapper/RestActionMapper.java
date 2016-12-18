/*
 * $Id: DefaultActionMapper.java 780092 2009-05-29 20:15:14Z wesw $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.downgoon.jresty.rest.struts2.mapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.RequestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.dispatcher.mapper.ActionMapper;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.apache.struts2.dispatcher.mapper.ParameterAction;
import org.apache.struts2.util.PrefixTrie;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.RuntimeConfiguration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Default action mapper implementation, using the standard *.[ext] (where ext
 * usually "action") pattern. The extension is looked up from the Struts
 * configuration key <b>struts.action.extension</b>.
 *
 * <p/> To help with dealing with buttons and other related requirements, this
 * mapper (and other {@link ActionMapper}s, we hope) has the ability to name a
 * button with some predefined prefix and have that button name alter the
 * execution behaviour. The four prefixes are:
 *
 * <ul>
 *
 * <li>Method prefix - <i>method:default</i></li>
 *
 * <li>Action prefix - <i>action:dashboard</i></li>
 *
 * <li>Redirect prefix - <i>redirect:cancel.jsp</i></li>
 *
 * <li>Redirect-action prefix - <i>redirectAction:cancel</i></li>
 *
 * </ul>
 *
 * <p/> In addition to these four prefixes, this mapper also understands the
 * action naming pattern of <i>foo!bar</i> in either the extension form (eg:
 * foo!bar.action) or in the prefix form (eg: action:foo!bar). This syntax tells
 * this mapper to map to the action named <i>foo</i> and the method <i>bar</i>.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Method Prefix</b> <p/>
 *
 * <!-- START SNIPPET: method -->
 *
 * With method-prefix, instead of calling baz action's execute() method (by
 * default if it isn't overriden in struts.xml to be something else), the baz
 * action's anotherMethod() will be called. A very elegant way determine which
 * button is clicked. Alternatively, one would have submit button set a
 * particular value on the action when clicked, and the execute() method decides
 * on what to do with the setted value depending on which button is clicked.
 *
 * <!-- END SNIPPET: method -->
 *
 * <pre>
 *  &lt;!-- START SNIPPET: method-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;method:anotherMethod&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: method-example --&gt;
 * </pre>
 *
 * <p/> <b>Action prefix</b> <p/>
 *
 * <!-- START SNIPPET: action -->
 *
 * With action-prefix, instead of executing baz action's execute() method (by
 * default if it isn't overriden in struts.xml to be something else), the
 * anotherAction action's execute() method (assuming again if it isn't overriden
 * with something else in struts.xml) will be executed.
 *
 * <!-- END SNIPPET: action -->
 *
 * <pre>
 *  &lt;!-- START SNIPPET: action-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;action:anotherAction&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: action-example --&gt;
 * </pre>
 *
 * <p/> <b>Redirect prefix</b> <p/>
 *
 * <!-- START SNIPPET: redirect -->
 *
 * With redirect-prefix, instead of executing baz action's execute() method (by
 * default it isn't overriden in struts.xml to be something else), it will get
 * redirected to, in this case to www.google.com. Internally it uses
 * ServletRedirectResult to do the task.
 *
 * <!-- END SNIPPET: redirect -->
 *
 * <pre>
 *  &lt;!-- START SNIPPET: redirect-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;redirect:www.google.com&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: redirect-example --&gt;
 * </pre>
 *
 * <p/> <b>Redirect-action prefix</b> <p/>
 *
 * <!-- START SNIPPET: redirect-action -->
 *
 * With redirect-action-prefix, instead of executing baz action's execute()
 * method (by default it isn't overriden in struts.xml to be something else), it
 * will get redirected to, in this case 'dashboard.action'. Internally it uses
 * ServletRedirectResult to do the task and read off the extension from the
 * struts.properties.
 *
 * <!-- END SNIPPET: redirect-action -->
 *
 * <pre>
 *  &lt;!-- START SNIPPET: redirect-action-example --&gt;
 *  &lt;s:form action=&quot;baz&quot;&gt;
 *      &lt;s:textfield label=&quot;Enter your name&quot; name=&quot;person.name&quot;/&gt;
 *      &lt;s:submit value=&quot;Create person&quot;/&gt;
 *      &lt;s:submit name=&quot;redirectAction:dashboard&quot; value=&quot;Cancel&quot;/&gt;
 *  &lt;/s:form&gt;
 *  &lt;!-- END SNIPPET: redirect-action-example --&gt;
 * </pre>
 *
 */
public class RestActionMapper implements ActionMapper {
	
	protected static final Logger LOG = LoggerFactory.getLogger(RestActionMapper.class);
    public static final String HTTP_METHOD_PARAM = "__http_method";
    private String idParameterName = "paramId";
    

    protected PrefixTrie prefixTrie = null;
    protected static final String METHOD_PREFIX = "method:";
    protected static final String ACTION_PREFIX = "action:";
    protected static final String REDIRECT_PREFIX = "redirect:";
    protected static final String REDIRECT_ACTION_PREFIX = "redirectAction:";

    protected boolean allowDynamicMethodCalls = true;
    
    protected List<String> extensions = new ArrayList<String>() {{ add("action"); add("");}};

    protected Container container;

    public RestActionMapper() {
        prefixTrie = new PrefixTrie() {
            {
                put(METHOD_PREFIX, new ParameterAction() {
                    @Override
					public void execute(String key, ActionMapping mapping) {
                        if (allowDynamicMethodCalls) {
                            mapping.setMethod(key.substring(
                                                   METHOD_PREFIX.length()));
                        }
                    }
                });

                put(ACTION_PREFIX, new ParameterAction() {
                    @Override
					public void execute(String key, ActionMapping mapping) {
                        String name = key.substring(ACTION_PREFIX.length());
                        if (allowDynamicMethodCalls) {
                            int bang = name.indexOf('!');
                            if (bang != -1) {
                                String method = name.substring(bang + 1);
                                mapping.setMethod(method);
                                name = name.substring(0, bang);
                            }
                        }
                        mapping.setName(name);
                    }
                });

                put(REDIRECT_PREFIX, new ParameterAction() {
                    @Override
					public void execute(String key, ActionMapping mapping) {
                        ServletRedirectResult redirect = new ServletRedirectResult();
                        container.inject(redirect);
                        redirect.setLocation(key.substring(REDIRECT_PREFIX
                                .length()));
                        mapping.setResult(redirect);
                    }
                });

                put(REDIRECT_ACTION_PREFIX, new ParameterAction() {
                    @Override
					public void execute(String key, ActionMapping mapping) {
                        String location = key.substring(REDIRECT_ACTION_PREFIX
                                .length());
                        ServletRedirectResult redirect = new ServletRedirectResult();
                        container.inject(redirect);
                        String extension = getDefaultExtension();
                        if (extension != null && extension.length() > 0) {
                            location += "." + extension;
                        }
                        redirect.setLocation(location);
                        mapping.setResult(redirect);
                    }
                });
            }
        };
    }

    /**
     * Adds a parameter action.  Should only be called during initialization
     *
     * @param prefix The string prefix to trigger the action
     * @param parameterAction The parameter action to execute
     * @since 2.1.0
    */
    protected void addParameterAction(String prefix, ParameterAction parameterAction) {
        prefixTrie.put(prefix, parameterAction);
    }

    @Inject(StrutsConstants.STRUTS_ENABLE_DYNAMIC_METHOD_INVOCATION)
    public void setAllowDynamicMethodCalls(String allow) {
        allowDynamicMethodCalls = "true".equals(allow);
    }

    @Inject
    public void setContainer(Container container) {
        this.container = container;
    }

    @Inject(StrutsConstants.STRUTS_ACTION_EXTENSION)
    public void setExtensions(String extensions) {
        if (extensions != null && !"".equals(extensions)) {
            List<String> list = new ArrayList<String>();
            String[] tokens = extensions.split(",");
            for (String token : tokens) {
                list.add(token);
            }
            if (extensions.endsWith(",")) {
                list.add("");
            }
            this.extensions = Collections.unmodifiableList(list);
        } else {
            this.extensions = null;
        }
    }

    /**
     * @deprecated	{@link RestActionMapper} 解析URI必须依赖当前配置
     * */
    @Deprecated
	@Override
	public ActionMapping getMappingFromActionName(String actionName) {
//        ActionMapping mapping = new ActionMapping();
//        mapping.setName(actionName);
//        return mapping;//return parseParamsAndName(mapping,null);
        throw new IllegalStateException("禁止调用");
    }

    @Override
	public ActionMapping getMapping(HttpServletRequest request,
            ConfigurationManager configManager) {
        ActionMapping mapping = new ActionMapping();
        mapping.setParams(new LinkedHashMap<String, Object>());//让URI参数有序
        
        String uri = getUri(request);
        //如果uri不是以"/"开头，是否补一个"/"。可以考虑，应该没什么副作用。
        
        //uri是否过滤连续重复的"/"：不过滤，防止用户在URI上传空参数时导致Action匹配违背用户意志。
        //例如：GET /city/beijing/user语义是查找来自北京的用户；
        //GET /city//user 过滤后：GET /city/user 语义变成了：查找名称叫user的城市
        
        int indexOfSemicolon = uri.indexOf(";");//删除URI上的"SESSIONID"
        uri = (indexOfSemicolon > -1) ? uri.substring(0, indexOfSemicolon) : uri;

        uri = dropExtension(uri, mapping);//删除URI拓展名
        if (uri == null) {
            return null;
        }

     	parseNamespaceAndNameSlash(uri, configManager, mapping);//解析出ActionNamespace和携带有斜杠("/")的ActionName

        handleSpecialParameters(request, mapping);
        if (mapping.getName() == null) {
            return null;
        }
        
        parseURIParamsAndNameFinal(configManager,mapping);//解析出静态URI上的参数和最终的ActionName
        
        parseRESTMethodIFNotSpecified(request, mapping);//如果用户没有专门指定Action对应的方法，则按REST风格约定方法
        
        return mapping;
    }
    
    

    
    
    /**
     * 改写 {@link DefaultActionMapper#parseActionName(ActionMapping)}方法：
     * 1、不支持"!"表示动态方法，而改用"-"支持动态方法；
     * 2、"-"衔接的可能是方法，也可能是name的一部分，判断依据：
     * 	先尝试作为name的一部分，如果用户的确配置了这个name，则就认为是name的一部分。
     * 	否则，则认为是动态方法。
     * 3、配置文件不再提供通配符的支持。
     * 
     * @see	com.opensymphony.xwork2.DefaultActionProxy#prepare
     * */
    protected void parseURIParamsAndNameFinal(ConfigurationManager configManager,ActionMapping mapping) {
        if (mapping.getName() == null) {
            return ;
        }
       	/*
      	 * 对于形如：paramName/paramValue/action-method/id 和 paramName/paramValue/action-method
       	 * 解析出action-method部分和id参数部分 （注：前提条件最终的ActionName是不能包含斜杠符"/"的）
       	 * */
       	String uriSlashTrimed = MyStringUtil.trimHeadAndTail(mapping.getName(),'/');
       	List<Integer> slashPositions = MyStringUtil.findCharPositions(uriSlashTrimed,'/');
       	String namemethodGuess = null;//计算action-method值
       	String idParamValue = null;//存放URI中的id参数
       	if(slashPositions.size()%2==0) {//URI不带id参数的，形如：paramName/paramValue/action-method 或 action-method
       		if(slashPositions.size()>0) {
        		namemethodGuess = uriSlashTrimed.substring(slashPositions.get(slashPositions.size()-1) + 1);
       		} else {
        		namemethodGuess = uriSlashTrimed;
        	}

       	} else {//URI带id参数的，形如：paramName/paramValue/action-method/id 或 action-method/id
       		if(slashPositions.size()>1) {
       			namemethodGuess = uriSlashTrimed.substring(
       					slashPositions.get(slashPositions.size()-2) + 1, 
           				slashPositions.get(slashPositions.size()-1));
       			
       		} else {//slashVector.size()==1
       			namemethodGuess = uriSlashTrimed.substring(0,slashPositions.get(slashPositions.size()-1));
       		}
       		idParamValue = uriSlashTrimed.substring(slashPositions.get(slashPositions.size()-1) + 1);//解析出ID参数，为保证URI前置参数和ID参数有序，ID参数放最后
//       		if (mapping.getParams() == null) {
//       			mapping.setParams(new LinkedHashMap<String, Object>());
//            }
//        	mapping.getParams().put(idParameterName, 
//        		uriSlashTrimed.substring(slashPositions.get(slashPositions.size()-1) + 1)
//        		);//解析出ID参数
        }
        
       	mapping.setName(namemethodGuess);//此时的name可能包含有最终的ActionName和Method
        	
        //装载URI参数（例如：paramName/paramValue/action-method[/id]的paramName/paramValue部分）
        if (mapping.getParams() == null) {
        	mapping.setParams(new LinkedHashMap<String, Object>());
        }
        mapping.getParams().putAll(MyStringUtil.extractParamBeforeAction(uriSlashTrimed, slashPositions,idParameterName));
        
        //装载URI中的ID参数，保证ID参数在所有前置参数之后（前提：ParamMap是个有序Map），以支持：/paramValue1/paramValue2/action-method/idParam 的URL
        if(idParamValue!=null) {
        	mapping.getParams().put(idParameterName, idParamValue);
        }
        	
         /*
          * 拆分：action-method，解析出最终的ActionName
          * 拆分原则：如果用户明确指定Action的处理方法，则使用用户指定的方法；否则，留给后面REST处理。
          * */
        //指定ActionMethod方式1（优先考虑）: 后端配置文件指定，形如：struts.xml中配置<action name="action-name" method="method-specified" class="action-class">
        ActionConfig actionConfig = findActionByNamespaceAndName(mapping.getNamespace(),namemethodGuess,configManager);
        if(actionConfig!=null) {//在配置文件（struts.xml）中能找到名为namemethodGuess的Action定义
        	if(actionConfig.getMethodName()!=null && !actionConfig.getMethodName().equals("")) {//找到定义，并指定方法
        		mapping.setName(namemethodGuess);
        		mapping.setMethod(actionConfig.getMethodName());
        	} else {//找到定义，但未指定方法，则把方法留给REST来确定
        		mapping.setName(namemethodGuess);
        	}
        } else {//指定ActionMethod方式2：前端URI连接符指定，形如：/namespace/paramName/paramValue/action-name-method
        	int concatenation = -1;
        	if(allowDynamicMethodCalls && (concatenation=namemethodGuess.lastIndexOf("-"))!=-1) {
        		String nameGuess = namemethodGuess.substring(0, concatenation);
        		String methodGuess = namemethodGuess.substring(concatenation + 1);
        		if(findActionByNamespaceAndName(mapping.getNamespace(), nameGuess,configManager)!=null) {
        			//不用再考虑namespace+nameGuess有没有在配置文件中指定Method了，甚至为了提高性能，不用确定
                   	//namespace+nameGuess是否有配置，都尝试动态方法。因为，如果没配置，会导致NoActionFound
                   	mapping.setName(nameGuess);
                    mapping.setMethod(methodGuess);
        		} else {
        			//No Action Found
        			mapping.setName(namemethodGuess);
        		}
        	} 
       }
    }
    
    protected void parseRESTMethodIFNotSpecified(HttpServletRequest request,ActionMapping mapping) {
    	if (mapping.getMethod() == null) {
    		/*
<li><code>GET:    /movie               => method="index"</code></li>
<li><code>GET:    /movie/Thrillers      => method="view", id="Thrillers"</code></li>
<li><code>POST:   /movie                => method="create"</code></li>		
<li><code>PUT:    /movie/Thrillers      => method="update", id="Thrillers"</code></li>
<li><code>DELETE: /movie/Thrillers      => method="remove", id="Thrillers"</code></li> 	 

<li><code>GET:    /movie/Thrillers!edit => method="edit", id="Thrillers"</code></li>  【暂不支持】
<li><code>GET:    /movie/new            => method="editNew"</code></li>  【暂不支持】
    		 * */
    		
    		String httpMethod = request.getParameter(HTTP_METHOD_PARAM);
    		if(httpMethod==null || httpMethod.equals("") || 
    				!Arrays.asList(new String[] {"GET","POST","PUT","DELETE"}).contains(httpMethod.toUpperCase())) {
    			httpMethod = request.getMethod();
    		}
    		httpMethod = httpMethod.toUpperCase();
    		if(httpMethod.equals("GET")) {
    			if(mapping.getParams().get(idParameterName)==null) {
    				mapping.setMethod("index");
    			} else {
    				mapping.setMethod("view");
    			}
    		} else 
    		if(httpMethod.equals("POST")) {
    			mapping.setMethod("create");//ID参数可以有，也可以没有
    		} else 
    		if(httpMethod.equals("PUT")) {
    			mapping.setMethod("update");
    		}else 
    		if(httpMethod.equals("DELETE")) {
    			mapping.setMethod("remove");
    		} else {//其他方法按GET处理
    			if(mapping.getParams().get(idParameterName)==null) {
    				mapping.setMethod("index");
    			} else {
    				mapping.setMethod("view");
    			}
    		}
    		
        }
    }
    
    protected ActionConfig findActionByNamespaceAndName(String namespace,String name,ConfigurationManager configManager) {
    	return findActionByNonRuntimeConfig(namespace,name,configManager);
    } 
    
    ActionConfig findActionByNonRuntimeConfig(String namespace,String name,ConfigurationManager configManager) {
    	ActionConfig actionConfig = null;
    	Iterator<PackageConfig> packConfs = configManager.getConfiguration().getPackageConfigs().values().iterator();
    	while(packConfs.hasNext()) {
    		PackageConfig pack = packConfs.next();
    		if(pack.getNamespace().equals(namespace) || isTwinBrothers(pack,namespace)) {//找到其中一个匹配的namespace（注：一个namespace可能存在于多个package中）
    			actionConfig = pack.getActionConfigs().get(name);
    			if(actionConfig!=null) {//按顺序，只要找到一个namespace+name完全匹配的action，那么可以提前结束。不需要考虑后面的package是否还存在可能的匹配。（最先匹配原则，不支持通配符）
    				break;
    			}
    		}
    	}
    	return actionConfig;
    }
    
    private boolean isTwinBrothers(PackageConfig pack,String uriNamespace) {//空Namespace和根Namespace是等价的（孪生兄弟）
    	return (pack.getNamespace().equals("") && uriNamespace.equals("/")) || (pack.getNamespace().equals("/") && uriNamespace.equals(""));
    }
    
    ActionConfig findActionByRuntimeConfig(String namespace,String name,ConfigurationManager configManager) {
    	RuntimeConfiguration runtimeConfig = configManager.getConfiguration().getRuntimeConfiguration();
    	return runtimeConfig.getActionConfig(namespace, name);//注：运行时，单元测试时RuntimeConfiguration获取不到。
    }
    

    /**
     * Special parameters, as described in the class-level comment, are searched
     * for and handled.
     *
     * @param request
     *            The request
     * @param mapping
     *            The action mapping
     */
    public void handleSpecialParameters(HttpServletRequest request,
            ActionMapping mapping) {
        // handle special parameter prefixes.
        Set<String> uniqueParameters = new HashSet<String>();
        Map<?, ?> parameterMap = request.getParameterMap();
        for (Iterator<?> iterator = parameterMap.keySet().iterator(); iterator
                .hasNext();) {
            String key = (String) iterator.next();

            // Strip off the image button location info, if found
            if (key.endsWith(".x") || key.endsWith(".y")) {
                key = key.substring(0, key.length() - 2);
            }
            
            // Ensure a parameter doesn't get processed twice
            if (!uniqueParameters.contains(key)) {
                ParameterAction parameterAction = (ParameterAction) prefixTrie
                        .get(key);
                if (parameterAction != null) {
                    parameterAction.execute(key, mapping);
                    uniqueParameters.add(key);
                    break;
                }
            }
        }
    }

    /**
     * 从uri中解析出ActionNamespace和阶段性（非最终的）ActionName。
     * ActionNamespace的解析，采取依据加载的Action配置文件，按最大匹配原则匹配；
     * 输入参数uri截除前面的ActionNamespace得到阶段性（非最终的）ActionName，阶段性ActionName中可能携带斜杠("/")。
     * 
     * @param uri
     *            The uri
     * @param
     * 			  配置文件管理器
     * @param mapping
     *            The action mapping to populate
     */
    protected void parseNamespaceAndNameSlash(String uri, ConfigurationManager configManager,
            ActionMapping mapping) {
        String namespace, name;
        int lastSlash = uri.lastIndexOf("/");
        if (lastSlash == -1) {
            namespace = "";
            name = uri;
        } else if (lastSlash == 0) {
            // ww-1046, assume it is the root namespace, it will fallback to
            // default
            // namespace anyway if not found in root namespace.
            namespace = "/";
            name = uri.substring(lastSlash + 1);
        } 
        //不再支持：alwaysSelectFullNamespace选项，按最长匹配原则匹配Namespace
        else {
            // Try to find the namespace in those defined, defaulting to ""
            Configuration config = configManager.getConfiguration();
            String prefix = uri.substring(0, lastSlash);
            namespace = "";
            boolean rootAvailable = false;
            // Find the longest matching namespace, defaulting to the default
            for (Object cfg : config.getPackageConfigs().values()) {
                String ns = ((PackageConfig) cfg).getNamespace();
                if (ns != null && prefix.startsWith(ns) && (prefix.length() == ns.length() || prefix.charAt(ns.length()) == '/')) {
                    if (ns.length() > namespace.length()) {
                        namespace = ns;
                    }
                }
                if ("/".equals(ns)) {//如果用户定义了"/"根命名空间（struts.xml一定会定义），那么如果当前请求没匹配到任何空间，则默认使用根空间
                    rootAvailable = true;
                }
            }

            name = uri.substring(namespace.length() + 1);

            // Still none found, use root namespace if found
            if (rootAvailable && "".equals(namespace)) {
                namespace = "/";
            }
        }
        //不再支持：allowSlashesInActionNames选项，要求ActionName一定不能包含斜杠符("/")

        mapping.setNamespace(namespace);
        mapping.setName(name);
    }

    /**
     * Drops the extension from the action name
     *
     * @param name
     *            The action name
     * @return The action name without its extension
     * @deprecated Since 2.1, use {@link #dropExtension(java.lang.String,org.apache.struts2.dispatcher.mapper.ActionMapping)} instead
     */
    @Deprecated
	protected String dropExtension(String name) {
        return dropExtension(name, new ActionMapping());
    }

    /**
     * Drops the extension from the action name, storing it in the mapping for later use
     *
     * @param name
     *            The action name
     * @param mapping The action mapping to store the extension in
     * @return The action name without its extension
     */
    protected String dropExtension(String name, ActionMapping mapping) {
        if (extensions == null) {
            return name;
        }
        for (String ext : extensions) {
            if ("".equals(ext)) {
                // This should also handle cases such as /foo/bar-1.0/description. It is tricky to
                // distinquish /foo/bar-1.0 but perhaps adding a numeric check in the future could
                // work
                int index = name.lastIndexOf('.');
                if (index == -1 || name.indexOf('/', index) >= 0) {
                    return name;
                }
            } else {
                String extension = "." + ext;
                if (name.endsWith(extension)) {
                    name = name.substring(0, name.length() - extension.length());
                    mapping.setExtension(ext);
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * Returns null if no extension is specified.
     */
    protected String getDefaultExtension() {
        if (extensions == null) {
            return null;
        } else {
            return extensions.get(0);
        }
    }

    /**
     * Gets the uri from the request
     *
     * @param request
     *            The request
     * @return The uri
     */
    protected String getUri(HttpServletRequest request) {
        // handle http dispatcher includes.
        String uri = (String) request
                .getAttribute("javax.servlet.include.servlet_path");
        if (uri != null) {
            return uri;
        }

        uri = RequestUtils.getServletPath(request);
        if (uri != null && !"".equals(uri)) {
            return uri;
        }

        uri = request.getRequestURI();
        return uri.substring(request.getContextPath().length());
    }

    /**
     * @deprecated	{@link RestActionMapper} 解析URL时过分依赖当前配置，严格意义上运算不可逆
     */
    @Deprecated
	@Override
	public String getUriFromActionMapping(ActionMapping mapping) {
        StringBuilder uri = new StringBuilder();

        if (mapping.getNamespace() != null) {
            uri.append(mapping.getNamespace());
            if (!"/".equals(mapping.getNamespace())) {
                uri.append("/");
            }
        }
        String name = mapping.getName();
        String params = "";
        if (name.indexOf('?') != -1) {
            params = name.substring(name.indexOf('?'));
            name = name.substring(0, name.indexOf('?'));
        }
        uri.append(name);

        if (null != mapping.getMethod() && !"".equals(mapping.getMethod())) {
            uri.append("!").append(mapping.getMethod());
        }

        String extension = mapping.getExtension();
        if (extension == null) {
            extension = getDefaultExtension();
            // Look for the current extension, if available
            ActionContext context = ActionContext.getContext();
            if (context != null) {
                ActionMapping orig = (ActionMapping) context.get(ServletActionContext.ACTION_MAPPING);
                if (orig != null) {
                    extension = orig.getExtension();
                }
            }
        }

        if (extension != null) {

            if (extension.length() == 0 || (extension.length() > 0 && uri.indexOf('.' + extension) == -1)) {
                if (extension.length() > 0) {
                    uri.append(".").append(extension);
                }
                if (params.length() > 0) {
                    uri.append(params);
                }
            }
        }

        return uri.toString();
    }


	public String getIdParameterName() {
		return idParameterName;
	}

	@Inject(required=false,value=StrutsConstants.STRUTS_ID_PARAMETER_NAME)
	public void setIdParameterName(String idParameterName) {
		this.idParameterName = idParameterName;
	}
	
	
	static class MyStringUtil {
    	/**
         * 去掉 输入参数data中的收尾的 trimChar字符
         * */
        public static String trimHeadAndTail(String data,char trimChar) {
    		int tail = data.length();
    		int head = 0;//前闭后开
    		char[] val = data.toCharArray();
    		while ((head < tail) && (val[head] == trimChar)) {
    		    head++;
    		}
    		while ((head < tail) && (val[tail - 1] == trimChar)) {
    		    tail--;
    		}
    		return data.substring(head, tail);
    	 }
        /**
         * 求解输入参数data中所有出现appearChar字符的位置构成的向量
         * */
        public static List<Integer> findCharPositions(String data, char targetChar) {
    		List<Integer> positionVector = new ArrayList<Integer>();
    		int fromIndex = 0;
    		int pos = -1;
    		while((pos=data.indexOf(targetChar+"", fromIndex))!=-1) {
    			positionVector.add(pos);
    			fromIndex = pos+1;
    		}
    		return positionVector;
    	}
        /**
         * 提取Action前面的静态化参数
         * */
        static Map<String,Object> extractParamBeforeAction(String uri, List<Integer> slashVector, String reservedWord) {
    		Map<String,Object> mapping = new LinkedHashMap<String, Object>();
    		try {
    			int offset = 0;
    			int pairAmout = slashVector.size()/2;
    			int pairIndex = 0;
    			while(pairIndex<pairAmout) {
    				int pairStartSlash = slashVector.get(2*pairIndex);
    				int pairEndSlash = slashVector.get(2*pairIndex+1);
    				String paramName = URLDecoder.decode(
    						uri.substring(offset,pairStartSlash), 
    						"UTF-8");
    				
    				String paramValue = URLDecoder.decode(
    						uri.substring(pairStartSlash+1,pairEndSlash),
    						"UTF-8");//参数值用URLEncode编码
    				
    				if(!paramName.equalsIgnoreCase(reservedWord)) {//防止覆盖id参数和干扰后续REST-GET-Method判断
    					mapping.put(paramName, paramValue);
    				}
    				offset = pairEndSlash+1;
    				pairIndex ++;
    			}
    		} catch (UnsupportedEncodingException e) {
    			LOG.warn("Unable to determine parameters from the url",e);
    		}
    		return mapping;
    	}
    }
}
