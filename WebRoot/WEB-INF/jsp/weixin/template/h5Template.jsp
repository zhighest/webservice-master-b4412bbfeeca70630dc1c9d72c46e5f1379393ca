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
  <title>最新活动</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/h5Template.css?<%=request.getAttribute("version")%>" />
  </head>
   
  <body class="whiteBkg">  	 
  <div id="bkgPic">
  </div>
  <div class="button"><img src="" width="90%" id="button"></div>
  <input type="hidden" id="bannerImgList" name="bannerImgList" value="<%=request.getAttribute("templateDirectory") %>">
  <input type="hidden" id="bannerImgList" name="bannerImgList" value="${bannerImgList}">
  <script src="${pageContext.request.contextPath}/js/weixin/page/https.js?<%=request.getAttribute("version")%>"></script>
  <script type="text/javascript" src="<%=request.getAttribute("templateDirectory")%>/h5Template.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
