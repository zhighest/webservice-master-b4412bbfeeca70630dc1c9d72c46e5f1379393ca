<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>兑奖页面</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/monkeyGame/monkeyGame.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="width90 blockC">
		<div class="pageTitle blockC"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/title1.png"></div>
		<div id="weixinLogin" class="width100 none blockC">
			<div class="popBox positionR">
				<p class="popTitle font16">请下载APP【联璧金融】兑换奖品</p>
				<div class="MT5P"><a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" rel="nofollow"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/btn3.png" width="70%"></a></div>
			</div>	
		</div>
		<div id="APPExchange" class="width100 blockC">
			<div class="popBox positionR">
				<p class="popTitle"><span id="parameter" class="PR10">奖品</span></p>
				<div class="MT5 MB5"><input text="" class="inputTel" placeHolder="请输入兑换手机号码" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" id="phoneNum"></div>
				<div class="MT5P"><a id="exchangeBtn"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/btn1.png" width="50%"></a></div>
			</div>	
		</div>
	</div>	
	<input type="hidden" name="installment" id="installment" value="${installment}" />
	<input type="hidden" name="kind" id="kind" value="${kind}" />
	<input type="hidden" name="prize_msg" id="prize_msg" value="${prize_msg}" />
	<input type="hidden" name="sort" id="sort" value="${sort}" />
	<input type="hidden" name="prize" id="prize" value="${prize}" />
	<input type="hidden" name="URL" id="URL" value="${URL}" />
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" />
	<script src="${pageContext.request.contextPath}/js/weixin/page/monkeyGame/login.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

