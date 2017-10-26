$(document).ready(function(){//页面加载完毕
    var mobile = $("#mobile").val();
    var channel = $("#channel").val();
    var ua = navigator.userAgent.toLowerCase();//运行环境
    $("#punchEggBtn").click(function(event) {
        if(ua.match(/MicroMessenger/i)=="micromessenger"){//如果运行环境是微信
                linkFn();
        } else if(channel == "LBAndroid") {//如果运行环境是安卓app
            if(mobile == "" || mobile == null|| mobile == "null"){
                if(window._cordovaNative){
                    window._cordovaNative.goLogin();
                }   
            }else{
                linkFn()
            }
        }else if(channel == "LBIOS") {//如果运行环境是Ios
            if(mobile == "" || mobile == null || mobile == "null"){
                    goLogin();
            }else{
                linkFn()
            }
        }else{
             // linkFn() 
        } 
    })
    function linkFn(){
        window.location.href = contextPath +'wxactivity/goPunchEggs?mobile='+mobile+'&channel='+channel
    }    
})
