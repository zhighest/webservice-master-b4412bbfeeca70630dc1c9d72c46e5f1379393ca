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
    <title>使用帮助</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/showZQHHelp.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
    <body class="positionR">
    <div class="showZQHHelp positionA color9f PLR5P">
        <div class="font18">
            <h3 class="font18 textC PTB10">如何使用智仟汇优惠券</h3> 
        </div>
        <ul class="font14 textL borderTopC8 tipsList"> 
            <li>
                <p>1.优惠券的领取方式及使用规则适用于智仟汇所有新老用户；</p>
            </li>
            <li>
                <p>2.在联璧金融相关活动页面，<em class="textRed">输入手机号码领取</em>优惠券后，<em class="textRed">登录/下载注册智仟汇APP</em>，点击左上角个人中心图标，在弹出的左侧菜单中优惠券一栏查看（用户领取优惠券时输入的手机号码必须与在智仟汇平台注册的手机号码一致方可进行优惠券的查看与使用）；</p>
            </li>
            <li>
                <p>3.用户在<em class="textRed">智仟汇平台购物</em>时，在提交订单页面选择使用优惠券，则可<em class="textRed">抵扣商品部分金额</em>；</p>
            </li>
            <li>
                <p>4.一件商品只能用一张优惠券，相同商品多个数量也只能用一张优惠券，如果是多件商品则可使用多张优惠券；</p>
            </li>
            <li>
                <p>5.所有优惠券相关规则以活动主办方智仟汇解释为准。</p>
            </li>
        </ul>
        <span class="closeBtn positionA">
        	<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/share/close.png?2016050901" width="25">
        </span>
    </div>
        <script src="${pageContext.request.contextPath}/js/weixin/page/showZQHHelp.js?<%=request.getAttribute("version")%>"></script>
        <%@  include file="../baiduStatistics.jsp"%>
    </body>
</html>