//产品购买进度动画
var finishedRatio = $("#finishedRatio").val();
$(".moveSpan").html(finishedRatio+'%');
$(".inLine").animate({width : finishedRatio + '%'},1200);
$(".moveEm").animate({left : parseFloat(finishedRatio) - 1 + '%'},1200);

//预期收益显隐
var isUp = true;
$(".totalReturn").click(function(){
    $(".returnMoney").toggle();
    if(isUp){
        $(".downOrUp").css({'transform' : 'rotate(360deg)','-webkit-transform' : 'rotate(360deg)'});
        isUp = false;
    }else{
        $(".downOrUp").css({'transform' : 'rotate(180deg)','-webkit-transform' : 'rotate(180deg)'});
        isUp = true;
    }
});
var buyLoanByBalance = function(data){
    if(data.rescode == "00000"){
            window.location.href=contextPath+"wxtrade/goFixBuySuccess?orderId="+data.orderId; 
       
    }else{
        errorMessage(data.resmsg_cn);
    }
}
function goPay(passwordCash){//购买传参
    rateIdsNew=$("#rateIds").val();
    if(rateIdsNew == null||rateIdsNew == "null"){
        rateIdsNew="";
    }
     var pay = {
        scatteredLoanId:$("#sloanId").val(),
        loanId:$("#loanId").val(),
        planId:$("#planId").val(),
        buyCount:$("#inputAmount").val(),
        cpType:$("#cpType").val(),
        rateIds:rateIdsNew,
        productId:"108",
        from:'LBWX',
        passwordCash:passwordCash,
        remanDays:remanDays,
        investPeriod:investPeriod,
        voucherId:voucherId
     };
     ajaxRequest(contextPath+"wxtrade/buyLoanByBalance",pay,buyLoanByBalance); 
}
var remanPeriods;//最小锁定期
var investPeriod;
var orderId;
var adjustRate;  //调整利率
var remanDays;
var investmentAmount = $("#investmentAmount").val();//代金券使用需满足的金额
var earningsAnnualRate; //年化利率
var investMinAmount;
var voucherAmount;//代金券金额
var voucherId = $("#voucherId").val();
var remanFlag;//是否是短期标
var earningsDay;
var setOrderFormDetails = function(data){ 
    if(data.rescode == "00000"){
        var dataInfo = data.list[0];
        investPeriod = dataInfo.investPeriod;
        remanDays = dataInfo.remanDays;
        orderId = dataInfo.orderId;
        adjustRate = parseFloat(dataInfo.adjustRate);  //调整利率
        earningsAnnualRate = dataInfo.annualRate;
        investMinAmount = dataInfo.investMinAmount;
        setRemanAmount(dataInfo.remanAmount); //剩余金额
        earningsDay = dataInfo.expectedReturnDays;
        //剩余可购
        $(".totalMoney").html(numFormat(dataInfo.remanAmount));
        //起投金额
        $(".investMinAmount").html(investMinAmount+"元");
        if(remanDays == "0") {
            remanFlag = false;           
        }else {
         investPeriod = remanDays;
            remanFlag = true;
        }
        $("#productName").html(dataInfo.productName);//标名
        $("#annualRate").html(dataInfo.annualRate);//年化利率
        $("#startDate").html(dataInfo.startDate); //起息日期
        $("#endTime").html(dataInfo.endTime); //到期时间
        if(remanDays == "0") {
            $("#investPeriod").html(dataInfo.investPeriod + "个月"); //理财期限月    
            remanPeriods=dataInfo.investPeriod;
        }else {
            $("#investPeriod").html(dataInfo.remanDays + "天"); //理财期限天
            remanPeriods=dataInfo.remanDays;
//          investPeriod = remanDays;
        }
        $("#repaymentType").html(dataInfo.repaymentType); //还款方式
        addRate = accAdd(rateRises, adjustRate)
        if(adjustRate != "" && adjustRate != null && adjustRate != "0") {
            $("#rateRisesFont").html(addRate);
            $("#ratesIcon").show();
        }
        $("#useRateCuuponsUL").attr("value",remanPeriods);
        ajaxRequest(contextPath + "wxtrade/accountBalance", "", accountBalance);
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "&status=0&product="+investPeriod,newSetMyRateCoupons,"GET");
    }
};
var setServAgreementByType = function(data){ //协议判断ios/app
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
$("#agreenment").click(function(){
    $("#subDiv").toggle("fast");    
})

var voucherAmount;
var voucherId = $("#voucherId").val();
if (voucherId == "null" || voucherId == '') {
    voucherId = '';
};

var host="",j=0,t=0;
function inquire(a,b){
     host=window.location.href;
     t=host.indexOf(a);
     p=host.indexOf(b)
     if(t==-1) return
     j=a.length;
     if(p==''||p==null){
        host=host.substring(t+j);
     }
    host=host.substring(t+j,p);
    if(host=="undefined") return
    return host
}
$(document).ready(function(){
    
    FastClick.attach(document.body);
    (function rem(x){
        document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
        window.addEventListener("resize",setRenSize,false);
        function setRenSize(){
        document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
        }
    }(7.5));
    ajaxRequest(contextPath + "base/getMetaInfo", "name=preSaleAgreementRegular", setMetaInfo);
    var host1=inquire("inquire=","");
    $("#inputAmount").val(host1);
     $("#earnings").html("0.00");
    voucherAmount = $('#voucherAmount').val();//代金券面额
    loanId = $("#loanId").val();
    sloanId = $("#sloanId").val();
    cpType = $("#cpType").val();
    rateRises = $("#rateRises").val();
     planId = $("#planId").val();
     rateIds = $("#rateIds").val();
    var earningsDay =$("#earningsDay").val();
    var investPeriod2 =$("#product").val();
    document.title=cpType;

    if(voucherAmount == 0){
        $("#voucherTr").hide();
    }else{
        $("#voucherTr").show();
        $("#voucher").html(voucherAmount);
    }
    if(rateRises != "" && rateRises != null){
        $("#rateRisesFont").html(rateRises);
        $("#ratesIcon").show();
    }
    if(earningsDay>0){
        $("#addEarnings").html("+"+numFormat(earningsDay));
    }else{
        $("#addEarnings").html();
    }
    ajaxRequest(contextPath+"wxtrade/getOrderFormDetails","loanId="+ loanId +"",setOrderFormDetails);
    
    ajaxRequest(contextPath + "wxvouchers/myVouchers", "loanId="+ sloanId, setMyVouchers,"GET");
});
//10月新增

function newSetMyRateCoupons(data){
    $("#useAddRates").html("<span class='redTex'>"+data.totalNum+"</span>张可用");
     if(rateRises != 0 && rateRises != "null"){
            $("#useAddRates").html("已加息<span class='redTex'>"+rateRises+"%</span>");
        }else{
            rateRises = 0;
        }
}
function setMyVouchers(data){
    $("#useVoucherTip").html("<span class='redTex'>"+data.totalNum+"</span>张可用");
    if(voucherAmount != "null" && voucherAmount != '' && voucherAmount != 0){
        $("#useVoucherTip").html("已抵扣<span class='redTex'>"+voucherAmount+"</span>元");
    }else{
        voucherAmount = 0;
    }
}
var iframeFn=function(){
    var mobile=$("#mobile").val();
    var userId=$("#userId").val();
    $("#iframe").attr("src",contextPath + "wxpay/goPay?userId="+userId+"&mobile="+mobile+"&payFlag=wx")
}
//充值页面
var goTopUpcz = function(money) {
    cancelTopUp();
    $(".wrap").addClass("height100P");
    $("iframe")[0].contentWindow.window.freshFn(money);
    $("#iframeWrap").addClass("showIframe");
}
//有加息券使用加息券
function useRatesInterest(){ 
    window.location.href = contextPath + "wxcoupons/goMyInterest?product="+investPeriod+'&sloanId='+sloanId+'&loanId='+loanId+'&voucherAmount='+voucherAmount+'&investmentAmount='+investmentAmount+'&voucherId='+voucherId+'&finishedRatio='+finishedRatio+'&inquire='+inputAmount;  
    sessionStorage.setItem("from","fix");
}

//使用代金券
function useVoucher(){
     window.location.href = contextPath + "wxuser/goVoucher?sloanId="+sloanId+'&rateRises='+rateRises+'&from=productDetails'+'&loanId='+loanId+'&planId='+planId+'&rateIds='+rateIds+'&inquire='+inputAmount+'&finishedRatio='+finishedRatio+"&remanPeriods="+ $("#useRateCuuponsUL").attr("value");;  
}
var userId;
var product;
var status;
var btnChange = function(type) {
    if (type == 3) {//余额是否足够
        $("#goPay").removeClass("inactiveBtn").html("马上充值");
        $("#goPay").attr("href", contextPath + "wxtrade/goTopUpcz");
    } else if (type == 4) {
        $("#goPay").addClass("inactiveBtn").html("立即投资");
        $("#goPay").attr("onclick", "errorMessage('请输入投资金额')");
    } else if (type == 5) {
        $("#goPay").removeClass("inactiveBtn").html("立即投资");
        //查询是否有代金券
        $("#goPay").attr("onclick", "ajaxMyCouponsList()");
    } else if (type == 6) {//将不够的金额带到充值页面
        iframeFn();
        $("#goPay").removeClass("inactiveBtn").html("马上充值");
        $("#goPay").attr("onclick", "goTopUpcz(" + topUpMoney + ")");
        alertBox1("您的账户当前余额不足，请及时充值","cancelTopUp","取消","goTopUpcz('" + topUpMoney + "')","充值",1); 
    }  
} 
var cancelTopUp = function(){
    $(".wrap").removeClass("height100P");
    $(".alertBoxWrap").addClass("none");
    $("#inputAmount").val("");
    $("#earnings").html("0.00");
    calculateEarnings();
}

//查询是否有代金券接口
var ajaxMyCouponsList = function(){
    if(!checkBoxArea){
        errorMessage("请仔细阅读并同意服务协议");
        return false; 
    }
    else{
        if (parseInt(inputAmount) >= parseInt(investMinAmount)) { //输入金额大于起投金额
            ajaxRequest(contextPath + "wxvouchers/getMyCouponsList", "productType=108&pageSize=5&remanPeriods="+investPeriod+"&remanFlag="+remanFlag, setMyCouponsList,'GET');
        }else{
            errorMessage('投资金额不能小于起投金额');
        }
    } 
}
//是否有代金券
var setMyCouponsList = function(data){
    if(data.rescode == "00000"){
        if(data.vouchersListCount != 0){//有代金券
            if(voucherAmount == "null" || voucherAmount == ''){//没有使用代金券
                if (parseInt(inputAmount) >= parseInt(investMinAmount)) { //输入金额大于起投金额
                    goMyCouponsList();
                } else {
                    $("#goPay").attr("onclick", "errorMessage('投资金额不能小于起投金额')");
                }
            }else{//如果使用了代金券则进入加息劵判断
                if(parseFloat(investmentAmount) > parseFloat(inputAmount)){
                    errorMessage('您选择的代金券需要投资'+investmentAmount+'元可用，请重新操作');
                    return false;
                }else{
                    queryRateRises();
                }      
            }
        }else{//如果没有代金券则直接进入加息劵判断
            queryRateRises();
        }
    }
}   
//代金券弹框
var goMyCouponsList = function(){
    alertBox1("您还有定期代金券未使用是否要使用？","queryRateRises","不使用","useVoucher","使用",1)           
}
//是否有加息劵，是否使用
var queryRateRises = function(){
    $(".alertBoxWrap").addClass("none");
    if(parseFloat(substrAmount) > parseFloat(accountBalanceAmount)){//判断是否需要充值
        errorMessage('账户余额不足');
        topUpMoney = Subtr(substrAmount, accountBalanceAmount);
        btnChange(6);
    }else{
        if(rateRises != "" && rateRises != "null"&&rateRises != 0){//加息利率不为空，使用了加息劵
            usePassword(goPay);
        }else{//如果没有使用加息劵则请求接口判断是否有加息劵
            goMyRateCoupons();     
        }
    }
}
//是否有可用加息劵    
function goMyRateCoupons(status) {
    ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "&status=0&product="+investPeriod+"&current=1&pageSize=5",setMyRateCoupons,"GET");   
    
}
function needGopay(){
    $(".alertBoxWrap").addClass("none");
    usePassword(goPay);
}
//未用过加息券使用加息券弹框
function setMyRateCoupons(data){
    if(data.rescode == "00000"){
        if(data.rateCouponsList.length <= 0){//没有加息劵
            needGopay()
            //没有对应加息券数据 使用加息券隐藏
        }else{
            //加息劵弹框
            alertBox1("是否使用加息券","needGopay","不使用","useRatesInterest","使用",1)
            
        }
        
    }else {
        
    }
}
var status;
var topUpMoney;
var rateRises;  //加息劵利率
var addEarnings;
var substrAmount;
var calculateEarnings = function(num) {
    inputAmount = $("#inputAmount").val();//输入金额
    rateRises = $("#rateRises").val();//加息劵利率
    substrAmount = Subtr(inputAmount,voucherAmount);//输入金额减去代金券的金额
    if(rateRises == "" || rateRises == null){
        if(adjustRate != "0") {
            addEarnings = accMul(accDiv(accMul(accMul(inputAmount, adjustRate), 0.01), 365), earningsDay);
            $("#addEarnings").html(numFormat(addEarnings)+'元');
        } 
        if(addEarnings >= 0.01){
            $("#addEarnings").html(numFormat(addEarnings)+'元');
        }
        
    }else{
        interestTol = accAdd(rateRises, adjustRate);
        addEarnings = accMul(accDiv(accMul(accMul(inputAmount, interestTol), 0.01), 365), earningsDay);
        if(addEarnings >= 0.01){
            $("#addEarnings").html(numFormat(addEarnings)+'元');
        }
    }
    earnings = accMul(accDiv(accMul(accMul(inputAmount, earningsAnnualRate), 0.01), 365), earningsDay);
    $("#earnings").html(numFormat(earnings)+'元');
    var allEarnings = numFormat(parseFloat(earnings) + parseFloat(addEarnings));
    $("#allEarnings").html(allEarnings);
    
    
    if (inputAmount > 0 ) {//输入金额大于0
        btnChange(5);
    } else if (substrAmount > accountBalanceAmount) {//输入金额减去代金券的金额大于账户余额
        topUpMoney = Subtr(substrAmount, accountBalanceAmount);
        btnChange(6);
    } else {
        btnChange(4);
    }
    if(!(num==1)){
        var minNumber=$("#inputAmount").attr("minNumber");
        if(parseInt(inputAmount)>=parseInt(minNumber)){
             errorMessage('投资金额不能大于可投总额');
        }
    }
    
}
var inputAmount;
var earnings;
var accountBalanceAmount;
var loanId;
var planId;
var sloanId;
var cpType;
var rateIds;

