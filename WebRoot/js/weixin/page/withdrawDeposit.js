var povince = $("#povince").val();
var cityCode;
var getPovince = function(data){
    cities = new Object(); 
    $.each(data.provinceList , function(i,jsonObj) {
        var options = "<option value='"+jsonObj.provinceCode+"'>"+jsonObj.provinceName+"</option>";
        $("#povince").append(options);
        cities[jsonObj.provinceCode] = [];  
        $.each(jsonObj.cityList,function(i,cityObj){
            cities[jsonObj.provinceCode].push(cityObj.cityName +"+"+ cityObj.cityCode) ;
        });    
    });
    $("#povince").change(function(event) {
        povince = $(this).val();
        var city = cities[povince];
        $("#city").empty();
        if(povince == '请选择省'){
            $("#city").html('<option value="">请选择市</option>');
        }else{
            for(var i=0;i < city.length;i++){
                var citySplit = city[i].split('+');
                var cityList = '<option value="'+citySplit[1]+'">'+citySplit[0]+'</option>';
                $("#city").append(cityList);
            };
            cityCode = $("#city").val();
        };
    });
    $("#city").change(function(event) {
        cityCode = $(this).val();
    });
};
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
var accountBalanceNum;
var systemQuickAmount;
var getRecePayBank = function(data) {
    if (data.bankId != "" && data.bankId != null) {
        $("#hasCard").show();
        $("#bankIcon").attr("src", contextPath + "" + data.picturePath);
        $("#bank_name").html(data.bankName);
        //$("#cardNumber").html(numHide(data.cardNumber));
        $("#cardNumber").html('('+data.cardNumber.substring(data.cardNumber.length - 4,data.cardNumber.length)+')'); //银行
        $("#cardNo").val(numHide(data.cardNumber));
        $("#cardId").val(data.id);
        $("#quickWithdrawBtnDegree").html(data.normalWithdrawTip);
        $("#quickWithdrawBtnDegreeMoney").html(data.quickWithdrawTip);
        if(data.quickWithdrawFlag!="ON"){
            tabSwitchLia();
            //$(".tab2").html("快速提现"+"<span>（暂停使用）</span>");
            //$(".tab1").addClass("fl").removeClass("fr");
           $("#tab1").unbind("click");
           $("#tab2").unbind("click");
        }
        else{
        	$("#tab1").bind("click");
            $("#tab2").bind("click");	
        }
        $("#quickAmountDesc").html(data.quickAmountDesc);//快速提现提示第一段文案
        $("#quickCountDesc").html(data.quickCountDesc);//快速提现提示第er段文案
        systemQuickAmount=data.systemQuickAmount;
        ajaxRequest(contextPath + "wxabout/withdrawNeedProvinceAndCity", "", withdrawNeedProvinceAndCity,"GET");
    } else {
        $("#noCard").show();
        $("#hasCard").hide();
        alertBox2("温馨提示","您尚未绑定银行卡不能提现，请先至少充值0.01元进行绑卡","goTopUpcz",2);
    }
};
$("#tab2").on("click",function(){
	tabSwitchLib();
});
$("#tab1").on("click",function(){
	tabSwitchLia();
})

function goTopUpcz() {
	window.location.href = contextPath + "wxpay/redirectPay";
}
var accountBalance = function(data) {
	accountBalanceNum = data;
	$("#extractMoneyHint").html(numFormat(accountBalanceNum));
    if(accountBalanceNum == 0){
        $("#withdrawBtn").css({"background":'#ccc',"color":"#fff"});
        $("#quickWithdrawBtn").css("background",'#ccc');
        withdrawBtnCheck = false;
        quickWithdrawBtnCheck = false;
    } 
	$("#insideInputBtn").click(function(event) {
		$("#extractMoney").val(accountBalanceNum);	
	});
    //$("#extractMoney").attr("oninput", "$.checkLimit($(this)," + data + ",false);").attr("onkeyup", "$.checkLimit($(this)," + data + ",false);").attr("onafterpaste", "$.checkLimit($(this)," + data + ",false);");
    if(data <= 0){
	    $("#withdrawBtn").addClass("inactiveBtn");
    }
};

