<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>联璧钱包</title>
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/download.css" />
</head>
	<body>
		<div class="width100 positionR downBg height100P">
			<div class="code positionA">
				<img src="${pageContext.request.contextPath}/pic/downLoad/code.png" width="100%">
			</div>
			<div class="downloadBtn positionA width70">
				<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" rel="nofollow">
					<img src="${pageContext.request.contextPath}/pic/downLoad/downBtn.png" width="100%">
				</a>
			</div>
		</div>
		<%@  include file="../baiduStatistics.jsp"%>
	</body>
	<script src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
	<script src="${pageContext.request.contextPath}/js/web/download.js"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/integration.js"></script>
	<script>
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?b1b74466da05b847b22ff3790e88b9a1";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
</script>
</html>
