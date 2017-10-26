<%@page import="com.web.util.tongdun.TongdunProperUtil"%>
<%@page import="com.web.util.tongdun.RandomUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../../common.jsp"%>
<%-- <%@  include file="../../shareUtil.jsp"%> --%>
<%
session.setAttribute("token_id", "lianbi" + session.getId());
application.setAttribute("tongDunSrc", TongdunProperUtil.src);
application.setAttribute("fpHost", TongdunProperUtil.fpHost);
String queryString = request.getQueryString()==null?"":"?"+request.getQueryString();
 %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<title>联璧金融</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/loginWeiXin.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
    <script type="text/javascript">

	//guanxuxing-add-shebeizhiwen-begin
	 (function() {
	        _fmOpt = {
	            partner: 'lianbi',
	            appName: 'lianbi_web',
	            token: '${token_id}' 
	              };
	        var cimg = new Image(1,1);
	        cimg.onload = function() {
	            _fmOpt.imgLoaded = true;
	        };
	        cimg.src = "${tongDunSrc}?partnerCode=lianbi&appName=lianbi_web&tokenId=" + _fmOpt.token;
	        var fm = document.createElement('script'); fm.type = 'text/javascript'; fm.async = true;
	        fm.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'static.fraudmetrix.cn/fm.js?ver=0.1&t=' + (new Date().getTime()/3600000).toFixed(0);
	        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(fm, s);
	    })();
	//guanxuxing-add-shebeizhiwen-end
   </script>
	</head>
<body class="positionR">
	<div class="wrapper">
		<div class="imgDiv clearfix" id="banner">
			<img id='bannerImg' src="${wxPicLink}" width="100%">
		</div>

		<!-- 活动详情开始 -->
	    <div class="activityDetailBtn positionF none" id="activityDetailBtn"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/activityDetailBtn.png?<%=request.getAttribute("version")%>" width="100%">
          <span class="positionA"></span>
	    </div>
	    <div class="positionF activityDetailBkg none" id="activityDetailBkg" >
	    	<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/activityRule.png?<%=request.getAttribute("version")%>" width="100%">
	    	<span class="closeBtn positionA closeBtn3" id="closeBtn"></span>
	    	<div class="activityTxt positionA">
	    	 <ul id="activityTxtList" class="activityTxtList"></ul>            
	    	</div>
 	    </div>
	    <div class="activityDetailBtn positionF none" id="activityDetailBtn2"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/activityDetailBtn.png?<%=request.getAttribute("version")%>" width="100%">
          <span class="positionA"></span>
	    </div>
 	     <div class="positionF activityDetailBkg none" id="activityDetailBkg2">
	    	<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/activityRule2.png?<%=request.getAttribute("version")%>" width="100%">
	    	<span class="closeBtn2 positionA closeBtn3" id="closeBtn"></span>
	    	<div class="activityTxt positionA">
	    	 <ul id="activityTxtList" class="activityTxtList"></ul>
	    	</div>
 	    </div>
	    <!-- 活动详情结束 -->
		<div class=" clearfix PT20 BlueBkg positionR" id="signIn">
			<img style="top: 0;z-index: -1;" class="positionA"  src="${wxBackpic1}" width="100%"/>
			<div class="width90 blockC">
