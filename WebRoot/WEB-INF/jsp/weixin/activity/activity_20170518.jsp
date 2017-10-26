<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE HTML>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Cache-Control" CONTENT="no-cache">		
	<title>518理财盛宴</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity_20170518.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
 </head>
  <body>
  	<div class="main">
        <!-- 第一部分标题banner begin -->
        <div class="header">
        	<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20170518/0518_header.png?<%=request.getAttribute("version")%>" width="100%">
        </div>
        <!-- 第一部分标题banner end -->

        <!-- 第2部分活动规则 begin -->
         <div class="content">
        	<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20170518/0518_content.png?<%=request.getAttribute("version")%>" width="100%">
        </div>
        <!--<div  class="PTB30 positionR clearfix">
            <img src="${pageContext.request.contextPath}/pic/weixin/annualActivity/time.png" width="100%">
        </div>-->
        <!-- 第2部分活动规则 end -->
        <!-- 第3部分投资排名 begin -->
        <div class="positionR clearfix">
            <!-- 投资排名文案区域 -->
            <div class="investRangeBox positionR PB30">
                <img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20170518/0518_investmentRanking.png?<%=request.getAttribute("version")%>" width="100%">
                <div class="outHide positionR investRange MT15 " id="range">
                    <ul id="investRange" class="font14 positionA width95P blockC blackTex">
                        <!-- 投资排名写在JS数组中 -->
                    </ul>
                    <div class="font30 MT5" id="empty"></div>
                </div>
            </div>
        </div>
        <!-- 第3部分投资排名 end -->
        <div class="footer">
        	<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20170518/0518_footer.png?<%=request.getAttribute("version")%>" width="100%">
        </div>	
    </div>	
	 <script>
        var _hmt = _hmt || [];
        (function() {
          var js = document.createElement("script");
          js.src = contextPath+"/js/weixin/page/activity_20170518.js?" + Math.random();
          document.body.appendChild(js);
        })();
   </script>
   <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
