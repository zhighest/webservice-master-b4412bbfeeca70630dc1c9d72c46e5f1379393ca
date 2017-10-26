var dateFomate = new Date()
$('.year').html(dateFomate.getFullYear());
$('.month').html(dateFomate.getMonth()+1);
$('.day').html(dateFomate.getDate());



$(document).ready(function() {
	//动画初始化 
	$('.animates img').css('display', 'none');
	$($('.animates')[0]).children().css('display', 'block');
	//文字
	$('.wordShow').css('height', '100%');
	$('.wordShow').css('transition', 'all 3s 0.5s ease-in');
})

var my_host = window.location.host;  //  获取当前域名
var mobile = $("#mobile").val(); //APP
var mobileWX = $("#mobileWX").val(); //weixin
var channel = $("#channel").val();
var ua = navigator.userAgent.toLowerCase();
var ImgUrl = ''; //分享图片链接
var sharedUrl = ''; //分享链接
var shareName = ''; //分享者名字
var mySwiper = new Swiper('.swiper-container', {
	direction: 'vertical',
	loop: false,
	// 如果需要分页器
	pagination: '.swiper-pagination',
	// 如果需要前进后退按钮
	nextButton: '.swiper-button-next',
	prevButton: '.swiper-button-prev',
	// 如果需要滚动条
	scrollbar: '.swiper-scrollbar',
	onSlideChangeEnd: function(swiper) {
		$('.animates img').css('display', 'none');
		$($('.animates')[swiper.activeIndex]).children().css('display', 'block');

	},
	onSlideChangeStart: function(swiper) {
		if(swiper.activeIndex == 1) {
			//文字
			$('.wordOne').css('height', '100%');
			$('.wordOne').css('transition', 'all 3s 0.5s ease-in');
		} else if(swiper.activeIndex == 2) {
			var heightTwo = $('.animateStyle img').height();
			$('.animateStyle').css('height', heightTwo);
			$('.animateStyle').css('transition', 'all 2s 0.5s ease-in');
			$('.pageTwo .animateHave').each(function(index){
				$(this).addClass('animated fadeInLeft delay2P5'+index);
			});
			$('.sloganTwo').addClass('animated zoomInDown delay2P52');

			$('.signNum').addClass('animated rollIn');
			$('.giftPackageNum').addClass('animated rollIn');
			$('.inviteFriendNum').addClass('animated rollIn');

		} else if(swiper.activeIndex == 3) {
			var heightThree = $('.animateStyleThree img').height();
			$('.animateStyleThree').css('height', heightThree);
			$('.animateStyleThree').css('transition', 'all 2s 0.5s ease-in');
			$('.pageThree .animateHave').each(function(index){
				$(this).addClass('animated fadeInLeft delay2P5'+index);
			});
			$('.sloganThree').addClass('animated zoomInDown delay2P53');
		} else if(swiper.activeIndex == 4) {
			var heightFour = $('.animateStyleFour img').height();
			$('.animateStyleFour').css('height', heightFour);
			$('.animateStyleFour').css('transition', 'all 2s 0.5s ease-in');
			$('.pageFour .animateHave').each(function(index){
				$(this).addClass('animated fadeInLeft delay2P5'+index);
			});
			$('.sloganFour').addClass('animated zoomInDown delay2P53');
			$('.sloganSix').addClass('animated bounceIn delay2P53');
			$('.sloganFive').addClass('animated rollIn delay2P53')
		}
	},
	onTouchStart: function() {
		if(channel == "LBAndroid") { // 安卓												//安卓
			if(mobile == "" || mobile == null || mobile == "null") {
				if(window._cordovaNative) {
					errorMessage('请先登录联璧账户查看成长之路~');
				}
			} else {

			}
		} else if(channel == "LBIOS") { // ios												//IOS
			if(mobile == "" || mobile == null || mobile == "null") {
				errorMessage('请先登录联璧账户查看成长之路~');
			} else {

			}
		} else { //微信
			if(mobileWX == "" || mobileWX == null || mobileWX == "null" || mobileWX == 'undefined' || mobileWX == undefined) {
				errorMessage('请先登录联璧账户查看成长之路~');
			}
		}
	}

});

