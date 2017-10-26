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
	<title>转出</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/rollOut.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%> 
</head>
<body>
<div class="main height100P">	
	<div class="wrapper PT10">
		<div class="hasTBBorder whiteBkg MB10">
			<div class="whiteBkg width90 blockC PLR5P clearfix PTB15 positionR">
				<div class="rollOff">转出金额</div>
				<input type="hidden"  id="token" name="token" value="${token}">
				<div class="PT15 outHide outNum">
				<label class="fontFwr positionA">￥</label>
				<input type="text" placeholder="请输入转出金额" name="" readonly class="inputNoborder moneyInput  block PL30 boxSizing" id="inputRollNum"></div>
			</div>
			<p class="width90 grayTex MLR5P blockC borderT PTB10">可转出金额:<span class="" id="rollNum"></span>元，<a class="redTex" id="insideInputBtn">全部转出</a></p>	
		</div>
		
		<div class="width90 blockC MT20"><a id="inactiveBtn" class="btn width100 block font16 radius5">立即转出</a></div>	
	</div>
	<div class="font14 grayTex textL MT20">
		<div class=" width90 blockC">
			<h4 class="lineHeight1_5x PB5 font14">转出小贴士</h4>
			<ul class="lineHeight1_5x PB5 font14">
				<li>1.转出免手续费。</li>
				<li>2.优先转出在投收益。</li>
				<li>3.转出成功后，转出金额将汇至您的账户余额中。</li>
			</ul>
		</div>
	</div>
	<div class="rollOutBtn whiteTex"><a href="${pageContext.request.contextPath}/wxdeposit/goTransferRecord">转出</br>记录</a></div>
</div>
<%@  include file="../numberKeyboard.jsp"%>
	<%@  include file="dealPWControls.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/transfer.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

