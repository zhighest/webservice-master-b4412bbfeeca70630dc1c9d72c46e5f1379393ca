<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html class="html">
  <head>
    
   <title>关于我们-联璧金融</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="no" />
	<meta name="apple-touch-fullscreen" content="yes" />
	<meta name="format-detection" content="telephone=no"/>
	<meta name="description" content="联璧金融，诞生于科技基因强大的“上海联璧电子科技有限公司”，由独立的专业金融产品团队组建运营，依托联璧科技的移动通信及大数据技术，让金融，惠民生"/>
	<meta name="keywords" content="关于联璧金融,联璧金融简介, 联璧金融介绍"/>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/main.css?<%=request.getAttribute("version")%>"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/web/aboutUs.css?<%=request.getAttribute("version")%>"/>

  </head>
  
  <body>
    <div id="wrap"> 
  		<div id="main">
	  		<c:set var="pageIndex" value="6"/>
  			<%@  include file="../header.jsp"%>
  			<div class="contentWrapper">
				<!--次级导航-->
				<div class="wrapper">
					<div class="clearfix PTB30"> 
						<c:set var="subnav" value="2"/>
						<%@  include file="menu.jsp"%>
						<ul class="fr inlineBUl rightGuild font12 MT7">
							<li>当前位置：<a href="${pageContext.request.contextPath}/webabout/aboutUs" class="blackTex" rel="nofollow">关于我们<span class="MLR5">/</span></a></li>
							<li class="redTex">联璧金融</li>
						</ul>
					</div>
				</div>
				<div class="aboutUsBanner banner2">
					<div class="wrapper">
						<div class="MT180">
							<p class="subTitle whiteTex show1">金融平台简介</p>
							<p class="subDes whiteTex show5">Financial platform introduction</p>	
						</div>
					</div>
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT20 PB20 clearfix positionR">
						<div class="fl qrcodeDesL positionR clearfix"><img src="${pageContext.request.contextPath}/pic/web/aboutUs/qrcode1.png" width="90%"></div>
						<div class="lineHeight2x font14 grayTex61T fr qrcodeDesR MT20">
							<p>联璧金融，诞生于科技基因强大的“上海联璧电子科技有限公司”，由独立的专业金融产品团队组建运营，依托联璧科技的移动通信及大数据技术，让金融，惠民生。</p>
							<p>联璧金融拥有互联网金融投资平台（www.lianbijr.com）以及手机APP客户端“联璧金融”以及微信公众号“联璧钱包”，专业创新型的互联网金融企业，致力于为互联网理财人群实现专业、互动、高收益的投资专区服务和资金运行提供稳定、持续的安全保障。联璧金融，致力于帮助互联网人群的小额富余资金“联”起来，实现财富增值，致力于小金融，稳稳赚。</p>
						</div>
						
					</div>
				</div>
				<div class="wrapper MB30">
					<p class="subTitle grayTex61 MT40">平台宗旨</p>
					<p class="subDes grayTex61T">Platform objectives</p>
					<div class="MT20 lineHeight2x grayTex61T ML15 font14">
						<p>自省自律，明确平台性质，绝不碰触监管红线；</p>
						<p>合法合规，注册资本金充足，管理机制完善，定期内审；</p>
						<p>资产保障，风控体系严谨实际，交易对手优质稳健，资产保障多重多样；</p>
						<p>资金安全，账户安全，交易安全，清算明晰，结算及时，实时监控；</p>
					</div>	
				</div>
				<div class="whiteBkg">
					<div class="wrapper PT20 PB20">
						<p class="subTitle grayTex61 MT20">强合规<span class="MLR5">|</span>强风控</p>
						<p class="subDes grayTex61T">Extremely compliance<span class="MLR5">|</span>Excellent risk control</p>
						<ul class="desUl font12 grayTex61T lineHeight2x">
							<li>资产来源特定，有行业属性，债务用户本身有良好的银行资信，良好的商业信誉，并企业固定资产及可变现资产充足可用于抵质押还款。</li>
							<li>联璧的资产风控：甄选优质交易对手，对交易资产进行精细化尽调，由独立第三方会计师事务所对财务报表进行审查审计。设立多级还款保障规则，以及充足的抵质押物附件审查。资产方以现金等价物承诺回购，或第三方担保公司承诺担保。</li>
							<li>联璧独立的资金监控：对投资人资金进行严格监管，设立资金监管中心，与风控联动，反映资金流向，信息透明，交易透明，资金流有完整的追踪及检索程序，实时可查，统计报表可视，实现实时的资金监控。确保资金流向真实透明，平台不涉足资金的运作，使用。</li>
							<li>联璧法律及财务合规：已与亚太排名前三位的律师事务所、及全球四大会计师事务所之一签约，进行交易对手实地尽调、交易相关材料的审定及合同审核，以及交易对手报表审计；</li>
							<li>闭环消费场景内信贷：目前正在探索在自有O2O体系电商平台消费环境内基于指定目的地消费产品发放个人消费分期，并基于O2O体系内留存优质商户发放消费场景下的小型供应链信贷。在同行业水平下，优化运营成本，做到真正低息贷，惠商贷，惠民贷。实现普惠生活，普惠商业金融。</li>
						</ul>
					</div>
				</div>
				<div class="overviewBanner positionR PTB20 clearfix">
					<div class="wrapper clearfix positionR">
						<p class="subTitle whiteTex MT20">强技术<span class="MLR5">|</span>强运营</p>
						<p class="subDes whiteTex opacity60">Advanced technology<span class="MLR5">|</span>Efficient operations</p>
						<ul class="desUl font12 positionR clearfix whiteTex opacity60 lineHeight2x">
							<li>银行与第三方支付对平台资金账户进行托管，平台不获取资金权限，不设资金池；已完成合作：中信银行、连连支付、通联支付。基于银行的直连划付与支付机构的账户托管，对互联网投资人资金进行隔离管理，与中信银行的直连合作，更将为平台资金的进一步机构化存管建立基础。</li>
							<li>整体平台系统架构采用国际信息安全标准设计，突破金融架构局限，采用SOA扩展性架构，实现底层数据与上层业务的服务化配置调用，保障信息的规范性、系统扩充的容量弹性，实现系统级高可用，高可靠。业务层互联网数据安全经KEENTEAM进行漏洞检测，内容层防护，强力保护数据及信息安全；</li>
							<li>实施电子化完善严谨的风控体系及资金管理，并保证监控的实时性，做到风险可预测、可预判、可预防；</li>
							<li>银行级别的完善灾备体系、应急管理体系，以及定期的应急演练，保障信息化基础设施及金融服务的可持续交付；</li>
							<li>业界先进的DeVops运营理念应用于实践，从业务架构至系统架构至运维架构力求统一无缝，以强大的运营实践保证互联网服务的优质，并以强大的运营实践融合第三方系统及业务与联璧金融实现快速对接，达到无壁垒，无边界的运营操作与互动，在保证业务安全的同时实现业务领先。</li>
						</ul>
					</div>	
				</div>
  			</div>
		</div>
	</div>
	<%@  include file="../baiduStatistics.jsp"%>
	<%@  include file="../footer.jsp"%>
  </body>
</html>
