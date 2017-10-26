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
	<meta name="format-detection" content="telephone=no"/>
	<title>金鸡报春,喜迎新年</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityNewYear.css?<%=request.getAttribute("version")%>" />
	<%@  include file="../header.jsp"%>
  </head>

  <body>
	<div class="swiper-container height100P outHide">
		<div class="swiper-wrapper">
			<div class="swiper-slide outHide height100P slider1">
				<div class="wordTop outHide positionR">
					<img class="height100P" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/firstPageWrodTop.png" />
                </div>
				<div class="content1 goldColor font14">
					<p class="PTB2">截止到<span class="whiteTex font18"> 2016 </span>年<span class="whiteTex font18"> 12 </span>月<span class="whiteTex font18"> 31 </span>日</p>
					<p class="PTB2">联璧金融已有 <span class="whiteTex font18 registerAmount"></span> 位注册用户</p>
					<p class="PTB2">累计帮助用户赚取</p>
					<p class="PTB2"> <span class="whiteTex font18 getMoneyAmount"></span> 元收益</p>
				</div>
				<div class="wordCenter clearfix positionR">
					<div class="wordLeft ML5P outHide none fl">
						<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/firstPageLeftWord.png" />
					</div>
					<div class="wordRight MR5P outHide none fr">
						<img class="width100" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/firstPageRightWord.png" />
					</div>
				</div>
			</div>
			<div class="swiper-slide outHide height100P slider2 positionR">
				<div class="wordTop outHide positionR">
					<img class="height100P" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/firstPageWrodTop.png" />
				</div>
				<div class="content1 noLoginFlag goldColor font14">
					<p class="PTB2"><span class="whiteTex font18 registerYear"></span> 年 <span class="whiteTex font18 registerMonth"></span> 月 <span class="whiteTex font18 registerDay"></span> 日</p>
					<p class="PTB2">您加入了联璧大家庭</p>
					<p class="PTB2">成为了我们的第 <span class="whiteTex font18 registerSort"></span> 位用户</p>
					<p class="PTB2">我们一起携手走过 <span class="whiteTex font18 registerDays"></span> 天的时光</p>
					<p class="PTB2">想起来也是很美好呢</p>
				</div>
				<img class="positionA flowerImg flowerImg1" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/yun1.png" />
				<img class="positionA flowerImg flowerImg2" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/yun2.png" />
				<img class="positionA flowerImg flowerImg3" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower1.png" />
				<img class="positionA flowerImg flowerImg4" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower2.png" />
				<img class="positionA flowerImg flowerImg5" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower3.png" />
				<img class="positionA flowerImg flowerImg6" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower3.png" />
			</div>
			<div class="swiper-slide height100P slider3 positionR">
				<div class="regularWrap positionA whiteBkg" style="display:inline-block;width:40vw;height:40vw;border-radius:50%;background-color:#FFF;-webkit-mask-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAA5JREFUeNpiYGBgAAgwAAAEAAGbA+oJAAAAAElFTkSuQmCC); ">
					<div class="positionA productName1 productName font24 colorD44535"><p>定期</p><p class="PTB2 font26" id="regularNum"></p></div>
					<div id="regularPer" style="width:250px;height:250px;border-radius:40%;background-color:#CB311F;position:relative;top:45%;left:-50%;animation:wave 5s linear infinite;-webkit-animation:wave 5s linear infinite;z-index:66"></div>
				</div>
				<div class="currentWrap positionA whiteBkg"  style="display:inline-block;width:34vw;height:34vw;border-radius:50%;background-color:#FFF;-webkit-mask-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAA5JREFUeNpiYGBgAAgwAAAEAAGbA+oJAAAAAElFTkSuQmCC); ">
					<div class="positionA productName2 productName font20 colorD44535"><p>零钱计划</p><p class="PTB2 font26" id="currentNum"></p></div>
					<div id="currentPer" style="width:250px;height:250px;border-radius:40%;background-color:#CB311F;position:relative;top:45%;left:-50%;-webkit-animation:wave 5s linear infinite;z-index:66"></div>
				</div>
				<div class="findWrap positionA whiteBkg"  style="display:inline-block;width:30vw;height:30vw;border-radius:50%;background-color:#FFF;-webkit-mask-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAA5JREFUeNpiYGBgAAgwAAAEAAGbA+oJAAAAAElFTkSuQmCC); ">
					<div class="positionA productName3 productName font16 colorD44535"><p>发现</p><p class="PTB2 font26" id="findNum"></p></div>
					<div id="foundPer" style="width:250px;height:250px;border-radius:40%;background-color:#CB311F;position:relative;top:45%;left:-50%;-webkit-animation:wave 5s linear infinite;z-index:66"></div>
				</div>
				<div class="content2 width100 positionA goldColor font14">
					<p class="PTB2">通过联璧金融平台</p>
					<p class="PTB2">您使用了 <span class="whiteTex font18 couponCount"></span> 张加息券</p>
					<p class="PTB2"><span class="whiteTex font18 voucherCount"></span> 张代金券，共计 <span class="whiteTex voucherAmout font18"></span> 元</p>
					<p class="PTB2">累计获得 <span class="whiteTex gainRewardSum font18"></span> 元现金奖励</p>
				</div> 
			</div>
			<div class="swiper-slide height100P slider4 positionR">
				<div class="content3 width100 positionA goldColor font14">
					<p class="PTB2">您累计赚取 <span class="whiteTex font18 moneyAmount"></span> 元收益</p>
					<p class="PTB2 none">超越了全国 <span class="whiteTex font18 moneyAssortPer"></span> 的用户</p>
					<p class="PTB2">财力爆表，霸气逼人</p>
				</div>
				<div class="sharePopup" style="display: none;">
					<div class="textSite">
						<img src="${pageContext.request.contextPath}/pic/weixin/shareTex.png?<%=request.getAttribute("version")%>" height="120">
					</div>
				</div>
				<div class="toShareWrap positionA goldColor width100">
					<a class="block toShare goldColor textC" href="javascript:void(0)">晒单</a>
					<p class="PTB2">分享给小伙伴们，炫耀成就哦~</p>
				</div>
				<img class="positionA flowerImg flowerImg1" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/yun1.png" />
				<img class="positionA flowerImg flowerImg2" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/yun2.png" />
				<img class="positionA flowerImg flowerImg3" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower1.png" />
				<img class="positionA flowerImg flowerImg4" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower2.png" />
				<img class="positionA flowerImg flowerImg5" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower3.png" />
				<img class="positionA flowerImg flowerImg6" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/flower3.png" />
			</div>
		</div>
	</div>
	<img class="block slideTop positionA" src="${pageContext.request.contextPath}/pic/weixin/activity/activity_newYear/slideTop.png" />
	<input id="goUrl" name="goUrl" type="hidden" value="${goUrl}">
	<input id="userId" name="userId" type="hidden" value="${userId}">
	<input id="mobileWX" name="mobileWX" type="hidden" value="${mobile}">
	<input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	<input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >
	<script type="text/javascript">
	var channel=$("#channel").val();
	var url=window.location.href;
	var protocal=window.location.protocol;
	if(protocal=="http:"){
		if(channel!="LBIOS"&&channel!="LBAndroid"){
			var newurl=url.replace('http','https');
			window.location.href=newurl;
		}else{


		}
	}else{

	}
	</script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/swiper-3.4.1.min.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityNewYear.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
