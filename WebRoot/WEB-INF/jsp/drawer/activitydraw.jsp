<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>问卷调查表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/normalize.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/component.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/cs-select.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/cs-skin-boxes.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/demo.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/drawer.css?<%=request.getAttribute("version")%>" />
	<script src="${pageContext.request.contextPath}/js/drawer/modernizr.custom.js?<%=request.getAttribute("version")%>"></script>
    <script src="${pageContext.request.contextPath}/js/weixin/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
<body id="container1">
	<div class="drawerTitle">
		<h1>济南长途汽车总站<br/>3.8妇女节抽奖活动</h1>
	</div>
	<div class="btnArea"><a class="btn activitydraw" id="activitydraw">点击抽奖</a></div>
	<div class="banner"><img src="${pageContext.request.contextPath}/pic/drawer/banner.jpg"></div>
	<div class="alertBg">
		<div class="alertBox">
			<h4 class="popupTitle">是否确认抽奖</h4>
			<a id="cancel" class="btn grayBtn">取消</a>
			<a id="enterBtn" class="btn activeBtn">确认</a>
		</div>
	</div>		
</body>
	<script src="${pageContext.request.contextPath}/js/drawer/activitydraw.js?<%=request.getAttribute("version")%>"></script>
</html>