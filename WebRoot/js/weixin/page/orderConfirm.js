//产品购买进度动画
var finishedRatio = $("#finishedRatio").val();
$(".moveSpan").html(finishedRatio+'%');
$(".inLine").animate({width : finishedRatio + '%'},1200);
$(".moveEm").animate({left : parseFloat(finishedRatio) - 1 + '%'},1200);

var iframeFn=function(){
    var mobile=$("#mobile").val();
    var userId=$("#userId").val();
    $("#iframe").attr("src",contextPath + "wxpay/goPay?userId="+userId+"&&mobile="+mobile+"&payFlag=wx")
}
//充值页面
var goTopUpcz = function(money) {
    cancelTopUp();
    $(".wrap").addClass("height100P");
    $("iframe")[0].contentWindow.window.freshFn(money)
    $("#iframeWrap").addClass("showIframe");
}
//代金券页面
var goVoucher = function(adjustRate,sloanId,fromUrl,inputAmount,finishedRatio){
    var temp = document.createElement("form");
    temp.action = contextPath + "wxuser/goVoucher";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "adjustRate";
    inp.value = adjustRate;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "sloanId";
    inp2.value = encodeURI(tagId);
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "fromUrl";
    inp3.value = fromUrl;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "inputAmount";
    inp4.value = inputAmount;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "finishedRatio";
    inp5.value = finishedRatio;
    temp.appendChild(inp5);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();

}
//定义需要传入接口的变量
var loanId;
var voucherId;//代金券id
var voucherAmount;//代金券的值
var acctBalance;//账户余额
var adjustRate;//专享
var investMinAmount;//起投金额
var annualRate;//当日年化利率
var remanAmount;//可投总金额
var tagId;//标的id
var inputAmount;//输入框输入的数值
var investmentAmount;//代金券使用需满足的金额
var voucherAmount;
var fromUrl;
var setDemanProductList = function(data){
    tagId = data.list[0].id;
    annualRate = data.list[0].annualRate;
    remanAmount = data.list[0].remanAmount;  //剩余可投
    investMinAmount = data.list[0].investMinAmount;
    var productName = data.list[0].productName;
    $("#titleNam").html(productName);
    //剩余可购
	$(".totalMoney").html(numFormat(remanAmount));
	//起投金额
	$(".investMinAmount").html(investMinAmount+"元");
    // $("#myAcctBalance").click(function() {
    //     if(acctBalance<=0){
    //         return false;
    //     }else{
    //         $("#inputAmount").val(acctBalance);
    //         calculateEarnings();
    //     }    
    // });
}
var setShowAcctBalance = function(data){
    acctBalance = data.accountDetail.acctBalance;
    $("#myAcctBalance").html(acctBalance);
    if (acctBalance <= remanAmount) {
//      var minNumber = acctBalance;
        var minNumber = remanAmount;
    } else {
        var minNumber = remanAmount;
    }
    $("#inputAmount").attr("minNumber", minNumber);
    
  
    calculateEarnings(1);  //键盘全额  传1
    
    var quickMoney = new Array;
    quickMoney = [1000,5000,10000];    //2016/12/22版本之后   定活期自定义键盘都写死的快捷输入值
    $("#shortcut").html("");  //先为空
    for(var i = 0; i < quickMoney.length; i++){
        $("#shortcut").append('<li class="fl grayTex1 radius3 bkg4 boxSizing" money="'+quickMoney[i]+'">'+quickMoney[i]+'</li>');
    }
    if(acctBalance<=0){
        $("#shortcut").append('<li class="fl grayTex1 radius3 bkg4 boxSizing allQuickMoney" money="0.00">全 额</li>');
    }else if(parseFloat(acctBalance)>parseFloat(remanAmount)){
        $("#shortcut").append('<li class="fl grayTex1 radius3 bkg4 boxSizing allQuickMoney" money="'+ remanAmount +'">全 额</li>');
    }else{
        $("#shortcut").append('<li class="fl grayTex1 radius3 bkg4 boxSizing allQuickMoney" money="'+ acctBalance +'">全 额</li>');
    }

    //自定义键盘数值选择时当前高亮
    $(".moneyBtn li").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
    });
    shortcutClick();
};

