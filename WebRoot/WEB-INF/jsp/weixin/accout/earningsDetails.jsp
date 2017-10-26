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
	<title>收益详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/myInterest.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/earningsDetails.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="positionR font14">
	<ul class="grayBkg02 clearfix borderB tabSwitch">
		<li class="width33 fl PTB10 grayTex positionR current tabNav tab1 borderR" onclick="tabSwitchLi('tabNav','tab1');tabSwitchCon('tabContent','tabContent1');tab1Click()">历史年化收益率</li>

		<li class="width33 fl PTB10 grayTex positionR tabNav tab2 borderR" onclick="tabSwitchLi('tabNav','tab2');tabSwitchCon('tabContent','tabContent2');tab2Click()">历史收益</li>
		<li class="width33 fl PTB10 grayTex positionR tabNav tab3" onclick="tabSwitchLi('tabNav','tab3');tabSwitchCon('tabContent','tabContent3');tab3Click()">历史在投本金</li>
	</ul>
	<!-- 历史年化收益率 begin -->
	<div class="tabContent1 tabContent">
		<ul class="clearfix PTB10" id="tabNav">
			<li class="width50 fl grayTex positionR">
				<a class="block width60 PTB5 CUA MLRA">年化收益率</a>
			</li>

			<li class="width50 fl grayTex positionR" onclick="tabSwitchLi('tabBox','tb2');tabSwitchCon('tB2','tabCont2')">
				<a class="block width60 PTB5 MLRA">加息收益率</a>
			</li>
		</ul>
		<!-- 年化收益率 begin -->
		<div id="tabContent1">
			<!-- <div class="MB10">
				<h4 class="grayTex font14 lineHeight1_5x">累计收益(元)</h4>
				<div class="redTex font28">
					<fmt:formatNumber value="${incomeTotal}" pattern="#,##0.00#"/>
				</div>
			</div> -->
			<ul id="totalFlowList" class="width95 blockC PB5">
			</ul>
			<!-- <div id="fundFlowListPaging" class="pagingMobile"></div> -->
		</div>
		<!-- 年化收益率 end -->
		<!-- 加息收益率 begin -->
		<div id="tabContent2" style="display: none;">

			<ul id="addFlowList" class="width95 blockC PB5"></ul>
		</div>
		<!-- 加息收益率 end -->
	</div>
	<!-- 历史收益率 end -->

	<!-- 历史收益 begin -->
	<div class="tabContent2 MT10 tabContent" style="display: none;">
		<div id="totalIncomeAmountBox">
			<h4 class="grayTex font16 MB10">累计收益</h4>
	    	<P class="redTex font24 MB10" id="totalIncomeAmount"></P>
	    </div>
		<ul id="incomeAmount" class="width95 blockC PB5">

		</ul>
	</div>
	<!-- 历史收益 begin -->
	<!-- 历史在投本金 begin -->
	<div class="tabContent3 MT10 tabContent" style="display: none;">
	    <h4 class="grayTex font16 MB10">当日在投本金</h4>
	    <P class="redTex font24 MB10" id="totalInvestAmount"></P>
		<ul id="investAmount" class="width95 blockC PB5"></ul>
	</div>
	<!-- 历史在投本金 begin -->
	<input type="hidden" name="tabSkip" id="tabSkip" value="<%=request.getParameter("tabSkip")%>" >
	<script src="${pageContext.request.contextPath}/js/weixin/integration/swipe.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/earningsDetails.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>