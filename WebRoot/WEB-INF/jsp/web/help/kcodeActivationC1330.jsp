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
							<c:set var="helpSidebar" value="2" />
							<%@  include file="helpSidebar.jsp"%>
							<ul class="fr inlineBUl rightGuild font12 MT7">
								<li>当前位置：
									<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation" class="blackTex" rel="nofollow">帮助中心<span class="MLR5">/</span></a>
								</li>
								<li class="redTex">K码激活</li>
							</ul>
						</div>
					</div>
					<!---次级导航 end -->
					<div class="helpCenterBanner banner1">
						<div class="wrapper">
							<div class="MT180">
								<p class="subTitle whiteTex show1">K码激活</p>
								<p class="subDes whiteTex show5">K code activation</p>
							</div>
						</div>
					</div>

					<!--智能手机C1330-->
					<div class="ActiveProductDetail" id="product4">
						<p class="font20 MB30">智能手机C1330 <span class="font12 Color97"></span></p>
						<ul class="clearfix">
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第一步：<span class="font12 Color2c fontWeightIntial">刮开产品获得底部k码</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/KcodeActivationImg1-4.jpg">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第二步：<span class="font12 Color2c fontWeightIntial">点击k码激活</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/KcodeActivationImg2.jpg">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第三步：<span class="font12 Color2c fontWeightIntial">打开扫描K码条形码功能</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/KcodeActivationImg3.jpg">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第四步：<span class="font12 Color2c fontWeightIntial">扫描条形码</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/KcodeActivationImg4.jpg">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第五步：<span class="font12 Color2c fontWeightIntial">若扫描无法识别请输入k码</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/KcodeActivationImg5.jpg">
							</li>
							<li class="clearfix">
								<p class="Color4a font20 fontBold MB10">第六步：<span class="font12 Color2c fontWeightIntial">激活成功</span></p>
								<img src="${pageContext.request.contextPath}/pic/web/help/KcodeActivationImg6.jpg">
							</li>
						</ul>
						<div class="textL ActiveProductS7 width100 MT20">
							<p class="font20 MB10">激活说明<span class="font12 Color97"></span></p>
							<p class="M10 font12 Color9c">1、新用户均可无条件激活任意一款K码产品。</p>
							<p class="MB10 font12 Color9c">2、老用户激活手机C1330需使用相对应的K码券方可激活。</p>
							<p class="MB10 font12 Color9c">3、新用户定义：从未参加过任何“0元购”返现活动 （未激活过任意K码）的用户。老用户定义：已经参加过“0元购”返现活动（激活过任意K码）的用户。</p>
							<p class="MB10 font12 Color9c">4、使用对应产品K码券获得的激活资格不受限制，使用的k码券越多，可激活对应产品的数量越多。</p>
							<p class="MB10 font12 Color9c"> 5、激活C1330前须实名认证，且只能用购买的机器进行激活。</p>
							<p class="MB10 font12 Color9c">6、注册“联璧金融”账户的身份证、银行卡及手机号码实名信息须一致（不支持运营商虚拟号码进行注册）。“联璧金融”新用户均可不用投资任何理财产品即可参加某一款产品的“0元购”返现活动。</p>
							<p class="MB10 MT30 font12 Color9c">本次“0”元购活动规则从2017年6月1日开始执行，由上海斐讯数据通信技术有限公司联合上海联璧电子科技有限公司合作开展，并对该活动负责。</p>
							<p class="MB10 font12 Color9c">上海斐讯数据通信技术有限公司及上海联璧电子科技有限公司对本次活动拥有最终解释权</p>
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