<!-- 				<form id="loginForm"> -->
					<div class="MT30">
						<!-- 手机号 -->
						<div class="positionR whiteBkg MB10 radius5">
							<span class="positionA inputIcon ML10 fl"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-01.png?<%=request.getAttribute("version")%>" width="30"></span>
							<div class="ML50"><input type="tel" name="phoneNum" id="phoneNum" value="${userDto.phoneNum}" maxlength="11" oninput="$.checkLimit($(this),'',true);" onkeyup="$.checkLimit($(this),'',true);" onafterpaste="$.checkLimit($(this),'',true);" placeholder="请输入手机号" class="inputBkg1  width90 whiteBkg" />
							</div>
						</div>
						<!-- 登录密码-->
						<div class="positionR whiteBkg MB10 radius5 captcha_div">
							<span class="positionA inputIcon ML10"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-02.png?<%=request.getAttribute("version")%>" width="30"></span>
							<div class="ML50"><input onkeyup="$.checkLimit1($(this),'',false);" type="password" placeholder="请设置登录密码" maxlength="16" class="inputBkg1 width90" id="passWord"/>
							</div>
						</div>
					<!-- 图片验证码 -->
							<!--<div class="positionR whiteBkg MB10 radius5 outHide">
								<span class="positionA inputIcon ML10"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-04.png?<%=request.getAttribute("version")%>" width="30"></span>
								<img id="imgcode" class="fr" src="${pageContext.request.contextPath}/imgcode" height="42" width="100" class="width25 fr PLR1P">
								<div class="ML50 MR100"><input type="text" class="inputBkg1 MB10 width90" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
								</div>
							</div>-->
					
						
						<div class="clearfix">
						<!-- 验证码 -->
							<div class="positionR whiteBkg MB10 radius5 outHide">
								<span class="positionA inputIcon ML10"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-05.png?<%=request.getAttribute("version")%>" width="30"></span>
								<a class="btn fr   smallBtn1" id="gainCode">获取<br/>验证码</a>
								<div class="ML50 MR100"><input type="tel" id="checkCode" name="checkCode" value="${userDto.checkCode}" placeholder="验证码" oninput="$.checkLimit($(this),'',false);checkC();" onkeyup="$.checkLimit($(this),'',false);checkC();" onafterpaste="$.checkLimit($(this),'',false);checkC();" maxlength="6" class="inputBkg1 MB10 width100" /></div>
							</div>
						</div>
					</div>
					
					<div><a class="btn width100 block forbid loginBtn" id="loginSubmit">注&nbsp;&nbsp;册</a></div>
					<div class="MB20 MT10 agreenmentArea">
					<span class="checkBoxArea fl MR10 inlineB" id="checkBoxArea">
						<img src="${pageContext.request.contextPath}/pic/weixin/checkbox2.png?<%=request.getAttribute("version")%>" >
					</span>
					<!-- <input type="checkbox" name="checkbox" checked="checked" value="checkbox" id="Agreement"/> -->
					<span class="inlineB" for="Agreement">我同意<a id="register">《用户注册服务协议》</a></span>
					</div>
