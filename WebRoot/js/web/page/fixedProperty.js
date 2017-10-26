
var goCheckOut = function(orderId){
    ajaxRequest(contextPath + "asset/getAgreementDetail", "orderId=" + orderId, setAgreementDetail);
}
var setAgreementDetail = function(data){
    if(data.rescode=="00000"){
        if(data.pdfFilePath==""||data.pdfFilePath=="null"||data.pdfFilePath==null){
            if(data.htmlFilePath==""||data.htmlFilePath=="null"||data.htmlFilePath==null){
                alertBox("","","合同正在生成中...","请稍后再试","closeIt",2);
            }else{
               window.location=data.htmlFilePath;
            }   
        }else{
            window.location=data.pdfFilePath;
        }
    }else {
        errorMessage("网络异常，请稍后再试");
    }
}
var closeIt = function(){
    $("#alertBox").remove();
}
$("#currentList").click(function() {//点击当前订单
	$("#historyList").removeClass('current');
	$("#currentList").addClass('current');
	getOrderEarningsList(1,true);
});
$("#historyList").click(function() {//点击当前订单
	$("#currentList").removeClass('current');
	$("#historyList").addClass('current');
	getOrderFinishList(1,true);
});
//点击当前订单所做的操作
var getOrderEarningsList = function(page, clear) {
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    }
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=4&type=2", setPurchaseDetailEarningsList);
}
var getOrderFinishList = function(page, clear) {
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    }
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=4&type=3", setPurchaseDetailFinishList);
}
//当前订单生成HTML begin
var setPurchaseDetailEarningsList = function(data) { 
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
    	html += '<p class="textC font14 grayTex61">暂无订单</p>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            var oi = jsonObj.orderId;
            var ym = getDate(jsonObj.endTime).Format("yyyy/MM/dd");
            html += '<li class="font12 width100 clearfix positionR border">';
            //列表信息begin,默认为展示状态
            if(i%2==0){
                html += '<div class="PTB20 grayBkgf5">';
            }else{
                html += '<div class="PTB20">';
            }
            html += '<div class="width100 clearfix positionR textC lineHeight2x">';
            html += '<div class="fl width20">到期日</div>';
            html += '<div class="fl width20">投资金额(元)</div>';
            html += '<div class="fl width20">预计年化收益率</div>';
            html += '<div class="fl width20">订单状态</div>';
            html += '<div class="fl width20">资产详情</div>';      
            html += '</div>';
            html += '<div class="width100 clearfix positionR textC lineHeight2x">';
            html += '<div class="fl width20">'+ym+'</div>';//到期日期
            html += '<div class="fl width20">'+ numFormat(jsonObj.investAmount) +'</div>';//投资金额
            var accAdd1 = accAdd(jsonObj.annualRate,jsonObj.couponsRateRises);
            var accAdd2 = accAdd(accAdd1,jsonObj.adjustRate);
            html += '<div class= "fl width20 positionR cleafix"><lable>'+accAdd2+'</lable>%';
            if(jsonObj.adjustRate != 0||jsonObj.couponsRateRises > 0){
                html += '<img class="vipHint ML5 cursor" onclick=\"hideOrShow('+ oi +')\" src="'+contextPath+'pic/web/accoutOverview/vipHintIcon.png" height="14">';
                html += '<div class="positionA" style="left:0;top:0">';
                html += '<ul class="vipHintUlF clearfix positionA font12 none" id="vipHintUl'+oi+'">';
                html += '<li class="whiteBkg clearfix grayTex61 radius5 annualizedReturnRateLi">';
                html += '<span class="fl">基本收益</span>';
                html += '<span class="annualizedReturnRate fr">'+jsonObj.annualRate+'%</span></li>';
            }else if(jsonObj.adjustRate == 0 && jsonObj.couponsRateRises == 0){
                html += '<img class="vipHint ML5 none cursor" src="'+contextPath+'pic/web/accoutOverview/vipHintIcon.png" height="14">';
                html += '<div class="positionA" style="left:0;top:0">';
                html += '<ul class="vipHintUlF clearfix positionA font12 none">';
                html += '<li class="whiteBkg clearfix grayTex61 radius5 annualizedReturnRateLi none">';
                html += '<span class="fl">基本收益</span>';
                html += '<span class="annualizedReturnRate fr">'+jsonObj.annualRate+'%</span></li>';
            }
            if(jsonObj.couponsRateRises > 0) {//加息劵
                html += '<li class="radius5 grayTex61 clearfix couponsRateRisesLi">';
                html += '<span class="fl">加息券</span>';
                html += '<span class="couponsRateRises fr">'+jsonObj.couponsRateRises+'%</span></li>';
            }else {
            }
            if(jsonObj.adjustRate > 0){//专享
                html += '<li class="radius5 grayTex61 adjustRateLi">';
                html += '<span class="fl">专享</span>';
                html += '<span class="adjustRate fr">'+jsonObj.adjustRate+'%</span></li>';
            }else{
            }
            html += '</ul></div></div>';//预计年化收益率
            //订单状态
            if(jsonObj.orderStatus == 5){
            	html += '<div class="fl width20">转让中</div>';
            }else if(jsonObj.orderStatus == 4 || jsonObj.orderStatus == 6 || jsonObj.orderStatus == null){
            	html += '<div class="fl width20">回款中</div>';
            }
            html += '<div class="fl width20 cursor none" id="backBtn'+oi+'" onclick=\"goBack('+ oi +')\">收起<img class="ckeckIcon" src="'+contextPath +'pic/web/accoutOverview/checkIconB.png"></div>';
            html += '<div class="fl width20 cursor" id="clickBtn'+oi+'" onclick=\"goProductDetails(' + jsonObj.orderId + ','+ oi +',' + jsonObj.investAmount + ',' + jsonObj.sloanId + ',' + jsonObj.couponsRateRises + ')\">查看详情<img class="ckeckIcon" src="'+contextPath +'pic/web/accoutOverview/checkIcon.png"></div>';
            html += '</div></div>';
            //列表信息end
            //查看详情 begin 默认为收起状态
            ////数据展示begin
            html += '<div class="showList'+oi+' none">';
            html += '<div class="clearfix positionR MT20 grayBkgf5 titleBox">';
            html += '<img src="'+contextPath+'pic/web/accoutOverview/contractIcon.png" class="ML20" width="20px">';
            html += '<span class="redTex ML10">借款合同</span>';
            html += '<span class="redTex MR20 fr cursor" onclick=\"goCheckOut(' + jsonObj.orderId + ')\">查看</span></div>';
            html += '<div class="clearfix positionR PTB20 grayTex61 font14">';
            html += '<ul class="width80 blockC contractCon">';
            html += '<li class="fl width50">';
            html += '<div>投资金额：<span>'+jsonObj.investAmount+'元</span></div>';
            html += '<div><span id="rateTitle'+oi+'">预计年化收益率：</span><span>'+accAdd2+'%</span></div>';
            html += '<div><span id="earingTex'+oi+'">预计收益：</span><span id="expectedReturn'+oi+'"></span>元</div>';
            html += '<div>理财期限：<span id="investPeriod'+oi+'"></span></div>';
            html += '<div>还款方式：<span id="repaymentType'+oi+'"></span></div></li>';
            html += '<li class="fl width50">';
            html += '<div>项目总额：<span id="contactAmount'+oi+'">元</span></div>';
            html += '<div>订单号：<span>'+jsonObj.orderId+'</span></div>';

            html += '<div>订单状态：<span id="disposeState'+oi+'"></span></div>';
            html += '<div>起息日期：<span id="startDate'+oi+'"></span></div>';
            html += '<div><span id="title'+oi+'">到期日期</span>：<span id="titleContent'+oi+'"></span></div>';
            html += '</li></ul></div>';
            //数据展示end
            //项目概况begin
            html += '<div class="grayBkgf5 PTB20 positionR">';
            html += '<div class="wrapper clearfix positionR titleBox">';
            html += '<img src="'+contextPath+'pic/web/accoutOverview/surveyIcon.png" class="ML20" width="20px">';
            html += '<span class="redTex ML10">项目概况</span></div>';
            html += '<div class="wrapper clearfix positionR">';
            html += '<p class="textL width90 blockC font14 lineHeight1_5x grayTex61" id="companyName'+oi+'"></p></div></div>';
            //资金保障 begin
            html += '<div class="PTB20"><div class="wrapper clearfix positionR titleBox">';
            html += '<img src="'+contextPath+'pic/web/accoutOverview/guaranteeIcon.png" class="ML20" width="20px">';
            html += '<span class="redTex ML10">资金保障</span></div>';
            html += '<div class="wrapper clearfix positionR">';
            html += '<p class="textL width90 blockC font14 lineHeight1_5x grayTex61" id="fundSecurity'+oi+'"></p>';
            html += '</div></div></div></li>';
            //查看详情 end
        });    
    }
    $("#purchaseDetailList").append(html);
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderEarningsList"));
};

