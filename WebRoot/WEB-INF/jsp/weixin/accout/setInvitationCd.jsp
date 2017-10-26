<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>填写邀请码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
<div class="wrapper PT20">
	<div class="hasTBBorder whiteBkg MB20">
		<div class="whiteBkg width80 blockC PLR5P clearfix PTB15">
			<span class="fl width30">邀请码</span>
			<input type="text" placeholder="请输入邀请人手机号" name="invitationCd" id="invitationCd" class="width70 inputNoborder fr">
		</div>	
	</div>
	<div class="width80 blockC MT30"><a class="btn width100 block" id="invitationCdBtn">确 定</a></div>
</div>
<script src="${pageContext.request.contextPath}/js/weixin/page/setInvitationCd.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>