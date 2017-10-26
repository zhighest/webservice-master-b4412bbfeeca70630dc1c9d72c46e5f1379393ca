<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html class="html">
<head>
	<title>关于我们-联璧金融</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta name="description" content="联璧金融，诞生于科技基因强大的“上海联璧电子科技有限公司”，由独立的专业金融产品团队组建运营，依托联璧科技的移动通信及大数据技术，让金融，惠民生"/>
	<meta name="keywords" content="关于联璧金融,联璧金融简介, 联璧金融介绍"/>
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/aboutUs.css?<%=request.getAttribute("version")%>"/>
</head>
<body>
	<div id="wrap">
		<div id="main">
			<c:set var="pageIndex" value="6"/>
			<%@  include file="../header.jsp"%>
			<div class="contentWrapper">
				<!---次级导航-->
				<div class="wrapper">
					<div class="clearfix PTB30"> 
						<c:set var="subnav" value="4"/>
						<%@  include file="menu.jsp"%>
						<ul class="fr inlineBUl rightGuild font12 MT7">
							<li>当前位置：<a href="${pageContext.request.contextPath}/webabout/aboutUs" class="blackTex" rel="nofollow">关于我们<span class="MLR5">/</span></a></li>
							<li class="redTex">产品介绍</li>
						</ul>
					</div>
				</div>
				<div class="aboutUsBanner banner4">
					<div class="wrapper">
						<div class="MT180">
							<p class="subTitle whiteTex show1">产品介绍</p>
							<p class="subDes whiteTex show5">Product introduction</p>	
						</div>
					</div>
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT20 PB20 clearfix">
						<div class="fl qrcodeDesL"><img src="${pageContext.request.contextPath}/pic/web/aboutUs/qrcode1.png" width="90%"></div>
						<p class="lineHeight2x font14 grayTex61T fr qrcodeDesR MT20">联璧金融致力于为投资人提供多样优质的理财产品，让投资人根据自身的情况选择更贴合需求的理财产品。截至目前，联璧金融已推出“零钱计划”、“铃铛宝3月期”、“铃铛宝6月期”等多款理财产品。</p>
					</div>
				</div>
				<div class="wrapper">
					<div class="MT40">
						<p class="subTitle grayTex61">理财产品</p>
						<p class="subDes grayTex61T">Financial products</p>	
					</div>
					<ul id="productList" class="MT30">

        			</ul>
					
				</div>
			</div>
		</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<%@  include file="../footer.jsp"%>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/propertyInfo.js?<%=request.getAttribute("version")%>"></script>
</html>
