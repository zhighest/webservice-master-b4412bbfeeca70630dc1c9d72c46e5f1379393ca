$(document).ready(function(){
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
	var URL = $("#URL").val();
		var ua = navigator.userAgent.toLowerCase();

		if(ua.match(/MicroMessenger/i)=="micromessenger"){
			$("#weixin").show();	
			$("#weixin").click(function(){
				$(this).attr("href",URL+"/wxprize/goPrizeList?mobile=" +mobile+ "&channel=" + channel);
			})
		}else if(channel == "LBAndroid") {
			$("#lianbiAndroid").show();
			if(mobile == "" || mobile == null){
				if(window._cordovaNative){
					$("#lianbiAndroid").click(function(){
						window._cordovaNative.goLogin();
					})
				}	
			}else{
				$("#lianbiAndroid").attr("href",URL+"/wxprize/goPrizeList?mobile="+mobile+ "&channel=" + channel);	
			}	
			
		}else if(channel == "LBIOS") {
			$("#lianbiIos").show();
			if(mobile == "" || mobile == null){
				$("#iosBtn").attr( "onclick", "goLogin()")			
			}else{
				$("#lianbiIconBtn").attr("href",URL+"/wxprize/goPrizeList?mobile=" +mobile+ "&channel=" + channel);				
			}	
		}else{
					
	}		
})