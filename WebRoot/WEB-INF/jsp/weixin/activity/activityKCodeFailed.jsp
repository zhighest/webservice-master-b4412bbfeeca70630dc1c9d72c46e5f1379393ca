<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>“K”码激活失败</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityKCode.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="failBkg positionR">
	<div class="contentTex positionA">
		<p class="whiteTex font120">激活失败！</p>
		<img class="block blockC failIcon" src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/failIcon.png" width="22%">
		<p class="whiteTex PLR5P lineHeight1_5x">${resmsg_cn}</p>
	</div>
	<div class="positionA cloud boxSizing">
		<!-- <img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/cloud.png" height="40%" alt=""> -->
		<div>
			<a class="pTB2P width30 inlineB radius3 introduce" href="${pageContext.request.contextPath}/wxactivity/goActivityKCodeDetail">活动介绍</a>
			<a class="pTB2P width30 inlineB radius3 invest ML20" href="${pageContext.request.contextPath}/wxproduct/goDemandProductIndex">立即投资</a>
		</div>
		<div class="redTex font20 positionA phoneNum width100">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/phoneIcon.png" width="22px">
			<span class="JsPhoneCall"></span>
		</div>
	</div>	 
	<input type="hidden" name="kCode" id="kCode" value="${kCode}" >
	<input type="hidden" name="keyCodeExchangeAmt" id="keyCodeExchangeAmt" value="${keyCodeExchangeAmt}" >
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeshare.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>