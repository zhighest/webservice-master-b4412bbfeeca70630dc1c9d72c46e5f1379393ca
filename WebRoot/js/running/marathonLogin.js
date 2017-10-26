var mobile;
var size;
var people;
var state= false;
var gainCodeSwitch = true;
$(document).ready(function() {
	 ajaxRequest(contextPath + "/running/getSurplusPeople", "", setSurplusPeople);
});
function setSurplusPeople(data) {
	if(data.resmsg == "success"){
		people = data.count;
		var html = '<li><span class="whiteTex fl font18">可报名人数</span></li>';
	    for (i = 0; i <= people.length - 1; i++) {
	        html += '<li class="whiteTex font26 ML5 strong positionR"><span class="positionA textNum">'+ people.substr(i, 1) +'</span><img src="' + contextPath + '/pic/running/numBkg.png" alt="" height="34"></li>';
	    }
	    $("#peopleNum").html(html); //累计收益
	}else{
		errorMessage("已参赛人数请求失败");
	}
}
$("#gainCode").click(function() {
	if (!gainCodeSwitch) {
        return false;
    }
    var mobile = $("#phoneNum").val();
    if (mobile == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(mobile))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    gainCodeSwitch = false;
    var curCount = 60;
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重发<br/>验证码");
            $("#gainCode").removeClass("forbid height46");
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
            $("#gainCode").addClass("forbid height46");
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    ajaxRequest(contextPath + "/running/getCaptcha", "mobile=" + mobile, goGetCaptcha);
});
function goGetCaptcha(data) {
    errorMessage("验证码发送成功");
}
$("#applySubmit").click(function() {
	mobile = $("#phoneNum").val();
	var checkCode = $("#checkCode").val();
    if (mobile == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(mobile))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (!$("#checkCode").val()) {
        errorMessage("请输入验证码");
        return false;
    }
    ajaxRequest(contextPath + "/running/checkCapche", "mobile=" + mobile + "&checkCode="+checkCode, setCheckCapche);
});
function setCheckCapche(data) {
	if(data.rescode == "00000"){
		ajaxRequest(contextPath + "/running/getEnrollInfo", "mobile=" + mobile , setEnrollInfo);
	}else{
		errorMessage(data.resmsg_cn);
	}
}
function setEnrollInfo(data) {
	if(data.rescode == "00001"){
		$("#phoneNum").attr("disabled","disabled");
		$("#checkCode").attr("disabled","disabled");
		$("#applySubmit").hide();
		$("#authentication").show();
	}else if(data.resmsg == "success"){
		if(data.enrollInfo.isPay != "Y"){
			window.location.href = contextPath + "/running/goMarathonPay?mobile="+mobile;
		}else{
			window.location.href = contextPath + "/running/goApplySuccess?mobile="+mobile;
		}
	}
}
$("#enterSubmit").click(function() {
	var nameCard = $("#nameCard").val();
	var idCard = $("#idCard").val();
	if (people == "" || people <=0) {
        errorMessage("已经报名满了");
        return false;
    }
	if(!state){
		errorMessage("请同意参赛须知");
        return false;
	}
	if(size == "" || size == null ){
		errorMessage("请选择衣服尺寸");
        return false;
	}
	if(nameCard == "" || nameCard == null ){
		errorMessage("请输入您的姓名");
        return false;
	}
	if(idCard == "" || idCard == null ){
		errorMessage("请输入身份证号码");
        return false;
	}
    ajaxRequest(contextPath + "/running/signUp", "mobile=" + mobile + "&realName=" + nameCard + "&idCard=" + idCard + "&materielSize=" + size, setSignUp);
});
function setSignUp(data) {
	if(data.rescode == "00000"){
		window.location.href = contextPath + "/running/goMarathonPay?mobile="+mobile;
	}else{
		errorMessage(data.resmsg_cn);
	}
}
$("#sizeBtn a").click(function(){
	size = $(this).attr("size");
	$(this).parent().addClass("active").siblings().removeClass("active")
});
$("#checkboxImg").click(function(){
	if(!state){
		state = true;
		$("#checkboxImg").attr("src",contextPath + "/pic/running/checkBox1.png");
	}else{
		state = false;
		$("#checkboxImg").attr("src",contextPath + "/pic/running/checkBox2.png");
	}
})
$("#agreenment").click(function() {
    $("#popup").show();
});
$("#ensureBtn").click(function() {
    $("#popup").hide();
});