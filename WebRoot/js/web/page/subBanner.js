function bannerWidth() {
    var contentWidth = $(window).width();
    
    var bannerWidth = $(".subBanner > img").width();
    var controlLeft =  - (bannerWidth/2 - contentWidth/2);
    $(".subBanner > img").css("margin-left", controlLeft)
    					.css("display","block");
    
}
$(document).ready(function () {
    bannerWidth();
});
$(window).resize(function(){
    bannerWidth();
});