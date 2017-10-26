$(document).ready(function() {
	FastClick.attach(document.body);
    var channelName=$('#channel').val();
    ajaxRequest(contextPath + "/channel/channelDetailImg", "channelEnName="+channelName, getImfor);
    if(channelName=="weihui") {
        $("#btmAreaFix").hide();
    }
    if (message) {
        errorMessage(message);
    }
    var mobileNumber = $("#mobileNumber").val();
    if(mobileNumber !== "" && mobileNumber !== null){
        $("#phoneNum").val(mobileNumber);
        $("#phoneMask").show();
    }
    
   $("#imgcode").click(function(){
       var me = $(this);
       me.attr("src",me.attr("src").replace(/\?.*/ig,"")+"?"+Math.random());
   });
   $(".newimg>a").click(function(){
       var top=$("#loginArea").offset().top;
        $("html,body").stop().animate({"scrollTop":""+top+"px"},500)
       
   })
   $(window).scroll(function(){
     　　var scrollTop = $(this).scrollTop(),
     　　 scrollHeight = $(document).height(),
     　　windowHeight = $(this).height();
     　　if(scrollTop + windowHeight == scrollHeight){
     　　　$("#arrowDown").css("display","none");
     　　}
     });
});

//网易易盾变量
var ins;
var verificationStatus = true;

var gainCodeSwitch = true;
var loginSubmitSwitch = false;
var traderPassword;
// var repetition;
var password;
// var repetPassWord;
var phoneNum;
var verifycode;
var checkCode;
$("#gainCode").click(function() {
    if (!gainCodeSwitch) {
        return false;
    }
    phoneNum = $("#phoneNum").val();
//  traderPassword = $("#traderPassword").val();//交易密码
//  repetition = $("#repetition").val();//确认交易密码
    password = $("#passWord").val();//登录密码
//     repetPassWord = $("#repetPassWord").val();//确认登录密码
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    //登录密码判断
    if(password == '' || password == null){
        errorMessage("请输入登录密码");
        return false;
    };
    if(password.length<8){
    	errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    };
	if(/^[\d]+$/.test(password)||/^[a-zA-Z]+$/.test(password)) {
		errorMessage('您当前设置的密码等级低，存在一定风险，请重新设置');
		return false;
	};
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(password))){
        errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    }
   
