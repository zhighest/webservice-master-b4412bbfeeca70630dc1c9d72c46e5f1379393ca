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
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<title>在投债权</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%> 
</head>
<style>
	
</style>
<body class="textL" style="background-color: #efefef;">	
	<div id="creditorWrap">
		<div id="creditorTitle" class="lineHeight100 whiteBkg textC PTB15 borderTGray">
			<div class="PL10 blackTex" id="inCreditorText"></div>
		    <div class="MT20"><font class="redTex font34 helveticaneue" id="inCreditor"></font></div>		
		</div>			
		<div class="wrapper font14" id="inCreditorList"></div>
		<div id="purchaseDetailListPaging" class="pagingMobile"></div>
	</div>
	<input id="accountAmount" name="accountAmount" type="hidden" value="<%=request.getParameter("accountAmount")%>">
	<div id="formDiv" style="display:none;"></div>			
	<script src="${pageContext.request.contextPath}/js/weixin/page/inCreditor.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

