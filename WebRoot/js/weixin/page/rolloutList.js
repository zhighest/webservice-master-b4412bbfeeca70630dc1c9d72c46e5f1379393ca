var ymArray = "";
// 后台数据组成列表
var getcreditorListMore = function(page) {
	ajaxRequest(contextPath + "wxenjoy/getEnjoyPlanMoneyFundFlow", "current=" + page + "&pageSize=15", setCreditorList);
}
var setCreditorList = function(data) { //图片
	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == "") {
			html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无转出记录</p>';
	        html += '<p class="p2">如果有转出记录，您将在这里看到</p>';
	        html += '</div>';
			} else {
				$.each(data.list,function(i, jsonObj) {
					var ym = getDate(jsonObj.transDate).Format("yyyy/MM");
			       	if(ym != ymArray){
				       	ymArray = ym;
				       	html += '<li style="background-color:#efefef;"><div class="grayTex font14  lineHeight100 PL5P heigh30">' + ym + '</div></li>';
					}
					html += '<li>';
					// html += '<div class="grayTex font14 ML5P lineHeight100 MB5">' +jsonObj.transDate+ '</div>';//此处填入月份
					html += '<div class="whiteBkg borderB grayTex">';
					html += '<div class="outHide width90 MLRA" style="padding-top:5px">';
					html += '<div class="fl font16">转出</div>';
					html += '<div class="redTex fr font18">' +jsonObj.outAmt+ '元</div>' ;//此处填写转出金额
					html += '</div>';
					html += '<div class="outHide width90 MLRA"  style="padding-bottom:5px">';
					html += '<div class="fl font14">' + getDate(jsonObj.transDate).Format("yyyy/MM/dd") + '</div>';//此处填写转出日期
					html += '<div class="fr font12"><span>手续费 </span><span style="margin-left:2px">' +jsonObj.poundage+ '元</span></div>';//此处填写手续费
					html += '</div>';
					html += '</li>'
					});
		}
		$("#myList").append(html);//将拼接内容放入id为fundFlowList的ul中
		$("#proListPaging").html(pagingMobile(data, "getcreditorListMore"));
	}
	
}
$(function(){
	getcreditorListMore(1);
})
