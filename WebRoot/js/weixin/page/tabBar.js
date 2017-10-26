
$(function(){
	ajaxRequest(contextPath + "springFestival/getSpringFestivalIcon", "marks=weixin",setSpringFestivalIcon,"GET");
})
function setSpringFestivalIcon(data){
	if(data.rescode=="00000"){
		$("#btmBar img").removeClass("bigImg");
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
	}
}