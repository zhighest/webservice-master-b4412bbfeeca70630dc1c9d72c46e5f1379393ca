var NetValidate = true;
var code = "";
var qrCodeJson={};
var opts = {
    "element": "captcha_div", // 可以是验证码容器id，也可以是HTMLElement
    "captchaId": "32a42600dad34c6f92dc9b3eb6b47f49", // 这里填入申请到的验证码id
    "width": 340, // 验证码组件显示宽度
    "verifyCallback": function(ret) { // 用户只要有拖动/点击，就会触发这个回调
        if(ret['value']) { // true:验证通过 false:验证失败
            // 通过 ret["validate"] 可以获得二次校验数据
            $("gainCode").removeAttr("disabled"); // 用户完成拖动之后再启用提交按钮
            NetValidate = false;
        }
    }
};
var ins = new NECaptcha(opts);

// ############验证码登录方式 begin #####################
// 1.获取短信验证码   
$("#gainCode").click(function() {
    $("#chc > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    $("#chc").removeClass('inputBoxR').addClass('inputBox');
    $("#checkCode").removeClass('inputFiledR').addClass('inputFiled');
    if(!gainCodeSwitch) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if(phoneNum == "") {
        $("#pn").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNum").addClass('inputFiledR').removeClass('inputFiled');
        $("#pn > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNum").attr("placeholder", "请输入手机号");
        return false;
    }
    if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        $("#pn").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNum").addClass('inputFiledR').removeClass('inputFiled');
        $("#phoneNum").val("");
        $("#pn > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNum").attr("placeholder", "请输入正确的手机号码");
        return false;
    }
    //  $("#pn > label > img").attr("src",contextPath + "pic/web/loginIcon1.png");
    //  $("#pn").removeClass('inputBoxR').addClass('inputBox');
    //  var verifycode = $("#verifycode").val();
    //  if (verifycode == "" || verifycode == null) {
    //      $("#vc").addClass('inputBoxR').removeClass('inputBox');
    //      $("#verifycode").addClass('inputFiledR').removeClass('inputFiled');
    //      $("#vc > label > img").attr("src",contextPath + "pic/web/register/errorIcon.png");
    //      $("#verifycode").attr("placeholder","请输入图片验证码");
    //      return false;
    //  }
    //网易云盾验证
    if(NetValidate) {
        errorMessage("请输入图形验证码");
        return false;
    }
    //最后一次的验证码
    var NECaptchaValidate = ins.getValidate();
    NECaptchaValidate = window.encodeURIComponent(NECaptchaValidate);

    gainCodeSwitch = false;
    ajaxRequest(contextPath + "user/goGetCaptcha", "phoneNum=" + phoneNum + "&type=wxlogin" + "&NECaptchaValidate=" + NECaptchaValidate + "&captchaId=32a42600dad34c6f92dc9b3eb6b47f49", setGoGetCaptcha);

});
//2.点击获取短信验证返回事件
var gainCodeSwitch = true;
var setGoGetCaptcha = function(str) {
    if(str.rescode != "00000") {
        //      var me = $("#imgcode");
        //      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        gainCodeSwitch = true;
        errorMessage(str.resmsg_cn);
        ins.refresh();
        return;
    }

    var curCount = 60;

    function SetRemainTime() {
        if(curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitch = true;
            $("#gainCode").html("重新发送");
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
};
//3.点击图片验证码
$("#imgcode").click(function() {
    var me = $(this);
    me.attr("src", me.attr("src").replace(/\?.*/ig, "") + "?" + Math.random());
});
//4.点击验证码登录按钮
var goLoginBtn = true;
$("#loginSubmit").click(function() {
    if(!goLoginBtn) {
        return false;
    }
    var phoneNum = $("#phoneNum").val();
    if(phoneNum == "") {
        $("#pn").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNum").addClass('inputFiledR').removeClass('inputFiled');
        $("#pn > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNum").attr("placeholder", "请输入手机号");
        return false;
    }
    if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        $("#pn").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNum").addClass('inputFiledR').removeClass('inputFiled');
        $("#phoneNum").val("");
        $("#pn > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNum").attr("placeholder", "请输入正确的手机号码");
        return false;
    }
    //  $("#pn > label > img").attr("src",contextPath + "pic/web/loginIcon1.png");
    //  $("#pn").removeClass('inputBoxR').addClass('inputBox');
    //  var verifycode = $("#verifycode").val();
    //  if (verifycode == "" || verifycode == null) {
    //      $("#vc").addClass('inputBoxR').removeClass('inputBox');
    //      $("#verifycode").addClass('inputFiledR').removeClass('inputFiled');
    //      $("#vc > label > img").attr("src",contextPath + "pic/web/register/errorIcon.png");
    //      $("#verifycode").attr("placeholder","请输入图片验证码");
    //      return false;
    //  }
    $("#vc > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    $("#vc").removeClass('inputBoxR').addClass('inputBox');
    var checkCode = $("#checkCode").val();
    if(!checkCode) {
        $("#chc").addClass('inputBoxR').removeClass('inputBox');
        $("#checkCode").addClass('inputFiledR').removeClass('inputFiled');
        $("#chc > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#checkCode").attr("placeholder", "请输入短信验证码");
        return false;
    }
    goLoginBtn = false;
    ajaxRequest(contextPath + "user/ajaxLogin", "phoneNum=" + phoneNum + "&loginFlag=2" + "&checkCode=" + checkCode, setLogin);
});
//5.点击验证码登录按钮返回事件
var setLogin = function(data) {
    if(data.rescode == "00000") {
        window.location.href = contextPath + "webindex/goIndex";
        goLoginBtn = true;
        //ajaxRequest(contextPath + "wxtrade/showAssets","",setShowAssets);
    } else {
        var me = $("#imgcode");
        //      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        goLoginBtn = true;
        errorMessage(data.resmsg_cn);
    };
};
// ############验证码登录方式 end #####################

// ############密码登录方式 loginFlag=1 begin #####################
//1.点击图片验证码
$("#imgcodePW").click(function() {
    var me = $(this);
    me.attr("src", me.attr("src").replace(/\?.*/ig, "") + "?" + Math.random());
});
//2.点击密码登录按钮
var goLoginBtnPW = true;
$("#loginSubmitPW").click(function() {
    if(!goLoginBtnPW) {
        return false;
    }
    var phoneNum = $("#phoneNumPW").val();
    if(phoneNum == "") {
        $("#pnPW").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNumPW").addClass('inputFiledR').removeClass('inputFiled');
        $("#pnPW > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNumPW").attr("placeholder", "请输入手机号");
        return false;
    }
    if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        $("#pnPW").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNumPW").addClass('inputFiledR').removeClass('inputFiled');
        $("#phoneNumPW").val("");
        $("#pnPW > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNumPW").attr("placeholder", "请输入正确的手机号码");
        return false;
    }
    $("#pnPW > label > img").attr("src", contextPath + "pic/web/loginIcon1.png");
    $("#pnPW").removeClass('inputBoxR').addClass('inputBox');
    var verifycode = $("#verifycodePW").val();
    if(verifycode == "" || verifycode == null) {
        $("#vcPW").addClass('inputBoxR').removeClass('inputBox');
        $("#verifycodePW").addClass('inputFiledR').removeClass('inputFiled');
        $("#vcPW > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#verifycodePW").attr("placeholder", "请输入图片验证码");
        return false;
    }
    $("#vcPW > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    $("#vcPW").removeClass('inputBoxR').addClass('inputBox');
    var pwd = $("#password").val();
    if(pwd == null || pwd == '') {
        $("#pwPW").addClass('inputBoxR').removeClass('inputBox');
        $("#password").addClass('inputFiledR').removeClass('inputFiled');
        $("#pwPW > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#password").attr("placeholder", "登录密码为空");
        return false;
    }
    //原用户可能密码只有6位  暂时不能做登录拦截
//	if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(pwd))) {
//		$("#pwPW").addClass('inputBoxR').removeClass('inputBox');
//		$("#password").addClass('inputFiledR').removeClass('inputFiled');
//		$("#pwPW > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
//		$("#password").attr("placeholder", "请输入8~16位字母数字或符号组合");
//		$("#password").val("");
//		return false;
//	}

    goLoginBtnPW = false;
    ajaxRequest(contextPath + "user/ajaxLogin", "phoneNum=" + phoneNum + "&loginFlag=1" + "&pwd=" + pwd + "&verifycode=" + verifycode, setLoginPW);
});
//3.点击密码登录按钮返回事件
var setLoginPW = function(data) {
    if(data.rescode == "00000") {
        localStorage.setItem("needUpdatePassword",data.needUpdatePassword);
        window.location.href = contextPath + "webindex/goIndex";
    } else { //密码错误
        var me = $("#imgcodePW");
        me.attr("src", me.attr("src").replace(/\?.*/ig, "") + "?" + Math.random());
        goLoginBtnPW = true;
        $("#password").val("");
        errorMessage(data.resmsg_cn);
    };
};
// ############ 密码登录方式 loginFlag=1 end ##################### 
// ############ 用户注册 begin #####################
// 1.获取短信验证码   
$("#gainCodeRG").click(function() {
    $("#chcRG > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    $("#chcRG").removeClass('inputBoxR').addClass('inputBox');
    if(!gainCodeSwitchRG) {
        return false;
    }
    var phoneNum = $("#phoneNumRG").val();
    if(phoneNum == "") {
        $("#pnRG").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNumRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#pnRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNumRG").attr("placeholder", "请输入手机号");
        return false;
    }
    if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        $("#pnRG").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNumRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#phoneNumRG").val("");
        $("#pnRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNumRG").attr("placeholder", "请输入正确的手机号码");
        return false;
    }
    //  var verifycode = $("#verifycodeRG").val();
    //  if (verifycode == "" || verifycode == null) {
    //      $("#vcRG").addClass('inputBoxR').removeClass('inputBox');
    //      $("#verifycodeRG").addClass('inputFiledR').removeClass('inputFiled');
    //      $("#verifycodeRG").val("");
    //      $("#vcRG > label > img").attr("src",contextPath + "pic/web/register/errorIcon.png");
    //      $("#verifycodeRG").attr("placeholder","请输入图片验证码");
    //      return false;
    //  }

    //网易云盾验证
    if(NetValidate) {
        errorMessage("请完成图形验证码");
        return false;
    }
    //最后一次的验证码
    var NECaptchaValidate = ins.getValidate();
    NECaptchaValidate = window.encodeURIComponent(NECaptchaValidate);

    gainCodeSwitchRG = false;
    ajaxRequest(contextPath + "user/goGetCaptcha", "phoneNum=" + phoneNum + "&type=register" + "&NECaptchaValidate=" + NECaptchaValidate + "&captchaId=32a42600dad34c6f92dc9b3eb6b47f49", setGoGetCaptchaRG);

});
//2.点击图片验证码
$("#imgcodeRG").click(function() {
    var me = $(this);
    me.attr("src", me.attr("src").replace(/\?.*/ig, "") + "?" + Math.random());
});
//3.点击获取短信验证返回事件
var gainCodeSwitchRG = true;
var setGoGetCaptchaRG = function(str) {
    if(str.rescode != "00000") {
        //      var me = $("#imgcodeRG");
        //      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        gainCodeSwitchRG = true;
        errorMessage(str.resmsg_cn);
        ins.refresh();
        return;
    }

    var curCount = 60;

    function SetRemainTime() {
        if(curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            gainCodeSwitchRG = true;
            $("#gainCodeRG").html("重新发送");
            $("#gainCodeRG").removeClass("forbid");
            ins.refresh();
        } else {
            curCount--;
            $("#gainCodeRG").html(curCount + "s");
            $("#gainCodeRG").addClass("forbid");
        }
    }
    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
    errorMessage("验证码发送成功");
};

var checkBoxArea = true;  //用户注册服务协议 true 已读
$("#checkBoxArea img").click(function(){
    checkBoxArea = !checkBoxArea;
    if(checkBoxArea){
        $(this).attr("src",contextPath+"pic/weixin/ver2_9/checkbox2.png");
    }else{
        $(this).attr("src",contextPath+"pic/weixin/ver2_9/checkbox1.png");
    }
});

//4.点击注册按钮
var data1 = {};
var goLoginBtnRG = true;
$("#loginSubmitRG").click(function() {

    if(!goLoginBtnRG) {
        return false;
    }
    var phoneNum = $("#phoneNumRG").val();
    if(phoneNum == "") {
        $("#pnRG").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNumRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#pnRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNumRG").attr("placeholder", "请输入手机号");
        return false;
    }
    if(!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        $("#pnRG").addClass('inputBoxR').removeClass('inputBox');
        $("#phoneNumRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#phoneNumRG").val("");
        $("#pnRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#phoneNumRG").attr("placeholder", "请输入正确的手机号码");
        return false;
    }
    //  $("#pnRG > label > img").attr("src",contextPath + "pic/web/loginIcon1.png");
    //  $("#pnRG").removeClass('inputBoxR').addClass('inputBox');
    //  var verifycode = $("#verifycodeRG").val();
    //  if (verifycode == "" || verifycode == null) {
    //      $("#vcRG").addClass('inputBoxR').removeClass('inputBox');
    //      $("#verifycodeRG").addClass('inputFiledR').removeClass('inputFiled');
    //      $("#verifycodeRG").val("");
    //      $("#vcRG > label > img").attr("src",contextPath + "pic/web/register/errorIcon.png");
    //      $("#verifycodeRG").attr("placeholder","请输入图片验证码");
    //      return false;
    //  }
    $("#vcRG").removeClass('inputBoxR').addClass('inputBox');
    $("#vcRG > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    var checkCode = $("#checkCodeRG").val();
    if(!checkCode) {
        $("#chcRG").addClass('inputBoxR').removeClass('inputBox');
        $("#checkCodeRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#chcRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#checkCodeRG").attr("placeholder", "请输入短信验证码");
        return false;
    }
    $("#chcRG > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    $("#chcRG").removeClass('inputBoxR').addClass('inputBox');
    var password = $("#passwordRG").val();
    var passwordAgain = $("#passwordAgain").val();
    if(password == null || password == '') {
        $("#pwRG").addClass('inputBoxR').removeClass('inputBox');
        $("#passwordRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#pwRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#passwordRG").attr("placeholder", "请设置登录密码");
        return false;
    }
    if(password.length<8){
        errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    }
    if(/^[\d]+$/.test(password)||/^[a-zA-Z]+$/.test(password)) {
        errorMessage('您当前设置的密码等级低，存在一定风险，请重新设置');
        return false;
    };
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(password))) {
        $("#pwRG").addClass('inputBoxR').removeClass('inputBox');
        $("#passwordRG").addClass('inputFiledR').removeClass('inputFiled');
        $("#passwordRG").val("");
        $("#pwRG > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#passwordRG").attr("placeholder", "请输入8~16位字母数字或符号组合");
        return false;
    };
    $("#pwRG").removeClass('inputBoxR').addClass('inputBox');
    $("#pwRG > label > img").attr("src", contextPath + "pic/web/loginIcon3.png");
    if(passwordAgain == null || passwordAgain == '') {
        $("#pwAgain").addClass('inputBoxR').removeClass('inputBox');
        $("#passwordAgain").addClass('inputFiledR').removeClass('inputFiled');
        $("#passwordAgain").val("");
        $("#pwAgain > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#passwordAgain").attr("placeholder", "两次输入密码不一致");
        return false;
    }
    if(passwordAgain.length<8){
        errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    }
    if(/^[\d]+$/.test(passwordAgain)||/^[a-zA-Z]+$/.test(passwordAgain)) {
        errorMessage('您当前设置的密码等级低，存在一定风险，请重新设置');
        return false;
    };
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(passwordAgain))) {
        $("#pwAgain").addClass('inputBoxR').removeClass('inputBox');
        $("#passwordAgain").addClass('inputFiledR').removeClass('inputFiled');
        $("#passwordAgain").val("");
        $("#pwAgain > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#passwordAgain").attr("placeholder", "请输入8~16位字母数字或符号组合");
        return false;
    };
    if(password !== passwordAgain) {
        $("#pwAgain").addClass('inputBoxR').removeClass('inputBox');
        $("#passwordAgain").addClass('inputFiledR').removeClass('inputFiled');
        $("#passwordAgain").val("");
        $("#pwAgain > label > img").attr("src", contextPath + "pic/web/register/errorIcon.png");
        $("#passwordAgain").attr("placeholder", "两次输入密码不一致");
        return false;
    }

    if(!checkBoxArea){//用户注册服务协议
        errorMessage("请仔细阅读并同意用户注册服务协议");
        return false;
    }

    $("#pwAgain > label > img").attr("src", contextPath + "pic/web/loginIcon2.png");
    $("#pwAgain").removeClass('inputBoxR').addClass('inputBox');
    goLoginBtnRG = false;
    data1.phoneNum = phoneNum;
    data1.password = password;
    data1.checkCode = checkCode; //解决ajax传输特殊字符的问题
    data1.channel = "WEBSITE";
    //  后台验证密码安全性
    ajaxRequest(contextPath+"/wxuser/loginPasswordCheck","phoneNum="+phoneNum+"&password="+password,passwordLevel,"GET");

});
function passwordLevel(data){
    if(data.rescode == "00000"){
        ajaxRequest(contextPath + "/user/registerPassword", data1, setLoginRG);
    }else{
        errorMessage(data.resmsg_cn);
    }
}
//5.点击注册按钮返回事件
var setLoginRG = function(data) {
    if(data.rescode == "00000") {
        window.location.href = contextPath + "webindex/goIndex";
        //ajaxRequest(contextPath + "wxtrade/showAssets","",setShowAssets);
    } else { //密码错误
        var me = $("#imgcodeRG");
        //      me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
        goLoginBtnRG = true;
        $("#passwordRG").val("");
        errorMessage(data.resmsg_cn);
    };
};
// ############ 用户注册 end #####################

