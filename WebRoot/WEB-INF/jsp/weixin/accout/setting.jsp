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
	<title>设置</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/setting.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">	
	<div class="wrapper PT20 PB50"> 
		<div class="borderT whiteBkg" onclick="queryAuthentication();">
			<div class="whiteBkg width90 blockC PLR5P clearfix PTB15">
				<span class="fl ">实名认证</span>
				<div class="fr positionR width60 textR" id="idcardValidateN" style="display: none;">
					<font class="MR30"><font class="grayTex">去认证</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
				<div class="fr positionR width60 textR" id="idcardValidateY" style="display:none;">
					<font class="MR10"><font class="grayTex" id="identityName"></font></font>
				</div>
			</div>		
		</div>
		<div class="borderTGray whiteBkg" onclick="queryInvitationCd();">
			<div class="whiteBkg width90 blockC clearfix PTB15 borderTG">
				<span class="fl width40">邀请码</span>
				<div class="fr positionR width60 textR" id="invitationCdN" style="display: none;">
					<font class="MR30"><font class="grayTex">去填写</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
				<div class="fr positionR width60 textR" id="invitationCdY" style="display:none;">
					<font class="MR10"><font class="grayTex" id="invitationCd"></font></font>
				</div>
			</div>
		</div>
		<div class="borderTGray whiteBkg tabBkgNone" id="setRiskEvaluation">
			<div class="whiteBkg width90 blockC clearfix PTB15">
				<span class="fl width60">风险承受能力评估</span>
				<div class="fr positionR width40 textR">
					<font class="MR30"><font class="grayTex" id="riskType">未评估</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
			</div>
		</div>
		<div class="borderTGray whiteBkg" id="setLoginPassword" onclick="setLoginPassword();">
			<div class="whiteBkg width90 blockC clearfix PTB15">
				<span class="fl width60">设置登录密码</span>
				<div class="fr positionR width40 textR">
					<font class="MR30"><font class="grayTex">&nbsp;</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
			</div>
		</div>
		<div class="borderTGray whiteBkg none" id="amendLoginPassword" onclick="amendLoginPassword();">
			<div class="whiteBkg width90 blockC clearfix PTB15">
				<span class="fl width60">修改登录密码<em id="userPasswordLevel" class="none" style="padding:2px 5px;font-size:13px;margin-left:8px;"></em></span>
				<div class="fr positionR width40 textR">
					<font class="MR30"><font class="grayTex">&nbsp;</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
			</div>
		</div>
		<div class="borderTGray whiteBkg" onclick="queryChangePassword();">
			<div class="whiteBkg width90 blockC clearfix PTB15">
				<span class="fl width60">修改交易密码</span>
				<div class="fr positionR width40 textR">
					<font class="MR30"><font class="grayTex">&nbsp;</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
			</div>
		</div>
		<div class="borderTGray whiteBkg" onclick="forgetPassword();">
			<div class="whiteBkg width90 blockC clearfix PTB15">
				<span class="fl width60">忘记交易密码</span>
				<div class="fr positionR width40 textR">
					<font class="MR30"><font class="grayTex">&nbsp;</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
			</div>
		</div>
		<div class="borderTGray whiteBkg MB20 borderB" onclick="unhitchBankCard();">
			<div class="whiteBkg width90 blockC clearfix PTB15">
				<span class="fl width60">银行卡解绑</span>
				<div class="fr positionR width40 textR">
					<font class="MR30"><font class="grayTex">&nbsp;</font></font>
					<span class="rightArrow"><img src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png?<%=request.getAttribute("version")%>" height="20"></span>
				</div>
			</div>
		</div>
	</div>

	<div class="width80 textC btmBtn">
	 	<div class="width80 blockC"><a href="${pageContext.request.contextPath}/wxuser/logOut" class="btn width100 block inactiveBtn font18">退出</a></div>
	</div>
	<div class="width100 height100P alertBkg positionA none" id="tipBox">
		<div class="width90 blockC whiteBkg radius5 positionA redTex PT20 alertBox">
			<p class="font16 lineHeight1_5x">您的账户信息不完整，</br>请联系客服重置交易密码</p>
			<p class="font20 MT10"><img class="MR10" src="${pageContext.request.contextPath}/pic/weixin/tel.png" width="18px"><span class="JsPhoneCall"></span></p>
			<div class="clearfix MT20">
				<a  class="JsPhoneCallTell fl whiteTex heigh30 boxSizing bkg3" id="comfirmBtn" href="tel:4006999211">确定</a>
				<a class="fr grayTex heigh30 grayBkg" href="#" id="cancel">取消</a>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/weixin/page/setting.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

