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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
  </head>
  <body class="fontFamily">
  	<div id="wrap"> 
  		<div id="main">
  		  <!-- 网页头部的主tab的val值判断 -->
			  <c:set var="pageIndex" value="2"/>
			  <!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
			  <c:set var="pageAccount" value="3"/>
  			<%@  include file="../header.jsp"%> 
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					<div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="3"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">定期资产</span></div>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="clearfix witeBkg width100 PTB30">
  					<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">定期资产</p>
  						<p class="grayTex61 opacity60">The term asset</p>
  					</div>
  					<div class="wrapper clearfix positionR MT20">
  						<ul class="width100 clearfix positionR">
  							<!-- 在投本金 begin -->
  							<li class="PTB30 grayBkgf5 fl width32 MR2P textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<span class="fl ML5">资产总额:</span>
  									</div>

  									<div class="width100 MT20 positionR">
  										<p class="redTex textC">
  											<span class="font12">￥</span>
  											<span class="font30"><fmt:formatNumber value="${investmentAssets}" pattern="#,##0.00#"/></span>
  										</p>
  									</div>
  								</div>
  							</li>
							<!-- 在投本金 end -->

							<!-- 累计收益 begin -->
  							<li class="PTB30 grayBkgf5 fl width32 MR2P textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<span class="fl ML5">累计收益:</span>
  									</div>
  									<div class="width100 MT20 positionR">
  										<p class="redTex textC">
  											<span class="font12">￥</span>
                        <span class="font30"><fmt:formatNumber value="${termIncomeAmount}" pattern="#,##0.00#"/></span>
  										</p>
  									</div>
  								</div>
  							</li>
							<!-- 累计收益 end -->

							<!-- 预期收益 begin -->
  							<li class="PTB30 grayBkgf5 fl width32 textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<span class="fl ML5">预期收益:</span>
  									</div>
  									<div class="width100 MT20 positionR">
  										<p class="redTex textC">
  											<span class="font12">￥</span>
                        		<span class="font30">
                        		<fmt:formatNumber value="${expectAmount}" pattern="#,##0.00#"/></span>
  										</p>
  									</div>
  								</div>
  							</li>
							 <!-- 预期收益 end -->
  						</ul>
  					</div>
  				</div>
	  			<!-- 资产总览 end -->
				
				  <!-- 投资详情 begin -->
	  			<div class="whiteBkg PTB30">
	  				<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">投资详情</p>
  						<p class="grayTex61 opacity60">Investment details</p>
              
              <div class="positionA selectType">
                <p class="clearfix heigh25 font14 grayTex61">
                  <span class="fl cursor MR5 current" id="currentList">当前订单 / </span>
                  <span class="fl cursor" id="historyList">历史订单</span>
                </p>
              </div>
              <!-- 订单存放区域 end --> 
	  			</div>
	  			<!-- 投资详情 end -->

          <div class="wrapper clearfix positionR MT20">
            <!-- 订单存放区域 begin -->
              <ul class="blackTex clearfix PL20 PR20 PB10 MT20" id="purchaseDetailList">
              </ul>
              <!-- 分页按钮 -->
              <div id="purchaseDetailListPaging" class="pagingMobile cursor"></div>

          </div>
		    </div>
  		</div> 
  	</div> 
    <input type="hidden" name="userId" id="userId" value="${userId}">
  	<!-- 是否登录 -->
  	<input id="userIdFlag" name="userIdFlag" type="hidden" value="${userIdFlag}">
  	<!-- 资产总额 -->
  	<input id="investmentAssets" name="investmentAssets" type="hidden" value="${investmentAssets}">
  	<!-- 累计收益 -->
	<input id="termIncomeAmount" name="termIncomeAmount" type="hidden" value="${termIncomeAmount}">
	<!-- 预期收益 -->
	<input id="expectAmount" name="expectAmount" type="hidden" value="${expectAmount}">
  	<%@  include file="../baiduStatistics.jsp"%>
  	<%@  include file="../footer.jsp"%>
    <div style="display:none" id="formDiv"></div>
  	<script src="${pageContext.request.contextPath}/js/web/page/fixedProperty.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
