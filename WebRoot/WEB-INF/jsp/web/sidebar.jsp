<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/account.css?<%=request.getAttribute("version")%>"/>
<ul class="inlineBUl subnav fl">
	<li class="${sidebarIndex eq 1?'currentA':'whiteTex'}" ><a href="${pageContext.request.contextPath}/trade/goAccoutOverview" class="whiteTex" rel="nofollow">我的账户</a></li>
	<li class="${sidebarIndex eq 2?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webindex/goDemandProperty" class="whiteTex" rel="nofollow">零钱计划资产</a></li>
	<li class="${sidebarIndex eq 3?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webindex/goFixedProperty" class="whiteTex" rel="nofollow">定期资产</a></li>
	<li class="${sidebarIndex eq 4?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/wxpay/redirectPay?payFlag=kj" class="whiteTex" rel="nofollow">充值</a></li>
	<li class="${sidebarIndex eq 5?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/trade/goWithdraw" class="whiteTex" rel="nofollow">提现</a></li>
	<li class="${sidebarIndex eq 6?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/webindex/goInvestDetail" class="whiteTex" rel="nofollow">交易记录</a></li>
	<li class="${sidebarIndex eq 7?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/coupons/goMyInterest" class="whiteTex" rel="nofollow">加息券</a></li>
	<li class="${sidebarIndex eq 8?'currentA':'whiteTex'}"><a href="${pageContext.request.contextPath}/trade/voucher" class="whiteTex" rel="nofollow">代金券</a></li>
</ul>
