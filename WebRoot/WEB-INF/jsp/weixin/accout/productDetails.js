var goOrderConfirmation = function(loanId, planId, sloanId, earnings, inputAmount,cpType,rateIds,rateRises,voucherAmount,voucherId) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxtrade/goOrderConfirmation";
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
    inp3.value = sloanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "earnings";
    inp4.value = numFormat(earnings);
    temp.appendChild(inp4);

    var inp5 = document.createElement("input");
    inp5.name = "inputAmount";
    inp5.value = inputAmount;
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "cpType";
    inp6.value = cpType;
    temp.appendChild(inp6);
    
    var inp7 = document.createElement("input");
    inp7.name = "rateIds";
    inp7.value = rateIds;
    temp.appendChild(inp7);
    
    var inp8 = document.createElement("input");
    inp8.name = "rateRises";
    inp8.value = rateRises;
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "earningsDay";
    inp9.value = addEarnings;
    temp.appendChild(inp9);

    var inp10 = document.createElement("input");
    inp10.name = "voucherAmount";
    inp10.value = voucherAmount;
    temp.appendChild(inp10);

    var inp11 = document.createElement("input");
    inp11.name = "voucherId";
    inp11.value = voucherId;
    temp.appendChild(inp11);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

var goTopUpcz = function(money) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxtrade/goTopUpcz";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "money";
    inp.value = money;
    temp.appendChild(inp);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

var userId;
var product;
var status;
var btnChange = function(type) {
    if (type == 1) {//是否登录
        $("#amountDiv").hide();
        $("#enterBid").css("width","100%").removeClass("inactiveBtn").html("请先登录");
        $("#enterBid").attr("href",contextPath + "/wxTrigger/getWxCode?actionScope="+$('#goUrl').val());
        
    } else if (type == 2) {//是否实名认证
        $("#amountDiv").hide();
        $("#enterBid").css("width","100%").removeClass("inactiveBtn").html("实名认证");
        $("#enterBid").attr("href", contextPath + "wxtrade/goAuthentication?productId=108");
    }  
    

} 
//查询是否有代金券接口

//是否有代金券
  
//代金券弹框
$("#enterBid").on("click",function(){
	  goOrderConfirmation(loanId,planId,sloanId,earnings,inputAmount,cpType,rateIds,rateRises,voucherAmount,voucherId);  
})
//是否有加息劵，是否使用

//是否有可用加息劵    

//未用过加息券使用加息券弹框

var status;







//有加息券使用加息券


//使用代金券




var rateRises;
var addEarnings;
var substrAmount;

var inputAmount;
var earnings;
var accountBalanceAmount;
var loanId;
var planId;
var sloanId;
var cpType;
var rateIds;

