<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>活动详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/seedetail.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="acitivityBkg positionR">
	<div class="wrapper">
			<div class="imgDiv clearfix">
					<img src="${pageContext.request.contextPath}/pic/invitationDetailImage.png?<%=request.getAttribute("version")%>">
			</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<!--<script src="${pageContext.request.contextPath}/js/weixin/page/activityShare.js?<%=request.getAttribute("version")%>"></script>	-->
</body>
</html>
    
    

    
   

    

   


    
   

    
    

   
    

    

