//设置密码回调
var goUpdateLoginPassword = function(data){
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
        clickCheck = true;
        return false;
    }else{
        window.location.href = contextPath +'wxtrade/goSetting';
    };
};

$(document).ready(function() {   
    //确定点击事件
    var clickCheck = true;
    $("#enterBtn").click(function() {
        var password = $("#password").val();
        var passwordAgin = $("#passwordAgin").val();
        var oldPassword = $("#oldPassword").val();
        if (oldPassword == null || oldPassword == '') {
            errorMessage("旧密码为空");
            return false;
        }
        if (password == null || password == '') {
            errorMessage("新密码为空");
            return false;
        }
        if(passwordAgin == '' || passwordAgin == null){
            errorMessage("请再次输入登录密码");
            return false;
        };
        // if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(passwordAgin))){
        //     errorMessage("请完整填写登录密码");
        //     return false;
        // };
        if(password !== passwordAgin){
            errorMessage("两次输入密码不一致，请重新输入");
            return false;
        }
        if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(password))){
            errorMessage('登录密码为8~16位字母数字或符号组合');
            return false;
        }
        clickCheck = false;
        var data={};
        data.passwordOld=oldPassword;
        data.password=password;//解决密码含有特殊字符的问题
        ajaxRequest(contextPath + "wxuser/updateLoginPassword", data, goUpdateLoginPassword ,"GET");
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
$("input").on("foucs",function(){
	var num=$(this).siblings(".eye").attr("value");
	var val=$(this).val();
	deleteFlag=0;
	if(val==""||val=="null"||val==null){
		$(".delete").addClass("none");
	}
	else{
		$("#delete"+num).removeClass("none");
	}
})
