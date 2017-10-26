<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!-- 网站大屏 begin -->
<div class="whiteBkg positionR" id="header">
	<!-- logo begin -->
	<div class="logoDiv">
	  <a href="${URL}${pageContext.request.contextPath}/webindex/goIndex">
	    <img src="${pageContext.request.contextPath}/pic/web/logo.png" width="120px">
	  </a>
	</div>
	<!-- logo end -->
	<!-- 网站tab begin -->
	<div class="wrapperHeader blockC textL clearfix PT20 PB15">
		<ul class="MT10 fl cleafix" id="nav">
		  <li class="PL10 fl PR10 MR5"><a href="${URL}/webindex/goIndex" class="positionR ${pageIndex eq 1?'redTex currentA':'blackTex'}">首 页</a></li>
		  <li class="PL10 fl PR10 MR5"><a href="${URLS}/trade/goAccoutOverview" class="positionR ${pageIndex eq 2?'redTex currentA':'blackTex'}">个人中心</a></li>
		  <li class="PL10 fl PR10 MR5"><a href="${URL}/webhtml/activityList1.html" class="positionR ${pageIndex eq 3?'redTex currentA':'blackTex'}">最新活动</a></li>
		  <li class="PL10 fl PR10 MR5"><a href="${URL}/webhtml/mediaList1.html" class="positionR ${pageIndex eq 4?'redTex currentA':'blackTex'}">媒体报道</a></li>
		  <li class="PL10 fl PR10 MR5"><a href="${URL}/webhtml/classesList1.html" class="positionR ${pageIndex eq 8?'redTex currentA':'blackTex'}">金融课堂</a></li>
		  <li class="PL10 fl PR10 MR5"><a href="${URL}/webindex/goKcodeActivation" class="positionR ${pageIndex eq 5?'redTex currentA':'blackTex'}">帮助中心</a></li>
		  <li class="PL10 fl PR10 MR5"><a href="${URL}/webabout/aboutUs" class="positionR ${pageIndex eq 6?'redTex currentA':'blackTex'}">关于我们</a></li>
		  <li class="PL10 fl PR10"><a href="${URL}/webabout/goDownloadDetails" class="positionR ${pageIndex eq 7?'redTex currentA':'blackTex'}">联璧金融APP</a></li>
		</ul>
	</div>
	<!-- 网站tab end -->

	<!-- 登录 begin -->
	<!-- 未登录状态 -->
	<div class="fr loginBtn grayBkg" id="loginBtn">
		<a href="${pageContext.request.contextPath}/trade/goAccoutOverview" class="width100 height100">
			<img src="${pageContext.request.contextPath}/pic/web/header/defaultAvatar.png" width="36px">
		</a>
	</div>

	<!-- 已登录 -->
	<div class="fr loginBtn grayBkg" id="loginOut">
		<img src="${pageContext.request.contextPath}/pic/web/header/defaultAvatar.png" width="36px" id="userPhoto">
		<div class="personalCenterBtn whiteBkg positionA PT10 clearfix none">
			<ul class="positionR">
				<li><a href="${URLS}/trade/goAccoutOverview" class="block font14 positionR ${pageAccount eq 1?'redBkg whiteTex current':'blackTex'}" id="myAccountBtn">我的账户</a></li>
				<li><a href="${URLS}/webindex/goDemandProperty" class="block font14 ${pageAccount eq 2?'redBkg whiteTex current':'blackTex'}">零钱计划</a></li>
				<li><a href="${URLS}/webindex/goFixedProperty" class="block font14 ${pageAccount eq 3?'redBkg whiteTex':'blackTex'}">定期资产</a></li>
				<li><a href="${URLS}/wxpay/redirectPay?payFlag=kj" class="block font14 ${pageAccount eq 4?'redBkg whiteTex':'blackTex'}">充值</a></li>
				<li><a href="${URLS}/trade/goWithdraw" class="block font14 ${pageAccount eq 5?'redBkg whiteTex':'blackTex'}">提现</a></li>
				<li><a href="${URLS}/webindex/goInvestDetail" class="block font14 ${pageAccount eq 6?'redBkg whiteTex':'blackTex'}">交易记录</a></li>
				<li><a href="${URLS}/coupons/goMyInterest" class="block font14 ${pageAccount eq 7?'redBkg whiteTex':'blackTex'}">加息券</a></li>
				<li><a href="${URLS}/trade/voucher" class="block font14 ${pageAccount eq 8?'redBkg whiteTex':'blackTex'}">代金券</a></li>
				<li id="loginOutBtn"><a href="#" class="block blackTex font14">退出</a></li>
			</ul>
		</div>	
	</div>
	<!-- 搜索和登录 end -->

</div>
<!-- 网站大屏 end -->

<!-- 网站小屏768 begin -->
<div class="clearfix none" id="responsiveNav">
		<div class="fl navbarLeft">
			<a href="${URL}${pageContext.request.contextPath}/webindex/goIndex">
			    <img src="${pageContext.request.contextPath}/pic/web/logo.png" width="100px">
			</a>
	    </div>
		<div class="fr">
			<a class="navbarRight" id="navbarRight">
				<div class="block" id="navbarIcon">
					<span class="sr-only">切换导航</span>
					<span class="icon-bar firstBar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</div>
				<span class="navBarClosed none" id="navBarClosed">
					<img src="${pageContext.request.contextPath}/pic/web/navBarClosed.png">
				</span>
			</a>	
		</div>

	<ul class="responsiveBar" id="responsiveBar">    
		<li class="${pageIndex eq 1?'current':''}"><a href="${URL}/webindex/goIndex" class="positionR block whiteTex">首 页</a></li>
		<li class="${pageIndex eq 3?'current':''}"><a href="${URL}/webhtml/activityList1.html" class="positionR block whiteTex">最新活动</a></li>
		<li class="${pageIndex eq 4?'current':''}"><a href="${URL}/webhtml/mediaList1.html" class="positionR block whiteTex">媒体报道</a></li>
		<li class="${pageIndex eq 8?'current':''}"><a href="${URL}/webhtml/classesList1.html" class="positionR block whiteTex">金融课堂</a></li>
        <li class="${pageIndex eq 5?'current':''}"><a href="${URL}/webindex/goKcodeActivation" class="positionR block whiteTex">帮助中心</a></li> 
		<li class="${pageIndex eq 6?'current':''}"><a href="${URL}/webabout/aboutUs" class="positionR block whiteTex">关于我们</a></li>
		<li class="goDownloadDetails ${pageIndex eq 7?'current':''}"><a href="${URL}/webabout/goDownloadDetails" class="positionR block whiteTex">联璧金融APP</a></li>	
	</ul>
</div>
<!-- 网站小屏768 end -->
<input id="webIsLogIn" name="webIsLogIn" type="hidden" value="${webIsLogIn}">
<input id="loginMobile" name="loginMobile" type="hidden" value="${loginMobile}">
<input id="hasHeadFoot" name="hasHeadFoot" type="hidden" value="${hasHeadFoot}">
<input id="channel" name="channel" type="hidden" value="<%=request.getParameter("channel")%>">
<script src="${pageContext.request.contextPath}/js/web/integration.js?<%=request.getAttribute("version")%>"></script>
<script src="${pageContext.request.contextPath}/js/web/page/header.js"></script>