$(document).ready(function() {
	/*轮播*/
    var index = 0;
    var focuslis = $('.focusBtnBox li'); /*焦点li元素集合*/
    var timer; //轮播方法
    var liWidth = $('#logonUL').width(); //420px
    var loginlis = $('#logonUL ul li');
    var len = $("#logonUL ul li").length; //3
    //左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
    $("#logonUL ul").css("width", liWidth * (len));

    //上一张按钮
    $(".goLeft").click(function() {
        clearInterval(timer);
        index -= 1;
        if(index == -1) {
            index = len - 1;
        }
        showPic(index);
        sentEventSouce(index);
    });

    //下一张按钮
    $(".goRight").click(function() {
        clearInterval(timer);
        index += 1;
        if(index == len) {
            index = 0;
        }
        showPic(index);
        sentEventSouce(index);
    });
    //当切换到扫码登录栏时开启后端服务推送监听扫码状态
    function sentEventSouce(index){
        var len = $("#logonUL ul li").length; //3
        if(index==len-1){
            $('#QR_code .QRCodeLogo').next().remove();
            getLoginStatus(qrCodeJson,code,qrCodecheck);
        }
    }
	/*显示index图片*/
    function showPic(index) {
        var nowLeft = -index * liWidth;
        focuslis.eq(index).addClass('selectli').removeClass("noSelectli");
        focuslis.not(focuslis.eq(index)).removeClass('selectli').addClass("noSelectli");
        $("#logonUL ul").stop(true, false).animate({ "left": nowLeft }, 300);
    }
	/*点击焦点区的div显示对应图*/
    focuslis.click(
        function() {
            index = focuslis.index(this);
            showPic(index);
            sentEventSouce(index);
        });
});

