var goToDetails = function(id){
	var temp = document.createElement("form");
    temp.action = contextPath + "wxabout/goDetail";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "id";
    inp.value = id;
    temp.appendChild(inp);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

//拼接媒体报道列表
var setMediaReport = function(data){
	var html = ""; //拼接html
	if(data.rescode == "00000"){
		if (data.list == null || data.list == "") {
			html += '<div class="PTB10P">';
			html += '<p class="font16 textC grayTex MT20">暂无媒体报道内容</p>';
			html += '</div>';
		}else{
			$.each(data.list, function(i, jsonObj) {
				html += '<li class="whiteBkg clearfix MT5">';
				html += '<a class="clearfix" onclick="goToDetails(\''+ jsonObj.id +'\')">';
				html += '<div class="fl width50 PLR5P boxSizing font16 blackTex PT5P strong">' +jsonObj.title+ '</div>';
				html += '<div class="fr width50 outHide mediaImg">';
				html += '<img src="'+jsonObj.imageUrl+'" width="100%">';
				html += '</div></a></li>';
			});
		}
	}
	$("#mediaList").append(html);
	$("#mediaListPaging").html(pagingMobile(data, "getMediaReport"));
};
var getMediaReport = function(page){
	ajaxRequest(contextPath + "wxpush/queryMediaReport", "current="+page+"&pageSize=5&sign=NEWS",setMediaReport);
}
var setClearUnread1 = function(data){	
    if(data.rescode == 00000){
    	console.log("cleared");
    }else{
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
	        ajaxRequest(contextPath + "wxpush/clearUnread", "message_type=2", setClearUnread1)};		
		}else if(channel == "LBAndroid") {
			
			if(mobile == "" || mobile == null || mobile=="null"){
				if(window._cordovaNative){
					
						window._cordovaNative.goLogin();
					
				}	
			}else{
				if($("#clearUnread").val()=="read"){
	                ajaxRequest(contextPath + "wxpush/clearUnread", "message_type=2&mobile="+mobile, setClearUnread1)};	
			}			
		}else if(channel == "LBIOS") {
			if(mobile == "" || mobile == null||mobile=="null"){
				goLogin();		
			}else{
				if($("#clearUnread").val()=="read"){
					ajaxRequest(contextPath + "wxpush/clearUnread","message_type=2&mobile="+mobile,setClearUnread1)};
				
			}					
		}
    getMediaReport(1);
});
