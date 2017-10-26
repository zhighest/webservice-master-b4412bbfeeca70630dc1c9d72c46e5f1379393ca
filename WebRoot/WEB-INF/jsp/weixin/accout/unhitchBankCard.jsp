<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>解绑银行卡</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="PT20">
	<div id="content">
		<div class="positionR whiteBkg PTB10 borderTB">
			<div class="width90 clearfix blockC">
				<span class="fl MR10"><img id="bankIcon" height="20"></span>
				<h4 class="blackTex MB5 fl" id="bank_name"></h4>
				<p class="grayTex fr ML10" id="">尾号:<span class="blackTex" id="cardNumber"></span></p>
			</div>
		</div>
		<div class="positionR MT10 whiteBkg borderTB">
			<div class="width90 blockC">
				<label class="positionA tips" for="IDnumber">身份证号</label>
				<input class="width100 inputNoborder PTB15 PL85 boxSizing" maxlength="18" id="IDnumber" placeholder="请填写身份确认信息" type="text">
			</div>
		</div>
		<div class="width90 blockC MT30">
			<a class="btn radius5 width100 block" id="submitBtn">解绑银行卡申请</a>
		</div>
	</div>
	<div class="width100 height100P alertBkg positionA none" id="tipBox">
		<div class="width90 blockC whiteBkg radius5 positionA PT20 alertBox">
			<p class="font16 PL20 PR20 lineHeight1_5x blackTex" id="tipTex"></p>
			<a class="width100 MT10 borderT inlineB redTex height45 boxSizing" href="#" onclick="pageSkip()">确定</a>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/unhitchBankCard.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>