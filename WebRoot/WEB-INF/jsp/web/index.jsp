<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../commonWeb.jsp"%>
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
		<meta name="format-detection" content="telephone=no" />
		<meta name="description" content="联璧金融是中国更安全的投资理财平台,严格筛选优质资产为投资理财人士提供安全、高收益的互联网投资理财产品，优化资产配置，助你财富实现增值。
" />
		<meta name="keywords" content="联璧金融，联璧金融官网，联璧金融网站，联璧金融下载，联璧金融APP，联璧钱包" />
		<meta name="sogou_site_verification" content="bbIOehiDPk" />
		<meta name="renderer" content="webkit" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta http-equiv="Pragma" content="no-cache" />
		<!-- html不被浏览器缓存   -->
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta http-equiv="Exp ires" content="0" />
		<title>联璧金融—安全有保障的互联网理财平台</title>
		<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/index.css?<%=request.getAttribute(" version ")%>" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/register.css?<%=request.getAttribute(" version ")%>" />
		<script type="text/javascript">
			//guanxuxing-add-shebeizhiwen-begin
			(function() {
				_fmOpt = {
					partner: 'lianbi',
					appName: 'lianbi_web',
					token: '${token_id}'
				};
				var cimg = new Image(1, 1);
				cimg.onload = function() {
					_fmOpt.imgLoaded = true;
				};
				cimg.src = "${tongDunSrc}?partnerCode=lianbi&appName=lianbi_web&tokenId=" + _fmOpt.token;
				var fm = document.createElement('script');
				fm.type = 'text/javascript';
				fm.async = true;
				fm.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'static.fraudmetrix.cn/fm.js?ver=0.1&t=' + (new Date().getTime() / 3600000).toFixed(0);
				var s = document.getElementsByTagName('script')[0];
				s.parentNode.insertBefore(fm, s);
			})();
			//guanxuxing-add-shebeizhiwen-end
		</script>
	</head>

	<body class="positionR">
		<c:set var="pageIndex" value="1" />
		<%@  include file="header.jsp"%>
		<!-- 点击理财产品的弹窗 begin -->
		<div class="positionF productAlertBox none" id="productAlertBox">
			<div class="whiteBkg positionR clearfix blockC radius5 none" id="listDetail">
				<div id="showName" class="redTex textC"></div>
				<div id="circleImg" class="circleBox blockC positionR clearfix">
					<div class="textC redTex sellRate">
						<font id="sellRate"></font>%
					</div>
					<p class=" textC grayTex61">已抢</p>
				</div>
				<p id="minDays" class="textC MT10 grayTex61">
					<span>理财期限</span>
					<span id="investPeriod"></span>
				</p>

				<p class="textC grayTex61">
					<span>起投金额</span>
					<span id="investMinAmount"></span>
				</p>
				<p class="textC grayTex61">
					<span>预期年化收益率</span>
					<span><span id="annualRate"></span>%</span>
				</p>
				<div class="whiteTex radius5 PTB5 PLR20 MT20 blockC cursor" id="closeBtn">确定</div>
				<img class="positionA closeIcon cursor" id="closeIcon" src="${pageContext.request.contextPath}/pic/web/index/closedIcon.png">
			</div>
		</div>
		<!-- 点击理财产品的弹窗 end -->
		<div class="oneScreen positionR">
			<!-- <div class="width100 positionR holderDiv"></div> -->
			<!-- 首屏悬浮区域 begin -->
			<div class="wrapper positionR clearfix suspendDiv block">
				<!-- 下载APP begin -->
				<div class="fl whiteTex font30 lineHeight2x suspendDivL">
					<p>联璧金融APP</p>
					<p>助您随时随地轻松理财</p>
					<div class="redBkg shortShortLine MT10"></div>
					<a href="${pageContext.request.contextPath}/webabout/goDownloadDetails" class="noHoverBtn PTB15 PLR20 redBkg font12 radius5 whiteTex cursor">
						<img src="${pageContext.request.contextPath}/pic/web/index/downLoadIcon.png" width="25px" class="verMiddle">
						<span>下载联璧金融APP</span>
					</a>
				</div>
				<!-- 下载APP end -->
				<!-- 登录注册对话框 begin -->
				<div class="positionR fr loginContent clearfix">
					<div class="goLeft">
						<a><img class="goWhere pointer" src="${pageContext.request.contextPath}/pic/web/index/leftIcon.png"></a>
					</div>
					<div class="goRight">
						<a><img class="goWhere pointer" src="${pageContext.request.contextPath}/pic/web/index/rightIcon.png"></a>
					</div>
					<!-- 未登录状态 begin -->
					<div id="noLogin" class="positionR clearfix">
						<!-- 左右箭头 -->
						<div class="goLeft cursor">
							<a>
								<img class="goWhere" src="${pageContext.request.contextPath}/pic/web/index/leftIcon.png">
							</a>
						</div>
						<div class="goRight cursor">
							<a>
								<img class="goWhere" src="${pageContext.request.contextPath}/pic/web/index/rightIcon.png">
							</a>
						</div>
						<!-- 轮播div begin -->
						<div class="positionR outHide clearfix blockC" id="logonUL">
							<ul class="positionA">
								<li class="fl">
									<!-- 圆角矩形1 begin -->
									<div class="loginBox blockC">
										<!-- 密码登录 begin -->
										<div class="">
											<p class="whiteTex font20 textC">密码登录</p>
											<form id="loginFormPW" action="${pageContext.request.contextPath}/wxuser/login" method="POST" class="MT20 width100" autocomplete="off">
												<!-- 请输入手机号 -->
												<div class="positionR blockC clearfix inputBox radius5" id="pnPW">
													<label class="inputIcon positionA" for="phoneNumPW">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon1.png" width="15px">
		</label>
													<input class="inputFiled whiteTex fr" type="text" autocomplete="off" name="phoneNumPW" id="phoneNumPW" value="" maxlength="11" placeholder="请输入手机号" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" />
												</div>
												<!-- 请输入图形验证码 -->
												<div class="positionR blockC MT10 clearfix inputBox radius5" id="vcPW">
													<label class="inputIcon positionA" for="verifycodePW">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon3.png" width="15px">
		</label>
													<div class="inputFiled whiteTex fr">
														<input type="text" autocomplete="off" class="inputFiled whiteTex fl width50" maxlength="4" placeholder="图形验证码" id="verifycodePW" maxlength="4" />
														<img id="imgcodePW" src="${pageContext.request.contextPath}/imgcode" height="50" class="positionA inputBtn cursor">
													</div>
												</div>

												<!-- 请输入登录密码 -->
												<div class="positionR blockC MT10 inputBox clearfix radius5" id="pwPW">
													<label class="inputIcon positionA" for="password">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon2.png" width="15px">
		</label>
													<input class="inputFiled whiteTex fr" type="password" name="password" autocomplete="off" id="password" value="${userDto.phoneNum}" maxlength="16" placeholder="请输入登录密码" oninput="checkD();" onafterpaste="checkD();" onkeyup="$.checkLimit1($(this),'',false);" />
												</div>
												<!-- 提交按钮 -->
												<div class="submitBtn noHoverBtn radius5 width100 whiteTex block MT20 forbid font20" id="loginSubmitPW">登录</div>
												<div class="clearfix MT20 textC">
													<a class="whiteTex blockC block" href="${pageContext.request.contextPath}/user/goRegister">立即注册</a>
												</div>
											</form>
										</div>
										<!-- 点击登录 -->
									</div>
									<!-- 圆角矩形1 end -->
								</li>
								<li class="fl">
									<!-- 圆角矩形2 begin -->
									<div class="loginBox blockC fl">
										<!-- 验证码登录 begin -->
										<div class="MB12">
											<p class="whiteTex font20 textC">验证码登录</p>
											<form id="loginForm" action="${pageContext.request.contextPath}/user/login" method="POST" class="MT20 width100">
												<!-- 请输入手机号 -->
												<div class="positionR blockC inputBox radius5 clearfix" id="pn">
													<label class="inputIcon positionA" for="phoneNum">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon1.png" width="15px">
		</label>
													<input class="inputFiled fr whiteTex" autocomplete="off" name="phoneNum" id="phoneNum" value="" maxlength="11" placeholder="请输入手机号" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" />
												</div>
												<!-- 请输入图形验证码 -->
												<!--<div class="positionR blockC MT10 inputBox radius5 clearfix" id="vc">
		<label class="inputIcon positionA" for="verifycode">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon3.png" width="15px">
		</label>
		<div class="inputFiled whiteTex fr">
		<input type="text" autocomplete="off" class="inputFiled whiteTex fl bkgNone width60" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
		<img id="imgcode" src="${pageContext.request.contextPath}/imgcode" class="positionA inputBtn cursor">
		</div>
		</div>-->
												<div style="margin: 10px auto;" id="captcha_div"></div>
												<!-- 请输入短信验证码 -->
												<div class="positionR blockC MT10 inputBox radius5 clearfix" id="chc">
													<label class="inputIcon positionA" for="checkCode">
		<img src="${pageContext.request.contextPath}/pic/web/loginIcon2.png" width="15px">
		</label>
													<div class="inputFiled whiteTex fr">
														<input id="checkCode" autocomplete="off" maxlength="6" name="checkCode" value="" class="inputFiled whiteTex fl bkgNone width60" placeholder="请输入验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" />
														<a class="redBkg textC fr positionA inputBtn font12 noHoverBtn cursor" id="gainCode">获取验证码</a>
													</div>
												</div>
												<!-- 点击登录 -->
												<div class="submitBtn noHoverBtn width100 radius5 whiteTex block MT20 forbid font20" id="loginSubmit">登录</div>
												<div class="clearfix MT20 textC">
													<a class="whiteTex blockC block" href="${pageContext.request.contextPath}/user/goRegister">立即注册</a>
												</div>
											</form>
										</div>
										<!-- 验证码登录 end -->
									</div>
									<!-- 圆角矩形2 end -->
								</li>
								<li class="fl">
									<!-- 圆角矩形2 begin -->
									<div class="loginBox blockC fl">
										<!-- 验证码登录 begin -->
										<div class="MB17">
											<p class="whiteTex font20 textC">扫码登录</p>
											<div id="QR_code">
												<span class="QRCodeLogo">
		<img src="${pageContext.request.contextPath}/pic/web/QRCodeLogo@2x.png"/>
		</span>
											</div>
											<p class="whiteTex font16 textC">请扫描二维码登录</p>
										</div>
										<!-- 验证码登录 end -->
									</div>
									<!-- 圆角矩形2 end -->
								</li>
							</ul>
						</div>
						<!-- 轮播div end -->
						<!-- 轮播焦点 -->
						<ul class="focusBtnBox cursor">
							<li class="selectli"><span></span></li>
							<li class="noSelectli"><span></span></li>
							<li class="noSelectli"><span></span></li>
						</ul>
					</div>
					<!-- 未登录状态 end -->

					<!-- 已登录状态 begin -->
					<div id="login" class="none">
						<!-- 圆角矩形 begin -->
						<div class="loginBox blockC">
							<div class="loginIcon blockC outHide">
								<img id="imageIconUrl" src="${pageContext.request.contextPath}/pic/web/defaultAvatar.png" width="80px">
							</div>
							<p class="textC grayTexf5 MT10">联璧金融欢迎您！</p>
							<ul class="textL MT20 grayTex2 accountShow">
								<table class="blockC">
									<tr>
										<td align="right" class="whiteTex">账户总资产：</td>
										<td class="redTex">￥<span class="font32 helveticaneue" id="totalAssets">0.00</span>
										</td>
									</tr>
									<tr>
										<td align="right" class="whiteTex">累计收益：</td>
										<td class="redTex">￥<span class="font32 helveticaneue" id="incomeAmount">0.00</span>
										</td>
									</tr>
									<tr>
										<td align="right" class="whiteTex">账户余额：</td>
										<td class="redTex">￥<span class="font32 helveticaneue" id="balanceMoney">0.00</span>
										</td>
									</tr>
								</table>
							</ul>
						</div>
						<!-- 圆角矩形 end -->
					</div>
					<!-- 已登录状态 end -->
				</div>
				<!-- 登录注册对话框 end -->
			</div>
			<!-- 首屏悬浮区域 end -->
			<!-- PC端轮播图 begin-->
			<div class="positionA oneScreenIn" id="PCbanner">
				<div class="slides" id="slides">
					<div class="slideInner bannerInner">
						<a href="#" style="background: url(${pageContext.request.contextPath}/pic/web/index/indexBannerBkg01.png) no-repeat; background-size:cover;">
						</a>
						<%--<a href="#" class="slide2" style="background: url(${pageContext.request.contextPath}/pic/web/banner/banner2.jpg) no-repeat; background-size:cover;">--%>
						<%--</a>--%>
						<%--<a href="#" id="banner3" class="slide3" style="background: url(${pageContext.request.contextPath}/pic/web/banner/banner3.jpg) no-repeat; background-size:cover;">--%>
						<%--</a>--%>
					</div>
				</div>
			</div>
			<!-- PC端轮播图 end-->
			<!-- 移动端轮播图 begin-->
			<div class="swipe" id="slider">
				<div class="swipe-wrap bannerInner" id="bannerList">
					<figure>
						<a href="#" style="background-image: url(${pageContext.request.contextPath}/pic/web/banner/banner1.jpg);">
						</a>
					</figure>
					<figure>
						<a href="#" class="slide2" style="background-image: url(${pageContext.request.contextPath}/pic/web/banner/banner2.jpg);">
						</a>
					</figure>
					<figure>
						<a href="#" class="slide3" style="background: url(${pageContext.request.contextPath}/pic/web/banner/banner3.jpg) no-repeat; background-size:cover;">
						</a>
					</figure>
				</div>
			</div>
			<!-- 移动端轮播图 end-->
		</div>
		<!-- 第二屏 begin-->
		<div class="screenFf positionR clearfix outHide whiteBkg">
			<h5 class="grayTex61 partTitle">精选理财产品</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<div class="wrapper positionR clearfix" id="productList">
				<div class="goLeftBtn cursor">
					<a><img class="goWhere" src="${pageContext.request.contextPath}/pic/web/index/leftIcon.png"></a>
				</div>
				<div class="goRightBtn cursor">
					<a><img class="goWhere" src="${pageContext.request.contextPath}/pic/web/index/rightIcon.png"></a>
				</div>
				<ul class="blockC positionR clearfix" id="productListUl">
					<div id="showLiBox" class="positionR clearfix">
					</div>
				</ul>
			</div>
			<div class="blockC width90 none" id="productListP"></div>
		</div>
		<!-- 第二屏 end-->

		<!-- 第三屏 begin -->
		<div class="screenF5 grayBkgf5 positionR">
			<h5 class="partTitle grayTex61">最新动态</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<div class="wrapper positionR clearfix" id="activityListW">
				<ul class="blockC width100 activityList positionR clearfix outHide" id="activityList">
					<div class="positionA clearfix" id="activityListBox"></div>
				</ul>

				<div class="blockC MT20 checkMore">
					<div class="fl goLeftBtnA cursor">
						<a id="goLeftBtnA"><img src="${pageContext.request.contextPath}/pic/web/index/leftIcon.png" class="goWhere"></a>
					</div>
					<div class="fr goRightBtnA cursor">
						<a id="goRightBtnA"><img src="${pageContext.request.contextPath}/pic/web/index/rightIcon.png" class="goWhere"></a>
					</div>
				</div>
			</div>
			<ul id="activityListP" class="blockC width90 activityList none">

			</ul>
		</div>
		<!-- 第三屏 end -->
		<!-- 第四屏 begin -->
		<div class="screenFf positionR clearfix">
			<h5 class="partTitle grayTex61">关于我们</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<div class="wrapper aboutUs">
				<div class="aboutUsImg outHide">
					<img src="${pageContext.request.contextPath}/pic/web/index/aboutUsImg.jpg">
				</div>
				<p class="textC width100 MT10 font14 grayTex99" id="aboutUsP">联璧科技成立于2012年，注册资本1亿元，实缴资金1亿元，总部位于中国上海，是较早提出场景互联网整体解决方案的运营商， 联璧科技秉承“致力于帮助传统行业实现O2O转型“这一使命，与上海某知名通信企业联手，实现资源战略对接，构建以“端、管、应用、云、运营”五大元素为核心的O2O产品闭环型整体解决方案，向世人展示了联璧科技的产品研发和运营实力
				</p>
				<a href="${pageContext.request.contextPath}/webabout/aboutUs" class="checkMoBtn noHoverBtn whiteTex textC block blockC redBkg MT20 radius5 font12">查看更多</a>
			</div>
		</div>
		<!-- 第四屏 begin -->
		<!-- 第五屏 begin -->
		<div class="fourScreen positionR whiteTex outHide">
			<h5 class="partTitle whiteTex">理财优势</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<ul class="clearfix blockC wrapper manage">
				<li class="showLi fl MB10">
					<div class="showImgDiv clearfix redBkg whiteTex blockC block">
						<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon1.png" width="65px" alt="用手机APP投资联璧金融收益更高">
					</div>
					<p class="textC whiteTex manageTitle">小金融 阳光普惠</p>
					<p class="manageP textC grayTex99 lineHeight1_5x">专业产品团队组建 提供专业服务</br>
						专注小额富余资金 随心投轻松赚</br>
						产品收益率浮动小 更稳定更安心</p> 
				</li>
				<li class="showLi fl MB10">
					<div class="showImgDiv clearfix redBkg whiteTex blockC">
						<div class="iconBox">
							<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon2.png" width="65px" alt="用手机APP投资联璧金融收益更高">
						</div>
					</div>
					<p class="textC whiteTex manageTitle">低门槛 百元起投</p>
					<p class="manageP textC grayTex99 lineHeight1_5x">
						微小资金点滴积累 钱包积少成多</br>
						最低百元即可投资 理财门槛更低</br>
						灵活投资自主选择 优质零钱计划</p>
				</li>
				<li class="showLi fl MB10">
					<div class="showImgDiv clearfix redBkg whiteTex blockC">
						<div class="iconBox">
							<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon3.png" width="65px" alt="用手机APP投资联璧金融收益更高"> 
						</div>
					</div>
					<p class="manageTitle textC whiteTex">严风控 资金监管</p>
					<p class="manageP textC grayTex99 lineHeight1_5x">国际信息安全标准 强力保护数据</br>
						资金透明同卡进出 由第三方监管</br>
						优质资产严格风控 法律财务合规</p>
				</li>
				<li class="showLi fl MB10">
					<div class="showImgDiv clearfix redBkg whiteTex blockC">
						<div class="iconBox">
							<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon4.png" width="65px" alt="用手机APP投资联璧金融收益更高">
						</div>
					</div>
					<p class="manageTitle textC whiteTex">更灵活 随存随取</p>
					<p class="manageP textC grayTex99 lineHeight1_5x">
						一键购买省时省力 更快捷更便利</br> 下午三点之前提现 当日即可到账 </br> 个人资金灵活运用 投资随心所欲</br>
				</li>
			</ul>

			<ul class="clearfix blockC wrapper manage1 none">
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon1.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="textL whiteTex manageTitle">小金融 阳光普惠</p>
					<p class="manageP textL grayTex99 lineHeight1_5x">专业产品团队组建 提供专业服务 专注小额富余资金 随心投轻松赚 产品收益率浮动小 更稳定更安心</p>
				</li>
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon2.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="textL whiteTex manageTitle">低门槛 百元起投</p>
					<p class="manageP textL grayTex99 lineHeight1_5x">
						微小资金点滴积累 钱包积少成多 最低百元即可投资 理财门槛更低 灵活投资自主选择 优质零钱计划</p>
				</li>
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon3.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="textL whiteTex manageTitle">严风控 资金监管</p>
					<p class="manageP textL grayTex99 lineHeight1_5x">国际信息安全标准 强力保护数据 资金透明同卡进出 由第三方监管 优质资产严格风控 法律财务合规</p>
				</li>
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon4.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="manageTitle textL whiteTex">更灵活 随存随取</p>
					<p class="manageP textL grayTex99 lineHeight1_5x">
						一键购买省时省力 更快捷更便利 下午三点之前提现 当日即可到账 个人资金灵活运用 投资随心所欲</p>
				</li>
			</ul>
		</div>

		<!-- 第五屏 end -->
		<!-- 第六屏 begin -->
		<div class="screenF5 whiteBkg positionR clearfix">
			<h5 class="partTitle grayTex61">媒体报道</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<div class="wrapper blockC mediaShow">
				<!-- 选中媒体报道的详细信息 begin  -->
				<div class="mediaShowCon positionR cursor">
					<!-- 选中媒体报道的图片  -->
					<div class="mediaImgCon" id="mediaImgCon">
						<img id="mdShowImg">
					</div>
					<div class="mediaPCon">
						<h5 id="mediaP"></h5>
						<p id="mdCreateTime"></p>
					</div>
					<div class="mediaPCon">
						<a class="checkBtn cursor">
							<img src="${pageContext.request.contextPath}/pic/web/index/checkBtn.png" width="20px">
						</a>
					</div>
				</div>
				<!-- 选中媒体报道的详细信息 end  -->
				<div class="mediaShowList positionR">
					<ul id="mediaShowList">
					</ul>
				</div>
			</div>
		</div>
		<!-- 第六屏 end -->
		<!-- 第七屏 begin -->
		<div class="sixScreen positionR">
			<h5 class="partTitle whiteTex">联璧金融理财</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<div class="wrapper blockC clearfix MT20 positionR">
				<div class="showUsL clearfix">
					<img src="${pageContext.request.contextPath}/pic/web/computer.png">
				</div>
				<!-- 二维码 begin -->
				<div class="whiteTex showUsR">
					<h3 class="textL MT20 addShow5">随时理财赚钱</h3>
					<p class="MT10 showUsRP addShow6">随存随取 当日计息</p>

					<ul class="whiteTex clearfix codeBox">
						<li>
							<div class="width80 fl addShow5">
								<img class="block blockC" src="${pageContext.request.contextPath}/pic/web/twoCode1.jpg" alt="关注联璧金融微信公众号，了解更多联璧金融官网信息">
								<p class="whiteTex textC MT10 font12">关注微信公众号</p>
							</div>
						</li>
						<li>
							<div class="width80 fr addShow6">
								<img class="block blockC" src="${pageContext.request.contextPath}/pic/web/twoCode2.jpg" alt="扫描下载手机APP，投资更方便，更快捷，随时随地">
								<p class="whiteTex textC MT10 font12">扫描下载手机APP</p>
							</div>
						</li>
					</ul>
				</div>
				<!-- 二维码 end -->
			</div>
		</div>
		<!-- 第七屏 end -->
		<!-- 第八 begin -->
		<div class="sevenScreen positionR clearfix">
			<div class="wrapper clearfix">
				<!-- 内容部分定位 begin -->
				<div class="wrapper whiteBkg clearfix partner">
					<h5 class="partTitle MT20 grayTex61 textC">合作伙伴</h5>
					<div class="redBkg shortLine blockC MT10"></div>
					<div class="swiper-container positionR outHide parterBox">
						<div class="swiper-wrapper positionR clearfix">
							<ul class="swiper-slide partnerImgCon positionR clearfix fl">
								<li class="whiteBkg fl MT40">
									<a href="http://www.cfca.com.cn/" target="_blank" rel="nofollow">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/logo.jpg" height="65px">
									</a>
								</li>
								<li class="whiteBkg fl MT40">
									<a href="https://pay.sina.com.cn/index" target="_blank" rel="nofollow" class="block blockC">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/img5.jpg" height="65px">
									</a>
								</li>
								<li class="whiteBkg fl MT40">
									<a href="http://www.lianlianpay.com" target="_blank" rel="nofollow" class="block blockC">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/img4.jpg" height="65px">
									</a>
								</li>
								<li class="whiteBkg fl MT40">
									<a href="https://www.tongdun.cn/" target="_blank" rel="nofollow" class="block blockC">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/img6.jpg" height="65px">
									</a>
								</li>
								<li class="whiteBkg fl MT40">
									<a href="http://udcredit.com/" target="_blank" rel="nofollow" class="block blockC">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/img2.jpg" height="65px">
									</a>
								</li>
								<li class="whiteBkg fl MT40">
									<a href="http://www.phicomm.com/cn/" target="_blank" rel="nofollow" class="block blockC">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/img1.jpg" height="65px">
									</a>
								</li>
							</ul>
							<ul class="swiper-slide partnerImgCon positionR clearfix fl">
								<li class="whiteBkg fl MT40">
									<a target="_blank" rel="nofollow">
										<img src="${pageContext.request.contextPath}/pic/web/partnerLogo/img3.jpg" height="65px">
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="swiper-pagination"></div>
				<!-- 内容部分定位 end -->
				<div class="font14 grayTex1 positionA" style="bottom: 90px; color: #CCC;z-index: 1000;left:50%;-webkit-transform:translateX(-50%);-ms-transform:translateX(-50%);-moz-transform:translateX(-50%);transform:translateX(-50%);"> 
					市场有风险，投资需谨慎
				</div>
			</div>
			
		</div>
		<!-- 第八 end -->
		<!-- 页脚 -->
		
		<%@  include file="footer.jsp"%>
		<!-- 手机屏幕下载的半透明提示浮窗 begin -->
		<div class="clearfix none" id="areaFix">
			<div class="leftArea">
				<span class="closed" id="closed"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/closed.png?<%=request.getAttribute("version")%>" width="20"></span>
				<span class="logo"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/logo.png?<%=request.getAttribute("version")%>" width="35"></span>
			</div>
			<div class="rightArea textL">
				<h4>联璧金融</h4>
				<p>使用APP客户端，体验更多惊喜！</p>
				<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" class="btmFixBtn cursor">下载APP</a>
			</div>
		</div>
		<!-- 手机屏幕下载的半透明提示浮窗 end -->
		<div style="display:none" id="formDiv"></div>
		<%@  include file="baiduStatistics.jsp"%>
		<input type="hidden" id="webIsLogIn" name="webIsLogIn" value="${webIsLogIn}">
		<input type="hidden" id="totalAssets1" name="totalAssets" value="${totalAssets}">
		<input type="hidden" id="incomeAmount1" name="incomeAmount" value="${incomeAmount}">
		<input type="hidden" id="balanceMoney1" name="balanceMoney" value="${balanceMoney}">
		<input type="hidden" name="loginFlag" value="1" />
		<input type="hidden" name="loginFlag" value="2" />
	</body>
	<script type='text/javascript'>
		(function() {
			document.getElementById('___szfw_logo___').oncontextmenu = function() {
				return false;
			}
		})();
	</script>
	<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script>
	<!-- 验证码组件js -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/slide.js?<%=request.getAttribute(" version ")%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/index.js?<%=request.getAttribute(" version ")%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/integration/countUp.js?<%=request.getAttribute(" version ")%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/jquery.qrcode.min.js?<%=request.getAttribute(" version ")%>"></script>
	<script src="${pageContext.request.contextPath}/js/web/page/register1.js?<%=request.getAttribute(" version ")%>"></script>
	<script src="${pageContext.request.contextPath}/js/web/integration/idangerous.swiper2.7.6.min.js?<%=request.getAttribute(" version ")%>"></script>

</html>