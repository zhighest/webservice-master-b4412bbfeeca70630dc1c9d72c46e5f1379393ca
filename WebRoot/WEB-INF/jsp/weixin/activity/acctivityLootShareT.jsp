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
	<title>抢加息券</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acctivityLootShareT.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body>
	<div class="wrapper">
		<div class="imgDiv clearfix" style="display: none;"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151202/icon.jpg?<%=request.getAttribute("version")%>" ></div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_April/share/img_01.gif?<%=request.getAttribute("version")%>">
		</div>
		<c:if test="${resultFlag=='00000'}">
		<!---返回值：${resultFlag}：（非分享人已成功抢到加息券）-->
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC content">
					<span class="activityTex font26">恭喜您获得一张加息券！</span>
					<a class="width80 blockC block MT10 textC" href="${pageContext.request.contextPath}/webabout/download"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn2.png" class="actBtn2"></a>	
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">已有<span class="ML5 MR5 actvityTexEm font26 robbedNum">0</span>人领取</div>
			</div>
		</c:if>
		<c:if test="${resultFlag=='11111'}">
			<!--返回值：${resultFlag}：（用户不能抢自己分享出去的加息券）-->
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC content">
					<span class="activityTex font26">自己无法领取加息券</span>
					<a class="width80 blockC block MT10 textC" href="${pageContext.request.contextPath}/webabout/download"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn2.png" class="actBtn2"></a>	
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">已有<span class="ML5 MR5 actvityTexEm font26 robbedNum">0</span>人领取</div>
			</div>
		</c:if>
		<c:if test="${resultFlag=='22222'}">
			<!--返回值：${resultFlag}：（进入页面手动输入手机号，进行抢券中）-->
			<form id="receiveForm" action="${pageContext.request.contextPath}/wxuser/lootShareIncreaseInterest?shareMobileDateJM=${shareMobileDateJM}" method="POST">
				<div class="imgDiv clearfix positionR">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
					<div class="positionA textC content1">
						<div class="width80 blockC">
							<input class="inputTex radius5 width100 font18" type="tel" name="lootMobile" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="请输入手机号码" >
							<a class="width100 blockC block MT20 textC" id="receiveBtn"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn1.png" class="actBtn1"></a>

						</div>	
					</div>
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				</div>
			</form>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">
					<div class="font18 actvityTexEm MT5P">快点进入联璧钱包</div>
					<div class="font16 MT5 actvityTexEm">和朋友一起享受加息收益吧</div>
					<div class="MT20">已有<span class="ML5 MR5 actvityTexEm font24 robbedNum">0</span>人领取</div>
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
		</c:if>
		<c:if test="${resultFlag=='33333'}">
			<!--返回值：${resultFlag}：（今天加息已经满9次，您还可以分享获取一次加息）-->
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC content">
					<span class="activityTex font26">您已加满加息券</span>
					<a class="width80 blockC block MT0 textC" href="${pageContext.request.contextPath}/webabout/download"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn2.png" class="actBtn2"></a>	
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">已有<span class="ML5 MR5 actvityTexEm font24 robbedNum">0</span>人领取</div>
			</div>
		</c:if>
		
		<c:if test="${resultFlag=='44444'}">
			<!--返回值：${resultFlag}：（您已抢过这张加息券）-->
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC content1">
					<span class="activityTex font26">您已领取过加息券</span>
					<a class="width80 blockC block MT10 textC" href="${pageContext.request.contextPath}/webabout/download"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn2.png" class="actBtn2"></a>	


				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">
					<div class="font18 actvityTexEm MT5P">快点进入联璧钱包</div>
					<div class="font16 MT5 actvityTexEm">和朋友一起享受加息收益吧</div>
					<div class="MT20">已有<span class="ML5 MR5 actvityTexEm font24 robbedNum">0</span>人领取</div>
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
		</c:if>
		<c:if test="${resultFlag=='66666'}">
			<!--返回值：${resultFlag}：（已登录可领取加息券）-->
			<form id="receiveForm" action="${pageContext.request.contextPath}/wxuser/lootShareIncreaseInterest?shareMobileDateJM=${shareMobileDateJM}&lootMobile=" method="POST">
				<div class="imgDiv clearfix positionR">
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
						<div class="positionA textC content1">
							<div class="width80 blockC">
								<div class="MB20 activityTex font20">您的手机号为<span class="font26" id="mobileLoot">${lootMobile}</span></div>
								<label><input class="width100 blockC block font18 none" type="submit" value="领取">
								<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn1.png" class="actBtn1"></label>


							</div>
						</div>	
					<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				</div>
			</form>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">
					<div class="font18 actvityTexEm MT5P">快点进入联璧钱包</div>
					<div class="font16 MT5 actvityTexEm">和朋友一起享受加息收益吧</div>
					<div class="MT20">已有<span class="ML5 MR5 actvityTexEm font24 robbedNum">0</span>人领取</div>
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
		</c:if>
		<c:if test="${resultFlag=='99999'}">
			<!--返回值：${resultFlag}：（该用户抢的加息券非当天分享，已失效）-->
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_02.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC content1">
					<span class="activityTex font26">抱歉，该券已失效</span>
					<a class="width80 blockC block MT10 textC" href="${pageContext.request.contextPath}/webabout/download"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/share/btn2.png" class="actBtn2"></a>	

				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
			<div class="imgDiv clearfix positionR">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
				<div class="positionA textC width100 activityTex font14 NumtextArea">
					<div class="font18 actvityTexEm MT5P">快点进入联璧钱包</div>
					<div class="font16 MT5 actvityTexEm">和朋友一起享受加息收益吧</div>
					<div class="MT20">已有<span class="ML5 MR5 actvityTexEm font24 robbedNum">0</span>人领取</div>
				</div>
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_03.jpg?<%=request.getAttribute("version")%>">
			</div>
		</c:if>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_04.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_05.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_06.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_07.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_08.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_09.jpg?<%=request.getAttribute("version")%>">
		</div>
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_March1/img_10.jpg?<%=request.getAttribute("version")%>">
		</div>
	</div>


	<c:if test="${resultFlag=='55555'}"> 
			<div class="pop popUp1" id="popUp1">
				<div class="popCon clearfix"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151202/alertweixin.png?<%=request.getAttribute("version")%>"></div>	
			</div>
	</c:if>
	<input type="hidden" name="goUrl" id="goUrl" value="${shareUrl}">
	<input type="hidden" name="count" id="count" value="${count}" >
	<input type="hidden" name="shareMobileDateJM" id="shareMobileDateJM" value="${shareMobileDateJM}">
	<script src="${pageContext.request.contextPath}/js/weixin/page/acctivityLootShareT.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>