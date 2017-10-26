function subStr(str){
   return  str=str.substring(0,10)
}
var ymArray = "";
var getSaleManRecordByDateList = function(page,data,mobileNumber) { 
    ajaxRequest(contextPath + "saleManager/querySaleManRecordByDate", "mobileNumber="+mobileNumber+"&current=" + page + "&pageSize=10&saleDate="+data, setSaleManRecordByDateList);
}
var setSaleManRecordByDateList = function(data) { 
var html="";//拼接html
     if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无销售记录</p>';
        html += '<p class="p2">如果有销售记录，您将在这里看到</p>';
        html += '</div>';
    }else{
        $.each(data.list,function(i, jsonObj){
    	var MonthData=subStr(jsonObj.saleDate);//获取MM-DD
        var upcaseProductModel=jsonObj.productModel.toUpperCase();
    	if(MonthData!=ymArray){
    		ymArray=MonthData;
            html+='<li class="PLR5P textL grayTex greyBkg"><span>'+MonthData+'</span></li>'
    	}
    		html+='<li class="PLR5P PTB5 font14 whiteBkg">'
	        html+='<span class="fr time">'+jsonObj.time+'</span>'
	  		html+='<span class="block textL type">'+upcaseProductModel+'</span>'
	  		html+='<div class="textL grayTex count"><span>'+jsonObj.saleCount+'</span>&nbsp;台</div>'
	  		html+="</li>"
      });
    }
    $('#saleList').append(html);
    $("#fundFlowListPaging").html(pagingMobile2(data,"getSaleManRecordByDateList"));
};

    var mobile=$('#mobileNumber').val();//提升为全局变量，供分页函数中调用！重要！！
    var month=$('#month').val();
    var mon=month.substring(5,7);
$(document).ready(function(){
    var re=/^0+/g;
    mon=mon.replace(re,"")
    if(mon=="0"||mon==""){
      
    }else{
        document.title=+mon+'月销售明细'
    }
    getSaleManRecordByDateList(1,month,mobile)
 })
//分页method中要传手机号和月份，无法引用merge中公共函数故引出来单独使用
//分页函数
//data格式如下：pageObject":{"end":5,"pageNum":2,"pageSize":178,"start":1,"totlePages":18,"totleSize":0};
function pagingMobile2(data,method) {
    var nextbutton = ""; //设置更多
    var txtNextPage = "";
    //设置更多
    if (data.pageObject['pageNum'] < data.pageObject['totlePages']) {
        // nextbutton = "<li><a id=\"moreBtn\" onclick=\""+method+"(" + (data.pageObject['pageNum'] + 1) + ",month,mobile);\">" + txtNextPage + "</a></li>";
        nextbutton = "<li><a id='moreBtn' onclick='"+method+"(" + (data.pageObject['pageNum'] + 1) + ",month,mobile);'>" + txtNextPage + "</a></li>";
    }else{
        nextbutton = "<li><a id=\"moreBtn\" onclick=\"errorMessage('没有更多了')\">" + txtNextPage + "</a></li>";
    }
    $(window).scroll(function(){
        if($(document).scrollTop() >= $(document).height()-$(window).height()){
            $("#moreBtn").click();
        };
    });
    return "<div class=\"pagingDiv MT20\">" + "<ul class=\"paging\">"
        + nextbutton //设置下一页
        + "</ul>" + "</div>";
}
