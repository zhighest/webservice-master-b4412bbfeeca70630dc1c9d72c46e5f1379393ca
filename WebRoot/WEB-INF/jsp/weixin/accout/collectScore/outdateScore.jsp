<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>积分说明</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/outdataScore.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../../header.jsp"%>
</head>
<body>
 <div class="positionF top width100 none">
	<div class="scoreWarp width100 whiteBkg textC borderTGray">
		<span class="grayTex font18 height60">当前积分</span>
		<div>
			<div class="scoreDiv  positionR inlineB">
				<div id="scoreNum" class="font50 redTex  inlineB">0</div>
				<div class="scoreNotice positionA"></div>
			</div>
		</div>
		<div class="shadow width150P inlineB heigh30"></div>
	</div>
	<div class="heigh30 textL PL5P grayTex font14 bkg4">即将过期的积分</div>
 </div>
	<div class="outdataScore whiteBkg none">
		<ul id="scorelist">
		
		</ul>
		
	</div>
<div id="nullList"></div>	
<div class="scoreExplain PLR5P none">
<!-- <p class="font18 PTB10 ruledetail borderB">规则说明</p> -->
<ul  id="scoreListRule" class="textL font14">
</ul>

<span class='closeBtn width100 textC positionA'><img src="${pageContext.request.contextPath}/pic/weixin/collectScore/close.png?<%=request.getAttribute("version")%>" width="25"/></span>
</div>
	<input id="mobile" name="mobile" type="hidden" value="<%=request.getParameter("mobile")%>">
    <script src="${pageContext.request.contextPath}/js/weixin/page/outdateScore.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>