$(document).ready(function() {
	getAcList(1);
});
var getAcList = function(page) {
	ajaxRequest(contextPath + "wxactivity/getAcList", "current=" + page + "&pageSize=5", setAcList,"GET");
}
var setAcList = function(data) { //图片
	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == "") {
		html += '<div class="PTB10P">';
		html += '<p class="font16 grayTex MT20">暂无活动内容</p>';
		html += '</div>';
	} else {
		$.each(data.list,function(i, jsonObj) {
		html += '<li class="activityItem borderBS whiteBkg radius5 MB10 MT10">';
		html += '<div class="PTB5 PLR5 imgArea list clearfix">';
		html +='<a href="'+jsonObj.ac_list_tzurl +'">';
		html +='<img src="'+jsonObj.ac_list_img+'" width="100%"></a>'; 
		html += '<div class="clearfix"><p class="activityDes grayTex font12 MT10 lineHeight1_5x PB5 fl">' + jsonObj.ac_list_ps + '</p>';
		if(jsonObj.endtimeflag == "0"){
			html += '<div class="grayTex font12 textC grayBkg MT10 PLR10  fr">已结束</div></div>';
		}else{
			html += '<div class="whiteTex font12 textC redBkg MT10 PLR10  fr">火热进行中</div></div>';
		}
		html +='</div></li>';
		});
		}
		//console.log(min, max);
		$("#myList").append(html);//将拼接内容放入id为fundFlowList的ul中
		$("#proListPaging").html(pagingMobile(data, "getAcList"));

	}
	else{
		errorMessage(data.resmsg_cn);
	}
}
// var setRateCouponsList = function(page) {
//     ajaxRequest(contextPath + "wxactivity/getAcList", "current=" + page + "&pageSize=5", setAcList,"GET");
// };

//分页函数
//data格式如下：pageObject":{"end":5,"pageNum":2,"pageSize":178,"start":1,"totlePages":18,"totleSize":0};
function pagingMobile(data, method) {
	
    var nextbutton = ""; //设置更多
    var txtNextPage = "";
    //设置更多
    if (data.pageNum < data.totlePages) {
        nextbutton = "<li><a id=\"moreBtn\" onclick=\""+method+"(" + (data.pageNum + 1) + ");\">" + txtNextPage + "</a></li>";
    }else{
	    nextbutton = "<li><a id=\"moreBtn\" onclick=\"errorMessage('没有更多了')\">" + txtNextPage + "</a></li>";
    }
	$(window).scroll(function(){
		if($(document).scrollTop() >= $(document).height()-$(window).height()){
			$("#moreBtn").click();
		};
	});
    return "<div class=\"pagingDiv MT20\">" + "<ul class=\"paging\">"
        + nextbutton //设置下一页
        + "</ul>" + "</div>";
}