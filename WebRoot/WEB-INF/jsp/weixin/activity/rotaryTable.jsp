<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*"> 
	<title>幸运转出来</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/rotaryTable.css?<%=request.getAttribute("version")%>" />
  <%@  include file="../header.jsp"%>
  <script src="${pageContext.request.contextPath}/js/weixin/page/rotaryTable.js"></script>
 </head> 
  <body>
  <div class="alertWrap none" id="alertWrap"> 
  			<div class="alert">
  				<img  src="${pageContext.request.contextPath}/pic/weixin/activity/rotaryTable/rotaryTable_05.png" width="100%" />
  				
  			</div>
  			<div class="icon" id="icon">
  				<img  src="${pageContext.request.contextPath}/pic/weixin/activity/rotaryTable/icon.png" width="100%"/>
  			</div>
  				<div class="typeface" >
  					<div >获得</div> 
  					<span  id="font" class="typeface2"></span></div>
  	</div>
  	<header class="wrap"> 
  		<img src="${pageContext.request.contextPath}/pic/weixin/activity/rotaryTable/rotaryTable_09.gif"width="100%" />
  			<img src="${pageContext.request.contextPath}/pic/weixin/activity/rotaryTable/rotaryTable_01.png"width="100%" class="none" id="img22"/>
  			<canvas  id="wheelcanvas3" width="422px" height="435px" class="width100"></canvas>
  			<div class="content" >
  				 <img src="${pageContext.request.contextPath}/pic/weixin/activity/rotaryTable/rotaryTable_07.gif" width="100%" />
  				<div class="plate" id="plate">
  					<canvas class="item" id="wheelcanvas" width="422px" height="422px" ></canvas>
  					<canvas class="item1" id="wheelcanvas1" width="422px" height="422px" ></canvas> 
  				</div>
  				<a class="plateBth" id="plateBth" href="javascript:"></a>
  			</div>
  			<div class="num" id="num">剩余抽奖次数(<span id="numNew"></span>)</div>
  	</header>
<div class="wrapOdd">  
  		<p class="common" id="activityDesc"></p>
  		  	<footer class="footer" id="footer">
			  		<p>— 本活动与苹果公司无关 —</p>
			  		<p>— 本次活动最终解释权归“联璧金融”所有 —</p>
  			</footer>
  	</div>
  	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >	
  	<%@  include file="../baiduStatistics.jsp"%>
 </body>
</html>
