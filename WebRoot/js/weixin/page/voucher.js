//活期传来的购买进度
var finishedRatio = $("#finishedRatio").val();
var obj={};
obj.val="";
obj.productType="";
////传值，将接口里的值传到定期购买页面
var goProductDetails = function(loanId, planId, sloanId, contactAmount,productName,voucherId,voucherAmount,rateRises,rateIds,investmentAmount,finishedRatio) {
    var temp = document.createElement("form");
    var host1=getUrlParam("inquire");//购买金额
    if(host1!=undefined){
    	temp.action = contextPath + "wxtrade/goOrderConfirmation?inquire="+host1
    }else{
    	temp.action = contextPath + "wxproduct/goProductBuy"
    }
    
    temp.method = "POST";

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
    inp3.value = sloanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "contactAmount";
    inp4.value = contactAmount;
    temp.appendChild(inp4);
    
    var inp5 = document.createElement("input");
    inp5.name = "cpType";
    inp5.value = encodeURI(productName);
    temp.appendChild(inp5);

    var inp6 = document.createElement("input");
    inp6.name = "voucherId";
    inp6.value = voucherId;
    temp.appendChild(inp6);

    var inp7 = document.createElement("input");
    inp7.name = "voucherAmount";
    inp7.value = voucherAmount;
    temp.appendChild(inp7);

    var inp8 = document.createElement("input");
    inp8.name = "rateRises";
    inp8.value = rateRises;
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "rateIds";
    inp9.value = rateIds;
    temp.appendChild(inp9);

    var inp10 = document.createElement("input");
    inp10.name = "investmentAmount";
    inp10.value = investmentAmount;
    temp.appendChild(inp10);
    
    var inp11 = document.createElement("input");
    inp11.name = "finishedRatio";
    inp11.value = finishedRatio;
    temp.appendChild(inp11);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
//传值，将接口里的值传到活期购买页面
//从我的账户代金券进入订单详情页
var goDemandOrderConfirmation = function(loanId,tagId,investMinAmount,id,voucherId,voucherAmount,investmentAmount,adjustRate,inputAmountBac) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxproduct/goDemandOrderConfirmation";
    temp.method = "GET";
 
    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "tagId";
    inp2.value = tagId;
    temp.appendChild(inp2);

    var inp5 = document.createElement("input");
    inp5.name = "investMinAmount";
    inp5.value = investMinAmount;
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "id";
    inp6.value = id;
    temp.appendChild(inp6);

    var inp7 = document.createElement("input");
    inp7.name = "voucherId";
    inp7.value = voucherId;
    temp.appendChild(inp7);

    var inp8 = document.createElement("input");
    inp8.name = "voucherAmount";
    inp8.value = voucherAmount;
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "investmentAmount";
    inp9.value = investmentAmount;
    temp.appendChild(inp9);

    var inp10 = document.createElement("input");
    inp10.name = "adjustRate";
    inp10.value = adjustRate;
    temp.appendChild(inp10);
    
    var inp11 = document.createElement("input");
    inp11.name = "inputAmountBac";
    inp11.value = inputAmountBac;
    temp.appendChild(inp11);
    
    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
//传值，将接口里的值传到活期购买页面
//从活期订单确认页进入代金券再回到活期购买
var goOrderConfirm = function(loanId,tagId,investMinAmount,id,voucherId,voucherAmount,investmentAmount,adjustRate,inputAmountBac,finishedRatio) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxtrade/orderConfirm";
    temp.method = "POST";
 
    var inp = document.createElement("input");
    inp.name = "loanId";
    inp.value = loanId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "tagId";
    inp2.value = tagId;
    temp.appendChild(inp2);

    var inp5 = document.createElement("input");
    inp5.name = "investMinAmount";
    inp5.value = investMinAmount;
    temp.appendChild(inp5);
    
    var inp6 = document.createElement("input");
    inp6.name = "id";
    inp6.value = id;
    temp.appendChild(inp6);

    var inp7 = document.createElement("input");
    inp7.name = "voucherId";
    inp7.value = voucherId;
    temp.appendChild(inp7);

    var inp8 = document.createElement("input");
    inp8.name = "voucherAmount";
    inp8.value = voucherAmount;
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "investmentAmount";
    inp9.value = investmentAmount;
    temp.appendChild(inp9);

    var inp10 = document.createElement("input");
    inp10.name = "adjustRate";
    inp10.value = adjustRate;
    temp.appendChild(inp10);
    
    var inp11 = document.createElement("input");
    inp11.name = "inputAmountBac";
    inp11.value = inputAmountBac;
    temp.appendChild(inp11);
    
    var inp12 = document.createElement("input");
    inp12.name = "finishedRatio";
    inp12.value = finishedRatio;
    temp.appendChild(inp12);
    
    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var rateRises = $("#rateRises").val();//加息劵利率
//拉取产品列表
var setLoanByVochers = function(scatteredList,voucherId,voucherAmount,investmentAmount){
    var scatteredList = decodeURIComponent(scatteredList);
        var html = '';
        $.each(JSON.parse(scatteredList), function(i, jsonObj) {
            html += '<li class="borderT PLR5P PTB15 clearfix">';
            html += '<div class="blackTex PTB5 fl">'+jsonObj.productName;
            if(jsonObj.remanDays == "0") { 
                html += '（'+jsonObj.remanPeriods+'个月）</div>';
            }else {
                html += '（'+jsonObj.remanDays+'天）</div>'; 
            }
            html += "<div class='fr redBkg whiteTex PLR15 radius5 PTB5' onclick=\"goProductDetails('"+ jsonObj.bidId + "','"+ jsonObj.planId +"','"+ jsonObj.id +"','"+ jsonObj.contactAmount +"','" + jsonObj.productName +"','" + voucherId +"','"+voucherAmount+"','"+rateRises+"','"+rateIds+"','"+investmentAmount+"','"+finishedRatio+"')\">立即投资</div>";
            html += '</li>';
        });
        $("#productList").empty();
        $("#productList").append(html);
        $("#investAlertBg").show();
}
var adjustRate = $("#adjustRate").val();
var inputAmountBac = $("#inputAmount").val();
var fromUrl = $("#fromUrl").val();
var tagId = $("#sloanId").val();
//代金券列表
var setMyVouchers = function(data){
    if(data.rescode == "00000"){
        var html = '';
        if(data.list == "" || data.list == null){
            html += '<div class="listNull">';
            html += '<img src="../pic/weixin/list.png">';
            if(sloanId == 'null' || sloanId == '' || sloanId == null){
            	switch (obj.productType){
            	 case "108":
            		 html += '<p class="p1">暂无定期代金券</p>';
                     html += '<p class="p2">如果有定期代金券，您将在这里看到</p>';
                     break;
            	 case "109":
            		 html += '<p class="p1">暂无零钱代金券</p>';
                     html += '<p class="p2">如果有零钱代金券，您将在这里看到</p>';
                     break;
            	 default:
            		 html += '<p class="p1">暂无代金券</p>';
	                 html += '<p class="p2">如果有代金券，您将在这里看到</p>';
	                 break;
            	}
            	
            }
            else{
            		if(obj.investmentAmount){		
            		html += '<p class="p1">暂无推荐代金券</p>';
                    html += '<p class="p2">如果有推荐代金券，您将在这里看到</p>';
            		}
            		else{
               		 html += '<p class="p1">暂无代金券</p>';
	                 html += '<p class="p2">如果有代金券，您将在这里看到</p>';
            		}
            }
            html += '</div>';
        }else{
            $.each(data.list, function(i, jsonObj) {
                if(jsonObj.vouchersRule.product_type == 109){
                    if(jsonObj.scatteredList == null || jsonObj.scatteredList == '' || jsonObj.scatteredList[0].finishedRatio == 100){//活期售罄或下架
                        html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"errorMessage('该代金券没有可适用的产品')\">";   
                    }else{
                        if(fromUrl == "" || fromUrl == "null" || fromUrl == null || fromUrl == 0){
                            html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"goDemandOrderConfirmation('"+ jsonObj.scatteredList[0].bidId+"','"+ tagId +"','"+ jsonObj.scatteredList[0].investMinAmount +"','"+ jsonObj.scatteredList[0].id+"','"+ jsonObj.voucherId +"','"+ jsonObj.voucherAmount +"','"+ jsonObj.investmentAmount +"','"+adjustRate+"','"+inputAmountBac+"')\">";
                        }else{
                            html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"goOrderConfirm('"+ jsonObj.scatteredList[0].bidId+"','"+ tagId +"','"+ jsonObj.scatteredList[0].investMinAmount +"','"+ jsonObj.scatteredList[0].id+"','"+ jsonObj.voucherId +"','"+ jsonObj.voucherAmount +"','"+ jsonObj.investmentAmount +"','"+adjustRate+"','"+inputAmountBac+"','"+finishedRatio+"')\">";
                        }       
                    }
                }else{
                    if(from == "productDetails"){
                         html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"useMyVouchers('"+jsonObj.voucherId+"','"+jsonObj.voucherAmount+"','"+jsonObj.investmentAmount+"')\">";
                    }else{
                        if(jsonObj.scatteredList == null || jsonObj.scatteredList == ''){//定期没有适用标的
                            html += "<li class='border whiteBkg PLR5 PTB5 MT10' onclick=\"errorMessage('该代金券没有可适用的产品')\">";
                        }else{
                            html += "<li  class='border whiteBkg PLR5 PTB5 MT10' onclick=\"setLoanByVochers('"+encodeURIComponent(JSON.stringify(jsonObj.scatteredList))+"','"+jsonObj.voucherId+"','"+jsonObj.voucherAmount+"','"+jsonObj.investmentAmount+"')\">";
                        }
                    }
                }
                var str=jsonObj.scatteredLoanDesc;
                str=str.substring(0,13)+'<br/>'+str.substring(13);
                html += '<div class="ticketConcent"><div class="whiteBkg border ticketConcent01">';
                html += '<table  class="width90 MLRA"><tr>';
                html += '<td class="width60 grayTex font12"><p class="lineHeight1_5x darkGrayTex">'+str+'</p>';
                html += '<p class="lineHeight1_5x MT5" >'+jsonObj.validityPeriodDesc+'</p></td>';
                html += '<td class="width40 redTex">';
                html += '<p class="textC"><span>￥</span><span class="font40 helveticaneue">'+jsonObj.voucherAmount+'</span></p>';
                html += '<p class="font12 textC">'+jsonObj.voucherDesc+'</p>';
                html += '</td></tr></table>';
                html += '</div></div>';
                html += '</li>';
            }); 
        }
        $("#voucherList").append(html);
        $("#voucherListPaging").html(pagingMobile(data, "setMyVouchersList"));
    }else{
        errorMessage(data.resmsg_cn);
    }
}
//代金券分页
var setMyVouchersList = function(page) {
	var voucherListPaging=$("#voucherListPaging").attr("value")||1;//排序
	if(typeof(voucherListPaging)=="string"){
		if(sloanId == 'null' || sloanId == '' || sloanId == null){
	    	//所有代金券列表接口
	    		ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&productType="+obj.productType+"&current=" + page + "&pageSize=5&sortType="+obj.val+"&orderBy="+obj.orderBy, setMyVouchers,"GET");	    	     
	    }else{
	       //根据标的拉取代金券列表接口
	    	  if(obj.investmentAmount===true){
			    	ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+ sloanId+"&current=" + page + "&pageSize=5&sortType="+obj.val+"&investmentAmount="+getInvestmentAmount+"&remanPeriods="+remanPeriods+"&orderBy="+obj.orderBy, setMyVouchers,"GET"); 
			    }
			    else{
			    	ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+ sloanId+"&current=" + page + "&pageSize=5&sortType="+obj.val+"&remanPeriods="+remanPeriods+"&orderBy="+obj.orderBy, setMyVouchers,"GET"); 
			    }
	    }
	}
	else{
		if(sloanId == 'null' || sloanId == '' || sloanId == null){//所有代金券列表接口
	    		ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&productType="+obj.productType+"&current=" + page + "&pageSize=5&sortType="+obj.val+"&orderBy="+obj.orderBy, setMyVouchers,"GET");	    	       
	    }else{
	       //根据标的拉取代金券列表接口        
		    if(obj.investmentAmount===true){
		    	ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+ sloanId+"&current=" + page + "&pageSize=5&sortType="+obj.val+"&investmentAmount="+getInvestmentAmount+"&remanPeriods="+remanPeriods+"&orderBy="+obj.orderBy, setMyVouchers,"GET"); 
		    }
		    else{
		    	ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+ sloanId+"&current=" + page + "&pageSize=5&sortType="+obj.val+"&remanPeriods="+remanPeriods+"&orderBy="+obj.orderBy, setMyVouchers,"GET"); 
		    }
	    }
	}   
};
//是否使用代金券
var useMyVouchers = function(voucherId,voucherAmount,investmentAmount){
    alertBox1("是否使用代金券","goProductDetails('"+ loanId + "','"+ planId +"','"+ sloanId +"','','','','','"+rateRises+"','"+rateIds+"','','"+finishedRatio+"')","不使用","goProductDetails('"+ loanId + "','"+ planId +"','"+ sloanId +"','','','" + voucherId +"','"+voucherAmount+"','"+rateRises+"','"+rateIds+"','"+investmentAmount+"','"+finishedRatio+"')","使用",1)
}
//活期标的信息接口
var sloanId;
var loanId = $("#loanId").val();
var planId = $("#planId").val();
var rateIds = $("#rateIds").val();//代金券id
var from = $("#from").val();//是否从投资详情页过来
var sign=0;//左右table
var remanPeriods=getUrlParam("remanPeriods")||"";
var getInvestmentAmount=getUrlParam("inquire")||$("#inputAmount").val();//定活期输入金额
if(typeof(getInvestmentAmount)==null||getInvestmentAmount==null||getInvestmentAmount=="null"||getInvestmentAmount=="") {
	getInvestmentAmount=0;
}
$(document).ready(function() {
	FastClick.attach(document.body);
    //关闭标的弹窗
    $(".closedBtn").click(function(event) {
        $(".investAlertBg").hide();
    });
    //代金券获取接口
    sloanId = $("#sloanId").val();//
    if(sloanId == 'null' || sloanId == '' || sloanId == null){
    	$("#current").removeClass("none");
        //所有代金券列表接口
        ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&productType=&current=1&pageSize=5&sortType=1&orderBy=asc",setMyVouchers,"GET");
    }else{   	
       //根据标的拉取代金券列表接口
    	$("#classify").html("推荐").addClass("redTex").find("span").removeClass("downArrow");//清除图片
    	$("#sortTypeLi").removeClass("redTex triangleUpRed");
        ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+sloanId+"&current=1&pageSize=5&remanPeriods="+remanPeriods+"&investmentAmount="+getInvestmentAmount,setMyVouchers,"GET"); 
    }
});
//排序
obj.orderBy="asc";
$("#sortType").on("click","li",function(){//排序	
	obj.investmentAmount=false;
	obj.val=$(this).attr("value");
	$(this).addClass("redTex").siblings().removeClass("redTex");
	var key=$(this).hasClass("triangleUpRed");
	if(!key){
		triangleUp($(this));		
	}
	else{
		triangleDown($(this));
	}
	$("#voucherList").empty();
		if(sloanId == 'null' || sloanId == '' || sloanId == null){
			removeAlert();
			ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&productType="+obj.productType+"&current=1&pageSize=5&sortType="+obj.val+"&orderBy="+obj.orderBy, setMyVouchers,"GET");
	    }else{
	    	$("#classify").removeClass("redTex");
	    	ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+sloanId+"&current=1&pageSize=5&remanPeriods="+remanPeriods+"&sortType="+obj.val+"&orderBy="+obj.orderBy,setMyVouchers,"GET"); 
	    }
})
$("#supernatant").on("click","div",function(){//定活期选择
	$("#voucherList").empty();
	var value=$(this).html();
	$("#classify").find("span").html(value);
	
	$(this).addClass("sortTypeCur redTex").siblings().removeClass("sortTypeCur redTex");
	obj.productType=$(this).attr("value");
		if(sloanId == 'null' || sloanId == '' || sloanId == null){
			ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&productType="+obj.productType+"&current=1&pageSize=5&sortType="+obj.val+"&orderBy="+obj.orderBy, setMyVouchers,"GET");
	    }else{
	    	ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+sloanId+"&current=1&pageSize=5&remanPeriods="+remanPeriods+"&sortType="+obj.val+"&investmentAmount="+getInvestmentAmount,setMyVouchers,"GET"); 
	    }
})
obj.classify=true;//全部开关
obj.investmentAmount=true;//金额开关
$("#classify").on("click",function() {	//全部按钮
	
	if(sloanId == 'null' || sloanId == '' || sloanId == null){
		if(obj.classify){
			addAlert();	
		}
		else{
			removeAlert();
		}
	}
	else{//投资入口
        $("#voucherList").empty();
		obj.investmentAmount=true;
		$("#classify").addClass("redTex");//清空红色
		$("#sortType li").removeClass("redTex triangleDownRed triangleUpRed");//清空三角
		 ajaxRequest(contextPath + "wxvouchers/myVouchers", "vsFlag=1&loanId="+sloanId+"&current=1&pageSize=5&investmentAmount="+getInvestmentAmount+"&remanPeriods="+remanPeriods,setMyVouchers,"GET"); 
	}
});
$("#boxF,#supernatant").on("click",function() {
	removeAlert()
});
var jinzhi=0;
$("#boxF,#supernatant").on("touchstart",function(e){
	jinzhi=1;
});
$("#boxF,#supernatant").on("touchmove",function(e){
	if(jinzhi==1){
		e.preventDefault();
		e.stopPropagation();
		}
});
$("#boxF,#supernatant").on("touchend",function(e){
	 jinzhi=0
});
function removeAlert(){//取消弹框默认
	$("#supernatant").addClass("sortType").removeClass("sortTypeAdd");
	$("#classify").find("span").removeClass("upArrow").addClass("downArrow");
	$("#boxF").addClass("none");
	obj.classify=true;
}
function addAlert(){//添加弹框默认
	$("#supernatant").addClass("sortTypeAdd").removeClass("sortType");
	$("#classify").find("span").removeClass("downArrow").addClass("upArrow");
	$("#boxF").removeClass("none");
	obj.classify=false;
}
function triangleUp(key){//三角形向上红
	key.addClass("triangleUpRed").removeClass("triangleDownRed ")
	.siblings().removeClass("triangleDownRed triangleUpRed").addClass("triangleUp triangleDown");
	obj.orderBy="asc";//升降序控制
}
function triangleDown(key){//三角形向下红
	key.addClass("triangleDownRed ").removeClass("triangleUpRed")
	.siblings().removeClass("triangleDownRed triangleUpRed").addClass("triangleUp triangleDown");
	obj.orderBy="desc";
}