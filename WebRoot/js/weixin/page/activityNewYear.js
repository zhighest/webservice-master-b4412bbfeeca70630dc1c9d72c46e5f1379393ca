/**
 * Created by huamin on 2017/1/4.
 */
//定期、活期、发现  进度
var periodPer;   //活期
var currentPer;   //定期
var foundPer;   //发现
var shareLink;   //分享链接
var shareImgUrl;  //分享链接的图片
var moneyAmount;   //累计赚取金额
var moneyAssort;   //排名
var registerDay;   //注册天数
var iShttp = getUrlHttp();   //获取http协议   放在merge.js里
var userName;   //用户名字
var loginFlag;   //是否登录
window.onload = function(){
    var mobile = $("#mobile").val();  //APP
    var mobileWX = $("#mobileWX").val();   //weixin
    var channel = $("#channel").val();
    var ua = navigator.userAgent.toLowerCase();
    // 微信
    if(channel == "LBAndroid") {	// 安卓												//安卓
        if(mobile == "" || mobile == null|| mobile == "null"){
            if(window._cordovaNative){
                window._cordovaNative.goLogin();
            }
        }else{
            ajaxRequest(contextPath + "/platformData/getUserCount", "mobile=" +mobile+ "&channel="+channel, setShareMessage1);
            ajaxRequest(contextPath + "/platformData/getVoucherAndProfitStatisInfo", "mobile=" +mobile+ "&channel="+channel, setShareMessage2);
            ajaxRequest(contextPath + "/platformData/getInvestData", "mobile=" +mobile+ "&channel="+channel, setShareMessage3);
            ajaxRequest(contextPath + "/platformData/getIncomeData", "mobile=" +mobile+ "&channel="+channel+"&shareChannel=0", setShareMessage4);
        }
    }else if(channel == "LBIOS") {	// ios												//IOS
        if(mobile == "" || mobile == null||mobile == "null"){
            ajaxRequest(contextPath + "/platformData/getUserCount", "mobile=" +mobile+ "&channel="+channel, setShareMessage1);
        }else{
            ajaxRequest(contextPath + "/platformData/getUserCount", "mobile=" +mobile+ "&channel="+channel, setShareMessage1);
            ajaxRequest(contextPath + "/platformData/getVoucherAndProfitStatisInfo", "mobile=" +mobile+ "&channel="+channel, setShareMessage2);
            ajaxRequest(contextPath + "/platformData/getInvestData", "mobile=" +mobile+ "&channel="+channel, setShareMessage3);
            ajaxRequest(contextPath + "/platformData/getIncomeData", "mobile=" +mobile+ "&channel="+channel+"&shareChannel=0", setShareMessage4);
        }
    }else{
        ajaxRequest(contextPath + "/platformData/getUserCount", "mobile=" +mobileWX+ "&channel="+channel, setShareMessage1);
        ajaxRequest(contextPath + "/platformData/getVoucherAndProfitStatisInfo", "mobile=" +mobileWX+ "&channel="+channel, setShareMessage2);
        ajaxRequest(contextPath + "/platformData/getInvestData", "mobile=" +mobileWX+ "&channel="+channel, setShareMessage3);
        ajaxRequest(contextPath + "/platformData/getIncomeData", "mobile=" +mobileWX+ "&channel="+channel+"&shareChannel=0", setShareMessage4);
    }




//页面加载第一页初始动画
$(".slider1 .wordTop").addClass("curTop");
$(".wordRight,.wordLeft").slideDown(2000);

//获取用户注册信息

function setShareMessage1(data){
    if(data.rescode == "00000") {
        console.log(data);
        $(".registerAmount").html(data.registerAmount);   //总注册人数
        $(".getMoneyAmount").html(numFormat(data.getMoneyAmount));  //总赚取金额
        if(data.registerTime){
            var dateStrArr = data.registerTime.split("-");  //注册时间
            $(".registerYear").html(dateStrArr[0]); //年
            $(".registerMonth").html(dateStrArr[1]); //月
            $(".registerDay").html(dateStrArr[2]); //日
            $(".registerSort").html(data.registerSort);   //第几位注册用户
            $(".registerDays").html(data.registerDay)    //注册天数
            registerDay = data.registerDay;
        }
        if(data.loginFlag && data.loginFlag == "N"){
            loginFlag = data.loginFlag;
            $(".slider3,.slider4").addClass("none");
            var html = '<p class="PTB40 font18">请先登录联璧账户查看您的成绩单!</p>';
            $(".noLoginFlag").html(html);
        }
        swiperFun();
    }else{
        errorMessage(data.resmsg_cn);
    }

}

function setShareMessage2(data){
    if(data.rescode == "00000") {
        console.log(data);
        $(".couponCount").html(data.couponCount);
        $(".voucherCount").html(data.voucherCount);
        $(".voucherAmout").html(numFormat(data.voucherAmout));
        $(".gainRewardSum").html(data.gainRewardSum);
    }else{
        errorMessage("请先登录联璧账户查看您的成绩单!");
    }
}
function setShareMessage3(data){
    if(data.rescode == "00000") {
        console.log(data);
        regularPer = data.regularPer;   //定期
        currentPer = data.currentPer;   //活期
        foundPer = data.foundPer;   //发现
        //当进度超过85% 就将字体显示为黄色
        if(regularPer >= 85) $(".productName1").css("color","#F2F0B2");
        if(currentPer >= 75) $(".productName2").css("color","#F2F0B2");
        if(foundPer >= 75) $(".productName3").css("color","#F2F0B2");
        if(regularPer >= 45) $("#regularNum").css("color","#F2F0B2");
        if(currentPer >= 45) $("#currentNum").css("color","#F2F0B2");
        if(foundPer >= 45) $("#findNum").css("color","#F2F0B2");
        $("#regularNum").html(regularPer + "%");
        $("#currentNum").html(currentPer + "%");
        $("#findNum").html(foundPer + "%");
        $("#regularPer").css("top",(100 - regularPer)+"%");   //波浪百分比  是相反的  所以用100减
        $("#currentPer").css("top",(100-currentPer)+"%");
        $("#foundPer").css("top",(100-foundPer)+"%");
    }else{
        errorMessage(data.resmsg_cn);
    }
}
function setShareMessage4(data){
    if(data.rescode == "00000") {
        console.log(data);
        chickShareIcon = data.chickShareIcon;
        chickShareLink = data.chickShareLink;
        userName = data.userName;
        moneyAmount = numFormat(data.moneyAmount);
        if(data.moneyAssort > 100){
            moneyAssort = data.moneyAssortPer + "%";
        }else{
            moneyAssort = data.moneyAssort;
        }
        $(".moneyAmount").html(moneyAmount);
        $(".moneyAssortPer").html(data.moneyAssortPer + "%");
        if(channel == "null" || channel == "" || channel == null){
            shareLink =  bathPath + chickShareLink+"?mobile="+mobileWX; // 分享链接
        }else{
            shareLink =  bathPath + chickShareLink+"?mobile="+mobile; // 分享链接
        }
        shareImgUrl = iShttp + chickShareIcon;
    }else{
        errorMessage(data.resmsg_cn);
    }
}
function swiperFun(){
    var mySwiper = new Swiper('.swiper-container',{
        direction : 'vertical',
        freeMode : false,
        onSlideChangeStart: function(swiper){
            //Swiper初始化了
            if(swiper.activeIndex == 0){  //第一页动画
                $(".slider1 .wordTop").addClass("curTop");
                $(".wordRight,.wordLeft").slideDown(2000);
            }else{
                $(".slider1 .wordTop").removeClass("curTop");
                $(".wordRight,.wordLeft").hide();
            }
            if(swiper.activeIndex == 1){
                if( channel == "LBIOS" && (mobile == "" || mobile == null||mobile == "null")){
                    goLogin();
                }
                if(loginFlag == "N"){

                }
            }
            if(swiper.activeIndex == 1 || swiper.activeIndex == 3){  //第二页、第四页动画
                $(".slider2 .wordTop").addClass("curTop");
                $(".flowerImg1").addClass("curFlower1");
                $(".flowerImg2").addClass("curFlower2");
                $(".flowerImg4,.flowerImg3,.flowerImg5,.flowerImg6").addClass("curFlower3");
            }else{
                $(".slider2 .wordTop").removeClass("curTop");
                $(".flowerImg1").removeClass("curFlower1");
                $(".flowerImg2").removeClass("curFlower2");
            }
            if(swiper.activeIndex == 2){  //第三页动画
                $(".regularWrap,.currentWrap,.findWrap").addClass("curWrap");
            }else{
                $(".regularWrap,.currentWrap,.findWrap").removeClass("curWrap");
            }
            if(swiper.activeIndex == 3){   // 最后一页的时候 隐藏滑动箭头
                $(".slideTop").hide();
            }else{
                $(".slideTop").show();
            }
            console.log(swiper.activeIndex);//提示Swiper的当前索引
        }
    });
}

//分享微信好友和朋友圈
$(".toShare").click(function(){
    var mobile = $("#mobile").val();
    var channel = $("#channel").val();
    var shareTitle = '我已经在联璧金融赚了'+moneyAmount+'元啦，快来看看吧！';
    var shareDesc = "活期理财超7%，当日计息，随存随取。小金融，稳稳的！";
    if (channel == "LBAndroid") {
        if(window._cordovaNative){
            window._cordovaNative.webContentShare(shareTitle,shareDesc,shareLink,shareImgUrl);
        }
    }else if(channel == "LBIOS") {
        webContentShare(shareTitle,shareDesc,shareLink,shareImgUrl);
    }else{
        $(".sharePopup").show();
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        wx.onMenuShareTimeline({
            title: shareTitle, // 分享标题
            desc: shareDesc, // 分享描述
            link: shareLink, // 分享链接
            imgUrl: shareImgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                errorMessage("分享成功");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                errorMessage("未分享");
            }
        });
        //获取“分享给朋友”按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title: shareTitle, // 分享标题
            desc: shareDesc, // 分享描述
            link: shareLink, // 分享链接
            type: 'link', // 分享类型,music、video或link，不填默认为link
            imgUrl: shareImgUrl, // 分享图标
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
                errorMessage("分享成功");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                errorMessage("未分享");
            }
        });
    }
});

};