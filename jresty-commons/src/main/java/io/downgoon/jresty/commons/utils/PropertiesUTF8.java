package io.downgoon.jresty.commons.utils;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public class PropertiesUTF8 {

	//REFER: http://stackoverflow.com/questions/863838/problem-with-java-properties-utf8-encoding-in-eclipse 
	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		URL resource = PropertiesUTF8.class.getClassLoader().getResource("starsUTF8.txt");
		props.load(new InputStreamReader(resource.openStream(), "UTF8"));
		System.out.println(props);
	}

}
