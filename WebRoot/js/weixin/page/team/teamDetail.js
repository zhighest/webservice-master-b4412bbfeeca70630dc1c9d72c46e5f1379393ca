/**
 * Created by huamin on 2017/9/1.
 */
var userId;
var channel;
var teamId;
var teamName;
var my_host = window.location.host;  //  获取当前域名
var teamLength;  //  队员长度
var invitationCode;  //  邀请手机号码
var shareName;

window.onload = function () {

    userId = $("#userId").val();
    teamId = $("#teamId").val();
    channel = $("#channel").val();
    ajaxRequest(contextPath + "team/queryTeamInfo", "teamId=" + teamId + "&type=2", queryTeamInfoTime);  //组队成员列表  按加入战队时间排序

    $.ajax({
        url: contextPath + "team/queryTeamInfo",     //组队 投资数量排名
        type: "GET",
        dataType: "json",
        data: "teamId=" + teamId + "&type=1",
        success: function (data) {
            queryTeamInfoRank(data);
        }
    });

    

}

function queryTeamInfoRank(data) {
    if (data.rescode == "00000") {
        if (data.ranking == "" || data.ranking == "null" || data.ranking == undefined || data.ranking == null) {
            $(".rankingP").addClass("none");
        } else {
            $(".teamRank").html(data.ranking);
        }
        for (var i = 0; i < data.records.length; i++) {
            var proportion = parseFloat(data.records[i].proportion);  // 投资占比
            if(data.records[i].img == ""){
                var userImg = data.records[i].img;
            }else{
                var userImg = data.records[i].img.replace(/http:\/\//,"https://");    // 用户头像
            }
            if (i < data.records.length && userImg == "" ) {
                userImg = contextPath + 'pic/weixin/version1125/defaultAvatar.png';
            } else if(userImg == ""){
                userImg = contextPath + '/pic/weixin/activity/team/figure.png';
            }

            var userName = data.records[i].name;  // 用户名称
            var html = '';
            html += '<li class="clearfix PLR5P PT20">';
            html += '<img class="fl figureImg radius5" src="' + userImg + '" />';
            html += '<div class="fl PL15 teamInfo textL positionR">';
            html += '<p class="font14 PTB2">' + userName + '</p>';
            if (proportion > 0 && i == 0) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/fiveStar.png" />';
            } else if (proportion > 0 && i == 1) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/fourStar.png" />';
            } else if (proportion > 0 && i == 2) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/threeStar.png" />';
            } else if (proportion > 0 && i == 3) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/twoStar.png" />';
            } else if (proportion > 0 && i == 4) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/oneStar.png" />';
            } else if (proportion > 0 && i == 5) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/oneStar.png" />';
            } else if (proportion > 0 && i == 6) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/oneStar.png" />';
            } else if (proportion > 0 && i == 7) {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/oneStar.png" />';
            } else {
                html += '<img class="levelStar positionA" src="' + contextPath + '/pic/weixin/activity/team/noneStar.png" />';
            }
            html += '</div>';
            if (proportion > 0 && i == 0) {
                html += '<img class="inRank fr PTB3" src="' + contextPath + '/pic/weixin/activity/team/one.png" />';
            } else if (proportion > 0 && i == 1) {
                html += '<img class="inRank fr PTB3" src="' + contextPath + '/pic/weixin/activity/team/two.png" />';
            } else if (proportion > 0 && i == 2) {
                html += '<img class="inRank fr PTB3" src="' + contextPath + '/pic/weixin/activity/team/three.png" />';
            } else {
                html += '<p class="font14 redTex fr PTB5P ' + "rank" + (i + 1) + '">第' + (i + 1) + '名</p>';
            }
            html += '</li>';

            $(".rankList").append(html);
        }
    } else {
        errorMessage(data.resmsg_cn);
    }
}

