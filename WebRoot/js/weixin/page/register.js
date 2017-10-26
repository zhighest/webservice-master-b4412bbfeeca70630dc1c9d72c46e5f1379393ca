var checkBoxArea;
$(document).ready(function() {
    if (message) {
        errorMessage(message);
    }
   $("#imgcode").click(function(){//图片验证码
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
   checkBoxArea = true;
    $("#checkBoxArea img").toggle( 
        function () { 
            $(this).attr("src",contextPath+"/pic/weixin/checkbox1.png"); 
            checkBoxArea = false;
        }, 
        function () { 
            $(this).attr("src",contextPath+"/pic/weixin/checkbox2.png");
            checkBoxArea = true;
        } 
    );
    //增加斐讯渠道
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
    
});
//网易易盾变量
var ins;
var verificationStatus = true;

var gainCodeSwitch = true;
var loginSubmitSwitch = false;
//获取验证码
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
		alertVerification('',phoneNum,'register');
	}else{
		alertVerification('',phoneNum, 'register',true);
		$("#verificationStatus").html('');
	}
});
$("#loginSubmit").click(function() {
    var password = $("#password").val();
    // var passwordAgin = $("#passwordAgin").val();
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
    if (password == null || password == '') {
        errorMessage("请设置登录密码");
        return false;
    }
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\^\*]{8,16})$/.test(password))){
        errorMessage('登录密码为8~16位字母数字或符号组合');
        return false;
    }
    // if(passwordAgin == '' || passwordAgin == null){
    //     errorMessage("两次输入密码不一致，请重新输入");
    //     return false;
    // };
    // if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(passwordAgin))){
    //     errorMessage("登录密码为6~16位字母数字组合");
    //     return false;
    // };
    // if(password !== passwordAgin){
    //     errorMessage("两次输入密码不一致，请重新输入");
    //     return false;
    // };
    if(!checkBoxArea){
       errorMessage("请仔细阅读并同意注册协议");
        return false; 
    }
    $("#loginForm").submit();
});
//发送验证码回调事件
var InterValObj;
function goGetCaptcha(data) {
	//网易易盾
    $('#Verification').remove();
    verificationStatus  = false;
	
    if (data.rescode != "00000") {
//      var me = $("#imgcode");
//      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        errorMessage(data.resmsg_cn);
        ins.refresh();
        return;
    }
    gainCodeSwitch = false;
    var curCount = 60;
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重发验证码");
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
    var checkCode = $("#checkCode").val();
    if (checkCode == "") {
        $("#loginSubmit").addClass("forbid");
        loginSubmitSwitch = false;
    } else {
        $("#loginSubmit").removeClass("forbid");
        loginSubmitSwitch = true;
    }
}
$("#register").click(function(){
    ajaxRequest(contextPath+"/wxagreement/getServAgreementByType","type=register&productId=107",setServAgreementByType,"GET");
})
var setServAgreementByType = function(data){ 
	var u = navigator.userAgent, app = navigator.appVersion;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	var serAgreementUrl=data.serAgreementUrl;
	var flag=serAgreementUrl.indexOf(";");
	serAgreementUrl2=serAgreementUrl.substring(0,flag);
	serAgreementUrl3=serAgreementUrl.substring(flag+1);
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
	    	if(isiOS){
	    	window.location.href = serAgreementUrl2;
		}
		else{
			window.location.href = serAgreementUrl3;
		}
    }
};
