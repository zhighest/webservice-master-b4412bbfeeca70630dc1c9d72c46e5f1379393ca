var maxNumber;
var setMaxrollNum = function(data) { 
    //var maxNumber = accAdd(data.accountDetail.currentAmount,data.accountDetail.currentIncome);
    maxNumber = data.accountDetail.currentAmount;
//     $("#inputRollNum").attr("placeholder","本次最多可转出"+numFormat(maxNumber)+"元");
    $("#rollNum").html(numFormat(maxNumber));
    
    $("#insideInputBtn").click(function(event) {
        $("#inputRollNum").val(maxNumber);  
    });
};

var getMaxrollNum = function() { 
    ajaxRequest(contextPath + "wxdeposit/queryMyAccountDetail", "", setMaxrollNum);
}

var setRollNum = function(data){
    if(data.rescode == "00000"){
        errorMessage("转出成功");
        function goUrl(){
            window.location.href=contextPath+"wxproduct/goDemandProductIndex?fixDemandSwitch=demand";
        }
        setTimeout(goUrl,3000);
    }else{
        errorMessage(data.resmsg_cn);
        setTimeout(function(){
            window.location.href=contextPath+"wxdeposit/goTransfer";
        },1000);
    }
}

var getRollOut2 = function(passwordCash){
    // 输入提现密码方法调用
    //var passwordCash = prompt("请输入提现密码：");
    if (passwordCash == null || passwordCash == "") {
        errorMessage("提现密码为空！请重新输入！");
    } else {
        var inputRollNum = $("#inputRollNum").val();
        var token = $("#token").val();
        ajaxRequest(contextPath + "wxdeposit/trunOut", {
            "passwordCash": passwordCash,
            "token": token,
            "turnOutAmount": inputRollNum,
            "from":'LBWX'
        },setRollNum);
    }
}

var getRollOut = function(){
    var inputRollNum = $("#inputRollNum").val();
    if(inputRollNum == 0){
        errorMessage("输入的金额不能为0");
        return false;
    }else if(maxNumber < 0 || maxNumber == 0){
        errorMessage("可转出金额为0");
        return false;
    }
    usePassword(getRollOut2);
}
$("#inactiveBtn").click(function(event) {
    var inputRollNum=$("#inputRollNum").val(); 
    if(inputRollNum>maxNumber){
        $("#inputRollNum").val(maxNumber); 
    }else{
        getRollOut();  
    }
});
$(document).ready(function() {
   getMaxrollNum();

   
//初始化键盘
     $('#KeyboardWrap').css({
        'position':"fixed",
        'zIndex':"1000",
        'bottom':"-120%",
        'display':'none',
        'width':'100%',
        'background':'#ffffff'
    });
    $(".main").css({
        '-webkit-tap-highlight-color':'rgba(0,0,0,0)'
    })
    $("#inputRollNum").click(function(ev) {
        $('.rollOutBtn').addClass('none');
        var ev=ev||event
        ev.stopPropagation();
         $('#KeyboardWrap').css({"display":"block"}).animate({
            'bottom':"0px"
        },100,function(){
             $(".main").on('click',function(event) {
               $('#KeyboardWrap').animate({
                 'bottom':"-120%"
                 });
               $('.rollOutBtn').removeClass('none');
               $(this).off('click')
              });
        });
            
    });
});


//自定义键盘

var xFunction;
var input = function(price) { // 数字初始化
    var num = $("#inputRollNum").val() + price;
    $("#inputRollNum").val(num);
    $.checkLimit($("#inputRollNum"),$("#inputRollNum").attr("minNumber"),false);
}
var inputNumber = function(price) { //显示输入数字
    if (price == "reture") {
        var num = $("#inputRollNum").val();
        num = num.substring(0,num.length-1);
        $("#inputRollNum").val(num);
        $.checkLimit($("#inputRollNum"),$("#inputRollNum").attr("minNumber"),false);
    }
    if (price == ".") {
        input(price)
    }
    if (price >= 0 && price <= 9) {
        input(price)
    }
}   
$(document).ready(function(){
    FastClick.attach(document.body);
     $$("#numberKeyboard li:not(.not)").tap(function() {
        inputNumber($(this).attr("keyboard"));
        var inputRollNum=$("#inputRollNum").val(); //判断输入金额是否大于可转金额
        var reg=/^[0].$/g;
        console.log(inputRollNum)
        if(reg.test(inputRollNum)){
              var inputRollNum=inputRollNum.replace('0','0.')
             $("#inputRollNum").val(inputRollNum); 
        }
       if(inputRollNum>maxNumber){
        $("#inputRollNum").val(maxNumber); 
    }
    });
    $$("#numberKeyboard #keyboardReturn").tap(function() {
         inputNumber($(this).attr("keyboard"));
      })
    $$("#keyboardPoint").tap(function() {
         inputNumber($(this).attr("keyboard"));
      })
})


//自定义键盘   点击键盘确认键
$("#keyboardConfirm").click(function(){
    $("#KeyboardWrap").stop().slideUp(200);
});