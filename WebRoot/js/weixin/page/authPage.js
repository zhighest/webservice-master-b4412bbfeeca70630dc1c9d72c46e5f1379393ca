			//网易易盾变量
var ins;
var verificationStatus = true;

var gainCodeSwitch = true;
var loginSubmitSwitch = false;
var authToken=getUrlParam("authToken");
var parterCode=getUrlParam("parterCode");
var backUrl=getUrlParam("backUrl");
var checkCode = $("#checkCode").val();
var mobile = getUrlParam("mobile");
$("#phoneNum").val(mobile);  //   授权用户的手机号码
var phoneNum = $("#phoneNum").val();
$("#gainCode").click(function() {
	
    if (!gainCodeSwitch) {
        return false;
    }
     phoneNum = $("#phoneNum").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
	//网易云盾
	if(verificationStatus){
		alertVerification('',phoneNum,'wxlogin');
	}else{
		alertVerification('',phoneNum, 'wxlogin',true);
		$("#verificationStatus").html('');
	}
});
$("#loginSubmit").click(function() {
	phoneNum = $("#phoneNum").val();
    if (!loginSubmitSwitch) {
        return false;
    }
    if (!phoneNum) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    ajaxRequest(contextPath + "wxKcodeTrade/authLogin", "phoneNum=" + phoneNum + "&checkCode="+checkCode+"&loginFlag=2&password=&authToken="+authToken+"&parterCode="+parterCode, goAuthLogin);
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
function checkC() {
	checkCode = $("#checkCode").val();
    if (checkCode == "") {
        $("#loginSubmit").addClass("forbid");
        loginSubmitSwitch = false;
    } else {
        $("#loginSubmit").removeClass("forbid");
        loginSubmitSwitch = true;
    }
}
function goAuthLogin(data){
	if(data.rescode=="00000"){			
        $.ajax({
        url:backUrl+"?from=WX&mobile="+mobile+"&authToken="+authToken,
        type: 'get',
        dataType: "jsonp",
        jsonp: 'callback',
        success: function(data) {
            errorMessage("授权成功");
            setTimeout(function(){
                history.go(-1)
            },500)           
        },
        error: function() {
            errorMessage("授权失败");
        }
    });	
		
	}
	else{
		errorMessage("授权失败");
	}
}
$("#returnSubmit").click(function(){
	history.go(-1);
})