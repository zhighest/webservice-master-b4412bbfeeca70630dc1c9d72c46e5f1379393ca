package com.web.Interceptor;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.actions.weixin.common.WeiXinUserDto;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.rsaO2O.RSAPublicPrivateLB;

public class SessionInterceptor extends HandlerInterceptorAdapter  {
	/** 日志 **/
	private static final Logger logger = Logger.getLogger(HandlerInterceptor.class);
	
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
		
		String url = req.getServletPath();
		String query = req.getQueryString();
		String backUrl = "";
		// 有参数的情况下
		if (query != null) {
			backUrl = basePath + url + "?" + query;
		} else {
			backUrl = basePath + url;
		}
		
		JSONObject json1 = new JSONObject();
		json1.put("invitationCd", "");
		// 拦截的url是提供给O2O的接口，判断渠道和拦截 ，内部加签名，注册，登录
		///wxproduct/goProductBuy?id=105&encrypt_key=XZeOEvB1bqK7le2zhUMUruGcuXDH5KWrjrd4xealwzU5WyMBz4nEIBa/AOizj5Jzz6pYxuKcz+CAIi9mo2VtnOS4FRAVFHIAZubIIzhfWYV9FtrwAANgUazaK8YCaL+lYhWgmRGBWe0RgQnqXIshHe/7m0zB6AHbQZhidiN8Av8=
		///wxproduct/goDemandOrderConfirmation?encrypt_key=XZeOEvB1bqK7le2zhUMUruGcuXDH5KWrjrd4xealwzU5WyMBz4nEIBa/AOizj5Jzz6pYxuKcz+CAIi9mo2VtnOS4FRAVFHIAZubIIzhfWYV9FtrwAANgUazaK8YCaL+lYhWgmRGBWe0RgQnqXIshHe/7m0zB6AHbQZhidiN8Av8=
		if ("/wxproduct/goProductBuy".equals(req.getServletPath())
				|| "/wxproduct/goDemandOrderConfirmation".equals(req.getServletPath())) {
			String encrypt_key = req.getParameter("encrypt_key");
			if (StringUtils.isNotBlank(encrypt_key)) {
				//清除登录状态
				UserCookieUtil.invalidUser(req, res);

				Map<String, String> rsaMap = new HashMap<String, String>();
				try {
					rsaMap = RSAPublicPrivateLB.getPrivate_Key_Deciphering(encrypt_key.replaceAll(" ", "+"));
				} catch (Exception e) {
					logger.info("提供给o2o接口"+req.getServletPath()+"解密失败，走正常登录goLogin");
					backUrl = basePath + "/wxtrade/goMyAsset";
					json1.put("backUrl", backUrl);
					String parm = getEncodeString(json1.toString());
					String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
//					req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+goUrl).forward(req, res);
					res.sendRedirect(basePath+"/wxTrigger/getWxCode?actionScope="+goUrl);
					return false;
				}
				String mobile = rsaMap.get("mobileNumber");
				String channel = rsaMap.get("channel");
				// 校验手机号不能为空
				if (StringUtils.isBlank(mobile)) {
					logger.error("数据检查失败： 渠道手机号不能为空");
					backUrl = basePath + "/wxtrade/goMyAsset";
					json1.put("backUrl", backUrl);
					String parm = getEncodeString(json1.toString());
					String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
//					req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+goUrl).forward(req, res);
					res.sendRedirect(basePath+"/wxTrigger/getWxCode?actionScope="+goUrl);
					return false;
				}
				// 非法手机号检查
				boolean flag = mobile.matches(Consts.PHONECHECK);
				if (!flag) {
					logger.error("数据检查失败：  手机号格式不对");
					backUrl = basePath + "/wxtrade/goMyAsset";
					json1.put("backUrl", backUrl);
					String parm = getEncodeString(json1.toString());
					String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
//					req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+goUrl).forward(req, res);
					res.sendRedirect(basePath+"/wxTrigger/getWxCode?actionScope="+goUrl);
					return false;
				}
				// 渠道验证
				if (StringUtils.isBlank(channel) || !"LINGDANG".equalsIgnoreCase(channel)) {
					logger.error("数据检查失败： 渠道为空或者渠道非铃铛入口");
					backUrl = basePath + "/wxtrade/goMyAsset";
					json1.put("backUrl", backUrl);
					String parm = getEncodeString(json1.toString());
					String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
//					req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+goUrl).forward(req, res);
					res.sendRedirect(basePath+"/wxTrigger/getWxCode?actionScope="+goUrl);
					return false;
				}
				
				// 检查用户是否注册
				Map<String,Object> userMap = new LinkedHashMap<String,Object>();
				userMap.put("phoneNum", mobile);
				String param = CommonUtil.getParam(userMap);
				try {
					param = DES3Util.encode(param);
				} catch (Exception e) {
					logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
				}
				// 调用service接口
				String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
				try {
					resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
				} catch (Exception e) {
					logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
				}
				JSONObject jsonObject = JSONObject.fromObject(resultMsg);
				JSONObject user = jsonObject.getJSONObject("user");
				//用户不存在 则注册
				if (user == null || "null".equals(user.toString())) {
					JSONObject jsonRs = new JSONObject();
					jsonRs.put("mobileNumber", mobile);
					jsonRs.put("channel", channel);
					String regparam = "";
					try {
						regparam = DES3Util.encode(jsonRs.toString());
					} catch (Exception e) {
						logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
					}
					// 调用service接口
					String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/register", regparam);
					try {
						regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
					}
					JSONObject regObject = JSONObject.fromObject(regMsg);
					if (!Consts.SUCCESS_CODE.equals(regObject.getString("rescode"))) {
						logger.info("O2O自动注册失败，跳到正常登录gologin注册页面");
						backUrl = basePath + "/wxtrade/goMyAsset";
						json1.put("backUrl", backUrl);
						String parm = getEncodeString(json1.toString());
						String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
//						req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+goUrl).forward(req, res);
						res.sendRedirect(basePath+"/wxTrigger/getWxCode?actionScope="+goUrl);
						return false;
					}else{
						// 调用service接口
						String resultMsg2 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
						try {
							resultMsg2 = java.net.URLDecoder.decode(DES3Util.decode(resultMsg2),"UTF-8");
						} catch (Exception e) {
							logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
						}
						JSONObject jsonObject2 = JSONObject.fromObject(resultMsg2);
						user = jsonObject2.getJSONObject("user");
					}
				}
				System.out.println("登陆成功！");
				
				UserSession us = new UserSession();
				us.setId(Integer.valueOf(user.getString("id")));
				us.setUsername(user.getString("nick_name"));
				us.setMobile(user.getString("mobile"));
				//登录
				UserCookieUtil.putUser(req, res, us);
				res.sendRedirect(backUrl.split("encrypt_key")[0]);
				return false;
			}
		}
		
