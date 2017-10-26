var goGetPrize = function(installment,kind,prize_msg,sort,prize,mobile) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxprize/goGetPrize";
    temp.method = "GET";

    var inp = document.createElement("input");
    inp.name = "installment";
    inp.value = installment;
    temp.appendChild(inp);
    
    var inp1 = document.createElement("input");
    inp1.name = "kind";
    inp1.value = kind;
    temp.appendChild(inp1);
    
    var inp2 = document.createElement("input");
    inp2.name = "prize_msg";
    inp2.value = prize_msg;
    temp.appendChild(inp2);
    
    var inp4 = document.createElement("input");
    inp4.name = "sort";
    inp4.value = sort;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "prize";
    inp5.value = prize;
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "mobile";
    inp6.value = mobile;
    temp.appendChild(inp6);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var documentHeight = $("body").height();
function bodyBkg(){
	$("body").css("background-size",documentHeight + 20);
}

$(window).resize(function(){
  bodyBkg()
});
$(document).ready(function(){	
  bodyBkg();
  mobile = $("#mobile").val();
  var channel = $("#channel").val();
  ajaxRequest(contextPath + "wxprize/prizeList", "mobile="+mobile, setUserInfo,"POST");
});
var mobile;
//微信进入兑换页面下载APP
function weixinShow() {
/*
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
*/
		$("#weixinLogin").show();
		$("#prizeList").hide();
		$("#noResult").hide();	
// 	}
}

function setUserInfo(data) {
	var ua = navigator.userAgent.toLowerCase();
	var html = ""; //拼接html
	if(ua.match(/MicroMessenger/i)=="micromessenger"){
		weixinShow();	
	}else {
		if(data.prizeList == null || data.prizeList == ""){
			$("#prizeList").hide();
	    	$("#noResult").show();
	    }else{
			$("#prizeList").show();
	        $.each(data.prizeList, function(i, jsonObj) {
	            html += "<div class=\"listBox\">";
				html += '<h4 class=\"textC font16 yuanti\">兑换票</h4>';
				html += '<ul class=\"listUl\">';
				html += '<li class=\"width35 textL firstLi\"><span class=\"font\">第'+ jsonObj.installment +'期</span><br/><span class=\"\">'+ jsonObj.prize +'</span></li>';
				html += '<li class=\"width34 verM\"><span class="popTitle font10" id="">第<span class="font14">'+ jsonObj.sort +'</span>名</span></li>';
				if(jsonObj.enable == "Y") {
					html += "<li class=\"verM\"><a class=\"block\" onclick=\"goGetPrize('" + jsonObj.installment + "','" + jsonObj.kind + "','" + jsonObj.prize_msg + "','" + jsonObj.sort + "','" + jsonObj.prize + "','" + mobile + "')\">";
					html += '<img src="../pic/weixin/monkeyGame/btn1.png" width=\"90%\" class=\"\"></a></li>';
				}
				if(jsonObj.enable == "N") {
					html += "<li class=\"verM\"><a class=\"block\"><img src=\"../pic/weixin/monkeyGame/btn2.png\" width=\"90%\" class=\"\"></a></li>";
				}
				html += '</ul>';
				html += '</div>'; 
	         
			});
		}
	}
	
	$("#prizeList").append(html); 
}

