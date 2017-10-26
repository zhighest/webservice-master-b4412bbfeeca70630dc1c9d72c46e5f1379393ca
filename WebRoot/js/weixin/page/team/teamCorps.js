$(function () {
    var userId = $("#userId").val();
    var teamName = $("#teamName").val();
    var shareTeamId = $("#teamId").val(); //分享过来的teamId
    //var mobile = $("#mobile").val();
    var channel = $("#channel").val();
    //var ua = navigator.userAgent.toLowerCase();
    var jionTeam = false, creatTeam = false; //加入、创建战队
    var invitationCode = $("#invitationCode").val();
    var teamId; //战队详情接口参数

    //alert($('#channel').val());
    $('.corpsTitle').text('战队编号: ' + teamName + '邀约');
    ajaxRequest(contextPath + "base/getMetaInfo", "name=teamRule", teamRule);  //组队投资 活动规则
    ajaxRequest(contextPath + "team/queryUserIsJoinTeam", "userId=" + userId, queryUserIsJoinTeam);  //组队投资查询用户是否已组队
    ajaxRequest(contextPath + "team/queryTeamInfo", "teamId=" + shareTeamId + "&type=2", queryTeamInfo);  //战队成员列表

    $(".rewardBox").click(function () { // 活动奖励
        window.location.href = contextPath + 'team/teamReward';
    });

    $('#setTeam').on('click', function () { //加入战队

        isUserId("您确定加入战队吗？<br/>一旦加入，不可退出", "jionTeam");

    });

    $('#teamRank').on('click', function () { //创建战队
        isUserId("您确定创建战队吗？<br/>一旦创建，不可退出", "creatTeam");
    });

    $("#looktTeam").on('click', function () { //查看我的战队
        window.location.href = contextPath + "team/goTeamDetail?teamId=" + teamId + "&userId=" + userId; //战队详情页
    });

    $(".cancelBtn").click(function () {  //取消弹框
        jionTeam = false;
        creatTeam = false;
        $(".bounceWrap").addClass("none");
    });
    $("#closeConfirmBox").click(function () {  //关闭弹框
        jionTeam = false;
        creatTeam = false;
        $(".bounceWrap").addClass("none");
    });

    $(".confirmBtn").click(function () {  //  确认弹框 加入战队、创建战队

        if (jionTeam) { //加入战队
            console.log('加入战队');

            ajaxRequest(contextPath + "team/joinTeam", "userId=" + userId + "&teamId=" + shareTeamId + "&teamName=" + teamName, jionTeamData); //加入战队
            $(".bounceWrap").addClass("none");  //  弹框消失
            jionTeam = false;

        } else if (creatTeam) { //创建战队
            console.log('创建战队');

            ajaxRequest(contextPath + "team/creatTeam", "userId=" + userId, creatTeamData); //创建战队

            $(".bounceWrap").addClass("none");  //  弹框消失
            creatTeam = false
        }

    });

    function isUserId(text, team) { //是否已登录
        if (userId) { //已登录

            if (team == 'jionTeam') {
                jionTeam = true;
            } else if (team == 'creatTeam') {
                creatTeam = true;
            }

            $("#confirmBox").html(text);
            $(".bounceWrap").removeClass("none");

        } else { //未登录
            if (team == 'jionTeam') {
                window.location.href = contextPath + 'team/teamLogin?teamId=' + shareTeamId + "&teamName=" + teamName + "&channel=" + channel + "&jionTeam=jionTeam" + "&invitationCode=" + invitationCode; //登录页
            } else if (team == 'creatTeam') {
                window.location.href = contextPath + 'team/teamLogin?teamId=' + shareTeamId + "&teamName=" + teamName + "&channel=" + channel + "&creatTeam=creatTeam" + "&invitationCode=" + invitationCode; //登录页
            }

        }
    };

    function teamRule(data) {  //组队投资 活动规则
        //console.log(data);
        if (data.rescode == "00000") {
            $(".scrollTex").html(data.content.role);
            $(".teamTime").text('活动日期: ' + data.content.activityTime);
        } else {
            errorMessage(data.content.resmsg_cn);
        }
    };
    function queryUserIsJoinTeam(data) { //是否已有战队
        console.log(data);
        if (data.rescode == "00000") {
            if (data.isJoinTeam == "N") {  //  没有战队
                teamId = shareTeamId;
            } else if (data.isJoinTeam == "Y") { // 已有战队

                teamId = data.teamId;

                if (teamId == shareTeamId) { //属于该战队
                    $("#looktTeam").css({"display": "block", "marginLeft": "27.5%"});
                    $("#setTeam").css({"display": "none"});
                    $("#teamRank").css({"display": "none"});
                }
            }

        } else {
            //errorMessage(data.resmsg_cn);
        }
    };
    function queryTeamInfo(data) { //页面初始化获取战队成员列表
        console.log(data);
        if (data.rescode == "00000") {
            var recordsData = data.records;

            for (var i = 0; i < 8; i++) {
                var html = "";

                if (i < recordsData.length) {

                    if (recordsData[i].img == '') {
                        var userIcon = recordsData[i].img;  //用户头像
                    } else {
                        var userIcon = recordsData[i].img.replace(/http:\/\//, "https://");    // 用户头像

                    }
                    /*if (userIcon == "") { //默认头像
                     userIcon = contextPath + '/pic/weixin/version1125/defaultAvatar.png';
                     }*/
                    var userName = recordsData[i].name;

                    html += '<li class="fl width18 textC positionR">';
                    if (i == 0) {
                        html += '<img class="positionA captain" src="' + contextPath + 'pic/weixin/activity/team/captain.png" />'
                    }
                    if (i < recordsData.length && userIcon == "") { //默认头像
                        userIcon = contextPath + 'pic/weixin/version1125/defaultAvatar.png';
                    }

                    html += '<img class="width100" src="' + userIcon + '" />';
                    html += '<span>' + userName + '</span>';
                    html += '</li>';
                } else {
                    html += '<li class="fl width18 textC positionR">';
                    html += '<img class="width100" src="' + contextPath + 'pic/weixin/activity/team/figure.png" />';
                    html += '<span>等你加入</span>';
                    html += '</li>';
                }

                $(".teamMembers").append(html);
            }
        } else {
            errorMessage(data.resmsg_cn);
        }
    };

    function jionTeamData(data) { //加入战队
        console.log('jionTeam =>', data);
        if (data.rescode == "00000") {
            errorMessage(data.resmsg_cn);
            window.location.href = contextPath + "team/goTeamDetail?teamId=" + teamId + "&userId=" + userId; //战队详情页
        } else {
            errorMessage(data.resmsg_cn);
        }

        $(".bounceWrap").addClass("none");  //  弹框 消失
    }

    function creatTeamData(data) { //创建战队
        console.log('creatTeam =>', data);
        if (data.rescode == "00000") {
            errorMessage(data.resmsg_cn);

            teamId = data.team.id;
            window.location.href = contextPath + "team/goTeamDetail?teamId=" + teamId + "&userId=" + userId; //战队详情页
        } else {

            errorMessage(data.resmsg_cn);
        }
        $(".bounceWrap").addClass("none");  //  弹框 消失
    }

});
