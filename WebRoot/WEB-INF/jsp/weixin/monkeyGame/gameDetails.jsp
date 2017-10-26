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
	<title>活动细则</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/monkeyGame/monkeyGame.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="noBkg">
	<div class="wrapper">
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_01.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_02.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_03.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_04.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_05.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_06.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_07.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_08.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/img_09.jpg?<%=request.getAttribute("version")%>">
			<div class="width100 textC gamebtnArea">
				<!--微信端按钮-->
				<a class="textC blockC" id="weixin" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/btn.png" class="KCodebtn"></a>
				<!--Ios按钮-->
				<label id="lianbiIos" style="display: none;">
					<input type="button" value="" style="display: none;" id="iosBtn"/>
					<a class="textC blockC" id="lianbiIconBtn"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/btn.png" class="KCodebtn"></a>
				</label>
				<!--安卓按钮-->
				<a class="textC blockC" id="lianbiAndroid" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/monkeyGame/gameDetail/btn.png" class="KCodebtn"></a>		
			</div>	
			
		</div>
	</div>
		
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" />
	<input type="hidden" name="channel" id="channel" value="${channel}" />
	<input type="hidden" name="URL" id="URL" value="${URL}" />
	<script src="${pageContext.request.contextPath}/js/weixin/page/monkeyGame/gameDetails.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

