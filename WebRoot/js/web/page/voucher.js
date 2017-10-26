//加息券列表
function setMyRateCoupons(data){
    var html = "";
    if(data.rescode == "00000"){
        if(data.list == "" || data.list == null){
            html += '<div class="textC">';
            html += '<img src="'+contextPath+'/pic/web/nothing.png">';
            html += '<p class="width90 blockC MT10 grayTex83 textC font18">暂无代金券</p></div>';
        }else{
            $.each(data.list, function(i, jsonObj) {
                    html += '<li class="clearfix fl ML10 MR10 MT20 textC positionR clearfix cursor helveticaneue" onclick="alertTip()">';
                    html += '<div class="clearfix blockC positionR liBorder">';
                    html += '<div class="clearfix blockC positionR tabBorder">';
                    html += '<table style="width:100%"><tr><td class="width60 grayTex61 font12">';
                    html += '<p class="lineHeight1_5x textL">'+jsonObj.scatteredLoanDesc+'</p>';//适用于
                    var str=jsonObj.validityPeriodDesc;
                    var endTime = str.substring(0,10); 
                    html += '<p class="lineHeight1_5x MT5 textL">'+endTime;//到期日期
                    html += '</p></td>';
                    html += '<td class="width40 redTex"><p class="textC PT10">';
                    html += '<span>￥</span>';
                    html += '<span class="font40 helveticaneue">'+jsonObj.voucherAmount;
                    html += '</span></p>';
                    html += '<p class="font12" style="min-height:24px;">'+jsonObj.voucherDesc;
                    html += '</td></tr></table>';
                    html += '</div></div>';
                    html += '</li>';
            });    
        }
    }
    $("#rateCouponsList").append(html);
    $("#proListPaging").html(pagingMobile(data, "setRateCouponsList"));
}
//点击活期事件
$("#demand").click(function(event) {
    $("#rateCouponsList").empty();
    $(this).addClass('current').siblings().removeClass('current');
    ajaxRequest(contextPath + "vouchers/myVouchers", "product=0&current=1&pageSize=9",setMyRateCoupons,"GET");
    product = 0;
});
//点击全部事件
$("#all").click(function(event) {
    $("#rateCouponsList").empty();
    $(this).addClass('current').siblings().removeClass('current');
    product = "";
    ajaxRequest(contextPath + "vouchers/myVouchers", "current=1&pageSize=9",setMyRateCoupons,"GET");
});
var setRateCouponsList = function(page) {
    ajaxRequest(contextPath + "vouchers/myVouchers", "product="+product+"&current=" + page + "&pageSize=9", setMyRateCoupons,"GET");
};
//点击加息券提示
var alertTip = function(){
    alertBox(contextPath + "pic/web/downloadDetails/QR-code.png","140px","请下载联璧金融APP使用代金券","","exitThisOne",2)
}
var exitThisOne = function(){
    $("#alertBox").remove();
}
var product;//定活期标识：0 活期 1 定期1个月 3 定期3个月 6 定期6个月 12 定期12个月
var status;//加息券状态：0 未使用 1 已使用 2 已过期
var sloanId;
var loanId;
$(document).ready(function() {
    product = $("#product").val();
    sloanId = $("#sloanId").val();
    loanId = $("#loanId").val();
    ajaxRequest(contextPath + "vouchers/myVouchers", "current=1&pageSize=9&product="+product,setMyRateCoupons,"GET");
});

	
