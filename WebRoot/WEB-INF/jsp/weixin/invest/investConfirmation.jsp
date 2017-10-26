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
	<title>投资详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/investConfirmation.css?<%=request.getAttribute("version")%>"/>
    
    <%@  include file="../header.jsp"%>
</head>
<body>	
<div class=" positionA width100  wrap outHide">
	<div class="main PB100">
		<div class="whiteBkg">
			<div class="width100 blockC twoColor PT30">
				<div class="width95 blockC">
					<div class="blockC">
						<div class="content whiteTex positionR">
							<div class="font16">
								<span class="positionR font14" id="returnRateName">预期年化收益率</span>
							</div>
							<div class="height60 lineHeight100 MT30 perTop">
								<span class="font60 helveticaneue strong" id="returnRate">0~0</span>
								<font class="helveticaneue font24">%</font>
							</div>
					</div>
					<ul class="whiteTex clearfix PTB10 font12">
						<li class="width33_3 boxSizing fl borderRWhite">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_8/bag.png" width="30">
							<p class="MT5">随时转让</p>

						</li>
						<li class="width33_3 boxSizing fl borderRWhite">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_8/hundred.png" width="30">
							<p class="MT5">百元起投</p>
						</li>
						<li class="width33_3 boxSizing fr">
							<img src="${pageContext.request.contextPath}/pic/weixin/ver2_8/goup.png" width="30">
							<p class="MT5">低风险</p>
						</li>
					</ul>
				</div>
			</div>	
		</div>
		<ul class="whiteBkg MT15">
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">产品名称</h4>
					<div class="fr blackTex" id="productName">优享计划</div>
				</li>
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">计息时间</h4>
					<div class="fr blackTex" id="investDate">购买成功立计算收益</div>
				</li>
				<li class="PLR5P clearfix heigh50">
					<h4 class="fl grayTex">起投总额</h4>
					<div class="fr blackTex"><span id="investMinAmount">100.00</span>元</div>
				</li>
				<li class="PLR5P clearfix heigh50" id="investMaxAmountLi">
					<h4 class="fl grayTex">累计投资上限</h4>
					<div class="fr blackTex"><span id="investMaxAmount">0.00</span>元</div>
				</li>
			</ul>
		</div>


        <ul class="whiteBkg borderTB textL MT15">
			<li class="PLR5P borderB">
				<h4 class="blackTex positionR PL30 heigh50 clickInfo1" onclick="switchBtn('clickInfo1','showInfo1')" ><i class="positionA desIcon1"></i>项目概况<img class="turnAround positionA stick img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></h4>
				<div class="grayTex borderDT  PTB5P font14 lineHeight1_5x showInfo1" id="projectView" style="display: none">
				</div>
			</li>
			<li class="PLR5P">
				<h4 class="blackTex  positionR PL30 heigh50 clickInfo2" onclick="switchBtn('clickInfo2','showInfo2')"><i class="positionA desIcon2"></i>资金保障<img class="turnAround positionA stick fr img" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></h4>
				<div class="grayTex borderDT PTB5P font14 lineHeight1_5x showInfo2" id="fundSecurity" style="display: none">
					
				</div>
			</li>
		</ul> 
		<ul class="whiteBkg textL MT15">
			<li class="borderB heigh50 whiteBkg PLR5P">
				<a class="block blackTex PL30 positionR" href="${pageContext.request.contextPath}/wxabout/goGuaranteeLetter"><i class="positionA desIcon5"></i>多重保障<img class="turnAround90 positionA stick" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"></a>
			</li> 
		</ul>
	</div>
  </div>
	<!-- 收入投资金额 begin -->
	<div class="btmDiv positionF clearfix whiteBkg borderTSL">
		<div class="width90 blockC clearfix">
			<a class="btn PTB10 width100 blockC fr radius3" id="enterBid" href="javascript:;">立即投资</a>
		</div>
	</div>
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
 	<script src="${pageContext.request.contextPath}/js/weixin/page/investConfirmation.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>

