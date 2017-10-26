var goProductDetails = function(loanId, planId, scatteredLoanId, contactAmount,productName) {
    var temp = document.createElement("form");
    temp.action = "goProductBuy";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "planId";
    inp2.value = planId;
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "sloanId";
    inp3.value = scatteredLoanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "contactAmount";
    inp4.value = contactAmount;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "cpType";
    inp5.value = encodeURI(productName);
    temp.appendChild(inp5);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var goDemandInvestorList = function(loanId) {
    var temp = document.createElement("form");
    temp.action = "goDemandInvestorList";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

	var inp2 = document.createElement("input");
    inp2.name = "productType";
    inp2.value = "108";
    temp.appendChild(inp2);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var requestProductList = function(page) {
    ajaxRequest(contextPath + "wxproduct/productListInfo", "current=" + page + "&pageSize=5", setProductList);
};
var setProductList = function(data) {
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="PTB10P">';
        html += '<img src="../pic/weixin/warning.png" alt="" height="120">';
        html += '<p class="font16 grayTex MT20">' + txtProductListNull + '</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += "<div class=\"proItem positionR\">";
            //会员专享图标
            if (jsonObj.finishedRatio != 100 ) {
	            if(jsonObj.adjustRate != 0){
	        		html += '<div class="vipIcon positionA"><img src="../pic/weixin/vipIcon.png" height="60"></div>'; 
	        	} else{
		        	
	        	} 
            }else {
	        	   
	        }
            html += '<div class="proCon">';
            html += '<h4 class="siteH4">';
            html += '<font class="proTitle MR10">' + jsonObj.productName + '</font>';
            html += '<img src="../pic/weixin/icon19.png" alt="" height="22" class="icon19">';
            if(jsonObj.investUserNum == 0){   
            }else {
	        	html += "<a onclick=\"goDemandInvestorList('" + jsonObj.scatteredLoanId + "')\" class=\"grayTex font14 fr MT7\">已有<font class=\"redTex\">"+ jsonObj.investUserNum +"</font>人购买></a>";    
            } 
            html += '</h4>';
            html += '<div class="clearfix positionR">';
            if (jsonObj.finishedRatio != 100) {
	            
            }else {
	        	html += '<div class="endIconArea"><img src="../pic/weixin/end.png" alt="" height="120"></div>';    
	        }
            html += '<div class="bkgArea width94 blockC">';
            html += '<div class="clearfix borderDottedB MB10">';
            html += '<div class="fl width60 MT15">';
			html += '<table class="textL prdTable font14">';
			html += '<tr><td><font class="">可投金额</font></td><td><font class="PL20 strong">' + numFormatInteger(jsonObj.remanAmount)+'</font></td></tr>';
			html += '<tr><td><font>总金额</font></td><td><font class="PL20 strong">' + numFormatInteger(jsonObj.contactAmount) +'</font></td></tr>';
			html += '<tr><td><font>理财期限</font></td><td><font class="PL20 strong">' + jsonObj.remanPeriods+'个月</font></td></tr>';
			html += '</table>';
			html += '</div>';
			html += '<div class="width30 textR fr">';
            html += '<div class="largeDetails">';
            if (jsonObj.finishedRatio != 100) {
	            html += '<img src="../pic/weixin/version1125/progressBarLarge3.png" height="80" alt="">';
	            html += '<div class="largeDetailsTex1">';
				html += '<div class="whiteTex font28">' + numFormatInteger(jsonObj.finishedRatio) + '<font class="font14">%</font></div>';
				html += '<div class="whiteTex font12">已抢</div>';
	            html += '</div>';
            }else {
		        html += '<img src="../pic/weixin/progressBarLarge2.png" height="80" alt="">';
		        html += '<div class="largeDetailsTex1 largeDetailsTex2">';
				html += '<div class="whiteTex font28">' + jsonObj.finishedRatio + '<font class="font14">%</font></div>';
	            html += '</div>';
	        }
	        html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '<ul class="inlineBUl width100 clearfix textL verticalM MB10">';
			html += '<li class="width30"><font class="font14 block">预期年收益</font><font class="redTex strong font24">' + jsonObj.annualRate + '</font><font class="font14 redTex">%</font></li>';
			html += '<li class="width40"><font class="font14 block">起投金额</font><font class="redTex strong font24">' + numFormatInteger(jsonObj.investMinAmount) + '</font><font class="font14 redTex">元</font></li>';
			html += '<li class="width30">';
            if (jsonObj.finishedRatio != 100) {		
				html += "<a class=\"btn PTB7 fixBtn width100 radius5 block font16\" onclick=\"goProductDetails('" + jsonObj.loanId + "','" + jsonObj.planId + "','" + jsonObj.scatteredLoanId + "','" + jsonObj.contactAmount + "','" + jsonObj.productName + "')\">立即投资</a>";
				html += '</li>';
            } else {
				html += "<a class=\"btn PTB7 width100 radius5 block inactiveBtn2 font16\")\">已售罄</a>";
            }
            html += '</ul>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
        });
    }
    $("#productList").append(html);
    $("#proListPaging").html(pagingMobile(data, "requestProductList"));
};
$(document).ready(function() {
    ajaxRequest(contextPath + "wxproduct/productListInfo", "current=1&pageSize=5", setProductList);  
    var channel = $("#channel").val();
    var parm = $("#parm").val();
	if(channel == "LINGDANG"){
		$("#activity20151103").attr("href",contextPath + "wxactivity/goLDActivityDetal?"+parm)
	}
 
});