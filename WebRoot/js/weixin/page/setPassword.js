var password = new Array;
var passwordOne = "";
var passwordTwo = "";
var passwordState = 2; // 1.使用密码 2.设置密码
var passwordStep = 1; // 1.第一次输入 2.第二次输入
var showError = function(txt) {
    if (txt != null && txt != "") {
        $("#promptArea").css("color", "red").html(txt).animate({
            marginLeft: '-10px'
        },
        100).animate({
            marginLeft: '10px'
        },
        100).animate({
            marginLeft: '-10px'
        },
        100).animate({
            marginLeft: '0px'
        },
        100,
        function() {
            promptAreaInitialization();
        });
    } else {
        $("#promptArea").animate({
            marginLeft: '-10px'
        },
        100).animate({
            marginLeft: '10px'
        },
        100).animate({
            marginLeft: '-10px'
        },
        100).animate({
            marginLeft: '0px'
        },
        100);
    }

};
var passwordCheck = function() { //审核提交的密码
    var ss = "";
    for (i = 0; i < password.length; i++) {
        ss += password[i];
    }
    if (passwordState == 1) { //1.使用密码 
    } else if (passwordState == 2) { //2.设置密码
        if (passwordStep == 1) {
            passwordOne = ss;
            passwordStep = 2;
            promptAreaInitialization();
            return false;
        } else if (passwordStep == 2) {
            passwordTwo = ss;
            if (passwordOne != passwordTwo) {
                passwordStep = 1;
                showError("两次输入的密码不一致！");
                return false;
            } else {
                return true;
            }
        }
    }
}
var promptAreaInitialization = function() { // 文案初始化
    $("#promptArea").css("color", "black");
    if (passwordStep == 1) {
        $("#promptArea").html("请输入六位交易密码");
    } else if (passwordStep == 2) {
        $("#promptArea").html("请再次输入六位交易密码");
    }
    password = new Array;
}
var inputPassword = function(price) { //显示输入密码
    if (price == "enter") {
        if (password.length == 6) {
            //提交
            if (passwordCheck()) {
                $("#passwordCash").val(passwordTwo);
                $("#setMessageForm").submit();
            }
        } else {
            showError("密码不能小于六位！");
        }
        return false;
    }
    if (price == "reture") {
        if (password.length > 1) {
            password.pop(); //删除
        } else {
            promptAreaInitialization();
        }
    }
    if (price >= 0 && price <= 9) {
        if (password.length < 6) {
            password.push(price); //正常数字输入
        } else {
            showError();
        }
    }
    if (password.length <= 6 && password.length > 0) { //输入1到6位数字
        var ss = "";
        for (i = 1; i <= password.length; i++) {
            ss += "*";
        }
        $("#promptArea").html(ss);
    }
}
$$(".keyboard li a").tap(function() {
    //$(this).addClass("hover");
    inputPassword($(this).attr("keyboard"));
});
$(document).ready(function() {
    promptAreaInitialization();
});