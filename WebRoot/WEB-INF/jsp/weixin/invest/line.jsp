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
	<title>联璧钱包</title>
    <%@  include file="../header.jsp"%>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<div id="main" style="height:400px"></div>
  
    	<script type="text/javascript" src="${pageContext.request.contextPath}/js/weixin/integration/echarts/chart/line.js?<%=request.getAttribute("version")%>"></script>
	<script src="${pageContext.request.contextPath}/js/weixin/integration/echarts/echarts.js?<%=request.getAttribute("version")%>"></script>
	
		<script>
			 // 路径配置
        require.config({
            paths: {
                echarts: contextPath + 'js/weixin/integration/echarts'
            }
        });
        
        // 使用
        require(
            [
                'echarts', 
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main')); 
                  var categories = [];  
                var values = [];  
                ajaxRequest(contextPath +"wxtrade/getYield",'',getYield,"GET");
                function getYield(data){
                	console.log(data)
                	
                }
           var option = {
					    title : {
					        text: '未来一周气温变化',
//					        subtext: '纯属虚构'
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : false,
					            data : ['周一','周二','周三','周四','周五','周六']
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            axisLabel : {
					                formatter: '{value} °C'
					            }
					        }
					    ],
					    series : [
					        {
					            name:'最高气温',
					            type:'line',
					            data:[],
					            markPoint : {
					                data : [
					                    {type : 'max', name: '最大值'},
					                    {type : 'min', name: '最小值'}
					                ]
					            },
					            markLine : {
					                data : [
					                    {type : 'average', name: '平均值'}
					                ]
					            }
					        }
					    ]
					};        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
		</script>
  </body>
 
</html>
