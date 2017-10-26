function tab1Click(){
   $("#totalFlowList").empty();
    getTotalFlowList(1);  
}
function tab2Click(){
  $("#incomeAmount").empty();
  getFundFlowList(1);
}


//tab栏切换效果
$("#tabNav li").eq(0).click(function(event) {
    $(this).children('a').addClass('CUA');
    $("#tabNav li").eq(1).children('a').removeClass('CUA');
    $("#tabContent1").show();
    $("#tabContent2").hide();
});
$("#tabNav li").eq(1).click(function(event) {
    useClick();
    $("#addFlowList").empty();
    getAddFlowList(1);
    
});

//切换至第二种状态
function useClick(){
    $("#tabNav li").eq(0).children('a').removeClass('CUA');
    $("#tabNav li").eq(1).children('a').addClass('CUA');
    $("#tabContent1").stop().hide();
    $("#tabContent2").stop().show();
}



// 历史收益
var getFundFlowList = function(page) { 
    ajaxRequest(contextPath + "wxenjoy/historyAmount", "current="+"&userId=" + page + "&pageSize=30", setFundFlowList,"GET");
}
var setFundFlowList = function(data) {
	$("#totalIncomeAmountBox h4").html("累计收益(元)");
    $("#totalIncomeAmount").html(numFormat(data.totalIncomeAmount));
    var html = ""; //拼接html
    var arr = new Array;
    var min=arr[0], max=arr[0];
    if (data.list == null || data.list == "") {
    	
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<h4 class="p1">暂无历史收益</h4>';
        html += '<p class="p2">如果有历史收益，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,function(i, jsonObj) {
         arr.push(jsonObj.incomeAmount);
        });
        var sum = 0;
        for (var i = arr.length - 1; i >= 0; i--) {
           sum += arr[i]
        }
        max = Math.max.apply(null, arr);
        min = Math.min.apply(null, arr); 
        if (parseFloat(sum) == 0){
        $("#totalIncomeAmountBox").stop().hide();
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<h4 class="p1">暂无历史收益</h4>';
        html += '<p class="p2">如果有历史收益，您将在这里看到</p>';
        html += '</div>';
        }else{
        $.each(data.list,function(i, jsonObj) {
          if (jsonObj.incomeAmount == "0.00"){

          }
          else {
              html += '<li class="MB10" onclick="rotate($(this))">';
              html += '<div class="width100 whiteBkg clearfix positionR list">';
              if(jsonObj.incomeAmount/max*100 > 50){
                   if(i == 0){
                      html += '<div class="blockC bkg3 fl positionA rotate1" style="width:'+ jsonObj.incomeAmount/max*100 +'%">';
                  }else{
                      html += '<div class="blockC bkg2 fl positionA rotate1" style="width:'+ jsonObj.incomeAmount/max*100 +'%">';
                  }
              }else{
                  if(i == 0){    
                    html += '<div class="blockC bkg3 fl positionA rotate1" style="width:50%">';
                  }else{
                    html += '<div class="blockC bkg2 fl positionA rotate1" style="width:50%">';
                  }
              }
              html += '<div class="fl ML10">';
              html += '<h4 class="font14 whiteTex">' + jsonObj.incomeDay + '</h4>';
              html += '</div><div class="fr font14 MR10 whiteTex">' + numFormat(jsonObj.incomeAmount) + '</div>';
              html += '</div>';
              if(jsonObj.incomeAmount/max*100 > 50){
                  html += '<div class="blockC bkg3 fl positionA rotate2" style="width:'+ jsonObj.incomeAmount/max*100 +'%">';
              }else{ 
                  html += '<div class="blockC bkg3 fl positionA rotate2" style="width:50%">';
              }
              html += '<div class="fl ML10">';
              html += '<h4 class="font14 whiteTex">'+numFormat(jsonObj.incomeAmount)+'</h4>';
              html += '</div><div class="fr font14 MR10 whiteTex">'+jsonObj.incomeDay+'</div>';
              html += '</div></div>';
              html += '</li>';
          }
        });
      }
    }
    //console.log(min, max);
    $("#incomeAmount").append(html);//将拼接内容放入id为fundFlowList的ul中
};

