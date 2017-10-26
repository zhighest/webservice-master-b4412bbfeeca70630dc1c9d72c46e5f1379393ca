$("#productTab1").click(function(event) {
	productTab1Active()
});
$("#productTab2").click(function(event) {
	productTab2Active();
});
function productTab2Active() {
    $("#productTab2").addClass("current2");
    $("#productTab1").removeClass("current1");
    $(".demand").stop().hide();
    $(".fix").stop().show();
    if (setProductListInfoCheck) {
        ajaxRequest(contextPath + "wxproduct/productListInfo", "",setProductListInfo);
    };
}
//用户获取收益率查询接口
function productTab1Active() {
    $("#productTab1").addClass("current1");
    $("#productTab2").removeClass("current2");
    $(".fix").stop().hide();
    $(".demand").stop().show();
    if ($("#userIdFlag").val() == 'Y') {
        if (setTotalRiseListCheck) {
            ajaxRequest(contextPath + "wxtrade/getYield", "", setTotalRiseList,'GET');
        }; 
    };
}
//活期购买人列表入口wxproduct/goCurrentInvestList
var goCurrentInvestList = function(loanId) {
    var temp = document.createElement("form");
    temp.action = "goCurrentInvestList";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
//定期购买人信息入口
var goDemandInvestorList = function(loanId) {
    var temp = document.createElement("form");
    temp.action = "goDemandInvestorList";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "productType";
    inp2.value = "108";
    temp.appendChild(inp2);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var setProductListInfoCheck = true;
var setProductListInfo = function(data){
    if(data.list == null || data.list == ''){

    }else{
        setProductListInfoCheck = false;
        var html = '';
        $.each(data.list, function(i, jsonObj) {
            html += '<li>';
            html += '<div class="clearfix PLR5P">';
            html += '<h4 class="fl blackTex1 PTB10 textL">'+ jsonObj.productName +'</h4>';
            html += '<div onclick="goDemandInvestorList(' + jsonObj.scatteredLoanId + ')" class="fr grayTex font14 PTB10 PR20 textL positionR">已有<span class="redTex">'+ jsonObj.investUserNum +'</span>人投资<img class="positionA rightArrows" src="../pic/weixin/rightArrows.png" width="16px" /></div>';
            html += '</div>';
            html += '<div class="borderTBLight PLR5P PTB10 clearfix positionR">';
            html += '<div class="fl PTB20 width30">';
            html += '<div class="redTex lineHeight100 font40 textL strong helveticaneue perTop01"><span>'+ jsonObj.annualRate +'</span>';
            html += '<font class="font18">%</font></div>';
            html += '<p class="grayTex MT5 ML2 lineHeight100 font12 textL">预期年化收益率</p>';
            if(jsonObj.adjustRate != "0") {
	        	html += '<p class="grayTex1 MT5 lineHeight100 font12 textL">专享加息<span class="redTex font16">'+ jsonObj.adjustRate +'%</span></p>';
            }
            html += '</div>';
            html += '<div class="fr width50">';
            html += '<p class="grayTex2 textL font14 lineHeight100">'+ numFormatInteger(jsonObj.investMinAmount) + '元起投</p>';
            if(jsonObj.remanDays == "0") { 
	        	html += '<p class="grayTex2 textL MT10 font14 lineHeight100">理财期限'+jsonObj.remanPeriods+'个月</p>';    
            }else {
	           html += '<p class="grayTex2 textL MT10 font14 lineHeight100">理财期限'+jsonObj.remanDays+'天</p>';  
            }
            // html += '<a class="redBkg whiteTex MT10 block PTB10 radius5" id="goProductBuy">立即投资</a>';
            if(jsonObj.finishedRatio != "100") {
	        	html += "<a onclick=\"goProductDetails('" + jsonObj.loanId + "','" + jsonObj.planId + "','" + jsonObj.scatteredLoanId + "','" + jsonObj.contactAmount + "','"+jsonObj.finishedRatio+"','" + jsonObj.productName + "')\" class=\"redBkg whiteTex MT10 block PTB10 radius3\">立即投资</a>";    
            }else {
	            html += '<a class=\"btn inactiveBtn whiteTex MT10 block PTB10 radius5\">已售罄</a>';
            }
           
            html += '</div>';
            html += '<span class="lineCenter positionA"></span>';
            html += '</div></li>';
        });
        $("#ProductListInfoList").empty();
        $("#ProductListInfoList").append(html);
    }
}
/*var demanDetails = function(data){
    $("#annualizedReturnRate").html(data.annualizedReturnRate);
    $("#accountAmount").html(numFormat(data.accountAmount));
    $("#yesterdayGain").html(numFormat(data.yesterdayGain));
}
$(document).ready(function() {
    ajaxRequest(contextPath + "wxdeposit/showIndex","", demanDetails);
});*/
//立即加息
/*$("#addRates").click(function(event) {
    sessionStorage.setItem("from","demand");
});*/

//折线图

//跳转到已售罄页面
var goSoldOut = function(){
	window.location.href = contextPath + "/wxdeposit/goSoldOut"
}
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
$(".vipHint").click(function(){
    $(".vipHintUl").animate({height: 'toggle', opacity: 'toggle'}, "400");  
});
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
        chart('main',totalRiseDate,totalRise,35,totalRiseDate[totalRiseDate.length - 1],totalRise[totalRise.length - 1],totalRise[totalRise.length - 1]);
        chart('main2',totalRiseDate,incomeAmount,50,totalRiseDate[totalRiseDate.length - 1],incomeAmount[incomeAmount.length - 1],incomeAmount[incomeAmount.length - 1]);
        chart('main3',totalRiseDate,investAmount,60,totalRiseDate[totalRiseDate.length - 1],investAmount[investAmount.length - 1],investAmount[investAmount.length - 1]);
    }
}
var loanId;
$(document).ready(function() { 
    var mobile = $("#mobile").val();
    var channel = $("#channel").val();
    var fixDemandSwitch = $("#fixDemandSwitch").val();
    if(channel == "LBWX"){
        $("#downBkg").stop().show();
    }
	var from = sessionStorage.getItem("from");
	if(from == 'fixIndex' || from == 'purchaseSucceed' || fixDemandSwitch == 'fix') {
		productTab2Active();
		sessionStorage.setItem("from",""); //销毁 from 防止在b页面刷新 依然触发$('#xxx').click()
	}else if(fixDemandSwitch == 'Demand') {
		productTab1Active();
		sessionStorage.setItem("from",""); //销毁 from 防止在b页面刷新 依然触发$('#xxx').click()
	}else{
        productTab1Active();
    }
    var couponsRateRises = $("#couponsRateRises").val();//加息券
    //计算收益率显示
    var adjustRate = $("#adjustRate").val();//专享
    var annualizedReturnRate = $("#annualizedReturnRate").val();//基本利率
    var signInRateRises = $("#signInRateRises").val();//签到利率
    $(".adjustRate").html(adjustRate);
    $(".annualizedReturnRate").html(annualizedReturnRate);
    $(".couponsRateRises").html(couponsRateRises);
    $(".signInRateRises").html(signInRateRises);
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
    //活期确认购买
    ajaxRequest(contextPath + "wxdeposit/showInvestmentListInfo", "", setDemanProductList);
    //消息提醒小红点
    ajaxRequest(contextPath + "tasklist/isCompleteTask", "mobile="+ mobile, setCompleteTask);
    if($("#fixDemandSwitch").val()==="fix"){		
    		$("#title1").html("定期理财");			
		}else{
			$("#title1").html("零钱计划理财");		
		}    
});

