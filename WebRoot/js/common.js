
/**
 * Created by yanfl on 2015/1/19.
 */
(function($){
    $.extend({
        /**
         * 金额千分位转换,保留两位小数
         * @param money
         * 调用：$.format(10000)或$.format('10000');
         * @returns {string}
         */
        format: function(/**string|number**/money){
            if(typeof money=="number") money = money.toString();
            return money.replace(/^(\d+)((\.\d+)?)$/, function(v1, v2, v3) {
                return v2.replace(/\d{1,3}(?=(\d{3})+$)/g, '$&,') + (v3 || '.00');
            });
        },

        /**
         * 计算两日期相隔多少天
         * @param startDay 格式：2015-02-28
         * @param endDay 格式2015-02-28
         * 调用$.getDay('2015-02-28','2015-02-29');
         * @returns {Number}
         */
        getDay: function(/**string**/startDay,/**string**/endDay){
            var startTime = (new Date(startDay)).getTime();//传过来的开始时间转换为毫秒
            var endTime = (new Date(endDay)).getTime();
            return parseInt(Math.abs(startTime - endTime ) / 1000 / 60 / 60 /24);
        },

        /**
         * 截取小数点后两位
         * @param money
         * @returns {string}
         */
        substr2Point: function (/**string|number**/money){
            if(typeof money=="number") money = money.toString();
            if(money.startWith("-")) money = money.substring(1);
            var s = /^[0-9]{0,}[\.]{0,1}[0-9]{2,}$/ig;
            var index = money.indexOf(".");
            if(index<0){
                money+=".00";
                return money;
            }
            else{
                if(s.test(money)){
                    money = money.substring(0,money.indexOf(".")+3);
                    return money;
                }
                else{
                    money = money+"0";
                    return money;
                }
            }
        }
    });
})(jQuery);


/**
 * function bind
 * @returns {Function}
 */
Function.prototype.bind = function() {
    var __method = this;
    var args = Array.prototype.slice.call(arguments);
    var object=args.shift();
    return function() {
        return __method.apply(object,
            args.concat(Array.prototype.slice.call(arguments)));
    }
};

String.prototype.startWith = function(str){
    return (this.match("^"+str)==str);
};

String.prototype.endWith = function(str){
    return (this.match(str+"$")==str);
};


/**
 * 加减乘除运算，解决小数、除法精度问题
 */
//加法函数
Number.prototype.plus = function(arg) {
    var a = new BigDecimal(this.toString());
    var b = new BigDecimal(arg.toString());
    return parseFloat(a.add(b).toString());//return number
};

//减法函数
Number.prototype.sub = function(arg) {
    var a = new BigDecimal(this.toString());
    var b = new BigDecimal(arg.toString());
    return parseFloat(a.subtract(b).toString());//return number
};

//乘法函数
Number.prototype.mul = function(arg) {
    var a = new BigDecimal(this.toString());
    var b = new BigDecimal(arg.toString());
    return parseFloat(a.multiply(b).toString()); //return number
};
/**
 * @param arg 除数
 * @param n 保留几位小数
 * @param type 若等于2代表四舍五入；1代表截取，不做四舍五入；默认等于1
 * @returns {Number}
 */
Number.prototype.acc = function(/**number**/arg,/**number**/n,/**number**/type) {
    type  = type?type:1;
    var a = new BigDecimal(this.toString());
    var b = new BigDecimal(arg.toString());
    return parseFloat(a.divide(b,n,type).toString()); //
};

var Event = {
    animation:{
        eventName:"animationend webkitAnimationEnd mozAnimationEnd MSAnimationEnd animationEnd",
        onStart: function(dom,fn){
           $(dom).one(this.eventName, function (e) {
               fn(e);
           });
        },
        onEnd:function(dom,fn){
            $(dom).one(this.eventName, function (e) {
                fn(e);
            });
        },
        onIteration: function(dom,fn){
            $(dom).one(this.eventName, function (e) {
                fn(e);
            });
        }
    },
    
    transition:{
        eventName:"transitionend webkitTransitionEnd mozTransitionEnd MSTransitionEnd transitionEnd",
        onStart: function(dom,fn){
            $(dom).one(this.eventName, function (e) {
                fn(e);
            });
        },
        onEnd:function(dom,fn){
            $(dom).one(this.eventName, function (e) {
                fn(e);
            });
        },
        onIteration: function(dom,fn){
            $(dom).one(this.eventName, function (e) {
                fn(e);
            });
        }
    }
};

var IE = {
    is9: function(){
        if(navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 9.0")>0) return true;
        return false;
    },
    is8: function(){
        if(navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 8.0")>0) return true;
        return false;
    },
    is7: function(){
        if(navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 7.0")>0) return true;
        return false;
    },
    is6: function(){
        if(navigator.userAgent.indexOf("MSIE")>0 && navigator.userAgent.indexOf("MSIE 6.0")>0) return true;
        return false;
    },
    is678: function(){
        if(this.is6() || this.is7() || this.is8()) return true;
        return false
    }
};
