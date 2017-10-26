var setClearUnread1 = function(data){	
    if(data.rescode == 00000){
    	console.log("cleared");
    }else{
    	
        errorMessage(data.resmsg_cn);
    }   
}
var getAcList = function(page) {
	ajaxRequest(contextPath + "wxpush/queryMediaReport", "current=" + page + "&pageSize=3&sign=CONSULTATION", setAcList,"GET");
}
var setAcList = function(data) { //图片
	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == "") {
			html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无资讯</p>';
	        html += '<p class="p2">如果有资讯，您将在这里看到</p>';
	        html += '</div>';
	} else {
		$.each(data.list,function(i, jsonObj) {
		html += '<div class="timeBox MLRA whiteTex MT20 font16 radius5">'+ jsonObj.time +'</div>';//此处填写日期
		html += '<div class="width90 radius5 whiteBkg ML5P MT20 PTB20 borderBS">';
		html += '<a class="width94 MLRA outHide positionR block" href="'+ jsonObj.details +'">';
		html +='<img src="'+ jsonObj.imageUrl +'"class="positionR  width100">';//此处填写图片的链接
		html +='<div class="positionA noticeN PLR10">'; //此处填写文案
		html += '<p class="whiteTex font12 textL">' + jsonObj.content + '</p></div>';
		html +='</a></div>';
		});
		}
		//console.log(min, max);
		$("#myNotice").append(html);//将拼接内容放入id为fundFlowList的ul中
		$("#proListPaging").html(pagingMobile(data, "getAcList"));

	}
	else{
		errorMessage(data.resmsg_cn);
	}
}
$(document).ready(function() {
	var parm = $("#parm").val();
	var mobile = $("#mobile").val();
	var channel = $("#channel").val();			 
	var ua = navigator.userAgent.toLowerCase();
	
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		
		if($("#clearUnread").val()=="read"){
	        ajaxRequest(contextPath + "wxpush/clearUnread", "message_type=5", setClearUnread1)};		
		}else if(channel == "LBAndroid") {
			if(mobile == "" || mobile == null || mobile=="null"){
				if(window._cordovaNative){
					
						window._cordovaNative.goLogin();
					
				}	
			}else{
				if($("#clearUnread").val()=="read"){
	                ajaxRequest(contextPath + "wxpush/clearUnread", "message_type=5&mobile="+mobile, setClearUnread1)};	
			}			
		}else if(channel == "LBIOS") {
			
			if(mobile == "" || mobile == null||mobile=="null"){
				goLogin();		
			}else{
				if($("#clearUnread").val()=="read"){
					ajaxRequest(contextPath + "wxpush/clearUnread","message_type=5&mobile="+mobile,setClearUnread1)};
				
			}					
		}
    getAcList(1);//默认显示第一页
});