$(document).ready(function(){
	$('.ProblemClassification ul div').toggleClass('ProblemNone');
	$('.ProblemClassification ul p').toggleClass('bottomGray');
	$('.ProblemClassification li').click(function(){
		var _this = this;
		$(_this).find('div').slideToggle();
		$(_this).find('p').toggleClass('bottomGray');
		$(_this).find('p img').toggleClass('rolateImage');
	})
})
