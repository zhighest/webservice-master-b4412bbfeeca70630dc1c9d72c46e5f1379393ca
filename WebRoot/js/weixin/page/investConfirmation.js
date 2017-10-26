//若没有进行实名认证则需要实名认证 begin
var idcardValidate;
var isRiskEvaluation//是否风险评估过
var getIdcardInfo = function(data) {
    if (data.idcardValidate != "Y") {
        idcardValidate = false;
        alertBox("请先实名认证","goIdcardValidate",2);
    } else {
        idcardValidate = true;
    }
}

function goIdcardValidate(){
    window.location.href = contextPath + "wxtrade/goAuthentication?productId=109";
}
//若没有进行实名认证则需要实名认证 end

var investLimitFlg;
var productName;
var investLimitAmount;
var investMaxAmount;
//调用接口wxenjoy/getEnjoyBuyDetail里的数据 begin
var setEnjoyBuyDetail = function(data){
  var jsonObj = data.enjoyBuyDetail;
  investLimitFlg = jsonObj.investLimitFlg;
  productName = jsonObj.productName;
  investLimitAmount = jsonObj.investLimitAmount;
  investMaxAmount = jsonObj.investMaxAmount;
	//从接口中调用数据，并将数据在jsp页面相对应的id里显示出来 begin
  if(investLimitFlg == "0"||investLimitFlg == 0){
	  $("#investMaxAmountLi").addClass("none");
  }
  else if(investLimitFlg == "1"||investLimitFlg == 1){
	  $("#investMaxAmountLi").removeClass("none");
  }
    $("#investMaxAmount").html(investMaxAmount);//上限金额
   $("#productName").html(productName);//产品名称
   $("#investDate").html(jsonObj.investDate);//计息时间
   $("#returnRateName").html(jsonObj.returnRateName);//收益率名称
   $("#returnRate").html(jsonObj.returnRate);//收益率
   $("#projectView").html(jsonObj.projectView);//项目概况
   $("#fundSecurity").html(jsonObj.fundSecurity);//资金保障
   $("#investMinAmount").html(jsonObj.investMinAmount)//起投金额
  
}
//调用接口wxenjoy/getEnjoyBuyDetail里的数据 end

function setRiskEvaluationById(data){//判断有没有进行过问卷调查
    if(data.rescode=="00000"){
      isRiskEvaluation=data.isRiskEvaluation
    }else{
        // errorMessage(data.resmsg_cn)
    }
}

    $("#enterBid").click(function(){
        if(isRiskEvaluation=="N"){
            alertRiskEvaluation("您未进行风险承受能力评估，若想继续投资，请进行评估。","goEvaluate",1);
        }else{
        window.location.href = contextPath + "wap/investeensure";
        }
    })

$(document).ready(function() {
   //实名认证接口
    var mobile=$("#mobile").val();
  ajaxRequest(contextPath + "wxuser/getIdcardInfo", "", getIdcardInfo);
  ajaxRequest(contextPath + "wxenjoy/getEnjoyBuyDetail", "productId=107", setEnjoyBuyDetail);
  ajaxRequest(contextPath + "wxtrade/getRiskEvaluationById", "mobile="+mobile,setRiskEvaluationById,"GET");
});
//去评估
function goEvaluate(){
    window.location.href=contextPath + "wxtrade/goRiskEvaluation?channel=LBWX";
}
function alertRiskEvaluation(tips,method,type){
    var html='';
    html += '<div class="width100 height100P positionF" id="alertBox" style="background:rgba(0,0,0,0.3);left:0;top:0;z-index: 1001;">';
    html += '<div class="radius5 whiteBkg width80 positionA textC" style="height:190px;left:10%;top:50%;margin-top: -95px; box-shadow: 0px 0px 3px #666;">';
    html += '<h3 class="font20 PTB15 redTex " style="border-bottom:1px solid #ff5a5a">温馨提示</h3>';
    html += '<p class="font16 blackTex lineHeight1_5x textL" style="height:75px;padding:15px 5% 0;">'+tips+'</p>';
    if(type == 1){
        html += '<a class="inlineB grayBkg width35 ML10 MR10 grayTex PT5 PB5 font16 radius5" onclick="$(\'#alertBox\').remove();">取消</a>';
    }
    html += '<a class="inlineB redBkg width35 ML10 MR10 whiteTex PT5 PB5 font16 radius5" onclick="'+method+'();">去评估</a>';
    html += '</div></div>';
    $("body").append(html);
}