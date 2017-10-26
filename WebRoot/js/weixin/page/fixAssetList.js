var goProductDetails = function(orderId, investAmount, sloanId, couponsRateRises) {
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
    
    var inp4 = document.createElement("input");
    inp4.name = "couponsRateRises";
    inp4.value = couponsRateRises;
    temp.appendChild(inp4);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
var getShowAssets = function() { //账户余额调用
    ajaxRequest(contextPath + "wxtrade/showAssets", "", setShowAssets);
}
var val=3;
var nowType=2;//类型:当前 历史 全部
var orderBy="desc";//排序方式  默认升序
// 参数：   当前页数   是否清空   排序方式
var getOrderEarningsList = function(page, clear) {
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    } 
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=5&type="+nowType+"&sortType="+(val||3)+"&orderBy="+orderBy, setPurchaseDetailEarningsList);
}
var getOrderFinishList = function(page, clear) {
    if (clear) {
        $("#purchaseDetailList").html("");
        $("#purchaseDetailListPaging").html("");
    }
    ajaxRequest(contextPath + "wxtrade/getPurchaseDetailList", "current=" + page + "&pageSize=5&type=3&sortType="+(val||3)+"&orderBy="+orderBy, setPurchaseDetailFinishList);
}
var setPurchaseDetailEarningsList = function(data) { //当前订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
    	if(nowType==2){
    		html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无当前订单</p>';
	        html += '<p class="p2">如果有当前订单，您会在这里看到</p>';
	        html += '</div>';
    	}else if(nowType==""){
    		html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无订单</p>';
	        html += '<p class="p2">如果有订单，您会在这里看到</p>';
	        html += '</div>';
    	}
        
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            var ym = getDate(jsonObj.endTime).Format("yyyy/MM/dd");
            html += '<li class="MT15 font14 whiteBkg clearfix">';
            html += '<div class="clearfix PTB10 PLR5P borderDashed">';
            html += '<h4 class="fl grayTex">' + jsonObj.productName + '</h4>';
            if(jsonObj.orderStatus == 5){
                html += '<div class="fr redTex">转让中</div>';
            }else if(jsonObj.orderStatus == 4 || jsonObj.orderStatus == 6 || jsonObj.orderStatus == null){
                html += '<div class="fr redTex">回款中</div>';
            }else if(jsonObj.orderStatus  == 1){
                html += '<div class="fr redTex">已转让</div>';
            }else if(jsonObj.orderStatus == 2){
                html += '<div class="fr redTex">已完成</div>';
            }else if(jsonObj.orderStatus == 3){
                html += '<div class="fr redTex">已取消</div>';
            };
            html += '</div>';
            html += '<div class="clearfix MT10 PLR5P grayTex1">';
            html += '<div class="fl">预计年化收益率</div>';
            html += '<div class="fr">投资金额(元)</div>';
            html += '</div>';
            html += '<div class="clearfix MT5 redTex PB10 PLR5P borderDashed">';
            html += '<div class="fl font50 perTop03"><span class="lineHeight100 helveticaneue">' + jsonObj.annualRate + '</span><font class="font24 helveticaneue">%</font></div>';
            html += '<div class="fl ML25 heigh54 font12 positionR">';
            if(jsonObj.adjustRate != 0){
                html += '<div class="positionA width65P addRate1 redBorder radius3">专享+'+ jsonObj.adjustRate +'%</div>';//会员专享图标 
            }
            if(jsonObj.couponsRateRises > 0){
                html += '<div class="positionA width65P addRate2 redBorder radius3">加息+'+ jsonObj.couponsRateRises +'%</div>';
            }
            html += '</div>';
            html += '<div class="fr investMoney font24 heigh54 lineHeight54 redTex helveticaneue strong">'+ numFormat(jsonObj.investAmount) +'</div>';
            html += '</div>';
            html += '<div class="clearfix grayTex PTB10 PLR5P">';
            html += '<div class="fl PTB5">到期日期 '+ ym +'</div>';
            html += '<div class="fr grayBorder PTB5 PLR5 radius3" onclick=\"goProductDetails(' + jsonObj.orderId + ',' + jsonObj.investAmount + ',' + jsonObj.sloanId + ',' + jsonObj.couponsRateRises + ')\">查看订单详情</div>';
        });
    }
    $("#purchaseDetailList").append(html);
    //showItem(".item");
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderEarningsList"));
};
var setPurchaseDetailFinishList = function(data) { //历史订单生成HTML
    var html = ""; //拼接html
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无历史订单</p>';
        html += '<p class="p2">如果有历史订单，您会在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,
        function(i, jsonObj) {
            var ym = getDate(jsonObj.endTime).Format("yyyy/MM/dd");
            html += '<li class="MT15 font14 whiteBkg clearfix">';
            html += '<div class="clearfix PTB10 PLR5P borderDashed">';
            html += '<h4 class="fl grayTex">' + jsonObj.productName + '</h4>';
            if(jsonObj.orderStatus  == 1){
                html += '<div class="fr redTex">已转让</div>';
            }else if(jsonObj.orderStatus == 2){
                html += '<div class="fr redTex">已完成</div>';
            }else if(jsonObj.orderStatus == 3){
                html += '<div class="fr redTex">已取消</div>';
            };
            html += '</div>';
            html += '<div class="clearfix MT10 PLR5P grayTex1">';
            html += '<div class="fl">预计年化收益率</div>';
            html += '<div class="fr">投资金额(元)</div>';
            html += '</div>';
            html += '<div class="clearfix MT5 redTex PB10 PLR5P borderDashed">';
            html += '<div class="fl font50 perTop03"><span class="lineHeight100 helveticaneue">' + jsonObj.annualRate + '</span><font class="font24 helveticaneue">%</font></div>';
            html += '<div class="fl ML25 heigh54 font12 positionR">';
            if(jsonObj.adjustRate != 0){
                html += '<div class="positionA width65P addRate1 redBorder radius3">专享+'+ jsonObj.adjustRate +'%</div>';//会员专享图标 
            }
            if(jsonObj.couponsRateRises > 0){
                html += '<div class="positionA width65P addRate2 redBorder radius3">加息+'+ jsonObj.couponsRateRises +'%</div>';
            }
            html += '</div>';
            html += '<div class="fr investMoney font24 heigh54 lineHeight54 redTex helveticaneue strong">'+ numFormat(jsonObj.investAmount) +'</div>';
            html += '</div>';
            html += '<div class="clearfix grayTex PTB10 PLR5P">';
            html += '<div class="fl PTB5">到期日期 '+ ym +'</div>';
            html += '<div class="fr grayBorder PTB5 PLR5 radius3" onclick=\"goProductDetails(' + jsonObj.orderId + ',' + jsonObj.investAmount + ',' + jsonObj.sloanId + ',' + jsonObj.couponsRateRises + ')\">查看订单详情</div>';
        });
    }
    $("#purchaseDetailList").append(html);
    //showItem(".item");
    $("#purchaseDetailListPaging").html(pagingMobile(data, "getOrderFinishList"));
};
$(document).ready(function() { 
   if(getUrlParam("skipAssetList") == "skipAssetList") {
    	getOrderFinishList(1,true,orderBy);
    	change("#complete","#recommend","#current",1);
    }
   else{
	   removeAlert();
	   getOrderEarningsList(1,true,orderBy);
   }
   $("#sortType").addClass('none');
   setTimeout(function(){
    	$("#sortType").removeClass('none');
   },100);
});
//排序
$("#selectList").on("click",".selectItem",function(){
	//上下箭头样式操作
	$(this).addClass("redTex").siblings().removeClass("redTex");
	if($(this).find('.triangle-up').hasClass('active')){
		$(this).find('.triangle-up').removeClass('active')
		$(this).find('.triangle-bottom').addClass('active')
		$(this).siblings().find('.triangle-up,.triangle-bottom').removeClass('active')
		orderBy="desc";//降序
	}else{
		$(this).find('.triangle-up').addClass('active')
		$(this).find('.triangle-bottom').removeClass('active')
		$(this).siblings().find('.triangle-up,.triangle-bottom').removeClass('active')
		orderBy="asc";//升序
	}
	
	val=$(this).attr("value");
	$("#purchaseDetailListPaging").attr("value",val);
	$("#purchaseDetailList").empty();
	//当前订单
	if(nowType==2){
		getOrderEarningsList(1,true,orderBy);
	}
	//已完成订单
	else if(nowType==3){
		getOrderFinishList(1,true,orderBy);
	} 
	//全部
	else if(nowType==""){
		getOrderEarningsList(1,true,orderBy);
	} 
	setTimeout(function(){
		removeAlert();
	},80)
})	
//选择订单类型  当前订单  已完成订单 全部	
$("#sortType").on("click",".sortTypeItem",function(){
	$(this).addClass("redTex").siblings().removeClass("redTex");
	$(this).find('.confirmIocn').removeClass("none")//选择的出现对勾
	$(this).siblings().find('.confirmIocn').addClass("none");
	$("#purchaseDetailList").empty();
	$("#rank span").text($(this).text());
	change("#complete","#recommend","#current",1)
	if($(this).attr('id')=="recommend"){
		nowType=2;
		getOrderEarningsList(1,true,orderBy);//当前订单
	}else if($(this).attr('id')=="complete"){
		nowType=3;
		getOrderFinishList(1,true,orderBy);//已完成订单
	}else if($(this).attr('id')=="all"){
		nowType="";
		getOrderEarningsList(1,true,orderBy);//所有订单
	}
	setTimeout(function(){
		removeAlert();
	},80)
})
var index=0;//阻止重复点击开关
var sign=0;//左右table
//全部删选按钮
$("#rank").on("click",function(){
	if(index==0){
		$("#sortType").removeClass("none sortType").addClass('sortTypeEnter');//隐藏筛选条件向上动画
		$("#boxF").removeClass("none"); //隐藏蒙版
		index=1;
		$("#rank .redTex").removeClass("downArrow").addClass("upArrow");
	}
	else{
		removeAlert();
	}
})
$("#boxF,#sortType").on("click",function() {
	removeAlert();
});
var jinzhi=0;//阻止重复点击
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
	$("#sortType").addClass("sortType");
	$("#sortType").removeClass("sortTypeEnter");
	$("#boxF").addClass("none");
	$("#rank .redTex").removeClass("upArrow").addClass("downArrow");
	index=0;
}
//分页函数
function change(a,b,c,d){//tab
	$("#purchaseDetailListPaging").attr("value","1");
	sign=d;
}