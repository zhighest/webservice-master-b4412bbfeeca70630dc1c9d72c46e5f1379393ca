window.onload=function(){
  var bkgPic=document.getElementById("bkgPic");
  var button=document.getElementById("button");
  var bannerImgListStr=document.getElementById("bannerImgList").value;
  var bannerImgList=bannerImgListStr.substring(1,bannerImgListStr.length-1).split(",");
  var content=document.createElement("div");
    for(var i=0; i<bannerImgList.length;i++){
       var banner=document.createElement("img");
       banner.setAttribute("src",bannerImgList[i]);
       banner.setAttribute("width","100%");
       content.appendChild(banner)
    }
    bkgPic.appendChild(content);
    button.parentNode.style.display="none";
  }
  