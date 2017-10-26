var channel = $("#channel").val();
var mobile = $("#mobile").val();
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
var ua = navigator.userAgent.toLowerCase();//运行环境
var numBoxwidth=$(document.body).width();
 numPWidth = Subtr(numBoxwidth,100);
 $(".textIn24").css("width", numPWidth);
$(document).ready(function(){
		if(ua.match(/MicroMessenger/i)=="micromessenger"){//如果运行环境是微信,跳转到应用宝
			$("#LD").html("下载");
			$("#ZQH").html("下载");
			$("#LD").attr("onclick","openLdAn()");
			$("#ZQH").attr("onclick","openZqhAn()");	
		}else if(channel == "LBAndroid") {//如果运行环境是安卓app	
			$("#exchange").show();	
			$("#wantjs").show();
			$("#wantts").show();
			$("#LD").html("打开");
			$("#ZQH").html("打开");
			$("#LD").attr("onclick","goAppAn('cn.com.xizhi.linktown.c','http://sj.qq.com/myapp/detail.htm?apkName=cn.com.xizhi.linktown.c','习之铃铛')");
			$("#ZQH").attr("onclick","goAppAn('com.zhidou.smart','http://a.app.qq.com/o/simple.jsp?pkgname=com.zhidou.smart','智仟汇')");
			$("#exchange").attr("onclick","goToIntegralActivityNow()");
			$("#wantjs").attr("onclick","goAppAnd('com.zhidou.smart','http://a.app.qq.com/o/simple.jsp?pkgname=com.zhidou.smart','智仟汇','3431')");
			$("#wantts").attr("onclick","goAppAnd('com.zhidou.smart','http://a.app.qq.com/o/simple.jsp?pkgname=com.zhidou.smart','智仟汇','3432')");
		}else if(channel == "LBIOS") {//如果运行环境是Ios，调用ios方法
			$("#exchange").show();
			$("#wantjs").show();
			$("#wantts").show();
			$("#LD").html("打开");
			$("#ZQH").html("打开");
			$("#LD").attr("onclick","goApp('linktownC://','习之铃铛','https://itunes.apple.com/cn/app/id1119505957?mt=8')")
			$("#ZQH").attr("onclick","goApp('com.zhidou.smart://','智仟汇','http://itunes.apple.com/cn/app/id1153907601?mt=8&ls=1')")
			$("#wantts").attr("onclick","goApp('com.zhidou.smart://service/goodsDetail?goodsId=3432','智仟汇','http://itunes.apple.com/cn/app/id1153907601?mt=8&ls=1')")
			$("#wantjs").attr("onclick","goApp('com.zhidou.smart://service/goodsDetail?goodsId=3431','智仟汇','http://itunes.apple.com/cn/app/id1153907601?mt=8&ls=1')")
			$("#exchange").attr("onclick","goIntegralMall()");
		}else{
						
		}
});
var goApp = function(urlScheme,appName,urlPath){
	if(window.jumpApp(urlScheme,appName,urlPath)){
		window.callBack();
	}	
}
var callBack=function(isInstor,urlScheme,urlPath,appName){//是否安装，包名，安装地址，app名字
	var str=urlScheme;
	if(isInstor==0){//未安装app
		window.installApp(urlPath);
	}else if(isInstor==1){//已安装app，去打开
		alertBox("'联璧金融'想要打开'"+appName+"'","startAppNowIos('"+urlScheme+"')",1);
	 }
}

var goAppAn = function(data1,data2,data3){
	var bagNam = data1;
	if(window._cordovaNative){
		 var isins=window._cordovaNative.IsAppInstalled(bagNam);//是否安装铃铛
		 if(isins==0){//没安装
		 	var isinsY=window._cordovaNative.IsAppInstalled('com.tencent.android.qqdownloader');//是否安装应用宝
		 	if(isinsY==0){//没安装应用宝
		 		alertBox("请先下载并安装“应用宝”再进行APP下载","closeIt",2)
		 	}else if(isinsY==1){//已安装应用宝，去安装智仟汇
		 		// window._cordovaNative.installApp(bagNam);//成功跳转到安装路径
		 		var yybIsins = window._cordovaNative.installApp(bagNam);
		 		if (yybIsins==0){
		 			alertBox("请先打开“应用宝”再进行操作","closeIt",2);
		 		}
		 	}
		 }else if(isins==1){//安装铃铛，去打开
		 	alertBox("'联璧金融'想要打开'"+data3+"'","startAppNow('"+bagNam+"')",1);
		 }
	}else{
		openAppNow(data3);
	}
}
var goAppAnd = function(data1,data2,data3,data4){
	var bagNam = data1;	
	var goodsId = data4;
	if(window._cordovaNative){
		 var isins=window._cordovaNative.IsAppInstalled(bagNam);//是否安装铃铛
		 if(isins==0){//没安装
		 	var isinsY=window._cordovaNative.IsAppInstalled('com.tencent.android.qqdownloader');//是否安装应用宝
		 	if(isinsY==0){//没安装应用宝
		 		alertBox("请先下载并安装“应用宝”再进行APP下载","closeIt",2)
		 	}else if(isinsY==1){//已安装应用宝，去安装智仟汇
		 		// window._cordovaNative.installApp(bagNam);//成功跳转到安装路径
		 		var yybIsins = window._cordovaNative.installApp(bagNam);
		 		if (yybIsins==0){
		 			alertBox("请先打开“应用宝”再进行操作","closeIt",2);
		 		}
		 	}
		 }else if(isins==1){//安装铃铛，去打开
		 	alertBox("'联璧金融'想要打开'"+data3+"'","jumpToZQHCDnow('"+bagNam+"','"+goodsId+"')",1);//4个参数 包名 
		 }
	}else{
		openAppNow(data3);
	}
}

var goToIntegralActivityNow = function(){
	window._cordovaNative.goToIntegralActivity();
}
var startAppNow = function(bagNam){
	window._cordovaNative.startApp(bagNam);
}
var jumpToZQHCDnow = function(bagNam,goodsId){
	$('#alertBox').remove();
	window._cordovaNative.jumpToZQHCD(bagNam,"EXTRA_COMMDITY_ID",goodsId,"com.zhidou.smart.ui.MainActivity");
}
var startAppNowIos = function(urlScheme){
	window.startApp(urlScheme);
}
var closeIt = function(){
	$('#alertBox').remove();
}
var openZqhAn = function(){
	window.location.href='http://a.app.qq.com/o/simple.jsp?pkgname=com.zhidou.smart';
}
var openLdAn = function(){
	window.location.href='http://a.app.qq.com/o/simple.jsp?pkgname=cn.com.xizhi.linktown.c';
}
var openAppNow = function(data){
	window.location.href = data;
}
var goScore = function(){
	window.location.href= contextPath+'wxPoint/colletScore?mobile='+mobile+'&channel='+channel;
}