//加息券列表
function setMyRateCoupons(data){
    var html = "";
    if(data.rescode == "00000"){
        if(data.rateCouponsList == "" || data.rateCouponsList == null){
            html += '<div class="textC">';
            html += '<img src="'+contextPath+'/pic/web/nothing.png">';
            html += '<p class="width90 blockC MT10 grayTex83 textC font18">暂无加息券</p></div>';
        }else{
            $.each(data.rateCouponsList, function(i, jsonObj) {
                html += '<li class="fl clearfix positionR cursor http://222.73.156.45:7080/webservice/trade/voucher" onclick="alertTip()" style="z-index: 2">';
                html += '<div class="width100 clearfix positionR PTB30">';
                //表明定活期的小圆标
                if(jsonObj.product_id == "109" && jsonObj.status == 0){//活期，未使用
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/demanCouSign.png" width="45px" class="positionA signImg">';
                }else if(jsonObj.product_id == "108" && jsonObj.status == 0){//定期，未使用
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/fixCouSign.png" width="45px" class="positionA signImg">';
                }else if(jsonObj.product_id == "109" && (jsonObj.status == 1 || jsonObj.status == 2)) {//活期过期或者已使用
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/demCouUsedSign.png" width="45px" class="positionA signImg">';
                }else if(jsonObj.product_id == "108" && (jsonObj.status == 1 || jsonObj.status == 2)) {//定期过期或者已使用
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/fixCouUsedSign.png" width="45px" class="positionA signImg">';
                };

                
                //内容区域
                html += '<div class="width90 blockC clearfix whiteTex">';
                //左半边
                if(jsonObj.status == 0){//未使用
                    html += '<div class="fl positionR couNumCon">';
                }else if(jsonObj.status == 1||jsonObj.status == 2){//已使用或者已过期
                    html += '<div class="fl positionR couNumUsedCon">';
                }
                html += '<p class="font14">年化</p>';
                html += '<div class="font40 positionR">+'+jsonObj.rate_rises;//加息利率jsonObj.rate_rises
                html += '<img class="upIcon positionA" src="'+contextPath+'pic/web/accoutOverview/upIcon.png" width="16px"></div>';//箭头变形的百分号图标
                html += '</div>';
                //右半边
                html += '<div class="fl ML10 font14 clearfix helveticaneue lineHeight1_5x">';
                if(jsonObj.product_id == "109"){
                    html += '<p>'+jsonObj.rate_rises_days+'<span>天</span></p>';
                }else{
                    if(jsonObj.remanDays == "0") {
                        html += '<p>'+jsonObj.rate_rises_days+'<span>个月</span></p>';   
                    }else{
                        html += '<p>'+jsonObj.remanDays+'<span>天</span></p>';
                    }
                }
                html += '<p>'+jsonObj.product_id_str+'</p>';//标名
                html += '<p>到期日期：'+jsonObj.validity_period_str+'</p>';
                html += '</div>';
                html += '</div>';
                if(jsonObj.status == 0){//未使用
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/couponBkg.png" class="couponBkg">';
                }else if(jsonObj.status == 1||jsonObj.status == 2){//已使用或者已过期
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/couponUsedBkg.png" class="couponBkg">';
                };
                html +='</div>';
                if(jsonObj.status == 0){//未使用
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/redShadow.png" class="width100" height="40px">';
                }else if(jsonObj.status == 1||jsonObj.status == 2){//已使用或者已过期
                    html += '<img src="'+contextPath+'pic/web/accoutOverview/grayShadow.png" class="width100" height="40px">';
                };
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
    product = 0;
    ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "product=0&current=1&pageSize=9",setMyRateCoupons,"GET");
});
//点击全部事件
$("#all").click(function(event) {
    $("#rateCouponsList").empty();
    $(this).addClass('current').siblings().removeClass('current');
    product = "";
    ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "current=1&pageSize=9",setMyRateCoupons,"GET");
});
var setRateCouponsList = function(page) {
    ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "product="+product+"&current=" + page + "&pageSize=9", setMyRateCoupons,"GET");
};
//点击加息券提示
var alertTip = function(){
    alertBox(contextPath + "pic/web/downloadDetails/QR-code.png","140px","请下载联璧金融APP使用加息券","","exitThisOne",2)
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
    ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "current=1&pageSize=9",setMyRateCoupons,"GET");
});