var setCurrentNum =function(data){
     $("#alreadyNum").html(data.peoplenum);  
}

//点击活期唯一的立即投资按钮触发事件
var startDay;
var startHour;
var startMin;
var startSec;   
var setDemanProductList = function(data) {
    var jsonObj = data.list[0];
    var loanId = jsonObj.loanId;

    $("#already").attr('onclick','goCurrentInvestList("'+ loanId+'")');     
    if(jsonObj.finishedRatio == "100" && (jsonObj.day == 0 && jsonObj.hour == 0 && jsonObj.min == 0 && jsonObj.sec == 0)){
        $("#goDemandOrderCon").attr('onclick','goSoldOut()');
    }else {
        $("#goDemandOrderCon").attr('onclick','goDemandOrderConfirmation("'+ loanId+' ","'+ jsonObj.remanAmount +'","'+ jsonObj.annualRate +'","'+ jsonObj.investMinAmount +'","'+ jsonObj.id+'","'+jsonObj.finishedRatio+'")');
    }
    //查询今日活期购买人数
    ajaxRequest(contextPath + "/wxproduct/currentInvestNum","mobile="+ mobile+"&loanId="+loanId,setCurrentNum);
}


//传值，将接口里的值传到活期购买页面
var goDemandOrderConfirmation = function(loanId,remanAmount,annualRate,investMinAmount,tagId,finishedRatio) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxproduct/goDemandOrderConfirmation";
    temp.method = "GET";
 
    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "remanAmount";
    inp2.value = remanAmount;
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "annualRate";
    inp3.value = annualRate;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "investMinAmount";
    inp4.value = investMinAmount;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "tagId";
    inp5.value = tagId;
    temp.appendChild(inp5);
	
	var inp6 = document.createElement("input");
    inp6.name = "finishedRatio";
    inp6.value = finishedRatio;
    temp.appendChild(inp6);
    
    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
//传值，将接口里的值传到定期购买页面
var goProductDetails = function(loanId, planId, scatteredLoanId, contactAmount,finishedRatio,productName) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxproduct/goProductBuy";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "planId";
    inp2.value = planId;
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "sloanId";
    inp3.value = scatteredLoanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "contactAmount";
    inp4.value = contactAmount;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "cpType";
    inp5.value = encodeURI(productName);
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "finishedRatio";
    inp6.value = finishedRatio;
    temp.appendChild(inp6);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

//小红点,消息提醒
var setCompleteTask = function(data){
    if (data.rescode == "00000"){
        if (data.isCompleteTask =="0"){
            $("#redDotted").show();
        }
        else if(data.isCompleteTask == "1"){
            $("#redDotted").hide();
        }
    }

    else {//接口不通的情况下，调用报错的方法
        // errorMessage(data.resmsg_cn);
    }
}

