<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>自定义菜单</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/customizeMenus.css?<%=request.getAttribute(" version ")%>" />
		<%@  include file="../header.jsp"%>
	</head>
	<body>
		<span class="bth" id="bth">编辑</span>
		<div class="header" id="header">
			<ul id="header_ul" class="header_ul">
				<li id="6">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxactivity/goLDActivityDetal">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconKBlack@2x.png" width="40%" />
						<span>K码激活</span>
						</a>
					</div>
				</li>
				<li id="1">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxpay/redirectPay?payFlag=WX">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconInvestmentBlack@2x.png" width="40%" />
						<span>充值</span>
						</a>
					</div>
				</li>
				<li id="2">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxquest/goDailyQuest">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconShortcutMenuRateHikeCoupon@2x.png" width="40%" />
						<span>加息券</span>
						</a>
					</div>
				</li>
				<li id="3">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxactivity/goActivityList">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/TouchIconAcitvity@2x.png" width="40%" />
						<span>最新活动</span>
						</a>
					</div>
				</li>
				<li id="4">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxtrade/goInviteFriend?invitationCd=">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconInviteBlack@2x.png" width="40%" />
						<span>邀请</span>
						</a>
					</div>
				</li>
				<li id="5">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxtrade/goWithdrawDeposit">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconWithdrawBlack@2x.png" width="40%" />
						<span>提现</span>
						</a>
					</div>
				</li>
				<li id="7">
					<div class="wrap" >
						<div class=" none wrap_div url_img"></div>
						<a href="${pageContext.request.contextPath}/wxproduct/goDemandProductIndex?fixDemandSwitch=demand">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconCurrentBlack@2x.png" width="40%" />
						<span>活期投资</span>
						</a>
					</div>
				</li>
			</ul>
		</div>
		<div class="section">
			<h1>更多功能</h1>
		</div>
		<div class="content">
			<ul id="content_ul" class="content_ul">
				<li id="9">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxproduct/goDemandProductIndex?fixDemandSwitch=fix">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconFixedTermBlack@2x.png" width="40%" />
						<span>定期投资</span>
						</a>
					</div>
				</li>
				<li id="10">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxtrade/goSetting">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconShortcutMenuSettings@2x.png" width="40%" />
						<span>设置</span>
						</a>
					</div>
				</li>
				<li id="13">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxdeposit/goTradingRecord">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconTransactionBlack@2x.png" width="40%" />
						<span>交易记录</span>
						</a>
					</div>
				</li>
				<li id="14">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxquest/goDailyQuest">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/TouchIconSign@2x.png" width="40%" />
						<span>签到</span>
						</a>
					</div>
				</li>
				<li id="15">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/taoluyou/guide/9D154DD9C8A7A991AD124B4463CC1723">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconObtainK2@2x.png" width="40%" />
						<span>购买K2</span>
						</a>
					</div>
				</li>
				<li id="16">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxquest/goDailyQuest">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconRatecouponBlack@2x.png" width="40%" />
						<span>每日加息</span>
						</a>
					</div>
				</li>
				<li id="17">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxInvest/getVoucherList">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/ShortcutMenuVouchers@2x.png" width="40%" />
						<span>抵用券</span>
						</a>
					</div>
				</li>
				<li id="18">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxuser/goVoucher">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconshortcutMenuVouchers@2x.png" width="40%" />
						<span>代金券</span>
						</a>
					</div>
				</li>
				<li id="21">
					<div class="wrap" >
						<div class=" none wrap_div url_img1"></div>
						<a href="${pageContext.request.contextPath}/wxInvest/getPlanProductIndex">
							<img src="${pageContext.request.contextPath}/pic/weixin/customizeMenus/iconShortcutMenuExcellentProject@2x.png" width="40%" />
						<span>优享计划</span>
						</a>
					</div>
				</li>
			</ul>
		</div>
		<input type="hidden" name="userId" id="userId" value="${userId}" >
		<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
		<input type="hidden" name="channel" id="channel" value="${channel}" >	
		<script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/weixin/page/customizeMenus.js"></script>
		<%@  include file="../baiduStatistics.jsp"%>
	</body>
</html>