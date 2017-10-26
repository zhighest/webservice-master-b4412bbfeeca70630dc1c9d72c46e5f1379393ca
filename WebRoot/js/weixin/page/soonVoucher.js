/**
 * Created by huamin on 2017/3/4.
 */
$(document).ready(function(){
    //加速券数据
    ajaxRequest(contextPath + "wxGiftPackage/getKcodeQuickCouponList", "current=1&pageSize=5&status=0",setVouchers,"GET");
});
var getMoreVouchers = function(page){  //获取更多（分页加载台数券 、 加速券 、 第三方兑换券）
      //加速券
    ajaxRequest(contextPath + "wxGiftPackage/getKcodeQuickCouponList", "current="+page+"&pageSize=5&status=0",setVouchers,"GET");
}

var setVouchers = function(data){
    if(data.rescode == "00000"){
        var html = '';
        if(data.list == "" || data.list == null || data.list.length == 0){
            html += '<div class="listNull">';
            html += '<img style="height: 70px" src="../pic/weixin/K3activity/emptyIcon.png">';
            html += '<p class="p1">暂无加速券</p>';
            html += '<p class="p2">如果有加速券，您将在这里看到</p>';
            html += '<a class="block toUsedVoucher positionR PT20 myspan font14" href="'+contextPath+'wxcoupons/usedVoucher?isVoucher=js">点击查看已使用的加速券 ></a>';
            html += '</div>';
        }else{
            $.each(data.list, function(i, jsonObj) {
                var lovValue = jsonObj.lovValue;
                var endDate =getDate(jsonObj.endDate).Format("yyyy/MM/dd hh:mm:ss");
                endDate = endDate.substring(0, 10);
                var quickDays = jsonObj.quickDays;
                html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"useexchangeVouchers('"+ lovValue+"')\">";
                html += '<div class="ticketConcent">';
                html += '<div class="whiteBkg border ticketConcent01" style="padding: 17px 0;">';
                html += '<table class="MLRA" style="width: 100%;"><tr class="boxsizing">';
                html += '<td class="grayTex font12 borderR" style="width: 60%;padding-left: 5%;">';
                html += '<p class="lineHeight1_5x blackTex">'+lovValue+'</p>';
                html += '<p class="lineHeight1_5x MT5" >'+endDate+'到期</p></td>';
                html += '<td class="width30 redTex">';
                html += '<p class="textC"><span class="font40 helveticaneue">'+quickDays+'</span><span>天</span></p>';
                //html += '<p style="height: 14px;"></p>';
                html += '</td></tr></table>';
                html += '</div></div>';
                html += '</li>';
            });
            $("#exchangeList").append(html);
            $("#exchangeListPaging").html(pagingMobile(data, "getMoreVouchers"));
        }
        if(data.pageObject.pageNum == data.pageObject.totlePages && (data.list != "" || data.list.length != 0)){   //当加载到最后一页的时候  底部出现 ‘点击查看已使用的兑换券’
            var htmlBottom = '<a class="block toUsedVoucher PTB16 myspan font14" href="'+contextPath+'wxcoupons/usedVoucher?isVoucher=js">点击查看已使用的加速券 ></a>';
            $("#exchangeList").append(htmlBottom);
        }else if(data.list == "" || data.list == null || data.list.length == 0){
            $("#exchangeList").append(html);
        }
    }else{
        errorMessage(data.resmsg_cn);
    }
}

//点击券的弹窗
var useexchangeVouchers = function(voucherTypeDetail){
    alertBox1("我要为礼包加速","cancelUseIt","取消","goUseVouchers","确定",1)
}
//弹窗点击取消
var cancelUseIt = function(){
    $("#alertBox").remove();
}
//弹窗点击确定
var goUseVouchers = function(){
    window.location.href = contextPath+"wxcoupons/giftBag";
}