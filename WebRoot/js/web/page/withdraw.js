
window.onload = function(){ 
    $("#passwordCash").val("");
    $("#extractMoney").val("");
}
var povince = $("#povince").text();
var cityCode;
//循环出省列表
var getPovince = function(data){
    cities = new Object(); 
    $.each(data.provinceList , function(i,jsonObj) {
        var povinceList = "<li class='fl width20 textC cursor' myVal='"+jsonObj.provinceCode+"'>"+jsonObj.provinceName+"</li>";
        $("#povinceList").append(povinceList);
        cities[jsonObj.provinceCode] = [];  
        $.each(jsonObj.cityList,function(i,cityObj){
            cities[jsonObj.provinceCode].push(cityObj.cityName +"+"+ cityObj.cityCode) ;
        });    
    });
    //选择省份生成城市
    $("#povinceList li").click(function(event) {
        $("#povinceList").stop().hide();
        $("#clickPovince").removeClass("active");
        povince = $(this).attr("myVal");
        $("#povince").text($(this).text());
        var city = cities[povince];
        $("#cityList").empty();
        if(povince == '请选择省'){
            $("#city").text("请选择市");
        }else{
            for(var i=0;i < city.length;i++){
                var citySplit = city[i].split('+');
                var cityList = '<li class="fl width33_3 textC cursor" onclick="getCity($(this))" myVal="'+citySplit[1]+'">'+citySplit[0]+'</li>';
                $("#cityList").append(cityList);
            };
            cityCode = $("#city").text();
        };
    });
};
//获取城市值
var getCity = function(a){
    cityCode = a.attr("myVal");
    $("#city").text(a.text());
    $("#cityList").stop().hide();
    $("#clickCity").removeClass("active");
}
//点击选择省份
$("#clickPovince").click(function(event) {
    $("#povinceList").stop().show();
    $("#cityList").stop().hide();
    $("#clickCity").removeClass("active");
    $(this).addClass("active");
});
//点击选择城市
$("#clickCity").click(function(event) {
    if(povince == '请选择省份'){
        errorMessage("请先选择省份！");
    }else{
        $("#cityList").stop().show();
        $(this).addClass("active");
        $("#povinceList").stop().hide();
        $("#clickPovince").removeClass("active");
    }
});
//判断是否显示选择城市
var withdrawNeedProvinceAndCity = function(data){
    if (data.needProvinceAndCity == true) {
        $("#cityInfo").stop().show();
        ajaxRequest(contextPath + "wxabout/getProvinceAndCity", "", getPovince,"GET");
    }else if(data.needProvinceAndCity == false){
        $("#cityInfo").stop().hide();
        povince = "";
        cityCode = "";
    }
}
//获取银行卡信息
var systemQuickAmount;
var getRecePayBank = function(data) {
    var systemQuickAmount = data.systemQuickAmount;
    if(data <= 0){
        $("#withdrawBtn").addClass("inactiveBtn");
    }
    $("#insideInputBtn").click(function(event) {
        $("#extractMoney").val(systemQuickAmount);  
    });
    $("#extractMoney").attr("oninput", "$.checkLimit($(this)," + systemQuickAmount + ",false);").attr("onkeyup", "$.checkLimit($(this)," + systemQuickAmount + ",false);").attr("onafterpaste", "$.checkLimit($(this)," + systemQuickAmount + ",false);");
    if (data.bankId != "" && data.bankId != null) {
        $("#hasCard").show();
        $("#quickAmountDesc").html(data.quickAmountDesc);
        $("#quickWithdrawTip").html(data.quickWithdrawTip);
        $("#bankIcon").attr("src", contextPath + "" + data.picturePath);//银行图标
        $("#bank_name").html(data.bankName);//银行名
        var cardNumber = data.cardNumber.toString();
        $("#cardNumber").html(cardNumber.substr(cardNumber.length-4));//银行卡号后四位
//         $("#limitMoney").html("每笔"+data.singleLimit+"元,每日"+data.singleDayLimit+"元,每月"+data.singleMonthLimit+"元");
        $("#cardNo").val(numHide(data.cardNumber));//银行卡号
        $("#cardId").val(data.id);
        ajaxRequest(contextPath + "wxabout/withdrawNeedProvinceAndCity", "", withdrawNeedProvinceAndCity,"GET");
    } else {
        $("#noCard").show();
    }
};
var reload = function(){
    window.location.reload();
};

