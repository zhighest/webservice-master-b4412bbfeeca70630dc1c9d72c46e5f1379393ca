$(document).ready(function() {
    if (message) {
        errorMessage(message);
    }
    var mobileNumber = $("#mobileNumber").val();
    var channel = $("#channel").val();
    if(mobileNumber !== "" && mobileNumber !== null && mobileNumber !== "null"){
        $("#phoneNum").val(mobileNumber);
        if(channel =="PHICOMM") {
            $("#phoneNum").after("<input type=\"hidden\" name=\"phoneNum\" id=\"phoneNumHide\" value=\"\">");
            $("#phoneNumHide").val(mobileNumber);
            $("#phoneNum").attr("disabled","true")    
        }
        $("#phoneMask").show();
    }
    
   $("#imgcode").click(function(){
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
});

//网易易盾变量
var ins;
var verificationStatus = true;

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
//  var verifycode = $("#verifycode").val();
//  if (verifycode == "" || verifycode == null) {
//      errorMessage("请输入图形验证码");
//      return false;
//  }
	//网易云盾
	if(verificationStatus){
		alertVerification('',phoneNum,'wxlogin');
	}else{
		alertVerification('',phoneNum, 'wxlogin',true);
		$("#verificationStatus").html('');
	}
});
$("#loginSubmit").click(function() {
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
//  if (!$("#verifycode").val()) {
//      errorMessage("请输入正确的图形验证码");
//      return false;
//  }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    $("#loginForm").submit();
});
var InterValObj;
function goGetCaptcha(data) {
	//网易易盾
    $('#Verification').remove();
    verificationStatus  = false;
	
    if (data.rescode != "00000") {
//      var me = $("#imgcode");
//      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        ins.refresh();
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
            $("#gainCode").removeClass("forbid");
            ins.refresh();
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
            $("#gainCode").addClass("forbid");
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

