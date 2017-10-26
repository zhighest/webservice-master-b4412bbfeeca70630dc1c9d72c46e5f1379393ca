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
	<title>其他</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/changeVoucher.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
    <ul class="PLR3P" id="exchangeList">
        <%--<li class="textR MT10">--%>
            <%--<a style="color:#2EB3EB" href="${pageContext.request.contextPath}/wxcoupons/jsVoucher"><img class="verMid" height="30" src="${pageContext.request.contextPath}/pic/weixin/K3activity/howIcon.png" />获取加数券</a>--%>
        <%--</li>--%>
    </ul>

    <div id="exchangeListPaging" class="MT40 pagingMobile"></div>
    <input id="gourl" name="gourl" type="hidden" value="${gourl}">
    <input id="key" name="key" type="hidden" value="${key}">
    <input id="channel" name="channel" type="hidden" value="${channel}">


    <%@  include file="../baiduStatistics.jsp"%>
    <script src="${pageContext.request.contextPath}/js/weixin/page/thirdVoucher.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>