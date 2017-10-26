//后台报错信息有的写zai resmsg有的写在resmsg_cn，优先报resmsg_cn
function resmsg_cn(data){
    if(data.resmsg_cn){
        return data.resmsg_cn
    }else{
        return data.resmsg
    }
}

var blcMoney;
var getBalanceMoney = function(data) {
    $("#blcMoney").html(numFormat(data.balanceMoney));
    blcMoney = data.balanceMoney;
    calculation();
} 
var calculation = function(data){
	$("#balance").html(numFormat(blcMoney));
	var inputVal = Number($("#moneyOrder").val());
	var commonNum = accAdd(blcMoney,inputVal);
	$("#blcMoney").html(numFormat(commonNum));
}

var getRecePayBank = function(data) {
    if (data.bankId != "" && data.bankId != null) {
    	$("#cardNo").val(data.cardNumber.toString());//银行卡号
        $("#bankIcon").attr("src", contextPath + "" + data.picturePath);
        $("#hasCard").show();
        $("#noCard").hide();
        $("#bank_name").html(data.bankName);
        var cardNumber = data.cardNumber.toString();
        $("#cardNumber").html(cardNumber.substr(cardNumber.length-4));
        $("#limitMoney").html("每笔"+data.singleLimit+"元，每日"+data.singleDayLimit+"元，每月"+data.singleMonthLimit +"元");
    } else {
        $("#noCard").show();
        $("#hasCard").hide();
    }
};

var topUpFinish = function(data) {
    if (data.msg != "success") {
        errorMessage(data.msg);
        oneClick = true;
    } else {
        $("#req_data").val(data.payInfo);
        $("#kjpayForm").submit();
    }
};
//实名认证判断
var idcardValidate;
//get user phone number
var mobile;
var getIdcardInfo = function(data) {
	mobile = data.phoneNo;
    if (data.idcardValidate != "Y") {
    	idcardValidate = "N";
    }else{
    	idcardValidate = "Y";
    }
}

//银行卡输入框限制
$("#cardNo").attr("oninput", "$.checkLimit($(this),'',true)").attr("onkeyup", "$.checkLimit($(this),'',true)").attr("onafterpaste", "$.checkLimit($(this),'',true)");
//快捷支付银行卡信息接口回调
var queryBankCardFinish = function(data){
	if (data.msg != "success") {
		oneClick = true;
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
// 			 $("#zshtPayForm").submit();
			//弹出弹出框
            $(".phoneAlertBg").show();
            $(".phoneAlertBox").show();
		 }
	} 
};
//快捷支付按钮点击事件
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
	oneClick = false;
	ajaxRequest(contextPath + "wxtrade/queryBankCard",{"cardNo": cardNo},queryBankCardFinish,"POST");
}

	//微信的方法
	function singlePay(){
		//基本信息
	    var payFlag = $('#payFlag').val()||'kj';				//pay_flag
	    var bankCardNumber = $("#cardNo").val();				//card_number
	    var userId = $("#userId").val();						//user_id
	    var tradeAmount = $("#moneyOrder").val();				//user_payMoney
	    
	    var url = contextPath+"/wxpay/singlePay";
	    $.ajax({ 
	    	type : "POST",
	        url : url,
	        data : "userId=" + userId + "&bankCardNumber=" + bankCardNumber + "&tradeAmount=" + tradeAmount + "&mobile=" + mobile+"&payFlag="+payFlag,
	        dataType : "json",
	        success : function(data) {
	            var rescode = data.rescode;
	            var resmsg = resmsg_cn(data);
	            var payObj = data.payObj;
	            var method = data.method;
	            var sendUrl = data.sendUrl;
	            if (rescode != "00000") {
	                errorMessage(resmsg);
	                return;
	            }
	            if (method == "POST") {
	                var jsonObj = $.parseJSON(payObj);
	                $("#singlePayForm").attr("action", sendUrl);
	                $.each(jsonObj,function(key, value) {
	                    //循环添加input的值
	                    var param = "<input type='hidden' value='"+value+"' name='"+key+"'/>";
	                    $("#params").append(param);
	                });
	                $("#singlePayForm").submit();
	            }else{
	                window.location.href = sendUrl;
	            }
	        },
	        error : function(data) {
	        	
	        }
	    });
	}





//连连支付充值事件
function topUpFunction() {
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
    if(idcardValidate == "N"){
	    showTip("请下载联璧金融APP进行实名认证");
	    return false;
    }
//  ajaxRequest(contextPath + "wxtrade/topUp", {
//      "cardNo": cardNo,
//      "moneyOrder": moneyOrder,
//      "userId": userId,
//      "userCardInfoCount": userCardInfoCount,
//      "llpayNoAgree": llpayNoAgree,
//      "token": token,
//      "from":'web',
//      "bankCode":bankCode,
//      "bankName":bankName,
//      "idCard":idCard,
//      "identityName":identityName,
//      "depositDockUserId":depositDockUserId,
//      "utmSource":utmSource,
//      "regTime":regTime,
//      "payFlag":"kj"
//  },
//  topUpFinish);
	
	//调用
	singlePay();
}



