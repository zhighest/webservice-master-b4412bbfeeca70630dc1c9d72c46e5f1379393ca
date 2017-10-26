<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.web.util.LoginRedirectUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("progma","no-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires",0);
	request.setAttribute("version","2016050901");

	request.setAttribute("URL",LoginRedirectUtil.URL);
	request.setAttribute("URLS",LoginRedirectUtil.URLS);
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=2.0,user-scalable=yes" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<title>图文详情</title>
    <%@  include file="../header.jsp"%>
</head>
<body>
	<div class="width100 textL">
		<div class="imgHead width100">
			<img  src="<%=request.getAttribute("goodsDetailImgHead")%>" alt="" class="width100">
		</div>
		<div class="imgArea width100">
			<img src="<%=request.getAttribute("picDetailLink")%>" alt="" class="width100">
		</div>
		<div class="imgBottom width100">
			<img  src="<%=request.getAttribute("goodsDetailImgBottom")%>" alt="" class="width100">
		</div>
	</div>
	<input type="hidden" name="commodityID" id="commodityID" value="<%=request.getParameter("commodityID")%>" >
</body>
</html>

