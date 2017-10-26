var setShowAccount = function(data) {
	if(data.accountDetail.realName == ''||data.accountDetail.realName==null||data.accountDetail.realName=='*ull'){
		$("#userName").html(numHide(data.accountDetail.mobile)); //用户名
	}else{
		$("#userName").html(data.accountDetail.realName); //用户名
	}
    $("#totalAssets").html(numFormat(data.accountDetail.totalAssets)); //账户总额
    $("#incomeAmount").html(numFormat(data.accountDetail.incomeAmount)); //累计收益
    $("#balanceMoney").html(numFormat(data.accountDetail.acctBalance)); //可用余额
    if(data.cashInMoney > 0) {
	    $("#cashInMoneyArea").show();
	    $("#hoverArea").css("cursor","pointer");
	    $("#hoverArea").hover(function(){
		    $("#cashInMoneyInfo").show();
		    },function(){
		    $("#cashInMoneyInfo").hide();
		});
		$("#cashInMoney").html(numFormat(data.cashInMoney)); // 在途金额   
    } 
}  
$(document).ready(function() {
	ajaxRequest(contextPath + "webindex/queryMyAccountDetail", "", setShowAccount);//账户信息
	$("#exit").click(function(){
	    alertBox(contextPath + "pic/web/accoutOverview/closeIt.png","100px","退出登录？","确认退出登录或取消","exitThis",1);
	})
});

var exitThis = function(){
	$("#okBtn").attr("href",contextPath+"user/logOut");
}


