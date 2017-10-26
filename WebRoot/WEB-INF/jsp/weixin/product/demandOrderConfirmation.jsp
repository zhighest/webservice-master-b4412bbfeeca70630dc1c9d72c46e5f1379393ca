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
	<!-- 每次访问此页面，均需要从服务器重新读取，而不是使用缓存中存在的此页面 -->
	<meta http-equiv="pragma" content="no-cache">  
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="expires" content="0"> 
	<title>投资详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/demandOrderConfirmation.css?<%=request.getAttribute("version")%>" />

    <%@  include file="../header.jsp"%>
</head>
<body class="font14">	
	<div class="main PB100">
		<div class="whiteBkg">
			<div class="indexBkg2">
				<div class="width95 blockC">
					<div class="blockC">
						<div class="content whiteTex PT40 positionR">
							<div class="font16">
								<span class="positionR font14">预期年化收益
									<span class="positionA vipHint none">
										<img src="${pageContext.request.contextPath}/pic/weixin/vipHint.png" height="16">
									</span>
									<ul class="vipHintUl positionA font12 none">
										<li class="whiteBkg redTex radius5 annualizedReturnRateLi">
											基本收益
											<span class="annualizedReturnRate" id="annualizedReturnRate"></span>
											<font>%</font>
										</li>
										<li class="radius5 whiteTex adjustRateLi">
											专享+<span>${adjustRate}</span>
											<font>%</font>
										</li>
									</ul>
								</span>
							</div>
							
							<div class="font24 MT30">
								<div class="positionR perTop height60 lineHeight100 MT10">
									<span class="font60 helveticaneue"id="annualRateAll"></span>
									<font>%</font>
								</div>
							</div>
					</div>
					<ul class="whiteTex clearfix PTB10 font12 MT10">
						<li class="width33_3 boxSizing fl borderRWhite">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_8/bag.png" width="30">
							<p class="MT5">取钱灵活</p>

						</li>
						<li class="width33_3 boxSizing fl borderRWhite">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_8/hundred.png" width="30">
							<p class="MT5">百元起投</p>
						</li>
						<li class="width33_3 boxSizing fr">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_8/goup.png" width="30">
							<p class="MT5">收益稳健</p>
						</li>
					</ul>
				</div>
			</div>	
		</div>
		<ul class="whiteBkg MT15">
				<li class="PLR5P clearfix heigh50" id="adjustRateLi">
					<h4 class="fl redTex">专享加息</h4>
					<div class="fr redTex">加息<span>${adjustRate}</span>%</div>
				</li>
				<%--<li class="PLR5P clearfix heigh50">--%>
					<%--<h4 class="fl grayTex">锁定期</h4>--%>
					<%--<div class="fr blackTex">0天</div>--%>
				<%--</li>--%>

				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">起投金额</h4>
					<div class="fr blackTex"><span class="redTex">${investMinAmount}</span>元</div>
				</li>
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">可投总额</h4>
					<div class="fr blackTex"><span id="remanAmount">0.00</span>元</div>
				</li>
			</ul>
		</div>
		<ul class=" borderT whiteBkg textL MT10">
			<li class="PLR5P borderB">
				<h4 class="blackTex positionR PL10P heigh50 clickInfo1" onclick="switchBtn('clickInfo1','showInfo1')" ><i class="positionA desIcon2"></i>产品详情<img class=" fr stick img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="12"></h4>
				<div class="grayTex PTB10 font14 lineHeight1_5x showInfo1" id="companyName">
					<div class="dashedT PT10">
						<p id="fundSecurity"></p>
					</div>
				</div>
			</li>
		</ul>
	</div>
	
	
    <!-- 提示弹窗  begin -->
    <div class="alertBg width100 height100P positionF" style="display:none;" id="alertBg1">
		<div class="radius5 whiteBkg width80 alertBox positionA PB5P">
			<div class="width80 blockC textL">
				<h3 class="font20 PT15 redTex textC">提&nbsp;示</h3>
				<div class="MB20" id="tips">
					<p id="tips1" class="font16 blackTex lineHeight1_8x grayTex textC">已选择投资金额<span class="redTex" id="chooseMoney">0.00</span>元</p>
					<p id="tips2" class="font16 blackTex lineHeight1_8x grayTex textC">已使用代金券<span class="redTex" id="voucherMoney">0.00</span>元</p>
					<p id="tips3" class="font16 blackTex lineHeight1_8x grayTex textC">账户实际扣除金额<span class="redTex" id="infuctMoney">0.00</span>元</p>
				</div>
				<div class="width75 outHide MLRA">
					<a class="inlineB grayBkg width35 PT5 PB5 grayTex textC fl" id="cancel">取消</a>
					<a class="inlineB redBkg width35 PT5 PB5 whiteTex textC fr" id="enterBtn" onclick="getRollOut()">确定</a>
				</div>
			</div>
		</div>
	</div>
    <!-- 提示弹窗  end -->

    <!-- 点击投资 begin -->
	<div class="btmDiv positionF clearfix whiteBkg borderTSL">
		<a id="goDemandOrderCon" class="btn width90 block MLRA radius3">立即投资</a>
	</div>
	<!-- 点击投资 end -->

    <input type="hidden" name="flag" id="flag" value="${flag}">
    <input type="hidden" name="loanId" id="loanId" value="<%=request.getParameter("loanId")%>">
    <!-- 代金券满金额可用的可用数值 -->
    <input type="hidden" name="investmentAmount" id="investmentAmount" value="<%=request.getParameter("investmentAmount")%>">
	<!-- 代金券金额 -->
	<input type="hidden" name="voucherAmount" id="voucherAmount" value="<%=request.getParameter("voucherAmount")%>">
	<!-- 代金券id -->
	<input type="hidden" name="voucherId" id="voucherId" value="<%=request.getParameter("voucherId")%>">
    <!-- 起投金额 -->
 	<input type="hidden" name="investMinAmount" id="investMinAmount" value="${investMinAmount}">
 	<!-- 账户余额 -->
 	<input type="hidden" name="acctBalance" id="acctBalance" value="${acctBalance}">
 	<!-- 基本收益率 -->
 	<input type="hidden" name="annualRate" id="annualRate" value="${annualRate}">
 	<!-- 标的id -->
 	<input type="hidden" name="tagId" id="tagId" value="<%=request.getParameter("tagId")%>">
 	<!-- 链接 -->
 	<!--可投进度-->
 	<input type="hidden" name="finishedRatio" id="finishedRatio" value="<%=request.getParameter("finishedRatio")%>">
 	<input id="URL" name="URL" type="hidden" value="${URL}">	
 	<!-- 上架名称 -->
 	<input id="productName" name="productName" type="hidden" value="${productName}">
 	<input type="hidden"  id="token" name="token" value="${token}">
 	<!-- 专享加息率 -->
 	<input type="hidden"  id="adjustRate" name="adjustRate" value="${adjustRate}">
 	<input type="hidden"  id="mobile" name="mobile" value="${mobile}">
 	<div id="formDiv" style="display:none;"></div>
 	<script src="${pageContext.request.contextPath}/js/weixin/page/demandOrderConfirmation.js?<%=request.getAttribute("version")%>"></script>
 	<script src="${pageContext.request.contextPath}/js/weixin/page/numberKeyboard.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/dealPWControls.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
