$(document).ready(function () {

    if (message) {
        errorMessage(message);
    }
    var mobileNumber = $("#mobileNumber").val();
    var channel = $("#channel").val();
    if (mobileNumber !== "" && mobileNumber !== null && mobileNumber !== "null") {
        $("#phoneNum").val(mobileNumber);
        if (channel == "PHICOMM") {
            $("#phoneNum").after("<input type=\"hidden\" name=\"phoneNum\" id=\"phoneNumHide\" value=\"\">");
            $("#phoneNumHide").val(mobileNumber);
            $("#phoneNum").attr("disabled", "true")
        }
        $("#phoneMask").show();
    }

    $("#imgcode").click(function () {
        var me = $(this);
        me.attr("src", me.attr("src").replace(/\?.*/ig, "") + "?" + Math.random());
    });

    var devicdHeight = document.documentElement.clientHeight + "px";

    $("#phoneNum").on("click", function () { //解决android手机上软键盘挤压布局
        $(".teamWrapper").css({
            "height": devicdHeight,
            "backgroundSize": "100% " + devicdHeight
        });
    });
    $("#checkCode").on("click", function () { //解决android手机上软键盘挤压布局
        $(".teamWrapper").css({
            "height": devicdHeight,
            "backgroundSize": "100% " + devicdHeight
        });
    });

});
//读取cookie
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
//console.log('USER_SESSION',getCookie("USER_SESSION"));
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
//  var verifycode = $("#verifycode").val();
//  if (verifycode == "" || verifycode == null) {
//      errorMessage("请输入图形验证码");
//      return false;
//  }
    //网易云盾
    if (verificationStatus) {
        alertVerification('', phoneNum, 'wxlogin');
    } else {
        $('#Verification').show();
        $("#verificationStatus").html('');
    }
});
//登录
$("#loginSubmit").click(function () {
    if (!loginSubmitSwitch) {
        if (!(/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
            errorMessage("请输入正确的手机号码");
            return false;
        }

        if (!$("#checkCode").val()) {
            errorMessage("请输入短信验证码");
            return false;
        }
        return false;
    }
    if (!$("#phoneNum").val()) {
        errorMessage("请输入手机号");
        return false;
    }
    if (!(/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
//  if (!$("#verifycode").val()) {
//      errorMessage("请输入正确的图形验证码");
//      return false;
//  }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    var phoneNum = $('#phoneNum').val(), checkCode = $('#checkCode').val();
    var strData = 'phoneNum=' + phoneNum + '&loginFlag=2' + '&checkCode=' + checkCode;

    //$("#loginForm").submit();
    //
    ajaxRequest(contextPath + "/user/ajaxLogin", strData, ajaxLogin); //登录
});
//登录回调
function ajaxLogin(data) {
    console.log(data);
    if (data.rescode == "00000") { //登录成功需要判断是加入战队还是创建战队
        //alert(getCookie("USER_SESSION"));
        //userId = getCookie("USER_SESSION");
        if (jionTeam == "jionTeam") { //加入战队

            ajaxRequest(contextPath + "/team/queryUserIsJoinTeam", "userId=", function (data) { //组队投资查询用户是否已加入战队
                console.log('queryUserIsJoinTeam =>', data);
                if (data.rescode == "00000") {

                    if (data.isJoinTeam == "Y") { //已加入过战队
                        teamId = data.teamId;
                        if (teamId == shareTeamId) { //属于该战队
                            errorMessage(data.resmsg_cn);
                            window.location.href = contextPath + "/team/goTeamDetail?teamId=" + teamId; //战队详情页
                        } else { //其他战队
                            ajaxRequest(contextPath + "/team/joinTeam", "userId=" + "&teamId=" + teamId + "&teamName=" + teamName, jionTeamData);
                        }

                    } else { //未加入过战队
                        teamId = shareTeamId;
                        ajaxRequest(contextPath + "/team/joinTeam", "userId=" + "&teamId=" + teamId + "&teamName=" + teamName, jionTeamData); //加入战队
                    }
                } else {
                    errorMessage(data.resmsg_cn);
                }

            });

        } else if (creatTeam == "creatTeam") { //创建战队

            ajaxRequest(contextPath + "/team/queryUserIsJoinTeam", "userId=", function (data) { //组队投资查询用户是否已加入战队
                console.log('queryUserIsJoinTeam =>', data);
                if (data.rescode == "00000") {

                    if (data.isJoinTeam == "Y") { //已加入过战队
                        teamId = data.teamId;
                        if (teamId == shareTeamId) { //属于该战队
                            errorMessage(data.resmsg_cn);
                            window.location.href = contextPath + "/team/goTeamDetail?teamId=" + teamId; //战队详情页
                        } else { //其他战队
                            ajaxRequest(contextPath + "/team/creatTeam", "userId=", creatTeamData); //创建战队
                        }

                    } else { //未加入过战队

                        ajaxRequest(contextPath + "/team/creatTeam", "userId=", creatTeamData); //创建战队
                    }
                } else {
                    errorMessage(data.resmsg_cn);
                }

            });
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
        window.location.href = contextPath + "/team/goTeamDetail?teamId=" + teamId ; //战队详情页
    } else {

        errorMessage(data.resmsg_cn);
    }
}

var InterValObj;
function goGetCaptcha(data) { //网易易盾回调
    //网易易盾
    $('#Verification').hide();
    verificationStatus = false;

    if (data.rescode != "00000") {
//      var me = $("#imgcode");
//      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        ins.refresh();
        errorMessage(data.resmsg_cn);
        return;
    }

    gainCodeSwitch = false;
    var curCount = 60;

    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重发验证码");
            $("#gainCode").removeClass("forbid");
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
function confirmFormSubmit() {
    $("#confirmForm").submit();
}
function cancelFormSubmit() {
    $("#confirmForm").submit();
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

