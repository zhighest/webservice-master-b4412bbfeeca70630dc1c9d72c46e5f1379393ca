<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html class="html">

	<head>
		<title>帮助中心-联璧金融</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="description" content="联璧金融帮助服务，介绍关于联璧金融投资的常见问题，注册、认证、充值、取现、投资操作等操作演示。解答您对于联璧金融的疑问问题" />
		<meta name="keywords" content="帮助服务，联璧金融帮助服务" />
		<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute(" version ")%>"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/helpCenter.css?<%=request.getAttribute(" version ")%>"/>
	</head>

	<body class="fontFamily">
		<div id="wrap">
			<div id="main">
				<c:set var="pageIndex" value="5" />
				<%@  include file="../header.jsp"%>
				<div class="contentWrapper positionR">
					<!---次级导航 begin -->
					<div class="wrapper">
						<div class="clearfix PTB30">
							<c:set var="helpSidebar" value="9" />
							<%@  include file="helpSidebar.jsp"%>
							<ul class="fr inlineBUl rightGuild font12 MT7">
								<li>当前位置：
									<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation" class="blackTex" rel="nofollow">帮助中心<span class="MLR5">/</span></a>
								</li>
								<li class="redTex">Q&A</li>
							</ul>
						</div>
					</div>
					<!---次级导航 end -->
					<div class="helpCenterBanner banner1">
						<div class="wrapper">
							<div class="MT180">
								<p class="subTitle whiteTex show1">平台产品</p>
								<p class="subDes whiteTex show5">Platform products</p>
							</div>
						</div>
					</div>
					
					<div class="wrapper">
						<div class="ProblemClassification MT40">
							<p class="problemTitle fontBold">平台产品</p>
							<ul class="MT20">
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90">1、你们是什么平台？</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20  ">答：联璧金融是联璧科技旗下的专业理财平台，为客户提供专业的互联网金融服务。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">2、你们有哪些产品？收益怎么样？</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：联璧金融目前推出的产品叫做铃铛宝，分为铃铛宝定期和零钱计划两类。其中，铃铛宝定期产品细分为：3月期产品，6月期产品。另外联璧金融推出零钱计划，当天15点之前提现当天到账，随存随取，灵活方便。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20  PT20 bottomGray "><span class="ProblemInlineBlock width90 ">3、你们平台安不安全？</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：联璧金融平台有多重的安全保障。
联璧金融平台实行的是同卡进出，并且是与第三方支付公司签署了账户资金托管，保证了您资金进出的安全。其次，联璧金融拥有优质资产，资产方会提供足够的质押物。联璧金融还有严格的风控，对于资产方联璧金融会进行实际调查，并且资产方还需要缴纳履约保证金。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">4、理财有哪些费用？</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：目前联璧金融平台充值、提现相关的手续费均由平台代付，客户无需支付任何费用。</div>
								</li>
							</ul>
						</div>
						
						<div class="ProblemClassification MT40">
							<p class="problemTitle fontBold ">注册认证</p>
							<ul class="MT20">
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">1、首次登陆，没有收到验证码怎么办?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c  PB20 ">答：建议核对输入的手机号码以及格式是否正确，并再次发送。如还是无法接收，致电<span class="JsPhoneCall"></span>咨询处理。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">2、为什么要进行身份认证?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：为了出借方和借款方能够在联璧金融平台上进行公平、公正、公开的合作交易，并且为了确保交易双方提供的资料真实性，以及出借方和借款方的资金安全，联璧金融与第三方身份验证公司进行合作，要求每位客户都要进行身份验证。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">3、我的实名认证无法通过怎么办?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：请您仔细核对填写的姓名和身份证号码是否正确，然后重新尝试验证操作。如果还是无法通过，建议致电<span class="JsPhoneCall"></span>咨询处理。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">4、密码忘记了怎么办?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：“联璧金融”APP和“联璧钱包”微信公众号的设置中提供密码找回功能</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">5、手机号码更改了怎么办?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：手机号码作为客户登陆联璧金融平台的唯一用户名，原则上无法修改。如客户已充值或购买理财，建议处理如下：<br>
																									①客户前往银行柜台修改银行卡预留手机号码；br>
																									②银行卡预留手机号码修改了之后致电<span class="JsPhoneCall"></span>咨询处理。</div>
								</li>
							</ul>
						</div>
						
						
						<div class="ProblemClassification MT40">
							<p class="problemTitle fontBold ">操作流程</p>
							<ul class="MT20">
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">1、如何在网站进行注册/登陆?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20  ">答：方式一：关注“联璧钱包”微信公众号，点击马上投资-注册绑定，填写手机号码和验证码，点击确定按钮，便可完成注册/登陆；方式二：下载“联璧金融”APP，点击图标打开应用，填写手机号码和验证码，点击确定按钮，便可完成注册/登陆。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">2、如何身份认证?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：登陆后在个人设置中进行身份认证。如您未设置，在充值绑卡过程中根据系统提示完成身份认证。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">3、如何完善个人信息?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：请登陆联璧金融账户后，点击个人设置中的基本资料进行完善或更改。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">4、如何进行理财?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：关注 “联璧钱包”并登陆或登陆“联璧金融”APP，通过身份验证后即可开始投资。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">5、如何退出提现？</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：如果您购买的是铃铛宝定期产品，到期后系统自动本金和利息返还至账户可用余额，点击“提现”按钮可发起提现申请。
																					如果您购买的是零钱计划产品，首先您需要操作转出，将在投金额转出至账户可用余额，其次可以点击“提现”按钮可发起提现申请。
																					工作日当天15点之前提现一般情况下当天可以到银行卡内，15点之后提现第二个工作日到银行卡内。</div>
								</li>
							</ul>
						</div>
						
						<div class="ProblemClassification MT40">
							<p class="problemTitle fontBold ">充值提现</p>
							<ul class="MT20">
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">1、充值要手续费吗?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：充值手续费为第三方支付平台收取，当前联璧金融代付，出借人免费充值。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">2、充值对银行卡有没有限制，是否可以用信用卡充值?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：您使用借记卡进行充值是根据第三方支付平台对接的银行相关支付限制，具体您可以在充值的时候参考充值页面中的银行限额表。其它充值失败的原因可能是您的银行卡设置了转账上限，您可以致电相关银行客服进行咨询。不支持信用卡充值。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">3、充值后，何时能在联璧金融账号看到充值成功?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：充值成功后您可以进行界面刷新，刷新完成后即可在您的账户中显示充值金额。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">4、可以线下充值吗?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">①网站（http://www.lianbijr.com/）；<br>
②“联璧钱包”微信公众号；<br>
③“联璧金融”APP。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">5、提现安全吗，用别人的银行卡信息能提现吗?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：为了保障您的资金安全，第三方支付平台会将您提现的信息与个人信息进行比对，确认无误后才可进行提现，无法使用他人银行卡进行提现。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">6、银行卡冻结或者丢失了怎么办?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：银行卡冻结会造成提现失败。您可以到您所在地的银行申请挂失和补办银行卡，或者拨打银行客服电话进行咨询。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">7、申请提现了，还可以撤销吗?</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：很抱歉，提现申请后系统会通过第三方支付将您的提现申请反馈至您的联璧金融账户，不能取消。</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">8、各银行客服热线</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：<br>
工商银行	95588	农业银行	95599 中国银行	95566<br>
建设银行	95533 浦发银行	95528 交通银行	95559<br>
北京银行	95526 招商银行	95555 光大银行	95595<br>
中信银行	95558 华夏银行	95577 民生银行	95568<br>
广发银行	95508	平安银行 95511 邮储银行 95580</div>
								</li>
								<li class="MB20">
									<p class="heightAuto PB20 PT20 bottomGray "><span class="ProblemInlineBlock width90 ">9、如何更换已绑定的银行卡？</span><img class="width20P" src="${pageContext.request.contextPath}/pic/web/help/downArrow.jpg"/></p>
									<div class="MT20 font14 Color9c PB20">答：银行卡涉及客户资金账户安全，建议处理如下：<br>
①	关注“联璧钱包”微信公众号，发送身份证正反面照片和客户手持身份证正面照，同时告知具体修改事项；<br>
②致电<span class="JsPhoneCall"></span>咨询处理。</div>
								</li>
							</ul>
						</div>
					</div>

					

					

					
				</div>
			</div>
		</div>
		<%@  include file="../footer.jsp"%>
		<%@  include file="../baiduStatistics.jsp"%>
		<input id="hasHeadFoot" name="hasHeadFoot" type="hidden" value="${hasHeadFoot}">
		<script src="${pageContext.request.contextPath}/js/web/page/helpCenter.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/web/page/subBanner.js?<%=request.getAttribute(" version ")%>"></script>
		<script src="${pageContext.request.contextPath}/js/web/page/helpCenterQA.js?<%=request.getAttribute(" version ")%>" type="text/javascript" charset="utf-8"></script>
	</body>

</html>