//初始化数据
function setpopup(data){
	if(data.rescode == 00000){	
		$("#restnum").html(data.totalNum);
		var date1 = data.beginDate+ " - " + data.endDate;
		$("#date1").html(date1);
		if(data.isActivityDate=="0"&&data.totalNum>0) {
			var html2='<img src="'+contextPath+'pic/weixin/activity/getInterestRates/btn.png" width="68%" class="getbtn">';
			$(".cursor").html(html2);	   		
		}
	}
	else{
		errorMessage(data.resmsg_cn);
	}   		   		
}
//抢加息券
function setpopup2(data){
	if(data.rescode==00000){
		ajaxRequest(contextPath + "wxactivity/queryWxCouponInfoByActivity","",setpopup,"GET");
		$("#popup1").show();
	$("#rateRises").html(data.rateRises+"%");
	var productType="";
	if(data.productType=='108'){
		productType="定期";
	}else{
		productType="活期";
	}
	$("#risesDays").html(productType+"加息券"+data.risesDays+"天");
	$("#close1").on('click',function(){
		$("#popup1").hide();
	})
		
	}else{
		errorMessage(data.resmsg_cn);
	}	
}

$(document).ready(function(){
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		$(".getbtn").show();
		ajaxRequest(contextPath + "wxactivity/queryWxCouponInfoByActivity","",setpopup,"GET");	
		$(".getbtn").click(function(){
			
			ajaxRequest(contextPath + "wxactivity/gainWxCouponByActivity","",setpopup2,"GET");	
		})
	}else if(channel == "LBAndroid") {
		$(".getbtn").show();
		ajaxRequest(contextPath + "wxactivity/queryWxCouponInfoByActivity","",setpopup,"GET");	
		if(mobile == "" || mobile == null || mobile == "null"){
			if(window._cordovaNative){
				$(".getbtn").click(function(){
					window._cordovaNative.goLogin();
				})
			}	
		}else{
			$(".getbtn").click(function(){
			ajaxRequest(contextPath + "wxactivity/gainWxCouponByActivity","mobile=" +mobile+ "&channel="+channel,setpopup2,"GET");	
		})	
		}	
			
	}else if(channel == "LBIOS") {
		$(".getbtn").show();
		ajaxRequest(contextPath + "wxactivity/queryWxCouponInfoByActivity","",setpopup,"GET");	
		if(mobile == "" || mobile == null || mobile == "null"){
			$(".getbtn").attr( "onclick", "goLogin()");			
		}else{
			$(".getbtn").click(function(){
							
				ajaxRequest(contextPath + "wxactivity/gainWxCouponByActivity","mobile=" +mobile+ "&channel="+channel,setpopup2,"GET");	
			})	
					
		}	
	}else {
// 		ajaxRequest(contextPath + "wxactivity/queryWxCouponInfoByActivity","",setpopup,"GET");
	}	
})
