/**
 * 
 */
package com.github.downgoon.jresty.data.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * @author downgoon
 *
 */
public class RestClient {
	
	protected static final Logger LOG = LoggerFactory.getLogger(RestClient.class);
	
	protected String urlPrefix = "http://10.1.115.173:10009";
	
	/**
	 * @param	jsonAddr  支持两种地址格式：
	 * <br>一种HTTP URL，以"/"开头为URL路径部分，主机部分由urlPrefix决定；
	 * <br>另一种本地CLASSPATH路径，形如 topic.json，不带任何前缀。
	 *  	
	 * @param type 形如 new TypeToken<List<Topic>>(){}.getType() 其中 Topic 是你期望反序列化的对象。
	 * 
	 * Example:
	 * List<Topic> topics = jsonApiTemplate.getObjects("topics.json", new TypeToken<List<Topic>>(){}.getType());
	 * 
	 * */
	public <T> List<T> getObjects(String jsonAddr, Type type) {
		String jsonText = fetchJsonText(jsonAddr);
		return parseJsonText(jsonText, type);
		
	}
	
	protected <T> List<T> parseJsonTextNotWork(String jsonText, Class<T> tClass) {
		// REFER: http://stackoverflow.com/questions/23919605/translating-nested-java-objects-to-from-json-using-gson
		// http://www.blogjava.net/brock/archive/2012/08/01/384520.html
		if(jsonText == null || jsonText.length() < 1) {
			return new ArrayList<T>();
		}
		Gson gson = new Gson();
		List<T> objs = gson.fromJson(jsonText, new TypeToken<List<T>>(){}.getType());
		return objs;
	}
	
	protected <T> List<T> parseJsonText(String jsonText, Type type) {
		// REFER: http://stackoverflow.com/questions/23919605/translating-nested-java-objects-to-from-json-using-gson
		if(jsonText == null || jsonText.length() < 1) {
			return new ArrayList<T>();
		}
		Gson gson = new Gson();
		List<T> objs = gson.fromJson(jsonText, type);
		return objs;
	}
	
	protected String fetchJsonText(String api) {
		/*
		 * [{"Name":"USE_COUPONS","ZkId":0,"ZkPath":"/ffan/kafka/ffan_service/coupon","ZkAddr":"10.77.130.12:2181","ReplicaNum":2,
		"Partitions":[{"LeaderBrokerAddr":"10.77.135.94:10105","PartitionId":0,"OffsetNewest":891,"OffsetOldest":36,"LogsNum":855}]}
		 * 
		 * */
		
		boolean isHttp = api.startsWith("/");
		
		String jsonText = null;
		if (isHttp) { // HTTP API
			long tmBegin = System.currentTimeMillis();
			LOG.info("access api ...: {}", urlPrefix + api);
			jsonText = HttpAgent.doGet(urlPrefix + api, "UTF-8");
			long tmEnd = System.currentTimeMillis();
			LOG.info("api time cost: {} ms at {} content size: {}", new Object[] {
					(tmEnd - tmBegin), api, jsonText.length()
			});
			return jsonText;
			
		} else { // Local File
			try {
				jsonText = readFileContent(api);
			} catch (IOException e) {
				LOG.error("local file {} exception {}", api, e);
			}
			return jsonText;
		}
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	
	private static String readFileContent(String file) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(RestClient.class.getClassLoader()
                    .getResourceAsStream(file)));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
	
}
