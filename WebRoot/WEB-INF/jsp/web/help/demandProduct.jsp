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
		<meta name="format-detection" content="telephone=no" />
		<meta name="description" content="联璧金融帮助服务，介绍关于联璧金融投资的常见问题，注册、认证、充值、取现、投资操作等操作演示。解答您对于联璧金融的疑问问题" />
		<meta name="keywords" content="帮助服务，联璧金融帮助服务" />
		<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute(" version ")%>"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/helpCenter.css?<%=request.getAttribute(" version ")%>"/>
	</head>

	<body class="fontFamily">
		<div id="wrap">
			<div id="main">
				<c:set var="pageIndex" value="5" />
				<%@  include file="../header.jsp"%>
				<div class="contentWrapper positionR">
					<!---次级导航 begin -->
					<div class="wrapper">
						<div class="clearfix PTB30">
							<c:set var="helpSidebar" value="5" />
							<%@  include file="helpSidebar.jsp"%>
							<ul class="fr inlineBUl rightGuild font12 MT7">
								<li>当前位置：
									<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation" class="blackTex" rel="nofollow">帮助中心<span class="MLR5">/</span></a>
								</li>
								<li class="redTex">购买零钱计划</li>
							</ul>
						</div>
					</div>
					<!---次级导航 end -->
					<div class="helpCenterBanner banner1">
						<div class="wrapper">
							<div class="MT180">
								<p class="subTitle whiteTex show1">购买零钱计划</p>
								<p class="subDes whiteTex show5">Investment current assets</p>
							</div>
						</div>
					</div>

					<div class="ActiveProductDetail " id="product2">
						<ul class="clearfix PT20">
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第一步：<span class="font12 Color2c fontWeightIntial">点击零钱计划</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/demandProductImg1.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第二步：<span class="font12 Color2c fontWeightIntial">点击开始投资</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/demandProductImg2.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第三步：<span class="font12 Color2c fontWeightIntial">点击立即投资</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/demandProductImg3.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第四步：<span class="font12 Color2c fontWeightIntial">输入投资金额</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/demandProductImg4.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第五步：<span class="font12 Color2c fontWeightIntial">输入交易密码</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/demandProductImg5.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第六步：<span class="font12 Color2c fontWeightIntial">加入成功</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/demandProductImg6.png">
							</li>
						</ul>
					</div>
					
				</div>
			</div>
		</div>
		<%@  include file="../footer.jsp"%>
		<%@  include file="../baiduStatistics.jsp"%>
		<input id="hasHeadFoot" name="hasHeadFoot" type="hidden" value="${hasHeadFoot}">
		<script src="${pageContext.request.contextPath}/js/web/page/helpCenter.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/web/page/subBanner.js?<%=request.getAttribute(" version ")%>"></script>
	</body>

</html>