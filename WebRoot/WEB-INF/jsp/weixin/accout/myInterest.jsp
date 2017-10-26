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
	<title>我的加息券</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/myInterest.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="positionR minHeight500">
	<div class="topTabWrap heigh42 whiteBkg clearfix positionF">
		<ul class="slideTab heigh42 blackTex1 whiteBkg positionR" style="z-index: 100;">
			<li class="fl allType positionR redTex whiteBkg"><span class="downArrow">全部</span></li>
			<li class="fl triangleUpRed triangleDown positionR validityLi redTex whiteBkg" value="1">有效期</li>
			<li class="fl triangleDown triangleUp positionR rateLi textL whiteBkg" value="2">加息收益率</li>
		</ul>
		<div class="positionF blackTex1 whiteBkg width100 textL sortType" id="sortType" style="z-index: 50;">
			<div class="PLR20 sortType1 borderB whiteBkg" id="sortType1" value="999">定期</div>
			<div class="PLR20 sortType2 borderB whiteBkg" id="sortType2" value="0">零钱</div>
			<div class="PLR20 sortType3 sortTypeCur whiteBkg" id="sortType3" value="">全部</div>
		</div>
	</div>

	<div class="positionF   width100 none rank" id="boxF" style="height: 100%; background: rgba(113,113,113,0.7); z-index: 40;"></div>
	<div class="width100" id="tabContent2">
		<ul class="PLR3P" id="RateCouponsList">
		</ul>
		<div class="textC grayTex PB10" id="proListPaging" class="pagingMobile"></div>
	</div>
	<div class="alertBg width100 height100P positionF" style="display:none;">
		<div class="radius5 whiteBkg width80 alertBox positionA textC">
			<h3 class="font20 PT15 redTex">提&nbsp;示</h3>
			<p id="tips" class="font16 blackTex">是否使用加息券</p>
			<a class="inlineB grayBkg width35 ML10 MR10 PT5 PB5 grayTex" id="cancel">取消</a>
			<a class="inlineB redBkg width35 ML10 MR10 PT5 PB5 whiteTex" id="enterBtn">确定</a>
		</div>
	</div>
	<a class="fixedBtn" href="${pageContext.request.contextPath}/wxquest/goDailyQuest">领取加息券</a>
	
	<input type="hidden" name="userId" id="userId" value="${userId}" >
	<input type="hidden" name="product" id="product" value="${product}" >
	<input type="hidden" name="sloanId" id="sloanId" value="${sloanId}" >
	<input type="hidden" name="errorMsg" id="errorMsg" value="${errorMsg}" >
	<input type="hidden" name="loanId" id="loanId" value="${loanId}" >
	<input type="hidden" name="loanId" id="loanId" value="${loanId}" >
	<input type="hidden" name="fixDemandRate" id="fixDemandRate" value="<%=request.getParameter("fixDemandRate")%>" >
	<input type="hidden" name="voucherAmount" id="voucherAmount" value="<%=request.getParameter("voucherAmount")%>" >
	<input type="hidden" name="voucherId" id="voucherId" value="<%=request.getParameter("voucherId")%>" >
	<input type="hidden" name="investmentAmount" id="investmentAmount" value="<%=request.getParameter("investmentAmount")%>" >
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/myInterest.js?<%=request.getAttribute("version")%>"></script> 
	<%@  include file="../baiduStatistics.jsp"%>
	</body>
	</html>