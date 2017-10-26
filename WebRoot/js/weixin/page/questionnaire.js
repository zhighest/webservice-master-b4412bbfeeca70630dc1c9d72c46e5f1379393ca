window.onload=function(){
	var mobile=$("#mobile").val();
	var userId=$("#userId").val();
	var channel=$("#channel").val();
	var bannerImgListStr=$("#bannerImgList").val();//获取后台压入的banner url
	var onOff=false;//防止未答完题提交
	var url=window.location.href;//后台需要传当前的url
	var questionLength=question.length;//题目总数
	var chooseItemLength;
	var answerArryString;
	var html="";
	var list=["A","B","C","D","E","F"];
	var html2="";
	var bannerImgList=bannerImgListStr.substring(1,bannerImgListStr.length-1).split(",");
	for(var i=0; i<bannerImgList.length;i++){
        html2+='<img src='+bannerImgList[i]+' width="100%">'
	}
	$(".banner").html(html2);//插入banner
	for(var i=0;i<questionLength;i++){
		var chooseItem=question[i].item;//单选题
		var mulItem=question[i].mulItem;//多选题
		html+='<li class="PT30 list" val="N">'
		html+='<p class="PB10">'+question[i].title+'</p>'
		  if(chooseItem){//判断是否为单选题，将题目循环生成
		  	 chooseItemLength=chooseItem.length;//单选题选项个数
		  	 for(var j=0;j<chooseItemLength;j++){
		       html+='<div class="outHide PTB3"><input type="radio" value="'+list[j]+'" name="question'+i+'" id="'+i+''+j+'" class="fl"/>'
		       html+='<label for="'+i+''+j+'" class="fl PL25 tabBkgNone ">'+chooseItem[j]+'</label></div>'
			}
		  }else if(mulItem){//判断是否为多选题，将题目循环生成
		  	    mulItemLength=mulItem.length;
		    for(var j=0;j<mulItemLength;j++){
		       html+='<div class="outHide PTB3"><input type="checkbox" value="'+list[j]+'" name="question'+i+'" id="'+i+''+j+'" class="fl"/>'
		       html+='<label for="'+i+''+j+'" class="fl PL25 tabBkgNone ">'+mulItem[j]+'</label></div>'
			}
		  }else{//否则为问答题
		  	   html+='<div class="choose"><textarea value=""  name="question'+i+'" class="tabBkgNone " style="margin: 0px;width:100%;height: 100px;"></textarea></div>'
		  }
        html+='</li>'
     }
	$("#question").append(html);
	$("#submit").removeClass("none")
	$("#submit").click(function(){
       getSubmit();
   });
	function getSubmit(){//提交时将答案收集，存入数组
		var answerArry=[];//思路：通过id获取每题和选项个数对每项进行判别，声明一个[]空数组存放答案数组，声明空对象{}存放每题答案；
		for(var i=0;i<questionLength;i++){
		 var question = document.getElementsByName("question"+i);
		 var length=question.length;
		 var answerObj={};
		 var arr=[];//存放每题答案
		 answerObj["no"]=i+1;
		   for(var j=0;j<length;j++){
	         if(length!=1&&question[j].checked){//通过判断选项个数来判断是否为选择题还是简答题；其中单选和多选都是通过此方法获取答案
	         	a=question[j].value;
	         	arr.push(a)
	         }else if(length==1&&question[j].value){
		   	    a=question[j].value;
		   	    arr.push(a)
		    }
		   }
		   var answer=arr.join(",");//将数组中的答案用逗号链接成字符串然后放入空对象中{}，然后将对象push到数组中
		   answerObj["result"]=answer;
		   answerArry.push( answerObj);
		}

		answerArryString=JSON.stringify(answerArry);//将json数组转为字符串
		check(answerArry,answerArryString);
	}
   function check(answerArry,answerArryString){
     	var onOff=true
   	   for(var i=0;i<answerArry.length;i++){
           if(answerArry[i].result==""){
           	errorMessage("请完成测试后再提交");
           	onOff=false
           }
   	   }
	   if(onOff){
		   ajaxRequest(contextPath2 + "question/submit", "mobile="+mobile+"&userId="+userId+"&url="+url+"&answers="+answerArryString,setSubmit,"POST");//提交答案的接口
	   }
   }
	function setSubmit(data){
      if(data.rescode=="00000"){
          ajaxRequest(contextPath2 + "wxcoupons/userWechatSign", "mobile="+mobile+"&userId="+userId+"&signFlag="+channel, setUserCurrentSign);//签到接口
      }else{
      	  errorMessage(data.resmsg_cn)
      }
	}

	function setUserCurrentSign(data){
		if(data.rescode=="00000"){
					 if(channel=="LBIOS"){
              if(mobile == "" || mobile == null || mobile == "null"){
                    
              }else{
              	finishBrowser();
              }
          }else if(channel=="LBAndroid"){
               if(mobile == "" || mobile == null || mobile == "null"){
               	 
              }else{
              	if(window._cordovaNative){
                    window._cordovaNative.finishBrowser();
                 }   
              }
          }
      }else{
      	   errorMessage(data.resmsg_cn)
      }

	}
}
var contextPath2 ="";//由于访问路径和以往不同，公共方法中获取的路径有问题，故重新定义
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
  contextPath2=localhostPaht+'/';
}
getRealPath();