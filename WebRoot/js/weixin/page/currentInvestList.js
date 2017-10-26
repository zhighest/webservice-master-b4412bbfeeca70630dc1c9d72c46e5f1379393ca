var setUserListByLoanId = function(data) {
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无投资记录</p>';
        html += '<p class="p2">如果有投资记录，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,function(i, jsonObj) {
            html += '<li class="clearfix borderT borderB">';
            html += '<div class="fl">';
            html += '<div class="imgArea fl">';
            if(jsonObj.imageIconDispose == "" || jsonObj.imageIconDispose == null ){
            	html += '<img src="' + contextPath + '/pic/weixin/defaultAvatar.png" class="radius100" height="40">';
            }else{
	            html += '<img src="' + jsonObj.imageIconDispose + '" class="radius100" height="40">';
            }
            html += '</div><div class="MT10 fl ML20 height40">';
            html += '<span class="font14">' + jsonObj.realName + '</span></div>';
            html += '</div>';
            html += '<div class="fr height40 MT10">';
            html += '<span class="grayTex textR font12">' + getDate(jsonObj.buyDate).Format("yyyy/MM/dd hh:mm:ss") + '</span>';
            html += '</div>';
            html += '</li>';
        });
    }
    $("#userListByLoanId").append(html);
    $("#userListByLoanIdPaging").html(pagingMobile(data, "getUserListByLoanId"));
};
var getUserListByLoanId = function(page) { //处理中订单调用
    ajaxRequest(contextPath + "/wxproduct/currentInvestList", "loanId=" + sloanId + "&current=" + page + "&pageSize=10", setUserListByLoanId);
}
var sloanId;
$(document).ready(function() {
	sloanId = $("#loanId").val();
    getUserListByLoanId(1)
});