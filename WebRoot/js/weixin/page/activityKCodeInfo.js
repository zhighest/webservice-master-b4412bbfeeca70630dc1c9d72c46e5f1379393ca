$(document).ready(function() {
   var isLogin = $("#isLogin").val();
   if(isLogin == "Y") {
	   $("#pop1").hide();
	    ajaxRequest(contextPath + "/wxactivity/findUseCompetency", "", setFindUseCompetency);
   }else {
	   $("#pop1").css("top",0);
   }
   $("#imgCodeTx").click(function(){
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
   
   $("#imgcodeWZ").click(function(){
       var me = $("#imgcode");
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
});
function setFindUseCompetency(data) {
	if(data.isUseFlg == false){//是否激活过
   		$("#typeOne").show();
   	}
   else{
   		$("#typeTwo").show();
   		//每个路由器型号
		$(".model1").html(data.list[0].routerModel);
		$(".model2").html(data.list[1].routerModel);
		//路由器激活数量
		$("#routerNumK1").html(data.list[0].routerNum);
		$("#routerNumK2").html(data.list[1].routerNum);
   		//K1路由器和K2路由器资格都达到上限
   		if(data.list[0].isToplimit == true && data.list[0].isToplimit == true){
   			$("#K1Box").hide();
   			$("#K2Box").hide();
   			$("#fullActivate").show();
   			return false;
   		};
   		//单独判断K1路由器是否达到上限
   		if(data.list[0].isToplimit == true){
   			$("#K1Box").hide();
   		}else{
   			if(data.list[0].useFlg == true){
   				$("#K1Tip").text("现可立即激活此台路由器");
   				$("#K1State").attr("onclick","goKCodeLogin()");
   			}else{
   				$("#K1Tip").text("若需激活，需要投资"+data.list[0].needAmount+"元");
   				$("#K1State").html("立即<br/>投资").attr("onclick","goFixBuy()");
   			};
   		};
   		//单独判断K2路由器是否达到上限
   		if(data.list[1].isToplimit == true){
   			$("#K2Box").hide();
   		}else{
   			if(data.list[1].useFlg == true){
   				$("#K2Tip").text("现可立即激活此台路由器");
   				$("#K2State").attr("onclick","goKCodeLogin()");
   			}else{
   				$("#K2Tip").text("若需激活，需要投资"+data.list[1].needAmount+"元");
   				$("#K2State").html("立即<br/>投资").attr("onclick","goFixBuy()");
   			};
   		};	
   	}	
}
//跳转到投资页
function goFixBuy(){
	window.location.href = contextPath + "wxproduct/goDemandProductIndex";
}
//跳转到激活页
function goKCodeLogin(){
	window.location.href = contextPath + "wxactivity/goActivityKCodeLogin";
}

//网易易盾变量
var ins;
var verificationStatus = true;


//获取验证码
var gainCodeSwitch = true;
var loginSubmitSwitch = false;
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
//  var verifycode= $("#imgVerifycode").val();
//  if (verifycode == "") {
//      errorMessage("请输入图片验证码");
//      return false;
//  }
	
	//网易云盾
	if(verificationStatus){
		alertVerification('',phoneNum,'wxlogin');
	}else{
		alertVerification('',phoneNum, 'wxlogin',true);
		$("#verificationStatus").html('');
	}
});
var InterValObj;
function goGetCaptcha(data) {
	//网易易盾
	$('#Verification').remove();
    verificationStatus  = false;
	
	if (data.rescode != "00000") {
        var me = $("#imgCodeTx");
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
            $("#gainCode").html("重发<br/>验证码").css("lineHeight","1.2em");
            $("#gainCode").removeClass("forbid");
            ins.refresh();
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s").css("lineHeight","36px");
            $("#gainCode").addClass("forbid");
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
}
//点击登录
var checkboxState = true;
$("#loginSubmit").click(function() {
	var phoneNum = $("#phoneNum").val();
	var checkCode = $("#checkCode").val();
    if (!loginSubmitSwitch) {
        return false;
    }
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    } 
//  if (!$("#imgVerifycode").val()) {
//      errorMessage("请输入图片验证码");
//      return false;
//  }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    ajaxRequest(contextPath + "/wxactivity/activityLogin", "mobile=" + phoneNum + "&checkCode="+ checkCode+"", getActivityLogin);
});
function getActivityLogin(data) {
	if(data.rescode == "00000"){
		window.location.reload();
    }else{
        errorMessage(data.resmsg_cn);
    }
}
//点击登录按钮状态
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