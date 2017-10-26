
//计算收益率显示
var couponsRateRises = $("#couponsRateRises").val();//加息券
var adjustRate = $("#adjustRate").val();//专享
var annualizedReturnRate = $("#annualizedReturnRate").val();//基本利率
var signInRateRises = $("#signInRateRises").val();//签到利率
$(".adjustRate").html(adjustRate+"%");
$(".annualizedReturnRate").html(annualizedReturnRate+"%");
$(".couponsRateRises").html(couponsRateRises+"%");
$(".signInRateRises").html(signInRateRises+"%");
if(adjustRate == 0) {
  $(".adjustRateLi").hide();  
}
if(couponsRateRises == 0) {
  $(".couponsRateRisesLi").hide();  
}
if(signInRateRises == 0) {
   $(".signInRateRisesLi").hide();   
}
if(couponsRateRises == 0 && adjustRate == 0 && signInRateRises == 0) {
    $(".vipHint").hide();
    $("#rateNum").html(annualizedReturnRate);
}
if(adjustRate != "0" || couponsRateRises != "0" || signInRateRises !="0") {
	$(".vipHint").show(); 
}
A1= accAdd(adjustRate, annualizedReturnRate);
A2= accAdd(A1, couponsRateRises);
A3= accAdd(A2, signInRateRises);
$("#rateNum").html(A3);
$(".vipHint").click(function(){
    $(".vipHintUl").animate({height: 'toggle', opacity: 'toggle'}, "400");  
});
//初始化折线图的插件
var chart = function(id,dataX,dataArr,Xnum,xAxisNum,yAxisNum,valueNum){
    require.config({
        paths: {
            echarts: contextPath + 'js/weixin/integration/echarts'
        }
    });
    require(
        [
            'echarts',
            'echarts/chart/line'//图表类型：折线图
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
                                color : '#d5d5d5'
                            }
                        },
                        axisLine : {//X坐标轴样式
                            lineStyle : {
                                color : '#d5d5d5',
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
                                color : '#d5d5d5'
                            }
                        },
                        axisLine : {//Y坐标轴样式
                            lineStyle : {
                                color : '#d5d5d5',
                                width : '0'
                            }
                        },
                        axisTick : {
                            lineStyle : {
                                color : '#d5d5d5'
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
                    } 
                ]
            };
                
            myChart.setOption(option);
        }
    );
}
//折线轮播图 begin
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
    var bullets = $("#position li");
};

//近七天收益率
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
        chart('main1',totalRiseDate,totalRise,35,totalRiseDate[totalRiseDate.length - 1],totalRise[totalRise.length - 1],totalRise[totalRise.length - 1]);
        chart('main2',totalRiseDate,incomeAmount,50,totalRiseDate[totalRiseDate.length - 1],incomeAmount[incomeAmount.length - 1],incomeAmount[incomeAmount.length - 1]);
        chart('main3',totalRiseDate,investAmount,60,totalRiseDate[totalRiseDate.length - 1],investAmount[investAmount.length - 1],investAmount[investAmount.length - 1]);
    }
}
//用户获取收益率查询接口
if ($("#userIdFlag").val() == 'Y') {//根据是否登录
    if (setTotalRiseListCheck) {
        ajaxRequest(contextPath + "webindex/getYield", "", setTotalRiseList,'GET');
    }; 
};
//折线轮播图 end

//历史收益展示
$(document).ready(function() {
    getHistoryAmount(1);
}); 
var getHistoryAmount = function(page) {
    ajaxRequest(contextPath + "webindex/historyAmount", "current="+page +"&pageSize=5", setHistoryAmount,'POST');
}
var setHistoryAmount = function(data){
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        $("#fundFlowList").removeClass('borderB');
        html += '<dd class="heigh50 textC grayTex">无记录</dd>'
    } else {
        $("#fundFlowList").addClass('borderB');
        $.each(data.list,
        function(i, jsonObj) {
            html += '<dd class="textC heigh50">';
            html += '<span class="fl width20">'+ jsonObj.incomeDay +'</span>';
            var totalRise = jsonObj.totalRise;
            var totalRised = Math.abs(totalRise);
            if(totalRised==0){
                html += '<span class="fl width20">'+ totalRised +'</span>';
            }else if(totalRise>0){
                html += '<span class="fl width20">'+ totalRised +'%</span>';
            }
            var addrateRise = jsonObj.addrateRise;
            var addrateRised = Math.abs(addrateRise);
            if(addrateRised==0){
                html += '<span class="fl width20">'+ addrateRised +'</span>';
            }else if(addrateRised>0){
                html += '<span class="fl width20">'+ addrateRised +'%</span>';
            }
            html += '<span class="fl width20">'+ jsonObj.investAmount +'</span>';
            html += '<span class="fl width20">'+ jsonObj.incomeAmount +'</span>';
            html += '</dd>';
        });
    }
    $("#fundFlowList").append(html);
    $("#historyAmountListPaging").html(pagingMobile(data, "getHistoryAmount"));
}