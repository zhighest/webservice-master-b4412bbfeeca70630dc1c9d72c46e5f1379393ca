<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>问卷调查表</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/product.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/finance.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/normalize.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/component.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/cs-select.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/cs-skin-boxes.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/demo.css?<%=request.getAttribute("version")%>" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/drawer.css?<%=request.getAttribute("version")%>" />
	<script src="${pageContext.request.contextPath}/js/drawer/modernizr.custom.js?<%=request.getAttribute("version")%>"></script>
    <script src="${pageContext.request.contextPath}/js/weixin/integration.js?<%=request.getAttribute("version")%>"></script>
</head>
<body>
		<div class="container">

			<div class="fs-form-wrap" id="fs-form-wrap">
				<div class="fs-title">
					<h1>问卷调查表</h1>
				</div>
				<form id="myform" class="fs-form fs-form-full" autocomplete="off">
					<ol class="fs-fields">
						<li data-input-trigger>
							<label class="fs-field-label fs-anim-upper" for="q1" data-info="1.为了方便您的乘车体验，请问您对济南长途汽车总站智慧商旅手机微信（或app）快捷购票是否支持：">1.为了方便您的乘车体验，请问您对济南长途汽车总站智慧商旅手机微信（或app）快捷购票是否支持：</label>
							<div class="fs-radio-group fs-radio-custom clearfix fs-anim-lower">
								<span><input id="q1a" name="q1" type="radio" required value="A"/><label for="q1a">A.非常支持，很期待；</label></span>
								<span><input id="q1b" name="q1" type="radio" required value="B"/><label for="q1b">B.愿意尝试，很支持；</label></span>
								<span><input id="q1c" name="q1" type="radio" required value="C"/><label for="q1c">C.看看效果再说；</label></span>
							</div>
						</li>
						<li data-input-trigger>
							<label class="fs-field-label fs-anim-upper" for="q2" data-info="2.济南长途汽车总站智慧商旅推出旅游项目，请问您生活中对旅游如何看待：">2.济南长途汽车总站智慧商旅推出旅游项目，请问您生活中对旅游如何看待：</label>
							<div class="fs-radio-group fs-radio-custom clearfix fs-anim-lower">
								<span><input id="q2a" name="q2" type="radio" required value="A"/><label for="q2a">A.喜欢旅游，济南周边游、出省游；</label></span>
								<span><input id="q2b" name="q2" type="radio" required value="B"/><label for="q2b">B.有时间会考虑去旅游，放松心情；</label></span>
								<span><input id="q2c" name="q2" type="radio" required value="C"/><label for="q2c">C.生活节奏越来越紧张，打算通过旅游放松自己；</label></span>
							</div>
						</li>
						<li data-input-trigger>
							<label class="fs-field-label fs-anim-upper" for="q2" data-info="3.济南长途汽车总站智慧商旅计划推出城市定制巴士，请问您会接受吗：">3.济南长途汽车总站智慧商旅计划推出城市定制巴士，请问您会接受吗：</label>
							<div class="fs-radio-group fs-radio-custom clearfix fs-anim-lower">
								<span><input id="q3a" name="q3" type="radio" required value="A"/><label for="q3a">A.低碳出行很支持；</label></span>
								<span><input id="q3b" name="q3" type="radio" required value="B"/><label for="q3b">B.开车太堵、公交太挤、对城市巴士很期待；</label></span>
								<span><input id="q3c" name="q3" type="radio" required value="C"/><label for="q3c">C.愿意尝试；</label></span>
							</div>
						</li>
						<li>
							<label class="fs-field-label fs-anim-upper" for="q6">请输入您的手机号码</label>
							<input class="fs-anim-lower" id="q6" name="q6" type="text" placeholder="手机号码" maxlength="11" required/>
						</li>
					</ol><!-- /fs-fields -->
					<button class="fs-submit" type="submit">提交问卷</button>
					<div style="clear:both;"></div>
				</form><!-- /fs-form -->
			</div><!-- /fs-form-wrap -->

		</div><!-- /container -->
		<script src="${pageContext.request.contextPath}/js/drawer/classie.js"></script>
		<script src="${pageContext.request.contextPath}/js/drawer/selectFx.js"></script>
		<script src="${pageContext.request.contextPath}/js/drawer/fullscreenForm.js"></script>
		<script>
			(function() {
				var formWrap = document.getElementById( 'fs-form-wrap' );

				[].slice.call( document.querySelectorAll( 'select.cs-select' ) ).forEach( function(el) {	
					new SelectFx( el, {
						stickyPlaceholder: false,
						onChange: function(val){
							document.querySelector('span.cs-placeholder').style.backgroundColor = val;
						}
					});
				} );

				new FForm( formWrap, {
					onReview : function() {
						classie.add( document.body, 'overview' ); // for demo purposes only
					}
				} );
			})();
		</script>
	</body>
</html>