//路由都没配的情况下执行
function noRouter(){
    if(top.location!==self.location){
        window.parent.freshAccountBalance();
    } else{
        window.location.href=contextPath+"wxtrade/goMyAsset"
    }
}
//跳转到实名认证页面
function idcardValidate(){
    window.location.href = contextPath + "wxtrade/goAuthentication";
}
//其他页面调到充值时刷新金额
function freshFn(money){
    $("#moneyOrder").val(money)
    var a=new topUp();
    a.init();
    a.afterTheTopUpAmount();

}
//以上全局函数是为了iframe时调用以及alerBox调用
//后台报错信息有的写zai resmsg有的写在resmsg_cn，优先报resmsg_cn
function resmsg_cn(data){
    if(data.resmsg_cn){
        return data.resmsg_cn
    }else{
        return data.resmsg
    }
}
//请求接口取出数据----客服电话
var PhoneCall;

$(document).ready(function(){
    if(top.location==self.location){
        var a= new topUp();
        a.init();
        a.afterTheTopUpAmount();
    }
    //请求接口取出数据----客服电话
	function getServiceCall(){
		ajaxRequest(contextPath + "/base/getMetaInfo",{'name':'customerServiceMobile'},function(res){
			if(res.rescode=='00000'){
				PhoneCall = res.content.mobile;
			}
		});
	}
	getServiceCall();
    
    
})
function topUp(){

}
$.extend(topUp.prototype,{
    init:function(options){
        this.options={
            userId        :'#userId',
            mobile        :'#mobile',
            bankCardNumber:'#bankCardNumber',
            acctBalance   :'#acctBalance',
            payFlag       :'#payFlag',
            moneyOrder    :"#moneyOrder",
            bankList      :"#bankList",
            inactiveBtn   :"#inactiveBtn",
            idcardValidate:"#idcardValidate",
            bankAccountNo :"#bankAccountNo",
            rescode       :"#rescode",
            resmsg_cn     :"#resmsg_cn",
            resmsg        :"#resmsg"
        }
        this.target="";
        this.onOff=true;
        $.extend(true,this.options,options||{});
        this.initDom();
        this.idcardValidateFn();
        this.CardNumber();
        this.initKeyBorder();
        this.acctBalance();
        this.getBankList();
        this.bankAccountNoFn();

    },
    //获取隐藏域value值
    initDom:function(){
        var opts=this.options;
        this.userId=$(opts.userId).val();
        this.mobile=$(opts.mobile).val();
        this.bankCardNumber=$(opts.bankCardNumber).val();
        this.bankAccountNo=$(opts.bankAccountNo).val();
        this.$acctBalance=$(opts.acctBalance).val();
        this.payFlag=$(opts.payFlag).val();
        this.moneyOrder=$(opts.moneyOrder).val();
        this.inactiveBtn=$(opts.inactiveBtn);
        this.bankList=$(opts.bankList);
        this.idcardValidate=$(opts.idcardValidate).val();
        this.rescode=$(opts.rescode).val();
        this.resmsg_cn=$(opts.resmsg_cn).val();
        this.resmsg=$(opts.resmsg).val();
        $(opts.bankAccountNo).val( this.bankCardNumber);
    },
    //充值余额
    acctBalance:function(){
        var self=this;
        if(self.$acctBalance!=""&&self.$acctBalance!=null){
            $("#afterTheTopUpAmount").html(numFormat(self.$acctBalance));
        }
    },
    //充值后金额
    afterTheTopUpAmount:function () {
        var self=this;
        var topUpAmount = $("#moneyOrder").val();
        var acctBalance=$("#acctBalance").val();
        var bankAccountNo=$("#bankAccountNo").val();
        if(topUpAmount == "" ||topUpAmount == null||topUpAmount == 0||bankAccountNo==""){
            $("#inactiveBtn").removeClass("changeBkgColor").addClass("inactiveBtn");
            $("#inactiveBtn").off("click")
            $("#inactiveBtn").click(function(){
                // errorMessage("请输入充值金额");
            });
        }else{
            $("#inactiveBtn").removeClass("inactiveBtn").addClass("changeBkgColor");
            $("#inactiveBtn").off("click")
            $("#inactiveBtn").on("click",function() {
                self.singlePay();
            });
        }
        $("#afterTheTopUpAmount").html(numFormat(accAdd(topUpAmount,acctBalance)));
    },
    bankAccountNoFn:function(){
        var self=this;
        $("#bankAccountNo").bind('input propertychange', function() {
            self.afterTheTopUpAmount()
        });
    },
    //是否实名认证
    idcardValidateFn:function(){
        if(this.rescode=="00000"){

        }else if(this.rescode=="00004"){//00004表示路由未开
            if(this.resmsg_cn){
                alertBox(this.resmsg_cn,"noRouter",2);
            }else{
                alertBox(this.resmsg,"noRouter",2);
            }
        }else if(this.rescode=="00008"){//未认证时后台统一传00008
            alertBox("请先实名认证","idcardValidate",2);
        }
    },
    //后台给的ajax方法，请求方式与第三方有关，需要后台返回方法
    singlePay:function(){
        var userId = this.userId;
        var bankCardNumber = this.bankCardNumber;
        var amount = $("#moneyOrder").val();
        var mobile = this.mobile;
        var payFlag=this.payFlag;
        var url = contextPath+"/wxpay/singlePay";
        loadingMessage('数据加载中...');
        $.ajax({    type : "POST",
            url : url,
            data : "userId=" + userId + "&bankCardNumber=" + bankCardNumber + "&tradeAmount=" + amount + "&mobile=" + mobile+"&payFlag="+payFlag,
            dataType : "json",
            success : function(data) {
                var rescode = data.rescode;
                var resmsg = resmsg_cn(data);
                var payObj = data.payObj;
                var method = data.method;
                var sendUrl = data.sendUrl;
                hideLoading();
                if (rescode != "00000") {
                    errorMessage(resmsg);
                    return;
                }
                if (method == "POST") {
                    var jsonObj = $.parseJSON(payObj);
                    $("#singlePayForm").attr("action", sendUrl);
                    $.each(jsonObj,function(key, value) {
                        //循环添加input的值
                        var param = "<input type='hidden' value='"+value+"' name='"+key+"'/>";
                        $("#params").append(param);
                    });
                    $("#singlePayForm").submit();
                }else{
                    window.location.href = sendUrl;
                }
            },
            error : function(data) {
                hideLoading();
            }
        });
    },
    //判断是否绑定银行卡
    CardNumber:function (){
        var self=this;
        if(this.bankCardNumber==null||this.bankCardNumber==""){
            $("#noHasCard").show();
            $("#hasCard").hide();
            $(".main").click(function(e) {
                var target = $(e.target);
                    if(!self.onOff&&!target.is('#bankAccountNo')){
                        self.checkBankInfo();
                        self.onOff=true;
                    }
            })
        }else{
            $("#noHasCard").hide();
            $("#hasCard").show();
            this.bankCardNumber=this.bankCardNumber.toString();
            $("#cardNumber").html(this.bankCardNumber.substr(self.bankCardNumber.length-4));
        }
    },
    //未绑定银行卡时、绑定银行卡时将银行卡信息传给后台
    checkBankInfo:function(){
        var self=this;
        this.bankCardNumber=$(this.options.bankAccountNo).val();
        var bankCardNumber=this.bankCardNumber;
        ajaxRequest(contextPath + "/wxpay/queryCardAllInfo","userId="+self.userId+"&mobile="+self.mobile+"&cardNumber="+bankCardNumber,callBackBankInfo)
        function callBackBankInfo(data){
            if(data.rescode=="00000"){
            }else if(data.rescode=="00004"){//00004表示路由未开
                alertBox(resmsg_cn(data),"noRouter",2);
            }else{
                errorMessage(resmsg_cn(data))
            }
        }
    },
    //获取银行列表
    getBankList:function(){
        var self=this;
        this.bankList.hide();
        this.showBankList();
        ajaxRequest(contextPath + "wxtrade/queryRouteBankList", "", callBackBankList,"GET");
        function callBackBankList(data){
            self.setBankList(data)
        }
    },
    //生成银行列表
    setBankList :function(data){
        var html = ""; //拼接html
        $.each(data.bankList,
            function(i, jsonObj) {
                html += '<li class="positionR whiteBkg clearfix thisLi borderB">';
                html += '<div class="fl block imgBox">';
                html += '<img src="'+ contextPath +jsonObj.picture + '"></div>';
                if (i==data.bankList.length-1) {
                    html += '<div class="fl  rightBox haveBorder bankListItem"><div class="PL5 rightMain">';
                }else{
                    html += '<div class="fl  rightBox haveBorder bankListItem "><div class="PL5 rightMain">';
                }
                html += '<p class="font14 bankName strong">' + jsonObj.bankName + '</p>';
                html += '<div class="font12 grayTex"><span>单笔:</span><span>'+ jsonObj.singleLimit +'元</span><span class="ML5">单日:</span><span>'+ jsonObj.singleDayLimit +'元</span><span class="ML5">单月:</span><span>'+ jsonObj.singleMonthLimit +'元</span></div>';
                html += '</div></div></li>';
            });
        html +='<p class="whiteBkg  textC lineHeigt20 grayTex font12 PT10  thisLi">*以上详情仅供参考,实际以提示支付界面为准</br>如有疑问,请联系客服'+PhoneCall+'</p>';
        $("#allBanks").append(html);
        // var allWidth= window.screen.availWidth; //手机分辨率的宽

    },
    //查看银行列表
    showBankList:function(){
        // 点击银行的输入框弹出银行列表
        $("#checkBankList").click(function() {
            $("#bankList").addClass('show');
        });
        $("#return").click(function() {
            $("#bankList").removeClass('show');
        });
    },
    //键盘初始化
    initKeyBorder:function(){
        var self=this;
        $(".main").click(function(e) {
            var target = $(e.target);
            if(!target.is('.keyBord')) {//如果点击事件是#inputAmount
                $("#momeyBox").removeClass("bottom");
            }else{
                $("#momeyBox").removeClass("none").addClass("bottom");
                if(target.attr("id")=="moneyOrder"){
                    self.target=$("#moneyOrder");
                    self.onOff=true;
                }else if(target.attr("id")=="bankAccountNo"){
                    self.target=$("#bankAccountNo");
                    self.onOff=false;
                }
            }

        });
        this.keyBorder()
    },
    //自定义键盘
    keyBorder:function(){
        var self=this;
        var xFunction;
        var input = function(price) { // 数字初始化
            var num =self.target.val() + price;
            self.target.val(num);
            if(self.onOff){
                $.checkLimit(self.target,self.target.attr("minNumber"),false);
            }
        }
        var inputNumber = function(price) { //显示输入数字
            if (price == "reture") {
                var num =self.target.val();
                num = num.substring(0,num.length-1);
                self.target.val(num);
                if(self.onOff){
                    $.checkLimit(self.target,self.target.attr("minNumber"),false);
                }
           }
            if (price == ".") {
                input(price)
            }
            if (price >= 0 && price <= 9) {
                input(price)
            }
        }
        $$("#numberKeyboard li:not(.not)").tap(function() {
            inputNumber($(this).attr("keyboard"));
            if(self.onOff){
                var moneyOrder=self.target.val(); //判断输入金额是否大于可转金额
                var reg=/^[0].$/g;
                if(reg.test(moneyOrder)){
                    moneyOrder=moneyOrder.replace('0','0.')
                    self.target.val(moneyOrder);
                }
                self.afterTheTopUpAmount();
            }
            self.afterTheTopUpAmount();
        });
        $$("#keyboardReturn").tap(function() {
            inputNumber($(this).attr("keyboard"));
                self.afterTheTopUpAmount()
        });
        $$("#keyboardPoint").tap(function() {
            inputNumber($(this).attr("keyboard"));
                self.afterTheTopUpAmount()
        })
    }
})


//自定义键盘   点击键盘确认键
$("#keyboardConfirm").click(function(){
    $("#momeyBox").removeClass("bottom");
}); 