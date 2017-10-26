

var goOrderConfirmation = function(turnOutAmount, userId, helpId, passwordCash, inputAmount,cpType,rateIds,rateRises) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxtrade/goOrderConfirmation";
    temp.method = "POST";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "planId";
    inp2.value = planId;
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "sloanId";
    inp3.value = sloanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "earnings";
    inp4.value = numFormat(earnings);
    temp.appendChild(inp4);

    var inp5 = document.createElement("input");
    inp5.name = "inputAmount";
    inp5.value = inputAmount;
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "cpType";
    inp6.value = cpType;
    temp.appendChild(inp6);
    
    var inp7 = document.createElement("input");
    inp7.name = "rateIds";
    inp7.value = rateIds;
    temp.appendChild(inp7);
    
    var inp8 = document.createElement("input");
    inp8.name = "rateRises";
    inp8.value = rateRises;
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "earningsDay";
    inp9.value = addEarnings;
    temp.appendChild(inp9);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var couponNum;//抵用券数量
var inputRollNum;//转出金额
var matchAmount;//撮合中金额
var handChargeRate;//手续费百分比
var minHandCharge;//最小手续费
var HandChargeMoney;//手续费
var hasChargeMomey;//转出金额-撮合中金额
//var inputRollNum;//转出金额
var faceValue;//手续费券的面值
var helpId;//抵用券ID
var HandChargeMoney2;//防NAN
var HandChargeMoney3;//未扣抵用券手续费
var HandChargeMoney4;//扣抵用券手续费
var couponAmount;//抵用劵金额
var limitAmount=$("#limitAmount").val();
var inAmount;
var outAmount
var labelFlag=$("#labelFlag").val();//全免劵（0）

var turnOutDetail = function(){
	ajaxRequest(contextPath + "wxenjoy/turnOutDetail", "",setTurnOutDetail);//转出页面账户接口
	
	couponAmount = $("#couponAmount").val();
	if(couponAmount != "null" && couponAmount != null && couponAmount != ""){		
		if(labelFlag == "0" || labelFlag == 0){			
			$("#useAddRates").html('<span class="redTex">全免券</span>')			
		}else{			
			$("#useAddRates").html('已抵用<span class="redTex">'+couponAmount+'</span>元')			
		}
		$('#canel1').hide();
		$('#canel2').show();
	}
	//calculateHandChargeMoney();
}
var setTurnOutDetail = function(data) { //转出页面账户接口生成HTML
		   inAmount=data.inAmount;
     	  outAmount=data.outAmount;
		  if(inAmount==""||inAmount==null) inAmount=0;
	      if(outAmount==""||outAmount==null) outAmount=0;
	      $("#inAmount").html(numFormat(inAmount)+"元");
	      $("#outAmount").html(numFormat(outAmount)+"元");
	      alertHint();
	handChargeRate = data.handChargeRate;//手续费百分比
	var turnOutMsg = data.turnOutMsg;//转出小提示（文字描述）
	var enableAmount = data.enableAmount;//可转出总金额
	var curBondAmount = data.curBondAmount;//在投债券总价值
	matchAmount = data.inAmount;//撮合中金额
	minHandCharge = data.minHandCharge;//最小手续费
	faceValue = data.faceValue;//抵用券的面值
	helpId = data.helpId;//抵用券ID
	if(handChargeRate=='0'){
		minHandCharge=0;
	}
// 	var curBondMoney = data.curBondMoney;//在投债券总金额
	couponNum =  data.couponNum;//判断是否有可用抵用券
	inputRollNum = $("#inputRollNum").val();//转出金额
	$("#turnOutMsg").html(turnOutMsg);//转出小提示（文字描述）
	$("#enableAmount").html(numFormat(enableAmount));//可转出总金额
	$("#enableAmount").attr("value",enableAmount);
	$("#curBondAmount").html(numFormat(curBondAmount));//在投债券总价值）
	$(".matchAmount").html(numFormat(data.matchAmount));//撮合中金额
 	$("#curBondMoney").html(numFormat(0));//在投债券总金额
	//可转出总金额
	var maxNumber = enableAmount;
//     $("#inputRollNum").attr("placeholder","本次最多可转出"+numFormat(maxNumber)+"元");
	$("#rollNum").html(numFormat(maxNumber));
	$("#insideInputBtn").click(function(event) {
		$("#inputRollNum").val(maxNumber);	
	});
	if(maxNumber!=0.00){
		$("#inputRollNum").attr("oninput", "$.checkLimit($(this)," + maxNumber + ",false);calculateHandChargeMoney();").attr("onkeyup", "$.checkLimit($(this)," + maxNumber + ",false);calculateHandChargeMoney();").attr("onafterpaste", "$.checkLimit($(this)," + maxNumber + ",false);calculateHandChargeMoney();");
	}else{
		//alert(maxNumber)
		$("#inputRollNum").attr('disabled',true);
		$("#inputRollNum:disabled").css('opacity',0);
	}
	
}

