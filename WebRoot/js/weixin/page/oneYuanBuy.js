flag=0;
$(document).ready(function() {
	//所有代金券列表接口
	ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=0&current=1&pageSize=5",setShopVouchers,"GET");
});
var getShopVouchers = function(page){
	ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "current="+page+"&pageSize=5&flag="+flag,setShopVouchers,"GET");
}
var setShopVouchers = function(data){
	if(data.rescode == "00000"){
		var html = '';
        if(data.list == "" || data.list == null){
            html += '<div class="listNull">';
            html += '<img src="../pic/weixin/list.png">';
            if(flag == 0){
                html += '<p class="p1">暂无未使用的兑换券</p>';
                html += '<p class="p2">如果有未使用的兑换券，您将在这里看到</p>';
            }else if(flag==1){
                html += '<p class="p1">暂无已使用的兑换券</p>';
                html += '<p class="p2">如果有已使用的兑换券，您将在这里看到</p>';
            }
            html += '</div>';
        }else{
            $.each(data.list, function(i, jsonObj) {
            	var endTime = jsonObj.endTime;
            	var endTimeEx =getDate(endTime).Format("yyyy/MM/dd hh:mm:ss");
                endTimeEx = endTimeEx.substring(0, 10);
                if(jsonObj.status==0){
                    html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"useexchangeVouchers('"+ jsonObj.voucherTypeDetail+"')\">";
                }else{
                    html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"usedexchangeVouchers('"+ jsonObj.voucherTypeDetail+"')\">";
                }   
                html += '<div class="ticketConcent">';
                html += '<div class="whiteBkg border ticketConcent01 PTB10">';
                html += '<table class="width90 MLRA"><tr class="boxsizing">';
                html += '<td class="width70 grayTex font12 borderR">';
                if(jsonObj.status==0){
                    html += '<p class="lineHeight1_5x blackTex">'+jsonObj.voucherTypeDetail+'</p>';
                }else{
                    html += '<p class="lineHeight1_5x grayTex">'+jsonObj.voucherTypeDetail+'</p>';
                }
                html += '<p class="lineHeight1_5x MT5" >'+endTimeEx+'到期</p></td>';
                if(jsonObj.status==0){
                    html += '<td class="width30 redTex">';
                }else{
                    html += '<td class="width30 grayTex">';
                }
                html += '<p class="textC"><span>￥</span><span class="font40 helveticaneue">'+jsonObj.voucherAmount+'</span></p>';
                html += '<p class="font12 textC">智豆</p>';
                html += '</td></tr></table>';
                html += '</div></div>';
                html += '</li>';
            }); 
        }
		$("#exchangeList").append(html);
        $("#exchangeListPaging").html(pagingMobile(data, "getShopVouchers"));
	}else{
        errorMessage(data.resmsg_cn);
    }
}

//点击券的弹窗
var useexchangeVouchers = function(voucherTypeDetail){
    alertBox1("确定使用"+voucherTypeDetail,"cancelUseIt","取消","goUseVouchers","确定",1)
}
var usedexchangeVouchers = function(voucherTypeDetail){
    alertBox1("是否去智仟汇查看“趣夺宝”活动结果","cancelUseIt","取消","goUseVouchers","确定",1)
}
//弹窗点击取消
var cancelUseIt = function(){
    $("#alertBox").remove();

}
//弹窗点击确定
var useUrl=$("#gourl").val();
var key=$("#key").val();
var channel=$("#channel").val();
var goUseVouchers = function(){
    ajaxRequest(contextPath + "shopVochers/goUseShopVouchers", "",setUseShopVouchers,"GET");
}
var setUseShopVouchers = function(data){
    if(data.rescode=="00000"){
        window.location.href = data.yygurl;
    }
}

//tab切换事件
//点击未使用
$("#noUse").click(function() {
    flag = 0;
    $("#noUse").addClass('current');
    $("#used").removeClass('current');
    $("#exchangeList").empty();
    ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=0&current=1&pageSize=5",setShopVouchers,"GET");
});
//点击已使用
$("#used").click(function() {
    flag = 1;
    $("#used").addClass('current');
    $("#noUse").removeClass('current');
    $("#exchangeList").empty();
    ajaxRequest(contextPath + "shopVochers/queryShopVouchers", "flag=1&current=1&pageSize=5",setShopVouchers,"GET");
});

