package com.web.actions.weixin.trigger;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.common.WeiXinUserDto;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.RSA;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.URLEncodeUtils;
import com.web.util.weixinAbout.WeixinRateUrlConfig;
import com.web.util.weixinAbout.WeixinRquestUtil;
import com.web.util.weixinAbout.course.pojo.WeixinOauth2Token;

/**
 * 
 * ClassName: WeiXinUserAction 
 * @Description: 微信公众号自定义按钮用户触发类
 * @author wangenlai
 * @date 2015年10月9日10:34:30
 */
@Controller
@RequestMapping("/wxTrigger")

public class WeiXinTriggerAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 207212744770578951L;
	private static final Log logger = LogFactory.getLog(WeiXinTriggerAction.class);
	
	/***
	 * TODO：域名待定
	 * 正式环境微信回调路径(动态回调地址)
	 */
	@RequestMapping(value="/getWxCode",method=RequestMethod.GET)
	public String getWxCode(HttpServletRequest request){
		String ua = request.getHeader("user-agent")
		          .toLowerCase();
		if (ua.indexOf("micromessenger") <= 0) {// 非微信浏览器
			String actionScope = request.getParameter("actionScope");
			String actionUrl = "";
			try {
				actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return "redirect:"+actionUrl;
		}
		
		//作用URL地址

		String actionScope = request.getParameter("actionScope");
		
		if(null==actionScope || "".equalsIgnoreCase(actionScope)){
			logger.info("[跳转作用域]参数不可为空");
			return null;
		}else{
			String actionUrl = "";
			try {
				actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
			} catch (Exception e) {
				logger.error("[跳转作用域]DES解密失败");
				e.printStackTrace();
			}
			logger.info("######actionUrl原来的串："+actionUrl);
		}
		
		String appid=WeixinRateUrlConfig.getValue("weixin.appid");//微信应用ID
		String redirctUrlRealy= WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/bingLoginInit?actionScope="+actionScope;//微信URL地址
		
		String redirctUrl=URLEncodeUtils.encodeURL(redirctUrlRealy);
		
		
		String mediaUid = "";
		Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
		if(null != mediaUidMap){
			if(null != mediaUidMap.get("mediaUid")){
				mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				if(null!=mediaUid && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid)){
					String actionUrl = "";
					try {
						actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
					} catch (Exception e) {
						logger.error("[跳转作用域]DES解密失败");
						e.printStackTrace();
					}
					return "redirect:"+actionUrl;
				}
			}
		}
		
		return "redirect:"+WeixinRateUrlConfig.getValue("weixin_Rate_Url")+"?appid="+appid+"&redirect_uri="+redirctUrl+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	}
	
	/***
	 * 分发类
	 */
	@RequestMapping(value="/bingLoginInit",method=RequestMethod.GET)
	public String bingLoginInit(HttpServletRequest request,HttpServletResponse response,Model model){
		//TODO 根据以上接口获取OPENID，嵌套到业务中，初步设想存储到SESSION内，接口从中获取使用
//		WeiXinUserDto userDto = new WeiXinUserDto();
		String openId ="";
		try {
			request.setCharacterEncoding("gb2312");
			response.setCharacterEncoding("gb2312");
			
			// 用户同意授权后，能获取到code
			String code = request.getParameter("code");
			logger.info("******用户同意授权CODE："+code);
			// 用户同意授权
			if (!"authdeny".equals(code)) {
				// 获取网页授权access_token
				Map<String,String> map = new HashMap<String,String>();
				map.put("appid", WeixinRateUrlConfig.getValue("weixin.appid"));
				map.put("appServlet", WeixinRateUrlConfig.getValue("weixin.appservlet"));
				map.put("code", code);
				WeixinOauth2Token weixinOauth2Token = WeixinRquestUtil.getOauth2AccessToken(map);
				// 用户标识
				if(weixinOauth2Token!=null){
					openId = weixinOauth2Token.getOpenId();
				}
				logger.debug("******获取用户OPENID："+openId);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error("获取微信OPENID失败");
		}
//		userDto.setWeixinId(openId);
//		request.getSession().setAttribute("userDto", userDto);
		
		Map<String,String> rsaMap = new HashMap<String,String>();
		rsaMap.put("openId", openId);
      
        String encrypt_key="";
		try {
			encrypt_key = RSA.getPublick_Key_Encrypt(rsaMap);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        logger.info("********public********[openid]String:"+openId);
        logger.info("********public********[openid]RSAString:"+encrypt_key);
        
        Map map = new HashMap();
		map.put("mediaUid", encrypt_key);
		
		UserCookieUtil.putWeixinOpenid(request, response, map);
		
		String actionScope = request.getParameter("actionScope");
		String actionUrl = "";
		try {
			actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
		} catch (Exception e) {
			logger.error("[跳转作用域]DES解密失败");
			e.printStackTrace();
		}
		
		logger.info("######actionUrl原来的串："+actionUrl);
		return "redirect:"+actionUrl;
	}
	
	
	/***
	 * 正式环境微信回调路径(动态回调地址) --推送功能使用
	 */
	@RequestMapping(value="/getWxCodePush",method=RequestMethod.GET)
	public String getWxCodePush(HttpServletRequest request){
		String ua = request.getHeader("user-agent")
		          .toLowerCase();
		if (ua.indexOf("micromessenger") <= 0) {// 非微信浏览器
			String actionScope = request.getParameter("actionScope");
			String actionUrl = "";
			try {
				actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return "redirect:"+actionUrl;
		}
		
		//作用URL地址
		String actionScope = request.getParameter("actionScope");
		
		if(null==actionScope || "".equalsIgnoreCase(actionScope)){
			logger.info("[跳转作用域]参数不可为空");
			return null;
		}else{
			String actionUrl = "";
			try {
				actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
			} catch (Exception e) {
				logger.error("[跳转作用域]DES解密失败");
				e.printStackTrace();
			}
			logger.info("######actionUrl原来的串："+actionUrl);
		}
		Map<String, String> wxPubAcctInfo =getWxPubAcctInfo("lianbi_push");
		if(!Consts.SUCCESS_CODE.equals(wxPubAcctInfo.get("rescode"))){
			logger.info("获取公众账号信息失败");
			return null;
		}
		String appid=wxPubAcctInfo.get("acctAppid");
		String redirctUrlRealy= wxPubAcctInfo.get("acctHosturl")+"/wxTrigger/bingLoginInitPush?actionScope="+actionScope;//微信URL地址
		
		String redirctUrl=URLEncodeUtils.encodeURL(redirctUrlRealy);
		
		
		String mediaUid = "";
		Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
		if(null != mediaUidMap){
			if(null != mediaUidMap.get("mediaUid")){
				mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				if(null!=mediaUid && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid)){
					String actionUrl = "";
					try {
						actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
					} catch (Exception e) {
						logger.error("[跳转作用域]DES解密失败");
						e.printStackTrace();
					}
					return "redirect:"+actionUrl;
				}
			}
		}
		
		return "redirect:"+WeixinRateUrlConfig.getValue("weixin_Rate_Url")+"?appid="+appid+"&redirect_uri="+redirctUrl+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	}
	
	/***
	 * 分发类 --推送功能使用
	 */
	@RequestMapping(value="/bingLoginInitPush",method=RequestMethod.GET)
	public String bingLoginInitPush(HttpServletRequest request,HttpServletResponse response,Model model){
		//TODO 根据以上接口获取OPENID，嵌套到业务中，初步设想存储到SESSION内，接口从中获取使用
//		WeiXinUserDto userDto = new WeiXinUserDto();
		String openId ="";
		try {
			request.setCharacterEncoding("gb2312");
			response.setCharacterEncoding("gb2312");
			
			// 用户同意授权后，能获取到code
			String code = request.getParameter("code");
			logger.info("******用户同意授权CODE："+code);
			// 用户同意授权
			if (!"authdeny".equals(code)) {
				// 获取网页授权access_token
				Map<String,String> map = new HashMap<String,String>();
				Map<String, String> wxPubAcctInfo =getWxPubAcctInfo("lianbi_push");
				if(!Consts.SUCCESS_CODE.equals(wxPubAcctInfo.get("rescode"))){
					logger.info("获取公众账号信息失败");
					return null;
				}
				map.put("appid", wxPubAcctInfo.get("acctAppid"));
				map.put("appServlet", wxPubAcctInfo.get("acctAppsecret"));
				map.put("code", code);
				WeixinOauth2Token weixinOauth2Token = WeixinRquestUtil.getOauth2AccessToken(map);
				// 用户标识
				if(weixinOauth2Token!=null){
					openId = weixinOauth2Token.getOpenId();
				}
				logger.debug("******获取用户OPENID："+openId);
			}
		}catch (Exception e){
			e.printStackTrace();
			logger.error("获取微信OPENID失败");
		}
//		userDto.setWeixinId(openId);
//		request.getSession().setAttribute("userDto", userDto);
		
		Map<String,String> rsaMap = new HashMap<String,String>();
		rsaMap.put("openId", openId);
      
        String encrypt_key="";
		try {
			encrypt_key = RSA.getPublick_Key_Encrypt(rsaMap);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        logger.info("********public********[openid]String:"+openId);
        logger.info("********public********[openid]RSAString:"+encrypt_key);
        
        Map map = new HashMap();
		map.put("mediaUid", encrypt_key);
		
		UserCookieUtil.putWeixinOpenid(request, response, map);
		
		String actionScope = request.getParameter("actionScope");
		String actionUrl = "";
		try {
			actionUrl = new String(Base64.decodeBase64(actionScope),"UTF-8");
		} catch (Exception e) {
			logger.error("[跳转作用域]DES解密失败");
			e.printStackTrace();
		}
		
		logger.info("######actionUrl原来的串："+actionUrl);
		return "redirect:"+actionUrl;
	}
	
	/**
	 * 根据微信唯一标识获取手机号Mobile，userId，nickName
	 * @param req
	 * @param res
	 * @return
	 */
	public static Map<String,String> getUserInfoByWeixinUid(HttpServletRequest req,HttpServletResponse res){
		Map<String,String> resultMap = new HashMap<String,String>();
		
		String ua = req.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			try {
				Map mediaUidMap = UserCookieUtil.getWeixinOpenid(req);
				String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				
				if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("mediaUid", mediaUid);
					String param = CommonUtil.getParam(userMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						logger.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUseridByWeixinUid",
							param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.fromObject(resultMsg);
					// 交易密码正确
					if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
						resultMap.put("userId",  jsonObject.getString("userId"));
						resultMap.put("mobile",  jsonObject.getString("mobile"));
						resultMap.put("nickName",  jsonObject.getString("nickName"));
						req.removeAttribute("invitationCd");
						req.setAttribute("invitationCd",jsonObject.getString("mobile"));
						
						UserSession us = new UserSession();
						us.setId(Integer.valueOf(jsonObject.getString("userId")));
						us.setUsername(jsonObject.getString("nickName"));
						us.setMobile(jsonObject.getString("mobile"));
						//登录
						UserCookieUtil.putUser(req, res, us);

						logger.info("************************分享手机号************"+jsonObject.getString("mobile"));
					}else{
						try {
							req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=" + Base64.encodeBase64URLSafeString("/wxuser/goLogin".getBytes())).forward(req, res);
							return resultMap ;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else{
					try {
						req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=" + Base64.encodeBase64URLSafeString("/wxuser/goLogin".getBytes())).forward(req, res);
						return resultMap;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	
	/**
	 * (不跳转到登录)根据微信唯一标识获取手机号Mobile，userId，nickName
	 * @param req
	 * @param res
	 * @return
	 */
	public static Map<String,String> getUserInfoByWeixinUidByLogin(HttpServletRequest req,HttpServletResponse res){
		Map<String,String> resultMap = new HashMap<String,String>();
		String ua = req.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			try {
				Map mediaUidMap = UserCookieUtil.getWeixinOpenid(req);
				String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				
				if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("mediaUid", mediaUid);
					String param = CommonUtil.getParam(userMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						logger.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUseridByWeixinUid",
							param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.fromObject(resultMsg);
					// 交易密码正确
					if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
						resultMap.put("userId",  jsonObject.getString("userId"));
						resultMap.put("mobile",  jsonObject.getString("mobile"));
						resultMap.put("nickName",  jsonObject.getString("nickName"));
						resultMap.put("mediaUid", mediaUid);
						req.removeAttribute("invitationCd");
						req.setAttribute("invitationCd",jsonObject.getString("mobile"));
						
						UserSession us = new UserSession();
						us.setId(Integer.valueOf(jsonObject.getString("userId")));
						us.setUsername(jsonObject.getString("nickName"));
						us.setMobile(jsonObject.getString("mobile"));
						//登录
						UserCookieUtil.putUser(req, res, us);

						logger.info("************************分享手机号************"+jsonObject.getString("mobile"));
					}else{
						resultMap.put("mediaUid", mediaUid);
						return resultMap ;
					}
				}else{
					return resultMap;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	
	/**
	 * (临时措施)根据微信唯一标识获取手机号Mobile，userId，nickName
	 * @param req
	 * @param res
	 * @return
	 */
	public static Map<String,String> getUserInfoByWeixinUidByLoginLS(HttpServletRequest req){
		Map<String,String> resultMap = new HashMap<String,String>();
		String ua = req.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			try {
				Map mediaUidMap = UserCookieUtil.getWeixinOpenid(req);
				String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				
				if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("mediaUid", mediaUid);
					String param = CommonUtil.getParam(userMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						logger.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUseridByWeixinUid",
							param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.fromObject(resultMsg);
					// 交易密码正确
					if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
						resultMap.put("userId",  jsonObject.getString("userId"));
						resultMap.put("mobile",  jsonObject.getString("mobile"));
						resultMap.put("nickName",  jsonObject.getString("nickName"));
						req.removeAttribute("invitationCd");
						req.setAttribute("invitationCd",jsonObject.getString("mobile"));
						logger.info("************************分享手机号************"+jsonObject.getString("mobile"));
					}else{
						return resultMap ;
					}
				}else{
					return resultMap;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	/**
	 * 新老用户绑定操作
	 * @return
	 */
	public static void binDingWeiXinUidUserId(HttpServletRequest request,HttpServletResponse response,WeiXinUserDto userDto){
		
		String ua = request.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			try {
				logger.info("*******************登录成功*******做自动绑定操作****");
				//登录成功后 TODO 绑定微信操作
				Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
				String  mediaUid = "";
				if(null !=mediaUidMap){
					mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				}else{
					try {
						request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=" + Base64.encodeBase64URLSafeString("/wxuser/goLogin".getBytes())).forward(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				//跳转到设置交易密码
				Map<String, Object> mediaBindingMap = new LinkedHashMap<String, Object>();
				mediaBindingMap.put("phoneNum", userDto.getPhoneNum());
				mediaBindingMap.put("mediaUid", mediaUid);
				
				
				String mediaBindingParam = CommonUtil.getParam(mediaBindingMap);
				try {
					mediaBindingParam = DES3Util.encode(mediaBindingParam);
				} catch (Exception e) {
					logger.info("[微信绑定参数]加密失败:" + e.getMessage());
					e.printStackTrace();
				}
				String addBingdingMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/insertIntoMediaBinding",mediaBindingParam);
				try {
					addBingdingMsg = java.net.URLDecoder.decode(DES3Util.decode(addBingdingMsg), "UTF-8");
				} catch (Exception e) {
					logger.info("[添加微信绑定]解密失败:" + e.getMessage());
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 查询当前输入手机号，是否已绑定其他微信号
	 * @param req
	 * @param res
	 * @return
	 */
	public static Map<String,String> getWeixinUidBindingByMobile(String phoneNum,HttpServletRequest req,HttpServletResponse res){
		Map<String,String> resultMap = new HashMap<String,String>();
		String ua = req.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			try {
				
				if(null!=phoneNum  && !"".equalsIgnoreCase(phoneNum) && !"null".equalsIgnoreCase(phoneNum) ){
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("phoneNum", phoneNum);
					String param = CommonUtil.getParam(userMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						logger.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getWeixinUidBindingByMobile",
							param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.fromObject(resultMsg);
					// 交易密码正确
					if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
						resultMap.put("mediaUid",  jsonObject.getString("mediaUid"));
					}else{
						return resultMap ;
					}
				}else{
					return resultMap;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	
	/**
	 * (铃铛活动登录拦截)根据微信唯一标识获取手机号Mobile，userId，nickName
	 * @param req
	 * @param res
	 * @return
	 */
	public static Map<String,String> getUserInfoByWeixinUidLD(HttpServletRequest req,HttpServletResponse res){
		Map<String,String> resultMap = new HashMap<String,String>();

		String ua = req.getHeader("user-agent").toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
			try {
				String forwardUrl = "/wxactivity/goLoginByLd?parm=";
				String parm = req.getQueryString();
				String actionScope = Base64.encodeBase64URLSafeString((forwardUrl+parm).getBytes("UTF-8"));

				Map mediaUidMap = UserCookieUtil.getWeixinOpenid(req);
				String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
				
				if(null!=mediaUid  && !"".equalsIgnoreCase(mediaUid) && !"null".equalsIgnoreCase(mediaUid) ){
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("mediaUid", mediaUid);
					String param = CommonUtil.getParam(userMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						logger.info("加密失败:" + e.getMessage());
						e.printStackTrace();
					}
					String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUseridByWeixinUid",
							param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
								"UTF-8");
					} catch (Exception e) {
						logger.info("检察用户功能解密失败:" + e.getMessage());
						e.printStackTrace();
					}
					JSONObject jsonObject = JSONObject.fromObject(resultMsg);
					// 交易密码正确
					if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
						resultMap.put("userId",  jsonObject.getString("userId"));
						resultMap.put("mobile",  jsonObject.getString("mobile"));
						resultMap.put("nickName",  jsonObject.getString("nickName"));
						req.removeAttribute("invitationCd");
						req.setAttribute("invitationCd",jsonObject.getString("mobile"));
						
						logger.info("************************分享手机号************"+jsonObject.getString("mobile"));
					}else{
						try {
							req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+actionScope).forward(req, res);
							return resultMap ;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else{
					try {
						req.getRequestDispatcher("/wxTrigger/getWxCode?actionScope="+actionScope).forward(req, res);
						return resultMap;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
}
