$(document).ready(function(){
	var shareUrl = "http://www.lianbijr.com/wxactivity/goLDActivityDetal?";
	var shareTitle = "全国【0元购】斐讯千兆无线路由器";
	var shareDesc = "联璧钱包送你2016新年大礼包！！！";
		wx.ready(function(){
			wx.onMenuShareTimeline({
			    title: shareTitle, // 分享标题
			    desc: shareDesc, // 分享描述
			    link: shareUrl, // 分享链接
			    imgUrl: bathPath+"/pic/weixin/activity/activityLCode/icon.jpg", // 分享图标
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			        //$("#popup2").hide();
			        errorMessage("分享成功");
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			        errorMessage("未分享");
			    }
			});
					
			wx.onMenuShareAppMessage({
			    title: shareTitle, // 分享标题
			    desc: shareDesc, // 分享描述
			    link: shareUrl, // 分享链接
			    type: 'link', // 分享类型,music、video或link，不填默认为link
			    imgUrl: bathPath+"/pic/weixin/activity/activityLCode/icon.jpg", // 分享图标
			    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			        //$("#popup2").hide();
			       errorMessage("分享成功");
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			        errorMessage("未分享");
			    }
			});
		});
	
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