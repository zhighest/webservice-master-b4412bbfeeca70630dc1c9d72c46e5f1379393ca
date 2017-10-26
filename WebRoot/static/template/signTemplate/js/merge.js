//隐藏数字中间, 调用：numHide(13888888888)或numHide('13888888888');
function numHide(m) {
    if(m.length <= 11){
        return m.substr(0, 3) + '****' + m.substr(7);
    }else{
        return m.substr(0, 4) + ' **** **** ' + m.substr(13);
    }
}


//字符串转日期格式，strDate要转为日期格式的字符串 
function getDate(strDate) {
    //strDate为需要转换成日期格式的字符串
    if(strDate.replace("/")){  
        strDate=strDate.replace("/","-").replace("/","-");
    }
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
            function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    return date;
}

// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   { //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  

//金额千分位转换函数（带小数点后两位）
//money 类型为string 或 number, 调用：numFormat(10000)或numFormat('10000');
function numFormat(m) {
    var m2 = parseFloat(m);
    if (isNaN(m2)) {
        return false;
    }
    var num = m2 + ""
    var re = /([0-9]+\.[0-9]{2})[0-9]*/;  
    var m2 = num.replace(re,"$1")
    
    var money = m2.toString();
    var pos_decimal = money.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = money.length;
        money += '.';
    }
    while (money.length <= pos_decimal + 2) {
        money += '0';
    }
    if (typeof money == "number") money = money.toString();
    return money.replace(/^(\d+)((\.\d+)?)$/, function(v1, v2, v3) {
        return v2.replace(/\d{1,3}(?=(\d{3})+$)/g, '$&,') + (v3 || '.00');
    });
}

//金额千分位转换函数（不带小数点后两位）
//money 类型为string 或 number, 调用：numFormat(10000)或numFormat('10000');
function numFormatInteger(m) {
    var m2 = parseFloat(m);
    if (isNaN(m2)) {
        return false;
    }
    var num = m2 + ""
    var re = /([0-9]+\.[0-9]{2})[0-9]*/;  
    var m2 = num.replace(re,"$1")
    
    var money = m2.toString();
    var pos_decimal = money.indexOf('.');
    if (pos_decimal < 0) {
        pos_decimal = money.length;
        money += '.';
    }
    while (money.length <= pos_decimal + 2) {
        money += '0';
    }
    if (typeof money == "number") money = money.toString();
    return money.replace(/^(\d+)((\.\d+)?)$/, function(v1, v2, v3) {
        return v2.replace(/\d{1,3}(?=(\d{3})+$)/g, '$&,');
    });
}

//加法函数
function accAdd(dataOne, dataTwo) {
    var dataOneInt=dataOne.toString().split(".")[0];
    var dataOneFloat="";
    var dataTwoInt=dataTwo.toString().split(".")[0];
    var dataTwoFloat="";
    var lengthOne=0;
    var lengthTwo=0;
    var maxlength=0;
    if(dataOne.toString().split(".").length==2){
        dataOneFloat=dataOne.toString().split(".")[1];
        lengthOne=dataOneFloat.toString().length;
    }
    if(dataTwo.toString().split(".").length==2){
        dataTwoFloat=dataTwo.toString().split(".")[1];
        lengthTwo=dataTwoFloat.toString().length;
    }
    maxLength=Math.max(lengthOne,lengthTwo);
    for(var i=0;i<maxLength-lengthOne;i++){
        dataOneFloat+="0";
    }
    for(var i=0;i<maxLength-lengthTwo;i++){
        dataTwoFloat+="0"; 
    }
    /**
    *对两个数据进行倍数放大
    *使其都变为整数。因为整数计算
    *比较精确。
    */
    var one=dataOneInt+""+dataOneFloat;
    var two=dataTwoInt+""+dataTwoFloat; 
    /**
    *数据扩大倍数后，经计算的到结果，
    *然后在缩小相同的倍数
    *进而得到正确的结果
    */
    var result= (Number(one)+Number(two))/Math.pow(10,maxLength);
    return result;
}

