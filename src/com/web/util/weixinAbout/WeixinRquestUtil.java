package com.web.util.weixinAbout;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.course.pojo.TemplateData;
import com.web.util.weixinAbout.course.pojo.WeixinOauth2Token;
import com.web.util.weixinAbout.course.pojo.WxTemplate;
/**
 * @author wangenlai
 * @date 2015年9月13日15:46:02
 */
public class WeixinRquestUtil {
	private static Log log = LogFactory.getLog(WeixinRquestUtil.class);

	
	/***
	 * @author wangenlai
	 * 获取OPENID用户唯一标识
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static WeixinOauth2Token getOauth2AccessToken(Map<String, String> map) throws Exception {
		String requestParam_openId = "appid="+map.get("appid")+"&secret="+map.get("appServlet")+"&code="+map.get("code")+"&grant_type=authorization_code";
		// 刷新网页授权凭证
		WeixinOauth2Token wat = null;
		log.info("############微信请求地址："+WeixinRateUrlConfig.getValue("weixin_openid_Rate_Url")+"**********获取OPENID请求数据："+requestParam_openId);
		String jsonData = CommonUtil.weixinOrderMetaData(WeixinRateUrlConfig.getValue("weixin_openid_Rate_Url"), "POST",requestParam_openId);
		
		JSONObject  jasonObject = JSONObject.fromObject(jsonData);
		if (null != jasonObject) {
			Map<String,String> resultMap = (Map<String,String>)jasonObject;
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(resultMap.get("access_token"));
				wat.setExpiresIn(Integer.parseInt(String.valueOf(resultMap.get("expires_in"))));
				wat.setRefreshToken(resultMap.get("refresh_token"));
				wat.setOpenId(resultMap.get("openid"));
				wat.setScope(resultMap.get("scope"));
				log.info("获取openid系列响应值：access_token="+resultMap.get("access_token")+
						"&expires_in="+String.valueOf(resultMap.get("expires_in"))+
						"&refresh_token="+resultMap.get("refresh_token")+
						"&openid="+resultMap.get("openid")+
						"&scope="+resultMap.get("scope"));
			} catch (Exception e) {
				wat = null;
//				int errorCode = Integer.parseInt(resultMap.get("errcode"));
				String errorMsg = resultMap.get("errmsg");
				log.info("获取网页授权凭证失败 "+1111+" errorMsg:"+errorMsg);
			}
		}
		return wat;
	}
	
	
	/***
	 * @author wangenlai
	 * 获取全局TOKEN
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String getAllToken(Map<String, String> map) throws Exception {
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getAllToken","");
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				log.info("[获取allToken值]失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			if (null != jsonObject) { 
				resultMap = (Map<String,Object>)jsonObject;
			}
			
			String accessToken ="";
			// 查询SDK签名成功
			if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
				if(null ==jsonObject.get("ACCESSTOKEN") || "".equalsIgnoreCase(String.valueOf(jsonObject.get("ACCESSTOKEN")))){
					String requestParam_openId = "grant_type=client_credential&appid="+map.get("appid")+"&secret="+map.get("appServlet");
					// 刷新网页授权凭证
					log.info("############微信请求地址："+WeixinRateUrlConfig.getValue("weixin_all_token")+"**********获取全局token请求数据："+requestParam_openId);
					String jsonData = CommonUtil.get_weixinOrderMetaData(WeixinRateUrlConfig.getValue("weixin_all_token"), "GET",requestParam_openId);
					
					JSONObject  jasonObject = JSONObject.fromObject(jsonData);
					if (null != jasonObject) {
						Map<String,String> accessTokenResultMap = (Map<String,String>)jasonObject;
						try {
							accessToken = accessTokenResultMap.get("access_token");
							log.info("获取全局token系列响应值：access_token="+accessTokenResultMap.get("access_token"));
						} catch (Exception e) {
							String errorMsg = accessTokenResultMap.get("errmsg");
							log.info("获取全局token失败 "+1111+" errorMsg:"+errorMsg);
						}
					}
					
					Map insertMap = new HashMap();
					insertMap.put("accessToken", accessToken);//全局TOKEN
					String param = CommonUtil.getParam(insertMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						log.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/insertAllToken",param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						log.info("[添加AllToken签名]响应解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject2 = JSONObject.fromObject(resultMsg);
					if (Consts.SUCCESS_CODE.equals(jsonObject2.get("rescode"))) {
						log.info("*********************ADD DATE***accessToken :"+accessToken);
					}else{
						log.error("********************添加AllToken失败");
					}
				}else{
					Integer inviteTime = 10;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					Calendar time1 = Calendar.getInstance();
					try {
						time1.setTime(sdf.parse(String.valueOf(jsonObject.get("UPDATE_TIME"))));
					} catch (ParseException e2) {
						e2.printStackTrace();
					}
					time1.add(Calendar.MINUTE, inviteTime);
					log.info("***************"+sdf.format(time1.getTime()));
					Calendar time2= Calendar.getInstance();
					time2.setTime(new Date());
					log.info("***************"+sdf.format(new Date()));
					//例：发送120分钟以后的时间 与当前时间比较 如果在120分钟有效时间内，正常比较，过期动态提示错误
					if(time1.after(time2)){
						accessToken = String.valueOf(resultMap.get("ACCESSTOKEN"));
						log.info("*********************YOUXIAO DATE***accessToken :"+accessToken);
					}else{
						String requestParam_openId = "grant_type=client_credential&appid="+map.get("appid")+"&secret="+map.get("appServlet");
						// 刷新网页授权凭证
						log.info("############微信请求地址："+WeixinRateUrlConfig.getValue("weixin_all_token")+"**********获取全局token请求数据："+requestParam_openId);
						String jsonData = CommonUtil.get_weixinOrderMetaData(WeixinRateUrlConfig.getValue("weixin_all_token"), "GET",requestParam_openId);
						
						JSONObject  jasonObject = JSONObject.fromObject(jsonData);
						if (null != jasonObject) {
							Map<String,String> accessTokenResultMap = (Map<String,String>)jasonObject;
							try {
								accessToken = accessTokenResultMap.get("access_token");
								log.info("获取全局token系列响应值：access_token="+accessTokenResultMap.get("access_token"));
							} catch (Exception e) {
								String errorMsg = accessTokenResultMap.get("errmsg");
								log.info("获取全局token失败 "+1111+" errorMsg:"+errorMsg);
							}
						}
						
						Map tokenMap = new HashMap();
						tokenMap.put("accessToken", accessToken);//全局TOKEN
						tokenMap.put("createPer", "otXu5swhlifvm8B2u9DoGJc5yclo");//全局TOKEN
						String param = CommonUtil.getParam(tokenMap);
						try {
							param = DES3Util.encode(param);
						} catch (Exception e) {
							log.info("加密失败:" + e.getMessage());
							e.printStackTrace();
						}
						resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/updateAllToken",param);
						try {
							resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
									"UTF-8");
						} catch (Exception e) {
							log.info("[修改AllToken]响应解密失败:" + e.getMessage());
							e.printStackTrace();
						}
						JSONObject jsonObject2 = JSONObject.fromObject(resultMsg);
						if (Consts.SUCCESS_CODE.equals(jsonObject2.get("rescode"))) {
							log.info("*********************UPDATE DATE***accessToken :"+accessToken);
						}else{
							log.error("********************修改AllToken失败");
						}
					}
				}
			}else{
				log.info("*******************AllToken****error");
			}
		return accessToken;
	}
	
	
	/***
	 * 获取签名凭证
	 * @author wangenlai
	 * @param timestamp 时间戳
	 * @param url 
	 * @param noncestr
	 * @param appid
	 * @param appServlet
	 * @return
	 */
	public static Map<String,String>  getSHAoneURL(String timestamp, String url, String noncestr,String appid,String appServlet) throws Exception{
		String requestParam_token = "grant_type=client_credential&appid="+appid+"&secret="+appServlet+"";
		Map<String,String> mapJson = (Map<String,String>)JSONObject.fromObject(CommonUtil.weixinOrderMetaData(WeixinRateUrlConfig.getValue("weixin_openid_token"), "POST",requestParam_token));
		String accessToken = String.valueOf(mapJson.get("access_token"));
		log.info("**********获取用户accessToken，用于分享验签*********"+accessToken);
		
		//根据以上接口得到的用户access_token信息，来获取票据Tickt信息
		String requestParam_ticket="access_token="+accessToken+"&type=jsapi";
		String tokenTickt = CommonUtil.weixinOrderMetaData(WeixinRateUrlConfig.getValue("weixin_openid_ticket"), "POST",requestParam_ticket);
		log.info("**********获取验签票据信息TokenTickt*********"+tokenTickt);
		
		Map<String,String> tokenTicktMap = (Map<String,String>)JSONObject.fromObject(tokenTickt);
		String shaURL = "jsapi_ticket="+String.valueOf(tokenTicktMap.get("ticket"))+
                "&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;

		Map<String,String> mapt = new HashMap<String,String>();
		mapt.put("ticket", String.valueOf(tokenTicktMap.get("ticket")));
		mapt.put("shaURL", shaURL);
		mapt.put("accessToken", accessToken);
		return mapt;
	}
	
