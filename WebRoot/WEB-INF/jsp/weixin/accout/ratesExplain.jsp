<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
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
	<title>专享加息说明</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/ratesExplain.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    <script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>  
</head>
<body class="acitivityBkg">
	<div class="wrapper bkg1">
		<ul class="whiteBkg clearfix borderB tabSwitch" id="tabNav">
			<li class="width50 fl PTB10 grayTex positionR borderR current boxSizing tabItem tab1" onclick="tabSwitchLi('tabItem','tab1');tabSwitchCon('tabContent','tabContent1');wrapperBkg1()">
				零钱计划
			</li>
			<li class="width50 fl PTB10 grayTex positionR boxSizing tabItem tab2"  onclick="tabSwitchLi('tabItem','tab2');tabSwitchCon('tabContent','tabContent2');wrapperBkg2()">
				定期
			</li>
		</ul>
		<div class="tabContent tabContent1" id="tabContent1">
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/domandbkg.jpg?<%=request.getAttribute("version")%>">
				<div class="whiteTex positionA ratesExplainDes">
					<div class="width80 blockC MB20">
						<h4 class="whiteTex font18 MB20">零钱计划说明</h4>
						<p class="lineHeight1_8x font14 textL">1.每日24点零钱计划资产在投本金<span class="yellowTex">满5000</span>元（或者当日收益满1元），<span class="yellowTex">次日</span>专享利率可以在当日基础上<span class="yellowTex">增加0.1%</span>，按日累加，<span class="yellowTex">上限为1%</span>（即连续满足条件10天可享受1%的加息）。之后在投本金不低于5000元（或者当日收益满1元）可持续享有加息1%。</p>
						<p class="lineHeight1_8x font14 textL">2.若计算零钱计划收益时在投本金不足5000元（且当日收益低于1元），则该项专享利率归0，之后的专享利率从0开始重新统计。</p>
					</div>
					<div class="btnArea width75 blockC">
						<!--微信活期按钮-->
						<a id="weixinDemand" class="positionA btnArea width75 blockC block" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/explainBtn.png?<%=request.getAttribute("version")%>">
						</a>
						<!--Ios活期按钮-->
						<label id="lianbiIosDemand" style="display: none;">
							<input type="button" value="" style="display: none;" id="iosBtnDemand"/>
							<a class="positionA btnArea width75 blockC block" id="lianbiIconBtnDemand">
								<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/explainBtn.png?<%=request.getAttribute("version")%>">
							</a>
						</label>
						<!--安卓活期按钮-->
						<a id="lianbiAndroidDemand" class="positionA btnArea width75 blockC block" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/explainBtn.png?<%=request.getAttribute("version")%>">
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="tabContent tabContent2" id="tabContent2" style="display: none">
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/fixbkg.jpg?<%=request.getAttribute("version")%>">
				<div class="whiteTex positionA ratesExplainDes">
					<div class="width80 blockC MB20">
						<h4 class="whiteTex font18 MB20">定期投资说明</h4>
						<p class="lineHeight1_8x font14 textL">1.每日24点定期资产在投本金<span class="yellowTex">每满10000元</span>，零钱计划的专享利率可以<span class="yellowTex">增加0.1%</span>，按金额累加，<span class="yellowTex">上限为1%</span>（即投资满10万元可享受1%的零钱计划专享加息）。</p>
						<p class="lineHeight1_8x font14 textL">2.到期后的定期投资不作为统计对象</p>
					</div>
					<div class="btnArea width75 blockC">
						<!--微信定期按钮-->
						<a id="weixinFix" class="positionA btnArea width75 blockC block" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/explainBtn.png?<%=request.getAttribute("version")%>">
						</a>
						<!--Ios定期按钮-->
						<label id="lianbiIosFix" style="display: none;">
							<input type="button" value="" style="display: none;" id="iosBtnFix"/>
							<a class="positionA btnArea width75 blockC block" id="lianbiIconBtnFix">
								<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/explainBtn.png?<%=request.getAttribute("version")%>">
							</a>
						</label>
						<!--安卓定期按钮-->
						<a id="lianbiAndroidFix" class="positionA btnArea width75 blockC block" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_7/explainBtn.png?<%=request.getAttribute("version")%>">
						</a>
							
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="fixDemandSwitch" id="fixDemandSwitch" value="<%=request.getParameter("fixDemandSwitch")%>" >
	<script src="${pageContext.request.contextPath}/js/weixin/page/ratesExplain.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>

