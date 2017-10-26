/**
 * Created by huamin on 2017/3/4.
 */
$(document).ready(function() {
    //第三方兑换券数据
    ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=0&current=1&pageSize=5",setVouchers,"GET");
});
var getMoreVouchers = function(page){  //获取更多
    ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=0&current="+page+"&pageSize=5",setVouchers,"GET");
}

var setVouchers = function(data){
    if(data.rescode == "00000"){
        var html = '';
        if(data.list == "" || data.list == null || data.list.length == 0){
            html += '<div class="listNull">';
            html += '<img style="height: 70px" src="../pic/weixin/K3activity/emptyIcon.png">';
            html += '<p class="p1">暂无兑换券</p>';
            html += '<p class="p2">如果有兑换券，您将在这里看到</p>';
            html += '<a class="block toUsedVoucher positionR PT20 myspan font14" href="'+contextPath+'wxcoupons/usedVoucher?isVoucher=qdb">点击查看已使用的兑换券 ></a>';
            html += '</div>';
        }else{
            $.each(data.list, function(i, jsonObj) {
                var endTime = jsonObj.endTime;
                var endTimeEx =getDate(endTime).Format("yyyy/MM/dd hh:mm:ss");
                endTimeEx = endTimeEx.substring(0, 10);
                if(jsonObj.status==0 || jsonObj.status==3){
                    html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"useexchangeVouchers('"+ jsonObj.voucherTypeDetail+"','" +jsonObj.id+"')\">";
                }
                html += '<div class="ticketConcent">';
                html += '<div class="whiteBkg border ticketConcent01 PTB10">';
                html += '<table class="width90 MLRA"><tr class="boxsizing">';
                html += '<td class="width70 grayTex font12 borderR">';
                if(jsonObj.status==0 || jsonObj.status==3){
                    html += '<p class="lineHeight1_5x blackTex">'+jsonObj.voucherTypeDetail+'</p>';
                }
                html += '<p class="lineHeight1_5x MT5" >'+endTimeEx+'到期</p></td>';
                if(jsonObj.status==0 || jsonObj.status==3){
                    html += '<td class="width30 redTex">';
                }
                html += '<p class="textC"><span>￥</span><span class="font40 helveticaneue">'+jsonObj.voucherAmount+'</span></p>';
                html += '</td></tr></table>';
                html += '</div></div>';
                html += '</li>';
            });
            $("#exchangeList").append(html);
            $("#exchangeListPaging").html(pagingMobile(data, "getMoreVouchers"));
        }
        if(data.pageObject.pageNum == data.pageObject.totlePages && (data.list != "" || data.list.length != 0)){   //当加载到最后一页的时候  底部出现 ‘点击查看已使用的兑换券’
            var htmlBottom = '<a class="block toUsedVoucher PTB16 myspan font14" href="'+contextPath+'wxcoupons/usedVoucher?isVoucher=qdb">点击查看已使用的兑换券 ></a>';
            $("#exchangeList").append(htmlBottom);
        }else if(data.list == "" || data.list == null || data.list.length == 0){
            $("#exchangeList").append(html);
        }
    }else{
        errorMessage(data.resmsg_cn);
    }
}

//点击券的弹窗
var useexchangeVouchers = function(detail,id){
    alertBox3("确定使用"+detail,"cancelUseIt","取消","goUseVouchers","确定",1,id);
}
var usedexchangeVouchers = function(detail,id){
    alertBox3("是否查看" + +detail,"cancelUseIt","取消","goUseVouchers","确定",1,id);
}
//弹窗点击取消
var cancelUseIt = function(){
    $("#alertBox").remove();

}
//弹窗点击确定
var key=$("#key").val();
var channel=$("#channel").val();
var goUseVouchers = function(id){
    var data = {};
    if(!!id){
        data = {"voucherId":id};
    }
    ajaxRequest(contextPath + "shopVochers/goUseShopVouchers", data,setUseShopVouchers,"GET");
}
var setUseShopVouchers = function(data){
    if(data.rescode=="00000"){
        window.location.href = data.yygurl;
    }
}


