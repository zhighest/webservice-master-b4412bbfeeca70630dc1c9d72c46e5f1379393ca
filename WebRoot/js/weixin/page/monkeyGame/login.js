//微信进入兑换页面下载APP
function weixinShow() {
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		$("#weixinLogin").show();
		$("#APPExchange").hide();	
	}
}
$(document).ready(function(){	
	weixinShow();
	installment = $("#installment").val();
	kind = $("#kind").val();
	prize_msg = $("#prize_msg").val();
	sort = $("#sort").val();
	mobile = $("#mobile").val();
	prize = $("#prize").val();
	$("#parameter").html(prize);
});
var installment;
var kind;
var prize_msg;
var sort;
var mobile;
var prize;
var gainCodeSwitch = true;
$("#exchangeBtn").click(function() {
    if (!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    ajaxRequest(contextPath + "wxprize/getPrize", "putMobile=" + phoneNum+ "&kind=" +kind+ "&installment=" +installment+ "&prize_msg=" +prize_msg+ "&sort=" +sort+ "&prize=" +prize+ "&mobile=" +mobile, setPrize);
});

function setPrize(data) {
	if(data.rescode == "00000"){
		errorMessage(data.resmsg_cn);
		function goIndex(){
			window.location.href = contextPath+ "wxprize/goPrizeSuccessd?prize="+prize+"&mobile="+mobile;
		}
		setTimeout(goIndex, 1000)
		
    }else{
        errorMessage(data.resmsg_cn);
    }
	
}