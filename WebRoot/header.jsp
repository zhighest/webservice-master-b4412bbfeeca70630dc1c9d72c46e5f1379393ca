<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${pageContext.request.contextPath}/js/web/integration.js?<%=request.getAttribute("version")%>"></script>
<div class="whiteBkg" id="header">
	<div class="wrapper clearfix PT20 PB15">
		<div class="fl width825">
			<div class="fl" >
			    <h1>
				  <a href="${URL}${pageContext.request.contextPath}/webindex/goIndex" title="">
				    <img src="${pageContext.request.contextPath}/pic/web/downloadDetails/logoLong.png?<%=request.getAttribute("version")%>" style="width: 140px;" alt="联币金融">
				  </a>
				</h1>
		    </div>
			
			<ul class="MT10 fr cleafix" id="nav">
			  <li class="PL10 fl PR10 MR5"><a href="${URL}/webindex/goIndex" class="positionR navLi ${pageIndex eq 1?'redTex':'blackTex'}">首 页</a></li>
			  <li class="PL10 fl PR10 MR5"><a href="${URLS}/trade/goAccoutOverview" class="positionR navLi ${pageIndex eq 2?'redTex':'blackTex'}">个人中心</a></li>
			  <li class="PL10 fl PR10 MR5"><a href="${URL}/webhtml/activityList1.html" class="positionR navLi ${pageIndex eq 3?'redTex':'blackTex'}">最新活动</a></li>
			  <li class="PL10 fl PR10 MR5"><a href="${URL}/webhtml/mediaList1.html" class="positionR navLi ${pageIndex eq 4?'redTex':'blackTex'}">媒体报道</a></li>
			  <li class="PL10 fl PR10 MR5"><a href="${URL}/webindex/goHelpCenter" class="positionR navLi ${pageIndex eq 5?'redTex':'blackTex'}">帮助中心</a></li>
			  <li class="PL10 fl PR10 MR5"><a href="${URL}/webabout/aboutUs" class="positionR navLi ${pageIndex eq 6?'redTex':'blackTex'}">关于我们</a></li>
			  <li class="PL10 fl PR10"><a href="${URL}/webabout/goDownloadDetails" class="positionR navLi ${pageIndex eq 7?'redTex':'blackTex'}">联币金融APP</a></li>
			</ul>
	      </div>
		  <ul class="fr navr MT5 font14">
			    <li class="fl MR5 none cursor"><a class="PL10 PR10 blackTex" href="https://eco-api.meiqia.com/dist/standalone.html?eid=8698" target="_blank">客服</a></li>
				<li class="fl cursor" id="loginBtn"><a class="PL10 PR10 currentA blackTex" href="${pageContext.request.contextPath}/trade/goAccoutOverview">登录</a></li>
				<li class="fl MR5 none" id="exitBtn">
					<div class="PL10 PR10 redTex fl" href="#" id="phoneNumber"></div>
					<a class="PL10 PR10 blackTex fl cursor" id="loginOut">退出</a>
				</li>
		  </ul>
		
	</div>	
</div>
<input id="webIsLogIn" name="webIsLogIn" type="hidden" value="${webIsLogIn}">
<input id="loginMobile" name="loginMobile" type="hidden" value="${loginMobile}">
<script type="text/javascript">
	$(document).ready(function() {
		var webIsLogIn = $("#webIsLogIn").val();
		var loginMobile = $("#loginMobile").val();
		if(webIsLogIn == "true"){
			$("#loginBtn").stop().hide();
			$("#exitBtn").stop().show();
			$("#phoneNumber").html(numHide(loginMobile));
		}else{
			$("#exitBtn").stop().hide();
			$("#loginBtn").stop().show();
		}
		$("#loginOut").click(function(event) {
			showTip1("退出登录？");
		});
	});
	
</script>