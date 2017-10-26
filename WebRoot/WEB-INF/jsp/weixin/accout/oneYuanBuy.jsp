<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title id="titleNum">兑换券</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />

    <%@  include file="../header.jsp"%>
</head>
<body>	
	<ul class="grayBkg02 clearfix borderB tabSwitch MB20">
		<li class="width50 fl PTB10 grayTex positionR current boxSizing borderR" id="noUse">未使用</li>
		<li class="width50 fl PTB10 grayTex positionR" id="used">已使用</li>
	</ul>
	<ul class="PLR3P" id="exchangeList">
	</ul>
	<div id="exchangeListPaging" class="MT40 pagingMobile"></div>
	<input id="gourl" name="gourl" type="hidden" value="${gourl}">
	<input id="key" name="key" type="hidden" value="${key}">
	<input id="channel" name="channel" type="hidden" value="${channel}">
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/oneYuanBuy.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>


