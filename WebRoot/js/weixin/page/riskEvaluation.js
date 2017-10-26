$(function () {
    var a = new evaluate();

})
function evaluate(options) {//
    this.answer = [];//存放答案的数组
    this.answerString; //答案以字符串方式传递
    this.val = '';
    this.onOff = false;
    this.arr = data;//题库
    this.time;
    this.n = -1;
    this.m = 3;
    this.init();
    this.createList();
    $.extend(this.settings, options || {});
}
$.extend(evaluate.prototype, {
    init: function () {
        this.settings = {
            nextBtn: "#nextBtn",
            preBtn: "#preBtn",
            surveyWrap: ".surveyWrap",
        }
        this.mobile = $("#mobile").val();
        this.channel = $("#channel").val();
        this.flag = $("#flag").val();
        this.userId = $("#userId").val();
        this.initDom();
        this.nextBtnFn();
        this.preBtnFn();
    },
    initDom: function () {
        this.nextBtn = $(this.settings.nextBtn);
        this.preBtn = $(this.settings.preBtn);
        this.surveyWrap = $(this.settings.surveyWrap)
    },
    createList: function () {
        var length = this.arr.length;//题目总数
        $("#totalPage").html(length);//显示总页面
        this.oUl = $("<ul>");
        this.oUl.attr("id", "listWrap");
        for (var i = 0, l = length; i < length; i++, l--) {
            var oLi = $("<li>");
            oLi.attr("id", "page" + i);
            oLi.css({
                "top": ((i + 1) * 15),
                "width": +(85 - (i) * 6) + "%",
                "left": ((i + 1) * 2.5) + "%",
                "zIndex": l
            });
            oLi.addClass("rotate2");
            if (i >= 3) {
                oLi.addClass("none");//让屏幕上始终只显示三个问卷
            }
            oLi.html('<dl class="contentWrap"><dt class="title font14 positionR">' + this.arr[i].question + '</dt><dd value="A">' + this.arr[i].A + '</dd> <dd value="B">' + this.arr[i].B + '</dd><dd value="C">' + this.arr[i].C + '</dd><dd value="D">' + this.arr[i].D + '</dd><dd value="E">' + this.arr[i].E + '</dd></dl>');
            this.oUl.append(oLi);

        }
        this.surveyWrap.append(this.oUl);
        var self = this;
        $('li').find("dd").on("touch", function () {
            self.nextBtn.removeClass("bkg2").addClass("redBkg")//改变按钮颜色
            self.val = $(this).attr('value');
            $(this).removeClass("grayTex").addClass("redTex").siblings().removeClass("redTex").addClass("grayTex");//改变文字颜色
            self.time = setTimeout(function () {
                self.onOff = true;//不做选择无法跳到下一题
            }, 500)
            if (self.n >= length - 2) {
                self.nextBtn.html("提交");
                self.nextBtn.click(function () {
                    ajaxRequest(contextPath + "wxtrade/queryRiskEvaluation", "mobile=" + self.mobile + "&channel=" + self.channel + "&userId=" + self.userId + "&answer=" + self.answerString, submitAnswer, "GET")
                })
            }
        })
        this.nextBtn.removeClass("redBkg").addClass("bkg2");

        this.oLi = $(this.oUl).find("li");
        var width = this.oLi.eq(0).width();
        $('.contentWrap').css("width", width);
        $("#currentPage").html(1)//显示当前页面

        function submitAnswer(data) {
            if (data.rescode == "00000") {
                var riskType = data.riskType;
                var declare = data.declare;
                self.submitFn(riskType, declare);
            } else {
                errorMessage(data.resmsg_cn)
            }
        }

        this.touchEventFn();//启用手滑方法
    },
    nextFn: function () {
        this.n++;
        var length = this.arr.length;
        if (this.n >= length - 2) {
            this.n = length - 2;
        }
        for (var i = 0, l = length; i < length; i++, l--) {
            this.oLi.eq(i).css({
                "top": ((i - this.n) * 15),
                "width": (85 - (i - this.n - 1) * 6) + "%",
                "left": ((i - this.n) * 2.5) + "%",
                "zIndex": l
            });
        }
        this.nextBtn.removeClass("redBkg").addClass("bkg2")
        $("#currentPage").html(this.n + 2)//显示当前页面
        this.oLi.eq(this.n).addClass("rotate").removeClass("rotate2");
        this.oLi.eq(this.m).removeClass("none");
        this.m >= length - 1 ? this.m = length - 1 : this.m++;
    },
    preFn: function () {
        var length = this.arr.length;
        this.m <= 3 ? this.m = 3 : this.m--;
        for (var i = 0, l = length; i < length; i++, l--) {
            if (this.n <= 0) {
                this.n = 0;
            }
            this.oLi.eq(this.n).addClass("rotate2").removeClass("rotate");
            this.oLi.eq(i).css({
                "top": ((i - this.n + 1) * 15),
                "width": (85 - (i - this.n) * 6) + "%",
                "left": ((i - this.n + 1) * 2.5) + "%",
                "zIndex": l
            })
            if (i >= this.n + 3) {
                this.oLi.eq(i).addClass("none")
            }
        }
        this.n--;
    },
    nextBtnFn: function () {
        var self = this;
        var length = this.arr.length;
        this.nextBtn.click(function () {
            if (self.onOff) {
                self.onOff = false
                if (self.n < length - 1) {
                    clearTimeout(self.time)
                    self.answer.push(self.val);
                    self.answerString = self.answer.join('|')
                    self.nextFn();
                }
            }
        })
    },
    preBtnFn: function () {
        var self = this;
        this.preBtn.click(function () {
            self.preFn()
        })
    },
    touchEventFn: function () {
        var self = this;
        $(this.oUl).on("touchstart", touchstartFn);
        function touchstartFn(event) {
            event.preventDefault();
            var offSetLeft = self.oUl.offset().left
            var touch = event.originalEvent.changedTouches[0];
            self.disx = touch.pageX - offSetLeft;

            self.oldX = 0;
            // self.oldY=0;
            $(document).on("touchmove", touchmoveFn);
            $(document).on("touchend", touchendFn);
            clearInterval(self.timer);
        };
        function touchmoveFn(event) {
            var touch = event.originalEvent.changedTouches[0];
            self.x = touch.pageX - self.disx;
            // self.y=touch.pageY-self.disy;
        };
        function touchendFn(event) {
            event.preventDefault();
            // if(self.x<0&&Math.abs(self.x)>50){
            //   self.preBtn.trigger("click")
            // }
            if (self.x > 0 && Math.abs(self.x) > 50) {
                self.nextBtn.trigger("click");
                self.x = 0
            }
            $(document).off("touchmove", touchmoveFn);
            $(document).off("touchend", touchendFn);
        }
    },
    submitFn: function (riskType, declare) {//数据提交到下一个页面
        var temp = document.createElement("form");
        temp.action = "goRiskEvaluationResult";
        temp.method = "POST";
        var inp1 = document.createElement("input");
        inp1.name = "riskType";
        inp1.value = riskType;
        temp.appendChild(inp1);
        var inp2 = document.createElement("input");
        inp2.name = "declare";
        inp2.value = declare;
        temp.appendChild(inp2);
        var inp3 = document.createElement("input");
        inp3.name = "channel";
        inp3.value = this.channel;
        temp.appendChild(inp3);
        var inp4 = document.createElement("input");
        inp4.name = "flag";
        inp4.value = this.flag;
        temp.appendChild(inp4);
        var inp5 = document.createElement("input");
        inp5.name = "mobile";
        inp5.value = this.mobile;
        temp.appendChild(inp5);
        var inp6 = document.createElement("input");
        inp6.name = "userId";
        inp6.value = this.userId;
        temp.appendChild(inp6);
        document.getElementById("formDiv").appendChild(temp);
        temp.submit();
    }
})
var data = [
    {"question": "1.您现在的年龄:", "A": "A 60岁以上", "B": " B 46-60", "C": "C 36-45", "D": "D 26-35", "E": "E 25岁以下"},
    {
        "question": "2.您的家庭年收入状况：",
        "A": "A 10万元以下",
        "B": "B 10万元-20万元",
        "C": "C 20万元-30万元",
        "D": "D 30万元-40万元",
        "E": "E 40万元以上"
    },
    {
        "question": "3. 是否有过金融投资（包含股票、基金、债券、外汇、金融衍生品等）的经历？",
        "A": "A 没有",
        "B": "B 有，少于3年",
        "C": "C 有，3年-5年",
        "D": "D 有，5年-10年",
        "E": "E 有，10年以上"
    },
    {
        "question": "4. 在您每年的家庭收入中，可用于金融投资（储蓄存款除外）的比例为？",
        "A": "A 5%以下",
        "B": " B 5%-10%",
        "C": "C 10%-25%",
        "D": "D 25%-50%",
        "E": "E 50%以上"
    },
    {"question": "5. 您的预期投资期限是？", "A": "A 3个月以下", "B": " B 3个月-6个月", "C": "C 6个月-1年", "D": "D 1年-3年", "E": "E 3年以上"},
    {
        "question": "6. 您投资的主要目的是什么？选择最符合您的一个描述",
        "A": "A 关心长期的高回报，能够接受短期的资产价值波动",
        "B": " B 倾向长期的成长，较少关心短期的回报以及波动",
        "C": "C 希望投资能获得一定的增值，同时获得波动适度的年回报",
        "D": "D 只想确保资产的安全性，同时希望能够得到固定的收益",
        "E": "E 希望利用投资以及投资所获得的收益在短期内用于大额的购买计划"
    },
    {
        "question": "7. 您如何理解风险？",
        "A": "A 不了解什么是风险",
        "B": "B 风险就是损失",
        "C": " C 风险代表不确定性，可能带来损失，也可能带来收益",
        "D": "D 风险代表机会，可能带来收益",
        "E": "E 风险代表巨大的额外收益机会"
    },
    {
        "question": "8. 如果您拥有50万用来建立资产组合，您会选择下面哪一个比重组合？",
        "A": "A低风险投资（80%）、一般风险投资（15%）、高风险投资（5%）",
        "B": "B低风险投资（60%）、一般风险投资（30%）、高风险投资（10%）",
        "C": "C低风险投资（30%）、一般风险投资（40%）、高风险投资（30%）",
        "D": "D低风险投资（10%）、一般风险投资（30%）、高风险投资（60%）",
        "E": "E低风险投资（5%）、一般风险投资（15%）、高风险投资（80%）"
    },
    {
        "question": "9. 当您进行投资时，您能接受一年内损失多少？",
        "A": "A 几乎不能承受任何亏损",
        "B": "B 最多只能承受5%以下的亏损",
        "C": "C 最多只能承受5%-10%的亏损",
        "D": "D 最多只能承受10%-20%的亏损",
        "E": "E 可以承受25%以上的亏损"
    },
    {"question": "10. 对您而言，保本比追求高收益更为重要？", "A": "A 非常同意", "B": "B 同意", "C": "C 无所谓", "D": "D 不同意", "E": "E 非常不同意"}
]