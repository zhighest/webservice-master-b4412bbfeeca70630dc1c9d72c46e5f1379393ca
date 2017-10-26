<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>报名成功</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/running/applySuccess.css?<%=request.getAttribute("version")%>" />
	<script src="${pageContext.request.contextPath}/js/weixin/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
<body class="positionR whiteBkg">
	<div class="PT50"><img src="${pageContext.request.contextPath}/pic/running/successImg.png" width="20%" alt="报名成功" class="blockC"></div>
	<p class="font26 width40 blockC MT20 blueTxt positionR heigh40 PL20 PR20 textC strong">报名成功</p>
	<p class="grayTex textC font16">恭喜您,报名成功!</p>
	<ul class="width85 blockC grayTex TextL infoList font14 MT20">
		<li class="PTB10 PLR5">
			报名编号：<span class="blackTex" id="marathonId"></span>
		</li>
		<li class="PTB10 PLR5">
			姓名：<span class="blackTex" id="marathonName"></span>
		</li>
		<li class="PTB10 PLR5">
			身份证号：<span class="blackTex" id="marathonIdcard"></span>
		</li>
		<li class="PTB10 PLR5">
			手机号：<span class="blackTex" id="marathonMobile"></span>
		</li>
		<li class="PTB10 PLR5">
			衣服尺寸：<span class="blackTex" id="marathonSize"></span>
		</li>
		<li class="PTB10 PLR5">
			<p class="PTB5">活动物料领取地点：</p>
			<p class="PTB5">上海猫之旅户外俱乐部，谷阳北路1470弄1号262室</p>
			<p class="PTB5"> 时间：12月18日 9:30-20:00</p>
			<p class="PTB5">联系电话：021-57820009</p>
		</li>
	</ul>
	<div class="foot MT10P"><a href="http://www.lianbijr.com/activity/activity20151212"><img src="${pageContext.request.contextPath}/pic/running/footImg.jpg" width="100%" alt=""></a></div>
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
	<script src="${pageContext.request.contextPath}/js/running/applySuccess.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>

