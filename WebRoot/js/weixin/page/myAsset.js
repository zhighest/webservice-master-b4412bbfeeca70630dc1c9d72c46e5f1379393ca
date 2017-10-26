//等级升级动画
var http = getUrlHttp();
ajaxRequest(contextPath + "wxuser/promptInfo","",showPromptInfo);   //等级up提示信息接口
function showPromptInfo(data){
	console.log(data);
	if(data.isPop == "1"){   //如果等级up
		$(".screenBkg").stop().show();
		$(".gradeUp").stop().show();
		$(".gradeUpImg").attr('src',http+data.msgImageUrl);
		$(".msgContent").html(data.msgContent);
	}
}
//点击关闭按钮   去掉蒙版
$(".closedBtn").click(function(){
	$(".screenBkg").stop().hide();
	$(".gradeUp").stop().hide();
});

var getShowAssets = function() { //账户余额调用
    ajaxRequest(contextPath + "wxdeposit/queryMyAccountDetail", "", setShowAssets);
}
var setShowAssets = function(data) { //账户余额生成HTML
    console.log(data);
    var isValidYygKg = data.accountDetail.isValidYygKg;//一元购开关
    var giftPackageSwitch = data.accountDetail.giftPackageSwitch;//大礼包开关
    var quantityCouponSwitch = data.accountDetail.quantityCouponSwitch;  //台数券开关
    var quickCouponSwitch = data.accountDetail.quickCouponSwitch;   //加速券开关
    var totalAssets = data.accountDetail.totalAssets;
    var incomeAmount = data.accountDetail.incomeAmount;
    var balanceMoney = data.accountDetail.acctBalance;
    var cashInMoney = data.accountDetail.drawlFrozenTotal;
    userPasswordLevel = parseInt(data.accountDetail.userPasswordLevel);
    var systemPasswordLevel = parseInt(data.accountDetail.systemPasswordLevel);
    if(userPasswordLevel < systemPasswordLevel){
        $(".warningIcon").stop().show();
    }
    $("#jsExchange").html(data.accountDetail.quickCouponNoCount+"张");
    $("#tsExchange").html(data.accountDetail.quantityCouponNoCount+"张");
    if(isValidYygKg=='N'){//兑换券开关
        $("#couponVoucher").hide();
    }else{
        $("#couponVoucher").show();
        $("#exchange").html(data.accountDetail.yygVoucherAcount);
    } 
    if(giftPackageSwitch=='N'){//大礼包开关
        $(".giftBagBox").hide();
    }else{
        $(".giftBagBox").show();
        $("#giftBalance").html(numFormat(data.accountDetail.giftBalance));
        $("#giftPackageSum").html(data.accountDetail.giftPackageSum+"个");
    } 
    if(data.accountDetail.isValidYxjh=='Y'){//优享计划开关
        $("#enjoyPlay").show();
        $("#vouchers").show();
    }else{
        $("#enjoyPlay").hide();
    }
    if(data.accountDetail.isValidDq=='N'){//定期开关
        $(".isValidDq").hide();
        $(".isValidHq").removeClass("width49").addClass("width100");
    }
    if(data.accountDetail.isValidHq=='N'){//活期开关
        $(".isValidHq").hide();
        $(".isValidDq").removeClass("width49").addClass("width100");
    }
    if(data.accountDetail.isSaleMan == 'Y'){//销售人员开关
        $("#salesManagement").removeClass("none");
    }
    else{
        $("#salesManagement").addClass("none");
    }
     if(data.accountDetail.pointSwitch  == 'Y'){//积分开关
        $("#collectScore").removeClass("none");
    }
    else{
        $("#collectScore").addClass("none");
    }
    $("#phoneNum").html(numHide($("#mobile").val())); //用户手机号
    $("#amountTitle .titlePhone").html(numHide($("#mobile").val())); //用户手机号
    $("#currentAmount").html(numFormat(data.accountDetail.currentAmount));//活期资产
    $("#investAmountSum").html(numFormat(data.accountDetail.investAmountSum));//定期资产
    $("#expectAmount").html(numFormat(data.accountDetail.expectAmount));//定期预计收益
    $("#yesterdayGain").html(numFormat(data.accountDetail.yesterdayGain));//活期昨日收益
    $("#enjoyPlanAmount").html(numFormat(data.accountDetail.enjoyPlanAmount));//优享计划资产
    $("#enjoyYesterdayIncome").html("+"+numFormat(data.accountDetail.enjoyYesterdayIncome));//优享计划昨日收益
    $("#totalScore").html(data.accountDetail.usablePoint);//可用积分
    $("#addRatesNum").html(data.accountDetail.rateRisesTotalNum);//加息券张数
    $("#gradeName").html(data.accountDetail.gradeName);//会员等级名称
    $("#amountTitle em").html(data.accountDetail.gradeName);//会员等级名称
    if(data.accountDetail.enjoyPlanAmount==null){
        $("#enjoyPlanAmount").html("0.00");//优享资产
    }else{
        $("#enjoyPlanAmount").html(numFormat(data.accountDetail.enjoyPlanAmount));
    }//优享资产
    $("#curHelpNub").html(data.accountDetail.curHelpNub);//抵用券张数
    $("#vouchersTotalNum").html(data.accountDetail.vouchersTotalNum);//代金券张数
    $("#incomeAmount").html(numFormat(data.accountDetail.incomeAmount)); //累计收益
    //$("#totalAssets").html(numFormat(data.accountDetail.totalAssets));*/ //账户总额
    $("#balanceMoney").html(numFormat(data.accountDetail.acctBalance)); //可用余额
    $("#cashInMoney").html(numFormat(data.accountDetail.drawlFrozenTotal));//提现中金额 
    $("#demandAsset").html(numFormat(data.accountDetail.currentAmount));//活期资产
    $("#FixAsset").html(numFormat(data.accountDetail.investAmountSum));//定期资产 
    if(data.accountDetail.imageIconDispose != "" && data.accountDetail.imageIconDispose != null){
        $("#defaultAvatar").attr("src",data.accountDetail.imageIconDispose);
        $(".personalImg").attr("src",data.accountDetail.imageIconDispose);
    }
    var countAnimate = new CountUp("totalAssets", 0, totalAssets, 2, 1, options);
    countAnimate.start();
    $("#totalAssets").click(function(event) {
        var countAnimate = new CountUp("totalAssets", 0, totalAssets, 2, 1, options);
        countAnimate.start();    
    });
    $("#incomeAmount").click(function(event) {
        var countAnimate = new CountUp("incomeAmount", 0, incomeAmount, 2, 1, options);
        countAnimate.start();
    });
    $("#balanceMoney").click(function(event) {
        var countAnimate = new CountUp("balanceMoney", 0, balanceMoney, 2, 1, options);
        countAnimate.start();
    });
    $("#cashInMoney").click(function(event) {
        var countAnimate = new CountUp("cashInMoney", 0, cashInMoney, 2, 1, options);
        countAnimate.start();
    });
   
}
var options = {
useEasing : true, 
useGrouping : true, 
separator : ',', 
decimal : '.', 
prefix : '', 
suffix : '' 
};
//获取银行卡信息
var getRecePayBank = function(data) {
    if (data.bankId != "" && data.bankId != null) {
        $("#point").stop().hide();
        $("#setTip").html("更多设置");
    } else {
        $("#point").stop().show();
        $("#setTip").html("未绑定银行卡");
    }
};
//登录密码提示弹框关闭
var closeBox = function(){
    $('#alertBox').remove();
}
//登录密码提示弹框立即设置
var setLoginPW = function(){
    window.location.href = contextPath + "wxuser/goSetLoginPassword";
}
//是否实名认证
var getIdcardInfo = function(data) {
    if (data.idcardValidate != "Y") {
        $("#point").stop().show();
        $("#setTip").html("未实名认证");
    } else {
        ajaxRequest(contextPath + "wxtrade/getRecePayBank", "", getRecePayBank);//是否绑定银行卡
    }
}
//获取加息券信息
var setMyRateCoupons = function(data){
    $("#addRatesNum").html(data.totalNum);
}

