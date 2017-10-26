<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../../common.jsp" %>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
<title>组队投资 一起赚奖励</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/team/teamRegister.css?<%=request.getAttribute("version")%>" />

<%@  include file="../../header.jsp" %>
</head>
<body>
	<div class="teamWrapper width100">
		<div class="teamText width100 PT20P">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/themeWords2x.png?<%=request.getAttribute("version")%>">
		</div>

		<div class="regsiter-wrapper">
			<div class="phoneNum width80 ML10P">
				<input class="width95 inputNoborder"placeholder="请输入手机号" type="tel" name="phoneNum" id="phoneNum" value="${phoneNum}" maxlength="11" oncopy="return false;" onpaste="return false" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" maxLength="11" />
			</div>

			<div class="verification width80 ML10P positionR">
				<input class="width95 inputNoborder" placeholder="请输入验证码" type="tel" id="checkCode" name="checkCode" value="" oncopy="return false;" onpaste="return false" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" />
				<span class="positionA code" id="gainCode">获取验证码</span>
			</div>

			<div class="phoneNum width80 ML10P">
				<input class="width95 inputNoborder" placeholder="请设置登录密码" type="password" name="password" id="password" value=""
maxlength="16" />
			</div>

			<div class="phoneNum width80 ML10P">
				<input class="width95 inputNoborder"  placeholder="请设置交易密码(6位数字)" type="password" name="passwordCash" id="passwordCash" value="" oncopy="return false;" onpaste="return false" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6">
			</div>

			<div class="regsiter width80 ML10P MT2P" id="loginSubmit">
				<a>立即注册</a>
			</div>

			<div class="clearfix width80 ML10P MT5">
				<a class="fl font12 whiteTex ML2P opacity80" id="register">我同意《用户注册服务协议》</a>
				<a class="fr font12 whiteTex MR2P" href="${pageContext.request.contextPath}/team/teamLogin<%=queryString%>">立即登录</a>
			</div>

		</div>

		<div class="content">
			<div class="title font18 whiteTex">
				<span>联璧金融优势</span>
			</div>
			<ul class="clearfix contentList">
				<li class="fl contentBg">
					<div class="MT10P">
						<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/security.png?<%=request.getAttribute("version")%>">
					</div>
					<h4 class="font14 blackTex2 MT2P">资金安全保障</h4>
					<p class="desc font12 blackTex1 MT2P">账号资金安全第三方支付托管,核心企业担保</p>
				</li>
				<li class="fl contentBg">
					<div class="MT10P">
						<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/shortcuts.png?<%=request.getAttribute("version")%>">
					</div>
					<h4 class="font14 blackTex2 MT2P">平台实力</h4>
					<p class="desc font12 blackTex1 MT2P">B轮融资,1亿元注册资金</p>
				</li>
				<li class="fl contentBg">
					<div class="MT10P">
						<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/safeguard.png?<%=request.getAttribute("version")%>">
					</div>
					<h4 class="font14 blackTex2 MT2P">活期理财</h4>
					<p class="desc font12 blackTex1 MT2P">当日计息，资金快速提现</p>
				</li>
				<li class="fl contentBg">
					<div class="MT10P">
						<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/scattered.png?<%=request.getAttribute("version")%>">
					</div>
					<h4 class="font14 blackTex2 MT2P">百万用户选择</h4>
					<p class="desc font12 blackTex1 MT2P">超 100 万用户信赖,帮您零钱存起来?</p>
				</li>
			</ul>
		</div>

		<div class="qrCode">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/qr-code.png?<%=request.getAttribute("version")%>">
		</div>
		<!-- App下载 -->
		<div class="btmAreaFix clearfix" id="btmAreaFix">
			<div class="leftArea">
				<span class="closed" id="closed">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/Suspension-close.png?<%=request.getAttribute("version")%>" width="20">
				</span>
				<span class="logo">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/team/icon_logo.png?<%=request.getAttribute("version")%>" width="35">
				</span>
			</div>
			<div class="rightArea textL">
				<h4>联璧金融</h4>
				<p>使用APP客户端，体验更多惊喜！</p>
				<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" class="btmFixBtn">下载APP</a>
			</div>
		</div>

	<p class="font12 grayText1 MT20 copyright">此次活动由联璧金融发起<br/>若对活动规则存在疑问,请与联璧金融客服联系咨询</p>
	</div>

    <input type="hidden" name="URL" id="URL" value="${URL}" >

    <input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
    <input type="hidden" name="teamId" id="teamId" value="<%=request.getParameter("teamId")%>" >
    <input type="hidden" name="teamName" id="teamName" value="<%=request.getParameter("teamName")%>" >
    <input type="hidden" name="creatTeam" id="creatTeam" value="<%=request.getParameter("creatTeam")%>" >
    <input type="hidden" name="jionTeam" id="jionTeam" value="<%=request.getParameter("jionTeam")%>" >
    <input type="hidden" name="invitationCode" id="invitationCode" value="<%=request.getParameter("invitationCode")%>" >

    <script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
    var message = "${message}";
    </script>
    <script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
    <script src="${pageContext.request.contextPath}/js/weixin/page/team/teamRegister.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../../baiduStatistics.jsp" %>
</body>
</html>