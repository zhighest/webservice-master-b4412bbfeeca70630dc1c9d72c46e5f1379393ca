package com.web.actions.weixin.accout;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.web.Interceptor.AvoidDuplicateSubmission;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.PaymentInfo;
import com.web.domain.Scattered;
import com.web.domain.UserRecePayCard;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.ConstantUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.MD5;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.UserCookieUtil;
import com.web.util.llpay.DateUtil;
import com.web.util.llpay.LLPayHostingEnvUtil;
import com.web.util.llpay.LLPayUtil;
import com.web.util.llpay.Md5Algorithm;
import com.web.util.llpay.RSAUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;
import com.web.vo.PayDataBean;

import cn.paypalm.sdk.mer.api.PayPalmAPISDK;
import cn.paypalm.sdk.mer.common.PaypalmException;
import cn.paypalm.sdk.mer.common.XMLDocument;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wxtrade")
public class WeiXinTradeAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeiXinTradeAction.class);
	 
	@RequestMapping(value = "/goGiftBag")
	public String goGiftBag(HttpServletRequest request, HttpSession session, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/giftBag";
	}

	/**
	 * 订单确认
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goOrderConfirmation")
	public String goOrderConfirmation(HttpServletRequest request, HttpSession session, HttpServletResponse res) {
		// 标的id
		String loanId = nullToBlank(request.getParameter("loanId"));
		// 加息券ID
		String rateIds = nullToBlank(request.getParameter("rateIds"));
		// 合同金额
		String contactAmount = nullToBlank(request.getParameter("contactAmount"));
		// 上架表Id
		String sloanId = nullToBlank(request.getParameter("sloanId"));
		// 预期年化收益
		String earnings = nullToBlank(request.getParameter("earnings"));
		// 输入金额
		String inputAmount = nullToBlank(request.getParameter("inputAmount"));
		// 计划id
		String planId = nullToBlank(request.getParameter("planId"));
		// 产品名称
		String cpType = nullToBlank(request.getParameter("cpType"));
		try {
			UserSession us = UserCookieUtil.getUser(request);
			if (us != null) {
				request.setAttribute("flag", "1"); // 已经登陆
				request.setAttribute("username", us.getUsername());
				//rxc 2016-11-07 15:24:37 帮前端压数据 userid+ 手机号mobile
				request.setAttribute("userId", us.getId());
				request.setAttribute("mobile", us.getMobile());
				
			} else {
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				String nickName = userInfoMap.get("nickName");

				if (nickName == null || "".equalsIgnoreCase(nickName)) {
					request.setAttribute("flag", "0"); // 没有登陆
				} else {
					request.setAttribute("flag", "1"); // 没有登陆
					request.setAttribute("username", nickName);
				}
			}
			request.setAttribute("cpType", URLDecoder.decode(cpType, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("flag", "0"); // 没有登陆
		}

		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		if (StringUtils.isNotBlank(rateIds)) {
			if (StringUtils.isNotBlank(sloanId) && (!sloanId.equals("undefined"))) {
				// 上架表不为空 判断标的表是否为空 为空则不坐任何操作
				if (StringUtils.isNotBlank(loanId)) {
					reqJavaMap.put("loanId", sloanId);
				}
			} else {
				// 如果上架表ID为空，根据加息券ID查询符合条件的上架表进行put进mobileView
				reqJavaMap.put("rateId", rateIds);
			}
		} else if (StringUtils.isNotBlank(sloanId) && (!sloanId.equals("undefined"))) {
			if (StringUtils.isNotBlank(loanId)) {
				reqJavaMap.put("loanId", sloanId);
			}
		}

		if (reqJavaMap.size() > 0) {

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询符合加息券的产品加密失败:" + e);
				e.printStackTrace();
			}

			String resultMsg = "";

			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/queryScattered", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("查询符合加息券的产品解密失败:" + e);
				e.printStackTrace();
			}

			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);

			String rescode = jsonObjRtn.getString("rescode");

			if (rescode.equals("00000")) {
				JSONObject scatteredJson = jsonObjRtn.getJSONObject("scattered");
				Scattered scattered = (Scattered) JSONObject.toBean(scatteredJson, Scattered.class);
				if (scattered != null) {
					loanId = scattered.getBidId().toString();
					sloanId = scattered.getId().toString();
					contactAmount = scattered.getContactAmount().toString();
					planId = scattered.getPlanId().toString();
					cpType = scattered.getProductName();
				}
			} else {
				request.getSession().setAttribute("errorMsg", "notFundScattered");
				return "redirect:/wxcoupons/goMyInterest";
			}
		}

		request.setAttribute("loanId", loanId);
		request.setAttribute("sloanId", sloanId);
		request.setAttribute("contactAmount", contactAmount);
		request.setAttribute("planId", planId);
		request.setAttribute("cpType", cpType);
		request.setAttribute("rateIds", rateIds);
		request.setAttribute("inputAmount", inputAmount);
		request.setAttribute("sloanId", sloanId);
		request.setAttribute("earnings", earnings);

		String rateRises = request.getParameter("rateRises");
		request.setAttribute("rateRises", rateRises);
		String earningsDay = request.getParameter("earningsDay");
		request.setAttribute("earningsDay", earningsDay);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/orderConfirmation";
	}

	/**
	 * 投资详情
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goInvestDetail")
	// @RequestMapping(value = "/goInvestDetail", method = RequestMethod.POST)
	public String goInvestDetail(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		// 标的id
		String loanId = request.getParameter("loanId");
		request.setAttribute("loanId", loanId);
		// 计划id
		String sloanId = request.getParameter("sloanId");
		request.setAttribute("sloanId", sloanId);
		// 起投金额
		String investAmount = request.getParameter("investAmount");
		request.setAttribute("investAmount", investAmount);
		// 订单id
		String orderId = request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		// 加息利率
		String couponsRateRises = request.getParameter("couponsRateRises");
		request.setAttribute("couponsRateRises", couponsRateRises);

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/investDetail";
	}

	/**
	 * 跳转到修改交易密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goChangeExchangePassword", method = RequestMethod.GET)
	public String goChangeExchangePassword(HttpServletResponse res, HttpServletRequest request) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		// 1:修改密码 2：设置密码
		request.setAttribute("passwordState", 1);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return WEIXIN_CHANGPASS;
	}

	/**
	 * 跳转到修改交易密码成功页面
	 */
	@RequestMapping(value = "/goChangePasswordCashSuccess", method = RequestMethod.GET)
	public String goWithdrawSuccess(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/changePasswordCashSuccess";
	}

	/**
	 * 跳转绑卡显示页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goHadBankCard", method = RequestMethod.GET)
	public String goHadBankCard(HttpServletResponse res, HttpServletRequest request) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/hadBankCard";
	}

	/**
	 * 跳转到设置邀请码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goSetInvitationCd", method = RequestMethod.GET)
	public String goSetInvitationCd(HttpServletResponse res, HttpServletRequest request) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/setInvitationCd";
	}

	/**
	 * 设置邀请码
	 */
	@RequestMapping(value = "/setInvitationCd", method = RequestMethod.POST)
	public void setInvitationCd(String invitationCd, HttpServletRequest request, HttpServletResponse res,
			PrintWriter out) {
		logger.info("=======进入setInvitationCd方法 接受参数invitationCd：" + invitationCd);
		JSONObject reqJson = new JSONObject();
		if (StringUtils.isBlank(invitationCd)) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "邀请码不能为空！");
			setResposeMsg(reqJson.toString(), out);
			return;
		}
		if (!invitationCd.matches(Consts.PHONECHECK)) {
			logger.error("手机号非法");
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "请输入规范的手机号码!");
			setResposeMsg(reqJson.toString(), out);
			return;
		}

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("invitationCode", invitationCd);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/updateInvitationCode",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// 交易密码正确
		if (!Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", jsonObject.get("resmsg_cn"));
			setResposeMsg(reqJson.toString(), out);
			return;
		} else {
			reqJson.put("rescode", Consts.SUCCESS_CODE);
			reqJson.put("resmsg_cn", "设置邀请码成功");
			setResposeMsg(reqJson.toString(), out);
			return;
		}
	}

	/**
	 * 查看邀请码信息
	 */
	@RequestMapping(value = "/findInvitationCd", method = RequestMethod.POST)
	public void findInvitationCd(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		JSONObject reqJson = new JSONObject();

		UserSession us = UserCookieUtil.getUser(request);
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();

		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}

		userMap.put("phoneNum", phoneNum);

		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		JSONObject user = jsonObject.getJSONObject("user");
		// 用户存在
		if (user != null && !"null".equals(user.toString())) {
			String inviterInvitationCode = user.getString("inviterInvitationCode");
			if (!StringUtils.isBlank(inviterInvitationCode)) {
				reqJson.put("rescode", Consts.SUCCESS_CODE);
				reqJson.put("resmsg_cn", "");
				reqJson.put("invitationCd", inviterInvitationCode);
				setResposeMsg(reqJson.toString(), out);
				return;
			}
		}
		reqJson.put("rescode", Consts.CHECK_CODE);
		reqJson.put("resmsg_cn", "用户没有邀请码");
		setResposeMsg(reqJson.toString(), out);
		return;
	}

	/**
	 * 修改交易密码
	 * 
	 * @param passwordCashOld
	 * @param passwordCashNew
	 * @param request
	 * @param session
	 */
	@RequestMapping(value = "/changeExchangePassword", method = RequestMethod.POST)
	public void changeExchangePassword(String passwordCashOld, String passwordCashNew, HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		logger.info("=======进入changeExchangePassword方法");
		JSONObject reqJson = new JSONObject();
		// 原始密码验证
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("phoneNum", phoneNum);

		String passwordMd5 = MD5.md5(passwordCashOld);
		userMap.put("passwordCash", passwordMd5);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkPasswordCash",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// 交易密码正确
		if (!Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "原始密码输入错误");
			setResposeMsg(reqJson.toString(), out);
			return;
		}

		/**
		 * 交易密码重置
		 */
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userName", phoneNum);
		map.put("passwordCash", passwordCashNew);
		map.put("setupFlag", "1");
		map.put("type", "mobile");
		String paramRset = CommonUtil.getParam(map);
		try {
			paramRset = DES3Util.encode(paramRset);
		} catch (Exception e) {
			logger.info("加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String rsetMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/resetPassword", paramRset);
		try {
			rsetMsg = java.net.URLDecoder.decode(DES3Util.decode(rsetMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject rsetObject = JSONObject.fromObject(rsetMsg);
		if (Consts.SUCCESS_CODE.equals(rsetObject.getString("rescode"))) {
			reqJson.put("rescode", Consts.SUCCESS_CODE);
			reqJson.put("resmsg_cn", "交易密码设置成功");
			setResposeMsg(reqJson.toString(), out);
		} else {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "交易密码设置失败");
			setResposeMsg(reqJson.toString(), out);
		}
	}

	/**
	 * 跳转到提现页面
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goWithdrawDeposit", method = RequestMethod.GET)
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String goWithdrawDeposit(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}

		request.setAttribute("mobile", mobile);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/withdrawDeposit";
	}

	/**
	 * 提现申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "/withdrawDeposit", method = RequestMethod.POST)
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void withdrawDeposit(HttpServletRequest request, HttpServletResponse res, HttpSession session,
			PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String extractMoney = request.getParameter("extractMoney");
		String cardNumber = request.getParameter("cardNumber");
		String cardId = request.getParameter("cardId");
		String setupFlag = "1";
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String withdrawFlag = request.getParameter("withdrawFlag");
		/*
		 * String provinceId = "1780"; String cityId = "22506";
		 */
		// 根据回款卡号查询回款卡号ID
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", userId);
		map.put("extractMoney", extractMoney);
		map.put("cardNumber", cardNumber);
		map.put("setupFlag", setupFlag);
		map.put("recePayCardId", cardId);
		map.put("withdrawFlag", withdrawFlag);
		map.put("passwordCash", request.getParameter("passwordCash"));
		if (StringUtils.isNotBlank(province)) {
			map.put("province", province);
		}
		if (StringUtils.isNotBlank(city)) {
			map.put("city", city);
		}

		logger.info(LogUtil.getStart("withdrawDeposit", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("申请提现加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/withdrawals/dealWithdrawal",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			JSONObject msgJson = JSONObject.fromObject(resultMsg);
			if (Consts.SUCCESS_CODE.equalsIgnoreCase(msgJson.getString("rescode"))) {
				// 微信模板消息调用
				if (null != userId && !"".equalsIgnoreCase(userId)) {
					Map<String, String> requestMap = new HashMap<String, String>();
					requestMap.put("userId", userId);// 用户ID
					requestMap.put("withdrawNumber", extractMoney);// 提现金额
					requestMap.put("withdrawPoundage", "0.00元");// 提现手续费
					// 发送提现模板消息
					WeixinRquestUtil.sendWithdrawModelMessage(request, requestMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 申请提现解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		setResposeMsg(resultMsg, out);
	}

	/**
	 * 提现成功跳转
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/withdrawDepositSuccess", method = RequestMethod.GET)
	public String withdrawDepositSuccess(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		String nextWorkingDay = request.getParameter("nextWorkingDay");

		request.setAttribute("nextWorkingDay", nextWorkingDay);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/withdrawDepositSucceed";
	}

	/**
	 * 连连支付-提现回调接口
	 */
	@RequestMapping(value = "/withdrawDepositNotify", method = RequestMethod.POST)
	public void withdrawDepositNotify(PrintWriter printWriter, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ret_code = "0001";
		String ret_msg = "交易失败";
		String reqStr = LLPayUtil.readReqStr(request);
		if (!LLPayUtil.isnull(reqStr)) {
			System.out.println("接收支付异步通知数据：【" + reqStr + "】");
			logger.info("进入连连支付-提现回调接口");
			logger.info("接收支付异步通知数据：【" + reqStr + "】");
			try {
				if (!LLPayUtil.checkSign(reqStr, LLPayHostingEnvUtil.getValue("yt_pub_key"),
						LLPayHostingEnvUtil.getValue("md5_key"))) {
					logger.info("提现异步通知验签失败!");
					ret_code = "0001";
				}
			} catch (Exception e) {
				logger.info("异步通知报文解析异常!" + e.getMessage());
				ret_code = "0001";
			}
			/**
			 * 封装接口参数
			 */
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject resultJson = JSONObject.fromObject(reqStr);
			String resultPay = resultJson.getString("result_pay");
			String noOrder = resultJson.getString("no_order");
			String moneyOrder = resultJson.getString("money_order");
			String dtOrder = resultJson.getString("dt_order");
			map.put("resultPay", resultPay);
			map.put("noOrder", noOrder);
			map.put("moneyOrder", moneyOrder);
			map.put("dtOrder", dtOrder);

			if (resultPay.equals("SUCCESS")) {
				String oidPaybill = resultJson.getString("oid_paybill");
				String settleDate = resultJson.getString("settle_date");
				map.put("oidPaybill", oidPaybill);
				map.put("settleDate", settleDate);
			}

			String param = CommonUtil.getParam(map);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("提现回调加密失败:" + e.getMessage());
				ret_code = "0001";
				e.printStackTrace();
			}

			String resultMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/userCard/llpayWithdrawalsNotify", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("提现回调解密失败:" + e.getMessage());
				ret_code = "0001";
				e.printStackTrace();
			}

			JSONObject resJson = JSONObject.fromObject(resultMsg);
			String rescode = resJson.getString("rescode");

			if (rescode.equals(Consts.SUCCESS_CODE)) {
				ret_code = "0000";
				ret_msg = "交易成功";

				JSONObject jsonObject = JSONObject.fromObject(resultMsg);
				String userId = "";
				try {
					userId = jsonObject.getString("userId");
				} catch (Exception e) {
					logger.debug("得到/userCard/llpayWithdrawalsNotify中传过来的userId异常!");
				}
			} else if (rescode.equals("99998")) {
				ret_code = "0000";
				ret_msg = "连连交易失败";
			} else if ("99997".equals(rescode)) {
				// 代表连连支付订单已经处理成功 不再发送短信相关
				ret_code = "0000";
				ret_msg = "交易成功";
			}
		}
		resultMap.put("ret_code", ret_code);
		resultMap.put("ret_msg", ret_msg);
		String req_data = JSON.toJSONString(resultMap);
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 掌上汇通提现回调方法
	 * 
	 * @param printWriter
	 * @param request
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/zshtWithdrawNotify", method = RequestMethod.POST)
	public void zshtWithdrawNotify(PrintWriter printWriter, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		String transData = request.getParameter("transdata");
		System.out.println("================================进入掌上汇通提现回调方法=============================");
		System.out.println("transdata=[" + transData + "]");
		/* 解密 */
		XMLDocument resXML;

		try {

			String merRepData = PayPalmAPISDK.encryptData("faild", PayPalmAPISDK.XML);
			resXML = PayPalmAPISDK.getReturnData(transData);
			System.out.println("解密后数据：" + resXML);

			if (resXML != null) {
				// 商户订单号
				String merOrderNo = resXML.getValueAt("merorderno").toString();
				// 订单状态 0等待支付 1支付成功 2支付失败
				String orderStatus = resXML.getValueAt("orderstatus").toString();
				// 错误码
				String rspCode = resXML.getValueAt("rspcode").toString();
				// 错误描述
				String rspDesc = resXML.getValueAt("rspdesc").toString();
				// 其他数据，根据需要获取
				Map<String, Object> map = new HashMap<String, Object>();
				String tranamt = resXML.getValueAt("tranamt").toString();
				BigDecimal moneyOrder = new BigDecimal(tranamt).divide(new BigDecimal(100));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String merOrderDate = sdf.format(new Date());
				map.put("noOrder", merOrderNo);
				map.put("moneyOrder", moneyOrder.toString());
				map.put("dtOrder", merOrderDate);
				String resultPay = "FAILD";
				String oidPaybill = resXML.getValueAt("orderno").toString();
				logger.debug("tranamt:" + tranamt);
				logger.debug("moneyOrder:" + moneyOrder);
				if ("1".equals(orderStatus)) {
					resultPay = "SUCCESS";
					map.put("settleDate", merOrderDate);
					map.put("oidPaybill", oidPaybill);
				} else {
					map.put("errorFlag", rspCode);

				}
				map.put("resultPay", resultPay);

				String param = CommonUtil.getParam(map);
				System.out.println(param);
				try {
					param = DES3Util.encode(param);
				} catch (Exception e) {
					logger.info("提现回调加密失败:" + e.getMessage());
					e.printStackTrace();
				}

				String resultMsg = HttpRequestParam
						.sendGet(LoginRedirectUtil.interfacePath + "/userCard/llpayWithdrawalsNotify", param);
				try {
					resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.info("提现回调解密失败:" + e.getMessage());
					e.printStackTrace();
				}

				JSONObject resJson = JSONObject.fromObject(resultMsg);
				String rescode = resJson.getString("rescode");

				if (rescode.equals(Consts.SUCCESS_CODE)) {
					merRepData = PayPalmAPISDK.encryptData("success", PayPalmAPISDK.XML);
					String userId = "";
					try {
						userId = resJson.getString("userId");
					} catch (Exception e) {
						logger.debug("得到/userCard/llpayWithdrawalsNotify中传过来的userId异常!");
					}
					
				} else if ("99997".equals(rescode)) {
					merRepData = PayPalmAPISDK.encryptData("success", PayPalmAPISDK.XML);
				}

				try {
					response.getWriter().write(merRepData);
					response.getWriter().close();

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (PaypalmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连连支付-提现回调接口(测试接口)
	 */
	@RequestMapping(value = "/withdrawDepositNotifyTest", method = RequestMethod.GET)
	public void withdrawDepositNotifyTest(PrintWriter printWriter, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ret_code = "0001";
		String ret_msg = "交易失败";
		
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject resultJson = JSONObject.fromObject(request.getParameterMap());
		String resultPay = request.getParameter("resultPay");
		String noOrder = request.getParameter("noOrder");
		String moneyOrder = request.getParameter("moneyOrder");
		String dtOrder = request.getParameter("dtOrder");
		map.put("resultPay", resultPay);
		map.put("noOrder", noOrder);
		map.put("moneyOrder", moneyOrder);
		map.put("dtOrder", dtOrder);

		if (resultPay.equals("SUCCESS")) {
			String oidPaybill = request.getParameter("oidPaybill");
			String settleDate = request.getParameter("settleDate");
			map.put("oidPaybill", oidPaybill);
			map.put("settleDate", settleDate);
		}

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("提现回调加密失败:" + e.getMessage());
			ret_code = "0001";
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/userCard/llpayWithdrawalsNotify", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("提现回调解密失败:" + e.getMessage());
			ret_code = "0001";
			e.printStackTrace();
		}

		JSONObject resJson = JSONObject.fromObject(resultMsg);
		String rescode = resJson.getString("rescode");

		if (rescode.equals(Consts.SUCCESS_CODE)) {
			ret_code = "0000";
			ret_msg = "交易成功";

			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			String userId = "";
			try {
				userId = jsonObject.getString("userId");
			} catch (Exception e) {
				logger.debug("得到/userCard/llpayWithdrawalsNotify中传过来的userId异常!");
			}
		} else if (rescode.equals("99998")) {
			ret_code = "0000";
			ret_msg = "连连交易失败";
		} else if ("99997".equals(rescode)) {
			// 代表连连支付订单已经处理成功 不再发送短信相关
			ret_code = "0000";
			ret_msg = "交易成功";
		}
		
		resultMap.put("ret_code", ret_code);
		resultMap.put("ret_msg", ret_msg);
		String req_data = JSON.toJSONString(resultMap);
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();
	}
	/**
	 * 跳转设置页面
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goSetting", method = RequestMethod.GET)
	public String goSetting(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/setting";
	}

	/**
	 * 查询回款卡号
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRecePayBank", method = RequestMethod.POST)
	public void getRecePayBank(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", userId);

		logger.info(LogUtil.getStart("getRecePayBank", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("绑定银行卡加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/investmentCenter/getRecePayBank", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 获取支持的银行列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 转换驼峰命名
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		boolean userReceCardExits = jsonObj.has("id");
		
		JSONObject reqJson = new JSONObject();
		
		String resmsg = jsonObj.getString("resmsg");
		String rescode = jsonObj.getString("rescode");
		String resmsgCn = jsonObj.getString("resmsg_cn");
		
		if(rescode.equals(Consts.SUCCESS_CODE)){
			if(userReceCardExits){
				String id = jsonObj.getString("id");
				String bankId = jsonObj.getString("bank_id");
				String accountName = jsonObj.getString("account_name");
				String cardNumber = jsonObj.getString("card_number");
				String phoneNo = jsonObj.getString("phone_no");
				String cityName = jsonObj.getString("cityName");
				String bankName = jsonObj.getString("bank_name");
				String picturePath = jsonObj.getString("picturePath");
				String singleDayLimit = jsonObj.getString("singleDayLimit");
				String singleMonthLimit = jsonObj.getString("singleMonthLimit");
				String singleLimit = jsonObj.getString("singleLimit");
				
				reqJson.put("id", id);
				reqJson.put("bankId", bankId);
				reqJson.put("bankName", bankName);
				reqJson.put("picturePath", picturePath);
				reqJson.put("cardNumber", cardNumber);
				reqJson.put("phoneNo", phoneNo);
				reqJson.put("cityName", cityName);
				reqJson.put("accountName", accountName);
				reqJson.put("singleDayLimit", singleDayLimit);
				reqJson.put("singleMonthLimit", singleMonthLimit);
				reqJson.put("singleLimit", singleLimit);
			}
			
			
			String quickWithdrawCount = jsonObj.getString("quickWithdrawCount");
			String quickWithdrawTip = jsonObj.getString("quickWithdrawTip");
			String systemQuickAmount = jsonObj.getString("systemQuickAmount");
			String quickAmountDesc = jsonObj.getString("quickAmountDesc");
			String quickCountDesc = jsonObj.getString("quickCountDesc");
			String quickWithdrawFlag = jsonObj.getString("quickWithdrawFlag");
			String normalWithdrawTip = jsonObj.getString("normalWithdrawTip");
			
			// 兼容老版本
			reqJson.put("quickWithdrawCount", quickWithdrawCount); // 快速提现次数
			reqJson.put("quickCountDesc", quickCountDesc);
			reqJson.put("systemQuickAmount", systemQuickAmount);
			reqJson.put("quickWithdrawFlag", quickWithdrawFlag); // 快速提现开关
			reqJson.put("quickWithdrawTip", quickWithdrawTip); // 快速提现提示
			reqJson.put("normalWithdrawTip", normalWithdrawTip); // 普通提现提示
			reqJson.put("quickAmountDesc", quickAmountDesc); // 提现文案
		}
		
		
		
		reqJson.put("resmsg", resmsg);
		
		reqJson.put("rescode", rescode);
		reqJson.put("resmsgCn", resmsgCn);
		
		setResposeMsg(reqJson.toString(), out);
	}

	/**
	 * 跳转充值页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goTopUpcz", method = RequestMethod.GET)
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String goTopUpcz(HttpServletRequest request, HttpServletResponse res, HttpSession session) {

		// 保存原路径
		String topUpBackUrl = request.getHeader("Referer");
		session.removeAttribute("topUpBackUrl");
		session.setAttribute("topUpBackUrl", topUpBackUrl);
		/**
		 * 查询用户已绑定银行卡信息（页面显示）
		 */
		UserSession us = UserCookieUtil.getUser(request);

		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		logger.info(LogUtil.getStart("goTopUp", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行卡加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/getUserBankCardInfo",
				param);

		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询银行卡解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 拼装用户

		Map<String, Object> userCardMap = parseCardObject(resultMsg);
		Integer userCardInfoCount = Integer.parseInt(userCardMap.get("userCardInfoCount").toString());
		List<UserRecePayCard> userCardList = (List<UserRecePayCard>) userCardMap.get("userCardList");
		String cardNo = null;
		// 如果已经绑定银行卡 则页面显示 不进行输入操作
		if (userCardInfoCount > 0) {
			cardNo = userCardList.get(0).getCard_number();
			request.setAttribute("userCard", userCardList.get(0));
		} else {
			request.setAttribute("userCard", null);
		}
		request.setAttribute("userCardInfoCount", userCardInfoCount);
		request.setAttribute("userId", userId);
		request.setAttribute("money", request.getParameter("money"));
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/topUp";
	}

	/**
	 * 查询银行卡信息
	 * 
	 * @param printWriter
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/queryBankCard", method = RequestMethod.POST)
	@ResponseBody
	public void queryBankCard(PrintWriter printWriter, HttpServletRequest request, HttpServletResponse res,
			HttpSession session) throws UnsupportedEncodingException {
		Map resultMap = new HashMap();

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		logger.info(LogUtil.getStart("goTopUp", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String cardNo = request.getParameter("cardNo");
		String resultMsg = "";
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		reqJavaMap.put("cardNo", cardNo);
		reqJavaMap.put("payType", "D");

		String param = CommonUtil.getParam(reqJavaMap);

		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行是否支持失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 获取用户实名认证信息
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/user/getIdcardInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户认证信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> userIdCardMap = parseJsonForMap(resultMsg);
		// 判断用户是否存在主账户
		String idcardValidate = userIdCardMap.get("idcardValidate").toString();
		if (idcardValidate.equals("N")) {
			resultMap.put("msg", "当前登录用户还没有进行实名认证，请实名认证之后再进行充值！");
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		}

		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/queryCardBinLLPay", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询银行卡卡bin解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		Map<String, Object> userCardBinMap = parseJsonForMap(resultMsg);
		String rescode = userCardBinMap.get("rescode").toString();
		if (!(rescode.equals("00"))) {
			resultMap.put("msg", "输入的银行卡卡号不正确，未能查询到银行卡信息，请重新输入！");
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		} else {
			// 输入的银行卡是信用卡时，提示信息
			if ("3".equals(userCardBinMap.get("cardType"))) {
				resultMap.put("msg", "对不起，暂不支持信用卡，请使用本人借记卡支付！");
				String req_data = JSON.toJSONString(resultMap);
				req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
				printWriter.write(req_data);
				printWriter.flush();
				printWriter.close();
				return;
			}

			String bankCode = userCardBinMap.get("bankCode").toString();
			String bankName = "";
			// 判断联璧数据库是否存在该银行信息
			reqJavaMap.put("bankCode", bankCode);
			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询银行是否支持失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/queryBankExistCode",
					param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("查询银行是否支持失败:" + e.getMessage());
				e.printStackTrace();
			}
			Map<String, Object> bankMap = parseJsonForMap(resultMsg);
			String bankExist = bankMap.get("isExist").toString();
			if (!Boolean.parseBoolean(bankExist)) {
				resultMap.put("msg", "对不起，暂不支持此银行，请选择其他银行卡支付！");
				String req_data = JSON.toJSONString(resultMap);
				req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
				printWriter.write(req_data);
				printWriter.flush();
				printWriter.close();
				return;
			}
			bankName = userCardBinMap.get("bankName").toString();
			String payPlatformId = bankMap.get("payPlatformId").toString();
			// 标识判断支付通道
			if (payPlatformId.equals("2")) {
				resultMap.put("topUpFlag", "ZSHTPAY");
			} else {
				resultMap.put("topUpFlag", "LLPAY");
			}
			resultMap.put("msg", "success");
			resultMap.put("bankCode", bankCode);
			resultMap.put("bankName", bankName);
			resultMap.put("cardNo", cardNo);
			resultMap.put("utmSource", userIdCardMap.get("utmSource"));
			resultMap.put("idCard", userIdCardMap.get("idCard"));
			resultMap.put("identityName", userIdCardMap.get("identityName"));
			resultMap.put("depositDockUserId", userIdCardMap.get("depositDockUserId"));
			resultMap.put("regTime", userIdCardMap.get("regTime"));

			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();

		}

	}

	/**
	 * 掌上汇通支付跳转页面
	 * 
	 * @param printWriter
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/goZshtTopUp", method = RequestMethod.POST)
	public String goZshtTopUp(HttpServletRequest request, HttpServletResponse res) throws UnsupportedEncodingException {
		Map resultMap = new HashMap();
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		String cardNo = request.getParameter("cardNo");
		String moneyOrder = request.getParameter("moneyOrder");
		String identityName = request.getParameter("identityName");
		String idCard = request.getParameter("idCard");
		String cardPhone = request.getParameter("cardPhone");
		String bankCode = request.getParameter("bankCode");
		String bankName = request.getParameter("bankName");
		String userCardInfoCount = request.getParameter("userCardInfoCount");

		// 判断是否存在绑定银行卡 如果存在 抽出绑定银行卡中的手机号
		if (StringUtils.isBlank(cardPhone)) {
			cardPhone = us.getMobile();
		}
		request.setAttribute("cardNo", cardNo);
		request.setAttribute("moneyOrder", moneyOrder);
		request.setAttribute("identityName", identityName);
		request.setAttribute("idCard", idCard);
		request.setAttribute("cardPhone", cardPhone);
		request.setAttribute("bankCode", bankCode);
		request.setAttribute("bankName", bankName);
		request.setAttribute("userCardInfoCount", userCardInfoCount);

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/zshtTopUpNew";

	}

	/**
	 * 支付方法 掌上汇通预支付
	 * 
	 * @param printWriter
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/zshtTopUp", method = RequestMethod.POST)
	@ResponseBody
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void zshtTopUp(PrintWriter printWriter, HttpServletRequest request, HttpServletResponse res,
			HttpSession session) throws UnsupportedEncodingException {
		Map resultMap = new HashMap();
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		logger.info(LogUtil.getStart("goTopUp", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);
		// 拼装用户
		String cardNo = request.getParameter("cardNo");

		String moneyOrder = request.getParameter("moneyOrder");
		String identityName = request.getParameter("identityName");

		String idCard = request.getParameter("idCard");
		String cardPhone = request.getParameter("phoneNum");
		String bankCode = request.getParameter("bankCode");
		String bankName = request.getParameter("bankName");

		Integer userCardInfoCount = Integer.parseInt(request.getParameter("userCardInfoCount"));
		String noOrder = createNoOrder("ZSHTWX");
		// 保存订单信息
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);
		reqJavaMap.put("cardPhone", cardPhone);

		String param = "";
		String resultMsg = "";
		reqJavaMap.put("bindindReqNum", noOrder);
		String payFlag = request.getParameter("payFlag");
		// 鉴权通道

		if (userCardInfoCount == 0) {
			// 保存银行卡信息
			reqJavaMap.put("bankCode", bankCode);
			reqJavaMap.put("bankName", bankName);
			reqJavaMap.put("acctName", identityName);
			reqJavaMap.put("idCard", idCard);
			reqJavaMap.put("cardNo", cardNo);

			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/saveUserBankCardInfo",
					param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			Map<String, Object> saveCardInfoMap = parseJsonForMap(resultMsg);
			if (!saveCardInfoMap.get("rescode").equals(Consts.SUCCESS_CODE)) {
				resultMap.put("msg", "银行卡信息保存失败！" + saveCardInfoMap.get("resmsg"));
				String req_data = JSON.toJSONString(resultMap);
				req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
				printWriter.write(req_data);
				printWriter.flush();
				printWriter.close();
				return;
			}
		} else {
			// 更新银行卡信息
			reqJavaMap.put("cardNo", cardNo);
			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询银行卡卡bin加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/userCard/updateUserBindindBankCardInfo", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			Map<String, Object> updateBindCardMap = parseJsonForMap(resultMsg);
			if (!updateBindCardMap.get("rescode").equals(Consts.SUCCESS_CODE)) {
				resultMap.put("msg", "银行卡信息更新失败！" + updateBindCardMap.get("resmsg"));
				String req_data = JSON.toJSONString(resultMap);
				req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
				printWriter.write(req_data);
				printWriter.flush();
				printWriter.close();
				return;
			}
		}
		reqJavaMap.put("moneyOrder", moneyOrder);
		reqJavaMap.put("orderdesc", "联璧钱包用户" + userId + "进行支付");
		reqJavaMap.put("bankcard", cardNo);
		reqJavaMap.put("cardusername", identityName);
		reqJavaMap.put("idno", idCard);
		reqJavaMap.put("cardphone", cardPhone);
		reqJavaMap.put("noOrder", noOrder);

		param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行卡卡bin加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/zshtThridTopUp", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("保存银行卡信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> zshtTopUpResMap = parseJsonForMap(resultMsg);
		String rescode = zshtTopUpResMap.get("rescode").toString();
		String resmsg = zshtTopUpResMap.get("resmsg").toString();
		if ("00".equals(rescode)) {
			// 保存预支付订单信息
			Map<String, Object> dealMap = new HashMap<String, Object>();
			// 保存订单信息
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String merOrderDate = sdf.format(new Date());
			String orderNo = zshtTopUpResMap.get("orderNo").toString();
			String paytoken = zshtTopUpResMap.get("paytoken").toString();
			String signSrc = zshtTopUpResMap.get("resJson").toString();
			dealMap.put("userId", userId);
			dealMap.put("moneyOrder", moneyOrder);
			dealMap.put("signSrc", signSrc);
			dealMap.put("noOrder", noOrder);
			dealMap.put("oidPartner", LLPayHostingEnvUtil.getValue("zshtMerId"));
			dealMap.put("dtOrder", merOrderDate);

			String rechargeChannel = StringUtils.isNotBlank(payFlag) ? "WEB" : "MOBILE";
			dealMap.put("rechargeChannel", rechargeChannel);
			dealMap.put("cardNumber", cardNo);
			dealMap.put("platform", "ZSHTPAY");

			param = CommonUtil.getParam(dealMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("保存预支付订单信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/dealLLPayFastPayToken",
					param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("保存预支付订单信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			Map<String, Object> dealResultMap = parseJsonForMap(resultMsg);

			rescode = dealResultMap.get("rescode").toString();
			if (!(rescode.equals(Consts.SUCCESS_CODE))) {
				resultMap.put("msg", "保存订单信息失败！");
				String req_data = JSON.toJSONString(resultMap);
				req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
				printWriter.write(req_data);
				printWriter.flush();
				printWriter.close();
				return;
			} else {
				resultMap.put("msg", "success");
				resultMap.put("paytoken", paytoken);
				resultMap.put("orderNo", orderNo);
			}

			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			System.out.println(req_data);
			logger.info("发送掌上汇通支付的请求参数：" + req_data);

			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
		} else {
			resultMap.put("msg", resmsg);
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		}
		// 应答码

	}

	/**
	 * 验证支付 掌上汇通
	 * 
	 * @param printWriter
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/checkZshtTopUp", method = RequestMethod.POST)
	@ResponseBody
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void checkZshtTopUp(PrintWriter printWriter, HttpServletRequest request, HttpServletResponse res,
			HttpSession session) throws UnsupportedEncodingException {
		Map resultMap = new HashMap();
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();

		String paytoken = request.getParameter("paytoken");
		String orderNo = request.getParameter("orderNo");
		String checkCode = request.getParameter("checkCode");

		reqJavaMap.put("paytoken", paytoken);
		reqJavaMap.put("orderNo", orderNo);
		reqJavaMap.put("checkCode", checkCode);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行卡卡bin加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/zshtThridCheckTopUp",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("保存银行卡信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> zshtTopUpResMap = parseJsonForMap(resultMsg);
		String rescode = zshtTopUpResMap.get("rescode").toString();
		String resmsg = zshtTopUpResMap.get("resmsg").toString();
		if ("00".equals(rescode)) {
			resultMap.put("msg", "success");
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		} else {
			resultMap.put("msg", resmsg);
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		}

	}

	/**
	 * 支付跳转第三方 (仅连连)
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/topUp", method = RequestMethod.POST)
	@ResponseBody
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void topUp(PrintWriter printWriter, HttpServletRequest request, HttpServletResponse res, HttpSession session)
			throws UnsupportedEncodingException {
		Map resultMap = new HashMap();

		/**
		 * 查询用户已绑定银行卡信息（页面显示）
		 */
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		logger.info(LogUtil.getStart("goTopUp", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行卡加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		String bankCode = request.getParameter("bankCode");
		String bankName = request.getParameter("bankName");
		// 拼装用户
		String cardNo = request.getParameter("cardNo");

		String moneyOrder = request.getParameter("moneyOrder");
		BigDecimal moneyOrderAmount = new BigDecimal(moneyOrder == null ? "0" : moneyOrder);
		if (moneyOrderAmount.compareTo(BigDecimal.ZERO) != 1) {
			resultMap.put("msg", "输入充值金额不正确，请修改后再进行充值！");
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		}
		String payFlag = request.getParameter("payFlag");
		// 拼接提交连连支付的参数
		PaymentInfo payInfo = new PaymentInfo();
		// 请求认证标识
		payInfo.setApp_request(LLPayHostingEnvUtil.getValue("app_requst_wap"));
		// 商户业务类型
		payInfo.setBusi_partner(LLPayHostingEnvUtil.getValue("busi_partner_virtual"));
		// 银行卡卡号
		if (cardNo != null) {
			payInfo.setCard_no(cardNo);
		}
		// 商户订单时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dtOrder = sdf.format(new Date());
		payInfo.setDt_order(dtOrder);
		// 身份证号码

		// 查询卡bin信息是否存在
		reqJavaMap.put("cardNo", cardNo);
		String utmSource = request.getParameter("utmSource");
		String idCard = request.getParameter("idCard");
		String identityName = request.getParameter("identityName");

		Map<String, Object> userIdCardMap = new HashMap<String, Object>();
		userIdCardMap.put("idCard", idCard);
		userIdCardMap.put("identityName", identityName);
		userIdCardMap.put("depositDockUserId", request.getParameter("depositDockUserId"));
		userIdCardMap.put("phoneNo", us.getMobile());
		userIdCardMap.put("regTime", request.getParameter("regTime"));

		// 判断用户是否已绑定卡 如果未绑定卡 则保存银行卡信息
		String rescode = "";
		String noOrder = "";
		if (StringUtils.isNotBlank(payFlag)) {
			noOrder = createNoOrder("LBKJ");
		} else {
			noOrder = createNoOrder("LBWX");
		}
		Integer userCardInfoCount = Integer.parseInt(request.getParameter("userCardInfoCount"));

		if (userCardInfoCount == 0) {
			// 保存银行卡信息
			reqJavaMap.put("bankCode", bankCode);
			reqJavaMap.put("bankName", bankName);
			reqJavaMap.put("bindindReqNum", noOrder);
			reqJavaMap.put("acctName", identityName);
			reqJavaMap.put("idCard", idCard);

			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/saveUserBankCardInfo",
					param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			Map<String, Object> saveCardInfoMap = parseJsonForMap(resultMsg);
			if (!saveCardInfoMap.get("rescode").equals(Consts.SUCCESS_CODE)) {
				resultMap.put("msg", "银行卡信息保存失败！");
			} else {
				String llpayNoAgree = request.getParameter("llpayNoAgree");
				payInfo.setId_no(idCard);
				// 订单描述
				payInfo.setInfo_order("联璧充值");
				// 充值金额
				payInfo.setMoney_order(moneyOrder);

				// 商户唯一订单号
				payInfo.setNo_order(noOrder);
				// 商品名称
				payInfo.setName_goods("联璧支付购买充值");
				// 服务器异步通知地址
				payInfo.setNotify_url(LLPayHostingEnvUtil.getValue("notify_url"));
				// 商户编号
				payInfo.setOid_partner(LLPayHostingEnvUtil.getValue("oid_partner"));
				// 账号名称
				payInfo.setAcct_name(String.valueOf(identityName));
				// 风控控制参数
				payInfo.setRisk_item(this.getTopRiskItem(userIdCardMap, userId));
				// 签名方式
				payInfo.setSign_type(LLPayHostingEnvUtil.getValue("sign_type"));
				if (StringUtils.isNotBlank(payFlag)) {
					// 返回路径
					payInfo.setUrl_return(
							LLPayHostingEnvUtil.getValue("url_kj_return") + "?sessionId=" + session.getId());
				} else {
					// 返回路径
					payInfo.setUrl_return(LLPayHostingEnvUtil.getValue("url_return") + "?sessionId=" + session.getId());
				}

				// 用户ID
				// 如果是迁移用户则传入迁移用户ID
				if (utmSource.equals("90")) {
					String depositDockUserId = userIdCardMap.get("depositDockUserId").toString();
					payInfo.setUser_id(depositDockUserId);
				} else {
					payInfo.setUser_id(userId);
				}

				// 版本号
				payInfo.setVersion(LLPayHostingEnvUtil.getValue("version"));
				// 背景颜色
				payInfo.setBg_color(LLPayHostingEnvUtil.getValue("bg_color"));
				// 签约协议号
				if (StringUtils.isNotBlank(llpayNoAgree)) {
					payInfo.setNo_agree(llpayNoAgree);
				}
				// 加签名
				String sign_src = getSignStr(payInfo);
				if (sign_src.startsWith("&")) {
					sign_src = sign_src.substring(1);
				}
				String sign = "";
				if ("RSA".equals(request.getParameter("sign_type"))) {
					sign = RSAUtil.sign(LLPayHostingEnvUtil.getValue("trader_pri_key"), sign_src);
				} else {
					sign_src += "&key=" + LLPayHostingEnvUtil.getValue("md5_key");
					try {
						sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				payInfo.setSign(sign);
				resultMap.put("msg", "success");
				resultMap.put("payInfo", JSON.toJSONString(payInfo));
			}

		} else {
			reqJavaMap.put("bindindReqNum", noOrder);

			// 更新银行卡信息
			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询银行卡卡bin加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/userCard/updateUserBindindBankCardInfo", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("银行卡信息更新解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			Map<String, Object> updateBindCardMap = parseJsonForMap(resultMsg);
			if (!updateBindCardMap.get("rescode").equals(Consts.SUCCESS_CODE)) {
				resultMap.put("msg", "银行卡信息更新失败！");
			} else {
				String llpayNoAgree = request.getParameter("llpayNoAgree");
				payInfo.setId_no(String.valueOf(idCard));
				// 订单描述
				payInfo.setInfo_order("联璧充值");
				// 充值金额
				payInfo.setMoney_order(moneyOrder);

				// 商户唯一订单号
				payInfo.setNo_order(noOrder);
				// 商品名称
				payInfo.setName_goods("联璧支付购买充值");
				// 服务器异步通知地址
				payInfo.setNotify_url(LLPayHostingEnvUtil.getValue("notify_url"));
				// 商户编号
				payInfo.setOid_partner(LLPayHostingEnvUtil.getValue("oid_partner"));
				// 账号名称
				payInfo.setAcct_name(String.valueOf(identityName));
				// 风控控制参数
				payInfo.setRisk_item(this.getTopRiskItem(userIdCardMap, userId));
				// 签名方式
				payInfo.setSign_type(LLPayHostingEnvUtil.getValue("sign_type"));
				if (StringUtils.isNotBlank(payFlag)) {
					// 返回路径
					payInfo.setUrl_return(
							LLPayHostingEnvUtil.getValue("url_kj_return") + "?sessionId=" + session.getId());
				} else {
					// 返回路径
					payInfo.setUrl_return(LLPayHostingEnvUtil.getValue("url_return") + "?sessionId=" + session.getId());
				}
				// 用户ID
				// 如果是迁移用户则传入迁移用户ID
				if (utmSource.equals("90")) {
					String depositDockUserId = userIdCardMap.get("depositDockUserId").toString();
					payInfo.setUser_id(depositDockUserId);
				} else {
					payInfo.setUser_id(userId);
				}
				// 版本号
				payInfo.setVersion(LLPayHostingEnvUtil.getValue("version"));
				// 背景颜色
				payInfo.setBg_color(LLPayHostingEnvUtil.getValue("bg_color"));
				// 签约协议号
				if (StringUtils.isNotBlank(llpayNoAgree)) {
					payInfo.setNo_agree(llpayNoAgree);
				}
				// 加签名
				String sign_src = getSignStr(payInfo);
				if (sign_src.startsWith("&")) {
					sign_src = sign_src.substring(1);
				}
				String sign = "";
				if ("RSA".equals(request.getParameter("sign_type"))) {
					sign = RSAUtil.sign(LLPayHostingEnvUtil.getValue("trader_pri_key"), sign_src);
				} else {
					sign_src += "&key=" + LLPayHostingEnvUtil.getValue("md5_key");
					try {
						sign = Md5Algorithm.getInstance().md5Digest(sign_src.getBytes("utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				payInfo.setSign(sign);
				resultMap.put("msg", "success");
				resultMap.put("payInfo", JSON.toJSONString(payInfo));
			}

		}

		Map<String, Object> dealMap = new HashMap<String, Object>();
		// 保存订单信息
		String signSrc = JSON.toJSONString(payInfo).replaceAll("=", "||");
		dealMap.put("userId", userId);
		dealMap.put("moneyOrder", moneyOrder);
		dealMap.put("signSrc", signSrc);
		dealMap.put("noOrder", noOrder);
		dealMap.put("oidPartner", LLPayHostingEnvUtil.getValue("oid_partner"));
		dealMap.put("dtOrder", dtOrder);
		String rechargeChannel = StringUtils.isNotBlank(payFlag) ? "WEB" : "MOBILE";
		dealMap.put("rechargeChannel", rechargeChannel);
		dealMap.put("cardNumber", cardNo);
		dealMap.put("platform", "LIANLIANPAY");

		param = CommonUtil.getParam(dealMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("保存预支付订单信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/dealLLPayFastPayToken",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("保存预支付订单信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		Map<String, Object> dealResultMap = parseJsonForMap(resultMsg);

		rescode = dealResultMap.get("rescode").toString();
		if (!(rescode.equals(Consts.SUCCESS_CODE))) {
			resultMap.put("msg", "保存订单信息失败！");
		} else {
			resultMap.put("msg", "success");
			resultMap.put("payInfo", JSON.toJSONString(payInfo));
		}

		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
		System.out
				.println("===================================LLPay Date Print========================================");
		System.out.println(req_data);
		logger.info("发送连连支付的请求参数：" + req_data);

		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 
	 * @param request
	 * @param res
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/zshtTopUpSucceed")
	public String zshtTopUpSucceed(HttpServletRequest request, HttpServletResponse res, HttpSession session) {

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		boolean isSucceed = true;

		request.setAttribute("isSucceed", isSucceed);
		// 判断是否是从我的资产页面进入，如果是，则不显示继续理财
		Object topUpBackUrlObj = session.getAttribute("topUpBackUrl");
		if (topUpBackUrlObj == null) {
			session.setAttribute("topUpBackUrl", LLPayHostingEnvUtil.getValue("topUpBackUrl"));
			/* session.setAttribute("continueButtonFalg", false); */
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/topUpSucceed";
	}

	/**
	 * 支付成功界面
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/topUpSucceed")
	public String topUpSucceed(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		String resData = request.getParameter("res_data");

		boolean isSucceed = StringUtils.isNotBlank(resData);

		request.setAttribute("isSucceed", isSucceed);
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("更新订单状态加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/topUpSuccessd", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("更新订单状态加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 判断是否是从我的资产页面进入，如果是，则不显示继续理财
		Object topUpBackUrlObj = session.getAttribute("topUpBackUrl");
		if (topUpBackUrlObj == null) {
			session.setAttribute("topUpBackUrl", LLPayHostingEnvUtil.getValue("topUpBackUrl"));
			/* session.setAttribute("continueButtonFalg", false); */
		}
		/*
		 * else { String topUpBackUrl = topUpBackUrlObj.toString(); int
		 * myAssetIndex = topUpBackUrl.indexOf("/wxtrade/goMyAsset"); if
		 * (myAssetIndex > 0) { session.setAttribute("continueButtonFalg",
		 * false); } else { int indexPage =
		 * topUpBackUrl.indexOf("/wxabout/goIndex"); if(indexPage > 0){
		 * session.setAttribute("continueButtonFalg", false); }else{
		 * session.setAttribute("continueButtonFalg", true); }
		 * 
		 * } }
		 */

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/topUpSucceed";
	}

	/**
	 * 连连支付异步通知
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/llpayNotify", method = RequestMethod.POST)
	public void llpayNotify(PrintWriter printWriter, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("====================WebService进入连连支付成功回调接口=======================");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ret_code = "0001";
		String ret_msg = "交易失败";
		String reqStr = LLPayUtil.readReqStr(request);
		if (!LLPayUtil.isnull(reqStr)) {
			System.out.println("接收支付异步通知数据：【" + reqStr + "】");
			logger.info("接收支付异步通知数据：【" + reqStr + "】");
			try {
				if (!LLPayUtil.checkSign(reqStr, LLPayHostingEnvUtil.getValue("yt_pub_key"),
						LLPayHostingEnvUtil.getValue("md5_key"))) {
					logger.info("支付异步通知验签失败!");
					ret_code = "0001";
				}
			} catch (Exception e) {
				logger.info("异步通知报文解析异常!" + e.getMessage());
				ret_code = "0001";
			}

			// 解析异步通知对象
			PayDataBean payDataBean = JSON.parseObject(reqStr, PayDataBean.class);
			String noOrder = payDataBean.getNo_order();

			// 根据商户唯一订单号查询用户银行卡信息
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("bindingReqNum", noOrder);
			reqJavaMap.put("noAgree", payDataBean.getNo_agree() == null ? "" : payDataBean.getNo_agree());
			reqJavaMap.put("moneyOrder", payDataBean.getMoney_order());
			reqJavaMap.put("oidPayBill", payDataBean.getOid_paybill());
			reqJavaMap.put("reqStr", reqStr.replace("=", "||"));

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				ret_code = "0001";
				logger.info("更新用户银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			String resultMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/userCard/updateUserBankCardByReqNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
				logger.info("/userCard/updateUserBankCardByReqNum方法返回结果:" + resultMsg);
				JSONObject resJson = JSONObject.fromObject(resultMsg);
				String rescode = resJson.getString("rescode");
				if (rescode.equals(Consts.SUCCESS_CODE)) {
					ret_code = "0000";
					ret_msg = "交易成功";
					// 微信模板消息调用
					if (null != resJson.getString("userId") && !"".equalsIgnoreCase(resJson.getString("userId"))) {
						Map<String, String> requestMap = new HashMap<String, String>();
						requestMap.put("userId", resJson.getString("userId"));// 用户ID
						requestMap.put("money", payDataBean.getMoney_order());// 本次充值
						// 发送充值
						// WeixinRquestUtil.sendRechargeModelMessage(request,requestMap);
					}
				} else {
					ret_code = rescode;
					ret_msg = resJson.getString("resmsg");
				}
				// ouyy 充值成功，推送提示短信
				// if("0000".equals(ret_code)){
				// SendSMSUtil.sendSMS(resJson.getString("userId"),
				// "sms01001", payDataBean.getMoney_order(), "");
				// }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ret_code = "0001";
				logger.info("更新用户银行卡信息解密失败:" + e.getMessage());
				e.printStackTrace();
			}

		} else {
			ret_code = "0001";
		}

		logger.info("====================WebService连连支付成功回调接口接口=======================");
		resultMap.put("ret_code", ret_code);
		resultMap.put("ret_msg", ret_msg);
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();

	}

	/**
	 * 掌上汇通回调方法
	 * 
	 * @param printWriter
	 * @param request
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/zshtPayNotify", method = RequestMethod.POST)
	public void zshtPayNotify(PrintWriter printWriter, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {

		String transData = request.getParameter("transdata");
		System.out.println("================================进入掌上汇通支付回调方法=============================");
		System.out.println("transdata=[" + transData + "]");
		/* 解密 */
		XMLDocument resXML;

		try {

			String merRepData = PayPalmAPISDK.encryptData("faild", PayPalmAPISDK.XML);
			resXML = PayPalmAPISDK.getReturnData(transData);
			System.out.println("解密后数据：" + resXML);

			if (resXML != null) {
				// 商户订单号
				String merOrderNo = resXML.getValueAt("merorderno").toString();
				// 订单状态 0等待支付 1支付成功 2支付失败
				String orderStatus = resXML.getValueAt("orderstatus").toString();
				// 错误码
				String rspCode = resXML.getValueAt("rspcode").toString();
				// 错误描述
				String rspDesc = resXML.getValueAt("rspdesc").toString();
				// 其他数据，根据需要获取

				Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();

				if ("1".equals(orderStatus)) {
					String tranamt = resXML.getValueAt("tranamt").toString();
					String orderno = resXML.getValueAt("orderno").toString();
					reqJavaMap.put("bindingReqNum", merOrderNo);
					reqJavaMap.put("noAgree", "");
					BigDecimal moneyOrder = new BigDecimal(tranamt).divide(new BigDecimal(100));
					reqJavaMap.put("moneyOrder", moneyOrder.toString());
					reqJavaMap.put("oidPayBill", orderno);
					JSONObject jsonObject = JSONObject.fromObject(resXML.getMapFromXml());
					reqJavaMap.put("reqStr", jsonObject.toString());

					String param = CommonUtil.getParam(reqJavaMap);
					try {
						param = DES3Util.encode(param);
					} catch (Exception e) {
						logger.info("更新用户银行卡信息加密失败:" + e.getMessage());
						e.printStackTrace();
					}

					String resultMsg = HttpRequestParam
							.sendGet(LoginRedirectUtil.interfacePath + "/userCard/updateUserBankCardByReqNum", param);
					try {
						resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("/userCard/updateUserBankCardByReqNum方法返回结果:" + resultMsg);
					JSONObject resJson = JSONObject.fromObject(resultMsg);
					String rescode = resJson.getString("rescode");
					if (rescode.equals(Consts.SUCCESS_CODE)) {
						merRepData = PayPalmAPISDK.encryptData("success", PayPalmAPISDK.XML);
					}
				} else {
					// 失败更新订单状态为失败

				}
				try {
					response.getWriter().write(merRepData);
					response.getWriter().close();

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} catch (PaypalmException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询用户银行卡列表json转换
	 * 
	 * @param resultMsg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> parseCardObject(String resultMsg) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		String userCardInfoCount = jsonObj.getString("userCardInfoCount");
		JSONArray userCardArray = jsonObj.getJSONArray("userCardInfo");
		resultMap.put("userCardInfoCount", userCardInfoCount);
		List<UserRecePayCard> list = (List<UserRecePayCard>) JSONArray.toCollection(userCardArray,
				UserRecePayCard.class);
		resultMap.put("userCardList", list);
		return resultMap;
	}

	/**
	 * 循环遍历返回的json串数据
	 * 
	 * @param resultMsg
	 * @return
	 */
	public Map<String, Object> parseJsonForMap(String resultMsg) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		Iterator it = jsonObj.keys();

		while (it.hasNext()) {
			String key = (String) it.next();
			String value = jsonObj.getString(key);
			resultMap.put(key, value);
		}
		return resultMap;
	}

	// 拼接风控参数
	public String getTopRiskItem(Map<String, Object> paramMap, String userId) {
		/**
		 * 风控控制参数
		 */
		JSONObject riskObject = new JSONObject();
		// 商品类目 (P2P小额贷款 :2009)
		riskObject.put("frms_ware_category", "2009");
		// 商户用户唯一标识
		riskObject.put("user_info_mercht_userno", userId);
		// 用户绑定手机号
		riskObject.put("user_info_bind_phone", paramMap.get("phoneNo").toString());
		// 商户用户分类
		riskObject.put("user_info_mercht_usertype", "1");
		// 用户注册时间
		// 转换时间
		String regTimeString = paramMap.get("regTime").toString();
		riskObject.put("user_info_dt_register", regTimeString);
		// 用户注册姓名：真实姓名
		riskObject.put("user_info_full_name", paramMap.get("identityName").toString());
		// 用户注册证件类型
		riskObject.put("user_info_id_type", "0");
		// 用户注册证件号码
		riskObject.put("user_info_id_no", paramMap.get("idCard").toString());
		// 是否实名认证
		riskObject.put("user_info_identity_state", "1");
		// 实名认证方式
		riskObject.put("user_info_identity_type", "3");
		return riskObject.toString();
	}

	/**
	 * 随机生成订单
	 * 
	 * @return
	 */
	public String createNoOrder(String title) {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = outFormat.format(now);

		String strReq = currTime;
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 8; i++) {
			result += random.nextInt(10);
		}

		String requestNo = title + strReq + result;
		return requestNo;
	}

	/**
	 * 拼接支付签名
	 * 
	 * @param payInfo
	 * @return
	 */
	public String getSignStr(PaymentInfo payInfo) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("acct_name=");
		strBuf.append(payInfo.getAcct_name());

		strBuf.append("&app_request=");
		strBuf.append(payInfo.getApp_request());

		strBuf.append("&bg_color=");
		strBuf.append(payInfo.getBg_color());

		strBuf.append("&busi_partner=");
		strBuf.append(payInfo.getBusi_partner());

		strBuf.append("&card_no=");
		strBuf.append(payInfo.getCard_no());

		strBuf.append("&dt_order=");
		strBuf.append(payInfo.getDt_order());

		strBuf.append("&id_no=");
		strBuf.append(payInfo.getId_no());

		strBuf.append("&info_order=");
		strBuf.append(payInfo.getInfo_order());

		strBuf.append("&money_order=");
		strBuf.append(payInfo.getMoney_order());

		strBuf.append("&name_goods=");
		strBuf.append(payInfo.getName_goods());

		if (StringUtils.isNotBlank(payInfo.getNo_agree())) {
			strBuf.append("&no_agree=");
			strBuf.append(payInfo.getNo_agree());
		}

		strBuf.append("&no_order=");
		strBuf.append(payInfo.getNo_order());

		strBuf.append("&notify_url=");
		strBuf.append(payInfo.getNotify_url());

		strBuf.append("&oid_partner=");
		strBuf.append(payInfo.getOid_partner());

		if (StringUtils.isNotBlank(payInfo.getRisk_item())) {
			strBuf.append("&risk_item=");
			strBuf.append(payInfo.getRisk_item());
		}

		strBuf.append("&sign_type=");
		strBuf.append(payInfo.getSign_type());

		strBuf.append("&url_return=");
		strBuf.append(payInfo.getUrl_return());

		strBuf.append("&user_id=");
		strBuf.append(payInfo.getUser_id());

		strBuf.append("&version=");
		strBuf.append(payInfo.getVersion());
		return strBuf.toString();
	}

	/**
	 * 理财人的累计收益--从投资开始计算人的累计收益 用户需要登录以后，查看的累计收益
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accumulatedIncome", method = RequestMethod.POST)
	public @ResponseBody String accumulatedIncome(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);

		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("accumulatedIncome", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("累计收益加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/assets/getAccumulatedIncome",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("累计收益解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// setResposeMsg(resultMsg,out);
		// setResposeMsg(resultMsg,out);
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// Map<String,Object> mapResult = new HashMap<String,Object>();
		// mapResult.put("", value);
		// return map;
		String incomeAmount = "";
		// JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
			incomeAmount = jsonObject.getString("incomeAmount");
		}
		return incomeAmount;
	}

	/**
	 * 理财人的帐户余额-- 用户需要登录以后，查看的帐户余额
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accountBalance", method = RequestMethod.POST)
	public @ResponseBody String accountBalance(HttpServletRequest request, HttpServletResponse res) {

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("accountBalance", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("帐户余额加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/investmentCenter/getAccountBalance", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("帐户余额解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// setResposeMsg(resultMsg,out);
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// Map<String,Object> mapResult = new HashMap<String,Object>();
		// mapResult.put("", value);
		// return map;
		String balanceMoney = "";
		// JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
			balanceMoney = jsonObject.getString("balanceMoney");
		}
		return balanceMoney;
	}

	/**
	 * 订单列表
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPurchaseDetailList", method = RequestMethod.POST)
	public void getPurchaseDetailList(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);

		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		//
		// String userId="";
		// if(null == us || null == us.getId()){
		//// 用户表示验证机制（通过微信标识OPENID）
		//// WeiXinUserDto userDto = (WeiXinUserDto)
		// session1.getAttribute("userDto");
		//// String mediaUid = userDto.getWeixinId();
		// Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
		// String mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
		//
		// if(null!=mediaUid && !"".equalsIgnoreCase(mediaUid) &&
		// !"null".equalsIgnoreCase(mediaUid) ){
		// Map<String, Object> userMap = new HashMap<String, Object>();
		// userMap.put("mediaUid", mediaUid);
		// String param = CommonUtil.getParam(userMap);
		// try {
		// param = DES3Util.encode(param);
		// } catch (Exception e) {
		// logger.info("加密失败:" + e.getMessage());
		// e.printStackTrace();
		// }
		// String resultMsg =
		// HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath +
		// "/wxuser/getUseridByWeixinUid",
		// param);
		// try {
		// resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
		// "UTF-8");
		// } catch (Exception e) {
		// logger.info("检察用户功能解密失败:" + e.getMessage());
		// e.printStackTrace();
		// }
		// JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// // 交易密码正确
		// if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
		// userId = jsonObject.getString("userId");
		// }else{
		// try {
		// request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu").forward(request,
		// res);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return;
		// }
		// }else{
		// try {
		// request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu").forward(request,
		// res);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// return;
		// }
		// }else{
		// userId = String.valueOf(us.getId());
		// }
		/*
		 * String current = request.getParameter("current"); String pageSize =
		 * request.getParameter("pageSize");
		 */
		// String userId = request.getParameter("userId"); // 用户ID
		String current = request.getParameter("current"); // 当前页数
		String pageSize = request.getParameter("pageSize"); // 页大小
		// String inOrOut =
		// request.getParameter("inOrOut");//抽取条件（无值--全部、in--收入、out--支出）
		String type = request.getParameter("type");
		System.out.println("############orderState##############:----" + type);
		//ouyy 2017-02-23增加列表排序类型 1-按到期日期排序：升序(默认) 2-按投资金额排序：升序 3-按投资日期排序：升序
		String sortType= request.getParameter("sortType");
		if (StringUtils.isBlank(sortType)||"null".equalsIgnoreCase(sortType)) {
			sortType = "1";//默认为1
		}
		String orderBy= request.getParameter("orderBy");
		if (StringUtils.isBlank(orderBy)||"null".equalsIgnoreCase(orderBy)) {
			orderBy = "asc";//默认升序
		}
		logger.info("获取订单列表排序字段:"+sortType+" 排序方式： "+orderBy);
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("current", current);
		map.put("pageSize", pageSize);
		// map.put("inOrOut", inOrOut);
		map.put("type", type);
		map.put("sortType", sortType);
		map.put("orderBy", orderBy);

		logger.info(LogUtil.getStart("getPurchaseDetailList", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId + "&current=" + current + "&pageSize=" + pageSize + "&type=" + type);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/order/getOrderInfoList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("订单列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> resultMap;
		try {
			// setResposeMsg(resultMsg,out);
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			String size = jsonObjRtn.getString("totalNum");
			List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
			// String size = jsonObjRtn.getString("totalNum");
			System.out.println("#####################" + listDetail.size());
			resultMap = new HashMap<String, Object>();

			PageUtils pageObject = new PageUtils();
			if (null != listDetail && listDetail.size() != 0) {
				int intPageSize = 0;
				if (null != pageSize && !"".equals(pageSize)) {
					intPageSize = Integer.parseInt(pageSize);
				}
				pageObject = PageUtil.execsPage(Integer.parseInt(current), Integer.parseInt(size), 5, intPageSize);
			}
			resultMap.put("pageObject", pageObject);
			resultMap.put("list", listDetail);
			resultMap.put("rescode", jsonObjRtn.getString("rescode"));
			setResposeMap(resultMap, out);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 资产明细
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showAssetDetail", method = RequestMethod.POST)
	public void showAssetDetail(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("buyLoanByBalance", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("资产明细加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/assets/showAssetDetail", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("资产明细解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}

	/**
	 * 跳转到我的资产页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goMyAsset", method = RequestMethod.GET)
	public String goMyAsset(HttpServletResponse res, HttpServletRequest request) {
		// 页面跳转标志
		request.setAttribute("pageTag", "myAsset");

		String query = request.getQueryString();
		if (StringUtils.isNotBlank(query)) {
			request.setAttribute("parm", query);
		} else {
			request.removeAttribute("parm");
		}
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		String mobile = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
			mobile = userInfoMap.get("mobile");
		} else {
			userId = String.valueOf(us.getId());
			mobile = us.getMobile();
		}

		try {
			String logInfo = "调用shop系统获得用户等级+mobile" + mobile;
			String pointResultStr = null;
			int pointGrade = 1;
			String pointSwitch = "N";
			logger.info(logInfo + "调用shop系统");
			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
			userMap.put("userId", userId);
			userMap.put("phone", mobile);

			String param = DES3Util.encode(CommonUtil.getParam(userMap));

			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/pointSwitch",
					param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr), "UTF-8");

			logger.info(logInfo + " shop系统返回结果:" + pointResultStr);

			try {
				JSONObject pointResult = JSONObject.fromObject(pointResultStr);
				pointGrade = Integer.valueOf(pointResult.getString("pointGrade"));
				pointSwitch = pointResult.getString("pointSwitch");

			} catch (Exception e) {
				logger.error(logInfo + " shop系统返回异常pointResultStr=" + pointResultStr, e);
				pointGrade = 1;

			}
			request.setAttribute("pointGrade", pointGrade);
			request.setAttribute("pointSwitch", pointSwitch);

		} catch (Exception e) {
			request.setAttribute("pointGrade", 1);
			request.setAttribute("pointSwitch", "N");

		}

		request.setAttribute("mobile", mobile);
		request.setAttribute("userId", userId);
		// 公众号链接过来的传值
		request.setAttribute("channel", request.getParameter("channel"));

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/myAsset";
	}

	/**
	 * 跳转到福利页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goWelfare", method = RequestMethod.GET)
	public String goWelfare(HttpServletResponse res, HttpServletRequest request) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/Welfare";
	}

	/**
	 * 我的资产总览
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showAssets", method = RequestMethod.POST)
	public void showAssets(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("showAssets", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("我的资产总览加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/assets/showAssets", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("我的资产总览解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}

	/**
	 * 
	 * @Description: 根据订单id或者标的id查询订单信息
	 * @param request
	 * @param out
	 * @return void
	 * @throws @author
	 *             qibo
	 * @date 2015-9-4 下午4:22:59
	 */
	@RequestMapping(value = "/getOrderFormDetails", method = RequestMethod.POST)
	public void getOrderFormDetails(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		// 订单号
		String orderId = request.getParameter("orderId");
		// 标的id
		String loanId = request.getParameter("loanId");
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId == null ? "" : orderId);
		map.put("loanId", loanId == null ? "" : loanId);
		map.put("userId", userId);
		map.put("couponsRateRises", request.getParameter("couponsRateRises"));

		logger.info(LogUtil.getStart("getOrderFormDetails", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("orderId=" + orderId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/order/getOrderFormDetails",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("订单列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// setResposeMsg(resultMsg,out);
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		// String size = jsonObjRtn.getString("totalNum");
		System.out.println("#####################" + listDetail.size());
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);

	}

	/**
	 * 
	 * @Description: 查询借款方公司信息
	 * @param request
	 * @param out
	 * @return void
	 * @throws @author
	 *             qibo
	 * @date 2015-9-4 下午2:43:31
	 */
	@RequestMapping(value = "/getBorrowerCompanyInfo", method = RequestMethod.POST)
	public void getBorrowerCompanyInfo(HttpServletRequest request, PrintWriter out) {

		String sloanId = request.getParameter("sloanId");
		String investType = request.getParameter("investType");

		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sloanId", sloanId);
		logger.info(LogUtil.getStart("getBorrowerCompanyInfo", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("sloanId=" + sloanId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/borrowerCompany/getBorrowerCompanyInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("订单列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// setResposeMsg(resultMsg,out);
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		// String size = jsonObjRtn.getString("totalNum");
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("borrowerCompany", jsonObjRtn);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);

	}

	/**
	 * 资产统计
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/goAssetsStatistics")
	public String goAssetsStatistics(HttpServletRequest request) {
		/*
		 * UserSession us = null; //获取Session try { us =
		 * UserCookieUtil.getUser(request); } catch (NoSessionException e) { us
		 * = null; return "redirect:"+"/user+goLogin"; }
		 * 
		 * if(us == null){ return "redirect:"+"/user+goLogin"; }
		 * System.out.println("userid="+us.getId());
		 */
		// return autoOut(request);
		UserSession us = UserCookieUtil.getUser(request);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "accout/assetStatistics";

	}

	/**
	 * 购买
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/buyLoanByBalance", method = RequestMethod.POST)
	public void buyLoanByBalance(HttpServletRequest request, PrintWriter out, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String buyCount = request.getParameter("buyCount"); // 购买份数
		String scatteredLoanId = request.getParameter("scatteredLoanId"); // 购买散标id
		String setupFlag = ConstantUtil.setup_Flag; // 发送标识（是来自网站用户，还是手机用户）:1：为来自网站的；2：来自手机的
		String from = "WX";
		String planId = request.getParameter("planId"); // 计划id
		String cycleMatchType = request.getParameter("cycleMatchType"); // 循环匹配类型1、本息循环2、本金循环3、不循环
		String loanId = request.getParameter("loanId");
		String passwordCash = request.getParameter("passwordCash");
		String rateIds = request.getParameter("rateIds");
		// String rateIds = "10398";
		String investPeriod = request.getParameter("investPeriod");
		String voucherId = request.getParameter("voucherId"); // 代金券ID

		String cpType = request.getParameter("cpType");
		try {
			cpType = URLDecoder.decode(cpType, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("undefined".equalsIgnoreCase(cpType)) {
			cpType = "铃铛宝";
		}

		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("buyCount", buyCount);
		map.put("scatteredLoanId", scatteredLoanId);
		map.put("setupFlag", setupFlag);
		map.put("from", from);
		map.put("planId", planId);
		map.put("cycleMatchType", "3");
		map.put("loanId", loanId);
		map.put("passwordCash", passwordCash);
		if (StringUtils.isNotBlank(rateIds)) {
			map.put("rateIds", rateIds);
		}
		map.put("investPeriod", investPeriod);
		map.put("remanDays", request.getParameter("remanDays"));
		map.put("voucherId", voucherId);

		logger.info(LogUtil.getStart("buyLoanByBalance", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId + "&buyCount=" + buyCount + "&scatteredLoanId=" + scatteredLoanId + "&setupFlag="
				+ setupFlag + "&planId=" + planId + "&cycleMatchType=" + cycleMatchType);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("购买加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/investmentCenter/buyLoanByBalance", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");

			JSONObject msgJson = JSONObject.fromObject(resultMsg);
			if (Consts.SUCCESS_CODE.equalsIgnoreCase(msgJson.getString("rescode"))) {
				// 微信模板消息调用
				if (null != userId && !"".equalsIgnoreCase(userId)) {
					Map<String, String> requestMap = new HashMap<String, String>();
					requestMap.put("userId", userId);// 用户ID
					requestMap.put("productInfo", cpType);// 产品类型
					requestMap.put("number", buyCount);// 购买金额
					// 发送购买模板消息
					// WeixinRquestUtil.sendBuyModelMessage(request,requestMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("购买解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 修改为javaservice调用
		// ouyy 投资成功，发送短信
		/*
		 * JSONObject jsonObject = JSONObject.fromObject(resultMsg); if
		 * (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
		 * SendSMSUtil.sendSMS(userId, "sms02001", buyCount,cpType); }
		 */
		setResposeMsg(resultMsg, out);
	}

	/**
	 * 交易记录
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/goTransaction", method = RequestMethod.GET)
	public String goTransaction(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "accout/transaction";
	}
	
	// Modify by Tangchenbing in 2017-6-28 begin
	/**
	 * 获取交易类别
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getTradeTypeList")
	public void getTradeTypeList(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		logger.info("======进入方法：wxtrade/getTradeTypeList====");

		String from = request.getParameter("from");
		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("from", from);
		String param = CommonUtil.getParam(mapParams);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取交易类别加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wallet/getTradeTypeList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取交易类别解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	// Modify by Tangchenbing in 2017-6-28 end

	/**
	 * 交易记录
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getFundFlowList", method = RequestMethod.POST)
	public void getFundFlowList(HttpServletRequest request, PrintWriter out, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		/*
		 * String current = request.getParameter("current"); String pageSize =
		 * request.getParameter("pageSize");
		 */
		// String userId = request.getParameter("userId"); // 用户ID
		String current = request.getParameter("current"); // 当前页数
		String pageSize = request.getParameter("pageSize"); // 页大小
		String inOrOut = request.getParameter("inOrOut");// 抽取条件（无值--全部、in--收入、out--支出）
		
		// Modify by Tangchenbing in 2017-6-26 begin
		//分类类型（空：全部；1：充值；2：提现；3：活期投资；4：定期投资；5：活期赎回；6：定期赎回；7：奖励；8：代金券使用）
		String type = request.getParameter("type");
		// Modify by Tangchenbing in 2017-6-26 end
		
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("current", current);
		map.put("pageSize", pageSize);
		map.put("inOrOut", inOrOut);
		
		// Modify by Tangchenbing in 2017-6-26 begin
		map.put("type", type);
		// Modify by Tangchenbing in 2017-6-26 end
		
		logger.info(LogUtil.getStart("getFundFlowList", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户流水加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wallet/getFundFlowList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("用户流水解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// setResposeMsg(resultMsg,out);

		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		String size = jsonObjRtn.getString("totalNum");
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		// String size = jsonObjRtn.getString("totalNum");
		System.out.println("#####################" + listDetail.size());
		Map<String, Object> resultMap = new HashMap<String, Object>();

		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			pageObject = PageUtil.execsPage(Integer.parseInt(current), Integer.parseInt(size), 5, 10);
		}
		resultMap.put("sysdate", DateUtil.getCurrentDateStr());
		resultMap.put("pageObject", pageObject);
		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);
	}

	/**
	 * 跳转实名认证
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goAuthentication", method = RequestMethod.GET)
	public String goAuthentication(HttpServletResponse res, HttpServletRequest request) {

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		String phoneNum = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
			phoneNum = userInfoMap.get("mobile");

			// 已经实名跳到个人资产页面
			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
			userMap.put("phoneNum", phoneNum);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			// 调用service接口
			String resultMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			// 用户不存在 则注册
			if (user != null || !"null".equals(user.toString())) {
				if (null != user.get("idcard_validate")
						&& !"null".equalsIgnoreCase(String.valueOf(user.get("idcard_validate")))
						&& !"".equalsIgnoreCase(String.valueOf(user.get("idcard_validate")))
						&& "Y".equalsIgnoreCase(String.valueOf(user.get("idcard_validate")))) {
					return "redirect:/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu";
					// return
					// "redirect:/wxTrigger/getWxCode?actionScope=pzvRYTlBqgn+ylkbzpEsUGS1cfOKeSo9";

				}
			}
		} else {
			userId = String.valueOf(us.getId());
			phoneNum = us.getMobile();

			// 已经实名跳到个人资产页面
			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
			userMap.put("phoneNum", phoneNum);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			// 调用service接口
			String resultMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			// 用户不存在 则注册
			if (user != null || !"null".equals(user.toString())) {
				if (null != user.get("idcard_validate")
						&& !"null".equalsIgnoreCase(String.valueOf(user.get("idcard_validate")))
						&& !"".equalsIgnoreCase(String.valueOf(user.get("idcard_validate")))
						&& "Y".equalsIgnoreCase(String.valueOf(user.get("idcard_validate")))) {
					return "redirect:/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu";
					// return
					// "redirect:/wxTrigger/getWxCode?actionScope=pzvRYTlBqgn+ylkbzpEsUGS1cfOKeSo9";
				}
			}
		}
		// 保存原路径
		String backUrl = request.getHeader("Referer");
		request.setAttribute("backUrl", backUrl);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/authentication";
	}

	/**
	 * 实名认证
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public void authentication(HttpServletRequest request, HttpSession session, HttpServletResponse res,
			PrintWriter out, HttpSession session1) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		String resultMsg = "";
		
	    
		String token_id = (String) request.getSession().getAttribute("token_id");
		logger.info("实名认证token_id="+token_id);
		
		String idCard = request.getParameter("idCard");
		String nameCard = request.getParameter("nameCard");

		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);
		reqJavaMap.put("idCard", idCard);
		reqJavaMap.put("nameCard", nameCard);
		reqJavaMap.put("blackBox", token_id);
		reqJavaMap.put("ipInfo",  CommonUtil.getClientIP(request));
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("实名认证失败:" + e.getMessage());
			e.printStackTrace();
		}

		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/user/getAuth", param);

		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("实名认证失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}

	/**
	 * 实名认证成功跳转
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/authenticationSuccess", method = RequestMethod.GET)
	public String authenticationSuccess(HttpServletResponse res, HttpServletRequest request, HttpSession session) {
		String redUrl = request.getParameter("redUrl");
		request.setAttribute("redUrl", redUrl);

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/authenticationSuccess";
	}

	/**
	 * 交易密码验证
	 * 
	 * @param request
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/checkPasswordCash", method = RequestMethod.POST)
	public void checkPasswordCash(String passwordCash, HttpServletRequest request, PrintWriter out,
			HttpServletResponse res) throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserSession us = UserCookieUtil.getUser(request);

		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("phoneNum", phoneNum);

		String passwordMd5 = MD5.md5(passwordCash);
		userMap.put("passwordCash", passwordMd5);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkPasswordCash",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// 交易密码正确
		if (Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
			resultMap.put("rescode", Consts.SUCCESS_CODE);
			resultMap.put("message", "交易密码正确！");
			resultMap.put("isCorrect", true);
		} else {
			resultMap.put("rescode", Consts.SUCCESS_CODE);
			resultMap.put("isCorrect", false);
			resultMap.put("message", jsonObject.get("resmsg_cn"));
		}
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
		/* setResposeMap(resultMap, out); */
	}

	/**
	 * 跳转邀请好友
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goInviteFriend", method = RequestMethod.GET)
	public String goInviteFriend(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}

		request.setAttribute("mobile", phoneNum);

		// login接收的数据
		JSONObject json = new JSONObject();
		json.put("invitationCd", phoneNum);
		json.put("backUrl", "/webabout/download");
		String loginParm = "";
		try {
			// loginParm = DES3Util.encode(json.toString());
			loginParm = getEncodeString(json.toString());
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("loginParm", loginParm);
		String goUrl = "";
		try {
			goUrl = Base64.encodeBase64URLSafeString(("/wxactivity/activity_20151103?parm=" + loginParm).getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("goUrl", goUrl);

		// 获取当前分享时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shareMobile", phoneNum);
		map.put("shareDate", date);

		String shareMobileDateJM = CommonUtil.getParam(map);
		try {
			shareMobileDateJM = DES3Util.encode(shareMobileDateJM);
		} catch (Exception e) {
			logger.info("[跳转到邀请好友]时间，分享人手机号加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("shareMobileDateJM", shareMobileDateJM);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/inviteFriend";
	}

	private String getEncodeString(String str) {
		String parm = "";
		try {
			parm = URLEncoder.encode(DES3Util.encode(str), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		return parm;
	}

	/**
	 * 银行限额
	 * 
	 * @return
	 */
	@RequestMapping(value = "/limitTable", method = RequestMethod.GET)
	public String limitTable(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);

		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}
		request.setAttribute("mobile", phoneNum);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/limitTable";
	}

	/**
	 * 交易密码控件页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dealPWControls", method = RequestMethod.GET)
	public String dealPWControls(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}
		request.setAttribute("mobile", phoneNum);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/dealPWControls";
	}

	/**
	 * （微信公众平台）发送模板信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getWeixinTemplateId", method = RequestMethod.GET)
	public void getWeixinTemplateId(HttpServletRequest request, HttpServletResponse res) {

		try {
			String flag = request.getParameter("flag");

			// recharge(充值)
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("openid", "oTHApxP138ltFDyiDBRvG_8GfhV4");// 模板发送openid
			dataMap.put("productInfo", "10000元");// 本次充值金额
			dataMap.put("url", "http://www.baidu.com");// 点击后地址
			// recharge(充值)，buy（购买），withdraw（提现）
			WeixinRquestUtil.getWeixinTemplateId(request, flag, dataMap);

			// //buy（购买）
			// Map<String,String> dataMap = new HashMap<String,String>();
			// dataMap.put("openid",
			// "oTHApxP138ltFDyiDBRvG_8GfhV4");//模板发送openid
			// dataMap.put("productInfo", "铃铛宝活期90天");//产品类型
			// dataMap.put("number", "10000元");//购买金额
			// dataMap.put("url", "http://www.baidu.com");//点击后地址
			// recharge(充值)，buy（购买），withdraw（提现）
			// WeixinRquestUtil.getWeixinTemplateId(request,flag,dataMap);
			//
			// //withdraw（提现）
			// Map<String,String> dataMap = new HashMap<String,String>();
			// dataMap.put("openid",
			// "oTHApxP138ltFDyiDBRvG_8GfhV4");//模板发送openid
			// dataMap.put("url", "http://www.baidu.com");//点击后地址
			// dataMap.put("withdrawNumber", "铃铛宝活期90天");//提现金额
			// dataMap.put("withdrawPoundage", "10000元");//提现手续费
			// recharge(充值)，buy（购买），withdraw（提现）
			// WeixinRquestUtil.getWeixinTemplateId(request,flag,dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到定期购买成功页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goFixBuySuccess", method = RequestMethod.GET)
	public String goPurchaseSucceed(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum = "";
		String userId = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
			userId = userInfoMap.get("userId");
		} else {
			phoneNum = us.getMobile();
			userId = String.valueOf(us.getId());
		}

		// 订单号
		String orderId = request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("userId", userId);
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/order/getOrderFormDetails",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("订单信息解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		if (jsonObjRtn != null && Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
			List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
			if (listDetail != null && listDetail.size() > 0) {
				Map<String, Object> obj = CommonUtil.jsonObjToHashMap(listDetail.get(0));
				request.setAttribute("productName", obj.get("productName"));
				request.setAttribute("investAmount", obj.get("investAmount"));
				request.setAttribute("annualRate", obj.get("annualRate"));
				request.setAttribute("investPeriod", obj.get("investPeriod"));
				request.setAttribute("orderId", obj.get("orderId"));
				request.setAttribute("rateRises", obj.get("rateRises"));
				request.setAttribute("adjustRate", obj.get("adjustRate"));
				request.setAttribute("remanDays", obj.get("remanDays"));// 购买成功的页面增加remanDays返回
			}
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/fixBuySuccess";
	}

	/**
	 * 解绑银行卡申请
	 * 
	 * @param out
	 * @param current
	 * @param request
	 * @param res
	 */
	@RequestMapping(value = "/addUnbindingCardApply", method = RequestMethod.GET)
	public void addUnbindingCardApply(PrintWriter out, String current, HttpServletRequest request,
			HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String idCard = request.getParameter("idCard");

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		map.put("idCard", idCard);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/addUnbindingCardApply",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);

	}

	/**
	 * 跳转到解绑银行卡页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goUnhitchBankCard", method = RequestMethod.GET)
	public String goUnhitchBankCard(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/unhitchBankCard";
	}

	/**
	 * 跳转到邀请列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goFriendList", method = RequestMethod.GET)
	public String goFriendList(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/friendList";
	}

	/**
	 * 跳转到我的二维码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goMyQuickMark", method = RequestMethod.GET)
	public String goMyQuickMark(HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		if (StringUtils.isBlank(mobile)) {
			mobile = request.getParameter("mobile");
		}

		request.setAttribute("mobile", mobile);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/myQuickMark";
	}

	/**
	 * 用户获取收益率查询
	 * 
	 * @param out
	 * @param current
	 * @param request
	 * @param res
	 */
	@RequestMapping(value = "/getYield", method = RequestMethod.GET)
	public void getYield(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String dayNum = request.getParameter("dayNum");
		if (null == dayNum || "".equalsIgnoreCase(dayNum)) {
			dayNum = "7";
		}

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		map.put("dayNum", dayNum);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/chart/getYield", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);

	}

	/**
	 * 用户获取收益率查询
	 * 
	 * @param out
	 * @param current
	 * @param request
	 * @param res
	 */
	@RequestMapping(value = "/historyAmount", method = RequestMethod.GET)
	public void historyAmount(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String pageSize = request.getParameter("pageSize");
		String current1 = request.getParameter("current");

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("userId", userId);
		map.put("current", current1);
		map.put("pageSize", pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/chart/historyAmount", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}

	/**
	 * 查询下一个工作日
	 * 
	 * @param out
	 * @param current
	 * @param request
	 * @param res
	 */
	@RequestMapping(value = "/queryNextWorkingDay", method = RequestMethod.GET)
	public void queryNextWorkingDay(PrintWriter out, String current, HttpServletRequest request,
			HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String withdrawFlag = request.getParameter("withdrawFlag");

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("withdrawFlag", withdrawFlag);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/queryNextWorkingDay",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);

	}

	/**
	 * 跳转到理财资产页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/financialAsset", method = RequestMethod.GET)
	public String financialAsset(HttpServletResponse res, HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/financialAsset";
	}

	/**
	 * 跳转新增订单确认页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/orderConfirm")
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String orderConfirm(HttpServletResponse res, HttpServletRequest request) {

		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		// 分享验签机制
		String loanId = request.getParameter("loanId");
		String productName = request.getParameter("productName");
		String remanAmount = request.getParameter("remanAmount");
		String annualRate = request.getParameter("annualRate");
		String investMinAmount = request.getParameter("investMinAmount");
		String acctBalance = request.getParameter("acctBalance");
		String adjustRate = request.getParameter("adjustRate");
		String id = request.getParameter("id");

		Map<String, Object> reqJavaMap = new HashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询零钱代金券数量加密失败:" + e);
			e.printStackTrace();
		}

		String resultMsg = "";

		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userVochers/queryVouchersCount",
				param);

		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查询零钱代金券数量解密失败:" + e);
			e.printStackTrace();
		}

		String vouchersCount = "0";
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);

		String rescode = jsonObjRtn.getString("rescode");
		if ("00000".equals(rescode)) {
			vouchersCount = jsonObjRtn.getString("vouchersCount");
		}
		request.setAttribute("vouchersCount", vouchersCount);
		request.setAttribute("loanId", loanId);
		request.setAttribute("productName", productName);
		request.setAttribute("annualRate", annualRate);
		request.setAttribute("remanAmount", remanAmount);
		request.setAttribute("investMinAmount", investMinAmount);
		request.setAttribute("acctBalance", acctBalance);
		request.setAttribute("adjustRate", adjustRate);
		request.setAttribute("id", id);
		
		/**压userId+mobile 2016-11-09 11:38:00 by rxc**/
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", us.getMobile());

		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/orderConfirm";
	}

	/**
	 * 跳转账户余额页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public String balance(HttpServletResponse res, HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/balance";
	}

	/**
	 * 获取充值银行列表
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/queryBankList")
	public void getEnjoyCreditList(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		logger.info("======进入方法：wxtrade/queryBankList====");

		String bankFlg = request.getParameter("bankFlg");
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("bankFlg", bankFlg == null ? "KJ" : bankFlg);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取银行列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/bank/queryBankList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取银行列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}

	/**
	 * 获取充值路由银行列表
	 *
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/queryRouteBankList")
	public void queryRouteBankList(HttpServletRequest request, PrintWriter out) {
		logger.info("======进入方法：wxtrade/queryBankList====");

		String bankFlg = request.getParameter("bankFlg");
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("bankFlg", bankFlg == null ? "KJ" : bankFlg);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取银行列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/bank/queryRouteBankList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取银行列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	

	/**
	 * 交易记录详情
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getFundFlowDetail")
	public void getFundFlowDetail(HttpServletRequest request, PrintWriter out) {
		String flowId = request.getParameter("flowId"); // 流水号
		String transFlowNo = request.getParameter("transFlowNo"); // 交易流水号、
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultMsg ="";

		String logInfo = "获取流水详情webServiceflowId=" + flowId + "&transFlowNo=" + transFlowNo;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flowId", flowId);
		map.put("transFlowNo", transFlowNo);

		logger.info(
				logInfo + LogUtil.getStart("getFundFlowDetail", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wallet/getFundFlowDetail",
					param);
			if (null == resultMsg) {
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}

			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");

			try {

				JSONObject.fromObject(resultMsg);

			} catch (Exception e) {
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				logger.error(logInfo + "JavaService系统返回异常resultMsg=" + resultMsg);
				logger.info(logInfo + "  结束");
				return;

			}

		} catch (Exception e) {
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.info(logInfo + "  结束");
			return;

		}

		logger.info(logInfo + " 正常返回" + resultMsg);
		setResposeMsg(resultMsg, out);
	}

	private String nullToBlank(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}
	
	
	/**
	 * 跳转风险评估
	 *
	 * @return
	 */
	@RequestMapping(value = "/goRiskEvaluation")
	public String goRiskEvaluation(HttpServletResponse res, HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		if (channel == null ||"".equals(channel)||"LBWX".equalsIgnoreCase(channel)) { //微信或者浏览器
			UserSession us = UserCookieUtil.getUser(request);
			if (null == us || null == us.getId()) {
				// 用户表示验证机制（通过微信标识OPENID）
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				userId = userInfoMap.get("userId");
			} else {
				userId = String.valueOf(us.getId());
			}
		}
		
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		request.setAttribute("flag", request.getParameter("flag"));//标志
		request.setAttribute("channel", request.getParameter("channel"));//渠道   区分是app还是微信
		request.setAttribute("riskType", request.getParameter("riskType"));//评估类型

		return "weixin/accout/riskEvaluation";
	}

	/**
	 * 跳转风险评估结果
	 *
	 * @return
	 */
	@RequestMapping(value = "/goRiskEvaluationResult")
	public String goRiskEvaluationResult(HttpServletResponse res, HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		if (channel == null ||"".equals(channel)||"LBWX".equalsIgnoreCase(channel)) { //微信或者浏览器
			UserSession us = UserCookieUtil.getUser(request);
			if (null == us || null == us.getId()) {
				// 用户表示验证机制（通过微信标识OPENID）
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				userId = userInfoMap.get("userId");
			} else {
				userId = String.valueOf(us.getId());
			}
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		request.setAttribute("flag", request.getParameter("flag"));//标志
		request.setAttribute("channel", request.getParameter("channel"));//渠道   区分是app还是微信
		request.setAttribute("riskType", request.getParameter("riskType"));//评估类型

		return "weixin/accout/riskEvaluationResult";
	}
	
	/**
	 * 跳转风险评估入口
	 *
	 * @return
	 */
	@RequestMapping(value = "/goRiskEvaluationType")
	public String goRiskEvaluationType(HttpServletResponse res, HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		if (channel == null ||"".equals(channel)||"LBWX".equalsIgnoreCase(channel)) { //微信或者浏览器
			UserSession us = UserCookieUtil.getUser(request);
			if (null == us || null == us.getId()) {
				// 用户表示验证机制（通过微信标识OPENID）
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				userId = userInfoMap.get("userId");
			} else {
				userId = String.valueOf(us.getId());
			}
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		request.setAttribute("flag", request.getParameter("flag"));//标志
		request.setAttribute("channel", request.getParameter("channel"));//渠道   区分是app还是微信
		request.setAttribute("riskType", request.getParameter("riskType"));//评估类型

		return "weixin/accout/riskEvaluationType";
	}
	
	/**
	 * 跳转风险评估类型
	 *
	 * @return
	 */
	@RequestMapping(value = "/goRiskEvaluationEntrance")
	public String goRiskEvaluationEntrance(HttpServletResponse res, HttpServletRequest request) {

		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		if (channel == null ||"".equals(channel)||"LBWX".equalsIgnoreCase(channel)) { //微信或者浏览器
			UserSession us = UserCookieUtil.getUser(request);
			if (null == us || null == us.getId()) {
				// 用户表示验证机制（通过微信标识OPENID）
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				userId = userInfoMap.get("userId");
			} else {
				userId = String.valueOf(us.getId());
			}
		}
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		request.setAttribute("flag", request.getParameter("flag"));//标志
		request.setAttribute("channel", request.getParameter("channel"));//渠道   区分是app还是微信
		request.setAttribute("riskType", request.getParameter("riskType"));//评估类型

		return "weixin/accout/riskEvaluationEntrance";
	}
	
	/**
	 * 跳转风险评估
	 *
	 * @return
	 */
	@RequestMapping(value = "/queryRiskEvaluation")
	public void queryRiskEvaluation(HttpServletResponse res, 
			HttpServletRequest request,PrintWriter out) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		
		if (channel == null ||"".equals(channel)||"LBWX".equalsIgnoreCase(channel)) { //微信或者浏览器
			UserSession us = UserCookieUtil.getUser(request);
			if (null == us || null == us.getId()) {
				// 用户表示验证机制（通过微信标识OPENID）
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				userId = userInfoMap.get("userId");
			} else {
				userId = String.valueOf(us.getId());
			}
		}

		if (userId ==null || "".equals(userId)) {//如果手机号码为空，通过手机号码查询userId
			Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
			paramsMap.put("mobile",mobile);
			String param1 = CommonUtil.getParam(paramsMap);
			try {
				param1 = DES3Util.encode(param1);
				//获取用户id
				String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath 
											+ "/wxuser/getUserIdByMobile", param1);
				checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
				JSONObject checkCaptchatObject1 = JSONObject.fromObject(checkCaptchatMsg1);
				userId = String.valueOf(checkCaptchatObject1.get("userId"));
				if (userId ==null || "".equals(userId)|| "".equals(userId)) {
					resultMap.put("rescode", Consts.ERROR_CODE);
					resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
					resultMap.put("resmsg_cn","请先登陆！");
					setResposeMap(resultMap, out);
					return;
				}
			} catch (Exception e) {
				logger.info("查询任务列表加密失败:",e);
				resultMap.put("rescode", Consts.ERROR_CODE);
				resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
				resultMap.put("resmsg_cn","请先登陆！");
				setResposeMap(resultMap, out);
				return;
			}
		} 

		Map<String, Object> map = new HashMap<String, Object>();
		String resultMsg ="";
		String answer = request.getParameter("answer");//用户答案
		map.put("userId", userId);
		map.put("answer", answer);
		String param = CommonUtil.getParam(map);
		
		logger.info( LogUtil.getStart("queryRiskEvaluation", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
	
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
		}

		// 调用service接口
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath 
				+ "/deposit/queryRiskEvaluation",param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			setResposeMsg(jsonObject.toString(), out);
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			logger.info( LogUtil.getEnd("queryRiskEvaluation", "方法执行结束", "WeixinTradeAction"));
			setResposeMap(resultMap, out);
			return;
		}
		logger.info("queryRiskEvaluation 方法 正常返回" + resultMsg);
		
	}

	/**
	 * 获取是否风险评测 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRiskEvaluationById")
	public void getRiskEvaluationById(HttpServletResponse res, 
			HttpServletRequest request,PrintWriter out)throws Exception{
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		
		if (channel == null ||"".equals(channel)||"LBWX".equalsIgnoreCase(channel)) { //微信或者浏览器
			UserSession us = UserCookieUtil.getUser(request);
			if (null == us || null == us.getId()) {
				// 用户表示验证机制（通过微信标识OPENID）
				Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
				userId = userInfoMap.get("userId");
			} else {
				userId = String.valueOf(us.getId());
			}
		}

		if (userId ==null || "".equals(userId)) {//如果手机号码为空，通过手机号码查询userId
			Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
			paramsMap.put("mobile",mobile);
			String param1 = CommonUtil.getParam(paramsMap);
			try {
				param1 = DES3Util.encode(param1);
				//获取用户id
				String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath 
											+ "/wxuser/getUserIdByMobile", param1);
				checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
				JSONObject checkCaptchatObject1 = JSONObject.fromObject(checkCaptchatMsg1);
				userId = (String) checkCaptchatObject1.get("userId");
				if (userId ==null || "".equals(userId)|| "".equals(userId)) {
					resultMap.put("rescode", Consts.ERROR_CODE);
					resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
					resultMap.put("resmsg_cn","请先登陆！");
					setResposeMap(resultMap, out);
					return;
				}
			} catch (Exception e) {
				logger.info("查询任务列表加密失败:",e);
			}
		}  
		
		String resultMsg ="";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		
		try {
			logger.info( LogUtil.getStart("getRiskEvaluationById", "方法开始执行", "WeixinTradeAction", getProjetUrl(request)));
			String param = CommonUtil.getParam(map);
			param = DES3Util.encode(param);
			// 调用service接口
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath 
				+ "/deposit/getRiskEvaluationById",param);
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			setResposeMsg(jsonObject.toString(), out);
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			logger.info( LogUtil.getEnd("getRiskEvaluationById", "方法执行结束", "WeixinTradeAction"));
			setResposeMap(resultMap, out);
			return;
		}
		logger.info("queryRiskEvaluation 方法 正常返回" + resultMsg);
	
	}
	
	/**
	 * 查询 提现确认最终信息
	 * @param request
	 * @param res
	 * @param out
	 */
	@RequestMapping(value = "/queryFinalWithdrawlInfo")
	public void queryFinalWithdrawlInfo(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}

		String extractMoney = request.getParameter("extractMoney");
		String withdrawFlag = request.getParameter("withdrawFlag"); // 提现标识 ZSHTPAY
		
		/**
		 * 封装接口参数
		 */
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", userId);
		map.put("extractMoney", extractMoney);
		map.put("withdrawFlag", withdrawFlag);

		logger.info(LogUtil.getStart("queryFinalWithdrawlInfo", "方法开始执行", "WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取确认提现信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/withdrawals/queryFinalWithdrawlInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 获取确认提现信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 转换驼峰命名
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		
		setResposeMsg(jsonObj.toString(), out);
	}

}