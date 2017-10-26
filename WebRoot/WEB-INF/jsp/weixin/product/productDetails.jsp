<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes"/>
	<meta name="format-detection" content="telephone=no"/>
	<title>投资详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/productDetails.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body>
	<div class="main PB100">
		<div class="whiteBkg">
			<div class="pTB2P indexBkg1">
				<div class="width95 blockC">
					<div class="blockC">
						<div class="content whiteTex PT30 positionR">
							<!-- <a id="addRates">
								<span class="rateIcon positionA"><img src="${pageContext.request.contextPath}/pic/weixin/version1125/icon23.png" alt="" height="22" class="MR2 MT10"></span>
							</a> -->
							<p class="font16">
								<div>
								<span class="positionR font14">预期年化收益率
									<span class="positionA vipHint">
										<img src="${pageContext.request.contextPath}/pic/weixin/vipHint.png" height="16">
									</span>
									<ul class="vipHintUl positionA font12 none">
										<li class="whiteBkg redTex radius5 annualizedReturnRateLi">基本收益<span class="annualizedReturnRate"></span>%</li>
										<li class="radius5 whiteTex couponsRateRisesLi">加息券+<span class="couponsRateRises"></span>%</li>
										<li class="radius5 whiteTex adjustRateLi">专享+<span id="adjustRate"></span>%</li>
									</ul>
								</span>
							</div>
						</p>
						<div class="font24 MT30">
							<div class="positionR perTop MT10 height60 lineHeight100">
								<span class="font60 helveticaneue" id="annualRate"></span>
								<font class="font24 helveticaneue">%</font>
							</div>
						</div>
					</div>
					<ul class="whiteTex clearfix PTB10">
						<li class="width33_3 boxSizing fl borderRWhite">
							<h4 class="font14 opacity50">理财期限</h4>
							<div class="font16 PT5" id="investPeriod"></div>
						</li>
						<li class="width33_3 boxSizing fl borderRWhite">
							<h4 class="font14 opacity50">起投金额</h4>
							<div class="font14 PT5"><span class="font16" id="investMinAmount"></span>元</div>
						</li>
						<li class="width33_3 boxSizing fr">
							<h4 class="font14 opacity50">到期日期</h4>
							<div class="font16 PT5" id="endTime"></div>
						</li>
					</ul>
				</div>
			</div>
			<div class="popupSmall popDiv4" style="display:none;">
				<div class="popupSmallCon">
					<img src="${pageContext.request.contextPath}/pic/weixin/end.png?<%=request.getAttribute("version")%>" alt="" height="150">
				</div>
			</div>	
		</div>
		<ul class="whiteBkg MT15">
				<!-- <li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">账户余额<span class="font14">(元)</span></h4>
					<div class="fr blackTex" id="accountBalanceAmount">0.00</div>
				</li> -->
				<!-- <li class="PLR5P clearfix heigh50 none" id="adjustRateLi">
					<h4 class="fl redTex">专享</h4>
					<div class="fr redTex"><span id="adjustRate" class="font16"></span>%</div>
				</li> -->
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">起息日期</h4>
					<div class="fr blackTex" id="startDate"></div>
				</li>
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">剩余可购</h4>
					<div class="fr blackTex"><span id="remanAmount">0.00</span>元</div>
				</li>
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">借款总额</h4>
					<div class="fr blackTex"><span id="contactAmount">0.00</span>元</div>
				</li>
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">还款方式</h4>
					<div class="fr blackTex" id="repaymentType"></div>
				</li>
			</ul>
		</div>
		
		<ul class="whiteBkg borderTB textL MT10">
			<li class="PLR5P">
				<h4 class="blackTex positionR PL10P heigh50 dashedB clickInfo1" onclick="switchBtn('clickInfo1','showInfo1')" ><i class="positionA desIcon1"></i>产品详情<img class="turnAround positionA stick img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></h4>
				<div class="grayTex dashedB PLR5P PTB5 font14 lineHeight1_5x showInfo1" id="companyName" style="display: none">
				</div>
			</li>
			<li class="PLR5P">
				<h4 class="blackTex positionR PL10P heigh50 clickInfo2" onclick="switchBtn('clickInfo2','showInfo2')"><i class="positionA desIcon2"></i>常见问题<img class="turnAround positionA stick fr img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></h4>
				<div class="grayTex dashedT PLR5P PTB5 font14 lineHeight1_5x showInfo2" id="fundSecurity" style="display: none">
				</div>
			</li>
		</ul>
		<ul class="borderT MT10 textL">
			<li class="borderB heigh50 whiteBkg PLR5P">
				<a class="block blackTex PL10P positionR" href="${pageContext.request.contextPath}/wxabout/goGuaranteeLetter"><i class="positionA desIcon5"></i>多重保障<img class="turnAround90 positionA stick" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></a>
			</li> 
			<li class="borderB heigh50 whiteBkg PLR5P">
				<!-- <a class="block blackTex linkIcon2 PL10P positionR" id="buyRecord">购买记录</a> -->
				<a class="block blackTex PL10P positionR" id="buyRecord"><i class="positionA desIcon6"></i>购买记录<img class="turnAround90 positionA stick" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></a>
			</li> 
			<!-- <li class="borderB PLR5P heigh50 whiteBkg">
				<a class="block blackTex linkIcon3 PL10P positionR" href="">购买帮助</a>
			</li> -->
		</ul>
	</div>
	<div class="btmDiv positionF clearfix whiteBkg borderTSL">
		<div class="width90 blockC clearfix">
			<a class="btn PTB10 width100 blockC fr radius3" id="enterBid" loginParm="${loginParm}">立即投资</a>
		</div>
		
	</div>
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
	<input id="loanId" name="loanId" type="hidden" value="${loanId}">
	<input id="planId" name="planId" type="hidden" value="${planId}">
	<input id="bidId" name="bidId" type="hidden" value="${bidId}">
	<input type="hidden" name="goUrl" id="goUrl" value="${goUrl}" >
	<input type="hidden" name="flag" id="flag" value="${flag}" >
	<input id="sloanId" name="sloanId" type="hidden" value="${sloanId}">
	<input id="cpType" name="cpType" type="hidden" value="${cpType}">
	<input id="rateIds" name="rateIds" type="hidden" value="${rateIds}">
	<!-- <input id="rateRises" name="rateRises" type="hidden" value="${rateRisesCoupons.rate_rises}"> -->
	<input id="couponsRateRises" name="couponsRateRises" type="hidden" value="${couponsRateRises}">
	<input id="annualizedReturnRate" name="annualizedReturnRate" type="hidden" value="${annualizedReturnRate}">
	<input type="hidden" name="voucherAmount" id="voucherAmount" value="<%=request.getParameter("voucherAmount")%>" >
	<input type="hidden" name="rateRises" id="rateRises" value="<%=request.getParameter("rateRises")%>" >
	<input type="hidden" name="investmentAmount" id="investmentAmount" value="<%=request.getParameter("investmentAmount")%>" >
	<input type="hidden" name="voucherId" id="voucherId" value="<%=request.getParameter("voucherId")%>" >
	<input type="hidden" name="finishedRatio" id="finishedRatio" value="<%=request.getParameter("finishedRatio")%>" >
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/productDetails.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/numberKeyboard.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

