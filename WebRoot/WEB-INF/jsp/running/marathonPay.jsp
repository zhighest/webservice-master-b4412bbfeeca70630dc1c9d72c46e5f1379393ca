<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>支付</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/running/marathonPay.css?<%=request.getAttribute("version")%>" />
	<script src="${pageContext.request.contextPath}/js/weixin/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
<body class="whiteBkg">
	<div class="width90 blockC PT30 blackTex">
		<div class="positionR">
			<label for="inputNum" class="positionA inputTitle font16">银行卡号</label>
			<input type="text" class="border width100 font16" id="inputNum" placeholder="请输入银行卡号" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);">
		</div>
		<p class="textL heigh50 font16 grayTex">报名费<span class="redTex font20">50</span>元/人</p>
		<a class="redBkg whiteTex block blockC PT10 PB10 font18" id="payBtn">支付</a>
		<p class="textL font14 grayTex lineHeight1_5x">您的报名时间为<span class="redTex" id="createTime"></span>，请在<span class="redTex">30</span>分钟内完成支付。</p>
		<P class="lineHeight1_5x">仅限本人借记卡支付，不支持信用卡支付</P>
		<ul class="PT30 clearfix bankList textL font14">
			<li class="font16 width33_3 MB15">支持银行：</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon1.png" width="25px" alt="邮政银行">
				邮政银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon2.png" width="25px" alt="中国银行">
				中国银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon3.png" width="25px" alt="招商银行">
				招商银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon4.png" width="25px" alt="兴业银行">
				兴业银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon5.png" width="25px" alt="浦发银行">
				浦发银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon6.png" width="25px" alt="民生银行">
				民生银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon7.png" width="25px" alt="平安银行">
				平安银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon8.png" width="25px" alt="农业银行">
				农业银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon9.png" width="25px" alt="工商银行">
				工商银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon10.png" width="25px" alt="华夏银行">
				华夏银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon11.png" width="25px" alt="交通银行">
				交通银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon12.png" width="25px" alt="广发银行">
				广发银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon13.png" width="25px" alt="建设银行">
				建设银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon14.png" width="25px" alt="光大银行">
				光大银行
			</li>
			<li class="fl positionR width33_3 MB20 grayTex">
				<img class="positionA" src="${pageContext.request.contextPath}/pic/running/bankIcon15.png" width="25px" alt="中信银行">
				中信银行
			</li>
		</ul>
	</div>
	<form action="https://wap.lianlianpay.com/authpay.htm" method="post" id="llpayForm">
  		<input type="hidden" name="req_data" value="" id="req_data" />
  	</form>
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
	<script src="${pageContext.request.contextPath}/js/running/marathonPay.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>