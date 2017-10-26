$(document).ready(function() {
    if (message) {
        errorMessage(message);
    } 
   $("#imgcode").click(function(){
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
});
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
$("#checkTopUpSubmit").click(function() {
    if (!loginSubmitSwitch) {
        return false;
    }
    if (!$("#phoneNum").val()) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    //验证支付
    var paytoken = $("#paytoken").val();
    var orderNo = $("#orderNo").val();
    var checkCode = $("#checkCode").val();
    
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
function zshtTopUpFinish(data){
	if (data.msg != "success") {
        errorMessage(data.msg);
    } else {
    	alert("发送验证码成功，请输入验证码进行支付");
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
function confirmFormSubmit() {
    $("#confirmForm").submit();
}
function cancelFormSubmit() {
    $("#confirmForm").submit();
}
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

