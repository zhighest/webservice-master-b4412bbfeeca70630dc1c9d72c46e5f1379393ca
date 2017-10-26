var mobile;
var payFinish = function(data) {
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
    } else {
        $("#req_data").val(JSON.stringify(data.payInfo));
        $("#llpayForm").submit();
    }
};
var setEnrollInfo = function(data){
	if(data.rescode == "00000"){
		$("#createTime").html(new Date(data.enrollInfo.signUpTime.time).Format("yyyy-MM-dd hh:mm:ss"));
	}else{
		//window.location.href = contextPath + "/running/goMarathonLogin";
	}
}
$("#payBtn").click(function(){
   if(mobile == "" || mobile == null){
	    errorMessage("请输入手机号");
	    return false;
    }
    var cardNo = $("#inputNum").val();
    if(cardNo == "" || cardNo == null){
	    errorMessage("请输入银行卡号");
	    return false;
    }
	ajaxRequest(contextPath + "running/payInfo", {
        "mobile": mobile,
        "cardNo": cardNo
    },
    payFinish);
});
$(document).ready(function() {
   	mobile = $("#mobile").val();
   	ajaxRequest(contextPath + "/running/getEnrollInfo", "mobile=" + mobile, setEnrollInfo);
});