<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<div class="menuLarge">
	<ul class="inlineBUl subnav fl">
		<li class="${subnav eq 1?'currentA':'whiteTex'}" ><a href="${pageContext.request.contextPath}/webabout/aboutUs" class="whiteTex" rel="nofollow">公司概况</a></li>
		<li class="${subnav eq 2?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webaboutUs/goPlatformOverview" class="whiteTex" rel="nofollow">联璧金融</a></li>
		<li class="${subnav eq 3?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webabout/goOurBackground" class="whiteTex" rel="nofollow">联系我们</a></li>
		<li class="${subnav eq 4?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webabout/goPropertyInfo" class="whiteTex" rel="nofollow">产品介绍</a></li>
		<li class="${subnav eq 5?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webabout/goSafetyGuarantee" class="whiteTex" rel="nofollow">安全保障</a></li>
		<li class="${subnav eq 6?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webaboutUs/goServicesAgreement" class="whiteTex" rel="nofollow">服务协议</a></li>
	</ul>
</div>
<div class="menuLittle none">
	<ul class="inlineBUl subnav clearfix">
		<li class="${subnav eq 1?'currentA':'whiteTex'}" ><a href="${pageContext.request.contextPath}/webabout/aboutUs" class="whiteTex" rel="nofollow">公司概况</a></li>
		<li class="${subnav eq 2?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webaboutUs/goPlatformOverview" class="whiteTex" rel="nofollow">联璧金融</a></li>
		<li class="${subnav eq 3?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webabout/goOurBackground" class="whiteTex" rel="nofollow">联系我们</a></li>
	</ul>
	<ul class="inlineBUl subnav MT5 clearfix">
		<li class="${subnav eq 4?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webabout/goPropertyInfo" class="whiteTex" rel="nofollow">产品介绍</a></li>
		<li class="${subnav eq 5?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webabout/goSafetyGuarantee" class="whiteTex" rel="nofollow">安全保障</a></li>
		<li class="${subnav eq 6?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webaboutUs/goServicesAgreement" class="whiteTex" rel="nofollow">服务协议</a></li>
	</ul>
</div>
