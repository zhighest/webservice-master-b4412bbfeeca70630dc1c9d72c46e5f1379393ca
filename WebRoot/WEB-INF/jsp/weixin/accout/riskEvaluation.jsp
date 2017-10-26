<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*"> 
	<title>风险承受能力评估</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/riskEvaluation.css?<%=request.getAttribute("version")%>"/>
    <%@  include file="../header.jsp"%>
</head>
<body>
<div>
	   <div class="surveyWrap textL lineHeight100" >
<!-- 			<dl id="listWrap">
				<dt class="title font18">1.您现在的年龄:</dt>
                <dd> A 60岁以上</dd>
                <dd> B 46-60</dd>
                <dd> C 36-45</dd>
                <dd> D 26-35</dd>
                <dd> E 25岁以下</dd>
			</dl> -->
   </div>
		<div class="positionF submitBtn width100">
		<a href="javascript:;" id="nextBtn"  class="redBkg radius5 blockC block whiteTex">下一题</a>
        <p class="MT10 grayTex"><span class="redTex font18" id="currentPage">0</span>/<span class="" id="totalPage">0</span></p>
		</div>
</div>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
<div class="none" id="formDiv"></div>
<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
<input type="hidden" name="userId" id="userId" value="<%=request.getParameter("userId")%>" >
<input type="hidden" name="flag" id="flag" value="<%=request.getParameter("flag")%>">
<script src="${pageContext.request.contextPath}/js/weixin/page/riskEvaluation.js?<%=request.getAttribute("version")%>"></script>
</html>