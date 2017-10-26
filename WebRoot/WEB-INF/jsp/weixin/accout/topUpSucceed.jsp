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
	<title>充值</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header2.jsp"%> 
</head>
<body>
	<div class="PT40P">
		<c:choose>
			<c:when test="${isSucceed}">
				<img src="${pageContext.request.contextPath}/pic/weixin/successd.png?<%=request.getAttribute("version")%>" alt="" height="120">
				<div class="font20 blackTex MT20">充值成功</div>
				<p class="font16 grayTex MT10">请稍后确认账户余额（预期2分钟左右）</p>
			</c:when>
			<c:otherwise>
				<img src="${pageContext.request.contextPath}/pic/weixin/iconClose.png?<%=request.getAttribute("version")%>" alt="" height="120">
				<div class="font20 blackTex MT20">充值失败</div>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="width80 blockC MT30">
	<a class="btn width100 fr font18 radius5" href="javascript:;" id="succeed">下一步</a>
	<%-- <a class="btn width46 fl font18 radius5" href="${pageContext.request.contextPath}/wxtrade/goMyAsset">我的账户</a> --%>
	<%-- <c:choose>
		<c:when test="${continueButtonFalg}">
			<a class="btn width46 fr font18 radius5" href="${topUpBackUrl}">继续理财</a>
		</c:when>
		<c:otherwise>
			<a class="btn width46 fr font18 radius5" href="${pageContext.request.contextPath}/wxproduct/goDemandProductIndex?${parm}">去理财</a>
		</c:otherwise>
	</c:choose> --%>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
<script>
	$(function(){
	if(top.location!==self.location){  
	   $("#succeed").click(function(){
	   	   window.parent.freshAccountBalance();
	   })
	} else{
		 $("#succeed").click(function(){
		 	window.location.href="${pageContext.request.contextPath}/wxtrade/goMyAsset"
		 })
	}
})
</script>
</body>
</html>