//  var verifycode = $("#verifycode").val();
//  if (verifycode == "" || verifycode == null) {
//      errorMessage("请输入图形验证码");
//      return false;
//  }
  //网易云盾
	if(verificationStatus){
		alertVerification('',phoneNum,'register');
	}else{
		alertVerification('',phoneNum, 'register',true);
		$("#verificationStatus").html('');
	}
});
$("#loginSubmit").click(function() {
    var channel=$('#channel').val();
    phoneNum = $("#phoneNum").val();
    password = $("#passWord").val();//登录密码
    checked=$('#Agreement').prop("checked");//复选框状态
 // repetPassWord = $("#repetPassWord").val();//确认登录密码
//  traderPassword = $("#traderPassword").val();交易密码
//  repetition = $("#repetition").val();//确认交易密码
    verifycode = $("#verifycode").val();
    checkCode = $("#checkCode").val();

    if (!loginSubmitSwitch) {
        loginSubmitSwitch = false;
    }
    if (!$("#phoneNum").val()) {
        errorMessage("请输入手机号");
        return false;
    }
    if (! (/^((13|15|18|14|17)[0-9]{9})$/.test($("#phoneNum").val()))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
   //登录密码判断
   if(password == '' || password == null){
        errorMessage("请输入登录密码");
        return false;
    };
    if(password.length<8){
    	errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    };
	if(/^[\d]+$/.test(password)||/^[a-zA-Z]+$/.test(password)) {
		errorMessage('您当前设置的密码等级低，存在一定风险，请重新设置');
		return false;
	};
    if(!(/(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9\!\@\#\$\%\^\&\*]{8,16})$/.test(password))){
        errorMessage('登录密码为8~16位字母数字或符号的组合');
        return false;
    }
    //图片验证码
//  if (!$("#verifycode").val()) {
//      errorMessage("请输入正确的图形验证码");
//      return false;
//  }
    if (!$("#checkCode").val()) {
        errorMessage("请输入短信验证码");
        return false;
    }
    if(!checkBoxArea){
       errorMessage("请仔细阅读并同意注册协议");
        return false;
    }
    ajaxRequest(contextPath + "/wxlanding/register", "phoneNum=" + phoneNum + "&channel="+channel+"&captcha="+checkCode+"&password="+password, getRegister);
});
var InterValObj;
function goGetCaptcha(data) {
	//网易易盾
    $('#Verification').remove();
    verificationStatus  = false;
	
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
            $("#gainCode").html("重发<br/>验证码");
            $("#gainCode").removeClass("forbid codeSecond");
            ins.refresh();
        } else {
            curCount--;
            $("#gainCode").html(curCount + "s");
            $("#gainCode").addClass("forbid codeSecond");
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
//激活是否成功事件
var URL = $("#URL").val();
function getRegister(data) {
    if(data.rescode == "00000"){
        function goIndex(){
            window.location.href = URL+ "/webabout/download";   
        }
        setTimeout(goIndex, 1000)
        
    }else{
        errorMessage(data.resmsg_cn);
    }
}
/*监听滚动条，显示或者隐藏某元素*/
window.onscroll = function(){
    var t = document.documentElement.scrollTop || document.body.scrollTop; 
    var subBox = document.getElementById( "arrowDown" );//要显示或者隐藏的元素
    if( t <= 1200 ) {
        subBox.style.display = "block";
    } else {
        subBox.style.display = "none";
    }
}
$("#closed").click(function(){
    $("#btmAreaFix").hide();
})

/*活动详情*/
    $('#activityDetailBtn').click(function(){
    $('#activityDetailBkg').show();
    })
    $('#activityDetailBtn2').click(function(){
    $('#activityDetailBkg2').show();
    })
    $('.closeBtn3').click(function(){
     $('.activityDetailBkg').hide();    
    })
    //注册协议
 var checkBoxArea;
 checkBoxArea = true;
    $("#checkBoxArea img").toggle( 
        function () { 
            $(this).attr("src",contextPath+"/pic/weixin/checkbox1.png"); 
            checkBoxArea = false;
        }, 
        function () { 
            $(this).attr("src",contextPath+"/pic/weixin/checkbox2.png");
            checkBoxArea = true;
        } 
    );

    // 接口化
    $(function(){
       
    })
    function getImfor(data){
         if(data.rescode=='00000'){
             advantageTex(data);
             getActivityDetail(data);
           }else{
         }
     };
    function getActivityDetail(data){
    	$("#template2TxtN").html(data.channelWelfareDesc)
        var channelName=$('#channel').val();
        var displayMode=data.displayMode;
        displayMode=displayMode.split("-");
        if(data.wxBackpic2){       	 
        	 $("#template2").removeClass("none");
        }
        else{
        	$("#template2").addClass("none");
        }
        if(displayMode[0]=='1'||displayMode[0]==1){//弹框
        	if(displayMode[1]=='1'||displayMode[1]==1){//shu
        		 $('#activityDetailBtn').show();
        		 showOrHide(true)
        	}
        	else if(displayMode[1]=='2'||displayMode[1]==2){
        		 $('#activityDetailBtn2').show();
        		 showOrHide()
        	}
          
           var detailText=data.channelDesc.split('<br/>');
            var re=/^\s+|\s+$/g;
            var html='';
            $.each(detailText,function(i,obj){
            html+='<li>'+obj.replace(re,'')+'</li>'//生成活动详情
          }) 
            $('.activityTxtList').append(html);
        }else{
           $('#activityDetailBtn').hide(); 
        }
        if(displayMode[0]=='2'||displayMode[0]==2) {//滑动
        	if(displayMode[1]=='1'||displayMode[1]==1){
    		showOrHide(true)
       	}
       	else if(displayMode[1]=='2'||displayMode[1]==2){//heng
       		showOrHide()
       	}
                $('.activityDetailBtn').hide();
                $(".ruleDetails").show();
                var detailText=data.channelDesc.split('<br/>');
                var re=/^\s+|\s+$/g;
                var html='';
                $.each(detailText,function(i,obj){
                html+='<li>'+obj.replace(re,'')+'</li>'//生成活动详情
            })
            $('#activityTxtList1').append(html) 
        } else {
            $(".ruleDetails").hide();
        } 
    }
    function advantageTex(data){
    	var dec='';//不同模板显示的理财优势文字
    	var displayMode=data.displayMode;
        displayMode=displayMode.split("-");
    	if(displayMode[1]=='1'||displayMode[1]==1){//蓝色
    		dec=data.channelPicTextDesc;
    	}
    	else if(displayMode[1]=='2'||displayMode[1]==2){//紫色
    		dec=data.channelPicTextDesc2;
    	}
        var text=dec.split('|');
        var re=/^\s+|\s+$/g;
        $.each(text,function(i,obj){
            $('#imgDivWrap p').eq(i).html(obj.replace(re,''))//理财优势区域文字
        })
      
    }
    function showOrHide(boole){
    	if(boole==true){
    		 $("#activityTxtN").addClass("activityTxt1").removeClass("activityTxt2");
       		 $("#imgDivL1").addClass("none");
       		 $("#imgDivL2").removeClass("none");       		
    	}
    	else{
    		 $("#activityTxtN").addClass("activityTxt2").removeClass("activityTxt1");
       		 $("#imgDivL1").removeClass("none");
       		 $("#imgDivL2").addClass("none");       		 
    	}
    }