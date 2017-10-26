var oData;

function require(data) {
	if(oData == 'Y') {
		var productId = $('#productId').val();
		ajaxRequest(contextPath + "wxPoint/exchangeProduct", "productId=" + productId, exchange, 'GET');
	} else {
		//errorMessage(data.resmsg_cn)
	}
}

function exchange(data) {
	if(data.rescode == '00000') {
		window.location.href = contextPath + "wxPoint/goExchangeSuccess";
		$('#rightNow').on('click', function() {
			require(data)
		})
	} else {
		errorMessage(data.resmsg_cn);
		$('#rightNow').click(function() {
			require(data);
		})
	}
}
$(document).ready(function() {
	var productId = $('#productId').val();
	var mobile = $('#mobile').val();
	ajaxRequest(contextPath + "wxPoint/queryPointProductDetail", "productId=" + productId + "&mobile=" + mobile, setProductList, 'GET');

	function setProductList(data) {
		oData = data.isUse;
		if(data.rescode = "00000") {
			if(data.pointProduct == "" || data.pointProduct == null) {

			} else {
				var html = '';
				html += '<div class="PT60 PTB2 whiteBkg width100">'
				html += '<img class="width75" src=' + data.pointProduct.imageUrl + '>'
				html += '<div class="ML5P textL redTex"><span id="pointNum" class="font22">' + data.pointProduct.pointAmount + '</span>&nbsp;积分</div>'
				html += '<div class="textL grayTex ML5P heigh30" id="ticketIfo"> ' + data.pointProduct.name + '</div></div>'

				html += '<div class="limitWrap textL blackTex">'
				html += '<div class="limitTime  textL  heigh30">' + data.pointProduct.productParam.period + '</div>'
				html += '<p id="limitWay">' + data.pointProduct.productParam.condition + '</p>'
				if(data.pointProduct.productParamMap.look) {
					html += '<div class="textL">' + data.pointProduct.productParamMap.look + '</div></div>'
				} else {
					html += '<div class="textL">兑换成功后可到“我的账户”查看</div></div>'
				}
				if(data.isUse == 'Y') {
					html += '<a  class="rightNow width80 heigh50 positionA radius3 font18 whiteTex bkg3 textC" id="rightNow">立即兑换</a>'
				} else {
					html += '<a class="rightNow width80 heigh50 positionA radius3 font18 whiteTex bkg2 textC" id="rightNow">立即兑换</a>'
				}
			}
			$('body').append(html);
		} else {
			errorMessage(data.resmsg_cn);
		}
		$('#rightNow').click(function() {
			if(oData!="Y"){
				return;
			}
			var oIfo = $('#ticketIfo').text();
			alertBox('确认兑换' + oIfo, "require", 1);
		})
	}

})