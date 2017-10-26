
var ymArray = "";

var getFundFlowList = function(page) { 
    ajaxRequest(contextPath + "wxdeposit/getMoneyFundFlow", "current=" + page + "&fundType=2&pageSize=15", setFundFlowList);
}
// var week=['周日','周一','周二','周三','周四','周五','周六']
var onOff=true;
var setFundFlowList = function(data) { //处理中订单生成HTML
    var sysdate=data.sysdate//获取系统时间
    var html = ""; //拼接html
    if ((data.list == null || data.list == "") && onOff) {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无交易记录</p>';
        html += '<p class="p2">如果有交易记录，您将在这里看到</p>';
        html += '</div>';
        $("#fundNull").append(html); 
        $("#moneyFundFlow").hide(); 
    } else {
        onOff=false;
        $.each(data.list,
        function(i, jsonObj) {
            var newdate=getDate(sysdate);//系统时间
            var date=getDate(jsonObj.transDate.substring(0,10));//交易日期格式化
            var datDate=Math.floor(Math.floor((newdate-date))/1000/86400);//判断交易日期与当前日期的差值来判断是昨天还是今天
            // var iWeek = date.getDay();//获取交易日期是周几
            var ym =jsonObj.transDate.substring(0,7);
            var md =jsonObj.transDate.substring(5,10);
            var newYm=newdate.Format("yyyy/MM")
            var detailTime=jsonObj.transTime.substring(11,16);
            if(ym != ymArray){
                ymArray = ym;
                if(ym == newYm){
                    html += '<li  class="borderB borderT grayBkg textL grayTex"><span class="textL blockC clearfix month">本月</span></li>';
                }else{
                    html += '<li  class="borderB borderT grayBkg textL grayTex"><span class="textL blockC clearfix month">'+ym+'</span></li>';  
                }
            }
            html+='<li class="MLR5 borderB detail">' 
            html+='<div class="outHide">' 
            html+='<div class="fl textL up">'
             if(datDate==0){
                html+='<div class="fl grayTex">今天</div>'
             }else if(datDate==1){
                html+='<div class="fl grayTex">昨天</div>'
             } else{
                html+='<div class="grayTex font16 height">'+md+'</div>'
             }
             html+='<div class="font14 grayTex height down"><span>'+detailTime+'</span></div><div class="fr greytex2"></div>' 
             html+='</div>'
             html += '<div class="fr font18 redTex outMoney"><span style="">'+ numFormat(jsonObj.tranAmount)+'</span></div>';
             html+='</div></li>'
       });
        $("#moneyFundFlow").append(html);
    }
    $("#moneyFundFlowPaging").html(pagingMobile(data, "getFundFlowList"));
};
$(document).ready(function() {
    getFundFlowList(1);
});