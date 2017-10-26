<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>大家来激活“K码”</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" /> -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityKCode.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
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
</head>
<body class="acitivityBkg positionR">
	
	<div class="loginBkg positionR PB50">
		<div class="content">
			<div id="wrap">
				<ul class="white none" id="white">
					<li class="red">请输入联通手机号验证</li>
					<li><input class="inlineB" type="text" placeholder="请输入手机号"  id="phoneNumber" name="phoneNumber" maxlength="11" value="${userDto.phoneNumber}" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);"/></li>
					<li><input class="inlineB fl" placeholder="发送验证码"  id="code" name="code" maxlength="4" value="${userDto.code}" oninput="$.checkLimit($(this),'',false);checkA('#code','#confirm');" onkeyup="$.checkLimit($(this),'',false);checkA('#code','#confirm');" onafterpaste="$.checkLimit($(this),'',false);checkA('#code','#confirm');"/>
					<a class="sms inlineB fl" id="sms">获取验证码</a>
					</li>
					<li><span class="cancel active active2 inlineB" id="close" >取消</span><span class="confirm active active2 inlineB" id="confirm">确定</span></li>
				</ul>	
				<ul class="white none" id="newWhite">
					<li class="red">请填写邀请码</li>
					<li><input class="inlineB " placeholder="请输入邀请码"  id="newCode" name="newCode" maxlength="11" value="${userDto.newCode}" oninput="$.checkLimit($(this),'',false);checkA('#newCode','#newConfirm');" onkeyup="$.checkLimit($(this),'',false);checkA('#newCode','#newConfirm');" onafterpaste="$.checkLimit($(this),'',false);checkA('#newCode','#newConfirm');"/>
					<li><span class="cancel active active2 inlineB" id="close1" >取消</span><span class="confirm active active2 inlineB" id="newConfirm">确定</span></li>
				</ul>
			</div>
			<div class="width90 blockC" id="hasActivate">
				<div class="textR MB5" id="tips">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/markIcon.png" id="" width="18" class="MR5">
					<span class="actYellowTex verTop">如何查看K码</span>
				</div>
				<input id="kCode" class="inputAct radius5 width90 blockC block MB20 font18" placeholder="输入K码，请区分大小写" maxlength="10" >
                <div class="inputArea width90 blockC clearfix">
                	<input type="text" class="inputAct fl radius5 width60 blockC block MB20 font18" name="verifycode" placeholder="请输入验证码" id="verifycode" maxlength="4"/>
					<img id="imgcode" class="radius5 ML5P" src="${pageContext.request.contextPath}/imgcode" height="52" width="35%">
				</div>
				<a class="btn actBtn width90 blockC block radius5 font20" id="postCode">激活</a>
				<div class="MT15 verTop textL width90 blockC" style="display: block;">
					<span class="" id="checkBox"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/checkBox1.png" id="checkboxImg" width="20"></span>
					<span class="inlineB whiteTex ML5">我已阅读并同意<a href="${pageContext.request.contextPath}/wxactivity/goActivityKCodeDetail" class="underLine whiteTex">活动规则</a></span>
				</div>
				<div class="none blockC font14 whiteTex MT20 positionR PL20">
					<img class="positionA walletIcon" src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/wallet.png" width="18">
					您已激活<span class="goldTex font18" id="nowUseNum"></span>张K码
				</div>
				<p class="font14 whiteTex MT10 none">每个手机最多激活<span class="font18" id="totalToplimitNum"></span>张K码</p>
				<!-- <a href="${pageContext.request.contextPath}/wxactivity/goActivityKCodeInfo" class="blockC inlineB MT15 goldTex PTB5 positionR statusBtn radius5 PR10">
					<img class="positionA" src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/statusIcon.png" width="22">
					<span class="inlineB font18">资格查询</span>
				</a> -->
			</div>
			<div class="none" id="fullActivate">
				<div class="investTip pTB2P PLR5P whiteTex width75 blockC lineHeight1_5x">
					您激活的K码数量已达上限<br/>感谢您的参与
				</div>
<!-- 				<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P none" href="${pageContext.request.contextPath}/wxabout/goIndex" id="goIndex">去首页</a> -->
				<!--微信端按钮-->
				<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P none" id="weixinGoIndex">去首页</a>
				<!--Ios按钮-->
				<label id="lianbiIosGoIndex" style="display: none;">
					<input type="button" value="" style="display: none;" id="iosBtnGoIndex"/>
					<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P" id="lianbiIconGoIndex">去首页</a>
				</label>
				<!--安卓按钮-->
				<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P " id="lianbiAndroidGoIndex" style="display: none;">去首页</a>
				
			</div>
			<div class="none" id="noActivate">
				<div class="investTip pTB2P PLR5P whiteTex width75 blockC lineHeight1_5x">
					您尚需单笔投资<span id="remanPeriods"></span>月期以上定期产品满<span id="needAmount"></span>元，就可激活下一个K码
				</div>
