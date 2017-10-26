pageNum = 0;   //分页标识
var scrollTop = 0;//给top变量一个初始值，以便下方赋值并引用。
//大礼包列表
$(document).ready(function(){
    ajaxRequest(contextPath + "wxGiftPackage/queryGiftPackageList","current=1&pageSize=5&packageType=''&type=1", giftPackageList);
});

var getMoreGiftBags = function(page){  //获取更多
    ajaxRequest(contextPath + "wxGiftPackage/queryGiftPackageList", "current="+page+"&pageSize=5&packageType=''&type=1",giftPackageList,"GET");
}
function giftPackageList(data){
    pageNum++;
    if(data.topRemind != "" && data.topRemind != null && data.topRemind != 'null' ){
        $(".topRemind").show().html(data.topRemind);
    }
    if(data.quickCouponSwitch == "Y"){   //加速券开关
        $(".goSoonVoucher").show();
    }
    if(data.list == "" || data.list == null || data.list.length == 0){  //如果没有礼包资产
        var html1 =　"";
        html1 += '<div class="listNull">';
        html1 += '<img src="../pic/weixin/list.png">';
        html1 += '<p class="p1">暂无K码礼包</p>';
        html1 += '<p class="p2">如果有K码礼包，您将在这里看到</p>';
        html1 += '</div>';
        $(".giftWrap").append(html1);
    }else{
        for(var i = 0; i < data.list.length; i++){
            var quickCouponNumber = data.list[i].quickCouponNumber;  //加速券数量（判断是否可加速，大于0 可以加速）
            var packageType = data.list[i].packageType;  //礼包类型: KCODE K码礼包 WELFARE 福利礼包
            var html = "";
            var packageName = data.list[i].packageName;  //礼包名称
            var kcodeMoney = data.list[i].kcodeMoney;  //礼包资产
            var surplusMoney = data.list[i].surplusMoney;   //剩余未兑换金额
            var endDate = data.list[i].endDate;  //礼包兑换到期日期
            var bagId = data.list[i].id;  //礼包Id
            var currExchangeMoney = data.list[i].currExchangeMoney;  //当期可兑换金额
            if(data.list[i].tradeNum != undefined && data.list[i].tradeNum.toString().length > 0 && data.list[i].tradeNum > 0){
                var tradeNum = data.list[i].tradeNum;  // 是否是二手
            }
            var enable = data.list[i].enable;  //按钮显示：1：显示按钮，0：灰掉按钮
            endDate = getDate(endDate).Format("yyyy/MM/dd hh:mm:ss");
            endDate = endDate.substring(0, 10);
            var islocked = data.list[i].islock;  //是否在锁定期
            if(islocked == "Y"){
                islocked = 1;
            }else if(islocked == "N"){
                islocked = 0;
            }
            var statusText = data.list[i].statusText;  //按钮文字 状态
            if(statusText == "已冻结"){   //如果是冻结，点击我要加速直接弹框
                var statusTextNum = 1;
            }else{
                var statusTextNum = 0;
            }
            if(data.list[i].enable == "1" || (statusText == "已冻结" || statusText == "销售中")){  //可兑换 彩色图片
                var imageUrl = data.list[i].imageUrl;
            }else{   //不可兑换  灰色图片
                var imageUrl = data.list[i].disabledImageUrl;
            }
            if(packageType == "KCODE"){  //K码礼包
                html += '<div class="k3Bag whiteBkg MT10 positionR">';
                if(tradeNum > 0){
                    html += '<span class="positionA transfer"></span>';
                }
                html +='<div class="clearfix">';
                html +='<div class="fl bagLeft">';
                if(enable == "0" && statusText != "已冻结" && statusText != "销售中"){  //灰色的文案和图片
                    html +='<p class="grayTex k3Detail textL PTB15 PLR10 font16 giftPlan" onclick="giftPlanFun('+bagId+')">'+packageName+' <img class="verMid" height="20" src="../pic/weixin/K3activity/usedDetailIcon.png" /></p>';
                    html +='<P class="textL PLR10 grayTex font14">礼金&nbsp;&nbsp;￥<span class="giftMoney grayTex font28">'+surplusMoney+'</span></P>';
                }else{
                    html +='<p class="redTex k3Detail textL PTB15 PLR10 font16 giftPlan" onclick="giftPlanFun('+bagId+')">'+packageName+' <img class="verMid" height="20" src="../pic/weixin/K3activity/detailIcon.png" /></p>';
                    html +='<P class="textL PLR10 blackTex2 font14">礼金&nbsp;&nbsp;￥<span class="giftMoney redTex font28">'+surplusMoney+'</span></P>';
                }
                html +='<p class="grayTex PLR10 textL font14 PTB15">礼包总礼金&nbsp;&nbsp;<span class="allMoney">￥'+kcodeMoney+'</span></p></div>';
                html +='<div class="fr bagRight">';
                html +='<img class="PT10" height="70" src="'+imageUrl+'" />';
                if(enable == "0" && statusText != "已冻结" && statusText != "销售中") {  //灰色的文案和图片
                    html +='<a class="wantSoon block grayTex heigh30 none" style="border: 1px solid #9F9F9F">我要加速</a>';
                }else{
                    html +='<a class="wantSoon redBorder redTex heigh30 none" onclick="getQuickCouponList('+bagId+','+islocked+','+statusTextNum+')">我要加速</a>';
                }
                html +='</div></div>';
                html +='<a class="block exchangeBtn heigh40 radius5 font12" onclick="exchangeGiftBag('+bagId+',\''+currExchangeMoney+'\')">'+statusText+'</a></div>';
            }

            if(packageType == "WELFARE"){  // 福利礼包
                html +=  '<div class="welfareBag k3Bag whiteBkg positionR MT10">';
                if( tradeNum > 0){
                    html +=  '<span class="positionA transfer"></span>';
                }
                html +=  '<div class="clearfix">';
                html +=  '<div class="fl welfareBagLeft">';
                html +=  '<p class="redTex k3Detail textL PTB20 PLR10 font16">'+packageName+'</p>';
                html +=  '<p class="grayTex PLR10 textL font14 PTB5">兑换到期日&nbsp;&nbsp;<span class="allMoney">'+endDate+'</span></p>';
                html +=  '</div>';
                html +=  '<div class="fr bagRight">';
                html +=  '<img class="PT10" height="70" src="'+imageUrl+'" />';
                html +=  '<a class="wantSoon redBorder redTex heigh30 none">我要加速</a>';
                html +=  '</div></div>';
                html +=  '<a class="block exchangeBtn walfareBtn heigh40 radius5 font12" onclick="walfareBag('+bagId+','+data.list[i].packageId+')">'+statusText+'</a></div>';
            }

            $(".giftWrap").append(html);   //加载数据
            if(data.quickCouponSwitch == "Y" && quickCouponNumber > 0){  //quantityCouponSwitch:加速券开关
                $(".wantSoon").eq((pageNum-1)*5 + i).removeClass('none').addClass("block");   //如果有加速券 就显示  我要加速 按钮
            }else if(enable == "0" && statusText != "立即兑换"&& statusText != "已冻结"&& statusText != "销售中"){
                $(".k3Detail").eq((pageNum-1)*5 + i).removeClass("redTex").addClass("colorA9 font20");
            }
            if(statusText == "立即兑换"){  //按钮状态
                $(".exchangeBtn").eq((pageNum-1)*5 + i).addClass("whiteTex redBkg");
            }else{
                $(".exchangeBtn").eq((pageNum-1)*5 + i).addClass("colorA9 bkg4");
                $(".exchangeBtn").eq((pageNum-1)*5 + i).removeAttr('onclick');
            }

            $("#exchangeListPaging").html(pagingMobile(data, "getMoreGiftBags"));   //加载更多
        }
    }

}
//礼包兑换计划 start
var giftPlanFun = function(orderId){
    $(".screenBkg").show();
    $(".k3Box").show();
    bodyFixedFun();
    ajaxRequest(contextPath + "wxGiftPackage/giftExchangePlan","orderId="+orderId, giftExchangePlan);  //礼包兑换计划
}
function giftExchangePlan(data){  ////礼包兑换计划
    $(".bagDetail").html("");  //礼包兑换计划清空列表
    var items = data.list;
    for(var i = 0; i < items.length; i++){
        var html = '<li class="clearfix redTex PLR5 radius5 remFont28">'+
            '<span class="fl">礼金'+items[i].amount+'</span>'+
            '<span class="fr">'+items[i].status+'</span></li>';
        $(".bagDetail").append(html);
        if(items[i].status == "可领取"){
            $(".bagDetail li").eq(i).addClass("redTex");
        }else{
            $(".bagDetail li").eq(i).addClass("colorA3");
        }
    }
}

