<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    <script type="text/javascript">
    
    	//guanxuxing-add-shebeizhiwen-begin
    	 (function() {
    	        _fmOpt = {
    	            partner: 'lianbi',
    	            appName: 'lianbi_web',
    	            token: '${token_id}' 
    	              };
    	        var cimg = new Image(1,1);
    	        cimg.onload = function() {
    	            _fmOpt.imgLoaded = true;
    	        };
    	        cimg.src = "${tongDunSrc}?partnerCode=lianbi&appName=lianbi_web&tokenId=" + _fmOpt.token;
    	        var fm = document.createElement('script'); fm.type = 'text/javascript'; fm.async = true;
    	        fm.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'static.fraudmetrix.cn/fm.js?ver=0.1&t=' + (new Date().getTime()/3600000).toFixed(0);
    	        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(fm, s);
    	    })();
    	//guanxuxing-add-shebeizhiwen-end
    	</script>
</head>
<body class="positonR loginWrapper">
	<div class="PB50 content">	
		<div class="wrapper PT10P">
			<div class="width90 blockC">
				<form id="loginForm" action="${pageContext.request.contextPath}/wxuser/login<%=queryString%>" method="POST">
					<div class="avatar MB30 blockC">
						<img src="${pageContext.request.contextPath}/pic/weixin/icon1.png">
					</div>
					<div id="passwordLogin">
						<div class="MT30">
							<!-- 手机号 -->
							<div class="clearfix inputContent">
								<input type="tel" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11"   oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="手机号" class="inputBkg1 width100" />
							</div>
							<!-- 登录密码 -->
							<div class="clearfix inputContent positionR"> 
								<input   type="password" name="password" id="password" value="" maxlength="16" placeholder="登录密码" class="inputBkg1 width100" onkeyup="$.checkLimit1($(this),'',false);"/>
								<div id="eye" class="positionA eye">
									<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px" class="eyeImg positionA">
								</div>
								<div id="delete" class="positionA delete  none">
									<img   src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/delete.png" width="16px" class="deleteImg positionA">
								</div>
								
							</div>
							<!-- 图片验证码 -->
							<div class="clearfix inputContent MB20">
								<input type="text" class="inputBkg1  width70 fl" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"  oninput="checkC();" onkeyup="checkC();" onafterpaste="checkC();" oncopy="return false;" onpaste="return false"  style="height:50px"/>
								<img id="imgcode" class="ML5P" src="${pageContext.request.contextPath}/imgcode" height="46" width="25%" class="width25 fr PLR1P">
							</div>
							<input class="none" id="loginFlag" type="text" name="loginFlag" value="1">
						</div>
						<div><a class="btn width100 block forbid bor radius5" id="loginSubmit">登录</a></div>
						<div class="MT20 textC" id="switchhover2">
							<a class="fl grayTex" href="${pageContext.request.contextPath}/wxuser/goLogin<%=queryString%>">通过短信验证码登录</a>
							<a class="fr grayTex" href="${pageContext.request.contextPath}/wxuser/goRegister<%=queryString%>">立即注册</a>
						</div>
					</div>
					<input type="hidden" name="mobileNumber" id="mobileNumber" value="<%=request.getParameter("mobileNumber")==null?request.getAttribute("mobileNumber"):request.getParameter("mobileNumber")%>" >
					<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")==null?request.getAttribute("channel"):request.getParameter("channel")%>">
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
	</script>

	<script src="${pageContext.request.contextPath}/js/weixin/page/passwordLogin.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>