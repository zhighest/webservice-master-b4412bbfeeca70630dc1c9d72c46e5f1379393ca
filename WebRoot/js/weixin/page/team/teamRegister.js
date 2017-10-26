var checkBoxArea;
$(document).ready(function () {
    if (message) {
        errorMessage(message);
    }

});
//网易易盾变量
var ins;
var verificationStatus = true;

var gainCodeSwitch = true;
var loginSubmitSwitch = false;

//加入/创建战队参数
var userId;
var shareTeamId = $("#teamId").val(); //分享过来的teamId
var teamName = $("#teamName").val();
var channel = $("#channel").val();
var jionTeam = $("#jionTeam").val();
var creatTeam = $("#creatTeam").val();
var invitationCode = $("#invitationCode").val(); //邀请码，手机号
var teamId;//战队详情接口参数

//获取验证码
$("#gainCode").click(function () {
    if (!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }

    if (!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }

    //网易云盾
    if (verificationStatus) {
        alertVerification('', phoneNum, 'register');
    } else {
        alertVerification('', phoneNum, 'register', true);
        $("#verificationStatus").html('');
    }
});
//注册
$("#loginSubmit").click(function () {
    var phoneNum = $('#phoneNum').val();//手机号
    var channel = $('#channel').val();
    var checkCode = $('#checkCode').val();//验证码
    var password = $("#password").val(); //登录密码
    var passwordCash = $('#passwordCash').val(); //交易密码
    var strData = "phoneNum=" + phoneNum + "&channel=" + channel + "&captcha=" + checkCode + "&password=" + password + "&passwordCash=" + passwordCash + "&invitationCode=" + invitationCode + "&isLogin=Y";

    // var passwordAgin = $("#passwordAgin").val();
    if (!loginSubmitSwitch) {

        if (!phoneNum) {
            errorMessage("请输入手机号");
            return false;
        }
        if (!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
            errorMessage("请输入正确的手机号码");
            return false;
        }

        if (!$("#checkCode").val()) {
            errorMessage("请输入短信验证码");
        }

        return false;
    }
    if (!phoneNum) {
        errorMessage("请输入手机号");
        return false;
    }
    if (!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }

    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    //密码验证
    if (password == '' || password == null) {
        errorMessage("请输入登录密码");
        return false;
    }
    ;
    if (password.length < 8) {
        errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    }
    ;
    if (/^[\d]+$/.test(password) || /^[a-zA-Z]+$/.test(password)) {
        errorMessage('您当前设置的密码等级低，存在一定风险，请重新设置');
        return false;
    }
    ;
    if (!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(password))) {
        errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    }

    //登录密码验证
    if (passwordCash == null || passwordCash == '') {
        errorMessage('请设置交易密码');
        return false;
    }
    var reg = /^\d{6}$/;
    if (!reg.test(passwordCash)) {
        errorMessage('交易密码为6位数字');
        return false;
    }

    ajaxRequest(contextPath + "/wxlanding/register", strData, getRegister);//注册

    /*if (!checkBoxArea) {
     errorMessage("请仔细阅读并同意注册协议");
     return false;
     }*/
    //$("#loginForm").submit();
});
//注册成功回调
function getRegister(data) {
    console.log("getRegister=>", data);
    if (data.rescode == '00000') {
        //userId = data.userId;
        if (jionTeam == "jionTeam") { //加入战队

            teamId = shareTeamId;
            ajaxRequest(contextPath + "/team/joinTeam", "teamId=" + teamId + "&teamName=" + teamName, jionTeamData); //加入战队

        } else if (creatTeam == "creatTeam") { //创建战队

            ajaxRequest(contextPath + "/team/creatTeam", "", creatTeamData); //创建战队

        }
    } else {
        errorMessage(data.resmsg_cn);
    }
}

function jionTeamData(data) { //加入战队
    console.log('jionTeam =>', data);
    if (data.rescode == "00000") {
        errorMessage(data.resmsg_cn);

        //window.location.href = contextPath + "/team/goTeamDetail?userId=928652071&teamId=100001"; //战队详情页
        window.location.href = contextPath + "/team/goTeamDetail?teamId=" + teamId; //战队详情页
    } else {
        errorMessage(data.resmsg_cn);
    }
};

function creatTeamData(data) { //创建战队
    console.log('creatTeam =>', data);
    if (data.rescode == "00000") {

        errorMessage(data.resmsg_cn);

        teamId = data.team.id;
        window.location.href = contextPath + "/team/goTeamDetail?teamId=" + teamId; //战队详情页
    } else {

        errorMessage(data.resmsg_cn);
    }
}

//发送验证码回调事件
var InterValObj;
function goGetCaptcha(data) {
    //网易易盾
    $('#Verification').remove();
    verificationStatus = false;

    if (data.rescode != "00000") {
//      var me = $("#imgcode");
//      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        errorMessage(data.resmsg_cn);
        ins.refresh();
        return;
    }
    gainCodeSwitch = false;
    var curCount = 60;

    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重发验证码");
            ins.refresh();
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
            $("#gainCode").addClass("forbid");
        }
    }

    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
}

function checkC() {
    var checkCode = $("#checkCode").val();
    if (checkCode == "") {
        $("#loginSubmit").addClass("forbid");
        loginSubmitSwitch = false;
    } else {
        $("#loginSubmit").removeClass("forbid");
        loginSubmitSwitch = true;
    }
}

//协议跳转
$("#register").click(function () {
    ajaxRequest(contextPath + "/wxagreement/getServAgreementByType", "type=register&productId=107", setServAgreementByType, "GET");
})
var setServAgreementByType = function (data) {
    var u = navigator.userAgent,
        app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var serAgreementUrl = data.serAgreementUrl;
    var flag = serAgreementUrl.indexOf(";");
    serAgreementUrl2 = serAgreementUrl.substring(0, flag);
    serAgreementUrl3 = serAgreementUrl.substring(flag + 1);
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
        if (isiOS) {
            window.location.href = serAgreementUrl2;
        } else {
            window.location.href = serAgreementUrl3;
        }
    }
};