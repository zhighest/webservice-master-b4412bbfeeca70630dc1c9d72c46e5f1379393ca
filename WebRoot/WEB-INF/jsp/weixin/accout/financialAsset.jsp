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
		<title>理财资产</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/financialAsset.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header.jsp"%>
	</head>

	<body class="whiteBkg">
		<div class="redArea">
			<div class="PB5 boxSizing MT20 textC whiteTex">
				<h4 class="font30" id="financialAssets">0.00</h4>
				<p class="font19 tittle" id="financialAssetsTitle">理财资产</p>
			</div>
			<div class="MT20">
				<ul class="whiteTex clearfix">
					<li class="width35 boxSizing fl">
						<h4 class="font20" id="incomeAmount">0.00</h4>
						<div class="font14 tittle" id="incomeAmountTitle">累计收益</div>
					</li>
					<li class="width30 boxSizing fl">
						<h4 class="font20" id="expectAmount1">0.00</h4>
						<div class="font14 tittle" id="expectAmountTitle">预期收益</div>
					</li>
					<li class="width35 boxSizing fl">
						<h4 class="font20" id="yesterdayAmount1">0.00</h4>
						<div class="font14 tittle" id="yesterdayAmountTitle">昨日收益</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="whiteBkg MT15 whiteTex borderTBLight PLR5P boxSizing clearfix" id="hq">
			<div class="fl ML10">
				<h4 class="font16 blackTex textL PT15">活期资产</h4>
				<div class="font16 textL grayTex lineHeight1_5x PB15" id="demandAsset">0.00</div>
			</div>
			<div class="fr textR">
				<h4 class="font16 grayTex PT15">昨日收益</h4>
				<div class="font16 redTex lineHeight1_5x PB15" id="yesterdayAmount">+0.00</div>
			</div>
		</div>
		<div class="whiteBkg MT15 whiteTex borderTBLight boxSizing PLR5P clearfix" id="yxjh">
			<div class="fl ML10">
				<h4 class="font16 textL blackTex PT15">优享计划</h4>
				<div class="font16 textL grayTex lineHeight1_5x PB15" id="enjoyPlanAmount">0.00</div>
			</div>
			<div class="fr textR">
				<h4 class="font16 grayTex PT15">昨日收益</h4>
				<div class="font16 redTex lineHeight1_5x PB15" id="enjoyYesterdayIncome">+0.00</div>
			</div>
		</div>
		<div class="whiteBkg MT15 whiteTex borderTBLight PLR5P boxSizing clearfix" id="dq">
			<div class="fl ML10">
				<h4 class="font16 textL blackTex PT15">定期资产</h4>
				<div class="font16 textL grayTex lineHeight1_5x PB15" id="FixAsset">0.00</div>
			</div>
			<div class="fr textR">
				<h4 class="font16 grayTex PT15">预期收益</h4>
				<div class="font16 redTex lineHeight1_5x PB15" id="expectAmount">+0.00</div>
			</div>
		</div>
		<input id="mobile" name="mobile" type="hidden" value="${mobile}">
		<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
		<input id="channel" name="channel" type="hidden" value="${channel}">
		<div style="display:none" id="formDiv"></div>
		<script src="${pageContext.request.contextPath}/js/weixin/integration/countUp.js?<%=request.getAttribute("version")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/financialAsset.js?<%=request.getAttribute("version")%>"></script>
		<%@  include file="../baiduStatistics.jsp"%>
	</body>

</html>