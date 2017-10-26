    function alertRule(data){
      var re2=/\d+(\.\d+)?%/g;
      var activtyText=data.pointDeclare.split('<br>');
      var re=/^\s+|\s+$/g;
      var html='';
      $("#scoreListRule").children('li').remove();
      $.each(activtyText,function(i,Obj){
      html+='<li>'+Obj+'</li>'
      });
      $("#scoreListRule").append(html);
    }
    var getScoreRule = function() { 
      ajaxRequest(contextPath + "wxPoint/getPointInfo", "", alertRule,"GET");
    }
    $(function(){
      getScoreRule()
    })