<%--
  Created by IntelliJ IDEA.
  User: R.hao
  Date: 2017/8/29
  Time: 14:32:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@  include file="../header.jsp"%>
<!--引入分享-->
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/reset.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/anniversary/layoutForAnniversary.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/weixin/anniversary/anniversaryShared.css?<%=request.getAttribute("version")%>" />
	</head>

	<body>
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<!--第一栏-->
					<div class="page">
						<div class="banner height30 width100">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/banner.jpg" class="imgBanner">
						</div>
						<div class="height70 positionR">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj.png" class="imgBanner">
						</div>
						<div class="width90 blockC positionA top30 left5 height55P whiteBkg borderRadius5500">
							<p class="MT20 colorfa324c">你的好友给你发了80元红包</p>
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bonus.png" class="width100p MT20 ">
							<br>
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/button5.png" width="200" class="MT40 PB20 rightNowUse">
						</div>

						<!--动画-->
						<div class="animates">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashTwo">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashThree">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat1.png" class="ribbonOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat3.png" class="ribbonTwo">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/balloon.png">
						</div>

					</div>

				</div>

				<div class="swiper-slide">
					<div class="page">
						<div class='downloadContent outHide '>
							<!--第一个图片栏-->
							<div class="column height30">
								<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bannerR.jpg" class="imgBanner" id="imgRiOrLogin" style="height:100%;">
							</div>
							<!--第二个图片栏-->
							<div class='bannerWrapper ML-1 column SecondConlumn backgroundImg' style="height:70%;">
								<!--未注册-->
									<input id="appMobileNumber" name="appMobileNumber" type="hidden" value="<%=request.getParameter("mobile")%>">
									<input id="channel" name="channel" type="hidden" value="<%=request.getParameter("channel")%>">
									<div class='bannerContent' id="bannerContent">
										<div class='contentRight  font14 bannerContentDiv' id="bannerContentDiv">
											<div class="whiteBkg radius30 MB12 phoneNum bannerContentDiv clearfix">
												<input type="tel" id="phoneNum" name="phoneNum" maxlength="11" placeholder="请输入手机号" class="fl ML25" isKey="1"></input>
											</div>

											<div class="MB12 positionR">
												<div class="whiteBkg radius30  outHide captcha positionR bannerContentDiv clearfix" id="width60">
													<input type="text" maxlength="4" name="checkCode" placeholder="请输入验证码" class="positionA" id="checkCode"></input>

												</div>
												<span id="Countdown" class=" verification positionA textC radius30 colorG backgroundEfb843" style="" >
								获取验证码
                 			 </span>
											</div>
											<div class="whiteBkg radius30 MB12 bannerContentDiv clearfix">
												<input type="password" name="password" id="passWord" maxlength="16" placeholder="请设置登录密码" class="fl ML25" style=""></input>
											</div>
											<div class="whiteBkg radius30 MB12 outHide verifycode bannerContentDiv clearfix">
												<input type="password" name="verifycode" maxlength="6" placeholder="请设置交易密码（6位数字）" oncopy="return false;" onpaste="return false" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" class="fl ML25 "   id="verifycode"></input>
											</div>
											<!--注册-->
											<img class="MT20" id="loginSubmit" src="${pageContext.request.contextPath}/pic/weixin/anniversary/rightNowRigister.png" />

											<div class="agreenment" style="text-align:left;">
												<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/choice.png" id="choice" style="width:20px;display:inline-block;vertical-align: middle" class="none">
												<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/unchoice.png" id="unchoice" style="width:20px;display:none;vertical-align: middle;">
												<a id="registration" class="pointer">我同意《用户注册服务协议》
												</a>
											</div>
										</div>
									</div>
								<!--注册-->
								<div class="rigistered none">
									<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/lianbilogo.png" class="PT30">
									<a href="${pageContext.request.contextPath}/wxtrade/goMyAsset?from=LBWX">
										<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/button2.png" / class="MT30">
									</a>
									<a href="${pageContext.request.contextPath}/wxabout/goIndex">
										<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/button3.png" / class="MT20 PB30">
									</a>
								</div>

							</div>

						</div>
					</div>

				</div>

				<div class="swiper-slide">
					<div class="page">
						<!--第四个图片栏-->
						<div class="column">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj3.png" class="swiper-slide-img">
						</div>
					</div>
				</div>

				<!-- 如果需要分页器 -->
				<div class="swiper-pagination"></div>

				<!-- 如果需要导航按钮 -->
				<div class="swiper-button-prev"></div>
				<div class="swiper-button-next"></div>

				<!-- 如果需要滚动条 -->
				<div class="swiper-scrollbar"></div>

			</div>

		</div>
		<!--悬浮-->
		<span class="changePage">
			<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/icon_arrow.png"/ class="imgBanner">
		</span>
		<!--底部-->
		<div class="DownLoadApp">
			<div class="overHidden">
				<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/close.png" / class="fl width20P close">
				<img class="fl" src="${pageContext.request.contextPath}/pic/weixin/anniversary/logo.png">
				<div class="fl height90P ML5P MT2point5 positionR">
					<p class="textWhite font16">联璧金融APP&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
					<p class="textWhite font12 opacity7 positionA bottom0">使用APP客户端，体验更多惊喜</p>
				</div>
				<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.duodianjr.finance" class="RightNowDownLoad">下载app</a>
			</div>
		</div>
		<input id="userId" name="userId" type="hidden" value="${userId}">
		<input type="hidden" name="mobileWX" id="mobileWX" value="<%=request.getParameter("mobile")%>">
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">

	</body>
	<script src="${pageContext.request.contextPath}/js/weixin/page/swiper-3.4.1.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/anniversary/anniversaryShared.js?<%=request.getAttribute("version")%>" type="text/javascript" charset="utf-8"></script>

</html>