//手续费计算
var calculateHandChargeMoney = function() {
	if($("#inputRollNum").val()=='00'){
		$("#inputRollNum").val('0');
	}
    inputRollNum = parseFloat($("#inputRollNum").val());
	hasChargeMomey = Subtr(inputRollNum, inAmount);//转出金额-撮合中金额
	HandChargeMoney = accMul(hasChargeMomey, accMul(handChargeRate, 0.01));//手续费
	HandChargeMoney4 = HandChargeMoney;
	console.log(couponAmount)
	if(parseFloat(couponAmount)&&isNaN(couponAmount)!=NaN){
		HandChargeMoney=Subtr(HandChargeMoney,couponAmount);
		HandChargeMoney<=0?HandChargeMoney=0:HandChargeMoney;	
	}
	if(labelFlag == "0" || labelFlag == 0){
		HandChargeMoney=0;
	}
	if($("#inputRollNum").val().length<=0){
		inputRollNum=0;		
	}
	if(hasChargeMomey >= 0) {
		$("#curBondMoney").html(numFormat(hasChargeMomey));//在投债券总金额
		$(".matchAmount2").html(numFormat(matchAmount));		
	}else {
		$(".matchAmount2").html(numFormat(inputRollNum));
		$("#curBondMoney").html(numFormat(0));//在投债券总金额		
	}
	if(inputRollNum < matchAmount) {
		$("#handCharge").html(numFormat(0));
		HandChargeMoney = 0;	
	}
	else if(hasChargeMomey > 0){		
		if(HandChargeMoney <= minHandCharge&&HandChargeMoney!=0){
			$("#handCharge").html(numFormat(minHandCharge));
			HandChargeMoney = minHandCharge;
		}else {
			$("#handCharge").html(numFormat(HandChargeMoney));
			HandChargeMoney = HandChargeMoney;
		}				
		 HandChargeMoney2=HandChargeMoney;
		 HandChargeMoney3=HandChargeMoney;
		faceValue=$("#couponAmount").val();		
		if(faceValue==-1){
			//HandChargeMoney=0;
			HandChargeMoney2="";
			//$("#handCharge").html(numFormat(HandChargeMoney));
			
		}else{
			HandChargeMoney-=faceValue;						
			if(HandChargeMoney>0){		
				//$("#handCharge").html(numFormat(HandChargeMoney));				
				HandChargeMoney2="";
			}else if(HandChargeMoney<0){
				//$("#handCharge").html(numFormat(0));				
				//HandChargeMoney=0;				
				HandChargeMoney2="";
			}else{				
				$("#handCharge").html(numFormat(HandChargeMoney2));				
			}
		}
				
	}else {
		$("#handCharge").html(numFormat(0));
		HandChargeMoney = 0;			
	}	
}
//有抵用券使用抵用券
function useRatesCoupon(){
    window.location.href = contextPath + "wxInvest/getVoucherList";  
    sessionStorage.setItem("from","available");
    localStorage.setItem('a',inputRollNum);
}
//转出提示确认弹框 
var rolloutAlert = function(){
	var arriveAmount=0;//转出金额
	$("#alertBox").remove();
	$("#alertBg1").show();
	//$("#alertBg1").show();
	$("#turnOutAmountText").html(numFormat(inputRollNum));
	if(inputRollNum < matchAmount){
			$("#matchAmountText").html(numFormat(inputRollNum));
		}else{
			$("#matchAmountText").html(numFormat(matchAmount));
		}
	if(HandChargeMoney2!=""){		
		$("#tips4").hide();
	}
	if(hasChargeMomey <= 0) {		
		//$("#tips3").hide();
		$("#handChargeText").html(numFormat(0));
		arriveAmount=inputRollNum-0;
		$("#arriveAmount").html(numFormat(arriveAmount));
	}else {
		$("#tips3").show();	
				
		if(HandChargeMoney2==""){
			$("#handChargeText").html(numFormat(HandChargeMoney3));
			arriveAmount=Subtr(inputRollNum,(HandChargeMoney3-0).toFixed(2));
		}else{			
			$("#handChargeText").html(numFormat(HandChargeMoney2));	
			arriveAmount=Subtr(inputRollNum,(HandChargeMoney3-0).toFixed(2));
		}
		$("#HandChargeMoneyText").html(numFormat(hasChargeMomey));	
		if(arriveAmount==0){
			arriveAmount=0;
		}
		$("#arriveAmount").html(numFormat(arriveAmount));			
		if(couponAmount=="-1"){
			$("#minusAmount").html("");
			$("#loan").html("全免手续费抵用券");
		}else{
			$("#minusAmount").html(numFormat(couponAmount));
			$("#loan").html("元手续费抵用券");
		}
	}
}	
//手续费抵用券确认弹框
var ratesCouponAlert = function(){
	if(couponNum != 0) {
		if(inputRollNum > matchAmount) {
			alertBox1("您有手续费抵用券未使用","rolloutAlert","不使用","useRatesCoupon","立即使用",1);
		}else {
// 			getRollOut();
			rolloutAlert();	
		}	
	}
}
//转出结果判断
var setRollNum = function(data){
	if(data.rescode == "00000"){
		
        errorMessage("转出成功");
        function goUrl(){        	           
           location.replace(contextPath+"wxInvest/getRolloutSucceed");
           
        }
        setTimeout(goUrl,3000);
    }else{
        errorMessage(data.resmsg_cn);
        setTimeout(function(){
            window.location.reload();
        },1000);
    }
}
//提现输入交易密码后调用转出接口
var getRollOut2 = function(passwordCash){
	
	$("#alertBg1").hide();
    $("#inputRollNum").val("");
	// 输入提现密码方法调用
    //var passwordCash = prompt("请输入提现密码：");
	if (passwordCash == null || passwordCash == "") {
        errorMessage("提现密码为空！请重新输入！");
   } else { 
   		var token = $("#token").val();
        helpId=$("#couponId").val();
        var turnOutFee;
        if(HandChargeMoney2==""){
        	turnOutFee=HandChargeMoney;
        }else{
        	turnOutFee=HandChargeMoney2;
        }
        ajaxRequest(contextPath + "wxenjoy/turnOut", {
            "passwordCash": passwordCash,
            "token": token,
            "helpId": helpId,
            "turnOutAmount": inputRollNum,
            "turnOutFee":turnOutFee,
            "from":'LBWX',
            "turnOutFee":HandChargeMoney3

        },setRollNum);
    }
}
var getRollOut = function(){
	var inputRollNum = $("#inputRollNum").val();
	if(inputRollNum == 0){
	    errorMessage("输入的金额不能为0");
	    return false;
	}		
	usePassword(getRollOut2);
	
}
//弹出框点击关闭
$("#cancel").click(function(event) {
	$(".alertBg").hide();	
	
});

