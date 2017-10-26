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
	<title>K2活动页</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity_20160501.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    
    <style>
    	#bgimg{width: 100%;}
    	img{width: 100%;}
    	#btn{width: 100%; -webkit-tap-highlight-color: rgba(0,0,0,0);}
    	
    </style>

  </head>
  
  <body>
  	<div id="bgimg">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_k2/giftinfo1.png?<%=request.getAttribute("version")%>">

		</div>
		<div id="btn">
				<a href="http://www.zhihuiup.com/toGoodsPage/zero?channelNo=135&channel=LBJR" rel="nofollow"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_k2/giftinfo2.png?<%=request.getAttribute("version")%>"></a>
			</div>
		<%@  include file="../baiduStatistics.jsp"%>
  </body>
  <!--<script src="${pageContext.request.contextPath}/js/weixin/page/activity_20160501.js?<%=request.getAttribute("version")%>"></script>-->
  
</html>
