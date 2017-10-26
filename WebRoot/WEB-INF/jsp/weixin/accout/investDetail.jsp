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
	<title>投资详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/investDetail.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
	<div class="whiteBkg">
		<div class="pTB2P popSire indexBkg1">
			<div class="width95 blockC">
				<div class="blockC">
					<div class="content whiteTex PT30 positionR">
						<span class="font14 positionR" id="rateTitle">预期年化收益率
							<span class="positionA vipHint none"><img src="${pageContext.request.contextPath}/pic/weixin/vipHint.png" height="16"></span>
							<ul class="vipHintUl positionA font12 none">
								<li class="whiteBkg redTex none radius5 annualizedReturnRateLi">基本收益<span class="annualizedReturnRate"></span>%</li>
								<li class="radius5 whiteTex none couponsRateRisesLi">加息券+<span class="couponsRateRises"></span>%</li>
								<li class="radius5 whiteTex none adjustRateLi">专享+<span class="adjustRate"></span>%</li>
							</ul>
						</span>
						<div class="font20 PTB5  ">
							<div class="positionR height60 lineHeight100 MT10 MB20">
								<font class="font60 helveticaneue" id="annualRate"></font><font class="newfont font24 helveticaneue">%</font>
								
								<!-- <span class="positionA addRateRisesBkg none" id="ratesIcon">
									<img src="${pageContext.request.contextPath}/pic/weixin/version1125/icon24.png" alt="" height="30">
									<span class="font16 lineHeight1_5x strong redTex positionA addRateRises">+<font id="rateRisesFont"></font>%</span>
								</span> -->
							</div>
						</div>
					</div>
					<ul class="whiteTex clearfix  PB10">
						<li class="width33_3 boxSizing fl borderRWhite">
							<h4 class="font14 opacity50">理财期限</h4>
							<div class="font16  PT5" id="investPeriod"></div>
						</li>
						<li class="width33_3 boxSizing fl borderRWhite">
							<h4 class="font14 opacity50" id="earingTex">预计收益</h4>
							<div class="font14 PT5"><span class="font16" id="expectedReturn"></span>元</div>
						</li>
						<li class="width33_3 boxSizing fr">
							<h4 class="font14 opacity50">订单状态</h4>
							<div class="font16  PT5" id="disposeState" id="endTime"></div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="width90 MT15 clearfix blockC positionR">
			<div class="fl">
				<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/investDicon1.png" width="40px" alt="">
				<p class="grayTex font14">起息日期</p>
				<p class="grayTex font14" id="startDate"></p>
			</div>
			<div class="positionA investDicon2">
				<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/investDicon2.png" width="40px" alt="">
				<p class="grayTex font14" id="title1"></p>
				<p class="grayTex font14" id="titleContent1"></p>
			</div>
			<div class="fr">
				<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/investDicon3.png" width="40px" alt="">
				<p class="grayTex font14" id="title2"></p>
				<p class="grayTex font14" id="titleContent2"></p>
			</div>
			<span class="dashedLine1 positionA"></span>
			<span class="dashedLine2 positionA"></span>
		</div>
		<ul class="whiteBkg MT15 borderB">
			<!-- <li class="PLR5P clearfix heigh50 none" id="adjustRateLi">
				<h4 class="fl redTex">专享</h4>
				<div class="fr redTex"><span id="adjustRate" class="font16"></span>%</div>
			</li> -->
			<li class="PLR5P clearfix heigh50">
				<h4 class="fl grayTex">投资金额</h4>
				<div class="fr redTex"><span id="investAmount"></span>元</div>
			</li>
			<li class="PLR5P clearfix heigh50">
				<h4 class="fl grayTex">项目总额</h4>
				<div class="fr blackTex"><span id="contactAmount"></span>元</div>
			</li>
			<!-- <li class="PLR5P clearfix heigh50">
				<h4 class="fl grayTex">起息日期</h4>
				<div class="fr blackTex" id="startDate"></div>
			</li>
			<li class="PLR5P clearfix heigh50">
				<h4 class="fl grayTex">到期日期</h4>
				<div class="fr blackTex" id="endTime"></div>
			</li> -->
			<li class="PLR5P clearfix heigh50 none" id="transferCost">
				<h4 class="fl grayTex">转让手续费</h4>
				<div class="fr blackTex"><span id="assignFee"></span>元</div>
			</li>
			<li class="PLR5P clearfix heigh50">
				<h4 class="fl grayTex">订单号</h4>
				<div class="fr blackTex">${orderId}</div>
			</li>
			<li class="PLR5P clearfix heigh50">
				<h4 class="fl grayTex">还款方式</h4>
				<div class="fr blackTex" id="repaymentType"></div>
			</li>
		</ul>
	</div>	
	<ul class="textL MT15 PB15 borderT" id="description">
		<li class="whiteBkg">
			<h4 class="blackTex dashedB width90 blockC boxSizing positionR newPL8P heigh50"><i class="positionA desIcon1"></i>产品详情<span class="thinStick positionA"></span></h4>
			<div class="grayTex none dashedB width90 blockC PTB5 font14 lineHeight1_5x" id="companyName"></div>
		</li>
		<li class="whiteBkg">
			<h4 class="blackTex width90 blockC boxSizing positionR newPL8P heigh50"><i class="positionA desIcon2"></i>常见问题<span class="thinStick positionA"></span></h4>
			<div class="grayTex none dashedT width90 blockC PTB5 font14 lineHeight1_5x" id="fundSecurity"></div>
		</li>
	</ul>
	<ul class="borderT borderB" id="dealUl">
		<li class="PLR5P heigh50 whiteBkg  textL" id="borrowDeal">
			<a id="loanAgreement" class="block blackTex dealIcon PL10P positionR">查看合同</a>
		</li>
		<li class="PLR5P heigh50 whiteBkg textL none" id="creditTranfer">
			<a class="block blackTex transferIcon dashedT PL10P positionR">债权转让</a>
		</li>
	</ul>
	<div class="redBkg whiteTex font16 heigh50 positionF width100 creditTranferBtn none" id="creditTranferBtn"></div>
	<input id="orderId" name="orderId" type="hidden" value="${orderId}">
    <input id="sloanId" name="sloanId" type="hidden" value="${sloanId}">
    <input id="couponsRateRises" name="couponsRateRises" type="hidden" value="${couponsRateRises}">
	<script src="${pageContext.request.contextPath}/js/weixin/page/investDetail.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

