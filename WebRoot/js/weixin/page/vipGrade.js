var totalPoint;  //累积积分
var grade;  //等级
var  _index = 0;  //动画里的index
var privilegeImageUrl;   //特权旋转图片
var iShttp = getUrlHttp();   //获取http协议   放在merge.js里
//积分等级数据
ajaxRequest(contextPath+"wxPoint/pointGradeIndex",'',getPointFun);  
function getPointFun(data){
	if(data.rescode == "00000"){
		console.log(data);
		var currentPoint = data.currentPoint;  //当前积分
		totalPoint = data.totalPoint;  //累积积分
		var userIcon = data.userIcon;  //用户头像
		grade = data.grade;  //等级
		$(".gradeWrap").css("background",'url('+iShttp + data.gradeList[parseInt(grade)-1].backgroundImageUrl+') no-repeat');  //会员等级背景图片
		var nextGradePointIntro = data.nextGradePointIntro;  //距离下一等级会员还差多少积分
		if(userIcon != '' && userIcon != null){
			$(".userImg img").attr('src',iShttp+userIcon);
		}
		$(".currentPoint").html(currentPoint);  //当前积分
		$(".totalPoint").html(totalPoint);  //累积积分
		$(".nextGradePointIntro").html(nextGradePointIntro);  //距离下一等级会员还差多少积分
		var gradeList = data.gradeList;  //等级列表
		$(".privilegeName").html(data.gradeName);   //特权名称
		$(".ladder .triangle").eq(parseInt(grade)-1).css("display","block");  //当前三角显示
		$(".ladder .triangle").eq(parseInt(grade)-1).addClass("cur");
		for(var i = 0; i < gradeList.length; i++){
			var name = gradeList[i].name;  //等级名称
			var minPoint = gradeList[i].minPoint;  //达到等级所需积分
			$(".pointName").eq(i).html(name);
			$(".userIntegral").eq(i).html(minPoint);
			//.progressImg img
			if( i <= parseInt(grade) - 1){  //已达到等级显示彩色 等级图片
 				$(".progressImg img").eq(i).attr("src",iShttp + gradeList[i].enabledImageUrl);
			}else{   //未达到等级显示灰色 等级图片
				$(".progressImg img").eq(i).attr("src",iShttp + gradeList[i].disabledImageUrl);
			}
			$(".progressImg img").eq(i).attr("gradeId",gradeList[i].id);    //等级Id
		}
		setTimeout(function(){gradeAnimate(_index);},500);   //等级过渡动画   定时器延时一下下，等待等级logo加载
		 
		var priviList = data.priviList;
		for(var k = 0; k < priviList.length; k++){
			var html = "";
			var privilegeId = priviList[k].id;  //特权Id
			privilegeImageUrl = iShttp + priviList[k].privilegeImageUrl; //特权旋转图片
			var status = priviList[k].status;  //是否启用特权状态  0启用 1禁用
			var privilegeName = priviList[k].privilegeName; // 特权名称
			if(status == '0' || status == 0){
				var imgUrl = iShttp+priviList[k].disabledImageUrl;
			}else if(status == '1' || status == 1){
				var imgUrl = iShttp+priviList[k].enabledImageUrl;
			}
			html = '<li privilegeImageUrl="'+privilegeImageUrl+'" privilegeId="'+privilegeId+'" class="fl width25 boxSizing PTB5P">'+
    			'<img class="width50 MB10P" src="'+imgUrl+'" />'+
    			'<p class="font14">'+privilegeName+'</p>'+
    		'</li>';
    		$(".privilegeList").append(html);
		}
		clickPrivilege();  //  点击特权
	}else{
		errorMessage(data.resmsg_cn);
	}
		
}


//等级过渡动画
var totalPoint = 0;
function gradeAnimate(_index){
	if(_index != grade - 1){   //等级大于1级
		$(".progressImg img").eq(_index).addClass("ladderAnimate");
	}else if(_index == grade - 1 && grade < 5){
		$(".progressImg img").eq(_index).addClass("currentVip");   //当前等级logo变稍微大
		var num = totalPoint - parseInt($(".userIntegral").eq(_index).html());  //距离下一等级会员还差多少积分
		var nextMinPoint = parseInt($(".userIntegral").eq(grade).html()) - parseInt($(".userIntegral").eq(_index).html());  //达到等级所需积分
		var len = num/nextMinPoint * 72 + 4 + '%';  //进度条百分比
		$(".progressImg .progress").eq(_index).stop(true).animate({"width":len},500);
		return false;
	}else{  //等级为5级
		$(".progressImg img").eq(_index).addClass("currentVip");   //当前等级logo变稍微大
		$(".progressImg img").eq(_index).addClass("ladderAnimate");
	}
	$(".progressImg .progress").eq(_index).stop(true).animate({"width":"72%"},500,function(){
		_index++;
		gradeAnimate(_index);
	});
}

