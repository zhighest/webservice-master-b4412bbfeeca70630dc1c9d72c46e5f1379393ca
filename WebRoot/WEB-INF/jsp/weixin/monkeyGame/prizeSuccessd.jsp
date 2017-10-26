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
	<title>兑奖页面</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/monkeyGame/monkeyGame.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="width80 blockC">
		<div class="pageTitle blockC"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/title1.png"></div>
		<div class="width100 blockC">
			<div class="popBox positionR">
				<p class="popTitle Font25">恭喜您</p>
				<div class="MT5P font12">已成功兑换<span id="parameter">奖品</span></div>
				<div class="width80 blockC textC successdBtnArea"><a class="width80 blockC textC block" id="prizeListBtn"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/btn4.png" class="width80"></a></div>
			</div>
			
	</div>	
	<input type="hidden" name="prize" id="prize" value="${prize}" />
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" />
	<script src="${pageContext.request.contextPath}/js/weixin/page/monkeyGame/prizeSuccessd.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

