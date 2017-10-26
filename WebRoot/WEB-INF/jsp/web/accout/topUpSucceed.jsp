<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>充值</title>
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/topUp.css?<%=request.getAttribute("version")%>"/>
</head>
<body class="positionR" style="background:#e7e7e7;">
	<div class="PT5P positionR clearfix" style="border:solid 2px green">
		<%@  include file="../header.jsp"%>
		<div class="PTB10P width70 radius5 whiteBkg blockC MB20 positionR clearfix outHide">
			<c:choose>
				<c:when test="${isSucceed}">
					<img src="${pageContext.request.contextPath}/pic/web/successd.png" alt="" height="120">
					<div class="font20 blackTex MT20">恭喜您，支付成功！</div>
					<p class="font16 grayTex MT10">请稍后确认账户余额（预期2分钟左右）</p>
				</c:when>
				<c:otherwise>
					<img src="${pageContext.request.contextPath}/pic/web/iconClose.png" alt="" height="120">
					<div class="font20 blackTex MT20">很抱歉，支付失败！</div>
				</c:otherwise>
			</c:choose>
			<div class="width80 blockC MT50">
				<a class="btn width30 block blockC font18 radius5" href="${pageContext.request.contextPath}/trade/goAccoutOverview">立刻去我的账户</a>
			</div>
		</div>
	</div>
	<div class="positionR width100 footer">
		<%@  include file="../footer.jsp"%>
	</div>
	<%@  include file="../../weixin/baiduStatistics.jsp"%>
</body>
</html>