var SHAKE_THRESHOLD = 4000;  
var last_update = 0;  
var x = y = z = last_x = last_y = last_z = 0;
var canRock = true;
function init() { 
    if (window.DeviceMotionEvent) {  
        window.addEventListener('devicemotion', deviceMotionHandler, false);  
    } else {  
        alert('not support mobile event');  
    }  
}  
function deviceMotionHandler(eventData) {  
    var acceleration = eventData.accelerationIncludingGravity;  
    var curTime = new Date().getTime();  
    if ((curTime - last_update) > 100) {  
        var diffTime = curTime - last_update;  
        last_update = curTime;  
        x = acceleration.x;  
        y = acceleration.y;  
        z = acceleration.z;  
        var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;  
				
        if (speed > SHAKE_THRESHOLD) {
	        if(canRock){
		        canRock = false;
		        $("#handImg").removeClass("handImgPaused");
		        $("#handImg").addClass("handImgRunning");
		        setTimeout('userCouponsLuckDraw()',2000);
	        }
        }
        
        last_x = x;  
        last_y = y;  
        last_z = z;  
    }  
}


var resetPage = function(){
	$(".yellowBox").css({"height":$(".yellowBox").width(),"marginTop":"25%"});
	$(".redBox").height($(".centerBox").height());
}

$("#playBtn").click(function(){
	userCouponsLuckDraw();
})

$("#escBtn").click(function(){
	$("#popBg").hide();
	$("#pop").hide();
	$("#tips1").hide();
	$("#tips2").hide();
	canRock = true;
})

var setShowCouponsLuckDraw = function(data){
	if(data.rescode == "00000"){
		$("#draw_rest_count").html(data.couponsLuckDraw.draw_rest_count)
	}
}

var setUserCouponsLuckDraw = function(data){
	$("#pop").show();
	$("#popBg").show();
	if(data.rescode == "00000"){
		$("#addNum").html(data.rateRisesCoupons.rate_rises + "%");
		$("#tips1").show();
		showCouponsLuckDraw();
	}else{
		$("#tips2").show();
	}
}

var showCouponsLuckDraw = function(){
	ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw", "userId="+userId, setShowCouponsLuckDraw,"GET");
}

var userCouponsLuckDraw = function(){
	$("#handImg").removeClass("handImgRunning");
	ajaxRequest(contextPath + "wxcoupons/userCouponsLuckDraw", "userId="+userId, setUserCouponsLuckDraw,"GET");
}
var userId;
$(document).ready(function() {
	resetPage();
	userId = $("#userId").val();
	showCouponsLuckDraw();
});

$(window).resize(function(){
	resetPage();
});