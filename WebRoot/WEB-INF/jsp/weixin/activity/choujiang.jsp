<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE HTML>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no" />
	<!--<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="x5-orientation"content="portrait">-->

	<title>幸运抽奖</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/layout.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/reset.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/luckyware.css?<%=request.getAttribute("version")%>" />

	<%@  include file="../header.jsp"%>
</head>

<body>

	<div class="header">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/header.png?<%=request.getAttribute("version")%>" width="100%">
		<div class="times blockC purpleBg1 clearfix">
			<img class="giftIcon fl" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/giftBox.png?<%=request.getAttribute("version")%>" width="100%">
			<div class="textContent fl">
				<em class="purpleText1 font14">您还有</em>
				<strong id="avaliableTimes" class="font18 avaliableTimes">0</strong>
				<em class="purpleText1 font14">次抽奖机会</em>
			</div>
		</div>
	</div>
	<div class="content purpleBg1">
		<div class="prizeContent purpleBg1">
			<ul class="prizeList clearfix" id="prizeList">
				<li class="prizeItem MR2 fl">
					<div class="topItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>" />
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
					<div class="bottomItem itemBg"> 
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>" />
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
				</li>
				<li class="prizeItem MR2 fl">
					<div class="topItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
					<div class="bottomItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
				</li>
				<li class="prizeItem MR2 fl">
					<div class="topItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
					<div class="bottomItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
				</li>
				<li class="prizeItem MR2 fl">
					<div class="topItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
					<div class="bottomItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
				</li>
				<li class="prizeItem MR2 fl">
					<div class="topItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
					<div class="bottomItem itemBg">
						<div class="prizeItemTop">
							<img width="100%" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/lianBiIcon.png?<%=request.getAttribute("version")%>"/>
						</div>
						<p class="prizeItemBot textC font12">联璧奖项</p>
					</div>
				</li>
			</ul>
		</div>

	</div>
	<div class="giftBox purpleBg2 positionR">
		<div class="swiperBox positionA" id="swiperBox"></div>
		<div class="clickBox positionA" id="clickBox"></div>
		<img class="hander positionA" id="handerAnimate" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/handerAnimate.gif?<%=request.getAttribute("version")%>" width="100%">
		<img class="giftPic positionA" id="giftPicAnimate" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/giftBoxAnimate.gif?<%=request.getAttribute("version")%>" width="60%">
		<img class="giftPicStatic positionA none" id="giftPicStatic" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/giftBoxStatic.png?<%=request.getAttribute("version")%>" height="100%">
	</div>
	<div class="selectWay clearfix purpleBg2" id="selectWayBtn1">
		<div class="selectWay1 fl NEMT3 width50 positionR">
			<p class="text font12 whiteText positionA">每次抽奖消耗1次机会</p>
			<img id="selectWay1" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/1timesActive.png?<%=request.getAttribute("version")%>" width="100%">
		</div>
		<div class="selectWay2 fl width50 positionR" id="selectWayBtn2">
			<p class="text font12 whiteText positionA">每次抽奖消耗10次机会</p> 
			<img id="selectWay2" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/10times.png?<%=request.getAttribute("version")%>" width="100%">
		</div>
	</div>
	<div class="activityDes purpleBg2 positionR">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/light.gif?<%=request.getAttribute("version")%>" width="100%">
		<div class="desTitle positionA">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/activeDesc.png?<%=request.getAttribute("version")%>" width="100%">
		</div>
		<div class="textArea positionA font12" id="activityDesc">
			<p class="MB14 lineHeight2_5x">1.零钱收益每满10元可以获得一次抽奖机会<br>2.点击抽奖就有机会抽中加息券或者代金券<br>3.抽中的加息券和代金券都可以在相应的理财产品中使用</p>
		</div>
	</div>
	<div class="notice purpleBg2">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/whiteLogo.png?<%=request.getAttribute("version")%>">
		<p class="noticeTop">一本次活动最终解释权归“联璧金融”所有一</p>
		<p class="noticeBot">*本活动与苹果公司无关</p>
	</div>
	<div class="mask none" id="mask"></div>
	<div class="oneTimesBox none" id="oneTimesBox">
		<div class="oneTimesHeader positionR">
			<img class="positionA closeIcon" id="oneTimesCloseBtn" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/closeIcon.png?<%=request.getAttribute("version")%>" width="100%">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/oneTimesHeader.png?<%=request.getAttribute("version")%>" width="100%">
		</div>
		<div class="oneTimesContent PTB33">
			<p class="redText font18 textC LH75">恭喜您！</p>
			<div class="prizeIfo clearfix font22 whiteText textC">
				<h3>获得<em id="awardName"></em></h3>
			</div>
		</div>
		<div class="oneTimesFooter">
			<div class="oneTimesBtn textC" id="oneTimesBtn">确定</div>
		</div>
	</div>
	<div class="tenTimesBox none" id="tenTimesBox">
		<div class="oneTimesHeader positionR">
			<img class="positionA closeIcon" id="tenTimesCloseBtn" src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/closeIcon.png?<%=request.getAttribute("version")%>" width="100%">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/choujiang/tenTimesHeader.png?<%=request.getAttribute("version")%>" width="100%">
		</div>
		<div class="tenTimesContent">
			<ul class="prizeList" id="tenTimesPrizeList">
				<!--<li class="prizeItem clearfix">
					<span class="prizeItemCategory fl textL">积分券</span>
					<em class="prizeItemNo positionR fl">1<div class="line positionA"></div></em>
					<span class="prizeItemIfo fr textR">联璧奖项</span>
				</li>-->
			</ul>
		</div>
		<div class="oneTimesFooter">
			<div class="oneTimesBtn textC"  id="tenTimesBtn">确定</div>
		</div>
	</div>
	<!-- info end -->
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
	<script src="${pageContext.request.contextPath}/js/weixin/page/luckyware.js?<%=request.getAttribute("version")%>" "></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>

</html>