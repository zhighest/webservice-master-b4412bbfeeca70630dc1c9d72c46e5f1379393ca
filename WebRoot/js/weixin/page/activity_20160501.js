$(document).ready(function(){//页面加载完毕
	var parm = $("#parm").val();//传值
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
	var sfrom = $("#from").val();

	var ua = navigator.userAgent.toLowerCase();//运行环境
		if(ua.match(/MicroMessenger/i)=="micromessenger"){//如果运行环境是微信
			$("#goToTickets").click(function(event) {//#goToTickets该按钮的点击事件
				sessionStorage.setItem("from","useTab");//调用sessionStorage方法，value值为useTab，即切换到useTab对应的页面
				window.location.href = contextPath +'wxcoupons/goMyInterest';//页面跳转到该地址
			});
		}else if(channel == "LBAndroid") {//如果运行环境是安卓app
			if(mobile == "" || mobile == null){
				if(window._cordovaNative){
					$("#goToTickets").click(function(){
						window._cordovaNative.goLogin();
					})
				}	
			}else{
				if(window._cordovaNative){
					$("#goToTickets").click(function(){
						window._cordovaNative.goToTickets();
					})
				}
			}	
			
		}else if(channel == "LBIOS") {//如果运行环境是Ios
			if(mobile == "" || mobile == null){
				$("#goToTickets").attr( "onclick", "goLogin()")	//为id附一个onclick方法		
			}else{
				$("#goToTickets").attr( "onclick", "goToTickets()")				
			}	
		}else{
					
	}		
})