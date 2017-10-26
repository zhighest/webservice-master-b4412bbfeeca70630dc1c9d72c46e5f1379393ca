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
	<title>活期加息新玩法</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity_20160501.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>

  </head>
  
  <body>
		<div class="wrapper">
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20160501/bkg.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="footBtnBox">
				<a class="block whiteBkg font14 redTex" id="goToTickets"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20160501/0501Btn.png" class="width100"></a>
				<p class="font12 MT10 textC">上述活动的一切解释权归<联璧金融>所有</p>
			</div>
		</div>
		
	    <input type="hidden" name="parm" id="parm" value="${parm}">
	    <input type="hidden" name="mobile" id="mobile" value="${mobile}">
	    <input type="hidden" name="channel" id="channel" value="${channel}">
    	<input type="hidden" name="from" id="from" value="${from}">
    	<%@  include file="../baiduStatistics.jsp"%>
  </body>
  <script src="${pageContext.request.contextPath}/js/weixin/page/activity_20160501.js?<%=request.getAttribute("version")%>"></script>
</html>
