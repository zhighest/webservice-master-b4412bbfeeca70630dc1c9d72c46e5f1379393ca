<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>媒体报道—联璧金融</title>
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
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/activityDetail.css?<%=request.getAttribute("version")%>"/>
  </head>
  <body class="fontFamily">
  <div id="wrap"> 
      <div id="main">
        <c:set var="pageIndex" value="4"/>
        <%@  include file="../header.jsp"%>
        <!-- banner begin -->
        <div class="width100"> 
          <div class="activityBanner positionR">
            <div class="wrapper positionR clearfix whiteTex">
              <span class="font48 show1 positionA New">NEW</span>
              <div class="show2 positionA new">
                <div class="shortShortLine redBkg MT50 MB10"></div>
                <p class="font18">Media reports</p>
                <p class="font12">媒体报道</p>
              </div>
            </div>
          </div>
          <!-- banner end -->
          <div class="wrapper blockC clearfix">
            <div class="PT50">
                <div class="textC font24 clearfix redBorder">
                  <span class="blackTex" id="mediaTitle">标题</span>
                  <p class="grayTex99 font12" id="mediaTime">日期</p>
                  <div class="littleShortLine redBkg MT15 blockC"></div>
              </div>
              <div class="whiteBkg PTB20 MT30 MB30">
                <div class="width90 blockC grayTex61" id="mediaDetail">内容</div>
              </div>
            </div>
          </div>
      </div>
    </div> 
    </div> 
  <%@  include file="../footer.jsp"%>
  <%@  include file="../baiduStatistics.jsp"%>
  <input type="hidden" name="noticeId" id="noticeId" value="${noticeId}" >
  <script src="${pageContext.request.contextPath}/js/web/page/subBanner.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/web/page/mediaDetail.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>

