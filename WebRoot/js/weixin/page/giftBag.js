$(function(){
	(function rem(x){
		var str=document.documentElement.clientWidth;
		if(str>375){
			str=375; 
		}
		document.documentElement.style.fontSize=str/x+"px";
		window.addEventListener("resize",setRenSize,false);
		function setRenSize(){
		document.documentElement.style.fontSize=str/x+"px";
		}
	}(7.5));
	var type=getUrlParam("type");
	if(type=="libao"){
		leftBtn();
	}
	else{
		rightBtn();
	};
	$("#headerL").on("click",function(){//点击左侧
		leftBtn();
	});
	$("#headerR").on("click",function(){//点击右侧
		rightBtn();
	});
	$("#contentUl").on("click",".status",function(){//点击福利兑换
		var packageId=$(this).attr("package"),
		orderId=$(this).attr("order"),
		enable=$(this).attr("enable");
		$("#KcodeBtn").attr("order",orderId);
		if(enable == "1"){
			ajaxRequest(contextPath + "wxGiftPackage/queryGiftPackageGiftList","packageId="+packageId,setQueryGiftPackageGiftList);
		}
	})
	$("#KcodeBtn").on("click",function(){//点击福利详情兑换
		var orderId=$("#KcodeBtn").attr("order");
		ajaxRequest(contextPath + "wxGiftPackage/giftPackageExchange","orderId="+orderId, setGiftPackageExchange);
	})
	$("#contentUl").on("click",".status2",function(){//点击K码兑换
		var orderId=$(this).attr("order"),
		enable=$(this).attr("enable");
		if(enable == "1"){//灰色不可点
			ajaxRequest(contextPath + "wxGiftPackage/giftPackageExchange","orderId="+orderId, setGiftPackageExchange);
		}
	})
	$("#confirmBtn").on("click",function(){//确定按钮
		$("#alertWrap2").addClass("none");
		if(flag==0){
			rightBtn();
		}
		else if(flag==1){
			leftBtn();
		}
	})
})
function leftBtn(){
	$("#contentUl").html("");
	pageIndex(1);
	$("#headerFl").removeClass("opacity5");
	$("#headerFr").addClass("opacity5");
	flag=1;
};
function rightBtn(){
	$("#contentUl").html("");
	pageIndex2(1);
	$("#headerFl").addClass("opacity5");
	$("#headerFr").removeClass("opacity5");
	flag=0;
};
function pageIndex(page){//K码
	ajaxRequest(contextPath + "wxGiftPackage/queryGiftPackageList","current="+page+"&packageType=KCODE&pageSize=5", setQueryGiftPackageList);
}
function pageIndex2(page){
	ajaxRequest(contextPath + "wxGiftPackage/queryGiftPackageList","current="+page+"&packageType=WELFARE&pageSize=5", setQueryGiftPackageList2);
}
function setQueryGiftPackageList(data){
	 if(data.rescode == 00000||data.rescode == "00000"){
		 $("#num").html(numFormat(data.gift_balance));
		  var html="";
		  if(data.list == ""||data.list == null){
	         html += '<div class="listNull">';
	         html += '<img src="../pic/weixin/list.png">';
	         html += '<p class="p1">暂无K码礼包</p>';
	         html += '<p class="p2">如果有K码礼包，您将在这里看到</p>';
	         html += '</div>';
	     } 
		 else{
			 var length=data.list.length,
		     newData=data.list;
			 for(var i=0;i<length;i++){
				 var ym = getDate(newData[i].startDate).Format("yyyy/MM/dd"),
				 kcodeMoney =parseInt(newData[i].kcodeMoney);
				 html+='<li class="width90 contentLi MLRA radius5">';
				 html+='<div class="contentFl contentFr height28"><div class="clearfix">'; 
				 if(newData[i].enable=="1"||newData[i].statusText =="立即兑换"||newData[i].statusText =="已冻结"){ 
					 html+='<div class="fl  textL headerPT height14R">';
					 html+='<div class="redTex letterSpace PTREM15">';
				 }
				 else{
					 html+='<div class="fl  textL headerPT grayColor height14R" >';
					 html+='<div class="grayColor letterSpace PTREM15">';
				 }
				 html+=newData[i].name+'</div>';
				 html+='<div class="positionR positionB1">';
				 html+='<span>礼金</span><span class="ML2REM font12">¥ </span>';
				 if(newData[i].enable=="1"||newData[i].statusText =="立即兑换"||newData[i].statusText =="已冻结"){
					 html+='<span class="redTex helveticaneue numFont positionR positionT2">';
				 }
				 else{
					 html+='<span class="grayColor1 helveticaneue numFont positionR positionT2">';
				 }
				 html+=+kcodeMoney+'</span></div></div>';
				 html+='<div class="fr newHeaderPT">';
				 if(newData[i].enable=="1"||newData[i].statusText =="立即兑换"||newData[i].statusText =="已冻结"){
					 html+='<img src="'+contextPath+'pic/weixin/giftBag/package2.png"/></div>';
				 }
				 else{
					 html+='<img src="'+contextPath+'pic/weixin/giftBag/greyAsset.png"/></div>';
				 }
				 html+='</div><div class="footer">';
				 html+='<span class="grayTex1 fl footerSpan font12">兑换开始日期：'+ym+'</span>';
				 if(newData[i].enable=="1"){
					 html+='<span class="contentSpan bkg3 whiteTex radius5 fr status2" order="'+newData[i].id+'" package="'+newData[i].packageId+'" enable="'+newData[i].enable+'">'+newData[i].statusText+'</span></div></div></li>';
				 }
				 else if(newData[i].enable=="0"){
					 html+='<span class="contentSpan grayBk whiteTex radius5 fr status2" order="'+newData[i].id+'" package="'+newData[i].packageId+'" enable="'+newData[i].enable+'">'+newData[i].statusText+'</span></div></div></li>';
				 }  
			 } 
		 }
		$("#contentUl").append(html);
		$("#purchaseDetailListPaging").html(pagingMobile(data,"pageIndex"));
	 }
	 else{
		$("#contentListNull").removeClass("none");
		setTimeout(function(){
			errorMessage(data.resmsg_cn);
		},300);
		
	 }
}
function setQueryGiftPackageList2(data){
	if(data.rescode == 00000||data.rescode == "00000"){
		$("#num").html(numFormat(data.gift_balance));
		  var html="";
		  if(data.list == ""||data.list == null){
	       html += '<div class="listNull">';
	       html += '<img src="../pic/weixin/list.png">';
	       html += '<p class="p1">暂无福利礼包</p>';
	       html += '<p class="p2">如果有福利礼包，您将在这里看到</p>';
	       html += '</div>';
	   }
		 else{
			 var length=data.list.length,
		     newData=data.list;
			 for(var i=0;i<length;i++){
			 var ym = getDate(newData[i].endDate).Format("yyyy/MM/dd"),
			 kcodeMoney=parseInt(newData[i].kcodeMoney);
			 html+='<li class="width90 MLRA radius5 contentLiNew">';
			 html+='<div class="contentFl contentFr height24"><div class="clearfix" >';
			 html+='<div class="fl textL MT1REM">';
			 if(newData[i].enable == "1"||newData[i].statusText =="立即兑换"||newData[i].statusText =="已冻结"){
				 html+='<div class="redTex letterSpace newMar">'+newData[i].name+'</div>';
				 html+='</div><div class="fr MT1REM">';
				 html+='<img src="'+contextPath+'pic/weixin/giftBag/package2.png"/>';
			 }
			 else{
				 html+='<div class="grayColor letterSpace newMar">'+newData[i].name+'</div>';
				 html+='</div><div class="fr MT1REM">';
				 html+='<img src="'+contextPath+'pic/weixin/giftBag/greyAsset.png"/>';	 
			 }	
				 html+='</div></div><div class="newFooter">';
				 html+='<span class="grayTex1 fl footerSpan font14">兑换到期日期：'+ym+'</span>';
				if(newData[i].enable == "1"){
					 html+='<span class="contentSpan bkg3 whiteTex radius5 fr status" order="'+newData[i].id+'" package="'+newData[i].packageId+'" enable="'+newData[i].enable+'">'+newData[i].statusText+'</span></div></div></li>';  
				}
				else if(newData[i].enable == "0"){
					 html+='<span class="contentSpan grayBk whiteTex radius5 fr status" order="'+newData[i].id+'" package="'+newData[i].packageId+'" enable="'+newData[i].enable+'">'+newData[i].statusText+'</span></div></div></li>'; 
				}
			 }
		 }
		$("#contentUl").append(html);
		$("#purchaseDetailListPaging").html(pagingMobile(data, "pageIndex2"));
	}
	else{
		 $("#contentListNull").removeClass("none");
		 setTimeout(function(){
			 errorMessage(data.resmsg_cn);
		 },300);
	 }
}
function setQueryGiftPackageGiftList(data){
	if(data.rescode == "00000"||data.rescode == 00000){
		var html="",text=data.list,j=data.list.length;
		for(var i=0;i<text.length;i++){
			html+='<div class="swiper-slide" >';
			html+='<img src="'+text[i].giftImageUrl+'" class="imgWidth width3R" width="100%"/>';
			html+='<div class="whiteTex prompt width3R" style="width:100%;">'+text[i].giftName+'×'+text[i].amount+'</div></div>';
		}
		$("#alertSwiper").html(html);
		if(j<3){
			var mySwiper = new Swiper('#alertCon', {
				initialSlide :0,
				slidesPerView: 2,
				spaceBetween : 10,
				centeredSlides: true,
				effect : 'coverflow',
				coverflow: {
								rotate:0,
					            depth: 150,
					            modifier: 2,
					            slideShadows : false,
					        }
			})	
		}
		else{
			var mySwiper = new Swiper('#alertCon', {
				initialSlide :1,
				slidesPerView: 2,
				spaceBetween : 10,
				centeredSlides: true,
				effect : 'coverflow',
				coverflow: {
								rotate:0,
					            depth: 150,
					            modifier: 2,
					            slideShadows : false,
					        }
			})	
		}
		$("#alertWrap").removeClass("opacity0");
	}
	else{
		 errorMessage(data.resmsg_cn);
	 }
}
function setGiftPackageExchange(data){
	$("#alertWrap").addClass("opacity0");
	if(data.rescode == "00000"||data.rescode == 00000){
		$("#alertWrap2").removeClass("none");
		setTimeout(function(){
			$("#bthImg2").css("background-position","-54rem 0")
		},1450);
	}
	else{
		 errorMessage(data.resmsg_cn);
	 }
}
$(".clearAlert").on("click",function(){
	$("#alertWrap").addClass("opacity0");
})