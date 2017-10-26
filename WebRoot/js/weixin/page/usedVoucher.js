/**
 * Created by huamin on 2017/2/28.
 */

// setTimeout(getData,500);
var isVoucher;   //通过url拿到的值
var getMoreVouchers;  //加载更多
$(document).ready(function(){
isVoucher  = getUrlParam('isVoucher');
if(isVoucher == "ts"){   //台数券  已使用、已过期数据
    $("title").html("K码券");
    $(".tsVoucher").show();
    getMoreVouchers = function(page){
        ajaxRequest(contextPath + "wxGiftPackage/getQuantityCouponRecordList", "current="+page+"&pageSize=10",setVouchers,"GET");
    }
    //台数券
    ajaxRequest(contextPath + "wxGiftPackage/getQuantityCouponRecordList", "current=1&pageSize=10",setVouchers,"GET");
    function setVouchers(data){
        if(data.rescode == "00000"){
            var html = '';
            if(data.list == "" || data.list == null || data.list.length == 0){
                html += '<div class="listNull">';
                html += '<img style="height: 70px" src="../pic/weixin/K3activity/emptyIcon.png">';
                html += '<p class="p1">暂无已使用K码券</p>';
                html += '<p class="p2">如果有已使用K码券，您将在这里看到</p>';
                html += '</div>';
                $(".tsVoucher").append(html);
            }else{
                $.each(data.list,function(i,jsonObj){
                    html = "";
                    var endDate =getDate(jsonObj.endDate).Format("yyyy/MM/dd hh:mm:ss");
                    endDate = endDate.substring(0, 10);
                    var lovValue = jsonObj.lovValue;
                    var couponNo = jsonObj.couponNo;
                    var useTime = jsonObj.useTime;
                    var useTimeEx =getDate(useTime).Format("yyyy/MM/dd hh:mm:ss");
                    useTimeEx = useTimeEx.substring(0, 10);
                    if(jsonObj.useFlag == "已过期"){  //已过期
                        html = '<li class="expiredVoucher whiteBkg positionR MT10">'+
                            '<div class="borderDiv boxSizing">'+
                            '<div class="borderDiv1 boxSizing PTB10 font14 clearfix">'+
                            '<div style="line-height:25px;">'+
                            '<p>'+lovValue+'</p>'+
                            '<p>'+endDate+'</p>'+
                            '</div></div></div>'+
                            '<img class="positionA usedIcon" height="45" src="../pic/weixin/K3activity/expiredIcon.png" >'+
                            '</li>';
                    }else if(jsonObj.useFlag =="已使用"){  //已使用
                        html = '<li class="usedVoucher whiteBkg positionR MT10">'+
                            '<div class="borderDiv boxSizing">'+
                            '<div class="borderDiv1 boxSizing PTB10 font14">'+
                            '<div style="line-height:25px;">'+
                            '<p>'+lovValue+'</p>'+
                            '<p>'+useTimeEx+'</p>'+
                            '</div></div></div>'+
                            '<img class="positionA usedIcon" height="45" src="../pic/weixin/K3activity/usedIcon.png" >'+
                            '</li>';
                    }
                    $(".tsVoucher").append(html);
                });
            }
            $("#exchangeListPaging").html(pagingMobile(data, "getMoreVouchers"));
        }else{
            errorMessage(data.resmsg_cn);
        }
    }
}
if(isVoucher == "qdb"){  //第三方（趣夺宝）  已使用、已过期的兑换券
    $("title").html("兑换券");
    $(".qdbVoucher").show();
    getMoreVouchers = function(page){
        ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=3&current="+page+"&pageSize=10",setVouchers,"GET");
    }
    //
    ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=3&current=1&pageSize=10",setVouchers,"GET");
    function setVouchers(data){
        if(data.rescode == "00000"){
            var html = '';
            if(data.list == "" || data.list == null || data.list.length == 0){
                html += '<div class="listNull">';
                html += '<img style="height: 70px" src="../pic/weixin/K3activity/emptyIcon.png">';
                html += '<p class="p1">暂无已使用兑换券</p>';
                html += '<p class="p2">如果有已使用兑换券，您将在这里看到</p>';
                html += '</div>';
                $(".qdbVoucher").append(html);
            }else{
                $.each(data.list,function(i,jsonObj){
                    html = "";
                    var couponName = jsonObj.voucherTypeDetail;
                    var voucherAmount = jsonObj.voucherAmount;
                    var voucherId=jsonObj.id;
                    if(jsonObj.status == "2"){  //已过期
                        var endDate = getDate(jsonObj.endTime).Format("yyyy/MM/dd hh:mm:ss").substring(0, 10);
                        html = '<li class="expiredVoucher whiteBkg positionR MT10" onclick="goUseVouchers('+voucherId+')" >'+
                        '<div class="borderDiv boxSizing">'+
                        '<div class="borderDiv1 boxSizing PTB10 font14 clearfix">'+
                        '<div class="fl vouName textL boxSizing">'+
                        '<p>'+couponName+'</p>'+
                        '<p class="">'+endDate+'</p>'+
                        '</div>'+
                        '<div class="fl vouMoney">'+
                        '<p class="font18">￥ <span class="font40 helveticaneue">'+voucherAmount+'</span></p>'+
                        '</div></div></div>'+
                        '<img class="positionA usedIcon" height="45" src="../pic/weixin/K3activity/expiredIcon.png" >'+
                        '</li>';
                    }else if(jsonObj.status =="1"){  //已使用
                        var endTime = getDate(jsonObj.endTime).Format("yyyy/MM/dd hh:mm:ss").substring(0, 10);
                        html = '<li class="usedVoucher whiteBkg positionR MT10" onclick="goUseVouchers('+voucherId+')">'+
                        '<div class="borderDiv boxSizing">'+
                        '<div class="borderDiv1 boxSizing PTB10 font14 clearfix">'+
                        '<div class="fl vouName textL boxSizing">'+
                        '<p>'+couponName+'</p>'+
                        '<p class="">'+endTime+'</p>'+
                        '</div>'+
                        '<div class="fl vouMoney">'+
                        '<p class="font18">￥ <span class="font40 helveticaneue">'+voucherAmount+'</span></p>'+
                        '</div></div></div>'+
                        '<img class="positionA usedIcon" height="45" src="../pic/weixin/K3activity/usedIcon.png" >'+
                        '</li>';
                    }
                    $(".qdbVoucher").append(html);
                });
            }
            $("#exchangeListPaging").html(pagingMobile(data, "getMoreVouchers"));
        }else{
            errorMessage(data.resmsg_cn);
        }
    }

}
if(isVoucher == "js"){
    $("title").html("加速券");
    $(".jsVoucher").show();
    getMoreVouchers = function(page){
        ajaxRequest(contextPath + "wxGiftPackage/getKcodeQuickCouponList", "current="+page+"&pageSize=10&status=1",setVouchers,"GET");
    }
    //加数券
    ajaxRequest(contextPath + "wxGiftPackage/getKcodeQuickCouponList", "current=1&pageSize=10&status=1",setVouchers,"GET");
    function setVouchers(data){
        if(data.rescode == "00000"){
            var html = '';
            if(data.list == "" || data.list == null || data.list.length == 0){
                html += '<div class="listNull">';
                html += '<img style="height: 70px" src="../pic/weixin/K3activity/emptyIcon.png">';
                html += '<p class="p1">暂无已使用加速券</p>';
                html += '<p class="p2">如果有已使用加速券，您将在这里看到</p>';
                html += '</div>';
                $(".jsVoucher").append(html);
            }else{
                $.each(data.list,function(i,jsonObj){
                    var lovValue = jsonObj.lovValue;
                    var endDate =getDate(jsonObj.endDate).Format("yyyy/MM/dd hh:mm:ss");
                    endDate = endDate.substring(0, 10);
                    var quickDays = jsonObj.quickDays;
                    var useTime = getDate(jsonObj.useTime).Format("yyyy/MM/dd hh:mm:ss");
                    useTime = useTime.substring(0, 10);
                    if(jsonObj.useFlag == "已过期"){  //已过期
                        html += "<li class='border whiteBkg PLR5 PTB5 MT10 positionR'>";
                        html += '<div class="ticketConcent">';
                        html += '<div class="whiteBkg border ticketConcent01 PTB10">';
                        html += '<table class="width90 MLRA"><tr class="boxsizing">';
                        html += '<td class="width70 grayTex font12 borderR">';
                        html += '<p class="lineHeight1_5x">'+lovValue+'</p>';
                        html += '<p class="lineHeight1_5x MT5" >'+endDate+'</p></td>';
                        html += '<td class="width30 grayTex">';
                        html += '<p class="textC"><span class="font40 helveticaneue">'+quickDays+'</span><span>天</span></p>';
                        html += '<p style="height: 14px;"></p>';
                        html += '</td></tr></table>';
                        html += '</div></div>';
                        html += '<img class="positionA usedIcon" height="45" src="../pic/weixin/K3activity/expiredIcon.png" >';
                        html += '</li>';
                    }else if(jsonObj.useFlag == "已使用"){  //已使用
                        html += "<li class='border whiteBkg PLR5 PTB5 MT10 positionR'>";
                        html += '<div class="ticketConcent">';
                        html += '<div class="whiteBkg border ticketConcent01 PTB10">';
                        html += '<table class="width90 MLRA"><tr class="boxsizing">';
                        html += '<td class="width70 grayTex font12 borderR">';
                        html += '<p class="lineHeight1_5x">'+lovValue+'</p>';
                        html += '<p class="lineHeight1_5x MT5" >'+useTime+'</p></td>';
                        html += '<td class="width30 grayTex">';
                        html += '<p class="textC"><span class="font40 helveticaneue">'+quickDays+'</span><span>天</span></p>';
                        html += '<p style="height: 14px;"></p>';
                        html += '</td></tr></table>';
                        html += '</div></div>';
                        html += '<img class="positionA usedIcon" height="45" src="../pic/weixin/K3activity/usedIcon.png" >';
                        html += '</li>';
                    }
                });
                $(".jsVoucher").append(html);
            }
            $("#exchangeListPaging").html(pagingMobile(data, "getMoreVouchers"));
        }else{
            errorMessage(data.resmsg_cn);
        }
    }
}

});

//已使用|已过期的券点击功能
var goUseVouchers = function(id){
    var data = {};
    if(!!id){
        data = {"voucherId":id};
    }
    ajaxRequest(contextPath + "shopVochers/goUseShopVouchers", data,setUseShopVouchers,"GET");
}
//点击已使用|已过期的券跳转至券使用详情页
var setUseShopVouchers = function(data){
    if(data.rescode=="00000"){
        window.location.href = data.yygurl;
    }
}