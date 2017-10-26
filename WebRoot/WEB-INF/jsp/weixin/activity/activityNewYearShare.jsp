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
<!DOCTYPE HTML>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>金鸡报春,喜迎新年</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityNewYearShare.css?<%=request.getAttribute("version")%>" />
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

  <body class="positionF">
	<form id="loginForm" action="${pageContext.request.contextPath}/wxuser/goRegisterPassword" method="POST">
	<input id="appMobileNumber" name="appMobileNumber" type="hidden" value="<%=request.getParameter("mobile")%>">
	<input id="channel" name="channel" type="hidden" value="<%=request.getParameter("channel")%>">
	<div class="swiper-container height100P outHide">
		<div class="swiper-wrapper">
			<div class="swiper-slide outHide height100P slider2 positionR">
				<div class="wordTop outHide positionR">
					<img class="height100P" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/firstPageWrodTop.png" />
				</div>
				<div class="content1 goldColor font14">
					<p class="PTB2">你的好友<span class="whiteTex font18 userName"></span>分享了TA的小金库</p>
					<p class="PTB2">一起来看看~</p>
					<p class="PTB2"><span class="whiteTex font18 registerDay"></span> 天累计赚取了 <span class="whiteTex font18 moneyAmount"></span> 元</p>
					<p class="PTB2 userAssort none"></p>
					<p class="PTB2">赚翻啦</p>
				</div>
				<img class="positionA flowerImg flowerImg1" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/yun1.png" />
				<img class="positionA flowerImg flowerImg2" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/yun2.png" />
				<img class="positionA flowerImg flowerImg3" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower1.png" />
				<img class="positionA flowerImg flowerImg4" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower2.png" />
				<img class="positionA flowerImg flowerImg5" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower3.png" />
				<img class="positionA flowerImg flowerImg6" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower3.png" />
			</div>
			<div class="swiper-slide outHide height100P slider3 positionR">
				<div class="wordTop outHide positionR">
					<img class="height100P" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/firstPageWrodTop.png" />
				</div>
				<!--注册页面开始-->
				<div class="imgDiv outHide blockC clearfix gradient">
					<img class="blockC logoImg1 block" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/logo1.png" />
					<div class="content width90 blockC positionR">
						<%--<c:if test="${null==userId || ''==userId }">--%>
						<div id="step1" class="step1 positionA blockC clearfix width100">
							<div class="positionR width100">
								<input class="inputTex radius5 MB10 width100" type="tel" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="请输入手机号码" >
								<img class="positionA delete1 delete" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/delete.png" />
							</div>
							<div class="positionR width100">
								<input class="inputTex radius5 MB10 width100" type="password" name="password" id="password" value="" maxlength="16" placeholder="请设置登录密码">
								<img class="positionA delete1 delete" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/delete.png" />
							</div>
							<div class="positionR width100">
								<input class="inputTex radius5 MB10 width100" type="password" name="password" id="passwordRe" value="" maxlength="16" placeholder="请再次输入登录密码确认">
								<img class="positionA delete1 delete" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/delete.png" />
							</div>
							<!-- 图片验证码 -->
							<!--<div class="positionR MB10">
								<input type="text" class="inputTex width100" name="verifycode" placeholder="请输入图形验证码" id="verifycode" maxlength="4"/>
								<img id="imgcode" class="positionA" src="${pageContext.request.contextPath}/imgcode" height="35" style="width: 35%;" >
							</div>-->
							<div style="margin: 10px auto;" id="captcha_div"></div>
							
							<div class="positionR MB10">
								<input type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="请输入手机验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputTex width100" />
								<a class="width35 font12 radius5 positionA btnAct goldColor" id="gainCode">获取验证码</a>
							</div>
							<a class="width100 btnAct radius5 blockC block forbid heigh40 goldColor" id="loginSubmit" >立即注册</a>
							<div class="agreenment MT15 fl colorA9A9A9"><input class="agree" type="checkbox" checked /><font class="font14 act03Tex">我同意<a id="register" class="act03Tex colorA9A9A9">《用户注册服务协议》</a></font></div>
							<%--<div class="fr font14 MT15"><a class="rightSignIn" href="#">马上登录</a></div>--%>
						</div>
						<%--</c:if>--%>
						<%--<c:if test="${null!=userId || ''!=userId }">--%>
						<div id="step2" class="step2 positionA blockC width100">
							<a class="width100 btnAct bkg1 radius5 blockC block MB5P goldColor commonA heigh40" href="${pageContext.request.contextPath}/wxtrade/goMyAsset">我的账户</a>
							<a class="width100 btnAct radius5 blockC block goldColor commonA heigh40" href="${pageContext.request.contextPath}/wxabout/goIndex">立即投资</a>
						</div>
						<%--</c:if>--%>
					</div>
				</div>
				<!--注册页面结束-->
				<!-- 手机屏幕下载的半透明提示浮窗 begin -->
				<div class="btmAreaFix clearfix" id="btmAreaFix">
					<div class="leftArea">
						<span class="closed" id="closed"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/closed.png?<%=request.getAttribute("version")%>" width="20"></span>
						<span class="logo"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/logo.png?<%=request.getAttribute("version")%>" width="35"></span>
					</div>
					<div class="rightArea textL">
						<h4>联璧金融</h4>
						<p>使用APP客户端，体验更多惊喜！</p>
						<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" class="btmFixBtn">下载APP</a>
					</div>
				</div>
				<!-- 手机屏幕下载的半透明提示浮窗 end -->
			</div>
		</div>
	</div>
	</form>
	<img class="block slideTop positionA" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/slideTop.png" />
	<input id="userId" type="hidden" value="${userId}" />
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
	</script>
	<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/swiper-3.4.1.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityNewYearShare.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
