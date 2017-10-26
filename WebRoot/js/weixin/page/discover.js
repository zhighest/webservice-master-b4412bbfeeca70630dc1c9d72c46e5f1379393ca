$(document).ready(function(){
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
	var ua = navigator.userAgent.toLowerCase();
		if(ua.match(/MicroMessenger/i)=="micromessenger"){
			ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,draw,"GET");
			
		}else if(channel == "LBAndroid") {
				
			if(mobile == "" || mobile == null || mobile == "null"){
				if(window._cordovaNative){
					window._cordovaNative.goLogin();
				}			
			}else{
				ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,draw,"GET");
			}	
				
		}else if(channel == "LBIOS") {
				
			if(mobile == "" || mobile == null || mobile == "null"){
				goLogin();			
			}else{
				ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel ,draw,"GET");					
			}	
		}else {
			ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw","mobile=" +mobile+ "&channel="+channel,draw,"GET");
		}	
	(function rem(x){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		window.addEventListener("resize",setRenSize,false);
		function setRenSize(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		}
	}(7.5));
	var sns=false;
	$(".alertBoxA").on("click",function(){
		sns=false;
		$("#alertBox").addClass("none");
	})
	$("#clickNumber").on("click",function(){
		if(!drawN||sns) return;
		sns=true;
		ajaxRequest(contextPath + "wxluckdraw/userSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,callback,"GET");	
	})
})
var drawN=true;
 function draw(data){
	if(data.rescode=="00000"){
		var num=data.restLuckDrawTimes;
		$("#number").text(num);
	if(num>0){
		$("#clickNumber")
		.addClass("phoneC")
		.removeClass("phoneC2");
		drawN=true;
	}
	else{
		$("#clickNumber")
		.addClass("phoneC2")
		.removeClass("phoneC");
		drawN=false;
	}
	}
	else{
		draw=false;
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	
			}
			else if(data.errorMessage.message_level == "2") {
				alertWrap(data.errorMessage.message_content)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}	
	
	}

function callback(data){
	if(data.rescode=="00000"){	
		var num=data.restLuckDrawTimes;
		$("#number").text(num);
		$("#alertPhone").text(data.awardName);
		if(num>=0){
			$("#contentI").attr("src",contextPath + "pic/weixin/activity/Discover/draw4.gif");
			setTimeout(function(){
				$("#alertBox").removeClass("none");
				$("#contentI").attr("src",contextPath + "pic/weixin/activity/Discover/draw3.png");
			},2800)
			drawN=true;
			if(num == 0){
				$("#clickNumber")
			.addClass("phoneC2")
			.removeClass("phoneC");
				drawN=false;
			}
		}
	}
	else{
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	
			}
			else if(data.errorMessage.message_level == "2") {
				alertWrap(data.errorMessage.message_content)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}	
}