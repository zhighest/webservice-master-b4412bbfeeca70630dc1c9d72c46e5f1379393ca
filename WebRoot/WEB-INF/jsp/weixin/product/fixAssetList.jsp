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
		<meta name="format-detection" content="telephone=no" />
		<title>定期资产</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/fixAssetList.css?<%=request.getAttribute(" version ")%>" />
		<%@  include file="../header.jsp"%>
	</head>

	<body class="positionR">
		<div class="content">
			<div class="detailsPopup">
				<div class="whiteBkg ">
					<div class="header clearfix positionF">
						<div class="selectType fl positionR" id="rank"> 
							<span class="redTex downArrow">当前订单</span>  
						</div>
						<div class="selectContent fl">
							<ul class="selectList clearfix" id="selectList">
								<li class="textC fl positionR selectItem redTex" value="3">
									投资日期
									<div class="triangle-up positionA"></div>
									<div class="triangle-bottom positionA active"></div>
								</li>
								<li class="textC fl positionR selectItem " value="1">
									到期日期
									<div class="triangle-up positionA"></div>
									<div class="triangle-bottom positionA"></div>
								</li>
								<li class="textC fl positionR selectItem" value="2">
									投资金额
									<div class="triangle-up positionA"></div>
									<div class="triangle-bottom positionA"></div>
								</li>
							</ul>
						</div>
					</div>
					<div class="positionF whiteBkg width100 textL" id="sortType" style="z-index: 2;">
						<ul class="sortTypeContent blackTex1">
							<li class="sortTypeItem redTex PLR20" id="recommend" >当前订单<img height="40%" class="fr confirmIocn" src="${pageContext.request.contextPath}/pic/weixin/icon_selected.png?<%=request.getAttribute("version")%>" /></li>
							<li class="sortTypeItem PLR20" id="complete">历史订单<img height="40%" class="fr none confirmIocn" src="${pageContext.request.contextPath}/pic/weixin/icon_selected.png?<%=request.getAttribute("version")%>"/></li>
							<li class="sortTypeItem PLR20" id="all">全部<img height="40%" class="fr none confirmIocn" src="${pageContext.request.contextPath}/pic/weixin/icon_selected.png?<%=request.getAttribute("version")%>"/></li>
						</ul>
					</div>
				</div>

				<div class="positionF   width100 none rank" id="boxF" style="height: 100%; background: rgba(113,113,113,0.7);"></div>

				<div class="grayBkg productContent blockC width100 outHide PB15">
					<ul id="purchaseDetailList">
					</ul>
				</div>
				<div id="purchaseDetailListPaging" class="pagingMobile"></div>
			</div>
		</div>
		<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
		<div style="display:none" id="formDiv"></div>
		<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/fixAssetList.js?<%=request.getAttribute(" version ")%>"></script>
		<%@  include file="../baiduStatistics.jsp"%>
	</body>

</html>