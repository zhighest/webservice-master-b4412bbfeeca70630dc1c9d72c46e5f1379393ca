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
	<title>跨年砸蛋，好运联礼</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/doubleFestival.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
  </head>
<body class="">
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic1.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic2.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic3.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic4.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic5.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic6.jpg" alt=""></div>
		<div><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic7.jpg" alt=""></div>
		<div class="positionR"><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityPic8.jpg" alt="">
		  <div class="activityBtn positionA  textC width100"><a href="javascript:;" class="inlineB width60" id="punchEggBtn"><img src="${pageContext.request.contextPath}/pic/weixin/activity/doubleFestival/activityBtn.png" ></a></div>
		</div>
		    
			<%@  include file="../baiduStatistics.jsp"%>

		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">	
		<script src="${pageContext.request.contextPath}/js/weixin/page/doubleFestival.js?<%=request.getAttribute("version")%>"></script>
		
</body>
</html>