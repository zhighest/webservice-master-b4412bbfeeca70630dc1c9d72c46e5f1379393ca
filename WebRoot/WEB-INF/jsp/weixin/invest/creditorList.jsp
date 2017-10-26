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
	<title>债权列表</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/creditorList.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script> -->
</head>
<body>
	
	<div class="wrapper">
		<div class="width100">
			<ul id="creditorList">
			</ul>
		</div>
		<div class="textC grayTex PB10" id="proListPaging" class="pagingMobile"></div>	
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<input id="URL" name="URL" type="hidden" value="${URL}">	
	<script src="${pageContext.request.contextPath}/js/weixin/page/creditorList.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>

