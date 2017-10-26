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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/giftBag.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
	<body >
		<div class="header whiteTex positionR ">
			<div class="headerPT redColor">礼包资产(元)</div>
			<div class="headerMT helveticaneue numFont" id="num">0,00</div>
			<div class="headerSec positionA">
				<div class=" width50 positionA headerL zIndex" id="headerL" ></div>
				<div class=" width50 positionA headerR zIndex" id="headerR"></div>
				<div class="headerBtom">
						<span class="fl headerFl borderBox " id="headerFl">K码礼包</span>
						<span class="fr headerFr borderBox opacity5"id="headerFr">福利礼包</span>
				</div>
			</div>
		</div>
		<div class="content ">
			<ul id="contentUl" >
				<div class="listNull none" id="contentListNull">
			        <img src="${pageContext.request.contextPath}/pic/weixin/list.png">
			         <p class="p1">暂无礼包</p>
			         <p class="p2">如果有礼包，您将在这里看到</p>
	        	</div>
			</ul>
			<div class="contentUl"></div>
		</div>
		<div id="purchaseDetailListPaging" class="pagingMobile"></div>
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
		<div class="alertWrap none" id="alertWrap2">
			<div class="luckAlert">
				<div id="confirmBtn" class="confirmBtn">
				<img class="bthImg" src="${pageContext.request.contextPath}/pic/weixin/giftBag/delete.png?<%=request.getAttribute("version")%>"  />
				</div>
				<div class="bthImg2" id="bthImg2"></div>
			</div>
		</div>
		<input type="hidden" name="userId" id="userId" value="<%=request.getParameter("userId")%>" >
		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
		<script src="${pageContext.request.contextPath}/js/weixin/integration/swiper-3.3.1.jquery.min.js?<%=request.getAttribute("version")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/giftBag.js"></script> 
		<%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
