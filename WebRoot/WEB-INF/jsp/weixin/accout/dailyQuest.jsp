

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
	<title>每日加息</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/dailyQuest.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="positionR">
    <div id="formDiv" class=" width100"></div>
    <div class="dailySign PLR5P none">
    <p class="font18 PTB10 ruledetail">规则说明</p>
	    <ul  id="dailySignList" class="textL font14">
		</ul>
		<span class='closeBtn positionA'><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/share/close.png?<%=request.getAttribute("version")%>" width="25"/></span>
    </div>
	<input type="hidden" name="product" id="product" value="${product}" >
	<input type="hidden" name="sloanId" id="sloanId" value="${sloanId}" >
	<input type="hidden" name="errorMsg" id="errorMsg" value="${errorMsg}" >
	<input type="hidden" name="loanId" id="loanId" value="${loanId}" >
	<input id="URL" name="URL" type="hidden" value="${URL}">
	<input type="hidden" name="mobile" id="mobile" value="${mobile}">
	<input type="hidden" name="newMobile" id="newMobile" value="<%=request.getParameter("newMobile")%>" >
    <input type="hidden" name="signFlag" id="signFlag" value="<%=request.getParameter("signFlag")%>" >
	<script src="${pageContext.request.contextPath}/js/weixin/page/dailyQuest.js?<%=request.getAttribute("version")%>"></script>	
    <%@  include file="../baiduStatistics.jsp"%>
	</body>
</html>