
//第1步 加载页面，调用接口 begin
//定义需要传入接口的变量
var loanId;
var voucherId;//代金券id
var voucherAmount;//代金券的值
var acctBalance;//账户余额
var adjustRate;//专享
var investMinAmount;//起投金额
var annualRate;//当日年化利率
var remanAmount;//可投总金额
var tagId;//标的id
var investmentAmount;//代金券使用需满足的金额
var finishedRatio;  //投资进度
var isRiskEvaluation;//是否进行过风险评估
investmentAmount = $("#investmentAmount").val();
loanId = $("#loanId").val();
voucherId = $("#voucherId").val();
voucherAmount = $("#voucherAmount").val();
acctBalance = $("#acctBalance").val();
adjustRate = $("#adjustRate").val();
investMinAmount=$("#investMinAmount").val();
//页面加载完毕
$(document).ready(function() {
    var mobile=$("#mobile").val();
    ajaxRequest(contextPath + "wxdeposit/showInvestmentListInfo", "", setDemanProductList);
    ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
    ajaxRequest(contextPath + "wxtrade/getRiskEvaluationById", "mobile="+mobile,setRiskEvaluationById,"GET");
});
function setRiskEvaluationById(data){//判断有没有进行过问卷调查
    if(data.rescode=="00000"){
       isRiskEvaluation=data.isRiskEvaluation
    }else{
        // errorMessage(data.resmsg_cn)
    }
    
}
//第1步 加载页面，调用接口 end
var startDay;
var startHour;
var startMin;
var startSec;
var setDemanProductList= function(data){
    //定义两个数组 秒数数组和标的id数组
    var youtime = new Array;
    var jsonObj = data.list[0];
    finishedRatio = jsonObj.finishedRatio;  //投资进度
    var remanAmount = jsonObj.remanAmount;
    $("#remanAmount").html(remanAmount);
    var loanId = jsonObj.loanId;  
    //倒计时开抢
    startDay = jsonObj.day;
    startHour = jsonObj.hour;
    startMin = jsonObj.min;
    startSec = jsonObj.sec;
    youtime.push(startDay*86400000 + startHour*3600000 + startMin*60000 + 1000*startSec);// 每个时间放到数组里
    lxfEndtime(youtime);

    tagId= jsonObj.id;
    annualRate = jsonObj.annualRate;
    $("#annualizedReturnRate").html(annualRate);
    if(adjustRate==null){
        $(".adjustRateLi").hide();
    }
    // 代金券的使用情况的显示 end
    //i标内容的显示隐藏条件和总收益率的计算 begin
/*
    if(parseInt(adjustRate) == "0" || adjustRate == "" || adjustRate == null ) {
        $(".adjustRateLi").hide();
        $(".vipHint").hide();
        $("#annualRateAll").html(annualRate);
    }
    if(adjustRate != "0" || adjustRate != "" || adjustRate != null) {
        $(".adjustRateLi").show();
        $(".vipHint").show();
        $(".annualRateAll").html(annualRate);    
    }
    A= accAdd(adjustRate, annualRate);
    $("#annualRateAll").html(A);
    if(A == annualRate) {
        $(".vipHint").hide();
    }
*/	
	$("#annualRateAll").html(annualRate);
    //资金保障信息获取接口
    ajaxRequest(contextPath + "wxtrade/getBorrowerCompanyInfo", "investType=0"+"&sloanId="+tagId, getCompanyInfo);
}
	  	

//为唯一的标使用倒计时
function lxfEndtime(youtime){
        youtime -= 1000;
        var seconds = youtime/1000;
        var minutes = Math.floor(seconds/60);
        var CSecond= Math.floor(seconds%60);//"%"是取余运算，可以理解为60进一后取余数，然后只要余数。
        setTimeout(function(){
            lxfEndtime(youtime);
        }, 1000);
        if(youtime > 0){
                $("#goDemandOrderCon").addClass("inactiveBtn");
                if(minutes>0){
                    $("#goDemandOrderCon").html("<div class='grayTex'><em>距离开卖还剩</em><span>"+minutes+"</span><em>分</em><span>"+CSecond+"</span><em>秒</em></div>");  
                }else{
                    $("#goDemandOrderCon").html("<div class='grayTex'><em>距离开卖还剩</em><span>"+CSecond+"</span><em>秒</em></div>");
                }
                    
        }else {
                 $("#goDemandOrderCon").removeClass("inactiveBtn");
                $("#goDemandOrderCon").html("立即投资");
                $("#goDemandOrderCon").attr('onclick','goOrderConfirm("'+ loanId +' ","'+ annualRate +'","'+ investMinAmount +'","'+ acctBalance + '","'+ adjustRate +'","","'+ voucherAmount +'","'+ investmentAmount +'","'+ voucherId +'","'+finishedRatio+'")');
        }       
}

