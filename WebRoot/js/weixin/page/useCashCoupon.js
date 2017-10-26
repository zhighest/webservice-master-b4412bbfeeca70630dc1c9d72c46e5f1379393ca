//点击立即立即领取后发送数据
var UrlData; 
function sendDataOnClick() {
	var useMobile = $("#phoneNo").val();//兑换手机的号
	ajaxRequest(contextPath + "shopVochers/useThirdCoupon?" + UrlData, "useMobile=" + useMobile, sendDataOnClickCb); //发送请求
}
//发送数据回调
function sendDataOnClickCb(data) {
	if(data.rescode == "00000") {
		loadingMessage("兑换成功");
		$('#alertBox').remove();
		var UrlData = window.location.href.split("?")[1];//把数据带到下个页面去
		window.location.href = "cashCouponUsedDetails?"+UrlData;
	} else {
		errorMessage(data.resmsg_cn);
	}

}

$(document).ready(function() {
	UrlData = window.location.href.split("?")[1]; 
	if(UrlData.indexOf("&") > 0){
		UrlData = UrlData.split("&")[0];
	}
	//页面初始化时加载数据
	function getDataOnStart() {
		ajaxRequest(contextPath + "shopVochers/queryVochersDetails?" + UrlData, "", getDataOnStartCb); //发送请求
	}
	getDataOnStart();
	//初始化数据回调
	function getDataOnStartCb(data) {
		if(data.rescode == "00000") {
			$("#couponIfo").text(data.voucher.voucherName); //更新现金券名称
			$("#validityDate").text(getDate(data.voucher.endTime).Format("yyyy/MM/dd"));//使用有效期
			$('#voucherDescribe').text(data.voucher.voucherDescribe);//券用途
		} else {
			errorMessage(data.resmsg_cn);
		}
	}
	//格式化手机号  EG:phoneNumFormat('13555555555','-')---->135-5555-5555
    function phoneNumFormat(str, separate) {
        return (str+"").substring(0, 3) + separate + (str+"").substring(3, 7) + separate + (str+"").substring(7);
    }
    //点击领取按钮事件
	$('#getCashCoupon').click(function() {
		var phoneNum = $("#phoneNo").val();
		//手机号不能为空
		if(phoneNum == "" || phoneNum == null || phoneNum == "null") {
			errorMessage("请输入手机号");
			return false;
		} 
		//手机号格式校验
		if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
			errorMessage("请输入正确的手机号码");
			return false;
		}

		alertBox2("温馨提示", "确认兑换到  " + phoneNumFormat(phoneNum, '-'), "sendDataOnClick", 1)
	});
	$('#showHelp').click(function(){
		UrlData = window.location.href.split("?")[1]; 
		window.location.href='showZQHHelp?'+UrlData;
	});
})