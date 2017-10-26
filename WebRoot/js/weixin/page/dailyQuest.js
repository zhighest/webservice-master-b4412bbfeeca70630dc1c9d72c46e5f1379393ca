
//点击签到之后刷新页面
var setUserWechatSign = function(data){
	if(data.rescode == "00000"){
		// window.location.reload();//刷新页面
		ajaxRequest(contextPath + "tasklist/getTaskList", "mobile="+ mobile, setSign);
	}
	else {
	    errorMessage(data.resmsg_cn);
	}
}
var goUserWechatSign = function(){//用户签到方法，后台提供
	/*ajaxRequest(contextPath + "wxcoupons/userWechatSign", "", setUserWechatSign);*/
	ajaxRequest(contextPath + "wxcoupons/userWechatSign", "mobile="+newMobile+"&signFlag="+signFlag, setUserWechatSign);
}
//弹窗
function alertRule(data){
	   var re2=/\d+(\.\d+)?%/g;
       var activtyText=data.activtyText.split('<br>');
       var re=/^\s+|\s+$/g;
       var html='';
      $("#dailySignList").children('li').remove();
      $.each(activtyText,function(i,Obj){
      	html+='<li>'+Obj+'</li>'
       });
       $("#dailySignList").append(html);
}
//setQueryUserCurrentSign方法的实现，查询用户签到方法
var score='';
var setQueryUserCurrentSign = function(data) {
	if(data.rescode == "00000"){//接口通的情况
		alertRule(data)
	    if(data.currentSign == "Y"){//判断是否已经签到，如果已签到，则id=signInBtn的按钮样式改变，文字内容改变
	    	if(data.signFlag=="WX"){
               $("#signInBtn").html("已加息+0.2%").addClass('dark');
	    	}else if(data.signFlag=="APP"){
	    	   $("#signInBtn").html("已加息+0.5%").addClass('dark');
	    	}
	    }else if(data.currentSign == "N"){	//如果还没有签到
	    	$("#btnWrap").bind("click", function(){//签到按钮的点击事件
	    	var ua = navigator.userAgent.toLowerCase();//定义一个参数ua存放获取的操作环境
               	if(ua.match(/MicroMessenger/i)=="micromessenger"){//如果操作环境为微信端
	    			alertBox("登录APP签到您将获得更多的加息收益，是否要继续在微信端签到？","requestAgain",1);//仅在微信端弹出提示对话框
	    		}else{
	    			delayGoUserWechatSign2();//如果不是微信端，直接进行下一步
	    		}
 	    	});
	    }
	}
	else {//接口不通的情况下，调用报错的方法
	    errorMessage(data.resmsg_cn);
	}
}
function requestAgain(){
	 ajaxRequest(contextPath + "wxcoupons/queryUserCurrentSign", "mobile="+newMobile, sureFn,"GET");//aler弹出框点击确定后再次请求验证是否签到，主要防止微信和app同时打开，app签到后微信端的判断是否签到
}
function sureFn(data){//防止用户用app和微信同时
   if(data.rescode == "00000"){//接口通的情况
	    if(data.currentSign == "Y"){//判断是否已经签到，如果已签到，则id=signInBtn的按钮样式改变，文字内容改变
	    	if(data.signFlag=="WX"){
               $("#signInBtn").html("已加息+0.2%").addClass('dark');
	    	}else if(data.signFlag=="APP"){
	    	   $("#signInBtn").html("已加息+0.5%").addClass('dark');
	    	}
	    	$('#alertBox').remove();//点击alertbox确定选项时remove掉弹出款
	    	   	goUserWechatSign();

		    }else{
	            delayGoUserWechatSign();
	        }
	 }else {//接口不通的情况下，调用报错的方法
	    errorMessage(data.resmsg_cn);
	 }
 }

