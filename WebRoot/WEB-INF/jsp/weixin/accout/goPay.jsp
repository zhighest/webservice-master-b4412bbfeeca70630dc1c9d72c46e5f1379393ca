	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ page contentType="text/html;charset=UTF-8"%>
		<%@  include file="../../common.jsp"%>

		<!DOCTYPE HTML>

		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8">
		<title>充值</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/topup.css?<%=request.getAttribute("version")%>" />
		<%@  include file="../header2.jsp"%>
		</head>
		<body>
		<div class="height100P positionA width100 outHide wrapper">
		<!-- 内容部分 begin -->
		<div class="main   height100P width100  PT10 boxSizing">
		<!--由于绑定银行卡后列表出来会显示在银行卡号输入框下面，故将结构调整-->
		<!-- 银行列表展现 begin -->
		<div id="bankList" class="positionA   bankList">
		<ul class="whiteBkg listBkg  PLR10 clearfix" id="allBanks">
		<li class="textC borderB PT10 height10" id="return"><img src="${pageContext.request.contextPath}/pic/weixin/returnIcon.png" height="14" class="fl MT5"></li>
		</ul>
		</div>
		<!-- 银行列表展现 end -->

		<!-- 已绑定银行卡银行信息 begin -->
		<div class="hasTBBorder whiteBkg MB10" id="hasCard" style="display:none;">
		<div class="whiteBkg width90 blockC PLR5P clearfix PTB10">
		<span class="fl"><img id="bankIcon" class="MT5" height="40" src="${pageContext.request.contextPath}${bankPicPath}"/></span>
		<div class="fr width80 textL">
		<div class="clearfix">
		<h4 class="blackTex MB5 fl" id="bank_name">${bankName}</h4>
		<p class="fl ML10" id="">尾号:<span class="blackTex" id="cardNumber"></span></p>
		</div>
		<p class="font14"><span class="grayTex" id="limitMoney">单笔限额 ${bankLimit }, 单日限额 ${bankDayLimit }</span></p>
		</div>
		</div>
		</div>
		<!-- 已绑定银行卡银行信息 end -->
		<!-- 未绑定银行卡表单整体信息 begin -->
		<form action="${pageContext.request.contextPath}/wxtrade/goZshtTopUp" method="POST" id="zshtPayForm">
		<!-- 未绑定银行卡银行信息 begin -->
		<div class="hasTBBorder whiteBkg MB10" id="noHasCard">
		<div class="whiteBkg width90 blockC PLR5P clearfix PTB15">
		<span class="fl">银行卡号</span>
		<c:choose>
			<c:when test="${userCardInfoCount>0}">
				<input type="hidden" value="${userCard.card_number}" id="cardNo" name="cardNo" class="width75 inputNoborder fr">
				<input type="hidden" value="${userCard.phone_no}" id="cardPhone" name="cardPhone">
			</c:when>
			<c:otherwise>
				<input type="text" placeholder="请输入银行卡号" id="bankAccountNo" readonly name="bankAccountNo" class="width75 inputNoborder fr keyBord">
			</c:otherwise>
		</c:choose>
		</div>
		</div>
		<!-- 未绑定银行卡银行信息 end -->
		<!-- 输入充值金额的输入框整体 begin -->
		<div class="hasTBBorder whiteBkg MB10 PB10">
		<div class="whiteBkg font16 grayTex width90 PLR5P clearfix PT15">充值金额</div>
		<!-- 输入框 begin -->
		<div class="whiteBkg width90 blockC PLR5P clearfix PTB15">
		<label class="grayTex font30 fl">￥</label>
		<input type="text" placeholder="建议充值100元以上的金额" id="moneyOrder" name="moneyOrder" readonly class="ML10 width80 font50 inputNoborder moneyInput fl keyBord">
		</div>
		<!-- 输入框 end -->
		<div class="width90 PLR5P">
		<p class=" borderT font16 PT10 grayTex blockC">充值后金额 <span class="redTex" id="afterTheTopUpAmount">0</span>元</p>
		</div>
		</div>
		<!-- 输入充值金额的输入框整体 end-->
		<!-- 提交按钮 begin -->
		<div class="width90 blockC MT20">
		<a id="inactiveBtn" class="width100 block font16 inactiveBtn radius5">确认充值</a>
		</div>
		<!-- 提交按钮 end -->
		<div class="width90 blockC MT10 outHide" ><span class="fr" id='checkBankList'>支持银行列表</span></div>
		</form>
		<!-- 未绑定银行卡表单整体信息 end -->
		</div>
		<!-- 内容部分 end -->

		<!-- 提示弹框 begin -->
		<div class="alertBg width100 height100P positionF" style="display:none;">
		<div class="radius5 whiteBkg width80 alertBoxTopup positionA textC blockC">
		<div class="width90 blockC">
		<h3 class="font20 PT15 redTex">提&nbsp;示</h3>
		<div class="MT20 textL">
		<input type="hidden" id="identityName" name="identityName" value="${identityName}">
		<input type="tel" name="phoneNum" id="phoneNum" value="${cardPhone}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="银行预留手机号" class="popupInput radius5 MB20 width100" />
		<div class="clearfix">
		<!-- 验证码 -->
		<input type="tel" id="checkCode" name="checkCode"  placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="4" class="popupInput radius5 MB20 width70 fl" />
		<a class="btn width25 fr opacity PLR1P smallBtn1 font12" id="gainCode">获取<br/>验证码</a>
		</div>
		<input class="none" id="loginFlag" type="text" name="loginFlag" value="2">
		</div>
		<input type="hidden"  id="paytoken" name="paytoken">
		<input type="hidden"  id="orderNo" name="orderNo">
		<div>
		<a class="btn inactiveBtn width45 block forbid fl" id="cannelBtnTopUp">取消</a>
		<a class="btn width45 block forbid fr" id="checkTopUpSubmit">确认支付</a>
		</div>
		</div>
		</div>

		</div>
		<!-- 提示弹框 end -->
		<form id="singlePayForm" method="POST" action="">
			<div id="params"></div>
		</form>
		</div>

		<!-- 输入数字的键盘 begin -->
		<div class="btmDiv width100 clearfix whiteBkg borderTSL " id="momeyBox">
		<%@  include file="../numberKeyboard.jsp"%>
		</div>
		<!-- <input type="hidden" value="${idcardValidate}" id="idcardValidate" name="idcardValidate"/> -->
		<input type="hidden" value="${money}" id="money" name="money"/>
		<input type="hidden" value="${userId}" id="userId" name="userId"/>
		<input type="hidden" id="mobile" value="${mobile }"/>
		<input type="hidden" id="bankCardNumber" value="${bankCardNumber}" />
		<input type="hidden" id="rescode" value="${rescode}" />
		<input type="hidden" id="resmsg_cn" value="${resmsg_cn}" />
		<input type="hidden" id="resmsg" value="${resmsg}" />
		<input type="hidden" id="acctBalance" value="${acctBalance}" />
		<input type="hidden" id="payFlag"	value="${payFlag}" />
		<script src="${pageContext.request.contextPath}/js/weixin/page/goPay.js?<%=request.getAttribute("version")%>"></script>
		<%@  include file="../baiduStatistics.jsp"%>
		</body>
		</html>