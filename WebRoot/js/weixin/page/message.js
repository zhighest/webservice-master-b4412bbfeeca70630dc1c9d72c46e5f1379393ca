// 公告
function tab1Click() {
	$("#title").html("公告");
	 $("#myMessage01").empty();
	 getAcListType02(1);
}
// 消息
function tab2Click() {
	$("#title").html("消息");
	$("#myMessage02").empty();
	getAcListType01(1);	
}

function tab1show() {
	$(".tabNav").removeClass("current");
	$(".tab1").addClass("current");
	$(".tabContent").hide();
	$(".tabContent1").show();
}

function tab2show() {
	$(".tabNav").removeClass("current");
	$(".tab2").addClass("current");
	$(".tabContent").hide();
	$(".tabContent2").show();
}
// 后台数据组成列表
$(document).ready(function() {
	var type = $("#type").val();
	if(type == 'notice') {
		tab1show();
		tab1Click();
		getAcListType02(1);
	}else if(type == 'message') {//
    	tab2Click();
    	tab2show();
    	getAcListType01(1);
	}
});
// 消息
var getAcListType01 = function(page) {
	ajaxRequest(contextPath + "wxpush/queryMessage", "current=" + page + "&pageSize=10"+"&type=0", setAcListType01,"GET");
}
// 公告
var getAcListType02 = function(page) {
	ajaxRequest(contextPath + "wxpush/queryMessage", "current=" + page + "&pageSize=10"+"&type=1", setAcListType02,"GET");
}
// 消息
var setAcListType01 = function(data) { //图片
	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == "") {
			html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无消息</p>';
	        html += '<p class="p2">如果有消息，您将在这里看到</p>';
	        html += '</div>';
	} else {
		$.each(data.list,function(i, jsonObj) {
			html += '<div class="timeBox MLRA whiteTex MT10 font16 radius5">'+ jsonObj.time +'</div>';//此处填写日期
			html +='<div class="width90 radius5 whiteBkg ML5P MT10 PTB10"><div class="width90 ML5P outHide positionR">';
			html +='<img src="../pic/weixin/ver2_8/notice1.png"  width="40" class="positionA noticeIcon">';//此处填写图片的链接
			html +='<div class="textL ML60"><h4 class="blackTex">'+ jsonObj.title +'</h4>'; //此处填写小标题
			html += '<p class="grayTex font12">' + jsonObj.details + '';//此处填写内容
			if(jsonObj.messageDetailTypeNew == 2||jsonObj.messageDetailTypeNew == "2"){
			html +='<span id="skipAssetList" class="redTex borderRedB inlineB">点击查看详情</span>';
			}
			html +='</p></div></div></div>';
		});
		}
		//console.log(min, max);
		$("#myMessage02").append(html);//将拼接内容放入id为fundFlowList的ul中
		$("#proListPaging02").html(pagingMobile(data, "getAcListType01"));
		$("#skipAssetList").on("click",function(){
			window.location.href = contextPath + "/wxproduct/goFixAssetList?skipAssetList=skipAssetList";
		})
	}
	else{
		errorMessage(data.resmsg_cn);
	}
}
// 公告
var setAcListType02 = function(data) { //图片
	if(data.rescode == "00000"){
		var html = ""; //拼接html
		if (data.list == null || data.list == "") {
			html += '<div class="listNull">';
	        html += '<img src="../pic/weixin/list.png">';
	        html += '<p class="p1">暂无公告</p>';
	        html += '<p class="p2">如果有公告，您将在这里看到</p>';
	        html += '</div>';
	} else {
		$.each(data.list,function(i, jsonObj) {
		html += '<div class="timeBox MLRA whiteTex MT10 font16 radius5">'+ jsonObj.time +'</div>';//此处填写日期
		html +='<div class="width90 radius5 whiteBkg ML5P MT10 PTB10"><div class="width90 ML5P outHide positionR">';
		html +='<img src="../pic/weixin/ver2_8/notice.png"  width="40" class="positionA noticeIcon">';//此处填写图片的链接
		html +='<div class="textL ML60"><h4 class="blackTex">'+ jsonObj.title +'</h4>'; //此处填写小标题
		html += '<p class="grayTex font12">' + jsonObj.details + '</p></div>';//此处填写内容
		html +='</div></div>';
		});
		}
		//console.log(min, max);
		$("#myMessage01").append(html);//将拼接内容放入id为fundFlowList的ul中
		$("#proListPaging01").html(pagingMobile(data, "getAcListType02"));

	}
}

