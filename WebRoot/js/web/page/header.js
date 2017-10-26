
$(document).ready(function() {

    var needUpdatePassword = localStorage.getItem("needUpdatePassword");  //取session
    if(needUpdatePassword == true || needUpdatePassword == "true" ){
        errorMessage("您当前设置的密码等级低，存在一定风险，请尽快修改");
        localStorage.removeItem("needUpdatePassword");
    }

	var webIsLogIn = $("#webIsLogIn").val();
	var loginMobile = $("#loginMobile").val();
	var channel = $("#channel").val();
	var ua = navigator.userAgent.toLowerCase();
	var hasHeadFoot = $("#hasHeadFoot").val();

	// 头部 hover事件控制样式 begin
	// app下载 begin
	$(".appBox").hover(function(){
		$(".appBox a").css("color","#ff5a5a");
		$(".appBox a i").removeClass("turnAround");
	},function(){
		$(".appBox a").css("color","#9f9f9f");
		$(".appBox a i").addClass("turnAround");
	});
	// app下载 begin
	// 帮助中心 begin
	$(".helpBox").hover(function(){
		$(".helpBoxA").css("color","#ff5a5a");
		$(".helpBoxA i").removeClass("turnAround");
	},function(){
		$(".helpBoxA").css("color","#9f9f9f");
		$(".helpBoxA i").addClass("turnAround");
	});
	// 帮助中心 end
	// 个人中心 begin
	$(".exitBtnB").hover(function(){
		$(".appBox a").css("color","#ff5a5a");
		$(".appBox a img").removeClass("turnAround");
	},function(){
		$(".appBox a").css("color","#9f9f9f");
		$(".appBox a img").addClass("turnAround");
	});
	// 个人中心 end
	// 头部 hover事件控制样式 end

	ajaxRequest(contextPath + "user/getUserVerifyInfo","",setUserVerifyInfo);//用户头像接口

	var webIsLogIn = $("#webIsLogIn").val();
	var loginMobile = $("#loginMobile").val();
	//根据不同登录状态做不同判断
	if(webIsLogIn == "true"){//如果当前状态为登录
		$("#loginBtn").stop().hide();//最上层的登录按钮
		$("#loginOut").stop().show();//最上层的退出按钮
	}else{//如果当前状态为未登录
		$("#loginOut").stop().hide();
		$("#loginBtn").stop().show();
	}
	$("#loginOutBtn").click(function(event) {
		alertBox("","","退出登录？","","exitThis",1);
	});
	navbar();
});
var exitThis = function(){
	$("#okBtn").attr("href",contextPath+"user/logOut");
}

$(window).resize(function(){
	navbar()
});
function navbar(){
	var windowWidth = $(window).width();
	var hasHeadFoot = $("#hasHeadFoot").val();
	if(hasHeadFoot == "false"){//从app帮助中心进入页面，头部隐藏
		$("#responsiveNav").hide();	
		$("#header").hide();
		$("#responsiveFooter").hide();
	}else if(hasHeadFoot == "index"){
		$("#header").hide();	
	}
	else if(hasHeadFoot == "falseHelp"){
		$("#header").hide();
		$("#responsiveNav").hide();	
		$("#responsiveFooter").hide();
		$(".topTex").hide();
		$(".helpSidebar").hide();
	}
	else{//响应式的样式
		if(windowWidth < 768) {
			$("#responsiveNav").show();	
			$("#header").hide();
			$(".logoLong").css("width","90px")
		}
		else{
			$("#responsiveNav").hide();	
			$("#header").show();
			$(".logoLong").css("width","140px")
		}
	}			
}
//响应式状态下的，切换展开tab按钮状态的判断
var a = 0;
function openIt(){ 
	$("#navbarIcon").hide();
	$("#navBarClosed").show();
	$("#responsiveBar").show();
} 
function closedIt(){ 
	$("#responsiveBar").hide();
	$("#navbarIcon").show();
	$("#navBarClosed").hide();
}
$("#navbarRight").click(function(){
	a++;
	a%2==1?openIt():closedIt();	
})
//头像设置
var setUserVerifyInfo = function(data){
    if(data.imageIcon == "Y"){
    	$("#userPhoto").attr("src",data.imageIconUrl);
    	$("#imageIconUrl").attr("src",data.imageIconUrl);
    	$("#userPhotoBig").attr("src",data.imageIconUrl);
        // $("#userPhoto").attr("src",data.imageIconUrl.replace('http','https'));
    }
} 
//个人中心的hover效果设置
$("#userPhoto").mouseover(function() {
	$(".personalCenterBtn").show();
})
$("#loginOut").mouseleave(function() {
	$(".personalCenterBtn").hide();
});