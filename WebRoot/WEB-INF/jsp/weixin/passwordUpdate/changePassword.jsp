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
	<title>重置交易密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="PT20 whiteBkg">
	<div class="positionR width90 blockC changeWrap clearfix">
		<div class="clearfix textL inputContent positionR captcha_div">
			<span class="color59 inlineB">手机号</span>
			<input type="tel" name="phoneNum" id="phoneNum" value="" readonly="readonly" maxlength="11" placeholder="" class="inputBkg1"/>
		</div>
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">验证码</span>
			<input type="text" id="virfyCode" style="width:100px;" value="" maxlength="6" placeholder="" class="inputBkg1" />
			<em class="color59 heigh50 PLR10 borderL inlineB fr textC" id="gainCode">获取验证码</em>
		</div>
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">身份证号</span>
			<input type="text" id="IDnumber" value="" maxlength="18" placeholder="" class="inputBkg1" />
		</div>
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">交易密码</span>
			<input type="password" name="password" id="password" value="" maxlength="6" placeholder="6位数字密码" class="inputBkg1" />
			<div class="positionA eye" eyeFlag="0">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
			</div>
		</div>
		<div class="clearfix textL inputContent positionR">
			<span class="color59 inlineB">重复输入</span>
			<input type="password" name="password" id="rePassword" value="" maxlength="6" placeholder="请再次输入" class="inputBkg1" />
			<div class="positionA eye" eyeFlag="0">
				<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
			</div>
		</div>
		<div class="width100 blockC MT20">
			<a class="btn radius5 width100 block" id="resetPwdBtn">确定</a>
		</div>
		<%--<div class="width90 blockC">--%>
			<%--<label class="positionA tips" for="IDnumber">身份证号</label>--%>
			<%--<input class="width100 inputNoborder PTB15 PL85 boxSizing" maxlength="18" id="IDnumber" placeholder="请输入身份证号" type="text">--%>
		<%--</div>--%>
	</div>
	
	<input id="mobile" name="mobile" type="hidden" value="${mobile}">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/changePassword2.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>