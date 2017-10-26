var xFunction;
var input = function(price) { // 数字初始化
	var num = $("#inputAmount").val() + price;
	$("#inputAmount").val(num);
	$.checkLimit($("#inputAmount"),$("#inputAmount").attr("minNumber"),true);
	calculateEarnings();
}
var inputNumber = function(price) { //显示输入数字
    if (price == "reture") {
	    var num = $("#inputAmount").val();
        num = num.substring(0,num.length-1);
        $("#inputAmount").val(num);
        $.checkLimit($("#inputAmount"),$("#inputAmount").attr("minNumber"),false);// 这里传false，是因为当为0.00 时可以删除了，如果为false就不能删除了
        calculateEarnings();
    }
    if (price == ".") {
	    input(price)
    }
    if (price >= 0 && price <= 9) {
        input(price)
    }
}	
$(document).ready(function(){  
	$$("#numberKeyboard li").tap(function() {
    	inputNumber($(this).attr("keyboard"));
	});
});

//自定义键盘   点击键盘确认键
$("#keyboardConfirm").click(function(){
	$(".wrap").removeClass("height100P");
    $("#momeyBox").stop().slideUp(200);
    $('#goPay').css("margin-bottom","0px"); 
});
//去除active
$("#numberKeyboard").on("click","li",function(){
	var confirm=$(this).attr("keyboard");
	if(!(confirm=="confirm")){
		$("#shortcut li").removeClass("active");
	}
})