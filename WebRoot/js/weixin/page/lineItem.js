var host = "",
	i = 0,
	t = 0,
	host2 = "flowId",
	host3 = "transFlowNo",
	t2 = 0,
	i2 = 0;
	host = window.location.href;
	t = host.indexOf("flowId");
	t2 = host2.length;
	i2 = host3.length;
	i = host.indexOf("transFlowNo");
	host2 = host.substring(i + i2 + 1);
	host3 = host.substring(t + 1 + t2, i - 1);
	ajaxRequest(contextPath + "wxtrade/getFundFlowDetail", "flowId=" + host3 + "&transFlowNo=" + host2, list, "POST");
	function list(data) {
		var html = "",
			obj = data.fundFlowDetailVo;
		html = '<header class="whiteBkg PTB30 ">';
		html += '<div class=" grayTex">交易金额(元)</div>';
		if(obj.type == 0) {
			html += '<div class="font40 MT7 helveticaneue"><span>+</span>';
		} else {
			html += '<div class="font40 MT7 helveticaneue"><span>-</span>';    
		}
		html += '<span>' + obj.amount + '</span></div></header><section class="whiteBkg ">';
		html += '<ul class="width90 MLRA borderC PT20">';
		html += '<li class=" clearfix PB20"><span class="fl">交易类型</span><span class="fr grayTex">' + obj.fundType + '</span></li>';
		if(obj.isShowCreateDate == 1 || obj.isShowCreateDate == "1") {
			html += '<li class=" clearfix PB20"><span class="fl">申请时间</span><span class="fr grayTex">' + obj.createDate + '</span></li>';
		}
		html += '<li class=" clearfix PB20"><span class="fl">交易时间</span><span class="fr grayTex">' + obj.transDate + '</span></li>';
		if(obj.isRemark ==1||obj.isRemark =="1") {
			html += '<li class=" clearfix PB20"><span class="fl">银行卡</span><span class="fr grayTex">' + obj.remark + '</span></li>';
		}
		if(obj.isSuccess == 1||obj.isSuccess =="1"){
			html += '<li class=" clearfix PB20"><span class="fl">交易状态</span><span class="fr blue">' + obj.status + '</span></li></ul></section>';
		}
		else{
			html += '<li class=" clearfix PB20"><span class="fl">交易状态</span><span class="fr redTex">' + obj.status + '</span></li></ul></section>';
		}
		$("#grayBk").append(html); 
	}