var setRemanAmount = function(data) { //账户余额生成HTML
    var html = '<li>剩余</li><li class="mark">:</li>';
    var remanAmount = numFormat(data);
    remanAmountNum = data;
}
var freshMoney=function(data){
    accountBalanceAmount = data;
    $("#accountBalanceAmount").html(numFormat(accountBalanceAmount));
}
var accountBalance = function(data) {
    accountBalanceAmount = data;
    $("#accountBalanceAmount").html(numFormat(accountBalanceAmount));
    if (accountBalanceAmount <= remanAmountNum) {
      var minNumber = remanAmountNum;
    } else {
        var minNumber = remanAmountNum;
    }
    $("#inputAmount").attr("minNumber", minNumber);
    
  
    calculateEarnings(1);  //键盘全额  传1
    
    var quickMoney = new Array;
    quickMoney = [1000,5000,10000];   //2016/12/22版本之后   定活期自定义键盘都写死的快捷输入值
    $("#shortcut").html("");  //先为空
    for(var i = 0; i < quickMoney.length; i++){
        $("#shortcut").append('<li class="fl grayTex1 radius3 bkg4 boxSizing" money="'+quickMoney[i]+'">'+quickMoney[i]+'</li>');
    }
    if(accountBalanceAmount<=0){
        $("#shortcut").append('<li class="fl radius3 grayTex1 bkg4 boxSizing allQuickMoney" money="0.00">全 额</li>');
    }else if(parseFloat(accountBalanceAmount)>parseFloat(remanAmountNum)){
        $("#shortcut").append('<li class="fl radius3 grayTex1 bkg4 boxSizing allQuickMoney" money="'+ remanAmountNum +'">全 额</li>');
    }else{
        $("#shortcut").append('<li class="fl radius3 grayTex1 bkg4 boxSizing allQuickMoney" money="'+ accountBalanceAmount +'">全 额</li>');
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


//快速选择金额
$(".main").click(function(e) {
    var target = $(e.target);
    if(!target.is('#inputAmount')) {//如果点击事件是#inputAmount
        $(".wrap").removeClass("height100P");
        $("#momeyBox").stop().slideUp(200);
        $('#goPay').css("margin-bottom","0px");  
    }else{
        $("#momeyBox").stop().slideDown(200);
        $('#goPay').css("margin-bottom","300px");   
    }
});

var checkBoxArea=true;
$("#checkBoxArea").on("click",function(){
    if(checkBoxArea){
        $(this).attr("src",contextPath+"/pic/weixin/ver2_9/checkbox1.png"); 
        checkBoxArea = false;
    }
    else{
        $(this).attr("src",contextPath+"/pic/weixin/ver2_9/checkbox2.png"); 
        checkBoxArea = true;
    }
})
function freshAccountBalance(){
    ajaxRequest(contextPath + "wxtrade/accountBalance", "", freshMoney);
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
					 ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type="+type+"&loanMonths="+investPeriod + "&productId=" + loanId+"&remanDays"+remanDays,setServAgreementByType,"GET");    
				})
		})(equal.type)
			
		}
	
	}
}
