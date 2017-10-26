



$(document).ready(function() { //页面加载完毕
	var channel = $('#channel').val(), //来源
		action = $('#KFunctionType').val(); //具体的行为
		
	if( action == 'null' || action == null || action == ''|| action == undefined || action == 'undefined' || action == 'noEvent'){
		$('.blackBKG').remove();
		return; 
	}
		
	switch (action){
		case 'realName':
			$('#doSome').html('去实名');
			action = 'goRealAuth';
			break;
		case 'bindCard':
			$('#doSome').html('去绑卡');
			action = 'goBindCard';
			break;
		case 'goInvestment':
			$('#doSome').html('立即投资');
			action = 'goFixInvestIndex';
			break;
		case 'exchangeTicket':
			$('#doSome').html('立即兑换');
			action = 'goToIntegralActivity'
			break;
		default:
			break;
	}
	if(channel == "LBAndroid") { //如果运行环境是安卓app
		if(window._cordovaNative) {
			$("#doSome").click(function() {
				window._cordovaNative[action]();
			})	
		}
	}
	else if(channel == "LBIOS") { //如果运行环境是Ios
		$("#doSome").attr("onclick", action+"()");
	} 
})