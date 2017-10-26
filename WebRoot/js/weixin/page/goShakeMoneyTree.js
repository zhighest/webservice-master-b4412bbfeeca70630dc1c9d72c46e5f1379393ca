window.onload=function(){
    var a=new setDoubleEggFn();
    $("#messageBoxBtn").click(function(){
        window.location.reload()//抽奖完成刷新页面
    });
}
function setDoubleEggFn(){
    this.init();
}
setDoubleEggFn.prototype.init=function(){
    this.mobile = $("#mobile").val();
    this.channel = $("#channel").val();
    this.speedy=0;
    this.wrap=$("#recordUlCut").get(0);
    this.obj=$("#recordUl").get(0);
    this.liLength=this.obj.children.length;
    this.timer=null;
    this.i=0;
    this.pageNum=1;
    this.onOff=true;
    this.firstTime=true;
    this.useAmount;
    this.getUrlHttp=getUrlHttp();
    this.getSDLuckDrawResult(1);
    this.judgeFirstTime();
    this.share();

}
setDoubleEggFn.prototype.judgeFirstTime=function(){
    var self=this;
    ajaxRequest(contextPath + "wxluckdraw/showSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel,setUserSDLuckDraw,"GET");
    function setUserSDLuckDraw(data){
        self.useAmount=data.useAmount;
        var channel=$("#channel").val();
        var protocal=window.location.protocol;
        if(self.useAmount=="0"||self.useAmount==0){
            if(protocal=="https:" ||channel=="LBIOS"||channel=='LBAndroid'){
                $(".cover").removeClass("scale0");//弹出蒙版
                $(".reminder").removeClass("none");
                $('body').addClass("positionF");
            }
            $("#firstEntrence").click(function(){
                $('body').removeClass("positionF");
                $(".cover").addClass("scale0");
                $(".reminder").addClass("none");
                self.shakeText();
                self.shake();
            })
        }else{
            self.shakeText();
            self.shake();
        }
        if(data.rescode=="00000"){
            if(self.useAmount=="0"||self.useAmount==0){
                var resttime=Number(data.restLuckDrawTimes)+1
                $("#numberWrap").html(resttime)
            }
            else{
                $("#numberWrap").html(data.restLuckDrawTimes)
            }

        }else{
            errorMessage(data.resmsg_cn)
        }
    }
}
setDoubleEggFn.prototype.shakeText=function(){
    var self=this;
    this.isShaked=true;
    $(".treeBtn").click(function(){
        if(self.isShaked){
            self.isShaked=false;
            var i=0;
            var time=setInterval(function(){
                i++
                if(i%2==0){
                    a=1;
                    b=4;
                }else{
                    a=-1;
                    b=-4;
                }
                $("#shakeText")[0].style.cssText="position:absolute;left:"+(9+a)+"%;top:"+(6+a)+"%;transform:rotate("+b+"deg);-webkit-transform:rotate("+b+"deg)";
                if(i>50){
                    $("#shakeText")[0].style.cssText="position:absolute;left:9%;top:6%;transform:rotate(9)deg);-webkit-transform:rotate(9)deg);";
                    clearInterval(time);
                }
            },30);

            //手机震动1秒
            function autoPlayAudio() {
                WeixinJSBridge.invoke('getNetworkType', {}, function(e) {
                    document.getElementById('shakeAudio').play();
                });
            }
            if(self.channel=="LBIOS"||self.channel=="LBAndroid"){
                document.getElementById('shakeAudio').play();
            }else{
                autoPlayAudio();
            }
            if (navigator.vibrate) {
                navigator.vibrate(1500);
            }else if (navigator.webkitVibrate) {
                navigator.webkitVibrate(1500);
            }else if(navigator.mozVibrate){
                navigator.mozVibrate(1500);
            }
            timer=setTimeout(function(){
                if(self.useAmount==0||self.useAmount=="0"){
                    ajaxRequest(contextPath + "wxluckdraw/userSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel+"&drawFlag=0",setUserSDLuckDraw,"GET");
                }else{
                    ajaxRequest(contextPath + "wxluckdraw/userSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel+"&drawFlag=1",setUserSDLuckDraw,"GET");
                }
            },3000)
            function setUserSDLuckDraw(data){
                self.setGift(data)
            }
        }

    })
}
setDoubleEggFn.prototype.getSDLuckDrawResult=function (pageNum){
    var self=this;
    this.mobile=$("#mobile").val();
    this.channel = $("#channel").val();
    ajaxRequest(contextPath + "wxluckdraw/getSDLuckDrawResult", "mobile="+self.mobile+"&channel="+self.channel+"&current="+pageNum+"&pageSize=15",setSDLuckDrawResult,"GET");
    function setSDLuckDrawResult(data){
        if(data.rescode=="00000"){
            if((data.list==""||data.list==null||data.list=="null")&&self.onOff){
            }else{
                if(data.list==""||data.list==null||data.list=="null"){
                    errorMessage('没有更多了')
                }
                self.onOff=false;
                $('.recordWrap').removeClass("none");
                var html2="";
                for(var i=0 ; i<data.list.length;i++){
                    html2+='<li class="borderL borderR borderB outHide ">'
                    html2+='<span class="fl width50 borderR boxSizing ">'+data.list[i].lotteryTime+'</span><span class="fl width50 ">'+data.list[i].awardName+'</span></li>'
                }
                $("#recordUl").append(html2);
            }
            self.addEvent(data);
        }else{
            $('.recordWrap').addClass("none");
            errorMessage(data.resmsg_cn)
        }
    }
}
setDoubleEggFn.prototype.shake=function(){
    var self=this;
    var SHAKE_THRESHOLD =40; //定义摇一摇加速度的临界值 值越小摇动的力度越小
    var x = y = z = lastX = lastY = lastZ = 0; //初始化x,y,z上加速度的默认值
    this.isHaveShaked = false;//用于记录是否在动画执行中
    function init() {
        //判断系统是否支持html5摇一摇的相关属性
        if (window.DeviceMotionEvent) {
            window.addEventListener('devicemotion', deviceMotionHandler, false);
        } else {
            alert('not support mobile event');
        }
    }
    function deviceMotionHandler() {
        clearTimeout(timer)
        /*获取x,y,z方向的即时加速度*/
        var timer=null
        var acceleration = event.accelerationIncludingGravity;
        x = acceleration.x;
        y = acceleration.y;
        z = acceleration.z;
        var speed = Math.abs(x + y + z - lastX - lastY - lastZ);
        if (speed > SHAKE_THRESHOLD) {
            //if (Math.abs(x - lastX) > speed || Math.abs(y - lastY) > speed
            //	|| Math.abs(z - lastZ) > speed) {
            //摇一摇实际场景就是加速度的瞬间暴增爆减
            if (!self.isHaveShaked) {
                $(".treeBtn").trigger("click")
            }else if(navigator.msVibrate){
                navigator.msVibrate(1500);
            }
//                  //模拟网络请求做想干的事
            self.isHaveShaked = true;
        }
        /*保存历史加速度*/
        lastX = x;
        lastY = y;
        lastZ = z;

    }
    $(function () {
        init();
    });
}
setDoubleEggFn.prototype.setGift=function(data){
    var self=this;
    if(data.rescode=="00000"){
        function autoPlayAudio() {
            WeixinJSBridge.invoke('getNetworkType', {}, function(e) {
                document.getElementById('shakeAudio2').play();
            });
        }
        if(self.channel=="LBIOS"||self.channel=="LBAndroid"){
            document.getElementById('shakeAudio2').play();
        }else{
            autoPlayAudio();
        }
        var giftUrl=self.getUrlHttp+data.showImg
        $(".giftPic").attr("src",giftUrl);
        $("#giftText").html(data.tipsMessage);
        $(".cover").removeClass("scale0");//弹出蒙版
        $('body').addClass("positionF");
        $(".messageBoxWrap").removeClass('none')
    }else{
        self.isHaveShaked = false;
        self.isShaked=true;
        if(data.resmsg_cn!=""&&data.resmsg_cn!=null&data.resmsg_cn!="null"){
            errorMessage(data.resmsg_cn);
        }else{
            errorMessage(data.errorMessage.message_content);
        }
    }
}
setDoubleEggFn.prototype.share=function(){
    var self=this;
    ajaxRequest(contextPath + "wxshare/getShareSub", "from=LBWX", setShareMessage);
    function setShareMessage(data){
        var shareTitle = data.title;
        var shareDesc = data.desc;
        var shareLink =  bathPath + "/wxTrigger/getWxCode?actionScope="+$("#goUrl").val(); // 分享链接
        var shareImgUrl = data.imgUrl;
        //var shareImgUrl="http://wx.lianbijr.com/pic/weixin/activity/activity_newYear/shareImg.png"

        $("#inviteFriendsBtn").click(function() {

            if (self.channel == "LBAndroid") {
                if(window._cordovaNative){
                    window._cordovaNative.webContentShare(shareTitle,shareDesc,shareLink,shareImgUrl);
                }
            }else if(self.channel == "LBIOS") {
                webContentShare(shareTitle,shareDesc,shareLink,shareImgUrl);
            }else{
                $(".cover").removeClass("scale0")
                $(".messageBoxWrap").addClass("none");
                $(".textSite").removeClass("none");
                // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                wx.onMenuShareTimeline({
                    title: shareTitle, // 分享标题
                    desc: shareDesc, // 分享描述
                    link: shareLink, // 分享链接
                    imgUrl: shareImgUrl, // 分享图标
                    success: function () {
                        // 用户确认分享后执行的回调函数
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
                        errorMessage("分享成功");
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        errorMessage("未分享");
                    }
                });
            }
        });
    }
}
setDoubleEggFn.prototype.addEvent=function(data){
    this.pageNum++;
    var obj=this.obj;
    var self=this;
    this.fatherHeight=this.wrap.offsetHeight;
    this.height=obj.offsetHeight;
    this.datHeight=this.fatherHeight-this.height;

    if(this.datHeight>=0){
        this.wrap.style.height=this.height+"px";
    }else{
        obj.addEventListener("touchstart",touchstartFn,false);
    }
    function touchstartFn(event){
        event.preventDefault();
        if (event.targetTouches.length == 1) {
            var touch = event.targetTouches[0];
            self.disy=touch.pageY-obj.offsetTop;
        }
        self.oldY=0;
        document.addEventListener("touchmove",touchmoveFn,false);
        document.addEventListener("touchend",touchendFn,false);
    };
    function touchmoveFn(event){
        event.preventDefault();
        self.height=obj.offsetHeight;
        self.datHeight=self.fatherHeight-self.height;
        if (event.targetTouches.length == 1) {
            var touch = event.targetTouches[0];
            self.y=touch.pageY-self.disy;
        }
        if(self.datHeight<0){
            if(self.y>0){
                self.y=0
            }else if(self.y<self.datHeight){
                self.y=self.datHeight
                self.getSDLuckDrawResult(self.pageNum);
            }
        }else{
            if(self.y<0){
                self.y=0
            }else if(self.y>self.datHeight){
                self.y=self.datHeight
            }
        }

        obj.style.top=self.y+"px";
    }
    function touchendFn(event){
        event.preventDefault();
        document.removeEventListener("touchmove",touchmoveFn,false);
        document.removeEventListener("touchend",touchendFn,false);
    }
};
function extend(obj1,obj2){
    for(var i in obj2){
        obj1[i]=obj2[i]
    }
}

