var getMyInvitationList = function(page) { //处理中订单调用
    ajaxRequest(contextPath + "wxuser/getMyInvitationList", "current=" + page + "&pageSize=10", setMyInvitationList);
}
var setMyInvitationList = function(data) { //处理中订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无邀请好友</p>';
        html += '<p class="p2">如果有邀请好友，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += '<li class="clearfix borderB">';
            html += '<div class="fl">';
            html += '<div class="imgArea fl">';
            if(jsonObj.litimg == "" || jsonObj.litimg == null || jsonObj.litimg == "null" ){
                html += '<img src="' + contextPath + '/pic/weixin/defaultAvatar.png" class="radius100" height="40">';
            }else{
                html += '<img src="' + jsonObj.litimg + '" class="radius100" height="40">';
            }
            html += '</div><div class="MT10 fl ML20 height40">';
            if(jsonObj.userName != "" && jsonObj.userName != null&& jsonObj.userName != "null"){
               html += '<span class="font14">' + jsonObj.userName + '</span></div>';
            }else{
               html += '<span class="font14">' + jsonObj.mobile + '</span></div>';
            }
            html += '</div>';
            html += '<div class="fr height40 MT10">';
            html += '<span class="grayTex textR font12">' + jsonObj.invitedTime + '</span>';
            html += '</div>';
            html += '</li>';
        });
    }
    $("#myInvitationList").append(html);
    $("#myInvitationListPaging").html(pagingMobile(data, "getMyInvitationList"));
};
//报错
function errorMessage(str) {
	if($("body .errorMessage").length > 0){
		return false;
	}
	function hideError(){
		$("body .errorMessage").fadeOut("normal",function(){
			$(this).remove();
		})
	}
	var html = '<div class="errorMessage" style="bottom: 100px; position: fixed; text-align: center; width: 100%; display:none; z-index:1000;"><font style="background-color: rgba(0, 0, 0, 0.7); border-radius: 5px; color: #fff; z-index:1000; display: inline-table; line-height: 170%; max-width: 80%; padding: 5px 10px;">'+ str +'</font></div>';
	$("body").append(html);
	$("body .errorMessage").fadeIn("normal",function(){
		setTimeout(hideError,3000);
	})
} 
$(document).ready(function() {
    getMyInvitationList(1);
});