function queryTeamInfoTime(data) {
    if (data.rescode == "00000") {
        teamName = data.teamName;
        teamLength = data.records.length;
        for (var i = 0; i < 8; i++) {
            var html = "";
            if (i < data.records.length) {
                if (userId == data.records[i].userId + '') {
                    invitationCode = data.records[i].mobile;
                    if( isNaN(data.records[i].name) ){
                        shareName = data.records[i].name;
                    }else{
                        shareName = data.records[i].mobile.substring(0,3) + "****" + data.records[i].mobile.substring(7,11);
                    }
                }
                //当前战队只有一位成员的时候不显示当前战队排名
                if (data.records.length == 1) {
                    $(".nowRank").addClass("none");
                }
                var userName = data.records[i].name;
                if(data.records[i].img == ""){  //  用户头像
                    var userIcon = data.records[i].img;
                }else{
                    var userIcon = data.records[i].img.replace(/http:\/\//,"https://");    // 用户头像
                }
                html += '<li class="fl MT10 positionR">';
                if (i == 0) {
                    html += '<img class="captain" src="' + contextPath + 'pic/weixin/activity/team/captain.png" />'
                }
                if (i < data.records.length && userIcon == "") {
                    userIcon = contextPath + 'pic/weixin/version1125/defaultAvatar.png';
                } else if(userIcon == ""){
                    userIcon = contextPath + '/pic/weixin/activity/team/figure.png';
                }
                html += '<img class="radius5" src="' + userIcon + '" />';
                html += '<p class="grayTex font12">' + userName + '</p>';
                html += '</li>';
            } else {
                html += '<li class="fl MT10 positionR">';
                var userIcon = "";  //  用户头像
                if (i == 0) {
                    userIcon = contextPath + 'pic/weixin/version1125/defaultAvatar.png';
                } else {
                    userIcon = contextPath + '/pic/weixin/activity/team/figure.png';
                }
                html += '<img class="radius5" src="' + userIcon + '" />';
                html += '<p class="grayTex font12">我的队员</p>';
                html += '</li>';
            }
            $(".teamMembers").append(html);
        }
    } else {
        errorMessage(data.resmsg_cn);
    }

}

$(".rewardBox").click(function () {   // 活动奖励
    window.location.href = contextPath + 'team/teamReward';
});

$(".moreTeam").click(function () {   // 战队排行榜
    window.location.href = contextPath + 'team/teamRank';
});

//取消分享点击事件
$(".sharePopup").on("click", function () {
    $(this).hide();
});

$(".strongTeam").click(function () {  //  //分享微信好友和朋友圈  壮大战队
    if( teamLength == 8 ){
        errorMessage("战队人数已达上限");
        return false;
    }
    var shareTitle = '您的好友' + shareName + '邀您加入战队，分享1万元大奖';
    var shareDesc = '您的好友' + shareName + '正在参加联璧金融组队投资活动，快去围观';
    // 分享出去的 相关
    var shareImgUrl = bathPath + "/pic/weixin/activity/team/shareLogo.png";  // 分享出去的图片
    var shareLink = getUrlHttp() + my_host + '/team/teamCorps?teamId=' + teamId + '&teamName=' + teamName + '&channel=' + channel + '&invitationCode=' + invitationCode;
    if (channel == "LBAndroid") {
        if (window._cordovaNative) {
            window._cordovaNative.webContentShare(shareTitle, shareDesc, shareLink, shareImgUrl);
        }
    } else if (channel == "LBIOS") {
        webContentShare(shareTitle, shareDesc, shareLink, shareImgUrl);
    } else {
        $(".sharePopup").show();
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        wx.onMenuShareTimeline({
            title: shareTitle, // 分享标题
            desc: shareDesc, // 分享描述
            link: shareLink, // 分享链接
            imgUrl: shareImgUrl, // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
                $(".sharePopup").hide();
                errorMessage("分享成功");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                errorMessage("未分享");
            }
        });
        //获取“分享给朋友”按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title: shareTitle, // 分享标题
            desc: shareDesc, // 分享描述
            link: shareLink, // 分享链接
            type: 'link', // 分享类型,music、video或link，不填默认为link
            imgUrl: shareImgUrl, // 分享图标
            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
            success: function () {
                // 用户确认分享后执行的回调函数
                $(".sharePopup").hide();
                errorMessage("分享成功啦");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                errorMessage("未分享");
            }
        });
    }
});

