var goProductDetails = function(id,productName,remanAmount,annualRate,investMinAmount) {
    var temp = document.createElement("form");
    temp.action = "goDemandOrderConfirmation";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "id";
    inp.value = id;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "productName";
    inp2.value = encodeURI(productName);
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "remanAmount";
    inp3.value = remanAmount;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "annualRate";
    inp4.value = annualRate;
    temp.appendChild(inp4);

    var inp5 = document.createElement("input");
    inp5.name = "investMinAmount";
    inp5.value = investMinAmount;
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
    inp2.value = "109";
    temp.appendChild(inp2);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}

var requestProductList = function(page) {
    ajaxRequest(contextPath + "wxdeposit/showInvestmentList", "current=" + page + "&pageSize=5", setDemanProductList);
};
var startDay;
var startHour;
var startMin;
var startSec;
var setDemanProductList = function(data) {
	//定义两个数组 秒数数组和标的id数组
	var timeV = new Array;
	var youtime = new Array;
    var html = ""; //拼接html
    
    if (data.list == null || data.list == "") {
        html += '<div class="PTB10P">';
        html += '<img src="../pic/weixin/warning.png" alt="" height="120">';
        html += '<p class="font16 grayTex MT20">暂无可投标的</p>';
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
            if(jsonObj.day == 0 && jsonObj.hour == 0 && jsonObj.min == 0 && jsonObj.sec == 0){
	            if(jsonObj.investUserNum == 0){
		            
	            }else{
                	html += "<a onclick=\"goDemandInvestorList('" + jsonObj.loanId + "')\" class=\"grayTex font14 fr MT7\">已有<font class=\"redTex\">"+ jsonObj.investUserNum +"</font>人购买></a>";
                }
            }else{
					//倒计时开抢
					startDay = jsonObj.day;
					startHour = jsonObj.hour;
				    startMin = jsonObj.min;
					startSec = jsonObj.sec;
					youtime.push(startDay*86400000 + startHour*3600000 + startMin*60000 + 1000*startSec);// 每个时间放到数组里
					timeV.push(jsonObj.loanId); //每个标的id放在数组里
					html += '<div class="fr investUserNum" id="time'+ jsonObj.loanId +'"></div>';
							     
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
			html += '<tr><td><font>总金额</font></td><td><font class="PL20 strong">' + numFormatInteger(jsonObj.saleTotalAmount) +'</font></td></tr>';
			html += '<tr><td><font>担保方式</font></td><td><font class="PL20 strong">本息保障</font></td></tr>';
			html += '</table>';
			html += '</div>';
			html += '<div class="width30 textR fr">';
            html += '<div class="largeDetails">';
            if (jsonObj.finishedRatio != 100) {
            html += '<img src="../pic/weixin/progressBarLarge1.png" height="80" alt="">';
            html += '<div class="largeDetailsTex1">';
//             html += '<div class="whiteTex font40">' + jsonObj.annualRate + '<font class="font14">%</font></div>';
			html += '<div class="whiteTex font28">' + numFormatInteger(jsonObj.finishedRatio) + '<font class="font14">%</font><br/><span class="font12">已抢</span></div>';

            html += '</div>';
            }else {
	        html += '<img src="../pic/weixin/progressBarLarge2.png" height="80" alt="">';
	        html += '<div class="largeDetailsTex1 largeDetailsTex2">';
//             html += '<div class="whiteTex font40">' + jsonObj.annualRate + '<font class="font14">%</font></div>';
			html += '<div class="whiteTex font28">' + jsonObj.finishedRatio + '<font class="font14">%</font></div>';

            html += '</div>';
            
	        }
	        html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '<ul class="inlineBUl width100 clearfix textL verticalM MB10">';
			html += '<li class="width30"><font class="font14 block">预期年收益</font><font class="redTex font24 strong">' + jsonObj.annualRate + '</font><font class="font14 redTex">%</font></li>';
			html += '<li class="width40"><font class="font14 block">起投金额（元）</font><font class="redTex font24 strong">' + numFormatInteger(jsonObj.investMinAmount)+'</font></li>';
			html += '<li class="width30">';
            if (jsonObj.finishedRatio != 100) {
/*
                html += '<div class="progressBar pink">';
                html += '<div class="progressControl" style="width:' + jsonObj.finishedRatio + '%"></div>';
                html += '<span class="whiteTex progressTex">已抢' + jsonObj.finishedRatio + '%</span>';
*/				
				if(jsonObj.day == 0 && jsonObj.hour == 0 && jsonObj.min == 0 && jsonObj.sec == 0){
                    html += "<a class=\"btn PTB7 width100 radius5 block font16\" onclick=\"goProductDetails('" + jsonObj.id + "','" + jsonObj.productName + "','" + jsonObj.remanAmount + "','" + jsonObj.annualRate + "','" + jsonObj.investMinAmount + "')\">立即投资</a>";
                    html += '</li>';
                }else{
                    html += "<a class=\"btn PTB7 width100 radius5 block font16\">敬请期待</a>";
                    html += '</li>';
                }
				
								
                
            } else {
			html += "<a class=\"btn PTB7 width100 radius5 block inactiveBtn2 font16\">已售罄</a>";
/*
				html += '<ul class="rowUl blackBkgUl MB20">';
				html += '<li>剩余</li>';
				html += '<li class="mark">:</li>';
				var remanAmount = numFormat(jsonObj.remanAmount);
			    for (i = 0; i <= remanAmount.length - 1; i++) {
			        if (remanAmount.substr(i, 1) != "." && remanAmount.substr(i, 1) != ",") {
			            html += '<li><font>' + remanAmount.substr(i, 1) + '</font></li>';
			        } else {
			            html += '<li class="mark"><font>' + remanAmount.substr(i, 1) + '</font></li>';
			        }
			    }
				html += '</ul>';
*/
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
    
    //循环一共有几个要上架的标 取出每个标的信息
    for(i=0;i<timeV.length;i++){
    	lxfEndtime(youtime[i],"#time"+timeV[i])
    }
};
//每个标都使用倒计时
function lxfEndtime(youtime,lxftime){
  	$(lxftime).each(function(){
        youtime -= 1000;
        var seconds = youtime/1000;
        var minutes = Math.floor(seconds/60);
        var hours = Math.floor(minutes/60);
        var days = Math.floor(hours/24);
        var CDay= days ;
        var CHour= hours % 24;
        var CMinute= minutes % 60;
        var CSecond= Math.floor(seconds%60);//"%"是取余运算，可以理解为60进一后取余数，然后只要余数。 

        if(youtime >= 0){
        	$(this).html("<i class='objecttimer'></i><span class='day redTex'>"+days+"</span><em>天</em><span class='hour redTex'>"+CHour+"</span><em>时</em><span class='mini redTex'>"+CMinute+"</span><em>分</em><span class='sec redTex'>"+CSecond+"</span><em>秒后开抢</em>");          //输出有天数的数据    
        }else {
            $(this).hide();
        }
	                      
    });
    setTimeout('lxfEndtime('+youtime+',"'+lxftime+'")',1000);
  };
$(document).ready(function() {
    ajaxRequest(contextPath + "wxdeposit/showInvestmentList", "current=1&pageSize=5", setDemanProductList);
});



