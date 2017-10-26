package com.web.actions.web.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.common.WeiXinUserDto;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.JsonUtil;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.Tools;
import com.web.util.UserCookieUtil;
import com.web.util.llpay.Validator;
import com.web.util.yundun.YunDunCaptcha;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")

public class UserAction  extends BaseAction implements Serializable{
	
	private static final long serialVersionUID = -4660733781531410744L;
	private static final Log logger = LogFactory.getLog(UserAction.class);
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping(value="/goLogin",method=RequestMethod.GET)
	public String goLogin(String parm, HttpServletRequest request,HttpServletResponse response,Model model){
		WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
		if(null==userDto){
			userDto = new WeiXinUserDto();
		}
		String backUrl = "";
		
		logger.info("######################goLogin获取的参数：["+parm+"]");
		if (parm!=null && parm.length() > 0) {
			try {
				parm = java.net.URLDecoder.decode(DES3Util.decode(parm.replaceAll(" ", "+")),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(parm);
			backUrl = jsonObj.getString("backUrl");
			
			String invitationCd = jsonObj.getString("invitationCd");
			if(invitationCd != null && invitationCd.length() > 0) {
				userDto.setInvitationCd(invitationCd);
			}
		} else {
			//暂定默认跳转到 页面
			backUrl = "/trade/goAccoutOverview";
		}
		request.getSession().setAttribute("backUrl", backUrl);
		request.getSession().setAttribute("userDto", userDto);
		
		return "web/accout/login";
	}

	/**
	 * 获取的验证码 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/goGetCaptcha",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goGetCaptcha(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		String phoneNum = request.getParameter("phoneNum");
		logger.info("=========goGetCaptcha-phoneNum:" + phoneNum);
		
		String type = request.getParameter("type");
		if(StringUtils.isBlank(type)){
			type="WXLOGIN";
		}else{
			if(type.equalsIgnoreCase("resetTradePassword"))
				type="RESET_TRADE_PASSWORD";
			else
				type=type.toUpperCase();
		}
		
	    
		String token_id = (String) request.getSession().getAttribute("token_id");
		String ipInfo=CommonUtil.getClientIP(request);
		logger.info("网站登录token_id="+token_id+"ipInfo="+ipInfo);

        //图片验证码
        boolean validate = false;
        if ("Y".equals(LoginRedirectUtil.YUNDUN_CAPTCHA_SWITCH)) {
            //网易云盾
            validate = YunDunCaptcha.validate(request);
        } else {
            String verifycode = request.getParameter("verifycode");
            String randomCode = String.valueOf(session.getAttribute("randomCode"));
            session.removeAttribute("randomCode");
            if (!StringUtils.isBlank(verifycode)) {
                if (randomCode.toUpperCase().equals(verifycode.toUpperCase())) {
                    validate = true;
                }
            }
        }

        if (!validate) {
            modelMap.put("rescode", Consts.CHECK_CODE);
            modelMap.put("resmsg_cn", "请输入正确的图形验证码！");
            modelMap.put("error", "true");
            return modelMap;
        }

		Map<String,String> map = getCaptcha(phoneNum, phoneNum, type,token_id,ipInfo);
		if (map.get("rescode").equals(Consts.SUCCESS_CODE)) {
			modelMap.put("rescode", Consts.SUCCESS_CODE);
			modelMap.put("resmsg_cn", "");
			modelMap.put("success", "true");
		} else {
			modelMap.put("rescode", Consts.CHECK_CODE);
			modelMap.put("resmsg_cn", map.get("resmsg_cn"));
			modelMap.put("error", "true");
		}
		return modelMap;
	}
	
	/**
	 * 登陆
	 * @param request 接收网站传过来的数据
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String phoneNum,String loginFlag,String password,String checkCode, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		logger.info("登录画面传过来的值:phoneNum="+phoneNum+"checkCode="+checkCode);
		request.removeAttribute("message");
		//设置smDeviceId
		String smDeviceId=Tools.getCookieValueByName(request, "smDeviceId");
		if(StringUtils.isBlank(smDeviceId)){
			smDeviceId=request.getHeader("smDeviceId");
			logger.info("login requestparams: smDeviceId"+smDeviceId);
		}
        request.setAttribute("smDeviceId", smDeviceId);
		String token_id = (String)request.getSession().getAttribute("token_id");
		logger.info("网站/user/login登录token_id="+token_id);
		
		if(null==loginFlag || "".equalsIgnoreCase(loginFlag) || !"1".equalsIgnoreCase(loginFlag)){
			//验证码进行拦截
			if (checkCode == null || "".equals(checkCode)) {
				request.setAttribute("message", "验证码不能为空！");
				return "web/accout/login";
			}
			WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
			userDto.setPhoneNum(phoneNum);
			userDto.setCheckCode(checkCode);
			
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("phoneNum", phoneNum);
			userMap.put("setupFlag", "1");
			userMap.put("checkCode", checkCode);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			//验证码检查
			String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
			try {
				checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
			if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
				request.setAttribute("message",checkCaptchatObject.getString("resmsg_cn"));
				return "web/accout/login";
			}
			// 调用service接口
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			//用户存在
			if (user != null && !"null".equals(user.toString())) {
				logger.info(LogUtil.getStart("goLogin", "方法开始执行", "LoginAction", getProjetUrl(request)));
	
				Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
				loginMap.put("userName", userDto.getPhoneNum());
				loginMap.put("setupFlag", "1");
				loginMap.put("type", "mobile");
				loginMap.put("checkCode", userDto.getCheckCode());
				loginMap.put("blackBox", token_id);
				loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
				String loginParam = CommonUtil.getParam(loginMap);
				try {
					loginParam = DES3Util.encode(loginParam);
				} catch (Exception e) {
					logger.info("登陆加密失败:" + e.getMessage());
					e.printStackTrace();
				}
	
				String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login",loginParam);
				try {
					loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.info("登陆解密失败:" + e.getMessage());
					e.printStackTrace();
				}
	
				logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
	
				JSONObject loginObject = JSONObject.fromObject(loginMsg);
				if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
					System.out.println("登陆成功！");
					UserSession us = new UserSession();
					us.setId(Integer.valueOf(loginObject.getString("userId")));
					us.setUsername(loginObject.getString("userName"));
					us.setMobile(loginObject.getString("mobile"));
					//登录
					UserCookieUtil.putUser(request, response, us);
					
					String backUrl = (String) session.getAttribute("backUrl");
					if (backUrl==null){
						backUrl = "/trade/goAccoutOverview";
					}
					session.removeAttribute("userDto");
					
					boolean needUpdatePassword = loginObject.getBoolean("needUpdatePassword");
                    if(needUpdatePassword){
                    	session.setAttribute("passwordMessage",loginObject.getString("passwordMessage"));
                    }
                    session.setAttribute("needUpdatePassword", needUpdatePassword);
					logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
					return "redirect:" + backUrl;
				} else {
					request.setAttribute("message",jsonObject.getString("resmsg_cn"));
					return "web/accout/login";
				}
			//不存在的时候注册登录
			} else {
				request.setAttribute("message","该用户未注册");
				return "web/accout/login";
			}
		}
		
		//密码进行拦截
		if(null!=loginFlag && !"".equalsIgnoreCase(loginFlag) && "1".equalsIgnoreCase(loginFlag)){
//			//验证码进行拦截
//			if (checkCode == null || "".equals(checkCode)) {
//				request.setAttribute("message", "验证码不能为空！");
//				return "web/accout/login";
//			}
			WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
			userDto.setPhoneNum(phoneNum);
//			userDto.setCheckCode(checkCode);
//			
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("phoneNum", phoneNum);
			userMap.put("setupFlag", "1");
			userMap.put("password", password);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}
//			
//			//验证码检查
//			String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkPassword", param);
//			try {
//				checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
//			} catch (Exception e) {
//				logger.info("检察用户功能解密失败:" + e.getMessage());
//				e.printStackTrace();
//			}
//			JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
//			if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
//				request.setAttribute("message",checkCaptchatObject.getString("resmsg_cn"));
//				return "web/accout/login";
//			}
			// 调用service接口
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			//用户存在
			if (user != null && !"null".equals(user.toString())) {
				logger.info(LogUtil.getStart("goLogin", "方法开始执行", "LoginAction", getProjetUrl(request)));
	
				Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
				loginMap.put("userName", userDto.getPhoneNum());
				loginMap.put("setupFlag", "1");
				loginMap.put("type", "mobile");
//				loginMap.put("checkCode", userDto.getCheckCode());
				loginMap.put("password", password);
				loginMap.put("loginFlag", "1");
				loginMap.put("blackBox", token_id);
				loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
				String loginParam = CommonUtil.getParam(loginMap);
				try {
					loginParam = DES3Util.encode(loginParam);
				} catch (Exception e) {
					logger.info("登陆加密失败:" + e.getMessage());
					e.printStackTrace();
				}
				String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login",loginParam);
				try {
					loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.info("登陆解密失败:" + e.getMessage());
					e.printStackTrace();
				}
	
				logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
	
				JSONObject loginObject = JSONObject.fromObject(loginMsg);
				if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
					System.out.println("登陆成功！");
					UserSession us = new UserSession();
					us.setId(Integer.valueOf(loginObject.getString("userId")));
					us.setUsername(loginObject.getString("userName"));
					us.setMobile(loginObject.getString("mobile"));
					//登录
					UserCookieUtil.putUser(request, response, us);
					
					String backUrl = (String) session.getAttribute("backUrl");
					if (backUrl==null){
						backUrl = "/trade/goAccoutOverview";
					}
					session.removeAttribute("userDto");
					
					boolean needUpdatePassword = loginObject.getBoolean("needUpdatePassword");
                    if(needUpdatePassword){
                    	session.setAttribute("passwordMessage",loginObject.getString("passwordMessage"));
                    }
                    session.setAttribute("needUpdatePassword", needUpdatePassword);
					logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
					return "redirect:" + backUrl;
				} else {
					request.setAttribute("message",jsonObject.getString("resmsg_cn"));
					return "web/accout/login";
				}
			//不存在的时候注册登录
			} else {
				request.setAttribute("message","该用户未注册");
				return "web/accout/login";
			}
		}
		request.setAttribute("message","该用户未注册");
		return "web/accout/login";
	}
	
	/**
	 * 登陆 （不跳转页面）
	 * @param request 接收网站传过来的数据
	 * @return
	 */
	@RequestMapping(value="/ajaxLogin",method=RequestMethod.POST)
	public void ajaxLogin(String phoneNum,String loginFlag,String verifycode, String checkCode, String pwd,HttpServletRequest request,
			HttpServletResponse response, HttpSession session,PrintWriter out) {
		logger.info("登录画面传过来的值:phoneNum="+phoneNum+"checkCode="+checkCode);
		JSONObject json = new JSONObject();
	
		//设置smDeviceId
		String smDeviceId=Tools.getCookieValueByName(request, "smDeviceId");
		if(StringUtils.isBlank(smDeviceId)){
			smDeviceId=request.getHeader("smDeviceId");
			logger.info("ajaxLogin requestParams:smDeviceId "+smDeviceId);
		}
		logger.info("smDeviceId:"+smDeviceId);
        request.setAttribute("smDeviceId", smDeviceId);
		String token_id = (String) request.getSession().getAttribute("token_id");
		logger.info("网站登录token_id="+token_id);
		
		if (StringUtils.isBlank(phoneNum)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "手机号不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
     
		
		if(null==loginFlag || "".equalsIgnoreCase(loginFlag) || !"1".equalsIgnoreCase(loginFlag)){
			if (StringUtils.isBlank(checkCode)) {
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", "验证码不能为空!");
				setResposeMsg(json.toString(), out);
				return;
			}
			
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("phoneNum", phoneNum);
			userMap.put("setupFlag", "1");
			userMap.put("checkCode", checkCode);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			}
			
			//验证码检查
			String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
			try {
				checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			}
			JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
			if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
				setResposeMsg(checkCaptchatObject.toString(), out);
				return;
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
//			//用户不存在则注册
			if (user == null || "null".equals(user.toString())) {
				//不存在的时候注册登录
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", "您还没有注册，请先进行注册！");
				setResposeMsg(json.toString(), out);
				return;
			}
			
			Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
			loginMap.put("userName", phoneNum);
			loginMap.put("setupFlag", "1");
			loginMap.put("type", "mobile");
			loginMap.put("checkCode", checkCode);
			loginMap.put("blackBox", token_id);
			loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
			String loginParam = CommonUtil.getParam(loginMap);
			logger.info("登陆loginParam"+loginParam);
			try {
				loginParam = DES3Util.encode(loginParam);
			} catch (Exception e) {
				logger.info("登陆加密失败:" + CommonUtil.printStackTraceToString(e));
			}
	
			String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login",loginParam);
			try {
				loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("登陆解密失败:" + CommonUtil.printStackTraceToString(e));
			}
	
			logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
	
			JSONObject loginObject = JSONObject.fromObject(loginMsg);
			if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
				System.out.println("登陆成功！");
				UserSession us = new UserSession();
				us.setId(Integer.valueOf(loginObject.getString("userId")));
				us.setUsername(loginObject.getString("userName"));
				us.setMobile(loginObject.getString("mobile"));
				//登录
				UserCookieUtil.putUser(request, response, us);
			} else {
				setResposeMsg(loginObject.toString(), out);
				return;
			}
			
			boolean needUpdatePassword = loginObject.getBoolean("needUpdatePassword");
            if(needUpdatePassword){
            	json.put("passwordMessage", loginObject.getString("passwordMessage"));
            }
            json.put("needUpdatePassword", needUpdatePassword);
			json.put("rescode", Consts.SUCCESS_CODE);
			json.put("resmsg_cn", "登录成功！");
			setResposeMsg(json.toString(), out);
		}
		//密码进行拦截
		if(null!=loginFlag && !"".equalsIgnoreCase(loginFlag) && "1".equalsIgnoreCase(loginFlag)){
			
			//图片验证码 
//			String verifycode = request.getParameter("verifycode");
			String randomCode = String.valueOf(session.getAttribute("randomCode"));
			session.removeAttribute("randomCode");
			/**
			 * 验证码进行拦截
			 */
			if (StringUtils.isBlank(verifycode)){
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", "图片验证码不能为空！");
				setResposeMsg(json.toString(), out);
				return;
			}
			if (!randomCode.toUpperCase().equals(verifycode.toUpperCase())){
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", "请输入正确的图形验证码！");
				setResposeMsg(json.toString(), out);
				return;
			}
//			//密码进行拦截
			if (pwd == null || "".equals(pwd)) {
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", "密码不能为空！！");
				setResposeMsg(json.toString(), out);
				return;
			}
//			WeiXinUserDto userDto = new WeiXinUserDto();
//			userDto.setPhoneNum(phoneNum);
////			userDto.setCheckCode(checkCode);
//			
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("phoneNum", phoneNum);
			userMap.put("setupFlag", "1");
			userMap.put("password", pwd);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			//验证码检查
			String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkPassword", param);
			try {
				checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
			if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", checkCaptchatObject.getString("resmsg_cn"));
				setResposeMsg(json.toString(), out);
				return;
			}
			// 调用service接口
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			//用户存在
			if (user != null && !"null".equals(user.toString())) {
				logger.info(LogUtil.getStart("goLogin", "方法开始执行", "LoginAction", getProjetUrl(request)));
	
				Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
				loginMap.put("userName", phoneNum);
				loginMap.put("setupFlag", "1");
				loginMap.put("type", "mobile");
//				loginMap.put("checkCode", userDto.getCheckCode());
				loginMap.put("password", pwd);
				loginMap.put("loginFlag", "1");
				loginMap.put("blackBox", token_id);
				loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
				String loginParam = CommonUtil.getParam(loginMap);
				try {
					loginParam = DES3Util.encode(loginParam);
				} catch (Exception e) {
					logger.info("登陆加密失败:" + e.getMessage());
					e.printStackTrace();
				}
				String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login",loginParam);
				try {
					loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.info("登陆解密失败:" + e.getMessage());
					e.printStackTrace();
				}
	
				logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
	
				JSONObject loginObject = JSONObject.fromObject(loginMsg);
				if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
					System.out.println("登陆成功！");
					UserSession us = new UserSession();
					us.setId(Integer.valueOf(loginObject.getString("userId")));
					us.setUsername(loginObject.getString("userName"));
					us.setMobile(loginObject.getString("mobile"));
					//登录
					UserCookieUtil.putUser(request, response, us);
					
					boolean needUpdatePassword = loginObject.getBoolean("needUpdatePassword");
		            if(needUpdatePassword){
		            	json.put("passwordMessage", loginObject.getString("passwordMessage"));
		            }
		            json.put("needUpdatePassword", needUpdatePassword);
				} else {
					setResposeMsg(loginObject.toString(), out);
					return;
				}
			//不存在的时候注册登录
			} else {
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", "您还没有注册，请先进行注册！");
				setResposeMsg(json.toString(), out);
				return;
			}
			json.put("rescode", Consts.SUCCESS_CODE);
			json.put("resmsg_cn", "登录成功！");
			setResposeMsg(json.toString(), out);
		}
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param out
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
		if (userDto==null) {
			return "redirect:goLogin";
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (userDto.getPhoneNum()==null || "".equals(userDto.getPhoneNum())) {
			return "redirect:goLogin";
		}
		map.put("regType", "web");
		map.put("userName", userDto.getPhoneNum());
		map.put("passwordCash", "");
		map.put("checkCode", userDto.getCheckCode());
		map.put("channel", "CHANNEL");
		map.put("setupFlag", "1");
		map.put("invitationCode", userDto.getInvitationCd()==null?"":userDto.getInvitationCd());
		map.put("weixinId", userDto.getWeixinId()==null?"":userDto.getWeixinId());
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户注册加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/register", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("用户注册解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		logger.info(LogUtil.getEnd("register", "方法结束运行", "LoginAction"));

		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		//注册成功后
		if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
			System.out.println("登陆成功！");
			UserSession us = new UserSession();
			us.setId(Integer.valueOf(jsonObject.getString("userId")));
			us.setUsername(jsonObject.getString("userName"));
			us.setMobile(jsonObject.getString("mobile"));
			//登录
			UserCookieUtil.putUser(request, response, us);
			String backUrl = (String) session.getAttribute("backUrl");
			
			session.removeAttribute("userDto");
			return "redirect:" + backUrl;
		} else {
			request.setAttribute("message", jsonObject.getString("resmsg_cn"));
			return "web/accout/login";
		}
	}
	
