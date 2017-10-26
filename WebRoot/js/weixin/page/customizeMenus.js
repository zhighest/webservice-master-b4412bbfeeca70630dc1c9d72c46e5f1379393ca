$(function(){
	 FastClick.attach(document.body);
	(function rem(x){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		window.addEventListener("resize",setRenSize,false);
		function setRenSize(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/x+"px";
		}
	}(7.5));
	var num=true,
	 mobile = $("#mobile").val(),
	 channel = $("#channel").val();
	ajaxRequest(contextPath + "tasklist/getAllIcoFuncList","mobile=" +mobile+ "&channel="+channel,refer,"");
	$("#bth").on("click",function(){
		if(num==true){
			$(this).text("完成"); 
			$(".wrap_div").removeClass("none");
			$(".wrap").addClass("oBorder");
			num=false;	
		}
		else{
			var dom=document.getElementById("header_ul"),
			dom1=dom.getElementsByTagName("li"),
			j="",
			length=dom1.length;
					for(var i=0;i<length;i++){
						j+=dom1[i].id+",";
					}
					var len=j.length;
					j=j.substring(0,len-1);
					$(this).text("编辑");
					$(".wrap_div").addClass("none");
					$(".wrap").removeClass("oBorder");
					num=true;
					ajaxRequest(contextPath + "tasklist/addUserIcoFuncList","mobile=" +mobile+ "&channel="+channel+"&icoFuncId="+j,newRefer,"");
		}
	})
	$(document).on("click",".wrap_div",function(){
		var $this2=$(this),
			$this=$this2.parent().parent(),
			$this1=$this.parent(),
			dom=document.getElementById("header_ul"),
			dom1=dom.getElementsByTagName("li"),
			$header=$("#header"),
			length=dom1.length;
		$header.removeClass("none");
		if($this1.hasClass("header_ul")){//删除插入
			$this.remove();
			$this2.addClass("urlImg1").removeClass("urlImg");
			$("#content_ul").append($this);
			if(length==1) $header.addClass("none");
		}
		else if(length>=7){
			errorMessage("您已经选满7个快捷菜单");	
			return
		}
		else if($this1.hasClass("content_ul")){
			$this.remove();
			$this2.addClass("urlImg").removeClass("urlImg1");
			$("#header_ul").append($this);
		}
	})
})
function refer(data){
	if(data.rescode=="00000"){
		var html="",
		html1="";
		$.each(data.list,function(i, jsonObj){
			 if(i<=7){
				 if(jsonObj.id==9999||jsonObj.id=="9999"){//去掉更多
						return
					}
				html+='<li id="'+jsonObj.id+'"><div class="wrap" ><div class=" none wrap_div urlImg"></div>';
					html+='<a href="'+jsonObj.webview_url+'"><img src="'+jsonObj.image_url_def+'" width="40%" />';
					html+='<span>'+jsonObj.ico_func_name+'</span></a></div></li>';
			}
			else {	
				html1+='<li id="'+jsonObj.id+'"><div class="wrap" ><div class=" none wrap_div urlImg1"></div>';
					html1+='<a href="'+jsonObj.webview_url+'"><img src="'+jsonObj.image_url_def+'" width="40%" />';
					html1+='<span>'+jsonObj.ico_func_name+'</span></a></div></li>'	;		
			}
		})
		$("#header_ul").html(html);
		$("#content_ul").html(html1);
	}
	else{
		setTimeout(function(){
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
		},1000);
	}	
}
function newRefer(data){
	if(data.rescode=="00000"){
		window.location.href = contextPath +"/wxabout/goIndex"; 
	}
	else{
		setTimeout(function(){
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
		},1000);
		}  
}