//资金保障的内容
var getCompanyInfo = function(data){
    $("#fundSecurity").html(data.borrowerCompany.fundSecurityNew)
}
//传值，将接口里的值传到活期购买页面
var goOrderConfirm = function(loanId,annualRate,investMinAmount,acctBalance,adjustRate,tagId,voucherAmount,investmentAmount,voucherId,finishedRatio) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxtrade/orderConfirm";
    temp.method = "GET";
 
    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp4 = document.createElement("input");
    inp4.name = "annualRate";
    inp4.value = annualRate;
    temp.appendChild(inp4);

    var inp5 = document.createElement("input");
    inp5.name = "investMinAmount";
    inp5.value = investMinAmount;
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "acctBalance";
    inp6.value = acctBalance;
    temp.appendChild(inp6);

    var inp7 = document.createElement("input");
    inp7.name = "adjustRate";
    inp7.value = adjustRate;
    temp.appendChild(inp7);

    var inp8 = document.createElement("input");
    inp8.name = "tagId";
    inp8.value = tagId;
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "voucherAmount";
    inp9.value = voucherAmount;
    temp.appendChild(inp9);

    var inp10 = document.createElement("input");
    inp10.name = "investmentAmount";
    inp10.value = investmentAmount;
    temp.appendChild(inp10);

    var inp11 = document.createElement("input");
    inp11.name = "voucherId";
    inp11.value = voucherId;
    temp.appendChild(inp11);
    
    var inp12 = document.createElement("input");
    inp12.name = "finishedRatio";
    inp12.value = finishedRatio;
    temp.appendChild(inp12);

    document.getElementById("formDiv").appendChild(temp);
    if(isRiskEvaluation=="N"){
      alertRiskEvaluation("您未进行风险承受能力评估，若想继续投资，请进行评估。","goEvaluate",1);
    }else{
       temp.submit(); 
    }
}
function goEvaluate(){
    window.location.href=contextPath + "wxtrade/goRiskEvaluation?channel=LBWX";
}
//若没有进行实名认证则需要实名认证 begin
var idcardValidate;
var getIdcardInfo = function(data) {
    if (data.idcardValidate != "Y") {
        idcardValidate = false;
        alertBox("请先实名认证","goIdcardValidate",2);
    } else {
        idcardValidate = true;
    }
}
function goIdcardValidate(){
    window.location.href = contextPath + "wxtrade/goAuthentication?productId=109";
}

//若没有进行实名认证则需要实名认证 end

//以下为js特效部分
// i标的点击事件，显示与隐藏效果
$(".vipHint").click(function(){
    $(".vipHintUl").animate({height: 'toggle', opacity: 'toggle'}, "400");  
});

function alertRiskEvaluation(tips,method,type){
    var html='';
    html += '<div class="width100 height100P positionF" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">';
    html += '<div class="radius5 whiteBkg width80 positionA textC" style="height:190px;left:10%;top:50%;margin-top: -95px; box-shadow: 0px 0px 3px #666;">';
    html += '<h3 class="font20 PTB15 redTex " style="border-bottom:1px solid #ff5a5a">温馨提示</h3>';
    html += '<p class="font16 blackTex lineHeight1_5x textL" style="height:75px;padding:15px 5% 0;">'+tips+'</p>';
    if(type == 1){
        html += '<a class="inlineB grayBkg width35 ML10 MR10 grayTex PT5 PB5 font16 radius5" onclick="$(\'#alertBox\').remove();">取消</a>';
    }
    html += '<a class="inlineB redBkg width35 ML10 MR10 whiteTex PT5 PB5 font16 radius5" onclick="'+method+'();">去评估</a>';
    html += '</div></div>';
    $("body").append(html);
}