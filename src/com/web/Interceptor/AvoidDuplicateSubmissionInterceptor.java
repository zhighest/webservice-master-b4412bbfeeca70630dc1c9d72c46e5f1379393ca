package com.web.Interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.UserCookieUtil;

/**
 * 防止重复提交过滤器
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = Logger.getLogger(AvoidDuplicateSubmissionInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
 
		UserSession userSession = UserCookieUtil.getUser(request);

		String reqString = request.getQueryString();

		if (userSession != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
 
            AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.needSaveToken();
                if (needSaveSession) {
                    request.getSession().setAttribute("token", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.needRemoveToken();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        LOG.warn("请勿重复提交,[user:" + userSession.getMobile() + ",url:"
                                + request.getServletPath() + ",sessionToken:"+request.getSession().getAttribute("token")+"]");
                        return false;
                    }
                    request.getSession(false).removeAttribute("token");
                }
            }
        }
        return true;
    }
 
    private boolean isRepeatSubmit(HttpServletRequest request) {
		Map<String, String> paramsMap = CommonUtil.decryptParamters(request);
		
       String serverToken = (String) request.getSession().getAttribute("token");
        if (serverToken == null) {
        	 LOG.warn("请勿重复提交:sessionToken"+serverToken+"为空");
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
        	LOG.warn("请勿重复提交:clinetToken"+clinetToken+"为空");
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
        	LOG.warn("请勿重复提交:clinetToken"+clinetToken+";sessionToken"+serverToken+"不一致");
            return true;
        }
        return false;
    }
 
}