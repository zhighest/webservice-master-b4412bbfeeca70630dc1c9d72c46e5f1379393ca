<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.net.URLEncoder" %>
<%
	String forwardAction = (String)request.getAttribute("forwardAction");
%>
<head>
<meta http-equiv="refresh" content="1;url=<%=forwardAction%>"> 
</head>
<body>

<script>
var backUrl = window.location.href;
function myrefresh()   
{   
	window.location.href="<%=forwardAction%>"; 
}   
setTimeout('myrefresh()',1);
</script>

</body>
</html>


