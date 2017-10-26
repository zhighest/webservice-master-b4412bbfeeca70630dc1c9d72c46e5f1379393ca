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
	<title>活期理财</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="content">
		<div class="detailsPopup">
			<ul class="normalTabUL clearfix">
	<!-- 			<li class="tabLi tabLi1"><a href="#" class="acitive">全部</a><span class="tabArrow grayBkg"></span></li> -->
<!-- 				<li class="tabLi tabLi1 width33_3 active" onclick="tabSwitchLi('tabLi','tabLi1');getOrderProcessedList(1,true);"><a>处理中</a><span class="normalTabArrow grayBkg"></span></li> -->
				<li class="tabLi tabLi2 width50 active" onclick="tabSwitchLi('tabLi','tabLi2');getOrderEarningsList(1,true);"><a>回款中</a><span class="normalTabArrow grayBkg"></span></li>
				<li class="tabLi tabLi3 width50" onclick="tabSwitchLi('tabLi','tabLi3');getOrderFinishList(1,true);"><a>已完成</a><span class="normalTabArrow grayBkg"></span></li>
			</ul>
			<div class="grayBkg productContent blockC width100 outHide" id="purchaseDetailList"></div>
			<div id="purchaseDetailListPaging" class="pagingMobile"></div>
				<div class="detailsPopupDiv ML100P" style="display: none;" id="detailsPopupDiv">
					<div class="textL PTB10 borderB PLR5P">
						<a id="escBtn"><img src="${pageContext.request.contextPath}/pic/weixin/closedBtn1.png?<%=request.getAttribute("version")%>" height="15"></a>
						<font class="fr grayTex">账户明细</font>
					</div>
					<ul class="detailsFlowUl" id="fundFlowList"></ul>
					<div id="fundFlowListPaging" class="pagingMobile"></div>
				</div>
		</div>
	</div>
	<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/demandAssetList.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

