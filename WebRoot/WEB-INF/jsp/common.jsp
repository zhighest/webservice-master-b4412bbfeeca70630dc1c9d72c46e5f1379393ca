<%@page import="com.web.util.LoginRedirectUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="shareUtil.jsp"%>
<%
   //ÉèÖÃÎÞ»º´æ
   response.setContentType("text/html;charset=UTF-8");
   response.setHeader("progma","no-cache");   
   response.setHeader("Cache-Control","no-cache");   
   response.setDateHeader("Expires",0);
   request.setAttribute("version","2016040701");
   
   request.setAttribute("URL",LoginRedirectUtil.URL);
   request.setAttribute("URLS",LoginRedirectUtil.URLS);
%>