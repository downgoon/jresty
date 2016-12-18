package io.downgoon.jresty.data.http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author downgoon
 *
 */
public class HttpAgentDemo {

	private static final Logger LOG = LoggerFactory.getLogger(HttpAgentDemo.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		// curl -i -XPOST -H "Pubkey: mypubkey" -d '{"cmd":"createTopic", "topic": "hello"}' "http://10.1.82.201:9191/topics/v1/_kateway"
		// String url, String charset, String jsonText, Map<String, String> headerMap
		
		String url = "http://10.1.82.201:9191/topics/v1/_kateway";
		String charset = "UTF-8";
		String jsonText = "{\"cmd\":\"createTopic\", \"topic\": \"hello\"}";
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Pubkey", "mypubkey");
		HttpResult result = HttpAgent.doPostJson(url, charset, jsonText, headerMap);
		LOG.info("result: " + result);
	}

}
