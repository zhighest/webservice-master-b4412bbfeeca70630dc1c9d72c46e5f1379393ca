//隐藏数字中间, 调用：numHide(13888888888)或numHide('13888888888');
function numHide(m) {
	if(m.length <= 11) {
		return m.substr(0, 3) + '****' + m.substr(7);
	} else {
		return m.substr(0, 4) + ' **** **** ' + m.substr(13);
	}
}

//金额千分位转换函数（带小数点后两位）
//money 类型为string 或 number, 调用：numFormat(10000)或numFormat('10000');
function numFormat(m) {
	var m2 = parseFloat(m);
	if(isNaN(m2)) {
		return false;
	}
	var num = m2 + ""
	var re = /([0-9]+\.[0-9]{2})[0-9]*/;
	var m2 = num.replace(re, "$1")

	var money = m2.toString();
	var pos_decimal = money.indexOf('.');
	if(pos_decimal < 0) {
		pos_decimal = money.length;
		money += '.';
	}
	while(money.length <= pos_decimal + 2) {
		money += '0';
	}
	if(typeof money == "number") money = money.toString();
	return money.replace(/^(\d+)((\.\d+)?)$/, function(v1, v2, v3) {
		return v2.replace(/\d{1,3}(?=(\d{3})+$)/g, '$&,') + (v3 || '.00');
	});
}

//金额千分位转换函数（不带小数点后两位）
//money 类型为string 或 number, 调用：numFormat(10000)或numFormat('10000');
function numFormatInteger(m) {
	var m2 = parseFloat(m);
	if(isNaN(m2)) {
		return false;
	}
	var num = m2 + ""
	var re = /([0-9]+\.[0-9]{2})[0-9]*/;
	var m2 = num.replace(re, "$1")

	var money = m2.toString();
	var pos_decimal = money.indexOf('.');
	if(pos_decimal < 0) {
		pos_decimal = money.length;
		money += '.';
	}
	while(money.length <= pos_decimal + 2) {
		money += '0';
	}
	if(typeof money == "number") money = money.toString();
	return money.replace(/^(\d+)((\.\d+)?)$/, function(v1, v2, v3) {
		return v2.replace(/\d{1,3}(?=(\d{3})+$)/g, '$&,');
	});
}

//加法函数
function accAdd(dataOne, dataTwo) {

	var dataOneInt = dataOne.toString().split(".")[0];
	var dataOneFloat = "";
	var dataTwoInt = dataTwo.toString().split(".")[0];
	var dataTwoFloat = "";
	var lengthOne = 0;
	var lengthTwo = 0;
	var maxlength = 0;

	if(dataOne.toString().split(".").length == 2) {
		dataOneFloat = dataOne.toString().split(".")[1];
		lengthOne = dataOneFloat.toString().length;

	}
	if(dataTwo.toString().split(".").length == 2) {
		dataTwoFloat = dataTwo.toString().split(".")[1];
		lengthTwo = dataTwoFloat.toString().length;

	}

	maxLength = Math.max(lengthOne, lengthTwo);
	for(var i = 0; i < maxLength - lengthOne; i++) {
		dataOneFloat += "0";
	}
	for(var i = 0; i < maxLength - lengthTwo; i++) {
		dataTwoFloat += "0";
	}

	/**
	 *对两个数据进行倍数放大
	 *使其都变为整数。因为整数计算
	 *比较精确。
	 */
	var one = dataOneInt + "" + dataOneFloat;
	var two = dataTwoInt + "" + dataTwoFloat;
	//alert("dataOne:"+dataOne+" dataTwo:"+dataTwo +" one:"+one+" two:"+two);

	/**
	 *数据扩大倍数后，经计算的到结果，
	 *然后在缩小相同的倍数
	 *进而得到正确的结果
	 */
	var result = (Number(one) + Number(two)) / Math.pow(10, maxLength);

	return result;
}

