$(document).ready(function() {
    if (message) {
        errorMessage(message);
    }
    //图片验证码点击
    $("#imgcode").click(function(){
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
   var mobileNumber = $("#mobileNumber").val();
   var channel = $("#channel").val();
   if(mobileNumber !== "" && mobileNumber !== null && mobileNumber !== "null"){
	    $("#phoneNum").val(mobileNumber);
	    if(channel =="PHICOMM") {
		   	$("#phoneNum").after("<input type=\"hidden\" name=\"phoneNum\" id=\"phoneNumHide\" value=\"\">");
	       	$("#phoneNumHide").val(mobileNumber);
	    	$("#phoneNum").attr("disabled","true")    
	    }
	}
});
var loginSubmitSwitch = false;
//点击登录
$("#loginSubmit").click(function() {
    var phoneNum = $("#phoneNum").val();
    if (!loginSubmitSwitch) {
        return false;
    }
    if (phoneNum == "" || phoneNum == null) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (!$("#password").val()) {
        errorMessage("请输入登录密码");
        return false;
    }
    //登录密码输入限制
/*
    if (! (/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test($("#password").val()))) {
        errorMessage("密码输入错误");
        return false;
    }
*/
    // $("#loginForm").submit();
    var verifycode = $("#verifycode").val();
    if (verifycode == "" || verifycode == null) {
        errorMessage("图片验证码为空");
        return false;
    }
   $("#loginForm").submit();

    localStorage.setItem("isUpdatePsdFirst",true);  //是否第一次登录  这个再merge里拿
});
//输入密码登录按钮状态
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
//密码可见
var eyePassword=0;
$("#eye").on("click",function(){
	if(eyePassword==0){
		$("#password").prop("type","text");
		$(".eyeImg").attr("src",contextPath + "/pic/weixin/passwordLogin/openEye.png");
		eyePassword=1;
	}
	else{
		$("#password").prop("type","password");
		$(".eyeImg").attr("src",contextPath + "/pic/weixin/passwordLogin/eye.png");
		eyePassword=0;
	}
});
$("#delete").on("click",function(){
	$("#password").attr("value","")
	mark()
})
$('input').bind('input propertychange', function() { 
	mark()
});
function mark(){
	var val=$("#password").val();
		if(val==""||val=="null"||val==null){
			$("#delete").addClass("none");
		}
		else{
			$("#delete").removeClass("none");
		}
	
}