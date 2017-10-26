<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"> </script>
<!-- <script src="http://demo.open.weixin.qq.com/jssdk/js/api-6.1.js?ts=1420774989"></script> -->
<script>
  //var bathPath = "http://wangenlai2010.vicp.cc/webservice";
  var bathPath = "http://uat.lianbijr.com";
  //var bathPath = "http://u.lianbijr.com";
  //var bathPath = "http://uat.lianbijr.com";
  wx.config({
      debug: false,
      appId: 'wx86a2e2cecca68055',
      timestamp: 1422935886,
      nonceStr: '5RrybhmXWOxyYrj6',
      signature: '${signature}',
      jsApiList: [
        'checkJsApi',
        'onMenuShareTimeline',
        'onMenuShareAppMessage',
        'onMenuShareQQ',
        'onMenuShareWeibo',
        'hideMenuItems',
        'showMenuItems',
        'hideAllNonBaseMenuItem',
        'showAllNonBaseMenuItem',
        'translateVoice',
        'startRecord',
        'stopRecord',
        'onRecordEnd',
        'playVoice',
        'pauseVoice',
        'stopVoice',
        'uploadVoice',
        'downloadVoice',
        'chooseImage',
        'previewImage',
        'uploadImage',
        'downloadImage',
        'getNetworkType',
        'openLocation',
        'getLocation',
        'hideOptionMenu',
        'showOptionMenu',
        'closeWindow',
        'scanQRCode',
        'chooseWXPay',
        'openProductSpecificView',
        'addCard',
        'chooseCard',
        'openCard'
      ]
  });
wx.ready(function(){
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	wx.onMenuShareTimeline({
	    title: '联璧金融', // 分享标题
	    desc: '传统理财收益低？联璧金融让您每天多赚点。上市公司履约背书，安全，高收益，100%本息兑付。', // 分享描述
	    link: 'http://uat.lianbijr.com/activity/activity20151021', // 分享链接
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151008/logo.png", // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	        errorMessage("未分享");
	    }
	});
	//获取“分享给朋友”按钮点击状态及自定义分享内容接口
	wx.onMenuShareAppMessage({
	    title: '联璧金融', // 分享标题
	    desc: '传统理财收益低？联璧金融让您每天多赚点。上市公司履约背书，安全，高收益，100%本息兑付。', // 分享描述
	    link: 'http://uat.lianbijr.com/activity/activity20151021', // 分享链接
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151008/logo.png", // 分享图标
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	        errorMessage("未分享");
	    }
	});
	//获取“分享到QQ”按钮点击状态及自定义分享内容接口
	wx.onMenuShareQQ({
	    title: '联璧金融', // 分享标题
	    desc: '传统理财收益低？联璧金融让您每天多赚点。上市公司履约背书，安全，高收益，100%本息兑付。', // 分享描述
	    link: 'http://uat.lianbijr.com/activity/activity20151021', // 分享链接
	    imgUrl: bathPath+"/pic/weixin/activity/activity_20151008/logo.png", // 分享图标
	    success: function () { 
	    	// 用户确认分享后执行的回调函数
	    },
	    cancel: function () {
	       // 用户取消分享后执行的回调函数
		   errorMessage("未分享");
	    }
	});
});	
</script>