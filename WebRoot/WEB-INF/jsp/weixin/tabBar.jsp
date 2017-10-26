<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../common.jsp"%>
<%@ page import="com.web.util.UserCookieUtil" %>
<%@ page import="com.web.domain.UserSession" %>
<%@ page import="com.web.util.CommonUtil" %>
<%@ page import="com.web.util.DES3Util" %>
<%@ page import="com.web.util.LoginRedirectUtil" %>
<%@ page import="com.web.util.HttpRequestParam" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.web.util.Consts" %>
<%
	try {
		UserSession us = UserCookieUtil.getUser(request);
		if (us != null) {
			request.setAttribute("flag", "1"); // 已经登陆
			request.setAttribute("mobile", us.getMobile());
		} else {
			Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
			String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
			if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("mediaUid", mediaUid);
				String param = CommonUtil.getParam(userMap);
				try {
					param = DES3Util.encode(param);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUseridByWeixinUid",
						param);
				try {
					resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
							"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				JSONObject jsonObject = JSONObject.fromObject(resultMsg);
				// 交易密码正确
				if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
					request.setAttribute("flag", "1"); 
					request.setAttribute("username",jsonObject.get("nickName"));
					request.setAttribute("mobile", jsonObject.get("mobile"));
				}else{
					request.setAttribute("flag", "0"); // 没有登陆
				}
			}else{
				request.setAttribute("flag", "0"); // 没有登陆
			}
			
		}
	} catch (Exception e) {
		request.setAttribute("flag", "0"); // 没有登陆
		e.printStackTrace();
	}
 %>
<div class="whiteOpacity">
	<div class="btmBar whiteBkg borderTS lineHeight100 " id="btmBar">
		<ul class="inlineBUl btmBarUl clearfix">
			<li class="width33_3 btmBarTabLi fl" id="indexBtn">
				<a id="index" href="${pageContext.request.contextPath}/wxabout/goIndex" class="block  positionR" style="height: 50px;">
					<img style="top: -4px;"  alt="" width="36" id="icon12" class="none centerX" src="${pageContext.request.contextPath}/pic/weixin/version1125/icon1Gray.png?<%=request.getAttribute("version")%>">
					<div class="font10 grayTex LH100P centerX" style="bottom: 0;">首页</div>
				</a>
				
			</li>
			<li class="width33_3 btmBarTabLi fl" id="productIndexBtn">
				
				<a id="productIndex" href="${pageContext.request.contextPath}/wxproduct/goProductIndex?pageTag=productIndex" class="block  positionR" style="height: 50px;">
					<img style="top: -4px;"   alt="" width="36" id="icon13" class="none centerX" src="${pageContext.request.contextPath}/pic/weixin/version1125/icon2Gray.png?<%=request.getAttribute("version")%>">
					<div class="font10 grayTex LH100P centerX" style="bottom: 0;">投资理财</div>
				</a>
				
			</li>
			<li class="width33_3 btmBarTabLi fl" id="myAssetBtn">
				<a id="myAsset" href="${pageContext.request.contextPath}/wxtrade/goMyAsset?from=LBWX" class="block  positionR" style="height: 50px;">
					<img style="top: -4px;"   alt=""  width="36" id="icon14" class="none centerX" src="${pageContext.request.contextPath}/pic/weixin/version1125/icon3Gray.png?<%=request.getAttribute("version")%>">
					<div class="font10 grayTex LH100P centerX" style="bottom: 0;">我的账户</div>
				</a>
				
			</li>
		</ul>
	</div>
</div>
<div class="positionF none width100 height100P downBkg" id="downBkg">
	<div class="positionA downBox width90 PT20 PB15 radius10">
		<img class="block blockC" src="${pageContext.request.contextPath}/pic/weixin/phoneImg.png" width="50%">
		<div class="width70 blockC font20 strong PTB10 downBtn MT10">
			<a class="whiteTex" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai" rel="nofollow">下载联璧金融APP</a>
		</div>
		<img class="block blockC MT20" src="${pageContext.request.contextPath}/pic/weixin/yellowLogo.png" width="45%">
		<img class="positionA closeIcon" src="${pageContext.request.contextPath}/pic/weixin/closeIcon.png" width="10%" id="closeIcon">
	</div>
</div>
<input type="hidden" name="flag" id="flag" value="${flag}" >
<script type="text/javascript">
	$(document).ready(function() {
		$("#closeIcon").click(function(event) {
			$("#downBkg").stop().hide();
		});
	});	
</script>