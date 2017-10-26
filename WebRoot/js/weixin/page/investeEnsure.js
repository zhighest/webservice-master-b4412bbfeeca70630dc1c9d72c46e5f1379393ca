
//以下为特效部分
//点击输入框弹出弹窗
$(".main").click(function(e) {
    var target = $(e.target);
    if(!target.is('#inputAmount')) {//如果点击事件是#inputAmount
    	$(".wrap").removeClass("height100P");
        $("#momeyBox").stop().slideUp(200);
        $('#enterBid').css("margin-bottom","0px");  
    }else{
        $("#momeyBox").stop().slideDown(200);
        $('#enterBid').css("margin-bottom","300px");   
    }
});
//点击协议显示隐藏三个协议的动画特效
//服务协议的显示隐藏
$("#agreenment").click(function(){
     $("#subDiv").toggle("fast");    
 })
//风险提示书的点击事件
$("#riskcue").click(function(){
    ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type=riskcueEnjoyPlan&productId=107",setServAgreementByType,"GET");
})
//个人出借咨询服务协议的点击事件
$("#loanAdvisoryServiceAgreement").click(function(){
    ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type=loanAdvisoryServiceAgreementEnjoyPlan&productId=107",setServAgreementByType,"GET");
})
//用户服务协议的点击事件
$("#serviceAgreement").click(function(){
    ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type=serviceAgreementEnjoyPlan&productId=107",setServAgreementByType,"GET");
})
//债权转让协议的点击事件
$("#assignmentOfDebtAgreement").click(function(){
    ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type=assignmentAgreementEnjoyPlan&productId=107",setServAgreementByType,"GET");
})
//点击协议的跳转事件
var setServAgreementByType = function(data){ 
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
        window.location.href = data.serAgreementUrl;
    }
};
//充值页面
var goTopUpcz = function(money) {
    $(".wrap").addClass("height100P");
    $("iframe")[0].contentWindow.window.freshFn(money);
    $("#iframeWrap").addClass("showIframe");
}
var acctBalance;
var investMinAmount;
var investLimitFlg;
var productName;
var investLimitAmount;
var investMaxAmount;
//调用接口wxenjoy/getEnjoyBuyDetail里的数据 begin
var setEnjoyBuyDetail = function(data){
  var jsonObj = data.enjoyBuyDetail;
  acctBalance = jsonObj.acctBalance;
  investMinAmount = jsonObj.investMinAmount;
  investLimitFlg = jsonObj.investLimitFlg;
  productName = jsonObj.productName;
  investLimitAmount = jsonObj.investLimitAmount;
  investMaxAmount = jsonObj.investMaxAmount;
  enjoyBuyTimeWarn=data.enjoyBuyTimeWarn;
	//从接口中调用数据，并将数据在jsp页面相对应的id里显示出来 begin
   // $("#investMaxAmount").html(investMaxAmount);
   $("#acctBalance").html(numFormat(acctBalance));//账户余额
    if(investLimitFlg =="0"){
      $("#investMaxAmountLi").hide();
   }else{
      $("#investMaxAmountLi").show();
   }
  
 /*  if(parseFloat(investMaxAmount)<0){
      $("#investLimitAmount").html("0");
   }else{
      $("#investLimitAmount").html(investMaxAmount);investLimitAmount
   }*/
   if(parseFloat(investLimitAmount)<0){
	      $("#investLimitAmount").html("0")
	   }else{
	      $("#investLimitAmount").html(jsonObj.investLimitAmount)
	   }
   //从接口中调用数据，并将数据在jsp页面相对应的id里显示出来 end
   var maxNumber = data.enjoyBuyDetail.acctBalance;
   $("#enjoyBuyTimeWarn").html(enjoyBuyTimeWarn);
   //输入框输入数字监听事件,ture限制第一个字符不能为0，若要计算则加上计算方法在true);后面
   $("#inputAmount").attr("oninput", "$.checkLimit($(this)," + maxNumber + ",true);calculateEarnings();").attr("onkeyup", "$.checkLimit($(this)," + maxNumber + ",true);calculateEarnings();").attr("onafterpaste", "$.checkLimit($(this)," + maxNumber + ",ture);calculateEarnings();");
}
//调用接口wxenjoy/getEnjoyBuyDetail里的数据 end

//第一步，判断输入金额。是否大于0.大于0的情况中是否大于限投金额
var calculateEarnings = function(){
    inputAmount =$("#inputAmount").val();//输入框输入的数值
    var accountBalanceAmount = parseFloat(acctBalance);//账户余额
    $("#inputAmountVal").html(numFormat(inputAmount));
    if (inputAmount > 0) {//输入金额大于0
      if(investLimitFlg=="1"){//有限投金额
          if(parseFloat(inputAmount)>parseFloat(investLimitAmount)){
            $("#enterBid").attr("onclick", "errorMessage('加入金额超过投资上限，请重新输入')");    
        }else{
            $("#enterBid").attr("onclick", "ajaxMyCouponsList()");
        } 
      }else{//没有限投金额
          $("#enterBid").attr("onclick", "ajaxMyCouponsList()");
      }  
    }else {
        $("#enterBid").attr("onclick", "errorMessage('请输入投资金额')");
    }

}

