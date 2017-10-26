
var setFindAppVersionInfo = function(data){
  	var appVersion;
  	var updateDesc;
  	var updateDesc = '';

    if (data.list == null || data.list == "") {
	    
    } else {
	    var updateDescTex = data.list[0].updateDesc.substring(0);
		var dataUpdateDesc = updateDescTex.replace(/。/g,"。<br/>");
        appVersion = data.list[0].appVersion;
        updateDesc = dataUpdateDesc;
    }
    $("#appVersion").html(appVersion);
    $("#updateDesc").html(updateDesc);
    
    ua = navigator.userAgent.toLowerCase();  
    if(ua.match(/MicroMessenger/i)=="micromessenger") { 
		$("#iosBtn").attr("href","http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai");
		$("#androidBtn").attr("href","http://a.app.qq.com/o/simple.jsp?pkgname=com.lincomb.licai");
    }else{
    	$("#iosBtn").attr("href","https://itunes.apple.com/cn/app/id1050036859");
		$("#androidBtn").attr("href",data.list[0].downloadUrl);
    }
    
  }

  $(document).ready(function() {

  	ajaxRequest(contextPath + "webabout/findAppVersionInfo","appType=android&current=1&pageSize=1",setFindAppVersionInfo);
  	var widthScreen = $(window).width();
  	var windowHeight = $(window).height();
  	var height1 = $('.addShow5').offset().top;
  	if(widthScreen >= 768){
	  	$(window).scroll(function(event) {
	  		var scrollTop = $(window).scrollTop();
	  		if(scrollTop <= windowHeight){
		  		$(".addShow1").addClass('show1');
		  		$(".addShow2").addClass('show2');
		  		$(".addShow3").addClass('show3');
		  		$(".addShow4").addClass('show4');
		  	}else{
		  		$(".addShow1").removeClass('show1');
		  		$(".addShow2").removeClass('show2');
		  		$(".addShow3").removeClass('show3');
		  		$(".addShow4").removeClass('show4');
		  	}
		  	if(scrollTop >= height1-windowHeight){
		  		$(".addShow5").addClass('show1');
		  		$(".addShow6").addClass('show2');
		  	}else{
		  		$(".addShow5").removeClass('show1');
		  		$(".addShow6").removeClass('show2');
		  	}
	  	});
  	}
  });