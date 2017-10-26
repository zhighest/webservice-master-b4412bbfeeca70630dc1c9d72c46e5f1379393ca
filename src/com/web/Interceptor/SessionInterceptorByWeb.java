package com.web.Interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.domain.UserSession;
import com.web.util.DES3Util;
import com.web.util.UserCookieUtil;

public class SessionInterceptorByWeb extends HandlerInterceptorAdapter  {
	/** 日志 **/
	private static final Logger logger = Logger.getLogger(SessionInterceptorByWeb.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {
		
		logger.debug("#####SESSION开始拦截####");
	    String path = req.getContextPath();
		String basePath = req.getScheme() + "://"
						+ req.getServerName() + ":" + req.getServerPort()  
						+ path;
		// 从session 里面获取用户名的信息
		UserSession userSession = UserCookieUtil.getUser(req);
		logger.info("cookie 拦截userSession--");
		// 未登陆时
		if (userSession == null) {
			logger.info("cookie 拦截userSession是null");
			String url = req.getServletPath();
			String query = req.getQueryString();
			String backUrl = "";
			String forwardUrl = "/user/goLogin?parm=";

			JSONObject json = new JSONObject();
			json.put("invitationCd", "");
			// 有参数的情况下
			if (query != null) {
				backUrl = basePath + url + "?" + query;
			} else {
				backUrl = basePath + url;
			}
			// 拦截的url是邀请列表页面时 跳转到分享活动页
			if ("/trade/goInviteFriend".equals(req.getServletPath())) {
				String invitationCd = req.getParameter("invitationCd");
				json.put("invitationCd", invitationCd==null ? "" : invitationCd);
				backUrl = basePath + "/webabout/download";

				json.put("backUrl", backUrl);
				String parm = getEncodeString(json.toString());
				logger.info("#######拦截器跳转url："+forwardUrl+"?"+parm+"#####");
				req.setAttribute("parm", parm);
				req.getRequestDispatcher(forwardUrl).forward(req, res);
				return false;
			}

			logger.info("#######加密前backUrl："+backUrl+"#####");
			//login接收的数据
			json.put("backUrl", backUrl);
			String parm = getEncodeString(json.toString());
			logger.info("#######拦截器跳转url："+forwardUrl+parm+"#####");
			
			req.getRequestDispatcher(forwardUrl + parm).forward(req, res);
			return false;
		}
		logger.info("cookie 拦截userSession--" +userSession.toString());
		return true;
	}
	
	private String getEncodeString(String str) {
		String parm = "";
		try {
			parm = URLEncoder.encode(DES3Util.encode(str),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		return parm;
	}
	
}
