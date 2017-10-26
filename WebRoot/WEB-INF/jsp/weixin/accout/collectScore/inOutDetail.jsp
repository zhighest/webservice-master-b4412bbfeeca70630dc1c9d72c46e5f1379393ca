<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>积分明细</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/inOutDetail.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../../header.jsp"%>
</head>
<body>
<ul class="tabBar clearfix font14 borderBottomTGray positionF width100 whiteBkg" id="tabBar">
	<li class="active">全部</li>
	<li>收入</li>
	<li>支出</li>
	<li>过期</li>
</ul>
<div class="containWrap ">
    <div class="contain1" id="tab1">
	        <ul id="listNull1"></ul>
			<ul id="allList" class="whiteBkg contain"></ul>
	</div>
	
</div>
<input id="mobile" name="mobile" type="hidden" value="<%=request.getParameter("mobile")%>">
<script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js?<%=request.getAttribute("version")%>"></script>
<script src="${pageContext.request.contextPath}/js/weixin/page/inOutDetail.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>