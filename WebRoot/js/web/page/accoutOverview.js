$(document).ready(function() {
    ajaxRequest(contextPath + "user/getUserVerifyInfo","",setUserVerify);
    ajaxRequest(contextPath + "wxdeposit/queryMyAccountDetail", "", setShowAssets);//账户信息
    getFundFlowList(1);
});
var setShowAssets = function(data) {
    $("#giftBalance").html(numFormat(data.accountDetail.giftBalance));//礼包资产
    $("#giftPackageSum").html(numFormat(data.accountDetail.giftPackageSum));//礼包数量
    $("#currentAmount").html(numFormat(data.accountDetail.currentAmount));//活期资产
    $("#investAmountSum").html(numFormat(data.accountDetail.investAmountSum));//定期资产
    $("#expectAmount").html(numFormat(data.accountDetail.expectAmount));//定期预计收益
    $("#yesterdayGain").html(numFormat(data.accountDetail.yesterdayGain));//活期昨日收益
    $("#enjoyPlanAmount").html(numFormat(data.accountDetail.enjoyPlanAmount));//优享计划资产
    $("#enjoyYesterdayIncome").html(numFormat(data.accountDetail.enjoyYesterdayIncome));//优享计划昨日收益
}
//交易明细
var setFundFlowList = function(data) { //处理中订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        $(".checkMoreBtn").hide();
        $("#fundFlowList").removeClass('borderB');
        html += '<dd class="textC grayTex PT20" style="line-height:60px">无订单记录</dd>'
    } else {
        $(".checkMoreBtn").show();
        $("#fundFlowList").addClass('borderB');
        $.each(data.list,
        function(i, jsonObj) {
            html += '<dd class="textC heigh50 PB10">';
            html += '<span class="fl width25">'+ jsonObj.transDate +'</span>';
            html += '<span class="fl width20">'+ jsonObj.fundType +'</span>';
            if(jsonObj.type == 0){
                html += '<span class="fl width35">-'+ numFormat(jsonObj.amount) +'</span>';
            }else{
                html += '<span class="fl redTex width35">+'+ numFormat(jsonObj.amount) +'</span>';
            }
            if(jsonObj.status == "交易完成" || jsonObj.status == "处理中"){
                html += '<span class="fl width20 greenTex">'+jsonObj.status+'</span>';
            }else{
                html += '<span class="fl width20 redTex">'+jsonObj.status+'</span>';
            }
            html += '</dd>';

        });
    }
    $("#fundFlowList").append(html);
};
var getFundFlowList = function(page) { //处理中订单调用
	    ajaxRequest(contextPath + "wxtrade/getFundFlowList", "current=" + page + "&pageSize=5", setFundFlowList);
}
//是否认证
var setUserVerify = function(data){
    if(data.idcardVerify == "Y"){
        $("#idcardVerify").show();
        $("#approveBox").remove();
    }else{
        $("#idcardVerify").hide();
        $("#approveBox").show();
    }
    if(data.buildIdCard == "Y"){
        $("#buildIdCard").show();
        $("#bindBox").hide();
    }else{
        $("#buildIdCard").hide();
        $("#bindBox").show();
    }
    if(data.passwordCash == "Y"){
        $("#passwordCash").show();
        $("#setBox").hide();
    }else{
        $("#passwordCash").hide();
        $("#setBox").show();
    }
}

$("#approve").click(function() {
    alertBox(contextPath + "pic/web/qrcode.jpg","160px","扫一扫二维码","请下载联璧金融APP进行实名认证","closeThis");
});
$("#bind").click(function() {
    alertBox(contextPath + "pic/web/qrcode.jpg","160px","扫一扫二维码","请下载联璧金融APP绑定银行卡","closeThis");
});
$("#set").click(function() {
    alertBox(contextPath + "pic/web/qrcode.jpg","160px","扫一扫二维码","请下载联璧金融APP设置交易密码","closeThis");
});
var closeThis = function(){
    $("#alertBox").remove();
}