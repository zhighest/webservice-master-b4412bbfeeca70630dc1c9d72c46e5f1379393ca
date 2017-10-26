window.onload=function(){
// 背景图设置
// setMainWidth() 
// 载入排名
    setRank()
// 文字轮播
    var investRange=document.querySelector(".investRange");
    var investRangeUl=document.querySelector("#investRange");
    var i=0;
    var annual=0;
// var timer;
    var datHeight=0;
    var Y;
    var NewY;
    var ulTop;
    var distance=0;
    var ulHeight=0;
    var investRangeHeight=investRange.offsetHeight;
    function setRank(){
        ajaxRequest(contextPath + "userRank/queryUserRankList",'',getQueryUserRankList,"GET");
    }
    var timer=setInterval(invest,1500);
    function invest(){
        ulTop=parseFloat(getStyle(investRangeUl,'top'));
        i=(Math.ceil(ulTop/22));
        if(ulHeight<=investRangeHeight){
            clearInterval(timer)
        }
        i--
        if(datHeight<=Math.abs(ulTop)+2){
            i=0;
            investRangeUl.style.top=0;
        }else{
            Move(investRangeUl,{"top":i*22}) ;
            investRange.addEventListener("touchstart",startFn,false);
        }
    }
    function startFn(event) {
        event.preventDefault();
        clearInterval(timer);
        Y = event.targetTouches[0].pageY;
        ulTop=parseFloat(getStyle(investRangeUl,'top'));
        investRange.addEventListener('touchmove',starMoveFn,false);
        investRange.addEventListener("touchend",endFn,false);
    }
    function starMoveFn(event) {
        event.preventDefault();
        clearInterval(timer);
        var  NewUlTop=parseFloat(getStyle(investRangeUl,'top'));
        NewY=event.targetTouches[0].pageY;
        var datY=NewY-Y;
        distance=ulTop+datY;
        if(distance>0){
            distance=0;
        }else if(distance<=-datHeight){
            distance=-datHeight
        }
        investRangeUl.style.top=distance+"px";
    }

    function endFn(){
        timer=setInterval(invest,1500) ;
        investRange.removeEventListener("touchmove",starMoveFn,false);
        investRange.removeEventListener("touchend",endFn,false);
    }
    function getQueryUserRankList(data){
        var investRange=document.getElementById("investRange");
        var html="";
        var len=data.list.length;
        var dataL=data.list;
        for(var i=0 ;i<len;i++){
            html+='<li class="PLR2P"><span class="fl" style="width:60px">'+dataL[i].rankNumberCn+'</span>';
            html+='<span class="fr">'+dataL[i].money+'元</span>';
            html+='<div class=" textC width60P blockC">';
            html+='<div class="blockC textL" style="width:120px">';
            html+='<span class="width100 PR10">'+dataL[i].userMobile+'</span><span class="none">'+dataL[i].userName+'</span></div></div></li>';
        }
        investRange.innerHTML=html;
        ulHeight=investRangeUl.offsetHeight;
        datHeight=ulHeight-investRangeHeight;
    }
}
// 编辑投资排名编辑区域
function getStyle(obj, arr) {
    return obj.currentStyle ? obj.currentStyle[arr] : getComputedStyle(obj)[arr];
}
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
