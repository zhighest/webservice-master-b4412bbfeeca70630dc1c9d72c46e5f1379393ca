setLoginPassword//实名认证
function queryAuthentication() {
    if (!idcardValidate) {
        location.href = contextPath + "wxtrade/goAuthentication";
    }
}
//解绑银行卡
function unhitchBankCard() {
        location.href = contextPath + "wxtrade/goUnhitchBankCard";
}
//修改交易密码
function queryChangePassword() {
        location.href = contextPath + "wxtrade/goChangeExchangePassword?isYN="+idcardValidateYN;
}
//填写邀请码
function queryInvitationCd() {
	if (!hasInvitationCd) {
    	location.href = contextPath + "wxtrade/goSetInvitationCd";
    }
}
//设置登录密码
function setLoginPassword(){
    location.href = contextPath + "wxuser/goSetLoginPassword";
}
//风险承受能力评估
function setRiskEvaluation(){

    location.href = contextPath + "wxtrade/goRiskEvaluationEntrance";
}
//修改登录密码
function amendLoginPassword(){
    location.href = contextPath + "wxuser/goAmendLoginPassword";
}
var idcardValidate = true;
var hasInvitationCd = true;
var idcardValidateYN;
var getIdcardInfo = function(data) {
    $("#user").html(data.phoneNo);
    if (data.idcardValidate != "Y") {//没有实名认证
        idcardValidate = false;
        idcardValidateYN = "N";
        $("#idcardValidateN").show();
    } else {
        idcardValidate = true;
        idcardValidateYN = "Y";
        $("#identityName").html(data.showname);
        $("#idcardValidateY").show();
    }
    if(data.settingPasswordFlag == "Y"){
        $("#setLoginPassword").stop().hide();
        $("#amendLoginPassword").stop().show();
    }else{
        $("#setLoginPassword").stop().show();
        $("#amendLoginPassword").stop().hide();
    }
    var systemPasswordLevel = parseInt(data.systemPasswordLevel);
    var userPasswordLevel = parseInt(data.userPasswordLevel);
    if(userPasswordLevel <= 3){
        $("#userPasswordLevel").show();
        if(userPasswordLevel == "0" || userPasswordLevel == 0){
            $("#userPasswordLevel").html("弱");
            $("#userPasswordLevel").css({
                "color":"#FF6F6F",
                "border":"1px solid #FF6F6F"
            });
        }else if(userPasswordLevel == "1" || userPasswordLevel == 1){
            $("#userPasswordLevel").html("一般");
            $("#userPasswordLevel").css({
                "color":"#FBA828",
                "border":"1px solid #FBA828"
            });
        }else if(userPasswordLevel == "2" || userPasswordLevel == 2){
            $("#userPasswordLevel").html("较强");
            $("#userPasswordLevel").css({
                "color":"#349CF3",
                "border":"1px solid #349CF3"
            });
        }else if(userPasswordLevel == "3" || userPasswordLevel == 3){
            $("#userPasswordLevel").html("强");
            $("#userPasswordLevel").css({
                "color":"#0AA528",
                "border":"1px solid #0AA528"
            });
        }
    }

}
var getInvitationCd = function(data) {
    if (data.rescode != "00000") {
        hasInvitationCd = false;
        $("#invitationCdN").show();
    } else {
        hasInvitationCd = true;
        $("#invitationCd").html(data.invitationCd);
        $("#invitationCdY").show();
    }
}
//忘记密码点击事件
var forgetPassword = function(){
    if(idcardValidateYN == 'Y'){
        window.location.href = contextPath + "wxuser/changePassword";
    }else{
        $("#tipBox").stop().show();
    }
}
$(document).ready(function() {
    var riskType;
    var isRiskEvaluation;
    var mobile
    ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
    ajaxRequest(contextPath + "wxtrade/findInvitationCd", "", getInvitationCd);
    ajaxRequest(contextPath + "wxtrade/getRiskEvaluationById", "mobile="+mobile,setRiskEvaluationById,"GET");
    function setRiskEvaluationById(data){
        isRiskEvaluation=data.isRiskEvaluation;
        riskType=data.riskType;
        if(isRiskEvaluation=="N"){
           $("#setRiskEvaluation").click(function(){
               window.location.href = contextPath + "wxtrade/goRiskEvaluationEntrance?channel=LBWX&flag=fromSetting";
           })
        }else if(riskType!=""&&riskType!=null){
            $("#riskType").html(riskType);
            $("#setRiskEvaluation").click(function(){
               window.location.href = contextPath + "wxtrade/goRiskEvaluationResult?channel=LBWX&flag=fromSetting";
            })
        }else{
            // errorMessage(data.resmsg_cn)
        }
       
    }
});
//弹窗点击取消按钮
$("#cancel").click(function(event) {
    $("#tipBox").stop().hide();
});

//请求接口取出数据----客服电话
var PhoneCall;

function getServiceCall(){
	ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
		if(res.rescode=='00000'){
			PhoneCall = res.content.mobile;
			$('.JsPhoneCall').html(PhoneCall);
			$('.JsPhoneCallTell').attr('href','tel:'+PhoneCall);
		}
	});
}
getServiceCall();