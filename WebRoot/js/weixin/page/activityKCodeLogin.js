//报错弹框
function alertBox(tips,type){
    var html='';
    html += '<div class="width100 height100P positionF" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">';
    html += '<div class="radius5 whiteBkg width80 positionA textC" style="height:190px;left:10%;top:50%;margin-top: -95px; box-shadow: 0px 0px 3px #666;">';
    html += '<h3 class="font20 PT15 redTex">提&nbsp;示</h3>';
    html += '<p class="font16 blackTex lineHeight1_5x" style="height:75px;padding:15px 5% 0;">'+tips+'</p>';
    if(type == 1){
        html += '<a class="inlineB redBkg width35 ML10 MR10 grayTex PT5 PB5 font16" onclick="$(\'#alertBox\').remove();">取消</a>';
    }
    html += '<a class="inlineB redBkg width35 ML10 MR10 whiteTex PT5 PB5 font16" onclick="$(\'#alertBox\').remove();">确 定</a>';
    html += '</div></div>';
    $("body").append(html);
}

var mobile;
var channel = $("#channel").val();
$(document).ready(function() {
	$("body").hide().css("background","none")	
   	ua = navigator.userAgent.toLowerCase();
   	var KSwitch = $("#KSwitch").val(); //关掉微信激活功能弹框
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
// 		$("#pop3").show(); //关掉微信激活功能弹框
	   	if(KSwitch== "off") {
		   	$("#pop3").hide();
	   	}
		$("#btmQrcode").show();
    }else{
    	$("#btmQrcode").hide();
    } 
    /*else {  
        $("#pop1").hide();
        if(mobile == '' && isLogin == 'N') {
            $("#popup1").show();
        }else{
            $("#popup1").hide();
        }
    }*/
    //判断登录跳转
    if(KSwitch== "off") {
	    $("body").show().css("background","#DE1620")
	    var isLogin = $("#isLogin").val();
	    mobile = $("#mobile").val();
	    $("#popup1").hide();
	    if(isLogin == "Y") {
	        $("#pop1").hide();
	    }else {
	        if(ua.match(/MicroMessenger/i)=="micromessenger"){
	            $("#pop1").css("top",0);
	        } else {
	        	if (mobile == '') {
	            	if(channel == 'LBAndroid'){
	                    if(window._cordovaNative){
	                        window._cordovaNative.goLogin();
	                    } 
	                }else if(channel == 'LBIOS'){
	                    goLogin();
	                }
	        	} else {
	                $("#pop1").hide();
	        	}
	        }
	    };
		   //登录验证码点击变化
		   $("#imgCodeTx").click(function(){
		       var me = $(this);
		       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
		   });
		   //激活K码验证码点击变化
		   $("#imgcode").click(function(){
		       var me = $("#imgcode");
		       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
		   });
		//K码资格查询接口   ajaxRequest(contextPath + "/wxactivity/findUseCompetency", "mobile="+mobile, setFindUseCompetency);
	   }
	   else {
			//页面强制跳转
			$("body").hide().css("background","none");
			window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai";  
	   } 
});
//获取K码激活信息
function setFindUseCompetency(data){
    if(data.isToplimit == true){//是否达到激活上限
       // $("#fullActivate").show();
        //去首页按钮判断APP和微信
        if(ua.match(/MicroMessenger/i)=="micromessenger"){
			$("#weixinGoIndex").css("display","block");	
			$("#weixinGoIndex").click(function(event) {
		   		window.location.href = contextPath + "wxabout/goIndex";
			});
		}else if (channel == 'LBAndroid'){
			$("#lianbiAndroidGoIndex").css("display","block");	
			if(window._cordovaNative){
				$("#lianbiAndroidGoIndex").click(function(){
					window._cordovaNative.goIndex();
				})
			}		
		}else if (channel == 'LBIOS'){
				$("#lianbiIosGoIndex").css("display","block");
				$("#lianbiIconGoIndex").attr( "onclick", "goIndex()");	
				
		}else{
			errorMessage("请关注微信公众号联璧钱包或者下载联璧金融APP");
		}
	    
        $("#hasActivate").hide();
        $("#noActivate").hide();
    }else{
/*
      //  $("#fullActivate").hide();
        $("#hasActivate").show();
        if(data.useFlg == true){
            $("#noActivate").hide();
          //  $("#nowUseNum").html(data.nowUseNum);//当前激活台数
            //$("#canUseNum").html(data.canUseNum);//当前可激活台数
          //  $("#totalToplimitNum").html(data.totalToplimitNum);//激活台数上限
        }else{
            $("#noActivate").show();
            $("#hasActivate").hide();
            $("#remanPeriods").html(data.remanPeriods);//激活所需投资期限
            $("#needAmount").html(data.needAmount);//激活所需投资金额
        }
*/
    }
}


