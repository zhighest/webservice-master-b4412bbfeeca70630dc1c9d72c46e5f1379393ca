window.onload=function(){
  var riskType;
  var channel=$("#channel").val();
  var flag=$("#flag").val();
  var mobile=$("#mobile").val();
  var channel=$("#channel").val();
  var returnInvestBtn=$("#returnInvestBtn");
  var userId=$("#userId").val();
  var score=$("#score").val();
  var description1=$("#description1");
  var description2=$("#description2");
  ajaxRequest(contextPath + "wxtrade/getRiskEvaluationById", "mobile="+mobile+"&userId="+userId+"&channel="+channel,setRiskEvaluationById,"GET");
  function setRiskEvaluationById(data){
    if(flag=="fromSetting"){
      $(".settingDefault").removeClass("none");
      if(data.rescode="00000"){
       riskType=data.riskType
       if(riskType!=""&&riskType!=null){
        $("#riskTypeResult").html(riskType);
       }else{
        $("#riskTypeResult").html('保守型');
       }
    }else{
      errorMessage(data.resmsg_cn)
    }
    }else{
      if(data.rescode="00000"){
       riskType=data.riskType
       if(riskType!=""&&riskType!=null){
        $("#riskTypeResult").html(riskType);
           $(".declare").removeClass("none");
           $(".declare").html(data.declare);
       }else{
        $("#riskTypeResult").html('保守型');
        $(".defult").removeClass("none");
       }
    }else{
      errorMessage(data.resmsg_cn)
    }
    }

     
   }
  $("#riskTypeResult").click(function(){
              window.location.href=contextPath + "wxtrade/goRiskEvaluationType";
   });  
  if(channel=="LBIOS"||channel=="LBAndroid"){
        if(flag=="fromSetting"){
            returnInvestBtn.html("重新评估");
            returnInvestBtn.removeClass("none");
            $(".settingDefault").removeClass("none");
            returnInvestBtn.click(function(){
              window.location.href=contextPath + "wxtrade/goRiskEvaluation?channel="+channel+"&flag="+flag+"&mobile="+mobile+"&userId="+userId;
            })
        }else{
        }
    }else{
      if(flag=="fromSetting"){
        
        $("#settingReturnBtn").removeClass("none");
        $(".settingDefault").removeClass("none");
        $("#returnEvaluateBtn").click(function(){
           window.location.href=contextPath+"wxtrade/goRiskEvaluation?channel=LBWX&flag=fromSetting"
        })
        $("#returnMyAssetBtn").click(function(){
           window.location.href=contextPath+"wxtrade/goMyAsset?from=LBWX"
        })
      }else{
         returnInvestBtn.removeClass("none");
         returnInvestBtn.click(function(){
         window.location.href=contextPath+"wxproduct/goProductIndex?pageTag=productIndex"
         });
      }
    }
}