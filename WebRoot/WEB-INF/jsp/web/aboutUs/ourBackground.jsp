<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
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
<body class="fontFamily">
	<div id="wrap"> 
		<div id="main">
			<c:set var="pageIndex" value="6"/>
			<%@  include file="../header.jsp"%>
			<div class="contentWrapper positionR">
				<!---次级导航 begin-->
				<div class="wrapper">
					<div class="clearfix PTB30"> 
						<c:set var="subnav" value="3"/>
						<%@  include file="../aboutUs/menu.jsp"%>
						<ul class="fr inlineBUl rightGuild font12 MT7">
							<li>当前位置：<a href="${pageContext.request.contextPath}/webabout/aboutUs" class="blackTex" rel="nofollow">关于我们<span class="MLR5">/</span></a></li>
							<li class="redTex">联系我们</li>
						</ul>
					</div>
				</div>
				<!---次级导航 end-->
				<div class="aboutUsBanner banner3">
					<div class="wrapper">
						<div class="MT180">
							<p class="subTitle whiteTex show1">联系我们</p>
							<p class="subDes whiteTex show5">Contact us</p>
						</div>
					</div>
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT40 PB20">
						<!--百度地图容器 begin -->
						<div id="dituContent"></div>
						<!--百度地图容器 end -->
						<div class="MT30 textC">
							<div><img src="${pageContext.request.contextPath}/pic/web/aboutUs/sign.png" width="13"><span class="blackTex ML10 lineHeight2x">联璧科技有限公司</span></div>
							<p class="grayTex61T lineHeight2x" id="p01">地址：上海市松江区广富林路4855号大业领地91幢</p>
						</div>
						<div class="MT50 textC MB30">
							<div><img src="${pageContext.request.contextPath}/pic/web/aboutUs/service.png" width="18" class="serviceIcon"><span class="blackTex ML10 lineHeight2x">客服服务</span></div>
							<p class="grayTex61T lineHeight2x">客服电话:<span class="font16 ML5 JsPhoneCall"></span><br/>工作时间：<span class="MR5">周一至周日 9:00-21:00</span></p>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<%@  include file="../footer.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/baiduMap.js" charset="UTF-8"></script>

</html>
