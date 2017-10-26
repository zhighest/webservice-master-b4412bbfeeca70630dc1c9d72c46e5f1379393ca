// var commodityID = $("#commodityID").val();

$(document).ready(function () {
    setCommodityID();
    // ajaxRequest(contextPath + "wxgoods/goodsDetailImg", "goodsId="+commodityID,setCommodityID,"GET");
});
var setCommodityID = function () {
    var data = $("#pageData").val();
    data =JSON.parse(data);
    if (data.rescode == "00000") {
        $(".imgHead img").attr("src", data.goodsDetailImgHead);
        $(".imgArea img").attr("src", data.picDetailLink);
        $(".imgBottom img").attr("src", data.goodsDetailImgBottom);
    }
    else {
        errorMessage(data.resmsg_cn)
    }
}