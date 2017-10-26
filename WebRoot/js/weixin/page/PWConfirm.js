var traderPassword;
var repetition;
//设置密码回调
var goRestPassword = function(data){
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
        clickCheck = true;
        return false;
    }else{
        window.location.href = contextPath +'wxtrade/goSetting';
    };
};
var clickCheck = true;
$(document).ready(function() {   
    //确定点击事件
    $("#enterBtn").click(function(event) {
        traderPassword = $("#traderPassword").val();
        repetition = $("#repetition").val();
        if (!clickCheck) {
            return false;
        };
        if(traderPassword == '' || traderPassword == null){
            errorMessage("请输入交易密码");
            return false;
        };
        if(!(/^[0-9]{6}$/.test(traderPassword))){
            errorMessage("请填写6位数字");
            return false;
        };
        if(repetition == '' || repetition == null){
            errorMessage("请再次输入交易密码");
            return false;
        };
        if(!(/^[0-9]{6}$/.test(repetition))){
            errorMessage("请完整填写交易密码");
            return false;
        };
        if(traderPassword !== repetition){
            errorMessage("两次输入密码不一致，请重新输入");
            return false;
        }
        clickCheck = false;
        ajaxRequest(contextPath + "wxuser/resetPassword", "passwordCash="+traderPassword, goRestPassword,'GET');
    });
});