<!-- 				<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P" id="goDemandProductIndex">立即投资</a> -->
				<!--微信端按钮-->
				<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P" id="weixinGoFixInvest" style="display: none;">立即投资</a>
				<!--Ios按钮-->
				<label id="lianbiIosGoFixInvest" style="display: none;">
					<input type="button" value="" style="display: none;" id="iosBtn"/>
					<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P" id="lianbiIconGoFixInvest">立即投资</a>
				</label>
				<!--安卓按钮-->
				<a class="width80 block blockC heigh50 goIndex redTex1 font20 MT10P MB5P" id="lianbiAndroidGoFixInvest" style="display: none;">立即投资</a>
				
				
				<a class="underLine whiteTex" href="${pageContext.request.contextPath}/wxactivity/goActivityKCodeDetail">查看活动规则</a>
			</div>
			<div class="width80 blockC textC MT50 none" id="btmQrcode"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/btmQrcode.png" alt="" width="50%"></div><!--长按图片-->
		</div>		
	</div>
	<!-- 用户登录弹窗 -->
	<div class="popup " id="pop1">
		<div class="popCon blockC width90 PTB5P radius5">
			<!-- <img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/closedBtn.png" width="20" class="closedBtn positionA"> -->
			<div class="width90 blockC">
				<h4 class="font18">输入领取手机号</h4>
				<div class="MT20">
					<!-- 手机号 -->
					<input type="tel" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="手机号" class="inputBkg1 MB20 width100 radius5" />
					<!-- 图片验证码 -->
					<!--<div class="clearfix">
						<input type="text" class="inputBkg1 MB20 width60 fl radius5" name="imgVerifycode" placeholder="图形验证码" id="imgVerifycode" maxlength="4"/>
						<img id="imgCodeTx" src="${pageContext.request.contextPath}/imgcode" height="46" width="35%" class="fr radius5" style="width:35%;">
					</div>-->
					<div style="margin: 10px auto;" id="captcha_div"></div> 
					
					<div class="clearfix captcha_div">
					<!-- 验证码 -->
						<input type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="4" class="inputBkg1 MB20 width60 fl radius5" />
						<a class="btn width35 fr PLR1P smallBtn1 radius5" id="gainCode">获取<br/>验证码</a>
					</div>
					<div class="textL MB15 font14">
						<span class="inlineB blackTex">我同意<a href="${pageContext.request.contextPath}/wxabout/regAgreement" class="blackTex underLine">《用户服务协议》</a></span>	
					</div>
				<div><a class="btn width100 block radius5 forbid" id="loginSubmit">登录</a></div>
				<div class="MT20 textC">-新用户也可直接登录-</div>
			</div>
		</div>		
	</div>
	</div>
	<!-- 查看K码弹框 -->
	<div class="popup none" id="pop2">
		<div class="blockC width90 PTB5P popConImg positionR">
			<span class="closedLetterBtn positionA"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/closedBtn.png" id="" width="30" class="MR5"></span>
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityLCode/KcodeTips.png" id="" width="100%" class="MR5">
		</div>		
	</div>
	<!-- 提醒APP登录弹框 -->
	<div class="popup none" id="popup1">
		<div class="popCon2 blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<h4 class="font22 redTex">提 示</h4>
				<img class="MT5" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/warning.png" alt="" width="30%">
				
				<p class="font20 MT5 blackTex heigh50">请先登录再参加活动</p>
				<!-- <a id="popBtn1" class="btn block width100 MT15 radius5 font16">确定</a> -->
			</div>
		</div>	
	</div>
	<!-- 关掉微信激活功能弹框 -->
	<div class="popup none" id="pop3">
		<div class="blockC width90 MT5P positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activityKCodeIntroduce/Kcancel.png" id="" width="80%" class="MR5">
		</div>		
	</div>
	<a class="meiqiaIcon"></a>
	<input type="hidden" name="isLogin" id="isLogin" value="${isLogin}" >
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
	<input type="hidden" name="channel" id="channel" value="${channel}" >
	<input type="hidden" name="KSwitch" id="KSwitch" value="<%=request.getParameter("KSwitch")%>" >
	<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeLogin.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeshare.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