//礼包兑换计划 end

// 弹出层出现的时候 body 定位
function bodyFixedFun(){
    $("#moreBtn").removeAttr("onclick");
    scrollTop = $(window).scrollTop(); //获取页面的scrollTop；
    $('body').css("top",-scrollTop+"px");//给body一个负的top值；
    $('body').addClass('positionF');
}
function bodyRemoveFixed(){
    $('body').removeClass('positionF');//去掉给body的类
    $(window).scrollTop(scrollTop);//设置页面滚动的高度，如果不设置，关闭弹出层时页面会回到顶部。
    setTimeout(function(){
        $("#moreBtn").attr("onclick","errorMessage('没有更多了')");
    },2000);
}

// 我要加速 start
var jsBagId = "";  //要加速的礼包ID
var couponNum = "";  // 券号
var voucherDays = "";  //加速天数
var isLock = "";  //礼包是否在锁定期
var isCheck = false;  // 判断是否选了 加速券
var getQuickCouponList = function(orderId,islocked,statusTextNum){
    jsBagId = orderId;
    isLock = islocked;
    if(statusTextNum == 1){  //该礼包是否已冻结状态
        alertBox1("该礼包已冻结，请联系在线客服","","","cancelUseIt","知道了",2);
    }else{
        ajaxRequest(contextPath + "wxGiftPackage/getKcodeQuickCouponList","current=1&pageSize=1000&status=0&giftPackageOrderId="+orderId, quickCouponList);  //对应的礼包加速券列表
    }
}
var quickCouponList = function(data){
    $(".jsDetail").html("");
    if(data.rescode=="00000"){
        if(data.list.length > 0){
            for(var i = 0; i < data.list.length; i++){
                var lovValue = data.list[i].lovValue;  //加速券名称
                var endDate = data.list[i].endDate;   //加速券到期时间
                endDate = getDate(endDate).Format("yyyy/MM/dd hh:mm:ss");
                endDate = endDate.substring(0, 10);
                var quickDays = data.list[i].quickDays;  //加速天数
                var couponNo = data.list[i].couponNo;  //券号'selecthoods(\" "+a + "\")
                var html = '<li class="colorA3 clearfix PLR5 font14 MB5" couponNo='+couponNo+' quickDays='+quickDays+'>'+
                    '<div class="fl">'+
                    '<img class="checkImg" height="20" src="'+contextPath+'/pic/weixin/K3activity/nocheck.png" >'+
                    '</div><div class="fl voucherDetail clearfix PLR10 boxSizing">'+
                    '<div class="fl blackTex1 remFont28">'+
                    '<p class="textL blackTex">'+lovValue+'</p>'+
                    '<p class="textL remFont24">'+endDate+'到期</p></div>'+
                    '<p class="fr" style="margin-top:0.85rem;">'+
                    '<span class="blackTex remFont32">'+quickDays+'<span class="remFont24">天</span></span></p></div></li>';
                $(".jsDetail").append(html);
            }
            $(".screenBkg").show();
            $(".jsDetailBox").show();
            bodyFixedFun();
        }else{
            bodyFixedFun();
            alertBox1("暂无可使用的加速券","cancelUseIt","取消","toGetVoucher","获取加速券",1);
        }
    }

    $(".jsDetail li").click(function(){
        isCheck = true;
        couponNum = $(this).attr("couponNo");
        voucherDays = $(this).attr("quickDays");
        $(".checkImg").attr('src',contextPath+'/pic/weixin/K3activity/nocheck.png');
        $(this).find(".checkImg").attr('src',contextPath+'/pic/weixin/K3activity/checked.png');
        $(".jsDetail").find("p").removeClass("redTex");
        $(".jsDetail").find("p span").removeClass("redTex");
        $(this).find("p").addClass("redTex");
        $(this).find("p span").addClass("redTex");
    });

}