//减法函数
function Subtr(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    //last modify by deeka
    //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

//乘法函数
function accMul(arg1, arg2) {
    var m = 0,
        s1 = arg1.toString(),
        s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    } catch (e) {}
    try {
        m += s2.split(".")[1].length;
    } catch (e) {}
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

//除法函数
function accDiv(arg1, arg2) {
    var t1 = 0,
        t2 = 0,
        r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    } catch (e) {}
    try {
        t2 = arg2.toString().split(".")[1].length;
    } catch (e) {}
    with(Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
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

//加载中
function loadingMessage(str) {
    var html = '<div class="loadingMessage" style="bottom: 100px; position: fixed; text-align: center; width: 100%; display:none; z-index:1000;"><font style="background-color: rgba(0, 0, 0, 0.7); border-radius: 5px; color: #fff; z-index:1000; display: inline-table; line-height: 170%; max-width: 80%; padding: 5px 10px;font-size:14px">'+ str +'</font></div>';
    $("body").append(html);
    $("body .loadingMessage").fadeIn("normal")
}
function hideLoading(){
    $("body .loadingMessage").fadeOut("normal",function(){
        $(this).remove();
    })
}

//报错
function errorMessage(str) {
    if($("body .errorMessage").length > 0){
        return false;
    }
    function hideError(){
        $("body .errorMessage").fadeOut("normal",function(){
            $(this).remove();
        })
    }
    var html = '<div class="errorMessage MT20" style="bottom: 100px; position: fixed; text-align: center; width: 100%; display:none; z-index:1111;"><font style="background-color: rgba(0, 0, 0, 0.7); border-radius: 5px; color: #fff; z-index:1000; display: inline-table; line-height: 170%; max-width: 80%; padding: 5px 10px;">'+ str +'</font></div>';
    $("body").append(html);
    $("body .errorMessage").fadeIn("normal",function(){
        setTimeout(hideError,3000);
    })
} 


//输入框限制
//object 输入框限制的对象，maximum 可输入的最大值，limited 是否限制0开始，true为限制。 maximum与limited可不输入。
//oninput="$.checkLimit($(this),1000,true);" onkeyup="$.checkLimit($(this),1000,true);" onafterpaste="$.checkLimit($(this),1000,true);"
(function($) {
    $.extend({
        checkLimit: function(object, maximum, limited) {
            if (limited == null || limited == "" || !limited) {
                var re = /^[0-9][0-9]{0,}[\.]{0,1}[0-9]{0,2}$/ig;
            } else {
                var re = /^[1-9][0-9]{0,}[\.]{0,1}[0-9]{0,2}$/ig;
            }
            if (object.val() != "") {
                if (!re.test(object.val())) {
                    object.val(object.attr("alt"));
                    return false;
                } else {
                    object.attr("alt", object.val());
                }
            } else {
                object.attr("alt", "");
            }
            if (maximum){
            if (maximum != null || maximum != "") {
                console.log("1:"+Number(object.val())+" / 2:"+Number(maximum))
                if (Number(object.val()) >= Number(maximum)) {
                    object.val(maximum);
                    object.attr("alt",maximum)
                } else if (object.val() < 0) {
                    object.val(0);
                }
            }
            }
        }
    });
})(jQuery);

var PD = new Array; //全局重复提交验证数组
Array.prototype.indexOf = function(val) {              
    for (var i = 0; i < this.length; i++) {  
        if (this[i] == val) return i;  
    }  
    return -1;  
};  
Array.prototype.remove = function(val) {  
    var index = this.indexOf(val);  
    if (index > -1) {  
        this.splice(index, 1);  
    }  
};

//接口函数
function ajaxRequest(url, str, method, type) {
    
    for (var i = 0; i < PD.length; i++) {  
        if (PD[i] == url){
            //errorMessage("请勿重复请求");
            return false;
        }
    }
    PD.push(url);
    
    var typeValue;
    if(type==null || type==""){ 
        typeValue = "POST";
    }else{
        typeValue = type;
    }
    loadingMessage("数据加载中...");
    // loadingMessage('<img src="'+contextPath+'pic/weixin/loading.gif" width=25px>');
    $.ajax({
        url: url,
        type: typeValue,
        dataType:"json",
        // data: port.encrypt(str),
        data: str,
        success: function(data) {
            //method(port.decrypt(data));
            PD.remove(url);
            hideLoading();
            method(data);
        },
        error: function() {
            PD.remove(url);
            hideLoading();
            //errorMessage("请求失败!");
        }
    });
};

//分页函数
//data格式如下：pageObject":{"end":5,"pageNum":2,"pageSize":178,"start":1,"totlePages":18,"totleSize":0};
function pagingMobile(data, method) {
    
    var nextbutton = ""; //设置更多
    var txtNextPage = "";
    //设置更多
    if (data.pageObject['pageNum'] < data.pageObject['totlePages']) {
        nextbutton = "<li><a id=\"moreBtn\" onclick=\""+method+"(" + (data.pageObject['pageNum'] + 1) + ");\">" + txtNextPage + "</a></li>";
    }else{
        nextbutton = "<li><a id=\"moreBtn\" onclick=\"errorMessage('没有更多了')\">" + txtNextPage + "</a></li>";
    }
    $(window).scroll(function(){
        if($(document).scrollTop() >= $(document).height()-$(window).height()){
            $("#moreBtn").click();
        };
    });
    return "<div class=\"pagingDiv MT20\">" + "<ul class=\"paging\">"
        + nextbutton //设置下一页
        + "</ul>" + "</div>";
}

//弹框 alertBox("提示文字","method"); type = 1 有取消按钮; type = 2 没有取消按钮;
function alertBox(tips,method,type){
    var html='';
    html += '<div class="width100 height100P positionF" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">';
    html += '<div class="radius5 whiteBkg width80 positionA textC" style="height:190px;left:10%;top:50%;margin-top: -95px; box-shadow: 0px 0px 3px #666;">';
    html += '<h3 class="font20 PT15 redTex">提&nbsp;示</h3>';
    html += '<p class="font16 blackTex lineHeight1_5x" style="height:75px;padding:15px 5% 0;">'+tips+'</p>';
    if(type == 1){
        html += '<a class="inlineB grayBkg width35 ML10 MR10 grayTex PT5 PB5 font16" onclick="$(\'#alertBox\').remove();">取消</a>';
    }
    html += '<a class="inlineB redBkg width35 ML10 MR10 whiteTex PT5 PB5 font16" onclick="'+method+'();">确 定</a>';
    html += '</div></div>';
    $("body").append(html);
}
//弹框 alertBox("提示文字","method"); type = 1 有两个按钮; type = 2 没有取消按钮;
function alertBox1(tips,method1,tipsMethod1,method2,tipsMethod2,type){
    var html='';
    html += '<div class="width100 height100P positionF alertBoxWrap" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">';
    html += '<div class="radius5 whiteBkg width80 positionA textC" style="height:190px;left:10%;top:50%;margin-top: -95px; box-shadow: 0px 0px 3px #666;">';
    html += '<h3 class="font20 PT15 redTex">提&nbsp;示</h3>';
    html += '<p class="font16 blackTex lineHeight1_5x" style="height:75px;padding:15px 5% 0;">'+tips+'</p>';
    if(type == 1){
        html += '<a class="inlineB grayBkg width35 ML10 MR10 grayTex PT5 PB5 font16" id="alertBtn1" onclick="'+method1+'();">'+tipsMethod1+'</a>';
    }
    html += '<a class="inlineB redBkg width35 ML10 MR10 whiteTex PT5 PB5 font16" onclick="'+method2+'();">'+tipsMethod2+'</a>';
    html += '</div></div>';
    $("body").append(html);
} 

//tab选择 每个单独的tab上写 onclick="tabSwitchLi('obAll','obSwitch');tabSwitchCon('obAll','obSwitch') 样式里ul的class叫tabSwitch"
var tabSwitchLi = function(obAll,obSwitch){
    $("."+obAll+"").removeClass("current");
    $("."+obSwitch+"").addClass("current");
}
var tabSwitchCon = function(obAll,obSwitch){
    $("."+obAll+"").hide();
    $("."+obSwitch+"").show();
}
//展开收缩，并且指示小图标随展开收缩改变方向
var switchBtn = function(clickInfo,showInfo){
    $("."+showInfo+"").animate({height: 'toggle', opacity: 'toggle'}, "100");
    $("."+clickInfo+"").children('.img').toggleClass('turnAround');
}

//展开收缩 有文字提示信息
var switchInfo = function(hint1,hint2,clickInfo,showInfo){
    if($("."+clickInfo+"").html()== hint1){
        $("."+clickInfo+"").html(hint2);
    }else {
        $("."+clickInfo+"").html(hint1);
    }
    $("."+showInfo+"").animate({height: 'toggle', opacity: 'toggle'}, "400");
}
//显示字符串的后四位，用于显示手机尾号，银行卡尾号等地方
function numLast(m) {
    var n = m.toString();//将号码字符串化
    return n.substr(n.length-4);//取字符串最后四位数字
}
//判断字符串中的数值是否为一位，若为一位数字则为其添加.0
       function  formatNum(str){
       var num=[];
       var str=str.toString();
         var reg = /\d+(\.\d{1,2})?/g;
          str=str.match(reg);
          $.each(str,function(i,obj){
              if(Number(obj)<10 && Number(obj) == parseInt(Number(obj))&& obj.indexOf('.')==-1){
                obj=obj+'.0';
                num.push(obj)
              }else{
                num.push(obj)
              }
          })
          if(num.length==1){
            return num;
          }else if(num.length==2){
             return num[0]+'~'+num[1]
          }
         
    }
       
//截取http://  或者 https://
function getUrlHttp(){
	var curUrl=window.location.href;
	if(curUrl.indexOf("http://") != -1){
		http = 'http://';
	}else if(curUrl.indexOf("https://") != -1){
		http = 'https://';
	}else{
		return false;
	}
	return http;
}

//获取url中的参数   传入参数名name  
 function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null) return unescape(r[2]); return null; //返回参数值
 }
 