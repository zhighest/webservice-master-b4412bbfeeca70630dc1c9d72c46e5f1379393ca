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
	<title>安全保障</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/drawer/swiper-3.3.1.min.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/guaranteeIntroduce.css?<%=request.getAttribute("version")%>" />
    <%@  include file="../header.jsp"%>
 </head>
  <body class="textL" style="font-family:Arial;">
  	<div class="MB40 PB40">
		<table class="width90 blockC lineHeight1_5x newHeight MT20 font14" >
			<tr>
				<td><div class="font24 whiteTex MB10 PB10" style="border-bottom: 1px dashed #ccc;">优质资产</div></td>
			</tr>
			<tr>
				<td class="whiteTex">联璧金融秉承安全至上的资产筛选原则，根据不同的产品类型及行业特性等特点制定了严苛的质量准则，对于企业的资质资信、股东资信、股东净资产、经营年限、资本规模、资产负债率、财务运营各项比率分析有着专业审查方法，并执行较高控制标准，保障理财资金全部投向合格的、安全的、有100%预期收益并正现金流回归的高质量标的方，以此保证资金的长期回报安全。</td>
			</tr>
		</table>
		<table class="width90 blockC ineHeight1_5x MB10P newHeight MT30 font14" >
			<tr>
				<td><div class="font24 whiteTex MB10 PB10" style="border-bottom: 1px dashed #ccc;">资金托管</div></td>
			</tr>
			<tr>	
				<td class="whiteTex">联璧金融采用第三方资金托管机制，意味着平台不直接经手客户资金，也无权擅自动用客户在第三方托管的资金，投资人通过第三方支付投资成功后，资金将直接进入借款人账户，保证交易真实和投资人资金安全。联璧金融采用风险准备金前置计提机制，保证在资金流动过程中出现不及时的回款情况下，投资人获得本金及利息的时效不受影响，高效的风险准备金调用机制严格保证资金的短期流动性安全。</td>
			</tr>
		</table>
		<table class="width90 blockC lineHeight1_5x newHeight MT30 font14" >
			<tr>
				<td><div class="font24 whiteTex PB10 MB0" style="border-bottom: 1px dashed #ccc;">风控严格</div></td>
			</tr>
			<tr>
				<td class="whiteTex">核验企业资信(各项审核资料提交);<br/>财务报表审查、偿债能力分析、现金流匡算;<br/>律所实地尽调,出具第三方尽调报告;会计师事务所出具审计报告;<br/>
上市公司证券部给予风控意见;应收账款合同、融资租赁合同、发票等凭证质押;</td>
			</tr>
		</table> 
        
	</div>
    <%@  include file="../baiduStatistics.jsp"%>
  </body>
</html>
