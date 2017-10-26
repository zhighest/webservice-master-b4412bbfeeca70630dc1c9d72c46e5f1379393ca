<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<title>联璧金融APP理财手机客户端-联璧金融</title>
		<meta content="联璧金融APP，联璧金融APP下载，联璧金融app下载软件" name="keywords">
		<meta content="你的投资理财神器，联璧金融APP理财手机客户端！" name="description">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute(" version ")%>"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/downloadDetails.css?<%=request.getAttribute(" version ")%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>

	<body class="fontFamily">
		<c:set var="pageIndex" value="7" />
		<%@  include file="../header.jsp"%>
		<!-- 首屏 begin -->
		<div class="oneScreen positionR">
			<!-- 下载APP begin -->
			<div class="wrapper positionR clearfix whiteTex font30 lineHeight2x MT100">
				<p>联璧金融APP</p>
				<p>祝您随时随地轻松理财</p>
				<div class="redBkg shortShortLine MT10"></div>
				<!-- 下半部分 begin -->
				<div class="positionR MT20">
					<div class="fl">
						<div class="positionR clearfix"><img src="${pageContext.request.contextPath}/pic/web/downloadDetails/QR-code.png" alt="打开微信扫一扫，快捷方便" class="fl codeImg block positionR"></div>
						<p class="font12 whiteTex textC">扫一扫下载</p>
					</div>
					<!-- 下载按钮 begin -->
					<div class="fl ML30">
						<div>
							<a id="iosBtn" class="show1 redBkg font12 radius5 whiteTex">
								<img src="${pageContext.request.contextPath}/pic/web/aboutUs/iosIcon.png">
								<span>iPhone下载</span>
							</a>
						</div>
						<div>
							<a id="androidBtn" class="show2 redBkg font12 radius5 whiteTex">
								<img src="${pageContext.request.contextPath}/pic/web/aboutUs/androidIcon.png">
								<span>Android下载</span>
							</a>
						</div>
					</div>
					<!-- 下载按钮 end -->
				</div>
				<!-- 下半部分 end -->
				<img src="${pageContext.request.contextPath}/pic/web/downloadDetails/iphone5.png
        " class="positionA iphone">
			</div>
			<!-- 下载APP end -->
		</div>
		<!-- 首屏 end -->
		<div class="positionR whiteTex PTB30">
			<h5 class="partTitle MT20 grayTex61 textC">理财优势</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<ul class="clearfix blockC wrapper manage">
				<li class="showLi fl">
					<div class="showImgDiv clearfix redBkg whiteTex blockC block">
						<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon1.png" width="65px" alt="用手机APP投资联璧金融收益更高">
					</div>
					<p class="textC blackTex manageTitle">小金融 阳光普惠</p>
					<p class="manageP textC grayTex83 lineHeight1_5x">专业产品团队组建 提供专业服务</br>
						专注小额富余资金 随心投轻松赚</br>
						产品收益率浮动小 更稳定更安心</p>
				</li>
				<li class="showLi fl">
					<div class="showImgDiv clearfix redBkg whiteTex blockC">
						<div class="iconBox">
							<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon2.png" width="65px" alt="用手机APP投资联璧金融收益更高">
						</div>
					</div>
					<p class="textC blackTex manageTitle">低门槛 百元起投</p>
					<p class="manageP textC grayTex83 lineHeight1_5x">
						微小资金点滴积累 钱包积少成多</br>
						最低百元即可投资 理财门槛更低</br>
						灵活投资自主选择 优质零钱计划</p>
				</li>
				<li class="showLi fl">
					<div class="showImgDiv clearfix redBkg whiteTex blockC">
						<div class="iconBox">
							<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon3.png" width="65px" alt="用手机APP投资联璧金融收益更高">
						</div>
					</div>
					<p class="manageTitle textC blackTex">严风控 资金监管</p>
					<p class="manageP textC grayTex83 lineHeight1_5x">国际信息安全标准 强力保护数据</br>
					资金透明同卡进出 由第三方监管</br>
					优质资产严格风控 法律财务合规</p>
				</li>
				<li class="showLi fl">
					<div class="showImgDiv clearfix redBkg whiteTex blockC">
						<div class="iconBox">
							<img src="${pageContext.request.contextPath}/pic/web/fourScreenIcon4.png" width="65px" alt="用手机APP投资联璧金融收益更高">
						</div>
					</div>
					<p class="manageTitle textC blackTex">更灵活 随存随取</p>
					<p class="manageP textC grayTex83 lineHeight1_5x">
						一键购买省时省力 更快捷更便利</br> 下午三点之前提现 当日即可到账 </br> 个人资金灵活运用 投资随心所欲</br>
				</li>
			</ul>
			<ul class="clearfix blockC wrapper manage1 none">
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon1.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="textL blackTex manageTitle">小金融 阳光普惠</p>
					<p class="manageP textL grayTex83 lineHeight1_5x">专业产品团队组建 提供专业服务 专注小额富余资金 随心投轻松赚 产品收益率浮动小 更稳定更安心</p>
				</li>
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon2.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="textL blackTex manageTitle">低门槛 百元起投</p>
					<p class="manageP textL grayTex83 lineHeight1_5x">
						微小资金累计增长 小投资大收益 最低百元即可投资 理财门槛更低 零钱变身投资本金 小金融稳稳的</p>
				</li>
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon3.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="textL blackTex manageTitle">严风控 资金监管</p>
					<p class="manageP textL grayTex83 lineHeight1_5x">
	国际信息安全标准 强力保护数据 资金透明同卡进出 由第三方监管 优质资产严格风控 法律财务合规</p>
				</li>
				<li class="showLi1 fl">
					<img src="${pageContext.request.contextPath}/pic/web/redIcon4.png" width="65px" alt="用手机APP投资联璧金融收益更高" class="fl MR5">
					<p class="manageTitle textL blackTex">更灵活 随存随取</p>
					<p class="manageP textL grayTex83 lineHeight1_5x">
						一键购买省时省力 更快捷更便利 下午三点之前提现 当日即可到账 个人资金灵活运用 投资随心所欲</p>
				</li>
			</ul>
		</div>
		<div class="bannerBot">
			<h5 class="partTitle whiteTex textC">联璧金融理财</h5>
			<div class="redBkg shortLine blockC MT10"></div>
			<div class="wrapper clearfix positionR MT50">
				<div class="fr whiteTex">
					<h1 class="addShow5 font30"><span id="appVersion"></span></h1>
					<p class="addShow6 MT20" id="updateDesc"></p>
				</div>
				<img class="positionR fl" src="${pageContext.request.contextPath}/pic/web/computer.png">
			</div>
		</div>
		<%@  include file="../footer.jsp"%>
		<%@  include file="../baiduStatistics.jsp"%>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/web/page/downloadDetails.js" charset="UTF-8"></script>

</html>