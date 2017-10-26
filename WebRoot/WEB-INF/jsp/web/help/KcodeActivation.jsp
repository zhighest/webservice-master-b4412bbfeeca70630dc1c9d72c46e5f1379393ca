<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

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
							<c:set var="helpSidebar" value="2" />
							<%@  include file="helpSidebar.jsp"%>
							<ul class="fr inlineBUl rightGuild font12 MT7">
								<li>当前位置：
									<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation" class="blackTex" rel="nofollow">帮助中心<span class="MLR5">/</span></a>
								</li>
								<li class="redTex">K码激活</li>
							</ul>
						</div>
					</div>
					<!---次级导航 end -->
					<div class="helpCenterBanner banner1">
						<div class="wrapper">
							<div class="MT180">
								<p class="subTitle whiteTex show1">K码激活</p>
								<p class="subDes whiteTex show5">K code activation</p>
							</div>
						</div>
					</div>

					<!--激活产品列表-->
					<div class="ActiveProductList" id="productList">
						<p class="">选择要激活的产品</p>
						<ul class="clearfix">
							<li class="clearfix">
								<a href="${pageContext.request.contextPath}/webkcode/k2?hasHeadFoot=${hasHeadFoot}">
									<img src="${pageContext.request.contextPath}/pic/web/help/K2.png">
									<div class="productDetail">
										<p class="MT20">斐讯智能路由器K2</p>
										<p class="MT10 Color97 font14">含K1,K1S,K2C</p>
									</div>
									<span>
										<img src="${pageContext.request.contextPath}/pic/web/help/rightArrow.jpg"/>
									</span>
								</a>
							</li>
							<li class="clearfix">
								<a href="${pageContext.request.contextPath}/webkcode/k3?hasHeadFoot=${hasHeadFoot}">
									<img src="${pageContext.request.contextPath}/pic/web/help/K3.png">
									<div class="productDetail">
										<p class="MT20">双频无线路由器K3</p>
									</div>
									<span>
										<img src="${pageContext.request.contextPath}/pic/web/help/rightArrow.jpg"/>
									</span>
								</a>
							</li>
							<li class="clearfix">
								<a href="${pageContext.request.contextPath}/webkcode/k2p?hasHeadFoot=${hasHeadFoot}">
									<img src="${pageContext.request.contextPath}/pic/web/help/K2P.png">
									<div class="productDetail">
										<p class="MT20">斐讯智能路由器K2P</p>
									</div>
									<span>
										<img src="${pageContext.request.contextPath}/pic/web/help/rightArrow.jpg"/>
									</span>
								</a>
							</li>
							<li class="clearfix">
								<a href="${pageContext.request.contextPath}/webkcode/c1330?hasHeadFoot=${hasHeadFoot}">
									<img src="${pageContext.request.contextPath}/pic/web/help/C1330.png">
									<div class="productDetail">
										<p class="MT20">智能手机C1330</p>
									</div>
									<span>
										<img src="${pageContext.request.contextPath}/pic/web/help/rightArrow.jpg"/>
									</span>
								</a>
							</li>
							<!--<li class="clearfix">
								<a href="${pageContext.request.contextPath}/webkcode/s7?hasHeadFoot=${hasHeadFoot}">
									<img src="${pageContext.request.contextPath}/pic/web/help/S7.png">
									<div class="productDetail">
										<p class="MT20">智能体脂秤S7</p>
									</div>
									<span>
										<img src="${pageContext.request.contextPath}/pic/web/help/rightArrow.jpg"/>
									</span>
								</a>
							</li>-->
							<li class="clearfix">
								<a href="${pageContext.request.contextPath}/webkcode/other?hasHeadFoot=${hasHeadFoot}">
									<img src="${pageContext.request.contextPath}/pic/web/help/other.png">
									<div class="productDetail">
										<p class="MT20">其他产品</p>
									</div>
									<span>
										<img src="${pageContext.request.contextPath}/pic/web/help/rightArrow.jpg"/>
									</span>
								</a>
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