//接口函数
function ajaxRequest(url, str, method, type) {
	var typeValue;
	if(type == null || type == "") {
		typeValue = "POST";
	} else {
		typeValue = type;
	}
	$.ajax({
		url: url,
		type: typeValue,
		dataType: "json",
		// data: port.encrypt(str),
		data: str,
		beforeSend: function(xhr) {
			var smDeviceId = getCookie("smDeviceId") || ""; //获取数美设备ID
			xhr.setRequestHeader('smDeviceId', smDeviceId); // 把数美的设备ID放入请求头里
		},
		success: function(data) {
			//method(port.decrypt(data));
			method(data);
		},
		error: function() {
			console.log("请求失败!");
		}
	});
};


window.onload = function(){
    var windowWith=$(window).width(); 
    if(windowWith<768){
    }else{
        // $("#qiyu").attr("src","https://qiyukf.com/script/ffe6600f40067cfc7e518d0cefe0b228.js?54545");
        $("#YSF-BTN-HOLDER").show();
    }
}
$(window).resize(function(event){
    var bodyWidth=$(this).width();
     if(bodyWidth<768){
        $("#YSF-BTN-HOLDER")?$("#YSF-BTN-HOLDER").hide():"";
    }else{
        // $("#qiyu").attr("src","https://qiyukf.com/script/ffe6600f40067cfc7e518d0cefe0b228.js?54545");
        $("#YSF-BTN-HOLDER")?$("#YSF-BTN-HOLDER").show():"";
    }
})   
var setAuthentication = function(data) {
    var rescode = data.rescode;
    var resmsg_cn = data.resmsg_cn;
    if (rescode != "00000") {
        errorMessage("实名认证失败！" + resmsg_cn);
    } else {
        window.location.reload();
    }
};
$("#authenticationBtn").click(function() {
    var nameCard = $("#nameCard").val();
    var idCard = $("#idCard").val();
    ajaxRequest(contextPath + "wxtrade/authentication", "nameCard=" + nameCard + "&idCard=" + idCard + "&pageSize=10", setAuthentication);
});
$("#next").click(function(event) {
    $(".alertBg,.alertBox").hide();
});
var showTip = function(tipText){
    $(".alertBg,.alertTipBox").stop().show();
    $("#tip").html(tipText);
}
var showTip1 = function(tipText){
    $(".alertBg,.alertTipBox1").stop().show();
    $("#tip1").html(tipText);
}
$("#know").click(function(event) {
    $(".alertBg,.alertTipBox").stop().hide();
});
$("#know1").click(function(event) {
    $(".alertBg,.alertTipBox1").stop().hide();
});
$("#logutBtn").click(function(){
	showTip1("退出登录？");
})


var userNameInfo = "";  //  姓名
var regularOrderInfo = "";  //定期理财
var mobile = "";  //手机号
var pointGrade = "";  //用户等级
var giftStatus = "";  //礼包资产
var demandGrade = ""; // 活期在投
var userPointGrade ="" // 积分等级
function setQueryUserInfoAndAcctInfo(data) {
	var resmsg_cn = data.resmsg_cn;
	if(data.rescode != "00000") {	
	}else{
		userNameInfo = data.userNameInfo;
		regularOrderInfo = data.regularOrderInfo;
        if(regularOrderInfo == "0"){
            regularOrderInfo = "无";
        }else{
            regularOrderInfo = "有";
        }
		mobile = data.mobile;
        pointGrade = parseInt(data.pointGrade);
        if(pointGrade == 0){
            if(parseInt(data.demandSum) > 0 || parseInt(data.regularSum)){
                pointGrade = 1;
            }
        }
        userPointGrade = data.pointGrade;
        giftStatus = data.giftStatus;
        if(giftStatus == "0"){
            giftStatus = "无";
        }else{
            giftStatus = "有";
        }
        demandGrade = data.demandGrade;
	}	
    // 七鱼客服代码
     ysf.config({
        level:pointGrade,
        data:JSON.stringify([
        {"key":"real_name", "value":userNameInfo},//名字
        {"key":"mobile_phone", "value":mobile},//手机
        {"key":"regularOrderInfo", "label":"定期理财",
        "value":regularOrderInfo},
        {"key":"userPointGrade",
        "label":"积分等级", "value":userPointGrade},
        {"key":"giftStatus",
            "label":"礼包资产", "value":giftStatus},
        {"key":"demandGrade",
            "label":"活期在投", "value":demandGrade}
        ])
    });	
}
$(document).ready(function() {
    // 超过一屏 
    var ScrolltoTop = $("#toTop");
    $(ScrolltoTop).hide();
    $(window).scroll(function() {
        if ($(window).scrollTop() == "0") {
            $(ScrolltoTop).fadeOut("slow")
        } else {
            $(ScrolltoTop).fadeIn("slow")
        }
    });
    $(ScrolltoTop).click(function() {
        $('html, body').animate({
            scrollTop: 0
        },700)
    })
	ajaxRequest(contextPath + "user/queryUserInfoAndAcctInfo", "", setQueryUserInfoAndAcctInfo);
	
	//关于我们下面的------公司声明
	var setServAgreementByType = function(data){ //协议判断ios/app
		var u = navigator.userAgent, app = navigator.appVersion;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		var serAgreementUrl=data.serAgreementUrl;
		var flag=serAgreementUrl.indexOf(";");
		serAgreementUrl2=serAgreementUrl.substring(0,flag);
		serAgreementUrl3=serAgreementUrl.substring(flag+1);
	    if (data.resmsg != "success") {
	        errorMessage(data.resmsg);
	    } else {
		    	if(isiOS){
		    	window.location.href = serAgreementUrl2;
			}
			else{
				window.location.href = serAgreementUrl3;
			}
	    }
	};
	
	$('.companyAnnouncements').click(function(){
		ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type=companyAnnouncements&from=ios",setServAgreementByType,"GET");
	})
	
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
	
})



