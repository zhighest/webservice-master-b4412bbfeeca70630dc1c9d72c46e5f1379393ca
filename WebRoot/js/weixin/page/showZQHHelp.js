$(document).ready(function() {
	var UrlData = window.location.href.split("?")[1]; 
	var screenH = $('body').height();
	$(".showZQHHelp").height(screenH)
	$('.showZQHHelp').animate({ 'top': 0 }, 300);
	$('.closeBtn').click(function() {
		$('.showZQHHelp').animate({ 'top': screenH * 1.3 }, 300);
		UrlData = window.location.href.split("?")[1]; 
		window.location.href='useCashCoupon?'+UrlData;
	})
}); 