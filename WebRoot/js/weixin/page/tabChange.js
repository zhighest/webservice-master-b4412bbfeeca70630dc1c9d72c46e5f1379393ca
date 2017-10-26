$(function(){
	$(".parent li").on("click",function(){
		$(this).addClass("light ").
		removeClass('dark').
		siblings().removeClass('light').
		addClass("dark ")
	})
})