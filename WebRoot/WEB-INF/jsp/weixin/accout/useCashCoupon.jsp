    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
        <%@ page contentType="text/html;charset=UTF-8" %>
        <%@  include file="../../common.jsp" %>
        <!DOCTYPE HTML>
        <html>
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta name="apple-mobile-web-app-capable" content="no" />
        <meta name="apple-touch-fullscreen" content="yes" />
        <meta name="format-detection" content="telephone=no"/>
        <meta name="x5-orientation" content="portrait">
        <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=0,minimum-scale=1.0,maximum-scale=1.0" />
        <title>兑换券</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/useCashCoupon.css?<%=request.getAttribute("version")%>" />
        <%@  include file="../header.jsp"%>
        </head>
<body class="positionR">
    <div class="wrapper useCashCoupon">
            <div class="phoneIfo MT20 MB20 font16 PLR4P whiteBkg">
                  <input class="width100 phoneVal" type="text" placeholder="请填入需要领取的手机号" id="phoneNo">
            </div>
            <ul class="whiteBkg PLR4P textL">
                        <li class="height44 font14">
                            <span class="color333 strong" id="couponIfo"></span>
                    </li>
                    <li class="height44 borderTB font12 color666">
                            <span>有效期至：</span>
                            <em id="validityDate"></em>
                    </li>
                    <li class="font12 color666 PTB16 lineHeight1_5x">
                             <span id="voucherDescribe"></span>
                    </li>
            </ul>
            <div class="MT20 PLR4P">
                 <a class="btn radius5 width100 block" id="getCashCoupon">立即领取</a>
            </div>
            <p class="textL PLR4P PT12" id="showHelp">
                 <a class="font12 color666 clearfix PL10">
                    <span class="howToUseIcon fl MR10" ><img src="../pic/weixin/useCashCoupon/howToUse.png" class="width100" alt="如何使用"></span>
                    <em class="fl">如何使用智仟汇抵用券</em>
                 </a> 
            </p>
    </div>
    <input type="hidden" name="voucherId" id="voucherId" value="<%=request.getParameter("voucherId")%>" >
    <input type="hidden" name="userId" id="userId" value="<%=request.getParameter("userId")%>" >
    <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
    <script src="${pageContext.request.contextPath}/js/weixin/page/useCashCoupon.js?<%=request.getAttribute("version")%>"></script>
    <%@  include file="../baiduStatistics.jsp"%>
</body>
</html>