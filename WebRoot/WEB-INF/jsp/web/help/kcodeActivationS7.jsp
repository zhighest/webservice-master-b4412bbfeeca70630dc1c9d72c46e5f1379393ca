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

					<!--智能体脂秤S7-->
					<div class="textL ActiveProductS7 width90 wrapper " id="product5">
						<p class="font18 MT20 PB30">智能体脂秤S7<span class="font12 Color97"></span></p>
						<p class="font16 fontWeightIntial fontBold MB10">智能体脂秤S7激活方法<span class="font12 Color97"></span></p>
						<p class="MB10 font12 Color9c">1. 注册“联璧金融”APP并实名绑卡</p>
						<p class="MB10 font12 Color9c">2. 刮开斐讯智能体脂秤S7底部标签处的涂层获取K码</p>
						<p class="MB10 font12 Color9c">3. 进入“联璧金融”APP首页，点击“K码激活”，输入K码或扫描条形码，提交激活</p>
						<p class="MB10 font12 Color9c">4. 成功激活K码后，点击右下方【我的资产-礼包资产】可查看已生成的礼包</p>
						<p class="MB10 MT30 font12 Color9c">本次“0”元购活动规则从2017年6月1日开始执行，由上海斐讯数据通信技术有限公司联合上海联璧电子科技有限公司合作开展，并对该活动负责。</p>
						<p class="MB10 font12 Color9c">上海斐讯数据通信技术有限公司及上海联璧电子科技有限公司对本次活动拥有最终解释权</p>
						
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