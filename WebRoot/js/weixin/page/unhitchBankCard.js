//获取银行卡信息
var getRecePayBank = function(data) {
    if (data.bankId != "" && data.bankId != null) {
        $("#bankIcon").attr("src", contextPath + "" + data.picturePath);
        $("#hasCard").show();
        $("#noHasCard").hide();
        $("#bank_name").html(data.bankName);
        var cardNumber = data.cardNumber.toString();
        $("#cardNumber").html(cardNumber.substr(cardNumber.length-4));
    } else {
        $("#content").stop().hide();
        $("body").html('您还未绑定银行卡，暂不需要解绑！');
    }
};
//页面跳转
var pageSkip = function(){
    window.location.href = contextPath +'wxtrade/goSetting';
}
//身份证号码正则
var IDnumVerify = /^(\d{14}(\d|X|x)$|^\d{18}$|^\d{17}(\d|X|x))$/;
var IDnumber;
//身份证核对回调事件
var goIDCardAndMobile = function(data){
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
        clickCheck = true;
        return false;
    }else if (data.rescode == "00000"){
        $("#tipBox").stop().show();
        $("#tipTex").html(data.resmsg_cn);
    }
};
var clickCheck = true;   
//解绑银行卡申请点击事件
$("#submitBtn").click(function(event) {
    IDnumber = $("#IDnumber").val();
    if (!clickCheck) {
        return false;
    };
    if(IDnumber == '' || IDnumber == null){
        errorMessage("请输入身份证号码");
        return false;
    };
    if(!IDnumVerify.test(IDnumber)){
        errorMessage("请输入正确的身份证号码");
        return false;
    };
    clickCheck = false;
    ajaxRequest(contextPath + "wxtrade/addUnbindingCardApply", "idCard="+IDnumber, goIDCardAndMobile,'GET');
});
$(document).ready(function() {
    ajaxRequest(contextPath + "wxtrade/getRecePayBank", "", getRecePayBank);
});