var hideOrShow = function(oi){
    $("#vipHintUl"+oi).animate({height: 'toggle', opacity: 'toggle'}, "400");  
}
//历史订单生成HTML
var setPurchaseDetailFinishList = function(data) {
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<p class="textC font14 grayTex61">暂无订单</p>';
        
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            var oi = jsonObj.orderId;
            var ym = getDate(jsonObj.endTime).Format("yyyy/MM/dd");
            html += '<li class="font12 width100 clearfix positionR border">';
            //列表信息begin,默认为展示状态
            if(i%2==0){
                html += '<div class="PTB20 grayBkgf5">';
            }else{
                html += '<div class="PTB20">';
            }
            html += '<div class="width100 clearfix positionR textC lineHeight2x">';
            html += '<div class="fl width20">到期日</div>';
            html += '<div class="fl width20">投资金额(元)</div>';
            html += '<div class="fl width20">预计年化收益率</div>';
            html += '<div class="fl width20">订单状态</div>';
            html += '<div class="fl width20">资产详情</div>';      
            html += '</div>';
            html += '<div class="width100 clearfix positionR textC lineHeight2x">';
            html += '<div class="fl width20">'+ym+'</div>';//到期日期
            html += '<div class="fl width20">'+ numFormat(jsonObj.investAmount) +'</div>';//投资金额
            var accAdd1 = accAdd(jsonObj.annualRate,jsonObj.couponsRateRises);
            var accAdd2 = accAdd(accAdd1,jsonObj.adjustRate);
            html += '<div class= "fl width20 positionR cleafix"><lable>'+accAdd2+'</lable>%';
            if(jsonObj.adjustRate != 0||jsonObj.couponsRateRises > 0){
                html += '<img class="vipHint ML5" onclick=\"hideOrShow('+ oi +')\" src="'+contextPath+'pic/web/accoutOverview/vipHintIcon.png" height="14">';
                html += '<div class="positionA" style="left:0;top:0">';
                html += '<ul class="vipHintUlF clearfix positionA font12 none" id="vipHintUl'+oi+'">';
                html += '<li class="whiteBkg clearfix grayTex61 radius5 annualizedReturnRateLi">';
            }else if(jsonObj.adjustRate == 0 && jsonObj.couponsRateRises == 0){
                html += '<img class="vipHint ML5 none" src="'+contextPath+'pic/web/accoutOverview/vipHintIcon.png" height="14">';
                html += '<div class="positionA" style="left:0;top:0">';
                html += '<ul class="vipHintUlF clearfix positionA font12 none">';
                html += '<li class="whiteBkg clearfix grayTex61 radius5 annualizedReturnRateLi none">';
            }
            html += '<span class="fl">基本收益</span>';
            html += '<span class="annualizedReturnRate fr">'+jsonObj.annualRate+'%</span></li>';
            if(jsonObj.couponsRateRises > 0) {//加息劵
                html += '<li class="radius5 grayTex61 clearfix couponsRateRisesLi">';
            }else {
               html += '<li class="radius5 grayTex61 clearfix couponsRateRisesLi none">'; 
            }
            html += '<span class="fl">加息券</span>';
            html += '<span class="couponsRateRises fr">'+jsonObj.couponsRateRises+'%</span></li>';
            if(jsonObj.adjustRate > 0){
                html += '<li class="radius5 grayTex61 adjustRateLi">';
            }else{
                html += '<li class="radius5 grayTex61 adjustRateLi none">';
            }
            html += '<span class="fl">专享</span>';
            html += '<span class="adjustRate fr">'+jsonObj.adjustRate+'%</span></li></ul></div>';
            html += '</div>';//预计年化收益率
            //订单状态
            if(jsonObj.orderStatus  == 1){
                html += '<span class="fl width20">已转让</span>';
            }else if(jsonObj.orderStatus == 2){
                html += '<span class="fl width20">已完成</span>';
            }else if(jsonObj.orderStatus == 3){
                html += '<span class="fl width20">已取消</span>';
            };
            html += '<div class="fl width20 cursor none" id="backBtn'+oi+'" onclick=\"goBack('+ oi +')\">收起<img class="ckeckIcon" src="'+contextPath +'pic/web/accoutOverview/checkIconB.png"></div>';
            html += '<div class="fl width20 cursor" id="clickBtn'+oi+'" onclick=\"goProductDetails(' + jsonObj.orderId + ','+ oi +',' + jsonObj.investAmount + ',' + jsonObj.sloanId + ',' + jsonObj.couponsRateRises + ')\">查看详情<img class="ckeckIcon" src="'+contextPath +'pic/web/accoutOverview/checkIcon.png"></div>';
            html += '</div></div>';
            //列表信息end
            //查看详情 begin 默认为收起状态
            ////数据展示begin
            html += '<div class="showList'+oi+' none">';
            html += '<div class="clearfix positionR MT20 grayBkgf5 titleBox">';
            html += '<img src="'+contextPath+'pic/web/accoutOverview/contractIcon.png" class="ML20" width="20px">';
            html += '<span class="redTex ML10">借款合同</span>';
            html += '<span class="redTex MR20 fr cursor" onclick=\"goCheckOut(' + jsonObj.orderId + ')\">查看</span></div>';
            html += '<div class="clearfix positionR PTB20 grayTex61 font14">';
            html += '<ul class="width80 blockC contractCon">';
            html += '<li class="fl width50">';
            html += '<div>投资金额：<span>'+jsonObj.investAmount+'元</span></div>';
            html += '<div><span id="rateTitle'+oi+'">预计年化收益率：</span><span>'+accAdd2+'%</span></div>';
            html += '<div><span id="earingTex'+oi+'">预计收益：</span><span id="expectedReturn'+oi+'"></span>元</div>';
            html += '<div>理财期限：<span id="investPeriod'+oi+'"></span></div>';
            html += '<div>还款方式：<span id="repaymentType'+oi+'"></span></div></li>';
            html += '<li class="fl width50">';
            html += '<div>项目总额：<span id="contactAmount'+oi+'">元</span></div>';
            html += '<div>订单号：<span>'+jsonObj.orderId+'</span></div>';

            html += '<div>订单状态：<span id="disposeState'+oi+'"></span></div>';
            html += '<div>起息日期：<span id="startDate'+oi+'"></span></div>';
            html += '<div><span id="title'+oi+'">到期日期</span>：<span id="titleContent'+oi+'"></span></div>';
            html += '</li></ul></div>';
            //数据展示end
            //项目概况begin
            html += '<div class="grayBkgf5 PTB20 positionR">';
            html += '<div class="wrapper clearfix positionR titleBox">';
            html += '<img src="'+contextPath+'pic/web/accoutOverview/surveyIcon.png" class="ML20" width="20px">';
            html += '<span class="redTex ML10">项目概况</span></div>';
            html += '<div class="wrapper clearfix positionR">';
            html += '<p class="textL width90 blockC font14 lineHeight1_5x grayTex61" id="companyName'+oi+'"></p></div></div>';
            //资金保障 begin
            html += '<div class="PTB20"><div class="wrapper clearfix positionR titleBox">';
            html += '<img src="'+contextPath+'pic/web/accoutOverview/guaranteeIcon.png" class="ML20" width="20px">';
            html += '<span class="redTex ML10">资金保障</span></div>';
            html += '<div class="wrapper clearfix positionR">';
            html += '<p class="textL width90 blockC font14 lineHeight1_5x grayTex61" id="fundSecurity'+oi+'"></p>';
            html += '</div></div></div></li>';
            //查看详情 end
        });
    }
    $("#purchaseDetailList").append(html);
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderFinishList"));
};