//点击等级图片  查看等级特权信息
$(".vipImgClick").click(function(){
	var gradeInt =	$(this).attr("gradeId");  //对应的等级Id
	var priviName = $(".pointName").eq(gradeInt-1).html();
	$(".ladder .triangle").css("display","none");  //先隐藏所有三角
	$(".ladder .triangle").eq(gradeInt-1).css("display","block");  //当前三角显示
	$(".privilegeName").html(priviName);  //对应等级名称
	$(".progressImg img").removeClass("ladderAnimate");
	ajaxRequest(contextPath+"wxPoint/changeGrade",{gradeId:gradeInt},changeGradeFun);
	$(".progressImg img").eq(gradeInt-1).addClass("ladderAnimate");
});
function changeGradeFun(data){
	console.log(data);
	var priviList = data.priviList;
	$(".privilegeList").html("");
	for(var k = 0; k < priviList.length; k++){
		var html = "";
		var privilegeId = priviList[k].id;  //特权Id
		privilegeImageUrl = iShttp + priviList[k].privilegeImageUrl; //特权旋转图片
		var status = priviList[k].status;  //是否启用特权状态  0启用 1禁用
		var privilegeName = priviList[k].privilegeName; // 特权名称
		if(status == '0' || status == 0){
			var imgUrl = iShttp+priviList[k].disabledImageUrl;
		}else if(status == '1' || status == 1){
			var imgUrl = iShttp+priviList[k].enabledImageUrl;
		}
		html = '<li privilegeImageUrl="'+privilegeImageUrl+'" privilegeId="'+privilegeId+'" class="fl width25 boxSizing PTB5P">'+
			'<img class="width50 MB10P" src="'+imgUrl+'" />'+
			'<p class="font14">'+privilegeName+'</p>'+
		'</li>';
		$(".privilegeList").append(html);
	}
	clickPrivilege();  //  点击特权
} 
//点击特权弹出层显示
var _x;
var _Y;
function clickPrivilege(){
	$(".privilegeList li").click(function(event){
		$("#keyframes").html("");
		var privilegeImageUrl = $(this).attr('privilegeImageUrl');  //对应的特权旋转图片
		$(".animateImg").attr('src',privilegeImageUrl);
	　　	_x= event.pageX;
		_Y= event.pageY;
		var styleHtml = '@keyframes showAnimate{0%{top: '+_Y+';left: '+_x+';transform: rotateY(0) translateX(0) scale(0.1);'+
		'-webkit-transform: rotateY(0) translateX(0) scale(0.1);}100%{top: 10%;left: 50%;'+
		'transform: rotateY(720deg) translateX(-50%) scale(1);'+
		'-webkit-transform: rotateY(720deg) translateX(-50%) scale(1);}}'+
		'@-webkit-keyframes showAnimate{0%{top: '+_Y+';left: '+_x+';transform: rotateY(0) translateX(0) scale(0.1) ;'+
		'-webkit-transform: rotateY(0) translateX(0) scale(0.1);}100%{top: 10%;left: 50%;'+
		'transform: rotateY(720deg) translateX(-50%) scale(1);'+
		'-webkit-transform: rotateY(720deg) translateX(-50%) scale(1);}}';
		$("#keyframes").html(styleHtml);
		var privilegeId = $(this).attr("privilegeId");
		ajaxRequest(contextPath+"wxPoint/priviDetail",{priviId:privilegeId},getPriviDetail);  //对应的特权数据  
		$(".popup").toggle();
//		$(".privilegeContent").toggle(); 
			$(".priviName").stop(true).fadeIn(500);
			$(".userPopup").stop(true).fadeIn(500);
			$(".popupContent").stop(true).fadeIn(500);
		setTimeout(function(){
			$('.animateImg').removeClass("closeCur");
			$('.animateImg').addClass("cur");
			},500);
		
	});

	//点击关闭按钮隐藏弹出层
	$(".closedBtn").click(function(){
		$("#keyframes").html("");
		var styleHtml = '@keyframes closeAnimate{0%{top: 10%;left: 50%;'+
			'transform: rotateY(720deg) translateX(-50%) scale(1);'+
		'-webkit-transform: rotateY(720deg) translateX(-50%) scale(1);}100%{top: '+_Y+';left: '+_x+';'+
		'transform: rotateY(0) translateX(0) scale(0.1) ;'+
		'-webkit-transform: rotateY(0) translateX(0) scale(0.1);}}'+
		'@-webkit-keyframes closeAnimate{0%{top: 10%;left: 50%;'+
		'transform: rotateY(720deg) translateX(-50%) scale(1);'+
		'-webkit-transform: rotateY(720deg) translateX(-50%) scale(1);}100%{top: '+_Y+';left: '+_x+';'+
		'transform: rotateY(0) translateX(0) scale(0.1) ;'+
		'-webkit-transform: rotateY(0) translateX(0) scale(0.1);}}';
		$("#keyframes").html(styleHtml);
//		$(".privilegeContent").toggle();
		$('.animateImg').removeClass("cur");
		$('.animateImg').addClass("closeCur");
		$(".priviName").stop(true).fadeOut(500);
		$(".userPopup").stop(true).fadeOut(500);
		$(".popupContent").stop(true).fadeOut(500);
		setTimeout(function(){$(".popup").hide();},1000); 
	});
	
}

//特权数据详情
function getPriviDetail(data){
	if(data.rescode == "00000"){
		$(".userPopup").html("");
		$(".popupDetail").html("");
		console.log(data);
		if(data.priviName != "" || data.priviName != undefined || data.priviName != null ){
			$(".priviName").html(data.priviName);   //特权名称
		}
		if(data.priviGradeIntro != "" || data.priviGradeIntro != undefined || data.priviGradeIntro != null ){
			$(".userPopup").html(data.priviGradeIntro);    //特权等级介绍
		}
		$(".popupDetail").html(data.priviIntro);   //特权介绍
		if(data.priviDetail.length != 0 && data.priviDetail != undefined && data.priviDetail != null && data.priviDetail !=""){
			$(".popupList").css("border-top","1px solid #54595A");   
		}else{
			$(".popupList").css("border","none");   //没有特权详情  去掉border为none
		}
		$(".popupList").html(data.priviDetail);
	}else{
		errorMessage(data.resmsg_cn);
	}
};


// 跳转到积分页面
$(".goColletScore").click(function(){
	window.location.href = contextPath + "wxPoint/colletScore";
});


