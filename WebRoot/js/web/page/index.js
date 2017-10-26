//媒体报道点击事件
var goMediaDetail = function (noticeId) {
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

// 鼠标选中媒体报道，左侧进行展示
var meadiaHover = function (title, createTime, smallIconUrl, id) {
    $("#mediaP").html(title);
    $("#mdCreateTime").html(createTime);
    $("#mdShowImg").attr("src", smallIconUrl);
    $(".mediaShowCon").attr("onclick", "goMediaDetail('" + id + "')");//整体需要点击效果
    // $(this).addClass('current'); 
}
//最新活动点击事件
var goActivityDetail = function (noticeId) {
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
//网站手机访问跳到下载页面
/*var ua = navigator.userAgent.toLowerCase();
 if(/android/i.test(navigator.userAgent)){
 window.location.href = contextPath + "webabout/aboutUs?hasHeadFoot=index";
 }else if (/ipad|iphone/i.test(navigator.userAgent)){
 window.location.href = contextPath + "webabout/aboutUs?hasHeadFoot=index";
 }*/
//短信验证码输入时登录按钮状态
var checkCode;
var checkC = function () {
    checkCode = $("#checkCode").val();
    if (checkCode == "") {
        $("#loginSubmit").addClass("forbid");
        loginSubmitSwitch = false;
    } else {
        $("#loginSubmit").removeClass("forbid");
        loginSubmitSwitch = true;
    }
    ;
};
var pwd;
var checkD = function () {
    pwd = $("#password").val();
    if (pwd == null || pwd == "") {
        $("#loginSubmitPW").addClass("forbid");
        goLoginBtnPW = false;
    } else {
        $("#loginSubmitPW").removeClass("forbid");
        goLoginBtnPW = true;
    }
    ;
};
//数字滚动插件
var options = {
    useEasing: true,
    useGrouping: true,
    separator: ',',
    decimal: '.',
    prefix: '',
    suffix: ''
};

//理财产品标的详情 begin
var setProductLoanList = function (data) {
    var htmlOdd = "";//手机端奇数
    var htmlEven = "";//手机端偶数
    var html = '';
    var moreHtml = '';
    moreHtml += '<li class="showBoxLi fl positionR clearfix block">';
    moreHtml += '<div class="showLiDiv clearfix redBkg whiteTex blockC">';
    moreHtml += '<h3 class="font20 textC lineHeight2x">敬&nbsp请</br>期&nbsp待</span></h3></div>';
    moreHtml += '<p class="MT10 font14 textC">敬请期待</p>';
    var listSize = data.listSize;
    if (data.list == null || data.list == "") {

    } else {
        $.each(data.list, function (i, jsonObj) {
            // 将理财产品写入指定的位置 begin
            var planId = jsonObj.planId;
            var remanDays = jsonObj.remanDays;
            var remanPeriods = jsonObj.remanPeriods;
            var annualRate = jsonObj.annualRate;//收益率
            var showName = jsonObj.showName;//产品名称
            var investMinAmount = jsonObj.investMinAmount;//起投金
            var finishedRatio = jsonObj.finishedRatio;//已抢百分比
            if (jsonObj.openSelling == "Y") {
                html += "<li class='showBoxLi fl positionR clearfix block cursor' onclick=\"productAlertBox('" + annualRate + "','" + showName + "','" + planId + "','" + remanDays + "','" + remanPeriods + "','" + investMinAmount + "','" + finishedRatio + "')\">";
                html += '<div class="showLiDiv clearfix positionR redBkg whiteColor blockC">';
                var rateLength = annualRate.toString().length;
                html += '<div style="height:50px;">';
                if (jsonObj.planId == "107") {//优享
                    if (rateLength <= 3) {
                        html += '<h3 class="font14 textC"><span class="helveticaneue productNam1">' + annualRate + '</span>%</h3>';
                    } else {
                        html += '<h3 class="font14 textC"><span class="textC helveticaneue productNam2" >' + annualRate + '</span>%</h3>';
                    }
                } else {
                    if (rateLength <= 3) {
                        html += '<h3 class="font14 textC"><span class="helveticaneue productNam1">' + annualRate + '</span>%</h3>';
                    }
                    else {
                        html += '<h3 class="font14 textC"><span class="textC helveticaneue productNam3" >' + annualRate + '</span>%</h3>';
                    }
                }
                html += '</div><p class="font12 textC lineHeight100 productP">年化收益率</p></div>';
                html += '<div class="clearfix positionR">';
                html += '<p class="MT10 font14 textC">' + showName + '</p>';
                if (jsonObj.planId == "107") {//优享
                    html += '<p class="MT5 font12 textC minHeight">&nbsp</p>';
                } else if (jsonObj.planId == "109") {//活期
                    html += '<p class="MT5 font12 textC minHeight" style="color:#a3a3a3"><!--锁定期限0天--><span>' + investMinAmount + '</span>起投</p>';
                } else {//定期
                    if (jsonObj.remanDays == "0") {
                        html += '<p class="MT5 font12 textC minHeight" style="color:#a3a3a3">锁定期限' + jsonObj.remanPeriods + '个月<span>' + jsonObj.investMinAmount + '</span>起投</p>';
                    } else {
                        html += '<p class="MT5 font12 textC minHeight" style="color:#a3a3a3">锁定期限' + jsonObj.remanDays + '天<span>' + jsonObj.investMinAmount + '</span>起投</p>';
                    }
                    ;
                }
                html += '</div></li>';
            }
            else {
                html += '<li class="showBoxLi fl block">';
                html += '<div class="showLiDiv clearfix redBkg whiteTex blockC">';
                html += '<h3 class="font20 textC lineHeight2x">敬&nbsp请</br>期&nbsp待</span></h3></div>';
                html += '<p class="MT10 font14 textC">敬请期待</p>';
                html += '</li>';
            }
        })
        // window.addEventListener("resize",setClientStyle,false);
        // 盛放标的的盒子的属性

        var ulW = $("#productListUl").width();
        var liW = ulW / 2;
        var totalLiW = liW * listSize;
        $("#showLiBox").css('width', totalLiW);
        var pageNum = ~~(listSize / 4) + (~~(listSize % 4) == 0 ? 0 : 1);//每页放4个，求出一共几页
        var n = 0;
        //上一张按钮
        $(".goLeftBtn").click(function () {
            n -= 1;
            if (n == -1) {
                n = pageNum - 1;
            }
            showPic(n);
        });
        //下一张按钮
        $(".goRightBtn").click(function () {
            n += 1;
            if (n == pageNum) {
                n = 0;
            }
            showPic(n);
        });
        function showPic(n) {
            var nowLeft = -n * liW;
            $("#showLiBox").stop(true, false).animate({"left": nowLeft}, 300);
        }

        if (pageNum == 1) {
            $(".goLeftBtn").hide();
            $(".goRightBtn").hide();
            if (listSize < 3) {
                $("#showLiBox").append(html + moreHtml + moreHtml);
            } else if (listSize < 4) {
                $("#showLiBox").append(html + moreHtml);
            } else if (listSize = 4) {
                $("#showLiBox").append(html);
            }
        } else if (pageNum > 1) {
            $(".goLeftBtn").show();
            $(".goRightBtn").show();
            $("#showLiBox").append(html);
        }
        // 将理财产品写入指定的位置 end  
        $("#productListP").append(html);
    }

}
//理财产品标的详情 end
// 点击理财产品的弹窗事件 begin
var productAlertBox = function (annualRate, showName, planId, remanDays, remanPeriods, investMinAmount, finishedRatio) {
    setTimeout(function () {
        if (planId == "109") {
            $("#investPeriod").html("0天");
            $("#minDays").hide();
        } else if (planId == "108") {
            if (remanDays == "0") {
                $("#investPeriod").html(remanPeriods + "个月");
            } else {
                $("#investPeriod").html(remanDays + "天");
            };
            $("#minDays").show();
        } else {
            $("#minDays").hide();
            $("#investPeriod").html(remanDays + "天");
        }
    }, 200)
    $("#productAlertBox").stop().fadeIn(300);
    $('#listDetail').removeClass('animated bounceIn bounceOutUp');
    $('#listDetail').addClass('animated bounceIn').fadeIn();
    $("#annualRate").html(annualRate);//年化收益
    $("#showName").html(showName);//标的名
    $("#investMinAmount").html(parseFloat(investMinAmount) + "元");//起投金额
    var sellRate = finishedRatio;
    $("#sellRate").html(sellRate);
    //数字滚动插件
    var countAnimate = new CountUp("sellRate", 0, sellRate, 0, 2, options);
    countAnimate.start();
}
// 点击理财产品的弹窗事件 end
//标的详情关闭
$("#closeIcon").click(function (event) {
    //$('#listDetail').removeClass('bounceOutUp');
    $('#productAlertBox').fadeOut(300);

});
$("#closeBtn").click(function (event) {
    $("#productAlertBox").stop().hide();
});

//最新活动详情展示 begin
var setActivity = function (data) {
    var html = ""; //网站端拼接html
    if (data.list == null || data.list == "") {

    } else {
        $.each(data.list,
            function (i, jsonObj) {
                html += '<li class="acLi blockC" onclick="goActivityDetail(' + jsonObj.id + ')">';
                html += '<div class="activityImgBox blockC positionR pointer">';
                html += '<div class="activityCheckMore">';
                html += '<div class="blockC whiteTex radius5 textC atCheckMoreBtn cursor">查看更多</div></div>';
                html += '<img src="' + jsonObj.smallIconUrl + '">';
                html += '</div>';
                html += '<div class="activityPBox blockC whiteBkg textC">';
                html += '<p class="font16 blackTex aboutUsP">' + jsonObj.title + '</p>';
                html += '<p class="font12 grayTex61 aboutUsP">' + jsonObj.intro + '</p>';
                html += '</div>';
                html += '</li>';
            });
    }
    var listSize = data.list.length;
    var pageNum = ~~(listSize / 3) + (~~(listSize % 3) == 0 ? 0 : 1);//每页放3个，求出一共几页
    var n = 0;
    //上一张按钮
    $(".goLeftBtnA").click(function () {
        n -= 1;
        if (n == -1) {
            n = pageNum - 1;
        }
        showPic(n);
    });
    //下一张按钮
    $(".goRightBtnA").click(function () {
        n += 1;
        if (n == pageNum) {
            n = 0;
        }
        showPic(n);
    });
    function showPic(n) {
        var nowLeft = -n * 360;
        $("#activityListBox").stop(true, false).animate({"left": nowLeft}, 300);
    }

    if (pageNum == 1) {
        $(".checkMore").hide();
    } else if (pageNum > 1) {
        $(".checkMore").show();
    }
    $("#activityListBox").append(html);
    $("#activityListP").append(html);
};
//最新活动详情展示 end
//媒体报道详情展示 begin
function setMedia(data) {
    var html = ""; //拼接html
    $("#mdShowImg").attr("src", data.list[0].smallIconUrl);
    $("#mediaP").html(data.list[0].title);
    $("#mdCreateTime").html(data.list[0].createTime);
    $(".mediaShowCon").attr("onclick", "goMediaDetail('" + data.list[0].id + "')");
    if (data.list == null || data.list == "") {

    } else {
        $.each(data.list,
            function (i, jsonObj) {

                html += "<li class='cursor' onclick=goMediaDetail(" + data.list[i].id + ") onmouseover=\"meadiaHover('" + jsonObj.title + "','" + jsonObj.createTime + "','" + jsonObj.smallIconUrl + "','" + jsonObj.id + "')\">";

                html += '<h5>' + jsonObj.title + '</h5>';
                html += '<p>' + jsonObj.createTime + '</p></li>';
            });
    }
    $("#mediaShowList").html(html);
};
//媒体报道详情展示 end
//
//判断IE浏览器版本
var _IE = (function () {
    var v = 3, div = document.createElement('div'), all = div.getElementsByTagName('i');
    while (
        div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->',
            all[0]
        );
    return v > 4 ? v : false;
}());

//移动端banner轮播
var setBannerList = function () {
    var bannerHtml = '';
    $("#bannerList figure").each(function (i, jsonObj) {
        if (i == 0) {
            bannerHtml += '<li class="on"></li>';
        } else {
            bannerHtml += '<li></li>';
        }
    });
};

var timer = null,
    autoRun,//轮播图定时器
    curScrollTop,//滚动高度
    winHeight = $(window).height(),//屏幕高度
    stopHeight = winHeight * 0.5,
    offsetTop1 = $(".fourScreen").offset().top;

$(document).ready(function () {
    swiperFun();
    var widthScreen = $(window).width();
    var windowHeight = $(window).height();
    var height1 = $('.addShow5').offset().top;
    if (widthScreen >= 768) {
        $(window).scroll(function (event) {
            var scrollTop = $(window).scrollTop();
            if (scrollTop >= height1 - windowHeight) {
                $(".addShow5").addClass('show1');
                $(".addShow6").addClass('show2');
            } else {
                $(".addShow5").removeClass('show1');
                $(".addShow6").removeClass('show2');
            }
        });
    }
    //获取个人账户信息
    $("#totalAssets").html(numFormat($("#totalAssets1").val())); //账户总额
    $("#incomeAmount").html(numFormat($("#incomeAmount1").val())); //累计收益
    $("#balanceMoney").html(numFormat($("#balanceMoney1").val())); //账户余额

    ajaxRequest(contextPath + "wxproduct/getProductLoanListWeb", "", setProductLoanList);//理财产品标的接口
    ajaxRequest(contextPath + "webmedia/getMedia", "current=1&pageSize=4&sign=ACTIVITY", setActivity);//最新动态详情接口
    //判断是否登录
    var webIsLogIn = $("#webIsLogIn").val();
    if (webIsLogIn == "true") {//已登录
        $(".goLeft").hide();
        $(".goRight").hide();
        $("#login").stop().show();
        $("#noLogin").stop().hide();
    } else {//未登录
        $(".goLeft").show();
        $(".goRight").show();
        $("#login").stop().hide();
        $("#noLogin").stop().show();
    }

    //判断移动端、PC端
    if ($(window).width() > 768) {
        //PC端轮播图
        if ($(".slideInner a").length > 1) {
            $(".slideInner").slide({
                slideContainer: $('.slideInner a'),
                effect: 'easeOutCirc',
                autoRunTime: 5000,//每一帧的轮播时间
                slideSpeed: 1000,
                nav: true,
                autoRun: true,
                prevBtn: $('a.prev'),
                nextBtn: $('a.next')
            })
        }
        //pc端第一屏效果 超出第一屏轮播停止
        $(window).scroll(function (event) {
            curScrollTop = $(document).scrollTop();
            if ($(".slideInner a").length > 1) {
                if (curScrollTop >= stopHeight) {
                    clearInterval(timer);
                } else {
                    autoRun();
                }
                ;
            }
        });
        //判断火狐浏览器
        var explorer = navigator.userAgent;
        if (explorer.indexOf("Firefox") >= 0) {
            $(window).trigger("scroll");
        }
        ;
        if (_IE == "6" || _IE == "7" || _IE == "8" || _IE == "9") {
            $(".reverse").stop().hide();
        }
    } else {
        //移动端轮播图
        setBannerList();
    }
    ajaxRequest(contextPath + "webmedia/getMedia", "current=1&pageSize=3&sign=NEWS", setMedia);
});
//PC端轮播下载APP
$("#goDownApp").click(function (event) {
    window.location.href = contextPath + 'webabout/goDownloadDetails';
});
//移动端轮播下载APP
$("#goDownAppPhone").click(function (event) {
    window.location.href = contextPath + 'webabout/goDownloadDetails';
});
//底部下载浮层关闭
$("#closed").click(function () {
    $("#areaFix").hide();
})
// 合作伙伴轮播和滑动
function swiperFun() {
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true
    });
}

