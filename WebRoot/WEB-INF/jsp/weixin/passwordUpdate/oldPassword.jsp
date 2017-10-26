<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>修改交易密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/userInfo.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
</head>
<body class="PT20">
	<div class="positionR">
		<label class="positionA tips" for="oldPassword">输入旧密码</label>
		<input class="width100 inputNoborder PTB15 PL120 boxSizing" id="oldPassword" placeholder="请输入旧密码" type="text">
	</div>
	<div class="width90 blockC MT30">
		<a class="btn radius5 width100 block" id="loginSubmit">下一步</a>
	</div>
	<div class="width90 blockC MT10 clearfix">
		<span class="fr">忘记交易密码</span>
	</div>
	<div class="width100 height100P alertBkg positionA none" id="tipBox">
		<div class="width90 blockC whiteBkg radius5 positionA redTex PTB30 alertBox">
			<p class="font20"><img class="MR10" src="${pageContext.request.contextPath}/pic/weixin/tel.png" width="20px">拨打客服电话</p>
			<p class="font22 MT15 JsPhoneCall"><span class="JsPhoneCall"></span></p>
		</div>
	</div>
</body>
<script type="text/javascript">
//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
			$('.JsPhoneCall').html(PhoneCall);
		}
	});
}
getServiceCall();
</script>
</html>