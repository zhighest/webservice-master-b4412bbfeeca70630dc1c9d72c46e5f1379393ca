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
	<title>修改交易密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="whiteBkg positionR">
	<div class="width90 blockC changeWrap clearfix">
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">输入旧密码</span>
			<input type="password" name="password" id="oldPassword" value="" maxlength="6" placeholder="请输入旧密码" class="inputBkg1" />
			<div class="positionA eye" eyeFlag="0">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
			</div>
		</div>
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">输入新密码</span>
			<input type="password" name="password" id="newPassword" value="" maxlength="6" placeholder="6位数字密码" class="inputBkg1" />
			<div class="positionA eye" eyeFlag="0">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
			</div>
		</div>
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">重复输入</span>
			<input type="password" name="password" id="reNewPassword" value="" maxlength="6" placeholder="请再次输入" class="inputBkg1" />
			<div class="positionA eye" eyeFlag="0">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
			</div>
		</div>
		<a class="btn width100 block radius5 MT20" id="updatePwdBtn">确定</a>
		<a class="blackTex2 MT15 fr" onclick="forgetPassword()">忘记交易密码</a>
	</div>
	<div class="width100 height100P alertBkg positionA none" id="tipBox">
		<div class="width90 blockC whiteBkg radius5 positionA redTex PT20 alertBox">
			<p class="font16 lineHeight1_5x">您的账户信息不完整，</br>请联系客服重置交易密码</p>
			<p class="font20 MT10"><img class="MR10" src="${pageContext.request.contextPath}/pic/weixin/tel.png" width="18px"><span class="JsPhoneCall"></span></p>
			<div class="clearfix MT20">
				<a class="JsPhoneCallTell fl whiteTex heigh30 bkg3 boxSizing" id="comfirmBtn" href="tel:4006999211">确定</a>
				<a class="fr grayTex heigh30 grayBkg" href="#" id="cancel">取消</a>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/changePassword.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>