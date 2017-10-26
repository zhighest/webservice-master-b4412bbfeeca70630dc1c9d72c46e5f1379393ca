<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html class="html">
<head>
	<title>关于我们-联璧金融</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta name="description" content="联璧金融，诞生于科技基因强大的“上海联璧电子科技有限公司”，由独立的专业金融产品团队组建运营，依托联璧科技的移动通信及大数据技术，让金融，惠民生"/>
	<meta name="keywords" content="关于联璧金融,联璧金融简介, 联璧金融介绍"/>
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/aboutUs.css?<%=request.getAttribute("version")%>"/>
</head>
<body class="fontFamily">
	<div id="wrap"> 
		<div id="main">
			<c:set var="pageIndex" value="6"/>
			<%@  include file="../header.jsp"%>
			<div class="contentWrapper">
				<!---次级导航-->
				<div class="wrapper">
					<div class="clearfix PTB30"> 
						<c:set var="subnav" value="5"/>
						<%@  include file="menu.jsp"%>
						<ul class="fr inlineBUl rightGuild font12 MT7">
							<li>当前位置：<a href="${pageContext.request.contextPath}/webabout/aboutUs" class="blackTex" rel="nofollow">关于我们<span class="MLR5">/</span></a></li>
							<li class="redTex">安全保障</li>
						</ul>
					</div>
				</div>
				<div class="aboutUsBanner banner5">
					<div class="wrapper">
						<div class="MT180">
							<p class="subTitle whiteTex show1">优质资产</p>
							<p class="subDes whiteTex show5">Quality assets</p>	
						</div>
					</div>
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT40 PB40 clearfix">
						<div class="fl qrcodeDesL"><img src="${pageContext.request.contextPath}/pic/web/aboutUs/safeImg1.jpg" width="90%"></div>
						<div class="lineHeight2x font14 grayTex61T fr qrcodeDesR MT40">
							<p>联璧金融秉承安全至上的资产筛选原则，根据不同的产品类型及行业特性等特点制定了严苛的质量准则，对于企业的资质资信、股东资信、股东净资产、经营年限、资本规模、资产负债率、财务运营各项比率分析有着专业审查方法，并执行较高控制标准，保障理财资金全部投向合格的、安全的、有100%预期收益并正现金流回归的高质量标的方，以此保证资金的长期回报安全。</p>
						</div>
						
					</div>
				</div>
				<div class="wrapper MB30">
					<p class="subTitle grayTex61 MT40">资金托管</p>
					<p class="subDes grayTex61T">Fund trust</p>
					<div class="MT20 lineHeight2x grayTex61T ML15 font14">
						<p>联璧金融采用第三方资金托管机制，意味着平台不直接经手客户资金，也无权擅自动用客户在第三方托管的资金，投资人通过第三方支付投资成功后，资金将直接进入借款人账户，保证交易真实和投资人资金安全。 </p>
						<p>联璧金融采用风险准备金前置计提机制，保证在资金流动过程中出现不及时的回款情况下，投资人获得本金及利息的时效不受影响，高效的风险准备金调用机制严格保证资金的短期流动性安全。</p>
					</div>	
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT20 PB40">
						<p class="subTitle grayTex61 MT20">风控严格</p>
						<p class="subDes grayTex61T">Strict risk control</p>
						<div class="MT20 lineHeight2x grayTex61T ML15 font14">
							<p>核验企业资信(各项审核资料提交);</p>
							<p>财务报表审查、偿债能力分析、现金流匡算;</p>
							<p>律所实地尽调,出具第三方尽调报告;会计师事务所出具审计报告;</p>
							<p>上市公司证券部给予风控意见;应收账款合同、融资租赁合同、发票等凭证质押;</p>
						</div>
					</div>
				</div>
				<div class="overviewBanner overBanner2">
					<div class="wrapper PT20 PB20">
						<p class="subTitle whiteTex MT20">回款保证</p>
						<p class="subDes whiteTex opacity60">Return guarantee</p>
						<div class="MT20 lineHeight2x whiteTex opacity60 ML15 font14">
							<p>融资到期，由融资方还本付息。</p>
							<p>融资方逾期的情况，其出质资产由联璧科技进行处置偿还本息。</p>
						</div>
					</div>	
				</div>

  			</div>
		</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<%@  include file="../footer.jsp"%>
</body>
</html>
