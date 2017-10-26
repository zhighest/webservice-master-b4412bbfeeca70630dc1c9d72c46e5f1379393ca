function setList(){
          this.settings = {//可以设置默认参数
              'page':'1',
              'obj':'#allList',
              'nullObj':'#listNull1'
              }
}
setList.prototype.json={'0':true,'1':true,'2':true,'3':true}//按钮开关，防止点击tab会不断请求接口
setList.prototype.init=function(options){
    $.extend(this.settings,options);
    this.obj=$(this.settings.obj);
    this.pageObj=$(this.settings.pageObj);
    this.nullObj=$(this.settings.nullObj);
    this.page=this.settings.page;
    this.flowType=this.settings.flowType;
    this.onOff=true;
    this.ymArray="";
    if(this.json[this.settings.flowType]){//点击tab时让其他按钮可请求接口
         this.obj.empty();
         this.getScoreList(this.page);
         for(var i in this.json){
            this.json[i]=true
         }
         this.json[this.settings.flowType]=false;//锁住按钮，点击不重复请求接口
    }
}
setList.prototype.getScoreList = function(page) {
     var This=this;
     var flowType=this.flowType;
     ajaxRequest(contextPath + "wxPoint/getPointDetails", "current="+page+"&pageSize=15&flowType="+flowType,callback ,"GET");
    function callback(data){//防止直接调用函数，改变this指向
        This.setScoreRecordList(data)
        This.pagingMobile2(data);
    }
}
setList.prototype.setScoreRecordList=function(data){
        this.week=['周日','周一','周二','周三','周四','周五','周六'];
        this.html='';
        this.nullObj.empty(); 
        var This=this;
        if(data.rescode=="00000"){
           if ((data.list == null || data.list == "" ) && this.onOff) {
              $('#allList').removeClass('borderB');
              this.html += '<div class="listNull">'
              this.html += '<img src="../pic/weixin/list.png">'
              if(this.flowType==0){
                 this.html += '<p class="p1">暂无积分明细</p>'
                 this.html += '<p class="p2">如果有积分明细，您将在这里看到</p>'
              }else if(this.flowType==1){
                 this.html += '<p class="p1">暂无积分收入</p>'
                 this.html += '<p class="p2">如果有积分收入，您将在这里看到</p>'
              }else if(this.flowType==2){
                 this.html += '<p class="p1">暂无积分支出</p>'
                 this.html += '<p class="p2">如果有积分支出，您将在这里看到</p>'
              }else if(this.flowType==3){
                 this.html += '<p class="p1">暂无积分过期</p>'
                 this.html += '<p class="p2">如果有积分过期，您将在这里看到</p>'
              }
              this.html += '</div>'
              this.nullObj.append(this.html); 
          } else {
            $('#allList').addClass('borderB');
             this.onOff=false;
            $.each(data.list,
              function(i, jsonObj) {
              This.newdate=getDate(jsonObj.sysTime);
              This.date=getDate(jsonObj.createTime.substring(0,10));
              This.datDate=Math.floor(Math.floor((This.newdate-This.date))/1000/86400);
              This.time =jsonObj.createTime.substring(11,16);
              This.ym =new Date(jsonObj.createTime.substring(0,7)).Format('yyyy年MM月');
              This.md =getDate(jsonObj.createTime).Format("MM/dd");
              This.newYm=new Date(jsonObj.sysTime.substring(0,7)).Format('yyyy年MM月');
              if(This.ym != This.ymArray){
                  This.ymArray = This.ym;
                  if(This.ym == This.newYm){
                      This.html += '<li  class="borderB borderT grayBkg textL font14 grayTex"><span class="textL blockC clearfix month bkg4">本月</span></li>';
                  }else{
                      This.html += '<li  class="borderB borderT grayBkg textL font14 grayTex"><span class="textL blockC clearfix month bkg4">'+This.ym+'</span></li>';   
                  }
              }
              This.html+='<li class="MLR5 borderB detail">' 
              This.html+='<div class="outHide font16" style="padding-top:9px;">' 
               if(This.datDate==0){
                  This.html+='<div class="fl grayTex font16">今天</div>'
               } else if(This.datDate==1){
                  This.html+='<div class="fl grayTex font16">昨天</div>'
               }else{
                  This.html+='<div class="fl grayTex font16">'+This.md+'</div>'
               }
              if(jsonObj.type == 1){
                  This.html += '<div class="fr redTex"><span class="font18">+'+ jsonObj.amount +'</span>&nbsp;积分</div></div>';
              }else if(jsonObj.type == 2){
                  This.html += '<div class="fr  grayTex"><span class="font18">-'+ jsonObj.amount +'</span>&nbsp;积分</div></div>';
              }else if(jsonObj.type == 3){
                  This.html += '<div class="fr  grayTex"><span class="font18">-'+ jsonObj.amount +'</span>&nbsp;积分</div></div>';
              }
               This.html+='<div class="outHide PT5 font14" style="padding-bottom:5px;"><div class="fl grayTex"><span  class="font14">'+This.time+'</span></div><div class="fr greytex2"><span  class="font12 inlineB textR" style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" id="info">'+ jsonObj.info +'</span></div></div>'  
               This.html+='</li>'
          });
               this.obj.append(this.html);
               var str=document.documentElement.clientWidth;
               if(str <= 360){
            	   $("#info").css("width","220px");
               }
           }
      }else{
        errorMessage(data.resmsg_cn)
      }
}
setList.prototype.pagingMobile2=function(data) {//自定义分页函数，不要用merge.js中的
    var This=this;
    this.pageNum=data.pageObject.pageNum;
    this.totlePages=data.pageObject.totlePages;
    window.onscroll=function(){
        if($(document).scrollTop() >= $(document).height()-$(window).height()){
             if (This.pageNum < This.totlePages) {
                var pageNum=This.pageNum+1
                This.getScoreList(pageNum)
            }else{
                errorMessage('没有更多了');
            }
          };
    };
}
$(document).ready(function() {
    FastClick.attach(document.body);
    $('#tabBar li').click(function(){
        var index=$(this).index();
        var list1=new setList();//创建对象
        list1.init({
        'flowType':index
       });
        $(this).addClass('active').siblings('li').removeClass('active');
    });
    $('#tabBar li').eq(0).trigger('click')//初始化页面

})


