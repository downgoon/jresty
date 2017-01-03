package com.github.downgoon.jresty.rest.content.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SampleModel {

	private Integer id;
	
	private String name;
	
	private Date date;


	private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String formatDate(Date date) {
		return new SimpleDateFormat(PATTERN).format(date);
	}
	
	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat(PATTERN).parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
	
	public SampleModel() {
		
	}
	
	public SampleModel(Integer id, String name, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "SampleModel [id=" + id + ", name=" + name + ", date=" + date + "]";
	}
	
	
}
