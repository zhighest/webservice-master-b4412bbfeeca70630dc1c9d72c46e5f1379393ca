	    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ page contentType="text/html;charset=UTF-8"%>
		<%@ include file="../../common.jsp"%>
		<!DOCTYPE HTML>
		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>新年迎好运 红包送不停</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/shakeMoneyTree.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header.jsp"%>
		</head>
		<body class="">
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/moneyTreePic1.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/moneyTreePic3.jpg" alt=""></div>
		<div class="positionR"><img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/moneyTreePic4.jpg" alt="">
		<div class="activityBtn positionA  textC width100"><a href="javascript:;" class="inlineB width80" id="shakeMoneyTreeBtn"><img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/moneyTreeBtn.png" ></a></div>
		</div>

		<%@  include file="../baiduStatistics.jsp"%>

		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
		<script src="${pageContext.request.contextPath}/js/weixin/page/shakeMoneyTree.js?<%=request.getAttribute("version")%>"></script>

		</body>
		</html>