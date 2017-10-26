<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/usedVoucher.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
    <!-- 趣夺宝券 -->
    <ul class="qdbVoucher PLR3P none">
        <!-- <li class="usedVoucher whiteBkg positionR MT10">
            <div class="borderDiv boxSizing">
                <div class="borderDiv1 boxSizing PTB10 font14 clearfix">
                    <div class="fl vouName textL boxSizing">
                        <p>智仟汇“趣夺宝”兑换券</p>
                        <p class="">2017/02/19已使用</p>
                    </div>
                    <div class="fl vouMoney">
                        <p class="font18">￥ <span class="font24">1</span></p>
                        <p>智豆</p>
                    </div>
                </div>
            </div>
            <img class="positionA usedIcon" height="45" src="/pic/weixin/K3activity/usedIcon.png" >
        </li>
        <li class="expiredVoucher whiteBkg positionR MT10">
            <div class="borderDiv boxSizing">
                <div class="borderDiv1 boxSizing PTB10 font14 clearfix">
                    <div class="fl vouName textL boxSizing">
                        <p>智仟汇“趣夺宝”兑换券</p>
                        <p class="">2017/02/19已过期</p>
                    </div>
                    <div class="fl vouMoney">
                        <p class="font18">￥ <span class="font24">1</span></p>
                        <p>智豆</p>
                    </div>
                </div>
            </div>
            <img class="positionA usedIcon" height="45" src="/pic/weixin/K3activity/expiredIcon.png" >
        </li> -->
    </ul>

    <!-- 台数券 -->
    <ul class="tsVoucher PLR3P none">
        <li class="textR MT10">
            <a class="redTex font14" href="${pageContext.request.contextPath}/wxcoupons/tsVoucher">获取K码券</a>
        </li>
        <!-- <li class="usedVoucher whiteBkg positionR MT10">
            <div class="borderDiv boxSizing">
                <div class="borderDiv1 boxSizing PTB10 font14">
                    <div style="line-height:25px;">
                        <p>K3路由器台数券</p>
                        <p class="">2017/02/19 13:56</p>
                    </div>
                </div>
            </div>
            <img class="positionA usedIcon" height="45" src="/pic/weixin/K3activity/usedIcon.png" >
        </li>
        <li class="expiredVoucher whiteBkg positionR MT10">
            <div class="borderDiv boxSizing">
                <div class="borderDiv1 boxSizing PTB10 font14 clearfix">
                    <div style="line-height:25px;">
                        <p>K3路由器台数券</p>
                        <p class="">2017/02/19 13:56</p>
                    </div>
                </div>
            </div>
            <img class="positionA usedIcon" height="45" src="/pic/weixin/K3activity/expiredIcon.png" >
        </li> -->
    </ul>
    <ul class="jsVoucher PLR3P none">
        <li class="textR MT10">
            <a class="redTex font14" href="${pageContext.request.contextPath}/wxcoupons/jsVoucher">获取加速券</a>
        </li>
    </ul>
    <div id="exchangeListPaging" class="MT40 pagingMobile"></div>
    <script src="${pageContext.request.contextPath}/js/weixin/page/usedVoucher.js?<%=request.getAttribute("version")%>"></script>
    <%@  include file="../baiduStatistics.jsp"%>
</body>
</html>