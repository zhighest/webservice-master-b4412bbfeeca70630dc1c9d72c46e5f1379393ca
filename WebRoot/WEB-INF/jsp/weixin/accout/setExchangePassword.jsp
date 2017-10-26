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
	<title>设置交易密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="bkg1">
	<div class="wrapper PT5P">
		<div class="width90 blockC">
			<h4 class="grayTex textC font16">为了您的资金安全，请设置交易密码</h4>
			<div class="MT20 textC font20" id="promptArea"></div>
		</div>
		<div class="width95 blockC">
			<div class="MT20">
				<ul class="keyboard">
					<li><a class="border" id="keyboardOne" keyboard="1">1</a></li>
					<li><a class="border" id="keyboardTwo" keyboard="2">2</a></li>
					<li><a class="border" id="keyboardThree" keyboard="3">3</a></li>
					<li><a class="border" id="keyboardFour" keyboard="4">4</a></li>
					<li><a class="border" id="keyboardFive" keyboard="5">5</a></li>
					<li><a class="border" id="keyboardSix" keyboard="6">6</a></li>
					<li><a class="border" id="keyboardSeven" keyboard="7">7</a></li>
					<li><a class="border" id="keyboardEight" keyboard="8">8</a></li>
					<li><a class="border" id="keyboardNine" keyboard="9">9</a></li>
					<li><a class="redBorder" id="keyboardReturn" keyboard="reture">回退</a></li>
					<li><a class="border" id="keyboardZero" keyboard="0">0</a></li>
					<li><a class="redBorder" id="keyboardEnter" keyboard="enter">确定</a></li>						</ul>	
			</div>
		</div>
	</div>
	<form id="setMessageForm" action="${pageContext.request.contextPath}/wxuser/${actionName}" method="POST">
		<input type="hidden" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}"/>
		<input type="hidden" name="passwordCash" id="passwordCash" value=""/>
		<input type="hidden" name="password" id="password" value="${password}"/>
	</form>
	<script src="${pageContext.request.contextPath}/js/weixin/page/setPassword.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>