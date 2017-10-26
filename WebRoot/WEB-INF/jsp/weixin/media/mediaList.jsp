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
	<title>媒体报道</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/mediaList.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
	<div class="wrapper">
		<ul id="mediaList">
		</ul>
		<div id="mediaListPaging" class="pagingMobile"></div>
	</div>
	<input type="hidden" id="clearUnread" value="<%=request.getParameter("unread")%>">
	<%@  include file="../baiduStatistics.jsp"%>
	<input id="URL" name="URL" type="hidden" value="${URL}">
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/mediaList.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>