$("#inactiveBtn").click(function(event) {
	var inputRollNum = $("#inputRollNum").val();
	judge(inputRollNum);
});	
function goxieyi(){	
    ajaxRequest(contextPath+"wxagreement/getServAgreementByType","type=assignmentAgreementEnjoyPlanRollout",setServAgreementByType,"GET");
}
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
var cantzhuan=false;
$("#icon1").click(function(){
	
	$("#icon2").toggle(0,function(){
		cantzhuan=!cantzhuan;
	});

})
$('#canel2').click(function(){
			event.stopPropagation();
			location.href=contextPath +'wxInvest/getRolloutIndex';
			$('#canel1').show();
			$('#canel2').hide();
			localStorage.setItem('a',inputRollNum);

		})
function judge(inputRollNum){
	if(inputRollNum == 0){
	    errorMessage("输入的金额不能为0");
	    return false;
	}
	if(cantzhuan){
		errorMessage("请同意债权转让协议");
	    return false;
	}

	if(HandChargeMoney2==""){
		if(HandChargeMoney4<=parseFloat(limitAmount)){
		/*errorMessage("转出金额手续费不满足抵用券使用条件，请重新选择");
		return false;*/
	}
		rolloutAlert();		
	}else{		
		if(couponNum!=0){
			ratesCouponAlert();
		}else{
			rolloutAlert();
		}
	}

	
}
$("#fullAmount").on("click",function(event){
	var fullAmount=parseFloat($("#enableAmount").attr("value"));
	var inputRollNum = $("#inputRollNum").val(fullAmount);
	calculateHandChargeMoney();
	//judge(inputRollNum);
	
})

//转出弹框
function alertHint(){
    var index=0;
  	$("#icon").on("click",function(){
  		if(index==0){
  			$("#msg").removeClass("opacity0");
  			index=1;
  		}
  		else if(index==1){
  			$("#msg").addClass("opacity0");
  			index=0;
  		}
  	})
  	var oImg=document.getElementById("iconImg"),
  	oMsg=document.getElementById("msg"),
  	top=oImg.offsetTop+12+"px",
  	width=oMsg.offsetWidth,
  	width2=oImg.offsetWidth,
  	left=oImg.offsetLeft-width+width2*3+"px";
  	width=width-width2*3+2+"px";
  	$("#msg").css({"top":top,"left":left});
  	$("#figure").css({"top":top,"left":left});
  	$("#figure").css({"transform":"translateX("+width+")","-webkit-transform":"translateX("+width+")"});
}

$(document).ready(function(){
	turnOutDetail();
	var value=localStorage.getItem('a');
	if(value=='0'){
		value='';
	}
	$("#inputRollNum").val(value);
	var timer=setTimeout(function(){
		
		calculateHandChargeMoney();
		localStorage.removeItem('a');
	},500);
	
	
	$("#inputRollNum").on('focus',function(){
		
		scrollTo(0,100);
		
	})
	$("#inputRollNum").on('blur',function(){
		
		scrollTo(0,0);
		
	})
	//setTurnOutDetail(data);
// 	$("#inputRollNum").attr("oninput", "$.checkLimit($(this)," + maxNumber + ",false);calculateHandChargeMoney();").attr("onkeyup", "$.checkLimit($(this)," + maxNumber + ",false);calculateHandChargeMoney();").attr("onafterpaste", "$.checkLimit($(this)," + maxNumber + ",false);calculateHandChargeMoney();");
	//calculateHandChargeMoney();
	
});