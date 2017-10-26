<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>实名认证</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    <script type="text/javascript">

	//guanxuxing-add-shebeizhiwen-begin
	 (function() {
	        _fmOpt = {
	            partner: 'lianbi',
	            appName: 'lianbi_web',
	            token: '${token_id}' 
	              };
	        var cimg = new Image(1,1);
	        cimg.onload = function() {
	            _fmOpt.imgLoaded = true;
	        };
	        cimg.src = "${tongDunSrc}?partnerCode=lianbi&appName=lianbi_web&tokenId=" + _fmOpt.token;
	        var fm = document.createElement('script'); fm.type = 'text/javascript'; fm.async = true;
	        fm.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'static.fraudmetrix.cn/fm.js?ver=0.1&t=' + (new Date().getTime()/3600000).toFixed(0);
	        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(fm, s);
	    })();
	//guanxuxing-add-shebeizhiwen-end
	</script>
</head>
<body>
<div class="wrapper PT20">
	<div class="hasTBBorder whiteBkg MB20">
		<div class="whiteBkg width80 blockC PLR5P clearfix PTB15">
			<span class="fl width30">真实姓名</span>
			<input type="text" placeholder="请输入真实姓名" name="nameCard" id="nameCard" class="width70 inputNoborder fr">
		</div>	
	</div>
	<div class="hasTBBorder whiteBkg MB20">
		<div class="whiteBkg width80 blockC PLR5P clearfix PTB15">
			<span class="fl width30">身份认证</span>
			<input type="text" placeholder="请输入身份证号码" name="idCard" id="idCard" class="width70 inputNoborder fr">
		</div>	
	</div>
	<input type="hidden" name="productId" id="productId" value="<%=request.getParameter("productId")%>">
	<input type="hidden" value="${backUrl}" id="backUrl" name="backUrl"/>
	<div class="width80 blockC MT30"><a class="btn width100 block" id="authenticationBtn">实名认证</a></div>
</div>
<script src="${pageContext.request.contextPath}/js/weixin/page/authentication.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>