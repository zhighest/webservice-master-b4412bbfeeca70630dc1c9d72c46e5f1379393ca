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
	<title>支付失败</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
</head>
<body class="positionR whiteBkg">
	<div class="PT50"><img src="${pageContext.request.contextPath}/pic/running/failedImg.png" width="20%" alt="支付失败" class="blockC"></div>
	<p class="font26 width40 blockC MT20 grayTex positionR heigh40 PL20 PR20 textC strong">支付失败</p>
	<p class="grayTex textC font16">报名失败，请重新支付。</p>
	<a href="${pageContext.request.contextPath}/running/goMarathonPay?mobile=${mobile}" class="width80 redBkg whiteTex block blockC MT20 MB20 PT10 PB10 font18">返回</a>
</body>
</html>

