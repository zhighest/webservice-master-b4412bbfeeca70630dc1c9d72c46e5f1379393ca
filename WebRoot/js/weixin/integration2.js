var contextPath ="";
function getRealPath(){
  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
   var curWwwPath=window.document.location.href;
   //获取主机地址之后的目录，如： myproj/view/my.jsp
  var pathName=window.document.location.pathname;
  var pos=curWwwPath.indexOf(pathName);
  //获取主机地址，如： http://localhost:8083
  var localhostPaht=curWwwPath.substring(0,pos);
  //获取带"/"的项目名，如：/myproj
  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
 
 //得到了 http://localhost:8083/myproj
  contextPath=localhostPaht+projectName+'/';
}
getRealPath();

//上下文路径
//var contextPath = "http://licai.lincomb.com/";
//var contextPath = "http://wx.lianbijr.com/";
//var contextPath = "http://114.141.172.206:7001/";//测试环境
//var contextPath = "http://222.73.156.45:7080/webservice/";//测试环境
var contextPath = "../";//测试环境
//var contextPath = "http://wangenlai2010.vicp.cc/webservice/";//本地环境

var qrcodeKey = "c_fe57ED1CUuFUtPWbHk6VvJ/f6pH16AOVlTAFy5kLSPg";  //114.141.172.14 1861****699
//var qrcodeKey = "c_7c0dXWnmkHvOlXwv5tKsTYqkbJcuQIZl2FBFJQ74JUA";  //wx.lianbijr.com 1852****877
//var qrcodeKey = "c_6918iI8hb5FOnRPcIml6Q5FWKX1pNIJK0na56H10mM";  //licai.lincomb.com 1380****423
//var qrcodeKey = "c_0744K8KeTX72K3KZnXQPF/EAJA4Oyd4khoFipiSiBlU"; //wangenlai2010.vicp.cc 1855****832

var jsarray = new Array();
jsarray[0] = contextPath+"js/weixin/integration/jquery-1.8.2.min.js";
//jsarray[1] = "js/integration/Base64-mini.js";
//jsarray[2] = "js/integration/DES3.js";
jsarray[1] = contextPath+"js/weixin/integration/merge.js";
jsarray[2] = contextPath+"js/weixin/integration/quo.js";

for(i=0;i<jsarray.length;i++){
	document.write("<script type='text/javascript' src='"+jsarray[i]+"'></script>");
}

//防钓鱼js代码
// if(top.location!==self.location){  
//   top.location.href=self.location.href;//防止页面被框架包含  
// }  