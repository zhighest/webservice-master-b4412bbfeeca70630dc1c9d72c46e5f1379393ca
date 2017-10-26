<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@  include file="../../common.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="x5-orientation" content="portrait">
		<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0" />
		<title>兑换券</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/cashCouponUsedDetails.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header.jsp" %>
	</head>

	<body class="positionR">
		<div class="wrapper width92 whiteBkg PLR4P textL">
			<div class="height68 font18 borderBc8 ">
				<span class="color333 strong" id="couponIfo"></span>
			</div>
			<p class="color666 font12 PT20" id="voucherDescribe"></p>
			<p class="color666 font12 PT20">
				<span>已领取至手机号&nbsp;</span>
				<span id="phoneNo"></span>
			</p>
			<p class="textR font14 redText height52 cursor">
				<span id="ZQH">打开智仟汇APP&gt;&gt;</span>
			</p>
		</div>
		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
		<script src="${pageContext.request.contextPath}/js/weixin/page/cashCouponUsedDetails.js?<%=request.getAttribute("version")%>"></script>
		<%@  include file="../baiduStatistics.jsp" %>
	</body>

</html>