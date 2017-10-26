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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="imgDiv clearfix" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/logo.png?<%=request.getAttribute("version")%>" ></div>
	<div style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/goInviteFriendIcon.png?<%=request.getAttribute("version")%>" /></div>
	<div class="blackBkg PTB5P textC" id="inviteDiv" style="display: none;">
		<div class="inviteBkg width80 blockC positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/pocketImg.png?<%=request.getAttribute("version")%>" alt="" width="100%">
			<div class="btmTexSection">
				<input id="goUrl" name="goUrl" type="hidden" value="${goUrl}">
				<a class="btn yellowBtn width40 block blockC MT10" id="inviteBtn">立即邀请</a>
			</div>	
		</div>
	</div>
	<div class="width100 blockC">
		<ul class="PTB15 PLR10P" id="myInvitationList"></ul>
		<div id="myInvitationListPaging" class="pagingMobile"></div>
	</div>
	<div class="sharePopup" style="display: none;">
		<div class="textSite">
			<img src="${pageContext.request.contextPath}/pic/weixin/shareTex.png?<%=request.getAttribute("version")%>" height="120">
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/inviteFriend.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>