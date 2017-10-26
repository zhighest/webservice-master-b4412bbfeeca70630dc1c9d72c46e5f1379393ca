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
	<title>注册</title>
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
	<div class="PB50">	
		<div class="wrapper PT10P">
			<div class="width90 blockC">
				<form id="loginForm" action="${pageContext.request.contextPath}/wxuser/goRegisterPassword<%=queryString%>" method="POST">
				<div class="avatar MB30 blockC">
					<img src="${pageContext.request.contextPath}/pic/weixin/icon1.png">
				</div>
				<div class="MT30">
					<!-- 手机号 -->
					<div class="clearfix inputContent captcha_div">
						<input type="tel" name="phoneNum" id="phoneNum" value="${phoneNum}" maxlength="11" oncopy="return false;" onpaste="return false" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);"  placeholder="手机号" class="inputBkg1  width100" />
					</div>
					<!-- 图片验证码 -->
					<!--<div class="clearfix inputContent">
						<input type="text" class="inputBkg1 width70 fl" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4" style="height:50px" oncopy="return false;" onpaste="return false"/>
						<img id="imgcode" class="ML5P" src="${pageContext.request.contextPath}/imgcode" height="46" width="25%" class="width25 fr PLR1P">
					</div>-->
					
					<div class="clearfix inputContent">
					<!-- 验证码 -->
						<input type="tel" id="checkCode" name="checkCode" value="" placeholder="验证码" oncopy="return false;" onpaste="return false" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputBkg1  width70 fl" />
						<a class=" fr redTex textC PT10 heigh30 PR5 " id="gainCode">获取验证码</a>
					</div>
					<!-- 登录密码 -->
					<div class="clearfix inputContent MB20 positionR">
						<input type="password" name="password" id="password" value="" maxlength="16" placeholder="8-16位字母、数字或符号的组合" class="inputBkg1 width100 " oncopy="return false;" onpaste="return false" onkeyup="$.checkLimit1($(this),'',false);"/>
						<div id="eye" class="positionA eye">
							<img  class="eyeImg positionA" src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/eye.png" width="18px">
						</div>
						<div id="delete" class="positionA delete  none">
							<img  class="deleteImg positionA" src="${pageContext.request.contextPath}/pic/weixin/passwordLogin/delete.png" width="16px">
						</div>
						
						
					</div> 
				</div>
				<div class="MB20 clearfix PL5P agreenmentArea01">
					<span class="checkBoxArea fl MR10 inlineB" id="checkBoxArea">
						<img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/checkbox2.png?<%=request.getAttribute("version")%>" >
					</span>
					<span class="inlineB fl">我同意<a  id="register">《用户注册服务协议》</a></span>
				</div>
				<div><a class="btn width100 block forbid radius5" id="loginSubmit">注册</a></div>
				<div class="MT20 textC whiteTex clearfix">
					<a class="fl grayTex" href="${pageContext.request.contextPath}/wxuser/goLogin<%=queryString%>">通过短信验证码登录</a>
					<a class="fr grayTex" href="${pageContext.request.contextPath}/wxuser/passwordLogin<%=queryString%>">使用密码登录</a>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/wxRegister.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
