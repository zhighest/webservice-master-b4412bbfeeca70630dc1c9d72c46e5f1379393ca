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
	<title>转出</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/rolloutIndex.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script> -->
	<style>
		body{overflow-x: hidden;}
		#canel2{display: inline-block;width: 36px; height: 50px;  position:absolute;left: 96%; display: none;}
		
	</style>
</head>
<body class="positionR bkg4">
	<!-- 红色头部整体 begin -->
	<div class="width100 blockC twoColor PT40">
		<a class="whiteTex font12 positionA rolloutList" href="${pageContext.request.contextPath}/wxInvest/getRolloutList">转出记录</a>
		<h1 class="textC whiteTex font14 opacity80">可转出总金额(元)</h1>
		<h6 class="textC whiteTex font48 helveticaneue strong lineHeight100 MT5" id="enableAmount">0.00</h6>
		<div class="halfBkg MT40">
			<ul class="outHide width90 whiteTex MLRA PTB2P font14">
				<li class="fl textC width49 borderRL">
					<a href="${pageContext.request.contextPath}/wxInvest/getCreditorList" class="whiteTex">
						<p class="opacity80 MB5">在投债权金额(元)</p>
						<p class="font24 helveticaneue lineHeight100 strong" id="curBondAmount">0.00</p>
					</a>
				</li>
				<li class="fl textC width50" id="icon">
					<p class="opacity80 MB5">撮合中金额(元)
						<img class="positionA MT4 ML2" src="${pageContext.request.contextPath}/pic/weixin/icon.png" width="12px" id="iconImg"/>
					</p>
					<p class="font24 helveticaneue lineHeight100 matchAmount strong">0.00</p>
				</li>
			</ul>
		</div>
	</div>
	
	<div class="whiteBkg MT10 width64  PLR5P clearfix PTB5 fl">
		<span class="fl heigh37 font16 PT5 blackTex" >¥</span>
		<input type="number" placeholder="请输入转出金额" name="" id="inputRollNum" class="width90 inputNoborder fr redTex font30 heigh42" alt="">
	</div>
	<div class="fr width23 PTB5 heigh42 MT10 whiteBkg redTex font16" id="fullAmount">
		全部转出
	</div>
	<div class="clearfix"></div>
	<ul class="borderT MT10 textL" id="useRateCuuponsUL">
		<li class="borderB heigh50 whiteBkg PLR5P">
			<a class="clearfix block blackTex PL10P positionR" onclick="useRatesCoupon()">
				<i class="positionA desIcon0"></i>
				<span class="fl redTex">手续费抵用券</span>
				<span class="fr MR20 grayTex1 font14" id="useAddRates">使用手续费抵用券</span>
				<span id="canel2">×</span>
				<span id="canel1"><img class="turnAround90 positionA stick" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></span>	
			</a>
		</li> 
	</ul>
	<div class="whiteBkg width94 blockC MT10 PTB5P ">
		<h4 class="MB10">手续费计算器</h4>
		<ul class="inlineBUl clearfix PTB5 textL width85 blockC">
			<li class="width50 fl MB10"><h5 class="font12 lineHeight100 MB5">撮合中金额（元）</h5><span class="redTex font24 helveticaneue matchAmount2">0.00</span></li>
			<li class="width40 fr MB10"><h5 class="font12 lineHeight100 MB5">免手续费（元）</h5><span class="redTex font24 helveticaneue">0.00</span></li>
			<li class="width50 fl MB10"><h5 class="font12 lineHeight100 MB5">在投债权金额（元）</h5><span class="redTex font24 helveticaneue" id="curBondMoney">0.00</span></li>
			<li class="width40 fr MB10"><h5 class="font12 lineHeight100 MB5">转出手续费（元）</h5><span class="redTex font24 helveticaneue" id="handCharge">0.00</span></li>
		</ul>
	</div>

	<div id="agreement">
		<span id="icon1"><img id="icon2" src="${pageContext.request.contextPath}/pic/weixin/checkbox2.png"></span><span class="blackTex" id="txt" onclick="goxieyi()">我同意《债权转让协议》</span>
	</div>
	<div id="turnOutMsg" class="grayTex width90 blockC MT5P textL PB30P lineHeight1_5x font14">

	</div>
	<!--<div class="whiteBkg borderT fixedBtmArea width100 PT5 PB5"><a class="btn width80 radius5 PTB7 block blockC" id="inactiveBtn">转出</a></div>-->
	<div class="whiteBkg borderT fixedBtmArea width100   btmDiv">
		<div class="width90 blockC clearfix">
			<a class="btn PTB10 width100 blockC fr radius3" id="inactiveBtn" >转出</a>
		</div>
	</div>
	<div style="display:block" id="formDiv"></div>
	<div class="alertBg width100 height100P positionF" style="display:none;" id="alertBg1">
		<div class="radius5 whiteBkg width80 alertBox positionA PB5P">
			<div class=" blockC textC">
				<h3 class="font20 PTB15  textC">温馨提示</h3>
				<div class="hearder"></div>
				<div class="MB15 PT15">
					<p id="tips1" class="font20 blackTex lineHeight1_8x ">转出<span class="redTex" id="turnOutAmountText">0.00</span>元</p>
					<p id="tips2" class="font16 blackTex lineHeight1_8x grayTex">撮合中金额<span id="matchAmountText">0.00</span>元，免手续费</p>
					<p id="tips3" class="font16 blackTex lineHeight1_8x grayTex">在投债权<span  id="HandChargeMoneyText">0.00</span>元，手续费<span id="handChargeText">5.00</span>元</p>
					<p id="tips4" class="font20 blackTex lineHeight1_8x ">已使用<span class="redTex" id="minusAmount">0.00</span><span id="loan">元手续费抵用券</span></p>
					<p id="tips5" class="font20 blackTex lineHeight1_8x ">实际到帐<span class="redTex" id="arriveAmount">0.00</span>元</p>
				</div>
				<a class="inlineB grayBkg width35 ML10 MR10 PTB8 grayTex textC radius5" id="cancel">取消</a>
				<a class="inlineB redBkg width35 ML10 MR10 PTB8  whiteTex textC radius5" id="enterBtn" onclick="getRollOut()">确定</a>
			</div>
		</div>
	</div>
	<div class="positionA blackTex1 font14 msg opacity0" id="msg">
		<div class="figure" id="figure"></div>
			<div class="whiteBkg msgB  radius5 shadowB">
				<div class="PTB5 PLR10 textL">转入金额:<span class="redTex" id="inAmount">0元</span></div>
				<div class="PTB5 PLR10 textL borderT">转出金额:<span class="redTex" id="outAmount">0元</span></div>
			</div>
	</div>
	<input type="hidden" id="couponId" name="couponId" value="<%=request.getParameter("couponId")%>">
	<input type="hidden" name="couponAmount" id="couponAmount" value="<%=request.getParameter("couponAmount")%>" >
	<input type="hidden" name="limitAmount" id="limitAmount" value="<%=request.getParameter("limitAmount")%>" >
	<input type="hidden" name="labelFlag" id="labelFlag" value="<%=request.getParameter("labelFlag")%>" >
	<!-- 红色头部整体 end -->
	<%@  include file="../accout/dealPWControls.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
<!-- 	<input type="hidden"  id="token" name="token" value="${token}">	 -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/rolloutIndex.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>	
	<%@  include file="../baiduStatistics.jsp"%>
</body>
	
	
</html>

