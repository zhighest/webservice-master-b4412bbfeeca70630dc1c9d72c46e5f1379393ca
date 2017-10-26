package com.web.Interceptor;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.DES3Util;
import com.web.util.UserCookieUtil;

public class SessionInterceptorByAct extends HandlerInterceptorAdapter  {
	/** 日志 **/
	private static final Logger logger = Logger.getLogger(SessionInterceptorByAct.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {
		
		logger.debug("#####SESSION开始拦截####");
	    String path = req.getContextPath();
		String basePath = req.getScheme() + "://"
						+ req.getServerName() + ":" + req.getServerPort()  
						+ path;
		String query = req.getQueryString();
		// 从session 里面获取用户名的信息
		UserSession userSession = UserCookieUtil.getUser(req);
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(req);
		// o2o过来时，换用户时让用户重新登录
		if (query != null) {
			String[] parmList = query.split("&");
			if (parmList.length > 1) {
				query = parmList[0];
			}
			if (userSession != null && parmMap.get("mobile") == null) {
				try {
					String jsonStr = java.net.URLDecoder.decode(DES3Util.decode(query.replaceAll(" ", "+")),"UTF-8");
					JSONObject jsonObj = JSONObject.fromObject(jsonStr);
					String mobileNumber = jsonObj.getString("mobileNumber");
					if (!mobileNumber.equals(userSession.getMobile())) {
						userSession = null;
						UserCookieUtil.invalidUser(req, res);
					}
				} catch (Exception e) {
					logger.error("=============e"+e.getMessage());
				}
			}
		}
		// 未登陆时
		if (null == userSession) {
			Map map  = UserCookieUtil.getWeixinOpenid(req);
			if(null==map.get("mediaUid") || "".equalsIgnoreCase(String.valueOf(map.get("mediaUid")))
					|| "null".equalsIgnoreCase(String.valueOf(map.get("mediaUid")))){
				String url = req.getServletPath();
				String backUrl = "";
				String forwardUrl = "/wxactivity/goLoginByLd?parm=";
				JSONObject json = new JSONObject();
				// 有参数的情况下
				if (query != null) {
					backUrl = basePath + url + "?" + query;
					try {
						if (StringUtils.isNotBlank(parmMap.get("mobile"))) {
							UserSession us = new UserSession();
							us.setMobile(parmMap.get("mobile"));
							//登录
							UserCookieUtil.putUser(req, res, us);
							return true;
						}
						String[] parmList = query.split("&");
						if (parmList.length > 1) {
							query = parmList[0];
						}
						String jstr = java.net.URLDecoder.decode(DES3Util.decode(query.replaceAll(" ", "+")),"UTF-8");
						//保存参数传过来的json数据
						json = JSONObject.fromObject(jstr);
					} catch (Exception e) {
						logger.error("[跳转作用域]DES解密失败");
						e.printStackTrace();
					}
				} else {
					backUrl = basePath + url;
				}

				logger.info("#######加密前backUrl："+backUrl+"#####");
				//login接收的数据
				json.put("backUrl", backUrl);
				String parm = getEncodeString(json.toString());
				logger.info("#######拦截器跳转url："+forwardUrl+parm+"#####");
				String actionScope = Base64.encodeBase64URLSafeString((forwardUrl+parm).getBytes("UTF-8"));
				
				req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+actionScope).forward(req, res);
				return false;
			}else{
				return true;
			}
		}
		// 为了同步app登录用户
		if (StringUtils.isNotBlank(parmMap.get("mobile"))) {
			if (!userSession.getMobile().equals(parmMap.get("mobile"))) {
				UserSession us = new UserSession();
				us.setMobile(parmMap.get("mobile"));
				//登录
				UserCookieUtil.putUser(req, res, us);
			}
		}
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
