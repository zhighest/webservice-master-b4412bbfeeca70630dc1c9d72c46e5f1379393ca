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
	<title>提现</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/withdrawDeposit.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
<div class="main height100P">
	<input type="hidden" value="${mobile}" id="mobile" name="mobile"/>
	<div class="wrapper PT5">
		<div class="hasTBBorder whiteBkg"  id="hasCard">
			<div class="whiteBkg width90 blockC PLR5P clearfix PTB5">
				<span class="fl"><img class="" id="bankIcon" height="35"></span>
				<div class="fr width80 textL" style="line-height:35px">
					<div class="clearfix fl">
						<h4 class="blackTex fl MR10" id="bank_name"></h4>
					</div>
					<div class="clearfix">
						<p class="blackTex" id="cardNumber"></p>
					</div>
				</div>
			</div>	
		</div>
		<!-- 未绑定银行卡 begin -->
		<div class="wrapper PT5" id="noCard" style="display: none">
			<div class="hasTBBorder whiteBkg MB10">
				<div class="whiteBkg width90 blockC PLR5P clearfix PTB10">
					<span class="fl">银行卡号</span>
					<input type="text" placeholder="请输入银行卡号" name="" class="width75 inputNoborder fr" id="">
				</div>
			</div>	
		</div>
		<!-- 未绑定银行卡 end -->
		<div class="hasTBBorder whiteBkg none" id="cityInfo">
			<div class="whiteBkg width90 blockC PLR5P clearfix heigh50 positionR">
				<div class="fl font14" style="width:22%">选择城市</div>
				<div class="fl width40 boxSizing borderR positionR">
					<select class="grayTex heigh50 inputNoborder font14 width100" id="povince">
						<option value="请选择省" class="font14">请选择省</option>
					</select>
					<i class="downIcon positionA"></i>
				</div>
				<div class="fl positionR"  style="width:38%">
					<select class="grayTex heigh50 inputNoborder font14 width100" id="city">
						<option value="请选择市"  class="font14">请选择市</option>
					</select>
					<i class="downIcon positionA"></i>
				</div>
			</div>	
		</div>

		<div class="hasTBBorder whiteBkg MB10 PB10">
			<div class="whiteBkg grayTex width90 PLR5P clearfix PT15">提现金额</div>
			<div class="whiteBkg width90 blockC PLR5P clearfix PTB15">
				<label class="grayTex font24 fl">￥</label>
				<input type="text" placeholder="请输入提现金额" id="extractMoney" name="extractMoney" readonly class="ML10 width80 font40 inputNoborder moneyInput fl">
				<input type="hidden" name="cardNumber" id="cardNo">
				<input type="hidden" name="cardId" id="cardId">
				<input type="hidden"  id="token" name="token" value="${token}">
			</div>
			<div class="width90 PLR5P">
				<p class=" borderT PT10 grayTex blockC">可提现金额 <span class="redTex" id="extractMoneyHint"></span>元 <a class="redTex" id="insideInputBtn">全部提现</a></p>
			</div>	
		</div>

		<div class="hasTBBorder whiteBkg MB10 clearfix">
			<p class="PLR5P height45 wayDetailP">提现方式<span class="fr" id="wayDetail">提现方式及额度说明</span></p>
			<ul class="whiteBkg hasTBBorder width100 blockC clearfix  positionR tabBox">
				<li class=" grayTex width100 textL current tab2 PTB10 PLR5P clearfix" id="tab2" >
					<div class="fl">
						<span id="soonIcon"></span>
					</div>
					<div class="fl ML20">
						<p class="textL blackTex">快速提现</p>
						<p class="textL MT10 grayTex font14" id="quickWithdrawBtnDegreeMoney">当日剩余快速提现金额：<span id="withdrawMoney" class="redTex"></span></p>
					</div>
				</li>
				<li class="grayTex textC tab1 PLR5P PTB10 clearfix" id="tab1" >
					<div class="fl">
						<span id="soonIcon"></span>
					</div>
					<div class="fl ML20">
						<p class="textL blackTex">普通提现</p>
						<p class="textL MT10 grayTex font14" id="quickWithdrawBtnDegree">当月剩余免费普通提现次数：<span id="withdrawTime" class="redTex"></span>次</p>
					</div>
				</li>
			</ul>
		</div>

		<div class="width90 blockC MT20 clearfix">
			<a id="withdrawBtn" class="btn radius5 width100 block none font16 fl">确认提现</a>
			<a id="quickWithdrawBtn" class="btn radius5 width100 block font16 fr">确认提现</a>
		</div>
	</div>

</div>
	<div class="wayExplain none">
		<p class="font18 PTB10 blackTex ruledetail borderB">提现方式及额度说明</p>
		<div class="MT20 PLR5P PB10 width90 blockC textL lineHeight1_5x grayTex font14">
			<p id="quickAmountDesc"></p>
			<p id="quickCountDesc"></p>
		</div>
		<span class='closeBtn width100 textC positionA'><img src="${pageContext.request.contextPath}/pic/weixin/collectScore/close.png?<%=request.getAttribute("version")%>" width="25"/></span>
	</div>
	<%@  include file="../numberKeyboard.jsp"%>
	<%@  include file="dealPWControls.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/withdrawDeposit.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

