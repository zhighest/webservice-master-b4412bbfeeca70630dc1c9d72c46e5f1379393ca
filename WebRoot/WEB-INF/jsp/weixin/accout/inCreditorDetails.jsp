<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<title>借款方信息</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%> 
</head>
<body class="textL">
	<div class="wrapper MT10 font14">
		<ul class="width90 blockC PTB10 PL10 PR10 lineHeight1_5x">
			<li class="whiteBkg PTB10 PL10 PR10"><h4 class="grayTex font14">资产方描述</h4><div class="blackTex font12 MT5 lineHeight1_5x" id="companyName"></div></li>
			<li class="MT10 whiteBkg PTB10 PL10 PR10"><h4 class="grayTex font14">资金保障</h4><div class="blackTex font12 MT5 lineHeight1_5x" id="fundSecurity"></div></li>
			<li class="MT10 whiteBkg PTB10 PL10 PR10"><h4 class="grayTex font14">担保方介绍</h4><div class="redTex font12 MT5 lineHeight1_5x" id="borrowerIntroduction"></div></li>
		</ul>
	</div>
	<input id="sloanId" name="sloanId" type="hidden" value="${sloanId}">
	<script src="${pageContext.request.contextPath}/js/weixin/page/inCreditorDetail.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

