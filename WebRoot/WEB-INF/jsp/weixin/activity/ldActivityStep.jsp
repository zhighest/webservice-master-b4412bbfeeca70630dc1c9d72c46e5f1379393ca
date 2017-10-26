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
	<title>大家来激活“K码”</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity20151029.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    <script src="${pageContext.request.contextPath}/js/weixin/uploadImg/mobileBUGFix.mini.js"></script>  
    <script src="${pageContext.request.contextPath}/js/weixin/uploadImg/upload.js"></script>  
</head>
<body class="acitivityBkg PB40 positionR">

	<div id="loading" style="display:none;width: 100%;height: 100%;position: absolute;z-index: 8888;opacity: 1;filter:alpha(opacity=100);">
		<div style="bottom: 100px; position: fixed; text-align: center; width: 100%; z-index:1000;">
			<font style="background-color: rgba(0, 0, 0, 0.7); border-radius: 5px; color: #fff; z-index:1000; display: inline-table; line-height: 170%; max-width: 80%; padding: 5px 10px;">处理中...</font>
		</div>
	</div>
	<div class="wrapper positionR">
		<div id="step1" style="display: none;">
			<div class="title textC width80 blockC PT5P"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/title.png?<%=request.getAttribute("version")%>" alt=""></div>
			<div class="bkgOp width90 blockC radius5 MT30">
				<div class="width90 blockC PTB5P textC">
					<input id="kCode" class="inputAct radius5 width90 blockC block MB20 font20" placeholder="输入K码，请区分大小写" maxlength="10">
					<a class="btn actBtn width90 blockC block radius5 font20" id="postCode">激活</a>	
				</div>	
			</div>
			<div class="bkgOp width90 blockC radius5 MT30 MB30">
				<div class="width90 blockC PTB5P textL MT30">
					<h4 class="font16 whiteTex MB10">第一步</h4>
					<p class="whiteTex">点击激活后可<span class="emTex strong">获赠79元</span>联璧钱包理财产品</p>
				</div>
			</div>
			<div class="width90 blockC clearfix"><a href="${pageContext.request.contextPath}/wxactivity/goLDActivityDetal?${parm}" class="linkAct fr">查看活动问答</a></div>
		</div>
		<div id="step2" class="positionR" style="display: none;">
			<a href="${pageContext.request.contextPath}/wxtrade/goMyAsset?${parm}" class="rightBtn positionA fl whiteTex" id="viewMyAsset">查看我的资产></a>
			<div class="title textC width80 blockC"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/title2.png?<%=request.getAttribute("version")%>" alt=""></div>		

			<h4 class="textC font26 strong whiteTex">K码激活成功!</h4>
			<div class="bkgOp width90 blockC radius5 MT30 MB30">
				<div class="width90 blockC PTB5P textL MT30">
					<h4 class="font16 whiteTex MB10">第二步</h4>
					<p class="whiteTex lineHeight1_8x"><span class="emTex strong">分享</span>微信或<span class="emTex strong">上传</span>天猫五星好评图片，可获得20元联璧钱包理财产品</p>
				</div>
			</div>
			<div class="width80 blockC clearfix"><a href="${pageContext.request.contextPath}/wxactivity/goLDActivityDetal?${parm}" class="linkAct fr">查看活动问答</a></div>
			<div class="width90 blockC PTB5P textL">
				<div class="clearfix">
					<div class="fl zIndex3" id="uplee">
						<label>
							<input id="imgFile" name="imgFile" type="file" accept="image/*" multiple="multiple" style="display: none;" /><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn1.png?<%=request.getAttribute("version")%>" alt="" width="110">
						</label>
					</div>
					<div class="fl zIndex3" id="upleeWeixin" style="display: none;">
						<a onclick="updateWeixin()"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn1.png?<%=request.getAttribute("version")%>" alt="" width="110"></a>
					</div>
					<div class="fr zIndex3">
						<a id="weixin" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn2.png?<%=request.getAttribute("version")%>" alt="" width="100">			</a>
						<label id="lianbiIos" style="display: none;">
							<input type="button" value="" onclick="onShare('测试标题','测试内容','http://wx.lianbijr.com/wxactivity/activity_20151103','http://114.141.172.206:7080/webservice/pic/weixin/activity/activity_20151029/test.jpg');" style="display: none;"/>
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn2.png?<%=request.getAttribute("version")%>" alt="" width="100">			</label>
						<a id="lianbiAndroid" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn2.png?<%=request.getAttribute("version")%>" alt="" width="100">			</a>
						<label id="LingdangIos" style="display: none;">
							<input type="button" value="" onclick="onShare('测试标题','测试内容','http://wx.lianbijr.com/wxactivity/activity_20151103','http://114.141.172.206:7080/webservice/pic/weixin/activity/activity_20151029/test.jpg',${mobile});" style="display: none;"/>
							
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn2.png?<%=request.getAttribute("version")%>" alt="" width="100">			</label>
						<a id="LingdangAndroid" style="display: none;">
							<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/btn2.png?<%=request.getAttribute("version")%>" alt="" width="100">			</a>
					</div>
				</div>
				<div class="textC img1"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/img1.png?<%=request.getAttribute("version")%>" alt="" width="170"></div>
			</div>
		</div>
	</div>
	<div class="btmImg"><img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/logo.png?<%=request.getAttribute("version")%>" alt=""></div>
	<div class="popup" id="popup1" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/succeed.png" alt="" width="55">
				<h4 class="font20 MT5">激活成功</h4>
				<p class="font14 MT5">请登录联璧钱包平台查看您的理财订单。</p>
				<a id="popBtn1" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup pop2 textR" id="popup2" style="display: none;">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/weixinAlert.png" alt="" width="100%">
	</div>
	<div class="popup" id="popup3" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/succeed.png" alt="" width="55">
				<h4 class="font20 MT5">上传图片成功</h4>
				<p class="font14 MT5">请登录联璧钱包平台查看您的理财订单。</p>
				<a id="popBtn3" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup" id="popup4" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/succeed.png" alt="" width="55">
				<h4 class="font20 MT5">分享成功</h4>
				<p class="font14 MT5">请登录联璧钱包平台查看您的理财订单。</p>
				<a id="popBtn4" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup" id="popup5" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/warning.png" alt="" width="55">
				<h4 class="font20 MT5">加载异常，请刷新重试</h4>
				<a id="popBtn5" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup" id="popup6" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/warning.png" alt="" width="55">
				<h4 class="font20 MT5">网络错误，请稍后重试</h4>
				<a id="popBtn6" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup" id="popup7" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/warning.png" alt="" width="55">
				<h4 class="font20 MT5">关注微信公共号“联璧钱包”进行K码激活</h4>
				<a id="popBtn7" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup" id="popup8" style="display: none;">
		<div class="popCon blockC width80 PTB5P radius5">
			<div class="width90 blockC">
				<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/warning.png" alt="" width="55">
				<h4 class="font20 MT5">请至“联璧钱包”微信公众号活动页面操作</h4>
				<a id="popBtn8" class="btn block width100 MT15 radius5 font16">确定</a>
			</div>
		</div>	
	</div>
	<div class="popup pop2" id="popup2" style="display: none;">
		<img src="${pageContext.request.contextPath}/pic/weixin/activity/activity_20151029/weixinAlert.png" alt="" width="100%">
	</div>
	<input type="hidden" name="activityKCodeFlg" id="activityKCodeFlg" value="${activityKCodeFlg}" >
	<input type="hidden" name="activityShareFlg" id="activityShareFlg" value="${activityShareFlg}" >
	<input type="hidden" name="activityPraiseFlg" id="activityPraiseFlg" value="${activityPraiseFlg}" >
	<input type="hidden" name="channel" id="channel" value="${channel}" >
	<input type="hidden" name="mobile" id="mobile" value="${mobile}" >
	<input type="hidden" name="message" id="message" value="${message}" >
	<script src="${pageContext.request.contextPath}/js/weixin/page/ldActivityStep.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>	
</body>
</html>

