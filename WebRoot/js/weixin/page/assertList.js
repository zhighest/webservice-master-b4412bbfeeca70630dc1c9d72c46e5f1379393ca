
$(document).ready(function() {
	ajaxRequest(contextPath + "wxenjoy/getEnjoyLoanList", "current=1&pageSize=10", setEnjoyLoanList);
});

var getEnjoyLoanListMore = function(page){
	ajaxRequest(contextPath + "wxenjoy/getEnjoyLoanList", "current=" + page + "&pageSize=10", setEnjoyLoanList);
}

var setEnjoyLoanList = function(data){

	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == "") {
			html += '<li class="textC">';
			html += '<div class="PTB10P">';
			html += '<p class="font16 grayTex MT20">暂无标的</p>';
			html += '</div></li>';
		} else {
			$.each(data.list,function(i, jsonObj) {
			html += '<li class="width100 whiteBkg PTB2P MT15 borderB">';
			html += '<div class="borderDashedB PL5P PB5">'+jsonObj.productName+'</div>';
			html += '<div class="width90 MLRA outHide MT2P">';
			html += '<div class="fl font14">';
			html += '<p class="grayTex">锁定期</p>';
			if (jsonObj.remanDays == 0) {
				html += '<p class="redTex"><span class="font18">'+jsonObj.investPeriod+'个月</span></p>';
             }else{
             	html += '<p class="redTex"><span class="font18">'+jsonObj.remanDays+'天</span></p>';
             }
			html += '';
			html += '</div>';
			html += '<div class="fr textL font14">';
			html += '<p class="grayTex textR">剩余债权</p>';
			html += '<p class="redTex"><span class="font18">'+numFormat(jsonObj.remanAmount)+'元</span></p>';
			html += '</div>';
			html += '</div>';
			html +='</li>';
		})
	}
	$("#myList").append(html);
	$("#proListPaging").html(pagingMobile(data, "getEnjoyLoanListMore"));
 }
}
