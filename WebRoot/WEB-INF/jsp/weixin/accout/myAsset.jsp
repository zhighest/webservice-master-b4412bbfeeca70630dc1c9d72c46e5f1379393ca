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
	<title>我的账户</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/myAsset.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
	</head>
<body class="positionR">
	<%@ include file="../tabBar.jsp"%>

	<!--等级升级动画Start-->
	<div class="screenBkg positionF none"></div>
	<div class="gradeUp positionF none">
		<img class="gradeUpImg positionA" src="" />
		<img class="fr closedBtn" src="${pageContext.request.contextPath}/pic/weixin/closedBtn.png" />
		<div class="gradeUpWrap whiteBkg radius5">
			<p class="blackTex2 msgContent"></p>
		</div>
		<a class="block whiteTex MT10 heigh50 redIndexBkg radius3 " href="${pageContext.request.contextPath}/wxdeposit/goVipGrade">忍不住看</a>
	</div>
	<!--等级升级动画End-->

	<!-- 公告的二级弹窗 begin-->
    <div class="positionF none" id="message">
      <div class="positionR changeBox clearfix">
        <img src="${pageContext.request.contextPath}/pic/weixin/ver2_9/messageBox.png?<%=request.getAttribute("version")%>" class="width100">
        <div class="width100 whiteBkg message PB20">
          <div class="width80 blockC">
            <h4 class="lineHeight2x messageTitle"></h4>
            <p class="textL font14 lineHeight1_5x messageMess"></p>
            <a id="know" class="redBkg whiteTex radius5 block MT10 pTB2P">知道了</a>
          </div>
        </div>
      </div>
    </div>
    <!-- 公告的二级弹窗 end-->
	<div class="content PB60">
		<div class="positionR PB210 scrollMessage bkg3">
			<!-- 积分等级开关打开 -->
	<!-- <c:if test="${pointSwitch eq 'Y'}">
				<c:if test="${pointGrade eq '1'}">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/generalLevel.png" class="myAssetBkg">
				</c:if>
				<c:if test="${pointGrade eq '2'}">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/goldLevel.png" class="myAssetBkg">
				</c:if>
				<c:if test="${pointGrade eq '3'}">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/platinumLevel.png" class="myAssetBkg">
				</c:if>
				<c:if test="${pointGrade eq '4'}">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/supremeLevel.png" class="myAssetBkg">
				</c:if>
				<c:if test="${pointGrade eq '5'}">
					<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/diamondLevel.png" class="myAssetBkg">
				</c:if>
			</c:if> -->
			<!-- 积分等级开关关闭
			<c:if test="${pointSwitch eq 'N'}">
				<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/generalLevel.png" class="myAssetBkg">
			</c:if> -->
			<!-- 用户基本信息 begin -->
			<div class="width100 PTB10 none" id="userMessage">
				<div class="moveAni none" id="moveAni">您有<span id="totalMsgNum">1</span>条未读消息！</div>
				<div class="textC clearfix width90 blockC">
					<table class="width100">
						<tr>
							<td width="50px" align="left">
								<div class="avatar blockC borderA fl clearfix">
									<img id="defaultAvatar" src="${pageContext.request.contextPath}/pic/weixin/version1125/defaultAvatar.png?<%=request.getAttribute("version")%>">
									<div class="levelIcon">
										<!-- 积分等级开关打开 -->
										<c:if test="${pointSwitch eq 'Y'}">
											<c:if test="${pointGrade eq '1'}">
												<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/general.png">
											</c:if>
											<c:if test="${pointGrade eq '2'}">
												<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/gold.png">
											</c:if>
											<c:if test="${pointGrade eq '3'}">
												<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/platinum.png">
											</c:if>
											<c:if test="${pointGrade eq '4'}">
												<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/supreme.png">
											</c:if>
											<c:if test="${pointGrade eq '5'}">
												<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/diamond.png">
											</c:if>
										</c:if>
										<!-- 积分等级开关关闭 -->
										<c:if test="${pointSwitch eq 'N'}">
										</c:if>
									</div>
								</div>
							</td>
							<td>
								<h4 class="ML5"><font id="phoneNum" class="whiteTex font16"></font></h4>
								<a class="whiteTex ML5 font14 textL" id="gradeName">普通会员</a>
							</td>
							<!-- 消息提示begin -->
							<td class="positionR" align="right">
								<img class="messageIcon" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconNewsBlack.png?<%=request.getAttribute("version")%>" width="40">
								<span class="positionA whiteBkg point none" id="dotted"></span>

							</td>
							<!-- 消息提示 end -->
						</tr>
					</table>
				</div>
			</div>
			<!-- 遮罩 begin -->
			<div  id="messageBox" class="none">
				<div class="messageBox radius5 positionA">
					<ul class="font14" id="msg">
					</ul>
				</div>
			</div>
			<!-- 遮罩 end -->
			<!-- 用户基本信息 end -->
			<!-- 账户基本信息 begin -->
			<div class="PB5 boxSizing textC whiteTex ">
				<div class="userInfoWrap clearfix">
					<img class="messageIcon fr MR20 MT10" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconNews.png?<%=request.getAttribute("version")%>" width="40">
					<div class="personalInfo PLR3P MT30 fl">
						<div class="personalImgBox blockC positionR fl">
							<img class="personalImg block" src="${pageContext.request.contextPath}/pic/weixin/version1125/defaultAvatar.png?<%=request.getAttribute("version")%>" />
							<div class="personalGrade positionA">
								<!-- 积分等级开关打开 -->
								<c:if test="${pointSwitch eq 'Y'}">
									<c:if test="${pointGrade eq '1'}">
										<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/general.png">
									</c:if>
									<c:if test="${pointGrade eq '2'}">
										<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/gold.png">
									</c:if>
									<c:if test="${pointGrade eq '3'}">
										<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/platinum.png">
									</c:if>
									<c:if test="${pointGrade eq '4'}">
										<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/supreme.png">
									</c:if>
									<c:if test="${pointGrade eq '5'}">
										<img src="${pageContext.request.contextPath}/pic/weixin/ver3_1_1/diamond.png">
									</c:if>
								</c:if>
								<!-- 积分等级开关关闭 -->
								<c:if test="${pointSwitch eq 'N'}">
								</c:if>
							</div>
						</div>
						<h4 class="PT10 PB20 fl ML20" id="amountTitle">
							<span class="font18 titlePhone"></span><em class="font14 MT5 block textL"></em>
						</h4>
					</div>
				</div>
			</div>

		</div>

		<!--  userInfo Start  -->
		<div class="whiteBkg PT10 PLR3P">
			<div class="MT5 PB15 borderB clearfix">
				<div class="fl">
					<div class="MT7 textL grayTex font14">账户总额(元)</div>
					<div class="font20 redTex strong MT5 textL " id="totalAssets">0.00</div>
				</div>
				<div class="fr">
					<a class="rechargeCon sameCon whiteTex PLR20 PTB5 MT10 MR10 redBkg font14 fl textC" id="topUp">充值</a>
					<a id="withdrawDeposit" class="whiteTex PLR20 PTB5 MT10 font14 withdrawalsCon sameCon fl textC">提现</a>
				</div>
			</div>

			<div class="PT15 PB15 clearfix">
				<ul class="clearfix textL">
					<li class="width35 boxSizing  fl">
						<h4 class="font12 grayTex" id="balanceMoneyTitle">可用余额(元)</h4>
						<div class="font14   strong MT7" id="balanceMoney"></div>
					</li>
					<li class="width30 boxSizing fl">
						<h4 class="font12 grayTex" id="earningsTitle">累计收益(元)</h4>
						<div class="font16   strong MT5" id="incomeAmount"></div>
					</li>
					<li class="width30 boxSizing fl">
						<h4 class="font12 grayTex" id="balanceMoneyTitle">提现中金额(元)</h4>
						<div class="font16   strong MT5" id="cashInMoney"></div>
					</li>
				</ul>
			</div>

		</div>
		<!--  userInfo end  -->


		<!-- 礼包资产 begin -->
		<div class="MT15 PLR3P giftBagBox none">
			<div class="clearfix whiteBkg radius3">
				<a class="fl" href="${pageContext.request.contextPath}/wxcoupons/giftBag">
					<div class="PTB15 clearfix" id="giftBag">
						<img class="fl" width="100" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconGift.png?<%=request.getAttribute("version")%>" >
						<div class="fl boxSizing ML10 textL MT15 PTB5 especiallyDiv1">
							<div class="font12 grayTex">礼包金额(元)</div>
							<div class="font14 MT3 blackTex  strong" id="giftBalance">0.00</div>
							<div class="font12 MT5 grayTex">可兑换礼包<span class="font12 MT3 redTex" id="giftPackageSum">0</span></div>
						</div>
					</div>
				</a>
				<div class="fr borderL MT15 PTB5 PL20 PR20 textL especiallyDiv2" style="margin-top:35px;">
					<a href="${pageContext.request.contextPath}/wxcoupons/changeVoucher">
						<div class="font12 grayTex toTsExchange">K码券<span class="redTex" id="tsExchange"></span></div>
					</a>
					<a href="${pageContext.request.contextPath}/wxcoupons/soonVoucher">
						<div class="font12 grayTex MT15 toJsExchange">加速券<span class="redTex" id="jsExchange"></span></div>
					</a>
				</div>
			</div>
		</div>
		<!-- 礼包资产 end -->

		<div class="MT10 PLR3P clearfix">
			<a class="inlineB fl blockC isValidDq width49 whiteBkg PB10 textC radius3" href="${pageContext.request.contextPath}/wxproduct/goFixAssetList">
				<img class="marginTop-7" width="80" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconTerminal.png?<%=request.getAttribute("version")%>" >
				<div class="font12 grayTex">定期资产(元)</div>
				<div class="font18 MT3 strong  blackTex" id="investAmountSum">0.00</div>
				<div class="MT3 grayTex font14">预期收益<span class="font14 strong  MT5" id="expectAmount">0.00</span></div>
			</a>
			<a class="inlineB fr blockC isValidHq width49 whiteBkg PB10 textC radius3" href="${pageContext.request.contextPath}/wxproduct/goDemandProductIndex?fixDemandSwitch=demand">
				<img class="marginTop-7" width="80" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconCurrent.png?<%=request.getAttribute("version")%>" >
				<div class="font12 grayTex">零钱计划资产(元)</div>
				<div class="font18 MT3   strong blackTex" id="currentAmount">0.00</div>
				<div class="MT3 grayTex font14">昨日收益<span class="font14 strong   MT5" id="yesterdayGain">0.00</span></div>
			</a>
		</div>

		<div class="whiteBkg MT15">
			<ul class="whiteTex clearfix">
				<!-- 积分 begin -->
				<li class="boxSizing none" id="collectScore">
					<a class="clearfix borderB PTB15 PLR3P" id="scoreLink">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconIntegral.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">积分</h4>
								</td>
								<td align="right">
									<div class="font12 textR grayTex"><span class="redTex" id="totalScore">0</span>积分可用<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5 MT2"/></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 积分 end -->
				<!-- 代金券 begin -->
				<li class="boxSizing">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxuser/goVoucher">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconCash.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">代金券</h4>
								</td>
								<td  align="right">
									<div class="font12 textR grayTex"><span class="redTex" id="vouchersTotalNum">0</span>张可用<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 代金券 end -->
				<!-- 加息券 begin -->
				<li class="boxSizing">
					<a class="clearfix borderB PTB15 PLR3P" id="addRates">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconInteres.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">加息券</h4>
								</td>
								<td align="right">
									<div class="font12 textR grayTex"><span class="redTex" id="addRatesNum">0</span>张可用<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5 MT2"/></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 加息券 end -->
				<!-- 抵用券 begin -->
				<li class="boxSizing none" id="vouchers">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxInvest/getVoucherList?invest=1">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/feeVouchers.png" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">手续费抵用券</h4>
								</td>
								<td  align="right">
									<div class="font12 textR grayTex"><span class="redTex" id="curHelpNub">0</span>张可用<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 抵用券 end -->

				<!-- 其他 begin -->
				<li class="boxSizing none" id="couponVoucher">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxcoupons/thirdVoucher">
						<table class="width100">
						<tr>
							<td width="35" align="left">
								<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconOther.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
							<td>
								<h4 class="font16 textL blackTex">其他</h4>
							</td>

							<td  align="right">
								<div class="font12 textR grayTex"><span class="redTex" id="exchange">0</span>张可用<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
							</td>
						</tr>
						</table>
					</a>
				</li>
				<!-- 其他 end -->
			</ul>
		</div>

		<div class="whiteBkg MT10">
			<ul class="tabTB10 whiteTex clearfix">
				<!-- 交易记录 begin -->
				<li class="boxSizing">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxdeposit/goTradingRecord">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconrecord.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">交易记录</h4>
								</td>
								<td align="right">
									<div class="font12 textR grayTex">点击查看详情<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 交易记录 end -->

				<!-- 每日加息 begin -->
				<li class="boxSizing">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxquest/goDailyQuest">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIcondaily.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">每日加息</h4>
								</td>
								<td align="right">
									<div class="font12 textR grayTex">去加息<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 每日加息 end -->

				<!-- 销售记录 begin -->
				<li class="boxSizing" id="salesManagement">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/saleManager/salesManagement?mobile=${mobile}">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/Rectangle.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">销售记录</h4>
								</td>
								<td align="right">
									<div class="font12 textR grayTex">点击查看<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 销售记录 end -->
				<!-- 邀请好友 begin -->
				<li class="boxSizing">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxtrade/goInviteFriend?invitationCd=${mobile}">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconfriends.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">邀请好友</h4>
								</td>
								<td align="right">
									<div class="font12 textR grayTex">把好友联起来<img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 邀请好友 end -->

				<!-- 设置 begin -->
				<li class="boxSizing">
					<a class="clearfix borderB PTB15 PLR3P" href="${pageContext.request.contextPath}/wxtrade/goSetting">
						<table class="width100">
							<tr>
								<td width="35" align="left">
									<img class="block fl" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/myAsset20170514/selfIconedit.png?<%=request.getAttribute("version")%>" alt="" width="30"></td>
								<td>
									<h4 class="font16 textL blackTex">设置</h4>
								</td>
								<td align="right">

									<div class="font12 textR grayTex">
										<img class="verMid none warningIcon" src="${pageContext.request.contextPath}/pic/weixin/myAsset_20170412/warning.png?<%=request.getAttribute("version")%>" alt="" width="16">
										<span id="setTip" ></span><img height="10" src="${pageContext.request.contextPath}/pic/weixin/rightArrow.png" class="ML5" /></div>
								</td>
							</tr>
						</table>
					</a>
				</li>
				<!-- 设置 end -->
			</ul>
		</div>

		<div class="clearfix grayTex blockC MT10">
			<a href="${pageContext.request.contextPath}/webindex/goKcodeActivation?hasHeadFoot=false" class="clearfix textC">
				<table class="MLRA MT20" style="width:80px;">
					<tr>
						<td align="left">
							<img  src="${pageContext.request.contextPath}/pic/weixin/ver2_9/help.png" alt="" width="14">
						</td>
						<td align="center">
							<span class="font14 grayTex ML5">帮助中心</span>
						</td>
					</tr>
				</table>
			</a>
		</div>

	</div>

	<input id="userId" name="userId" type="hidden" value="${userId}">
	<input id="mobile" name="mobile" type="hidden" value="${mobile}">
	<input id="pageTag" name="pageTag" type="hidden" value="${pageTag}">
	<input id="channel" name="channel" type="hidden" value="${channel}">
	<input id="pointGrade" name="pointGrade" type="hidden" value="${pointGrade}">
	<input id="pointSwitch" name="pointSwitch" type="hidden" value="${pointSwitch}">
	<div style="display:none" id="formDiv"></div>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/countUp.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabBar.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/myAsset.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/jquery.kxbdmarquee.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/scrollText.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>


