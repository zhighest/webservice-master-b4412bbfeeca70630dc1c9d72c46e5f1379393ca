var goProductDetails = function(loanId, planId, scatteredLoanId, contactAmount,productName) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxproduct/goProductBuy";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "planId";
    inp2.value = planId;
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "sloanId";
    inp3.value = scatteredLoanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "contactAmount";
    inp4.value = contactAmount;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "cpType";
    inp5.value = encodeURI(productName);
    temp.appendChild(inp5);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var goDemandOrderConfirmation = function(id,productName,remanAmount,annualRate,investMinAmount) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxproduct/goDemandOrderConfirmation";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "id";
    inp.value = id;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "productName";
    inp2.value = encodeURI(productName);
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "remanAmount";
    inp3.value = remanAmount;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "annualRate";
    inp4.value = annualRate;
    temp.appendChild(inp4);

    var inp5 = document.createElement("input");
    inp5.name = "investMinAmount";
    inp5.value = investMinAmount;
    temp.appendChild(inp5);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
$(document).ready(function() {
	var channel = $("#channel").val();
	var mobile =$("#mobile").val();
	if(channel == "LBWX"){
		$("#downBkg").stop().show();
	}
	ajaxRequest(contextPath + "wxactivity/getBannerList", "", setBannerList);
    ajaxRequest(contextPath + "wxproduct/getProductLoanList", "", setProductLoanList);    
    ajaxRequest(contextPath + "tasklist/getAllIcoFuncList", "mobile="+ mobile, setAllIcoFuncList,"POST");//自定义快捷菜单
});
var setBannerList = function(data) {
    var html = ''; //拼接html
    var html2 = '';
    if (data.list == null || data.list == "") {

    } else {
        $.each(data.list,
        function(i, jsonObj) { 
        	var requestURL = '';
        	var URLSubstring = jsonObj.requestURL.substring(jsonObj.requestURL.length-1);
        	if(URLSubstring == '?'){
        		requestURL = jsonObj.requestURL +'from=LBWX';
        	}else{
        		requestURL = jsonObj.requestURL +'?from=LBWX';
        	}    
           html += '<figure>';
		   html += '<a href="'+ requestURL +'"><img src="' + jsonObj.ftpPath + '" alt="'+ jsonObj.bannerName +'"></a>';
		   html += '</figure>';
         getImgNaturalDimensions(jsonObj.ftpPath,'');
        });
        $.each(data.list,
        function(i, jsonObj) {
           if(i == 0){   
            	html2 += '<li class="on"></li>';
	        }else{
		        html2 += '<li></li>';
	        }
        });
    }
    $("#bannerList").html(html);
    $("#position").html(html2);

    var slider =
	  Swipe(document.getElementById('slider'), {
	    auto: 3000,
	    continuous: true,
	    callback: function(pos) {
	
	      var i = bullets.length;
	      while (i--) {
	        bullets[i].className = ' ';
	      }
	      bullets[pos].className = 'on';
	
	    }
	  });
	var bullets = document.getElementById('position').getElementsByTagName('li');
};
var setProductLoanList=function(data){
	html='';
	 if (data.list == null || data.list == "") {

    } else{
    	 $.each(data.list,function(i, jsonObj){ 	 	
          	if (jsonObj.finishedRatio!=  100){//未售罄
				if(jsonObj.day == 0 && jsonObj.hour == 0 && jsonObj.min == 0 && jsonObj.sec == 0){//正常出售
					html+='<div class="width95 PL5P PTB5 textL loanBorder clearfix">';
					if(jsonObj.planId == "108"){//定期
						html += "<a onclick=\"goProductDetails('" + jsonObj.loanId + "','" + jsonObj.planId + "','" + jsonObj.scatteredLoanId + "','" + jsonObj.contactAmount + "','" + jsonObj.productName +"')\">";
					}else if(jsonObj.planId == "109"){//活期
						html += "<a onclick=\"goDemandOrderConfirmation('" + jsonObj.scatteredLoanId + "','" + jsonObj.productName + "','" + jsonObj.remanAmount + "','" + jsonObj.annualRate + "','" + jsonObj.investMinAmount + "','" + jsonObj.productName +"')\">";
					}
					html+=redText(jsonObj);
				}else{//预售，预售状态所有的字体都是灰色的，并且不能点击
					if(jsonObj.planId == "108"){//定期
						html+='<div class="width95 PL5P PTB5 textL loanBorder clearfix grayTex positionR">';
	    	 			html+='<img src="'+contextPath +'/pic/weixin/index/version0413/iconHomePleaseLookForward.png" class="positionA positionT5 positionR0" style="height:58px;z-index:10"/>';
	    	 			html+=grayText(jsonObj);
	    	 		}else if(jsonObj.planId == "109"){//活期
	    	 			html+='<div class="width95 PL5P PTB5 textL loanBorder clearfix positionR">';
	    	 			html += "<a onclick=\"goDemandOrderConfirmation('" + jsonObj.scatteredLoanId + "','" + jsonObj.productName + "','" + jsonObj.remanAmount + "','" + jsonObj.annualRate + "','" + jsonObj.investMinAmount + "','" + jsonObj.productName +"')\">";
	    	 			html+='<img src="'+contextPath +'/pic/weixin/index/version0413/iconHomePleaseLookForward.png" class="positionA positionT5 positionR0" style="height:58px;z-index:10"/>';
	    	 			html+=redText(jsonObj);
	    	 		}
				}         		    	 	
            } else {// 已售罄，售罄状态所有的字体都是灰色的，并且不能点击	 	
				html+='<div class="width95 PL5P PTB5 textL loanBorder clearfix grayTex positionR">';
				html+='<img src="'+contextPath +'/pic/weixin/index/version0413/iconHasBeenSoldOut.png" class="positionA positionT5 positionR0" style="height:58px;z-index:10"/>';
				html+=grayText(jsonObj);
            }
            html+='<p class="grayTex  font12 PT5 PB10">';//右边的下部区域
    	 	if(jsonObj.planId == "108"){
				if(jsonObj.remanDays == "0") {
					html += '锁定期<span >'+ jsonObj.remanPeriods +'</span>个月';		
				}else {
					//html += '锁定期<span >'+ jsonObj.remanDays +'</span>天';
				}							
			}else{
				//html += '锁定期<span>0</span>天';
			}			
			html +=  '<span class="ML5"><span >'+jsonObj.investMinAmount+'</span>元起投<span>';
			//会员专享icon
			if(jsonObj.adjustRate == "0"){ 
					
			}else{	
				html += '<span class="ML5">专享加息<span>'+jsonObj.adjustRate+'%</span></span>';
			}
			html+='</p>';
    	 	html+='</div>';            
    	 	html+='</a></div>';
    	 })
    	 $('#productUl').append(html)
    }
}
function grayText(jsonObj){
	html3="";
	 //左边的区域
 	html3 += '<div class="fl PT10 width32P">';
	html3 += '<div class=" lineHeight100  PB10 helveticaneue PT15"><span class="positionR font40 block" >'+ formatNum(jsonObj.annualRate) +'<span class="font16 positionA positionT10">%</span></span></div>';
	html3 += '<div class="font12 grayTex PB5 PT2">年化收益率</div>';
	html3 += '</div>';
    // 中间的竖线   	 	
	html3 += '<div class="line fl"><img src="'+ contextPath +'/pic/weixin/index/version0413/line.png" class="lineIndex"></div>';
    //右边的区域（右边title部分）		
 	html3 += '<div class="fr PT10 width65">';	    	 	
 	if(jsonObj.isRecommend == "1"){
 		html3+='<p class="font18 grayTex loanHeight38"><span class="positionR">'+jsonObj.showName+'</span><img src="'+ contextPath +'/pic/weixin/index/version0413/icon9.jpg"  width="20" class="positionA" ></p>';
 	}else if(jsonObj.isRecommend == "0"){
 		html3+='<p class="font18 grayTex loanHeight38">'+jsonObj.showName+'</p>';
 	}	
 	return html3;
}
function redText(jsonObj){
	html3="";
	 //左边的区域
 	html3 += '<div class="fl  PT10 width32P">';
	html3 += '<div class="  lineHeight100 redTex PB10 helveticaneue PT15" ><span class="positionR font40 block">'+ formatNum(jsonObj.annualRate) +'<span class="font16 positionA positionT10">%</span></span></div>';
	html3 += '<div class="font12 grayTex PB5 PT2">年化收益率</div>';
	html3 += '</div>';
   // 中间的竖线   	 	
	html3 += '<div class="line fl"><img src="'+ contextPath +'/pic/weixin/index/version0413/line.png" class="lineIndex"></div>';
   //右边的区域	（右边title部分）	
 	html3 += '<div class="fr PT10 width65">';	    	 	
 	if(jsonObj.isRecommend == "1"){//推荐
 		html3+='<p class="font18 blackTex loanHeight38"><span class="positionR">'+jsonObj.showName+'</span><img src="'+ contextPath +'/pic/weixin/index/version0413/icon9.jpg"  width="20" class="positionA"></p>';
 	}else if(jsonObj.isRecommend == "0"){//不推荐
 		html3+='<p class="font18 blackTex loanHeight38">'+jsonObj.showName+'</p>';
 	}
 	return html3;
}
var setProductLoanList1 = function(data) {
    var html = '<div class="swipe-wrap">'; //拼接html
    var html2 = '';
    if (data.list == null || data.list == "") {

    } else {
        $.each(data.list,function(i, jsonObj) {
            html += '<figure class="" style="margin-top:20px">';
            html += '<div class="width100 blockC productListItem whiteBkg positionR">';
			html += '<h4 class="textL heigh45 borderB positionR ML5P  MR5P">';
			html += '<span class="blackTex font20 positionR">';
			if(jsonObj.isRecommend == "1"){
				html += '<img src="'+ contextPath +'/pic/weixin/index/version0413/icon7.png" alt="" width="20" class="icon10 positionA" style="width:20px;">';
			}
			html += jsonObj.showName+'</span>';
			html += '<div class="positionA rightIcon radius5 font12" style="z-index:100;">';
			html += '<a class="block redTex" id="guaranteeLetter" href="'+ contextPath +'/wxabout/goGuaranteeLetter">';
			html += '<div class="lineHeight1_5x"><span class="redBkg radius5 whiteTex PLR5 lineHeight1_5x iconTex">本息保障</span></div>';
			html += '</a>';
			html += '</div>';
			html += '</h4>';		
			html += '<div class="width95 clearfix blockC MT5P MB5P">';
			html += '<div class="fl MR2P width28">';
			html += '<div class="font40P redTex MT5P newfont">'+ jsonObj.annualRate +'<span class="font20">%</span></div>';
			html += '<div class="font14 grayTex">年化收益率</div>';
			html += '</div>';
			html += '<div class="line fl"><img src="'+ contextPath +'/pic/weixin/index/version0413/line.png" class="lineIndex"></div>';
			html += '<div class="fr clearfix width65">';
			html += '<ul class="fl width55 textL">';
			if(jsonObj.planId == "108"){
				if(jsonObj.remanDays == "0") {
					html += '<li class="MT5 PL25 positionR"><img class="positionA greenIcon" src="'+ contextPath +'/pic/weixin/index/version0413/icon5.png" style="width:12px;"><span class="grayTex ML5P font12">锁定期<span>'+ jsonObj.remanPeriods +'</span>个月</span></li>';		
				}else {
					html += '<li class="MT5 PL25 positionR"><img class="positionA greenIcon" src="'+ contextPath +'/pic/weixin/index/version0413/icon5.png" style="width:12px;"><span class="grayTex ML5P font12">锁定期<span>'+ jsonObj.remanDays +'</span>天</span></li>';	
				}			
				html += '<li class="MT5 PL25 positionR"><img class="positionA yellowIcon" src="'+ contextPath +'/pic/weixin/index/version0413/icon6.png" style="width:14px;padding-top:2px"><span class="grayTex ML5P font12"><span id="">'+ jsonObj.investMinAmount+'</span>元起投</span></li>';
				
			}else{
				html += '<li class="MT5 PL25 positionR"><img class="positionA greenIcon" src="'+ contextPath +'/pic/weixin/index/version0413/icon5.png" style="width:12px;"><span class="grayTex ML5P font12">锁定期<span><span>0</span>天</span></li>';
				html += '<li class="MT5 PL25 positionR"><img class="positionA yellowIcon" src="'+ contextPath +'/pic/weixin/index/version0413/icon6.png" style="width:14px;padding-top:2px"><span class="grayTex ML5P font12"><span id="">'+ jsonObj.investMinAmount+'</span>元起投</span></li>';
			}
			//会员专享icon
			if(jsonObj.adjustRate == "0"){ 
				
			}else{
				html += '<li class="MT5 PL25 positionR"><img class="positionA redIcon" src="'+ contextPath +'/pic/weixin/index/version0413/icon8.png" style="width:15px;"><span class="grayTex ML5P font12">专享加息'+jsonObj.adjustRate+'%</span></li>';	
			}
			html += '</ul>';
			html += '<div class="fr">';
			html += '<div class="progressState circle'+ i +' positionR" value='+ jsonObj.finishedRatio +'>';
			html += '<div class="progressHead textC positionA">';
			html += '<h4 class="grayTex lineHeight1_8x font12">已抢</h4>';
			html += '<span class="redTex font26 lineHeight100">'+ jsonObj.finishedRatio +'<span class="font20">%</span></span>';
			html += '</div>';
			html += '</div>';
			html += '</div>';
			html += '</div>';
			html += '</div>';
			if (jsonObj.finishedRatio != 100) {						
				if(jsonObj.planId == "108"){
					html += "<div class=\"width70 blockC\"><a onclick=\"goProductDetails('" + jsonObj.loanId + "','" + jsonObj.planId + "','" + jsonObj.scatteredLoanId + "','" + jsonObj.contactAmount + "','" + jsonObj.productName + "')\" class=\"btnIndex PTB10 width100 block radius5 font16\">立即加入</a></div>";
				}else if(jsonObj.planId == "109"){
					if(jsonObj.day == 0 && jsonObj.hour == 0 && jsonObj.min == 0 && jsonObj.sec == 0){	
						html += "<div class=\"width70 blockC\"><a class=\"btnIndex PTB10 width100 block radius5 font16\" onclick=\"goDemandOrderConfirmation('" + jsonObj.scatteredLoanId + "','" + jsonObj.productName + "','" + jsonObj.remanAmount + "','" + jsonObj.annualRate + "','" + jsonObj.investMinAmount + "','" + jsonObj.productName + "')\">立即加入</a></div>";
					}else{
						html += "<div class=\"width70 blockC\"><a class=\"btnIndexInactivite PTB10 width100 block radius5 font16\">敬请期待</a></div>";
            		}
				}
                
            } else {
				html += "<div class=\"width70 blockC\"><a class=\"btn btnIndexInactivite width100 block radius5 font16 grayTex\">已售罄</a></div>";
            }
			html += '</div>';	
			html += '</figure>';
			
			if(i == 0){   
            	html2 += '<li class="redBkg"></li>';
	        }else{
		        html2 += '<li></li>';
	        }
			
        });
    }
    html += '</div>';
    $("#productLoanList").html(html);
    $("#productLoanPosition").html(html2);
    
    $.each(data.list,function(i, jsonObj) {
	    $('.progressState.circle'+i).circleProgress({
		    value:$('.progressState.circle'+i).attr("value") / 100,
		    size:80,
		    thickness:5,
		    startAngle:-Math.PI /2,
		    lineCap: 'round',
		    fill:{ gradient: ["#ff5b58", "#f8ab03"] }
		});
	});
    


    var productLoanListSlider =
	  Swipe(document.getElementById('productLoanList'), {
	    auto: false,
	    continuous: true,
	    callback: function(pos) {
	      var i = productLoanPosition.length;
	      while (i--) {
	        productLoanPosition[i].className = ' ';
	        $('.progressState.circle'+i).circleProgress({
			    value:$('.progressState.circle'+i).attr("value") / 100,
			    size:80,
			    thickness:5,
			    startAngle:-Math.PI /2,
			    lineCap: 'round',
			    fill:{ gradient: ["#ff5b58", "#f8ab03"] }
			});
	      }
	        productLoanPosition[pos].className = 'redBkg';
	    }
	  });
	var productLoanPosition = document.getElementById('productLoanPosition').getElementsByTagName('li');
};


