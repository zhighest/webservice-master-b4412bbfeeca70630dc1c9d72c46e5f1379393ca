<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

<!DOCTYPE HTML>
<html class="html">
  <head>
    <title>网站地图-联璧金融</title>
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
  			<%@  include file="../header.jsp"%>
  			<div class="contentWrapper"> 
	  			<div class="positionR bannerBox outHide">
	  				<img class="block aboutBanner" src="${pageContext.request.contextPath}/pic/web/aboutUs/auBanner.jpg" alt="关于联璧金融简介，能让您详细地了解联璧金融，对联璧金融有信心">
				</div>
				<div class="whiteTex PT50 top">
				  <p class="show1">欢迎来到联璧钱包官网</p>
				  <p class="show2">Welcome to the official website lincomb</p>
				</div>
	  			<div class="width100 clearfix whiteBkg MT20">
			    	<!-- 右边 begin -->
			    		<div class="wrapper blockC PT20">
							<p class="subTitle">网站地图</p>
							<p class="subDes">Websitemap</p>
			    			<div class="message MT10 PLR30 PB30 PT30">
			    				<div class="font18 MB20 fl MR30">首页</div>
				    			<ul class="inlineBUl block MB30">
					    			<li class=""><a href="${URL}/webindex/goIndex" class="font14 grayTex2" target="_blank">联璧官网首页</a></li>
				    			</ul>
				    			<div class="font18 MB10">个人中心</div>
				    			<ul class="inlineBUl MB30 block">
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/trade/goAccoutOverview" class="font14 grayTex2" target="_blank">登录</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/trade/goAccoutOverview" class="font14 grayTex2" target="_blank">账户总览</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/webindex/goDemandProperty" class="font14 grayTex2" target="_blank">零钱计划资产</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/webindex/goFixedProperty" class="font14 grayTex2" target="_blank">定期资产</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/wxpay/redirectPay?payFlag=KJ" class="font14 grayTex2" target="_blank">充值</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/trade/goWithdraw" class="font14 grayTex2" target="_blank">提现</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/webindex/goInvestDetail" class="font14 grayTex2" target="_blank">交易记录</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/coupons/goMyInterest" class="font14 grayTex2" target="_blank">加息券</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/trade/voucher" class="font14 grayTex2" target="_blank">代金券</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/user/logOut" class="font14 grayTex2" target="_blank">退出登录</a></li>
					    			<li class="MR30"><a href="${pageContext.request.contextPath}/wxabout/regAgreement" class="font14 grayTex2" target="_blank">用户注册协议</a></li>
				    			</ul>
				    			<div class="font18 MB20 fl MR30">最新活动</div>
				    			<ul class="inlineBUl MB30 block">
					    			<li class="MR30"><a href="${URL}/webhtml/activityList1.html" class="font14 grayTex2" target="_blank">最新活动</a></li>
				    			</ul>
				    			<div class="font18 MB20 fl MR30">媒体报道</div>
				    			<ul class="inlineBUl MB30 block">
					    			<li class="MR30"><a href="${URL}/webhtml/mediaList1.html" class="font14 grayTex2" target="_blank">媒体报道</a></li>
				    			</ul>
				    			<div class="font18 MB20 fl MR30">金融课堂</div>
				    			<ul class="inlineBUl MB30 block">
					    			<li class="MR30"><a href="${URL}/webhtml/classesList1.html" class="font14 grayTex2" target="_blank">金融课堂</a></li>
				    			</ul>
				    			<div class="font18 MB10">帮助中心</div>
				    			<ul class="inlineBUl MB30 block">
					    			<li class="MR30"><a href="${URL}/webindex/goKcodeActivation" class="font14 grayTex2" target="_blank">K码激活</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goTopUp" class="font14 grayTex2" target="_blank">充值</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goNameBindCard" class="font14 grayTex2" target="_blank">实名绑卡</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goDemandProduct" class="font14 grayTex2" target="_blank">购买零钱计划</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goFixProduct" class="font14 grayTex2" target="_blank">购买定期</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goTransferOut" class="font14 grayTex2" target="_blank">转出</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goWithdraw" class="font14 grayTex2" target="_blank">提现</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goSignIn" class="font14 grayTex2" target="_blank">签到</a></li>
					    			<li class="MR30"><a href="${URL}/webindex/goHelpCenterQA" class="font14 grayTex2" target="_blank">Q&A</a></li>
				    			</ul>
				    			<div class="font18 MB10">关于我们</div>
				    			<ul class="inlineBUl MB30 block">
					    			<li class="MR30"><a href="${URL}/webabout/aboutUs" class="font14 grayTex2" target="_blank">公司概况</a></li>
					    			<li class="MR30"><a href="${URL}/webaboutUs/goPlatformOverview" class="font14 grayTex2" target="_blank">金融平台简介</a></li>
					    			<li class="MR30"><a href="${URL}/webabout/goOurBackground" class="font14 grayTex2" target="_blank">联系我们</a></li>
					    			<li class="MR30"><a href="${URL}/webabout/goPropertyInfo" class="font14 grayTex2" target="_blank">产品介绍</a></li>
					    			<li class="MR30"><a href="${URL}/webabout/goSafetyGuarantee" class="font14 grayTex2" target="_blank">安全保障</a></li>
					    			<li class="MR30"><a href="${URL}/webaboutUs/goServicesAgreement" class="font14 grayTex2" target="_blank">服务协议</a></li>
					    			<li class="MR30 companyAnnouncements"><a href="###" class="font14 grayTex2" >公司声明</a></li>
				    			</ul>
				    			<div class="font18 MB20 fl MR30">联璧金融APP</div>
				    			<ul class="inlineBUl MB20 block">
					    			<li class="MR30"><a href="${URL}/webabout/goDownloadDetails" class="font14 grayTex2" target="_blank">下载</a></li>
				    			</ul>	
			    			</div>	
			    		</div>
					<!-- 右边 end -->
			    </div>
			</div>
		</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<%@  include file="../footer.jsp"%>
  </body>
</html>
