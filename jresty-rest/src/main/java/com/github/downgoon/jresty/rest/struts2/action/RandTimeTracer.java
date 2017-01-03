package com.github.downgoon.jresty.rest.struts2.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.opensymphony.xwork2.ActionContext;

public class RandTimeTracer implements Traceable {

	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random rand = new Random();
	private static final String REFNUM4LOG = RandTimeTracer.class.getName()+"#"+"REFNUM4LOG";
	
	
	@Override
	public String getRefNum4Log() {
		String refNum = (String)ActionContext.getContext().get(REFNUM4LOG);//ActionContext是ThreadLocal的
		if(refNum==null) {
			refNum = sdf.format(new Date())+(String.format("%06d", Math.abs(rand.nextInt()))).subSequence(0, 6);
			ActionContext.getContext().put(REFNUM4LOG, refNum);
		}
		return refNum;
	}
	

}
