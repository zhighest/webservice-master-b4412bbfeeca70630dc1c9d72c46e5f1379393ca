var traderPassword;
var repetition;
//设置密码回调
var goresetLoginPassword = function(data){
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
        clickCheck = true;
        return false;
    }else{
        window.location.href = contextPath +'wxtrade/goSetting';
    };
};
//网易易盾变量
var ins;
var verificationStatus = true;

//获取验证码
var gainCodeSwitch = true;
$("#gainCode").click(function() {
    if (!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if (phoneNum == "") {
        errorMessage("手机号为空");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    //网易云盾
	if(verificationStatus){
		alertVerification('',phoneNum,'resetpassword');
	}else{
		alertVerification('',phoneNum, 'resetpassword',true);
		$("#verificationStatus").html('');
	}
    
});
//发送验证码回调事件
var InterValObj;
function goGetCaptcha(data) {
	//网易易盾
    $('#Verification').remove();
    verificationStatus  = false;
	
    if (data.rescode != "00000") {
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
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
}
var NetValidate = true;
var ins;
$(document).ready(function() {   	
    //确定点击事件
    var clickCheck = true;
    $("#enterBtn").click(function() {
        var password = $("#password").val();
        var passwordAgin = $("#passwordAgin").val();
        var checkCode = $("#checkCode").val();
        if (!$("#phoneNum").val()) {
            errorMessage("手机号为空");
            return false;
        }
        if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
            errorMessage("请输入正确的手机号码");
            return false;
        }
        if (!$("#checkCode").val()) {
            errorMessage("短信验证码为空");
            return false;
        }
        if (password == null || password == '') {
            errorMessage("登录密码为空");
            return false;
        }
        if(passwordAgin == '' || passwordAgin == null){
            errorMessage("请再次输入登录密码");
            return false;
        };
        // if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\^\*]{8,16})$/.test(passwordAgin))){
        //     errorMessage("请完整填写登录密码");
        //     return false;
        // };
        if(password !== passwordAgin){
            errorMessage("两次输入密码不一致，请重新输入");
            return false;
        }
        if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\^\*]{8,16})$/.test(password))){
            errorMessage('请输入8~16位字母数字或符号组合！');
            return false;
        }
        clickCheck = false;
        var data={};
        data.checkCode=checkCode;
        data.password=password;//解决ajax不能提交特殊字符问题
        ajaxRequest(contextPath + "wxuser/resetLoginPassword",data, goresetLoginPassword ,"GET");
    });
});
//密码可见
var eyePassword=0;
var deleteFlag=0;
$(".eye").on("click",function(){
	var num=$(this).attr("value");
	if(eyePassword==0){
		$(this).siblings(".password").prop("type","text");
		$('#eyeImg'+num).attr("src",contextPath + "/pic/weixin/passwordLogin/openEye.png");
		eyePassword=1;
	}
	else{
		$(this).siblings(".password").prop("type","password");
		$('#eyeImg'+num).attr("src",contextPath + "/pic/weixin/passwordLogin/eye.png");
		eyePassword=0;
	}
});
$(".delete").on("click",function(){
	var num=$(this).attr("value");
	$(this).siblings(".password").attr("value","");
	$("#delete"+num).addClass("none");
	deleteFlag=1;
})
mark()
function mark(){
	$('input').bind('input propertychange', function(){ 
		var num=$(this).siblings(".eye").attr("value");
		var val=$(this).val();
			if(val==""||val=="null"||val==null){
				$(".delete").addClass("none");
			}
			else{
				$("#delete"+num).removeClass("none");
				
			}
			
	});
}