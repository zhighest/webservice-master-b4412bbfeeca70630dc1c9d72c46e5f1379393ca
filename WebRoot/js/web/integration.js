var contextPath = "";
function getRealPath() {
    //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： myproj/view/my.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/myproj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    if(projectName!="/webservice")  projectName=""; 
    //得到了 http://localhost:8083/myproj
    contextPath = localhostPaht + projectName + '/';
}
getRealPath();

//上下文路径
//var contextPath = "http://licai.lincomb.com/";
//var contextPath = "http://wx.lianbijr.com/";
//var contextPath = "http://114.141.172.206:7001/";//测试环境
//var contextPath = "http://222.73.156.45:7080/webservice/";//测试环境
//测试环境
//var contextPath = "http://wangenlai2010.vicp.cc/webservice/";//本地环境


var jsarray = new Array();
jsarray[0] = "../js/web/integration/jquery-1.11.2.min.js";
jsarray[1] = "../js/web/integration/merge.js";
jsarray[2] = "../js/web/integration/SMSdk.js"
for(i=0;i<jsarray.length;i++){
	document.write("<script type='text/javascript' src='"+jsarray[i]+"'></script>");
}