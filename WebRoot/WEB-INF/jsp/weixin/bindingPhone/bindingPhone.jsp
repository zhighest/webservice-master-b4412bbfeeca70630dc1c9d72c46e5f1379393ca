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
		<ul class="mobelBind">
			<li>
				<label for="phoneNumber">手机号</label>
				<input type="tel" id="phoneNumber" placeholder="请输入手机号" maxlength="11" onafterpaste="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" oninput="$.checkLimit($(this),'',true);"/>
			</li>
			<li class="identifyingCode">
				<label for="identifyingCode">验证码</label>
				<input type="tel" id="identifyingCode" maxlength="6" placeholder="请输入验证码" onafterpaste="$.checkLimit($(this),'',true);"/>
				<input type="button" name="" id="codeButton" value="获取" />
			</li>
			<li class="city">
				<span>城市</span>
				<input type="hidden" name="contact_province_code" data-id="0001" id="contact_province_code" value="" data-province-name="">
				<input type="hidden" name="contact_city_code" id="contact_city_code" value="" data-city-name="">
				<p data-city-code="510100" data-province-code="510000" id="show_contact">请选择城市</p>
			</li>
			<a id="confirmButton" href="javascript:;">确定</a>
		</ul>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/areaData_v2.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/iosSelect.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/iscroll.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/bindingPhone/bindingPhone.js?<%=request.getAttribute(" version ")%>"></script>
	<input type="hidden" name="mobile" id="token_id" value="${token_id}" >
		<%@  include file="../baiduStatistics.jsp"%>
	</body>

</html>