package org.example.jresty.action;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.downgoon.jresty.rest.model.UnifiedResponse;
import com.opensymphony.xwork2.ActionProxy;

import net.sf.json.JSONObject;

public class PingActionTest extends RestTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testPingJson() throws Exception {
		ActionProxy proxy = getActionProxy("/jresty/ping.json");
		PingAction pingAction = (PingAction) proxy.getAction();

		String methodResult = proxy.execute(); // get result of Action
		UnifiedResponse model = pingAction.getModel(); // get model of Action
		String jsonContent = response.getContentAsString(); // get response of Action

		Assert.assertNull(methodResult);
		
		Assert.assertNotNull(model);
		Assert.assertEquals(200, model.getStatus());
		Assert.assertEquals("OK", model.getMessage());
		
		/*
		 * {"attachment":null,"debug":"20170105101240444480:系统正在运行","message":"OK","status":200}
		 * */
		
		JSONObject jsonObject = JSONObject.fromObject(jsonContent);
		Assert.assertEquals(200, jsonObject.getInt("status"));
		Assert.assertEquals("OK", jsonObject.getString("message"));
		
	}
	
	@Test
	public void testPingXml() throws Exception {

		// construct a http request
		request.addHeader("Cookie", "WG-PPC-CODE=vLeKmuvVKh62gej7DxhQEdqEE_yshic3; WG-PPC-UID=1ueIUbXoMNQ.");
		request.addParameter("name", "downgoon");
		ActionProxy proxy = getActionProxy("/jresty/ping.xml");
		// ActionProxy proxy = getActionProxy("/jresty/ping.xml?name=downgoon");
		
		
		PingAction pingAction = (PingAction) proxy.getAction();

		String methodResult = proxy.execute(); // get result of Action
		UnifiedResponse model = pingAction.getModel(); // get model of Action
		String xmlContent = response.getContentAsString(); // get response of Action
		
		Assert.assertNull(methodResult);
		
		Assert.assertNotNull(model);
		Assert.assertEquals(200, model.getStatus());
		Assert.assertEquals("OK", model.getMessage());
		
		Assert.assertEquals("downgoon", request.getParameter("name"));
		
		
		/*
<com.github.downgoon.jresty.rest.model.UnifiedResponse>
  <status>200</status>
  <message>OK</message>
  <debug>20170105103750397059:系统正在运行</debug>
</com.github.downgoon.jresty.rest.model.UnifiedResponse>
		 * */
		
		Assert.assertTrue(xmlContent.startsWith("<com."));
		Assert.assertTrue(xmlContent.indexOf("<status>200</status>") != -1);
		Assert.assertTrue(xmlContent.endsWith("UnifiedResponse>"));
	}
	
	
	@Test
	public void testPingHtml() throws Exception {
		// construct a http request
		// request.addHeader("Cookie", "WG-PPC-CODE=vLeKmuvVKh62gej7DxhQEdqEE_yshic3; WG-PPC-UID=1ueIUbXoMNQ.");
		// request.addParameter("redirect", "http://www.example.com");
		ActionProxy proxy = getActionProxy("/jresty/ping.html");
		
		PingAction pingAction = (PingAction) proxy.getAction();

		String methodResult = proxy.execute(); // get result of Action
		UnifiedResponse model = pingAction.getModel(); // get model of Action
		
		// String htmlContent = response.getContentAsString();
		
		Assert.assertNotNull(methodResult);
		Assert.assertEquals("index", methodResult);
		
		Assert.assertNotNull(model);
		Assert.assertEquals(200, model.getStatus());
		Assert.assertEquals("OK", model.getMessage());
		
		
		
		
	}
	
	
	// @Test()
//	public void testPingRedirect() throws Exception {
//
//		request.addHeader("Cookie", "WG-PPC-CODE=vLeKmuvVKh62gej7DxhQEdqEE_yshic3; WG-PPC-UID=1ueIUbXoMNQ.");
//		request.addParameter("redirect", "http://www.example.com");
//		ActionProxy proxy = getActionProxy("/jresty/ping.html");
//		
//		// PingAction pingAction = (PingAction) proxy.getAction();
//
//		String methodResult = proxy.execute(); // get result of Action
//		
//		
//		/*  
//		 * Why ?  ActionMapping return NULL
//		 *      ActionContext.getContext().get(ServletActionContext.ACTION_MAPPING);
//		 * */ 
//
//		Assert.assertEquals("index", methodResult);
//		Assert.assertEquals(302, response.getStatus());
//	}

}
