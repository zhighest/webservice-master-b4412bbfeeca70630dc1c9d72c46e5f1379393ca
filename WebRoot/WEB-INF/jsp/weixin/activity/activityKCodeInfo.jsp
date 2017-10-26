<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
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
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>“K”码活动资格查询</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityKCode.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityKCodeInfo.css?<%=request.getAttribute("version")%>" />
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
<body class="infoBkg positionR">
	<h1 class="font20 whiteTex title">资格查询</h1>
	<div class="typeOne none" id="typeOne">
		<img class="block blockC MT15P" src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/tipTex.png" width="85%" alt="">
		<div class="yellowBox clearfix width85 blockC PLR5P boxSizing MT10P font18">
			<div class="fl TextL PT5P">
				<h2 class="font22">斐讯K2路由器</h2>
				<p class="MT10P">或（斐讯K1路由器）</p>
			</div>
			<a href="${pageContext.request.contextPath}/wxactivity/goActivityKCodeLogin" class="fr redTex1 width10 PL5P activateBtn PTB10P">
				立即<br/>激活
			</a>
		</div>
	</div>
	<div class="typeTwo none" id="typeTwo">
		<div class="yellowBox clearfix width85 blockC PLR5P boxSizing MT10P font18" id="K2Box">
			<div class="fl TextL PT5P">
				<h2 class="font22">斐讯<span class="model2"></span>路由器</h2>
				<p class="MT10P font14" id="K2Tip"></p>
			</div>
			<div class="fr width10 PL5P redTex1 activateBtn PTB10P" id="K2State">
				立即<br/>激活
			</div>
		</div>
		<div class="redBox clearfix width85 blockC PLR5P boxSizing font18" id="K1Box">
			<div class="fl TextL PT5P">
				<h2 class="font22">斐讯<span class="model1"></span>路由器</h2>
				<p class="MT10P font14" id="K1Tip"></p>
			</div>
			<div class="fr width10 PL5P activateBtn whiteTex  PTB10P" id="K1State">
				立即<br/>激活
			</div>
		</div>
		<div class="none" id="fullActivate">
			<img class="block blockC MT20P" src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/fullTip.png" width="85%" alt="">
			<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT15P MB5P" href="${pageContext.request.contextPath}/wxabout/goIndex">去首页</a>
		</div>
		<p class="whiteTex MT3P activateNum">您已激活<span class="model1"></span>路由器<span id="routerNumK1"></span>台，<span class="model2"></span>路由器<span id="routerNumK2"></span>台</p>
	</div>
	<div class="popup" id="pop1">
		<div class="popCon blockC width90 PTB5P radius5">
			<!-- <img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/closedBtn.png" width="20" class="closedBtn positionA"> -->
			<div class="width90 blockC">
				<h4 class="font18">输入领取手机号</h4>
				<div class="MT20">
					<!-- 手机号 -->
					<input type="tel" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="手机号" class="inputBkg1 MB20 width100 radius5" />
					<!-- 图片验证码 -->
					<!--<div class="clearfix">
						<input type="text" class="inputBkg1 MB20 width60 fl radius5" name="imgVerifycode" placeholder="图形验证码" id="imgVerifycode" maxlength="4"/>
						<img id="imgCodeTx" src="${pageContext.request.contextPath}/imgcode" height="46" width="35%" class="fr radius5" style="width:35%;">
					</div>-->
					
					<div class="clearfix captcha_div" >
					<!-- 验证码 -->
						<input type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputBkg1 MB20 width60 fl radius5" />
						<a class="btn width35 fr PLR1P smallBtn1 radius5" id="gainCode">获取<br/>验证码</a>
					</div>
					<div class="textL MB15 font14">
						<span class="inlineB blackTex">我同意<a href="${pageContext.request.contextPath}/wxabout/regAgreement" class="blackTex underLine">《用户服务协议》</a></span>	
					</div>
				<div><a class="btn width100 block radius5 forbid" id="loginSubmit">登录</a></div>
				<div class="MT20 textC">-新用户也可直接登录-</div>
			</div>
		</div>		
	</div>
	<input type="hidden" id="isLogin" value="${isLogin}"><!-- 是否登录 -->
	<%@  include file="../baiduStatistics.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeInfo.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeshare.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>