//快捷支付按钮点击事件
var oneClick = true;
$("#kjInactiveBtn").click(function(event) {
	if(oneClick){
        queryBankCard();
    }
});
//网银、快捷切换
$("#E-payWay").click(function(event) {
	$(this).addClass("current");
	$("#shortCutWay").removeClass("current");
	$("#E-bank").show();
	$("#shortcutPay").hide();
});
$("#shortCutWay").click(function(event) {
	$(this).addClass("current");
	$("#E-payWay").removeClass("current");
	$("#E-bank").hide();
	$("#shortcutPay").show();
});
//网银充值事件
function EpayWay(){
	var bankCode = $("input[id='bankCode']").filter(":checked").val();
	var moneyOrder = $("#moneyOrder").val();
	var userId = $("#userId").val();
	if (moneyOrder == "" || moneyOrder == null) {
		errorMessage("请输入充值金额");
		return false;
	}

	if (moneyOrder == 0) {
		errorMessage("输入的金额不能为0");
		return false;
	}
	if(idcardValidate == "N"){
	    showTip("请下载联璧金融APP进行实名认证");
	    return false;
    }
	inactiveBtnClick = false;
	$.ajax({
		type : "POST",
		url : contextPath + "trade/topUp",
		data : {
			"bankCode" : bankCode,
			"moneyOrder" : moneyOrder,
			"userId" : userId
		},
		dataType : "json",
		success : function(data) {
			if (data.msg != "success") {
				errorMessage(data.msg);
				inactiveBtnClick = true;
			} else {
				$("#llpay_version").val(
						data.payInfoObj.version);
				$("#llpay_charset_name").val(
						data.payInfoObj.charset_name);
				$("#llpay_oid_partner").val(
						data.payInfoObj.oid_partner);
				$("#llpay_user_id").val(
						data.payInfoObj.user_id);
				$("#llpay_sign_type").val(
						data.payInfoObj.sign_type);
				$("#llpay_sign").val(
						data.payInfoObj.sign);
				$("#llpay_busi_partner").val(
						data.payInfoObj.busi_partner);
				$("#llpay_no_order").val(
						data.payInfoObj.no_order);
				$("#llpay_dt_order").val(
						data.payInfoObj.dt_order);
				$("#llpay_name_goods").val(
						data.payInfoObj.name_goods);
				$("#llpay_info_order").val(
						data.payInfoObj.info_order);
				$("#llpay_money_order").val(
						data.payInfoObj.money_order);
				$("#llpay_notify_url").val(
						data.payInfoObj.notify_url);
				$("#llpay_url_return").val(
						data.payInfoObj.url_return);
				$("#llpay_userreq_ip").val(
						data.payInfoObj.userreq_ip);
				$("#llpay_valid_order").val(
						data.payInfoObj.valid_order);
				$("#llpay_timestamp").val(
						data.payInfoObj.timestamp);
				$("#llpay_risk_item").val(
						data.payInfoObj.risk_item);
				$("#llpay_no_agree").val(
						data.payInfoObj.no_agree);
				$("#llpay_bank_code").val(
						data.payInfoObj.bank_code);
						
				$("#llpayForm").submit();
			}
		}
	});
}
var inactiveBtnClick = true;
$(document).ready(function() {
	ajaxRequest(contextPath + "wxtrade/getRecePayBank", "", getRecePayBank);
	ajaxRequest(contextPath + "wxtrade/showAssets", "",getBalanceMoney);
	ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
	$("#moneyOrder").attr("oninput","$.checkLimit($(this),'',false);calculation()")
					.attr("onkeyup","$.checkLimit($(this),'',false);calculation()")
					.attr("onafterpaste","$.checkLimit($(this),'',false);calculation()");

	$(".bankSelect").click(function(event) {
		$(".bankSelect").removeClass("current");
		$(this).addClass("current");
	});

//网银支付按钮点击事件
	$("#inactiveBtn").click(function() {
		if(inactiveBtnClick){
			EpayWay();
		}
	});
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
    gainCodeSwitch = false;
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
//     	errorMessage("发送验证码成功，请输入验证码进行支付");
		//验证码倒计时
		var curCount = 60;
	    function SetRemainTime() {
	        if (curCount == 0) {
	            window.clearInterval(InterValObj); //停止计时器
	            gainCodeSwitch = true;
	            $("#gainCode").html("重发验证码");
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
    	window.location.href=contextPath + "trade/topUpSucceed";
    }
};
//取消按钮
$("#cannelBtnTopUp").click(function(){
	oneClick = true;
	$(".phoneAlertBg").hide();
	$(".phoneAlertBox").hide();
	$("#phoneNum").val("");
	$("#checkCode").val("");
});