function getImgNaturalDimensions(img, callback) {
    var nWidth, nHeight;
    if (img.naturalWidth) { // 现代浏览器
        nWidth = img.naturalWidth;
        nHeight = img.naturalHeight;
    } else { // IE6/7/8
        var image = new Image();
        image.src = img.src;
        image.onload = function() {
            callback(image.width, image.height);
        }
    }
    return [nWidth, nHeight]
}


//加息券跳转
$("#addRates").click(function(event) {
    window.location.href = contextPath + "wxcoupons/goMyInterest";
    sessionStorage.setItem("from","all");
});
//自定义快捷菜单
var setAllIcoFuncList= function(data){
	if(data.rescode == "00000"){
		var html= "";
		if(data.list==null||data.list==""){
			
		}else{
			for(var i=0;i<8;i++){
				html += '<li class="width25 positionR fl boxSizing textC">';
				html += 	'<div class="width70 ML15P MR15P">';
				html += 		'<a href=' + data.list[i].webview_url + ' class="clearfix MT10 ">';
				
				html += 			'<div  class="positionR width40P blockC">';
				html +=						'<img src="'+ data.list[i].image_url_choose +'" width="40px">';
 				if(data.list[i].ico_func_name=='K码激活'){
 				html +=						'<span class="positionA Cashback">返现</span>';
 				}
				html +=				'</div>';
				html += 			'<span class="font12 blackTex MB5 block" >'+ data.list[i].ico_func_name +'</span>';
				html += 		'</a>';
				html +=		'<div>';
				html += '</li>';
			}
			$("#AllIcoFuncList").css({
				  "background":'url('+data.backIcon+')',
				  "background-size":"cover"
				  });
		}
	}else{
		
	}
	$("#AllIcoFuncList").append(html);
}