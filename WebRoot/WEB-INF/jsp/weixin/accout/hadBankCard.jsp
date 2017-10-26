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
	<title>银行卡</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />

</head>
<body>
	<%@  include file="../header.jsp"%>
	<div class="blackBkg PTB5P textC">
		<div class="bankCardDiv width90 blockC">
			<div class="clearfix">
				<div class="fl"><font class="letterB">B</font><font class="font20">ank</font></div>
				<span class="fr bank whiteTex MT10" id="bankName"></span>
			</div>
			<div class="font24 MT20 textC" id="cardNumber"></div>
			<div class="MT20" id="accountName"></div>	
		</div>
	</div>
	<div class="textC PT30">
		<h4 class="font18" id="instructions"></h4>
		<div class=" MT20" id="tel" style="display: none;">
			<span class="tel">
				<img src="${pageContext.request.contextPath}/pic/weixin/tel.png?<%=request.getAttribute("version")%>" alt="" height="25">
				<a href="tel:400-699-9211"  class="JsPhoneCallTell font24 strong ML10 blackTex"><span class="JsPhoneCall"></span></a>
			</span>
		</div>	
	</div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/hadBankCard.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>