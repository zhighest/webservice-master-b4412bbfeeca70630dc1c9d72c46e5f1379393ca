var id;
id=$("#id").val();
$(document).ready(function() {
	ajaxRequest(contextPath + "wxpush/queryMediaReportDetail", "id="+id,setMediaReport);
});
var setMediaReport = function(data){
	var html = ""; //拼接html
	if(data.rescode == "00000"){
		if (data.result == null || data.result == "") {
			html += '<div class="PTB10P">';
			html += '<p class="font16 textC grayTex MT20">暂无媒体报道内容</p>';
			html += '</div>';
		}else{
			html += '<div class="textC font16 clearfix width90 blockC"><h4 class="blackTex">'+ data.result.title +'</h4></div>';
			html += '<div class="MT10 width90 MLRA font14 blackTex1 lineHeight2x textL myContent">'+data.result.content+'</div>';
		}
	}
	$("#mediaDetail").append(html);
}

