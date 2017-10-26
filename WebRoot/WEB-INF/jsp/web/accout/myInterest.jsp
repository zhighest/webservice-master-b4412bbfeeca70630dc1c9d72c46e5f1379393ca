<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>个人中心-联璧金融</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta content="个人中心，联璧金融个人中心" name="keywords">
		<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣" name="description">
		<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
		<script src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
	</head>
  
<body class="fontFamily">
	<div id="wrap"> 
  		<div id="main">
  		    <!-- 网页头部的主tab的val值判断 -->
		    <c:set var="pageIndex" value="2"/>
		    <!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
		    <c:set var="pageAccount" value="7"/>
  			<%@  include file="../header.jsp"%> 
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					<div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="7"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">加息券</span></div>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="clearfix witeBkg width100 PTB30">
  					<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">加息券</p>
  						<p class="grayTex61 opacity60">Increase interest coupons</p>
						<!-- 加息券筛选按钮 -->
  						<div class="positionA selectType">
	    					<p class="clearfix heigh25 font14 grayTex61">
	    						<span class="fl cursor MR5 current" id="all">全 部 / </span>
	    						<span class="fl cursor" id="demand">零钱计划</span>
	    					</p>
		    			</div>

  					</div>
  					<!-- 加息券区域 begin -->
  					<div class="wrapper clearfix whiteBkg PTB30 positionR MT20">
		    			<ul class="clearfix rateCouponsList MB20" id="rateCouponsList">
						</ul>
						<!-- 查看更多按钮 -->
						<div class="textC grayTex61 PB10 cursor MT10" id="proListPaging" class="pagingMobile"></div>
  					</div>
  					<!-- 加息券区域 end -->
  				</div>
	  			<!-- 资产总览 end -->
		    </div>
  		</div> 
  	</div>
	<input type="hidden" name="product" id="product" value="${product}" >
	<input type="hidden" name="sloanId" id="sloanId" value="${sloanId}" >
	<input type="hidden" name="errorMsg" id="errorMsg" value="${errorMsg}" >
	<input type="hidden" name="loanId" id="loanId" value="${loanId}" >
	<%@  include file="../footer.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/web/page/myInterest.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
