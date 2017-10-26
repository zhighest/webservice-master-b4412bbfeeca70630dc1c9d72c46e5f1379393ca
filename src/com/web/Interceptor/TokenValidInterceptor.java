package com.web.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.web.util.Constants;
import com.web.util.LoginRedirectUtil;
import com.web.util.TokenHandler;



public class TokenValidInterceptor implements HandlerInterceptor{
	public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object arg2, Exception arg3)
            throws Exception {
    }
 
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
         
    }
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object arg2) throws Exception {
	    	String path=""; 
	        if(!"".equals(LoginRedirectUtil.LOGINFUNCTIONSSLURL)){
	            path=LoginRedirectUtil.LOGINFUNCTIONSSLURL;
	        }else{
	        	path=request.getContextPath();
	        }
	        System.out.println("********************="+request.getParameter(Constants.DEFAULT_TOKEN_NAME));
	        if(!TokenHandler.validToken(request)){
	            response.sendRedirect(path+"/tokenError/tokenErrors");//应该跳转到一个失效的地址；或者原来的地址�?
	            return false;
	        }
    return true;
    }
}
