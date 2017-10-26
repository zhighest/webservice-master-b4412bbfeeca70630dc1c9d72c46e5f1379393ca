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
	<title>投资成功</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>"/>
    <%@  include file="../header.jsp"%>
</head>
<body>
	<div class="PT40P">
		<a>
			<img src="${pageContext.request.contextPath}/pic/weixin/successd.png?<%=request.getAttribute("version")%>" alt="" height="120">
		</a>
		<p class="font16 grayTex MT20">投资成功</p>
	</div>
	<div class="width80 blockC MT30"><a class="btn width100 block font18 radius5" href="" id="backBtn">完成</a></div>
	<input type="hidden" name="productId" id="productId" value="<%=request.getParameter("productId")%>">
	<input type="hidden" name="productName" id="productName" value="<%=request.getParameter("productName")%>">
    <!-- 起投金额 -->
 	<input type="hidden" name="investMinAmount" id="investMinAmount" value="<%=request.getParameter("investMinAmount")%>">
 	<!-- 基本收益率 -->
 	<input type="hidden" name="annualRate" id="annualRate" value="<%=request.getParameter("annualRate")%>">
 	<!-- 标的id -->
 	<input type="hidden" name="id" id="id" value="<%=request.getParameter("id")%>">
 	<!-- 可投总金额 -->
 	<input type="hidden" name="remanAmount" id="remanAmount" value="<%=request.getParameter("remanAmount")%>">
	<script src="${pageContext.request.contextPath}/js/weixin/page/investSucceed.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>	
</body>
</html>