var turnplate={
		restaraunts:[],				//大转盘奖品名称
		colors:[],					//大转盘奖品区块对应背景颜色
		outsideRadius:192,			//大转盘外圆的半径
		textRadius:140,				//大转盘奖品位置距离圆心的距离
		insideRadius:0,			//大转盘内圆的半径
		startAngle:0,				//开始角度
};
var flag_click=true;

$(document).ready(function(){
	var mobile = $("#mobile").val();
var channel = $("#channel").val();
	document.body.setAttribute("style","overflow-x: hidden;");
	var ua = navigator.userAgent.toLowerCase();
		$("#plateBth").click(function(){
		document.body.setAttribute("style","overflow-x: hidden;");
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
		if(!flag_click) return;
			ajaxRequest(contextPath + "wxluckdraw/userSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,callback,"GET");	
		
	});
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,luckyDraw,"GET");
	}else if(channel == "LBAndroid") {	
		if(mobile == "" || mobile == null || mobile == "null"){
			ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,luckyDraw,"GET");
				
		}else{
			ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,luckyDraw,"GET");
		}			
	}else if(channel == "LBIOS") {	
		ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,luckyDraw,"GET");
		if(mobile == "" || mobile == null || mobile == "null"){
						
		}else{
			ajaxRequest(contextPath + "wxluckdraw/showSpecLuckDraw","mobile=" +mobile+ "&channel="+channel,luckyDraw,"GET");					
		}	
	}else {
	}
	(function rem(x){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		window.addEventListener("resize",setRenSize,false);
		function setRenSize(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		}
	}(12.24));
	//动态添加大转盘的奖品与奖品区域背景颜色
	turnplate.restaraunts = [];
	turnplate.colors = ["rgb(234,218,35)", "rgb(255,138,0)", "rgb(254,68,64)", "rgb(220,9,9)","rgb(234,218,35)", "rgb(255,138,0)", "rgb(254,68,64)", "rgb(220,9,9)"];	
	turnplate.num='';
	$("#icon").click(function(){
		$("#alertWrap").addClass("none")
	})
});
window.onload=function(){
	drawRouletteWheel(); 
	drawRouletteWheel1();	
	drawRouletteWheel3();
	document.ondragstart = function() {
	    return false;
	};
};
function stopDefault(x){
	document.getElementById(x).ontouchmove = function(e){
        e.preventDefault && e.preventDefault();
        e.returnValue = false;
        e.stopPropagation && e.stopPropagation();
        return false;
    } 
}
function callback(data){
	if(data.rescode=="00000"){
		turnplate.num=parseInt(data.restLuckDrawTimes);
	var num=360/turnplate.restaraunts.length;
	var newNum=num+num/2+180;
	var data =	parseInt(data.id);
	rotateFunc(data,newNum-num*data,turnplate.restaraunts[data]);
	$("#numNew").text(turnplate.num)
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
function luckyDraw(data){
	if(data.rescode=="00000"){
		turnplate.num=data.restLuckDrawTimes;
		$("#numNew").text(turnplate.num);
		$("#activityDesc").html(data.activityDesc)
		var html='';
		$.each(data.list,function(i, jsonObj){
			turnplate.restaraunts.push(jsonObj.awardName)
			html +='<img  id="img'+jsonObj.id+'" class="img" style="display:none;">'	
		});
		$("#activityDesc").append(html);
		$.each(data.list,function(i, jsonObj){
			var select='#img'+jsonObj.id;
			$(select).attr('src',jsonObj.showImg)
		});
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
function alertWrap(tips){
    var html='';
    html += '<div class="width100 height100P positionF" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">';
    html += '<div class="radius5 whiteBkg width80 positionR textC claerfix" style="margin-left:10%;top: 50%;-webkit-transform: translateY(-50%);transform: translateY(-50%); padding-bottom:16px;box-shadow: 0px 0px 3px #666;">';
    html += '<h3 class="font20 PT15 redTex">提&nbsp;示</h3>';
    html += '<div class="ML10 MR10">';
    html += '<p class="font16 blackTex">'+tips+'</p></div>';
    html += '<a class="inlineB redBkg width35 ML10 MR10 MB10 whiteTex PT5 PB5 font16" onclick="$(\'#alertBox\').remove();">确 定</a>';
    html += '</div></div>';
    $("body").append(html);
}
function drawRouletteWheel1(){
	var canvas1 = document.getElementById("wheelcanvas1"); 
	var Width=canvas1.width;
	var height=canvas1.height;
	if (canvas1.getContext) { 
		  var ctx1 = canvas1.getContext("2d");
		  var arc = Math.PI / (turnplate.restaraunts.length/2);
		  ctx1.clearRect(0,0,Width,height);
		  //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
		  ctx1.strokeStyle = "#cc201f";
		  //font 属性设置或返回画布上文本内容的当前字体属性
		  ctx1.font = '16px Microsoft YaHei'; 
		  for(var i = 0; i < turnplate.restaraunts.length; i++) {       
			  var angle = turnplate.startAngle + i * arc;
			  ctx1.fillStyle = 'rgba(0,0,0,0)';
			  ctx1.beginPath();
			  //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
			  ctx1.arc(Width/2, height/2, turnplate.outsideRadius, angle, angle + arc, false);    
			  ctx1.arc(Width/2, height/2, turnplate.insideRadius, angle + arc, angle, true);   
			  ctx1.fill();
			  //锁画布(为了保存之前的画布状态)
			  ctx1.save();    
			  //----绘制奖品开始----
			  	ctx1.fillStyle = "#171717";
				  var text = turnplate.restaraunts[i];
				  var line_height = 22;
				  //translate方法重新映射画布上的 (0,0) 位置
				  ctx1.translate(Width/2 + Math.cos(angle + arc / 2) * turnplate.textRadius, height/2 + Math.sin(angle + arc / 2) * turnplate.textRadius);
				  //rotate方法旋转当前的绘图
				  ctx1.rotate(angle + arc / 2 + Math.PI / 2);
				  ctx1.font = 'bold 18px Microsoft YaHei';
				//字符串切割
				  if (i==0||i==1||i==3||i==5)ctx1.fillStyle = "#fff";
				  if(i==2||i==4||i==6||i==7)ctx1.fillStyle = "#f53f38";
				 	 if(text.indexOf("||")>0){	 
					  var texts = text.split("||");
					  for(var j = 0; j<texts.length; j++){
						 // ctx.font =  'bold 18px Microsoft YaHei';
						  if(j == 0){
							  ctx1.fillText(texts[j], -ctx1.measureText(texts[j]).width / 2, j * line_height);
						  }else{
							  ctx1.fillText(texts[j], -ctx1.measureText(texts[j]).width / 2, j * line_height);
						  }
					  }
				  }	
				  //奖品名称长度超过一定范围 
				 	 else if(text.length>7){
					  text = text.substring(0,7)+"||"+text.substring(7);
					  var texts = text.split("||");
					  for(var j = 0; j<texts.length; j++){
						  ctx1.fillText(texts[j], -ctx1.measureText(texts[j]).width / 2, j * line_height);
					  }
				  }else{  
					  //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
					  ctx1.fillText(text, -ctx1.measureText(text).width / 2, 0);
				  }
				  ctx1.restore();
		  }
	}	
}
function drawRouletteWheel() {    
  var canvas = document.getElementById("wheelcanvas"); 
  var Width=canvas.width;
  var height=canvas.height;
  if (canvas.getContext) {
	  //根据奖品个数计算圆周角度
	  var ctx = canvas.getContext("2d"); 
	  var arc = Math.PI / (turnplate.restaraunts.length/2);
	  //在给定矩形内清空一个矩形
	  //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
	  ctx.strokeStyle = "rgba(254,143,114,0.3)";
	  //font 属性设置或返回画布上文本内容的当前字体属性
	  ctx.font = '16px Microsoft YaHei';      
	  for(var i = 0; i < turnplate.restaraunts.length; i++) {       
		  var angle = turnplate.startAngle + i * arc;
		  ctx.fillStyle = turnplate.colors[i];
		  ctx.beginPath();
		  //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
		  ctx.arc(Width/2, height/2, turnplate.outsideRadius, angle, angle + arc, false);    
		  ctx.arc(Width/2, height/2, turnplate.insideRadius, angle + arc, angle, true);   
		  ctx.fill();
		  ctx.stroke();
		  //锁画布(为了保存之前的画布状态)
		  ctx.save();    
		  //----绘制奖品开始----
		  	ctx.fillStyle = "#171717";
			  var text = turnplate.restaraunts[i];
			  var line_height = 17;
			  //translate方法重新映射画布上的 (0,0) 位置
			  ctx.translate(Width/2 + Math.cos(angle + arc / 2) * turnplate.textRadius, height/2 + Math.sin(angle + arc / 2) * turnplate.textRadius);
			  //rotate方法旋转当前的绘图
			  ctx.rotate(angle + arc / 2 + Math.PI / 2);
			  ctx.font = 'bold 18px Microsoft YaHei'; 
			  //图片插入
			  var t='img'+i
			  var img= document.getElementById(t); 
			  img.onload=function(){  
				  ctx.drawImage(img,-45,-30,90,65);      
			  };  
			  ctx.drawImage(img,-45,-30,90,65);
		  //把当前画布返回（调整）到上一个save()状态之前 
		  ctx.restore();
		  //----绘制奖品结束----
	  }      
  }  
} 
function drawRouletteWheel3(){
	var canvas2 = document.getElementById("wheelcanvas3");
	if (canvas2.getContext) {
		 var ctx2 = canvas2.getContext("2d");
			var img= document.getElementById("img22"); 
			  img.onload=function(){  
				  ctx2.drawImage(img,0,0);      
			  };
			  ctx2.drawImage(img,0,0,422,435);
	}
}
var a = 0; 
//awards:奖项，angle:奖项对应的角度，text:文本内容
var rotateFunc = function(awards,angle,text){ 
	var b = angle+1440+a;
		  var p = text.replace("||","");
    document.getElementById('plate').setAttribute("style","-webkit-transform: rotate("+b+"deg);transform: rotate("+b+"deg)");
    $("#plate").css("top","3%;");
    a += 1440; 
    flag_click=false;
    setTimeout(rotateAlert,2300);
    function rotateAlert(){
    	flag_click=true;
    	$("#font").html(p);
    	$("#alertWrap").removeClass("none");
    	 $("#plate").css("top","3%;");
    	return false;	
    }
};