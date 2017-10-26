<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>分享加息说明</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activity_20151202Share.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    <script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>  
</head>
<body class="acitivityBkg">
	<div class="wrapper">
			<!--
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/img_01.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/img_02.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/img_04.jpg?<%=request.getAttribute("version")%>">-->
				
				<!-- 分享按钮-->
				<!--<a id="weixin" class="positionA btnArea width60 blockC block" style="display: none;">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/btn.png?<%=request.getAttribute("version")%>">
				</a>
				<label id="lianbiIos" style="display: none;">
					<input type="button" value="" style="display: none;" id="iosBtn"/>
					<div class="positionA btnArea width60 blockC block">
						<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/btn.png?<%=request.getAttribute("version")%>">
					</div>
				</label>
				<a id="lianbiAndroid" class="positionA btnArea width60 blockC block" style="display: none;">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/btn.png?<%=request.getAttribute("version")%>">
				</a>
				
			</div>
			<div class="imgDiv clearfix">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/img_05.jpg?<%=request.getAttribute("version")%>">
			</div>
		</div>
		<div class="popup pop2 textR" id="popup2" style="display: none;">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/weixinAlert.png" alt="" width="100%">
		</div>
		<div class="popup" id="popup4" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/succeed.png" alt="" width="55">
				<h4 class="font20 MT5">分享成功</h4>
				<a id="popBtn4" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	-->

		<div class="width80 ML10P MT30">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151202Share/cannotImg.png" width="60" class="MLRA block MB30">
			
			<h4 class="textC font16 lineHeight2x  blackTex">功能暂停使用</h4>
			<p class="textC font12 lineHeight1_5x blackTex">根据微信相关规则要求，平台暂停分享加息活动.
			</p>
			<p class="textC font12 lineHeight1_5x blackTex">您可以尝试平台最新的签到功能进行加息!</p>
	<div id="btnBox" class="btnBox none"><a class="block textC width100 whiteTex redBkg radius5 MT20">立即签到</a></div>
			
		</div>

	</div>
	<input type="hidden" name="shareMobileDateJM" id="shareMobileDateJM" value="${shareMobileDateJM}">
	<input type="hidden" name="mobileWXFlag" id="mobileWXFlag" value="${mobileWXFlag}">
	<input type="hidden" name="goUrl" id="goUrl" value="${goUrl}">
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("sharePhone")%>" >
    <input type="hidden" name="signFlag" id="signFlag" value="<%=request.getParameter("from")%>" >
	<script src="${pageContext.request.contextPath}/js/weixin/page/activity_20151202Share.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