var shortcutClick = function(){
    $("#shortcut li").click(function(event) {
        var val = $(this).attr("money");
        $("#inputAmount").val(val).attr("alt",val);
        calculateEarnings(1);  //键盘全额  传1
    });
}
//第2步，实时计算预计收益的值，输入投资金额的监听事件之后返回的方法 begin    
var calculateEarnings = function(num) {
    inputAmount =$("#inputAmount").val();//输入框输入的数值
    substrAmount = Subtr(inputAmount,voucherAmount);//输入金额减去代金券的金额
    var accountBalanceAmount = parseFloat(acctBalance);//账户余额
    var rate = accAdd(annualRate,adjustRate);
    if (inputAmount > 0) {//输入金额大于0
        if(parseFloat(inputAmount)>parseFloat(remanAmount)){
            $("#inputAmount").val(remanAmount);
            inputAmount =$("#inputAmount").val();
            errorMessage('投资金额不能大于可投总额');
            earnings = accDiv(accMul(accMul(remanAmount, rate), 0.01), 365);//预计收益的值
            $("#goPay").removeClass("inactiveBtn").html("立即投资");
            $("#goPay").attr("onclick", "ajaxMyCouponsList('" + inputAmount + "')");   
        }else{
            earnings = accDiv(accMul(accMul(inputAmount, rate), 0.01), 365);//预计收益的值
            $("#goPay").removeClass("inactiveBtn").html("立即投资");
            $("#goPay").attr("onclick", "ajaxMyCouponsList('" + inputAmount + "')");
        }  
        if(earnings>0){
            $("#earnings").html(numFormat(earnings) + '元');
        }else{
            $("#earnings").html(numFormat(0) + '元');
        }    
    }else {
        $("#earnings").html(numFormat(0) + '元');
        $("#goPay").addClass("inactiveBtn").html("立即投资");
        $("#goPay").attr("onclick", "errorMessage('请输入投资金额')");
    }

    $("#fromUrl").val("orderConfirm");
    fromUrl = $("#fromUrl").val();
    //点击代金券的点击事件，跳转到代金券页面
    $("#useRatesCoupon").attr("onclick", "goVoucher('" + adjustRate + "','"+tagId+"','" + fromUrl + "','"+inputAmount+"','"+finishedRatio+"')");
    if(!(num==1)){
    	var minNumber=$("#inputAmount").attr("minNumber");
    	if(parseInt(inputAmount)>=parseInt(minNumber)){
    		 errorMessage('投资金额不能大于可投总额');
    	}
    }
}
//第2步，输入投资金额的监听事件之后返回的方法 end 

//第1步 加载页面，调用接口 begin
//页面加载完毕

$(document).ready(function() {
	ajaxRequest(contextPath + "base/getMetaInfo", "name=preSaleAgreementCurrent", setMetaInfo);
	
    voucherAmount = $("#voucherAmount").val();
    voucherId = $("#voucherId").val();
    ajaxRequest(contextPath + "wxdeposit/showInvestmentListInfo", "", setDemanProductList);
    ajaxRequest(contextPath + "wxdeposit/queryMyAccountDetail", "", setShowAcctBalance);//账户余额
    var inputAmountBac =$("#inputAmountBac").val();//输入框输入的数值
    adjustRate = $("#adjustRate").val();
    if(inputAmountBac>0){
        inputAmount = $("#inputAmountBac").val();//输入框输入的数值
        $("#inputAmount").val(inputAmountBac);
        setTimeout(function(){
            calculateEarnings(inputAmountBac);
        }, 500)   
    }else{
        inputAmount =$("#inputAmount").val();//输入框输入的数值
        setTimeout(function(){
             calculateEarnings(inputAmount);
        }, 500)
    }
    //输入框监听事件
    $("#inputAmount").attr("oninput", "$.checkLimit($(this)," + remanAmount + ",false);calculateEarnings();").attr("onkeyup", "$.checkLimit($(this)," + remanAmount + ",false);calculateEarnings();").attr("onafterpaste", "$.checkLimit($(this)," + remanAmount + ",false);calculateEarnings();");
    
    $("#myAcctBalance").html(acctBalance);
});


//第1步 加载页面，调用接口 end

var earnings; //预计收益
var annualRate;
var inputAmountBac;//输入框输入的数值
var substrAmount = Subtr(inputAmount,voucherAmount);//输入金额减去代金券的金额
var accountBalanceAmount = parseFloat(acctBalance);//账户余额
var adjustRate = $("#adjustRate").val();//专享加息率 
var topUpMoney;
var adjustRate;//专享加息
var substrAmount;
var investMinAmount;//起投金额
//查询是否有代金券接口
var ajaxMyCouponsList = function(){
    if(!checkBoxArea){
        errorMessage("请仔细阅读并同意服务协议");
        return false; 
    }
    if(checkBoxArea){
        if(parseInt(inputAmount) >= parseInt(investMinAmount)) { //输入金额大于起投金额
            ajaxRequest(contextPath + "wxvouchers/getMyCouponsList", "productType=109&remanPeriods=0"+"&remanFlag=false&", setMyCouponsList,'GET'); 
            return false; 
        }
        if(parseInt(inputAmount) < parseInt(investMinAmount)){
            errorMessage('投资金额不能小于起投金额');
            return false; 
        }    
    }    
}

