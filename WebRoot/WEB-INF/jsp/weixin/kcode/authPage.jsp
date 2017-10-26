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
	<title>授权</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/authPage.css?<%=request.getAttribute("version")%>" />
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
<body class="whiteBkg">
	<div class="width90 MLRA">
		<div class="PT10P ">
			<img src="${pageContext.request.contextPath}/pic/weixin/LBlogo.png" width="45%">
		</div>
		<div class="MT40 ">
						<!-- 手机号 -->
						<div class="clearfix inputContent">
							<input type="tel" name="phoneNum" id="phoneNum" value="" readonly="readonly" maxlength="11" class="inputBkg1 width100" />
						</div>

						<!-- 验证码 -->
						<div class="clearfix inputContent MB20 captcha_div">
							<input type="tel" id="checkCode" name="checkCode" value="" placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputBkg1 width70 fl" />
							<a class="fr redTex textC PT10 heigh30 PR5" id="gainCode">获取验证码</a>
						</div>
						<input class="none" id="loginFlag" type="text" name="loginFlag" value="2">
			</div>
			<div class="MT80">
				<a class="btn width100 block  radius5 forbid" id="loginSubmit">授权</a>
				<a class="btn width100 block forbid radius5 MT10 forbid" id="returnSubmit">返回</a>
			</div>
	</div>
		<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
		<script src="${pageContext.request.contextPath}/js/weixin/page/authPage.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
