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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/investeEnsure.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
	<body >
		<input id="channel" name="channel" type="hidden" value="${channel}">
		<div class=" positionA width100  wrap outHide">
		
			<!-- main begin -->
			<div class="main positionR">
				<!-- 投资主体部分 begin -->
				<p class="textC font14 PTB3 grayTex"><span style="letter-spacing: 0.5px;">每日可加入优享计划时间为:&nbsp;&nbsp;</span><span class="redTex" id="enjoyBuyTimeWarn">2:00-23:00</span></p>
				<div class="header PTB15">
					<p class="grayTex font18">投资金额(元)</p>
		
					<!-- 输入框 begin -->
					<div class="headerDiv PT10">
						<font class="grayTex fl font24 lineHeigt18 fontF positionR ">￥</font>
						<div class="fl width80">
							<input type="text" class="headerInput invalid" placeholder="请输入金额" id="inputAmount"  readonly="readonly"/>
						</div>
					</div>
					<!-- 输入框 end -->
		
					<!-- 账户余额 begin -->
					<p class="textC PTB4 grayTex1">
						<span class="inlineB font16" id="allMoney">
							<span>账户余额(元):</span>
							<span class="blackTex helveticaneue font20" id="acctBalance">0.00</span>
						</span>
					</p>
					<!-- 账户余额 end -->
					<!-- 预计收益 begin -->
					<p class="textC  grayTex1 font14 PT3">
						<span class="inlineB" id="investMaxAmountLi">
							<span>剩余可投金额(元)&nbsp;&nbsp;:&nbsp;&nbsp;</span>
							<span class="redTex helveticaneue font14" id="investLimitAmount">10000</span>
						</span>
					</p>
					<!-- 预计收益 end -->
				</div>
				<!-- 投资主体部分 end -->		
			</div>
			<!-- main end -->
			<!-- 服务协议 begin -->
			<div class="width90 blockC MT15 textL">
				<div class="MB5">
					<div  class="grayTex underLine clearfix">
						<span class="checkBox fl MR5 inlineB" >
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/checkbox2.png?<%=request.getAttribute("version")%>" width="16px" id="checkBoxArea">
						</span>
						<span class="inlineB fl" id="agreenment">我同意<a class="blueColor">《联璧金融服务协议》</a></span>
					</div>
				</div>
				<div class="PL10" id="subDiv" style="display:none;">
					<a id="loanAdvisoryServiceAgreement" class="redTex lineHeight1_8x ">《个人出借咨询服务协议》</a><br/>
					<a id="assignmentOfDebtAgreement" class="redTex lineHeight1_8x">《债权转让协议》</a><br/>
					<a id="serviceAgreement" class="redTex lineHeight1_8x">《用户服务协议》</a><br/>
					<a id="riskcue" class="redTex lineHeight1_8x">《风险提示书》</a><br/>
				</div>
			</div> 
			<!-- 服务协议 end -->
		
			<!-- 投资按钮 begin	 -->
		<div class="width90 blockC MT30 PB50" >
			<a id="enterBid" class="btn width100 block font16  radius3">确认投资</a>
		</div>
			</div>
			<!-- 投资按钮 end -->
		     <!--<!-- 嵌套的充值页面 -->
			<div id="iframeWrap" class="positionA iframe ">
			  <iframe src="" frameborder="0" name="iframe" id="iframe"></iframe>
		   </div> 
			<!-- 输入投资金额的键盘 begin -->

			<!-- 输入投资金额的键盘 end -->
		    <div style="display:none" id="formDiv"></div>
		        <!-- 点击立即投资弹出的提示框 begin -->
			 	<div class="alertBg width100 height100P positionF" style="display:none;" id="alertBg1">
					<div class="radius5 whiteBkg width80 alertBox positionA">
						<h3 class="font20 PT15 PB15 redTex textC ">提示</h3>
						<div class="width80 blockC textL MT10">
							<p class="textC MB20">投资<span id="productName01"></span><span id="inputAmountVal"></span>元</p>
							<div class="width90 MLRA outHide">
								<a class="inlineB grayBkg width40  PT5 PB5 grayTex textC fl" id="cancel">取消</a>
								<a class="inlineB redBkg width40 PT5 PB5 whiteTex textC fr" id="enterBtn" onclick="getRollOut()">
							确定</a>
							</div>
						</div>
					</div>
				</div>
		<div class="btmDiv positionF clearfix whiteBkg borderTSL none" id="momeyBox">
				<%@  include file="../numberKeyboard.jsp"%>
		</div>
		   <!-- 点击立即投资弹出的提示框 end -->
		 	<%@  include file="../accout/dealPWControls.jsp"%>
		 	<!-- 密码盘的jsp -->
		 	<%@  include file="../accout/dealPWControls.jsp"%>
		    <script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js?<%=request.getAttribute("version")%>"></script>
			<script src="${pageContext.request.contextPath}/js/weixin/page/investeEnsure.js?<%=request.getAttribute("version")%>"></script>
			<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>
			<script src="${pageContext.request.contextPath}/js/weixin/page/numberKeyboard.js?<%=request.getAttribute("version")%>"></script>
			<%@  include file="../baiduStatistics.jsp"%>
	
	</body>
</html>
