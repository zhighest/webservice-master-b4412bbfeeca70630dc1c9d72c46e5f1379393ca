var setProductLoanList = function (data){
	if (data.list == null || data.list == "") {

    } else {
       var html = '';
       if (data.listSize > 3) {
            html += '<ul class="fourList clearfix">';
       }else{
            html += '<ul class="threeList clearfix">';
       };
       $.each(data.list, function(i, jsonObj) {
           if(jsonObj.planId == "108"){
                html += '<li class="fl border fix MB10">';
           }else{
                html += '<li class="fl border demand MB10">';
           };
           html += '<h2 class="blackTex1 font22">'+jsonObj.showName+'</h2>';
           html += '<div class="font16 rate MT20"><span class="font48 strong">'+jsonObj.annualRate+'</span>%</div>';
           html += '<div class="clearfix width90 blockC font12 grayTex MT20">';
           html += '<div class="fl">起投金额：<span>'+jsonObj.investMinAmount+'</span>元</div>';
           if(jsonObj.planId == "108"){
                html += '<div class="fr">期限：<span>'+jsonObj.remanPeriods+'</span>个月</div>'
           }else{
                html += '<div class="fr">期限：<span>0</span>天</div>'
           };
           html += '</div>';
           html += '<div class="blockC grayBkg positionR progress MT20 outHide">';
           html += '<div class="positionA progressLine" value="'+jsonObj.finishedRatio+'"></div>';
           html += '</div>';
           if(jsonObj.planId == "108"){
                html += '<p class="blackTex1 strong font18 MT20">高收益 本息保障</p>';
           }else{
                html += '<p class="blackTex1 strong font18 MT20">随存随取 本息保障</p>';
           };
           html += '</li>';
       });
       html += '</ul>';
       $("#productList").append(html);
       $(".progressLine").each(function(i,el) {
            $(this).animate({
                "width":$(this).attr('value')*1.95+'px'},500);
        });
    };
};
//进度条动画
$(".progressLine").animate({"width":$(this).width()+'px'},500)
$(document).ready(function() {
	ajaxRequest(contextPath + "wxproduct/getProductLoanList", "", setProductLoanList);
});
//网易云盾
var NetValidate = true;
var opts = {
		"element": "captcha_div", // 可以是验证码容器id，也可以是HTMLElement
		"captchaId": "32a42600dad34c6f92dc9b3eb6b47f49", // 这里填入申请到的验证码id
		"width": 340, // 验证码组件显示宽度
		"verifyCallback": function(ret){ // 用户只要有拖动/点击，就会触发这个回调
		  	if(ret['value']){ // true:验证通过 false:验证失败
		    // 通过 ret["validate"] 可以获得二次校验数据
		    	$("gainCode").removeAttr("disabled"); // 用户完成拖动之后再启用提交按钮
		    	NetValidate = false;
		    }
	    }
};
var ins = new NECaptcha(opts);






//点击获取短信验证返回事件
var gainCodeSwitch = true;
var setGoGetCaptcha = function(str) {
	if (str.rescode != "00000") {
        var me = $("#imgcode");
//      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        
        gainCodeSwitch = true;
	    errorMessage(str.resmsg_cn);
	    return;
	}

    var curCount = 60;
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重新发送");
            $("#gainCode").removeClass("grayBtn");
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
            $("#gainCode").addClass("grayBtn");
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
};
//点击登录返回事件
var setLogin = function(data){
	if (data.rescode == "00000") {
        window.location.href = contextPath + "webindex/goIndex";
		//ajaxRequest(contextPath + "wxtrade/showAssets","",setShowAssets);
	}else{
		goLoginBtn = true;
		errorMessage(data.resmsg_cn);
	};
};
//获取短信验证码	
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
//	if (verifycode == "" || verifycode == null) {
//      errorMessage("请输入图形验证码");
//      return false;
//  }
	//网易云盾验证
	if(NetValidate){
		errorMessage("请完成图形验证");
		return false;
	}
	//最后一次的验证码
	var NECaptchaValidate = ins.getValidate();
	NECaptchaValidate = window.encodeURIComponent(NECaptchaValidate);
	
	gainCodeSwitch = false;
    ajaxRequest(contextPath + "user/goGetCaptcha", "phoneNum=" + phoneNum + '&type=register'+"&NECaptchaValidate="+NECaptchaValidate+"&captchaId=32a42600dad34c6f92dc9b3eb6b47f49", setGoGetCaptcha);
    	    
});
//密码验证正则表达式
var passwordReg = /^[0-9]{6}$/;
//登录按钮
var goLoginBtn = true;
$("#loginSubmit").click(function(){
	if(!goLoginBtn){
		return false;
	}
    var password = $("#password").val();
    var loginPassword = $("#loginPassword").val();
    var checkCode = $("#checkCode").val(); 
    var phoneNum = $("#phoneNum").val();
    var verifycode = $("#verifycode").val();

    // var passwordAgin = $("#passwordAgin").val();
    if (phoneNum == "" || phoneNum == null) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (!$("#verifycode").val()) {
        errorMessage("请输入正确的图形验证码");
        return false;
    }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    if (loginPassword == null || loginPassword == '') {
        errorMessage("请输入登录密码");
        return false;
    }
     if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(loginPassword))){
        errorMessage("请输入6~16位字母和数字组合的登录密码");
        return false;
    };
    // if (password == null || password == '') {
    //     errorMessage("交易密码为空");
    //     return false;
    // }
    // // if(!passwordReg.test(password)){
    //     errorMessage('请填写6位数字的交易密码');
    //     return false;
    // }
    // if(passwordAgin == '' || passwordAgin == null){
    //     errorMessage("请再次输入交易密码");
    //     return false;
    // };
    // if(!(passwordReg.test(passwordAgin))){
    //     errorMessage("请完整填写交易密码");
    //     return false;
    // };
    // if(password !== passwordAgin){
    //     errorMessage("两次输入密码不一致，请重新输入");
    //     return false;
    // }
    goLoginBtn = false;
    ajaxRequest(contextPath + "/wxlanding/register", "phoneNum=" + phoneNum + "&channel=WEBLANDING&verifycode="+verifycode+"&captcha="+checkCode+"&isLogin=Y"+"&password="+loginPassword, setLogin);
});
//点击图片验证码
$("#imgcode").click(function(){
    var me = $(this);
    me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
});
//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
			$('.JsPhoneCall').html(PhoneCall);
		}
	});
}
getServiceCall();



// 扫一扫交互效果

// $('.closeBtn').click(function(){
//   $('.relation').animate({'left':-1920},1200,'easeOutExpo',function(){
//     $('.smallDownload').animate({'left':0},500,'linear')})
//   });

// $('.smallDownload').click(function(){
//   $(this).animate({'left':-193},500,'linear',function(){$('.relation').animate({'left':0},1200,'easeOutExpo')})
// })



// $(function(){
//    $('.relation').animate({'left':0},1200,'easeOutExpo')
// })



  // ,function(){$('.relation').animate({'left':-1920},3000,'linear')}