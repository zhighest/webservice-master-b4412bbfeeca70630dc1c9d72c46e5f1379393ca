//点击签到之后刷新页面
var setUserWechatSign = function(data){
	if(data.rescode == "00000"){
		window.location.reload();//刷新页面
	}
	else {
	    errorMessage(data.resmsg_cn);
	}
}

var goUserWechatSign = function(){
	/*ajaxRequest(contextPath + "wxcoupons/userWechatSign", "", setUserWechatSign);*/
	ajaxRequest(contextPath + "wxcoupons/userWechatSign", "mobile="+mobile+"&signFlag="+signFlag, setUserWechatSign);
}
//setQueryUserCurrentSign方法的实现
var setQueryUserCurrentSign = function(data) {
	if(data.rescode == "00000"){
	    $("#explainText").html(data.activtyText);//获取文案内容，加入页面中的id=explainText中
	    if(data.currentSign == "Y"){//判断是否已经签到，如果已签到，则id=signInBtn的按钮样式改变，文字内容改变
	    	
	    	$("#signInBtn").html("已签到")
	    	.addClass('cannot');
	    }else if(data.currentSign == "N"){	
	    	$("#signInBtn").bind("click", function(){
	    		var ua = navigator.userAgent.toLowerCase();
	    		if(ua.match(/MicroMessenger/i)=="micromessenger"){
	    			alertBox("登录APP签到您将获得更多的加息收益，是否要继续在微信端签到？","goUserWechatSign",1);//弹出框
	    		}else{
	    			goUserWechatSign();
	    		}
	    		
	    	});
	    }
	}
	else {
	    errorMessage(data.resmsg_cn);
	}
}
var mobile;
var signFlag;
//调用获取用户当日签到信息的接口/wxcoupons/queryUserCurrentSign ，定义一个方法名setQueryUserCurrentSign
$(document).ready(function() {
	/*ajaxRequest(contextPath + "wxcoupons/queryUserCurrentSign", "", setQueryUserCurrentSign,"GET");*/
	mobile = $("#mobile").val();
	signFlag = $("#signFlag").val();
	ajaxRequest(contextPath + "wxcoupons/queryUserCurrentSign", "mobile="+mobile, setQueryUserCurrentSign,"GET");
});