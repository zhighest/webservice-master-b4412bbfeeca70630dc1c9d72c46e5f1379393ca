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
	<title>资讯</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/notice.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
  </head>
  
  <body class="positionR minHeight500">  	 
    <div id="myNotice"></div>
    <div class="textC grayTex PB10" id="proListPaging" class="pagingMobile"></div>	
    <input type="hidden" name="clearUnread" id="clearUnread" value="<%=request.getParameter("unread")%>">
    <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
    <script src="${pageContext.request.contextPath}/js/weixin/page/notice.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
