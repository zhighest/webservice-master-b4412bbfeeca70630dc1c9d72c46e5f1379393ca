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
<body class="positionR useInfoBody">
	<div class="width90 blockC MB20">
		<div class="pageTitleUserInfo blockC"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/title1.png"></div>
		<div class="width100 listDiv" id="prizeList"></div>
		<div class="width90 blockC none" id="noResult">
			<div class="popBox positionR">
				<p class="popTitle Font25">提示</p>
				<div class="MT5P font14">暂无兑奖信息</div>
			</div>	
		</div>
		<div id="weixinLogin" class="width100 none blockC">
			<div class="popBox positionR">
				<p class="popTitle font16">请下载APP【联璧金融】兑换奖品</p>
				<div class="MT5P"><a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" rel="nofollow"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/btn3.png" width="70%"></a></div>
			</div>	
		</div>
	</div>
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" />
	<input type="hidden" name="channel" id="channel" value="${channel}" />
	<div id="formDiv" style="display:none;"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/monkeyGame/userInfo.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

