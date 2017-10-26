window.onload=function(){
// 背景图设置
// setMainWidth() 
// 载入排名
    setRank();
// 文字轮播
    var investRange=document.querySelectorAll(".investRange")[0];//内容区父级
    var investRangeUl=document.querySelector("#investRange");//内容区
    var i=0;
    var annual=0;
// var timer;
    var datHeight=0;
    var Y;
    var NewY;
    var ulTop;
    var distance=0;
    var ulHeight=0;
    var investRangeHeight=investRange.offsetHeight;//内容区父级高度
    //投资排名接口调用
    function setRank(){
        ajaxRequest(contextPath + "userRank/queryUserRankList",'rankId=2',getQueryUserRankList,"GET");
    }
    var timer=setInterval(invest,1500);
    function invest(){
        ulTop=parseFloat(getStyle(investRangeUl,'top'));//实时获取内容区top值
        //每个li的高度为22
        i=(Math.ceil(ulTop/22));
        //内容过小时不开启定时器
        if(ulHeight<=investRangeHeight){
            clearInterval(timer)
        }
        i--
        if(datHeight<=Math.abs(ulTop)+2){
            i=0;
            investRangeUl.style.top=0;//滚动到最底部时回归初始状态
        }else{
            Move(investRangeUl,{"top":i*22}) ;//运动   每个li的高度为22
            investRange.addEventListener("touchstart",startFn,false);
        }
    }
    //操作时停掉定时器
    function startFn(event) {
        event.preventDefault();
        clearInterval(timer);
        Y = event.targetTouches[0].pageY;
        ulTop=parseFloat(getStyle(investRangeUl,'top'));//已滚动距离
        investRange.addEventListener('touchmove',starMoveFn,false);
        investRange.addEventListener("touchend",endFn,false);
    }
    function starMoveFn(event) {
        event.preventDefault();
        clearInterval(timer);
        var  NewUlTop=parseFloat(getStyle(investRangeUl,'top'));
        NewY=event.targetTouches[0].pageY;//用户操作后重新获取当前位置
        var datY=NewY-Y;//用户滑动距离
        distance=ulTop+datY;//内容区现在的top值
        if(distance>0){
            distance=0;//滑动到顶部了
        }else if(distance<=-datHeight){
            distance=-datHeight;//滑动到底部了
        }
        investRangeUl.style.top=distance+"px";
    }

    function endFn(){
        timer=setInterval(invest,1500) ;//用户操作接受重新开启定时器
        investRange.removeEventListener("touchmove",starMoveFn,false);
        investRange.removeEventListener("touchend",endFn,false);
    }
    //投资排名接口回调
    function getQueryUserRankList(data){
        var investRange=document.getElementById("investRange");
        var range=document.getElementById("range");
        var empty=document.getElementById("empty");
        var html="";
        var len=data.list.length;
        var dataL=data.list;
        //没有数据
        if(len===0){
            empty.innerHTML='敬请期待';
            empty.style.letterSpacing= '2px';
            range.style.height= '58px';
            return false
        }
        var num=len*22;
        num>=218?num=218:num;//最多显示218条信息
        range.style.height= num+'px';
        for(var i=0 ;i<len;i++){
            html+='<li class="PLR2P whiteTex"><span class="fl" style="width:60px">'+dataL[i].userName+'</span>';
            html+='<span class="fr">'+dataL[i].money+'元</span>';
            html+='<div class=" textC width60P blockC">';
            html+='<div class="blockC textL" style="width:120px">';
            html+='<span class="width100 PR10">'+dataL[i].userMobile+'</span><span class="none">'+dataL[i].userName+'</span></div></div></li>';
        }
        investRange.innerHTML=html;
        ulHeight=investRangeUl.offsetHeight;//内容区高度
        datHeight=ulHeight-investRangeHeight;
    }
}
// 编辑投资排名编辑区域
function getStyle(obj, arr) {
    return obj.currentStyle ? obj.currentStyle[arr] : getComputedStyle(obj)[arr];
}
//运动函数
function Move(obj, json,fn) {
    var oTxt = document.getElementsByTagName('input');
    clearInterval(obj.timer);
    obj.timer = setInterval(function () {
        var Onoff = true;
        for (attr in json) {
            var iCur = 0;
            if (attr == 'opacity') {
                iCur = parseInt(parseFloat(getStyle(obj, attr)) * 100);
            } else {
                iCur = parseInt(getStyle(obj, attr));
            }
            var iSpeed = (json[attr] - iCur) / 10;
            iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
            if (json[attr] != iCur) {
                Onoff = false;
            }
            if (attr == 'opacity') {
                obj.style.filter = 'alpha(opacity(' + (iSpeed + iCur) + ')';
                obj.style.opacity = (iSpeed + iCur) / 100;
            } else {
                obj.style[attr] = iSpeed + iCur + 'px';
            }
        }
        if (Onoff) {
            clearInterval(obj.timer);
            fn&&fn()
        }
    }, 30)
}
// function setMainWidth() {   
//     // var main=document.querySelector(".main");
//     // var wrapper=document.querySelector(".wrapper");
//     // main.style.height=wrapper.offsetHeight+"px";
// }
// window.onresize = function(){
//     setMainWidth()
// }
