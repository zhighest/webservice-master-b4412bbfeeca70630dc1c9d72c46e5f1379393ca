function setMediaContent(data) {
	$("#crumbsMediaTitle").html(data.title);
	$("#mediaTitle").html(data.title);
	$("#mediaDetail").html(data.afficheContent);
	$("#mediaDetailP").html(data.afficheContent);
	$("#mediaTime").html(data.createTime);
}

$(document).ready(function () {
	var noticeId = $("#noticeId").val();
    ajaxRequest(contextPath + "webmedia/getMediaContent","noticeId="+noticeId,setMediaContent);
});