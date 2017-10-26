<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>抽奖</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity_20151212.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
  
  <body onload="init()">
  	<div class="width100 PT10P height100P content positionR">
        <img class="positionA yiyIcon" src="${pageContext.request.contextPath}/pic/weixin/yiyIcon.png" width="40%" alt="">
        <div class="redBox width90 blockC"></div>
        <div class="center width100 height100P positionA">
          <div class="centerBox">
            <div class="yellowBox">
              <div class="blockC handImgPaused" id="handImg">
                <img src="${pageContext.request.contextPath}/pic/weixin/handImg.png" width="100%" alt="">
              </div>
            </div>
            <a class="btnYiy font24 blockC block redTex whiteBkg radius5 heigh40" id="playBtn">摇一摇</a>
            <a class="residue blockC block lineHeight2x whiteTex">您还可以摇奖<span class="residueNum font20" id="draw_rest_count">0</span>次</a>
          </div>
          <dl class="whiteTex textL MT10P lineHeight1_5x PLR5P">
            <dt class="font18">规则说明</dt>
            <dd>1.活期收益每满10元可以获得一次抽奖机会。</dd>
            <dd>2.每次抽奖可以获得一张最低0.1%最高1%的1日加息券</dd>
            <dd>3.使用加息券后当天收益加息，每日最多可以加息1%</dd>
            <dd>4.仅限于活期产品，可以与其他活期加息券同时使用</dd>
          </dl>
          <!-- <p class="textC whiteTex font14 MB10">*此活动与苹果公司无关</p> -->
        </div>
  	</div>
  	<div class="alertBg width100 height100P positionF none" id="popBg" style="z-index: 1000;"></div>
  	<div class="alertBox width90 whiteBkg radius5 positionF none" id="pop" style="z-index: 1001;">
  		<p class="textC blackTex none" id="tips1">恭喜您,获得一张<span class="redTex" id="addNum"></span>的加息券</p>
  		<p class="textC blackTex none" id="tips2">您的摇一摇机会已经用完啦<br/>请下次再来!</p>
  		<button class="width90 whiteTex redBkg radius5" id="escBtn">确&nbsp;定</button>
  	</div>
  	<input type="hidden" name="userId" id="userId" value="${userId}" >
    <script src="${pageContext.request.contextPath}/js/weixin/page/activity_20151212.js?<%=request.getAttribute("version")%>" Charset="UTF-8" type="text/javascript"></script>
    <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
