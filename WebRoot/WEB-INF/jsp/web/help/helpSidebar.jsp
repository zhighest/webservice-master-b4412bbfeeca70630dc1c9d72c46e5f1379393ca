<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<div class="helpSidebar">
	<div class="menuLarge">
		<ul id="goEveryLi" class="subnav fl inlineBUl">
			<li class="${helpSidebar eq 2?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goKcodeActivation?hasHeadFoot=${hasHeadFoot}">K码激活</a></li>
			<li class="${helpSidebar eq 3?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goNameBindCard?hasHeadFoot=${hasHeadFoot}">实名绑卡</a></li>
			<li class="${helpSidebar eq 11?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/bindCardError?hasHeadFoot=${hasHeadFoot}">绑卡出错</a></li>
			<li class="${helpSidebar eq 4?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goTopUp?hasHeadFoot=${hasHeadFoot}">充　值</a></li>
			<li class="${helpSidebar eq 5?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goDemandProduct?hasHeadFoot=${hasHeadFoot}">购买零钱计划</a></li>
			
			<li class="${helpSidebar eq 6?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goFixProduct?hasHeadFoot=${hasHeadFoot}">购买定期</a></li>
			<li class="${helpSidebar eq 7?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goTransferOut?hasHeadFoot=${hasHeadFoot}">零钱计划转出</a></li>
			
			<li class="${helpSidebar eq 8?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goWithdraw?hasHeadFoot=${hasHeadFoot}">提　现</a></li>
			<li class="${helpSidebar eq 10?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goSignIn?hasHeadFoot=${hasHeadFoot}">签　到</a></li>
			
			<li class="${helpSidebar eq 9?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goHelpCenterQA?hasHeadFoot=${hasHeadFoot}">Q&A</a></li>	
		</ul>
	</div>
	<div class="menuLittle none">
		<ul class="subnav inlineBUl">
			<li class="${helpSidebar eq 2?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goKcodeActivation?hasHeadFoot=${hasHeadFoot}">K码激活</a></li>
			<li class="${helpSidebar eq 3?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goNameBindCard?hasHeadFoot=${hasHeadFoot}">实名绑卡</a></li>
			<li class="${helpSidebar eq 11?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/bindCardError?hasHeadFoot=${hasHeadFoot}">绑卡出错</a></li>
			<li class="${helpSidebar eq 4?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goTopUp?hasHeadFoot=${hasHeadFoot}">充　值</a></li>
			<li class="${helpSidebar eq 5?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goDemandProduct?hasHeadFoot=${hasHeadFoot}">购买零钱计划</a></li>
			
			<li class="${helpSidebar eq 6?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goFixProduct?hasHeadFoot=${hasHeadFoot}">购买定期</a></li>
			<li class="${helpSidebar eq 7?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goTransferOut?hasHeadFoot=${hasHeadFoot}">零钱计划转出</a></li>
			<li class="${helpSidebar eq 8?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goWithdraw?hasHeadFoot=${hasHeadFoot}">提　现</a></li>
			<li class="${helpSidebar eq 10?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goSignIn?hasHeadFoot=${hasHeadFoot}">签　到</a></li>	
			<li class="${helpSidebar eq 9?'currentA':'whiteTex'}"><a class="whiteTex" href="${pageContext.request.contextPath}/webindex/goHelpCenterQA?hasHeadFoot=${hasHeadFoot}">Q&A</a></li>	
		</ul>	
	</div>
</div>
<input id="hasHeadFoot" name="hasHeadFoot" type="hidden" value="${hasHeadFoot}">
