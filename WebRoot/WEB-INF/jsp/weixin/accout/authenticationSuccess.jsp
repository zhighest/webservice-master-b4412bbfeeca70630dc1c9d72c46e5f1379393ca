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
	<title>实名认证</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>		
	<div class="PT40P">
		<img src="${pageContext.request.contextPath}/pic/weixin/successd.png?<%=request.getAttribute("version")%>" alt="" height="120">
		<p class="font16 grayTex MT20">实名认证成功</p>
		
	</div>
	<div class="width80 blockC MT30"><a href="${pageContext.request.contextPath}/wxtrade/goMyAsset" class="btn width100 block font18 radius5"  id="backBtn">确定</a></div>
	<input type="hidden" name="productId" id="productId" value="<%=request.getParameter("productId")%>">
	<script src="${pageContext.request.contextPath}/js/weixin/page/authenticationSuccess.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>