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
	<title>奖励说明</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamReward.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../../header.jsp"%>
</head>
<body>
<div class="width100 teamWrap PB10 positionR">
	<div class="bannerTex positionR">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/reward-title.png?<%=request.getAttribute("version")%>" />
	</div>
	<div class="teamRuleOne PLR3P">
		<p class="ruleTitle whiteTex positionR font14">TOP3战队奖</p>
		<div class='reward rewardOne font12 textL'>
			<%--<p class="MT7">整理投资额排名前三的战队即可获赠联璧金融送出的豪华大礼。</p>--%>
			<%--<p class="MT7">第一名战队奖励1万元, 第二名奖励8000元, 第三名奖励5000元现金；</p>--%>
			<%--<p class="MT7">站内成员按投资额占比进行奖励分配，计算公式（个人投资额/战队总投资额）*奖金，四舍五入进行取整发放到个人账户。</p>--%>
		</div>
	</div>
	<div class="teamRule PLR3P">
		<p class="ruleTitle whiteTex positionR font14">优秀队长奖</p>
		<div class='reward rewardTwo font12 textL'>
			<%--<p class="MT7">第4-20名上榜战队队长可获得50元优秀队长奖。</p>--%>
		</div>
	</div>

	<div class="teamRule PLR3P">
		<p class="ruleTitle whiteTex positionR font14">投资明星奖</p>
		<div class='reward rewardThree font12 textL'>
			<%--<p class="MT7">第4-20名上榜战队队长可获得50元优秀队长奖第4-20名上榜战队队长可获得50元优秀队长奖。</p>--%>
		</div>
	</div>

	<div class="teamRule PLR3P">
		<p class="ruleTitle whiteTex positionR font14">参与奖</p>
		<div class='reward rewardFour font12 textL'>
			<%--<p class="MT7">第4-20名上榜战队队长可获得50元优秀队长奖第4-20名上榜战队队长可获得50元优秀队长奖第4-20名上榜战队队长可获得50元优秀队长奖。</p>--%>
		</div>
	</div>
	<p class="text font12 whiteTex textL" id="text"><!--本活动与邀请奖励可同享--></p>
	<p class="font12 grayText1 MT20">此次活动由联璧金融发起<br/>若对活动规则存在疑问,请与联璧金融客服联系咨询</p>
</div>

	<script src="${pageContext.request.contextPath}/js/weixin/page/team/teamReward.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>

