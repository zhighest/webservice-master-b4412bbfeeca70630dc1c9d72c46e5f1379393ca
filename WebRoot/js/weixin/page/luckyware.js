$(document).ready(function() {
	var arr = [];
	var selectType="selectType=1"
    var mobile = $("#mobile").val(); //手机号
    var channel = $("#channel").val(); //渠道号
    var ua = navigator.userAgent.toLowerCase();
    var oScreenWidth=window.screen.width ;//屏幕宽度
    var oMiddlePoint=Math.floor(oScreenWidth/2);//屏幕中心点X轴的位置
    var isLoaded=false;//用于判断抽奖是否成功
    var isOneTime=true;//用于判断是单次抽奖还是10连抽  默认指定为单次
    var oAvaliableTimes="";//剩余可抽奖次数
    var scrollTop=0;//窗口滚动的距离
    //所有幸运抽奖页的js功能
    var luckywareJs={
    	//初始化时加载
    	init:{
    		 //查询摇一摇奖项列表 初始化时更新可抽奖次数 活动说明
    		selectType:function(a) {
		        if(ua.match(/MicroMessenger/i) == "micromessenger") { //微信环境
		            ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw", a, luckywareJs.setprize, "GET");
		        } else if(channel == "LBAndroid") { //安卓APP
		            if(mobile == "" || mobile == null || mobile == "null") {
		                if(window._cordovaNative) {
		                    window._cordovaNative.goLogin();
		                }
		            } else {
		                ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw", a + "&mobile=" + mobile + "&channel=" + channel, luckywareJs.setprize, "GET");
		            }
		        } else if(channel == "LBIOS") { //IOS环境
		            if(mobile == "" || mobile == null || mobile == "null") {
		                goLogin();
		            } else {
		                ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw", a + "&mobile=" + mobile + "&channel=" + channel, luckywareJs.setprize, "GET");
		            }
		        } else {
//		            ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw", a, luckywareJs.setprize, "GET");
		        }
   			},
   			//初始化添加各按钮功能
   			initFunction:function(){
   				//盒子运动8秒后变为静态
   				setTimeout(function(){
   					$("#giftPicAnimate").addClass("none");
			        $("#giftPicStatic").removeClass("none");
   				},8000)
   				//点击抽奖
   				$("#clickBox").on("click",function(){
			        //判断当前是抽一次还是抽10次
			        if(isOneTime){
			            //抽一次
			            luckywareJs.getPrize(1);
			        }else{
			            //抽十次
			            luckywareJs.getPrize(10);
			        }
			    });
			    //抽奖1次
			    $("#selectWayBtn1").on("click", function() {
			        $('.selectWay1').addClass('NEMT3');
			        $("#selectWay1").attr("src", contextPath + "pic/weixin/activity/choujiang/1timesActive.png");
			        //判断10连抽按钮的状态
		            if(oAvaliableTimes>=10){
		            	 $('.selectWay2').removeClass('NEMT3');
			        	 $("#selectWay2").attr("src", contextPath + "pic/weixin/activity/choujiang/10times.png");
		            }
			        $("#giftPicAnimate").addClass("none");
			        $("#giftPicStatic").removeClass("none active");
			        isOneTime=true;
			        return false;//阻止其他事件
			    })
			    //10连抽
			    $("#selectWayBtn2").on("click", function() {
			    	//判断10连抽按钮的状态
		            if(oAvaliableTimes>=0&&oAvaliableTimes<10){
		            	$("#selectWay2").attr("src", contextPath + "pic/weixin/activity/choujiang/10timesDisable.png");
		            	return;  
		            }
			        $('.selectWay2').addClass('NEMT3');
			        $("#selectWay2").attr("src", contextPath + "pic/weixin/activity/choujiang/10timesActive.png");
			        $('.selectWay1').removeClass('NEMT3');
			        $("#selectWay1").attr("src", contextPath + "pic/weixin/activity/choujiang/1times.png");
			        $("#giftPic").attr("src", contextPath + "pic/weixin/activity/choujiang/giftBoxStatic.png");
			         $("#giftPicAnimate").addClass("none");
			        $("#giftPicStatic").removeClass("none").addClass("active");
			        isOneTime=false;
			        return false;//阻止其他事件
			    })
			    //单次抽奖关闭抽奖结果
				$("#oneTimesCloseBtn,#oneTimesBtn").on('click',function(){
					luckywareJs.closeResultBox($('#oneTimesBox'));
				})
				//十次抽奖关闭抽奖结果
				$("#tenTimesCloseBtn,#tenTimesBtn").on('click',function(){
					luckywareJs.closeResultBox($('#tenTimesBox'));
				})
			    
   			},
   			//滑动抽奖部分
   			swiperFunction:function(){
			    var startX, startY, moveEndX, moveEndY, X, Y;
			 
			    $("#swiperBox").on("touchstart", function(e) {
			        e.preventDefault();
			        startX = e.originalEvent.changedTouches[0].pageX,
			        startY = e.originalEvent.changedTouches[0].pageY;
			    });
			    //判断左滑右滑是否符合条件 
			    $("#swiperBox").on("touchend", function(e) {
			        e.preventDefault();
			        moveEndX = e.originalEvent.changedTouches[0].pageX,
			        moveEndY = e.originalEvent.changedTouches[0].pageY,
			        X = moveEndX - startX,
			        Y = moveEndY - startY;
			 		//左往右滑
			        if ( Math.abs(X) > Math.abs(Y) && X >0&&moveEndX>oMiddlePoint&& startX<oMiddlePoint) {
				        //判断当前是抽一次还是抽10次
				        if(isOneTime){
				            //抽一次
				            luckywareJs.getPrize(1);
				        }else{
				            //抽十次
				            luckywareJs.getPrize(10);
				        }
			        }
			        //右往左滑
			        else if ( Math.abs(X) > Math.abs(Y) && X <0&&moveEndX<oMiddlePoint &&startX>oMiddlePoint) {
				        //判断当前是抽一次还是抽10次
				        if(isOneTime){
				            //抽一次
				            luckywareJs.getPrize(1);
				        }else{
				            //抽十次
				            luckywareJs.getPrize(10);
				        }
			        }
			    });
   			}
    	},
    	//错误提示
    	errorFunction:function(data){
	        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
	            errorMessage(data.resmsg_cn)
	        }
	        else{
	            if(data.errorMessage.message_level == "1"){
	                errorMessage(data.errorMessage.message_content)
	            }
	            else if(data.errorMessage.message_level == "2") {
	                errorMessage(data.errorMessage.message_content)
	            }
	            else {
	                errorMessage(data.resmsg_cn);
	            }
	        }
	    },
	    //查询奖项回调 初始化时调用
	    setprize:function (data) { //页面加载数据
	        $("#imgcontainor").empty();
	        if(data.rescode == "00000") {
	            $("#avaliableTimes").text(data.restLuckDrawTimes) //剩余抽奖次数
	            oAvaliableTimes=data.restLuckDrawTimes;//剩余抽奖次数
	            if(oAvaliableTimes>=0&&oAvaliableTimes<10){
	            	$("#selectWay2").attr("src", contextPath + "/pic/weixin/activity/choujiang/10timesDisable.png");
	            }
	            $("#activityDesc p").html(data.activityDesc) //活动说明
	            var oLiWidth = $('.prizeItem').outerWidth(true); //包括margin的宽度
	            var oLiLen = Math.ceil(data.listSize / 2);
	            $('.prizeContent').css('width', oLiWidth * oLiLen);
	            $("#prizeList").empty();
	            //生成奖品列表
	            luckywareJs.renderList(data)
	
	        } else {
	            luckywareJs.errorFunction(data);
	        }
	    },
	    //生成奖品列表  初始化时调用
	    renderList:function (data) {
	        $.each(data.list, function(i, jsonObj) {
	            var html = "";
	            if(i % 2 === 0) {
	                //li上面的div
	                html += '<li id="li' + (Math.floor(i / 2) + 1) + '" class="prizeItem MR2 fl">';
	                html += '<div class="topItem itemBg">';
	                html += '<div class="prizeItemTop">';
	                //如果奖项种类为99 只有一个图片
	                if(jsonObj.awardType == '99') {
	                    html += '<img src="' + jsonObj.showImg + '" width="90%"/ style="padding-top:50%">';
	                    html += '</div>';
	                    html += '</div>';
	                    html += '</li>';
	                } else {
	                    html += '<img width="100%" src="' + jsonObj.showImg + '" />';
	                    html += '</div>';
	                    html += '<p class="prizeItemBot textC font12">' + jsonObj.awardName + '' + jsonObj.descName + '</p>';
	                    html += '</div>';
	                    html += '</li>';
	                }
	                $("#prizeList").append(html);
	            } else {
	                //li下面的div
	                var html = "";
	                html += '<div class="bottomItem itemBg">';
	                html += '<div class="prizeItemTop">';
	                //如果奖项种类为99 只有一个图片
	                if(jsonObj.awardType == '99') {
	                    html += '<img src="' + jsonObj.showImg + '" width="90%"/ style="padding-top:50%">';
	                    html += '</div>';
	                    html += '</div>';
	                } else {
	                    html += '<img width="100%" src="' + jsonObj.showImg + '" />';
	                    html += '</div>';
	                    html += '<p class="prizeItemBot textC font12">' + jsonObj.awardName + '' + jsonObj.descName + '</p>';
	                    html += '</div>';
	                }
	                var nowItem = Math.floor(i / 2);
	                $(".prizeItem").eq(nowItem).append(html);
	            }
	        });
	    },
	    // 抽奖
	    getPrize:function (times){
	        var channel = $("#channel").val();
	        var ua = navigator.userAgent.toLowerCase();
	        //只能在微信、安卓、或者IOS下可以抽奖
	        if(ua.match(/MicroMessenger/i)!="micromessenger"){
	            if(channel !="LBAndroid" ){
	                if(channel !="LBIOS" ){
	                    return;
	                }
	            }
	        }
	        $("#handerAnimate").hide();//抽奖后隐藏小手动画
	        //盒子运动动画
	        if($("#giftPicStatic").hasClass('none')){
	        	//盒子还在摆动
	        	$("#giftPicAnimate").animate({opacity:0},1000,function(){
	        		//变为静态 添加动画
	        		$("#giftPicAnimate").addClass("none");
			        $("#giftPicStatic").removeClass("none").addClass("active");
	        	});
	        }else{
	        	//盒子掉落动画
	        	$("#giftPicStatic").addClass('animate');
	        	//盒子运动8秒后变为静态
   				setTimeout(function(){
			        $("#giftPicStatic").removeClass("animate");
   				},1000)
	        }
	        //单连抽剩余抽奖次数不够  或者10连抽  抽奖次数大于0小于10的时候提示抽奖次数已用完
	        if( oAvaliableTimes==0||(oAvaliableTimes>0&&oAvaliableTimes<10&&!isOneTime)){
	        	errorMessage("您的抽奖机会已经用完啦！请下次再来");
	        	return;
	        }
	        $("#mask").removeClass('none');
	        var oContent;
	        oContent=times===1?$("#oneTimesBox"):$("#tenTimesBox");
	        luckywareJs.showResultBox(oContent);//显示抽奖结果
	    },
	    //抽奖回调
	    getPrizeCallback:function (data){
	        if(data.rescode=="00000"){
	            $("#avaliableTimes").text(data.restLuckDrawTimes);//剩余抽奖次数
	            oAvaliableTimes=data.restLuckDrawTimes;//剩余抽奖次数
	            if(isOneTime){
	            	//单次抽奖数据
	            	$('#awardName').text(data.list[0].awardName+data.list[0].field1)
	            }else{
	            	//十连抽数据
	            	$("#tenTimesPrizeList").empty();
	            	var html="";
	            	for(var i=0;i<data.listSize;i++){
	            		html+='<li class="prizeItem clearfix">'
						html+='<span class="prizeItemCategory fl textL">'+data.list[i].field1+'</span>'
						html+='<em class="prizeItemNo positionR fl">'+(i+1)+'<div class="line positionA"></div></em>'
						html+='<span class="prizeItemIfo fr textR redText">'+data.list[i].awardName+data.list[i].field1+'</span>'
						html+='</li>'
	            	}
	            	$("#tenTimesPrizeList").html(html);
	            	$("#tenTimesPrizeList").find(".line:last").remove();//最后一个没有竖线
	            }
	             //判断10连抽按钮的状态
	            if(oAvaliableTimes>=0&&oAvaliableTimes<10){
	            	$('.selectWay2').removeClass('NEMT3');
	            	$("#selectWay2").attr("src", contextPath + "/pic/weixin/activity/choujiang/10timesDisable.png");
	            	$('.selectWay1').addClass('NEMT3');
			        $("#selectWay1").attr("src", contextPath + "pic/weixin/activity/choujiang/1timesActive.png");
			        $("#giftPicStatic").removeClass("active");
			        isOneTime=true;
	            }
	            
	        }
	        else{
	            luckywareJs.errorFunction(data);
	        }
	    },
	    //关闭抽奖结果弹框 oContent要关闭的内容区
		closeResultBox:function (oContent){
			$('#mask').addClass('none');
			$('body').removeClass('positionF');
    		$(window).scrollTop(scrollTop);//设置页面滚动的高度，如果不设置，关闭弹出层时页面会回到顶部。
			oContent.addClass('none'); 
			oContent.css('top',"0%")
		},
		//抽奖结果显示 oContent要显示的内容区
		showResultBox:function (oContent){
			oContent.removeClass('none');
			oContent.animate({top:"50%"},500)
			$('#mask').removeClass('none');
			scrollTop = $(window).scrollTop(); //获取页面的scrollTop；
			$('body').addClass('positionF');
			$('body').css("width",oScreenWidth);
  			$('body').css("top",-scrollTop+"px");//给body一个负的top值；
			var oTimes;
	        oTimes=isOneTime?1:10;
			ajaxRequest(contextPath + "wxcoupons/userCouponsLuckDraw",selectType+"&mobile=" +mobile+ "&channel="+channel+"&times="+oTimes,luckywareJs.getPrizeCallback,"GET");			
		}
	    
    }
    //查询摇一摇奖项列表 初始化时更新可抽奖次数 活动说明
   luckywareJs.init.selectType(selectType);//初始化时更新数据
   luckywareJs.init.initFunction();//初始化添加各按钮功能  
   luckywareJs.init.swiperFunction();//初始化添加各按钮功能  
})