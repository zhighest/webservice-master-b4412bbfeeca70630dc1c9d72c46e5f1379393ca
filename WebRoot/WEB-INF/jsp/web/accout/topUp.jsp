<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>

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
<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣" name="description">
<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/topUp.css?<%=request.getAttribute("version")%>"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.2.js"></script>
</head>

<body class="fontFamily">
	<div id="wrap"> 
  		<div id="main">
  			<!-- 网页头部的主tab的val值判断 -->
			<c:set var="pageIndex" value="2"/>
		    <!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
			<c:set var="pageAccount" value="4"/>
  			<%@  include file="../header.jsp"%> 
  			<!-- 内容部分 begin -->
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					<div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="4"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">充值</span></div>
  					</div>
  				</div>

  				<div class="wrapper textL fon20 positionR MT20">
  					<!-- 小红标志，勿删除 -->
  					<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">充值</p>
  						<p class="grayTex61 opacity60">Recharge</p>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="wrapper grayBkgf5 clearfix positionR MT20 MB20">
  					<div class="width80 blockC PTB30">
    					<div class="clearfix">
	    					<label class="fl heigh35 grayTex61" for="moneyOrder">充值金额</label>
	    					<div class="fl ML20">
	    						<input class="moneyOd heigh35 PL10 font14" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="建议充值100元以上" type="text" id="moneyOrder" name="moneyOrder">
	    						<div class="font14 grayTex MT5">当前可用余额<span class="redTex" id="balance"></span>元，充值后金额<span class="redTex" id="blcMoney"></span>元</div>
	    					</div>
	    				</div>

	    				<div class="clearfix PT20 heigh50">
	    					<div class="fl grayTex61">充值方式</div>
	    					<div class="fl ML20 textC">
	    						<ul class="clearfix">
	    							<li class="width160P fl cursor hover" id="E-payWay">网上银行</li>
	    							<li class="width160P heigh50 positionR fl ML20 cursor hover current" id="shortCutWay"><img class="positionA kjzf" src="${pageContext.request.contextPath}/pic/web/kjzf.jpg" width="50%"></li>
	    						</ul>
	    					</div>
	    				</div>
		    				
	    				<div class="PT30 clearfix none" id="E-bank">
	    					<label class="fl MT10 grayTex61">银行卡</label>
	    					<div class="fl bankIcon ML15">
	    						<c:forEach var="bank" items="${bankList}">
	    							<%--屏蔽兴业银行--%>
	    							<c:if test="${bank.ylbankcode ne '03090000'}">
										<label>
				    						<div class="cursor border fl ML20 MB20 PLR10 PTB10 bankSelect hover">
				    							<img class="bankImg fl" src="${pageContext.request.contextPath}${bank.picture}" />
												<input class="none" type="radio" id="bankCode" name="bankCode"
													value="${bank.ylbankcode }" />
												<span class="fl textC font14 heigh35">${bank.bank_name}</span>
											</div>
										</label>
									</c:if>
								</c:forEach>
	    					</div>
	    					<input class="redBkg radius5 whiteTex width150P heigh40 MT30 cursor enterBtn noHoverBtn" type="button" id="inactiveBtn" value="确认充值" />
	    				</div>

		    				<div class="PT30" id="shortcutPay">
		    					<div class="clearfix none" id="hasCard">
		    						<div class="fl heigh40">选择银行</div>
									<div class="ML20 fl redBorder PTB10 PLR10 bankInfo">
										<h4 class="blackTex fl">
											<img id="bankIcon" class="fl" height="25">
											<span class="fl ML10" id="bank_name"></span>
										</h4>
										<p class="blackTex fl ML20">尾号：<span id="cardNumber"></span></p>
										<p class="grayTex fl ML30 font14">限额：<span id="limitMoney"></span></p>
									</div>
		    					</div>
		    					<div class="clearfix none" id="noCard">
		    						<div class="fl heigh40 grayTex61">银行卡号</div>
									<input value="" class="ML20 fl moneyOd heigh35 PL10 font14" placeholder="请输入银行卡号" type="text" id="cardNo">
		    					</div>
								<input class="redBkg radius5 whiteTex width150P heigh40 MT50 cursor enterBtn noHoverBtn" type="button" id="kjInactiveBtn" value="确认充值" />
		    				</div>

							<form action="https://cashier.lianlianpay.com/payment/bankgateway.htm"
								method="post" id="llpayForm" class="none">
								version:<input type="text" name="version" id="llpay_version" />
								charset_name:<input type="text" name="charset_name" id="llpay_charset_name" />
								oid_partner: <input type="text" name="oid_partner"
									id="llpay_oid_partner" />
								timestamp: <input type="text"
									name="timestamp" id="llpay_timestamp" />
								user_id: <input type="text" name="user_id"
									id="llpay_user_id" /> 
								sign_type: <input type="text" name="sign_type"
								id="llpay_sign_type" /> 
								sign:<input type="text" name="sign"
								id="llpay_sign" /> 
								
								busi_partner: <input type="text"
								name="busi_partner" id="llpay_busi_partner" /> 
								no_order: <input
								type="text" name="no_order" id="llpay_no_order" /> 
								dt_order: <input
								type="text" name="dt_order" id="llpay_dt_order" /> 
								name_goods:<input
								type="text" name="name_goods" id="llpay_name_goods" /> 
								info_order: <input
								type="text" name="info_order" id="llpay_info_order" /> 
								money_order:
								<input type="text" name="money_order" id="llpay_money_order" />
								notify_url:<input type="text" name="notify_url" id="llpay_notify_url" />
								url_return: <input type="text" name="url_return" id="llpay_url_return" />
								userreq_ip: <input type="text" name="userreq_ip" id="llpay_userreq_ip" />
								
								valid_order: <input type="text" name="valid_order"
									id="llpay_valid_order" />  
									risk_item: <input
									type="text" name="risk_item" id="llpay_risk_item" />
									 bank_code: <input
									type="text" name="bank_code" id="llpay_bank_code" />
							</form>
							
		    			</div>	
  				</div>
	  			<!-- 资产总览 end -->
		    </div>
		    <!-- 内容部分 end -->
  		</div> 
  		<!-- #main end -->
  	</div> 
  	<!-- #wrap end -->

  	<!-- 银行预留手机号弹框 -->
  	<div class="positionF none width100 height100P phoneAlertBg"></div>

  	<div class="phoneAlertBox none positionF">
  		<h4 class="font20 redTex heigh50 borderB">提示</h4>
  		<div class="clearfix MT20 PLR25">
			<label class="fl heigh35" for="phoneNum">手机号</label>
			<div class="fl ML20">
				<input class="mobile border heigh35 PL10 font14" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="请输入银行预留手机号" type="tel" id="phoneNum" name="phoneNum">
			</div>
		</div>
		<div class="clearfix MT10 PLR25">
			<label class="fl heigh35" for="verification">验证码</label>
			<div class="fl ML20">
				<input class="verification border heigh35 PL10 font14" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" maxlength="4" placeholder="请输入验证码" type="text" id="checkCode" name="checkCode">
			</div>
			<div class="fr font14 whiteTex redBkg heigh35 PLR10 cursor" id="gainCode">发送验证码</div>
			<input class="none" id="loginFlag" type="text" name="loginFlag" value="2">
		</div>
		<input type="hidden"  id="paytoken" name="paytoken">
		<input type="hidden"  id="orderNo" name="orderNo">
		<div class="clearfix PLR25 whiteTex MT20">
			<div class="fl grayBkg1 heigh35 width45 cursor" id="cannelBtnTopUp">取消</div>
			<div class="fr redBkg heigh35 width45 cursor" id="checkTopUpSubmit">确认</div>
		</div>
  	</div>


  	<form action="https://wap.lianlianpay.com/authpay.htm"
				method="post" id="kjpayForm">
  		<ul><li><input type="hidden" name="req_data" value="" id="req_data" /></li></ul>
  	</form>
  	<form id="singlePayForm" method="POST" action="">
			<div id="params"></div>
	</form>

  	<input type="hidden" value="${userId }" id="userId" name="userId" />
	<input type="hidden" value="${userCardInfoCount }"
		id="userCardInfoCount" name="userCardInfoCount" />
	<input type="hidden" value="${userCard.llpay_no_agree }"
		id="llpayNoAgree" name="llpayNoAgree" />
	<input type="hidden"  id="token" name="token" value="${token}">

	<input type="hidden"  id="bankName" name="bankName" value="${bankName}">
	<input type="hidden"  id="bankCode" name="bankCode" value="${bankCode}">
	
	<input type="hidden"  id="idCard" name="idCard" value="${idCard}">
	<input type="hidden"  id="identityName" name="identityName" value="${identityName}">
	<input type="hidden"  id="depositDockUserId" name="depositDockUserId" value="${depositDockUserId}">
	<input type="hidden"  id="utmSource" name="utmSource" value="${utmSource}">
	<input type="hidden"  id="regTime" name="regTime" value="${regTime}">
	
    <input type="hidden" id="mobile" value="${mobile }"/>
    <input type="hidden" id="bankCardNumber" value="${bankCardNumber}" />
    <input type="hidden" id="acctBalance" value="${acctBalance}" />
    <input type="hidden" id="payFlag"	value="${payFlag}" />
	<%@  include file="../footer.jsp"%>
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/web/page/topUp.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>
