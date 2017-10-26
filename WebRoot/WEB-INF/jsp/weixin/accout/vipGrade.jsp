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
    <title>联璧会员</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/vipGrade.css?<%=request.getAttribute("version")%>" />
    <style id="keyframes"></style> 
    <%@  include file="../header.jsp"%>

</head>
<body>
	<!--个人信息&等级信息开始-->
    <div class="gradeWrap width100 whiteTex">
        <div class="content clearfix boxSizing textC blockC positionR font16">
            <div class="allIntegral fl">
                <p class="totalPoint"></p>
                <p>累计积分</p>
            </div>
            <div class="userImg fl positionA outHide">
                <img class="width100" src="${pageContext.request.contextPath}/pic/weixin/version1125/defaultAvatar.png" />
            </div>
            <div class="curIntegral fr">
                <p class="currentPoint"></p>
                <p>当前积分</p>
            </div>
        </div>
        <div class="nextGradePointIntro"></div>
        <div class="ladder blockC font14 boxSizing clearfix PT5P">
        	<div class="lineWrap positionR outHide">
				<div class="progressImg width100 positionR clearfix">
					<div class="progressBox width20 fl positionR">
						<img class="vipOne vipImgClick inlineB fl" src="" />
						<i class="progress whiteBkg positionA"></i>
					</div> 
					<div class="progressBox width20 fl positionR">
						<img class="vipTwo vipImgClick inlineB fl" src="" />
						<i class="progress whiteBkg positionA"></i>
					</div>
					<div class="progressBox width20 fl positionR">
						<img class="vipThree vipImgClick inlineB fl" src="" />
						<i class="progress whiteBkg positionA"></i> 
					</div>
					<div class="progressBox width20 fl positionR">
						<img class="vipFour vipImgClick inlineB fl" src="" />
						<i class="progress whiteBkg positionA"></i>
					</div>
					<div class="progressBox width20 fl positionR">
						<img class="vipFive vipImgClick inlineB fl" src="" />
						<!--<i class="progress whiteBkg positionA"></i>-->
					</div>
				</div>
				<div class="progressLine blockC positionA" id="progressContent"></div>
			</div>
            <dl class="fl positionR">
				<dd class="pointName"></dd>
				<dd class="userIntegral"></dd>
				<span class="triangle positionA"></span>
			</dl>
			<dl class="fl positionR">
				<dd class="pointName"></dd>
				<dd class="userIntegral"></dd>
				<span class="triangle positionA"></span>
			</dl>
			<dl class="fl positionR">
				<dd class="pointName"></dd>
				<dd class="userIntegral"></dd>
				<span class="triangle positionA"></span>
			</dl>
			<dl class="fl positionR">
				<dd class="pointName"></dd>
				<dd class="userIntegral"></dd>
				<span class="triangle positionA"></span>
			</dl>
			<dl class="fl positionR">
				<dd class="pointName"></dd>
				<dd class="userIntegral"></dd>
				<span class="triangle positionA"></span>
			</dl>
        </div>
    </div>
    <!--个人信息&等级信息结束-->
	<div class="height2P grayBkg"></div><!--充当背景色	-->
    <!--我的特权开始-->
    <div class="privilegeContent width100 whiteBkg">
    	<h2 class="myPrivilege heigh50 font18 PLR5P blackTex2 textL"><span class="privilegeName"></span><img class="downOrUp turnAround90 fr" src="${pageContext.request.contextPath}/pic/weixin/ver2_7/thinStick.png" width="14"><span class="fr textR font14 blueColor width20 goColletScore">积分</span></h2>
    	<ul class="clearfix blackTex2 privilegeList">
    	</ul>
    </div>
    <!--我的特权结束-->
    
    <!--特权弹出层开始-->
    <div class="popup positionF width100 clearfix font18">
    	<img class="fr closedBtn" src="${pageContext.request.contextPath}/pic/weixin/closedBtn.png" />
    	<img class="positionA animateImg" src="" />
    	<p class="priviName none"></p>
    	<p class="userPopup whiteTex MT5 none"></p>
    	<div class="popupContent none PT40 PB30">
    		<p class="popupDetail PB20 PLR5P"></p>
    		<p class="popupList font16">
    			
    		</p>
    	</div>
    </div>
    <!--特权弹出层结束-->
    
    <script src="${pageContext.request.contextPath}/js/weixin/integration/merge.js?<%=request.getAttribute("version")%>"></script>
    <script src="${pageContext.request.contextPath}/js/weixin/page/vipGrade.js?<%=request.getAttribute("version")%>"></script>
    <%@  include file="../baiduStatistics.jsp"%>
</body>
</html>
