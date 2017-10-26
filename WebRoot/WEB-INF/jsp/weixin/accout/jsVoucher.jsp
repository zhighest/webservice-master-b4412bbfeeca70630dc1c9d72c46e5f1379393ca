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

	<title>如何获取加速券</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/getVouchers.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%> 
</head>
<body>
	<div class="whiteBkg MT10 textL PTB10 PLR15 font14">
		<!-- 使用说明 begin -->
		<div class="positionR MT30 firstPart">
			<div class="whiteBkg positionA textC titleP">
				<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/jsUser.png" class="blockC" width="140px">
			</div>
			<p class="blackTex lineHeight2x">使用加速券可缩短指定产品的礼包兑换时间，每张加速券仅限使用一次，一个礼包可使用多张加速券。</p>
		</div>
		<!-- 使用说明 end -->
	</div>
	<div class="whiteBkg MT10 textL PTB20 PLR15 font14">
		<!-- 获取方式 begin -->
		<div class="positionR MT30 width80 blockC heigh1">
			<div class="whiteBkg positionA textC titleP">
				<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/jsHow.png" class="blockC" width="140px">
			</div>
		</div>

		<div class="MT20">
			<div class="positionR clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/firstImg.png" class="numImg fl verMid" width="45px">
				<p class="blackTex font14 textIn24 verMid lineHeight1_5x">下载并注册<span class="redTex">“智仟汇”</span>即可获得K3加速券1张 (仅限一次免费获取机会)</p>
			</div>
			<ul class="width100 positionR clearfix MT10">
			<li class="width80 blockC positionR clearfix">
					<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/appTwo.png" class=" fl verMid" width="80px">
					<p class="fl ML10 lineH80P">智仟汇</p>
					<div class="openBtn fr blockC redTex cursor redBorder textC" id="ZQH">
						打开
					</div>
				</li>
				<!-- <li class="width80 blockC positionR clearfix MT10">
					<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/appOne.png" class=" fl verMid" width="80px">
					<p class="fl ML10 lineH80P">习之铃铛</p>
					<div class="openBtn fr blockC redTex cursor redBorder textC" id="LD">
						打开
					</div>
				</li> -->
				
			</ul>
		</div>
		<!-- 获取方式 end -->
	</div>

	<div class="whiteBkg MT10 textL PTB20 PLR15 font14">
		<div class="positionR clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/secondImg.png" class="numImg fl verMid" width="45px">
			<p class="blackTex font14 textIn24 verMid lineH45P">可在<span class="redTex">联璧积分商城</span>进行兑换“加速券”</p>
		</div>
		<div class="width40 blockC MT10 whiteTex cursor PTB10 redBkg textC radius20 MT20 font16 none" id="exchange">我要兑换</div>
	</div>

	<!-- <div class="whiteBkg MT10 MB10 textL PTB20 PLR15 font14">
		<div class="positionR clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/K3activity/thirdImg.png" class="numImg fl verMid" width="45px">
			<p class="blackTex font14 textIn24 verMid lineH45P">可在<span class="redTex">智仟汇商城</span>进行购买“加速券”</p>
		</div>
		<div class="width40 blockC MT10 whiteTex cursor PTB10 redBkg textC radius20 MT20 font16 none" id="wantjs">我要购买</div>
	</div> -->
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/page/vouchers.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
