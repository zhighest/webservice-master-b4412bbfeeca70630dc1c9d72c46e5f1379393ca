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
		<title>会员销售管理</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/salesManagement.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header.jsp"%>
  </head>  
  <body class="saleManagWrap">
  	<div id="saleMonthWrap">
  		<!--单独月份的销售明细-->
  	</div>
  	<div id="monthListPaging" class="pagingMobile"></div>	
  	<%@  include file="../baiduStatistics.jsp"%>
  	<script src="${pageContext.request.contextPath}/js/weixin/page/salesManagement.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
