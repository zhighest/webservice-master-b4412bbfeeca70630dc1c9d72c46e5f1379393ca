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
	<meta http-equiv="Access-Control-Allow-Origin" content="*"> 
	<title>福利专享日</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/welfareExclusive.css?<%=request.getAttribute("version")%>" />
  <%@  include file="../header.jsp"%>
  <script src="${pageContext.request.contextPath}/js/weixin/page/welfareExclusive.js"></script>
 </head> 
  <body>
  	<div id="alertBox"  class="width100 height100P positionF whiteTex none alertBox">
  		<div id="alert" class="positionA">
  			<div class="MLRA width80 font16 content font18" id="alertBoxContent"></div>
  		</div>
  		<div class="Ikonw">
  			<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/know.png?<%=request.getAttribute("version")%>" width="100%"  class="know positionF" id="know"/>
  		</div>
		<div class="btnWrap positionF clearfix">
			<a class="goExchange">抽奖</a>
			<a class="cancleEx">取消</a>
		</div>
  	</div>
  	<div   class="width100 height100P positionF whiteTex  alertBox none" id="congratulationsWrap">
  		<div  class="positionA congratulations">
  			<div class="MLRA width80 font16 content font18" id="congratulationsWrapContent"></div>
  		</div>
  		<div class="">
  			<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/know.png?<%=request.getAttribute("version")%>" width="100%"  class="know positionF" id="congratulations"/>
  		</div>
  	</div>
  	<div  class="width100 height100P positionF whiteTex alertBox none" id="WinnersWrap">
  		<div class="Winners positionA" >
  			<div class="MLRA width85 font16 content" >
  				<div id="WinnersDiv">
	  				<ul id="WinnersUl">
	  				</ul>
  				</div>
  			</div>
  		</div>
  		<div class="">
  			<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/know.png?<%=request.getAttribute("version")%>" width="100%"  class="know positionF" id="WinnersButton"/>
  		</div>
  	</div>
  	<div>
  		<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/welfareExclusive.jpg?<%=request.getAttribute("version")%>" width="100%" class="welfareExclusiveImg"/>
  	</div>
  	<div>
  		<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/welfareExclusive2.jpg?<%=request.getAttribute("version")%>" width="100%" class="welfareExclusiveImg2"/>
  	</div>
  	<div  class="wrap">
  		<div class="bkg"></div>
  		<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/draw.png?<%=request.getAttribute("version")%>" width="100%" id="drawImg"/>
  		<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/light.gif?<%=request.getAttribute("version")%>" width="100%" class="light"/>
		  <div id="welfareExclusive">
		  	
				<ul id="welfareExclusiveUl"> 
					<li>
						<a> 
							<span>2元零钱<br/>代金券</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/voucher.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>100M<br/>手机流量</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/m.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>100元<br/>京东卡</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/jd.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>5元零钱<br/>代金券</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/voucher.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>爱奇艺<br/>月卡会员</span><br/> 
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/film2x.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>10元零钱<br/>代金券</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/voucher.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>50元<br/>电话费</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/iphone.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
					<li>
						<a><span>0.5%零钱<br/>加息券3日</span><br/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/raise.png?<%=request.getAttribute("version")%>" width="100%">
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="check" >
			<div class="whiteTex">
				<span class="fl ML30 font14 ">当前用户积分：<span id="current"></span></span>
				<span id="check" class="fr MR30 font14 " >查看中奖记录</span>
			</div>
		</div>
		<div>
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/welfareExclusive4.jpg?<%=request.getAttribute("version")%>" width="100%">
		</div>
		<div>
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/welfareExclusive/welfareExclusive5.jpg?<%=request.getAttribute("version")%>" width="100%">
		</div>
		
	  <input type="hidden" name="mobile" id="mobile" value="<%=request.getParameter("mobile")%>" >
	  <input type="hidden" name="channel" id="channel" value="<%=request.getParameter("channel")%>" >	
	  <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
