//tab栏切换效果
// $("#tabNav li").eq(0).click(function(event) {
//     $(this).addClass("current").siblings().removeClass("current");
//     $("#tabContent1").stop().show();
//     $("#tabContent2").stop().hide();
// });
// $("#tabNav li").eq(1).click(function(event) {
//     useClick();
// });
var index=0
$(".allType").on("click",function(){
    //判断是 投资理财入口  还是  我的账户入口
    if((sloanId == "" || sloanId == null || sloanId == "null") && (fixDemandRate==""||fixDemandRate==null||fixDemandRate=="null")){ //我的账户入口
        if(index==0){
            $("#sortType").removeClass("sortType").addClass("sortTypeDown");
            $("#boxF").removeClass("none");
            index=1;
            $(this).children("span").removeClass("downArrow").addClass("upArrow");
        }
        else{
            $("#sortType").removeClass("sortTypeDown").addClass("sortType");
            removeAlert()
        }
    }else if(fixDemandRate== "demandRate"){//  每日加息 入口
        $("#RateCouponsList").html("");
        $(".allType").html("零钱").removeClass("downArrow").css("box-shadow","none");
        var eqNum = $(this).index();
        if(clickNum == 1 || clickNum == "1"){
        	orderByFlagTime = "desc";
        }else if(clickNum == 2 || clickNum == "2"){
        	orderByFlagOdd = "desc";
        }
        $(this).addClass("redTex").siblings().removeClass("redTex triangleUpRed triangleDownRed").addClass("triangleDown triangleUp");
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&vsFlag=1&product=0&status=0&current=1&pageSize=5",setMyRateCoupons,"GET");
    }else{   // 投资页面的加息券入口
        $("#RateCouponsList").html("");
        $(".allType").html("推荐").removeClass("downArrow").css("box-shadow","none");
        if(clickNum == 1 || clickNum == "1"){
        	orderByFlagTime = "desc";
        }else if(clickNum == 2 || clickNum == "2"){
        	orderByFlagOdd = "desc";
        }
        $(this).addClass("redTex").siblings().removeClass("redTex triangleUpRed triangleDownRed").addClass("triangleDown triangleUp");
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&vsFlag=1&status=0&current=1&pageSize=5",setMyRateCoupons,"GET");
    }

})
$("#boxF,#sortType").on("click",function() {
    removeAlert()
});
var jinzhi=0;
$("#boxF,#sortType").on("touchstart",function(e){
    jinzhi=1;
});
$("#boxF,#sortType").on("touchmove",function(e){
    if(jinzhi==1){
        e.preventDefault();
        e.stopPropagation();
    }
});
$("#boxF,#sortType").on("touchend",function(e){
    jinzhi=0
});
function removeAlert(){//取消弹框默认
    $("#sortType").removeClass("sortTypeDown").addClass("sortType");
    $("#boxF").addClass("none");
    if(sloanId == "" || sloanId == null || sloanId == "null") { //我的账户入口
        $(".allType span").removeClass("upArrow").addClass("downArrow");
    }
    index=0;
}


var productFlag = "";   //  定 、活 期
var sortTypeFlag = "1"; //排序类型
var orderByFlagTime = "asc";  //升
var orderByFlagOdd = "desc";  //降
var orderByFlagCopy = "asc";  //  存 当前排序
var clickNum = 1;  // 是否切换tab 标记
//获取活期加息券列表
$("#sortType1,#sortType2,#sortType3").click(function(){
    orderByFlag = "asc";
    productFlag = $(this).attr("value");
    $("#RateCouponsList").html("");
    $(this).addClass("sortTypeCur").siblings().removeClass("sortTypeCur");
    $(".allType").html('<span>'+$(this).html()+'</span>');
    ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagCopy+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
});

