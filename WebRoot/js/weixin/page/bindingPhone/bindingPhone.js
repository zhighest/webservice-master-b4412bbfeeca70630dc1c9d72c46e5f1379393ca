var selectContactDom = $('.city');
var showContactDom = $('#show_contact');
var contactProvinceCodeDom = $('#contact_province_code');
var contactCityCodeDom = $('#contact_city_code');
var gainCodeSwitch = true;//重发验证码开关
var province, city;
selectContactDom.on('click', function () {
    var sccode = showContactDom.attr('data-city-code');
    var scname = showContactDom.attr('data-city-name');

    var oneLevelId = showContactDom.attr('data-province-code');
    var twoLevelId = showContactDom.attr('data-city-code');
    //var threeLevelId = showContactDom.attr('data-district-code');
    /*插件用的是iosSelect*/
    var iosSelect = new IosSelect(2, [iosProvinces, iosCitys /*, iosCountys*/], {
        title: '选择城市',
        itemHeight: 35,
        relation: [1, 0, 0, 0],
        oneLevelId: oneLevelId,
        twoLevelId: twoLevelId,
        //threeLevelId: threeLevelId,
        callback: function (selectOneObj, selectTwoObj /*, selectThreeObj*/) {
            contactProvinceCodeDom.val(selectOneObj.id);
            contactProvinceCodeDom.attr('data-province-name', selectOneObj.value);
            contactCityCodeDom.val(selectTwoObj.id);
            contactCityCodeDom.attr('data-city-name', selectTwoObj.value);

            showContactDom.attr('data-province-code', selectOneObj.id);
            showContactDom.attr('data-city-code', selectTwoObj.id);
            //showContactDom.attr('data-district-code', selectThreeObj.id);
            showContactDom.html(selectOneObj.value + '' + selectTwoObj.value /*+ ' ' + selectThreeObj.value*/);
            province = selectOneObj.value;
            city = selectTwoObj.value;
            showContactDom.css({
                'color': '#333333',
            });
        }
    });
});

function checkPhoneNo() {
    var phoneNum = $("#phoneNumber").val();
    if (phoneNum == "") {
        errorMessage("请输入手机号");
        return false;
    }
    if (!(/^((13|15|18|14|17)[0-9]{9})$/.test(phoneNum))) {
        errorMessage("请输入正确的手机号码");
        return false;
    }
    return true;
}

$('#codeButton').on('click', function () {
    var phoneNoState = checkPhoneNo();
    var phoneNum = $("#phoneNumber").val();
    var token_id = $("#token_id").val();
    if (!phoneNoState) {
        return;
    }
    if (!gainCodeSwitch) {
        return;
    }
    getCaptchaCb();
    errorMessage("验证码发送成功");
    ajaxRequest(contextPath + "wxPush/getCaptcha", "phoneNum=" + phoneNum + "&token_id=" + token_id, function (data) {
    });
});

var getCaptchaCb = function () {
    var t = 60;
    gainCodeSwitch = false;
    $('#codeButton').attr('disabled', 'disabled');
    var timer = setInterval(function () {
        t--;
        $('#codeButton').val(t + 's');
        if (t <= 0) {
            clearInterval(timer);
            gainCodeSwitch = true;
            $('#codeButton').val('重新获取');
            $('#codeButton').removeAttr('disabled');
        }
    }, 1000)
}

var submitCb = function (res) {
    console.log(res);
    if (res.rescode == "00000") {
        errorMessage(res.resmsg);
        window.location.href = contextPath + "/wxPush/goBindingSuccessPage";
    } else {
        errorMessage(res.resmsg);
    }
}
$('#confirmButton').on('click', function () {
    var codeNo = $("#identifyingCode").val();//验证码
    var cityName = $("#show_contact").text();//选中的地址
    var phoneNoState = checkPhoneNo();
    var phoneNum = $("#phoneNumber").val();
    if (!phoneNoState) {
        return;
    }
    if (!codeNo) {
        errorMessage("请输入验证码");
        return false;
    }
    if (cityName == "请选择城市") {
        errorMessage("请选择城市");
        return false;
    }
    ajaxRequest(contextPath + "wxPush/phoneBinding", "phone=" + phoneNum + "&captcha=" + codeNo + "&province=" + province + "&city=" + city, submitCb, "GET");

});