var typeIds=0;
var setBannerList = function(data) {
	if(data.rescode == "00000") {
		$('#useAmount').html(data.useAmount);
		$('#invalidAmount').html(data.invalidAmount);
		$("#gradeName").html(data.gradeName)
		var html = ''; //拼接html
		var html2 = '';
	} else {
		errorMessage(data.resmsg_cn);
	}
};
//筛选类型列表
function setTradeTypeList(data) {
	if(data.rescode === "00000") {
		var oListData = $.parseJSON(data.typeListJson);
		var oLiLen = oListData.length;
		var html = "";
		for(var i = 0; i < oLiLen; i++) {
			var oVal = oListData[i].code || "";
			html += '<li class="selectItem textC fl" data-type=' + oVal + '>\
					<span class="text blackTex font16" >' + oListData[i].name + '</span>\
				</li> '
		}
		$("#selectList").empty();
		$("#selectList").html(html);
		$("#selectList li").eq(0).addClass('active');
		$("#selectList li").eq(0).find('span').addClass('active');
		var totalWidth = 0;
		for(var i = 0; i < oLiLen; i++) {
			totalWidth += $("#selectList li").eq(i).innerWidth();
		}
		$("#selectList").css('width', totalWidth + 100);
	} else {
		console.log(data.resmsg_cn);
	}
}
var isLoaded = false;
//生产主体部分
var setPruductList = function(data) {
	var html = ''; //拼接html
	
	if(data.pruductList == null || data.pruductList == "") {
	} else {
		//首次加载筛选信息
		if(!isLoaded) {
			setTradeTypeList(data); //筛选类型
		}
		$.each(data.pruductList,
			function(i, jsonObj) {
				html += '<li class="fl mainItem textL MB10 font12 border boxSizing" id=' + jsonObj.id + '>'
				html += '<div class="item PT20 PLR10 PB10 positionR ">'
				if(jsonObj.productParam.newest == 'Y') {
					html += '<span class="positionA new"></span>'
				}
				html += '<a href="javascript:;" class="blackTex block productLink width100">'
				html += '<img src=' + jsonObj.imageUrl + ' width="100%"></a>'
				html += '<a href="javascript:;" class="blackTex inlineB productLink MT10 width100">'
				html += '<div class="lineHeight1_5x name">' + jsonObj.name + '</div>'
				html += '<div class="needScore redTex lineHeigt20"><span class="blackTex">积分&nbsp;</span>' + jsonObj.pointAmount + '</div></div></a></li>'
			});
		$('#pruductList').append(html);
		$('.productLink').click(function() {
			var productId = $(this).parents('li').attr('id');
			var mobile = $('#mobile').val();
			var invalidAmount = $('#invalidAmount').html();
			window.location.href = contextPath + "wxPoint/exchangeDetail?productId=" + productId
		})
		isLoaded = true;
	}
	$('#pruductListPaging').html(pagingMobile(data, "getProdunctList"))
}
//规则说明弹窗
function alertRule(data) {
	var re2 = /\d+(\.\d+)?%/g;
	var activtyText = data.pointDeclare.split('<br>');
	var re = /^\s+|\s+$/g;
	var html = '';
	$("#scoreListRule").children('li').remove();
	$.each(activtyText, function(i, Obj) {
		html += '<li>' + Obj + '</li>'
	});
	$("#scoreListRule").append(html);
}

function getScoreRule() {
	ajaxRequest(contextPath + "wxPoint/getPointInfo", "", alertRule, "GET");
}

function getProdunctList(page) {
	ajaxRequest(contextPath + "wxPoint/pointProductList", "current=" + page + "&pageSize=6&typeIds=" + typeIds, setPruductList, 'GET');
}
$(function() {
	ajaxRequest(contextPath + "wxPoint/index", "", setBannerList, 'GET');
	getProdunctList(1);
	getScoreRule();
	$('.outdateScore').click(function() {
		window.location.href = contextPath + "wxPoint/outdateScore"
	})
	$('#inOutDetail').click(function() {
		window.location.href = contextPath + "wxPoint/inOutDetail"
	});
	$('.scoreNotice ').click(function() {
		$('.scoreExplain').removeClass('none').animate({ 'top': 0 }, 300);
	});
	$('.scoreNotice ').click(function() {
		$('.scoreExplain').removeClass('none').animate({ 'top': 0 }, 300);
	});
	var screenH = $('body').height();
	$('.closeBtn').click(function() {
		$('.scoreExplain').animate({ 'top': screenH * 1.3 }, 300);
	})
	$('#vip').click(function() {
		window.location.href = contextPath + "wxdeposit/goVipGrade"
	})
})
$(document).ready(function() {
	$("#selectList").on('click', '.selectItem', function() {
		typeIds = $(this).attr("data-type") //获取筛选类型
		$("#selectList li").removeClass('active'); //点击切换样式
		$("#selectList li span").removeClass('active');
		$(this).addClass('active');
		$(this).find('span').addClass('active');
		$('#pruductList').empty();
		getProdunctList(1);
	});
	var oContentScrollTop=$('#main')[0].offsetTop+10;//积分导航条距离顶部的距离
	//滚动置顶效果
	$(window).on("scroll",function(){
      var scroll_top = $(document).scrollTop();//滚动条到顶部的垂直高度 
      if(scroll_top > oContentScrollTop){ 
          $("#selectContent").addClass('positionF fixTop'); 
          $("#scrollBox").removeClass('none');//解决置顶时抖动的问题
        }else{ 
          $("#scrollBox").addClass('none');
          $("#selectContent").removeClass('positionF fixTop'); 
      } 
	});
});