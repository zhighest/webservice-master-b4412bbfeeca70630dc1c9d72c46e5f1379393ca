<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta content="个人中心，联璧金融个人中心" name="keywords">
	<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣" name="description">
    <title>个人中心-联璧金融</title>
    <link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
  </head>
  <body class="fontFamily">
  	<div id="wrap"> 
  		<div id="main">
  			<!-- 网页头部的主tab的val值判断 -->
			<c:set var="pageIndex" value="2"/>
			<!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
			<c:set var="pageAccount" value="2"/>
  			<%@  include file="../header.jsp"%> 
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					<div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="2"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">活期资产</span></div>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="clearfix witeBkg width100 PTB30">
  					<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">活期资产</p>
  						<p class="grayTex61 opacity60">Current assets</p>
  					</div>
  					<div class="wrapper clearfix positionR MT20">
  						<ul class="width100 clearfix positionR">
  							<!-- 活期资产简略 begin -->
  							<li class="PTB30 grayBkgf5 fl width48 MR2P textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<span class="fl ML5">在投本金:</span>
  									</div>

  									<div class="width100 MT20 positionR">
  										<div class="fl redTex">
  											<span class="font12">￥</span>
  											<span class="font30">${accountAmount}</span>
  										</div>
  										<div class="fr font12">
  											<p class="grayTex61 opacity60">昨日收益</p>
  											<p class="redTex">￥<span>${yesterdayGain}</span></p>
  										</div>
  									</div>
  								</div>
  							</li>
							<!-- 活期资产简略 end -->

							<!-- 定期资产简略 begin -->
  							<li class="PTB30 grayBkgf5 fl width48 MR2P textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<span class="fl ML5">当日年化收益率:</span>
  										<img class="vipHint ML5" src="${pageContext.request.contextPath}/pic/web/accoutOverview/vipHintIcon.png" height="14">

  										<ul class="vipHintUl clearfix positionA font12 none">
  											<li class="whiteBkg clearfix grayTex61 radius5 annualizedReturnRateLi">
  												<span class="fl">基本收益</span>
  												<span class="annualizedReturnRate fr" id="">0.00</span>
  											</li>
  											<li class="radius5 grayTex61 clearfix couponsRateRisesLi">
  												<span class="fl">加息券</span>
  												<span class="couponsRateRises fr">0.00</span>
  											</li>
  											<li class="radius5 clearfix grayTex61 signInRateRisesLi">
  												<span class="fl">签到</span>
  												<span class="signInRateRises fr">0.00</span>
  											</li>
  											<li class="radius5 grayTex61 adjustRateLi">
  												<span class="fl">专享</span>
  												<span class="adjustRate fr">0.00</span>
  											</li>
										  </ul>

  									</div>
  									<div class="width100 MT20 positionR">
  										<div class="fl redTex">
                        <span class="font30" id="rateNum">0.00</span>
                        <span class="font12">%</span>
  										</div>
  										<div class="fr font12">
  											<p class="grayTex61 opacity60">在投收益</p>
  											<p class="redTex">￥<span>${currentIncome}</span></p>
  										</div>
  									</div>
  								</div>
  							</li>
							<!-- 定期资产简略 end -->
  						</ul>
  					</div>
  				</div>
	  			<!-- 资产总览 end -->
				
				<!-- 投资前准备 begin -->
	  			<div class="grayBkgf5 PTB30">
	  				<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">曲线图</p>
  						<p class="grayTex61 opacity60">Diagram</p>
  					</div>

  					<div class="wrapper clearfix positionR MT20">
  						<!-- 折线图轮播 begin-->
						<div class="swipe clearfix none" id="slider">
							<div class="swipe-wrap PTB20" id="bannerList">
								<figure>
									<div class="PL5P font14 grayTex61 textL">近七天年化收益率（%）</div>
									<div id="main1" class="width80 blockC"></div><!-- 折线图插件 -->
								</figure>
								<figure>
									<div class="PL5P font14 grayTex61 textL">近七天收益（元）</div>
									<div id="main2" class="width80 blockC"></div><!-- 折线图插件 -->
								</figure>
								<figure>
									<div class="PL5P font14 grayTex61 textL ">近七天在投本金（元）</div>
									<div id="main3" class="width80 blockC"></div><!-- 折线图插件 -->
								</figure>
							</div>
							<nav>
								<ul id="position"></ul>
							</nav>
						</div>
						<!-- 折线图轮播 end-->
  					</div>
	  			</div>
	  			<!-- 投资前准备 end -->

	  			<!-- 最近交易 begin -->
	  			<div class="grayBkgf5 PTB30">
	  				<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">历史明细</p>
  						<p class="grayTex61 opacity60">Historical transaction details</p>
  					</div>
  					<div class="wrapper whiteBkg PTB30 MT20">
  						<dl class="blackTex PL20 PR20 PTB20" id="fundFlowList">
		    				<dd class="grayTex textC">
                <span class="fl width20 spanBorderR boxSizing">时间</span>
		    					<span class="fl width20 spanBorderR boxSizing">年化收益率</span>
		    					<span class="fl width20 spanBorderR boxSizing">加息收益率</span>
                  <span class="fl width20 spanBorderR boxSizing">历史本金(元)</span>
		    					<span class="fl width20">历史收益(元)</span>
		    				</dd>
				    	</dl>
              <div class="textC colorb2 width100" id="historyAmountListPaging"></div>
  					</div>
  				</div>
  				<!-- 最近交易 end -->
		    </div>
  		</div> 
  	</div> 
  	<!-- 是否登录 -->
  	<input id="userIdFlag" name="userIdFlag" type="hidden" value="${userIdFlag}">
    <input id="accountAmount" name="accountAmount" type="hidden" value="${accountAmount}">
    <input id="yesterdayGain" name="yesterdayGain" type="hidden" value="${yesterdayGain}">
    <input id="currentIncome" name="currentIncome" type="hidden" value="${currentIncome}">
    
  	<!-- 专享加息 -->
  	<input id="adjustRate" name="adjustRate" type="hidden" value="${adjustRate}">
  	<!-- 签到加息 -->
	<input id="signInRateRises" name="signInRateRises" type="hidden" value="${signInRateRises}">
	<!-- 基本利率 -->
	<input id="annualizedReturnRate" name="annualizedReturnRate" type="hidden" value="${annualizedReturnRate}">
	<!-- 加息券 -->
	<input id="couponsRateRises" name="couponsRateRises" type="hidden" value="${couponsRateRises}">
  	<%@  include file="../baiduStatistics.jsp"%>
  	<%@  include file="../footer.jsp"%>
  	<div style="display:none" id="formDiv"></div>
  	<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/echarts/chart/line.js"></script>
  	<script src="${pageContext.request.contextPath}/js/weixin/integration/echarts/echarts.js"></script>
  	<script src="${pageContext.request.contextPath}/js/web/page/demanProperty.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
