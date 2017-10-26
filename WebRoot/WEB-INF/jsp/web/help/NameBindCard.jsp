<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html class="html">

	<head>
		<title>帮助中心-联璧金融</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="description" content="联璧金融帮助服务，介绍关于联璧金融投资的常见问题，注册、认证、充值、取现、投资操作等操作演示。解答您对于联璧金融的疑问问题" />
		<meta name="keywords" content="帮助服务，联璧金融帮助服务" />
		<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute(" version ")%>"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/helpCenter.css?<%=request.getAttribute(" version ")%>"/>

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
							<c:set var="helpSidebar" value="3" />
							<%@  include file="helpSidebar.jsp"%>
							<ul class="fr inlineBUl rightGuild font12 MT7">
								<li>当前位置：
									<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation" class="blackTex" rel="nofollow">帮助中心<span class="MLR5">/</span></a>
								</li>
								<li class="redTex">实名绑卡</li>
							</ul>
						</div>
					</div>
					<!---次级导航 end -->
					<div class="helpCenterBanner banner1">
						<div class="wrapper">
							<div class="MT180">
								<p class="subTitle whiteTex show1">实名绑卡</p>
								<p class="subDes whiteTex show5">Real name authentication & bind bank card</p>
							</div>
						</div>
					</div>

					<div class="ActiveProductDetail " id="product2">
						<ul class="clearfix PT20">
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第一步：<span class="font16 Color2c fontWeightIntial">点击设置</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/NameBindCardImg1.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第二步：<span class="font16 Color2c fontWeightIntial">点击进行实名认证</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/NameBindCardImg2.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第三步：<span class="font16 Color2c fontWeightIntial">输入身份信息</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/NameBindCardImg3.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第四步：<span class="font16 Color2c fontWeightIntial">输入银行卡号</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/NameBindCardImg4.png">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第五步：<span class="font16 Color2c fontWeightIntial">实名绑卡成功</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/NameBindCardImg5.png">
							</li>
						</ul>
						<div class="textL ActiveProductS7 width100 MT20">
							<p class="font20 MB10">请仔细阅读：<span class="font12 Color97"></span></p>
							<p class="M10 font12 Color9c">1.仅支持本人借记卡充值，借记卡开户人须与实名人一致。</p>
							<p class="MB10 font12 Color9c">2.充值后改银行卡自动设定为提现银行卡。</p>
							<p class="MB10 font12 Color9c">3.各银行卡充值限额请点击支持银行列表查看。</p>
							<p class="MB10 font12 Color9c WordBreakAll">4.所绑定的银行卡须开通银联在线支付业务，网站为https://www.95516.com/static/union/pages/card/openFirst.html?entry=openPay</p>
							<p class="MB10 font12 Color9c WordBreakAll">5.使用工商银行卡，交通银行卡充值发生错误时，请按下述方式操作。工商银行：建议开通银联无卡支付业务，需要前往工行网店办理4204柜面预留手机号码业务。交通银行：需开通银联在线无卡支付业务，您可以使用电脑访问无卡支付业务开通网址Http://www.95516.com/portal/open/init?enter=open进行操作。</p>
							<p class="MB10 font12 Color9c">6.其他问题，请联系客服<span class="JsPhoneCall"></span></p>
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
	</body>

</html>