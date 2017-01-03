///**
// * 
// */
//package com.github.downgoon.jresty.rest.content.handler;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.io.StringReader;
//import java.io.StringWriter;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.github.downgoon.jresty.rest.view.JsonLibHandler;
//
///**
// * @author liwei
// *
// */
//public class JsonLibHandlerTest {
//	
//	protected static final Logger LOG = LoggerFactory.getLogger(JsonLibHandlerTest.class);
//
//	private final JsonLibHandler jsonHandler = new JsonLibHandler();
//	
//	private final String jsonText = "{\"date\":\"2010-10-10 12:34:01\",\"id\":217,\"name\":\"windrain\"}";
//	
//	private final SampleModel jsonObj = new SampleModel(217, "windrain", SampleModel.parseDate("2010-10-10 12:34:01"));
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@Test
//	public void testFromObject() throws IOException {
//		SampleModel obj = jsonObj;
//		StringWriter writer = new StringWriter();
//		jsonHandler.fromObject(obj, "200", writer);
//		writer.flush();
//		String json = writer.getBuffer().toString();
//		LOG.info("to json: {}", json);
//		Assert.assertEquals(jsonText, json);
//	}
//	
//	@Test
//	public void testToObject() throws Exception {
//		Reader reader = new StringReader(jsonText);
//		SampleModel obj = new SampleModel();
//		jsonHandler.toObject(reader, obj);
//		LOG.info("from json: {}", obj);
//		Assert.assertEquals(jsonObj.getName(), obj.getName());
//		Assert.assertEquals(jsonObj.getDate(), obj.getDate());
//		Assert.assertEquals(jsonObj.getId(), obj.getId());
//	}
//
//}