//是否有代金券
var setMyCouponsList = function(data){
    var investmentAmount = $("#investmentAmount").val();
    substrAmount = Subtr(inputAmount,voucherAmount);//输入金额减去代金券的金额
    var accountBalanceAmount = parseFloat(acctBalance);//账户余额
    if(data.rescode == "00000"){
        //有代金券 begin
        if(data.vouchersListCount != 0){
            //还没有使用代金券 begin
            if(voucherAmount == "null" || voucherAmount == ''){//还没有使用代金券
                ratesCouponAlert();
            }
            //还没有使用代金券 end

            //已经使用了代金券 begin
            else{
                topUpMoney = Subtr(substrAmount, accountBalanceAmount);
                //输入金额不满足代金券使用条件 begin
                if (parseFloat(investmentAmount) > parseFloat(inputAmount)) {
                    errorMessage('您选择的代金券需要投资'+investmentAmount+'元可用，请重新操作');
                    $("#inputAmount").val("");
                    $("#inputAmountBac").val("");
                    $("#earnings").html("0.00元");
                    calculateEarnings();
                }
                //输入金额不满足代金券使用条件 end
                //输入金额满足代金券使用条件 begin
                else{
                   if (substrAmount > accountBalanceAmount){//输入金额减去代金券的金额大于账户余额
                        topUpAlert(topUpMoney);//弹出提示充值的弹窗
                    } 
                    else{//输入金额减去代金券的金额不大于账户余额
                        // $("#alertBox").remove();
                        usePassword(getRollOut2);
                    } 
                } 
                //输入金额满足代金券使用条件 end          
            }
            //使用了代金券 end
        }
        //有代金券 end
        //没有代金券则直接弹出展示弹窗 begin
        else{
            topUpMoney = Subtr(inputAmount, accountBalanceAmount);
            if (inputAmount > accountBalanceAmount){//输入金额减去代金券的金额大于账户余额
                topUpAlert(topUpMoney);//弹出提示充值的弹窗
            } 
            else{
                usePassword(getRollOut2);
            }
        }
        //没有代金券则直接弹出展示弹窗 end
    }
}
var voucherAmount = $("#voucherAmount").val();
var vouchersCount = $("#vouchersCount").val();
// 代金券的使用情况的显示 begin
if( voucherAmount != "0" && voucherAmount != "" && voucherAmount != "null") {
    $("#useVoucherAmount").html('已抵扣<span class="redTex">'+voucherAmount+'</span>元');
}
else{
    $("#useVoucherAmount").html('<font class="redTex">'+vouchersCount+'</font>张可用');
}
//第5步
var getRollOut = function(){
    $("#alertBox").remove();
    var accountBalanceAmount = parseFloat(acctBalance);//账户余额
    if(inputAmount<=accountBalanceAmount){
        usePassword(getRollOut2);  
    }
    else{
        topUpMoney = Subtr(inputAmount, accountBalanceAmount);
        topUpAlert(topUpMoney);//弹出提示充值的弹窗
    }  
}

//第6步，提现输入交易密码后调用转出接口
var getRollOut2 = function(passwordCash){
    $("#goPay").removeAttr('onclick');//去掉a标签中的onclick事件
    $("#inputAmount").val("");
    $("#earnings").html("0.00元");
    var token = $("#token").val();
    if (passwordCash == null || passwordCash == "") {
        errorMessage("交易密码为空！请重新输入！");
    } else {
        if (voucherId==""||voucherId==null||voucherId=="undefined") {
            ajaxRequest(contextPath + "wxdeposit/join", {
                "passwordCash": passwordCash,
                "finaceId": tagId,
                "token": token,
                "voucherId": "null",
                "buyAmount": inputAmount,
                "cycleMatchType":'3'
            },setRollNum);
        }else{
            ajaxRequest(contextPath + "wxdeposit/join", {
                "passwordCash": passwordCash,
                "finaceId": tagId,
                "token": token,
                "voucherId": voucherId,
                "buyAmount": inputAmount,
                "cycleMatchType":'3'
            },setRollNum);
        }    
    }
    $("#alertBox").remove();

}
var setRollNum = function(data){
//第7步，投资结果判断
    if(data.rescode == "00000"){
        errorMessage("投资成功");
        function goUrl(){
            window.location.href=contextPath+"wxInvest/getInvestSucceed?productId=109"+"&investMinAmount="+investMinAmount+"&annualRate="+annualRate+"&remanAmount="+remanAmount;
        }
        setTimeout(goUrl,200);
    }else{
        errorMessage(data.resmsg_cn);
        setTimeout(function(){
            window.location.reload();
            calculateEarnings();
        },1000);
    }
}

 
var inputAmount =$("#inputAmount").val();//输入框输入的数值
//代金券未使用确认弹框
var ratesCouponAlert = function(){
    alertBox1("您有可使用的代金券未使用","getRollOut","不使用","goVoucher('" + adjustRate + "','"+tagId+"','" + fromUrl + "','"+inputAmount+"')","使用",1); 
}
//提示充值的弹窗
var topUpAlert = function(topUpMoney){
    $(".wrap").addClass("height100P");
    iframeFn()//载入iframe内容
    alertBox1("您的账户当前余额不足，请及时充值","cancelTopUp","取消","goTopUpcz('" + topUpMoney + "')","充值",1); 
}
//充值弹窗点击取消
var cancelTopUp = function(){
    $(".wrap").removeClass("height100P");
    $("#alertBox").remove();
    $("#inputAmount").val("");
    $("#inputAmountBac").val("");
    $("#earnings").html("0.00元");
    calculateEarnings();
}

