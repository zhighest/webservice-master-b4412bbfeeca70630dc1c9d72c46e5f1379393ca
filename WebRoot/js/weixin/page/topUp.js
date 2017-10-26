//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
		}
	});
}
getServiceCall();



var getRecePayBank = function(data) {
    if (data.bankId != "" && data.bankId != null) {
        $("#bankIcon").attr("src", contextPath + "" + data.picturePath);
        $("#hasCard").show();
        $("#noHasCard").hide();
        $("#bank_name").html(data.bankName);
        var cardNumber = data.cardNumber.toString();
        $("#cardNumber").html(cardNumber.substr(cardNumber.length-4));
        $("#limitMoney").html("每笔"+data.singleLimit+"元,每日"+data.singleDayLimit+"元");
    } else {
        $("#noHasCard").show();
        $("#hasCard").hide();
    }
};
var accountBalanceAmount;
var accountBalance = function(data) {
    accountBalanceAmount = data;
    afterTheTopUpAmount();
    $("#afterTheTopUpAmount").html(numFormat(accountBalanceAmount));
};
var topUpFinish = function(data) {
    if (data.msg != "success") {
        errorMessage(data.msg);
    } else {
        $("#req_data").val(data.payInfo);
        $("#llpayForm").submit();
    }
};

var queryBankCardFinish = function(data){
    if (data.msg != "success") {
            errorMessage(data.msg);
    }else{
        var topUpFalg = data.topUpFlag;
         $("#bankCode").val(data.bankCode);
         $("#bankName").val(data.bankName);
         $("#cardNo").val(data.cardNo);
         
         $("#idCard").val(data.idCard);
         $("#identityName").val(data.identityName);
         $("#depositDockUserId").val(data.depositDockUserId);
         $("#utmSource").val(data.utmSource);
         $("#regTime").val(data.regTime);
         
         
         if(topUpFalg == "LLPAY"){
             topUpFunction();
         }else{
             //页面跳转
//           $("#zshtPayForm").submit();
            //弹出弹出框
            $(".alertBg").show();
         }
    } 
};
//银行卡输入框限制
//.
$("#cardNo").attr("oninput", "$.checkLimit($(this),'',true)").attr("onkeyup", "$.checkLimit($(this),'',true)").attr("onafterpaste", "$.checkLimit($(this),'',true)");


function queryBankCard(){
    var moneyOrder = $("#moneyOrder").val();
    if(moneyOrder == "" || moneyOrder == null){
        errorMessage("请输入充值金额");
        return false;
    }  
    if(moneyOrder == 0){
        errorMessage("输入的金额不能为0");
        return false;
    }
    var cardNo = $("#cardNo").val();
    ajaxRequest(contextPath + "wxtrade/queryBankCard",{"cardNo": cardNo},queryBankCardFinish,"POST");
}
function topUpFunction() {
    var cardNo = $("#cardNo").val();
    var moneyOrder = $("#moneyOrder").val();
    var userId = $("#userId").val();
    var userCardInfoCount = $("#userCardInfoCount").val();
    var llpayNoAgree = $("#llpayNoAgree").val();
    var token = $("#token").val();
    var bankCode = $("#bankCode").val();
    var bankName = $("#bankName").val();
    var idCard = $("#idCard").val();
    var identityName = $("#identityName").val();
    var depositDockUserId = $("#depositDockUserId").val();
    var utmSource = $("#utmSource").val();
    var regTime = $("#regTime").val();
    
    
    if(moneyOrder == "" || moneyOrder == null){
        errorMessage("请输入充值金额");
        return false;
    }
    
    if(moneyOrder == 0){
        errorMessage("输入的金额不能为0");
        return false;
    }

    ajaxRequest(contextPath + "wxtrade/topUp", {
        "cardNo": cardNo,
        "moneyOrder": moneyOrder,
        "userId": userId,
        "userCardInfoCount": userCardInfoCount,
        "llpayNoAgree": llpayNoAgree,
        "token": token,
        "from":'LBWX',
        "bankCode":bankCode,
        "bankName":bankName,
        "idCard":idCard,
        "identityName":identityName,
        "depositDockUserId":depositDockUserId,
        "utmSource":utmSource,
        "regTime":regTime
    },
    topUpFinish);
}
function afterTheTopUpAmount() {
    var topUpAmount = $("#moneyOrder").val();

    if(topUpAmount == "" || topUpAmount == null||topUpAmount == 0){
        $("#inactiveBtn").removeClass("changeBkgColor").addClass("inactiveBtn")
        // return false;
    }else{
        $("#inactiveBtn").removeClass("inactiveBtn").addClass("changeBkgColor")
    }
    $("#afterTheTopUpAmount").html(numFormat(accAdd(topUpAmount, accountBalanceAmount)));
}
$("#inactiveBtn").click(function() {
    queryBankCard();
});

