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
	<meta name="x5-orientation"content="portrait">
		
	<title>领取加息券</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/getInterestRates.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
  </head>
	<body class="acitivityBkg positionR">		
		<div class="wrapper">
			<div id="date"><span class="block">活动时间</span><div id="date1" class="redTex">2016-06-25 - 2016-07-25</div></div> 
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/bkg01.png?<%=request.getAttribute("version")%>">
			</div>
		</div>

		<div class="width100 blockC positionA getbtnArea btn22 getbtn none">
			<a class="cursor"><img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/btn01.png?<%=request.getAttribute("version")%>" width="68%" class="getbtn"></a>	
		</div>

		
<!--
		<div class="width100 textC PT10">
			
			<a class="textC blockC" id="weixin" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/btn01.png?<%=request.getAttribute("version")%>" class="getbtn"></a>
			
			<label id="lianbiIos" style="display: none;">
				<input type="button" value="" style="display: none;" id="iosBtn"/>
				<a class="textC blockC" id="lianbiIconBtn"><img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/btn01.png?<%=request.getAttribute("version")%>" class="getbtn"></a>
			</label>
			
			<a class="textC blockC" id="lianbiAndroid" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/btn01.png?<%=request.getAttribute("version")%>" class="getbtn"></a>		
		</div>
-->
		<div id="restnum0">还剩 <span id="restnum">0000</span> 张加息券</div>
		<div class="popup" id="popup1" style="display: none;">
			<div class="blockC width90 MTB30P positionR">
				<span class="closedPopBtn positionA"><img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/closed.png" id="close1" width="30" class="MR5"></span>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/getInterestRates/pop.png?<%=request.getAttribute("version")%>" width="100%">
				<div class="textC positionA font28 redTex largeTitle width100 blockC lineHeight100" id="rateRises">1%</div>
				<div class="textC positionA rateDec redTex largeTitle width100 blockC font16 lineHeight100" id="risesDays">活期加息券1天</div>	
			</div>
		</div>	
	</body>
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
   <script src="${pageContext.request.contextPath}/js/weixin/page/getInterestRates.js?<%=request.getAttribute("version")%>"></script> 
</html>
