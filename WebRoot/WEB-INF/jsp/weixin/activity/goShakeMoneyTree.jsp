	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ page contentType="text/html;charset=UTF-8"%>
		<%@ include file="../../common.jsp"%>
		<!DOCTYPE HTML>
		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>新年迎好运 红包送不停</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/goShakeMoneyTree.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header.jsp"%>
		</head>
		<body>
		     <div class="main positionR">
				  <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/moneyTreeBkg1.jpg" width="100%" class="">
		          <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shakeText.png" width="60%" class="shakeText" id="shakeText">
		            <div class="positionA giftWrap width100">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/coin.png" width="6%" class="coin1">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/coin.png" width="6%" class="coin2">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/coin.png" width="6%" class="coin3">
						   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/coin.png" width="6%" class="coin4">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/ingot.png" width="10%" class="ingot1">
						   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/ingot.png" width="10%" class="ingot2">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/ingot.png" width="10%" class="ingot3">
						   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/ingot.png" width="10%" class="ingot4">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/enveloppe.png" width="8%" class="enveloppe1">
						   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/enveloppe.png" width="8%" class="enveloppe2">
						   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/enveloppe.png" width="8%" class="enveloppe3">
						   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/enveloppe.png" width="8%" class="enveloppe4">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/enveloppe.png" width="8%" class="enveloppe5">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/moneyBag.png" width="8%" class="moneyBag1">
	                       <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/moneyBag.png" width="8%" class="moneyBag2">
		                   <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/moneyBag.png" width="8%" class="moneyBag3">
		            </div>
		            <div class="treeBtn positionA tabBkgNone"></div>
		     </div>
		     <div class="invite positionR">
		           <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/moneyTreeBkg2.jpg" width="100%" class="">
		           <p class="whiteTex  font18 positionA numberWrap lineHeight100">还能摇<span id="numberWrap">0</span>次哦~</p>
		           <a class="positionA width100   block  inviteFriendsBtn" id="inviteFriendsBtn">
	                	<img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/inviteFriendsBtn.png" width="80%" class="">
		           </a>
		     </div>
		<!-- 中奖纪录 -->
		<div class="recordWrap lineHeight2x none">
				<div class="positionR">
		             <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/record.png" width="60%" class="">
				</div>
				<div class="record width85 blockC whiteTex  " id="record">
					<div class="outHide recordTitle strong "><span class="fl width50 borderR boxSizing ">中奖时间</span><span class="fl width50 ">中奖奖项</span></div>
					<div id="recordUlCut" class="outHide positionR">
						<ul class="recordUl font12 positionA width100" id="recordUl">
						</ul>
					</div>
				</div>
				<div class="changePages">

				</div>
		</div>
		<div class="bottom"><img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/bottom.png" width="100%" class=""></div>
		<!-- 弹出层 -->
		<div class="cover positionF scale0">
		    <div class="textSite none">
		        <img src="${pageContext.request.contextPath}/pic/weixin/shareTex.png?" height="120">
		    </div>
			<div class="messageBoxWrap positionA textC none">
				<div class="messageBox radius5 PTB5P">
					<div class="img positionR"><img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/label.png" width="80%" class="PT5 giftPic positionA"></div>
					<p class="font20 MT5P MB10 PLR10 lineHeight1_5x">恭喜您获得了<span id="giftText">财运签啦！<span class="block">财源广进，招财进宝</span></span></p>
				</div>
		        <a class="messageBoxBtn width100 whiteTex PTB5  redBkg radius5 MT10 block" id="messageBoxBtn"> 我知道了</a>

		        </div>
		    <div>
		    </div>
		    <div class="reminder positionA radius5 none">
		        <img src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shakePic.png" width="40%" class="">
		        <p class="PTB15 grayTex">摇一摇领奖品</p>
		        <a class="messageBoxBtn width100 whiteTex PTB5  redBkg radius5 MT10 block" id="firstEntrence"> 我知道了</a>
		    </div>
		</div>
		<div class="none">
		<audio  controls="controls" id="shakeAudio"   class="none" preload>
			<source src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shake.mp3"/>
		    <source src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shake.ogg"/>
		    <source src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shake.wav"/>
		</audio>
		<audio  controls="controls" id="shakeAudio2"  class="none" preload>
		    <source src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shakeOver.mp3"/>
		    <source src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shakeOver.ogg"/>
			<source src="${pageContext.request.contextPath}/pic/weixin/activity/shakeMoneyTree/goShakeMoneyTree/shakeOver.wav"/>
		</audio>
		</div>
		<%@  include file="../baiduStatistics.jsp"%>
		<input id="goUrl" name="goUrl" type="hidden" value="${goUrl}">
		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
		<script>
			var channel=$("#channel").val();
			var url=window.location.href;
			var protocal=window.location.protocol;
			if(protocal=="http:"){
				if(channel!="LBIOS"&&channel!="LBAndroid"){
						var newurl=url.replace('http','https');
						window.location.href=newurl;
				}else{

			}
			}else{

			}
		</script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/goShakeMoneyTree.js?<%=request.getAttribute("version")%>"></script>
		</body>
		</html>