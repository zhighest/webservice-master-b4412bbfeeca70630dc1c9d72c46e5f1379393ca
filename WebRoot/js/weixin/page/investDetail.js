var setHandleAssignApply = function(data){
    if(data.rescode == 00000){
        errorMessage(data.resmsg_cn);
        $("#creditTranferBtn").hide();
        setTimeout(function(){
            window.location.href = contextPath + "wxproduct/goFixAssetList";
        }, 2000);
    }else{
        errorMessage(data.resmsg_cn);
    }
}

var setOrderFormDetails = function(data) {
    if (data.rescode == "00000") {
        var dataInfo = data.list[0];
        $(".annualizedReturnRate").html(dataInfo.annualRate);//基本收益率
        $("#adjustRate").html(dataInfo.adjustRate);//专享加息
        $(".adjustRate").html(dataInfo.adjustRate);
        $(".couponsRateRises").html(dataInfo.couponsRateRises);//加息劵
        if(dataInfo.adjustRate != 0){//专享加息
            $(".vipHint").show();
			$("#adjustRateLi").show(); 
            $(".adjustRateLi").show();
            $(".annualizedReturnRateLi").show(); 
	    }
        if(dataInfo.couponsRateRises > 0) {//加息劵
            $(".vipHint").show();
            $(".annualizedReturnRateLi").show();
            $(".couponsRateRisesLi").show();
        }
        if(dataInfo.adjustRate == 0 && dataInfo.couponsRateRises == 0){
            $(".vipHint").hide();
            $(".vipHintUl").hide();
        }
        $("#investAmount").html(numFormat(dataInfo.investAmount)); //投资金额
        $("#startDate").html(getDate(dataInfo.startDate).Format("yyyy/MM/dd")); //起息日期
        //$("#endTime").html(dataInfo.endTime); //到期时间
        var accAdd1 = accAdd(dataInfo.annualRate,dataInfo.couponsRateRises);
        var accAdd2 = accAdd(accAdd1,dataInfo.adjustRate);
        $("#annualRate").html(accAdd2); //年化利率
        $("#investMinAmount").html(numFormat(dataInfo.investMinAmount)); //起投金额
        $("#expectedReturn").html(numFormat(dataInfo.expectedReturn)); //预期收益
        if (dataInfo.remanDays == 0) {
            $("#investPeriod").html(dataInfo.investPeriod + "个月"); //理财期限
        }else{
            $("#investPeriod").html(dataInfo.remanDays + "天");//短期标理财期限
        }
        
        $("#repaymentType").html(dataInfo.repaymentType); //还款方式
        $("#contactAmount").html(numFormat(dataInfo.contactAmount)); //借款总额
        if(dataInfo.orderStatus == 5){
            $("#disposeState").html("转让中");
            $("#title1").html("转让申请日期");
            $("#titleContent1").html(getDate(dataInfo.assignApplyDate).Format("yyyy/MM/dd"));//转让申请日
            $("#title2").html("到期日期");
            $("#titleContent2").html(dataInfo.endTime);
            $("#transferCost").show();//转让手续费li
            $("#assignFee").html(dataInfo.assignFee);//转让手续费
            if(dataInfo.applyStatus == 1){
               $("#creditTranferBtn").html('债权转让取消')
                $("#creditTranferBtn").show();//债权转让取消按钮
                $("#creditTranferBtn").click(function(event) {
                    ajaxRequest(contextPath + "wxassign/handleAssignApply", "orderId="+ dataInfo.orderId +"&customerType="+ dataInfo.assignUserType +"&applyFlag=N", setHandleAssignApply,"GET");
                }); 
            }            
        }else if(dataInfo.orderStatus == 4 || dataInfo.orderStatus == 6){
            $("#disposeState").html("回款中");
            $("#title1").html("到期日期");
            $("#titleContent1").html(getDate(dataInfo.endTime).Format("yyyy/MM/dd"));
            $("#title2").html("资金到账");
            $("#titleContent2").html('');
            if(dataInfo.orderStatus == 6){
                $("#title1").html("可转让申请日期");
                $("#titleContent1").html(getDate(dataInfo.assignDate).Format("yyyy/MM/dd")); 
                $("#title2").html("到期日期");
                $("#titleContent2").html(dataInfo.endTime);
                $("#creditTranfer").show();//债权转让入口
                $("#creditTranfer").click(function(event) {
                    if(dataInfo.isAssign == "Y"){
                        window.location.href = contextPath + 'wxproduct/goTransferApplication?orderIdSed=' + dataInfo.orderId +'&valueDate='+dataInfo.startDate+'&investAmount='+dataInfo.investAmount+'&assignUserType='+ dataInfo.assignUserType +'&sloanId='+ dataInfo.sloanId + '&couponsRateRises='+ dataInfo.couponsRateRises;
                    }else{
                        errorMessage('您的投资未满3个月，还不能申请债权转让');
                    }
                });
            }
        }else if(dataInfo.orderStatus  == 1){
            $("#disposeState").html("已转让");
            $("#rateTitle").html("实际年化收益率");
            $("#title1").html("转让申请日期");
            $("#titleContent1").html(getDate(dataInfo.assignApplyDate).Format("yyyy/MM/dd")); 
            $("#title2").html("转让处理日期");
            $("#titleContent2").html(dataInfo.auditTime);
            $("#transferCost").show();//转让手续费
            $("#assignFee").html(dataInfo.assignFee);
            $("#earingTex").html("实际收益");
            $("#expectedReturn").html(dataInfo.annualInterest);//实际收益
            $("#dealUl").hide();//隐藏借款协议入口
        }else if(dataInfo.orderStatus == 2){
            $("#disposeState").html("已完成");
            $("#rateTitle").html("实际年化收益率");
            $("#title1").html("到期日期");
            $("#titleContent1").html(getDate(dataInfo.endTime).Format("yyyy/MM/dd")); 
            $("#title2").html("资金到账");
            $("#titleContent2").html('');
            $("#earingTex").html("实际收益");
//             $("#expectedReturn").html(dataInfo.annualInterest);//实际收益
        }else if(dataInfo.orderStatus == 3){
            $("#disposeState").html("已取消");
            $("#title1").html("取消日期");
            $("#titleContent1").html(getDate(dataInfo.orderCancelDate).Format("yyyy/MM/dd"));  
            $("#title2").html("订单取消");
            $("#titleContent2").html('');
            $("#dealUl").hide();
        };
    }
};
var setBorrowerCompanyInfo = function(data) {
    if (data.rescode == "00000") {
        var dataInfo = data.borrowerCompany;
        $("#companyName").html(dataInfo.projectDetails);
        $("#fundSecurity").html(dataInfo.fundSecurityNew);
        $("#borrowerIntroduction").html(dataInfo.guarantorIntroduction);
    }
};

var setLoanAgreement = function(data){ 
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
        window.location.href = data.serAgreementUrl;
    }
};

$("#loanAgreement").click(function(){
	 ajaxRequest(contextPath + "wxagreement/getServAgreementByType", "type=loanAgreement&orderId="+orderId,setLoanAgreement);
});
$(".vipHint").click(function(){
    $(".vipHintUl").animate({height: 'toggle', opacity: 'toggle'}, "400");  
});
var orderId,sloanId,couponsRateRises;
$(document).ready(function() {
    orderId = $("#orderId").val();
    sloanId = $("#sloanId").val();
    couponsRateRises = $("#couponsRateRises").val();
    ajaxRequest(contextPath + "wxtrade/getOrderFormDetails", "orderId=" + orderId + "&couponsRateRises=" +couponsRateRises, setOrderFormDetails);
    ajaxRequest(contextPath + "wxtrade/getBorrowerCompanyInfo", "sloanId=" + sloanId + "&investType=1", setBorrowerCompanyInfo);
    $("#description h4").click(function(event) {
        $(this).siblings('div').slideToggle(500);
        $(this).children('.thinStick').toggleClass('rotate');
    });
});