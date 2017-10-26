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
	<title>联璧金融</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/loginDare.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="positionR">
	<div class="wrapper">
		<div class="imgDiv clearfix">
			<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/wojiatishuBanner.png?<%=request.getAttribute("version")%>">
		</div>
		<!-- 活动详情开始 -->
	    <div class="activityDetailBtn positionF"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/activityDetailBtn.png?<%=request.getAttribute("version")%>" width="100%">
          <span class="positionA"></span>
	    </div>
	    <div class="positionF activityDetailBkg  none">
	    	<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/activityRule.png?<%=request.getAttribute("version")%>" width="100%">
	    	<span class="closeBtn positionA"></span>
	    	<div class="activityTxt positionA">
	    	 <ul>
			      <li>1、本活动仅限通过本活动页面注册的联璧金融新用户参与。</li>
			      <li>2、新用户注册即送1%加息券，免费送162元现金红包。</li>
			      <li>3、新用户注册即送1%加息券，免费送162元现金红包。</li>
			      <li>4、162元现金红包（2元活期代金券、定期10元、定期50元和定期100元），投资时可以直接抵用提现。</li>
			      <li>5、注册用户享0元购399路由器特权，具体规则见联璧金融app内活动。</li>
			      <li>6、有任何问题可以咨询联璧金融客服热线：<span class="JsPhoneCall"></span>或关注微信联璧钱包。</li>
             </ul>
	    	</div>
 	    </div>
	    <!-- 活动详情结束 -->
		<div class=" clearfix PTB20 BlueBkg">
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
						<div class="positionR whiteBkg MB10 radius5 ">
							<span class="positionA inputIcon ML10"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-02.png?<%=request.getAttribute("version")%>" width="30"></span>
							<div class="ML50"><input type="password" placeholder="请设置登录密码" maxlength="16" class="inputBkg1 width90" id="passWord"/>
							</div>
						</div>
						<!-- 交易密码-->
                          <div class="positionR whiteBkg MB10 radius5">
							<span class="positionA inputIcon ML10"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-02.png?<%=request.getAttribute("version")%>" width="30"></span>
							<div class="ML50"><input type="password" placeholder="请设置交易密码(6位数字)" maxlength="16" class="inputBkg1  width100" id="traderPassword"/>
							</div>
						  </div> 
						<!-- 图片验证码 -->
							<div class="positionR whiteBkg MB10 radius5 outHide">
								<span class="positionA inputIcon ML10"><img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon-04.png?<%=request.getAttribute("version")%>" width="30"></span>
								<img id="imgcode" class="fr" src="${pageContext.request.contextPath}/imgcode" height="42" width="100" class="width25 fr PLR1P">
								<div class="ML50 MR100"><input type="text" class="inputBkg1 MB10 width90" name="verifycode" placeholder="图形验证码" id="verifycode" maxlength="4"/>
								</div>
							</div>
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
		<!-- 理财优势 -->
		<div class="Advantage width100 positionR">
			<p class="advantageTxt1 positionA textC width100">理财优势</p>
         <img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/advantageBkg1.png?<%=request.getAttribute("version")%>" alt="" width="100%">
		</div>
		<div class="Advantage width100 positionR"><p class="positionA textC width100 advantageTxt2">安全有保障的互联网理财平台</p>
        <img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/advantageBkg2.png?<%=request.getAttribute("version")%>" alt="" width="100%">
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/img_01.jpg?<%=request.getAttribute("version")%>">
			<div class="imgDivTxtR positionA width50 whiteTex">
				<p class="font18 lineHeight2x">更胜一筹 高收益</p>
				<p class="font12 MB5">高出同类产品利率</p> 
                <p class="font12">秒杀各种“宝”</p>
			</div>
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/img_02.jpg?<%=request.getAttribute("version")%>">
			<div class="imgDivTxtL positionA width50 whiteTex">
				<p class="font18 lineHeight2x">百元起投 低门槛</p>
				<p class="font12 MB5">微小资金累计增长</p> 
                <p class="font12">小投资大收益</p>
			</div>
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/img_03.jpg?<%=request.getAttribute("version")%>">
			<div class="imgDivTxtR positionA width50 whiteTex">
				<p class="font18 lineHeight2x">随存随取 更灵活</p>
				<p class="font12 MB5">一键购买省时省力 </p> 
                <p class="font12">更快捷更便捷</p>
			</div>
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/img_04.jpg?<%=request.getAttribute("version")%>">
			<div class="imgDivTxtL positionA width50 whiteTex">
				<p class="font18 lineHeight2x">资金管理  强保障</p>
				<p class="font12 MB5">国际信息安全标准 </p> 
                <p class="font12">强力保护数据</p>
			</div>
		</div>
		<div class="imgDiv clearfix positionR">
			<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/scanImg.jpg?<%=request.getAttribute("version")%>">
		</div>		
	</div>
	<div class="arrowDown" id="arrowDown">
		<img src="${pageContext.request.contextPath}/pic/weixin/landingPageTemplate/icon5.png?<%=request.getAttribute("version")%>" width="40" class="arrowsIcon">
	</div>
	<div class="btmAreaFix clearfix" id="btmAreaFix">
	  	<div class="leftArea">
		  	<span class="closed" id="closed"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/closed.png?<%=request.getAttribute("version")%>" width="20"></span>
		  	<span class="logo"><img src="${pageContext.request.contextPath}/pic/web/mobileLanding/logo.png?<%=request.getAttribute("version")%>" width="35"></span>
	  	</div>
	  	<div class="rightArea textL">
		  	<h4>联璧金融</h4>
		  	<p>使用APP客户端，体验更多惊喜！</p>
		  	<a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" class="btmFixBtn">下载APP</a>
	  	</div>
  	</div>
	<input type="hidden" name="URL" id="URL" value="${URL}" >
	<script type="text/javascript">
		var contextPath = "${pageContext.request.contextPath}";
		var message = "${message}";
	</script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/landingPage/landingPage.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/landingPage/loginWojiatisu.js?<%=request.getAttribute("version")%>"></script>
	<div class="none"><script src="https://s4.cnzz.com/z_stat.php?id=1259544535&web_id=1259544535" language="JavaScript"></script></div>
	<%@  include file="../baiduStatistics.jsp"%>		
</body>
</html>
