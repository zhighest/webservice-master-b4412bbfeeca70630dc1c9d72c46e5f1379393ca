<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>最新活动-联璧金融</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/reset.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/mobileLanding.css?<%=request.getAttribute("version")%>"/>
  </head>
  <body class="fontFamily">
	<div id="wrap">
  		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_03.png?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix PositionR">
			<div class="rightIcon"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_10.png?<%=request.getAttribute("version")%>"></div>
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_04.png?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_05.png?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_06.png?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_07.png?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_08.png?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/web/mobileLanding/img_09.png?<%=request.getAttribute("version")%>">
		</div>	
  	</div>
  	<div class="btmAreaFix clearfix" id="btmAreaFix">
	  	<div class="leftArea">
		  	<span class="closed" id="closed"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/closed.png?<%=request.getAttribute("version")%>" width="20"></span>
		  	<span class="logo"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/logo.png?<%=request.getAttribute("version")%>" width="35"></span>
	  	</div>
	  	<div class="rightArea">
		  	<h4>联璧金融</h4>
		  	<p>使用APP客户端，体验更多惊喜！</p>
		  	<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" class="btn" rel="nofollow">下载APP</a>
	  	</div>
  	</div>
  	<%@  include file="../baiduStatistics.jsp"%>
  	<script src="${pageContext.request.contextPath}/js/web/integration.js?<%=request.getAttribute("version")%>"></script> 
	<script src="${pageContext.request.contextPath}/js/web/page/mobileLanding.js?<%=request.getAttribute("version")%>"></script>
	</body>
</html>