//是否实名认证
function goIdcardValidate(){
    window.location.href = contextPath + "wxtrade/goAuthentication";
}
var idcardValidate;
var getIdcardInfo = function(data) {
    if (data.idcardValidate != "Y") {
        alertBox("请先实名认证","goIdcardValidate",2);
    }
}
// 银行信息
var getBankList = function(data){
    var html = ""; //拼接html
    $.each(data.bankList,
    function(i, jsonObj) {
        html += '<li class="positionR whiteBkg clearfix thisLi borderB">';
        html += '<div class="fl block imgBox">';
        html += '<img src="'+ contextPath +jsonObj.picture + '"></div>';
        if (i==data.bankList.length-1) {
            html += '<div class="fl  rightBox haveBorder bankListItem"><div class="PL5 rightMain">';
        }else{
            html += '<div class="fl  rightBox haveBorder bankListItem "><div class="PL5 rightMain">';
        }
        html += '<p class="font14 bankName strong">' + jsonObj.bankName + '</p>';
        html += '<div class="font12 grayTex"><span>单笔:</span><span>'+ jsonObj.singleLimit +'元</span><span class="ML5">单日:</span><span>'+ jsonObj.singleDayLimit +'元</span><span class="ML5">单月:</span><span>'+ jsonObj.singleMonthLimit +'元</span></div>';
        html += '</div></div></li>';  
    });
    html +='<p class="whiteBkg  textC lineHeigt20 grayTex font12 PT10  thisLi">*以上详情仅供参考,实际以提示支付界面为准</br>如有疑问,请联系客服'+PhoneCall+'</p>';
    $("#allBanks").append(html);
    var allWidth= window.screen.availWidth;//手机分辨率的宽
}

// 点击银行的输入框弹出银行列表
$("#checkBankList").click(function() {
    $("#bankList").addClass('show');
});
$("#return").click(function() {
    $("#bankList").removeClass('show');
});
// //初始化键盘
    $(".main").click(function(e) {
        var target = $(e.target);
        if(!target.is('#moneyOrder')) {//如果点击事件是#inputAmount
            $("#momeyBox").stop().slideUp(200);
            $(".main").css("padding-bottom","0px");  
        }else{
            $("#momeyBox").stop().slideDown(200);
            $(".main").css("padding-bottom","520px");  
        }
    });

//充值弹出输入预留手机号
var gainCodeSwitch = true;
var loginSubmitSwitch = false;
$("#gainCode").click(function() {
    
    if (!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }

    var cardNo = $("#cardNo").val();
    var moneyOrder = $("#moneyOrder").val();
    var userCardInfoCount = $("#userCardInfoCount").val();
    var phoneNum = $("#phoneNum").val();
    var idCard = $("#idCard").val();
    var identityName = $("#identityName").val();
    var bankCode = $("#bankCode").val();
    var bankName = $("#bankName").val();
    
    ajaxRequest(contextPath + "/wxtrade/zshtTopUp",
            {
                "cardNo": cardNo,
                "moneyOrder": moneyOrder,
                "userCardInfoCount": userCardInfoCount,
                "phoneNum":phoneNum,
                "idCard":idCard,
                "identityName":identityName,
                "bankCode":bankCode,
                "bankName":bankName
            }, 
            zshtTopUpFinish,"POST");
});

function checkC() {
    var checkCode = $("#checkCode").val();
    if (checkCode == "") {
        $("#loginSubmit").addClass("forbid");
        loginSubmitSwitch = false;
    } else {
        $("#loginSubmit").removeClass("forbid");
        loginSubmitSwitch = true;
    }
}

