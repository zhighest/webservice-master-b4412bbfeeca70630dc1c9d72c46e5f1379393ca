<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>如何获取K码券</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/getVouchers.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%> 
</head>
<body>
	<div class="whiteBkg MT10 textL PTB10 PLR15 font12">
		<!-- 使用说明 begin -->
		<div class="positionR MT30 firstPart">
			<div class="whiteBkg positionA textC titleP">
				<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/kvUser.png" class="blockC" width="140px">
			</div>
			<p class="blackTex lineHeight2x">使用K码券可获得一次指定产品的激活资格。</p>
		</div>
		<!-- 使用说明 end -->
	</div>


	<div class="whiteBkg textL PTB20 PLR15 font12 MT10">
		<!-- 获取方式 begin -->
		<div class="positionR MT30 width60 blockC heigh1">
			<div class="whiteBkg positionA textC titleP">
				<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/kvHow.png" class="blockC" width="140px">
			</div>
		</div>

		<div class="MT20 positionR clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/firstImg.png" class="numImg fl verMid" width="45px">
			<p class="blackTex font14 textIn24 verMid lineH45P">可在<span class="redTex">联璧积分商城</span>进行兑换“K码券”</p>
		</div>
		

		<div class="width40 blockC MT10 whiteTex cursor PTB10 redBkg textC radius20 MT20 font16 none" id="exchange">我要兑换</div>
	</div>

	<!-- <div class="whiteBkg MT10 MB10 textL PTB20 PLR15 font12">

		<div class="positionR clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/secondImg.png" class="numImg fl verMid" width="45px">
			<p class="blackTex font14 textIn24 verMid lineH45P">可在<span class="redTex">智仟汇商城</span>进行购买“K码券”</p>
		</div>
		<div class="width40 blockC MT10 whiteTex cursor PTB10 redBkg textC radius20 MT20 font16 none" id="wantts">我要购买</div>
	</div> -->
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/page/vouchers.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>