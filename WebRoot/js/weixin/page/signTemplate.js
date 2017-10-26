window.onload=function(){
  var a=new signTemplate({

  })
}
function signTemplate(options){
     this.mobile=$("#mobile").val();
     this.userId=$("#userId").val();
     this.channel=$("#channel").val();
     var bannerImgListStr=$("#bannerImgList").val();
     this.bannerImgList=bannerImgListStr.substring(1,bannerImgListStr.length-1).split(",");
     this.button=document.getElementById("button");
     this.sign();
     this.setPicture();
}
signTemplate.prototype.sign=function(){
     var self=this
     this.button.onclick=function(){
        ajaxRequest(contextPath2 + "wxcoupons/userWechatSign", "mobile="+self.mobile+"&userId="+self.userId+"&signFlag="+self.channel, setUserCurrentSign);//签到接口
     }
     function setUserCurrentSign(data){
         if(data.rescode=="00000"){
          if(self.channel=="LBIOS"){
              if(self.mobile == "" || self.mobile == null || self.mobile == "null"){

              }else{
                  setTimeout(function(){
                      finishBrowser();
                  },1000)

              }
          }else if(self.channel=="LBAndroid"){
               if(self.mobile == "" || self.mobile == null || self.mobile == "null"){

               }else{
                 if(window._cordovaNative){
                     setTimeout(function(){
                         window._cordovaNative.finishBrowser();
                     },1000)

                 }   
              }
          }
      }else{
          errorMessage(data.resmsg_cn)
      }
     }
}
signTemplate.prototype.setPicture=function(){
      document.getElementById("iframe").contentWindow.setPictureFn(this.bannerImgList)
  }

function message(a){
    errorMessage(a)
}

var contextPath2 ="";
function getRealPath(){
  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
   var curWwwPath=window.document.location.href;
   //获取主机地址之后的目录，如： myproj/view/my.jsp
  var pathName=window.document.location.pathname;
  var pos=curWwwPath.indexOf(pathName);
  //获取主机地址，如： http://localhost:8083
  var localhostPaht=curWwwPath.substring(0,pos);
  //获取带"/"的项目名，如：/myproj
  // var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
 
 //得到了 http://localhost:8083/myproj
  contextPath2=localhostPaht+'/';
}
getRealPath();