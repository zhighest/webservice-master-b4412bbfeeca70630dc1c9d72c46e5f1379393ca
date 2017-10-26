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
	<title>积分说明</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/scoreRule.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../../header.jsp"%>
</head>
<body class="whiteBkg">
 <div class=" PLR5P">
<!--      <p class="font18 radius3 ruledetail">规则说明</p> -->
	    <ul  id="scoreListRule" class="textL font14 grayTex">
		</ul>
   </div>
<input id="mobile" name="mobile" type="hidden" value="<%=request.getParameter("mobile")%>">
<script src="${pageContext.request.contextPath}/js/weixin/page/scoreRule.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../../baiduStatistics.jsp"%>

</body>
</html>

