var setAuthentication = function(data) {
    var rescode = data.rescode;
    var resmsg_cn = data.resmsg_cn;
    var backUrl = $("#backUrl").val();
    if (rescode != "00000") {
        errorMessage(resmsg_cn);
    } else {
        // var successUrl = contextPath + "wxtrade/authenticationSuccess?redUrl=" + backUrl;
        // window.location.href = successUrl;
        window.location.href = contextPath + "wxtrade/authenticationSuccess?productId="+ productId;
    }
};
$("#authenticationBtn").click(function() {
    var nameCard = $("#nameCard").val();
    var idCard = $("#idCard").val();
    ajaxRequest(contextPath + "wxtrade/authentication", "nameCard=" + nameCard + "&idCard=" + idCard + "&pageSize=10", setAuthentication);
});

var productId;
$(document).ready(function() {
     productId =$("#productId").val();
});