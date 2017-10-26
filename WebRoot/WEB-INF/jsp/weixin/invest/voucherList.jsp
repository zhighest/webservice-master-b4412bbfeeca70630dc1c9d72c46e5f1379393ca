<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<%
String tranAmount=request.getParameter("tranAmount");

%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>手续费抵用券</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/voucherList.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.qrcode.min.js"></script> -->
</head>
<body>
	<div class="wrapper">
		<div class="width94 blockC">
			<ul id="myList">
			</ul>
			
			<div class="textC grayTex PB10" id="proListPaging" class="pagingMobile"></div>
		</div>
	</div>
	<div class="alertBg width100 height100P positionF" style="display:none;">
		<div class="radius5 whiteBkg width80 alertBox positionA textC">
			<h3 class="font20 PT15 redTex">提&nbsp;示</h3>
			<p id="tips" class="font16 blackTex">是否使用手续费抵用券</p>
			<a class="inlineB grayBkg width35 ML10 MR10 PT5 PB5 grayTex" id="cancel">取消</a>
			<a class="inlineB redBkg width35 ML10 MR10 PT5 PB5 whiteTex" id="enterBtn">确定</a>
		</div>
	</div>
	<div style="display:none" id="formDiv"></div>
	<input type="hidden" name="tranAmount" id="tranAmount" value="${param.tranAmount}" >
	<input type="hidden" name="couponId" id="couponId" value="${param.couponId}" >
	<%@  include file="../baiduStatistics.jsp"%>
	<input id="URL" name="URL" type="hidden" value="${URL}">	
	<script src="${pageContext.request.contextPath}/js/weixin/page/voucherList.js?<%=request.getAttribute("version")%>"></script>		
</body>
</html>

