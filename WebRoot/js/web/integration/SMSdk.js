(function() {
	//数美SDK配置
	window._smConf = {
		organization: '4QHO5wdebwuYd1TeuEY3', //联壁的组织代码
		staticHost: 'static.fengkongcloud.com' //主机地址不要更改
	}
	var sm = document.createElement("script");
	sm.src = ('https:' === document.location.protocol ? 'https:' : 'http:') + '//' + _smConf.staticHost + '/fpv2.js';
	var s = document.getElementsByTagName("script")[0]; //引用数美js
	s.parentNode.insertBefore(sm, s);
	//删除cookie中所有定变量函数    
	function delAllCookie() {
		var myDate = new Date();
		myDate.setTime(-1000); //设置时间    
		var data = document.cookie;
		var dataArray = data.split("; ");
		for(var i = 0; i < dataArray.length; i++) {
			var varName = dataArray[i].split("=");
			document.cookie = varName[0] + "=''; expires=" + myDate.toGMTString();
		}

	}
	//判断从数美js是否加载完毕
	sm.onload = sm.onreadystatechange = function() {
		if(!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') {
			var deviceId = SMSdk.getDeviceId(); //每个注册账号唯一的设备号
			setCookie("smDeviceId", deviceId);
		}
		sm.onload = sm.onreadystatechange = null;
	}
})();