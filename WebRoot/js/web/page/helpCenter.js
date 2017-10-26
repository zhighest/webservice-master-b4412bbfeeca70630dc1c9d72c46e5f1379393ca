
$(document).ready(function(){
	var windowWidth = $(window).width();
	var hasHeadFoot = $("#hasHeadFoot").val();
	//判断来源app的时候隐藏
	if(hasHeadFoot=='false'){
		$('.contentWrapper').css('padding-top','0px');
	}
	
	//请求接口取出数据----客服电话
	var PhoneCall;
	function getServiceCall(){
		ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
			if(res.rescode=='00000'){
				PhoneCall = res.content.mobile;
				$('.JsPhoneCall').html(PhoneCall);
			}
		});
	}
	getServiceCall();
	
})
