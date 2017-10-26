<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${pageContext.request.contextPath}/js/web/page/accoutbar.js?<%=request.getAttribute("version")%>"></script>

<div class="personalInfo positionR clearfix">
	<!-- 用户头像 -->
	<div class="photo blockC cursor outHide positionR">
		<img id="userPhotoBig" src="${pageContext.request.contextPath}/pic/web/defaultAvatar.png">
	</div>
	<!-- 用户名 -->
	<p class="MT20 font14 textC whiteTex">欢迎<span id="userName"></span>，<span id="exit" class="cursor">退出</span></p>
	<!-- 用户资产 -->
	<div class="blockC accoutInfo whiteTex MT30 textC positionR">
		<ul class="clearfix positionR">
			<!-- 用户账户总资产 -->
			<li class="fl borderRight">
				<p class="redTex font12">￥<span id="totalAssets" class="font20 heigh50">0.00</span></p>
				<p class="font12">账户总资产</p>
			</li>
			<!-- 用户累计收益 -->
			<li class="fl positionR borderRight">
				<p class="redTex font12">￥<span id="incomeAmount" class="font20 heigh50">0.00</span></p>
				<p class="font12">累计收益</p>
			</li>
			<!-- 账户余额 -->
			<li class="fl positionR">
					<div class="PR30 positionR" id="hoverArea">
						<p class="redTex font12">￥<span id="balanceMoney" class="font20 heigh50">0.00</span></p>
						<p class="font12 positionR clearfix lineHeigh100">
							<span>账户余额</span>
							<img id="cashInMoneyArea" width="12px" class="ML5 none" src="${pageContext.request.contextPath}/pic/web/accoutOverview/accountMeIcon.png">
							<div class="cashInMoneyInfo positionA none" id="cashInMoneyInfo">
								<h2 class="font12 grayTex61">提现中金额(元)</h2>
								<p class="font12 redTex" id="cashInMoney">0.00</p>	
							</div>
						</p>
					</div>
			</li>
		</ul>
	</div>
</div>
