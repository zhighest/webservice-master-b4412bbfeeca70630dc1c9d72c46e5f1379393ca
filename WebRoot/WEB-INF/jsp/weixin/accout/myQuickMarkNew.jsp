<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>我的二维码</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/myQuickMark.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>

</head>
<body>
	<div class="width80 ML10P whiteBkg markBox">
		<div class="messageTable ML10P PT30 clearfix">
			<table>
				<tr>
					<td>
						<div class="imgBox outHide radius100 block">
							<img id="defaultAvatar" src="${pageContext.request.contextPath}/pic/weixin/version1125/defaultAvatar.png?<%=request.getAttribute("version")%>" alt="">
						</div>
					</td>
					<td class="font12 blackTex" id="mobileNum"></td>
				</tr>
			</table>

		</div>
		<div class="MT20">
			<img src="http://s.jiathis.com/qrcode.php?url=${URL}/wxuser/goInviteFriendForApp?mobile=${mobile}"class="width80">
		</div>
		<p class="font12 grayTex PT10 PB20">扫一扫上面的二维码图案，邀请好友</p>
	</div>
	<input id="mobile" name="mobile" type="hidden" value="${mobile}">
	<input id="URL" name="URL" type="hidden" value="${URL}">

	<script src="${pageContext.request.contextPath}/js/weixin/page/myQuickMark.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
