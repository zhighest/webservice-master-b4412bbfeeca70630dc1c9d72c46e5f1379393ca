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
	<title>风险承受能力评估结果</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>"/>
    <%@  include file="../header.jsp"%>
</head>
<body>
   <p class="MT10P font16">您目前的风险承受能力评估是：<span class="redTex borderRedB" id="riskTypeResult"></span></p>
   <a href="javascript:;" class="returnInvestBtn width80 radius5 bkg3 whiteTex PTB10 block blockC MT25  none tabBkgNone" id="returnInvestBtn">返回投资</a>
    <div class="outHide width85 blockC  none" id="settingReturnBtn">
	    <a href="javascript:;" class="returnInvestBtn width80 radius5 bkg3 whiteTex PTB10 block blockC MT25  width46 fl tabBkgNone" id="returnEvaluateBtn">重新评估</a>
	    <a href="javascript:;" class="returnInvestBtn width80 radius5 bkg3 whiteTex PTB10 block blockC MT25  width46 fr tabBkgNone" id="returnMyAssetBtn">我的账户</a>
	</div>
    <div class="declare textL PLR5P font14 lineHeight1_8x textIndent25 none PT10" id="description1">
        
    </div>
    <div class="defult none textL PLR5P font14 lineHeight1_8x textIndent25 PT10">您的风险承担能力水平比较低，您关注资产的安全性远超于资产的收益性，所以低风险 、 高流动性的投资品种比较适合您，这类投资的收益相对偏低。</div>
    <div class="settingDefault none  textL PLR5P font14 lineHeight1_8x textIndent25 PT10">
    <p>1）为保障投资人购买合适的产品，平台根据投资人风险承受能力评测结果，将投资人风险承受能力分为保守型、稳健型、平衡型、积极型、激进型五种类型。平台会根据结果为投资人更好的配置资产。</p>
    <p class="MT10">2）投资人如果没有进行过风险承受能力评测，在投资前需要进行评估。</p>
    
    <p class="MT10">3）评测问题均为单选题，请您认真作答，感谢您的配合！</p>
  </div>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
<input type="hidden" name="riskType" id="riskType" value="${riskType}" >
<input type="hidden" name="channel" id="channel" value="${channel}" >
<input type="hidden" name="flag" id="flag" value="${flag}">
<input type="hidden" name="userId" id="userId" value="${userId}">
<input type="hidden" name="mobile" id="mobile" value="${mobile}">
<script src="${pageContext.request.contextPath}/js/weixin/page/riskEvaluationResult.js?<%=request.getAttribute("version")%>"></script>
</html>