//有效期排序
$(".validityLi").click(function(){
    removeAlert();
    var eqNum = $(this).index();
    if(clickNum == eqNum){

    }else{
        orderByFlagTime = "desc";
        clickNum = eqNum;
    }
    sortTypeFlag = $(this).attr("value");
    $("#RateCouponsList").html("");
    if(orderByFlagTime == "asc" || orderByFlagTime == ""){
        orderByFlagTime = "desc";
        orderByFlagCopy = orderByFlagTime;
        $(this).removeClass("triangleUpRed triangleDown").addClass("triangleUp triangleDownRed redTex").siblings().addClass("triangleDown triangleUp").removeClass("triangleDownRed triangleUpRed redTex");
        if((sloanId == "" || sloanId == null || sloanId == "null") && (fixDemandRate==""||fixDemandRate==null||fixDemandRate=="null")) { //我的账户入口
            $(".allType").addClass("redTex").removeClass("triangleDown triangleUp");
        }else{
            $(".allType").removeClass("triangleDown triangleUp");
        }
        if(fixDemandRate== "demandRate"){ //  每日加息中入口
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagTime+"&status=0&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }else{
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagTime+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }
    }else if(orderByFlagTime == "desc"){
        orderByFlagTime = "asc";
        orderByFlagCopy = orderByFlagTime;
        $(this).removeClass("triangleDownRed").addClass("triangleUpRed redTex triangleDown").siblings().addClass("triangleDown triangleUp").removeClass("triangleDownRed triangleUpRed redTex");
        if((sloanId == "" || sloanId == null || sloanId == "null") && (fixDemandRate==""||fixDemandRate==null||fixDemandRate=="null")) { //我的账户入口
            $(".allType").addClass("redTex").removeClass("triangleDown triangleUp");
        }else{
            $(".allType").removeClass("triangleDown triangleUp");
        }
        if(fixDemandRate== "demandRate"){ //  每日加息中入口
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagTime+"&status=0&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }else{
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagTime+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }

    }
});

// 加息收益率 排序
$(".rateLi").click(function(){
    removeAlert();
    var eqNum = $(this).index();
    if(clickNum == eqNum){

    }else{
        orderByFlagOdd = "desc";
        clickNum = eqNum;
    }
    sortTypeFlag = $(this).attr("value");
    $("#RateCouponsList").html("");
    if(orderByFlagOdd == "asc" || orderByFlagOdd == ""){
        orderByFlagCopy = orderByFlagOdd;
        $(this).removeClass("triangleDownRed").addClass("triangleUpRed triangleDown redTex").siblings().addClass("triangleDown triangleUp").removeClass("triangleDownRed triangleUpRed redTex");
        if((sloanId == "" || sloanId == null || sloanId == "null") && (fixDemandRate==""||fixDemandRate==null||fixDemandRate=="null")) { //我的账户入口
            $(".allType").addClass("redTex").removeClass("triangleDown triangleUp");
        }else{
            $(".allType").removeClass("triangleDown triangleUp");
        }
        if(fixDemandRate== "demandRate") { //  每日加息中入口
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagOdd+"&status=0&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }else{
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagOdd+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }
        orderByFlagOdd = "desc";
    }else if(orderByFlagOdd == "desc"){
        orderByFlagCopy = orderByFlagOdd;
        $(this).removeClass("triangleUpRed triangleDown").addClass("triangleUp triangleDownRed redTex").siblings().addClass("triangleDown triangleUp").removeClass("triangleDownRed triangleUpRed redTex");
        if((sloanId == "" || sloanId == null || sloanId == "null") && (fixDemandRate==""||fixDemandRate==null||fixDemandRate=="null")) { //我的账户入口
            $(".allType").addClass("redTex").removeClass("triangleDown triangleUp");
        }else{
            $(".allType").removeClass("triangleDown triangleUp");
        }
        if(fixDemandRate== "demandRate"){  //  每日加息中入口
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagOdd+"&status=0&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }else{
            ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagOdd+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        }

        orderByFlagOdd = "asc";
    }
});




//使用加息券列表
// function useClick(){
//     $("#tabNav li").eq(1).addClass("current").siblings().removeClass("current");
//     $("#tabContent1").stop().hide();
//     $("#tabContent2").stop().show();
// }

