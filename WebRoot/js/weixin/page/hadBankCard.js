var getRecePayBank = function(data) {
    if (data.bankId != "" && data.bankId != null) {
        $("#bankName").html(data.bankName);
        $("#cardNumber").html(numHide(data.cardNumber));
        $("#accountName").html(data.accountName);
        $("#instructions").html("如您需修改银行卡，请拨打客服电话");
        $("#tel").show();
    } else {
        $("#bankName").html("联璧银行");
        $("#cardNumber").html("**** **** **** ****");
        $("#accountName").html("联璧钱包");
        $("#instructions").html("暂未绑定银行卡，首次充值后将自动绑定");
    }
};
$(document).ready(function() {
    ajaxRequest(contextPath + "wxtrade/getRecePayBank", "", getRecePayBank);
});

//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
			$('.JsPhoneCall').html(PhoneCall);
			$('.JsPhoneCallTell').attr('href','tel:'+PhoneCall)
		}
	});
}
getServiceCall();