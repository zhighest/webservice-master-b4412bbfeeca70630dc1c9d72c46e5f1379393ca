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
	<title>活动细则</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity20151029.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="wrapper">
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/img_01.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/img_02.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/img_03.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/img_04.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix KCodebtnArea">
			<div class="width100 textC PT10">
				<!--微信端按钮-->
				<a class="textC blockC" id="weixin" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/btn.png" class="KCodebtn"></a>
				<!--Ios按钮-->
				<label id="lianbiIos" style="display: none;">
					<input type="button" value="" style="display: none;" id="iosBtn"/>
					<a class="textC blockC" id="lianbiIconBtn"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/btn.png" class="KCodebtn"></a>
				</label>
				<!--安卓按钮-->
				<a class="textC blockC" id="lianbiAndroid" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/btn.png" class="KCodebtn"></a>
				
				
					
			</div>

				
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/img_05.jpg?<%=request.getAttribute("version")%>">
		</div>
	
	
		
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" />
	<input type="hidden" name="channel" id="channel" value="${channel}" />
	<script src="${pageContext.request.contextPath}/js/weixin/page/ldActivityDetal.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

