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
	<title id="title1">零钱计划</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/productIndex.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR whiteBkg">
	<!-- 活期 begin -->
	<div class="demand PB30">
		<!-- 活期头 begin -->
		<div class="indexBkg2">
			<div class="content whiteTex PT40 PB20 positionR">
				<p class="font14">
					<div>
						<span class="positionR font14">当日年化收益率<span class="positionA vipHint"><img src="${pageContext.request.contextPath}/pic/weixin/vipHint.png" height="16"></span>
							<ul class="vipHintUl positionA font12 none">
								<li class="whiteBkg redTex radius5 annualizedReturnRateLi">基本收益<span class="annualizedReturnRate"></span>%</li>
								<li class="radius5 whiteTex couponsRateRisesLi">加息券+<span class="couponsRateRises"></span>%</li>
								<li class="radius5 whiteTex signInRateRisesLi">签到+<span class="signInRateRises"></span>%</li>
								<li class="radius5 whiteTex adjustRateLi">专享+<span class="adjustRate"></span>%</li>
							</ul>
						</span>
					</div>
				</p>
				<div class="font60 clearfix height60 strong helveticaneue lineHeight100 MT10 perTop">
					<span class="positionR" id="rateNum"></span>
					<font class="font24">%</font>
				</div>
				<div class="inlineB" id="inCreditor" onclick="window.location.href='${pageContext.request.contextPath}/wxdeposit/goInCreditor?accountAmount=${accountAmount}'">
					<p class="MT20 font14">在投本金(元)</p>
					<div class="font28 helveticaneue strong MT10"><fmt:formatNumber value="${accountAmount}" pattern="#,##0.00#"/></div>
				</div>
			</div>
		</div>
		<!-- 活期头 end -->
		<!-- 分栏显示表 begin -->
		<div class="clearfix MT10 borderB positionR">
			<!-- <a id="demandProductList" href="${pageContext.request.contextPath}/wxproduct/goDemandProductList" class="btnCenter positionA width45 block radius3">投&nbsp;资</a> -->
			<ul class="textL width90 PB5 clearfix blockC">
				<li class="width50 fl PTB10 lineHeight100 productTab tab1 positionR">
					<p class="blackTex1 font14">在投收益</p>
					<div class="redTex MT15 font24 helveticaneue strong"><fmt:formatNumber value="${currentIncome}" pattern="#,##0.00#"/></div>
					<div class="line60 positionA"></div>	
				</li>
				<li class="width40 fl PTB10 lineHeight100 productTab tab1 PL5P">
					<a id="earningsDetails" href="${pageContext.request.contextPath}/wxdeposit/goEarningsDetails">
						<p class="blackTex1 font14">昨日收益</p>
						<div class="redTex MT15 font24 helveticaneue strong"><fmt:formatNumber value="${yesterdayGain}" pattern="#,##0.00#"/></div>
					</a>
				</li>
			</ul>
		</div>
		<!-- 分栏显示表 end -->

        <!-- 活期标 begin -->
		<div class="width90 blockC MT20 clearfix">
			<a href="${pageContext.request.contextPath}/wxdeposit/goTransfer" class="btn width25 block fl radius3">转出</a>
			<a class="btn width65 block fr radius3" id="goDemandOrderCon">开始投资</a>
		</div>
        <!-- 活期标 end -->
		
		<!-- 每日加息入口 begin -->
		<div class="width90 MT10 MB10 clearfix blockC">
			<div id="already" class="fl grayTex font14 PTB10 PR20 textL positionR">
				今日投资人数：<span class="redTex" id="alreadyNum">0</span>
				<img class="positionA rightArrows" src="../pic/weixin/rightArrows.png" width="16px" />
			</div>
			<div class="fr PTB10">
				<a href="${pageContext.request.contextPath}/wxquest/goDailyQuest" class="font14 redTex positionR block">
			   	 <div class="positionA redDotted01 redBkg" id="redDotted"></div>
					<p class="fl height20">每日加息</p>
					<img class="fl ML5" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/addRateIcon.png" width="20px">
				</a>
			</div>
		</div>
		<!-- 每日加息入口 end -->

		<!-- 折线图轮播 begin-->
		<div class="swipe none" id="slider">
			<div class="swipe-wrap" id="bannerList">
				<figure>
					<a href="${pageContext.request.contextPath}/wxdeposit/goEarningsDetails?tabSkip=totalRise">
						<div class="PL5P font14 grayTex textL ">近七天年化收益率（%）</div>
						<div id="main" style="width:100%;height:200px;"></div><!-- 折线图插件 -->
					</a>
				</figure>
				<figure>
					<a href="${pageContext.request.contextPath}/wxdeposit/goEarningsDetails?tabSkip=incomeAmount">
						<div class="PL5P font14 grayTex textL ">近七天收益（元）</div>
						<div id="main2" style="width:100%;height:200px;"></div><!-- 折线图插件 -->
					</a>
				</figure>
				<figure>
					<a href="${pageContext.request.contextPath}/wxdeposit/goEarningsDetails?tabSkip=investAmount">
						<div class="PL5P font14 grayTex textL ">近七天在投本金（元）</div>
						<div id="main3" style="width:100%;height:200px;"></div><!-- 折线图插件 -->
					</a>
				</figure>
			</div>
			<nav>
				<ul id="position"></ul>
			</nav>
		</div>
		<!-- 折线图轮播 end-->
	</div>
	<!-- 活期 end -->

	<!-- 定期 begin -->
	<div class="fix PB30"  style="display: none;">
	    <!-- 定期头 begin -->
		<div class="indexBkg1">
			<div class="content whiteTex PT40 PB20 positionR">
				<a class="positionA doderIcon whiteTex font12" id="fixAssetList" href="${pageContext.request.contextPath}/wxproduct/goFixAssetList">
					<img class="block blockC" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/doderIcon.png" width="20px"><span class="lineHeight1_8x">订单</span>
				</a>
				<p class="font14">预期年化收益率</p>
				<div class="font60 helveticaneue height60 strong lineHeight100 MT10 perTop">
					10<span class="font24">&sim;</span>15
					<font class="font24">%</font>
				</div>
				<a href="${pageContext.request.contextPath}/wxproduct/goFixAssetList" class="whiteTex block"><p class="MT20 font14"><span class="positionR">定期资产总额(元)<img class="positionA rightArrowsWhite" src="${pageContext.request.contextPath}/pic/weixin/ver2_8/rightArrowsWhite.png" width="16px"></span></p>
				<div class="font28 helveticaneue MT10 strong"><fmt:formatNumber value="${investmentAssets}" pattern="#,##0.00#"/></div></a>
			</div>
		</div>
		<!-- 定期头 end -->
		<!-- 分栏显示表 begin -->
		<div class="clearfix borderB MT10 positionR">
			<!-- <a id="productList" href="${pageContext.request.contextPath}/wxproduct/goProductList" class="btnCenter positionA width45 block radius3">投&nbsp;资</a> -->
			<ul class="textL width90 PB5 clearfix blockC">
				<li class="width50 fl PTB10 lineHeight100 productTab tab1 positionR">
					<p class="blackTex1 font14">预期收益</p>
					<div class="redTex MT15 font24 helveticaneue strong"><fmt:formatNumber value="${expectAmount}" pattern="#,##0.00#"/></div>
					<div class="line60 positionA"></div>	
				</li>
				<li class="width40 fl PTB10 lineHeight100 productTab tab1 PL5P">
					<p class="blackTex1 font14">累计收益</p>
					<div class="redTex MT15 font24 helveticaneue  strong"><fmt:formatNumber value="${termIncomeAmount}" pattern="#,##0.00#"/></div>	
				</li>
			</ul>
		</div>
		<!-- 分栏显示表 end -->

		<!-- <div class="width85 blockC MT30 clearfix PB70">
			<a id="fixAssetList" href="${pageContext.request.contextPath}/wxproduct/goFixAssetList" class="btn fixBtn width45 block fl PTB10 radius3">订&nbsp;单</a>
			<a id="productList" href="${pageContext.request.contextPath}/wxproduct/goProductList" class="btn fixYellowBtn width45 block fr PTB10 radius3">投&nbsp;资</a>
			<a id="addRatesFix" class="btn fixYellowBtn width45 block fr PTB10 radius3">加息投资</a>
			<script type="text/javascript">
				$("#addRatesFix").click(function(event) {
				    window.location.href = contextPath + "wxcoupons/goMyInterest?product=999";
				    sessionStorage.setItem("from","fix");
				});
			</script>
		</div> -->

		<!-- 定期表 begin -->
		<ul id="ProductListInfoList">	
		</ul>
		<!-- 定期表 end -->
		
	</div>
	<!-- 定期 end -->
	<input id="userIdFlag" name="userIdFlag" type="hidden" value="${userIdFlag}">
	<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
	<input id="couponsRateRises" name="couponsRateRises" type="hidden" value="${couponsRateRises}">	
	<input id="channel" name="channel" type="hidden" value="${channel}">
	<input id="adjustRate" name="adjustRate" type="hidden" value="${adjustRate}">
	<input id="signInRateRises" name="signInRateRises" type="hidden" value="${signInRateRises}">
	<input id="annualizedReturnRate" name="annualizedReturnRate" type="hidden" value="${annualizedReturnRate}">
	<input type="hidden" name="fixDemandSwitch" id="fixDemandSwitch" value="<%=request.getParameter("fixDemandSwitch")%>" >
	<input id="mobile" name="mobile" type="hidden" value="${mobile}">
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute("version")%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/echarts/chart/line.js"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/echarts/echarts.js"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/demandProductIndex.js?<%=request.getAttribute("version")%>"
	></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>

	
	
	
</html>