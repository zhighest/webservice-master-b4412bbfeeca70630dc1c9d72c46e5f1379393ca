//债权转让申请回调函数
var sloanId = $("#sloanId").val();
var couponsRateRises = $("#couponsRateRises").val();
var setHandleAssignApply = function(data){
	if(data.rescode == 00000){
		errorMessage(data.resmsg_cn);
		//alert(orderIdSed+','+investAmount+','+sloanId+','+couponsRateRises)
		setTimeout(function(){
			window.location.href = contextPath + "wxtrade/goInvestDetail?orderId="+orderIdSed+"&investAmount="+investAmount+"&sloanId="+sloanId+"&couponsRateRises="+couponsRateRises;
		}, 2000);
	}else{
		errorMessage(data.resmsg_cn);
	}
}

//债权转让申请按钮需同意协议才可以点击事件
var state= true;
var orderIdSed = $("#orderIdSed").val();;
$("#enterSubmit").click(function() {
	if(!state){
		errorMessage("请同意《债权转让权益》！");
        return false;
	}
	if(state){
		ajaxRequest(contextPath + "wxassign/handleAssignApply", "orderId="+orderIdSed+"&customerType="+ assignUserType +"&applyFlag=Y", setHandleAssignApply,"GET");
	}
});
//同意债权转让协议的点击按钮状态以及控制申请按钮的状态
$("#checkboxImg").click(function(){
	if(!state){
		state = true;
		$("#checkboxImg").attr("src",contextPath + "pic/weixin/ver2_7/full.png");
		$("#enterSubmit").addClass('redBkg').removeClass('grayBox')
	}else{
		state = false;
		$("#checkboxImg").attr("src",contextPath + "pic/weixin/ver2_7/empty.png");
		$("#enterSubmit").addClass('grayBox').removeClass('redBkg')
	}
});
//查询债权转让利率信息回调函数
var setDebtAssignmentRate = function(data){
	if(data.rescode == 00000){
		var dataInfo = data.list[0];
		$("#probability").html(dataInfo.annualRate + '%');//年化收益率
		$("#probabilityP").html(dataInfo.rateDesc);//年化收益率描述信息
		$("#poundage").html(dataInfo.assignFee+'元');//转让手续费
		$("#poundageP").html(dataInfo.assignFeeDesc);//转让手续费描述信息
		$("#transferRegulation").html(dataInfo.assignRuleDesc);//债权转让规则
	}else{
		errorMessage(data.resmsg_cn);
	}
}
var investAmount;
var assignUserType;
$(document).ready(function() {
	investAmount = $("#investAmount").val();
	var valueDate = $("#valueDate").val();
	assignUserType = $("#assignUserType").val();
	//查询债权转让利率信息
	ajaxRequest(contextPath + "wxassign/getDebtAssignmentRate", "valueDate="+ valueDate +"&customerType="+ assignUserType +"&investAmount="+investAmount, setDebtAssignmentRate,"GET");
});

