
var goUseCoupon = function(couponId,couponAmount,limitAmount,labelFlag) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxInvest/getRolloutIndex";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "couponId";
    inp.value = couponId;
    temp.appendChild(inp);
    
    var inp2 = document.createElement("input");
    inp2.name = "couponAmount";
    inp2.value = couponAmount;
    temp.appendChild(inp2);

    var inp7 = document.createElement("input");
    inp7.name = "from";
    inp7.value = "LBWX";
    temp.appendChild(inp7);
    
    var inp8 = document.createElement("input");
    inp8.name = "limitAmount";
    inp8.value = limitAmount;
    temp.appendChild(inp8);
    
    var inp9 = document.createElement("input");
    inp8.name = "labelFlag";
    inp8.value = labelFlag;
    temp.appendChild(inp9);
    
    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var getCouponList = function(page) {
	var invest=getUrlParam("invest")
	if(invest == 1||invest == "1")
		ajaxRequest(contextPath + "wxenjoy/getCouponList", "current=" + page + "&pageSize=4&status=2", setCouponList,"GET");
	else{
		ajaxRequest(contextPath + "wxenjoy/getCouponList", "current=" + page + "&pageSize=4&status=0", setCouponList,"GET");
	}
};

//抵用券列表
function setCouponList(data){
    var html = "";
    if(data.rescode == "00000"){
        if(data.list == "" || data.list == null){
			html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无手续费抵用券</p>';
	        html += '<p class="p2">如果有手续费抵用券，您将在这里看到</p>';
	        html += '</div>';
        }else{
            $.each(data.list, function(i, jsonObj) {
            	var ym = getDate(jsonObj.endDate).Format("yyyy/MM/dd");
            	var couponAmount=jsonObj.couponAmount;
            	if(jsonObj.labelFlag == "0"){
            		couponAmount='-1';
            	}
                html +='<li class="border whiteBkg PLR5 PTB5 MT10" onclick="useVoucher('+jsonObj.couponId+','+couponAmount+','+jsonObj.limitAmount+','+jsonObj.labelFlag+')">';
				html += '<div class="ticketConcent"><div class="whiteBkg border ticketConcent01">';
				html += '<table class="width90 MLRA">';
				html += '<tr>';
				
				html += '<td class="font14 width60 lineHeight1_5x borderDashedR PTB20">';
				html += '<ul class="grayTex font12 MT5 transform4" >';
				if(jsonObj.status == "0"){
					if(jsonObj.couponType =="999") {
						html += '<li class="font16 blackTex">通用券</li>';
					}else if(jsonObj.couponType =="107") {
						html += '<li class="font16 blackTex">优享计划专享券</li>';
					}
				}
				else{
					if(jsonObj.couponType =="999") {
						html += '<li class="font16 grayTex">通用券</li>';
					}else if(jsonObj.couponType =="107") {
						html += '<li class="font16 grayTex">优享计划专享券</li>';
					}
				}
				
				html += '<li>'+ym+'到期</li>';
				
				html += '</ul>';
				html += '<div class="width80 MT5P">';
				html += '</div>';
				html += '</td>';
				if(jsonObj.status == "0"){
					html += '<td class="redTex width40 textC">';
				}
				else{
					html += '<td class="grayTex width40 textC">';
				}
				
				if(jsonObj.labelFlag == "0") {
					html += '<p><span class="font22 lineHeight40">全免券</span></p>';
					
				}else {
					html += '<p><span>¥</span><span class="font40 helveticaneue">'+jsonObj.couponAmount+'</span></p>';	
				}
				html += '<p class="font12">手续费满'+jsonObj.limitAmount+'元可用</p>';
				html += '</td>';
				html += '</tr>';
				html += '</table>';
				html += '</div></div>';
				html += '</li>'; 
		
			});
            
        }
    }
    $("#myList").append(html);
    $("#proListPaging").html(pagingMobile(data, "getCouponList"));
}
//使用抵用券
function useVoucher(couponId,couponAmount,limitAmount,labelFlag){
	if(getUrlParam("invest")=="1"){
		return false
	}
    $(".alertBg").stop().show();
    $("#cancel").attr("onclick",'$(".alertBg").stop().hide();');
	$("#enterBtn").attr("onclick",'goUseCoupon('+couponId+','+couponAmount+','+limitAmount+','+labelFlag+')');
 }

//var couponAmount	
$(document).ready(function() {
// 	getCouponList();
	//setCouponList(data);
// 	ajaxRequest(contextPath + "wxenjoy/getCouponList", "current=1&pageSize=5",setCouponList,"GET");
	getCouponList(1);
});
