//横屏提醒

window.onorientationchange=function(){ 
var obj=document.getElementById('orientation');

	if(window.orientation==0){
		window.location.reload();//刷新当前页面
	}else{//横屏
 			window.location.reload();//刷新当前页面     
	}
}; 

//通告
 var setSystemSeetingInfo = function(data){ 
	if (data.rescode == "00001") {	
        $("#marquee").hide();
    } else if (data.rescode == "00000") {
    	if(data.message_level== 0){
    		$("#marquee").hide();
    		$("#message").hide();
    	}else if(data.message_level== 1){
    		if(data.commet != "" && data.commet != null&& data.commet != 'null') {
			    $("#marquee").show();			    
				$("#marquee ul li.marqueeLi").html(data.commet);															
				$("#marquee").kxbdMarquee({isEqual:false});
				$(".topMenu").css("margin-top","28px");
				// $(".content").css("margin-top","28px");
				// $("#content").css("margin-top","28px");
			}else{
				$("#marquee").hide();
				$("#slider").css("margin-top","0px");//banner在滚动条没有时置顶
			}
    			
    	}else if(data.message_level== 2){
    		$("#message").show();
    		$(".messageTitle").html(data.message_title);
    		$(".messageMess").html(data.commet);
    	}
	    
    }
}
 //通告接口
$(document).ready(function() {
	
	setTimeout(function(){
		ajaxRequest(contextPath + "wxabout/getSystemSeetingInfo", "flag=1", setSystemSeetingInfo);
		
	},800);	
  	
    var marWidth = $(document).width();
	$("#marquee").css("width",marWidth);
	
	
	
	
	
});

$("#know").click(function() {
	$("#message").hide();
		
});
$("#message").not(".changeBox").click(function() {
	$("#message").hide();
});