	<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
		<%@page import="com.web.util.tongdun.RandomUtils"%>
		<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ page contentType="text/html;charset=UTF-8"%>
		<%@  include file="../../commonWeb.jsp"%>
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
		<meta content="个人中心，联璧金融个人中心" name="keywords">
		<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣！" name="description">
		<title>账户登录 - 联璧金融</title>
		<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/register.css?<%=request.getAttribute("version")%>" />
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
		<c:set var="pageIndex" value="2"/>
		<%@  include file="../header.jsp"%>

		<div class="loginBkg">
		<div class="wrapper clearfix">
		<!-- 首屏悬浮区域 begin -->
		<div class="wrapper loginBoxB">
		<!-- 下载APP begin -->
		<div class="fl whiteTex font30 lineHeight2x suspendDivL">
		<p>联璧金融APP</p>
		<p>助您随时随地轻松理财</p>
		<div class="redBkg shortShortLine MT10"></div>
		<a href="${pageContext.request.contextPath}/webabout/goDownloadDetails" class="noHoverBtn PTB15 PLR15 redBkg font12 radius5 whiteTex">
		<img src="${pageContext.request.contextPath}/pic/web/index/downLoadIcon.png"  width="25px" class="verMiddle">
		<span>下载联璧金融APP</span>
		</a>
		</div>
		<!-- 下载APP end -->
		<!-- 登录注册对话框 begin -->
		<div class="positionR fr loginContent clearfix">
		<div class="goLeft"><a><img class="goWhere pointer" src="${pageContext.request.contextPath}/pic/web/index/leftIcon.png"></a></div>
		<div class="goRight"><a><img class="goWhere pointer" src="${pageContext.request.contextPath}/pic/web/index/rightIcon.png"></a></div>
		<!-- 轮播div begin -->
		<div class="positionR outHide clearfix blockC" id="logonUL">
		<ul class="positionA">
		<li class="fl">
		<!-- 圆角矩形1 begin -->
		<div class="loginBox blockC">
		<!-- 密码登录 begin -->
		<div class="">
		<p class="whiteTex font20 textC">密码登录</p>
		<form id="loginFormPW" action="${pageContext.request.contextPath}/wxuser/login" method="POST" class="MT20 width100">
		<!-- 请输入手机号 -->
		<div class="positionR blockC clearfix inputBox radius5" id="pnPW">
		<label class="inputIcon positionA" for="phoneNumPW">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon1.png" width="15px">
		</label>
		<input class="inputFiled whiteTex fr" autocomplete="off" name="phoneNumPW" id="phoneNumPW" value="" maxlength="11" placeholder="请输入手机号" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" />
		</div>
		<!-- 请输入图形验证码 -->
		<div class="positionR blockC MT10 clearfix inputBox radius5" id="vcPW">
		<label class="inputIcon positionA" for="verifycodePW">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon3.png" width="15px">
		</label>
		<div class="inputFiled whiteTex fr">
		<input type="text" autocomplete="off" class="inputFiled whiteTex fl width50" name="verifycodePW" placeholder="图形验证码" id="verifycodePW" maxlength="4"/>
		<img id="imgcodePW" src="${pageContext.request.contextPath}/imgcode" height="40" class="positionA inputBtn cursor">
		</div>
		</div>
		<!-- 请输入登录密码 -->
		<div class="positionR blockC MT10 inputBox clearfix radius5" id="pwPW">
		<label class="inputIcon positionA" for="password">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon2.png" width="15px">
		</label>
		<input class="inputFiled whiteTex fr" type="password" name="password" autocomplete="off" id="password" value="${userDto.phoneNum}" maxlength="16" placeholder="请输入登录密码" oninput="checkD();" onkeyup="$.checkLimit1($(this),'',false);" onafterpaste="checkD();" />
		</div>
		<!-- 提交按钮 -->
		<div class="submitBtn noHoverBtn radius5 width100 whiteTex block MT20 forbid font20" id="loginSubmitPW">登录</div>
		<div class="clearfix MT20 textC"><a class="whiteTex blockC block" href="${pageContext.request.contextPath}/user/goRegister">立即注册</a></div>
		</form>
		</div>
		<!-- 点击登录 -->
		</div>
		<!-- 圆角矩形1 end -->
		</li>
		<li class="fl">
		<!-- 圆角矩形2 begin -->
		<div class="loginBox blockC fl">
		<!-- 验证码登录 begin -->
		<div class="MB17">
		<p class="whiteTex font20 textC">验证码登录</p>
		<form id="loginForm" action="${pageContext.request.contextPath}/user/login" method="POST" class="MT20 width100">
		<!-- 请输入手机号 -->
		<div class="positionR blockC inputBox radius5 clearfix" id="pn">
		<label class="inputIcon positionA" for="phoneNum">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon1.png" width="15px">
		</label>
		<input class="inputFiled fr whiteTex" autocomplete="off" name="phoneNum" id="phoneNum" value="" maxlength="11" placeholder="请输入手机号" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" />
		</div>
		<!-- 请输入图形验证码 -->
		<!--<div class="positionR blockC MT10 inputBox radius5 clearfix" id="vc">
		<label class="inputIcon positionA" for="verifycode">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon3.png" width="15px">
		</label>
		<div class="inputFiled whiteTex fr">
		<input type="text" autocomplete="off" class="inputFiled whiteTex fl bkgNone width60" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
		<img id="imgcode" src="${pageContext.request.contextPath}/imgcode" class="positionA inputBtn cursor">
		</div>
		</div>-->
		<div style="margin: 10px auto;" id="captcha_div"></div>
		<!-- 请输入短信验证码 -->
		<div class="positionR blockC MT10 inputBox radius5 clearfix" id="chc">
		<label class="inputIcon positionA" for="checkCode">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon2.png" width="15px">
		</label>
		<div class="inputFiled whiteTex fr">
		<input id="checkCode" autocomplete="off" maxlength="6" name="checkCode" value="" class="inputFiled whiteTex fl bkgNone width60" placeholder="请输入验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();"/>
		<a class="redBkg textC fr positionA inputBtn font12 noHoverBtn" disabled="disabled" id="gainCode" value="">获取验证码</a>
		</div>
		</div>
		<!-- 点击登录 -->
		<div class="submitBtn noHoverBtn width100 radius5 whiteTex block MT20 forbid font20" id="loginSubmit">登录</div>
		<div class="clearfix MT20 textC"><a class="whiteTex blockC block" href="${pageContext.request.contextPath}/user/goRegister">立即注册</a></div>
		</form>
		</div>
		<!-- 验证码登录 end -->
		</div>

		<!-- 圆角矩形2 end -->
		</li>
		<li class="fl">
		<!-- 圆角矩形2 begin -->
		<div class="loginBox blockC fl">
		<!-- 验证码登录 begin -->
		<div class="MB17">
		<p class="whiteTex font20 textC">扫码登录</p>
		<div id="QR_code">
		<span class="QRCodeLogo">
		<img src="${pageContext.request.contextPath}/pic/web/QRCodeLogo@2x.png"/>
		</span>
		</div>
		<p class="whiteTex font16 textC">请扫描二维码登录</p>
		</div>
		<!-- 验证码登录 end -->
		</div>
		<!-- 圆角矩形2 end -->
		</li>
		</ul>
		</div>
		<!-- 轮播div end -->
		<!-- 轮播焦点 -->
		<ul class="focusBtnBox cursor positionR clearfix">
		<li class="selectli"><span></span></li>
		<li class="noSelectli"><span></span></li>
		<li class="noSelectli"><span></span></li>
		</ul>
		</div>
		<!-- 登录注册对话框 end -->
		</div>
		<!-- 首屏悬浮区域 end -->
		</div>

		<div class="loginFooter width100">
		<p class="font12 whiteTex lineHeight1_5x"><a href="http://www.lianbi.com.cn/" target="_blank"  class="whiteTex">上海联璧电子科技有限公司</a> | <a  class="whiteTex" href="${URL}/webabout/goWebsiteMap">网站地图</a></p>
		<p class="font12 whiteTex lineHeight1_5x">联璧金融版权所有 2015 <a href="http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action" rel="nofollow" class="whiteTex" target="_blank">沪ICP备15009293号-2</a></p>
		</div>
		</div>
		<%@  include file="../baiduStatistics.jsp"%>
		<input type="hidden" name="message" id="message" value="${message}" >
		</body>
		<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/jquery.qrcode.min.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/web/page/login.js?<%=request.getAttribute("version")%>"></script>

		</html>
