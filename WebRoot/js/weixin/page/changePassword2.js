
var phoneNum = $("#mobile").val();
$("#phoneNum").val(phoneNum);

//密码可见
$(".eye").on("click",function(){
    var eyeFlag = $(this).attr("eyeFlag");
    if(eyeFlag=="0"){
        $(this).prev().prop("type","text");
        $(this).children(".eyeImg").attr("src",contextPath + "/pic/weixin/passwordLogin/openEye.png");
        $(this).attr("eyeFlag","1");
    }
    else{
        $(this).prev().prop("type","password");
        $(this).children(".eyeImg").attr("src",contextPath + "/pic/weixin/passwordLogin/eye.png");
        $(this).attr("eyeFlag","0");
    }
});
//网易易盾变量
var ins;
var verificationStatus = true;


//获取短信验证码
var gainCodeSwitch = true;  //再次获取验证码
var InterValObj;  // 验证码定时器
var curCount = 60;  ///验证码时间

$("#gainCode").click(function() {
    if (!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if (phoneNum == "" || phoneNum == null) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    //网易云盾
	if(verificationStatus){
		alertVerification('',phoneNum,'resetTradePassword');
	}else{
		alertVerification('',phoneNum, 'resetTradePassword',true);
		$("#verificationStatus").html('');
	}
	
});
function goGetCaptcha(data){
	//网易盾变量
    $('#Verification').remove();
    verificationStatus  = false;
	
    if (data.rescode == "00000") {
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
            }
        }
        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
        errorMessage("验证码发送成功");
    }else{
        errorMessage(data.resmsg_cn);
        ins.refresh();
    }

}


//身份证号码正则
var IDnumVerify = /^(\d{14}(\d|X|x)$|^\d{18}$|^\d{17}(\d|X|x))$/;
var IDnumber;  //身份证号
var phoneNum;  //手机号
var checkCode; //验证码
var password; // 交易密码
var rePassword; //再次输入交易密码
 $("#resetPwdBtn").on("click",function(){
    IDnumber = $("#IDnumber").val();
     phoneNum = $("#phoneNum").val();
     checkCode = $("#virfyCode").val();
     password = $("#password").val();
     rePassword = $("#rePassword").val();
     if (checkCode == "" || checkCode == null) {
         errorMessage("请输入短信验证码");
         return false;
     }
    if(IDnumber == '' || IDnumber == null){
        errorMessage("请输入身份证号码");
        return false;
    };
    if(!IDnumVerify.test(IDnumber)){
        errorMessage("请输入正确的身份证号码");
        return false;
    };
     if (password == "" || password == null) {
         errorMessage("请输入交易密码");
         return false;
     }
     if (rePassword == "" || rePassword == null) {
         errorMessage("请再次输入交易密码");
         return false;
     }
     if(password != rePassword){
         errorMessage("两次输入密码不一致");
         return false;
     }
     ajaxRequest(contextPath + "wxuser/resetTradePwd", "newTradePwd="+password+"&idCard="+IDnumber+"&checkCode="+checkCode+"&channel=LBWX",resetTradePwd,"GET");
 });
function resetTradePwd(data){
    if(data.rescode == "00000"){
        window.location.href = contextPath + "wxtrade/goChangePasswordCashSuccess";
    }else{
        errorMessage(data.resmsg_cn);
    }
}