	/**
	 * 邀请码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/regSucceed")
	public String regSucceed(HttpServletRequest request){
		return "weixin/accout/regSucceed";		
	}

	/**
	 * 退出
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/logOut",method=RequestMethod.GET)
	public String logOut(HttpServletRequest request,HttpServletResponse response){
		UserCookieUtil.invalidUser(request, response);
		return "redirect:" + "/trade/goAccoutOverview";
	}
	
	/**
	 * 邀请人列表
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getMyInvitationList",method=RequestMethod.POST)
	public void getMyInvitationList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us && null != us.getId()){
			userId = String.valueOf(us.getId());
		}
		
		String current = request.getParameter("current");
		String pageSize = request.getParameter("pageSize");

		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("current", current);
		map.put("pageSize", pageSize);
		map.put("userId", userId);
		logger.info(LogUtil.getStart("getMyInvitationList", "方法开始执行", "WeiXinUserAction", getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize + "&userId="+ userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("邀请人列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/invitation/myInvitationList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("邀请人列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> jsonMap = JsonUtil.getMapFromJsonString(resultMsg);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (Consts.SUCCESS_CODE.equals(jsonMap.get("rescode"))) {
			String size = (String) jsonMap.get("count");
			List<JSONObject> listDetail = (List<JSONObject>) jsonMap.get("list");
			// String size = jsonObjRtn.getString("totalNum");
			System.out.println("#####################" + listDetail.size());

			PageUtils pageObject = new PageUtils();
			if (null != listDetail && listDetail.size() != 0) {
				int intPageSize = 0;
				if (null != pageSize && !"".equals(pageSize)){
					intPageSize = Integer.parseInt(pageSize);
				}
				pageObject = PageUtil.execsPage(Integer.parseInt(current), Integer.parseInt(size), 5, intPageSize);
			}
			resultMap.put("pageObject", pageObject);
			resultMap.put("list", listDetail);
		}
		resultMap.put("rescode", jsonMap.get("rescode"));

		setResposeMap(resultMap, out);
	}
	
	/**
	 * 查询用户信息
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/getIdcardInfo",method=RequestMethod.POST)
	public void getIdcardInfo(HttpServletRequest request,PrintWriter out,HttpServletResponse res){
		UserSession	us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us && null != us.getId()){
			userId = String.valueOf(us.getId());
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);
		
		logger.info(LogUtil.getStart("getIdcardInfo", "方法开始执行", "WeiXinUserAction",getProjetUrl(request)));
		logger.info("userId="+userId);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户加密失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		//获取用户信息
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/user/getIdcardInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户认证信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		setResposeMsg(resultMsg.toString(),out);
	}
	
	/**
	 * 查询用户验证信息
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/getUserVerifyInfo")
	public void getUserVerifyInfo(HttpServletRequest request,PrintWriter out,HttpServletResponse res){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us && null != us.getId()){
			userId = String.valueOf(us.getId());
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);
		
		logger.info(LogUtil.getStart("getUserVerifyInfo", "方法开始执行", "UserAction",getProjetUrl(request)));
		logger.info("userId="+userId);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户验证信息加密失败:"+e.getMessage());
			e.printStackTrace();
		}
		
		//获取用户验证信息
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/user/getUserVerifyInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户验证信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		setResposeMsg(resultMsg.toString(),out);
	}
	
	/**
	 * 美恰对接查询用户账户和信息
	 * @param request
	 * @param out
	 * @param res
	 */
	@RequestMapping(value = "/queryUserInfoAndAcctInfo")
	public void queryUserInfoAndAcctInfo(HttpServletRequest request,PrintWriter out,HttpServletResponse res){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		JSONObject jsonObject = null;
		if(us!=null){
			String mobile = us.getMobile();
			
			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
			
			userMap.put("mobile", mobile);
			
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("美恰对接查询用户账户和信息失败:" + e.getMessage());
				e.printStackTrace();
			}

			// 调用service接口
			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/deposit/queryUserInfoAndAcctInfo", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("美恰对接查询用户账户和信息解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			jsonObject = JSONObject.fromObject(resultMsg);
		}else{
			jsonObject = new JSONObject();
			jsonObject.put("rescode", Consts.SESSION_LOSE_CODE);
			jsonObject.put("resmsg_cn", "用户未登录");
			jsonObject.put("resmsg", "falid");
		}
		
