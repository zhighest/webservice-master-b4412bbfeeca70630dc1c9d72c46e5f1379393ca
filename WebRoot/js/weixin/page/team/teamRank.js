/**
 * Created by huamin on 2017/9/4.
 */
$(document).ready(function(){
    ajaxRequest(contextPath + "team/queryTeamList","current=1&pageSize=20", queryTeamList);
});

function queryTeamList(data){
    if(data.rescode == "00000"){
        console.log(data);
        if(data.totalNum <= 0){   //  少于20个战队  不显示战队排名
            var htmlTop = "";
            htmlTop += '<li class="fl width33_3 font12">';
            htmlTop += '<img src="'+contextPath+'pic/weixin/activity/team/second.png" />';
            htmlTop += '</li>';
            htmlTop += '<li class="fl width33_3 font12">';
            htmlTop += '<img src="'+contextPath+'pic/weixin/activity/team/champion.png" />';
            htmlTop += '</li>';
            htmlTop += '<li class="fl width33_3 font12">';
            htmlTop += '<img src="'+contextPath+'pic/weixin/activity/team/bronze.png" />';
            htmlTop += '</li>';
            $(".rankList").append(htmlTop);
            var html = "";
            html += '<li><img class="PT20P trophies" src="'+contextPath+'/pic/weixin/activity/team/trophies.png" />';
            html += '<p class="PLR5P font12 grayTex heigh50">投资排行榜生成中，稍后查看...</p>';
            html += '</li>';
            $(".moreRankList").append(html);
            $(".tipsTex").addClass("none");
        }else{
            for(var i = 0; i < data.records.length; i++){
                var teamName = data.records[i].teamName;
                var ranking = data.records[i].ranking;
                var investAmount = data.records[i].investAmount;
                var htmlImg = "";
                if( i <= 2 && data.totalNum >=4 && data.pageObject.pageNum == 1 ){   //  当战队多于或等于4个战队时在图片中 显示排名
                    htmlImg += '<li class="fl width33_3 font12">';
                    if( i == 0 ){
                        htmlImg += '<img src="'+contextPath+'pic/weixin/activity/team/champion.png" />';
                    }else if( i == 1 ){
                        htmlImg += '<img src="'+contextPath+'pic/weixin/activity/team/second.png" />';
                    }else{
                        htmlImg += '<img src="'+contextPath+'pic/weixin/activity/team/bronze.png" />';
                    }
                    htmlImg += '<p class="rankNum whiteTex">'+ranking+'</p>';
                    htmlImg += '<p class="teamName whiteTex">战队编号: '+teamName+'</p>';
                    htmlImg += '<p class="teamMoney">'+investAmount+'元</p></li>';
                    if( i == 0 ){
                        $(".rankList").append(htmlImg);
                    }else if( i == 1 ){
                        $(".rankList").prepend(htmlImg);
                    }else{
                        $(".rankList").append(htmlImg);
                    }
                }else{
                    if(i == 0 && data.pageObject.pageNum == 1){  //  加载一次
                        var htmlTop = "";
                        htmlTop += '<li class="fl width33_3 font12">';
                        htmlTop += '<img src="'+contextPath+'pic/weixin/activity/team/second.png" />';
                        htmlTop += '</li>';
                        htmlTop += '<li class="fl width33_3 font12">';
                        htmlTop += '<img src="'+contextPath+'pic/weixin/activity/team/champion.png" />';
                        htmlTop += '</li>';
                        htmlTop += '<li class="fl width33_3 font12">';
                        htmlTop += '<img src="'+contextPath+'pic/weixin/activity/team/bronze.png" />';
                        htmlTop += '</li>';
                        $(".rankList").append(htmlTop);
                    }
                    htmlImg += '<li class="clearfix font14 borderB heigh50 PLR5P textL">';
                    htmlImg += '<span class="moreNum inlineB">'+ranking+'</span>';
                    htmlImg += '<span class="moreName blackTex inlineB textC">战队编号: '+teamName+'</span>';
                    htmlImg += '<span class="moreMoney inlineB textR">'+investAmount+'元</span></li>';
                    $(".moreRankList").append(htmlImg);
                }

            }
        }
        $("#exchangeListPaging").html(pagingMobile(data, "getMoreTeamList"));
    }else{
        errorMessage(data.resmsg_cn);
    }
}

var getMoreTeamList = function(page) { //获取更多
    ajaxRequest(contextPath + "team/queryTeamList","current=" + page + "&pageSize=20", queryTeamList);
}

$(".rewardBox").click(function(){   // 活动奖励
    window.location.href = contextPath + 'team/teamReward';
});