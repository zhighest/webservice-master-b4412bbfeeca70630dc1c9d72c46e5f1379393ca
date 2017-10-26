$(document).ready(function(){
	var shareUrl = "http://www.lianbijr.com/wxactivity/activity_20151103?";
	var shareTitle = "联璧金融";
	var shareDesc = "活期理财超8% 资金灵活存取 超50万用户信赖 帮您把零钱联起来";
		wx.ready(function(){
			wx.onMenuShareTimeline({
			    title: shareTitle, // 分享标题
			    desc: shareDesc, // 分享描述
			    link: shareUrl, // 分享链接
			    imgUrl: bathPath+"/pic/weixin/activity/activity_20151008/logo.png", // 分享图标
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
			    imgUrl: bathPath+"/pic/weixin/activity/activity_20151008/logo.png", // 分享图标
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