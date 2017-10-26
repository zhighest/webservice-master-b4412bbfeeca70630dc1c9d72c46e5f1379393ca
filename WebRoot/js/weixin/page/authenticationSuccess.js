
var productId;
$(document).ready(function() {
    productId =$("#productId").val();
    if(productId==109){
    	$("#backBtn").click(function() {
   			window.location.href = contextPath + "wxproduct/goDemandProductIndex?fixDemandSwitch=demand";  
   		});
	}else if(productId==108){
		$("#backBtn").click(function() {
   			window.location.href = contextPath + "wxproduct/goDemandProductIndex?fixDemandSwitch=fix";  
   		});
	}
});