<!-- 				</form> -->
			</div>			
		</div>
		<div id="contentWrapN" >
		<!--新手专享福利begin  专为channel=kangou  这个平台用的-->
		<div id="templateKangou" class="positionR">
			<img src="${wxBackpic2}" width="100%"/>
			<p class="positionA template2Txt" style="color: #fff;" id="template2TxtN">
			1、注册送<font color="#DAA520">80元</font>现金红包，红包可提现；<br/>2、注册加送<font color="#DAA520">0.5%活期加息券</font>；<br/>3、注册享<font color="#DAA520">0元购</font>399元路由器；
			</p>
		</div>
		<!--新手专享福利end-->

		<!--新手专享福利begin-->
		<div id="template2" class="positionR none">
			<img src="${wxBackpic2}" width="100%"/>
			<p class="positionA template2Txt" style="color: #fff;" id="template2TxtN">
				注册送<font color="#FF0000">80元</font>现金红包，红包可提现；<br/>注册加送<font color="#FF0000">0.5%活期加息券</font>；<br/>注册享<font color="#FF0000">0元购</font>399元路由器；
			</p>
		</div>
		<!--新手专享福利end-->
		<!-- 活动规则显示在外版 -->
		<div class="ruleDetails none" style="margin-top: -1px;">
			<div class="imgDiv clearfix positionR" >
				<img src="${wxBackpic3}" alt="" width="100%">
				<div class="activityTxt2 positionA" id="activityTxtN">
			    	<ul id="activityTxtList1" ></ul>
	    		</div>
			</div>
		</div>
	<!-- 活动规则显示在外版  专为看够影豆搞的-->
		<div class="ruleDetailsKangou" style="margin-top: -1px;">
			<div class="imgDiv clearfix positionR" >
				<img src="${wxBackpic3}" alt="" width="100%">
				<div class="activityTxt2 positionA" id="activityTxtN">
					<ul id="activityListKangou" >
						<li>
		<p>1、本活动仅限通过本页面注册联璧金融的新用户；</p>
		<p>2、新用户注册即送80元红包和0.5%活期加息券，可以在个人账户里查看；</p>
		<p>3、注册联璧金融并成功首次投资铃铛宝定期（30天以上）满800元，即送通兑电影票1张，首投定期满2000元即送通兑电影票2张，首投定期满5000元即可获得5张电影票；</p>
		<p>4、注册用户还可享0元购399路由器特权，在京东上购买k2路由器后，在联璧金融激活k码，即返还399元，一个月后可以全额提现，具体参见app内路由器活动。</p>
		<p>5、符合条件用户电影票将在3个工作日内以口令码形式发放到注册手机号上，电影票通兑券详细使用规则以看购网为准。</p>
		<p>6、有任何问题可以咨询联璧金融客服热线：4006-999-211或关注微信联璧钱包。</p>
		</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="imgDivWrap" class="imgDivWrap">
			<div class="imgDiv clearfix positionR  textC">
				<img src="${wxBackpic4}">
					<div id="imgDivL1" class="purple">
						<div class=" positionA  imgDivL">
							<p class="font16 lineHeight2x fontBold whiteSpace">高收益 随存随取</p>
							<p class="font12 MB5 whiteSpace">当日计息 资金快速提现</p>
						</div>
						<div class=" positionA  imgDivR">
							<p class="font16 lineHeight2x fontBold whiteSpace">低门槛 100元起投</p>
							<p class="font12 MB5 whiteSpace">零钱投资 人人轻松理财</p>
						</div>
						<div class=" positionA  imgDivLN">
							<p class="font16 lineHeight2x fontBold whiteSpace">强保障 优质债权</p>
							<p class="font12 MB5 whiteSpace marginLeft3">供应链资产 实力平台担保 </p>
						</div>
						<div class=" positionA  imgDivRN">
							<p class="font16 lineHeight2x fontBold whiteSpace">风控强 B轮融资</p>
							<p class="font12 MB5 whiteSpace">银行存管 1亿元实缴资金</p>
						</div>
					</div>
					<div id="imgDivL2" class="whiteTex none">
						<div class=" positionA  imgDivTxtR">
							<p class="font18 lineHeight2x">更胜一筹 高收益</p>
							<p class="font12 MB5">高出同类产品利率</p>
			                <p class="font12">秒杀各种“宝”</p>
						</div>
						<div class=" positionA  imgDivTxtL">
							<p class="font18 lineHeight2x">百元起投 低门槛</p>
							<p class="font12 MB5">微小资金累计增长</p>
			                <p class="font12">小投资大收益</p>
						</div>
						<div class=" positionA  imgDivTxtRN">
							<p class="font18 lineHeight2x">随存随取 更灵活</p>
							<p class="font12 MB5">一键购买省时省力 </p>
			                <p class="font12">更快捷更便捷</p>
						</div>
						<div class=" positionA  imgDivTxtLN">
							<p class="font18 lineHeight2x">资金管理  强保障</p>
							<p class="font12 MB5">国际信息安全标准</p>
			                <p class="font12">强力保护数据</p>
						</div>
					</div>
			</div>
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${wxBackpic5}">
			
		</div>	
	</div>
		
		
	</div>
	<!--提示下载-->
	<div class="arrowDown" id="arrowDown">
		<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon5.png?<%=request.getAttribute("version")%>" width="40" class="arrowsIcon">
	</div>
	<!--提示下载app-->
	<div class="btmAreaFix clearfix" id="btmAreaFix">
	  	<div class="leftArea">
		  	<span class="closed" id="closed"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/closed.png?<%=request.getAttribute("version")%>" width="20"></span>
		  	<span class="logo"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/logo.png?<%=request.getAttribute("version")%>" width="35"></span>
	  	</div>
	  	<div class="rightArea textL">
		  	<h4>联璧金融</h4>
		  	<p>使用APP客户端，体验更多惊喜！</p>
		  	<a href="https://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" class="btmFixBtn" rel="nofollow">下载APP</a>
	  	</div>
  	</div>
	<input type="hidden" name="URL" id="URL" value="${URL}" >
	<input type="hidden" name="channel" id="channel"  value="<%=request.getParameter("channel")%>">
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
		var cnzz_s_tag = document.createElement('script');
		cnzz_s_tag.type = 'text/javascript';
		cnzz_s_tag.async = true;
		cnzz_s_tag.charset = 'utf-8';
		cnzz_s_tag.src = 'https://w.cnzz.com/c.php?id=1259544535&async=1';
		var root_s = document.getElementsByTagName('script')[0];
		root_s.parentNode.insertBefore(cnzz_s_tag, root_s);

	</script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/fastclick.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/web/integration/yundun.js"></script><!-- 验证码组件js -->
	<script src="${pageContext.request.contextPath}/js/weixin/page/landingPage/landingPage.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/landingPage/loginWeiXin.js?<%=request.getAttribute("version")%>"></script>
	<%@  include file="../baiduStatistics.jsp"%>
	<!--<script src="https://s4.cnzz.com/z_stat.php?id=1259544535&web_id=1259544535" language="JavaScript"></script> -->
</body>
</html>
