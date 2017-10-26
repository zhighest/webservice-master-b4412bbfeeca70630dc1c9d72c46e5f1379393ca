<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />

		<title>二维码登录</title>
		<style>
			#QR_code {
				width: 200px;
				height: 200px;
			}
			
			#json {
				margin-top: 200px;
			}
		</style>
	</head>

	<body>
		<div id="QR_code"></div>
		<div id="json"></div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration/jquery-1.8.2.min.js?<%=request.getAttribute(" version ")%>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration.js?<%=request.getAttribute(" version ")%>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/jquery.qrcode.min.js?<%=request.getAttribute(" version ")%>"></script>
		<script>
			var contextPath = "";

			function getRealPath() {
				//获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
				var curWwwPath = window.document.location.href;
				//获取主机地址之后的目录，如： myproj/view/my.jsp
				var pathName = window.document.location.pathname;
				var pos = curWwwPath.lastIndexOf(pathName);
				//获取主机地址，如： http://localhost:8083
				var localhostPaht = curWwwPath.substring(0, pos);
				//获取带"/"的项目名，如：/myproj
				var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
				if(projectName.indexOf('shop-webservice') >= 0) {
					contextPath = localhostPaht + projectName + "/";
				} else {
					contextPath = localhostPaht + "/";

				}
				//得到了 http://localhost:8083/myproj
				//contextPath = localhostPaht + projectName + '/';
			}
			getRealPath();
//			contextPath += 'webservice/'
			//http://222.73.156.45:7080/
			//http://222.73.156.45:7080/webservice/qrcode/deleteQrCodeForL
			var end = new Date();
			end = end.getTime() + 10 * 60 * 1000;
			var codepool = '1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM';
			var code = "";
			setTimeout(function() {
				window.location.reload();
			}, 10 * 60 * 1000)
			for(var i = 0; i < 10; i++) {
				code += codepool.charAt(GetRandomNum(0, codepool.length))
			}

			function GetRandomNum(Min, Max) {
				return Math.floor(Math.random() * (Max - Min) + Min);
			}
			//code="qwertyuio1";
			var json = '{"type":"login","qrCode":"' + code + '","endDate":"' + end.toString() + '"}'
			$('#json').html(json);

			//contextPath="https"+window.location.href.substring(4,contextPath.length)
			$('#QR_code').qrcode(json).request(contextPath + "qrcode/deleteQrCodeForLogin?qrCode=" + code, check);

			function check(data) {
				if(data === "SUCCESS") {
					window.location.href = contextPath + "trade/goAccoutOverview";
				}
			}
		</script>
	</body>

</html>