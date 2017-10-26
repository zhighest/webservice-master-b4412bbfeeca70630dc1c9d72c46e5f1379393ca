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
	<title id="title">公告</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/message.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
  </head>
  
  <body class="positionR minHeight500">
    <ul class="grayBkg02 clearfix borderB tabSwitch">
		<li class="width50 fl PTB10 grayTex positionR current tabNav tab1" onclick="tabSwitchLi('tabNav','tab1');tabSwitchCon('tabContent','tabContent1');tab1Click()">公告</li>

		<li class="width50 fl PTB10 grayTex positionR tabNav tab2 " onclick="tabSwitchLi('tabNav','tab2');tabSwitchCon('tabContent','tabContent2');tab2Click()">消息</li>
	</ul>
    <!-- 公告 begin -->
	<div class="tabContent1 tabContent">
		<div id="myMessage01"></div>
    	<div class="textC grayTex PB10" id="proListPaging01" class="pagingMobile"></div>	
	</div>
    <!-- 公告 end -->

    <!-- 消息 begin -->
	<div class="tabContent2 tabContent" style="display: none">
		<div id="myMessage02"></div>
    	<div class="textC grayTex PB10" id="proListPaging02" class="pagingMobile"></div>
	</div>
    <!-- 消息 end -->
    <input type="hidden" name="type" id="type" value="<%=request.getParameter("type")%>" >
    <script src="${pageContext.request.contextPath}/js/weixin/page/message.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