var idcardValidate;
var getIdcardInfo = function(data) {
    if (data.idcardValidate != "Y") {
        idcardValidate = false;
        btnChange(2);
        alertBox("请先实名认证","goIdcardValidate",2);
    } else {
        idcardValidate = true;
    }
}
function goIdcardValidate(){
    window.location.href = contextPath + "wxtrade/goAuthentication?productId=109";
}
var remanAmountNum;
var setRemanAmount = function(data) { //账户余额生成HTML
    var html = '<li>剩余</li><li class="mark">:</li>';
    var remanAmount = numFormat(data);
    remanAmountNum = data;
/*
    for (i = 0; i <= remanAmount.length - 1; i++) {
        if (remanAmount.substr(i, 1) != "." && remanAmount.substr(i, 1) != ",") {
            html += '<li>' + remanAmount.substr(i, 1) + '</li>';
        } else {
            html += '<li class="mark"><font>' + remanAmount.substr(i, 1) + '</font></li>';
        }
    }
*/
    $("#remanAmount").html(remanAmount); //累计收益
}
var earningsAnnualRate; //年化利率
var earningsDay; //预期天数
var investMinAmount;//起投金额
var investPeriod;
var remanDays;
var rateRises;
var annualRate;//基础利率
var adjustRate;//专享利率
var couponsRateRises;//加息利率
var rateRises;
var setOrderFormDetails = function(data) {
    if (data.list[0].remanAmount <= 0) {
        $(".popDiv4").show();
    }
    if(data.list[0].adjustRate != 0){
        $("#adjustRateLi").show(); 
        $("#adjustRate").html(data.list[0].adjustRate);
    }
    if (data.rescode == "00000") {
        var dataInfo = data.list[0];
        adjustRate = dataInfo.adjustRate;
        earningsAnnualRate = dataInfo.annualRate;
        earningsDay = dataInfo.expectedReturnDays;
        investMinAmount = dataInfo.investMinAmount;
        investPeriod = dataInfo.investPeriod;
        remanDays = dataInfo.remanDays;
        setRemanAmount(dataInfo.remanAmount); //剩余金额
        if(remanDays == "0") {
            $("#investPeriod").html(dataInfo.investPeriod + "个月"); //理财期限月   
            remanFlag = false;
        }else {
            $("#investPeriod").html(dataInfo.remanDays + "天"); //理财期限天
            investPeriod = remanDays;
            remanFlag = true;
        }
        $("#annualRate").html(dataInfo.annualRate);//基本利率
        $("#startDate").html(getDate(dataInfo.startDate).Format('yyyy/MM/dd')); //起息日期
        $("#endTime").html(getDate(dataInfo.endTime).Format('yyyy/MM/dd')); //到期时间
        $("#investMinAmount").html(numFormatInteger(dataInfo.investMinAmount)); //起投金额
        $("#repaymentType").html(dataInfo.repaymentType); //还款方式
        $("#contactAmount").html(numFormat(dataInfo.contactAmount)); //借款总额
        $("#adjustRate").html(dataInfo.adjustRate); //专享
        //计算收益率显示
        //专享利率
        if(dataInfo.adjustRate == 0) {
          $(".adjustRateLi").hide();  //专享
        }
        if(rateRises == 0 || rateRises == "" || rateRises == "null") {
          $(".couponsRateRisesLi").hide();  //加息券
        }
        if(parseInt(dataInfo.adjustRate) == "0" && rateRises == 0 && dataInfo.adjustRate == "" && rateRises == "" ) {
            $(".vipHint").hide();
            $("#annualRate").html(dataInfo.annualRate);
        }
        if(rateRises != "" || rateRises != null || rateRises != "0"){
            $(".couponsRateRises").html(rateRises);
            // $("#useRateCuuponsUL").hide();
            $(".vipHint").show();
            $(".annualizedReturnRate").html(dataInfo.annualRate);  
        }
        if(dataInfo.adjustRate != "0" || rateRises != "" || rateRises != null) {
            $(".vipHint").show();
            $(".annualizedReturnRate").html(dataInfo.annualRate);    
        }
        A1= accAdd(dataInfo.adjustRate, rateRises);
        A2= accAdd(A1, dataInfo.annualRate);
        $("#annualRate").html(A2);
        if(A2 == dataInfo.annualRate) {
	        $(".vipHint").hide();
        }

      
        //项目概况、资金保障、担保方介绍接口
        ajaxRequest(contextPath + "wxtrade/getBorrowerCompanyInfo", "sloanId=" + sloanId + "&investType=0", setBorrowerCompanyInfo);
//         goMyRateCoupons();//判断是否有加息券
    }
};



var setBorrowerCompanyInfo = function(data) {
    if (data.rescode == "00000") {
        var dataInfo = data.borrowerCompany;
        $("#companyName").html(dataInfo.projectDetails);//项目概况
        $("#fundSecurity").html(dataInfo.fundSecurityNew);//资金保障
        $("#borrowerIntroduction").html(dataInfo.guarantorIntroduction);
    }
};

$(".vipHint").click(function(){
    $(".vipHintUl").animate({height: 'toggle', opacity: 'toggle'}, "400");  
});
var voucherAmount;//代金券金额
var voucherId = $("#voucherId").val();
var remanFlag;//是否是短期标
var investmentAmount = $("#investmentAmount").val();//代金券使用需满足的金额
$(document).ready(function() {
    $("#inputAmount").val("");
    $("#earnings").html("0.00");
    var flag = $("#flag").val();
    loanId = $("#loanId").val();
    sloanId = $("#sloanId").val();
    planId = $("#planId").val();
    cpType = $("#cpType").val();
    rateIds = $("#rateIds").val();
    rateRises = $("#rateRises").val();//加息利率
    couponsRateRises = $("#couponsRateRises").val();
    if(rateRises != 0 && rateRises != "null"){
       
    }else{
        rateRises = 0;
    }
    voucherAmount = $("#voucherAmount").val();
    if(voucherAmount != "null" && voucherAmount != '' && voucherAmount != 0){
        
    }else{
        voucherAmount = 0;
    }
    if (flag != 1) {
        btnChange(1);
    } else {
        ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
    }
    ajaxRequest(contextPath + "wxtrade/getOrderFormDetails", "loanId=" + loanId + "", setOrderFormDetails);
});

//立即加息
$("#addRates").click(function(event) {
    window.location.href = contextPath + "wxcoupons/goMyInterest?product="+investPeriod+'&sloanId='+sloanId+'&loanId='+loanId;   
    sessionStorage.setItem("from","fix");
});
//购买记录
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
$("#buyRecord").click(function(event) {
    goDemandInvestorList(sloanId);
});

