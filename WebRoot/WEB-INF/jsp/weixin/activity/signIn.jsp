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
	<title>立即签到</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/signIn.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>

  </head>
  
  <body class="acitivityBkg">
    <div class="wrapper signinBkg">
        <div class="clearfix MT30">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/signIn.png" class="width60 MLRA block">
				<div class="signInbtn MT20"><a class="block" id="signInBtn" href="#
				">立即签到</a></div>
				<div class="width80 ML10P whiteTex MT20 PB">
					<h4 class="font20 strong lineHeight2x">规则说明</h4>
					<p id="explainText" class="font14 lineHeight1.5x"></p>
					<!-- <p>1 每天可以进行签到，签到后可以获得活期加息收益，不签到则无法获得<br/>2 签到后活期的【当日年化收益率】会发生相应的提高<br/>3 使用<span>联璧金融APP</span>签到的用户可以获得<span>0.5的</span>加息收益,使用<span>微信签到</span>的用户可以获得<span>0.2</span的加息收益。<br/>4 每日只可签到一次，不可重复签到。</p> -->
				</div>
		</div>
    </div>
    <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
    <input type="hidden" name="signFlag" id="signFlag" value="<%=request.getParameter("signFlag")%>" >
    <%@  include file="../baiduStatistics.jsp"%>
  </body>
  <script src="${pageContext.request.contextPath}/js/weixin/page/signIn.js"></script>
</html>