var toGetVoucher = function(){
    window.location.href = contextPath + "wxcoupons/jsVoucher";
}
$(".confirm").click(function(){
    if(!isCheck){
        errorMessage("请选择加速券");
    }else{
        $(".jsDetailBox").hide();
        if(isLock == 1){  // 判定礼包是否在锁定期
            $("#alertBox").remove();
            alertBox1("礼包尚在锁定期内,确认使用加速券","cancelUseIt","取消","confirmUseVoucher","确定",1);
        }else{
            $("#alertBox").remove();
            alertBox1("使用"+voucherDays+"天加速券，礼金兑换时间可提前"+voucherDays+"天，确认使用加速券","cancelUseIt","取消","confirmUseVoucher","确定",1);
        }
    }
});
var confirmUseVoucher = function(){
    $("#alertBox").remove();
    ajaxRequest(contextPath + "wxGiftPackage/useQuickCoupon","couponNo="+couponNum+"&giftPackageOrderId="+jsBagId, useQuickCoupon); //使用加速券
}
var useQuickCoupon = function(data){
    $("#alertBox").remove();
    $(".screenBkg").hide();
    bodyRemoveFixed();
    if(data.rescode == "00000"){
        window.location.reload();
    }else{
        errorMessage(data.resmsg_cn);
    }
}
//我要加速 end

