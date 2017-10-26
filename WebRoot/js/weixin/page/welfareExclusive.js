var oldDataArr=[];
$(document).ready(function(){
	$(".know,.cancleEx").click(function(){
		$(".alertBox").addClass("none");
		$(document.body).removeClass("positionF")
	})
	$("#check").click(function(){
		
		ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawRecord","mobile=" +mobile+ "&channel="+channel+"&current=1&pageSize=1000",pointLuckDrawRecord,"GET");
		
	})

	var mobile = $("#mobile").val();
	var channel = $("#channel").val();
	var ua = navigator.userAgent.toLowerCase();
	$("#drawImg,.goExchange").click(function(){
        $("#alertBox").addClass("none");
		if(channel == "LBAndroid"){
			if(mobile == "" || mobile == null || mobile == "null"){
				if(window._cordovaNative){
				window._cordovaNative.goLogin();
			}		
		}
		}
		else if(channel == "LBIOS") {	
		if(mobile == "" || mobile == null || mobile == "null"){
			goLogin();			
		}
	}
		if(!isDraw) return;
			ajaxRequest(contextPath + "wxluckdraw/pointLuckDraw","mobile=" +mobile+ "&channel="+channel,pointLuckDraw,"GET");

	});
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawAwardList","mobile=" +mobile+ "&channel="+channel,pointLuckDrawAwardList,"GET");
	}else if(channel == "LBAndroid") {	
		if(mobile == "" || mobile == null || mobile == "null"){
			ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawAwardList","mobile=" +mobile+ "&channel="+channel,pointLuckDrawAwardList,"GET");
				
		}else{
			ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawAwardList","mobile=" +mobile+ "&channel="+channel,pointLuckDrawAwardList,"GET");
		}			
	}else if(channel == "LBIOS") {	
		ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawAwardList","mobile=" +mobile+ "&channel="+channel,pointLuckDrawAwardList,"GET");
		if(mobile == "" || mobile == null || mobile == "null"){
						
		}else{
			ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawAwardList","mobile=" +mobile+ "&channel="+channel,pointLuckDrawAwardList,"GET");					
		}	
	}else {
		ajaxRequest(contextPath + "wxluckdraw/pointLuckDrawAwardList","mobile=" +mobile+ "&channel="+channel,pointLuckDrawAwardList,"GET");
	}


})
var isDraw=true;
var a = 0;
// 优化： 在原有需求基础上新增弹框提示
//判断用户在每月18号当天第一次进入详情页时，首先弹框提示用户，抽奖需要消耗积分。
//第二次及多次进入详情页时，不需要再弹框提示
var dayTime = new Date();
var nowTime = dayTime.getDate();   // 获取当前是某年某月的某号  判断是否是 18号
var inTimes = parseInt(localStorage.getItem("inTimes"));
//awards:奖项，angle:奖项对应的角度，text:文本内容
var rotateFunc = function(angle,text){ 
	var b = angle+1440+a;
    document.getElementById('welfareExclusive').setAttribute("style","-webkit-transform: rotate("+b+"deg);transform: rotate("+b+"deg)");
    a += 1440; 
    isDraw=false;
    setTimeout(rotateAlert,2300);
    function rotateAlert(){
    	isDraw=true;
    	$("#congratulationsWrap").removeClass("none");
    	$("#congratulationsWrapContent").html(text)
    	return false;	
    }
};
(function rem(x){
	var width=document.documentElement.clientWidth;
	if(parseInt(width)>=600) width=600;
	document.documentElement.style.fontSize=width/x+"px";
	window.addEventListener("resize",setRenSize,false);
	function setRenSize(){
	document.documentElement.style.fontSize=width/x+"px";
	}
	 
})(7.5);
function pointLuckDrawAwardList(data){//展示
	if(data.rescode=="00000"){
		var oDiv=document.getElementById("welfareExclusiveUl");
		var oSpan=oDiv.getElementsByTagName("span");
		var oImg=oDiv.getElementsByTagName("img");
		var dataN=data.list;
		$("#alertBoxContent").html('你确定要抽奖吗？<br/>抽奖一次，消耗'+data.needPoint+'积分');
        if(nowTime == 18 && inTimes != 2){
            localStorage.setItem("inTimes","2");  // 当天第一次进入这个页面
            $(".Ikonw").hide();
            $("#alertBox").removeClass("none");
        }else if(nowTime != 18){
            localStorage.removeItem("inTimes");  // 当天第一次进入这个页面
        }
		$("#current").html(data.usablePoint)
		for(var i=0;i<dataN.length;i++){
			oldDataArr.push(dataN[i].id);
			oSpan[i].innerHTML=dataN[i].awardDesc;
			oImg[i].setAttribute("src",'//'+dataN[i].awardImg);
		}
		console.log(oldDataArr);
	}
	else{
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	
			}
			else if(data.errorMessage.message_level == "2") {
				alertWrap(data.errorMessage.message_content)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}
}
function pointLuckDraw(data){//抽奖
	if(data.rescode=="00000"){
		var oIndex=oldDataArr.indexOf(data.id);
		var num=oIndex*-45;
		var text="恭喜您，获得"+data.awardName;
		$("#current").html(data.usablePoint)
		rotateFunc(num,text)
	}
	else{
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
        	$("#alertBoxContent").html(data.resmsg_cn);
            $(".Ikonw").show();
            $(".btnWrap").hide();
        	$("#alertBox").removeClass("none");
		}
		else{
			if(data.errorMessage.message_level == "1"){		
				$("#alertBoxContent").html(data.errorMessage.message_content)
                $(".Ikonw").show();
                $(".btnWrap").hide();
	        	$("#alertBox").removeClass("none");
			}
			else if(data.errorMessage.message_level == "2") {
				alertWrap(data.errorMessage.message_content)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}
}
function pointLuckDrawRecord(data){//中奖类表
	
	$("#WinnersUl").empty();
	var html='';
	if(data.pageObject['pageNum']==1||data.pageObject['pageNum']=='1'){
		html += '<li class="clearfix"><div class="width49 inlineB fl  Winning" >中奖时间</div><div class="width49 inlineB fl  Winning">中奖奖项</div></li>';
	}
	if(data.rescode=="00000"){	
		if(data.listSize==0){
			setTimeout(function(){
				errorMessage('暂无中奖记录')
			},200);
			return false;
		}
			$.each(data.list, function(i, jsonObj) {
				html += '<li class="clearfix font12"><div class="width49 inlineB fl  Winning" >'+jsonObj.awardTime+'</div><div class="width49 inlineB fl  Winning">'+jsonObj.awardName+'</div></li>'		
			})
		$("#WinnersUl").append(html); 
		$("#WinnersWrap").removeClass("none");
		$(document.body).addClass("positionF");
	}
	else{
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	
			}
			else if(data.errorMessage.message_level == "2") {
				alertWrap(data.errorMessage.message_content)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}
}
