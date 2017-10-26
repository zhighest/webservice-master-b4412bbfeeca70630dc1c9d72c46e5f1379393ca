<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>马拉松报名</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/running/marathonLogin.css?<%=request.getAttribute("version")%>" />
	<script src="${pageContext.request.contextPath}/js/weixin/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
<body class="positonR">
	<div class="applyWrapper">	
		<div class="wrapper PT10P">
			<div class="width90 blockC">
				<div class="MT40P textR">
					<ul class="applyNum inlineBUl verticalM" id="peopleNum"></ul>
				</div>
				<form id="applyForm" action="${pageContext.request.contextPath}/running/beforSignUp" method="POST">
					<div class="MT10P">
						<!-- 手机号 -->
						<input type="tel" name="mobile" id="phoneNum" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="手机号" class="inputBkg1 MB20 width100" />
						<div class="clearfix">
						<!-- 验证码 -->
							<input type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="验证码" oninput="$.checkLimit($(this),'',false);" onkeyup="$.checkLimit($(this),'',false);" onafterpaste="$.checkLimit($(this),'',false);" maxlength="4" class="inputBkg1 MB20 width70 fl" />
							<a class="btn width25 fr opacity PLR1P smallBtn2" id="gainCode">获取<br/>验证码</a>
						</div>
					</div>
					<div><a class="btn width100 block opacity" id="applySubmit">报名 / 登录</a></div>
					<div id="authentication" style="display: none;">
						<input type="text" name="nameCard" id="nameCard" value="" maxlength="18" placeholder="姓名" class="inputBkg1 MB20 width100" />	
						<input type="text" name="idCard" id="idCard" value="" maxlength="18" placeholder="身份证号" class="inputBkg1 MB20 width100" />
						<div><a class="btn width100 block opacity forbid" id="enterSubmit">确定</a></div>
						<div class="textR MT15 verTop" style="display: block;"><span class="" id="checkBox"><img src="${pageContext.request.contextPath}/pic/running/checkBox2.png" id="checkboxImg" width="22"></span><a id="agreenment" class="whiteTex underLine verTop">已阅读并同意参赛须知</a></div>
						<div class="MT30 PB10">
							<h4 class="whiteTex">请选择衣服尺寸：</h4>
							<ul class="sizeUl inlineBUl MT10 MB10" id="sizeBtn">
								<li class="size"><a size="M">M</a></li>
								<li class="size"><a size="L">L</a></li>
								<li class="size"><a size="XL">XL</a></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="popup popupMar none" id="popup">
		<div class="grayBkg width80 MT30P popupMarCon blockC">
			<div class="width90 blockC PTB5P">
				<div class="detailsCon">
					<h4 class="font24 MB20">参赛须知</h4>
					<p class="lineHeight1_5x">长跑是一项高负荷大强度长距离的竞技运动，对参赛者身体状况有较高的要求，参赛者应身体健康，有长期参加跑步锻炼或训练的基础。<br/>
					　　有以下疾病患者不宜参加比赛：<br/>
					　　1.先天性心脏病和风湿性心脏病患者；<br/>
					　　2.高血压和脑血管疾病患者；<br/>
					　　3.心肌炎和其他心脏病患者；<br/>
					　　4.冠状动脉病患者和严重心律不齐者；<br/>
					　　5.血糖过高或过低的糖尿病患者；<br/>
					　　6.两周内发热感冒者；<br/>
					　　7.其它不适合运动的疾病患者。<br/>
					　　主办方要求每位参赛选手在赛前要到县级以上医院进行一次身体检查，检查结论为身体健康者方能参加比赛。如检查单显示出身体有问题或是不宜参加长跑活动等结论者不能参加比赛。在比赛中，因个人身体及其他个人原因导致的人身损害和财产损失，由参赛者个人承担责任。			</p>
				</div>
				<div class="dottedT MT20">
					<a id="ensureBtn" class="btn width30 block blockC MT30">确认</a>
				</div>
			</div>
		</div>
	</div>
	<div class="popup popupMar">
		<div class="grayBkg width80 MT30P popupMarCon blockC">
			<div class="width90 blockC PTB5P textC">
				<div class="textC MB10"><img src="${pageContext.request.contextPath}/pic/weixin/warning.png" width="80"></div>
				<h4 class="redTex">本次报名已结束</h4>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
	</script>
	<script src="${pageContext.request.contextPath}/js/running/marathonLogin.js?<%=request.getAttribute("version")%>"></script>
</body>
</html>