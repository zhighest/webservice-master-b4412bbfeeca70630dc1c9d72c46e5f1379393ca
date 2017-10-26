
// $("#popBtn4").click(function(){
// 	$("#popup4").hide();
// })
// var shareMobileDateJM;
// var shareUrl;
// var shareTitle;
// var shareDesc;
// var goUrl;
// $(document).ready(function(){
// 	shareMobileDateJM = $("#shareMobileDateJM").val();
// 	mobileWXFlag = $("#mobileWXFlag").val();
// 	goUrl = $("#goUrl").val();
// 	shareUrl = "http://wx.lianbijr.com/wxTrigger/getWxCode?actionScope="+goUrl;
// 	shareTitle = "天上掉馅饼 不领白不领";
// 	shareDesc = "独乐乐不如众乐乐，不看气质看朋友";
// 	shareImg = "http://wx.lianbijr.com/pic/weixin/activity/activity_20151202/icon.jpg";

// 	var ua = navigator.userAgent.toLowerCase();

// 		if(ua.match(/MicroMessenger/i)=="micromessenger"){
// 			$("#weixin").show();	
// 			$("#weixin").click(function(){
// 				weixinPopShow();
// 			})
// 		}else if(mobileWXFlag == "lincomb") {
// 			if (/android/i.test(navigator.userAgent)){
// 				$("#lianbiAndroid").show();
// 				if(window._cordovaNative){
// 					$("#lianbiAndroid").click(function(){
// 						window._cordovaNative.onShareTicket(shareTitle,shareDesc,shareUrl,shareImg);
// 					})
// 				}
// 			}
// 			if (/ipad|iphone|mac/i.test(navigator.userAgent)){
// 				$("#lianbiIos").show();
// 				$("#iosBtn").attr( "onclick", "onShareTicket('"+ shareTitle +"','"+ shareDesc +"','"+ shareUrl +"','"+ shareImg +"')")
// 			}
// 		}else{
// 			errorMessage("请关注微信公众号联璧钱包或者下载联璧金融APP进行分享");
// 		}
	

	
	
	
// });
// var setActivitiyShareNotify = function(data){
// 	if(data.msg != "success"){
//     	errorMessage("分享成功");
//     	//$("#popup4").show();
//     }else{
//     	errorMessage("分享加息成功");
//     }
// }
// var weixinPopShow = function(){
// 		$("#popup2").show();
		
// }
// wx.ready(function(){
// 	wx.onMenuShareTimeline({
// 	    title: shareTitle, // 分享标题
// 	    desc: shareDesc, // 分享描述
// 	    link: shareUrl, // 分享链接
// 	    imgUrl: shareImg, // 分享图标
// 	    success: function () { 
// 	        // 用户确认分享后执行的回调函数
// 	        ajaxRequest(contextPath+"wxuser/shareIncreaseInterest","",setActivitiyShareNotify);
// 	        //$("#popup2").hide();
// 	        /*errorMessage("分享成功");*/
// 	    },
// 	    cancel: function () { 
// 	        // 用户取消分享后执行的回调函数
// 	        errorMessage("未分享");
// 	        $("#popup2").hide();
// 	    }
// 	});
			
// 	wx.onMenuShareAppMessage({
// 	    title: shareTitle, // 分享标题
// 	    desc: shareDesc, // 分享描述
// 	    link: shareUrl, // 分享链接
// 	    type: 'link', // 分享类型,music、video或link，不填默认为link
// 	    imgUrl: shareImg, // 分享图标
// 	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
// 	    success: function () { 
// 	        // 用户确认分享后执行的回调函数
// 	        ajaxRequest(contextPath+"wxuser/shareIncreaseInterest","",setActivitiyShareNotify);
// 	        //$("#popup2").hide();
// 	       /* errorMessage("分享成功");*/
// 	    },
// 	    cancel: function () { 
// 	        // 用户取消分享后执行的回调函数
// 	        errorMessage("未分享");
// 	        $("#popup2").hide();
// 	    }
// 	});
// }); -->

/*
var mobile;//定义两个参数mobile，signFlag
var signFlag;
$(document).ready(function(){
	mobile = $("#mobile").val();//参数mobile用来存放#mobile传过来的val
	signFlag = $("#signFlag").val();//参数signFlag用来存放#signFlag传过来的val
	var ua = navigator.userAgent.toLowerCase();//定义一个参数ua为判断运行环境
	if(signFlag == "LBIOS"){//如果传来的signFlag值为LBIOS即运行环境为ios环境，则执行以下
		$("#btnBox a").attr("href",contextPath+"wxactivity/goSignIn?mobile="+mobile+"&signFlag="+signFlag)//按钮的跳转链接为
		$("#btnBox").show();//显示该按钮，即该按钮只在ios环境下显示
	}
})
*/