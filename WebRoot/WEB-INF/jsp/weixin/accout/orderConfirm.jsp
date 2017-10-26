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
	<title id="titleNam"></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/investment.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/orderConfirm.css?<%=request.getAttribute("version")%>"/>
    <%@  include file="../header.jsp"%>
</head>
<body>	
<div class=" positionA width100  wrap outHide">
<!-- <div class="contentBox clearfix"> -->
    <!-- main begin -->
	<div class="main positionR">
		<!-- main内容的容器 begin -->
		<div class="header PT20">
			<p class="blackTex font16 textL PLR5P">投资金额</p>
			<!-- 输入框 begin -->
			<div class="headerDiv MT5">
				<font class="grayTex fl font24 lineHeigt18 fontF">￥</font>
				<div class="fl width80">
					<input type="text" class="headerInput invalid PL10" placeholder="请输入金额" id="inputAmount" readonly="readonly"/>
				</div>
			</div>
			<!-- 输入框 end -->
			<!-- 账户余额 begin -->
			<p class="textL PLR5P heigh40 accountLeft">
				<span>账户余额:</span>
				<span class="redTex helveticaneue font16" id="myAcctBalance"></span>
				<span>元 </span>
				<!-- <span class="redTex" id="allMoney">全部余额</span> -->
			</p>
			<!-- 账户余额 end -->
			<!--<hr class="hrBoder">-->
			<!-- 预计收益 begin -->
			<!--<div class="PLR5P PT5">
				<img src="${pageContext.request.contextPath}/pic/weixin/investment/dollar.png" width="20">
				<p class="redTex MT5 font24 helveticaneue strong" id="earnings">0.00</p>
				<p class="grayTex MT5 font16 lineHeight100">预计收益(元)</p>
			</div>-->
			<!-- 预计收益 end -->
		</div>
		
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
			<span  class="fr redTex" id="earnings">0.00元</span>
		</div>
		<!--预期收益结束-->
		<!-- main内容的容器 end -->

		<!-- 各种券类的展示区域 begin -->
		<ul class="textL">
			<li class="borderB heigh50 header PLR5P" id="useRateCuuponsLi">
				<a class="block grayTex clearfix PL2P positionR" id="useRatesCoupon">
					<i class="positionA"></i><span class="fl">代金券</span>
					<div class="fr outHide">
						<span class="fl MR10 grayTex" id="useVoucherAmount">
							<font class="redTex">${vouchersCount}</font>张可用
						</span>
						<img class="turnAround90 stick fr" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="12">
					</div>
				</a>		
			</li> 
		</ul>
	<!-- 各种券类的展示区域 end -->
	</div>
	<!-- main end -->

	<!-- 服务协议 begin -->
	<div class="width90 blockC MT15 textL">
		<div class="MB5">
			<div id="agreenment" class="grayTex underLine clearfix">
				<span class="checkBox fl MR5 inlineB" id="checkBoxArea">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/checkbox2.png?<%=request.getAttribute("version")%>" width="16px">
				</span>
				<span class="inlineB fl">我同意<a class="blueColor">《联璧金融服务协议》</a></span>
			</div>
		</div>
		<div class="PL10" id="subDiv" style="display:none;">
			<a id="" class="redTex lineHeight1_8x">《债权转让协议》</a><br/>
			<a id="" class="redTex lineHeight1_8x">《用户注册服务协议》</a><br/>
			<a id="" class="redTex lineHeight1_8x">《风险提示书》</a><br/>
		</div>
	</div> 
	<!-- 服务协议 end -->
	<!-- 各种券类的展示区域 end -->
	<div class="width90 blockC MT30 PB50">
		<a id="goPay" class="btn width100 block font16 inactiveBtn radius3" loginParm="${loginParm}">立即投资</a>
	</div>
	<!-- <a class="btn PTB10 blockC clearfix radius5 width90 MLRA MT20" id="goPay" loginParm="${loginParm}">立即投资</a> -->
    <!-- </div> -->
    </div>
	  <!-- 嵌套的充值页面 -->
	<div id="iframeWrap" class="positionA iframe ">
	  <iframe src="" frameborder="0" name="iframe" id="iframe"></iframe>
    </div>

	<!-- 输入数字的键盘 begin -->
	<div class="btmDiv width100 clearfix whiteBkg borderTSL none textL" id="momeyBox">
		<div>
			<p class="PL5P grayTex font14"><img class="verMid" src="${pageContext.request.contextPath}/pic/weixin/vipGrade/safeBkg.png" width="14">&nbsp;正在使用联璧安全键盘</p>
			<ul class="clearfix textC moneyBtn width90 blockC MT10 MB20" id="shortcut"></ul>
			<%@  include file="../numberKeyboard.jsp"%>
		</div>
	</div>
	<!-- 输入数字的键盘 end -->
	<input id="mobile" name="mobile" type="hidden" value="${mobile}">
	<input id="userId" name="userId" type="hidden" value="${userId}">
    <!-- 收入投资金额 end -->
	<input type="hidden" name="loanId" id="loanId" value="<%=request.getParameter("loanId")%>">
    <!-- 代金券满金额可用的可用数值 -->
    <input type="hidden" name="investmentAmount" id="investmentAmount" value="<%=request.getParameter("investmentAmount")%>">
	<!-- 代金券金额 -->
	<input type="hidden" name="voucherAmount" id="voucherAmount" value="<%=request.getParameter("voucherAmount")%>">
	<!-- 从代金券页面带来的购买数值 -->
	<input type="hidden" name="inputAmountBac" id="inputAmountBac" value="<%=request.getParameter("inputAmountBac")%>">
	<!-- 代金券id -->
	<input type="hidden" name="voucherId" id="voucherId" value="<%=request.getParameter("voucherId")%>">
	<input type="hidden" name="fromUrl" id="fromUrl" value="<%=request.getParameter("fromUrl")%>">
	<input type="hidden" name="finishedRatio" id="finishedRatio" value="<%=request.getParameter("finishedRatio")%>">
 	<!-- 链接 -->
 	<input id="URL" name="URL" type="hidden" value="${URL}">	
 	<!-- 上架名称 -->
 	<input id="productName" name="productName" type="hidden" value="${productName}">
 	<input type="hidden"  id="token" name="token" value="${token}">
 	<input type="hidden"  id="vouchersCount" name="vouchersCount" value="${vouchersCount}">
 	<!-- 专享加息率 -->
 	<input type="hidden"  id="adjustRate" name="adjustRate" value="${adjustRate}">
 	<!-- 渠道 -->
 	<input id="channel" name="channel" type="hidden" value="${channel}">
 	<div id="formDiv" style="display:none;"></div>

 	<%@  include file="../accout/dealPWControls.jsp"%>
 	<script src="${pageContext.request.contextPath}/js/weixin/page/orderConfirm.js?<%=request.getAttribute("version")%>"></script>
 	<script src="${pageContext.request.contextPath}/js/weixin/page/numberKeyboard.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>


