$(document).ready(function() {
	var userId = $('#userId').val();
	if(userId) { //已登录
		$('.rigistered').css('display', 'block');
		$('.bannerContent').css('display', 'none');
		$('#imgRiOrLogin').attr('src',contextPath+'pic/weixin/anniversary/banner.jpg');
	} else { //未登录
		$('#imgRiOrLogin').attr('src',contextPath+'pic/weixin/anniversary/bannerR.jpg');
	}

	//初始化 
	$('.animates img').css('display', 'none');
	$($('.animates')[0]).children().css('display', 'block');
	//关闭按钮
	$('.close').click(function() {
		$('.DownLoadApp').remove();
	})
	//立即使用按钮
	$('.rightNowUse').click(function() {
		mySwiper.slideTo(1, 1000, false);
	});
	//选择是否同意注册
	$('#unchoice').click(function() {
		$('#choice').css('display', 'inline-block');
		$(this).css('display', 'none');
		choiceOrUnchoice = true;
	});
	$('#choice').click(function() {
		$('#unchoice').css('display', 'inline-block');
		$(this).css('display', 'none');
		choiceOrUnchoice = false;
	})
	
	
	
})
var mySwiper = new Swiper('.swiper-container', {
		direction: 'vertical',
		loop: false,
		// 如果需要分页器
		pagination: '.swiper-pagination',
		// 如果需要前进后退按钮
		nextButton: '.swiper-button-next',
		prevButton: '.swiper-button-prev',
		// 如果需要滚动条
		scrollbar: '.swiper-scrollbar',
		onSlideChangeEnd: function(swiper) {
			$('.animates img').css('display', 'none');
			$($('.animates')[swiper.activeIndex]).children().css('display', 'block');
		}
	})

function checkC(){
	
}


//网易易盾变量
var ins;
var verificationStatus = true;

var choiceOrUnchoice = true; 	//用户协议是否选择
var gainCodeSwitch = true;		//
var loginSubmitSwitch = false;	//防止二次点击

var password;	//登录密码
var phoneNum;	//手机号
var verifycode;	//交易密码
var checkCode;	//验证码
var invitationCode = $('#mobileWX').val();	//邀请码
	var channel = $('#channel').val();

//发送验证码
$("#Countdown").click(function() {
	if(!gainCodeSwitch) {
		return false;
	}
	phoneNum = $("#phoneNum").val();

	if(phoneNum == "") {
		errorMessage("请输入手机号");
		return false;
	}
	if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
		errorMessage("请输入正确的手机号码");
		return false;
	}
	//网易云盾
	if(verificationStatus) {
		alertVerification('', phoneNum, 'register');
	} else {
		alertVerification('', phoneNum, 'register', true);
		$("#verificationStatus").html('');
	}
});

//验证码回调
function goGetCaptcha(data) {
	//网易易盾
	$('#Verification').remove();
	verificationStatus = false;

	if(data.rescode != "00000") {
		errorMessage(data.resmsg_cn);
		ins.refresh();
		return;
	}
	gainCodeSwitch = false;
	var curCount = 60;

	function SetRemainTime() {
		if(curCount == 0) {
			window.clearInterval(InterValObj); //停止计时器
			gainCodeSwitch = true;
			$("#Countdown").html("重发<br/>验证码");
			$("#Countdown").removeClass("forbid codeSecond");
			ins.refresh();
		} else {
			curCount--;
			$("#Countdown").html(curCount + "s");
			$("#Countdown").addClass("forbid codeSecond");
		}
	}
	InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	errorMessage("验证码发送成功");
}

//点击注册
$("#loginSubmit").click(function() {

	phoneNum = $("#phoneNum").val();
	password = $("#passWord").val(); //登录密码
	checked = $('#Agreement').prop("checked"); //复选框状态
	verifycode = $("#verifycode").val();	//交易密码
	checkCode = $("#checkCode").val();		//验证码

	if(!loginSubmitSwitch) {
		loginSubmitSwitch = false;
	}
	if(!$("#phoneNum").val()) {
		errorMessage("请输入手机号");
		return false;
	}
	if(!(/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
		errorMessage("请输入正确的手机号码");
		return false;
	}
	//登录密码判断
	if(password == '' || password == null) {
		errorMessage("请输入登录密码");
		return false;
	};
	if(password.length < 8) {
		errorMessage('登录密码为8~16位字母数字或符号的组合');
		return false;
	};
	if(/^[\d]+$/.test(password) || /^[a-zA-Z]+$/.test(password)) {
		errorMessage('您当前设置的密码等级低，存在一定风险，请重新设置');
		return false;
	};
	if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(password))) {
		errorMessage('登录密码为8~16位字母数字或符号的组合');
		return false;
	}
	if(!$("#checkCode").val()) {
		errorMessage("请输入短信验证码");
		return false;
	}
	if(!verifycode) {
		errorMessage("请输入交易密码");
		return false;
	}
	if(!(/^\d{6}$/.test(verifycode))){
		errorMessage("交易密码必须6位数字");
		return false;
	}
	if(!choiceOrUnchoice){
		errorMessage("请同意用户注册服务协议");
		return false;
	}
	ajaxRequest(contextPath + "/wxlanding/register", "phoneNum=" + phoneNum + "&channel=" + channel + "&captcha=" + checkCode + "&password=" + password +'&passwordCash='+verifycode+'&invitationCode='+invitationCode + '&isLogin=Y', getRegister);
	});

//注册成功回调
function getRegister(res) {
	errorMessage(res.resmsg_cn);
	if(res.rescode == '00000') {
		window.location.href = contextPath + 'wxtrade/goMyAsset?from=LBWX';
	}
}

//协议跳转
$("#registration").click(function() {
	ajaxRequest(contextPath + "/wxagreement/getServAgreementByType", "type=register&productId=107", setServAgreementByType, "GET");
})
var setServAgreementByType = function(data) {
	var u = navigator.userAgent,
		app = navigator.appVersion;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	var serAgreementUrl = data.serAgreementUrl;
	var flag = serAgreementUrl.indexOf(";");
	serAgreementUrl2 = serAgreementUrl.substring(0, flag);
	serAgreementUrl3 = serAgreementUrl.substring(flag + 1);
	if(data.resmsg != "success") {
		errorMessage(data.resmsg);
	} else {
		if(isiOS) {
			window.location.href = serAgreementUrl2;
		} else {
			window.location.href = serAgreementUrl3;
		}
	}
};