$(function(){
	getMonthList(1);
});
function getMonthList(page){
	ajaxRequest(contextPath + "saleManager/querySaleManRecord", "pageSize=10&current="+page,creatMonth,'POST');
};
function creatMonth(data){
	var html = ""; //拼接月份的销售情况的html
    if (data.list == null || data.list == "") {//若无销售记录的情况
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<p class="p1">暂无销售记录</p>';
        html += '<p class="p2">如果有销售记录，您将在这里看到</p>';
        html += '</div>';
    }else {
// 按照年份将同年的数据排在一起
			var dateYear={month:''};
           //将获取年份的字段，将循环中的年份字段加入一个对象中，将本次的年份同上一个字段对比，若不等的话，则年份不同，年份的div显示（后台给的年份的字段都是已经排好序的）
        	for(var i=0;i<data.list.length;i++){
        		var yearNumb=data.list[i].saleDate.substring(0,4);
 				if(dateYear.month!==yearNumb){
 					html += "<div class='MT5 MB5 width90 PLR5P PTB5 grayTex font14 textL outHide'>"+yearNumb+"年</div>";
 				};
 				dateYear.month=yearNumb;
	            html += "<div class='saleMonth MT5 MB5 width90 PLR5P PTB5 whiteBkg font14 outHide'>";
	            html += "<div class='saleMonthTotal width100 height100P'>";
	            html += "<div class='saleAmount fl'>";
	            html +=data.list[i].month+"月销售台数："+data.list[i].total+"台";
	            html += "</div>";
	            html += "<a href="+contextPath +"saleManager/salesManagementDetails?month="+data.list[i].saleDate+"&mobileNumber="+data.list[i].inviterAccount+"><div class='bkg3 radius3 PTB5 PLR10 MT7 fr'>";
	            html += "<span class='whiteTex'>查看明细</span>";
	  			html += "</div></a>"; 				
	  			html += "</div>";
	  			for(var j=0;j<data.list[i].salesmanList.length;j++){ //当月销售的各种型号和台数
	  				var saleStyle=data.list[i].salesmanList[j].productModel.toUpperCase();
	  				html +=" <div class='saleMonthDetail grayTex width100 outHide'>";
		  			html += "<div class='fl'>";
		  			html += "销售型号："+saleStyle;
		  			html += "</div>";
		  			html += "<div class='fr'>";
		  			html += "销售台数："+data.list[i].salesmanList[j].saleCount+"台";
		  			html += "</div>";
		  			html += "</div>";
	  			}
	  		    html += "</div>";
        	}
    }
    $("#saleMonthWrap").append(html);
    $("#monthListPaging").html(pagingMobile(data, "getMonthList"));
 };
