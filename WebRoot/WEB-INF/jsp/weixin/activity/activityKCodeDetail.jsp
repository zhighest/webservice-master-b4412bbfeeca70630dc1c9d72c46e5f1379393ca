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
	<title>“K”码活动详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/acitivity.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/activityKCode.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
</head>
<body class="acitivityBkg positionR">
	<div class="detailsBkg positionR">
		<ul class="MB20 parent tabSwitch inlineBUl clearfix"><li class="fl width50 light tabItem tab1 lineHeight1_8x" onclick="tabSwitchLi('tabItem','tab1');tabSwitchCon('tabContent','tabContent1')">K码礼包</li> <li class="fl width50 dark tabItem tab2 lineHeight1_8x" onclick="tabSwitchLi('tabItem','tab2');tabSwitchCon('tabContent','tabContent2')">福利礼包</li></ul>
		<div class="width90 blockC tabContent tabContent1">	
			<div class="contentDetails width95 blockC PTB20 textL lineHeight1_5x radius5">
				<h5 class="actYellowTex MB5 textIndent15">1、什么是K码？</h5>
				<p class="MB15 whiteTex textIndent15">K码是上海斐讯数据通信技术有限公司与上海联璧电子科技有限公司联合推出的返还活动码，k码印刷在路由器上面。</p>
				<h5 class="actYellowTex MB5 textIndent15">2、如何参加返送活动？</h5>
				<p class="MB15 whiteTex textIndent15">您可以通过安装“联璧金融”手机APP，进入联璧金融理财平台，输入涂层内K码并激活，k码礼包到期后即可获赠相应金额的礼金。</p>
				<h5 class="actYellowTex MB5 textIndent15">3、送我的K码礼包，我在哪里可以看到？</h5>
				<p class="whiteTex textIndent15 MB15">您可以通过关注“联璧钱包”微信公众号，或安装“联璧金融”手机APP，在“我的账户-礼包资产”中查看到您的K码礼包，礼包到期后可兑换礼金，礼金将返还至您的账户可用余额，届时您可以选择心仪的理财产品进行投资或者提现至银行卡。</p>
				<h5 class="actYellowTex MB5 textIndent15">4、K码有有效时间吗？</h5>
				<p class="whiteTex textIndent15 MB15">K码有效时间为购买开始的30天，请在此期限内激活K码。在期限内没有被激活的K码会失效，将无法再被激活。</p>
				<h5 class="actYellowTex MB5 textIndent15">5、拥有多个K码如何参与活动？</h5>
				<p class="whiteTex textIndent15">本活动允许一个联璧金融平台用户（同一实名认证下的同一手机号码）激活多个K码。</p>
				<p class="whiteTex textIndent15">激活斐讯K3路由器细则为：</p>
				<p class="whiteTex textIndent15">*凡“联璧金融”平台有效注册用户，无需投资即可激活一个K码。每个K码礼包对应12期返还，每期30天，礼包到期可以兑换相应的礼金。</p>
				<p class="whiteTex textIndent15">激活斐讯K2（含K2C、FIR302、 K1及K1S）路由器规则为：</p>
				<p class="whiteTex textIndent15">*凡“联璧金融”平台有效注册用户，活动期间（2017年3月20日起）仍可激活K码。</p>
				<p class="whiteTex textIndent15">激活斐讯手机活动细则为：</p>
				<p class="whiteTex textIndent15 MB15">* 每位“联璧金融”用户最多可激活2个手机K码，其中小龙6系列手机（包括C1230L、C1530L）仅限激活一台。</p>
				<h5 class="actYellowTex MB5 textIndent15">6、如果我购买的路由器退货了，送我的K码礼包还有吗？</h5>
				<p class="whiteTex textIndent15">本次活动为斐讯产品（详见：斐讯路由器产品活动）获得者赠送的K码礼包，如您选择退货，视为您主动放弃参与本次活动。联璧金融平台将取消您在本次活动中获赠相应金额的K码礼包。如果是K3路由器，凡已兑换过K码礼金的，均无法退货。</p>
				<p class="font12 textC whiteTex MT10">上海斐讯数据通信技术有限公司及<br/>上海联璧电子科技有限公司对本次活动拥有最终解释权</p>
			</div>	
		</div>
		<div class="width90 blockC showInfo2 tabContent tabContent2" style="display: none">	
			<div class="contentDetails width95 blockC PTB20 textL lineHeight1_5x radius5">
				<h5 class="actYellowTex MB5 textIndent15">1、什么是福利礼包？</h5>
				<p class="MB15 whiteTex textIndent15">福利礼包是联璧金融赠送给用户的内含“福利卡券”的礼包，福利礼包内容根据不同活动可能有所不同。</p>
				<h5 class="actYellowTex MB5 textIndent15">2、送我的福利礼包，我在哪里可以看到？</h5>
				<p class="MB15 whiteTex textIndent15">您可以通过关注“联璧钱包”微信公众号，或安装“联璧金融”手机APP，进入联璧金融理财平台，在“我的账户-礼包资产”中查看到您的福利礼包。</p>
				<h5 class="actYellowTex MB5 textIndent15">3、福利礼包会过期吗？</h5>
				<p class="whiteTex textIndent15 MB15">每一个福利礼包都会有一个兑换到期日，兑换到期日后礼包将失效。</p>
				<h5 class="actYellowTex MB5 textIndent15">4、福利礼包兑换后我在哪里可以看到我的福利卡券？</h5>
				<p class="whiteTex textIndent15 MB15">福利礼包兑换后系统会自动将您的福利卡券发放至您“我的账户-福利”的相应分类中。</p>
				<h5 class="actYellowTex MB5 textIndent15">5、如果我购买的路由器退货了，送我的福利礼包还有吗？</h5>
				<p class="MB15 whiteTex textIndent15">福利礼包为联璧金融赠送给用户的福利，如您选择退货，福利礼包仍然可以兑换。联璧金融平台只取消您在本次活动中获赠相应金额的K码礼包。</p>
				<p class="font12 textC whiteTex MT10">上海联璧电子科技有限公司对本次活动拥有最终解释权</p>

			</div>	
		</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<script src="${pageContext.request.contextPath}/js/weixin/page/activityKCodeshare.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/page/tabChange.js?<%=request.getAttribute("version")%>"></script>	
</body>
</html>
    
    

    
   

    

   


    
   

    
    

   
    

    

