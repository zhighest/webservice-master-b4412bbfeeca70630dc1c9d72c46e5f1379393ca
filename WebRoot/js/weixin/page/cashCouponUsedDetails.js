//判断本地是否有装智仟汇APP，如果有就打开，没有就跳转到下载页
var channel = $("#channel").val();
var mobile = $("#mobile").val();
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var ua = navigator.userAgent.toLowerCase(); //运行环境
$(document).ready(function() {
	if(ua.match(/MicroMessenger/i) == "micromessenger") { //如果运行环境是微信,跳转到应用宝
		$("#ZQH").attr("onclick", "openZqhAn()");
	} else if(channel == "LBAndroid") { //如果运行环境是安卓app
		$("#ZQH").attr("onclick", "goAppAn('com.zhidou.smart','http://a.app.qq.com/o/simple.jsp?pkgname=com.zhidou.smart','智仟汇')");
	} else if(channel == "LBIOS") { //如果运行环境是Ios，调用ios方法
		$("#ZQH").attr("onclick", "goApp('com.zhidou.smart://','智仟汇','http://itunes.apple.com/cn/app/id1153907601?mt=8&ls=1')")
	} else {

	}
});
//IOS跳转 goApp(本地访问路径，应用名称，下载地址)
var goApp = function(urlScheme, appName, urlPath) {
	if(window.jumpApp(urlScheme, appName, urlPath)) {
		window.callBack();
	}
}
//IOS回调
var callBack = function(isInstor, urlScheme, urlPath, appName) { //是否安装，包名，安装地址，app名字
	var str = urlScheme;
	if(isInstor == 0) { //未安装app
		window.installApp(urlPath);
	} else if(isInstor == 1) { //已安装app，去打开
		alertBox("'联璧金融'想要打开'" + appName + "'", "startAppNowIos('" + urlScheme + "')", 1);
	}
}
//安卓跳转goAppAn(程序包名，应用宝下载地址，应用名称)
var goAppAn = function(data1, data2, data3) {
	var bagNam = data1;
	if(window._cordovaNative) {
		var isins = window._cordovaNative.IsAppInstalled(bagNam); //是否安装智仟汇
		if(isins == 0) { //没安装
			var isinsY = window._cordovaNative.IsAppInstalled('com.tencent.android.qqdownloader'); //是否安装应用宝
			if(isinsY == 0) { //没安装应用宝
				alertBox("请先下载并安装“应用宝”再进行APP下载", "closeIt", 2)
			} else if(isinsY == 1) { //已安装应用宝，去安装智仟汇
				// window._cordovaNative.installApp(bagNam);//成功跳转到安装路径
				var yybIsins = window._cordovaNative.installApp(bagNam);
				if(yybIsins == 0) {
					alertBox("请先打开“应用宝”再进行操作", "closeIt", 2);
				}
			}
		} else if(isins == 1) { //安装智仟汇，去打开
			alertBox("'联璧金融'想要打开'" + data3 + "'", "startAppNow('" + bagNam + "')", 1);
		}
	} else {
		openAppNow(data3);
	}
}
//打开应用
var startAppNow = function(bagNam) {
	window._cordovaNative.startApp(bagNam);
}
//IOS打开本地应用
var startAppNowIos = function(urlScheme) {
	window.startApp(urlScheme);
}
//关闭弹出框
var closeIt = function() {
	$('#alertBox').remove();
}
//如果是微信 跳转到应用宝
var openZqhAn = function() {
	window.location.href = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.zhidou.smart';
}
//非安卓/IOS 直接打开APP
var openAppNow = function(data) {
	window.location.href = data;
}
var goScore = function() {
	window.location.href = contextPath + 'wxPoint/colletScore?mobile=' + mobile + '&channel=' + channel;
}

$(document).ready(function() {
	var urlData = window.location.href.split("?")[1];
	if(urlData.indexOf("&") > 0){
        urlData = urlData.split("&")[0];
	}
	ajaxRequest(contextPath + "shopVochers/queryVochersDetails?"+urlData, "", cashCouponUsedDetailsCb); //初始化时发送请求
	//更新现金券使用详情页面数据
	function cashCouponUsedDetailsCb(data) {
		if(data.rescode == "00000") {
			$('#voucherDescribe').text(data.voucher.voucherDescribe);//券用途
			$('#couponIfo').text(data.voucher.voucherName);//券名称
			if(data.voucher.status==="2"){
				$('#phoneNo').html("该券已过期").prev().html("");
			}else{
				$('#phoneNo').text(data.voucher.mobile); //手机号
			}
			
		}else {
			errorMessage(data.resmsg_cn);
		}
	}
})