//网易云盾
var NetValidate = true;
var tmpWidth = 	$('.captcha_div').width();
	tmpWidth = parseInt(tmpWidth);
var opts = {
		"element": "captcha_div", // 可以是验证码容器id，也可以是HTMLElement
		"captchaId": "32a42600dad34c6f92dc9b3eb6b47f49", // 这里填入申请到的验证码id
		"width":tmpWidth, // 验证码组件显示宽度
		"verifyCallback": function(ret){ // 用户只要有拖动/点击，就会触发这个回调
		  	if(ret['value']){ // true:验证通过 false:验证失败
		    // 通过 ret["validate"] 可以获得二次校验数据
		    	$("gainCode").removeAttr("disabled"); // 用户完成拖动之后再启用提交按钮
		    	NetValidate = false;
		    }
	    }
  };
var ins = new NECaptcha(opts);



var gainCodeSwitch = true;
var loginSubmitSwitch = false;
//登录框点击获取验证码事件
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
	//网易云盾验证
	if(NetValidate){
		errorMessage("请完成图形验证码");
		return false;
	}
	//最后一次的验证码
	var NECaptchaValidate = ins.getValidate();
	NECaptchaValidate = window.encodeURIComponent(NECaptchaValidate);
	
    ajaxRequest(contextPath + "/wxuser/goGetCaptcha", "phoneNum=" + phoneNum + "&type=register"+'&NECaptchaValidate='+NECaptchaValidate+"&captchaId=32a42600dad34c6f92dc9b3eb6b47f49", goGetCaptcha);
});
var InterValObj;
function goGetCaptcha(data) {
	if (data.rescode != "00000") {
//      var me = $("#imgCodeTx");
//      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random()); 
		ins.refresh();
	    errorMessage(data.resmsg_cn);
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
//点击登录事件
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
//活动规则选择事件
$("#checkboxImg").click(function(){
	if(!checkboxState) {
		checkboxState = true;
		$(this).attr("src",contextPath + "pic/weixin/activity/activityLCode/checkBox1.png");
	}else {
		checkboxState = false;
		$(this).attr("src",contextPath + "pic/weixin/activity/activityLCode/checkBox2.png");
	}
})
function getActivityLogin(data) {
	if(data.rescode == "00000"){
		$("#pop1").hide();
		 var me = $("#imgcode");
//	      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
		ajaxRequest(contextPath + "/wxactivity/findUseCompetency", "mobile="+$("#phoneNum").val(), setFindUseCompetency);
    }else{
        errorMessage(data.resmsg_cn);
    }
}
//点击激活按钮
$("#postCode").click(function() {
	if(!checkboxState) {
		errorMessage("请先阅读并同意活动规则");
		return false;
	}
	setActivitiyKCodeUse();
});
//激活文案修改
function ajaxNewRequest(url, str, method, type) {
    for (var i = 0; i < PD.length; i++) {  
        if (PD[i] == url){
            return false;
        }
    }
    PD.push(url);
    var typeValue;
    if(type==null || type==""){ 
        typeValue = "POST";
    }else{
        typeValue = type;
    }
    $.ajax({
        url: url,
        type: typeValue,
        dataType:"json",
        data: str,
        success: function(data) {
            PD.remove(url);
            hideLoading();
            method(data);
        },
        error: function() {
            PD.remove(url);
            hideLoading();
        }
    });
};
//点击激活事件
function setActivitiyKCodeUse() { 
	var token_id=$("#token_id").val();
	var kCode = $("#kCode").val();
    var verifycode = $("#verifycode").val();
	if (kCode == "" || kCode == null) {
        errorMessage("请输入K码");
        return false;
    }
    if (kCode.length < 8 || kCode.length > 10) {
        errorMessage("无效K码,请重新输入！<br/>若有疑问请点击画面右上角图标联系在线客服");
        return false;
    }
	if (verifycode == "" || verifycode == null) {
        errorMessage("请输入验证码");
        return false;
    }
	ajaxNewRequest(contextPath + "/wxactivity/activitiyKCodeUse", "kCode=" + kCode + "&verifycode="+verifycode+ "&mobile="+mobile, getActivitiyKCodeUse); 
}
//激活是否成功事件
function getActivitiyKCodeUse(data) {
	if(data.rescode == "00000"){
		if(data.type=="licai"){
			window.location.href = contextPath + "wxactivity/goActivityKCodeSuccess?mobile=" + mobile;
		}
		else if(data.type=="libao"){
			window.location.href = contextPath + "wxcoupons/giftBag?type=libao&mobile=" + mobile;
		}	
    }else{
        var me = $("#imgcode");
        me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)	//验证码不正确
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	//达到上限(非联通手机)
			}
			else if(data.errorMessage.message_level == "2") {
				alertBox(data.errorMessage.message_content,2)
			}
			else if(data.errorMessage.message_level == "3"){
				$("#white").removeClass("none"); 
		    	$("#wrap").addClass("popup");
			}
			else if(data.errorMessage.message_level == "4"){
				$("#newWhite").removeClass("none"); 
		    	$("#wrap").addClass("popup");
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
        
	}			
}
//短信验证码输入时登录按钮状态
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
var windowHeight = $("window").height();
$("#kCode").focus(function(){
	$(".loginBkg").css("min-height",windowHeight);
	if(windowHeight <= 480){
		setTimeout('$("body").scrollTop(250)',100);
	}else {
		setTimeout('$("body").scrollTop(300)',100);
	}
	
});
$("#verifycode").focus(function(){
    $(".loginBkg").animate({
        "padding-bottom":"100px"},100);   
});
$("#verifycode").blur(function(){
    $(".loginBkg").animate({
        "padding-bottom":"50px"},100);  
});
//查看K码弹出
$("#tips").click(function() {
	$("#pop2").show();
});
//弹出K码关闭
$(".closedLetterBtn").click(function() {
	$("#pop2").hide();
});
//立即投资点击
var ua = navigator.userAgent.toLowerCase();
if(ua.match(/MicroMessenger/i)=="micromessenger"){
	$("#weixinGoFixInvest").css("display","block");	
	$("#weixinGoFixInvest").click(function(event) {
   		window.location.href = contextPath + "wxproduct/goDemandProductIndex?fixDemandSwitch=fix";
   		sessionStorage.setItem("from","fixIndex");
	});
}else if (channel == 'LBAndroid'){
	$("#lianbiAndroidGoFixInvest").css("display","block");	
	if(window._cordovaNative){
		$("#lianbiAndroidGoFixInvest").click(function(){
			window._cordovaNative.goFixInvest();
		})
	}		
}else if (channel == 'LBIOS'){
		$("#lianbiIosGoFixInvest").css("display","block");
		$("#lianbiIconGoFixInvest").attr( "onclick", "goFixInvest()");	
		
}else{
	
};


//添加联通手机号
(function rem(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/12.42+"px";
	window.addEventListener("resize",setRenSize,false);
	function setRenSize(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/12.42+"px";
	}
	
})();
//输入验证码确认状态改变
function checkA(dom,dom1) {	
    var checkCode = $(dom).val();
    if (checkCode == "") {
        $(dom1).addClass("active");
        loginSubmitSwitch = false;
    } else {
        $(dom1).removeClass("active");
        loginSubmitSwitch = true;
    }
};
//判断非微信
function IsPC(){
    var channel = $("#channel").val(),
     flag = 1;
    if(channel == "LBAndroid"||channel == "LBIOS"){
		flag = 2;
	}	
	else{
		 flag = 1;
	}
    return flag;
};
//点击验证码验证
$("#sms").on("click",function(){
	 if (!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNumber").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    ajaxRequest(contextPath + "/wxuser/goGetCaptchaByKcode", "phoneNum=" + phoneNum+"&type=WXLOGIN&setupFlag=" +IsPC()+"", goGetCaptchaNew);
});
function goGetCaptchaNew(data) {
	var $sms= $("#sms")
if (data.rescode != "00000") {
	    errorMessage(data.resmsg_cn);
	    return;
	} 
    gainCodeSwitch = false;
    var curCount = 60;
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $sms.html("重发验证码")
            $sms.removeClass("forbid");
        } else {
            curCount--;
            $sms.html(curCount + "s")
            $sms.addClass("forbid");
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
};
//点击确认按钮验证
$("#confirm").on("click",function(){
    var phoneNum = $("#phoneNumber").val();
    var verifycode=$("#code").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (verifycode == "" || verifycode == null) {
        errorMessage("请输入验证码");
        return false;
    }
     
    ajaxRequest(contextPath + "/wxuser/checkMoblie", "phoneNum=" + phoneNum + "&checkCode="+ verifycode+"", checkSuccess);
});
$("#newConfirm").on("click",function(){
    var kCode = $("#kCode").val();
    mobile = $("#mobile").val();
    var verifycode = $("#verifycode").val();
    var salemanMobile=$("#newCode").val();
    if (salemanMobile == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(salemanMobile))) {
        errorMessage("请输入有效的邀请人手机号码");
        return false;
    }
    ajaxNewRequest(contextPath + "/wxactivity/activitiyKCodeUse", "kCode=" + kCode + "&verifycode="+verifycode+ "&mobile="+mobile+"&salemanMobile=" + salemanMobile, getNewActivityLogin); 
});
$("#close").on("click",function(){
	$('#white').addClass('none');$('#wrap').removeClass('popup');$('#phoneNumber').val('');$('#code').val('');
});
$("#close1").on("click",function(){
	$('#newWhite').addClass('none');$('#wrap').removeClass('popup');$('#newCode').val('');
});
function checkSuccess(data){
    if(data.rescode == "00000"){
    var unionMobile = $("#phoneNumber").val();
    var kCode = $("#kCode").val();
    mobile = $("#mobile").val();
    var verifycode = $("#verifycode").val();
    ajaxNewRequest(contextPath + "/wxactivity/activitiyKCodeUse", "kCode=" + kCode + "&verifycode="+verifycode+"&unionMobile="+unionMobile+"&mobile="+mobile, getNewActivityLogin); 
    }
    else{    
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
            errorMessage(data.resmsg_cn)
        }
        else{
            if(data.errorMessage.message_level == "1"){
                errorMessage(data.errorMessage.message_content) 
            }
            else if(data.errorMessage.message_level == "2") {
                alertBox(data.errorMessage.message_content,2)
            }
            else {
                errorMessage(data.resmsg_cn);   
            }
        }
    }   
};
function getNewActivityLogin(data){
	if(data.rescode == "00000"){
		$('#phoneNumber').val('');
		$('#code').val('');
		if(data.type=="licai"){
			window.location.href = contextPath + "wxactivity/goActivityKCodeSuccess?mobile=" + mobile;
		}
		else if(data.type=="libao"){
			window.location.href = contextPath + "wxcoupons/giftBag?type=libao&mobile=" + mobile;
		}
    }else{
    	
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	
				if(data.errorMessage.message_name == "EM000000022"){
					$('#newWhite').addClass('none');
					$('#wrap').removeClass('popup')
					$('#newCode').val('');
				}
			}
			else if(data.errorMessage.message_level == "2") {
				alertBox(data.errorMessage.message_content,2)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}			
}
function service(a){
	var newTop=($(document).height());
	$(a).on("focus",function(){
		var newTop2=$(document).height();
		var top=newTop2-newTop+0.02*newTop2+35;
		$(".meiqiaIcon").css({
			  "position":"absolute",
			  "top":top+"px"
			  });
	})
	var falg=0;
	$(document).on("touchstart",function(){
			$(".meiqiaIcon").css({
			  "position":"fixed",
			  "top":2+"%"
			  });
			$(a).blur()
	})
	$(a).on("blur",function(){
		$(".meiqiaIcon").css({
			  "position":"fixed",
			  "top":2+"%"
			  });
	})
}
//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxNewRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
		}
	});
}
getServiceCall();

$(".meiqiaIcon").click(function(){
    errorMessage("您可以拨打客服电话"+PhoneCall+"或者关注微信公众号【联璧钱包】联系在线客服");
});
service("#kCode");
service("#verifycode");