		// 正常业务没有登陆拦截
		if ("/wxproduct/goDemandProductIndex".equals(req.getServletPath())){
			String encrypt_key = req.getParameter("encrypt_key");
			if(null==encrypt_key || "".equalsIgnoreCase(encrypt_key)){
				return true;
			}
			//清除登录状态
			UserCookieUtil.invalidUser(req, res);
			
			Map<String, String> rsaMap = new HashMap<String, String>();
			try {
				rsaMap = RSAPublicPrivateLB.getPrivate_Key_Deciphering(encrypt_key.replaceAll(" ", "+"));
			} catch (Exception e) {
				logger.info("提供给o2o接口"+req.getServletPath()+"解密失败，走正常登录goLogin");
				res.sendRedirect(basePath+"/wxproduct/goDemandProductIndex");
				return false;
			}
			String mobile = rsaMap.get("mobileNumber");
			String channel = rsaMap.get("channel");
			// 校验手机号不能为空
			if (StringUtils.isBlank(mobile)) {
				logger.error("数据检查失败： 渠道手机号不能为空");
				res.sendRedirect(basePath+"/wxproduct/goDemandProductIndex");
				return false;
			}
			// 非法手机号检查
			boolean flag = mobile.matches(Consts.PHONECHECK);
			if (!flag) {
				logger.error("数据检查失败：  手机号格式不对");
				res.sendRedirect(basePath+"/wxproduct/goDemandProductIndex");
				return false;
			}
			// 渠道验证
			if (StringUtils.isBlank(channel) || !"LINGDANG".equalsIgnoreCase(channel)) {
				logger.error("数据检查失败： 渠道为空或者渠道非铃铛入口");
				res.sendRedirect(basePath+"/wxproduct/goDemandProductIndex");
				return false;
			}
			
			// 检查用户是否注册
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("phoneNum", mobile);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			}
			// 调用service接口
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			//用户不存在 则注册
			if (user == null || "null".equals(user.toString())) {
				JSONObject jsonRs = new JSONObject();
				jsonRs.put("mobileNumber", mobile);
				jsonRs.put("channel", channel);
				String regparam = "";
				try {
					regparam = DES3Util.encode(jsonRs.toString());
				} catch (Exception e) {
					logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
				}
				// 调用service接口
				String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/register", regparam);
				try {
					regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
				} catch (Exception e) {
					logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
				}
				JSONObject regObject = JSONObject.fromObject(regMsg);
				if (!Consts.SUCCESS_CODE.equals(regObject.getString("rescode"))) {
					logger.info("O2O自动注册失败，跳到正常登录gologin注册页面");
					res.sendRedirect(basePath+"/wxproduct/goDemandProductIndex");
					return false;
				}else{
					// 调用service接口
					String resultMsg2 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
					try {
						resultMsg2 = java.net.URLDecoder.decode(DES3Util.decode(resultMsg2),"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
					}
					JSONObject jsonObject2 = JSONObject.fromObject(resultMsg2);
					user = jsonObject2.getJSONObject("user");
				}
			}
			System.out.println("登陆成功！");
			UserSession us = new UserSession();
			us.setId(Integer.valueOf(user.getString("id")));
			us.setUsername(user.getString("nick_name"));
			us.setMobile(user.getString("mobile"));
			//登录
			UserCookieUtil.putUser(req, res, us);
			res.sendRedirect(basePath+"/wxproduct/goDemandProductIndex");
			return false;
		}
		// 未登陆时
		if (null == userSession) {
			
			Map map  = UserCookieUtil.getWeixinOpenid(req);
			String forwardUrl = "/wxuser/goLogin?parm=";
			JSONObject json = new JSONObject();
			json.put("invitationCd", "");
			
			// 我的账户页面手机号带入登陆页
			if ("/wxtrade/goMyAsset".equals(req.getServletPath())) {
				String mobileNumber = req.getParameter("mobileNumber");
				if (mobileNumber != null && mobileNumber.length() > 0) {
					String channel = req.getParameter("channel");
					channel = channel == null ? "" : channel;
					json.put("backUrl", backUrl);
					String parm = getEncodeString(json.toString());
					logger.info("#######拦截器跳转url："+forwardUrl+parm+"#####");
					String actionScope = Base64.encodeBase64URLSafeString((forwardUrl+parm + "&mobileNumber="+mobileNumber+"&channel="+channel).getBytes("UTF-8"));
					
					req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+actionScope).forward(req, res);
					return false;
				}
			}

