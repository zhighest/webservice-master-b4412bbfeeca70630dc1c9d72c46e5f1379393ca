$(".viewIcon").click(function(){
	$("#popup1").show();
})
$(".closedLetterBtn").click(function(){
	$("#popup1").hide();
})
var windowHeight = $(window).height();

var popheight = function() {
	$(".popCon").css("height",windowHeight - 70);
	$(".agreenmentCon").css("height",windowHeight - 120);
}

$(window).resize(function(){
	popheight();
});
$(document).ready(function(){
	popheight();
})