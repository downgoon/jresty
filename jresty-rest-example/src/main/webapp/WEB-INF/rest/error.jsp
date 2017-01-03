<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="eco.windrain.mvc.model.UnifiedResponse,eco.windrain.mvc.model.UnifiedResponseCode" %>
<%@ include file="/WEB-INF/include/config.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
	UnifiedResponse  up = (UnifiedResponse)request.getAttribute("model");
	if(up == null) {
		up = new UnifiedResponse();
		up.setStatus(UnifiedResponseCode.RC_ERROR);
		up.setMsg("系统运行异常");
		up.setMore_info("jsp页面中提取model响应数据失败");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="robots" content="all" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>异常提示</title>
</head>
<body>
<!--BEGIN:指数-关键词终端-->
<div id="contentA" class="area">
	<div class="left">
		<div class="gray651">
			<div class="gray-hd">
				<div class="tit">&nbsp;</div>
			</div>
			<div class="gray-bd">	
				<div class="error-tips">
					<p><span><%=up.getMsg()%></span></p>
					<p></p>
					<p></p>
					<a href="/index.html" title="" class="btn_6">返回首页</a>
				</div>
			</div>
			<div class="gray-ft"></div>
		</div>
	</div>
</div>
<!--END:指数-关键词终端-->
<div id="footArea">页尾</div>
</body>
</html>
