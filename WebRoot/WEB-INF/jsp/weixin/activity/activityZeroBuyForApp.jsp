<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityZeroBuyForApp.css?<%=request.getAttribute("version")%>" />

</head>
<body>
	<div class="wrap">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityZeroBuyForApp/activityRuler_01.jpg">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityZeroBuyForApp/activityRuler_02.jpg">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityZeroBuyForApp/activityRuler_03.jpg">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityZeroBuyForApp/activityRuler_04.jpg">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityZeroBuyForApp/activityRuler_05.jpg">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityZeroBuyForApp/activityRuler_06.jpg">
		<div class="blackBKG">
			<div class="doSome" id='doSome'>
				去实名
			</div>
		</div>
	</div>		
</body>
<input type="hidden" name="channel" id="channel"  value="<%=request.getParameter("channel")%>">
<input type="hidden" name="KFunctionType" id="KFunctionType"  value="<%=request.getParameter("KFunctionType")%>">
<script src="${pageContext.request.contextPath}/js/weixin/integration.js"></script>
<script src="${pageContext.request.contextPath}/js/weixin/page/activityZeroBuyForApp.js?<%=request.getAttribute("version")%>"></script>
</html>