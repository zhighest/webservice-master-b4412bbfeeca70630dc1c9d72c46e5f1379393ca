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
						<c:set var="subnav" value="1"/>
						<%@  include file="../aboutUs/menu.jsp"%>
						<ul class="fr inlineBUl rightGuild font12 MT7">
							<li>当前位置：<a href="${pageContext.request.contextPath}/webabout/aboutUs" class="blackTex" rel="nofollow">关于我们<span class="MLR5">/</span></a></li>
							<li class="redTex">公司概括</li>
						</ul>
					</div>
				</div>
				<div class="aboutUsBanner banner1">
					<div class="wrapper">
						<div class="MT180">
							<p class="subTitle whiteTex show1">公司概况</p>
							<p class="subDes whiteTex show5">Company overview</p>
						</div>
					</div>
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT50 PB50 lineHeight2x font14 grayTex61T">
						<p class="">联璧科技成立于2012年，注册资本1亿元，实缴资金1亿元，总部位于中国上海，是较早提出场景互联网整体解决方案的运营商，联璧科技秉承“致力于帮助传统行业实现O2O转型“这一使命，与上海某知名通信企业联手，实现资源战略对接，构建以“端、管、应用、云、运营”五大元素为核心的O2O产品闭环型整体解决方案，向世人展示了联璧科技的产品研发和运营实力。</p>
						<p class="MT30">基于强大的科技基因，公司于2014年组建成立互联网金融事业部，并投入研发力量进行互联网理财平台的开发，微信版理财平台“联璧钱包”及APP产品“联璧金融”藉此而生，平台启动股本金1亿元。平台诞生之初便与上海某知名通信企业在个人消费数码领域开展了成功的跨界合作，平台上线伊始即获得大量用户资源。联璧平台由专业的金融技术团队、运营管理团队悉心打造，至2016年初，联璧互联网金融事业部拟独立成为金融信息服务公司，依托联璧与上海某知名通信企业及其上市公司旗下智慧城市布局，基于高科技产业链内优质基础资产，面向互联网理财用户提供专业的投资咨询与金融信息服务，并对润滑产业链、振兴实体科技起到引擎及支撑作用。在联璧的未来三年规划当中，互联网金融将逐步回归小商业金融服务实质，为联璧科技的O2O移动互联网产业及电商消费平台提供持续的，高效的，低成本的优质金融服务。</p>
					</div>
				</div>
				<div class="wrapper PT50 PB50">
					<p class="subTitle grayTex61">发展历程</p>
					<p class="subDes grayTex61T">Development history</p>
					<div class="MT50 PT50">
						<ul class="devPathUl boxsizing positionR">
							<li class="positionR width100 clearfix">
								<div class="leftSquare positionA radius5 whiteTex grayBkg61"><span class="font18">2012</span><br/>years</div>
								<div class="centerDot grayBkg61 positionA"></div>
								<div class="rightTex positionA grayTex61T">联璧科技成立</div>
							</li>
							<li class="positionR clearfix">
								<div class="leftSquare positionA radius5 whiteTex grayBkg61"><span class="font18">2013.10</span><br/>years</div>
								<div class="centerDot grayBkg61 positionA"></div>
								<div class="rightTex positionA grayTex61T">地产行业O2O产品（密圈）上市</div>
							</li>
							<li class="positionR">
								<div class="leftSquare positionA radius5 whiteTex grayBkg61"><span class="font18">2013.11</span><br/>years</div>
								<div class="centerDot grayBkg61 positionA"></div>
								<div class="rightTex positionA grayTex61T">文教行业O2O产品（乐学）上市</div>
							</li>
							<li class="positionR">
								<div class="leftSquare positionA radius5 whiteTex grayBkg61"><span class="font18">2014.5</span><br/>years</div>
								<div class="centerDot grayBkg61 positionA"></div>
								<div class="rightTex positionA grayTex61T">联璧科技总部迁至上海<br/>联璧科技重组，将总部迁至上海，成立上海联璧电子科技有限公司。公司成立西南（成都办事处）、华南（南宁办事处）、西北（西安办事处）、东北（沈阳办事处）、华中（武汉办事处）、华东（上海办事处）、华北（北京办事处）七个大区市场。</div>
							</li>
							<li class="positionR">
								<div class="leftSquare positionA radius5 whiteTex grayBkg61"><span class="font18">2015.9</span><br/>years</div>
								<div class="centerDot grayBkg61 positionA"></div>
								<div class="rightTex positionA grayTex61T">普惠生活从联璧开始<br/>联璧金融平台产品发布会圆满落幕</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT50 PB50">
						<p class="subTitle grayTex61">公司资质</p>
						<p class="subDes grayTex61T">Company qualification</p>
						<div class="blockC width60 MT30 border">
							<img src="${pageContext.request.contextPath}/pic/web/aboutUs/license.jpg" class="width100">
						</div>
					</div>
				</div>

  			</div>
		</div>
	</div>
	<%@  include file="../footer.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>


