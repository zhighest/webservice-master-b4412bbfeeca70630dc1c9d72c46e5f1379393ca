<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../../common.jsp"%>
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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamCorps.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../../header.jsp"%>

    <style>
        .width18{
            width: 18%;
        }
    </style>
</head>
<body>
<div class="width100 teamWrap PB10 positionR">
	<div class="rewardBox positionA font12 whiteTex">活动奖励</div>
        <div class="bannerTex positionR">
            <img src="${pageContext.request.contextPath}/pic/weixin/activity/team/Themewords.png?<%=request.getAttribute("version")%>" />
        </div>
        <p class="whiteTex font12 teamTime positionR"><!--活动日期： 2017年9月15日—2017年10月15日--></p>
        <div class="teamRule PLR3P">
            <p class="ruleTitle whiteTex positionR font14 textL">
                <img class="verMid" src="${pageContext.request.contextPath}/pic/weixin/activity/team/rule.png?<%=request.getAttribute("version")%>" />活/动/规/则
            </p>
            <div class='ruleTex font12 textL outHide'>
                <div class="outHide scrollTex">
                    <%--<p class="MT7">--%>
                        <%--1、组建自己的投资战队，队伍人数最少2人，最多8人（包括队长），创建人即为队长，每人仅可创建或加入一支投资战队，只能选择一种角色，一旦加入队伍，中途不可退出。--%>
                    <%--</p>--%>
                    <%--<p class="MT7">--%>
                        <%--2、战队创建后，需分享战队链接，好友只可通过链接加入战队，创建的战队人数不满2人的，不参与排名。--%>
                    <%--</p>--%>
                    <%--<p class="MT7">3、投资战队投资额说明：活动期间每个战队个人累计新增定期投资额（包括联璧发现）计入战队总的投资额内，个人每笔新增投资额计算方法：新增投资额*天数/90，3月期按90天算，6月期按180天算。</p>--%>
                    <%--<p class="MT7">4、根据战队总投资额进行高低排名，前20名战队可上榜，投资额相同，按照战队创建的时间先后顺序进行排序。</p>--%>
                </div>
            </div>
        </div>

        <div class="teamRule PLR3P">
            <p class="ruleTitle corpsTitle whiteTex positionR font14 textL">
                <!--战队编号: 100001邀约-->
            </p>
            <div class='corpsTex font12 textL'>
                <ul class="clearfix teamMembers">
                    <%--<li class="fl width18 textC positionR">--%>
                        <%--<img class="positionA captain" src="${pageContext.request.contextPath}/pic/weixin/activity/team/captain.png?<%=request.getAttribute("version")%>">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>陈*</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>陈*</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>陈*</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>陈*</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>陈*</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>2345</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>等你加入</span>--%>
                    <%--</li>--%>
                    <%--<li class="fl width18 textC">--%>
                        <%--<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/figure.png?<%=request.getAttribute("version")%>">--%>
                        <%--<span>等你加入</span>--%>
                    <%--</li>--%>
                </ul>
            </div>
        </div>
        <div class="btnBox clearfix font16 MT10">
            <a class="fl inlineB whiteTex radius5 setTeam" id="setTeam">加入战队</a>
            <a class="fr inlineB whiteTex radius5 teamRank" id="teamRank">创建战队</a>
            <a class="fl inlineB whiteTex radius5 setTeam" id="looktTeam">查看我的战队</a>
        </div>

        <div></div>
    <p class="font12 grayText1 MT20">此次活动由联璧金融发起<br/>若对活动规则存在疑问,请与联璧金融客服联系咨询</p>

        <!-- 弹框  -->
        <div class="bounceWrap positionF none">
            <div class="bounceCon positionF font14">
                <div class="positionA iconClose" id="closeConfirmBox">
                    <img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/team/iconClose.png?<%=request.getAttribute("version")%>">
                </div>
                <p class="blackTex" id="confirmBox"><!--您确定加入战队吗？<br/>一旦创建，不可退出--></p>
                <div class="bounceBtn whiteTex clearfix MT15">
                    <span class="inlineB fl cancelBtn">取消</span>
                    <span class="inlineB fr confirmBtn">确定</span>
                </div>
            </div>
        </div>
    </div>

    <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
    <input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
    <input type="hidden" name="teamId" id="teamId" value="<%=request.getParameter("teamId")%>" >
    <input type="hidden" name="teamName" id="teamName" value="<%=request.getParameter("teamName")%>" >
    <input type="hidden" name="invitationCode" id="invitationCode" value="<%=request.getParameter("invitationCode")%>" >
    <input type="hidden" name="userId" id="userId" value="${userId}" >
    <input type="hidden" name="mobile" id="mobile" value="${mobile}" >
    <script src="${pageContext.request.contextPath}/js/weixin/page/team/teamCorps.js?<%=request.getAttribute("version")%>"></script>
<%@  include file="../../baiduStatistics.jsp"%>
</body>
</html>