var sScripts = document.getElementsByTagName("script");
var oStateNum=0;
//所有的js加载完毕后才执行生成二维码
for(var i=0;i<sScripts.length;i++){
    sScripts[i].onload = sScripts[i].onreadystatechange = function(i) {
        if(!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') {
            oStateNum++;
        }
    }(i)
}
if(oStateNum==sScripts.length){
    generateQRCode();
}
//扫码回调
function qrCodecheck(data) {
    if(data === "SUCCESS") {
        window.location.href = contextPath + "trade/goAccoutOverview";
    }
}
function getLoginStatus(json,code,qrCodecheck){
    $('#QR_code').qrcode(json).request(contextPath + "qrcode/deleteQrCodeForLogin?qrCode=" + code, qrCodecheck);
}
//生成二维码
function generateQRCode() {
    var contextPath = "";
    function getRealPath() {
        //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： myproj/view/my.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.lastIndexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/myproj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        if(projectName.indexOf('shop-webservice') >= 0) {
            contextPath = localhostPaht + projectName + "/";
        } else {
            contextPath = localhostPaht + "/";

        }
        //得到了 http://localhost:8083/myproj
        //contextPath = localhostPaht + projectName + '/';
    }
    getRealPath();
    //			contextPath += 'webservice/'
    //http://222.73.156.45:7080/
    //http://222.73.156.45:7080/webservice/qrcode/deleteQrCodeForL
    var end = new Date();
    end = end.getTime() + 10 * 60 * 1000;
    var codepool = '1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM';
    code = "";
    setTimeout(function() {
        window.location.reload();
    }, 10 * 60 * 1000)
    for(var i = 0; i < 10; i++) {
        code += codepool.charAt(GetRandomNum(0, codepool.length))
    }

    function GetRandomNum(Min, Max) {
        return Math.floor(Math.random() * (Max - Min) + Min);
    }
    //code="qwertyuio1";
    qrCodeJson = '{"type":"login","qrCode":"' + code + '","endDate":"' + end.toString() + '"}'
}

//控制登陆按钮样式   暂时用不到  解决报错问题
var checkD = function () {
//  pwd = $("#password").val();
//  if (pwd == null || pwd == "") {
//      $("#loginSubmitPW").addClass("forbid");
//      goLoginBtnPW = false;
//  } else {
//      $("#loginSubmitPW").removeClass("forbid");
//      goLoginBtnPW = true;
//  }
//  ;
};
//暂时用不到
var checkC = function () {
//  checkCode = $("#checkCodeRG").val();
//  if (checkCode == "") {
//      $("#loginSubmit").addClass("forbid");
//      loginSubmitSwitch = false;
//  } else {
//      $("#loginSubmit").removeClass("forbid");
//      loginSubmitSwitch = true;
//  }
//  ;
};