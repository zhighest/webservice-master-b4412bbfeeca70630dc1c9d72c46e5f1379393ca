<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>分享邀请</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity_20151103.css?<%=request.getAttribute("version")%>" />
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
<body>
	<form id="loginForm" action="${pageContext.request.contextPath}/wxuser/goRegisterPassword" method="POST">
		<input id="appMobileNumber" name="appMobileNumber" type="hidden" value="<%=request.getParameter("mobile")%>">
		<input id="channel" name="channel" type="hidden" value="<%=request.getParameter("channel")%>">
		<div class="wrapper">
			<div class="imgDiv clearfix" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151008/logo.png?<%=request.getAttribute("version")%>" ></div>
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/share_01.png?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix gradient">
				<!--<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/share_01.png?<%=request.getAttribute("version")%>">-->
				<div class="content width90 blockC positionR">
					<div class="positionA contentBkg">
						<!-- <img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/contentBkg.png?<%=request.getAttribute("version")%>" alt=""> -->
					</div>
					<c:if test="${null==userId || ''==userId }">
					<div id="step1" class="step1 positionA blockC">
						<input class="inputTex radius5 MB10 width100" type="tel" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="手机号" >

						<!-- 图片验证码 -->
						<!--<div class="clearfix MB10">
							<input type="text" class="inputTex radius5 width60 fl" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
							<img id="imgcode" class="fr radius5" src="${pageContext.request.contextPath}/imgcode" height="35" style="width: 35%; float: right;" >
						</div>-->						
						<div class="clearfix MB10 captcha_div">
							<input type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputTex radius5 width60 fl" />
							
							<a class="width35 fr btnAct radius5" id="gainCode">获取验证码</a>
						</div>
						<input class="inputTex radius5 MB10 width100" type="password" name="password" id="password" value="" maxlength="16" placeholder="请设置登录密码">
						<a class="width100 btnAct radius5 blockC block forbid" id="loginSubmit" >立即注册</a>
						<div class="agreenment MT5"><font class="font14 act03Tex">我同意<a id="register" class="act03Tex">《用户注册服务协议》</a></font><!-- <font class="textC act03Tex1 MT10 font14">新用户可直接登录</font> --></div>
					</div>
					</c:if>
					<c:if test="${null!=userId}">
						<div id="step2" class="step2 positionA blockC">
							<a class="width100 btnAct bkg1 radius5 blockC block MB10" href="${pageContext.request.contextPath}/wxtrade/goMyAsset">我的账户</a>	
							<a class="width100 btnAct radius5 blockC block" href="${pageContext.request.contextPath}/wxabout/goIndex">立即投资</a>
						</div>
					</c:if>
				</div>
			</div>
			<div>
				<div class="imgDiv clearfix">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/share_03.png?<%=request.getAttribute("version")%>">
				</div>
				<div class="imgDiv clearfix">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/share_04.png?<%=request.getAttribute("version")%>">
				</div>
				<div class="imgDiv clearfix">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/share_05.png?<%=request.getAttribute("version")%>">
				</div>
				<div class="imgDiv clearfix">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151103/share_06.png?<%=request.getAttribute("version")%>">
				</div>
			</div>
		</div>
	</form>
	<%@  include file="../baiduStatistics.jsp"%>
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
	</script>
	<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/register.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>