//年化收益率
var getTotalFlowList = function(page) { 
    ajaxRequest(contextPath + "wxenjoy/historyAmount", "current="+"&userId=" + page + "&pageSize=30", setTotalFlowList,"GET");  
}
var setTotalFlowList  = function(data) {
    var html = ""; //拼接html
    var arr = new Array;
    var min=arr[0], max=arr[0];
    if (data.list == null || data.list == "") {
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<h4 class="p1">暂无年化收益率</h4>';
        html += '<p class="p2">如果有年化收益率，您将在这里看到</p>';
        html += '</div>';
    } else {
        $.each(data.list,function(i, jsonObj) {
         arr.push(parseFloat(jsonObj.totalRise));
        });
        var sum = 0;
        for (var i = arr.length - 1; i >= 0; i--) {
             sum += arr[i]
        }
        max = Math.max.apply(null, arr);
        min = Math.min.apply(null, arr); 
        if (parseInt(sum) == 0){
        html += '<div class="listNull">';
        html += '<img src="../pic/weixin/list.png">';
        html += '<h4 class="p1">暂无年化收益率</h4>';
        html += '<p class="p2">如果有年化收益率，您将在这里看到</p>';
        html += '</div>';
        }else{
            $.each(data.list,function(i, jsonObj) {
            if (jsonObj.totalRise == "0.00"){
                }
            else{
                html += '<li class="MB10" onclick="rotate($(this))">';
                html += '<div class="width100 whiteBkg clearfix positionR list">';
                if(jsonObj.totalRise/max*100 > 50){
                  if(i == 0){
                    html += '<div class="blockC bkg3 fl positionA rotate1" style="width:'+ jsonObj.totalRise/max*100 +'%">';
                  }else{
                    html += '<div class="blockC bkg2 fl positionA rotate1" style="width:'+ jsonObj.totalRise/max*100 +'%">';
                  }
                }else{
                  if(i == 0){    
                  html += '<div class="blockC bkg3 fl positionA rotate1" style="width:50%">';
                  }else{
                  html += '<div class="blockC bkg2 fl positionA rotate1" style="width:50%">';
                  }
                }
                html += '<div class="fl ML10">';
                html += '<h4 class="font14 whiteTex">' + jsonObj.incomeDay + '</h4>';
                html += '</div><div class="fr font14 MR10 whiteTex">' + numFormat(jsonObj.totalRise) + '</div>';
                html += '</div>';
                if(jsonObj.totalRise/max*100 > 50){
                  html += '<div class="blockC bkg3 fl positionA rotate2" style="width:'+ jsonObj.totalRise/max*100 +'%">';
                }else{ 
                  html += '<div class="blockC bkg3 fl positionA rotate2" style="width:50%">';
                }
                html += '<div class="fl ML10">';
                html += '<h4 class="font14 whiteTex">'+numFormat(jsonObj.totalRise)+'</h4>';
                html += '</div><div class="fr font14 MR10 whiteTex">'+jsonObj.incomeDay+'</div>';
                html += '</div></div>';
                html += '</li>';
              }
            });
        }   
    }
    //console.log(min, max);
    $("#totalFlowList").append(html);//将拼接内容放入id为fundFlowList的ul中
};  

 



function rotate(a){
    a.toggleClass('hover');
}
$(document).ready(function() {
  var tabSkip = $("#tabSkip").val();
  if (tabSkip == 'totalRise') {
    tabSwitchLi('tabNav','tab1');tabSwitchCon('tabContent','tabContent1');tab1Click()
  }else if (tabSkip == 'incomeAmount') {
    tabSwitchLi('tabNav','tab2');tabSwitchCon('tabContent','tabContent2');tab2Click()
  }
    getTotalFlowList(1);
    // getAddFlowList(1);
    // getFundFlowList(1);
    // getInvFlowList(1);
    
});