		setResposeMsg(jsonObject.toString(), out);
	}

	/**
	 * 用户注册
	 * @param request
	 * @param out
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/registerPassword",method=RequestMethod.POST)
	public void registerPassword(HttpServletRequest request, HttpServletResponse response,PrintWriter out,HttpSession session) {
		JSONObject json = new JSONObject();
		String phoneNum = request.getParameter("phoneNum");
		String checkCode = request.getParameter("checkCode");
		String password = request.getParameter("password");
		String verifycode = request.getParameter("verifycode");//图片验证码
		
		//设置smDeviceId
		String smDeviceId=Tools.getCookieValueByName(request, "smDeviceId");
		if(StringUtils.isBlank(smDeviceId)){
			smDeviceId = request.getHeader("smDeviceId");
			logger.info("registerPassword requestParams:smDeviceId "+smDeviceId);
		}
        request.setAttribute("smDeviceId",smDeviceId);
		String token_id = (String) request.getSession().getAttribute("token_id");
		logger.info("网站注册token_id="+token_id);
		if(null == phoneNum || "".equalsIgnoreCase(phoneNum)){
			logger.error("注册异常:手机号不能为空");
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "手机号不能为空");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		if(null == checkCode || "".equalsIgnoreCase(checkCode)){
			logger.error("注册异常:短信验证码不能为空");
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn","短信验证码不能为空");
			setResposeMsg(json.toString(), out);
			return;
		}
		//登录 密码不能为空
		if(null==password || "".equalsIgnoreCase(password)){
			logger.error("注册异常:密码不能为空");
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn","密码不能为空");
			setResposeMsg(json.toString(), out);
			return;
		}

		Boolean checkPassword = Validator.isPassword(password);
		if(!checkPassword){
			logger.error("注册异常:您输入的密码规则不符");
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn","您输入的密码规则不符");
			setResposeMsg(json.toString(), out);
			return;
		}
		

		/**
		 * 查询验证码是否正确接口
		 */
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("phoneNum", phoneNum);
		userMap.put("setupFlag", "1");
		userMap.put("checkCode", checkCode);
		String param1 = CommonUtil.getParam(userMap);
		try {
			param1 = DES3Util.encode(param1);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		//验证码检查
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param1);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn",checkCaptchatObject.getString("resmsg_cn"));
			setResposeMsg(json.toString(), out);
			return;
		}
		/**
		 * 查询该用户是否注册接口
		 */
		// 调用service接口
		String resultMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param1);
		try {
			resultMsg1 = java.net.URLDecoder.decode(DES3Util.decode(resultMsg1),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject1 = JSONObject.fromObject(resultMsg1);
		JSONObject user = jsonObject1.getJSONObject("user");
		//用户存在
		if (user != null && !"null".equals(user.toString())) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn","该手机已经注册，请直接登录");
			setResposeMsg(json.toString(), out);
			return;
		}
		/**
		 * 密码注册新用户接口调用
		 */
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("regType", "web");
		map.put("userName", phoneNum);
		map.put("passwordCash", "");//交易密码
		map.put("password", password);//登录密码
		map.put("passwordConfirm", password);//确认密码
		
		map.put("checkCode", checkCode);
		map.put("channel", "WEBSITE");
		map.put("setupFlag", "1");
		map.put("invitationCode", "");
		map.put("weixinId", "");
		map.put("blackBox", token_id);
		map.put("ipInfo",  CommonUtil.getClientIP(request));
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户注册加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/registerLoginPassword", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("用户注册解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		logger.info(LogUtil.getEnd("register", "方法结束运行", "LoginAction"));

		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		//注册成功后
		if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
			System.out.println("登陆成功！");
			UserSession us = new UserSession();
			us.setId(Integer.valueOf(jsonObject.getString("userId")));
			us.setUsername(jsonObject.getString("userName"));
			us.setMobile(jsonObject.getString("mobile"));
			//登录
			UserCookieUtil.putUser(request, response, us);
			
		} else {
			setResposeMsg(jsonObject.toString(), out);
			return;
		}
		json.put("rescode", Consts.SUCCESS_CODE);
		json.put("resmsg_cn", "登录成功！");
		setResposeMsg(json.toString(), out);
	}
	
	
	/**
	 * 跳转到注册页
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/goRegister",method=RequestMethod.GET)
	public String goRegister(HttpServletRequest request,HttpServletResponse response){
		UserCookieUtil.invalidUser(request, response);
		return "web/accout/register";
	}
	
	/**
	 * 跳转到二维码登录页
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/goQrcode")
	public String goQrcode(HttpServletRequest request,HttpServletResponse response){
		
		return "web/qrcode";
	}
}
