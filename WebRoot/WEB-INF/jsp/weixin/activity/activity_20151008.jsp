<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>好友“联起来”</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
	<div class="wrapper">
		<div class="imgDiv clearfix" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/logo.png?<%=request.getAttribute("version")%>" ></div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_02.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_04.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_05.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_06.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_07.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_08.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_09.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_10.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_11.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_12.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_13.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_14.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv bkg1">
			<div class="width100 textC PTB10P"><a href="${pageContext.request.contextPath}/wxtrade/goInviteFriend?invitationCd=${mobile}" class="btn activitybtn1 width80 blockC block radius5">邀请好友</a></div>	
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/img_16.jpg?<%=request.getAttribute("version")%>">
		</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

