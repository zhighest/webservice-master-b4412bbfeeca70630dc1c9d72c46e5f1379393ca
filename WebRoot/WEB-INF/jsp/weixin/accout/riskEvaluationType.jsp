	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<%@ page contentType="text/html;charset=UTF-8"%>
		<%@ include file="../../common.jsp"%>
		<!DOCTYPE HTML>
		<html>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="no" />
		<meta name="apple-touch-fullscreen" content="yes" />
		<meta name="format-detection" content="telephone=no"/>
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>风险承受能力评估</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/weixin/main.css?<%=request.getAttribute("version")%>"/>
		<%@  include file="../header.jsp"%>
		</head>
		<body>

		<div class="PT20" id="description1">
		<ul class="textL PLR5P font14">
		<li class="PTB15">评分标准：</li>
		<li class="PTB15"><span class="PR10">A 1分</span> <span  class="PR10">B 2分</span><span  class="PR10">C 3分</span> <span class="PR10">D 4分</span> E 5分</li>
		<li class="PTB15">保守型 10分-15分</li>
		<li class="PTB15">您的风险承担能力水平比较低，您关注资产的安全性远超于资产的收益性，所以低风险、高流动性的投资品种比较适合您，这类投资的收益相对偏低。</li>
		<li class="PTB15">稳健型 16分-20分</li>
		<li class="PTB15">您有比较有限的风险承受能力，对投资收益比较敏感，期望通过短期、持续、渐进的投资获得高于定期存款的回报。所以较低等级风险的产品如保本保息的固定收益类，比较适合您，适当回避风险的同时保证收益，跑赢通胀。</li>
		<li class="PTB15">平衡型 21分-30分</li>
		<li class="PTB15">您有一定的风险承受能力，对投资收益比较敏感，期望通过长期且持续的投资获得高于平均水平的回报，通常更注重十年甚至更长期限内的平均收益。所以中等风险收益的投资品种比较适合您，回避风险的同时有一定的收益保证。</li>
		<li class="PTB15">积极型 31分-40分</li>
		<li class="PTB15">您有中高的风险承受能力，愿意承担可预见的投资风险去获取更多的收益，一般倾向于进行中短期投资。所以中高等级的风险收益投资品种比较适合您，以一定的可预见风险换取超额收益。</li>
		<li class="PTB15">激进型 41分-50分</li>
		<li class="PTB15">您有较高的风险承受能力，是富有冒险精神的积极型选手。在投资收益波动的情况下， 仍然保持积极进取的投资理念。短期内投资收益的下跌被您视为加注投资的利好机会。您适合从事灵活、风险与报酬都比较高的投资，不过要注意不要因一时的高报酬获利而将全部资 金投入高风险操作，务必做好风险管理与资金调配工作。</li>
		</ul>
		</div>
		<%@  include file="../baiduStatistics.jsp"%>
		</body>
		<input type="hidden" name="riskType" id="riskType" value="${riskType}" >
		<input type="hidden" name="channel" id="channel" value="${channel}" >
		<input type="hidden" name="flag" id="flag" value="${flag}">
		<input type="hidden" name="userId" id="userId" value="${userId}">
		<input type="hidden" name="mobile" id="mobile" value="${mobile}">
		<%--<script src="${pageContext.request.contextPath}/js/weixin/page/riskEvaluationResult.js?<%=request.getAttribute("version")%>"></script>--%>
		</html>