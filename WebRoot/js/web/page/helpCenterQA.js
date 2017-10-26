$(document).ready(function(){
	//初始化
	$('.ProblemClassification ul div').toggleClass('ProblemNone');
	$('.ProblemClassification ul p').toggleClass('bottomGray');
	
	//操作
	$('.ProblemClassification li').click(function(){
		var _this = this;
		$(_this).find('div').slideToggle();
		$(_this).find('p').toggleClass('bottomGray');
		$(_this).find('p img').toggleClass('rolateImage');
	})
	
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
