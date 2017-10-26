package com.web.actions.weixin.about;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.Tools;
import com.web.util.UserCookieUtil;
import com.web.util.llpay.Validator;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wxlanding")

public class WeixinLandingAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeiXinActivityAction.class);

	/**
	 * 跳转活动分享页
	 * @return
	 */
	@RequestMapping(value = "/goLoginDare", method = RequestMethod.GET)
	public String goLoginDare(HttpServletRequest request,HttpServletResponse res) {
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/loginDare";
	} 
	
	/**
	 * 渠道登陆功能
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(HttpServletRequest request,HttpServletResponse response,PrintWriter out) {
		JSONObject json = new JSONObject();
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		// 手机号
		String phoneNum = request.getParameter("phoneNum");
		// 渠道
		String channel = request.getParameter("channel");
		// 交易密码
		String passwordCash = request.getParameter("passwordCash");
		// 邀请码
		String invitationCode = request.getParameter("invitationCode");
		//登录密码
		String password = request.getParameter("password");

		// 短信验证码
		String captcha = request.getParameter("captcha");
		// 是否登陆
		String isLogin = request.getParameter("isLogin");
		
		//设置smDeviceId
        request.setAttribute("smDeviceId", Tools.getCookieValueByName(request, "smDeviceId"));
		String token_id = (String) request.getSession().getAttribute("token_id");
	    String ipInfo=CommonUtil.getClientIP(request);
		logger.info("渠道注册/wxlanding/register|token_id="+token_id);
		logger.info("register接口 画面传过来的值:"+ request.getQueryString());

		if (StringUtils.isBlank(phoneNum)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "手机号不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		//登录 密码不能为空
		if(null==password || "".equalsIgnoreCase(password)){
			logger.error("注册异常:密码不能为空");
			String result = CommonUtil.setResultStringCn(new HashMap<String,Object>(),
					Consts.CHECK_CODE, "","密码不能为空");// 处理返回结果
			CommonUtil.responseJson(result, response); 
			return;
		}
		
		Boolean checkPassword = Validator.isPassword(password);
		if(!checkPassword){
			logger.error("注册异常:您输入的密码规则不符");
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "您输入的密码规则不符，6~16位字符，包含数字、字母（区分大小写）");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		if (!phoneNum.matches(Consts.PHONECHECK)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "请输入规范的手机号码！");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(channel)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "渠道不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
//		if (StringUtils.isBlank(passwordCash)) {
//			json.put("rescode", Consts.CHECK_CODE);
//			json.put("resmsg_cn", "交易密码不能为空!");
//			setResposeMsg(json.toString(), out);
//			return;
//		}

		if (StringUtils.isBlank(captcha)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "短信证码不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("phoneNum", phoneNum);
		userMap.put("setupFlag", "1");
		userMap.put("checkCode", captcha);
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
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		JSONObject user = jsonObject.getJSONObject("user");
		//用户不存在 则注册
		if (user == null || "null".equals(user.toString())) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("userName", phoneNum);
			if (StringUtils.isNotBlank(passwordCash)) {
				map.put("passwordCash", passwordCash);// 交易密码
			}
			if (StringUtils.isNotBlank(invitationCode)) {
				map.put("invitationCode", invitationCode);// 邀请码
			}else{
				map.put("invitationCode", "");	
			}
			map.put("password", password);//登录密码
			map.put("passwordConfirm", password);//确认密码
			map.put("checkCode", captcha);
			map.put("channel", channel);
			map.put("setupFlag", "1");
			map.put("weixinId","");
			map.put("blackBox", token_id);	
			map.put("ipInfo",  ipInfo);
			String regparam = CommonUtil.getParam(map);
			try {
				param = DES3Util.encode(regparam);
			} catch (Exception e) {
				logger.info("用户注册加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/registerLoginPassword", param);
			try {
				regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("用户注册解密失败:" + e.getMessage());
			}
			JSONObject regObject = JSONObject.fromObject(regMsg);
			// 注册成功后
			if (Consts.SUCCESS_CODE.equals(regObject.getString("rescode"))) {
				logger.info("******************************登录成功！");
				
				if (StringUtils.isNotBlank(isLogin) && "Y".equals(isLogin)) {
					UserSession us = new UserSession();
					us.setId(Integer.valueOf(regObject.getString("userId")));
					us.setUsername(regObject.getString("userName"));
					us.setMobile(regObject.getString("mobile"));
					//登录
					UserCookieUtil.putUser(request, response, us);
				}
				
				json.put("rescode", Consts.SUCCESS_CODE);
				json.put("resmsg_cn", "注册成功!");
				setResposeMsg(json.toString(), out);
				return;
			} else {
				json.put("rescode", Consts.CHECK_CODE);
				json.put("resmsg_cn", regObject.getString("resmsg_cn"));
				setResposeMsg(json.toString(), out);
				return;
			}
		} else {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "该手机号已被注册!");
			setResposeMsg(json.toString(), out);
			return;
		}
	} 
	
	
	@RequestMapping(value = "/goLoginZhizhuwang", method = RequestMethod.GET)
	public String goLoginZhizhuwang(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/loginLiuliangbaoZhizhuwang";
	}
	
	/**
	 * 渠道注册1
	 * @return
	 */
	@RequestMapping(value = "/goLoginWojiatisu", method = RequestMethod.GET)
	public String goLoginWojiatisu(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/loginWojiatisu";
	}
	/**
	 * 沃助手
	 * @return
	 */
	@RequestMapping(value = "/goLoginWozhushou", method = RequestMethod.GET)
	public String goLoginWozhushou(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/loginWozhushou";
	}
	
	
	/**
	 * 格瓦拉
	 * @return
	 */
	@RequestMapping(value = "/landingPageGewala", method = RequestMethod.GET)
	public String landingPageGewala(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/landingPageGewala";
	}
	
	/**
	 * 汽车大师
	 * @return
	 */
	@RequestMapping(value = "/landingPageQicheDaShi", method = RequestMethod.GET)
	public String landingPageQicheDaShi(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/landingPageQicheDaShi";
	}
	
	/**
	 * 落地页微信模板
	 * @return
	 */
	@RequestMapping(value = "/goLoginWeiXin", method = RequestMethod.GET)
	public String gologinWeiXin(HttpServletRequest request,HttpServletResponse res) {
		String logInfo = "/wxlanding/goLoginWeiXin-";
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		String channelEnName = request.getParameter("channel");
//		channelId="413";
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("channelId","");
		userMap.put("channelEnName", channelEnName == null ? "" : channelEnName);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error(logInfo+"获取渠道图片失败:" + e);
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePathForShop + "/channelshop/channelDetailImg", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error(logInfo+"获取渠道图片解密失败:" + e);
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		logger.info(logInfo+"shop/channelshop/channelDetailImg返回:"+jsonObject.toString());
		request.setAttribute("wxPicLink", jsonObject.getString("wxPicLink"));
		
		//不同渠道福利图片和描述
		request.setAttribute("channelWelfareDesc", jsonObject.getString("channelWelfareDesc"));
		request.setAttribute("channelWelfarePicLink", jsonObject.getString("channelWelfarePicLink"));
		request.setAttribute("displayMode", jsonObject.getString("displayMode"));
		request.setAttribute("wxBackpic1", jsonObject.getString("wxBackpic1"));
		request.setAttribute("wxBackpic2", jsonObject.getString("wxBackpic2"));
		request.setAttribute("wxBackpic3", jsonObject.getString("wxBackpic3"));
		request.setAttribute("wxBackpic4", jsonObject.getString("wxBackpic4"));
		request.setAttribute("wxBackpic5", jsonObject.getString("wxBackpic5"));
		
		return "weixin/landingPage/loginWeiXin";
	}
	
	/**
	 * 蜘蛛网落地页
	 * @return
	 */
	@RequestMapping(value = "/gologinNewZhizhuwang", method = RequestMethod.GET)
	public String gologinNewZhizhuwang(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/landingPage/loginZhizhuwang";
	}
}
