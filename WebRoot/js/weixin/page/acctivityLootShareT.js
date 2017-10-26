var shareUrl;
var shareTitle;
var shareDesc;

$(document).ready(function() {
    var count = $("#count").val();
    $(".robbedNum").html(count);
	shareUrl = $("#goUrl").val();
	shareTitle = "天上掉馅饼 不领白不领";
	shareDesc = "独乐乐不如众乐乐，不看气质看朋友";
});

var setActivitiyShareNotify = function(data){
	if(data.msg != "success"){
    	errorMessage("分享成功");
    	//$("#popup4").show();
    }else{
    	errorMessage("分享加息成功");
    }
}

var receiveSwitch = true;
$("#receiveBtn").click(function() {
    if (!receiveSwitch) {
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
//     ajaxRequest(contextPath + "/wxuser/goGetCaptcha", "phoneNum=" + phoneNum + "", goGetCaptcha);
	$("#receiveForm").submit();
});


wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: shareTitle, // 分享标题
	    desc: shareDesc, // 分享描述
	    link: shareUrl, // 分享链接
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151202/icon.jpg", // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
//	        ajaxRequest(contextPath+"wxuser/shareIncreaseInterest","",setActivitiyShareNotify);
	        //$("#popup2").hide();
	        /*errorMessage("分享成功");*/
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	        errorMessage("未分享");
	        $("#popup2").hide();
	    }
	});
			
	wx.onMenuShareAppMessage({
	    title: shareTitle, // 分享标题
	    desc: shareDesc, // 分享描述
	    link: shareUrl, // 分享链接
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151202/icon.jpg", // 分享图标
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
//	        ajaxRequest(contextPath+"wxuser/shareIncreaseInterest","",setActivitiyShareNotify);
	        //$("#popup2").hide();
	       /* errorMessage("分享成功");*/
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	        errorMessage("未分享");
	        $("#popup2").hide();
	    }
	});
});


