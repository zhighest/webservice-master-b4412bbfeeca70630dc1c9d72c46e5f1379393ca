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
	<title>优享计划</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/planProductIndex.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script> -->
</head>
<body>
	<div class="wrapper  clearfix outHide">
		<!-- 红色头部整体 begin -->
		<div class="width100 blockC twoColor PT30">
			<h1 class="textC whiteTex font14 none" id="expect">预期年化收益率</h1>
			<div class="textC whiteTex font60 lineHeight100 MT10 perTop">
				<span id="returnRate" class="helveticaneue strong">8.0~12</span>
				<font class="font24 helveticaneue">%</font>
			</div>
			
				<h1 class="textC whiteTex font14 MT10">优享计划资产总额（元）</h1>
				<h6 class="textC whiteTex font30 lineHeight100 MT10 helveticaneue strong" id="totalAssets">0.00</h6>
				
			<div class="halfBkg MT20">
				<ul class="outHide width90 whiteTex MLRA PTB2P font14">
					<li class="fl textC width49 borderRL">
						<a href="${pageContext.request.contextPath}/wxInvest/getCreditorList" class="whiteTex">
							<p class="font14 MB5">在投债权金额（元）</p>
							<p class="font24 lineHeight100 helveticaneue strong" id="curBondAmount">0.00</p>
						</a>
					</li>
					<li class="fl textC width50" id="icon">
						<p class="font14 MB5 ">撮合中金额（元）
							<img class="positionA" src="${pageContext.request.contextPath}/pic/weixin/icon.png" width="12px" id="iconImg"/>							
						</p>
						<p class="font24 lineHeight100 helveticaneue  strong" id="matchAmount">0.00</p>
					</li>
				</ul>
			</div>
		</div>
		<div class="width100 height2P bkg4"></div>
		<!-- 红色头部整体 end -->
		<div class="bottomPart clearfix width100 ">
			<div class="whiteBkg clearfix ">
				<div class="width90 MLRA PTB18 borderB">
					<table class="width100">
						<tr>
							<td class="blackTex1 font14">昨日综合年化收益率</td>
							<td class="redTex font16 helveticaneue" align="right">
								<span id="yesterdayYield" class="helveticaneue strong">0.00</span>
								<font  class="helveticaneue">%</font>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="whiteBkg clearfix borderB">
				<div class="width90 ML5P PTB18">
					<table class="width100">
							<tr>
								<td class="blackTex1 font14">昨日收益</td>
								<td class="redTex font16 helveticaneue" align="right">
									<span id="yesterdayIncome" class="helveticaneue strong">0.00</span>
								</td>
							</tr>
					</table>
				</div>
			</div>
			<div class="width100 height15P bkg4"></div>
			<div class="grayTex width90 PL5P font12 PT14 PR20 textL positionR  whiteBkg "> 
				<a href="${pageContext.request.contextPath}/wxInvest/goEnjoyTodayUserInfoList" class="grayTex">
					<span>今日投资人数：</span>
					<span class="redTex" id="peopleNum">0</span>
					<span>人</span>
					<img class="rightArrows ML5 positionA" src="${pageContext.request.contextPath}/pic/weixin/rightArrows.png" width="17px" />
				</a>
			</div>
			<div class="whiteBkg heigh10"></div>
		</div>
		<!-- 折线图轮播 begin-->
		<div class="swipe none PB85 whiteBkg PT2" id="slider">
			<div class="swipe-wrap" id="bannerList">
				<figure>
					<a href="${pageContext.request.contextPath}/wxabout/goPlanning?tabSkip=totalRise">
						<div class="PL5P font12 grayTex textL">近七天年化收益率（%）</div>
						<div id="main" style="width:100%;height:200px;"></div><!-- 折线图插件 -->
					</a>
				</figure>
				<figure>
					<a href="${pageContext.request.contextPath}/wxabout/goPlanning?tabSkip=incomeAmount">
						<div class="PL5P font12 grayTex textL">近七天收益（元）</div>
						<div id="main2" style="width:100%;height:200px;"></div><!-- 折线图插件 -->
					</a>
				</figure>
				
			</div>
			<nav>
				<ul id="position"></ul>
			</nav>
		</div>
	
		<div class="whiteBkg clearfix bottomBtn PTB10 positionF width90 PLR5P zindex">
			<a onclick="goturnout()" class="btn width25 block fl radius3">转出</a>
			<a class="btn width65 block fr radius3" id="goDemandOrderCon" href="${pageContext.request.contextPath}/wxInvest/getInvestConfirmation">开始投资</a>
		</div>
	</div>
	<div class="positionA blackTex1 font14 msg opacity0" id="msg">
		<div class="figure" id="figure"></div>
			<div class="whiteBkg msgB  radius5 shadowB">
				<div class="PTB5 PLR10 textL" >转入金额:<span class="redTex" id="inAmount">0元</span></div>
				<div class="PTB5 PLR10 textL borderT" >转出金额:<span class="redTex" id="outAmount">0元</span></div>
			</div>
	</div>
	<div style="display:none" id="formDiv"></div>
	<%@  include file="../baiduStatistics.jsp"%>
	<input id="URL" name="URL" type="hidden" value="${URL}">
<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute("version")%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/echarts/chart/line.js"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/echarts/echarts.js"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/planProductIndex.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>

