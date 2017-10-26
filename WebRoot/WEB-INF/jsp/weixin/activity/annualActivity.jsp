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
	<title>开门拿红包 天天有惊喜</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/annualActivity.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
 </head>
  <body  style="background: #cd0336;">
  	<div class="main">
        <!-- 第一部分标题banner begin -->
        <img src="${pageContext.request.contextPath}/pic/weixin/annualActivity/titleBanner.jpg" width="100%">
        <!-- 第一部分标题banner end -->

        <!-- 第2部分标题banner begin -->
        <div  class="PTB30 positionR clearfix">
            <img src="${pageContext.request.contextPath}/pic/weixin/annualActivity/time.png" width="100%">
        </div>
        <!-- 第2部分标题banner end -->

        <!-- 第3部分 begin -->
        <div  class="PB30 positionR clearfix">
            <img src="${pageContext.request.contextPath}/pic/weixin/annualActivity/rules.jpg" width="100%">
        </div>
        <!-- 第3部分 end -->
        <!-- 第4部分 begin -->
        <div class="PB20 positionR clearfix">
            <!-- 投资排名文案区域 -->
            <div class="investRangeBox positionR PB30">
                <img src="${pageContext.request.contextPath}/pic/weixin/annualActivity/topImg.jpg" width="100%">
                <div class="outHide positionR investRange MT15">
                    <ul id="investRange" class="font14 positionA width95P blockC blackTex">
                        <!-- 投资排名写在JS数组中 -->
                    </ul>
                </div>
            </div>
        </div>
        <!-- 第4部分 end -->
            
        <div class="giftSend font12">
        	<div class="bottom textC">本次活动最终解释权归“联璧金融”所有</br>本活动与苹果公司无关</div>
        </div>	
    </div>	
	 <script>
        var _hmt = _hmt || [];
        (function() {
          var js = document.createElement("script");
          js.src = contextPath+"/js/weixin/page/annualActivity.js?" + Math.random();
          document.body.appendChild(js);
        })();
   </script>
   <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
