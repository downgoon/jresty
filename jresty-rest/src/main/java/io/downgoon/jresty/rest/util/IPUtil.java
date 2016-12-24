package io.downgoon.jresty.rest.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	/**
	 * 获取客户端ip地址
     *
	 * @param request
	 * @return 源IP地址
	 */
	public static String getSrcIP(HttpServletRequest request) {
		/*
		 * 有的CDN厂商取源时通过Cdn-Src-Ip传递源IP
		 * */
		String ip = request.getHeader("Cdn-Src-Ip");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null) {
			ip.trim().replace(";", ",").replace(" ", ",");
			String[] ipList = ip.split(",");
			if (ipList != null && ipList.length > 0) {
				ip = ipList[0];
			}
		}
		return ip;
	}
}
