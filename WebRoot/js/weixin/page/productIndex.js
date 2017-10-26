
var setProductIndex =function(data){
    if(data.rescode!=="00000"){
        errorMessage(data.resmsg);  
    }
    var html="";
    if (data.list == null || data.list == "") {
    } else {
        $.each(data.list,function(i, jsonObj){  
        if(jsonObj.isValid=='Y'){
            var goUrl="";
            
            if(jsonObj.id=="1"){
                goUrl=contextPath+"wxproduct/goDemandProductIndex?fixDemandSwitch=demand";            
            }else if(jsonObj.id=="2"){
                goUrl=contextPath+"wxproduct/goDemandProductIndex?fixDemandSwitch=fix";
            }else if(jsonObj.id="3"){
                
                goUrl=contextPath+"wxInvest/getPlanProductIndex";
            }           
            html+='<div class="show"><a href='+goUrl+'>';
            html+='<div class="clearfix"><div class="fl main">';
            html+='<div class="fl width35"><img src='+jsonObj.icon+' class="width100 block MLRA"></div>';
            html+='';
            html+='<div class="textWrap"><div class="fr textL text">';
            html+='<h4 class="font16 ">'+jsonObj.productName+'</h4><p class="redTex  font12">'+jsonObj.feature+'</p></div></div></div>';
            html+='<div class="imgBox"><img src="'+contextPath+'/pic/weixin/index/version0413/line.png?"></div>';
            html+='<div class="percent fl textC positionR">';
            html+='<div class="redTex clearfix number "><div class="font40 height fl helveticaneue rate">'+formatNum(jsonObj.returnRate)+'<span class="positionR per font16">%</span></div></div>';
            html+='<div class="clearfix  rateName"><p class="grayTex font12 fl letterspace">'+jsonObj.returnRateName+'</p></div></div></div>';
            html+='<div class="textL grayTex PTB5 textBkg PLR5 font12">'+jsonObj.introduction+'</div></a></div>';
        }           
      }) 
             $('#banner img').attr('src',data.investBanner)
            $('#content').append(html) 
    }
}

$(document).ready(function() {
    ajaxRequest(contextPath+"wxenjoy/getInvestList","",setProductIndex);
    ajaxRequest(contextPath+"springFestival/getSpringFestivalIcon","marks=weixin",setSpringFestivalIcon); 
});
function setSpringFestivalIcon(data){
	if(data.rescode=="00000"){
		var $icon12= $("#icon12"),
		$icon13= $("#icon13"),
		$icon14= $("#icon14"),
		imgUrl=getUrlHttp();
	 $icon12.attr("src", imgUrl+data.icon1.ficonDefault);
	 $icon13.attr("src", imgUrl+data.icon2.ficonDefault);
	 $icon14.attr("src", imgUrl+data.icon4.ficonDefault);
	 if(data.cjtbkg == "0"||data.cjtbkg == 0){
		 $("#btmBar a").css({"height":"38px","padding-top":"4px"});
	     $("#btmBar img").attr("width","25px").css("top","0");
	     $(".bottom").css({"height":"70px","text-align":"center"});
	     }
	 else if(data.cjtbkg == "1"||data.cjtbkg == 1){
		 $("#btmBar a").css("height","50px");
	     $("#btmBar img").attr("width","36px");
	     $(".bottom").css({"height":"88px","text-align":"center"});
	 }
	 var accoutState = false;
	 var pageTag = $("#pageTag").val();
	 if (pageTag == "productIndex") {
		 $icon13.attr("src", imgUrl+data.icon2.ficonSelect);
	     $("#productIndexBtn").addClass("active");
	     if(data.cjtbkg == "1"||data.cjtbkg == 1){
	     $("#productIndexBtn img").addClass("bigImg"); 
	     }
	 } else if (pageTag == "myAsset") {
		 $icon14.attr("src", imgUrl+data.icon4.ficonSelect);
	     $("#myAssetBtn").addClass("active");
	     if(data.cjtbkg == "1"||data.cjtbkg == 1){
	     $("#myAssetBtn img").addClass("bigImg"); 
	     }
	 }else if (pageTag == "index") {
		 $icon12.attr("src", imgUrl+data.icon1.ficonSelect);
	     $("#indexBtn").addClass("active");
	     if(data.cjtbkg == "1"||data.cjtbkg == 1){
	    	 $("#indexBtn img").addClass("bigImg"); 
	     } 
	 }
	 $("#btmBar img").removeClass("none");
	}
	else{
		 $("#btmBar img").removeClass("none");
		 $("#btmBar img").removeClass("bigImg");
		 $("#btmBar a").css({"height":"38px","padding-top":"4px"});
	     $("#btmBar img").attr("width","25px").css("top","-4px");
	     $(".bottom").css({"height":"70px","text-align":"center"});
	}
}