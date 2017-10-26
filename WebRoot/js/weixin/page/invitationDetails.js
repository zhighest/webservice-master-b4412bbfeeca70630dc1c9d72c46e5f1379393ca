$(document).ready(function(){
	ajaxRequest(contextPath + "/wxuser/activityRecord", "userId=28644665", getShareMessage);
})
function getShareMessage(data){//活动文案及邀请人数
	var j=data.count;
	if(data.rescode==00000){
			 $("#wrapImg2").text(j) ;
			 $("#smallActivityRecord").html(data.smallActivityRecord);
			 $("#bigActivityRecord").html(data.bigActivityRecord);
	}
	else{
        if(data.errorMessage == "" || data.errorMessage == null || data.errorMessage == "null"){
			errorMessage(data.resmsg_cn)
		}
		else{
			if(data.errorMessage.message_level == "1"){
				errorMessage(data.errorMessage.message_content)	
			}
			else if(data.errorMessage.message_level == "2") {
				alertWrap(data.errorMessage.message_content)
			}
			else {
				errorMessage(data.resmsg_cn);	
			}
		}
	}		
}