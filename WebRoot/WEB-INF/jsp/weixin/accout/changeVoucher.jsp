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
		<meta name="format-detection" content="telephone=no" />
		<title>K码券</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/changeVoucher.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/usedVoucher.css?<%=request.getAttribute(" version ")%>" />
		<%@  include file="../header.jsp"%>
	</head>

	<body class="positionR">
		<div>
			<div class="textR MT10 PLR3P">
				<a class="redTex font14" href="${pageContext.request.contextPath}/wxcoupons/tsVoucher">获取K码券</a>
			</div>
			<div class="clearfix height44  textAzure font14 MT10 whiteBkg PLR3P" id="showActiveStatus">
				<span class="fl">我的K码激活资格</span>
				<span class="fr right-arrow PT15">
					<img src="${pageContext.request.contextPath}/pic/weixin/changeVoucher/rightArrow.png?<%=request.getAttribute(" version ")%>" alt="rightArrow" />
				</span>
			</div>
		</div>
		<ul class="PLR3P" id="exchangeList">
		</ul> 
		<!--K码券激活资格弹框-->
		<div class="width100 height100P positionF statusAlertBox none" id="statusAlertBox">
			<div class="radius5 whiteBkg width80 positionA textC statusContent boxSizing font14"> 
				<img src="${pageContext.request.contextPath}/pic/weixin/changeVoucher/closeIcon.png?<%=request.getAttribute(" version ")%>" alt="closeIcon" class="closeIcon positionA" id="closeIcon"/>
				<h3 class="PB20">我的K码激活资格</h3>
				<div class="text666 heigh30 statusContentList" id="statusContentList">
					<!--<li class="MB15">
						<span></span>
						<span class="redTex"></span>
					</li>--> 
				</div>
			</div>
		</div>
		<div id="exchangeListPaging" class="MT40 pagingMobile"></div>
		<input id="gourl" name="gourl" type="hidden" value="${gourl}">
		<input id="key" name="key" type="hidden" value="${key}">
		<input id="channel" name="channel" type="hidden" value="${channel}">

		<%@  include file="../baiduStatistics.jsp"%>
		<script src="${pageContext.request.contextPath}/js/weixin/page/changeVoucher.js?<%=request.getAttribute(" version ")%>"></script>
	</body>

</html>