function zshtTopUpFinish(data){
    if (data.msg != "success") {
        errorMessage(data.msg);
    } else {
//      errorMessage("发送验证码成功，请输入验证码进行支付");
        //验证码倒计时
        var curCount = 60;
        function SetRemainTime() {
            if (curCount == 0) {
                window.clearInterval(InterValObj); //停止计时器
                gainCodeSwitch = true;
                $("#gainCode").html("重发<br/>验证码");
                $("#gainCode").removeClass("lineHeight25");
            } else {
                curCount--;
                $("#gainCode").html(curCount + "s");
                $("#gainCode").addClass("lineHeight25");
            }
        }
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        errorMessage("验证码发送成功");
        
        var paytoken = data.paytoken;
        var orderNo = data.orderNo;
        
        $("#paytoken").val(paytoken);
        $("#orderNo").val(orderNo);
    }
}
function goGetCaptcha(data) {
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
        return;
    }
    gainCodeSwitch = false;
    var curCount = 60;
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重发验证码");
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
}
$("#checkTopUpSubmit").click(function() {
    
    //验证支付
    var paytoken = $("#paytoken").val();
    var orderNo = $("#orderNo").val();
    var checkCode = $("#checkCode").val();
/*
    if (!loginSubmitSwitch) {
        return false;
    }
*/
    if (!$("#phoneNum").val()) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (checkCode == "") {
        errorMessage("短信验证码为空");
    }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    
    
    ajaxRequest(contextPath + "/wxtrade/checkZshtTopUp",
            {
                "paytoken": paytoken,
                "orderNo": orderNo,
                "checkCode": checkCode
            }, 
            checkZshtTopUpFinish,"POST");
});
//发送验证码回调事件
var InterValObj;
function checkZshtTopUpFinish(data){
    if (data.msg != "success") {
        errorMessage(data.msg);
    } else {
        //跳转成功页面
        window.location.href=contextPath + "/wxtrade/zshtTopUpSucceed";
    }
}
//取消按钮
$("#cannelBtnTopUp").click(function(){
    $(".alertBg").hide();
    $("#phoneNum").val("")
    $("#checkCode").val("")
})

//自定义键盘

var xFunction;
var input = function(price) { // 数字初始化
    var num = $("#moneyOrder").val() + price;
    $("#moneyOrder").val(num);
    $.checkLimit($("#moneyOrder"),$("#moneyOrder").attr("minNumber"),false);
}
var inputNumber = function(price) { //显示输入数字
    if (price == "reture") {
        var num = $("#moneyOrder").val();
        num = num.substring(0,num.length-1);
        $("#moneyOrder").val(num);
        $.checkLimit($("#moneyOrder"),$("#moneyOrder").attr("minNumber"),false);
    }
    if (price == ".") {
        input(price)
    }
    if (price >= 0 && price <= 9) {
        input(price)
    }
}   
var topUpAmount;//输入框输入的金额
$(document).ready(function(){
    $("#bankList").hide();
    $("#bankChoose").val();
    ajaxRequest(contextPath + "wxtrade/getRecePayBank", "", getRecePayBank);
    ajaxRequest(contextPath + "wxtrade/accountBalance", "", accountBalance);
    ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
    ajaxRequest(contextPath + "wxtrade/queryBankList", "", getBankList);
    var topUpMoney =$("#money").val();//需要充值的金额
    if(topUpMoney>0){
        topUpAmount = $("#money").val();//输入框输入的数值
        setTimeout(function(){  
            $("#moneyOrder").val(topUpMoney);
            afterTheTopUpAmount(topUpMoney); 
        },800)
    }else{
        topUpAmount =$("#moneyOrder").val();//输入框输入的数值
        setTimeout(function(){  
            afterTheTopUpAmount(topUpAmount);
        },800)  
    }
    $("#moneyOrder").attr("oninput", "$.checkLimit($(this),1000000,false);afterTheTopUpAmount();").attr("onkeyup", "$.checkLimit($(this),1000000,false);afterTheTopUpAmount();").attr("onafterpaste", "$.checkLimit($(this),1000000,false);afterTheTopUpAmount();");
    // var topUpMoney = $("#money").val();
    // if (topUpMoney > 0) {
    //     $("#moneyOrder").val(topUpMoney);
    //     afterTheTopUpAmount();
    // }
    $("#zshtPayForm input").on("focus",function(){
        if(document.documentElement){
            document.documentElement.scrollTop=0;
        }else{
            document.body.scrollTop=0; 
        }
    }) 

     $$("#numberKeyboard li:not(.not)").tap(function() {
        inputNumber($(this).attr("keyboard"));
         var moneyOrder=$("#moneyOrder").val(); //判断输入金额是否大于可转金额
         var reg=/^[0].$/g;
         if(reg.test(moneyOrder)){
             moneyOrder=moneyOrder.replace('0','0.')
             $("#moneyOrder").val(moneyOrder); 
         }
         afterTheTopUpAmount();
     });
      $$("#keyboardReturn").tap(function() {
          inputNumber($(this).attr("keyboard"));
          afterTheTopUpAmount()
      });
      $$("#keyboardPoint").tap(function() {
         inputNumber($(this).attr("keyboard"));
         afterTheTopUpAmount()
      })
})
