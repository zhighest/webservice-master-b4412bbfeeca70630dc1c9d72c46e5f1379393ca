<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta content="个人中心，联璧金融个人中心" name="keywords">
	<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣" name="description">
    <title>个人中心-联璧金融</title>
    <link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/investDetail.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
  </head>
  
  <body class="fontFamily">
	<div id="wrap"> 
  		<div id="main">
  		    <!-- 网页头部的主tab的val值判断 -->
		    <c:set var="pageIndex" value="2"/>
		    <!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
		    <c:set var="pageAccount" value="6"/>
  			<%@  include file="../header.jsp"%> 
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					<div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="6"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">交易记录</span></div>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="clearfix witeBkg width100 PTB30">
  					<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">交易记录</p>
  						<p class="grayTex61 opacity60">Transaction record</p>
  					</div>
  					<div class="wrapper clearfix grayBkgf5 PTB30 positionR MT20">
		    			<dl class="blackTex PL20 PR20 PTB20 textC" id="fundFlowList">
		    				<dd class="grayTex textC MB10">
		    					<span class="fl width25 spanBorderR boxSizing">时间</span>
		    					<span class="fl width20 spanBorderR boxSizing">名称</span>
		    					<span class="fl width35 spanBorderR boxSizing">金额（元）</span>
		    					<span class="fl width20">状态</span>
		    				</dd>
				    	</dl>
		    			<div class="textC colorb2 width100" id="purchaseDetailListPaging"></div>
  					</div>
  				</div>
	  			<!-- 资产总览 end -->
		    </div>
  		</div> 
  	</div> 

  	<%@  include file="../footer.jsp"%>
  	<%@  include file="../baiduStatistics.jsp"%>
  	<script src="${pageContext.request.contextPath}/js/web/page/investDetail.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