// 倒计时5秒消息提示从提示框变为小红点
var t = 5;//显示倒数秒数 5  
function showTime(){  
    t -= 1; 
    if(t==0){  
        $("#dotted").show(); 
        $("#moveAni").hide();
    }  
    //每秒执行一次,showTime()  
    setTimeout("showTime()",1000);  
} 

//获取未读消息数
var setUnread = function(data){ 
    if(data.rescode == 00000){
        var totalMsgNum = data.totalMsgNum;//消息总数
        $("#totalMsgNum").html(data.totalMsgNum);
        if(data.totalMsgNum>0){//有消息
            $("#moveAni").show();
             showTime();
        }else{
            $("#moveAni").hide();
            $("#dotted").hide();
        }

        var html="";
        $.each(data.list,function(i, jsonObj){
            var type=jsonObj.message_type;
            var msgCount;//单条选项的消息数
            var msName = jsonObj.message_name;
            if(jsonObj.message_count>=100){
                msgCount="..."
            }else{
                msgCount=jsonObj.message_count;
            }
            if(jsonObj.message_app_type==28){//屏蔽客服
                    html+='<li class="none">';
            }else{
            		 if(i==0){
                         html+='<li class="PLR15" onclick="clearMsg('+type+')">';//第一个不加上部描边
                     }else{
                         html+='<li class="borderT PLR15" onclick="clearMsg('+type+')">';
                     }
            }
            html+='<input id="b'+type+'" type="hidden" value="'+jsonObj.webview+'">';
            if(jsonObj.message_count==0){
                html+='<div class="PTB10 clearfix grayThis" id="personalMessageA">';
                html+='<img class="fl MR10" width="20" src="'+jsonObj.message_icon+'">';//对应图标
                html+='<span class="fl">'+msName+'</span>';//名称
            }else{
                html+='<div class="PTB10 clearfix personalMessageA" id="personalMessageA">';
                html+='<img class="fl MR10" width="20" src="'+jsonObj.message_icon+'">';
                html+='<span class="fl">'+msName+'</span>';
                html+='<div class="fr font12 orangeBox" id="a'+type+'" >'+msgCount+'</div></div></div>';
            }    
        })
        $("#msg").html(html);
    }
};
//消息跳转页面
var type1;
var setClearUnread1 = function(data){   
    if(data.rescode == 00000){
        $("#a"+type1).hide(); 
        setTimeout(function(){
            window.location.href = $("#b"+type1).val(); 
        }, 200);
        setTimeout(function(){ 
            window.location.reload(); 
        },1000);          
    }else{
        errorMessage(data.resmsg_cn);
    }   
}
//消息点击事件
 function clearMsg(type){
    type1=type;
    ajaxRequest(contextPath + "wxpush/clearUnread", "message_type="+type, setClearUnread1);
 }
