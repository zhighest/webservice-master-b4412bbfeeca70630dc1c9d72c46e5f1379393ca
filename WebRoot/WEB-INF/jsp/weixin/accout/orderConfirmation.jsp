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
	<title>订单确认</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/investment.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body >
<div class=" positionA width100  wrap outHide">
	<!-- main begin -->
	<div class="main positionR">
		<!-- 投资主体部分 begin -->
		<div class="header PT20">
			<p class="blackTex2 font16 textL PLR5P">投资金额</p>

			<!-- 输入框 begin -->
			<div class="headerDiv MT5">
				<font class="grayTex fl font24 lineHeigt18 fontF">￥</font>
				<div class="fl width80">
					<input type="text" class="headerInput invalid PL10" placeholder="请输入金额" id="inputAmount"  readonly="readonly"/>
				</div>
			</div>
			<!-- 输入框 end -->

			<!-- 账户余额 begin -->
			<p class="textL PLR5P heigh40 accountLeft">
				<span class="inlineB" id="allMoney">
					<span>账户余额: </span>
					<span class="redTex helveticaneue font16" id="accountBalanceAmount"></span>
					<span>元 </span>
				</span>
			</p>
			<!-- 账户余额 end -->
			<!--<div class="hrBoder"></div>-->
		</div>
		<!-- 投资主体部分 end -->
		<!--剩余可投金额开始-->
		<div class="investmentMoney MT10 PLR5P whiteBkg borderTB clearfix">
			<div class="line radius5 positionR">
				<div class="inLine height100P radius5"></div>
				<em class="moveEm positionA">
					<span class="moveSpan positionA whiteTex font12"></span>
				</em>
			</div>
			<span class="fl font12 colorA9A9A9 PTB12">起投金额: <span class="investMinAmount"></span></span>
			<p class="fr font12 colorA9A9A9 PTB12">剩余可购: <span class="totalMoney redTex"></span>元</p>
		</div>
		<!--剩余可投金额开始-->
		<!--预期收益开始-->
		<div class="MT10 totalReturn PLR5P whiteBkg heigh50 borderTB clearfix">
			<span class="fl grayTex">预期收益</span>
			<span class="fr redTex"><span id="allEarnings">0.00</span>元</span>
		</div>
		<!--预期收益结束-->  
		<!-- 券类展示 begin -->
		<ul class=" MT10 textL" id="useRateCuuponsUL">
			<li class=" heigh50 header PLR5P borderB borderT">
				<a class="clearfix block blackTex positionR" onclick="useRatesInterest()">
					<span class="fl grayTex ">加息券</span>
					<span class="fr MR20 grayTex font14" id="useAddRates">使用加息券</span>
					<i class="positionA desIcon0"></i>
					<img class="turnAround90 positionA stick" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14">
				</a>
			</li> 
			<li class="borderB heigh50 header PLR5P">
				<a class="clearfix block blackTex  positionR" onclick="useVoucher()">
					<span class="fl grayTex ">代金券</span>
					<span class="fr MR20 grayTex font14" id="useVoucherTip"><span class='redTex'>0</span>张可用</span>
					<i class="positionA voucherIcon"></i>
					<img class="turnAround90 positionA stick" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14">
				</a>
			</li> 
		</ul>
		<!-- 券类展示 end -->
	</div>
	<!-- main end -->
	
	<!-- 服务协议 begin -->
	<div class="width90 blockC MT15 textL">
		<div class="MB5">
			<div  class="grayTex underLine clearfix">
				<span class="checkBox fl MR5 inlineB" id="checkBoxArea">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/checkbox2.png?<%=request.getAttribute("version")%>" width="16px">
				</span>
				<span class="inlineB fl" id="agreenment">我同意<a class="blueColor">《联璧金融服务协议》</a></span>
			</div>
		</div>
		<div class="PL10" id="subDiv" style="display:none;">
			<a id="" class="redTex lineHeight1_8x ">《定向委托投资管理协议》</a><br/>
			<a id="" class="redTex lineHeight1_8x">《联璧金融平台服务协议》</a><br/>
			<a id="" class="redTex lineHeight1_8x">《风险提示书》</a><br/>
		</div>
	</div> 
	<!-- 服务协议 end -->

	<!-- 投资按钮 begin	 -->
	<div class="width90 blockC MT30 PB50" >
		<a id="goPay" class="btn width100 block font16  radius3">确认投资</a>
	</div>
	</div>
	<!-- 投资按钮 end -->
     <!--<!-- 嵌套的充值页面 -->
	<div id="iframeWrap" class="positionA iframe ">
	  <iframe src="" frameborder="0" name="iframe" id="iframe"></iframe>
   </div> 
	<!-- 输入投资金额的键盘 begin -->
	<div class="btmDiv positionF clearfix whiteBkg borderTSL none" id="momeyBox">
		<div>
			<p class="PL5P grayTex font14"><img class="verMid" src="${pageContext.request.contextPath}/pic/weixin/vipGrade/safeBkg.png" width="14">&nbsp;正在使用联璧安全键盘</p>
			<ul class="clearfix textC moneyBtn width90 blockC MT10 MB20" id="shortcut"></ul>
			<%@  include file="../numberKeyboard.jsp"%>
		</div>
	</div>
	<!-- 输入投资金额的键盘 end -->
	<input id="money" name="money" type="hidden" value="">
	<input id="mobile" name="mobile" type="hidden" value="${mobile}">
	<input id="userId" name="userId" type="hidden" value="${userId}">
	<input id="loanId" name="loanId" type="hidden" value="${loanId}">
    <input id="planId" name="planId" type="hidden" value="${planId}">
    <input id="sloanId" name="sloanId" type="hidden" value="${sloanId}">
    <input id="cpType" name="cpType" type="hidden" value="${cpType}">
    <input id="rateIds" name="rateIds" type="hidden" value="${rateIds}">
    <input id="rateRises" name="rateRises" type="hidden" value="${rateRises}">
    <input id="earningsDay" name="earningsDay" type="hidden" value="${earningsDay}">
    <input id="bidId" name="bidId" type="hidden" value="${bidId}">
	<input type="hidden" name="goUrl" id="goUrl" value="${goUrl}" >
	<input type="hidden" name="flag" id="flag" value="${flag}" >
	<input id="couponsRateRises" name="couponsRateRises" type="hidden" value="${couponsRateRises}">
	<input id="annualizedReturnRate" name="annualizedReturnRate" type="hidden" value="${annualizedReturnRate}">
    <input type="hidden" name="voucherAmount" id="voucherAmount" value="<%=request.getParameter("voucherAmount")%>" >
    <input type="hidden" name="voucherId" id="voucherId" value="<%=request.getParameter("voucherId")%>" >
    <input type="hidden" name="investmentAmount" id="investmentAmount" value="<%=request.getParameter("investmentAmount")%>" >
    <input type="hidden" name="product" id="product" value="<%=request.getParameter("product")%>" >
    <input type="hidden" name="finishedRatio" id="finishedRatio" value="<%=request.getParameter("finishedRatio")%>" >
    <input type="hidden" name="newFinishedRatio" id="newFinishedRatio" value="<%=request.getParameter("newFinishedRatio")%>" >
    <!-- 渠道 -->
 	<input id="channel" name="channel" type="hidden" value="${channel}">
    <div style="display:none" id="formDiv"></div>
    <%@  include file="dealPWControls.jsp"%>
    <script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/orderConfirmation.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/numberKeyboard.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>

</body>
</html>

