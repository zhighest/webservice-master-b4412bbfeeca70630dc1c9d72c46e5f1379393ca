<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>2周年  感恩回馈</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamDetail.css?<%=request.getAttribute(" version ")%>" />
		<%@  include file="../../header.jsp"%>
	</head>
	<body>
		<div class="width100 height100">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/activity0.jpg" class="block width100">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/activity1.jpg" class="block width100 goTeam">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/activity2.jpg" class="block width100 ">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/activity3.jpg" class="block width100 goTwoYear">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/activity4.jpg" class="block width100 ">
		</div>

		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
		<%@  include file="../../baiduStatistics.jsp"%>
	</body>
	<script type="text/javascript">
		//
		var channel = $('#channel').val();
		var mobile = $('#mobile').val();

		$('.goTeam').click(function() {
			hrefWhere('team/teamIndex');
		});
		$('.goTwoYear').click(function(){
			hrefWhere('wxAnniversary/toAnniversary');
		})

		function hrefWhere(type) {
			//判断是微信还是app 微信
			if(channel == "LBAndroid") { // 安卓												//安卓
				window.location.href = contextPath + type+'?mobile='+ mobile + '&channel='+channel;
			} else if(channel == "LBIOS") { // ios												//IOS
				window.location.href = contextPath + type+'?mobile='+ mobile + '&channel='+channel;
			} else {
				window.location.href = contextPath + type+'?from=WX';

			}
		}
	</script>

</html>