var delayGoUserWechatSign=function(){//微信端调此函数
	$('#alertBox').remove();//点击alertbox确定选项时remove掉弹出款
	$('#signInBtn').addClass('rotate');
	$('#score').addClass('score');
	setTimeout(function(){goUserWechatSign();$("#btnWrap").unbind('click')},300)
}
var delayGoUserWechatSign2=function(){//非微信调此函数
	$('#signInBtn').addClass('rotate');
	$('#score').addClass('score');
	setTimeout(function(){goUserWechatSign();$("#btnWrap").unbind('click')},300);
}
var setSign=function(){
    ajaxRequest(contextPath + "wxcoupons/queryUserCurrentSign", "mobile="+newMobile, setQueryUserCurrentSign,"GET");
}  
var setTaskList= function(data) {
	if(data.rescode == "00000"){
		var html="";
		var re=/0.[0-9]/g;
		if (data.list == null || data.list == "") {
			html += '<div class="PTB10P">';
			html += '<p class="font16 grayTex MT20">暂无活动内容</p>';
			html += '</div>';
		} else {
			$.each(data.list,function(i, jsonObj) {
				if(i == 0&&jsonObj.buttonName=="签到"){
					if (jsonObj.enable == "Y"){
                         html +='<div class="checkIn positionR">'; 
						 html +='<img src="' + contextPath + '/pic/weixin/activity/activity_April/share/invesBkg.jpg" width="100%" />';
						 html+='<p class="positionA sign whiteTex font16">'+jsonObj.descDody
						 html +='<span class="rule positionA" id="rule"><img src="' + contextPath + '/pic/weixin/activity/activity_April/share/question.png" width="60%" /></span></p>';
						 html+='<div class="positionA btnWrap" id="btnWrap">'
						 html+='<span class="positionA" id="score">0.2</span>'
						 html +='<a  class="font14 block height100P signInBtn" id="signInBtn">'+jsonObj.buttonName+'</a>';
						 html +='</div></div>';
					}else{
						
					}
				}
				if (jsonObj.enable == "Y"&&jsonObj.buttonName!="签到"){
					if (jsonObj.menuOneName == null || jsonObj.menuOneName == "") {
						html += '<h4 class="borderB"></h4>';
					}
					else{
						html += '<h4 class="textL width90 blockC lineHeight2_0x blackTex MB10 MT10">' + jsonObj.menuOneName+ '</h4>';
					}
					html += '<div class="whiteBkg">';
	                html += '<table class="width90 blockC textL verM minHeight">';
					html += '<tr>';
					html += '<td class="verTop PT10" width="25"><img src="'+jsonObj.imageUrl +'" width="30">';
					html += '<td class="PTB10 PL10 PR20">';
					html += '<h4 class="blackTex font14">' + jsonObj.menuTwoName;
					if (jsonObj.ratris == "-1"){
						html +='';
					}
					else{
						if (jsonObj.menuTwoFlag == 5){
							html += '<span class="redTex ML10"></span>';
						}
						else{
							html += '<span class="redTex ML10">+'+jsonObj.ratris +'%</span>';
						}	
					}
					html += '</h4><p class="grayTex font12 lineHeight1.2">'+ jsonObj.descDody +'</p></td>';
					html += '<td class="PTB10 textR" width="60" id="taskBtn">';

					if(jsonObj.menuTwoFlag == 2 || jsonObj.menuTwoFlag == 3){
						if(jsonObj.fullFlag==1){
                           html += '<a href="javascript:;" class="dark font14 block radius3">';	 
						}else{
						   html += '<a href="'+jsonObj.webview_url+'&from=LBWX" class="btnInterest font14 block radius3">';
						}
					}
					else{
						if(jsonObj.fullFlag==1){
                           html += '<a href="javascript:;" class="dark font14 block radius3">';	 
						}else{
						   html += '<a href="'+jsonObj.webview_url+'" class="btnInterest font14 block radius3">';
						}
					}
					html += jsonObj.buttonName+'</a></td>';
					html += '</tr></table>';
					html += '</div>';
				}else {
          
				}
			});
		}
        ajaxRequest(contextPath + "wxcoupons/queryUserCurrentSign", "mobile="+newMobile, setQueryUserCurrentSign,"GET");
		$("#formDiv").append(html);//将拼接内容放入id为fundFlowList的ul中
	}
	else {//接口不通的情况下，调用报错的方法
	    errorMessage(data.resmsg_cn);
	}
        $("#rule").click(function(){
        	$('.dailySign').removeClass('none').animate({'top':0},300);
        })
   };
var mobile,signFlag;
$(document).ready(function() {
	var newMobile;
	var screenH=$('body').height();
	$(".dailySign").height(screenH)
    mobile = $("#mobile").val();
    newMobile = $("#newMobile").val();
    signFlag = $("#signFlag").val();
    $('.dailySign').css('top',screenH*1.3)
    ajaxRequest(contextPath + "tasklist/getTaskList", "mobile="+ mobile, setTaskList);

	$('.closeBtn').click(function(){
             $('.dailySign').animate({'top':screenH*1.3},300);
         })
  });