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
	<title>好友“联起来”</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/inviteFriend.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body>
	<div class="wrap"> 
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/inviteBanner2.png"class="width100">
		<span class="wrapImg1">已成功邀请<span class="wrapImg2" id="wrapImg2">0</span>人</span>
		<a href="${pageContext.request.contextPath}/wxtrade/goFriendList" class="wrapImg3">查看邀请记录<span class="ML3">&gt;</span></a> 
	</div>
	 
	  <div class="content">
	  	<p class="newFont20 strong" id="bigActivityRecord"></p>
	  	
	  	<p class="newFont18 MT7 newcolor" id="smallActivityRecord"></p>
	  	
	  </div>
	   <div class="details ">
 		 <a href="${pageContext.request.contextPath}/wxactivity/goSeedetails" class="block MB20">查看活动详情<span class="ML3">&gt;</span></a>
	  </div>
	  <div class="inviteBtn newMT40">
	  	<a id="inviteBtn" class="block">邀请好友</a>
	    <a href="${pageContext.request.contextPath}/wxtrade/goMyQuickMark" class="block ">我的二维码</a>	    
	  </div>
	  <div class="heigh30"></div>
	<div class="sharePopup" style="display: none;">
		<div class="textSite">
			<img src="${pageContext.request.contextPath}/pic/weixin/shareTex.png?<%=request.getAttribute("version")%>" height="120">
		</div>
	</div>
	<input id="goUrl" name="goUrl" type="hidden" value="${goUrl}">
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" />
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" />
	<script src="${pageContext.request.contextPath}/js/weixin/page/invite.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/invitationDetails.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>