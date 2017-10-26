var jsarray = new Array();
jsarray[0] = "js/integration/jquery-1.8.2.min.js";
jsarray[1] = "js/integration/Base64-mini.js";
jsarray[2] = "js/integration/DES3.js";
jsarray[3] = "js/integration/merge.js";

for(i=0;i<jsarray.length;i++){
	document.write("<script type='text/javascript' src='"+jsarray[i]+"'></script>");
}