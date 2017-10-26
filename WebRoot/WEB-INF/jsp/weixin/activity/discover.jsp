<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>抽奖</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/discover.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>

  </head>
  
  <body >
  	<header></header>
  	<div class="content">
  		<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/draw3.png?<%=request.getAttribute("version")%>" width="90%" id="contentI"/>
  	</div>
  	<footer class="wrap positionR">
  		<div class="positionA wrapSpan">你还可以获取 <span id="number" class="number font20">0</span> 个抽奖号码</div>
  		<div class="phone positionA font16 phoneC2" id="clickNumber">点我获取抽奖号码</div>
  	</footer>
  	 <div class="width100 height100P positionF none" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">
			<div class="radius5 whiteBkg  positionR textC alertBox" >
				<h3 class="font14 PT15 alert positionR" id="">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/alert2.png?<%=request.getAttribute("version")%>" width="65%" />
				</h3>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/alert.png?<%=request.getAttribute("version")%>" width="10%" class="alertBoxA alertBoxI positionA" id="alertBoxI"/>
				<p class="  lineHeight1_5x positionR alertP font18" >
					您的抽奖号码为<span id="alertPhone">1111</span>
				</p>
				<p class="font14 blackTex lineHeight1_5x positionR  alertP2 width90 " >
					感谢您的参与，号码稍后会通过短信发送到您的注册手机。
				</p>
				<a class="inlineB redBkg width100 whiteTex PT15 PB15 font18 positionA alertA alertBoxA" id="alertBoxA" >我知道了</a>
			</div>
		</div>
		 <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" > 
			<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
  		<script src="${pageContext.request.contextPath}/js/weixin/page/discover.js"></script>
  		<%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
