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
		<title>投资排行榜</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamRank.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../../header.jsp"%>
	</head>
	<body class="whiteBkg">
		<div class="width100 PB10 positionR">
			<div class="rewardBox positionA font12 whiteTex">活动奖励</div>
			<div class="threeRank width100">
				<ul class="rankList clearfix PT10P">
					<%--<li class="fl width33_3 font12">--%>
						<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/second.png?<%=request.getAttribute("version")%>" />--%>
						<%--<p class="rankNum whiteTex">第2名</p>--%>
						<%--<p class="teamName whiteTex">战队编号: 100003</p>--%>
						<%--<p class="teamMoney">197000.00元</p>--%>
					<%--</li>--%>
					<%--<li class="fl width33_3 font12">--%>
						<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/champion.png?<%=request.getAttribute("version")%>" />--%>
						<%--<p class="rankNum whiteTex">第1名</p>--%>
						<%--<p class="teamName whiteTex">战队编号: 100003</p>--%>
						<%--<p class="teamMoney">197000.00元</p>--%>
					<%--</li>--%>
					<%--<li class="fl width33_3 font12">--%>
						<%--<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/bronze.png?<%=request.getAttribute("version")%>" />--%>
						<%--<p class="rankNum whiteTex">第3名</p>--%>
						<%--<p class="teamName whiteTex">战队编号: 100003</p>--%>
						<%--<p class="teamMoney">197000.00元</p>--%>
					<%--</li>--%>
				</ul>
			</div>
			<ul class="moreRankList">
				<!-- <li>
					<img class="PT20P trophies" src="${pageContext.request.contextPath}/pic/weixin/activity/team/trophies.png?<%=request.getAttribute("version")%>" />
					<p class="PLR5P font12 grayTex heigh50">投资排行榜生成中，稍后查看...</p>
				</li> -->
				<%--<li class="clearfix font14 borderB heigh50 PLR5P textL">--%>
					<%--<span class="moreNum inlineB">第4名</span>--%>
					<%--<span class="moreName blackTex inlineB textC">战队编号: 100004</span>--%>
					<%--<span class="moreMoney inlineB textR">163300.00元</span>--%>
				<%--</li>--%>
	<%--<li class="clearfix font14 borderB heigh50 PLR5P textL">--%>
	<%--<span class="moreNum inlineB">第四名</span>--%>
	<%--<span class="moreName blackTex inlineB textC">战队编号: 100004</span>--%>
	<%--<span class="moreMoney inlineB textR">163300.00元</span>--%>
	<%--</li>--%>
	<%--<li class="clearfix font14 borderB heigh50 PLR5P textL">--%>
	<%--<span class="moreNum inlineB">第四名</span>--%>
	<%--<span class="moreName blackTex inlineB textC">战队编号: 100004</span>--%>
	<%--<span class="moreMoney inlineB textR">163300.00元</span>--%>
	<%--</li>--%>
	<%--<li class="clearfix font14 borderB heigh50 PLR5P textL">--%>
	<%--<span class="moreNum inlineB">第四名</span>--%>
	<%--<span class="moreName blackTex inlineB textC">战队编号: 100004</span>--%>
	<%--<span class="moreMoney inlineB textR">163300.00元</span>--%>
	<%--</li>--%>
	<%--<li class="clearfix font14 borderB heigh50 PLR5P textL">--%>
	<%--<span class="moreNum inlineB">第四名</span>--%>
	<%--<span class="moreName blackTex inlineB textC">战队编号: 100004</span>--%>
	<%--<span class="moreMoney inlineB textR">163300.00元</span>--%>
	<%--</li>--%>
	<%--<li class="clearfix font14 borderB heigh50 PLR5P textL">--%>
	<%--<span class="moreNum inlineB">第四名</span>--%>
	<%--<span class="moreName blackTex inlineB textC">战队编号: 100004</span>--%>
	<%--<span class="moreMoney inlineB textR">163300.00元</span>--%>
	<%--</li>--%>
			</ul>
			<p class="PLR5P textL font12 grayTex heigh50 tipsTex">提示: 活动期间只展示投资排行榜前20名战队投资情况</p>
		</div>
	<div id="exchangeListPaging" class="MT40 pagingMobile"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/team/teamRank.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../../baiduStatistics.jsp"%>
	</body>
</html>

