
// 后台数据组成列表
$(document).ready(function() {
	getCreditorList(1);//默认显示消息页面第一页
});
var getCreditorList = function(page) {
	ajaxRequest(contextPath + "/wxenjoy/getEnjoyCreditList", "current=" + page + "&pageSize=4"+"&type=0&", setCreditorList);
}
var setCreditorList = function(data) { //图片
	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == ""){
		html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无债权列表</p>';
        html += '<p class="p2">如果有债权列表，您将在这里看到</p>';
        html += '</div>';
	} else {
		$.each(data.list,function(i, jsonObj) {
		html += '<li class="MT15 font14 whiteBkg clearfix PTB2P width100"><div class="clearfix grayTex1 PLR5P">';
		html += '<div class="fl">预期年化收益率</div><div class="fr">在投本金(元)</div></div>';//债权当前价值修改为在投本金
		html += '<div class="clearfix MT5 redTex PB10 borderDashedB PLR5P"><div class="fl">';
		html += '<span class="font48 lineHeight100">' + jsonObj.interestRate + '</span>%</div>';//此处填写预计年化收益率
		html += '<div class="fr investMoney font28 heigh48 redTex">' + numFormat(jsonObj.cashValue) + '</div></div>';//此处填写债权当前价值
		html += '<div class="clearfix grayTex PT10 PB5 PLR5P">';
		html += '<div class="fl PTB5">到期日期:<span>' + jsonObj.endDate + '</span></div>';//此处填写到期日期
		html += '<div class="fr grayBorder PTB5 PLR10 radius3"  onclick="getServAgreementByType('+  jsonObj.creditAcctCid +')"><a class="grayTex">查看合同</a></div>';
		html += '</div></li>'
		});
		}
		//console.log(min, max);
		$("#creditorList").append(html);//将拼接内容放入id为fundFlowList的ul中
		$("#proListPaging").html(pagingMobile(data, "getcreditorList"));
	}
}

var getServAgreementByType = function(creditAcctCid){
	ajaxRequest(contextPath + "wxagreement/getServAgreementByType","type=enjoy&creditAcctCid=" + creditAcctCid , setServAgreement);
}

var setServAgreement = function(data){ 
    if (data.rescode != "00000") {
        errorMessage(data.rescode);
    } else {
        window.location.href = data.serAgreementUrl;
    }
};