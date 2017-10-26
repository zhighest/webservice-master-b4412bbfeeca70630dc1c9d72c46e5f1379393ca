var ymArray = "";//用于月份分类
var oType;
var getFundFlowList = function(page) { 
    ajaxRequest(contextPath + "wxtrade/getFundFlowList", "current=" + page + "&pageSize=15&type="+oType, setFundFlowList);
} 
//分页函数
var getFundFlowListMore = function(page) { 
    ajaxRequest(contextPath + "wxtrade/getFundFlowList", "current=" + page + "&pageSize=15&type="+oType, setFundFlowListMore);
}
//获取交易类别
var getTradeTypeList = function(from) { 
    ajaxRequest(contextPath + "wxtrade/getTradeTypeList", "from="+from, setTradeTypeList);
} 

var week=['周日','周一','周二','周三','周四','周五','周六'];
var onOff=true;
var setFundFlowList = function(data) { //处理中订单生成HTML
    var sysdate=data.sysdate//获取系统时间
    var html = ""; //拼接html
    if ((data.list == null || data.list == "") &&  onOff) {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无交易记录</p>';
        html += '<p class="p2">如果有交易记录，您将在这里看到</p>';
        html += '</div>';
        $("#fundNull").append(html);  
        $("#fundList").hide(); 
    } else {
        onOff=false;
        $.each(data.list,
        function(i, jsonObj) {
            var newdate=getDate(sysdate);
            var date=getDate(jsonObj.transDate.substring(0,10));
            var datDate=Math.floor(Math.floor((newdate-date))/1000/86400);
            var iWeek = date.getDay();
            var ym =jsonObj.transDate.substring(0,7);
            var md =jsonObj.transDate.substring(5,16);
            var dd=jsonObj.transDate.substring(10,16);
            var newYm=newdate.Format("yyyy/MM");
            if(ym != ymArray){
                ymArray = ym;
                if(ym == newYm){
                    html += '<li  class="borderB borderT grayBkg textL font14 grayTex"><span class="textL blockC clearfix month">本月</span></li>';
                }else{
                    html += '<li  class="borderB borderT grayBkg textL font14 grayTex"><span class="textL blockC clearfix month">'+ym+'</span></li>';   
                }
            }
            html+='<li class="MLR5 borderB detail" flowId="'+jsonObj.flowId+'"  transFlowNo="'+jsonObj.transFlowNo+'" >' 
            html+='<div class="outHide font16" style="padding-top:9px;">'  
                html+='<div class="fl  font16" style="color:#333">'+jsonObj.fundType+'</div>'
            if(jsonObj.type == 0){
                html += '<div class="fr blackTex2"><span class="font18">-'+ numFormat(jsonObj.amount) +'元</span></div></div>'; 
            }else{
                html += '<div class="fr  blackTex2"><span class="font18">+'+ numFormat(jsonObj.amount) +'元</span></div></div>';
            }
            html+='<div class="outHide PT5 font14" style="padding-bottom:5px;"><div class="fl grayTex">';
            if(datDate==0){
                html+='<span  class="font14">今天'+dd+'</span>'
             }else if(datDate==1){
                html+='<span  class="font14">昨天'+dd+'</span>'
             } else{
                html+='<span  class="font14">'+md+'</span>'
             }
             html+='</div><div class="fr grayTex2">';
             if(jsonObj.isSuccess == 1||jsonObj.isSuccess == "1"){
            	  html+='<span  class="font12" style="color: #77b8f1;">'+ jsonObj.status +'</span></div></div>'; 
             }
             else{
            	 html+='<span  class="font12" style="color: #ff5a5a;">'+ jsonObj.status +'</span></div></div>';  
             }
             html+='</li>'
             
       });
       $("#fundList").show(); 
       $("#fundList").append(html);
       onOff=true;
    }
    
    $("#fundFlowListPaging").html(pagingMobile(data, "getFundFlowListMore"));
};
//分页函数回调
var setFundFlowListMore = function(data) { //处理中订单生成HTML
    var sysdate=data.sysdate//获取系统时间
    var html = ""; //拼接html
    if ((data.list == null || data.list == "") &&  onOff) {
        
    } else {
        onOff=false;
        $.each(data.list,
        function(i, jsonObj) {
            var newdate=getDate(sysdate);
            var date=getDate(jsonObj.transDate.substring(0,10));
            var datDate=Math.floor(Math.floor((newdate-date))/1000/86400);
            var iWeek = date.getDay();
            var ym =jsonObj.transDate.substring(0,7);
            var md =jsonObj.transDate.substring(5,16);
            var dd=jsonObj.transDate.substring(10,16);
            var newYm=newdate.Format("yyyy/MM");
            if(ym != ymArray){
                ymArray = ym;
                if(ym == newYm){
                    html += '<li  class="borderB borderT grayBkg textL font14 grayTex"><span class="textL blockC clearfix month">本月</span></li>';
                }else{
                    html += '<li  class="borderB borderT grayBkg textL font14 grayTex"><span class="textL blockC clearfix month">'+ym+'</span></li>';   
                }
            }
            html+='<li class="MLR5 borderB detail" flowId="'+jsonObj.flowId+'"  transFlowNo="'+jsonObj.transFlowNo+'" >' 
            html+='<div class="outHide font16" style="padding-top:9px;">'  
                html+='<div class="fl  font16" style="color:#333">'+jsonObj.fundType+'</div>'
            if(jsonObj.type == 0){
                html += '<div class="fr blackTex2"><span class="font18">-'+ numFormat(jsonObj.amount) +'元</span></div></div>'; 
            }else{
                html += '<div class="fr  blackTex2"><span class="font18">+'+ numFormat(jsonObj.amount) +'元</span></div></div>';
            }
            html+='<div class="outHide PT5 font14" style="padding-bottom:5px;"><div class="fl grayTex">';
            if(datDate==0){
                html+='<span  class="font14">今天'+dd+'</span>'
             }else if(datDate==1){
                html+='<span  class="font14">昨天'+dd+'</span>'
             } else{
                html+='<span  class="font14">'+md+'</span>'
             }
             html+='</div><div class="fr grayTex2">';
             if(jsonObj.isSuccess == 1||jsonObj.isSuccess == "1"){
            	  html+='<span  class="font12" style="color: #77b8f1;">'+ jsonObj.status +'</span></div></div>'; 
             }
             else{
            	 html+='<span  class="font12" style="color: #ff5a5a;">'+ jsonObj.status +'</span></div></div>';  
             }
             html+='</li>'
             
       });
       $("#fundList").show(); 
       $("#fundList").append(html);
       onOff=true;
    }
    
    $("#fundFlowListPaging").html(pagingMobile(data, "getFundFlowListMore"));
};
function setTradeTypeList(data){
	if(data.rescode==="00000"){
		var dataLen=data.listSize;
		var html="";
		for(var i=0;i<dataLen;i++){
			var oVal=data.list[i].value||"";
			html+='<li class="selectItem textC fl" data-type='+oVal+'>\
					<span class="text blackTex font16" >'+data.list[i].name+'</span>\
				</li> '
		}
		$("#selectList").empty();
		$("#selectList").html(html);
		$("#selectList li").eq(0).addClass('active');
		$("#selectList li").eq(0).find('span').addClass('active');
		var totalWidth=0;
    	for(var i=0;i<dataLen;i++){
    		totalWidth+=$("#selectList li").eq(i).innerWidth();
    	}
    	$("#selectList").css('width',totalWidth+100);
	}else{
		console.log(data.resmsg_cn);
	}
}
var goNext = function(flowId,transFlowNo) {
    var temp = document.createElement("form");
    temp.action = contextPath + "wxpay/goDealDetail";
    temp.method = "GET";
    
    var inp = document.createElement("input");
    inp.name = "flowId";
    inp.value = flowId;
    temp.appendChild(inp);

    var inp2 = document.createElement("input");
    inp2.name = "transFlowNo";
    inp2.value = transFlowNo;
    temp.appendChild(inp2);

    document.getElementById("formDiv").appendChild(temp);
    temp.submit();
}
$(document).ready(function() {
	oType=""
    getFundFlowList(1,oType);
    $("#fundList").on("click",".detail",function(){
    	var flowId=$(this).attr("flowId");
    	var transFlowNo=$(this).attr("transFlowNo");
    	goNext(flowId,transFlowNo)
    })
    var oFrom="LBWX"
    getTradeTypeList(oFrom);
    $("#selectList").on('click','.selectItem',function(){
    	oType=$(this).attr("data-type")//获取筛选类型
    	$("#selectList li").removeClass('active');//点击切换样式
    	$("#selectList li span").removeClass('active');
    	$(this).addClass('active');
    	$(this).find('span').addClass('active');
    	$("#fundList").empty();
     	$("#fundNull").empty();
     	ymArray="";//初始化日期月份
    	getFundFlowList(1,oType);//拉取筛选数据
	});
});