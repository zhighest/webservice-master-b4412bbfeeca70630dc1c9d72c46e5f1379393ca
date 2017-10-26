window.onload=function(){
  var goEvaluateBtn=$("#goEvaluateBtn");
  var channel=$("#channel").val();
  var flag=$("#flag").val();
  var mobile=$("#mobile").val();
  var userId=$("#userId").val();
  goEvaluateBtn.click(function(){
    window.location.href=contextPath + "wxtrade/goRiskEvaluation?channel="+channel+"&flag="+flag+"&mobile="+mobile+"&userId="+userId;
  })
}