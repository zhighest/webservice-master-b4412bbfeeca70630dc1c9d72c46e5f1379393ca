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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/swiper-3.3.1.min.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/newGiftBag.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body style="background:#EFEFEF;">
	<p class="topRemind PTB10 font14 textL none"></p>
	<div class="clearfix PLR20 MT10 none goSoonVoucher">
		<a class="fr redTex font14" href="${pageContext.request.contextPath}/wxcoupons/soonVoucher">加速券</a>
	</div>

	<div class="giftWrap PLR10 bkg4">
		<!-- <div class="welfareBag k3Bag whiteBkg MT10 positionR">
			<span class="positionA transfer none"></span>
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
		</div> -->
	</div>

	<div class="screenBkg width100 positionF none"></div>
	<a class="block toUsedPageList PTB16 font14" href="${pageContext.request.contextPath}/wxGiftPackage/usedPagList">
		点击查看已兑换的礼包 &gt;
	</a>
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
	<div class="k3DetailBox jsDetailBox whiteBkg positionF radius5 boxSizing balckTex none" id="jsDetailBox">
		<img class="fr closeBtn" src="${pageContext.request.contextPath}/pic/weixin/closedBtn.png?<%=request.getAttribute("version")%>" />
		<div class="PB10">请选择加速券</div>
		<ul class="jsDetail">
			<!-- <li class="colorA3 clearfix PLR5 font14 MB5">
				<div class="fl">
					<img class="checkImg" height="20" src="${pageContext.request.contextPath}/pic/weixin/K3activity/nocheck.png" >
				</div>
				<div class="fl voucherDetail clearfix PLR10 boxSizing">
					<div class="fl blackTex1 remFont28">
						<p class="textL blackTex">K3路由器</p>
						<p class="textL remFont24">2017/03/20到期</p>
					</div>
					<p class="fr remFont28" style="margin-top:0.85rem;">
						<span class="blackTex">30天</span>
					</p>
				</div>
			</li> -->
		</ul>
		<a class="cancelUse aBtn border radius3 inlineB blackTex bkg4">取消</a>
		<a class="confirm aBtn bkg3 radius3 inlineB whiteTex">确认</a>
	</div>
	<!-- 兑换礼包动画 -->
	<div class="alertWrap closeBtn none" id="alertWrap2">
		<div class="luckAlert">
			<div id="confirmBtn" class="confirmBtn">
				<img class="bthImg" src="${pageContext.request.contextPath}/pic/weixin/giftBag/delete.png?<%=request.getAttribute("version")%>"  />
			</div>
			<div class="bthImg2" id="bthImg2"></div>
		</div>
	</div> 
	
	<!-- 兑换福利礼包动画 -->
	<div class="alertWrap opacity0" id="alertWrap">
		<div class="clearAlert clearAlertH"></div>
		<div class="alertConent">
			<div class="divImg">
				<img src="${pageContext.request.contextPath}/pic//weixin/giftBag/assetEx.png?<%=request.getAttribute("version")%>" width="72%"/>
			</div>
			<div class="swiper-container" id="alertCon">
				<div class="swiper-wrapper" id="alertSwiper">
				</div>
			</div>
			<div class="KcodeBtn  width75 MLRA redTex radius5" id="KcodeBtn">兑换</div>
		</div>
		<div  class="clearAlert clearAlertH8"></div>
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
	<script src="${pageContext.request.contextPath}/js/weixin/page/newGiftBag.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>