<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<title>账户余额</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/balance.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header.jsp"%>
	</head>

	<body class="whiteBkg">
		<div class="redArea">
			<div class="MB30 boxSizing MT30 textC whiteTex">
				<h4 class="font30" id="balanceMoney">0.00</h4>
				<p class="font16 whiteBkg" id="balanceMoneyTitle">可用余额</p>
			</div>
		</div>
		<div class="whiteBkg MT15 whiteTex borderTBLight PLR5P boxSizing clearfix" id="tx">
			<div class="fl ML10">
				<h4 class="font16 blackTex textL PT15 PT15">提现中金额</h4>
			</div>
			<div class="fr textR">
				<div class="font16 redTex lineHeight1_5x PB15 PT15" id="cashInMoney">0.00</div>
			</div>
		</div>
		<input id="mobile" name="mobile" type="hidden" value="${mobile}">
		<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
		<input id="channel" name="channel" type="hidden" value="${channel}">
		<div style="display:none" id="formDiv"></div>
		<script src="${pageContext.request.contextPath}/js/weixin/integration/countUp.js?<%=request.getAttribute("version")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/balance.js?<%=request.getAttribute("version")%>"></script>
		<%@  include file="../baiduStatistics.jsp"%>
	</body>

</html>