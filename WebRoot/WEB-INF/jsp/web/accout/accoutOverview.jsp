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
	<meta name="format-detection" content="telephone=no"/>
	<meta content="个人中心，联璧金融个人中心" name="keywords">
	<meta content="联璧金融个人中心目的是为用户提供更多的奖励渠道，更尊贵的特权，更好玩的活动，让您在理财赚钱的同时，获得更多乐趣" name="description">
  <title>个人中心-联璧金融</title>
  <link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/accoutOverview.css?<%=request.getAttribute("version")%>"/>
  </head>
  <body class="fontFamily">
  	<div id="wrap"> 
  		<div id="main">
  			<!-- 网页头部的主tab的val值判断 -->
			<c:set var="pageIndex" value="2"/>
			<!-- 网页头部的已登录账号的个人中心tab的val值判断 -->
			<c:set var="pageAccount" value="1"/>
  			<%@  include file="../header.jsp"%> 
  			<div class="contentWrapper positionR">
  				<!-- 公用的头部banner和用户展示 -->
  				<%@  include file="../accoutbar.jsp"%>
  				<div class="clearfix grayBkgf5 width100 PTB30">
					  <div class="wrapper clearfix">
  						<c:set var="sidebarIndex" value="1"/>
  						<%@  include file="../sidebar.jsp"%>
  						<div class="fr font12 blackTex">当前位置：<a class="blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">个人中心 / </a><span class="redTex">我的账户</span></div>
  					</div>
  				</div>
				<!-- 资产总览 begin -->
  				<div class="clearfix whiteBkg width100 PTB30">
  					<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">资产总览</p>
  						<p class="grayTex61 opacity60">Account overview</p>
  					</div>
  					<div class="wrapper clearfix positionR MT20">
  						<ul class="width100 clearfix positionR">
  							<!-- 零钱计划资产简略 begin -->
  							<li class="PTB30 grayBkgf5 fl width32 MR2P textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<img src="${pageContext.request.contextPath}/pic/web/accoutOverview/fixIcon.png" width="20" class="fl">
  										<span class="fl ML5">零钱计划资产:</span>
  									</div>

  									<div class="width100 MT20 positionR">
  										<div class="fl redTex">
  											<span class="font12">￥</span>
  											<span class="font30" id="currentAmount">0.00</span>
  										</div>
  										<div class="fr font12">
  											<p class="grayTex61 opacity60">昨日收益</p>
  											<p class="redTex">￥<span id="yesterdayGain">0.00</span></p>
  										</div>
  									</div>
  								</div>
  							</li>
							<!-- 零钱计划资产简略 end -->

							<!-- 定期资产简略 begin -->
  							<li class="PTB30 grayBkgf5 fl width32 MR2P textL positionR">
  								<div class="width90 blockC">
  									<div class="grayTex61 positionR clearfix">
  										<img src="${pageContext.request.contextPath}/pic/web/accoutOverview/demanIcon.png" width="20" class="fl">
  										<span class="fl ML5">定期资产:</span>
  									</div>
  									<div class="width100 MT20 positionR">
  										<div class="fl redTex">
  											<span class="font12">￥</span>
                        <span class="font30" id="investAmountSum">0.00</span>
  										</div>
  										<div class="fr font12">
  											<p class="grayTex61 opacity60">预期收益</p>
  											<p class="redTex">￥<span id="expectAmount">0.00</span></p>
  										</div>
  									</div>
  								</div>
  							</li>
							<!-- 定期资产简略 end -->

              <!-- 礼包资产简略 begin -->
              <li class="PTB30 grayBkgf5 fl width32 textL positionR">
                <div class="width90 blockC">
                  <div class="grayTex61 positionR clearfix">
                    <img src="${pageContext.request.contextPath}/pic/web/accoutOverview/gift.png" width="20" class="fl">
                    <span class="fl ML5">礼包资产:</span>
                  </div>
                  <div class="width100 MT20 positionR">
                    <div class="fl redTex">
                      <span class="font12">￥</span>
                      <span class="font30" id="giftBalance">0.00</span>
                    </div>
                    <div class="fr font12">
                      <p class="grayTex61 opacity60">礼包数量</p>
                      <p class="redTex"><span id="giftPackageSum">0.00</span>个</p>
                    </div>
                  </div>
                </div>
              </li>
              <!-- 定期资产简略 end -->

							<!-- 优享计划 begin -->
							<!-- <c:if test="${isValidYxjh eq 'Y'}">
								<li class="PTB30 grayBkgf5 fl width32 textL positionR">
	  								<div class="width90 blockC">
	  									<div class="grayTex61 positionR clearfix">
  										<img src="${pageContext.request.contextPath}/pic/web/accoutOverview/enjoyIcon.png" width="20" class="fl">
  										<span class="fl ML5">优享计划:</span>
  									</div>
	  									<div class="width100 MT20 positionR">
	  										<div class="fl redTex">
	  											<span class="font12">￥</span>
	  											<span class="font30" id="enjoyPlanAmount">0.00</span>
	  										</div>
	  										<div class="fr font12">
	  											<p class="grayTex61 opacity60">昨日收益</p>
	  											<p class="redTex">￥<span id="enjoyYesterdayIncome">0.00</span></p>
	  										</div>
	  									</div>
	  								</div>
	  							</li>
							</c:if> -->
							<!-- 优享计划 end -->
  						</ul>
  					</div>
  				</div>
	  			<!-- 资产总览 end -->
				
				<!-- 投资前准备 begin -->
	  			<div class="grayBkgf5 PTB30">
	  				<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">投资前准备</p>
  						<p class="grayTex61 opacity60">Pre - investment preparation</p>
  					</div>

  					<div class="wrapper clearfix positionR MT20">
  						<ul class="width100 clearfix positionR">
  							<!-- 实名认证 begin -->
  							<li class="PTB30 whiteBkg fl width32 MR2P textC positionR">
  								<img src="${pageContext.request.contextPath}/pic/web/accoutOverview/attestationIcon.png" width="26" class="blockC">
  								<p class="grayTex61">实名认证</p>
  								<div class="heigh40 positionR textC stateBox cursor none" id="idcardVerify">
				    				<img src="${pageContext.request.contextPath}/pic/web/done.gif">
				    				<span class="font14 grayTex61 opacity60">已完成</span>	
				    			</div>
				    			<div class="accountBtn none" id="approveBox">
				    				<a class="cursor font12 blockC noHoverBtn" id="approve">去认证</a>
				    			</div>
  							</li>
  							<!-- 实名认证 end -->

  							<!-- 绑定银行卡 begin -->
  							<li class="PTB30 whiteBkg fl width32 MR2P textC positionR">
  								<img src="${pageContext.request.contextPath}/pic/web/accoutOverview/cardIcon.png" width="26" class="blockC">
  								<p class="grayTex61">绑定银行卡</p>
  								<div class="heigh40 positionR textC stateBox cursor none" id="buildIdCard">
				    				<img src="${pageContext.request.contextPath}/pic/web/done.gif">
				    				<span class="font14 grayTex61 opacity60">已完成</span>	
				    			</div>
				    			<div class="accountBtn none" id="bindBox">
				    				<a class="cursor font12 blockC " id="bind">去绑定</a>
				    			</div>
  							</li>
  							<!-- 绑定银行卡 end -->

  							<!-- 交易密码设置 begin -->
  							<li class="PTB30 whiteBkg fl width32 textC positionR">
  								<img src="${pageContext.request.contextPath}/pic/web/accoutOverview/setIcon.png" width="26" class="blockC">
  								<p class="grayTex61">交易密码设置</p>
  								<div class="heigh40 positionR textC stateBox cursor none" id="passwordCash">
				    				<img src="${pageContext.request.contextPath}/pic/web/done.gif">
				    				<span class="font14 grayTex61 opacity60">已完成</span>	
				    			</div>
				    			<div class="accountBtn none" id="setBox">
				    				<a id="set" class="cursor blockC font12">去设置</a>
				    			</div>
  							</li>
  							<!-- 交易密码设置 end -->
  						</ul>
  					</div>
	  			</div>
	  			<!-- 投资前准备 end -->

	  			<!-- 最近交易 begin -->
	  			<div class="grayBkgf5 PTB30">
	  				<div class="wrapper textL fon20 positionR">
  						<!-- 小红标志，勿删除 -->
  						<div class="redTitleSign"></div>
  						<p class="grayTex61 lineHeight100">最近交易</p>
  						<p class="grayTex61 opacity60">Recent trade</p>
  					</div>
  					<div class="wrapper clearfix whiteBkg PTB30 MT20">
  						<dl class="blackTex clearfix PL20 PR20 MT10" id="fundFlowList">
		    				<dd class="grayTex textC">
		    					<span class="fl width25 spanBorderR99 boxSizing">时间</span>
		    					<span class="fl width20 spanBorderR99 boxSizing">名称</span>
		    					<span class="fl width35 spanBorderR99 boxSizing">金额（元）</span>
		    					<span class="fl width20">状态</span>
		    				</dd>
				    	</dl>
				    	<div class="blockC blackBkg checkMoreBtn MT20 none">
				    		<a class="whiteTex font12" href="${pageContext.request.contextPath}/webindex/goInvestDetail">查看更多</a>
				    	</div>
  					</div>
  				</div>
  				<!-- 最近交易 end -->
		    </div>
  		</div> 
  	</div> 
  	<%@  include file="../baiduStatistics.jsp"%>
  	<%@  include file="../footer.jsp"%>
  	<div style="display:none" id="formDiv"></div>
  	<script src="${pageContext.request.contextPath}/js/web/page/accoutOverview.js?<%=request.getAttribute("version")%>"></script>
  </body>
</html>
