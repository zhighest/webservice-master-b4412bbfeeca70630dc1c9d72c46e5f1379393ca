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
  <title>调查问卷</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/questionnaire.css?<%=request.getAttribute("version")%>" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/jquery-1.8.2.min.js?<%=request.getAttribute("version")%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/merge.js?<%=request.getAttribute("version")%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/quo.js?<%=request.getAttribute("version")%>"></script>
   <!-- <%@  include file="../header.jsp"%> -->
  </head>
   
  <body class="whiteBkg">
  <div class="banner"></div>  	 
  <div class="textL PLR10 PB20">
    <ul id="question">

    </ul>
    <div class="submit textC radius5 width100  MT30 PTB8 redBkg whiteTex none" id="submit">提交并签到</div>
  </div>
    <%@  include file="../baiduStatistics.jsp"%>
    <input type="hidden" id="mobile" name="mobile" value="<%=request.getAttribute("mobile") %>">
    <input type="hidden" id="userId" name="userId" value="<%=request.getAttribute("userId") %>">
    <input type="hidden" id="channel" name="channel" value="<%=request.getAttribute("channel") %>">
    <input type="hidden" id="bannerImgList" name="bannerImgList" value="${bannerImgList}">
    <script type="text/javascript" src="<%=request.getAttribute("templateDirectory")%>/questionnaireItem.js?<%=request.getAttribute("version")%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/page/questionnaire.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>

