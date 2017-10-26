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
<p class="PT50">您还未进行风险承受能力评估，请立刻评估</p>
    <div class="PT20" id="description2">
    	<ul class="textL PLR5P font14">
    		<li  class="PTB15">1）为保障投资人购买合适的产品，平台根据投资人风险承受能力评测结果，将投资人风险承受能力分为保守型、稳健型、平衡型、积极型、激进型五种类型。平台会根据结果为投资人更好的配置资产。</li>
            <li  class="PTB15">2）投资人如果没有进行过风险承受能力评测，在投资前需要进行评估。</li>
            <li  class="PTB15">3）评测问题均为单选题，请您认真作答，感谢您的配合！</li>
    	</ul>
    </div>
    <a href="javascript:;" class="returnInvestBtn width80 radius5 bkg3 whiteTex PTB10 block blockC MT25 MB10  tabBkgNone" id="goEvaluateBtn">进行评估</a>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
<div class="none" id="formDiv"></div>
<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
<input type="hidden" name="channel" id="channel" value="${channel}" >
<input type="hidden" name="userId" id="userId" value="${userId}" >
<input type="hidden" name="flag" id="flag" value="${flag}">
<script src="${pageContext.request.contextPath}/js/weixin/page/riskEvaluationEntrance.js?<%=request.getAttribute("version")%>"></script>
</html>