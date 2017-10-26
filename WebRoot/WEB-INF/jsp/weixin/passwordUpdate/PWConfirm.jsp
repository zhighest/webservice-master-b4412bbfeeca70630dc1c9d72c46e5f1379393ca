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
	<title>修改交易密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="PT20">
	<div class="positionR whiteBkg">
		<div class="width90 blockC borderB">
			<label class="positionA tips" for="traderPassword">交易密码</label>
			<input class="width100 inputNoborder PTB15 PL85 boxSizing" maxlength="6" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);" id="traderPassword" placeholder="六位数字" type="password">
		</div>
	</div>
	<div class="positionR whiteBkg">
		<div class="width90 blockC">
			<label class="positionA tips" for="repetition">重复输入</label>
			<input class="width100 inputNoborder PTB15 PL85 boxSizing" maxlength="6" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);" id="repetition" placeholder="请再次输入" type="password">
		</div>
	</div>
	<div class="width90 blockC MT30">
		<a class="btn radius5 width100 block" id="enterBtn">确 定</a>
	</div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/PWConfirm.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>