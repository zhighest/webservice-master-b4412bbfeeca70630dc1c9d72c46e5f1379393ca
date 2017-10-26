$(function(){
    ajaxRequest(contextPath + "base/getMetaInfo", "name=teamPrizeInfo", teamPrizeInfo);  //组队投资 活动规则


    function teamPrizeInfo(data){
        console.log(data);
        if(data.rescode == "00000"){
            $(".rewardOne").html(data.content[0].one);
            $(".rewardTwo").html(data.content[1].two);
            $(".rewardThree").html(data.content[2].three);
            $(".rewardFour").html(data.content[3].four);
            $("#text").html(data.content[4].five);
        }else{
            errorMessage(data.resmsg_cn);
        }
    }
});