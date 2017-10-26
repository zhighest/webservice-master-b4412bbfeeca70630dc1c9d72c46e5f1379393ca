var setBorrowerCompanyInfo = function(data) {
    if (data.rescode == "00000") {
        var dataInfo = data.borrowerCompany;
        $("#companyName").html(dataInfo.borrowerIntroduction);
        $("#fundSecurity").html(dataInfo.fundSecurity);
        $("#borrowerIntroduction").html(dataInfo.guarantorIntroduction);
    }
};
var sloanId;
$(document).ready(function() {
     sloanId = $("#sloanId").val();
    ajaxRequest(contextPath + "wxtrade/getBorrowerCompanyInfo", "sloanId=" + sloanId + "", setBorrowerCompanyInfo);
});