var setStep1 = function(){
	$("#step1").show();
	$("#step2").hide();
}
var setStep2 = function(){
	$("#step2").show();
	$("#step1").hide();
}
var setActivitiyKCodeUse = function(data) {
	clickable = true;
	if(data.rescode === "00000") {
		$("#popup1").show();
    }else{
	    errorMessage(data.resmsg_cn);
    }
}

var setActivitiyPraiseUpload = function(data) {
	if (data.rescode == "00000") {
		errorMessage("上传成功");
		$("#popup3").show();
    }else{
	    errorMessage(data.resmsg_cn);
    }
}
var updateWeixin = function(){
	var localIds;
	wx.chooseImage({
	    count: 1, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        localIds = res.localIds[0]; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			wx.uploadImage({
			    localId: localIds, // 需要上传的图片的本地ID，由chooseImage接口获得
			    isShowProgressTips: 1, // 默认为1，显示进度提示
			    success: function (res) {
			        var serverId = res.serverId; // 返回图片的服务器端ID
			        ajaxRequest(contextPath+"wxactivity/activitiyPraiseUploadByWX","imgName=" + localIds + "&serverId=" + serverId, setActivitiyPraiseUpload);
			    }
			});
	    }
	});
}



wx.ready(function(){
	wx.onMenuShareTimeline({
	    title: shareTitle, // 分享标题
	    desc: shareDesc, // 分享描述
	    link: shareUrl, // 分享链接
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151029/test.jpg", // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	        ajaxRequest(contextPath+"wxactivity/activitiyShareNotify","",setActivitiyShareNotify);
	        $("#popup2").hide();
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
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151029/test.jpg", // 分享图标
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	        ajaxRequest(contextPath+"wxactivity/activitiyShareNotify","",setActivitiyShareNotify);
	        $("#popup2").hide();
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	        errorMessage("未分享");
	        $("#popup2").hide();
	    }
	});
			
/*
	wx.onMenuShareQQ({
	    title: shareTitle, // 分享标题
	    desc: shareDesc, // 分享描述
	    link: shareUrl, // 分享链接
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151029/test.jpg", // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	        ajaxRequest(contextPath+"wxactivity/activitiyShareNotify","",setActivitiyShareNotify);
	        $("#popup2").hide();
	    },
	    cancel: function () {
	       // 用户取消分享后执行的回调函数
	        errorMessage("未分享");
	        $("#popup2").hide();
	    }
	});
*/
});
var updateImg = function(obj) {
//	$.ajaxFileUpload({
//		url : "../wxactivity/activitiyPraiseUpload",
//        type: 'post',
//		secureuri : false,
//		fileElementId : 'imgFile',// file标签的id
//		dataType : 'json',// 返回数据的类型
//		data : {"rd" : new Date()},// 一同上传的数据
//		success : function(data, status) {
//			if (data.rescode == "00000") {
//				errorMessage("上传成功");
//				$("#popup3").show();
//		    }else{
//			    errorMessage(data.resmsg_cn);
//		    }
//		},
//		error : function(data, status, e) {
//			errorMessage(e);
//		}
//	});
}
var clickable = true;
$("#postCode").click(function(){
	if(!clickable){
	    errorMessage("请勿重复提交");
	    return false;
    }
	var kCode = $("#kCode").val();
    if(kCode == "" || kCode == null){
	    errorMessage("请输入K码");
	    return false;
    }
    if(kCode.length < 10) {
	   errorMessage("您输入的K码长度不正确");
	   return false; 
    }
	ajaxRequest(contextPath+"wxactivity/activitiyKCodeUse","kCode=" + kCode, setActivitiyKCodeUse);
	clickable = false;
})
$("#popBtn1").click(function(){
	$("#popup1").hide();
	setStep2();
})
$("#popBtn3").click(function(){
	$("#popup3").hide();
})
$("#popBtn4").click(function(){
	$("#popup4").hide();
})
$("#popBtn5").click(function(){
	location.reload(); 
})
$("#popBtn6").click(function(){
	location.reload(); 
})
$("#popBtn7").click(function(){
	$("#popup7").hide();
})
$("#popBtn8").click(function(){
	$("#popup8").hide();
})
var mobileNum;
var shareUrl = "http://wx.lianbijr.com/wxactivity/activity_20151103";
var shareTitle = "测试标题";
var shareDesc = "测试内容";
//var shareUrl = "http://licai.lincomb.com/activity/activity20151105/";
//var shareTitle = "斐讯路由器0元购，联璧钱包送理财"
// var desc = "斐讯路由器0元！0元！0元！你还在等什么？"
$(document).ready(function(){	
	
	$('#imgFile').UploadImg({
        url : '../wxactivity/activitiyPraiseUpload',
        width : '200',
        //height : '200',
        quality : '0.2', //压缩率，默认值为0.8
        // 如果quality是1 宽和高都未设定 则上传原图
        mixsize : 1024*1024*10,
        type : 'image/png,image/jpg,image/jpeg,image/pjpeg,image/gif,image/bmp,image/x-png',
        before : function(blob){
            //上传前返回图片blob
        },
        error : function(data){
            //上传出错处理
        	errorMessage(data);
        },
        success : function(data){
            //上传成功处理
			if (data.rescode == "00000") {
				errorMessage("上传成功");
				$("#popup3").show();
		    }else{
			    errorMessage(data.resmsg_cn);
		    }
        }
    });

	mobileNum = $("#mobile").val();
	var activityKCodeFlg = $("#activityKCodeFlg").val();
	var message = $("#message").val();
	if(message != "" && message != null){
		$("#popup5").show();
	}
	if(activityKCodeFlg == "Y"){
		setStep2()
	}else{
		setStep1()
	}

});
var setActivitiyShareNotify = function(data){
	if(data.rescode == "00000"){
    	//errorMessage("分享成功");
    	$("#popup4").show();
    }else{
    	errorMessage(data.resmsg_cn);
    }
}
var weixinPopShow = function(){
	
		$("#popup2").show();
		
}
var channel = $("#channel").val();
var ua = navigator.userAgent.toLowerCase();
if(channel == "LINGDANG"){
	if (/android/i.test(navigator.userAgent)){
		$("#LingdangAndroid").show();
		if(window._cordovaNative){
			$("#LingdangAndroid").click(function(){
				window._cordovaNative.onShare(shareTitle,shareDesc,shareUrl,contextPath+ "pic/weixin/activity/activity_20151029/test.jpg",mobileNum);
			})
		}
	}
	if (/ipad|iphone|mac/i.test(navigator.userAgent)){
		$("#LingdangIos").show();
/*
		$("#LingdangIos").click(function(){
			$("#popup8").show();	
		})
*/				
	}
}else if(channel == "WEBSITE"){
	
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		$("#weixin").show();	
		$("#weixin").click(function(){
			weixinPopShow();
		})
		$("#uplee").hide();
		$("#upleeWeixin").show();
	}else{
		if (/android/i.test(navigator.userAgent)){
			$("#lianbiAndroid").show();
			$("#viewMyAsset").hide();
			if(window._cordovaNative){
				$("#lianbiAndroid").click(function(){
					window._cordovaNative.onShare(shareTitle,shareDesc,shareUrl,contextPath+ "pic/weixin/activity/activity_20151029/test.jpg");
				})
			}
		}
		if (/ipad|iphone|mac/i.test(navigator.userAgent)){
			$("#lianbiIos").show();
			$("#viewMyAsset").hide();	
		}
	}
}

