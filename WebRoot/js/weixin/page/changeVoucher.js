$(document).ready(function() {
	//台数券
	ajaxRequest(contextPath + "wxGiftPackage/getQuantityCouponList", "current=1&pageSize=5", setVouchers, "GET");
	//显示可激活K码数量
	$('#showActiveStatus').click(function() {
		$('#statusAlertBox').removeClass('none');
		ajaxRequest(contextPath + "wxactivity/getKcodeSurplusTimesList", "", getGiftPackgeList);
		function getGiftPackgeList(data) {
			if(data.rescode === "00000") {
				$('#statusContentList').empty();
				$('#statusContentList').html(data.kcodeNumberInfo);
			} else {
				errorMessage(data.resmsg_cn);
			}
		} 

	});
	//关闭按钮
	$('#closeIcon').click(function() {
		$('#statusAlertBox').addClass('none');
	});
});
var getMoreVouchers = function(page) { //获取更多
	//台数券
	ajaxRequest(contextPath + "wxGiftPackage/getQuantityCouponList", "current=" + page + "&pageSize=5", setVouchers, "GET");
}
var setVouchers = function(data) {
	if(data.rescode == "00000") {
		//判断是否显示我的K码激活资格开关
		if(data.kCodeSwitch=='N'){
			$('#showActiveStatus').addClass('none');
		}
		var html = '';
		if(data.list == "" || data.list == null || data.list.length == 0) {
			html += '<div class="listNull">';
			html += '<img style="height: 70px" src="../pic/weixin/K3activity/emptyIcon.png">';
			html += '<p class="p1">暂无K码券</p>';
			html += '<p class="p2">如果有K码券，您将在这里看到</p>';
			html += '<a class="block toUsedVoucher positionR PT20 myspan font14" href="' + contextPath + 'wxcoupons/usedVoucher?isVoucher=ts">点击查看已使用的K码券 ></a>';
			html += '</div>';
		} else {
			$.each(data.list, function(i, jsonObj) {
				var endDate = getDate(jsonObj.endDate).Format("yyyy/MM/dd hh:mm:ss");
				endDate = endDate.substring(0, 10);
				var lovValue = jsonObj.lovValue;
				var couponNo = jsonObj.couponNo;
				var couponName = jsonObj.couponName;
				var quantity = jsonObj.quantity;
				html = "<li class='usedVoucher whiteBkg positionR MT10' couponNo='" + couponNo + "' onclick=\"useexchangeVouchers('" + couponName + "','" + couponNo + "','" + quantity + "')\">" +
					"<div class='borderDiv boxSizing'>" +
					"<div class='borderDiv1 boxSizing PTB10 font14'>" +
					"<div style='line-height:25px;'>" +
					"<p class='redTex'>" + lovValue + "</p>" +
					"<p>" + endDate + "到期</p></div></div></div>" +
					"</li>";
				$("#exchangeList").append(html);
			});
		}
		if(data.pageObject.pageNum == data.pageObject.totlePages && (data.list != "" || data.list.length != 0)) { //当加载到最后一页的时候  底部出现 ‘点击查看已使用的兑换券’
			var htmlBottom = '<a class="block toUsedVoucher PTB16 myspan font14" href="' + contextPath + 'wxcoupons/usedVoucher?isVoucher=ts">点击查看已使用的K码券 ></a>';
			$("#exchangeList").append(htmlBottom);
		} else if(data.list == "" || data.list == null || data.list.length == 0) {
			$("#exchangeList").append(html);
		}
		$("#exchangeListPaging").html(pagingMobile(data, "getMoreVouchers"));
	} else {
		errorMessage(data.resmsg_cn);
	}
}

//点击券的弹窗
var useexchangeVouchers = function(voucherTypeDetail, couponNum, quantity) {
	alertBox1("使用后可获得" + quantity + "次兑换<font class='redTex'>" + voucherTypeDetail + "</font>激活资格，确认使用K码券？", "cancelUseIt", "取消", "goUseVouchers('" + couponNum + "')", "确定", 1)
}
//弹窗点击取消
var cancelUseIt = function() {
	$("#alertBox").remove();

}
//弹窗点击确定
var goUseVouchers = function(couponNum) {
	ajaxRequest(contextPath + "wxGiftPackage/useQuantityCoupon", "couponNo=" + couponNum, setUseShopVouchers, "GET");
}
var setUseShopVouchers = function(data) {
	if(data.rescode == "00000") {
		errorMessage("使用成功");
		window.location.reload();
	}
}