//第二步，小于限投金额中，是否小于起投金额
var ajaxMyCouponsList = function(){
    if(!checkBoxArea){
        errorMessage("请仔细阅读并同意服务协议");
        return false; 
    }
    if (parseInt(inputAmount) >= parseInt(investMinAmount)) { //输入金额大于起投金额
        setMyCouponsList(); 
    }else{
        errorMessage('投资金额不能小于起投金额');
    }
}

var setMyCouponsList = function(){
    var accountBalanceAmount = parseFloat(acctBalance);//账户余额
    topUpMoney = Subtr(inputAmount, accountBalanceAmount);//输入金额减去账户余额
    if (inputAmount > accountBalanceAmount){//输入金额大于账户余额
       var mobile=$("#mobile").val();
       var userId=$("#userId").val();
       $("#iframe").attr("src",contextPath + "wxpay/redirectPay");
       $(".wrap").addClass("height100P");
       $("#enterBid").html("马上充值");
       $("#enterBid").attr("onclick", "goTopUpcz(" + topUpMoney + ")");
    } 
    else{
        rolloutAlert(); 
        // usePassword(getRollOut2);
    }
}
//点击立即投资按钮弹出提示弹框 
var rolloutAlert = function(){
  var inputAmount = $("#inputAmount").val();
  $("#alertBg1").show();
  $("#productName01").html(productName);//产品名称
  $("#inputAmountVal").html(inputAmount);
}
//点击提示弹框的取消按钮，关闭弹出框
$("#cancel").click(function(event) {
  $("#alertBg1").hide();  
  $("#inputAmount").val("").attr('alt',"");
  inputAmount=0;
  $("#earnings").html("0.00");
});
//点击提示弹框的确定按钮，则调用getRollOut方法
var getRollOut = function(){
  usePassword(getRollOut2);
}
//提现输入交易密码后调用购买接口
var getRollOut2 = function(passwordCash){
    $("#alertBg1").hide();
	  var buyAmount = $("#inputAmount").val();
    $("#inputAmount").val("");
	// 输入提现密码方法调用
    //var passwordCash = prompt("请输入提现密码：");
	if (passwordCash == null || passwordCash == "") {
        errorMessage("密码为空！请重新输入！");
    } else {
        ajaxRequest(contextPath + "wxenjoy/buyEnjoyPlan", {
            "passwordCash": passwordCash,
            "buyAmount": buyAmount,
            "productId": '107',
            "from":'LBWX'
        },setRollNum);
        // ajaxRequest(contextPath + "wxenjoy/buyEnjoyPlan", "passwordCash="+passwordCash+"&buyAmount="+buyAmount+"&productId="+productId+"&from=LBWX", setRollNum);
    }
}
//投资结果判断
var setRollNum = function(data){
	if(data.rescode == "00000"){
        errorMessage("购买成功");
        function goUrl(){
            window.location.href=contextPath+"wxInvest/getInvestSucceed?productId=107";//投资成功页面
        }
        setTimeout(goUrl,3000);
    }else{
        errorMessage(data.resmsg_cn);
        setTimeout(function(){
            window.location.reload();
        },1000);
    }
}
//加载iframe内容
var iframeFn=function(){
    $("#inputAmount").val("");
    $("#enterBid").html("立即投资");
    $("#enterBid").attr("onclick", "errorMessage('请输入投资金额')");
    var mobile=$("#mobile").val();
    var userId=$("#userId").val();
    $("#iframe").attr("src",contextPath + "wxpay/redirectPay?payFlag=WX");
    ajaxRequest(contextPath + "wxenjoy/getEnjoyBuyDetail", "productId=107", setEnjoyBuyDetail);
}
$(document).ready(function() {
	FastClick.attach(document.body);
	(function rem(x){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		window.addEventListener("resize",setRenSize,false);
		function setRenSize(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		}
	}(7.5));
  $("#inputAmount").val("");
	if(!checkBoxArea){
        errorMessage("请仔细阅读并同意服务协议");
        return false; 
    }
   $("#enterBid").attr("onclick", "errorMessage('请输入投资金额')");
   //实名认证接口
  ajaxRequest(contextPath + "wxenjoy/getEnjoyBuyDetail", "productId=107", setEnjoyBuyDetail);
 
});
//iframe页面充值后刷新余额
function freshAccountBalance(){
    $("#iframeWrap").removeClass("showIframe");
    $(".wrap").removeClass("height100P");
    iframeFn();
}
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