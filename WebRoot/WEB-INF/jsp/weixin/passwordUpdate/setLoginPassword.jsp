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
	<title>设置登录密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="PT20">
	<div class=" whiteBkg borderB">
		<div class="width90 blockC positionR captcha_div">
			<label class="positionA tips1" for="phoneNum">手机号</label>
			<input class="width100 inputNoborder PTB15 PL75 boxSizing " maxlength="11" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" readonly="readonly" onafterpaste="$.checkLimit($(this),'',false);" id="phoneNum" value="${mobile}" type="text">
		</div>
	</div>
	<div class=" whiteBkg" id="VerificationCode"> 
		<div class="width95 ML5P clearfix positionR ">
			<label class="positionA tips1" for="checkCode">验证码</label>
			<input class="width70 fl inputNoborder PTB15 PL75 boxSizing " maxlength="4" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);" id="checkCode" placeholder="请输入手机验证码" type="text">
			<a class="width30 boxSizing fr PLR1P PTB10 MT5 blackTex1 borderL" id="gainCode">获取验证码</a>
		</div>
	</div>
	<div class="positionR MT20 whiteBkg borderB">
		<div class="width90 blockC positionR">
			<label class="positionA tips1" for="password">登录密码</label>
			<input class="width100 inputNoborder PTB15 PL75 boxSizing password" maxlength="16" id="password" placeholder="请输入登录密码" type="password" onkeyup="$.checkLimit1($(this),'',false);">
			<div  class="positionA eye" id="eye1" value="1">
				<img id="eyeImg1" src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA ">
			</div>
			<div  class="positionA delete  none" id="delete1" value="1">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/delete.png" width="16px" class="deleteImg positionA">
			</div>
		</div>
	</div>
	<div class=" whiteBkg">
		<div class="width90 blockC positionR">
			<label class="positionA tips1" for="passwordAgin">重复输入</label>
			<input class="width100 inputNoborder PTB15 PL75 boxSizing password" maxlength="16" id="passwordAgin" placeholder="请再次输入" type="password" onkeyup="$.checkLimit1($(this),'',false);">
			<div  class="positionA eye" id="eye2" value="2">
				<img id="eyeImg2"  src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
			</div>
			<div  class="positionA delete  none" id="delete2" value="2">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/delete.png" width="16px" class="deleteImg positionA">
			</div>
		</div>
	</div>
	<div class="width90 blockC MT30">
		<a class="btn radius5 width100 block" id="enterBtn">确 定</a>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/setLoginPassword.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>