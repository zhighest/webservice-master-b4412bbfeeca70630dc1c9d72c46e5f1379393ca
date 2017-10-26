package com.web.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.web.domain.UserSession;

/**
 * 
 * @author lbs-pc
 *
 */
public class UserCookieUtil {

	public static Logger logger = Logger.getLogger(UserCookieUtil.class.getName());
	
	public static String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		String sessionId = Tools.getCookieValueByName(request,Constants.SESSION_NAME);
		if (sessionId == null) {
			//生成全球唯一ID
			sessionId = UUID.randomUUID().toString();
			Tools.setCookie(request, response, Constants.SESSION_NAME, sessionId, Constants.COOKIE_ALIVE_SIX_HOUR);
		}
		logger.info("当前访问的SESSIONID为"+sessionId);
		return sessionId;
	}
	
	/**
	 * 获取用户
	 * @param request
	 * @return
	 */
	public static UserSession getUser (HttpServletRequest request) {
		UserSession userSession = null;
		String usJson = Tools.getCookieValueByName (request,Constants.USER_SESSION);
		try {
			if (usJson != null && usJson.length() > 0 ) {
				usJson = java.net.URLDecoder.decode(DES3Util.decodeCookie(usJson), "UTF-8");
				userSession = (UserSession) JsonUtil.getObjectFromJsonString(usJson, UserSession.class);
			}
		} catch (Exception e) {
			logger.info("解密Cooke失败:", e);
		}
		return userSession;
	}
	
	/**
	 * 设置用户
	 * @param request
	 * @return
	 */
	public static void putUser(HttpServletRequest request,HttpServletResponse response, UserSession us){
		String jsonObj = JsonUtil.getJsonStringFromObject(us);
		try {
			jsonObj = DES3Util.encodeCookie(jsonObj);
		} catch (Exception e) {
			logger.info("加密Cookie失败", e);
		}
		Tools.setCookie(request, response, Constants.USER_SESSION, jsonObj, Constants.COOKIE_ALIVE_SIX_HOUR);
	}
	
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 */
	public static void invalidUser(HttpServletRequest request,HttpServletResponse response) {
		Tools.delCookieByName(request, response, Constants.WEIXIN_OPENID);
		Tools.delCookieByName(request, response, Constants.USER_SESSION);
		for(int i = 0 ;i<10;i++){
			Tools.delCookieByName(request, response, "loot_ShareLjFlag_Key"+i);
		}
		
	}
	
//	public static Long getUsrOnlyId(HttpServletRequest request){
//		String userID = Tools.getCookieValueByName(request,Constants.GLOBAL_COOKIE_NAME_USRONLYID);
//		if(userID!=null && userID.length()>0){
//			logger.info("当前访问的USRONLYID为"+userID);
//			return Long.parseLong(userID);
//		}else{
//			return 10000008179L;
//		}
//	}
//	
//    public static String getUsrName(HttpServletRequest request) {
//		String usrName = Tools.getCookieValueByName(request,Constants.GLOBAL_COOKIE_NAME_USRLOGINID);
//		if(usrName != null){
//			return usrName;
//		}
//		return "";
//	}
	
	/**
	 * 设置微信OPENID
	 * @param request
	 * @return
	 */
	public static void putWeixinOpenid(HttpServletRequest request,HttpServletResponse response,Map map){
		String jsonObj = JsonUtil.getJsonStringFromObject(map);
		try {
			jsonObj = DES3Util.encode(jsonObj);
		} catch (Exception e) {
			logger.info("设置OPENID到co加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Tools.delCookieByName(request, response, Constants.WEIXIN_OPENID);
		
		Tools.setCookie(request, response, Constants.WEIXIN_OPENID, jsonObj, Constants.WEIXIN_OPENID_COOKIE_ALIVE_TWO_WEEK);
	}
	
	/**
	 * 从COOKIE中获取OPENID
	 * @param request
	 * @return
	 */
	public static Map getWeixinOpenid(HttpServletRequest request){
		Map map = new HashMap();
		String usJson = Tools.getCookieValueByName(request,Constants.WEIXIN_OPENID);
		try {
			if (usJson!=null && usJson.length()>0) {
				usJson = java.net.URLDecoder.decode(DES3Util.decode(usJson),"UTF-8");
				map = (Map) JsonUtil.getObjectFromJsonString(usJson, Map.class);
			}
		}catch (Exception e) {
			logger.info("获取openid失败:" + e.getMessage());
			e.printStackTrace();
		}
		//TODO
		String encrypt_key = String.valueOf(map.get("mediaUid")).replaceAll(" ", "+");
		Map rsaResultMap =  new HashMap(); 
		try {
			rsaResultMap = RSA.getPrivate_Key_Deciphering(encrypt_key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("********private********[openid]RSAString:"+encrypt_key);
		logger.info("********private********[openId]String:"+rsaResultMap.get("openId"));
		
		Map resultMap = new HashMap();
		if(null!=rsaResultMap && !"".equalsIgnoreCase(String.valueOf(rsaResultMap.get("openId")))){
			resultMap.put("mediaUid", String.valueOf(rsaResultMap.get("openId")));
		}
		
		return resultMap;
	}
	
	
	
	/**
	 * 设置分享链接cookie（每个链接 每天只可以抢一次）
	 * @param request
	 * @return
	 */
	public static void putLootShareLj(HttpServletRequest request,HttpServletResponse response,Map map,String lootShareLjFlagKey){
		String jsonObj = JsonUtil.getJsonStringFromObject(map);
		try {
			jsonObj = DES3Util.encode(jsonObj);
		} catch (Exception e) {
			logger.info("分享人参数手机号，时间，加密失败失败:" + e.getMessage());
			e.printStackTrace();
		}
		Tools.delCookieByName(request, response, lootShareLjFlagKey);
		
		Tools.setCookie(request, response, lootShareLjFlagKey, jsonObj, 24 * 60 * 60);
	}
	
	/**
	 * 从COOKIE中获取OPENID
	 * @param request
	 * @return
	 */
	public static String getLootShareLj(HttpServletRequest request,String lootShareLjFlagKey){
		Map map = new HashMap();
		String usJson = Tools.getCookieValueByName(request,lootShareLjFlagKey);
		try {
			if (usJson!=null && usJson.length()>0) {
				usJson = java.net.URLDecoder.decode(DES3Util.decode(usJson),"UTF-8");
				map = (Map) JsonUtil.getObjectFromJsonString(usJson, Map.class);
			}
		}catch (Exception e) {
			logger.info("获取lootShareLjFlagValue失败:" + e.getMessage());
			e.printStackTrace();
		}
		//TODO
		String lootShareLjFlagValue = String.valueOf(map.get("lootShareLjFlagValue")).replaceAll(" ", "+");
		
		logger.info("********[lootShareLjFlagKey]:"+lootShareLjFlagKey+"*********[lootShareLjFlagValue]:"+lootShareLjFlagValue);
		return lootShareLjFlagValue;
	}
	
}


