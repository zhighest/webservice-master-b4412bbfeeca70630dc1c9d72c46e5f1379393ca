function codeImg() {
	var winWidth = $(window).width();
	if(winWidth < 730 ) {
		$("#code").attr("src",contextPath + "pic/downLoad/img1_s.png");
	}else {
		$("#code").attr("src",contextPath + "pic/downLoad/img1.png");
}}
$(window).resize(function() {
	codeImg();
})
$(document).ready(function() {
	codeImg();
});