function goIdcardValidate(){
    window.location.href = contextPath + "wxtrade/goAuthentication?productId=109";
}
//若没有进行实名认证则需要实名认证 end

//以下为js特效部分
//同意服务协议
var checkBoxArea;
checkBoxArea = true;
$("#checkBoxArea img").toggle( 
    function () { 
        $(this).attr("src",contextPath+"/pic/weixin/ver2_9/checkbox1.png"); 
        checkBoxArea = false;
    }, 
    function () { 
        $(this).attr("src",contextPath+"/pic/weixin/ver2_9/checkbox2.png");
        checkBoxArea = true;
    } 
);
//点击输入金额输入框，弹出数字键盘
$(".main").click(function(e) {
    var target = $(e.target);
    if(!target.is('#inputAmount')) {//如果点击事件是#inputAmount
        $(".wrap").removeClass("height100P");
        $("#momeyBox").stop().slideUp(200);
    }else{
        $("#momeyBox").stop().slideDown(200);  
    }
});
// i标的点击事件，显示与隐藏效果
$(".vipHint").click(function(){
    $(".vipHintUl").animate({height: 'toggle', opacity: 'toggle'}, "400");  
});
//服务协议的显示隐藏
$("#agreenment").click(function(){
     $("#subDiv").toggle("fast");    
 })

//点击协议的跳转事件
var setServAgreementByType = function(data){
	var u = navigator.userAgent, app = navigator.appVersion;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	var serAgreementUrl=data.serAgreementUrl;
	var flag=serAgreementUrl.indexOf(";");
	serAgreementUrl2=serAgreementUrl.substring(0,flag);
	serAgreementUrl3=serAgreementUrl.substring(flag+1);
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
	    	if(isiOS){
	    	window.location.href = serAgreementUrl2;
		}
		else{
			window.location.href = serAgreementUrl3;
		}
    }
};


//刻度尺 begin
var x2=0
var x100=10000//余额
function addNum(x100){
    var html='<span id="zhanwei"></span>';
    for(var i=1;i<=x100/1000+1;i++){
        var num=i*1000;
        html+='<span class="num">'+num+'</span>';
    }
    $("#progress2").html(html);
}        
addNum(x100);
$("#mask").on('touchstart',getmoney)    
function getmoney(){            
    var x3=parseInt(event.targetTouches[0].pageX);
    $(this).on('touchmove',function(){
        event.preventDefault();
        var x4=parseInt(event.targetTouches[0].pageX);
        var x5=x3-x4;
        x2+=x5;
        if(x2<=0){
            x2=0;
        }
        var x6=parseInt(x2/13);
        if(x6*100>=x100){
            $("#inputAmount").val(x100);
            $("#progress2").css('left',-x100/100*13);
            x2=x100/100*13;    
        }
        $("#progress2").css('left',-x2);
        $("#inputAmount").val(x6*100);    
        x3=x4;    
        $(this).on('touchend',function(){
            $(this).unbind('touchmove');
        })
    })
}                       
//iframe页面充值后刷新余额
function freshAccountBalance(){
    ajaxRequest(contextPath + "wxdeposit/queryMyAccountDetail", "", setShowAcctBalance);
    $("#iframeWrap").removeClass("showIframe");
    $(".wrap").removeClass("height100P");
    iframeFn();
}
//协议回调并且绑定事件
function setMetaInfo(data){
	var oDiv=document.getElementById("subDiv");
	var oA=oDiv.getElementsByTagName("a");
	if(data.rescode==="00000"){
		for(var i=0;i<oA.length;i++){
			var equal=data.content[i];
			oA[i].innerHTML=equal.name;
			oA[i].id=equal.type;
			//协议的点击事件
			 (function closure(type){
				$("#"+type+"").click(function(){
				    ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type="+type+"&productId=" + tagId,setServAgreementByType,"GET");
				})
		})(equal.type)
			
		}
	
	}
}




