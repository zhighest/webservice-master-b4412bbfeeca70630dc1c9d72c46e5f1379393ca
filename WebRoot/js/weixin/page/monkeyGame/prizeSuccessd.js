var mobile;
$(document).ready(function(){	
	var prize = $("#prize").val();
	mobile = $("#mobile").val();
	$("#parameter").html(prize);
	
});
$("#prizeListBtn").click(function(){
	window.location.href = contextPath+ "wxprize/goPrizeList?mobile="+mobile;	
})
