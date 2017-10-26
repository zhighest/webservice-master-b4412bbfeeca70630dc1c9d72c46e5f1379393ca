var goMediaDetail = function(noticeId) {
    var temp = document.createElement("form");
    temp.action = contextPath + "webindex/goMediaDetail";
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
            if(i == 0){
               html +='<li class="width30 MR5P boxSizing fl firstTr positionR whiteBkg" onclick="goMediaDetail('+ jsonObj.id +')">';
               html +='<div class="clearfix positionR width80 blockC halfLi">';
               html +='<p class="blackTex positionR clearfix textL strong nowrap">'+jsonObj.title+'</p>';
               html +='<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
               html +='<div class="littleShortLine redBkg MT5 MB10"></div>';
               html +='<p class="grayTex61 font16 textL  lineHeight28 positionR clearfix">'+jsonObj.intro+'</p>';
               html +='</div>';
               html +='<div  class="halfImg outHide">';
               html +='<img src="' + jsonObj.smallIconUrl + '" class="imgMedia">';
               html += '</div></li>';
            }else if(i == 1) {
                html +='<li class="width30 MR5P boxSizing fl firstTr positionR whiteBkg" onclick="goMediaDetail('+ jsonObj.id +')">';
                html +='<div  class="halfImg outHide">';
                html +='<img src="' + jsonObj.smallIconUrl + '" class="imgMedia">';
                html += '</div>';
                html +='<div class="clearfix positionR width80 blockC halfLi">';
                html +='<p class="blackTex positionR clearfix textL strong nowrap">'+jsonObj.title+'</p>';
                html +='<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
                html +='<div class="littleShortLine redBkg MT5 MB10"></div>';
                html +='<p class="grayTex61 font16 textL lineHeight28 positionR clearfix">'+jsonObj.intro+'</p>';
                html +='</div>';
                html += '</li>';
            }else if(i == 2) {
                html +='<li class="width30 boxSizing fl firstTr positionR whiteBkg" onclick="goMediaDetail('+ jsonObj.id +')">';
                html +='<div class="clearfix positionR width80 blockC halfLi">';
                html +='<p class="blackTex positionR clearfix textL strong nowrap">'+jsonObj.title+'</p>';
                html +='<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
                html +='<div class="littleShortLine redBkg MT5 MB10"></div>';
                html +='<p class="grayTex61 font16 textL lineHeight28 positionR clearfix">'+jsonObj.intro+'</p>';
                html +='</div>';
                html +='<div  class="halfImg outHide">';
                html +='<img src="' + jsonObj.smallIconUrl + '" class="imgMedia">';
                html += '</div></li>';
            }else if(i == 3) {
                html += '<li class="width30 MR5P boxSizing fl secondTr whiteBkg"  onclick="goMediaDetail('+ jsonObj.id +')">';
                html += '<div class="clearfix positionR width80 blockC halfLi">';
                html += '<p class="blackTex positionR clearfix textL strong nowrap">'+jsonObj.title+'</p>';
                html += '<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
                html += '<div class="littleShortLine redBkg MT5 MB10"></div>';
                html += '<p class="grayTex61 font16 textL lineHeight28 positionR clearfix">'+jsonObj.intro+'</p>';
                html += '</div></li>';
            }else if(i == 4) {
                html += '<li class="width30 MR5P boxSizing fl secondTr whiteBkg"  onclick="goMediaDetail('+ jsonObj.id +')">';
                html += '<div class="clearfix positionR width80 blockC halfLi">';
                html += '<p class="blackTex positionR clearfix textL strong nowrap">'+jsonObj.title+'</p>';
                html += '<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
                html += '<div class="littleShortLine redBkg MT5 MB10"></div>';
                html += '<p class="grayTex61 font16 textL lineHeight28 positionR clearfix">'+jsonObj.intro+'</p>';
                html += '</div></li>';
            }else if(i == 5) {
                html += '<li class="width30 boxSizing fl secondTr whiteBkg">';
                html += '<div class="clearfix positionR width80 blockC halfLi">';
                html += '<p class="blackTex positionR clearfix textL strong nowrap">'+jsonObj.title+'</p>';
                html += '<p class="grayTex61 font12 positionR clearfix">日期：'+jsonObj.createTime+'</p>';
                html += '<div class="littleShortLine redBkg MT5 MB10"></div>';
                html += '<p class="grayTex61 font16 lineHeight28 textL positionR clearfix">'+jsonObj.intro+'</p>';
                html += '</li>';
            }  
        });
    }
    $("#mediaList").append(html);
    $("#mediaListPaging").html(pagingMobile(data, "getMedia"));
    
}

var getMedia = function(page) {
    ajaxRequest(contextPath + "webmedia/getMedia","current="+ page +"&pageSize=6&sign=NEWS",setMedia);
};
$(document).ready(function () {
    getMedia(1)
});