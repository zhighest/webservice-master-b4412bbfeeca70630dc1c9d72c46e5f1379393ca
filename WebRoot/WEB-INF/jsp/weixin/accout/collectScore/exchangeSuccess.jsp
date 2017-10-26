<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>兑换成功</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/collectScore.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../../header.jsp"%>
</head>
<body>
   <div class="width30 blockC PT20P">
   	<img src="${pageContext.request.contextPath}/pic/weixin/collectScore/success.png" width="100%" >
   </div>
   <p class="font24 PT15">兑换成功</p>
   <a href="${pageContext.request.contextPath}/wxPoint/colletScore" class="block blockC heigh50 width85 bkg3 whiteTex MT20 radius3">完成</a>
   <%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>

