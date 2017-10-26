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
	<title>商品详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/jump.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>

  </head>
  
  <body >
    <div class="wrapper">
    	<div class="imgDiv clearfix">
    		<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/image_02.jpg?<%=request.getAttribute("version")%>" />
    	</div>
    	<div class="imgDiv clearfix">
    		<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/image_03.jpg?<%=request.getAttribute("version")%>" />
    	</div>
    	<div class="imgDiv clearfix positionR">
    		<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/image_04.jpg?<%=request.getAttribute("version")%>" />
    			<a class="positionA divA width80 textC" id="weixin" style="display: none;">点击领取抽奖号码</a>
    			<a class="positionA divA width80 textC" id="lianbiAndroid" style="display: none;">点击领取抽奖号码</a>
    			<a class="positionA divA width80 textC" id="lianbiIos" style="display: none;">点击领取抽奖号码</a>
    	</div>
    	<div class="imgDiv clearfix">
    		<img src="${pageContext.request.contextPath}/pic/weixin/activity/Discover/image_05.jpg?<%=request.getAttribute("version")%>" />
    	</div>
    </div>
    <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" > 
			<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
  	<script src="${pageContext.request.contextPath}/js/weixin/page/jump.js"></script>
    <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
