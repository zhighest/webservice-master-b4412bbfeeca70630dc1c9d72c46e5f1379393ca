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
	<title>礼包资产</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/usedPagList.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body style="background:#EFEFEF;">
	<div class="clearfix PLR20 MT10 none goSoonVoucher">
		<a class="fr redTex font14" href="${pageContext.request.contextPath}/wxcoupons/soonVoucher">加速券</a>
	</div>
	<div class="giftWrap PLR10 bkg4 PB35">
		<!-- <div class="welfareBag k3Bag whiteBkg MT10">
			<div class="clearfix">
				<div class="fl welfareBagLeft">
					<p class="redTex k3Detail textL PTB20 PLR10 font20">K3路由器活动礼包</p>
					<p class="grayTex PLR10 textL font14 PTB5">礼包总礼金&nbsp;&nbsp;<span class="allMoney">￥1900099.00</span></p>
				</div>
				<div class="fr bagRight">
					<img class="PT10" height="70" src="/pic/weixin/K3activity/k3Icon.png" />
				</div>
			</div>
			<a class="block exchangeBtn walfareBtn heigh40 radius5">10天后可兑换礼金399.00元</a>
		</div> >-->
	</div>

	<div class="screenBkg width100 positionF none"></div>
	<!-- K3礼包详情  -->
	<div class="k3DetailBox k3Box whiteBkg positionF radius5 boxSizing balckTex none" id="k3Box">
		<img class="fr closeBtn" src="${pageContext.request.contextPath}/pic/weixin/closedBtn.png?<%=request.getAttribute("version")%>" />
		<div class="PB10">礼包兑换计划</div>
		<ul class="bagDetail">
			<!-- <li class="colorA3 clearfix PLR5 radius5 remFont28">
				<span class="fl">礼金349.00元</span>
				<span class="fr">已兑换</span>
			</li> -->
		</ul>
	</div>
	<div id="exchangeListPaging" class="MT40 pagingMobile"></div>
	<script type="text/javascript">
		// 获得html标签
		var oHtml = document.documentElement;
		// 页面加载完毕进行函数调用
		getSize();
		function getSize(){
			// 获取屏幕的宽度
			var screenWidth = oHtml.clientWidth;
			oHtml.style.fontSize = screenWidth/(750/40)+'px';
		}

		// 当窗口一段发生改变就触发
		window.onresize = function(){
			getSize();
		}
	</script>
	
    <script src="${pageContext.request.contextPath}/js/weixin/integration/swiper-3.3.1.jquery.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/usedPagList.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>