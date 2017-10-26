$(document).ready(function(){
	(function rem(x){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		window.addEventListener("resize",setRenSize,false);
		function setRenSize(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		}
	}(7.5));
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
		var ua = navigator.userAgent.toLowerCase();

		if(ua.match(/MicroMessenger/i)=="micromessenger"){
			$("#weixin").show();	
			$("#weixin").click(function(){
				$(this).attr("href",contextPath + "/wxactivity/goDiscover?mobile=" +mobile+ "&channel=" + channel);
			})
		}else if(channel == "LBAndroid") {
			$("#lianbiAndroid").show();
			if(mobile == "" || mobile == null || mobile == "null"){
				if(window._cordovaNative){
					$("#lianbiAndroid").click(function(){
						window._cordovaNative.goLogin();
					})
				}	
			}else{
				$("#lianbiAndroid").attr("href",contextPath + "/wxactivity/goDiscover?mobile="+mobile+ "&channel=" + channel);	
			}	
			
		}else if(channel == "LBIOS") {
			$("#lianbiIos").show();
			if(mobile == "" || mobile == null || mobile == "null"){
				$("#lianbiIos").attr( "onclick", "goLogin()")			
			}else{
				$("#lianbiIos").attr("href",contextPath + "/wxactivity/goDiscover?mobile=" +mobile+ "&channel=" + channel);				
			}	
		}else{
					
	}		
})