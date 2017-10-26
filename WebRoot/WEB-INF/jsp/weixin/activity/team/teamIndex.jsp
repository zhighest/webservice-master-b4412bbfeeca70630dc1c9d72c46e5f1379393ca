<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>组队投资 一起赚奖励</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamIndex.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../../header.jsp"%>
</head>
<body>
	<div class="width100 teamWrap PB10 positionR">
		<div class="rewardBox positionA font12 whiteTex">活动奖励</div>
		<div class="bannerTex positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/Themewords.png?<%=request.getAttribute("version")%>" />
		</div>
		<p class="whiteTex font12 teamTime positionR"></p>
		<div class="teamRule PLR3P">
			<p class="ruleTitle whiteTex positionR font14 textL"><img class="verMid" src="${pageContext.request.contextPath}/pic/weixin/activity/team/rule.png?<%=request.getAttribute("version")%>" />活/动/规/则</p>
			<div class='ruleTex font12 textL'>
				<div class="outHide scrollTex">
					<!-- <p class="MT7">1、战队创建后我是贷款就卡删掉就四大皆空萨德贷款就反馈说科技的反馈垃圾阿斯蒂芬看就看房间卡时代峻峰开啦四季坊的卡是放假阿斯蒂芬金坷垃时代峰峻的看风景开啦时</p>
					<p class="MT7">2、战队创建后我是贷款就卡删掉就四大皆空萨德贷款就反馈说科技的反馈垃圾阿斯蒂芬看就看房间卡时代峻峰开啦四季坊的卡</p>
					<p class="MT7">3、战队创建后我是贷款就卡删掉就四大皆空萨德贷款就反馈说科技的反馈垃圾阿斯蒂芬看就看房间卡时代峻峰开啦四季坊的卡是放假阿斯蒂芬金坷垃时代峰峻的看风景开啦</p>
					<p class="MT7">4、战队创建后我是贷款就卡删掉就四大皆空萨德贷款就反馈说科技的反馈垃圾阿斯蒂芬看就看房间卡时代峻峰开啦四季坊的卡是放假阿斯蒂芬金坷垃时</p>
	<p class="MT7">4、战队创建后我是贷款就卡删掉就四大皆空萨德贷款就反馈说科技的反馈垃圾阿斯蒂芬看就看房间卡时代峻峰开啦四季坊的卡是放假阿斯蒂芬金坷垃时</p>
	<p class="MT7">4、战队创建后我是贷款就卡删掉就四大皆空萨德贷款就反馈说科技的反馈垃圾阿斯蒂芬看就看房间卡时代峻峰开啦四季坊的卡是放假阿斯蒂芬金坷垃时</p> -->
				</div>
			</div>
		</div>
		<div class="btnBox clearfix font16">
			<a class="fl whiteTex radius5 setTeam none">创建战队</a>
			<a class="fl whiteTex radius5 lookTeam none">查看我的战队</a>
			<a class="fr inlineB whiteTex radius5 teamRank">战队投资排行榜</a>
		</div>
		<p class="font12 grayText1 MT20">此次活动由联璧金融发起<br/>若对活动规则存在疑问,请与联璧金融客服联系咨询</p>

		<!-- 弹框  -->
		<div class="bounceWrap positionF none">
			<div class="bounceCon positionF font14">
				<p class="blackTex">您确定创建战队吗？<br/>一旦创建，不可退出</p>
				<div class="bounceBtn whiteTex clearfix MT15">
					<span class="inlineB fl cancelBtn">取消</span>
					<span class="inlineB fr confirmBtn">确定</span>
				</div>
			</div>
		</div>
	</div>
	<input id="userId" name="userId" type="hidden" value="${userId}">
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
	<script src="${pageContext.request.contextPath}/js/weixin/page/team/teamIndex.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>

