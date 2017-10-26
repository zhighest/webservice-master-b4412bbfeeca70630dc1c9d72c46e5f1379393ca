<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta name="x5-orientation" content="portrait">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0" />
	<title>联璧钱包</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
    <!--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />-->
    <%@  include file="header.jsp"%>
</head>
<body class="positionR">
	<%@ include file="tabBar.jsp"%>
	<!-- 公告一级菜单 begin -->
 	<div id="marquee"><ul><li class="marqueeLi" style="margin-top:5px;"></li></ul></div>
    <!-- 公告一级菜单 end -->
    <!-- 公告的二级弹窗 begin-->
	<div class="positionF none" id="message">
		<div class="positionR changeBox clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/messageBox.png?<%=request.getAttribute("version")%>" class="width100">
			<div class="width100 whiteBkg message PB20">
				<div class="width80 blockC">
					<h4 class="lineHeight2x messageTitle"></h4>
					<p class="textL font14 lineHeight1_5x messageMess"></p>
					<a id="know" class="redBkg whiteTex radius5 block MT10 pTB2P">知道了</a>
				</div>

			</div>
		</div>
	</div>
	<!-- 公告的二级弹窗 end-->

	<div class="topMenu"></div><!--很重要，勿删，删掉之后，头部的公告栏的字体在苹果手机上面会变大-->

	<div class="banner swipe" id="slider" >
		<div class="swipe-wrap" id="bannerList"></div>
		<nav>
		    <ul id="position"></ul>
	  </nav>
	</div>
		<!-- 自定义菜单 begin-->
	<div class="whiteBkg MB10 PTB5">
		<ul class="whiteTex clearfix width100 blockC" id="AllIcoFuncList"></ul>
	</div>
	<!-- 自定义菜单 end-->
		<!--理财精选界面-->
	<div class="financeDetaiWrap whiteBkg">
		<!--title start-->
		<div class="financeTitle PLR5P width89 outHide">
			<div class="fl font16 blackTex">
				精选理财
			</div>
			<div class="fr">
				<a href="${pageContext.request.contextPath}/wxabout/goGuaranteeLetter" class="font14 grayTex ">
					<div class="fl">多重保障</div>
					<img class="fl FinanceArrow" src="${pageContext.request.contextPath}/pic/weixin/rightIcon.png?<%=request.getAttribute("version")%>"/>
				</a>
			</div>
		</div>
		<!--title end-->
		<!--理财详情-->
		<div id='productUl'>
			<!--详情-->
		</div>
	</div>
	<div class="financeAdvanWrap grayTex ">
		<div class="width70 ML15P">
			<ul  class="financeAdvan clearfix inlineBUl">
				<li class="block width15">
					<img src="${pageContext.request.contextPath}/pic/weixin/index/version0413/iconHomeHighIncome@2x.png?<%=request.getAttribute("version")%>" >
					<p>高收益</p>
				</li>
				<li class="block PT5P spaceLi">
					.
				</li>
				<li class="block width15">
					<img src="${pageContext.request.contextPath}/pic/weixin/index/version0413/iconHomeLowThreshold@2x.png?<%=request.getAttribute("version")%>" >
					<p>低门槛</p>
				</li>
				<li class="block PT5P spaceLi" >
					.
				</li>
				<li class="block width15" >
					<img src="${pageContext.request.contextPath}/pic/weixin/index/version0413/iconHomeLowRisk@2x.png?<%=request.getAttribute("version")%>" >
					<p>低风险</p>
				</li>
				<li class="block PT5P spaceLi" >
					.
				</li>
				<li class="block width15">
					<img src="${pageContext.request.contextPath}/pic/weixin/index/version0413/iconHomeMoreFlexible@2x.png?<%=request.getAttribute("version")%>" >
					<p>更灵活</p>
				</li>
			</ul>
		</ddiv>

	</div>
	<div class="heigh45 width100 "></div><!--由于给body设置padding-bottom:45px未起作用，特加一个空的div，将空间撑开，防雨html的尾部-->
	<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
	<input id="channel" name="channel" type="hidden" value="${channel}">
	<input id="URL" name="URL" type="hidden" value="${URL}">
	<input type="hidden" name="mobile" id="mobile" value="${mobile}">
	<input type="hidden" name="icoFuncId" id="icoFuncId" value="${icoFuncId}">
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/circle-progress.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/index.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/jquery.kxbdmarquee.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/scrollText.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="baiduStatistics.jsp"%>
</body>
</html>

