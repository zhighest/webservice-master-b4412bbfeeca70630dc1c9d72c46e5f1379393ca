var mobile;
var setEnrollInfo = function(data){
	if(data.rescode == "00000"){
		if(data.enrollInfo.isPay == "Y"){
			$("#marathonId").html(data.enrollInfo.signUpNo);
			$("#marathonName").html(data.enrollInfo.realName);
			$("#marathonIdcard").html(numHide(data.enrollInfo.idCard));
			$("#marathonMobile").html(numHide(data.enrollInfo.mobile));
			$("#marathonSize").html(data.enrollInfo.materielSize);
		}else{
			window.location.href = contextPath + "/running/goMarathonPay?mobile="+mobile;
		}
	}else{
		window.location.href = contextPath + "/running/goMarathonLogin";
	}
}
$(document).ready(function() {
   	mobile = $("#mobile").val();
   	ajaxRequest(contextPath + "/running/getEnrollInfo", "mobile=" + mobile, setEnrollInfo);
});