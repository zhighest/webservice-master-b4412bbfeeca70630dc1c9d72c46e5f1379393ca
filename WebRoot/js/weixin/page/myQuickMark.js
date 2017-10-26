var setShowAssets = function(data) { //根据model val获取的手机号得到改手机号对应的用户头像
	if(data.accountDetail.imageIconDispose != "" && data.accountDetail.imageIconDispose != null&& data.accountDetail.imageIconDispose != "null"){
		$("#defaultAvatar").attr("src",data.accountDetail.imageIconDispose)
	}
}
var mobile; //定义一个变量mobile
$(document).ready(function() {//确定页面加载完毕
    mobile =  $("#mobile").val();//获取model val，将值赋给mobile
    $("#mobileNum").html(numHide(mobile));//通过mobile得到#mobileNum，并调用numHide方法
    ajaxRequest(contextPath + "wxdeposit/queryMyAccountDetail", "", setShowAssets);//获取接口建立方法setShowAssets
});