//调取加息券剩余次数
function setShowCouponsLuckDraw(data){
    if(data.rescode == "00000"){
        $("#times").html(data.restLuckDrawTimes);
    }
}
function setUseRateRisesD(data){
    if(data.rescode == "00000"){
        $(".alertBg").stop().hide();
        errorMessage("加息券使用成功");
        window.location.reload();
        function goUrl(){
            window.location.href = contextPath + "wxproduct/goDemandProductIndex"
        }
        //setTimeout(goUrl,3000);
    }else{
        errorMessage(data.resmsg_cn);
    }
}

var finishedRatio = getUrlParam("finishedRatio");  //通过url获取可投进度
var goProductBuy = function(id,loanId,sloanId,voucherAmount,investmentAmount,rateRises,product,voucherId,finishedRatio) {
    var temp = document.createElement("form");
   var host1=inquire("inquire="); 
   host1!=undefined?temp.action = contextPath + "wxtrade/goOrderConfirmation?inquire="+host1:temp.action = contextPath + "wxproduct/goProductBuy"
    temp.method = "POST";

    var inp = document.createElement("input");
    inp.name = "rateIds";
    inp.value = id;
    temp.appendChild(inp);
    
    var inp2 = document.createElement("input");
    inp2.name = "loanId";
    inp2.value = loanId;
    temp.appendChild(inp2);
    
    var inp3 = document.createElement("input");
    inp3.name = "sloanId";
    inp3.value = sloanId;
    temp.appendChild(inp3);

    var inp4 = document.createElement("input");
    inp4.name = "voucherAmount";
    inp4.value = voucherAmount;
    temp.appendChild(inp4);

    var inp5 = document.createElement("input");
    inp5.name = "investmentAmount";
    inp5.value = investmentAmount;
    temp.appendChild(inp5);

    var inp6 = document.createElement("input");
    inp6.name = "rateRises";
    inp6.value = rateRises;
    temp.appendChild(inp6);

    var inp7 = document.createElement("input");
    inp7.name = "product";
    inp7.value = product;
    temp.appendChild(inp7);

    var inp8 = document.createElement("input");
    inp8.name = "from";
    inp8.value = "LBWX";
    temp.appendChild(inp8);

    var inp9 = document.createElement("input");
    inp9.name = "voucherId";
    inp9.value = voucherId;
    temp.appendChild(inp9);
    
    var inp10 = document.createElement("input");   //可投进度
    inp10.name = "finishedRatio";
    inp10.value = finishedRatio;
    temp.appendChild(inp10);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var voucherAmount = $("#voucherAmount").val();//代金券面额
var investmentAmount = $("#investmentAmount").val();//代金券使用需满足金额
var voucherId = $("#voucherId").val();//代金券ID
function useRateRisesD(id,productId,rateRises){
    $(".alertBg").stop().show();
    $("#cancel").attr("onclick",'$(".alertBg").stop().hide();');
    $("#enterBtn").attr("onclick",'ajaxRequest("'+contextPath+'wxcoupons/useRateRises", "rateIds='+id+'&productId=' + productId+'&from=LBWX&voucherAmount'+voucherAmount+'&rateRises='+rateRises+'&investmentAmount='+investmentAmount+'&voucherId='+voucherId+'",setUseRateRisesD,'+'"GET")');
}
//使用加息券
function useRateRisesF(id,productId,rateRises){
    $(".alertBg").stop().show();
    $("#cancel").attr("onclick",'$(".alertBg").stop().hide();');

	if(loanId != "" && loanId != null || sloanId != "" && sloanId != null) {
		if(product != "" && product != null){
			$("#enterBtn").attr("onclick","goProductBuy('"+id+"','"+loanId+"','"+ sloanId+"','"+ voucherAmount+"','"+ investmentAmount+"','"+ rateRises+"','"+ product+"','"+voucherId+"','"+finishedRatio+"')");
		}else{
			$("#enterBtn").attr("onclick","goProductBuy('"+id+"','"+loanId+"','"+ sloanId+"','"+voucherAmount+"','"+ investmentAmount+"','"+rateRises+"','','"+voucherId+"','"+finishedRatio+"')");
		}
	}else {
		if(product != "" && product != null){
			$("#enterBtn").attr("onclick","goProductBuy('"+id+"','','','"+ voucherAmount+"','"+ investmentAmount+"','"+rateRises+"','"+ product+"','"+voucherId+"','"+finishedRatio+"')");
		}else{
			if(errorMsg == "notFundScattered"){
				$("#enterBtn").attr("onclick",'$(".alertBg").stop().hide();errorMessage("暂时没有可以使用该加息券的产品");');
			}
			else{
				$("#enterBtn").attr("onclick","goProductBuy('"+id+"','"+loanId+"','"+ sloanId+"','"+voucherAmount+"','"+ investmentAmount+"','"+rateRises+"','','"+voucherId+"','"+finishedRatio+"')");
			}
		}
	}

 }

//加息券列表
function setMyRateCoupons(data){
    var html = "";
    if(data.rescode == "00000"){
        if(data.rateCouponsList == "" || data.rateCouponsList == null){
            html += '<div class="listNull">';
            html += '<img src="../pic/weixin/list.png">';
            html += '<p class="p1">暂无加息券</p>';
            html += '<p class="p2">如果有加息券，您将在这里看到</p>';
            html += '</div>';
        }else{
            $.each(data.rateCouponsList, function(i, jsonObj) {
                var ym = getDate(jsonObj.validity_period_str).Format("yyyy/MM/dd");
                if(jsonObj.product_id == "109" && jsonObj.status == 0){//活期，未使用
                    html += '<li class="MT10 huoqi" onclick="useRateRisesD('+jsonObj.id+','+jsonObj.product_id+','+jsonObj.rate_rises+')">';
                }else if(jsonObj.product_id == "108" && jsonObj.status == 0){//定期，未使用
                    html += '<li class="MT10 dingqi" onclick="useRateRisesF('+jsonObj.id+','+jsonObj.product_id+','+jsonObj.rate_rises+')">';
                }else if(jsonObj.status == 1) {//使用过的
                    html += '<li class="MT10 moren positionR"><span class="statusImg1 positionA"></span>';
                }else if(jsonObj.status == 2){  //已过期
                    html += '<li class="MT10 moren positionR"><span class="statusImg2 positionA"></span>';
                };
                html += '<div class=""><div class="whiteBkg">';
                html += '<table class="width100 MLRA" cellpadding="0" cellspacing="0" border="0"><tr>';

                if(jsonObj.product_id == "108" && jsonObj.status == 0){//定期 未过期
                    html += '<td class="whiteTex width40 textC blueBackground">';
                }else if( jsonObj.product_id == "109" && jsonObj.status == 0 ){
                    html += '<td class="whiteTex width40 textC redBackground">';
                } else{//过期
                    html += '<td class="whiteTex width40 textC grayBackground">';
                }
                html += '<div class="perTop02"><span class="font36 helveticaneue"><span class="font16">+</span>'+formatNum(jsonObj.rate_rises)+'<span class="font20">%</span></span></div>';
                if(jsonObj.product_id == "109"){//活期
                    html += '<p class="font12">年化收益率</p>';
                }else{
                    html += '<p class="font12">年化收益率</p>';
                }
                html += '</td>';

                html += '<td class="grayTex PL15 width60 lineHeight1_5x">';
                if(jsonObj.status == 0){//未过期
                    html += '<p class="blackTex font14 strong">';
                }else{//过期
                    html += '<p class="font14 strong">';
                }
                html += jsonObj.product_id_str +'<span class="ML10 get_way font12">'+jsonObj.get_way+'</span></p>';

                if(jsonObj.product_id == "109"){//活期
                    html += '<p class="font12 PT15">加息期限: '+jsonObj.rate_rises_days+'天</p>';
                }else{
                    if(jsonObj.remanDays =="0") {
                        html += '<p class="font12 PT15">产品类型: '+jsonObj.rate_rises_days+'个月</p>';
                    }else{
                        html += '<p class="font12 PT15">产品类型: '+jsonObj.remanDays+'天</p>';
                    }
                }
                html += '<p class="font12">到期日期: '+ym+'</p></td>';

                html += '</tr></table></div></div>';
                html += '</li>'; 
            });
            
        }
    }
    $("#RateCouponsList").append(html);
    $("#proListPaging").html(pagingMobile(data, "setRateCouponsList"));
}

var setRateCouponsList = function(page) {
    if(fixDemandRate== "demandRate") {  //  每日加息中入口
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagCopy+"&status=0&vsFlag=1&current="+page+"&pageSize=5",setMyRateCoupons,"GET");
    }else{
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagCopy+"&vsFlag=1&current="+page+"&pageSize=5",setMyRateCoupons,"GET");
    }

};

