<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>确认支付页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positonR">
	<div class="PB50">
		<div class="wrapper PT10P">
			<div class="width90 blockC">
					<div class="MT30">
						<!-- 手机号 -->
						<input type="hidden"  id="bankName" name="bankName" value="${bankName}">
						<input type="hidden"  id="bankCode" name="bankCode" value="${bankCode}">
						<input type="hidden"  id="cardNo" name="cardNo" value="${cardNo}">
						<input type="hidden"  id="moneyOrder" name="moneyOrder" value="${moneyOrder}">
						<input type="hidden"  id="userCardInfoCount" name="userCardInfoCount" value="${userCardInfoCount}">
						<input type="hidden"  id="idCard" name="idCard" value="${idCard}">
						<input type="hidden"  id="identityName" name="identityName" value="${identityName}">
						<h4 class="MB10 grayTex">银行预留手机号</h4>
						<input type="tel" name="phoneNum" id="phoneNum" value="${cardPhone}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="银行预留手机号" class="inputBkg1 MB20 width100" />
						<div class="clearfix">
						<!-- 验证码 -->
							<input type="tel" id="checkCode" name="checkCode"  placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputBkg1 MB20 width70 fl" />
							<a class="btn width25 fr opacity PLR1P smallBtn1" id="gainCode">获取<br/>验证码</a>
						</div>
						<input class="none" id="loginFlag" type="text" name="loginFlag" value="2">
					</div>
					<input type="hidden"  id="paytoken" name="paytoken">
					<input type="hidden"  id="orderNo" name="orderNo">
					<div><a class="btn width100 block forbid" id="checkTopUpSubmit">确认支付</a></div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
	</script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/zshtTopUp.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
