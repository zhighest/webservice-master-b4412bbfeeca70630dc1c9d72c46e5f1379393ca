var setFundFlowList = function(data) { //处理中订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        $("#fundFlowList").removeClass('borderB');
        html += '<dd class="borderBd heigh70 textC grayTex" style="width:100%;">暂无账户明细</dd>'
    } else {
        $("#fundFlowList").addClass('borderB');
        $.each(data.list,
        function(i, jsonObj) {
			html += '<li class="textC invert heigh60 clearfix grayTex invertCon">';
			html += '<span class="width25 fl">'+ jsonObj.transDate +'</span>';
			html += '<span class="width20 blackTex fl">'+ jsonObj.fundType +'</span>';
			if(jsonObj.type == 0){
				html += '<span class="width35 blackTex fl">-'+ numFormat(jsonObj.amount) +'</span>';
			}else{
				html += '<span class="width35 redTex fl">+'+ numFormat(jsonObj.amount) +'</span>';
			}
			if(jsonObj.status == "交易完成" || jsonObj.status == "处理中"){
                html += '<span class="fl width20 greenTex">'+jsonObj.status+'</span>';
            }else{
                html += '<span class="fl width20 redTex">'+jsonObj.status+'</span>';
            }
			html += '</li>';
        });
    }
    $("#fundFlowList").append(html);
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getFundFlowList"));
    $(".paging li").css("width","100%");
};
var getFundFlowList = function(page) { //处理中订单调用
	    ajaxRequest(contextPath + "wxtrade/getFundFlowList", "current=" + page + "&pageSize=10", setFundFlowList);
	}
$(document).ready(function() {
	getFundFlowList(1);
});