//K码点击福利兑换 start
var giftBagId = "";  //礼包Id
var exchangeGiftBag = function(orderId,currExchangeMoney){
    bodyFixedFun();
    giftBagId = orderId;
    alertBox1("当前可兑换礼金<font class='redTex'>"+currExchangeMoney+"</font>元","cancelUseIt","取消","giftPackageExchangeFun","确定",1);
}
var giftPackageExchangeFun = function(){
    ajaxRequest(contextPath + "wxGiftPackage/giftPackageExchange", "orderId="+giftBagId,giftBagFun,"GET");
}



function giftBagFun(data){
    if(data.rescode=="00000"){
        $("#alertBox").remove();
        $("#alertWrap").addClass("opacity0");
        $("#alertWrap2").removeClass("none");
        setTimeout(function(){
            $("#bthImg2").css("background-position","-135rem 0");
            window.location.reload();
        },1450);
    }else{
        message_level(data,'','cancelUseIt');
        cancelUseIt();
    }
}
//K码点击福利兑换 end

//福利礼包点击兑换 start
var walfareBag = function(orderId,packageId){
    $("#alertWrap").removeClass("none");
    bodyFixedFun();
    giftBagId = orderId;
    ajaxRequest(contextPath + "wxGiftPackage/queryGiftPackageGiftList","packageId="+packageId,setQueryGiftPackageGiftList);
}

function setQueryGiftPackageGiftList(data){
    if(data.rescode == "00000"||data.rescode == 00000){
        var html="",text=data.list,j=data.list.length;
        for(var i=0;i<text.length;i++){
            html+='<div class="swiper-slide" >';
            html+='<img src="'+text[i].giftImageUrl+'" class="imgWidth width3R" width="100%"/>';
            html+='<div class="whiteTex prompt width3R" style="width:100%;">'+text[i].giftName+'×'+text[i].amount+'</div></div>';
        }
        $("#alertSwiper").html(html);
        if(j<3){
            var mySwiper = new Swiper('#alertCon', {
                initialSlide :0,
                slidesPerView: 2,
                spaceBetween : 10,
                centeredSlides: true,
                effect : 'coverflow',
                coverflow: {
                    rotate:0,
                    depth: 150,
                    modifier: 2,
                    slideShadows : false,
                }
            })
        }
        else{
            var mySwiper = new Swiper('#alertCon', {
                initialSlide :1,
                slidesPerView: 2,
                spaceBetween : 10,
                centeredSlides: true,
                effect : 'coverflow',
                coverflow: {
                    rotate:0,
                    depth: 150,
                    modifier: 2,
                    slideShadows : false,
                }
            })
        }
        $("#alertWrap").removeClass("opacity0");
    }
    else{
        errorMessage(data.resmsg_cn);
    }
}

$("#KcodeBtn").on("click",function(){//点击福利详情兑换
    ajaxRequest(contextPath + "wxGiftPackage/giftPackageExchange", "orderId="+giftBagId,giftBagFun,"GET");
})
//福利礼包点击兑换  end


//弹窗点击取消
var cancelUseIt = function(){
    $("#alertBox").remove();
    $("#alertWrap").addClass('none');
    $(".bagDetail").html("");  //礼包兑换计划清空列表
    $("#alertWrap2").addClass("none");
    $(".k3Box").hide();
    $(".screenBkg").hide();
    $(".jsDetailBox").hide();
    bodyRemoveFixed();
    $(".jsDetail").find("p").removeClass("redTex");
    $(".checkImg").attr('src',contextPath+'/pic/weixin/K3activity/nocheck.png');
}
$(".clearAlert").click(function(){
    bodyRemoveFixed();
    $("#alertWrap").addClass("opacity0");
});

$(".closeBtn,.cancelUse").click(function(){   //点击关闭按钮 或 取消
    $(".bagDetail").html("");  //礼包兑换计划清空列表
    $("#alertWrap2").addClass("none");
    $(".k3Box").hide();
    $(".screenBkg").hide();
    $(".jsDetailBox").hide();
    bodyRemoveFixed();
    $(".jsDetail").find("p").removeClass("redTex");
    $(".checkImg").attr('src',contextPath+'/pic/weixin/K3activity/nocheck.png');
    isCheck = false;
});




