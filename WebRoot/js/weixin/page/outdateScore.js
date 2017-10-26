
      var getScoreList = function(page) { 
         ajaxRequest(contextPath + "wxPoint/getExpirePoint", "current=" + page + "&pageSize=3", setContent,"GET");
      }
      var getScoreRule = function() { 
         ajaxRequest(contextPath + "wxPoint/getPointInfo", "", alertRule,"GET");
      }
      function setContent(data){
        var html='';
        var date=new Date().Format('yyyy-MM');
        var currentMonth=date;
        if(data.rescode=="00000"){
          $('#scoreNum').html(data.amountSum);
          if(data.pointExpireList==""||data.pointExpireList==null){
              html += '<div class="listNull">';
              html += '<img src="../pic/weixin/list.png">';
              html += '<p class="p1">暂无即将过期积分</p>';
              html += '<p class="p2">如果有即将过期积分，您将在这里看到</p>';
              html += '</div>';
              $("#nullList").append(html); 
          }else{
            $('.top').removeClass('none');
            $('.outdataScore').removeClass('none');
            $.each(data.pointExpireList,function(i,jsonObj){
            var date=new Date(jsonObj.time).Format('yyyy年MM月'),
            times=jsonObj.time;
            html+='<li class="borderBottomTGray PLR5P">'
            html+='<div class="scoreDetail outHide font16">'
            if(currentMonth==times){
            html+='<p id="scoreTime" class="blackTex fl">本月</p>'
            }else{
            html+='<p id="scoreTime" class="grayTex fl">'+date+'</p>'
            } 
            html+='<p id="outDataNum" class="redTex font18 fr"><span>'+jsonObj.amount+'</span>&nbsp;积分</p>'
            html+='</div></li>'
            })
            $('#scorelist').append(html);
          }
        }else{
             errorMessage(data.resmsg_cn);
         }
      }
//规则说明弹窗
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
      $(document).ready(function(){
        var screenH=$('body').height();
        getScoreList(1);
        getScoreRule()
        $('.scoreExplain').css('top',screenH*1.3);
        $('.closeBtn').click(function(){
             $('.scoreExplain').animate({'top':screenH*1.3},300);
        })
        $('.scoreNotice ').click(function(){
             $('.scoreExplain').removeClass('none').animate({'top':0},300);
        })
      })