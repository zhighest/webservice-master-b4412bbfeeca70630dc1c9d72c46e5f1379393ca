var password = new Array;
var xFunction;
var promptAreaInitialization = function() { // 文案初始化
    $("#promptArea").css("color", "black");
    $("#promptArea").html("请输入交易密码");
    password = new Array;
}
var usePassword = function(sFunction) {
	xFunction = sFunction
	promptAreaInitialization();
	$("#usePassword").animate({top: '0'}, "fast");
}
var showError = function(txt){
    if(txt != null && txt != ""){
        $("#promptArea").css("color","red").html(txt).animate({
            marginLeft: '-10px'
        },100).animate({
            marginLeft: '10px'
        },100).animate({
            marginLeft: '-10px'
        },100).animate({
            marginLeft: '0px'
        },100,function(){
            promptAreaInitialization()
        });
    }else{
        $("#promptArea").animate({
            marginLeft: '-10px'
        },100).animate({
            marginLeft: '10px'
        },100).animate({
            marginLeft: '-10px'
        },100).animate({
            marginLeft: '0px'
        },100);
    }
};
var inputPassword = function(price) { //显示输入密码
    if (price == "enter") {
        if (password.length == 6) {
            //提交
            var ss = "";
		    for (i = 0; i < password.length; i++) {
		        ss += password[i];
		    }
			$("#usePassword").animate({top: '100%'}, "fast");
			xFunction(ss);
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
$(document).ready(function(){
	$$(".keyboard li a").tap(function() {
    	inputPassword($(this).attr("keyboard"));
	});
});