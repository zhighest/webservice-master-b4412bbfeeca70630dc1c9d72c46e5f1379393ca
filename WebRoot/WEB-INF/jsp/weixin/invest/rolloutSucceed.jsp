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
	<title>转出成功</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />

</head>
<body>
	<div class="PT40P">
		<a href="${pageContext.request.contextPath}/wxtrade/goMyAsset">
		<img src="${pageContext.request.contextPath}/pic/weixin/successd.png?<%=request.getAttribute("version")%>" alt="" height="120">
		</a>
		<p class="font16 grayTex MT20">转出成功</p>
	</div>
	<div class="width80 blockC MT30"><a class="btn width100 block font18 radius5" href="${pageContext.request.contextPath}/wxtrade/goMyAsset">完成</a></div>
	<%@  include file="../baiduStatistics.jsp"%>	
</body>
</html>