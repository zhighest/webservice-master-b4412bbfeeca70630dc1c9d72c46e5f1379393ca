window.onload=function(){
      var a=new setDoubleEggFn();
      $(".messageBoxBtn").click(function(){
      	window.location.reload()//抽奖完成刷新页面
	  });
// 背景图设置
    setMainWidth();
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
        this.useAmount;
        this.getUrlHttp=getUrlHttp();
        this.showSDLuckDraw();
        this.getSDLuckDrawResult(1);
        this.punchEggs();
        this.hummer();
	}
	setDoubleEggFn.prototype.showSDLuckDraw=function(){
		var self=this;
		ajaxRequest(contextPath + "wxluckdraw/showSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel,setUserSDLuckDraw,"GET");
	   	function setUserSDLuckDraw(data){
	   	 self.useAmount=data.useAmount;
	   	 if(self.useAmount==0||self.useAmount=="0"){
            $(".silverCover").removeClass("none");
            $('body').addClass("positionF");
            self.punchSilverEgg();
            clearInterval(self.timer);
	   	 }
		 if(data.rescode=="00000"){
		 	if(data.list == null || data.list == "" ){
		 	}else{
		 	$("#numberWrap").empty();
			var number=data.restLuckDrawTimes.toString();
			if(number.length==2){
			   $(".textPic1").css("marginLeft","4%");
			   $(".textPic2").css("marginRight","4%");
			   $("#numberWrap").css("left","33.5%")
			}
			if(number.length==3){
			   $(".textPic1").css("marginLeft","0%");
			   $(".textPic2").css("marginRight","0%");
			   $("#numberWrap").css("left","32%");
			}
			var html=""
			for(var i=0 ; i<number.length;i++){
			 html+='<img src='+contextPath+'pic/weixin/activity/doubleFestival/punchEgg/number/'+number[i]+'.png>'
			}
			$("#numberWrap").append(html);	
		 	}
        }else{
          $("#numberWrap img").removeClass("none")	
          errorMessage(data.resmsg_cn)
         }
     }
	}
	setDoubleEggFn.prototype.getSDLuckDrawResult=function (pageNum){
		var self=this;
		this.mobile=$("#mobile").val();
	    this.channel = $("#channel").val();
	    ajaxRequest(contextPath + "wxluckdraw/getSDLuckDrawResult", "mobile="+self.mobile+"&channel="+self.channel+"&current="+pageNum+"&pageSize=15",setSDLuckDrawResult,"GET");	
	    function setSDLuckDrawResult(data){
	   	   if(data.rescode=="00000"){
	   	   	  if((data.list==""||data.list==null||data.list=="null")&&self.onOff){
                // $('.recordWrap').addClass("none");
	   	   	  }else{
	   	   	  	if(data.list==""||data.list==null||data.list=="null"){
                 errorMessage('没有更多了')
	   	   	  	}
	   	   	  	self.onOff=false;
	   	   	  	$('.recordWrap').removeClass("none");
	   	   	  	setMainWidth();
	   	   	  	var html2="";
		        for(var i=0 ; i<data.list.length;i++){
		           html2+='<li class="borderL borderR borderB outHide ">'
		           html2+='<span class="fl width50 borderR boxSizing ">'+data.list[i].lotteryTime+'</span><span class="fl width50 ">'+data.list[i].awardName+'</span></li>'
		        }
		        $("#recordUl").append(html2);
	   	   	  }
	   	   	  self.addEvent(data);
	   	   	  setMainWidth();
	       }else{
	       	$('.recordWrap').addClass("none");
	        errorMessage(data.resmsg_cn)
	      }
	   }

	}
     setDoubleEggFn.prototype.hummer=function(){
     	var self=this;
     	self.n=0
     	this.timer=setInterval(function(){
         hummerMove()
       },1500);
        function hummerMove(){//锤子左右移动函数
	  	    self.n++
	    	self.i=self.n%4; 
	        var arr=["10%","40%","73%","40%"];//锤子坐标
		   $("#hummer").css("left",arr[self.i]);
	   }
     }

	  setDoubleEggFn.prototype.punch=function(n){
		  var hummer=$("#hummer");
		  $("#hummer img").eq(1).removeClass("none")
		  $("#hummer img").eq(0).addClass("none")
		  hummer.addClass("hummerMove");
          WN.addTranEvent(hummer.get(0),addClassFn,1);//锤子动作结束后回调
        function addClassFn(){  //给个图片添加class使其动起来
	          $(".brokenEggPic").eq(n%3).removeClass("opacity0").addClass("shake");
	          $(".eggPic").eq(n%3).addClass("opacity0")
			  $("#hummer").addClass("none");
			  var eggShellPic=$(".eggShell img");
			  var eggShellPicLength=eggShellPic.length;
			  for(var i=0;i<eggShellPicLength;i++){
	                eggShellPic.eq(i).addClass("shell"+(i+1))
			  }
			  var eggLightWrap=$(".eggLightWrap img");
			  var eggLightWrapLength=eggLightWrap.length;
			  for(var i=0;i<eggLightWrapLength;i++){
			  	if(i>2){
	                eggLightWrap.eq(i).addClass("wallet"+(i-2))
			  	}
			  }
			  $(".eggLightPic").addClass("eggLight");
			  $(".eggFlowerPic").addClass("eggFlower");
			  $(".twinkleLightPic").addClass("twinkleLight");
			  WN.addAnimEvent(eggLightWrap.get(1),eggLightWrapRemove);
			  WN.addAnimEvent($(".gift").get(0),coverComeOutFn)
         }

         function eggLightWrapRemove(){//移除福袋并且让礼物出来
          $(".eggLightWrap").addClass("none");
          $(".gift").addClass("giftMove");
        }

         function coverComeOutFn(){
      	  $(".cover").removeClass("none");//弹出蒙版
		      $(".messageBoxWrap").removeClass("scale0");
         }
        }
      setDoubleEggFn.prototype.punchEggs=function(){
      	  var  onOff=true;
      	  var self=this;
      	  var pos=''
        	var arr=["10%","40%","73%"];//锤子坐标
		    	var arr2=["-40%","-8%","26%"];//碎蛋壳坐标
		    	var arr3=["-50%","-18%","16%"];//碎花坐标
			    var arr4=["17%","49%","80%"];	//礼物图坐标
      	  $(".brokenEggPic").on("click",function(){
           pos=$(this).index();
           var hummer=$("#hummer");
           $("#hummer").css("left",arr[pos]);
           $(".eggShell").css("left",arr2[pos]);
           $(".eggLightWrap").css("left",arr3[pos]);
           $(".gift").css("left",arr4[pos]);
      	   clearInterval(self.timer);
      	   if(onOff){
      	    	onOff=false;//防止用户重复点击
      	     setTimeout(function(){
             	ajaxRequest(contextPath + "wxluckdraw/userSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel+"&drawFlag=1",setGift,"GET");
     	      },1000)
         	}
	      })
	      function setGift(data){
	      	onOff=true;//防止用户重复点击
		  	if(data.rescode=="00000"){
		  	   $(".brokenEggPic").off("click");
           var giftUrl=self.getUrlHttp+data.showImg
		       $(".giftPic").attr("src",giftUrl);
		       $(".giftPic2").attr("src",giftUrl);
		       $("#giftText").html(data.tipsMessage);
           self.punch(pos);
		  	}else{
		  		$(".brokenEggPic").click(function(){
		  			if(data.resmsg_cn!=""&&data.resmsg_cn!=null&data.resmsg_cn!="null"){
		  			errorMessage(data.resmsg_cn);
			  		}else{
		                errorMessage(data.errorMessage.message_content);
			  		}
			  		})
		  		if(data.resmsg_cn!=""&&data.resmsg_cn!=null&data.resmsg_cn!="null"){
		  			errorMessage(data.resmsg_cn);
		  		}else{
	                errorMessage(data.errorMessage.message_content);
		  		}
		  	}
		   }
      }  
     setDoubleEggFn.prototype.punchSilverEgg=function(){
     	var self=this;
     	var onOff=true;
     	var onOff2=true;
     	$(".silverEggShell").click(function(){
     		if(onOff2){
     			onOff2=false;//防止用户重复点击
     			ajaxRequest(contextPath + "wxluckdraw/userSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel+"&drawFlag=0",setGift,"GET");
     		}
     	});
     	$("#punchSilverEgg").click(function(){
     		if (onOff2) {
     			onOff2=false;//防止用户重复点击
     			ajaxRequest(contextPath + "wxluckdraw/userSDLuckDraw", "mobile="+self.mobile+"&channel="+self.channel+"&drawFlag=0",setGift,"GET");
     		}
    	})
     	setInterval(function(){
           if(onOff){
           	onOff=false;//开关让银蛋跑马灯闪烁
             $(".bulblesBkg").addClass("none")
           }else{
           	onOff=true;
           	 $(".bulblesBkg").removeClass("none")
           }
          
     	},200);
     	function setGift(data){
           if(data.rescode=="00000"){
           var giftUrl=self.getUrlHttp+data.showImg//礼物图片路径
		       $(".silverGiftPic").attr("src",giftUrl);
		       $(".giftPic2").attr("src",giftUrl);
		       $("#giftText").html(data.tipsMessage);
              self.punchSilver();
		  	}else{
		  		onOff2=true;
		  		if(data.resmsg_cn!=""&&data.resmsg_cn!=null&data.resmsg_cn!="null"){
		  			errorMessage(data.resmsg_cn);
		  		}else{
	          errorMessage(data.errorMessage.message_content);
		  		}
		  	}
     	}
     }
     setDoubleEggFn.prototype.punchSilver=function(){
       	var silverEggShellImg=$(".silverEggShell img")
        var length=silverEggShellImg.length;
  		  var hummer=$("#silverHummer");
  		  $("#silverHummer img").eq(1).removeClass("none")
  		  $("#silverHummer img").eq(0).addClass("none")
  		  hummer.removeClass("silverHummerMove").addClass("silverHummerMove2");
		    for(var i=0; i<length;i++){
               silverEggShellImg.eq(i).addClass("silverShell"+(i+1))
     		}
         setTimeout(function(){
         	addClassFn();
         },20)//锤子动作结束后回调
        function addClassFn(){  //给个图片添加class使其动起来
	      $(".brokenSilverEggPic").removeClass("opacity0").addClass("shake");
	      $(".silverEggPic").addClass("opacity0");
			  $("#silverHummer").addClass("none");
			  var silverEggShell=$(".silverEggShell img");
			  var silverEggShellLength=silverEggShell.length;
			  for(var i=0;i<silverEggShellLength;i++){
	                silverEggShell.eq(i).addClass("silverShell"+(i+1))//蛋壳动画
			  }
			  var silverEggLightWrap=$(".silverEggLightWrap img");
			  var silverEggLightWrapLength=silverEggLightWrap.length;
			  for(var i=0;i<silverEggLightWrapLength;i++){
			  	if(i>2){
	                silverEggLightWrap.eq(i).addClass("silverWallet"+(i-2))//福袋动画
			  	}
			  }
			  $(".silverEggLightPic").addClass("eggLight");//蛋壳上方光线
			  $(".silverEggFlowerPic").addClass("eggFlower");//蛋壳上方彩带
			  $(".silverTwinkleLightPic").addClass("twinkleLight");//星星
			  WN.addAnimEvent(silverEggLightWrap.get(1),eggLightWrapRemove);//动画执行完后回调。移除光线和彩带
			  WN.addAnimEvent($(".silverGift").get(0),coverComeOutFn)//动画执行完后回调。蒙版弹出
         }

         function eggLightWrapRemove(){//移除福袋并且让礼物出来
          $(".silverEggLightWrap").addClass("none");
          $(".silverGift").addClass("silverGiftMove");
        }

         function coverComeOutFn(){
      	  $(".cover").removeClass("none");
		  $(".messageBoxWrap").removeClass("scale0");
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
	function setMainWidth() {   
	    var main=document.querySelector(".main");
	    var wrapper=document.querySelector(".content");
	    main.style.height=wrapper.offsetHeight+"px";
	}
	window.onresize = function(){
	    setMainWidth()
	}

	function extend(obj1,obj2){
       for(var i in obj2){
          obj1[i]=obj2[i]
       }
    }

  (function (root, factory) {
        if (typeof define === 'function') {
            define(factory);
        }else if (typeof exports === 'object') {
            module.exports = factory;
        } else {
            root.WN = factory();
        }
})(this,function(){
        var WN = {},
            body=document.body || document.documentElement,
            style=body.style, 
            transition="transition",
            transitionEnd,
            animationEnd,
            vendorPrefix; 
         
        transition=transition.charAt(0).toUpperCase() + transition.substr(1);
 
        vendorPrefix=(function(){//现在的opera也是webkit
            var  i=0, vendor=["Moz", "Webkit", "Khtml", "O", "ms"];
            while (i < vendor.length) {
                if (typeof style[vendor[i] + transition] === "string") {
                  return vendor[i];
                }
                i++;
            }
            return false;
        })();
 
        transitionEnd=(function(){
            var transEndEventNames = {
              WebkitTransition : 'webkitTransitionEnd',
              MozTransition    : 'transitionend',
              OTransition      : 'oTransitionEnd otransitionend',
              transition       : 'transitionend'
            }
            for(var name in transEndEventNames){
                if(typeof style[name] === "string"){
                    return transEndEventNames[name]
                }
            }
        })();
 
        animationEnd=(function(){
            var animEndEventNames = {
              WebkitAnimation : 'webkitAnimationEnd',
              animation      : 'animationend'
            }
            for(var name in animEndEventNames){
                if(typeof style[name] === "string"){
                    return animEndEventNames[name]
                }
            }
        })();
        WN.addTranEvent=function(elem,fn,duration){
            var called=false;
            var fncallback = function(){
                    if(!called){
                        fn();
                        called=true;
                    }
            };
            function hand(){    
                elem.addEventListener(transitionEnd, function () {
                    elem.removeEventListener(transitionEnd, arguments.callee, false);
                        fncallback();
                }, false);
            }
            setTimeout(hand,duration);
        };
        WN.addAnimEvent=function(elem,fn){
            elem.addEventListener(animationEnd,fn,false)
        };
 
        WN.removeAnimEvent=function(elem,fn){
            elem.removeEventListener(animationEnd,fn,false)
        };
 
        WN.setStyleAttribute=function(elem,val){
            if(Object.prototype.toString.call(val)==="[object Object]"){
                for(var name in val){
                    if(/^transition|animation|transform/.test(name)){
                        var styleName=name.charAt(0).toUpperCase() + name.substr(1);
                        elem.style[vendorPrefix+styleName]=val[name];
                    }else{
                        elem.style[name]=val[name];
                    }
                }
            }
        };
        WN.transitionEnd=transitionEnd;
        WN.vendorPrefix=vendorPrefix;
        WN.animationEnd=animationEnd;
        return WN;
    });
