$("#register").click(function(){
    ajaxRequest(contextPath+"/wxagreement/getServAgreementByType","type=register&productId=107",setServAgreementByType,"GET");
})
var setServAgreementByType = function(data){ 
	var u = navigator.userAgent, app = navigator.appVersion;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	var serAgreementUrl=data.serAgreementUrl;
	var flag=serAgreementUrl.indexOf(";");
	serAgreementUrl2=serAgreementUrl.substring(0,flag);
	serAgreementUrl3=serAgreementUrl.substring(flag+1);
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
	    	if(isiOS){
	    	window.location.href = serAgreementUrl2;
		}
		else{
			window.location.href = serAgreementUrl3;
		}
    }
};

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