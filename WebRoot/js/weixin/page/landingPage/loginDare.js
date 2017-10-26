$(document).ready(function() {
    if (message) {
        errorMessage(message);
    }
    var mobileNumber = $("#mobileNumber").val();
	if(mobileNumber !== "" && mobileNumber !== null){
		$("#phoneNum").val(mobileNumber);
		$("#phoneMask").show();
	}
	
   $("#imgcode").click(function(){
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
});
var gainCodeSwitch = true;
var loginSubmitSwitch = false;
var traderPassword;
// var repetition;
var password;
// var repetPassWord;
var phoneNum;
var verifycode;
var checkCode;
$("#gainCode").click(function() {
    if (!gainCodeSwitch) {
        return false;
    }
    phoneNum = $("#phoneNum").val();
    traderPassword = $("#traderPassword").val();//交易密码
//     repetition = $("#repetition").val();//确认交易密码
    password = $("#passWord").val();//登录密码
//     repetPassWord = $("#repetPassWord").val();//确认登录密码
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    //登录密码判断
    if(password == '' || password == null){
        errorMessage("请输入登录密码");
        return false;
    };
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(password))){
        errorMessage('登录密码为6~16位字母数字组合');
        return false;
    }
/*
    if(repetPassWord == '' || repetPassWord == null){
        errorMessage("再次输入登录密码为空");
        return false;
    };
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(repetPassWord))){
        errorMessage("请再次输入6~16位字母和数字组合的登录密码");
        return false;
    };
    if(password !== repetPassWord){
        errorMessage("两次输入登录密码不一致，请重新输入");
        return false;
    }
*/
    //交易密码判断
    if(traderPassword == '' || traderPassword == null){
        errorMessage("交易密码为空");
        return false;
    }; 
    if(!(/^[0-9]{6}$/.test(traderPassword))){
        errorMessage("请填写6位数字的交易密码");
        return false;
    };
/*
    if(repetition == '' || repetition == null){
        errorMessage("再次输入交易密码为空");
        return false;
    };
    if(!(/^[0-9]{6}$/.test(repetition))){
        errorMessage("请再次输入6位数字的交易密码");
        return false;
    };
    if(traderPassword !== repetition){
        errorMessage("两次输入交易密码不一致，请重新输入");
        return false;
    }
*/
   
    var verifycode = $("#verifycode").val();
	if (verifycode == "" || verifycode == null) {
        errorMessage("请输入图形验证码");
        return false;
    }

    ajaxRequest(contextPath + "/wxuser/goGetCaptcha", "phoneNum=" + phoneNum + "&verifycode="+verifycode, goGetCaptcha);
});
$("#loginSubmit").click(function() {
	phoneNum = $("#phoneNum").val();
    password = $("#passWord").val();//登录密码
//     repetPassWord = $("#repetPassWord").val();//确认登录密码
    traderPassword = $("#traderPassword").val();//交易密码
//     repetition = $("#repetition").val();//确认交易密码
    verifycode = $("#verifycode").val();
    checkCode = $("#checkCode").val();

    if (!loginSubmitSwitch) {
        loginSubmitSwitch = false;
    }
    if (!$("#phoneNum").val()) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
   //登录密码判断
   if(password == '' || password == null){
        errorMessage("请输入登录密码");
        return false;
    };
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(password))){
        errorMessage('登录密码为6~16位字母数字组合');
        return false;
    }
/*
    if(repetPassWord == '' || repetPassWord == null){
        errorMessage("再次输入登录密码为空");
        return false;
    };
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(repetPassWord))){
        errorMessage("请再次输入6~16位字母和数字组合的登录密码");
        return false;
    };
    if(password !== repetPassWord){
        errorMessage("两次输入登录密码不一致，请重新输入");
        return false;
    }
*/
    //交易密码判断
    if(traderPassword == '' || traderPassword == null){
        errorMessage("交易密码为空");
        return false;
    }; 
    if(!(/^[0-9]{6}$/.test(traderPassword))){
        errorMessage("请填写6位数字的交易密码");
        return false;
    };
/*
    if(repetition == '' || repetition == null){
        errorMessage("再次输入交易密码为空");
        return false;
    };
    if(!(/^[0-9]{6}$/.test(repetition))){
        errorMessage("请再次输入6位数字的交易密码");
        return false;
    };
    if(traderPassword !== repetition){
        errorMessage("两次输入交易密码不一致，请重新输入");
        return false;
    }
*/
    //图片验证码
    if (!$("#verifycode").val()) {
        errorMessage("请输入正确的图形验证码");
        return false;
    }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }

	ajaxRequest(contextPath + "/wxlanding/register", "phoneNum=" + phoneNum + "&channel=xiaogandui&passwordCash="+ traderPassword+ "&verifycode="+verifycode+"&captcha="+checkCode+"&password="+password, getRegister);
});
var InterValObj;
function goGetCaptcha(data) {
	if (data.rescode != "00000") {
        var me = $("#imgcode");
        me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        
	    errorMessage(data.resmsg_cn);
	    return;
	}
    gainCodeSwitch = false;
    var curCount = 60;
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重发<br/>验证码");
            $("#gainCode").removeClass("forbid codeSecond");
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
            $("#gainCode").addClass("forbid codeSecond");
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
//激活是否成功事件
var URL = $("#URL").val();
function getRegister(data) {
	if(data.rescode == "00000"){
		function goIndex(){
			window.location.href = URL+ "/webabout/download";	
		}
		setTimeout(goIndex, 1000)
		
    }else{
        errorMessage(data.resmsg_cn);
    }
}
/*监听滚动条，显示或者隐藏某元素*/
window.onscroll = function(){
    var t = document.documentElement.scrollTop || document.body.scrollTop; 
    var subBox = document.getElementById( "arrowDown" );//要显示或者隐藏的元素
    if( t <= 1200 ) {
        subBox.style.display = "block";
    } else {
        subBox.style.display = "none";
    }
}
$("#closed").click(function(){
	$("#btmAreaFix").hide();
})