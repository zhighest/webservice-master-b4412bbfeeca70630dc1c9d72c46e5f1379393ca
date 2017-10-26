package com.web.actions.running.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.web.actions.weixin.common.BaseAction;
import com.web.domain.PaymentInfo;
import com.web.util.CommonUtil;
import com.web.util.ConstantUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.SendSMSUtil;
import com.web.util.llpay.LLPayHostingEnvUtil;
import com.web.util.llpay.LLPayUtil;
import com.web.util.llpay.Md5Algorithm;
import com.web.util.llpay.RSAUtil;
import com.web.vo.PayDataBean;

/**
 * 
 * ClassName: RunningAction 
 * @Description: 用户相关信息
 * @author qibo
 * @date 2015-9-7 下午3:36:35
 */
@Controller
@RequestMapping("/running")
@Scope("prototype")
public class RunningAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(RunningAction.class);
	
	/**
	 * 查询剩余名额
	 * @return
	 */ 
	@RequestMapping(value = "/getSurplusPeople")
	public void setInvitationCd(HttpServletRequest request,HttpServletResponse res,  PrintWriter out) {
		JSONObject reqJson = new JSONObject();
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
//		userMap.put("userId", userId);
//		userMap.put("invitationCode", invitationCd);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/running/getSurplusPeople", param);
		logger.info("==========resultMsg["+resultMsg+"]");
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询剩余名额解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
		return;
	}
	
	/**
	 * 查询报名者信息(考虑是否过期)
	 * @return
	 */ 
	@RequestMapping(value = "/getEnrollInfo")
	public void getEnrollInfo(String mobile,HttpServletRequest request,HttpServletResponse res,  PrintWriter out) {
		JSONObject reqJson = new JSONObject();
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();

		userMap.put("mobile", mobile);//过期标识：1 未过期标识：2
		userMap.put("checkTimeFlg", "1");
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/running/getEnrollInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察报名者信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
		return;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goApplyFailed", method = RequestMethod.GET)
	public String goApplyFailed(String mobile, HttpServletRequest request, HttpSession session) {
		
		request.setAttribute("mobile", mobile);
		return "running/applyFailed";
	}

	/**
	 * 充值返回方法
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goApplySuccess")
	public String goApplySuccess(String llPayRtu,HttpServletRequest request, HttpSession session) {
		
		String mobile = request.getParameter("mobile");
		request.setAttribute("mobile", mobile);

		if (StringUtils.isBlank(llPayRtu)) {
			return "running/applySuccess";
		}
		String resData = request.getParameter("res_data");
		boolean isSucceed = StringUtils.isNotBlank(resData);
		
		if(!isSucceed){
			//调用更新失败订单
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("mobile", mobile);
			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("更新订单状态加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			String resultMsg = "";
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
					+ "/running/marathonUpdateFaildOrder", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				logger.info("更新订单状态加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			JSONObject resJson = JSONObject.fromObject(resultMsg);
			String rescode = resJson.getString("rescode");
			
			if (!rescode.equals(Consts.SUCCESS_CODE)) {
				request.setAttribute("resmsg", resJson.getString("resmsg"));
				logger.info("==================更新失败订单状态失败！===================");
				logger.info(resJson.getString("resmsg"));
			}
			request.setAttribute("rescode", rescode);
			
			return "running/applyFailed";
		}
		return "running/applySuccess";
	}

	/**
	 * 跳转到报名页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goMarathonLogin")
	public String goMarathonLogin(String mobile, HttpServletRequest request, HttpSession session) {
		
		request.setAttribute("mobile", mobile);
		return "running/marathonLogin";
	}
	
	
	/**
	 * 验证验证码
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/checkCapche")
	public void checkCapche(HttpServletRequest request, HttpSession session, PrintWriter out) {
		String mobile = request.getParameter("mobile");
		String checkCode = request.getParameter("checkCode");
		JSONObject reqJson = new JSONObject();
		
		if (StringUtils.isBlank(mobile)) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "手机号不能为空！");
			setResposeMsg(reqJson.toString(), out);
			return;
		}

		if (!mobile.matches(Consts.PHONECHECK)) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "请输入规范的手机号码！");
			setResposeMsg(reqJson.toString(), out);
			return;
		}

		if (StringUtils.isBlank(checkCode)) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "验证码不能为空！");
			setResposeMsg(reqJson.toString(), out);
			return;
		}
		
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("phoneNum", mobile);
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
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", checkCaptchatObject.getString("resmsg_cn"));
			setResposeMsg(reqJson.toString(), out);
			return;
		}

		reqJson.put("rescode", Consts.SUCCESS_CODE);
		setResposeMsg(reqJson.toString(), out);
		return;
	}

	
	/**
	 * 报名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/signUp")
	public void signUp(HttpServletRequest request, HttpSession session,PrintWriter out) {
		String mobile = request.getParameter("mobile");
		String realName = request.getParameter("realName");
		String idCard = request.getParameter("idCard");
		String materielSize = request.getParameter("materielSize");

		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("mobile", mobile);
		userMap.put("realName", realName);
		userMap.put("idCard", idCard);
		userMap.put("materielSize", materielSize);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
		}
		
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/running/signUp", param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
		}
		setResposeMsg(checkCaptchatMsg, out);
	}


	/**
	 * 跳转到支付页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goMarathonPay", method = RequestMethod.GET)
	public String goMarathonPay(String mobile, HttpServletRequest request,
			HttpSession session) {
		request.setAttribute("mobile", mobile);
		return "running/marathonPay";
	}
	
	/**
	 * 支付
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/payInfo", method = RequestMethod.POST)
	public void PayInfo(String mobile, String cardNo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, PrintWriter out) {
		JSONObject resJson = new JSONObject();
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();

		userMap.put("mobile", mobile);//过期标识：1 未过期标识：2
		userMap.put("checkTimeFlg", "1");
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/running/getEnrollInfo", param);
		logger.info("==========resultMsg["+resultMsg+"]");
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察报名者信息解密失败:" + e.getMessage());
		}
		
		//解析返回结果
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		//返回编码
		String rescode = jsonObject.getString("rescode");
		
		if(!Consts.SUCCESS_CODE.equals(rescode)) {
			resJson.put("rescode", Consts.CHECK_CODE);
			resJson.put("resmsg_cn", "您的报名信息超时");
			setResposeMsg(resJson.toString(), out);
			return;
		}else{
			//报名用户信息
			JSONObject enrollInfoObject = jsonObject.getJSONObject("enrollInfo");
			//真实姓名
			String realName = enrollInfoObject.getString("realName");
			//身份证号码
			String idCard = enrollInfoObject.getString("idCard");
			String userId = enrollInfoObject.getString("id");
			
			// 充值金额
			String moneyOrder = "0.01";
			// 拼接提交连连支付的参数
			PaymentInfo payInfo = new PaymentInfo();
			// 充值金额
			payInfo.setMoney_order(moneyOrder);
			// 请求认证标识
			payInfo.setApp_request(LLPayHostingEnvUtil.getValue("app_requst_wap"));
			// 商户业务类型
			payInfo.setBusi_partner(LLPayHostingEnvUtil
					.getValue("busi_partner_virtual"));
			// 银行卡卡号
			if (cardNo != null) {
				payInfo.setCard_no(cardNo);
			}
			// 商户订单时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String dtOrder = sdf.format(new Date());
			payInfo.setDt_order(dtOrder);
			
			// 查询卡bin信息是否存在
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("cardNo", cardNo);
			reqJavaMap.put("payType", "D");

			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询银行卡卡bin加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/userCard/queryCardBinLLPay", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("查询银行卡卡bin解密失败:" + e.getMessage());
			}

			Map<String, Object> userCardBinMap = parseJsonForMap(resultMsg);
			
			/*Map<String, Object> userCardBinMap = new HashMap<String,Object>();
			userCardBinMap.put("rescode","00");
			userCardBinMap.put("bankCode","01040000");
			userCardBinMap.put("bankName","中国银行");*/
			
			
			String rescodeJava = userCardBinMap.get("rescode").toString();
			if (!(rescodeJava.equals("00"))) {
				resJson.put("rescode", Consts.CHECK_CODE);
				resJson.put("resmsg_cn", "输入的银行卡卡号不正确，未能查询到银行卡信息，请重新输入！");
				setResposeMsg(resJson.toString(), out);
				return;
			}
			
			//判断联璧数据库是否存在该银行信息
			String bankCode = userCardBinMap.get("bankCode").toString();
			reqJavaMap.put("bankCode", bankCode);
			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询银行是否支持失败:" + e.getMessage());
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/userCard/queryBankExistCode", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("查询银行是否支持失败:" + e.getMessage());
				e.printStackTrace();
			}
			Map<String, Object> bankMap = parseJsonForMap(resultMsg);
			String bankExist = bankMap.get("isExist").toString();
			
			if(!Boolean.parseBoolean(bankExist)){
				resJson.put("rescode", Consts.CHECK_CODE);
				resJson.put("resmsg_cn", "对不起，暂不支持此银行，请选择其他银行卡支付！");
				setResposeMsg(resJson.toString(), out);
				return;
			}

			// 商户编号
			payInfo.setOid_partner(LLPayHostingEnvUtil.getValue("oid_partner"));
			// 签名方式
			payInfo.setSign_type(LLPayHostingEnvUtil.getValue("sign_type"));
			
			// 版本号
			payInfo.setVersion(LLPayHostingEnvUtil.getValue("version"));
			
			// 背景颜色
			payInfo.setBg_color(LLPayHostingEnvUtil
					.getValue("bg_color"));
			
			// 生成订单号
			String noOrder = createNoOrder();
			// 商户唯一订单号
			payInfo.setNo_order(noOrder);
			// 商品名称
			payInfo.setName_goods("报名充值");
			// 订单描述
			payInfo.setInfo_order("马拉松活动报名充值");
			// 服务器异步通知地址
			payInfo.setNotify_url(LLPayHostingEnvUtil.getValue("run_notify_url"));
			// 返回路径
			payInfo.setUrl_return(LLPayHostingEnvUtil.getValue("run_url_return") + "?mobile=" + mobile + "&llPayRtu=1");
			
			// 风控控制参数
			JSONObject riskObject = new JSONObject();
			// 商品类目 (P2P小额贷款 :2009)
			riskObject.put("frms_ware_category", "2009");
			// 商户用户唯一标识
			riskObject.put("user_info_mercht_userno", userId);
			//用户绑定手机号
			riskObject.put("user_info_bind_phone", mobile);
			//商户用户分类
			riskObject.put("user_info_mercht_usertype", "1");
			//用户注册时间
			//转换时间
			String regTimeString = dtOrder;
			riskObject.put("user_info_dt_register", regTimeString);
			//用户注册姓名：真实姓名
			riskObject.put("user_info_full_name", realName);
			//用户注册证件类型
			riskObject.put("user_info_id_type", "0");
			//用户注册证件号码
			riskObject.put("user_info_id_no",  idCard);
			//是否实名认证
			riskObject.put("user_info_identity_state","1");
			//实名认证方式
			riskObject.put("user_info_identity_type", "3");
			
			payInfo.setRisk_item("");
			// 用户ID
			payInfo.setUser_id(userId);
			// 证件号码
			payInfo.setId_no(idCard);
			// 账号名称
			payInfo.setAcct_name(realName);
			
			String llpayNoAgree = request.getParameter("llpayNoAgree");
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
				sign = RSAUtil.sign(LLPayHostingEnvUtil
						.getValue("trader_pri_key"), sign_src);
			} else {
				sign_src += "&key="
						+ LLPayHostingEnvUtil.getValue("md5_key");
				try {
					sign = Md5Algorithm.getInstance().md5Digest(
							sign_src.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			payInfo.setSign(sign);

			/** 更新临时订单表 **/
			Map<String, Object> dealMap = new HashMap<String, Object>();
			// 保存订单信息
			String signSrc = JSON.toJSONString(payInfo).replaceAll("=", "||");
			dealMap.put("userId", userId);
			dealMap.put("moneyOrder", moneyOrder);
			dealMap.put("signSrc", signSrc);
			dealMap.put("noOrder", noOrder);
			dealMap.put("oidPartner",
					LLPayHostingEnvUtil.getValue("oid_partner"));
			dealMap.put("dtOrder", dtOrder);
			dealMap.put("rechargeChannel", "MOBILE");

			param = CommonUtil.getParam(dealMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
			}

			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/userCard/dealLLPayFastPayToken", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("保存银行卡信息加密失败:" + e.getMessage());
			}

			Map<String, Object> dealResultMap = parseJsonForMap(resultMsg);

			rescode = dealResultMap.get("rescode").toString();
			if (!(rescode.equals(Consts.SUCCESS_CODE))) {
				resJson.put("rescode", Consts.CHECK_CODE);
				resJson.put("resmsg_cn", "保存订单信息失败！");
				setResposeMsg(resJson.toString(), out);
				return;
			} else {
				resJson.put("rescode", Consts.SUCCESS_CODE);
				resJson.put("resmsg", "success");
				resJson.put("payInfo", JSON.toJSONString(payInfo));
				System.out.println("================================BEGINPRINT===================================");
				System.out.println( JSON.toJSONString(payInfo));
			}
			
			/** 更新报名表 **/
			Map<String, Object> signUpMap = new HashMap<String, Object>();
			dealMap.put("mobile", mobile);
			dealMap.put("payOrderId", noOrder);
			dealMap.put("payAmount", moneyOrder);
			dealMap.put("payBankNo", cardNo);
			param = CommonUtil.getParam(dealMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("更新报名表加密失败:" + e.getMessage());
			}

			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/running/updateRunningSignUpInfo", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("更新报名表解密失败:" + e.getMessage());
			}
			Map<String, Object> signResultMap = parseJsonForMap(resultMsg);
			rescode = signResultMap.get("rescode").toString();
			if (!Consts.SUCCESS_CODE.equals(rescode)) {
				resJson.put("rescode", Consts.CHECK_CODE);
				resJson.put("resmsg_cn", "更新订单信息失败！");
				setResposeMsg(resJson.toString(), out);
				return;
			}
		}
		setResposeMsg(resJson.toString(), out);
	}
	
	/**
	 * 随机生成订单
	 * 
	 * @return
	 */
	public String createNoOrder() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = outFormat.format(now);

		String strReq = currTime;
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 8; i++) {
			result += random.nextInt(10);
		}

		String requestNo = "MLS" + strReq + result;
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
		
	/**
	 * 发送手机验证码 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCaptcha")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
			String resultMsg = "";
			try {
				String mobile = request.getParameter("mobile");
				String type=request.getParameter("type");
				if(StringUtils.isBlank(type)){
					type="WXLOGIN";
				}else{
					if(type.equalsIgnoreCase("resetTradePassword"))
						type="RESET_TRADE_PASSWORD";
					else
						type=type.toUpperCase();
				}
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("phoneNum", mobile);
				map.put("type", type);
				map.put("setupFlag", ConstantUtil.setup_Flag);
				map.put("userId", mobile);
				String param = CommonUtil.getParam(map);
				param = DES3Util.encode(param);
				resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/sendSMS/captcha", param);
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				String result = CommonUtil.setResultStringCn(
						new HashMap<String, Object>(), Consts.ERROR_CODE, "", "网络或服务器连接异常,请稍后再试!");
				// 处理返回结果
				CommonUtil.responseJson(result, response);
				return;
			}
			// 处理返回结果
			CommonUtil.responseJson(resultMsg, response);
	}
	
	
	/**
	 * 连连支付回调
	 * @param printWriter
	 * @param request
	 * @param session
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/goMarathonLlpayNotify", method = RequestMethod.POST)
	public void goMarathonLlpayNotify(PrintWriter printWriter,
			HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("====================WebService进入连连支付成功回调接口=======================");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ret_code = "0000";
		String ret_msg = "交易成功";
		String reqStr = LLPayUtil.readReqStr(request);
		if (!LLPayUtil.isnull(reqStr)) {
			System.out.println("接收支付异步通知数据：【" + reqStr + "】");
			logger.info("接收支付异步通知数据：【" + reqStr + "】");
			try {
				if (!LLPayUtil.checkSign(reqStr,
						LLPayHostingEnvUtil.getValue("yt_pub_key"),
						LLPayHostingEnvUtil.getValue("md5_key"))) {
					logger.info("支付异步通知验签失败!");
					ret_code = "0001";
				}
			} catch (Exception e) {
				logger.info("异步通知报文解析异常!" + e.getMessage());
				ret_code = "0001";
			}

			// 解析异步通知对象
			PayDataBean payDataBean = JSON.parseObject(reqStr,
					PayDataBean.class);
			String noOrder = payDataBean.getNo_order();

			// 根据商户唯一订单号查询用户银行卡信息
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("bindingReqNum", noOrder);
			reqJavaMap.put("noAgree", payDataBean.getNo_agree() == null ? ""
					: payDataBean.getNo_agree());
			reqJavaMap.put("moneyOrder", payDataBean.getMoney_order());
			reqJavaMap.put("oidPayBill", payDataBean.getOid_paybill());
			reqJavaMap.put("reqStr", reqStr.replace("=", "||"));

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				ret_code = "0001";
				logger.info("连连回调信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/running/runningLLpayNotify", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
				logger.info("/running/runningLLpayNotify方法返回结果:"
						+ resultMsg);
				JSONObject resJson = JSONObject.fromObject(resultMsg);
				String rescode = resJson.getString("rescode");
				if (rescode.equals(Consts.SUCCESS_CODE)) {
					ret_code = "0000";
					
					/*//微信模板消息调用
					if(null!=resJson.getString("userId") && !"".equalsIgnoreCase(resJson.getString("userId"))){
						Map<String,String> requestMap = new HashMap<String,String>();
						requestMap.put("userId", resJson.getString("userId"));//用户ID
						requestMap.put("money", payDataBean.getMoney_order());//本次充值
						//发送充值
						WeixinRquestUtil.sendRechargeModelMessage(request,requestMap);
					}*/
				} else {
					ret_code = rescode;
					ret_msg = resJson.getString("resmsg");
				}
				//ouyy  报名并支付成功后，推送提示短信
				if("0000".equals(ret_code)){
					try {
						SendSMSUtil.sendSMSKz("", 
								"sms07003", "","",noOrder);
					} catch (Exception e) {
						logger.error("报名并支付成功后，推送提示短信失败:" + e.getMessage());
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ret_code = "0001";
				logger.info("连连回调信息解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
		} else {
			ret_code = "0001";
		}
		
		logger.info("====================WebService连连支付成功回调接口接口=======================");
		resultMap.put("ret_code", ret_code);
		resultMap.put("ret_msg", ret_msg);
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();
	}

}
