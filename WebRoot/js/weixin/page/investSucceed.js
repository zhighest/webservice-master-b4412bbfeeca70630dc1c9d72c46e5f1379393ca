
var adjustRate;//专享
var investMinAmount;//起投金额
var annualRate;//当日年化利率
var remanAmount;//可投总金额
var productName;//上架名称
var id;//标的id
$(document).ready(function() {
	var productId = $("#productId").val();
	investMinAmount = $("#investMinAmount").val();
	annualRate = $("#annualRate").val();
	remanAmount = $("#remanAmount").val();
	productName = $("#productName").val();
	id = $("#id").val();
	if(productId == 107){
		$("#backBtn").attr("href",contextPath+"/wxInvest/getPlanProductIndex");
	}else if(productId == 109){
		$("#backBtn").attr("href",contextPath+"wxproduct/goDemandProductIndex?fixDemandSwitch=demand");
	}
});