var withdrawCashFinish = function(data) {
    var rescode = data.rescode;
    var resmsg_cn = data.resmsg_cn;
    if (rescode != "00000") {
        if(data.errorFlag == "1"&&rescode == "00003"){
            errorMessage(resmsg_cn);
            setTimeout(function(){
                window.location.reload();
            },1000); 
        }
        else if(data.errorMessage.message_content!=""){             
            if(data.errorMessage.message_level=='1'){
                errorMessage(data.errorMessage.message_content);
                setTimeout(function(){
                    window.location.reload();
                },1000);
            }else if(data.errorMessage.message_level=='2'){
                alertBox(data.errorMessage.message_content,"remove00",2);               
            }
        }
        else{
            errorMessage("提现失败！" + resmsg_cn);
            setTimeout(function(){
                window.location.reload();
            },1000);
        }
    } else {
        errorMessage("提现成功！");
        setTimeout(reload, 3000);
    }
};
var passwordCash;
var passwordCashCheckFinish = function(data) {
    var isCorrect = data.isCorrect;
    if (!isCorrect) {
        errorMessage("提现失败！交易密码错误！");
    } else {
        var extractMoney = $("#extractMoney").val();
        var cardNumber = $("#cardNo").val();
        var cardId = $("#cardId").val();
        var token = $("#token").val();
        oneClick = false;
        ajaxRequest(contextPath + "wxtrade/withdrawDeposit", {
            "extractMoney": extractMoney,
            "cardNumber": cardNumber,
            "cardId": cardId,
            "province": povince,
            "city": cityCode,
            "token": token,
            "withdrawFlag": "ZSHTPAY"
        },
        withdrawCashFinish);
    }
};
var withdrawCash2 = function(){
    // 手机号
    /*
var mobile = $("#mobile").val();
    ajaxRequest(contextPath + "wxtrade/checkPasswordCash", {
        "passwordCash": passwordCash,
        "phoneNum": mobile
    },
    passwordCashCheckFinish);
*/
    var extractMoney = $("#extractMoney").val();
    var cardNumber = $("#cardNo").val();
    var cardId = $("#cardId").val();
    var token = $("#token").val();
    oneClick = false;
    ajaxRequest(contextPath + "wxtrade/withdrawDeposit", {
        "extractMoney": extractMoney,
        "cardNumber": cardNumber,
        "cardId": cardId,
        "province": povince,
        "city": cityCode,
        "token": token,
        "withdrawFlag": "ZSHTPAY",
        "passwordCash": passwordCash
    },
    withdrawCashFinish);

}


var withdrawCash = function() {
    var extractMoney = $("#extractMoney").val();
    passwordCash = $("#passwordCash").val()
    if(povince == "请选择省份"){
        errorMessage("请选择城市！");
        return false;
    }
    if(systemQuickAmount <= 0){
        errorMessage("可提现金额不足！");
        return false;
    }
    if (extractMoney == null || extractMoney == "") {
        errorMessage("请输入提现金额！");
        return false;
    }
    if(extractMoney == 0){
        errorMessage("输入的金额不能为0");
        return false;
    }
    if(passwordCash == "" || passwordCash == null){
        errorMessage("提现密码为空！请重新输入！");
        return false;
    }
    ajaxRequest(contextPath + "wxtrade/queryFinalWithdrawlInfo", "withdrawFlag=ZSHTPAY&extractMoney="+extractMoney, queryNextWorkingDay2,"GET");//掌上汇通获取工作日
};
//快速提现回调
var queryNextWorkingDay2 = function(data){ 
    if(data.rescode == 00000){
        withdrawCash2();
    }else{
        errorMessage(data.resmsg_cn);
    }
}
//提现点击事件
var oneClick = true;
$("#withdrawBtn").click(function() {
    if(oneClick){
        withdrawCash();
    }
});
$(document).ready(function() {
    ajaxRequest(contextPath + "trade/getRecePayBank", "", getRecePayBank);
    ajaxRequest(contextPath + "wxtrade/accountBalance", "", accountBalance);
});