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
	<title>销售明细</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/salesManagementDetails.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
  </head>
  <body>
    <div class="wrap">
    	<ul class="saleList font14" id="saleList"></ul> <!-- 销售记录列表 -->
    	
      <div id="fundFlowListPaging" class="pagingMobile"></div><!-- 分页文字提示 -->
    </div>
  <div id="formDiv"></div>
  <input id="mobileNumber" name="mobileNumber" type="hidden" value="<%=request.getParameter("mobileNumber")%>">
  <input id="month" name="month" type="hidden" value="<%=request.getParameter("month")%>">
  <script src="${pageContext.request.contextPath}/js/weixin/page/salesManagementDetails.js?<%=request.getAttribute("version")%>"></script>
  <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
