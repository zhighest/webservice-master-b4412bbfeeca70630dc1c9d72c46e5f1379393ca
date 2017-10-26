<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>帮助中心-联璧金融</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="no" />
    <meta name="apple-touch-fullscreen" content="yes" />
    <meta name="format-detection" content="telephone=no"/>
    <meta name="description" content="联璧金融帮助服务，介绍关于联璧金融投资的常见问题，注册、认证、充值、取现、投资操作等操作演示。解答您对于联璧金融的疑问问题"/>
    <meta name="keywords" content="帮助服务，联璧金融帮助服务"/>
    <link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/helpCenter.css?<%=request.getAttribute("version")%>"/>
</head>

<body class="fontFamily">
		<div id="wrap">
			<div id="main">
				<c:set var="pageIndex" value="5" />
				<%@  include file="../header.jsp"%>
				<div class="contentWrapper positionR">
					<!---次级导航 begin -->
					<div class="wrapper">
						<div class="clearfix PTB30">
							<c:set var="helpSidebar" value="11" />
							<%@  include file="helpSidebar.jsp"%>
							<ul class="fr inlineBUl rightGuild font12 MT7">
								<li>当前位置：
									<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation" class="blackTex" rel="nofollow">帮助中心<span class="MLR5">/</span></a>
								</li>
								<li class="redTex">绑卡出错</li>
							</ul>
						</div>
					</div>
					<!---次级导航 end -->
					<div class="helpCenterBanner banner1">
						<div class="wrapper">
							<div class="MT180">
								<p class="subTitle whiteTex show1">绑卡出错</p>
								<p class="subDes whiteTex show5">Bind card error </p>
							</div>
						</div>
					</div>

					<div class="wrapper">
						<div class="ProblemClassification MT40">
							<ul class="MT20">
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90">报错1107,1108,9990,9991</span><img class="fr width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c  PB20 ">您的银行卡没有开通银联在线支付，您可以在电脑上打开银联官网的链接开通的。<br>
 https://www.95516.com/static/union/pages/card/openFirst.html?entry=openPay </div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90">报错1114</span><img class="fr width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">请您核实银行卡预留手机号码是否有做变更，如有变更，建议您关注连连微信公众号自助修改预留手机号码 更换方法： 1、关注官方认证的微信公众号“连连支付”； 2、关注后在左下角“自助服务”点击“更换手机号”，进入更换手机号界面； 3、按提示填写正确的信息，滑动验证通过后输入新手机号接收的验证码即可更换成功。 温馨提示： 1.如您未在银行修改过预留手机号码，无需进行此操作； 2.一个微信号仅支持更换同一人名下银行卡的预留手机号。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20  PT20 bottomGray "><span class="ProblemInlineBlock width90">报错1100</span><img class="fr width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">请您核对银行卡是否有挂失、过期、状态异常等情况，如果有以上情况建议去银行核实或者换张状态正常的卡绑定。另外还需确认下银行卡号是否输入错误，如果卡号输入正确、卡片状态是都是正常，可以提供银行卡号这边去支付平台帮您核实原因。<br/>注：农行用户核实信息无误的情况下，建议开通银联在线支付业务之后再试。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20  PT20 bottomGray "><span class="ProblemInlineBlock width90">报错1112，1113</span><img class="fr width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">请您确认一下输入的信息是否是银行的开户信息，是否与身份证信息保持一致，有没有空格或其他字符。
如果您有迁移过户口或者更改过身份证姓名，建议您前往户籍所在地在公安库中重新录入或刷新同步下信息后再操作
注：现役军人无法通过实名认证，是不支持支付的</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20  PT20 bottomGray "><span class="ProblemInlineBlock width90">报错6001</span><img class="fr width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">根据报错提示您银行卡余额不足，建议您核实账户余额情况，保证余额充足的情况再进行充值哦</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20  PT20 bottomGray "><span class="ProblemInlineBlock width90">报错ios缓存问题</span><img class="fr width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">苹果手机支付时前端提示：“银行预留手机号码错误”，无手机号输入框，有验证码框，是由于IOS缓存问题，苹果手机支付时可能会出现这种情况，建议该用户重新安装APP后再尝试支付<br></div>
								</li>
							</ul>
							<p class="MT20 MB20 textC font16 Color4c">其他问题请联系客服<span class="JsPhoneCall"></span></p>
						</div>
					</div>

				</div>
			</div>
		</div>
			<%@  include file="../footer.jsp"%>
			<%@  include file="../baiduStatistics.jsp"%>
			<input id="hasHeadFoot" name="hasHeadFoot" type="hidden" value="${hasHeadFoot}">
			<script src="${pageContext.request.contextPath}/js/web/page/helpCenter.js?<%=request.getAttribute(" version ")%>"></script>
			<script src="${pageContext.request.contextPath}/js/web/page/subBanner.js?<%=request.getAttribute(" version ")%>"></script>
			<script src="${pageContext.request.contextPath}/js/web/page/helpCenterBindError.js?<%=request.getAttribute(" version ")%>"></script>
	</body>
	
</html>