	public static String getSHAoneURLTOKEN(String timestamp, String url,
			String noncestr, String accessToken, String jsapiTicket) {
		// TODO Auto-generated method stub
		String shaURL = "jsapi_ticket="+jsapiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		return shaURL;
	}
	
	
	/***
	 * A:s
	 * 邀请分享验签：与页面交互    
	 * @author  wangenlai
	 * @param request
	 */
	public static void getSignature(HttpServletRequest request){
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			UserSession us = UserCookieUtil.getUser(request);
			
			String phoneNum="";
			if(null == us || null == us.getMobile()){
				//用户表示验证机制（通过微信标识OPENID）
				Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLoginLS(request);
				phoneNum  = userInfoMap.get("mobile");
			}else{
				phoneNum = us.getMobile();
			}
			request.removeAttribute("invitationCd");
			request.setAttribute("invitationCd",phoneNum);
			
			String shaOneUrl = request.getRequestURI();
			if(null!=request.getQueryString() && !"".equalsIgnoreCase(request.getQueryString())){
				shaOneUrl = shaOneUrl+"?"+request.getQueryString();
			}
			
			log.info("***********任意分享页面，原验签请求地址："+shaOneUrl);
			shaOneUrl = shaOneUrl.replaceAll("/webservice", "");
			log.info("***********任意分享页面，截取后验签请求地址："+shaOneUrl);
			
			String appid =  WeixinRateUrlConfig.getValue("weixin.appid");
			String appidServlet = WeixinRateUrlConfig.getValue("weixin.appservlet");
			
			Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
			String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
			
			String  jsapiTicket="";
			String  accessToken="";
			String timestamp ="1422935886";
			String noncestr = "5RrybhmXWOxyYrj6";
			String url =WeixinRateUrlConfig.getValue("weixin.hostUrl")+shaOneUrl;
			String signature = "";
			
			
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getSignature","");
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				log.info("[获取SIGNTURE签名]失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			if (null != jsonObject) { 
				resultMap = (Map<String,Object>)jsonObject;
			}
			
			Map map = new HashMap();
			// 查询SDK签名成功
			if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
				if(null ==jsonObject.get("JSAPI_TICKET") || "".equalsIgnoreCase(String.valueOf(jsonObject.get("JSAPI_TICKET")))){
					//data：获取签名凭证URL
					Map<String,String> data =new HashMap<String,String>();
					try {
						data = WeixinRquestUtil.getSHAoneURL(timestamp,url,noncestr,appid,appidServlet);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					String ticket = String.valueOf(data.get("ticket"));
					String shaURL = String.valueOf(data.get("shaURL"));
					accessToken = String.valueOf(data.get("accessToken"));
					
					//signature：签名SHA加密
					signature = new ShaOne().getDigestOfString(shaURL.getBytes());
					map.put("ticket", ticket);//临时票据
					map.put("accessToken", accessToken);//全局TOKEN
					map.put("createPer", "otXu5swhlifvm8B2u9DoGJc5yclo");//创建微信号
					String param = CommonUtil.getParam(map);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						log.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/insertSignature",param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						log.info("[添加SIGNTURE签名]响应解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject2 = JSONObject.fromObject(resultMsg);
					if (Consts.SUCCESS_CODE.equals(jsonObject2.get("rescode"))) {
						log.info("*****************ADD DATE****signature:"+signature);
						log.info("*********************ADD DATE***timestamp :"+timestamp);
						log.info("*********************ADD DATE***noncestr :"+noncestr);
						log.info("*********************ADD DATE***url :"+url);
						log.info("*********************ADD DATE***openId :"+mediaUid);
						log.info("*********************ADD DATE***accessToken :"+accessToken);
						log.info("*********************ADD DATE***ticket :"+ticket);
						log.info("*********************ADD DATE***shaURL :"+shaURL);
					}else{
						log.error("********************添加签名失败");
					}
				}else{
					Integer inviteTime = 120;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					Calendar time1 = Calendar.getInstance();
					try {
						time1.setTime(sdf.parse(String.valueOf(jsonObject.get("UPDATE_TIME"))));
					} catch (ParseException e2) {
						e2.printStackTrace();
					}
					time1.add(Calendar.MINUTE, inviteTime);
					
					log.info("***************"+sdf.format(time1.getTime()));
					
					Calendar time2= Calendar.getInstance();
					time2.setTime(new Date());
					
					log.info("***************"+sdf.format(new Date()));
					//例：发送120分钟以后的时间 与当前时间比较 如果在120分钟有效时间内，正常比较，过期动态提示错误
					if(time1.after(time2)){
						jsapiTicket = String.valueOf(resultMap.get("JSAPI_TICKET"));
						accessToken = String.valueOf(resultMap.get("ACCESSTOKEN"));
						
						//data：获取签名凭证URL
						String data = WeixinRquestUtil.getSHAoneURLTOKEN(timestamp,url,noncestr,accessToken,jsapiTicket);
						//signature：签名SHA加密
						signature = new ShaOne().getDigestOfString(data.getBytes());
						
						//noncestr：随机数
						log.info("*********************YOUXIAO DATE***noncestr :"+noncestr);
						log.info("*********************YOUXIAO DATE***url :"+url);
						log.info("*********************YOUXIAO DATE***openId :"+mediaUid);
						log.info("*********************YOUXIAO DATE***accessToken :"+accessToken);
						log.info("*********************YOUXIAO DATE***ticket :"+jsapiTicket);
						log.info("*********************YOUXIAO DATE***shaURL :"+data);
						log.info("********************signature签名有效期获取成功");
						log.info("*****************YOUXIAO DATE****signature:"+signature);
						log.info("*********************YOUXIAO DATE***timestamp :"+timestamp);
					}else{
						//data：获取签名凭证URL
						Map<String,String> data = new HashMap<String,String>();;
						try {
							data = WeixinRquestUtil.getSHAoneURL(timestamp,url,noncestr,appid,appidServlet);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						String ticket = String.valueOf(data.get("ticket"));
						String shaURL = String.valueOf(data.get("shaURL"));
						//signature：签名SHA加密
						signature = new ShaOne().getDigestOfString(shaURL.getBytes());
						accessToken = String.valueOf(data.get("accessToken"));
						
						map = new HashMap();
						map.put("ticket", ticket);//临时票据
						map.put("accessToken", accessToken);//全局TOKEN
						map.put("createPer", "otXu5swhlifvm8B2u9DoGJc5yclo");//创建微信号
						String param = CommonUtil.getParam(map);
						try {
							param = DES3Util.encode(param);
						} catch (Exception e) {
							log.info("加密失败:" + e.getMessage());
							e.printStackTrace();
						}
						resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/updateSignature",param);
						try {
							resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
									"UTF-8");
						} catch (Exception e) {
							log.info("[添加SIGNTURE签名]响应解密失败:" + e.getMessage());
							e.printStackTrace();
						}
						JSONObject jsonObject2 = JSONObject.fromObject(resultMsg);
						if (Consts.SUCCESS_CODE.equals(jsonObject2.get("rescode"))) {
							log.info("********************修改签名成功,修改人标识："+mediaUid);
							log.info("*****************UPDATE DATE****signature:"+signature);
							log.info("*********************UPDATE DATE***timestamp :"+timestamp);
							log.info("*********************UPDATE DATE***noncestr :"+noncestr);
							log.info("*********************UPDATE DATE***url :"+url);
							log.info("*********************UPDATE DATE***openId :"+mediaUid);
							log.info("*********************UPDATE DATE***accessToken :"+accessToken);
							log.info("*********************UPDATE DATE***ticket :"+ticket);
							log.info("*********************UPDATE DATE***shaURL :"+shaURL);
						}else{
							log.error("********************修改签名失败");
						}
					}
				}
				request.removeAttribute("signature");
				request.setAttribute("signature",signature);
			}else{
				log.info("*******************getSignature****error");
			}
		}
	}
	
	/***
	 * 用于获取accessToken 非全局（验签）token值等验签信息信息
	 * @author  wangenlai
	 * @param request
	 */
	public static Map<String,String> getTokenAndSignature(HttpServletRequest request){
		String ua = request.getHeader("user-agent").toLowerCase();
		Map<String,String> resultInfoMap = new HashMap<String,String>();
		
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			String shaOneUrl = request.getRequestURI();
			if(null!=request.getQueryString() && !"".equalsIgnoreCase(request.getQueryString())){
				shaOneUrl = shaOneUrl+"?"+request.getQueryString();
			}
			
			log.info("***********任意分享页面，原验签请求地址："+shaOneUrl);
			shaOneUrl = shaOneUrl.replaceAll("/webservice", "");
			log.info("***********任意分享页面，截取后验签请求地址："+shaOneUrl);
			
			String appid =  WeixinRateUrlConfig.getValue("weixin.appid");
			String appidServlet = WeixinRateUrlConfig.getValue("weixin.appservlet");
			
			String  jsapiTicket="";
			String  accessToken="";
			String timestamp ="1422935886";
			String noncestr = "5RrybhmXWOxyYrj6";
			String url =WeixinRateUrlConfig.getValue("weixin.hostUrl")+shaOneUrl;
			String signature = "";
			
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getSignature","");
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				log.info("[获取SIGNTURE签名]失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			if (null != jsonObject) { 
				resultMap = (Map<String,Object>)jsonObject;
			}
			
			Map map = new HashMap();
			// 查询SDK签名成功
			if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
				if(null ==jsonObject.get("JSAPI_TICKET") || "".equalsIgnoreCase(String.valueOf(jsonObject.get("JSAPI_TICKET")))){
					//data：获取签名凭证URL
					Map<String,String> data =new HashMap<String,String>();
					try {
						data = WeixinRquestUtil.getSHAoneURL(timestamp,url,noncestr,appid,appidServlet);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					String ticket = String.valueOf(data.get("ticket"));
					String shaURL = String.valueOf(data.get("shaURL"));
					accessToken = String.valueOf(data.get("accessToken"));
					
					//signature：签名SHA加密
					signature = new ShaOne().getDigestOfString(shaURL.getBytes());
					map.put("ticket", ticket);//临时票据
					map.put("accessToken", accessToken);//全局TOKEN
					map.put("createPer", "otXu5swhlifvm8B2u9DoGJc5yclo");//创建微信号
					String param = CommonUtil.getParam(map);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						log.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/insertSignature",param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						log.info("[添加SIGNTURE签名]响应解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject2 = JSONObject.fromObject(resultMsg);
					if (Consts.SUCCESS_CODE.equals(jsonObject2.get("rescode"))) {
						log.info("*****************ADD DATE****signature:"+signature);
						log.info("*********************ADD DATE***timestamp :"+timestamp);
						log.info("*********************ADD DATE***noncestr :"+noncestr);
						log.info("*********************ADD DATE***url :"+url);
						log.info("*********************ADD DATE***accessToken :"+accessToken);
						log.info("*********************ADD DATE***ticket :"+ticket);
						log.info("*********************ADD DATE***shaURL :"+shaURL);
					}else{
						log.error("********************添加签名失败");
					}
				}else{
					Integer inviteTime = 120;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					Calendar time1 = Calendar.getInstance();
					try {
						time1.setTime(sdf.parse(String.valueOf(jsonObject.get("UPDATE_TIME"))));
					} catch (ParseException e2) {
						e2.printStackTrace();
					}
					time1.add(Calendar.MINUTE, inviteTime);
					
					log.info("***************"+sdf.format(time1.getTime()));
					
					Calendar time2= Calendar.getInstance();
					time2.setTime(new Date());
					
					log.info("***************"+sdf.format(new Date()));
					//例：发送120分钟以后的时间 与当前时间比较 如果在120分钟有效时间内，正常比较，过期动态提示错误
					if(time1.after(time2)){
						jsapiTicket = String.valueOf(resultMap.get("JSAPI_TICKET"));
						accessToken = String.valueOf(resultMap.get("ACCESSTOKEN"));
						
						//data：获取签名凭证URL
						String data = WeixinRquestUtil.getSHAoneURLTOKEN(timestamp,url,noncestr,accessToken,jsapiTicket);
						//signature：签名SHA加密
						signature = new ShaOne().getDigestOfString(data.getBytes());
						
						//noncestr：随机数
						log.info("*********************YOUXIAO DATE***noncestr :"+noncestr);
						log.info("*********************YOUXIAO DATE***url :"+url);
						log.info("*********************YOUXIAO DATE***accessToken :"+accessToken);
						log.info("*********************YOUXIAO DATE***ticket :"+jsapiTicket);
						log.info("*********************YOUXIAO DATE***shaURL :"+data);
						log.info("********************signature签名有效期获取成功");
						log.info("*****************YOUXIAO DATE****signature:"+signature);
						log.info("*********************YOUXIAO DATE***timestamp :"+timestamp);
					}else{
						//data：获取签名凭证URL
						Map<String,String> data = new HashMap<String,String>();;
						try {
							data = WeixinRquestUtil.getSHAoneURL(timestamp,url,noncestr,appid,appidServlet);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						String ticket = String.valueOf(data.get("ticket"));
						String shaURL = String.valueOf(data.get("shaURL"));
						//signature：签名SHA加密
						signature = new ShaOne().getDigestOfString(shaURL.getBytes());
						accessToken = String.valueOf(data.get("accessToken"));
						
						map = new HashMap();
						map.put("ticket", ticket);//临时票据
						map.put("accessToken", accessToken);//全局TOKEN
						map.put("createPer", "otXu5swhlifvm8B2u9DoGJc5yclo");//创建微信号
						String param = CommonUtil.getParam(map);
						try {
							param = DES3Util.encode(param);
						} catch (Exception e) {
							log.info("加密失败:" + e.getMessage());
							e.printStackTrace();
						}
						resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/updateSignature",param);
						try {
							resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
									"UTF-8");
						} catch (Exception e) {
							log.info("[添加SIGNTURE签名]响应解密失败:" + e.getMessage());
							e.printStackTrace();
						}
						JSONObject jsonObject2 = JSONObject.fromObject(resultMsg);
						if (Consts.SUCCESS_CODE.equals(jsonObject2.get("rescode"))) {
							log.info("*****************UPDATE DATE****signature:"+signature);
							log.info("*********************UPDATE DATE***timestamp :"+timestamp);
							log.info("*********************UPDATE DATE***noncestr :"+noncestr);
							log.info("*********************UPDATE DATE***url :"+url);
							log.info("*********************UPDATE DATE***accessToken :"+accessToken);
							log.info("*********************UPDATE DATE***ticket :"+ticket);
							log.info("*********************UPDATE DATE***shaURL :"+shaURL);
						}else{
							log.error("********************修改签名失败");
						}
					}
				}
				resultInfoMap.put("signature", signature);
				resultInfoMap.put("timestamp", timestamp);
				resultInfoMap.put("noncestr", noncestr);
				resultInfoMap.put("url", url);
				resultInfoMap.put("accessToken", accessToken);
				log.info("*******************获取token&signature&noncestr等数据****"+accessToken);
			}else{ 
				log.info("*******************getSignature****error");
			}
		}
		return resultInfoMap;
	}
	
	
	
	/***
	 * 调用微信API接口发送微信模板消息
	 * 	//recharge(充值)，buy（购买），withdraw（提现）,increateInterest（加息）
	 * @return
	 * @throws Exception
	 */
	public static String getWeixinTemplateId(HttpServletRequest request,String flag,Map<String,String> dataMap) throws Exception {
		String errcode = "-1";
		
		//获取全局TOKEN
		Map<String,String> requestMap = new HashMap<String,String>();
		requestMap.put("appid", WeixinRateUrlConfig.getValue("weixin.appid"));
		requestMap.put("appServlet", WeixinRateUrlConfig.getValue("weixin.appservlet"));
		String allToken = WeixinRquestUtil.getAllToken(requestMap);
		
		//获取微信公众平台模板ID
		Map<String,String> resultMap = new HashMap<String,String>();
		
		if(!"".equalsIgnoreCase(allToken) && null!=allToken){
			String sendModelInfo ="";
			
			if("recharge".equalsIgnoreCase(flag)){
				//发送模板消息（充值模板:weixin.templejson_recharge）
				Map<String,String> templeMap = new HashMap<String,String>();
				templeMap.put("url", dataMap.get("url"));
				templeMap.put("openid", dataMap.get("openid"));
				templeMap.put("template_id", WeixinRateUrlConfig.getValue("weixin.templejson_recharge"));
				templeMap.put("productInfo", dataMap.get("productInfo"));//本次充值
				templeMap.put("contentInfo", "您好，已成功充值联璧钱包\n请点击查看详情，购买理财产品，赚钱收益！");
				sendModelInfo =getTempleJson(templeMap,"recharge");
			}
			if("buy".equalsIgnoreCase(flag)){
				//发送模板消息（购买模板:weixin.templejson_buy）
				Map<String,String> templeMap = new HashMap<String,String>();
				templeMap.put("url", dataMap.get("url"));
				templeMap.put("openid", dataMap.get("openid"));
				templeMap.put("template_id", WeixinRateUrlConfig.getValue("weixin.templejson_buy"));
				templeMap.put("productInfo", dataMap.get("productInfo"));
				templeMap.put("number", dataMap.get("number"));
				templeMap.put("contentInfo", "您好，您在联璧钱包购买了产品"+dataMap.get("productInfo")+"\n请点击查询详情");
				sendModelInfo =getTempleJson(templeMap,"buy");
			}
			if("withdraw".equalsIgnoreCase(flag)){
				//发送模板消息（提现模板:weixin.templejson_withdraw）
				Map<String,String> templeMap = new HashMap<String,String>();
				templeMap.put("url", dataMap.get("url"));
				templeMap.put("openid", dataMap.get("openid"));
				templeMap.put("template_id", WeixinRateUrlConfig.getValue("weixin.templejson_withdraw"));
				templeMap.put("withdrawNumber", dataMap.get("withdrawNumber"));//到账金额
				templeMap.put("withdrawPoundage", dataMap.get("withdrawPoundage"));//手续费
				templeMap.put("contentInfo", "您好，您在联璧钱包已成功发起提现申请\n请点击查看交易记录");
				sendModelInfo =getTempleJson(templeMap,"withdraw");
			} 
			if("increateInterest".equalsIgnoreCase(flag)){
				//发送模板消息（提现模板:weixin.templejson_withdraw）
				Map<String,String> templeMap = new HashMap<String,String>();
				templeMap.put("url", dataMap.get("url"));
				templeMap.put("openid", dataMap.get("openid"));
				templeMap.put("template_id", WeixinRateUrlConfig.getValue("weixin.templejson_increateInterest"));
				templeMap.put("increateInterestRate", dataMap.get("increateInterestRate"));//加息利率
				templeMap.put("contentInfo", "为了回馈客户，小联推出加息活动，感谢您长期以来对联璧钱包的支持。用心，是小联对您的承诺。\n 请点击进入查看使用");
				sendModelInfo =getTempleJson(templeMap,"increateInterest");
			} 
			String requestParam_openId = "?access_token="+allToken;
			log.info("############微信请求地址："+WeixinRateUrlConfig.getValue("weixin_template_id")+"**********获取OPENID请求数据："+requestParam_openId);
			String jsonData = CommonUtil.json_weixinOrderMetaData(WeixinRateUrlConfig.getValue("weixin_send_template")+requestParam_openId, "POST",sendModelInfo);
			
			JSONObject  jasonObject = JSONObject.fromObject(jsonData);
			if (null != jasonObject) {
				resultMap = (Map<String,String>)jasonObject;
				try {
					//{"errcode":0,"errmsg":"ok","msgid":400640388}
					log.info("获取errcode系列响应值：errcode="+jsonData);
					errcode = String.valueOf(resultMap.get("errcode"));
					log.info("**********取值封装：errcode"+errcode);
				} catch (Exception e) {
					String errorMsg = resultMap.get("errmsg");
					e.printStackTrace();
					log.info("获取网页授权凭证失败 "+1111+" errorMsg:"+jsonData);
				}
			}
		}
		return errcode;
	}
	
	
	/***
	 * 微信模板消息拼装(充值成功(recharge)，购买成功(buy)，提现成功(withdraw))
	 * @param map
	 * @return
	 */
	public static String getTempleJson(Map<String,String> map,String flag){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String date2 = sdf2.format(new Date());
		String jsonString ="";
		//充值微信消息模板
		if("recharge".equalsIgnoreCase(flag)){
				WxTemplate t = new WxTemplate();  
		        t.setUrl(map.get("url"));  
		        t.setTouser(map.get("openid"));  
		        t.setTopcolor("#000000");  
		        t.setTemplate_id(map.get("template_id"));
		        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
		        
		        //first（首标）
		        TemplateData tpd1 = new TemplateData();  
		        tpd1.setColor("#000000");  
		        tpd1.setValue("恭喜您成功充值！");  
		        m.put("first", tpd1);  
		        
		        //keyword1（本次充值）
		        TemplateData tpd2 = new TemplateData();  
		        tpd2.setColor("#173177");  
				tpd2.setValue(map.get("productInfo"));
		        m.put("keyword1", tpd2);
		        
		        //keyword2（充值时间）
		        TemplateData tpd3 = new TemplateData();  
		        tpd3.setColor("#173177");  
		        tpd3.setValue(date);
		        m.put("keyword2", tpd3);  
		        
		        //remark(下面内容)
		        TemplateData tpd4 = new TemplateData();  
		        tpd4.setColor("#000000");  
		        tpd4.setValue(map.get("contentInfo")); 
		        m.put("remark", tpd4);
		        
		        t.setData(m); 
		        jsonString = JSONObject.fromObject(t).toString();
		}
		//购买微信消息模板
		if("buy".equalsIgnoreCase(flag)){
				WxTemplate t = new WxTemplate();  
		        t.setUrl(map.get("url"));  
		        t.setTouser(map.get("openid"));  
		        t.setTopcolor("#000000");  
		        t.setTemplate_id(map.get("template_id"));
		        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
		        
		        //first（首标）
		        TemplateData tpd1 = new TemplateData();  
		        tpd1.setColor("#000000");  
		        tpd1.setValue("您好,您已购买成功。");  
		        m.put("first", tpd1);
		        
		        //keyword1 (产品名字)
		        TemplateData tpd2 = new TemplateData();  
		        tpd2.setColor("#173177");  
		        tpd2.setValue(map.get("productInfo"));  
		        m.put("keyword1", tpd2);
		        
		        //keyword2（购买金额）
		        TemplateData tpd3 = new TemplateData();  
		        tpd3.setColor("#173177");
				tpd3.setValue(map.get("number"));
		        m.put("keyword2", tpd3);  
		        
		        //keyword3（购买时间）
		        TemplateData tpd4 = new TemplateData();  
		        tpd4.setColor("#173177");  
		        tpd4.setValue(date2); 
		        m.put("keyword3", tpd4);  
		        
		        //remark(下面内容)
		        TemplateData tpd5 = new TemplateData();  
		        tpd5.setColor("#000000");  
		        tpd5.setValue(map.get("contentInfo")); 
		        m.put("remark", tpd5);
		        
		        t.setData(m); 
		        jsonString = JSONObject.fromObject(t).toString();
		}
		//提现微信消息模板
		if("withdraw".equalsIgnoreCase(flag)){
				WxTemplate t = new WxTemplate();  
		        t.setUrl(map.get("url"));  
		        t.setTouser(map.get("openid"));  
		        t.setTopcolor("#000000");  
		        t.setTemplate_id(map.get("template_id"));
		        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
		        
		        //first（首标）
		        TemplateData tpd1 = new TemplateData();  
		        tpd1.setColor("#000000");  
		        tpd1.setValue("提现申请");  
		        m.put("first", tpd1);
		        
		        //keyword1：交易时间
		        TemplateData tpd2 = new TemplateData();  
		        tpd2.setColor("#173177");  
		        tpd2.setValue(date);  
		        m.put("keyword1", tpd2);
		        
		        //keyword2：到账金额
		        TemplateData tpd3 = new TemplateData();  
		        tpd3.setColor("#173177");
				tpd3.setValue(map.get("withdrawNumber"));
		        m.put("keyword2", tpd3);  
		        
		        //keyword3：手续费
		        TemplateData tpd4 = new TemplateData();  
		        tpd4.setColor("#173177");
				tpd4.setValue(map.get("withdrawPoundage"));
		        m.put("keyword3", tpd4);  
		        
		        //remark(下面内容)
		        TemplateData tpd5 = new TemplateData();  
		        tpd5.setColor("#000000");  
		        tpd5.setValue(map.get("contentInfo")); 
		        m.put("remark", tpd5);
		        
		        t.setData(m); 
		        jsonString = JSONObject.fromObject(t).toString();
		}
		
		//加息券微信消息模板
		if("increateInterest".equalsIgnoreCase(flag)){
				WxTemplate t = new WxTemplate();  
		        t.setUrl(map.get("url"));  
		        t.setTouser(map.get("openid"));  
		        t.setTopcolor("#000000");  
		        t.setTemplate_id(map.get("template_id"));
		        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
		        
		        //first（首标）
		        TemplateData tpd1 = new TemplateData();  
		        tpd1.setColor("#000000");  
		        tpd1.setValue("您获赠了联璧钱包加息券");
		        m.put("first", tpd1);  
		        
		        //keyword1（加息利率）
		        TemplateData tpd2 = new TemplateData();  
		        tpd2.setColor("#173177");  
		        tpd2.setValue(map.get("increateInterestRate"));  
		        m.put("keyword1", tpd2);
		        
		        //keyword2（赠送时间）
		        TemplateData tpd3 = new TemplateData();  
		        tpd3.setColor("#173177");  
		        tpd3.setValue(date);
		        m.put("keyword2", tpd3);  
		        
		        //remark(下面内容)
		        TemplateData tpd4 = new TemplateData();  
		        tpd4.setColor("#000000");  
		        tpd4.setValue(map.get("contentInfo")); 
		        m.put("remark", tpd4);
		        
		        t.setData(m); 
		        jsonString = JSONObject.fromObject(t).toString();
		}
		return jsonString;
	}
	
	/***
	 * 发送充值微信模板消息（充值）
	 */
	public static String sendRechargeModelMessage(HttpServletRequest request,Map<String,String> map){
			//微信模板消息
			String errcode="-1";
			
			Map<String, Object> reqJavaMap2 = new LinkedHashMap<String, Object>();
			reqJavaMap2.put("userId", map.get("userId"));
			String param2 = CommonUtil.getParam(reqJavaMap2);
			try {
				param2 = DES3Util.encode(param2);
			} catch (Exception e) {
				log.info("[微信模板消息userid]加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			// 调用service接口
			String resultMsgCash = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/wxuser/queryMediaUid",
					param2);
			String resultMsg ="";
			try {
			       resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsgCash),
						"UTF-8");
			} catch (Exception e) {
				log.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			// 交易密码正确
			if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
				String mediaUid = jsonObject.getString("mediaUid");
				String money="";
				try {
					money = CommonUtil.setFormatMoney(map.get("money"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
					//recharge(充值)
					Map<String,String> dataMap = new HashMap<String,String>();
					dataMap.put("openid", mediaUid);//模板发送openid
					dataMap.put("productInfo", "人民币"+money+"元");//本次充值金额
					dataMap.put("url", WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
					//recharge(充值)，buy（购买），withdraw（提现）
					try {
						errcode = WeixinRquestUtil.getWeixinTemplateId(request,"recharge",dataMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				log.info("该用户未绑定微信，用户Userid："+map.get("userId"));
			}
			return errcode;
	}
	
	
	/***
	 * 发送购买微信模板消息（购买）
	 */
	public static String sendBuyModelMessage(HttpServletRequest request,Map<String,String> map){
			//微信模板消息
			String errcode="-1";
			
			Map<String, Object> reqJavaMap2 = new LinkedHashMap<String, Object>();
			reqJavaMap2.put("userId", map.get("userId"));
			String param2 = CommonUtil.getParam(reqJavaMap2);
			try {
				param2 = DES3Util.encode(param2);
			} catch (Exception e) {
				log.info("[微信模板消息userid]加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			// 调用service接口
			String resultMsgCash = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/wxuser/queryMediaUid",
					param2);
			String resultMsg ="";
			try {
			       resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsgCash),
						"UTF-8");
			} catch (Exception e) {
				log.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			// 交易密码正确
			if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
				String mediaUid = jsonObject.getString("mediaUid");
				String number="";
				try {
					number = CommonUtil.setFormatMoney(map.get("number"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
					//buy（购买）
					Map<String,String> dataMap = new HashMap<String,String>();
					dataMap.put("openid", mediaUid);//模板发送openid
					dataMap.put("productInfo",map.get("productInfo"));//产品类型
					dataMap.put("number",  "人民币"+number+"元");//购买金额
					dataMap.put("url",  WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
					//recharge(充值)，buy（购买），withdraw（提现）
					try {
						errcode = WeixinRquestUtil.getWeixinTemplateId(request,"buy",dataMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					
				}
			} else {
				log.info("该用户未绑定微信，用户Userid："+map.get("userId"));
			}
			return errcode;
	}
	
	
	/***
	 * 发送提现微信模板消息（提现）
	 */
	public static String sendWithdrawModelMessage(HttpServletRequest request,Map<String,String> map){
			//微信模板消息
			String errcode="-1";
		
			Map<String, Object> reqJavaMap2 = new LinkedHashMap<String, Object>();
			reqJavaMap2.put("userId", map.get("userId"));
			String param2 = CommonUtil.getParam(reqJavaMap2);
			try {
				param2 = DES3Util.encode(param2);
			} catch (Exception e) {
				log.info("[微信模板消息userid]加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			// 调用service接口
			String resultMsgCash = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/wxuser/queryMediaUid",
					param2);
			String resultMsg ="";
			try {
			       resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsgCash),
						"UTF-8");
			} catch (Exception e) {
				log.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			// 交易密码正确
			if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
				
				boolean mediaUidExits = jsonObject.has("mediaUid");
				
				String withdrawNumber="";
				try {
					withdrawNumber = CommonUtil.setFormatMoney(map.get("withdrawNumber"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String withdrawPoundage = map.get("withdrawPoundage");
				if(mediaUidExits){
					String mediaUid = jsonObject.getString("mediaUid");
					Map<String,String> dataMap = new HashMap<String,String>();
					dataMap.put("openid", mediaUid);//模板发送openid
					dataMap.put("url", WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
					dataMap.put("withdrawNumber", "人民币"+withdrawNumber+"元");//提现金额
					dataMap.put("withdrawPoundage", withdrawPoundage);//提现手续费
					//recharge(充值)，buy（购买），withdraw（提现）
					try {
						errcode = WeixinRquestUtil.getWeixinTemplateId(request,"withdraw",dataMap);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				log.info("该用户未绑定微信，用户Userid："+map.get("userId"));
			}
			return errcode;
	}
	
    /**
     * 获取下载图片信息（jpg）
     * @param mediaId
     *            文件的id
     * @throws Exception
     */
    public static void saveImageToDisk(HttpServletRequest request,String fileName, String mediaId) throws Exception {
        InputStream inputStream = getInputStream(request,mediaId);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	/**
	    * 根据文件id下载文件
	    * @
	    * @param mediaId
	    *            媒体id
	    * @throws Exception
	    */
	   public static InputStream getInputStream(HttpServletRequest request,String mediaId) {
	       InputStream is = null;
	       Map<String,String> map = getTokenAndSignature(request);
	       String access_token =map.get("accessToken");
	       String url = WeixinRateUrlConfig.getValue("weixin_url_file")+"?access_token="+access_token+"&media_id="+mediaId;
			// 刷新网页授权凭证
		   log.info("############微信请求地址："+WeixinRateUrlConfig.getValue("weixin_url_file")+"**********获取OPENID请求数据："+url);
	       try {

	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	           http.setRequestMethod("GET"); // 必须是get方式请求
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
	           http.connect();
	           // 获取文件转化为byte流
	           is = http.getInputStream();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return is;
	   }

	/***
	 * 发送模板消息
	 * @param request
	 * @return
	 */
	public static String sendWeixinModelMessage(HttpServletRequest request) {
		// TODO Auto-generated method stub
		log.info("=========进入webservice sendWeixinModelMessage方法：");
		String result = "";
		// 解密请求参数
		Map<String, String> paramsMap = CommonUtil.decryptParamtersTwo(request);
		log.info("=========传入的参数："+paramsMap.toString());
		
		String mediaUid = paramsMap.get("mediaUid");
		String functionScope = paramsMap.get("functionScope");
		
		String errcode="-1";
		if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) 
				&& null!=functionScope  && !"".equalsIgnoreCase(functionScope) && !"null".equalsIgnoreCase(functionScope)){
			
			Map<String,String> dataMap = new HashMap<String,String>();
			if("recharge".equalsIgnoreCase(functionScope)){
				String money="";
				try {
					money = CommonUtil.setFormatMoney(paramsMap.get("money"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dataMap.put("openid", mediaUid);//模板发送openid
				dataMap.put("productInfo","人民币"+money+"元");//本次充值金额
				dataMap.put("url", WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
			}
			
			if("buy".equalsIgnoreCase(functionScope)){
				String productInfo = paramsMap.get("productInfo");
				String number="";
				try {
					number = CommonUtil.setFormatMoney(paramsMap.get("number"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//buy（购买）
				dataMap.put("openid", mediaUid);//模板发送openid
				dataMap.put("productInfo",productInfo);//产品类型
				dataMap.put("number",  "人民币"+number+"元");//购买金额
				dataMap.put("url",  WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
			}
			
			if("withdraw".equalsIgnoreCase(functionScope)){
				String withdrawNumber="";
				try {
					withdrawNumber = CommonUtil.setFormatMoney(paramsMap.get("withdrawNumber"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String withdrawPoundage = paramsMap.get("withdrawPoundage");
				dataMap.put("openid", mediaUid);//模板发送openid
				dataMap.put("url", WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
				dataMap.put("withdrawNumber", "人民币"+withdrawNumber+"元");//提现金额
				dataMap.put("withdrawPoundage", withdrawPoundage);//提现手续费
			}
			if("increateInterest".equalsIgnoreCase(functionScope)){
				String increateInterestRateInfo = paramsMap.get("increateInterestRateInfo");
				dataMap.put("openid", mediaUid);//模板发送openid
				dataMap.put("url", WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu");//点击后地址
				dataMap.put("increateInterestRate",increateInterestRateInfo);//获赠礼品
			}
			
			//recharge(充值)，buy（购买），withdraw（提现）,increateInterest（加息）
			try {
				errcode = WeixinRquestUtil.getWeixinTemplateId(request,functionScope,dataMap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return errcode;
	}
}
