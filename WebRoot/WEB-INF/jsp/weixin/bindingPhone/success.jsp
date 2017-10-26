<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<!DOCTYPE HTML>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<title>手机绑定</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/iosSelect.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/bindingPhone.css?<%=request.getAttribute(" version ")%>" />
		<%@  include file="../header.jsp"%>
	</head>

	<body class="positonR loginWrapper">
		<div id="successPic">
			<img src="${pageContext.request.contextPath}/pic/weixin/success.png"  width="19%" >
			<p class="bindSuccess">绑定成功</p>
		</div>

		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/areaData_v2.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/iosSelect.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/iscroll.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/bindingPhone.js?<%=request.getAttribute(" version ")%>"></script>
	<input type="hidden" name="mobile" id="token_id" value="${token_id}" >
		<%@  include file="../baiduStatistics.jsp"%>
	</body>

</html>