$(document).ready(function() {
    getOrderEarningsList(1);
});
var j;
var goProductDetails = function(orderId,oi,investAmount, sloanId, couponsRateRises) {
    $("#clickBtn"+oi).hide();
    $("#backBtn"+oi).show();
    $(".showList"+oi).show();
    j=oi;
    ajaxRequest(contextPath + "wxtrade/getBorrowerCompanyInfo", "sloanId=" + sloanId + "&investType=1", setBorrowerCompanyInfo);
    ajaxRequest(contextPath + "wxtrade/getOrderFormDetails", "orderId=" + orderId + "&couponsRateRises=" +couponsRateRises, setOrderFormDetails);
}
var goBack = function(oi){
    $(".showList"+oi).hide();
    $("#clickBtn"+oi).show();
    $("#backBtn"+oi).hide();
}
// 订单详情 begin
var setOrderFormDetails = function(data) {
    if (data.rescode == "00000") {
        var dataInfo = data.list[0];
        $("#startDate"+j).html(getDate(dataInfo.startDate).Format("yyyy/MM/dd")); //起息日期
        // $("#endTime").html(dataInfo.endTime); //到期时间
        $("#expectedReturn"+j).html(numFormat(dataInfo.expectedReturn)); //预期收益
        if (dataInfo.remanDays == 0) {
            $("#investPeriod"+j).html(dataInfo.investPeriod + "个月"); //理财期限
        }else{
            $("#investPeriod"+j).html(dataInfo.remanDays + "天");//短期标理财期限
        }
        $("#repaymentType"+j).html(dataInfo.repaymentType); //还款方式
        $("#contactAmount"+j).html(numFormat(dataInfo.contactAmount)+"元"); //借款总额
        if(dataInfo.orderStatus == 5){
            $("#disposeState"+j).html("转让中");
            $("#title"+j).html("转让申请日期");
            $("#titleContent"+j).html(getDate(dataInfo.assignApplyDate).Format("yyyy/MM/dd"));//转让申请日       
        }else if(dataInfo.orderStatus == 4){
            $("#disposeState"+j).html("回款中");
            $("#title"+j).html("到期日期");
            $("#titleContent"+j).html(getDate(dataInfo.endTime).Format("yyyy/MM/dd"));
        }else if(dataInfo.orderStatus == 6){
            $("#disposeState"+j).html("回款中");
            $("#title"+j).html("到期日期");
            $("#titleContent"+j).html(getDate(dataInfo.endTime).Format("yyyy/MM/dd"));
            $("#title"+j).html("可转让申请日期");
            $("#titleContent"+j).html(getDate(dataInfo.assignDate).Format("yyyy/MM/dd"));
        }else if(dataInfo.orderStatus  == 1){//已转让
            $("#disposeState"+j).html("已转让");
            $("#rateTitle"+j).html("实际年化收益率：");
            $("#title"+j).html("转让申请日期");
            $("#titleContent"+j).html(getDate(dataInfo.assignApplyDate).Format("yyyy/MM/dd"));
            $("#earingTex"+j).html("实际收益：");
            $("#expectedReturn"+j).html(dataInfo.annualInterest);//实际收益
        }else if(dataInfo.orderStatus == 2){
            $("#disposeState"+j).html("已完成");
            $("#rateTitle"+j).html("实际年化收益率：");
            $("#title"+j).html("到期日期");
            $("#titleContent"+j).html(getDate(dataInfo.endTime).Format("yyyy/MM/dd"));
            $("#earingTex"+j).html("实际收益：");
        }else if(dataInfo.orderStatus == 3){
            $("#disposeState"+j).html("已取消");
            $("#title"+j).html("取消日期");
            $("#titleContent"+j).html(getDate(dataInfo.orderCancelDate).Format("yyyy/MM/dd"));
        };
    }
};
var setBorrowerCompanyInfo = function(data) {
    if (data.rescode == "00000") {
        var dataInfo = data.borrowerCompany;
        $("#companyName"+j).html(dataInfo.projectDetails);
        $("#fundSecurity"+j).html(dataInfo.fundSecurityNew);
    }
};