/**
 * Created by huamin on 2017/9/1.
 */
var teamId;  //  战队Id
var mobile;
var channel;
var userId;
window.onload = function(){

    ajaxRequest(contextPath + "base/getMetaInfo", "name=teamRule", teamRule);  //组队投资 活动规则

    mobile = $("#mobile").val();  //APP
    if(mobile == "null"){
        mobile = "";
    }
    channel = $("#channel").val();
    userId = $("#userId").val();
    var ua = navigator.userAgent.toLowerCase();

    if(userId == "0" || userId == "null" || userId == ""){
        $(".setTeam").removeClass("none");
    }

    ajaxRequest(contextPath + "team/queryUserIsJoinTeam", "mobile="+mobile, queryUserIsJoinTeam);  //组队投资查询用户是否已组队

    $(".setTeam").click(function(){  // 创建战队
        if(channel == "LBAndroid" && (userId == "0" || userId == "null" || userId == "")){	// 安卓
            // if(window._cordovaNative){
            //     window._cordovaNative.goLogin();
            // }
            errorMessage("登录后组队投资可赚奖励哟");
        }else if(channel == "LBIOS" && (userId == "0" || userId == "null" || userId == "")){	// ios
            errorMessage("登录后组队投资可赚奖励哟");
        }else if(userId == "0" || userId == "null" || userId == ""){       // 微信
            errorMessage("登录后组队投资可赚奖励哟");
            // window.location.href = contextPath+"wxuser/goLogin"
        }else{
            $(".bounceWrap").removeClass("none");
        }
    });

    $(".cancelBtn").click(function(){  //取消 弹框
        $(".bounceWrap").addClass("none");
    });

    $(".confirmBtn").click(function(){  //  确认 创建团队
        ajaxRequest(contextPath + "team/creatTeam", "userId="+userId, creatTeam);  //创建战队
    });

    $(".lookTeam").click(function(){
        window.location.href = contextPath+"team/goTeamDetail?userId="+userId+"&teamId="+teamId+"&channel="+channel+"&mobile="+mobile;
    });

}

function teamRule(data){  //组队投资 活动规则
    if(data.rescode == "00000"){
        $(".scrollTex").html(data.content.role);
        $(".teamTime").html('活动日期： '+data.content.activityTime);
    }else{
        errorMessage(data.resmsg_cn);
    }
}

function queryUserIsJoinTeam(data){
    if(data.rescode == "00000"){
        if(data.isJoinTeam == "N"){  //  没有战队  显示“创建战队” 按钮
            $(".setTeam").removeClass("none");
        }else if(data.isJoinTeam == "Y"){ // 已有战队  显示“查看我的战队” 按钮
            $(".lookTeam").removeClass("none");
            teamId = data.teamId;
        }
    }else{
        errorMessage(data.resmsg_cn);
    }
}


function creatTeam(data){
    if(data.rescode == "00000"){
        teamId = data.team.id;
        window.location.href = contextPath+"team/goTeamDetail?userId="+userId+"&teamId="+teamId+"&channel="+channel+"&mobile="+mobile;
    }else{
        errorMessage(data.resmsg_cn);
    }
    $(".bounceWrap").addClass("none");  //  弹框 消失
}

$(".rewardBox").click(function(){   // 活动奖励
    window.location.href = contextPath + 'team/teamReward';
});

$(".teamRank").click(function(){   // 战队排行榜
    window.location.href = contextPath + 'team/teamRank';
});




