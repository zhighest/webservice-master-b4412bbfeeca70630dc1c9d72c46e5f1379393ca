var goActivityDetail = function(noticeId) {
    var temp = document.createElement("form");
    temp.action = contextPath + "webindex/goActivityDetail";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "noticeId";
    inp.value = noticeId;
    temp.appendChild(inp);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

function setMedia(data) {
	
	var html = ""; //拼接html
    if (data.list == null || data.list == "") {
	    
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += '<li class="width90 blockC MT20 positionR clearfix" onclick="goActivityDetail('+ jsonObj.id +')">';

            html += '<div class="imgContent positionR outHide">';
            html += '<img src="' + jsonObj.smallIconUrl + '">';
            html += '</div>';

            html += '<div class="PTB20 pContent positionR clearfix">';
            html += '<div class="blackTex positionR clearfix font16 width80">'+jsonObj.title+'</div>';
            html += '<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
            html += '<div class="littleShortLine redBkg MT5 MB10"></div>';
            html += '<div class="grayTex61 font12 width80 textL positionR clearfix">'+jsonObj.afficheContent+'</div>';
            html += '</div>';

            html += '</li>';
        });
    }
    $("#mediaList").append(html);
    $("#mediaListPaging").html(pagingMobile(data, "getMedia"));
    
}
var getMedia = function(page) {
    ajaxRequest(contextPath + "webmedia/getMedia","current="+ page +"&pageSize=3&sign=ACTIVITY",setMedia);
};
$(document).ready(function () {
    getMedia(1)
});