$(document).ready(function() {
    // 头部的特效
    //滚动到90像素时加载showdiv
    var m_st;
    var m_pf = 0; 
    $(window).scroll(function () {
        m_st = Math.max(document.body.scrollTop || document.documentElement.scrollTop);
        if (m_st > m_pf) {
            $(".point").addClass('redBkg').removeClass('whiteBkg');
            // $(".scrollMessage").addClass('PT60');
            $("#userMessage").stop(true).show();
            $("#userMessage").addClass('userMessage');
            $("#userMessage p").removeClass('whiteTex').addClass('blackTex');
            $("#phoneNum").removeClass('whiteTex').addClass('blackTex');
            // $(".messageIcon").attr("src",contextPath+"pic/weixin/ver2_8/messageIconB.png");
            $("#gradeName").removeClass('whiteTex').addClass('blackTex');
        }else  {
            $(".point").addClass('whiteBkg').removeClass('redBkg');
            // $(".scrollMessage").removeClass('PT60');
            $("#userMessage").removeClass('userMessage');
            $("#userMessage").stop(true).hide();
            $("#userMessage p").removeClass('blackTex').addClass('whiteTex');
            $("#phoneNum").removeClass('blackTex').addClass('whiteTex');
            // $(".messageIcon").attr("src",contextPath+"pic/weixin/ver2_8/messageIcon.png");
            $("#gradeName").removeClass('blackTex').addClass('whiteTex');
        }
    })
    //消息图标点击展开
    var jinzhi=0;
    $(".messageIcon").click(function(event) {
        $("#messageBox").show();
        console.log("123");
    });
    $("#messageBox").click(function(event) {
        $("#messageBox").hide();
    });
    $("#messageBox").on("touchstart",function(e){
    	jinzhi=1;
    })
     $("#messageBox").on("touchmove",function(e){
    	if(jinzhi==1){
    		e.preventDefault();
    		e.stopPropagation();
    		}
    })
     $("#messageBox").on("touchend",function(e){
    	 jinzhi=0
    })
    var channel = $("#channel").val();
    var mobile = $("#mobile").val();
    if(channel == "LBWX"){
        $("#downBkg").stop().show();
    }
    getShowAssets();
    ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
    ajaxRequest(contextPath + "tasklist/isCompleteTask", "mobile="+ mobile, setCompleteTask);//消息提醒
    ajaxRequest(contextPath + "wxpush/unread", "", setUnread);//获取未读消息数接口
    // ajaxRequest(contextPath + "wxPoint/getUserGrade", "", getUserGrade);//用户等级接口
});

var accoutArrowState = false;
var mobile = $("#mobile").val();
var userId=$('#userId').val();
//加息券跳转
$("#addRates").click(function(event) {
    window.location.href = contextPath + "wxcoupons/goMyInterest";
    sessionStorage.setItem("from","all");
});
$('#scoreLink').click(function(){
    window.location.href = contextPath + "wxPoint/colletScore";
});
$('#topUp').click(function(){
    window.location.href = contextPath + "wxpay/redirectPay?payFlag=WX";
})
$("#withdrawDeposit").click(function(){
    window.location.href = contextPath +"wxtrade/goWithdrawDeposit";
})
//小红点,消息提醒
var setCompleteTask = function(data){
    if (data.rescode == "00000"){
        if (data.isCompleteTask =="0"){
            $("#redDotted").stop().show();
        }
        else if(data.isCompleteTask == "1"){
            $("#redDotted").stop().hide();
        }
    }

    else {//接口不通的情况下，调用报错的方法
        // errorMessage(data.resmsg_cn);
    }
}

//点击头像跳转到会员等级页面
$(".avatar,.personalImg").click(function(){
    window.location.href = contextPath + "wxdeposit/goVipGrade";
});

