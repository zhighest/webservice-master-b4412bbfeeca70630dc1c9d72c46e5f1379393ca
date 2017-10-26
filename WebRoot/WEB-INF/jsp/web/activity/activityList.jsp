<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>最新活动-联璧金融</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
  <meta name="apple-mobile-web-app-capable" content="no" />
  <meta name="apple-touch-fullscreen" content="yes" />
  <meta name="format-detection" content="telephone=no"/>
  <meta name="description" content="联璧金融专题频道为用户提供最新的联璧金融专题、活动、互动、有奖竞猜以及注册送好礼等信息"/>
  <meta name="keywords" content="联璧金融专题，联璧金融活动"/>
  <link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/activityList.css?<%=request.getAttribute("version")%>"/>
  </head>
  <body class="fontFamily whiteBkg">
  <div id="wrap"> 
      <div id="main">
        <c:set var="pageIndex" value="3"/>
        <%@  include file="../header.jsp"%>
        <!-- banner begin -->
        <div class="width100"> 
          <div class="activityBanner positionR">
            <div class="wrapper positionR clearfix whiteTex">
              <span class="font48 show1 positionA New">NEW</span>
              <div class="show2 positionA new">
                <div class="shortShortLine redBkg MT50 MB10"></div>
                <p class="font18">Recently events</p>
                <p class="font12">最新活动</p>
              </div>
            </div>
          </div>
          <!-- banner end -->

          <div class="wrapper PT50">
            <p class="subTitle grayTex61">最新活动</p>
            <p class="subDes grayTex61T">Recently events</p> 
          </div>

          <div class="PB50">
            <ul class="wrapper clearfix boxSizing" id="mediaList">
            </ul>
            <div id="mediaListPaging" class="textC grayTex PT10"></div>
            <div class="clearfix none lrBtn">
              <div class="fr MR6P font30">
                <a class="fl grayTex MR10 PLR10 heigh35 cursor"><</a>
                <a class="fl redTex PLR10 heigh35 cursor">></a>
              </div>
            </div>
          </div>
      </div>
    </div> 
    </div> 
  <%@  include file="../footer.jsp"%>
  <%@  include file="../baiduStatistics.jsp"%>
  <div style="display:none" id="formDiv"></div>
  <script src="${pageContext.request.contextPath}/js/web/page/activityList.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>