//减法函数
function Subtr(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	//last modify by deeka
	//动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return((arg1 * m - arg2 * m) / m).toFixed(n);
}

//乘法函数
function accMul(arg1, arg2) {
	var m = 0,
		s1 = arg1.toString(),
		s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch(e) {}
	try {
		m += s2.split(".")[1].length;
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

//除法函数
function accDiv(arg1, arg2) {
	var t1 = 0,
		t2 = 0,
		r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch(e) {}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch(e) {}
	with(Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return(r1 / r2) * pow(10, t2 - t1);
	}
}

//接口加解密函数
//port.encrypt(str) 加密方法, port.decrypt(str) 解密方法;
var key = "SHHBJR$mobile%0519[!SECRET@]&";
var port = {
	encrypt: function(str) {
		return DES3.encrypt(key, encodeURIComponent(str));
	},
	////3DES解密，CBC/PKCS5Padding
	decrypt: function(str) {
		return decodeURIComponent(DES3.decrypt(key, str));
	}
};

//接口函数
function ajaxRequest(url, str, method, type) {
	var typeValue;
	if(type == null || type == "") {
		typeValue = "POST";
	} else {
		typeValue = type;
	}
	$.ajax({
		url: url,
		type: typeValue,
		dataType: "json",
		// data: port.encrypt(str),
		data: str,
		beforeSend: function(xhr) {
			var smDeviceId = getCookie("smDeviceId") || ""; //获取数美设备ID
			xhr.setRequestHeader('smDeviceId', smDeviceId); // 把数美的设备ID放入请求头里
		},
		success: function(data) {
			//method(port.decrypt(data));
			method(data);
		},
		error: function() {
			console.log("请求失败!");
		}
	});
};

//报错
function errorMessage(str) {
	function hideError() {
		$("body .errorMessage").stop().animate({ top: "-75px" }, "fast", function() {
			$(this).remove();
		})
	}
	var html = '<div class="errorMessage" style="top: -75px; position: fixed; left:0; text-align: center; width: 100%; display:block; z-index:1111; background-color: #ff5855; "><font style=" color: #fff; z-index:1000; display: inline-table; line-height: 75px; font-size:26px; width:100%; max-width: 100%;">' + str + '</font></div>';
	$("body").append(html);
	$("body .errorMessage").stop().animate({ top: 0 }, "fast", function() {
		setTimeout(hideError, 2500);
	})
}

//输入框限制
//object 输入框限制的对象，maximum 可输入的最大值，limited 是否限制0开始，true为限制。 maximum与limited可不输入。
//oninput="$.checkLimit($(this),1000,true);" onkeyup="$.checkLimit($(this),1000,true);" onafterpaste="$.checkLimit($(this),1000,true);"
(function($) {
	$.extend({
		checkLimit: function(object, maximum, limited) {
			if(limited == null || limited == "" || !limited) {
				var re = /^[0-9][0-9]{0,}[\.]{0,1}[0-9]{0,2}$/ig;
			} else {
				var re = /^[1-9][0-9]{0,}[\.]{0,1}[0-9]{0,2}$/ig;
			}
			if(object.val() != "") {
				if(!re.test(object.val())) {
					object.val(object.attr("alt"));
					return false;
				} else {
					object.attr("alt", object.val());
				}
			} else {
				object.attr("alt", "");
			}
			if(maximum) {
				if(maximum != null || maximum != "") {
					if(object.val() >= maximum) {
						object.val(maximum);
					} else if(object.val() < 0) {
						object.val(0);
					}
				}
			}
		}
	});
})(jQuery);
 

//输入框限制  可以输入部分特殊字符  !@#$%^&*  0-9a-zA-Z 
//object 输入框限制的对象，maximum 可输入的最大值，limited 是否限制0开始，true为限制。 maximum与limited可不输入。
//oninput="$.checkLimit($(this),1000,true);" onkeyup="$.checkLimit($(this),1000,true);" onafterpaste="$.checkLimit($(this),1000,true);"
(function($) {
	$.extend({
		checkLimit1: function(object, maximum, limited) {
			if(limited == null || limited == "" || !limited) {
				var re = /^[a-zA-Z0-9\!\@\#\$\^\*]+$/g;
			} else {
				var re = /^[a-zA-Z0-9\!\@\#\$\^\*]+$/g;
			}
			if(object.val() != "") { 
				if(!re.test(object.val())) {
					object.val(object.attr("alt"));
					return false;
				} else {
					object.attr("alt", object.val());
				}
			} else {
				object.attr("alt", "");
			}
			if(maximum) {
				if(maximum != null || maximum != "") {
					console.log("1:" + Number(object.val()) + " / 2:" + Number(maximum))
					if(Number(object.val()) >= Number(maximum)) {
						object.val(maximum);
						object.attr("alt", maximum)
					} else if(object.val() < 0) {
						object.val(0);
					}
				}
			}
		}
	});
})(jQuery);

//分页函数
//data格式如下：pageObject":{"end":5,"pageNum":2,"pageSize":178,"start":1,"totlePages":18,"totleSize":0};
function paging(data, method) {
	var firstbutton = ""; //设置首页
	var beforebutton = ""; //设置上一页
	var beforepagestad = ""; //页码前面的省略号
	var pagesbutton = ""; //设置页码
	var lastpagestad = ""; //页码后面的省略号
	var nextbutton = ""; //设置下一页
	var lastbutton = ""; //设置末页
	var txtHomePage = "首页";
	var txtEndPage = "末页";
	var txtPrevPage = "上一页";
	var txtNextPage = "下一页";
	//设置首页
	if(data.pageObject['pageNum'] == 1) {
		firstbutton = "<li><a>" + txtHomePage + "</a></li>";
	} else {
		firstbutton = "<li><a onclick=\"" + method + "(1)\">" + txtHomePage + "</a></li>";
	}
	//设置上一页
	if(data.pageObject['pageNum'] > 1) {
		beforebutton = "<li><a onclick=\"" + method + "(" + (data.pageObject['pageNum'] - 1) + ")\">" + txtPrevPage + "</a></li>";
	}
	//页码前面的省略号
	if(data.pageObject['start'] > 1) {
		beforepagestad = "<li>...&nbsp;&nbsp;</li>";
	}
	//设置页码
	for(var pageI = data.pageObject['start']; pageI <= data.pageObject['end']; pageI++) {
		if(data.pageObject['pageNum'] == pageI) {
			pagesbutton = pagesbutton + "<li class=\"active\"><a>" + pageI + "</a></li>";
		} else {
			pagesbutton = pagesbutton + "<li><a onclick=\"" + method + "(" + pageI + ")\">" + pageI + "</a></li>";
		}
	}
	//页码后面的省略号
	if(data.pageObject['end'] < data.pageObject['totlePages']) {
		lastpagestad = "<li>...&nbsp;&nbsp;</li>";
	}
	//设置下一页
	if(data.pageObject['pageNum'] < data.pageObject['totlePages']) {
		nextbutton = "<li><a onclick=\"" + method + "(" + (data.pageObject['pageNum'] + 1) + ")\">" + txtNextPage + "</a></li>";
	}
	//设置末页
	if(data.pageObject['totlePages'] == data.pageObject['pageNum']) {
		lastbutton = "<li><a>" + txtEndPage + "</a></li>";
	} else {
		lastbutton = "<li><a onclick=\"" + method + "(" + data.pageObject['totlePages'] + ")\">" + txtEndPage + "</a></li>";
	}
	return "<div class=\"pagingDiv MT20\">" + "<ul class=\"paging\">" + firstbutton //首页
		+
		beforebutton //上一页 
		+
		beforepagestad //页码前面的省略号
		+
		pagesbutton //设置页码
		+
		lastpagestad //页码后面的省略号
		+
		nextbutton //设置下一页
		+
		lastbutton //设置末页
		+
		"<li class=\"noBorder\">&nbsp;&nbsp;共" + data.pageObject['totlePages'] + "页</li>" + "</ul>" + "</div>";
}

function pagingMobile(data, method) {
	var nextbutton = ""; //设置更多
	var txtNextPage = "查看更多";
	//设置更多
	if(data.pageObject['pageNum'] < data.pageObject['totlePages']) {
		nextbutton = "<div class=\"blockC blackBkg checkMoreBtn MT20\"><a class=\"whiteTex font12\" onclick=\"" + method + "(" + (data.pageObject['pageNum'] + 1) + ")\">" + txtNextPage + "</a></div>";
	}
	return nextbutton; //设置下一页
}

//tab选择
var tabSwitchLi = function(obAll, obSwitch) {
	$("." + obAll + "").removeClass("active");
	$("." + obSwitch + "").addClass("active");
}
var tabSwitchCon = function(obAll, obSwitch) {
	$("." + obAll + "").hide();
	$("." + obSwitch + "").show();
}

//展开收缩
var switchInfo = function(hint1, hint2, clickInfo, showInfo) {
	if($("." + clickInfo + "").html() == hint1) {
		$("." + clickInfo + "").html(hint2);
	} else {
		$("." + clickInfo + "").html(hint1);
	}
	$("." + showInfo + "").animate({ height: 'toggle', opacity: 'toggle' }, "400");
}

//弹框 alertBox("提示文字","method"); type = 1 有取消按钮; type = 2 没有取消按钮;
function alertBox(img, imgWidth, firstTip, secondTips, method, type) {
	var html = '';
	html += '<div class="width100 height100P positionF" id="alertBox" style="background:rgba(0,0,0,0.5);left:0;top:0;z-index: 1001;">';
	html += '<div class="radius5 whiteBkg positionA textC" style="padding:30px 50px;left:40%;top:30%;margin-top: -95px; box-shadow: 0px 0px 3px #666;">';
	html += '<h3 class="font20 redTex">提&nbsp;示</h3>';
	html += '<img src="' + img + '" class="MT10" width="' + imgWidth + '">';
	html += '<p class="font14 blackTex MT5">' + firstTip + '</p>';
	html += '<p class="font12 grayTex61 MT5 MB10">' + secondTips + '</p>';
	if(type == 1) {
		html += '<a class="radius5 block inlineB clickBtn width35 ML10 MR5 PTB5 font16 cursor" onclick="$(\'#alertBox\').remove();">取消</a>';
	}
	html += '<a class="radius5 inlineB block clickBtn width35 ML10 MR5 PTB5 font16 cursor" id="okBtn" onclick="' + method + '();">确 定</a>';
	html += '</div></div>';
	$("body").append(html);
}

//字符串转日期格式，strDate要转为日期格式的字符串 
function getDate(strDate) {
	//strDate为需要转换成日期格式的字符串
	if(strDate.replace("/")) {
		strDate = strDate.replace("/", "-").replace("/", "-");
	}
	var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
		function(a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
	return date;
}

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt) { //author: meizz   
	var o = {
		"M+": this.getMonth() + 1, //月份   
		"d+": this.getDate(), //日   
		"h+": this.getHours(), //小时   
		"m+": this.getMinutes(), //分   
		"s+": this.getSeconds(), //秒   
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
		"S": this.getMilliseconds() //毫秒   
	};
	if(/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

//全局重复提交验证数组
var PD = new Array;
Array.prototype.indexOf = function(val) {
	for(var i = 0; i < this.length; i++) {
		if(this[i] == val) return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if(index > -1) {
		this.splice(index, 1);
	}
};

//读取cookie
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if(arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

function setCookie(name, value) {
	var Minutes = 5; //cookie过期时间 分钟
	var exp = new Date();
	exp.setTime(exp.getTime() + Minutes * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
//清除cookie  
function clearCookie(name) {
	setCookie(name, "", -1000);
}