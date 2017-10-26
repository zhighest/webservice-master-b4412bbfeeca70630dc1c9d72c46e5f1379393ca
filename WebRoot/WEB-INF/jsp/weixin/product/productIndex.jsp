<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
  <meta name="apple-mobile-web-app-capable" content="no" />
  <meta name="apple-touch-fullscreen" content="yes" />
  <meta name="format-detection" content="telephone=no"/>
  <title>投资理财</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/productIndex01.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
  </head>
  <body class="positionR">
    <%@ include file="../tabBar.jsp"%>
    <!-- 公告的二级弹窗 begin-->
    <!-- banner -->
    <div class="width100" id="banner"><img src="" class="width100"></div>
    <div class="positionF none" id="message">
      <div class="positionR changeBox clearfix">
        <img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/messageBox.png?<%=request.getAttribute("version")%>" class="width100">
        <div class="width100 whiteBkg message PB20">
          <div class="width80 blockC">
            <h4 class="lineHeight2x messageTitle"></h4>
            <p class="textL font14 lineHeight1_5x messageMess"></p>
            <a id="know" class="redBkg whiteTex radius5 block MT10 pTB2P">知道了</a>
          </div>
        </div>
      </div>    
    </div>
    <!-- 公告的二级弹窗 end-->
    <div id="content" class="PB10">

    </div>
    <div class="bottom positionR grayTex font14"><img src="${pageContext.request.contextPath}/pic/weixin/invest/investBkg.png?<%=request.getAttribute("version")%>" width="100%">联璧金融</div>
    <input id="pageTag" name="pageTag" type="hidden" value="<%=request.getParameter("pageTag")%>">
    <script src="${pageContext.request.contextPath}/js/weixin/page/productIndex.js"></script>
    <script src="${pageContext.request.contextPath}/js/weixin/page/jquery.kxbdmarquee.js?<%=request.getAttribute("version")%>"></script>
    <script src="${pageContext.request.contextPath}/js/weixin/page/scrollText.js?<%=request.getAttribute("version")%>"></script>
  </body>  
</html>