			if(null==map.get("mediaUid") || "".equalsIgnoreCase(String.valueOf(map.get("mediaUid"))) || "null".equalsIgnoreCase(String.valueOf(map.get("mediaUid")))){
				// 拦截的url是邀请列表页面时 跳转到分享活动页
				if ("/wxtrade/goInviteFriend".equals(req.getServletPath())) {
					String invitationCd = req.getParameter("invitationCd");
					json.put("invitationCd", invitationCd==null ? "" : invitationCd);
					if (invitationCd != null && invitationCd.length() > 0) {
						backUrl = basePath + "/webabout/download";
						forwardUrl = "/wxactivity/activitySharing";

						json.put("backUrl", backUrl);
						String parm = getEncodeString(json.toString());
						logger.info("#######拦截器跳转url："+forwardUrl+"?"+parm+"#####");
						req.setAttribute("parm", parm);
						
						String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
						req.setAttribute("goUrl", goUrl);
						req.getRequestDispatcher(forwardUrl).forward(req, res);
						return false;
					} else {
						backUrl = basePath + "/wxtrade/goMyAsset";

						json.put("backUrl", backUrl);
						String parm = getEncodeString(json.toString());
//						logger.info("#######拦截器跳转url："+forwardUrl+parm+"#####");
						
						String goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+parm).getBytes("UTF-8"));
						
//						req.getRequestDispatcher(forwardUrl + parm).forward(req, res);
						req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+goUrl).forward(req, res);
						return false;
					}
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
		} else {
			String ua = req.getHeader("user-agent")
			          .toLowerCase();
			if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
				logger.info("****************是微信浏览器************");
				logger.debug("=======当前用户已登录=======");
				// 用户交易密码为空的时候
				if (checkUserPasswordByMobile(userSession.getMobile())) {
					logger.debug("=======当前用户 交易密码未设置=======");

					WeiXinUserDto userDto = new WeiXinUserDto();
					userDto.setPhoneNum(userSession.getMobile());
					
					req.getSession().setAttribute("backUrl", backUrl);
					req.getSession().setAttribute("userDto", userDto);

					req.getRequestDispatcher("/wxuser/goSetExchangePasswordByLogin").forward(req, res);
					return false;
				}
				logger.debug("=======当前用户 交易密码已设置=======");
			}
			return true;
		}
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
	
	/**
	 * 判断当前登陆用户的 交易密码是否为空。
	 * @param phoneNum
	 * @return true：为空  false：不为空
	 */
	private boolean checkUserPasswordByMobile(String phoneNum) {
		boolean rtnFlg = false;
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("phoneNum", phoneNum);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
		}
		
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		JSONObject user = jsonObject.getJSONObject("user");
		//用户存在
		if (user != null && !"null".equals(user.toString())) {
			String passwordCash = user.getString("password_cash");
			if (StringUtils.isBlank(passwordCash)) {
				rtnFlg = true;
			}
		}
		return rtnFlg;
	}
	
}
