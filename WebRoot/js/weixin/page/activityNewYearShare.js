/**
 * Created by huamin on 2017/1/9.
 */

$(".wordTop").addClass("curTop");
setTimeout(function(){
    $(".flowerImg1").addClass("curFlower1");
    $(".flowerImg2").addClass("curFlower2");
},10);
$(".flowerImg4,.flowerImg3,.flowerImg5,.flowerImg6").addClass("curFlower3");
var mySwiper = new Swiper('.swiper-container',{
    direction : 'vertical',
    freeMode : false,
    onSlideChangeStart: function(swiper){
        if(swiper.activeIndex == 0){  //第二页、第四页动画
            $(".wordTop").addClass("curTop");
            $(".flowerImg1").addClass("curFlower1");
            $(".flowerImg2").addClass("curFlower2");
            $(".flowerImg4,.flowerImg3,.flowerImg5,.flowerImg6").addClass("curFlower3");
            $(".slideTop").show();
        }else{
            $(".slideTop").hide();
            $(".wordTop").removeClass("curTop");
            $(".flowerImg1").removeClass("curFlower1");
            $(".flowerImg2").removeClass("curFlower2");
        }
    }
})
//点击删除图标清空对应的input
$(".delete").click(function(){
    $(this).prev().val("");
});



var checkBoxArea;
$(document).ready(function() {
    //已注册和未注册 弹框切换
    var userId = $("#userId").val();
    if(userId){   //  已登陆状态
        $("#step2").css("display","block");
        $(".imgDiv").css({
            "height":"36%",
            "background":"rgba(225,225,225,1)",
            "margin-top":"25%"
        });
    }else{                   //未注册状态
        $("#step1").css("display","block");
        $(".imgDiv").css({
            "height": "72%",
            "background": "rgba(225,225,225,.95)",
            "margin-top": "-8%"
        });
    }

    if (message) {
        errorMessage(message);
    }
    $("#imgcode").click(function(){//图片验证码
        var me = $(this);
        me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
    });
    checkBoxArea = true;
    $(".agree").click(function(){
        if(checkBoxArea){
            $(this).addClass("checkbox1").removeClass("checkbox2");
            checkBoxArea = false;
        }else{
            $(this).addClass("checkbox2").removeClass("checkbox1");
            checkBoxArea = true;
        }
    });
    //增加斐讯渠道
    var mobileNumber = $("#mobileNumber").val();
    var channel = $("#channel").val();
    if(mobileNumber !== "" && mobileNumber !== null && mobileNumber !== "null"){
        $("#phoneNum").val(mobileNumber);
        if(channel =="PHICOMM") {
            $("#phoneNum").after("<input type=\"hidden\" name=\"phoneNum\" id=\"phoneNumHide\" value=\"\">");
            $("#phoneNumHide").val(mobileNumber);
            $("#phoneNum").attr("disabled","true")
        }
        $("#phoneMask").show();
    }

//获取数据
    var mobile = $("#mobile").val();
    ajaxRequest(contextPath + "/platformData/getIncomeData", "mobile=" +mobile+"&shareChannel=1", setShareMessage1);
    function setShareMessage1(data){



        if(data.rescode == "00000"){
            if(data.moneyAssort > 100 || data.moneyAssort == 0 || data.moneyAssort == "0"){    //排名
                var moneyAssort = data.moneyAssortPer + "%";
                var html1 = '超越了全国 <span class="whiteTex font18 moneyAssort">'+moneyAssort+'</span> 的用户';
                $(".userAssort").html(html1);
            }else{
                var moneyAssort = data.moneyAssort;
                var html2 = '收益榜排名 <span class="whiteTex font18 moneyAssort">'+moneyAssort+'</span> 位';
                $(".userAssort").html(html2);
            }
            var registerDay = data.registerDay;   //注册天数
            var moneyAmount = data.moneyAmount;   //赚取多少钱
            var userName = data.userName;  //用户名字
            if(userName == "null" || userName == null || userName == ""){

            }else{
                $(".userName").html(' '+userName.substring(0,1) + '** ');
            }
            $(".registerDay").html(registerDay);
            $(".moneyAmount").html(numFormat(moneyAmount));
        }else{

        }
    }




var gainCodeSwitch = true;
var loginSubmitSwitch = true;
//获取验证码
$("#gainCode").click(function() {
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
    var verifycode = $("#verifycode").val();
    if (verifycode == "" || verifycode == null) {
        errorMessage("请输入图形验证码");
        return false;
    }


    ajaxRequest(contextPath + "/wxuser/goGetCaptcha", "phoneNum=" + phoneNum + "&verifycode="+verifycode, goGetCaptcha);
});
$("#loginSubmit").click(function() {
    var password = $("#password").val();
    var passwordRe = $("#passwordRe").val();  //再次密码
    // var passwordAgin = $("#passwordAgin").val();
    if (!loginSubmitSwitch) {
        return false;
    }
    if (!$("#phoneNum").val()) {
        errorMessage("请输入手机号");
        return false;
    }
    if($("#phoneNum").val().substring(0,3) == "170"){
        errorMessage("170开头的手机号段暂不支持注册，请联系客服");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    if (password == null || password == '') {
        errorMessage("请设置登录密码");
        return false;
    }
    if (password !== passwordRe) {
        errorMessage("两次输入密码不一致，请重新输入");
        return false;
    }
    if (!$("#verifycode").val()) {
        errorMessage("请输入正确的图形验证码");
        return false;
    }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(password))){
        errorMessage('登录密码为6~16位字母数字组合');
        return false;
    }
    // if(passwordAgin == '' || passwordAgin == null){
    //     errorMessage("两次输入密码不一致，请重新输入");
    //     return false;
    // };
    // if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/.test(passwordAgin))){
    //     errorMessage("登录密码为6~16位字母数字组合");
    //     return false;
    // };
    // if(password !== passwordAgin){
    //     errorMessage("两次输入密码不一致，请重新输入");
    //     return false;
    // };
    if(!checkBoxArea){
        errorMessage("请仔细阅读并同意用户服务协议");
        return false;
    }
    $("#loginForm").submit();
});
//发送验证码回调事件
var InterValObj;
function goGetCaptcha(data) {
    if (data.rescode != "00000") {
        var me = $("#imgcode");
        me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());

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

$("#register").click(function(){
    ajaxRequest(contextPath+"/wxagreement/getServAgreementByType","type=register&productId=107",setServAgreementByType,"GET");
})
var setServAgreementByType = function(data){
    var u = navigator.userAgent, app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var serAgreementUrl=data.serAgreementUrl;
    var flag=serAgreementUrl.indexOf(";");
    serAgreementUrl2=serAgreementUrl.substring(0,flag);
    serAgreementUrl3=serAgreementUrl.substring(flag+1);
    if (data.resmsg != "success") {
        errorMessage(data.resmsg);
    } else {
        if(isiOS){
            window.location.href = serAgreementUrl2;
        }
        else{
            window.location.href = serAgreementUrl3;
        }
    }
};

$("#closed").click(function(){   //下载APP
    $("#btmAreaFix").hide();
})

});