var setUserListByLoanId = function(data) {
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {         
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无投资记录</p>';
        html += '<p class="p2">如果有投资记录，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += '<li class="clearfix">';
            html += '<div class="fl">';
            html += '<div class="imgArea fl">';
            if(jsonObj.imageIconDispose == "" || jsonObj.imageIconDispose == null ){
            	html += '<img src="' + contextPath + '/pic/weixin/defaultAvatar.png" class="radius100" height="60">';
            }else{
	            html += '<img src="' + jsonObj.imageIconDispose + '" class="radius100" height="60">';
            }
            html += '</div>';
            html += '<span class="ML20 height60 font18">' + jsonObj.realName + '</span>';
            html += '</div>';
            html += '<div class="fr height60">';
            html += '<span class="grayTex textR">' + jsonObj.buyDate + '</span>';
            html += '</div>';
            html += '</li>';
        });
    }
    $("#userListByLoanId").append(html);
    $("#userListByLoanIdPaging").html(pagingMobile(data, "getUserListByLoanId"));
};
var getUserListByLoanId = function(page) { //处理中订单调用
    ajaxRequest(contextPath + "wxproduct/getUserListByLoanId", "loanId=" + sloanId + "&productType=" + productType + "&current=" + page + "&pageSize=10", setUserListByLoanId);
}
var sloanId;
var productType;
$(document).ready(function() {
	sloanId = $("#loanId").val();
	productType = $("#productType").val();
    getUserListByLoanId(1)
});