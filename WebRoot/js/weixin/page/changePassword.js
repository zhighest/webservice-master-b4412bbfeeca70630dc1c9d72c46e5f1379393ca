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

//忘记密码点击事件
var idcardValidateYN = getUrlParam("isYN");
var forgetPassword = function(){
    if(idcardValidateYN == 'Y'){
        window.location.href = contextPath + "wxuser/changePassword";
    }else{
        $("#tipBox").stop().show();
    }
}

var oldPwd;  //旧密码
var newPwd;  //新密码
var reNewPwd;  //重复新密码

$("#updatePwdBtn").on("click",function(){
    oldPwd = $("#oldPassword").val();
    newPwd = $("#newPassword").val();
    reNewPwd = $("#reNewPassword").val();
    if(oldPwd == '' || oldPwd == null){
        errorMessage("请输入旧密码");
        return false;
    };
    if(newPwd == '' || newPwd == null){
        errorMessage("请输入新密码");
        return false;
    };
    if(reNewPwd == '' || reNewPwd == null){
        errorMessage("请再次输入新密码");
        return false;
    };

    if(newPwd != reNewPwd){
        errorMessage("两次输入密码不一致");
        return false;
    }
    ajaxRequest(contextPath + "wxuser/updateTradePwd", "oldTradePwd=" + oldPwd + "&newTradePwd="+newPwd + "&channel=LBWX", updateTradePwd,"GET");
});

function updateTradePwd(data){
    if(data.rescode == "00000"){
        window.location.href = contextPath + "wxtrade/goChangePasswordCashSuccess";
    }else{
        errorMessage(data.resmsg_cn);
    }
}

//弹窗点击取消按钮
$("#cancel").click(function(event) {
    $("#tipBox").stop().hide();
});

//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
			$('.JsPhoneCall').html(PhoneCall);
			$('.JsPhoneCallTell').attr('href','tel:'+PhoneCall);
		}
	});
}
getServiceCall();