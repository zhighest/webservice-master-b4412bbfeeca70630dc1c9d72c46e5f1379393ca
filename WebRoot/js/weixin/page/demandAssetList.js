var goProductDetails = function(orderId, investAmount, sloanId) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxtrade/goInvestDetail";
    temp.method = "POST";

    var inp = document.createElement("input");
    inp.name = "orderId";
    inp.value = orderId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "investAmount";
    inp2.value = investAmount;
    temp.appendChild(inp2);

    var inp3 = document.createElement("input");
    inp3.name = "sloanId";
    inp3.value = sloanId;
    temp.appendChild(inp3);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var getShowAssets = function() { //账户余额调用
    ajaxRequest(contextPath + "wxtrade/showAssets", "", setShowAssets);
}
var getOrderProcessedList = function(page, clear) { //处理中订单调用
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    }
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=5&type=1", setPurchaseDetailProcessedList);
}
var getOrderEarningsList = function(page, clear) {
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    }
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=5&type=2", setPurchaseDetailEarningsList);
}
var getOrderFinishList = function(page, clear) {
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    }
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=5&type=3", setPurchaseDetailFinishList);
}
var getFundFlowList = function(page) { //处理中订单调用
    ajaxRequest(contextPath + "wxtrade/getFundFlowList", "current=" + page + "&pageSize=10", setFundFlowList);
}
var cashInMoney;
var setShowAssets = function(data) { //账户余额生成HTML
    if(data.incomeAmount <= 0){
	    var html = "";
	    var totalAssets = numFormat(data.totalAssets);
	    for (i = 0; i <= totalAssets.length - 1; i++) {
	        if (totalAssets.substr(i, 1) != "." && totalAssets.substr(i, 1) != ",") {
	            html += '<li class="whiteBlock"><font>' + totalAssets.substr(i, 1) + '</font></li>';
	        } else {
	            html += '<li class="mark"><font>' + totalAssets.substr(i, 1) + '</font></li>';
	        }
	    }
	    $("#incomeAmount").html(html); //累计收益
	    $("#totalAssets").html(numFormat(data.incomeAmount)); //账户总额
	    $("#earningsTitle").html("账户总额");
	    $("#amountTitle").html("累计收益");
    }else{
	    var html = "";
		var incomeAmount = numFormat(data.incomeAmount);
	    for (i = 0; i <= incomeAmount.length - 1; i++) {
	        if (incomeAmount.substr(i, 1) != "." && incomeAmount.substr(i, 1) != ",") {
	            html += '<li class="whiteBlock"><font>' + incomeAmount.substr(i, 1) + '</font></li>';
	        } else {
	            html += '<li class="mark"><font>' + incomeAmount.substr(i, 1) + '</font></li>';
	        }
	    }
	    $("#incomeAmount").html(html); //累计收益
	    $("#totalAssets").html(numFormat(data.totalAssets)); //账户总额
		$("#earningsTitle").html("累计收益");
		$("#amountTitle").html("账户总额");
    }    
    $("#balanceMoney").html(numFormat(data.balanceMoney)); //可用余额
    cashInMoney = numFormat(data.cashInMoney);
    if(data.cashInMoney >0){
	    $("#hintIcon").show();
    }
    
}
//列表出现效果
var showItem = function(object) {
    for (i = 0; i <= $(object).length - 1; i++) {
        $(object + ".animate").eq(i).animate({
            marginLeft: "5%"
        },
        (i + 1) * 200,
        function() {
            $(this).removeClass("animate");
        });
    }
}
var setPurchaseDetailProcessedList = function(data) { //处理中订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无处理中订单</p>';
        html += '<p class="p2">如果有处理中订单，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += "<div class=\"item textL width90 ML5P animate\" onclick=\"goProductDetails('" + jsonObj.orderId + "','" + jsonObj.investAmount + "','" + jsonObj.sloanId + "')\">";
            html += '<h4 class="MT10 blackTex1 font14">' + jsonObj.productName + '</h4>';
            html += '<div class="itemCon">';
            html += '<span class="itemLeftArrow"><font></font></span>';
            html += '<span class="itemRightArrow"><font></font></span>';
            html += '<div class="clearfix">';
            html += '<div class="progress largeProgress">';
            html += '<img src="../pic/weixin/progressbar.png" alt="" height="70">';
            html += '<div class="positionA detailTex">';
            html += '<font class="redTex block font14 MB10">年化</font>';
            html += '<span class="font18 redTex"><font class="font30">' + jsonObj.annualRate + '</font>%</span>';
            html += '</div>';
            html += '</div>';
            html += '<div class="fl grayTex MT10">';
            html += '<div class="borderB itemRow">';
            html += '<h4 class="font16">投资金额</h4>';
            html += '<span class="font28">' + numFormat(jsonObj.investAmount) + '</span>';
            html += '</div>';
            html += '<div class="borderB itemRow MT5 PB5">';
            html += '<span>' + jsonObj.endTime + '到期</span>';
            html += '</div>';
            html += '</div>';
            html += '<div class="rightState fr width20P borderLDotted PL10 PR5 PT15 grayTex">';
            html += '<font class="width20P lineHeight1_5x">处理中</font>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
        });
    }
    $("#purchaseDetailList").append(html);
    //showItem(".item");
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderProcessedList"));
};
var setPurchaseDetailEarningsList = function(data) { //回款中订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无回款中订单</p>';
        html += '<p class="p2">如果有回款中订单，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += "<div class=\"item textL width90 ML5P animate\" onclick=\"goProductDetails('" + jsonObj.orderId + "','" + jsonObj.investAmount + "','" + jsonObj.sloanId + "')\">";
            html += '<h4 class="MT10 blackTex1 font14">' + jsonObj.productName + '</h4>';
            html += '<div class="itemCon">';
            html += '<span class="itemLeftArrow"><font></font></span>';
            html += '<span class="itemRightArrow"><font></font></span>';
            html += '<div class="clearfix">';
            html += '<div class="progress largeProgress">';
            html += '<img src="../pic/weixin/progressbar.png" alt="" height="70">';
            html += '<div class="positionA detailTex">';
            html += '<font class="redTex block font14 MB10">年化</font>';
            html += '<span class="font18 redTex"><font class="font30">' + jsonObj.annualRate + '</font>%</span>';
            html += '</div>';
            html += '</div>';
            html += '<div class="fl grayTex MT10">';
            html += '<div class="borderB itemRow">';
            html += '<h4 class="font16">投资金额</h4>';
            html += '<span class="font28">' + numFormat(jsonObj.investAmount) + '</span>';
            html += '</div>';
            html += '<div class="borderB itemRow MT5 PB5">';
            html += '<span>' + jsonObj.endTime + '到期</span>';
            html += '</div>';
            html += '</div>';
            html += '<div class="rightState fr width20P borderLDotted PL10 PR5 PT15 grayTex">';
            html += '<font class="width20P lineHeight1_5x">回款中</font>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
        });
    }
    $("#purchaseDetailList").append(html);
    //showItem(".item");
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderEarningsList"));
};
var setPurchaseDetailFinishList = function(data) { //已完成订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无已完成订单</p>';
        html += '<p class="p2">如果有已完成订单，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            html += "<div class=\"item textL width90 ML5P animate\" onclick=\"goProductDetails('" + jsonObj.orderId + "','" + jsonObj.investAmount + "','" + jsonObj.sloanId + "')\">";
            html += '<h4 class="MT10 blackTex1 font14">' + jsonObj.productName + '</h4>';
            html += '<div class="itemCon">';
            html += '<span class="itemLeftArrow"><font></font></span>';
            html += '<span class="itemRightArrow"><font></font></span>';
            html += '<div class="clearfix">';
            html += '<div class="progress largeProgress">';
            html += '<img src="../pic/weixin/progressbar.png" alt="" height="70">';
            html += '<div class="positionA detailTex">';
            html += '<font class="redTex block font14 MB10">年化</font>';
            html += '<span class="font18 redTex"><font class="font30">' + jsonObj.annualRate + '</font>%</span>';
            html += '</div>';
            html += '</div>';
            html += '<div class="fl grayTex MT10">';
            html += '<div class="borderB itemRow">';
            html += '<h4 class="font16">投资金额</h4>';
            html += '<span class="font28">' + numFormat(jsonObj.investAmount) + '</span>';
            html += '</div>';
            html += '<div class="borderB itemRow MT5 PB5">';
            html += '<span>' + jsonObj.endTime + '到期</span>';
            html += '</div>';
            html += '</div>';
            html += '<div class="rightState fr width20P borderLDotted PL10 PR5 PT15 grayTex">';
            html += '<font class="width20P lineHeight1_5x">已完成</font>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
        });
    }
    $("#purchaseDetailList").append(html);
    //showItem(".item");
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderFinishList"));
};
var setFundFlowList = function(data) { //处理中订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无账户明细</p>';
        html += '<p class="p2">如果有账户明细，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {

            html += '<li class="clearfix">';
            html += '<div class="fl">';
            html += '<h4 class="font20">' + jsonObj.fundType + '</h4>';
            html += '<div class="grayTex MT10">' + jsonObj.transDate + '</div>';
            html += '</div>';
            html += '<div class="fr font20 MT10">' + numFormat(jsonObj.amount) + '</div>';
            html += '</li>';

        });
    }
    $("#fundFlowList").append(html);
    $("#fundFlowListPaging").html(pagingMobile(data, "getFundFlowList"));
};
$("#showFundFlowList").click(function() {
    $("#detailsPopupDiv").show().animate({
        marginLeft: '0px'
    },
    "fast");
    $("#purchaseDetailList").hide();
    $("#btmBar").animate({
        bottom: "-48px"
    },
    100)
});
$("#escBtn").click(function() {
    $("#detailsPopupDiv").animate({
        marginLeft: '100%'
    },
    "fast",
    function() {
        $(this).hide();
    });
    $("#purchaseDetailList").show();
    $("#btmBar").animate({
        bottom: "0px"
    },
    100)
});
$("#hintTitle").click(function(){
	errorMessage("在途金额："+cashInMoney)
})
$(document).ready(function() {
    getShowAssets();
    getOrderEarningsList(1);
    getFundFlowList(1);
});