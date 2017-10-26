
$(document).ready(function() {
  ajaxRequest(contextPath + "wxproduct/getProductLoanList", "", setProductLoanList);
});
var setProductLoanList = function (data){
	var html = '';
	if (data.list == null || data.list == "") {
		html += '<p>敬请期待</p>';
    }else{
    	$.each(data.list, function(i, jsonObj) {
        	html += '<li class="listItem whiteBkg MB30 clearfix">';
        	if(jsonObj.showName=="零钱计划"){ 
            	html += '<img src="'+contextPath+'/pic/web/aboutUs/demand.png">';
            }else if(jsonObj.showName=="铃铛宝3月期"||jsonObj.showName=="铃铛宝定期90天"||jsonObj.showName=="铃铛宝定期3月期"){
            	html += '<img src="'+contextPath+'/pic/web/aboutUs/fixedThreeMonth.png">';
            }else if(jsonObj.showName=="铃铛宝6月期"||jsonObj.showName=="铃铛宝定期180天"||jsonObj.showName=="铃铛宝定期6月期"){
            	html += '<img src="'+contextPath+'/pic/web/aboutUs/fixedSixMonth.png">';
            }
            html += '<div class="listItemP">';
            if(jsonObj.planId == "108"){
                html += '<p class="MT30 font16">稳步投资 多重保障</p>';
           	}else{
                html += '<p class="MT30 font16">简便快捷 聚少成多</p>';
           	};
           	html += '<p class="MT10 MB10 line">/</p>';
           	html += '<ul class="inlineTUl">';
           	html += '<li class="boxSizing"><div class="opacity60 font12">预期收益率(年)</div>';
           	html += '<div class="redTex font48 helveticaneue">'+jsonObj.annualRate+'<span class="font12">%</span></div></li>';
           	html += '<li class="boxSizing"><div class="opacity60 font12">起投金额</div>';
           	html += '<div class="redTex font48 helveticaneue">'+jsonObj.investMinAmount+'<span class="font12">元</span></div></li>';
           	html += '<li class="boxSizing"><div class="opacity60 font12">投资期限</div>';
           	if(jsonObj.planId == "108"){
                html += '<div class="redTex font48 helveticaneue">'+jsonObj.remanPeriods+'<span class="font12 fontYahei">个月</span></div>'
           	}else{
           		html += '<div class="redTex MT20">随存随取</div></li>'
           	};
           	html += '</ul>';
            html += '</div>';
        	html += '</li>';
       });
    	$("#productList").append(html);
    }
}