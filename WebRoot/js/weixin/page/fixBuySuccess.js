$("#fixIndex").click(function(event) {
    window.location.href = contextPath + "wxproduct/goDemandProductIndex?fixDemandSwitch=fix";
    sessionStorage.setItem("from","purchaseSucceed");
});

var orderId = $("#orderId").val();

var setLoanAgreement = function(data){ 
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
        window.location.href = data.serAgreementUrl;
    }
};

$("#loanAgreement").click(function(){
	 ajaxRequest(contextPath + "wxagreement/getServAgreementByType", "type=loanAgreement&orderId="+orderId,setLoanAgreement);
})
$(document).ready(function() {
	var rateRises = $("#rateRises").val();
	var adjustRate = $("#adjustRate").val();
	var remanDays = $("#remanDays").val();
	var investPeriod = $("#investPeriod").val();
	addRate = accAdd(rateRises, adjustRate);
	if (addRate == 0) {
		$("#addRate").hide();
	}else{
		$("#addRate").html("+"+addRate+"%");
	}
	if(remanDays == "0") {
		$("#days").html(+investPeriod+"个月");	
	}else{
		$("#days").html(+remanDays+"天");
	}
});