var peopleNum;
var matchAmount;
var curBondAmount;
var yesterdayIncome;
var yesterdayYield;
var returnRate;
var totalAssets;
//页面加载完毕，调用接口
$(document).ready(function() {
	ajaxRequest(contextPath+"wxenjoy/enjoyPlanProductIndex","",setCurrentInvestNum,"GET");//优享计划接口
	
});

var setCurrentInvestNum = function(data){
	$("#expect").removeClass("none");
  if(data.rescode == "00000"){  
      peopleNum = data.todayInvestNum;
      if(data.matchAmount!=null){
      	matchAmount = data.matchAmount;
      }else{
      	matchAmount = "0.00";
      }
      if(data.curBondAmount!=null){
      	curBondAmount = data.curBondAmount;
      }else{
      	curBondAmount = "0.00";
      }
      yesterdayIncome = data.yesterdayIncome;
      yesterdayYield = data.yesterdayYield;
      returnRate = data.returnRate;
      if(data.totalAssets!=null){
      	totalAssets = data.totalAssets;
      }else{
      	totalAssets="0.00";
      }
      var inAmount=data.inAmount,
      	  outAmount=data.outAmount;
      $("#peopleNum").html(peopleNum);
      $("#yesterdayIncome").html(numFormat(yesterdayIncome));
      $("#yesterdayYield").html(data.yesterdayYield);
      if(returnRate == "0"||returnRate == 0||returnRate==""||returnRate==null){
    	  $("#expect").addClass("none");
    	  $("#returnRate").html("暂无收益")
    	  .addClass("font40")
    	  .removeClass("helveticaneue strong")
    	  .siblings("font")
    	  .addClass("none");
      }else{
    	  $("#expect").removeClass("none");
    	  $("#returnRate").html(returnRate)
    	  .removeClass("font40")
    	  .addClass("helveticaneue strong")
    	  .siblings("font")
    	  .removeClass("none");
      }
      
      $("#totalAssets").html(totalAssets);
      $("#curBondAmount").html(numFormat(curBondAmount));
      $("#matchAmount").html(numFormat(matchAmount));
      $("#already").attr('onclick','goCurrentInvestList("")');//点击小尖角调用投资人列表入口的方法
      if(inAmount==""||inAmount==null) inAmount=0;
      if(outAmount==""||outAmount==null) outAmount=0;
      $("#inAmount").html(numFormat(inAmount)+"元");
      $("#outAmount").html(numFormat(outAmount)+"元");
      alertHint();
  }
}
//优享计划投资人列表入口wxInvest/goEnjoyTodayUserInfoList
var goCurrentInvestList = function() {
    var temp = document.createElement("form");
    temp.action = "goEnjoyTodayUserInfoList";//传值页面的地址
    temp.method = "GET";

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

//折线
window.addEventListener("resize", function () {

    option.chart.resize();

});
var chart = function(id,dataX,dataArr,Xnum,xAxisNum,yAxisNum,valueNum){
require.config({
paths: {
echarts: contextPath + 'js/weixin/integration/echarts'
}
});
require(
[
'echarts',
'echarts/chart/line'
],
function (ec) {
var myChart = ec.init(document.getElementById(id));
var option = {
color : ['#ff5a5a'],
tooltip : {
  show:false
},
legend: {
  data:['']
},
calculable : true,
xAxis : [
  {
      type : 'category',
      boundaryGap : true,
      data : dataX,
      splitLine : { //分割线
          lineStyle : {
              color : '#ededed'
          }
      },
      axisLine : {//X坐标轴样式
          lineStyle : {
              color : '#ededed',
              width : '1'
          }
      },
      axisTick : {//X坐标轴隔点
          show : false
      },
      axisLabel : {
    	  interval: 0,
          textStyle :{
              color : '#c1c2c4'
          }
      }
  }
],
yAxis : [
  {
      type : 'value',
      splitLine : { //分割线
          lineStyle : {
              color : '#ededed'
          }
      },
      axisLine : {//Y坐标轴样式
          lineStyle : {
              color : '#ededed',
              width : '0'
          }
      },
      axisTick : {
          lineStyle : {
              color : '#ededed'
          }
      },
      axisLabel : {
          textStyle :{
              color : '#c1c2c4'
          },
          formatter: '{value}'
      },
      splitNumber:5
      /*,
      min : minNum,
      max : maxNum */
  }
],
grid : { //上下左右空白间隔
  borderWidth : 0,
  x : Xnum,
  y : 25,
  x2 :20,
  y2 :40
},
series : [
  {
      name:'近七天年化收益率(%)',
      type:'line',/*
      symbol : 'none',*///折线上的点
      symbolSize: 1,
      data:dataArr,
      markPoint : {
          data : [
              {name: 'today',xAxis: xAxisNum, yAxis: yAxisNum, value: valueNum,symbol:'circle',symbolSize:2,
                  itemStyle : { 
                      normal: {
                          label : {
                              show: true,
                              textStyle : {
                                  color:'#ff5a5a',
                                  fontSize : '12'
                              },
                          position:'top'
                          }
                      }
                  }
              }
          ]
      } 
  } /*, 
      markLine : {
          data : [
              {type : 'average', name: '平均值'}
          ]
      }*/
]
};

myChart.setOption(option);
}
);
}
//折线轮播图
var setBannerList = function() {
    var bannerHtml = '';
    $("#bannerList figure").each(function(i, jsonObj) {
     if(i == 0){   
        bannerHtml += '<li class="on"></li>';
    }else{
        bannerHtml += '<li></li>';
    }
});
    $("#position").html(bannerHtml);
    var slider =
    Swipe(document.getElementById('slider'), {
        auto: 5000,
        continuous: true,
        callback: function(pos) {
            
          var i = bullets.length;
          while (i--) {
            bullets[i].className = ' ';
        }
        bullets[pos].className = 'on';
        
    }
});
    var bullets = document.getElementById('position').getElementsByTagName('li');
};

var setTotalRiseListCheck = true;
var setTotalRiseList = function(data){
if(data.list == null || data.list == ''){
	
	
}else{
	 setTotalRiseListCheck = false;
	 $("#slider").show();
var totalRiseDate = [];
var totalRise = [];
var incomeAmount = [];
var investAmount = [];
$.each(data.list, function(i, jsonObj) {
var totalRiseDateSplit = jsonObj.incomeDay.split('-');
var totalRiseDatePush = totalRiseDateSplit[1]+'/'+totalRiseDateSplit[2];
totalRiseDate.push(totalRiseDatePush);
totalRise.push(parseFloat(jsonObj.totalRise));
incomeAmount.push(parseFloat(jsonObj.incomeAmount));
investAmount.push(parseFloat(jsonObj.investAmount));
});
setBannerList();
chart('main',totalRiseDate,totalRise,35,totalRiseDate[totalRiseDate.length - 1],totalRise[totalRise.length - 1],totalRise[totalRise.length - 1]);
chart('main2',totalRiseDate,incomeAmount,50,totalRiseDate[totalRiseDate.length - 1],incomeAmount[incomeAmount.length - 1],incomeAmount[incomeAmount.length - 1]);

}
}
function goturnout(){
	if(totalAssets<="0.00"){
		errorMessage("当前无可转出金额")
	}else{
		location.href=contextPath+"wxInvest/getRolloutIndex"
	}
}
$(document).ready(function() {
	
	if(setTotalRiseListCheck){
		{ajaxRequest(contextPath + "wxenjoy/getYield", "", setTotalRiseList,'GET')};	
	}

});
//转出弹框
function alertHint(){
    var index=0;
  	$("#icon").on("click",function(){
  		if(index==0){
  			$("#msg").removeClass("opacity0");
  			index=1;
  		}
  		else if(index==1){
  			$("#msg").addClass("opacity0");
  			index=0;
  		}
  	})
  	var oImg=document.getElementById("iconImg"),
  	oMsg=document.getElementById("msg"),
  	top=oImg.offsetTop+12+"px",
  	width=oMsg.offsetWidth,
  	width2=oImg.offsetWidth,
  	left=oImg.offsetLeft-width+width2*3+"px";
  	width=width-width2*3+2+"px";
  	$("#msg").css({"top":top,"left":left});
  	$("#figure").css({"top":top,"left":left});
  	$("#figure").css({"transform":"translateX("+width+")","-webkit-transform":"translateX("+width+")"});
}
