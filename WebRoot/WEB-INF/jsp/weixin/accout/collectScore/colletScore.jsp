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
	<title>积分</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/collectScore.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../../header.jsp"%>
</head>
<body class="bkg4">
<div class="header whiteTex outHide">
<p class="positionR font18 MB5">我的积分<span class="scoreNotice positionA"></span></p>
<p class="font32 pTB2P " id="useAmount">0</p>
<p class="PB3P MT2P"><span class="outdateScore">即将过期: <span id="invalidAmount" class="font18 PR10">0</span><span class="font15">></span></span></p>
</div>
<ul class="entrance clearfix textL whiteBkg lineHeight100">
	<li class="fl width50 boxSizing borderR positionR ">
		<a href="javascript:;" class="block height100P tapBkg" id="vip">
				<div class="wrap  positionA">
					<div class="redTex font16 PB10" >会员等级</div>
					<div class="font14 grayTex" id="gradeName">普通会员</span></div>
				</div>
		</a>
	</li>
	<li class="fr width50 positionR">
      <a href="javascript:;" class="block height100P tapBkg" id="inOutDetail">
		  <div class="wrap  positionA">
	          <div class="redTex font16 PB10">收支明细</div>
	          <div class="font14 grayTex">查看详情</div>
		  </div>
	  </a>
	</li>
</ul>


<div class="main clearfix whiteBkg MT10 PT10 PLR15 positionR" id="main">
	<h3 class="textL PB10 PT10"><span class="title">积分兑换</span></h3>
	<div class="selectContent" id="selectContent">
		<ul class="selectList clearfix" id="selectList">
		</ul>
	</div>
	<div class="scrollBox none" id="scrollBox"></div>
	<div class="gradientBg positionA"></div>
	
	<ul id="pruductList" class="outHide">
 	</ul>
	<div id="pruductListPaging" class="pagingMobile"></div>
</div>
<div class="bottom positionR grayTex font14 MT10">
<img src="${pageContext.request.contextPath}/pic/weixin/invest/investBkg.png?2016050901" width="100%">
联璧金融
</div>
<div class="scoreExplain PLR5P none">
<!-- <p class="font18 PTB10 ruledetail borderB">规则说明</p> -->
<ul  id="scoreListRule" class="textL font14">
</ul>

<span class='closeBtn width100 textC positionA'><img src="${pageContext.request.contextPath}/pic/weixin/collectScore/close.png?<%=request.getAttribute("version")%>" width="25"/></span>
</div>
<input id="mobile" name="mobile" type="hidden" value="<%=request.getParameter("mobile")%>">
<script src="${pageContext.request.contextPath}/js/weixin/page/collectScore.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../../baiduStatistics.jsp"%>

</body>
</html>

