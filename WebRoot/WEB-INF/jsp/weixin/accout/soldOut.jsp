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
	<title>投资理财</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
  </head>
  
  <body>
    <img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151202Share/cannotImg.png" class="width30 MT30">
    <h6 class="font20 blackTex MT10">已售罄</h6>
    <p class="font16 grayTex MT10">该产品已售罄</p>
    <a class="block width90 ML5P whiteTex redBkg radius5 PTB10 MT20" href="${pageContext.request.contextPath}/wxproduct/goDemandProductIndex?fixDemandSwitch=demand">返 回</a>
  </body>
</html>
