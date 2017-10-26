//$('#qrcode').qrcode("http://licai.lincomb.com/wxtrade/goInviteFriend?invitationCd=");

//thisURL = contextPath + "wxuser/goLogin?parm="+ encodeURIComponent($("#parm").val()); 
thisURL = contextPath + "/wxTrigger/getWxCode?actionScope="+$("#goUrl").val();

strwrite = "<img src='http://www.2d-code.cn/2dcode/api.php?key="+ qrcodeKey +"&url="+ thisURL +"&bgcolor=ffffff&color=000000&cl=H&size=250&border=1&logo="+ contextPath +"pic/weixin/qrcodeLogo.png' />";

$('#qrcode').html(strwrite);