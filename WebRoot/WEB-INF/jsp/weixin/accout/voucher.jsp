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
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<title>代金券</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/voucher.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%> 
</head>
<body>
		<div class="topWrap  heigh40 positionF width100" style="top:0">
			<div class="topWrapDiv whiteBkg positionR" id="topWrapDiv" style="z-index: 100;">
				<div class="topWrapLeft inlineB  fl   positionR redTex"  id="classify">
					<span class="downArrow">全部</span>
				</div>
				<ul class="topWrapRight inlineB "  id="sortType">
					<li class="triangleDown triangleUp positionR  inlineB redTex triangleUpRed whiteBkg" value="1" id="sortTypeLi" orderBy="asc">有效期</li>
					<li class="triangleDown triangleUp positionR  inlineB whiteBkg" value="2" orderBy="desc">投资金额</li>
					<li class="triangleDown triangleUp positionR  inlineB whiteBkg" value="3" orderBy="desc">代金券面值</li>
				</ul>			
			</div>
			<div class="positionA blackTex1 whiteBkg width100 textL sortType"  style="z-index: 50;" id="supernatant">
					<div class="PLR15 sortType1 borderB whiteBkg" id="sortType1" value="108">定期</div>
					<div class="PLR15 sortType2 borderB whiteBkg" id="sortType2" value="109">零钱</div>
					<div class="PLR15 sortType3 sortTypeCur redTex whiteBkg" id="sortType3" value="">全部</div>				
			</div>
			<div class="positionF width100  none" id="boxF" ></div>
		</div>
	<ul class="PLR3P MT20 MT50" id="voucherList">
	</ul>
	<div id="voucherListPaging" class="MT40 pagingMobile"></div>
	<div class="investAlertBg none width100 height100P positionF" id="investAlertBg">
		<div class="investAlertBox radius5 whiteBkg positionA width90">
			<h4 class="blackTex positionR PTB10">投资列表<img class="positionA closedBtn" src="${pageContext.request.contextPath}/pic/weixin/closedBtn.png" width="20px"></h4>
			<ul class="font14 " id="productList">
			</ul>
		</div>
	</div>
	<div style="display:none" id="formDiv"></div>
	<input type="hidden" name="sloanId" id="sloanId" value="<%=request.getParameter("sloanId")%>" >
	<input type="hidden" name="loanId" id="loanId" value="<%=request.getParameter("loanId")%>" >
	<input type="hidden" name="planId" id="planId" value="<%=request.getParameter("planId")%>" >
	<input type="hidden" name="rateIds" id="rateIds" value="<%=request.getParameter("rateIds")%>" >
	<input type="hidden" name="rateRises" id="rateRises" value="<%=request.getParameter("rateRises")%>" >
	<input type="hidden" name="from" id="from" value="<%=request.getParameter("from")%>" >
	<input type="hidden" name="productName" id="productName" value="<%=request.getParameter("productName")%>" >
	<input type="hidden" name="adjustRate" id="adjustRate" value="<%=request.getParameter("adjustRate")%>">
	<input type="hidden" name="fromUrl" id="fromUrl" value="<%=request.getParameter("fromUrl")%>">
	<input type="hidden" name="inputAmount" id="inputAmount" value="<%=request.getParameter("inputAmount")%>"><!-- 从购买页带过来的购买金额 -->
	<input type="hidden" name="finishedRatio" id="finishedRatio" value="<%=request.getParameter("finishedRatio")%>"><!-- 从购买页带过来的购买进度 -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/voucher.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

