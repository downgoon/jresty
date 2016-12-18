package io.downgoon.jresty.data.http;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;

/**
 * @author downgoon
 *
 */
public class RestClientTest {

	private static final Logger LOG = LoggerFactory.getLogger(RestClientTest.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		RestClient jsonApiTemplate = new RestClient();
		List<Topic> topics = jsonApiTemplate.getObjects("topics.json", new TypeToken<List<Topic>>(){}.getType());
		// List<Topic> topics = jsonApiTemplate.getObjects("topics.json", Topic.class); // NOT WORK
		int i = 0;
		for (Topic topic : topics) {
			LOG.info("topic {} >> {}", i, topic);
			i ++;
		}
		
		Assert.assertEquals(35, topics.size());
		Assert.assertEquals("USE_COUPONS", topics.get(0).getName());
		// Partition [LeaderBrokerAddr=10.77.135.94:10105, PartitionId=0, OffsetNewest=891, OffsetOldest=36, LogsNum=855]
		Assert.assertEquals("10.77.135.94:10105", topics.get(0).getPartitions().get(0).getLeaderBrokerAddr());
	}

}
