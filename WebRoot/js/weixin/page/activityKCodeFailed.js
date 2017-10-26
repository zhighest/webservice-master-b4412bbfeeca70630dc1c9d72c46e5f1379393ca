$(document).ready(function(){	
	kCode = $("#kCode").val();
	keyCodeExchangeAmt = $("#keyCodeExchangeAmt").val();
	$("#priceMoney").html(keyCodeExchangeAmt);
	btmContentImg()
});
function btmContentImg() {
	var widowsHeight = $(document).height();
	if(widowsHeight <= 480){
		$("#btmContent").attr("src",contextPath + "/pic/weixin/activity/activityLCode/btmContent_1.png")
	}else if(widowsHeight <= 580 && widowsHeight> 480){
		$("#btmContent").attr("src",contextPath + "/pic/weixin/activity/activityLCode/btmContent_1.png")
		$(".contentTex").css("top","30%");
	}
}
$(window).resize(function(){
  btmContentImg()
});