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
	<title>“K”码活动详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityKCode.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="successBkg positionR">
	<div class="contentTex positionA">
		<p class="whiteTex font120">您已获得<span class="font140"><span id="priceMoney">0</span>元</span>的理财产品</p>
		<p class="whiteTex">联璧金融再送您<span class="font140">加息券</span></p>
		<div class=""><span class="positionR inlineB"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/interestRates.png" class="interestRatesImg"><span class="positionA interestRates redTex font120 strong" id="interestRates">+1%</span></span><span class="font40 whiteTex ML20">×3</span></div>
		<p class="whiteTex">活期、三月期、六月期各一张</p>
	</div>
	<div class="positionA btmBkg"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/btmContent.png" id="btmContent"></div>	
	<input type="hidden" name="kCode" id="kCode" value="${kCode}" >
	<input type="hidden" name="keyCodeExchangeAmt" id="keyCodeExchangeAmt" value="${keyCodeExchangeAmt}" >
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeSuccess.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeshare.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>
    
    

    
   

    

   


    
   

    
    

   
    

    

