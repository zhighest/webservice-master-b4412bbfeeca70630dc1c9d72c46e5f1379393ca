<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<title>银行限额</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/limitTable.css?<%=request.getAttribute("version")%>" />
</head>
<body>
	<div class="width90 blockC PT20 PB50">
		<h4 class="blueTex2 font24 whiteTex lineHeight2x">认证支付支持最高限额</h4>	
		<table class="limitTable width100 blockC MT30 MB30">
			<thead class="blueTex2">
				<th>支持银行</th>
				<th>单笔最高<br/>支持限额</th>
				<th>单日最高<br/>支持限额</th>
				<th>单月最高<br/>支持限额</th>
			</thead>
			<tr>
				<td>农业银行</td>
				<td>5万</td>
				<td>30万</td>
				<td>900万</td>
			</tr>
			<!-- <tr>
				<td>浦发银行</td>
				<td>5万</td>
				<td>5万</td>
				<td>150万</td>
			</tr> -->
			<tr>
				<td>交通银行</td>
				<td>5万</td>
				<td>20万</td>
				<td>600万</td>
			</tr>
			<tr>
				<td>工商银行</td>
				<td>5万</td>
				<td>5万</td>
				<td>150万</td>
			</tr>
			<tr>
				<td>邮储银行</td>
				<td>5000</td>
				<td>5000</td>
				<td>15万</td>
			</tr>
			<tr>
				<td>广发银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
			<tr>
				<td>民生银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
			<tr>
				<td>平安银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
			<tr>
				<td>招商银行</td>
				<td>1万</td>
				<td>1万</td>
				<td>30万</td>
			</tr>
			<tr>
				<td>中国银行</td>
				<td>5万</td>
				<td>20万</td>
				<td>600万</td>
			</tr>
			<tr>
				<td>建设银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
			<tr>
				<td>光大银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
			<tr>
				<td>兴业银行</td>
				<td>5万</td>
				<td>5万</td>
				<td>150万</td>
			</tr>
			<tr>
				<td>中信银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
			<tr>
				<td>华夏银行</td>
				<td>50万</td>
				<td>50万</td>
				<td>1500万</td>
			</tr>
		</table>
		<p class="textL lineHeight1_5x font14">注1:商户限额,用户银行卡本身限额,认证支付标准限额,三者取最低限额。限额表仅供参考,实际以支付界面提示为准。</p>
		<p class="MT10 textL lineHeight1_5x font14">注2:如单日有大额充值需求，可以登录联璧官网www.lianbijr.com通过网银充值</p>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
</body>
</html>