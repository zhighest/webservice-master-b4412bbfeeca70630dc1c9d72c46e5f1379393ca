var setInvitationCd = function(data) {
    if (data.rescode != "00000") {
        errorMessage(data.resmsg_cn);
    } else {
        window.location.href = contextPath + "wxtrade/goSetting";
    }
};
$("#invitationCdBtn").click(function() {
    var invitationCd = $("#invitationCd").val();
    ajaxRequest(contextPath + "wxtrade/setInvitationCd", "invitationCd=" + invitationCd, setInvitationCd);
});