//数据渲染
var initialData = function(res) {
	data = res.userData;
	//日期格式化
	var tmpTime = data.registerTime;
	tmpTime = data.registerTime.split('-');

	$('.timeLong').html(data.joinTime);//相聚今天

	$('.year').html(tmpTime[0]);
	$('.month').html(tmpTime[1]);
	$('.day').html(tmpTime[2]);

	$('.rank').html(data.registerSort); //排名
	$('.signNum').html(data.signNum); //签到
	$('.giftPackageNum').html(data.giftPackageNum); //兑换礼包
	$('.inviteFriendNum').html(data.inviteFriendNum); //邀请好友
	$('.investAmount').html(data.investAmount) //累计投资
	$('.pointAmount').html(data.pointAmount) //累计积分
	$('.interestCouponNum').html(data.interestCouponNum) //加息券
	$('.jsmCouponNum').html(data.jsmCouponNum) //加速券
	$('.cashCouponNum').html(data.cashCouponNum) //代金券
	$('.tsmCouponNum').html(data.tsmCouponNum) //k码券
	$('.income').html(data.income); //总收入
	$('.investSort').html(data.investSort); //排名
	$('.vipGradeName').html(data.vipGrade.name); //会员等级
	$('.investPercent').html(parseInt(toPercent(data.investPercent))); //战胜用户比

	if((data.jsmCouponNum + data.interestCouponNum + data.cashCouponNum + data.tsmCouponNum) >= 10) {
		$('.CouponNum').html('恭喜您成为用券小达人！');
	} else {
		$('.CouponNum').html('您参与的活动次数有点少哦！');
	}

	if(parseInt(toPercent(data.investPercent)) >= 60) { //投资大于60%
		
	}
	//分享链接  图片链接  分享者
	ImgUrl = data.anniversary.imgUrl;
	sharedUrl = getUrlHttp() + my_host + data.anniversary.sharedUrl;
	shareName = data.realName;

}

//判断是微信还是app 微信
if(channel == "LBAndroid") { // 安卓												//安卓
	if(mobile == "" || mobile == null || mobile == "null") {
		if(window._cordovaNative) {
			errorMessage('请先登录联璧账户查看成长之路~');
			$($('.swiper-slide')[0]).addClass('swiper-no-swiping');
		}
	} else {
		$($('.swiper-slide')[0]).removeClass('swiper-no-swiping');
		ajaxRequest(contextPath + "/wxAnniversary/anniversary", "mobile=" + mobile, initialData);
	}
} else if(channel == "LBIOS") { // ios												//IOS
	if(mobile == "" || mobile == null || mobile == "null") {
		errorMessage('请先登录联璧账户查看成长之路~');
		$($('.swiper-slide')[0]).addClass('swiper-no-swiping');
	} else {
		$($('.swiper-slide')[0]).removeClass('swiper-no-swiping');
		ajaxRequest(contextPath + "/wxAnniversary/anniversary", "mobile=" + mobile, initialData);
	}
} else {
	if(mobileWX == "" || mobileWX == null || mobileWX == "null" || mobileWX == 'undefined' || mobileWX == undefined) {
		errorMessage('请先登录联璧账户查看成长之路~');
		$($('.swiper-slide')[0]).addClass('swiper-no-swiping');
	} else {
		$($('.swiper-slide')[0]).removeClass('swiper-no-swiping');
		ajaxRequest(contextPath + "/wxAnniversary/anniversary", "mobile=" + mobileWX, initialData);	
	}

}

//分享微信好友和朋友圈
$(".toShare").click(function() {

	var shareTitle = '您的好友' + shareName + '送您联璧金融80元代金券';
	var shareDesc = "预期年化收益率6.9%，当日计息";
	var shareImgUrl = ImgUrl;
	var shareLink = sharedUrl;
	
	if(channel == "LBAndroid") {
		shareLink = shareLink+'?mobile='+mobile + '&channel=LBAndroid';
		if(window._cordovaNative) {
			window._cordovaNative.webContentShare(shareTitle, shareDesc, shareLink, shareImgUrl);
		}
	} else if(channel == "LBIOS") {
		shareLink = shareLink+'?mobile='+mobile + '&channel=LBIOS';
		webContentShare(shareTitle, shareDesc, shareLink, shareImgUrl);
	} else {
		sharedUrl = sharedUrl+'?mobile='+mobileWX + '&channel=WX';
		$('.sharePopup').css('display', 'block');
		$('.sharePopup').click(function() {
			$(this).hide();
			return false;
		});
		$('.sharePopup').on('touchmove', function() {
			return false;
		});
		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.onMenuShareTimeline({
			title: shareTitle, // 分享标题
			desc: shareDesc, // 分享描述
			link: sharedUrl, // 分享链接
			imgUrl: shareImgUrl, // 分享图标
			success: function() {
				// 用户确认分享后执行的回调函数
				errorMessage("分享成功");
				$('.sharePopup').css('display', 'none');
			},
			cancel: function() {
				// 用户取消分享后执行的回调函数
				errorMessage("未分享");
			}
		});
		//获取“分享给朋友”按钮点击状态及自定义分享内容接口
		wx.onMenuShareAppMessage({
			title: shareTitle, // 分享标题
			desc: shareDesc, // 分享描述
			link: sharedUrl, // 分享链接
			type: 'link', // 分享类型,music、video或link，不填默认为link
			imgUrl: shareImgUrl, // 分享图标
			dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			success: function() {
				// 用户确认分享后执行的回调函数
				errorMessage("分享成功");
				$('.sharePopup').css('display', 'none');
			},
			cancel: function() {
				// 用户取消分享后执行的回调函数
				errorMessage("未分享");
			}
		});

	}
});