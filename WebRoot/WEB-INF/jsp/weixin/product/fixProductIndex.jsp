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
	<title>定期理财</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="bkg3">
		<div id="productList">
		</div>
		<div id="proListPaging" class="pagingMobile"></div>
		<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
		<div style="display:none" id="formDiv"></div>
	</div>
	<input type="hidden" name="channel" id="channel" value="${channel}" >
	<input type="hidden" name="parm" id="parm" value="${parm}" >
	<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/fixProductIndex.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>