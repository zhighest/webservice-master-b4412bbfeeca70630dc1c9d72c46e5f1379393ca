window.onload=function(){
    keyWords("keyWords1");
    keyWords("keyWords2")
}

function keyWords(a){
    var keyWords = document.querySelector('#'+a);
    var clipboard = new Clipboard(keyWords);

    clipboard.on('success', function (e) {
        parent.message("内容成功复制到剪贴板");
    });

    clipboard.on('error', function (e) {
        parent.message("内容复制失败")
    });

}

function setPictureFn(banner){
    var bannerImgList=banner;
    var bannerImgListLength=bannerImgList.length;
    var img=$(".picture");
    for(var i=0;i<img.length;i++){
        if(bannerImgList[i]==""||bannerImgList[i]==undefined){
            img.eq(i).remove();
        }else{
            img.eq(i).attr("src",bannerImgList[i]);
        }
    }
}