var userId;
var product;
var status;
var sloanId;
var loanId; 
var notFundScattered = function(){
	window.location.href = contextPath+ "wxcoupons/goMyInterest"
};
var host="",j=0,t=0;
function inquire(a){
	 host=window.location.href;
	 t=host.indexOf(a);
	 if(t==-1) return
	 j=a.length;
	host=host.substring(t+j);
	if(host=="undefined") return
	return host
}
var fixDemandRate;
$(document).ready(function() {
    productFlag = $("#product").val();
    userId = $("#userId").val();
    sloanId = $("#sloanId").val();
    loanId = $("#loanId").val();
    fixDemandRate = $("#fixDemandRate").val();
    //判断是 投资理财入口  还是  我的账户入口
    if( (sloanId == "" || sloanId == null || sloanId == "null") && (fixDemandRate==""||fixDemandRate==null||fixDemandRate=="null") ){ //我的账户入口

    }else if(fixDemandRate== "demandRate"){  // 每日加息入口
        $(".allType").html("零钱").removeClass("downArrow").css("box-shadow","none");
        $(".validityLi").removeClass("triangleUpRed redTex").addClass("triangleUp");
        orderByFlagTime = "desc";
    }else{  //投资理财入口
        $(".allType").html("推荐").removeClass("downArrow").css("box-shadow","none");
        $(".validityLi").removeClass("triangleUpRed redTex").addClass("triangleUp");
        orderByFlagTime = "desc";
    }
	// useClick();

    ajaxRequest(contextPath + "wxcoupons/showCouponsLuckDraw", "userId="+userId,setShowCouponsLuckDraw,"GET");
    var from = sessionStorage.getItem("from");
    errorMsg = $("#errorMsg").val();
	if(errorMsg == "notFundScattered"){
		errorMessage("暂时没有可以使用该加息券的产品");
// 		sessionStorage.setItem("from","fix");
	}
    if(fixDemandRate== "fixRate") {
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        $(".fixedBtmArea").hide();

    }else if(fixDemandRate== "demandRate") {
        product = 0;
        productFlag = 0;
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&vsFlag=1&product=0&status=0&current=1&pageSize=5",setMyRateCoupons,"GET");
        status = 0;
    }else if(from == 'demand') {
        // $("#tabNav").hide();
        // useClick();
        product = 0;
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product=0&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        /*sessionStorage.setItem("from",""); //销毁 from 防止在b页面刷新 依然触发$('#xxx').click()*/
        status = 0;
    }else if(from == 'fix'){
        // $("#tabNav").hide();
        // useClick();
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&vsFlag=1&status=0&current=1&pageSize=5",setMyRateCoupons,"GET");
        $(".fixedBtmArea").hide();
        /*sessionStorage.setItem("from","");*/
        status = 0;
    }else if(from == 'useTab'){
        // useClick();
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        status = "";
    }else{
        ajaxRequest(contextPath + "wxcoupons/myRateCoupons", "userId="+userId+"&product="+productFlag+"&sortType="+sortTypeFlag+"&orderBy="+orderByFlagTime+"&vsFlag=1&current=1&pageSize=5",setMyRateCoupons,"GET");
        status = "";
    }
});