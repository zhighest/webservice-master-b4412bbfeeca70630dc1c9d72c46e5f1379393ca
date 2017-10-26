<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html class="html">
  <head>
    <title>帮助中心-联璧金融</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta name="description" content="联璧金融帮助服务，介绍关于联璧金融投资的常见问题，注册、认证、充值、取现、投资操作等操作演示。解答您对于联璧金融的疑问问题"/>
	<meta name="keywords" content="帮助服务，联璧金融帮助服务"/>
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/helpCenter.css?<%=request.getAttribute("version")%>"/>
  </head>
  
  <body class="fontFamily">
  	<div class="positionF helpBkg width100 height100P"></div>
	<div id="wrap"> 
  		<div id="main">
 			<c:set var="pageIndex" value="5"/>
  			<%@  include file="../header.jsp"%>
  			<div class="contentWrapper positionR">
  				<div class="topTex">
				  <p class="show1">欢迎来到联璧金融</p>
				  <p class="show2">Welcome to lincomb</p>
				</div>
	  			<div class="wrapper blockC clearfix wap">
	  				<c:set var="helpSidebar" value="11"/>
			    	<%@  include file="helpSidebar.jsp"%>
			    	<div class="fr PL20 PR20 PT30 whiteBkg right">
			    		<div class="recharge">
			    			<div class="pinkLead heigh40 PL20 redTex strong">
			    				<span class="dealTex PL10 font16">购买定期产品</span>
			    			</div>
			    			<div class="PB30">
								<ul class="clearfix PLR20">
									<li class="fl width30 MR5P MT30">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/oneStep.png" width="60px">点击立即投资</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA fixProductImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg1.png" width="85%">
										</div>
									</li>
									<li class="fl width30 MR5P MT30">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/twoStep.png" width="60px">立即投标</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA topUpImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg2.png" width="85%">
										</div>
									</li>
									<li class="fl width30 MT30">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/threeStep.png" width="60px">输入金额</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA fixProductImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg3.png" width="85%">
										</div>
									</li>
									<li class="fl width30 MT30 MR5P">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/fourStep.png" width="60px">点击确认投资</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA fixProductImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg4.png" width="85%">
										</div>
									</li>
									<li class="fl width30 MT30 MR5P">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/fiveStep.png" width="60px">输入交易密码</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA fixProductImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg5.png" width="85%">
										</div>
									</li>
									<li class="fl width30 MT30">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/sixStep.png" width="60px">投资成功</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA fixProductImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg6.png" width="85%">
										</div>
									</li>
									<li class="fl width30 MT30">
										<h4 class="positionR blackTex heigh40 PL60">
											<span><img class="positionA stepIcon" src="${pageContext.request.contextPath}/pic/web/help/sevenStep.png" width="60px">查看投资订单</span>
										</h4>
										<div class="positionR MT20">
											<img class="block phoneBkg" src="${pageContext.request.contextPath}/pic/web/help/phoneBkg.png" width="100%">
											<img class="positionA fixProductImg" src="${pageContext.request.contextPath}/pic/web/help/fixProductImg7.png" width="85%">
										</div>
									</li>
								</ul>
			    			</div>
			    		</div>
			    	</div>
			    </div>
			</div>
		</div> 
  	</div> 
	<%@  include file="../footer.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/web/page/subBanner.js?<%=request.getAttribute("version")%>"></script>
	</body>
</html>
