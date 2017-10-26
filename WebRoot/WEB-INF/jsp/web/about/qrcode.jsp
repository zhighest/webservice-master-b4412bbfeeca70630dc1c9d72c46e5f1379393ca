<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@  include file="../../commonWeb.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="width=device-width,target-densitydpi=high-dpi,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<title></title>
<link href="${pageContext.request.contextPath}/pic/web/favicon.ico" rel="icon" type="image/x-icon" />
</head>
<body style="background-color:#999;position: relative; padding:0; margin:0;">
<div id="iosDiv" style="background-image: url('${pageContext.request.contextPath}/pic/qrcode/alertios.png');background-position: right top;background-repeat: no-repeat;background-size: cover;height: 137px;position: absolute;right: 5px;top: 0;width: 309px;display:none;"></div>
<div id="androidDiv" style="position: absolute;right: 1%;top: 0;width: 98%;display:none;"><img width="100%" height="auto" src="${pageContext.request.contextPath}/pic/qrcode/alertandroid.png"></div>
<%@  include file="../baiduStatistics.jsp"%>
<script>
// window.location = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai';
	
	var android = 'http://licai.lincomb.com/app/android/lincombp2p_product_1.0.0_20151020.apk';
	var ios = 'https://itunes.apple.com/cn/app/id1050036859';
	var ua = navigator.userAgent.toLowerCase();
	if (/android/i.test(navigator.userAgent)){
		if(android != "0000"){
			if(ua.match(/MicroMessenger/i)=="micromessenger") {
				document.getElementById('androidDiv').style.display = "";
			}else {
				var androidsrc='lincomb://lianbijr.com';
				var ifr=document.createElement('iframe');
				ifr.src=androidsrc;
				ifr.style.display='none';
				document.body.appendChild(ifr);
				setTimeout(function(){
					window.location=android;
				},600);
				
			}
		}else{
			alert("Android版即将上线...")
	    }
	}
	if (/ipad|iphone|mac/i.test(navigator.userAgent)){	
		if(ios != "0000"){
			if(ua.match(/MicroMessenger/i)=="micromessenger") {
				document.getElementById('iosDiv').style.display = "";
				var iossrc='lincombfinance://';
				var ifr=document.createElement('iframe');
				ifr.src=iossrc;
				ifr.style.display='none';
				document.body.appendChild(ifr);				
				setTimeout(function(){
					window.location=ios;
				},600);
			}else {			
				var iossrc='lincombfinance://';
				var ifr=document.createElement('iframe');
				ifr.src=iossrc;
				ifr.style.display='none';
				document.body.appendChild(ifr);
				setTimeout(function(){
					window.location=ios;
				},600);
				
			}
		}else{
			alert("ios版即将上线...")
		}
	}

	
</script>
</body>
</html>
