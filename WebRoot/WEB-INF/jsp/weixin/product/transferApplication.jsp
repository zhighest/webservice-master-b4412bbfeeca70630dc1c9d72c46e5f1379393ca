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
	<title>债权转让申请</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/transferApplication.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="MB50 positionF autoHide width100">
		<!-- part first begin -->
		<div class="whiteBkg borderT MT10 borderB">
			<div class="grayTex font12 textL PT10 PB10 borderBD PLR5P">如果您今日提出债权转让申请，年化收益率及手续费如下所示</div>
			<!-- 年化收益率 begin -->
			<div class="clearfix borderBD PLR5P">
				<div class="clearfix font16 heigh50">
					<div class="fl positionR blackTex clickInfo1 cursor" onclick="switchBtn('clickInfo1','showInfo1')">年化收益率<img class="turnAround positionA stick img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/stick.png" width="10"></div>
					<span class="fr redTex" id="probability"></span> 
				</div>
				<div class="positionR borderThis radius5 font12 textL MB10 PT5 PB5 showInfo1 tip" style="display: none;">
					<img  class="positionA" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/miniStick.png" height="10">
					<p id="probabilityP"><!-- 3个月,年化收益率<span>15%</span>; 6个月,年化收益率<span>16%</span>; 9个余额,年化收益率<span>17%</span>; --></p>
				</div>
			</div>
			<!-- 年化收益率 end -->
			<!-- 转让手续费 begin -->
			<div class="clearfix borderBD PLR5P">
				<div class="clearfix font16 heigh50">
					<div class="fl positionR blackTex clickInfo2 cursor"  onclick="switchBtn('clickInfo2','showInfo2')">转让手续费<img class="turnAround positionA stick img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/stick.png" width="10"></div>
					<span class="fr redTex" id="poundage"></span> 
				</div>
				<div class="positionR borderThis radius5 font12 textL MB10 PT10 PB10 showInfo2" style="display: none;">
					<img  class="positionA" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/miniStick.png" height="10">
					<p id="poundageP"><!-- 3个月,年化收益率<span>15%</span>; 6个月,年化收益率<span>16%</span>; 9个余额,年化收益率<span>17%</span>; --></p>
				</div>
			</div>
			<!-- 转让手续费 end -->
			<div class="heigh50 clearfix PLR5P font16">
				<a class="fl positionR blackTex ">转让完成日</a>
				<span class="fr blackTex">预计<label class="redTex" id="dayTex">7</label>个工作日内</span> 
			</div>
		</div>
		<!-- part first end -->
	
		<!-- part second begin -->
		<div class="whiteBkg borderT MT10 borderB clearfix PLR5P">
			<div class="clickInfo3 clearfix cursor positionR PL10P heigh50" onclick="switchBtn('clickInfo3','showInfo3')">
				<i class="positionA desIcon1"></i>
				<span class="fl font16 blackTex ">债权转让规则说明</span>
				<img  class="fr turnAround img MT20" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="20">
			</div>
			<div class="showInfo3 grayTex font14 textL PB10" style="display: none;">
				<p class="lineHeight1_5x" id="transferRegulation"><!-- 1.投资超享计划自可转让开始日起(即满<span>3个月</span>)可选择债权转让<br>
					2.申请债权转让后，年化收益率将做出如下改变：<br>自起息日起到转让申请日满 <span>3个月</span>且不满<span>6个月</span>，享受<span>15%</span>的年化收益率；
					自起息日起到转让申请日满<span>6个月</span>且不满<span>9个月</span>，享受 <span>15%+1%</span>的年化收益率；
					自起息日起到转让申请日满<span>9个月</span>且不满<span>12个月</span>，享受<span>15%+2%</span> 的年化收益率；<br>
					3.债权转让需要支付转让手续费，转让手续费为转让总额（投 
					资本金+投资收益）的<span>0.5%</span>。 --></p>
				</div>
			</div>
			<!-- part second end -->
			<div class="clearfix font14 PLR5P MB10 MT10">
				<img class="fl MR10 thisMT2" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/full.png" width="14" id="checkboxImg">
				<span class="grayTex fl">查看并同意<a class="redTex" href="${pageContext.request.contextPath}/wxabout/goUTTranAgreement">《债权转让协议》</a></span>
			</div>
		</div>

		<div class="positionF bottom">
			<div class="redBkg transferBtn whiteTex positionR heigh50 cursor" id="enterSubmit">债权转让申请</div>
		</div>
		<input type="hidden" name="orderIdSed" id="orderIdSed" value="<%=request.getParameter("orderIdSed")%>" >
		<input type="hidden" name="valueDate" id="valueDate" value="<%=request.getParameter("valueDate")%>" >
		<input type="hidden" name="investAmount" id="investAmount" value="<%=request.getParameter("investAmount")%>" >
		<input type="hidden" name="assignUserType" id="assignUserType" value="<%=request.getParameter("assignUserType")%>" >
		<input type="hidden" name="sloanId" id="sloanId" value="<%=request.getParameter("sloanId")%>" >
		<input type="hidden" name="couponsRateRises" id="couponsRateRises" value="<%=request.getParameter("couponsRateRises")%>" >
		<input id="URL" name="URL" type="hidden" value="${URL}">
		<script src="${pageContext.request.contextPath}/js/weixin/page/transferApplication.js"></script>
	</body>
	</html>
