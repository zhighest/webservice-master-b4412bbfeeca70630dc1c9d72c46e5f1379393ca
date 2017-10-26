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
<title>战队详情</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamDetail.css?<%=request.getAttribute("version")%>" />
<%@  include file="../../header.jsp"%>
</head>
<body>
   	<div class="bannerWrap width100 positionR">
		<div class="rewardBox positionA font12 whiteTex">活动奖励</div>
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/teamDetailbanner.png?<%=request.getAttribute("version")%>" />
	</div>
	<div class="myTeam clearfix blackTex heigh37 borderB PLR5P whiteBkg">
		<span class="fl title font14">我的战队</span>
		<span class="fr redTex font12 nowRank">当前战队排名： <em class="teamRank"></em></span>
	</div>
	<div class="teamMans PTB10 PB20 whiteBkg">
		<ul class="teamMembers clearfix">
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">陈 *</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
			<%--<li class="fl MT10">--%>
				<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<span class="grayTex font12 inlineB">0713</span>--%>
			<%--</li>--%>
		</ul>
		<a class="inlineB strongTeam whiteTex radius5 heigh37 redBkg font14">壮大战队</a>
	</div>
	<div class="myTeam clearfix blackTex MT10 heigh37 borderB PLR5P whiteBkg">
		<span class="fl title strongIcon font14">队员战斗力</span>
		<span class="fr redTex font12 moreTeam">战队投资排行榜</span>
	</div>
	<div class="teamRankBox whiteBkg">
		<ul class="rankList PB20">
			<%--<li class="clearfix PLR5P PT20">--%>
				<%--<img class="fl figureImg" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
				<%--<div class="fl PL15 teamInfo textL positionR">--%>
					<%--<p class="font14 PTB2">赵*</p>--%>
					<%--<img class="levelStar positionA" src="${pageContext.request.contextPath}/pic/weixin/activity/team/fiveStar.png?<%=request.getAttribute("version")%>" />--%>
				<%--</div>--%>
				<%--<img class="inRank fr PTB3" src="${pageContext.request.contextPath}/pic/weixin/activity/team/one.png?<%=request.getAttribute("version")%>" />--%>
			<%--</li>--%>
            <%--<li class="clearfix PLR5P PT20">--%>
                <%--<img class="fl figureImg" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
                <%--<div class="fl PL15 teamInfo textL positionR">--%>
                    <%--<p class="font14 PTB2">0713</p>--%>
                    <%--<img class="levelStar positionA" src="${pageContext.request.contextPath}/pic/weixin/activity/team/fiveStar.png?<%=request.getAttribute("version")%>" />--%>
                <%--</div>--%>
                <%--<img class="inRank fr PTB3" src="${pageContext.request.contextPath}/pic/weixin/activity/team/one.png?<%=request.getAttribute("version")%>" />--%>
            <%--</li>--%>
            <%--<li class="clearfix PLR5P PT20">--%>
                <%--<img class="fl figureImg" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>" />--%>
                <%--<div class="fl PL15 teamInfo textL positionR">--%>
                    <%--<p class="font14 PTB2">0713</p>--%>
                    <%--<img class="levelStar positionA" src="${pageContext.request.contextPath}/pic/weixin/activity/team/fiveStar.png?<%=request.getAttribute("version")%>" />--%>
                <%--</div>--%>
                <%--<p class="font14 redTex fr PTB5P">第三名</p>--%>
            <%--</li>--%>
		</ul>
	</div>
	<div class="sharePopup" style="display: none;">
		<div class="textSite">
			<img src="${pageContext.request.contextPath}/pic/weixin/shareTex.png?<%=request.getAttribute("version")%>" height="120">
		</div>
	</div>
	<input id="userId" name="userId" type="hidden" value="<%=request.getParameter("userId")%>">
	<input id="teamId" name="teamId" type="hidden" value="<%=request.getParameter("teamId")%>">
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >

	<script src="${pageContext.request.contextPath}/js/weixin/page/team/teamDetail.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>

