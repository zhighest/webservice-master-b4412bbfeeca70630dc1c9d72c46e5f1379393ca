<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../../common.jsp" %>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="no" />
    <meta name="apple-touch-fullscreen" content="yes" />
    <meta name="format-detection" content="telephone=no"/>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <title>组队投资 一起赚奖励</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamLogin.css?<%=request.getAttribute("version")%>" />

    <%@  include file="../../header.jsp" %>
    <script type="text/javascript">

    //guanxuxing-add-shebeizhiwen-begin
    (function() {
        _fmOpt = {
        partner: 'lianbi',
        appName: 'lianbi_web',
        token: '${token_id}'
        };
        var cimg = new Image(1,1);
        cimg.onload = function() {
        _fmOpt.imgLoaded = true;
        };
        cimg.src = "${tongDunSrc}?partnerCode=lianbi&appName=lianbi_web&tokenId=" + _fmOpt.token;
        var fm = document.createElement('script'); fm.type = 'text/javascript'; fm.async = true;
        fm.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'static.fraudmetrix.cn/fm.js?ver=0.1&t=' + (new Date().getTime()/3600000).toFixed(0);
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(fm, s);
    })();
    //guanxuxing-add-shebeizhiwen-end
    </script>
</head>
<body>

    <div class="teamWrapper width100 positionR">
         <%--<img class="teamWrapperImg" src="${pageContext.request.contextPath}/pic/weixin/activity/team/login-bg.png?<%=request.getAttribute("version")%>">--%>
        <div class="teamText width100 PT20P">
            <img src="${pageContext.request.contextPath}/pic/weixin/activity/team/themeWords2x.png?<%=request.getAttribute("version")%>">
        </div>

        <div class="login-wrapper">
            <form id="loginForm" action="${pageContext.request.contextPath}/wxuser/login<%=queryString%>" method="POST">
                <div class="phoneNum width80 ML10P">
                    <input class="width95 inputNoborder" type="tel" placeholder="请输入手机号" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);">
                </div>

                <div class="verification width80 ML10P MT2P positionR">
                    <input class="width95 inputNoborder" placeholder="请输入验证码" type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" />
                    <span class="positionA code" id="gainCode">获取验证码</span>
                    <input class="none" id="loginFlag" type="text" name="loginFlag" value="2">
                </div>
                <input type="hidden" name="parm" id="parm" value="<%=request.getQueryString()%>">
                <div class="login width80 ML10P MT2P" id="loginSubmit">
                    <a >登 录</a>
                     <%--<input type="submit" value="Submit">--%>
                </div>
                <div class="MT5 textC whiteTex clearfix MR15P">
                    <a class="fr whiteTex" href="${pageContext.request.contextPath}/team/teamRegister<%=queryString%>">立即注册</a>
                </div>
                <input type="hidden" name="mobileNumber" id="mobileNumber" value="<%=request.getParameter("mobileNumber")==null?request.getAttribute("mobileNumber"):request.getParameter("mobileNumber")%>" >
                <input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")==null?request.getAttribute("channel"):request.getParameter("channel")%>">

            </form>

        </div>
        <p class="font12 grayText1 MT20 copyright">此次活动由联璧金融发起<br/>若对活动规则存在疑问,请与联璧金融客服联系咨询</p>
    </div>

    <input type="hidden" name="userId" id="userId" value="${userId}" >
    <input type="hidden" name="mobile" id="mobile" value="${mobile}" >

    <input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
    <input type="hidden" name="teamId" id="teamId" value="<%=request.getParameter("teamId")%>" >
    <input type="hidden" name="teamName" id="teamName" value="<%=request.getParameter("teamName")%>" >
    <input type="hidden" name="creatTeam" id="creatTeam" value="<%=request.getParameter("creatTeam")%>" >
    <input type="hidden" name="jionTeam" id="jionTeam" value="<%=request.getParameter("jionTeam")%>" >
    <input type="hidden" name="invitationCode" id="invitationCode" value="<%=request.getParameter("invitationCode")%>" >

    <script type="text/javascript">
        var contextPath = "${pageContext.request.contextPath}";
        var message = "${message}";
    </script>
    <script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
    <script src="${pageContext.request.contextPath}/js/weixin/page/team/teamLogin.js?<%=request.getAttribute("version")%>"></script>
    <%@  include file="../../baiduStatistics.jsp" %>
</body>
</html>