var setTimeoutFun = function(){
    setTimeout(function(){
        window.location.reload();
    },1000);
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
    	}else if(data.errorMessage.message_content!=""){
            message_level(data,setTimeoutFun,'remove00');  // 报错等级  在merge里
    	}else{
        errorMessage("提现失败！" + resmsg_cn);
        setTimeout(function(){
            window.location.reload();
        },1000);
        }
    } else {
        window.location.href = contextPath + "wxtrade/withdrawDepositSuccess";
    }
  
};
function remove00(){

	$('#alertBox').remove();
	  location.reload();
}
//var passwordCashCheckFinish = function(data) {
//    var isCorrect = data.isCorrect;
//    if (!isCorrect) {
//        errorMessage("提现失败！交易密码错误！");
//        setTimeout(function(){
//            window.location.reload();
//        },1000);
//    } else {
//        var extractMoney = parseFloat($("#extractMoney").val());
//        var cardNumber = $("#cardNo").val();
//        var cardId = $("#cardId").val();
//        var token = $("#token").val();
//        ajaxRequest(contextPath + "wxtrade/withdrawDeposit", {
//            "extractMoney": extractMoney,
//            "cardNumber": cardNumber,
//            "cardId": cardId,
//            "province": povince,
//            "token": token,
//            "city": cityCode,
//            "withdrawFlag": "LIANLIANPAY",
//            "from":'LBWX'
//        },
//        withdrawCashFinish);
//    }
//};
var withdrawCash2 = function(passwordCash){
	// 输入提现密码方法调用
    //var passwordCash = prompt("请输入提现密码：");
	if (passwordCash == null || passwordCash == "") {
        errorMessage("提现密码为空！请重新输入！");
    } else {
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
        ajaxRequest(contextPath + "wxtrade/withdrawDeposit", {
                "extractMoney": extractMoney,
                "cardNumber": cardNumber,
                "cardId": cardId,
                "province": povince,
                "token": token,
                "city": cityCode,
                "withdrawFlag": "LIANLIANPAY",
                "passwordCash": passwordCash,
                "from":'LBWX'
            },
            withdrawCashFinish);
    }
    // ajaxRequest(contextPath+"wxtrade/withdrawDeposit",{"extractMoney":
    // extractMoney,"cardNumber":cardNumber,"cardId":cardId},withdrawCashFinish);
}
//普通提现弹框确定
var withdrawCashEnter = function(){
    $("#alertBox").hide();
    usePassword(withdrawCash2);
}
var extractMoney;
var withdrawCash = function() {
    extractMoney = $("#extractMoney").val();
    if(povince == "请选择省"){
        errorMessage("请选择城市！");
        return false;
    }
	if(accountBalanceNum <= 0){
		errorMessage("账户余额不足！");
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
    //ajaxRequest(contextPath + "wxtrade/queryNextWorkingDay", "withdrawFlag=LIANLIANPAY", queryNextWorkingDay,"GET");
    ajaxRequest(contextPath + "wxtrade/queryFinalWithdrawlInfo", "withdrawFlag=LIANLIANPAY&extractMoney="+extractMoney, queryNextWorkingDay,"GET");
};
//普通提现按钮方法
var withdrawBtnCheck = true;
$("#withdrawBtn").click(function() {
    if(withdrawBtnCheck){
        withdrawCash();
    };
});
//普通提现回调
var queryNextWorkingDay = function(data){
    if(data.rescode == 00000){
        alertBox2("温馨提示","提现金额:<font class='redTex'>"+numFormat(extractMoney)+"</font>元<br/>提现手续费:<font class='redTex'>"+numFormat(data.extractFee)+"</font>元<br/>"+data.promptInfo,'withdrawCashEnter',1);
    }else{
        errorMessage(data.resmsg_cn);
    }
}
$(document).ready(function() {
    ajaxRequest(contextPath + "wxtrade/getRecePayBank", "", getRecePayBank);
    ajaxRequest(contextPath + "wxtrade/accountBalance", "", accountBalance);
});
//快速提现弹框确定
var quickWithdrawEnter = function(){
    $("#alertBox").remove();
    usePassword(withdrawCash3);
}
//快速提现按钮方法
var quickWithdrawBtnCheck = true;
$("#quickWithdrawBtn").click(function(){
    withdrawCash1();
    //if(quickWithdrawBtnCheck){
    //	if(quickWithdrawCount == "0"){
    //        errorMessage("当前快速提现不可用，请联系在线客服");
    //    }else if($("#extractMoney").val() > parseFloat((systemQuickAmount))){
    //	    errorMessage("提现金额超出您的快速提现限额，请使用普通提现");
    //    }else{
    //	    withdrawCash1();
    //	}
    //
    //}
});
var withdrawCash1 = function() {
	extractMoney = $("#extractMoney").val();
    if(povince == "请选择省"){
        errorMessage("请选择城市！");
        return false;
    }
	if(accountBalanceNum <= 0){
		errorMessage("账户余额不足！");
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
    ajaxRequest(contextPath + "wxtrade/queryFinalWithdrawlInfo", "withdrawFlag=ZSHTPAY&extractMoney="+extractMoney, queryNextWorkingDay2,"GET");//掌上汇通获取工作日
};
//快速提现回调
var queryNextWorkingDay2 = function(data){ 
    if(data.rescode == 00000){
        alertBox2("温馨提示","提现金额:<font class='redTex'>"+numFormat(extractMoney)+"</font>元<br/>"+data.promptInfo,'quickWithdrawEnter',1);
    }else{
        errorMessage(data.resmsg_cn);
    }
}
var withdrawCash3 = function(passwordCash){
	// 输入提现密码方法调用
    //var passwordCash = prompt("请输入提现密码：");
	if (passwordCash == null || passwordCash == "") {
        errorMessage("提现密码为空！请重新输入！");
    } else {
        // 手机号
        /*
var mobile = $("#mobile").val();
        ajaxRequest(contextPath + "wxtrade/checkPasswordCash", {
            "passwordCash": passwordCash,
            "phoneNum": mobile
        },
        passwordCashCheckFinish3);
*/
        var extractMoney = $("#extractMoney").val();
        var cardNumber = $("#cardNo").val();
        var cardId = $("#cardId").val();
        var token = $("#token").val();
        ajaxRequest(contextPath + "wxtrade/withdrawDeposit", {
            "extractMoney": extractMoney,
            "cardNumber": cardNumber,
            "cardId": cardId,
            "province": povince,
            "token": token,
            "city": cityCode,
            "withdrawFlag": "ZSHTPAY",
            "passwordCash": passwordCash,
            "from":'LBWX'
        },
        withdrawCashFinish);
    
    }
    // ajaxRequest(contextPath+"wxtrade/withdrawDeposit",{"extractMoney":
    // extractMoney,"cardNumber":cardNumber,"cardId":cardId},withdrawCashFinish);
}
//var passwordCashCheckFinish3 = function(data) {
//    var isCorrect = data.isCorrect;
//    if (!isCorrect) {
//        errorMessage("提现失败！交易密码错误！");
//        setTimeout(function(){
//            window.location.reload();
//        },1000);
//    } else {
//        var extractMoney = $("#extractMoney").val();
//        var cardNumber = $("#cardNo").val();
//        var cardId = $("#cardId").val();
//        var token = $("#token").val();
//        ajaxRequest(contextPath + "wxtrade/withdrawDeposit", {
//            "extractMoney": extractMoney,
//            "cardNumber": cardNumber,
//            "cardId": cardId,
//            "province": povince,
//            "token": token,
//            "city": cityCode,
//            "withdrawFlag": "ZSHTPAY",
//            "from":'LBWX'
//        },
//        withdrawCashFinish);
//    }
//};
//切换提现方式
var tabSwitchLia = function(){//普通提现
    $("#withdrawBtn").show();
    $("#quickWithdrawBtn").hide();
    //$("#quickWithdrawBtnDegreeMoney").hide();
    //$("#quickWithdrawBtnDegree").show();
    $(".tab2").removeClass("current");
    $(".tab1").addClass("current");
}
var tabSwitchLib = function(){//快速提现
    $("#withdrawBtn").hide();
    $("#quickWithdrawBtn").show();
    //$("#quickWithdrawBtnDegree").hide();
    //$("#quickWithdrawBtnDegreeMoney").show();
    $(".tab1").removeClass("current");
    $(".tab2").addClass("current");
}
$('#wayDetail ').click(function(){
    $('.wayExplain').removeClass('none').animate({'top':0},300);
});
var screenH=$('body').height();
$('.closeBtn').click(function(){
    $('.wayExplain').animate({'top':screenH*1.3},300);
})

//自定义键盘

var xFunction;
var input = function(price) { // 数字初始化
    var num = $("#extractMoney").val() + price;
    $("#extractMoney").val(num);
    $.checkLimit($("#extractMoney"),$("#extractMoney").attr("minNumber"),false);
}
var inputNumber = function(price) { //显示输入数字
    if (price == "reture") {
        var num = $("#extractMoney").val();
        num = num.substring(0,num.length-1);
        $("#extractMoney").val(num);
        $.checkLimit($("#extractMoney"),$("#extractMoney").attr("minNumber"),false);
    }
    if (price == ".") {
        input(price)
    }
    if (price >= 0 && price <= 9) {
        input(price)
    }
}   


$(document).ready(function(){
      $$("#numberKeyboard li:not(.not)").tap(function() {
      inputNumber($(this).attr("keyboard"));
      var extractMoney=$("#extractMoney").val(); //判断输入金额是否大于可转金额
      var reg=/^[0].$/g;
         if(reg.test(extractMoney)){
             extractMoney=extractMoney.replace('0','0.');
             $("#extractMoney").val(extractMoney); 
         }
      
        if(extractMoney>accountBalanceNum){
            $("#extractMoney").val(accountBalanceNum); 
        }

     });
      $$("#keyboardReturn").tap(function() {
        inputNumber($(this).attr("keyboard"));
     });
       $$("#keyboardPoint").tap(function() {
         inputNumber($(this).attr("keyboard"));
      })
     //初始化键盘
     $('#KeyboardWrap').css({
        'position':"fixed",
        'zIndex':"1000",
        'bottom':"-120%",
        'display':'none',
        'width':'100%',
        'background':'#ffffff'
    });
    $(".main").css({
        '-webkit-tap-highlight-color':'rgba(0,0,0,0)'
    })
    $("#extractMoney").click(function(ev) {
        var ev=ev||event
        ev.stopPropagation();
         $('#KeyboardWrap').css({"display":"block"}).animate({
            'bottom':"0px"
        },100,function(){
             $(".main").on('click',function(event) {
               $('#KeyboardWrap').animate({
                 'bottom':"-120%"
                 });
               $(this).off('click')
              });
        });
            
    });
})


//自定义键盘   点击键盘确认键
$("#keyboardConfirm").click(function(){
    $("#KeyboardWrap").stop().slideUp(200);
});