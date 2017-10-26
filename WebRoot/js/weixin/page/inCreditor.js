var goProductDetails = function(sloanId) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxdeposit/goInCreditorDetails";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "sloanId";
    inp.value = sloanId;
    temp.appendChild(inp);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var ymArray = "";
var week=['周日','周一','周二','周三','周四','周五','周六'];
var setInCreditor = function(data) { //再投债权生成HTML
	var sysdate=data.sysdate;//获取系统时间
    var html = ""; //拼接html
    var inCreditorNum=$('#accountAmount').val();
    inCreditorNum=numFormat(inCreditorNum);
    if (data.list == null || data.list == "") {
    	$("#creditorWrap").html(" ");
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无在投债权信息</p>';
        html += '<p class="p2">如果有在投债权信息，您将在这里看到</p>';
        html += '</div>';
        $("#creditorWrap").append(html);
    } else {   	
		$('#inCreditorText').html('当前在投债权（元）');
		$('#inCreditor').html(inCreditorNum);
       $.each(data.list,
        function(i, jsonObj) {
        	var newdate=getDate(sysdate);
        	var date=getDate(jsonObj.investTime.substring(0,10));
            var datDate=Math.floor(Math.floor((newdate-date))/1000/86400);
            var iWeek = date.getDay();
	       	var ym =getDate(jsonObj.investTime).Format("yyyy年MM月");
            var md =getDate(jsonObj.investTime).Format("MM/dd");
            var newYm=getDate(sysdate).Format("yyyy年MM月");
            if(ym != ymArray){
		       	ymArray = ym;
                if(ym == newYm){
	                 html += '<div class="grayTex heigh30 width90 PLR5P font14 bkg4 MTN1"  onclick="getServAgreementByType('+  jsonObj.orderId +','+ jsonObj.sloanId +')">本月</div>';
                }else{
                    html += '<div class="grayTex heigh30 width90 PLR5P font14 bkg4 MTN1" onclick="getServAgreementByType('+  jsonObj.orderId +','+ jsonObj.sloanId +')">'+ym+'</div>';
                }
	       	}        
			html += '<div class="whiteBkg grayTex width90 PLR5P" onclick="getServAgreementByType('+  jsonObj.orderId +','+ jsonObj.sloanId +')">';
			html += '<div class=" clearfix font16 PT9">';
			  if(datDate==0){
			    html+='<span class="fl font16">今天</span>';
			 }else if(datDate==1){
			    html+='<span class="fl font16">昨天</span>';
			 } else{
			    html+='<span class="fl font16">'+week[iWeek]+'</span>';
			 }
			html +='<span class="fr redTex font18">+'+numFormat(jsonObj.investAmount)+'</span>';
			html += '</div>';
			html += '<div class="PB5 clearfix borderBottomTGray"><span class="fl font14">'+md+'</span><span class="fr"><a class="underLine font12">查看合同</a></span>';
			html += '</div>';
			html += '</div>';			
       });
		html+='<div class="whiteBkg width100 MTN1 heigh1">';//把最下面的border给去掉
		html+='</div>';		
		 $("#inCreditorList").append(html);
    }
   
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getInCreditor"));
};

var getInCreditor = function(page){
     ajaxRequest(contextPath + "wxdeposit/getOrderCredit", "current=" + page + "&pageSize=10", setInCreditor); 
}
var setServAgreement = function(data){ 
    if (data.rescode != "00000") {
        errorMessage(data.rescode);
    } else {
        window.location.href = data.serAgreementUrl;
    }
};
$(document).ready(function() {
    getInCreditor(1);
});

var getServAgreementByType = function(orderId,sloanId){
	ajaxRequest(contextPath + "wxagreement/getServAgreementByType","type=creditoTranAgreement&orderId=" + orderId + "&sloanId="+ sloanId, setServAgreement);
}