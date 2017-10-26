package com.web.actions.weixin.kcode;

import com.web.actions.web.kcode.KcodeAction;
import com.web.actions.weixin.common.BaseAction;
import com.web.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * kcodeTrade Created by R.hao on 2017/7/25.
 */
@Controller
@RequestMapping("/wxKcodeTrade")
public class KcodeTradeAction extends BaseAction {

	private static final Log logger = LogFactory.getLog(KcodeAction.class);

	@RequestMapping(value = "/authPage")
	public String authPage() {
		return "/weixin/kcode/authPage";
	}

	@RequestMapping(value = "/authLogin", method = RequestMethod.POST)
	public void authLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response)
			throws Exception {
		String logInfo = "kcodeTrade-authLogin";

		String token_id = (String) request.getSession().getAttribute("token_id");
		logger.info(logInfo + "网站登录token_id=" + token_id);

		String phoneNum = request.getParameter("phoneNum");
		String checkCode = request.getParameter("checkCode");
		String authToken = request.getParameter("authToken");
		String loginFlag = request.getParameter("loginFlag");
		String password = request.getParameter("password");
		String parterCode = request.getParameter("parterCode");

		logger.info(logInfo + "页面传值:phoneNum=" + phoneNum + "checkCode=" + checkCode + "authToken=" + authToken
				+ "password=" + password + "loginFlag=" + loginFlag + "parterCode=" + parterCode);

		JSONObject result = new JSONObject();

		// 验证码登录
		if (null == loginFlag || "".equalsIgnoreCase(loginFlag) || !"1".equalsIgnoreCase(loginFlag)) {
			if (null == checkCode || "".equals(checkCode)) {
				logger.info(logInfo + " 验证码为空");
				result.put("resmsg_cn", "请输入验证码");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
			userMap.put("phoneNum", phoneNum);
			userMap.put("setupFlag", "1");
			userMap.put("checkCode", checkCode);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info(logInfo + "加密失败", e);
				result.put("resmsg_cn", Consts.ERROR_DESC);
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			JSONObject checkResult = checkCaptcha(logInfo, param);
			if (!Consts.SUCCESS_CODE.equals(checkResult.getString("rescode"))) {
				result.put("resmsg_cn", "验证码错误");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			JSONObject findUserResult = findWeixinUserByPhoneNum(logInfo, param);
			JSONObject user = null;
			if (findUserResult.containsKey("user")) {
				user = findUserResult.getJSONObject("user");
			}

			if (null == user) {
				result.put("resmsg_cn", "您还没有注册，请先进行注册！");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			JSONObject authRet = authLogin(logInfo, token_id, request);
			outWriter(response, authRet.toString());
			return;
		}

		// 密码登录
		if (!"".equalsIgnoreCase(loginFlag) && "1".equalsIgnoreCase(loginFlag)) {

			// 图片验证码
			String verifycode = request.getParameter("verifycode");
			String randomCode = String.valueOf(session.getAttribute("randomCode"));
			session.removeAttribute("randomCode");
			session.removeAttribute("password");

			if (StringUtils.isBlank(verifycode)) {
				result.put("resmsg_cn", "请输入图形验证码");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}
			if (!randomCode.toUpperCase().equals(verifycode.toUpperCase())) {
				result.put("resmsg_cn", "请输入正确的图形验证码");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}
			if (null == password || "".equalsIgnoreCase(password)) {
				result.put("resmsg_cn", "请输入登录密码");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
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

			JSONObject findUserResult = findWeixinUserByPhoneNum(logInfo, param);
			JSONObject user = null;
			if (findUserResult.containsKey("user")) {
				user = findUserResult.getJSONObject("user");
			}

			if (null == user) {
				result.put("resmsg_cn", "您还没有注册，请先进行注册！");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			JSONObject checkPasswdResult = checkPassword(logInfo, param);
			if (!Consts.SUCCESS_CODE.equals(checkPasswdResult.getString("rescode"))) {
				result.put("resmsg_cn", "登录密码错误");
				result.put("rescode", Consts.ERROR_CODE);
				outWriter(response, result.toString());
				return;
			}

			JSONObject authRet = authLogin(logInfo, token_id, request);
			outWriter(response, authRet.toString());
		}
	}

	/**
	 * 认证登录
	 *
	 * @param logInfo
	 *            日志
	 * @param token_id
	 *            token_id
	 * @param request
	 *            请求
	 * @return
	 */
	private JSONObject authLogin(String logInfo, String token_id, HttpServletRequest request) {

		JSONObject result = new JSONObject();
		String phoneNum = request.getParameter("phoneNum");
		String checkCode = request.getParameter("checkCode");
		String authToken = request.getParameter("authToken");
		String parterCode = request.getParameter("parterCode");

		logger.info(logInfo + "登录");
		Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
		loginMap.put("userName", phoneNum);
		loginMap.put("setupFlag", "1");
		loginMap.put("type", "mobile");
		loginMap.put("checkCode", checkCode);
		loginMap.put("blackBox", token_id);
		loginMap.put("ipInfo", CommonUtil.getClientIP(request));
		String loginParam = CommonUtil.getParam(loginMap);
		try {
			loginParam = DES3Util.encode(loginParam);
		} catch (Exception e) {
			logger.info(logInfo + "加密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}

		String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login", loginParam);

		try {
			loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
			logger.info(logInfo + "loginMsg-" + loginMsg);
		} catch (Exception e) {
			logger.info(logInfo + "解密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}

		JSONObject loginResult = JSONObject.fromObject(loginMsg);
		if (!Consts.SUCCESS_CODE.equals(loginResult.getString("rescode"))) {
			logger.info(logInfo + "登陆失败");
			result.put("resmsg_cn", "登陆失败");
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}

		logger.info(logInfo + "登陆成功");

		Map<String, Object> authLoginMap = new LinkedHashMap<String, Object>();
		authLoginMap.put("mobile", phoneNum);
		authLoginMap.put("authToken", authToken);
		authLoginMap.put("parterCode", parterCode);
		String authLoginParam = CommonUtil.getParam(authLoginMap);
		try {
			authLoginParam = DES3Util.encode(authLoginParam);
		} catch (Exception e) {
			logger.info(logInfo + "加密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}

		String authLoginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/kcodeTrade/authLogin",
				authLoginParam);

		try {
			authLoginMsg = java.net.URLDecoder.decode(DES3Util.decode(authLoginMsg), "UTF-8");
		} catch (Exception e) {
			logger.info(logInfo + "解密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}
		logger.info(logInfo + "authLoginMsg-" + authLoginMsg);
		return JSONObject.fromObject(authLoginMsg);
	}

	public JSONObject checkPassword(String logInfo, String param) {
		JSONObject result = new JSONObject();
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkPassword",
				param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
		} catch (Exception e) {
			logger.info(logInfo + "解密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}
		logger.info(logInfo + "checkCaptchatMsg-" + checkCaptchatMsg);
		return JSONObject.fromObject(checkCaptchatMsg);
	}

	private JSONObject findWeixinUserByPhoneNum(String logInfo, String param) {

		JSONObject result = new JSONObject();
		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");

		} catch (Exception e) {
			logger.info(logInfo + "解密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}
		logger.info(logInfo + "resultMsg-" + resultMsg);
		return JSONObject.fromObject(resultMsg);
	}

	/**
	 * 验证码检查
	 *
	 * @param logInfo
	 * @param param
	 * @return
	 */
	private JSONObject checkCaptcha(String logInfo, String param) {

		JSONObject result = new JSONObject();
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha",
				param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败", e);
			result.put("resmsg_cn", Consts.ERROR_DESC);
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}

		logger.info(logInfo + "checkCaptcha-" + checkCaptchatMsg);
		JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
			result.put("resmsg_cn", "验证码错误");
			result.put("rescode", Consts.ERROR_CODE);
			return result;
		}

		result.put("resmsg_cn", Consts.SUCCESS_DESCRIBE);
		result.put("rescode", Consts.SUCCESS_CODE);
		return result;
	}
}
