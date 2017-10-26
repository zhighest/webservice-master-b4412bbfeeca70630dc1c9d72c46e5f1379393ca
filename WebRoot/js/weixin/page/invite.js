
$(document).ready(function(){
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
		var ua = navigator.userAgent.toLowerCase();
		// 微信
		if(ua.match(/MicroMessenger/i)=="micromessenger"){									//微信
			
		}else if(channel == "LBAndroid") {	// 安卓												//安卓																
			if(mobile == "" || mobile == null|| mobile == "null"){
				if(window._cordovaNative){					
						window._cordovaNative.goLogin();
				}	
			}else{
				
			}	
		}else if(channel == "LBIOS") {	// ios												//IOS
			if(mobile == "" || mobile == null||mobile == "null"){
				goLogin()		
			}else{
							
			}	
		}else{
					
	}
	ajaxRequest(contextPath + "wxshare/getShareSub", "from=LBWX", setShareMessage);	
})

var setShareMessage = function(data){
	var shareTitle = data.title;
	var shareDesc = data.desc;
	var shareLink =  bathPath + "/wxTrigger/getWxCode?actionScope="+$("#goUrl").val(); // 分享链接
	var shareImgUrl = data.imgUrl;
	$("#inviteBtn").click(function() {
	    $(".sharePopup").show();
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.onMenuShareTimeline({
		    title: shareTitle, // 分享标题
		    desc: shareDesc, // 分享描述
		    link: shareLink, // 分享链接
		    imgUrl: shareImgUrl, // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
	 	        errorMessage("分享成功");
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
	 	        errorMessage("未分享");
		    }
		});
		//获取“分享给朋友”按钮点击状态及自定义分享内容接口
		wx.onMenuShareAppMessage({
		    title: shareTitle, // 分享标题
		    desc: shareDesc, // 分享描述
		    link: shareLink, // 分享链接
		    type: 'link', // 分享类型,music、video或link，不填默认为link
		    imgUrl: shareImgUrl, // 分享图标
		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		        // 用户确认分享后执行的回调函数
	 	        errorMessage("分享成功");
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
	 	        errorMessage("未分享");
		    }
		});
		    
	});	
}
