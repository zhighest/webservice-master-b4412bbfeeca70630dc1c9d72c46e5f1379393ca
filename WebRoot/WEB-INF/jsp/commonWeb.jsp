<%@page import="com.web.util.UserCookieUtil"%>
<%@page import="com.web.domain.UserSession"%>
<%@page import="com.web.util.LoginRedirectUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
   //�����޻���
   response.setContentType("text/html;charset=UTF-8");
   response.setHeader("progma","no-cache");   
   response.setHeader("Cache-Control","no-cache");   
   response.setDateHeader("Expires",0);
   request.setAttribute("version","20160202");

   request.setAttribute("hasHeadFoot", request.getParameter("hasHeadFoot"));
   
   request.setAttribute("URL",LoginRedirectUtil.URL);
   request.setAttribute("URLS",LoginRedirectUtil.URLS);
   
   	UserSession us = UserCookieUtil.getUser(request);
	request.setAttribute("webIsLogIn", false);
	if (us != null) {
		request.setAttribute("webIsLogIn", true);
		request.setAttribute("loginMobile", us.getMobile());
		request.setAttribute("loginUserId", us.getId());
	}
   
%>
