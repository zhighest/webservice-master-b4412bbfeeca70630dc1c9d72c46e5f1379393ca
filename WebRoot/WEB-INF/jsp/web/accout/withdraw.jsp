<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<!DOCTYPE HTML>
<html>
<head>
<title>个人中心-联璧金融</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="no" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="format-detection" content="telephone=no"/>
<meta content="个人中心，联璧金融个人中心" name="keywords">
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣" name="description">
<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/withdraw.css?<%=request.getAttribute("version")%>"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
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
<script src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
</head>

<body class="fontFamily">
	<div id="wrap"> 
  		<div id="main">
  		  <!-- 网页头部的主tab的val值判断 -->
			  <c:set var="pageIndex" value="2"/>
			  <!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
			  <c:set var="pageAccount" value="5"/>
  			<%@  include file="../header.jsp"%> 
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					<div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="5"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">提现</span></div>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="clearfix witeBkg width100 PTB30">
  					<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">提现</p>
  						<p class="grayTex61 opacity60">Withdraw</p>
  					</div>
  					<div class="wrapper clearfix positionR MT20 grayBkgf5">
  						<!-- 提现内容 begin -->
  						<div class="width80 blockC PTB30">
		    				<!-- 未绑定银行卡 begin -->
	    					<div class="clearfix noCard none" id="noCard">
		    					<label class="fl heigh45 width65" for="moneyOrder">银行卡</label>
		    					<div class="fl ML20">
		    						<div class="clearfix PTB10 PLR10 dashed">
			   							<img class="fl" src="${pageContext.request.contextPath}/pic/web/reminder.png" alt="" width="25px">
			   							<p class="fl ML10">提示 您还未绑定银行卡不能提现，请至少<span class="redTex underLine">充值0.01</span>元进行绑卡</p>
			    					</div>
			    					<a class="redBkg noHoverBtn radius5 whiteTex width150P heigh40 MT50 cursor textC block goTopUp" href="${pageContext.request.contextPath}/wxpay/redirectPay?payFlag=KJ" />立即充值</a>
		    					</div>
		    				</div>
							<!-- 未绑定银行卡 end -->
							<!-- 已绑定银行卡 begin -->
		    				<div class="clearfix hasCard none" id="hasCard">
			    				<div class="clearfix">
			    					<div class="fl heigh40 width65">银行卡</div>
									<div class="ML20 fl PTB10 PLR10 bankInfo whiteBkg moneyOd">
										<h4 class="blackTex fl">
											<img id="bankIcon" class="fl" height="20">
											<span class="fl ML10" id="bank_name"></span>
										</h4>
										<p class="blackTex fl ML30">尾号：<span id="cardNumber"></span></p>
									</div>
			    				</div>
			    				<div class="MT30 none" id="cityInfo">
									<div class="clearfix positionR">
										<div class="fl heigh40 width65">选择城市</div>
										<div class="fl width220P moneyOd heigh40 boxSizing PL20 grayTex cursor positionA povince whiteBkg" id="clickPovince">
											<span id="povince">请选择省份</span>
											<img class="downIcon positionA block" src="${pageContext.request.contextPath}/pic/web/downIcon.png" width="16px">
										</div>
										<ul class="positionA font14 blackTex whiteBkg redBorder PTB10 PLR10 povinceList lineHeight2x none" id="povinceList">
										</ul>
										<div class="fl width220P moneyOd cursor heigh40 boxSizing PL20 positionA grayTex city whiteBkg" id="clickCity">
											<span id="city">请选择城市</span>
											<img class="downIcon positionA block" src="${pageContext.request.contextPath}/pic/web/downIcon.png" width="16px">
										</div>
										<ul class="positionA font14 blackTex whiteBkg redBorder PTB10 PLR10 cityList lineHeight2x none" id="cityList">
										</ul>
									</div>	
								</div>
								<div class="clearfix MT30">
		    						<div class="fl heigh40 width65">提现金额</div>
		    						<div class="fl ML20">
		    							<input class="width350 moneyOd heigh35 PL10 font14" autocomplete="off" placeholder="请输入提现金额" type="text" id="extractMoney">
										<div class="font14 MT5 width350 clearfix">
											<p class="fl grayTex83" id="quickWithdrawTip"></p>
											<span class="fr redTex cursor" id="insideInputBtn">全部取出</span>
										</div>
		    						</div>
		    					</div>
		    					<div class="clearfix MT30">
		    						<div class="fl heigh40 width65">交易密码</div>
		    						<div class="fl ML20">
		    							<input class="width350 moneyOd heigh35 PL10 font14" maxlength="6" autocomplete="off" placeholder="请输入交易密码" type="password" id="passwordCash">
										<div class="font14 MT5 width250 clearfix PL10">
											
										</div>
										<ul class="font14 grayTex83 MT30 lineHeight1_8x" id="quickAmountDesc">
										</ul>
										<a class="redBkg noHoverBtn whiteTex radius5 width150P heigh40 MT40 cursor textC block enterBtn " id="withdrawBtn" />确认提现</a>
		    						</div>
		    					</div>
		    				</div>
							<!-- 已绑定银行卡 end -->
		    			</div>
		    			<!-- 提现内容 end -->
  					</div>
  				</div>
		    </div>
  		</div> 
  	</div> 
 
  	<input type="hidden" value="${mobile}" id="mobile" name="mobile"/>
  	<input type="hidden" name="cardNumber" id="cardNo"  >
	<input type="hidden" name="cardId" id="cardId"  >
	<input type="hidden"  id="token" name="token" value="${token}">
	<%@  include file="../footer.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/web/page/withdraw.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>

