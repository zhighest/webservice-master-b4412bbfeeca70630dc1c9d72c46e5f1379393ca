<%--
  Created by IntelliJ IDEA.
  User: R.hao
  Date: 2017/8/29
  Time: 14:31:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@  include file="../header.jsp"%>
<!--引入分享-->
<%@ include file="../../common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>2周年庆 感恩有你</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/layout.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/weixin/anniversary/anniversary.css?<%=request.getAttribute("version")%>" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/weixin/anniversary/animate.css?<%=request.getAttribute("version")%>" />
	</head>

	<body>
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<!--第一栏-->
					<div class="page">
						<!--滑动提示-->
						<span class="changePage">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/icon_arrow.png"/ class="imgBanner">
						</span>
						
						
						<p class="MT100 textC positionA clolrffdb85 width100 bottom70 zIndex2">查看成长之路</p>
						<div class="height100 positionR ">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj.png" class="imgBanner">
							<div class="height30 positionA width100 top0">
								<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/title.png" alt="" class="width90 positionA left5 top30 zIndex2">
								<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/balloon.png" class='balloon'>
							</div>
							<div class="positionA height70 top30 width100 MT40">
								<div class="pageOneContent textL clolrffdb85">
									<div class="wordShow">
										<p class="textC">2年时光匆匆</p>
										<p class="MT20 textC">我们有幸相遇、相知、相爱</p>
										<p class="MT20 textC">您的信赖是我们前进的动力</p>
										<p class="MT20 textC">感恩一路上有你</p>
										
									</div>
								</div>
							</div>
						</div>
						<!--动画-->
						<div class="animates">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png"  class="startFlashOne top0">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashTwo">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashThree" style="top:64%;">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat4.png" class="ribbonOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat3.png" class="ribbonTwo">
							<!--<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line2.png" class="meteoricOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line3.png" class="meteoricTwo">-->
						</div>
					</div>
				</div>
				<div class="swiper-slide">
					<!--第一栏-->
					<div class="page pageOne">
						<!--滑动提示-->
						<span class="changePage">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/icon_arrow.png"/ class="imgBanner">
						</span>

						<div class="height100 positionR ">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj.png" class="imgBanner">
							<div class="height30 positionA width100 top0">
								<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/title.png" alt="" class="width90 positionA left5 top30 zIndex2">
								<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/balloon.png" class='balloon'>
							</div>
							<div class="positionA height70 top30 width100 MT40">
								<div class="pageOneContent textL clolrffdb85">
									<div class="wordOne">
										<p><span class="year"></span>年<span class="month"></span>月<span class="day"></span>日，您加入了联璧大家庭</p>
										<p class="MT15 fadeInDown">成为我们的第<span class="ML10 MR10 yellowBackground colorfa324c font24 strong rank">12000012</span>位用户</p>
										<p class="MT10">时光荏苒</p>
										<p class="MT15">我们已经相伴走过了<span class="ML10 MR10 yellowBackground colorfa324c font24 strong timeLong">0</span>天</p>
									</div>
								</div>
							</div>
						</div>
						<!--动画-->
						<div class="animates">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashOne top0">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashTwo left30">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashThree">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat4.png" class="ribbonOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat3.png" class="ribbonTwo">
						</div>
					</div>
				</div>
				<div class="swiper-slide">
					<!--第二栏-->
					<div class="page pageTwo">
						<!--滑动提示-->
						<span class="changePage">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/icon_arrow.png"/ class="imgBanner">
						</span>
						<div class="height100 positionR">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj.png" class="imgBanner">
							<div class="pageOneContent clolrffdb85 textC">
								<p class="MT30">签到次数<span class="ML10 MR10 yellowBackground colorfa324c font24 strong signNum">0</span>次</p>
								<p class="MT20">兑换礼包<span class="ML10 MR10 yellowBackground colorfa324c font24 strong giftPackageNum">0</span>个</p>
								<p class="MT20">邀请好友<span class="ML10 MR10 yellowBackground colorfa324c font24 strong inviteFriendNum">0</span>个</p>
							</div>
							<div class="width85 blockC positionA top44 left7P5">
								<div class="overHidden height0 animateStyle positionR">
									<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/base3.png" class="imgInitial positionA">
								</div>
								<p class="positionA totalOneB colorfa324c animateHave">累计投资&nbsp;&nbsp;&nbsp;&nbsp;<span class="strong font24 investAmount ">0</span>元</p>
								<p class="positionA totalTwoB colorfa324c animateHave">累计积分&nbsp;&nbsp;&nbsp;&nbsp;<span class="strong font24 pointAmount ">0</span>分</p>
								<p class="MT20 clolrffdb85 sloganTwo" >恭喜您成为我们<span class="vipGradeName">普通会员</span></p>
							</div>
						</div>
						<!--动画-->
						<div class="animates">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashOne left70">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashTwo top30P">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashThree" style="top:84%;">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat1.png" class="ribbonOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat3.png" class="ribbonTwo">
							<!--<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line2.png" class="meteoricOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line3.png" class="meteoricTwo">-->
						</div>

					</div>
				</div>
				<div class="swiper-slide">
					<!--第三栏-->
					<div class="page pageThree">
						<!--滑动提示-->
						<span class="changePage">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/icon_arrow.png"/ class="imgBanner">
						</span>
						<div class="height100 positionR">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj.png" class="imgBanner">
							<div class="pageOneContent clolrffdb85">
								<p class="title">通过联璧金融平台，您使用了</p>
							</div>
							<div class="width85 blockC positionA top20 left7P5">
								<div class="overHidden height0 animateStyleThree positionR">
									<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/base.png" class="imgInitial positionA">
								</div>
								<p class="positionA ticketOne colorfa324c animateHave"><span class="strong font30 interestCouponNum">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张加息券</p>
								<p class="positionA ticketTwo colorfa324c animateHave"><span class="strong font30 jsmCouponNum">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张加速券</p>
								<p class="positionA ticketThree colorfa324c animateHave"><span class="strong font30 cashCouponNum">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张代金券</p>
								<p class="positionA ticketFour colorfa324c animateHave"><span class="strong font30 tsmCouponNum">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;张k码券</p>
								<p class="MT20 clolrffdb85 sloganThree"><span class="CouponNum">您参与的活动次数有点少哦！</span></p>
							</div>
						</div>

						<div class="animates">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashOne Left80 top0">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashTwo top78P">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashThree">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat2.png" class="ribbonOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat3.png" class="ribbonTwo">

							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/balloon.png">
							<!--<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line2.png" class="meteoricOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line3.png" class="meteoricTwo">-->
						</div>
					</div>
				</div>
				<div class="swiper-slide">
					<!--第四栏-->
					<div class="page pageFour">
						<div class="height100 positionR">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/bj.png" class="imgBanner">

							<div class="width85 blockC positionA top7 left7P5">
								<div class="overHidden height0 animateStyleFour positionR">
									<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/base6.png" class="imgInitial positionA">
								</div>
								<p class="totalOneA colorfa324c   animateHave">您已累计赚取收益</p>
								<p class="positionA totalOne colorfa324c  animateHave"><span class="strong font24 income">0</span>元</p>
								
								<div class="pageFourContent clolrffdb85 MT20 sloganFive" style="width:100%;">
										<p class="titleTwo">联璧金融，帮您把零钱连起来</p>
								</div>
								<p class="slogan sloganFour clolrffdb85 MT20 visibilityHidden">
									<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/horn.png" class=""><span class="sloganEight"></span>
								</p>
								<img src='${pageContext.request.contextPath}/pic/weixin/anniversary/button1.png' class="inviteFriend toShare MT20 sloganSix">
							</div>

						</div>

						<div class="animates">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashOne Left80P">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashTwo">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/fireworks.png" class="startFlashThree">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat4.png" class="ribbonOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/flloat3.png" class="ribbonTwo">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/balloon.png">
							<!--<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line2.png" class="meteoricOne">
							<img src="${pageContext.request.contextPath}/pic/weixin/anniversary/line3.png" class="meteoricTwo">-->
						</div>
					</div>
				</div>

			</div>
			<!-- 如果需要分页器 -->
			<div class="swiper-pagination"></div>

			<!-- 如果需要导航按钮 -->
			<div class="swiper-button-prev"></div>
			<div class="swiper-button-next"></div>

			<!-- 如果需要滚动条 -->
			<div class="swiper-scrollbar"></div>
			<div class="sharePopup" style="display: none;">
				<div class="textSite">
					<img src="${pageContext.request.contextPath}/pic/weixin/shareTex.png?<%=request.getAttribute("version")%>" height="120">
				</div>
			</div>
			
			
		</div>
		<input id="userId" name="userId" type="hidden" value="${userId}">
		<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>">
		<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>">
		<input id="mobileWX" name="mobileWX" type="hidden" value="${mobile}">
		<input id="Url" name="Url" type="hidden" value="${pageContext.request.contextPath}">
	
	</body>
	<script src="" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/swiper-3.4.1.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/anniversary/anniversary.js?<%=request.getAttribute("version")%>"></script>

</html>