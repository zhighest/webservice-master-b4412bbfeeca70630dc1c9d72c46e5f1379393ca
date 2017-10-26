var wrapperBkg1 = function(){
    $(".wrapper").addClass("bkg1");
    $(".wrapper").removeClass("bkg2");
}
var wrapperBkg2 = function(){
    $(".wrapper").addClass("bkg2");
    $(".wrapper").removeClass("bkg1");
}
$(document).ready(function(){
	var parm = $("#parm").val();
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
	//定活期跳转
	var fixDemandSwitch = $("#fixDemandSwitch").val();
	if(fixDemandSwitch == 'fixExplain'){//
	    tabSwitchLi('tabItem','tab2');
	    tabSwitchCon('tabContent','tabContent2');
	    wrapperBkg2()
	}else if(fixDemandSwitch == 'demandExplain'){
	    tabSwitchLi('tabItem','tab1');
	    tabSwitchCon('tabContent','tabContent1');
	    wrapperBkg1()
	}
	
// 	var ua = navigator.userAgent.toLowerCase();
// 	if(ua.match(/MicroMessenger/i)=="micromessenger"){	
// 	}
	//渠道安卓
	if(channel == "LBAndroid") {
		$("#lianbiAndroidDemand").show();
		$("#lianbiAndroidFix").show();
		if(mobile == "" || mobile == null || mobile == "null"){
			if(window._cordovaNative){
				$("#lianbiAndroidDemand").click(function(){
					window._cordovaNative.goLogin();
				})
				$("#lianbiAndroidFix").click(function(){
					window._cordovaNative.goLogin();
				})
			}	
		}else{
			if(window._cordovaNative){
				$("#lianbiAndroidDemand").click(function(){
					window._cordovaNative.goInvest();
				})
				$("#lianbiAndroidFix").click(function(){
					window._cordovaNative.goFixInvest();
				})
			}
				
		}		
	}else if(channel == "LBIOS") {//渠道IOS
		$("#lianbiIconBtnFix").show();
		$("#lianbiIconBtnDemand").show();
		$("#lianbiIosFix").show();
		$("#lianbiIosDemand").show();
		if(mobile == "" || mobile == null || mobile == "null"){
			$("#lianbiIconBtnFix").attr( "onclick", "goLogin()");
			$("#lianbiIconBtnDemand").attr( "onclick", "goLogin()");			
		}else{
			$("#lianbiIconBtnFix").attr( "onclick", "goFixInvest()");
			$("#lianbiIconBtnDemand").attr( "onclick", "goInvest()");					
		}	
	}else{
		//活期按钮
		$("#weixinDemand").show();	
		$("#weixinDemand").click(function(){
			$(this).attr("href",contextPath + "wxproduct/goDemandProductIndex?fixDemandSwitch=demand&mobile=" +mobile+ "&channel=" + channel);
		})
		//定期按钮
		$("#weixinFix").show();	
		$("#weixinFix").click(function(){
			$(this).attr("href",contextPath + "wxproduct/goDemandProductIndex?fixDemandSwitch=fix&mobile=" +mobile+ "&channel=" + channel);
		})			
	}
		
})