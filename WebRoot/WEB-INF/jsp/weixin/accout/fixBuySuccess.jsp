<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>购买成功</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="PT30 boxSizing">
	<div class="MB30 height60 whiteBkg font20 PTB10">
		<img class="MR10" src="${pageContext.request.contextPath}/pic/weixin/buySuccessIcon.png?<%=request.getAttribute("version")%>" alt="" width="25px">投资成功！
	</div>
	<ul class="borderT textL whiteBkg">
		<li class="borderB PLR5P grayTex heigh40">
			<div class="inlineB width30">产品名称</div>
			<div class="inlineB redTex">${productName}（<span id="days"></span>）</div>
		</li>
		<li class="borderB PLR5P grayTex heigh40">
			<div class="inlineB width30">投资金额</div>
			<div class="inlineB redTex"><fmt:formatNumber value="${investAmount}" pattern="#,##0.00#"/><span class="font12">元</span></div>
		</li>
		<li class="borderB PLR5P grayTex heigh40">
			<div class="inlineB width30">年化利率</div>
			<div class="inlineB redTex">${annualRate}%<span id="addRate"></span></div>
		</li>
	</ul>
	<div class="width80 blockC MT30">
		<a class="heigh40 redBorder redTex boxSizing width46 fl font18 radius5" id="fixIndex">完成</a>	
		<a class="heigh40 redBkg width46 whiteTex fr font18 radius5" id="loanAgreement">查看合同</a>
	</div>
	<input type="hidden" id="orderId" value="${orderId}">
	<input type="hidden" id="rateRises" value="${rateRises}">
	<input type="hidden" id="adjustRate" value="${adjustRate}">
	<input type="hidden" id="remanDays" value="${remanDays}">
	<input type="hidden" id="investPeriod" value="${investPeriod}">
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/fixBuySuccess.js?<%=request.getAttribute("version")%>" Charset="UTF-8" type="text/javascript"></script>
</body>
</html>