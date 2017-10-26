package com.web.actions.web.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSON;
import com.web.Interceptor.AvoidDuplicateSubmission;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.Bank;
import com.web.domain.PaymentInfo;
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
import com.web.util.weixinAbout.WeixinRquestUtil;
import com.web.vo.PayDataBean;

@Controller
@RequestMapping("/trade")

public class TradeAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(TradeAction.class);

	@RequestMapping(value = "/goTopUpcz", method = RequestMethod.GET)
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String goTopUpcz(HttpServletRequest request,
			HttpServletResponse res, HttpSession session) {
		// 保存原路径
		String topUpBackUrl = request.getHeader("Referer");
		session.removeAttribute("topUpBackUrl");
		session.setAttribute("topUpBackUrl", topUpBackUrl);
		/**
		 * 查询用户已绑定银行卡信息（页面显示）
		 */
		UserSession us = UserCookieUtil.getUser(request);

		String userId = String.valueOf(us.getId());

		logger.info(LogUtil.getStart("goTopUp", "方法开始执行", "TradeAction",
				getProjetUrl(request)));
		logger.info("userId=" + userId);
		//查询所有银行列表
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		String param = CommonUtil.getParam(paramsMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询支持银行加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
						LoginRedirectUtil.interfacePath + "/bank/queryAllBankInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			List<Bank> bankList = this.parseBankObject(resultMsg);
			request.setAttribute("bankList", bankList);
		} catch (Exception e) {
			logger.info("查询支持银行解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行卡加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/userCard/getUserBankCardInfo", param);

		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询银行卡解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> userCardMap = parseCardObject(resultMsg);
		Integer userCardInfoCount = Integer.parseInt(userCardMap.get(
				"userCardInfoCount").toString());
		List<UserRecePayCard> userCardList = (List<UserRecePayCard>) userCardMap
				.get("userCardList");
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
		return "web/accout/topUp";
	}

	/**
	 * 支付跳转第三方
	 * 
	 * @param request
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/topUp", method = RequestMethod.POST)
	@ResponseBody
	public void topUp(PrintWriter printWriter, HttpServletRequest request,
			HttpSession session) throws UnsupportedEncodingException {
		Map resultMap = new HashMap();
		/**
		 * 查询用户已绑定银行卡信息（页面显示）
		 */
		UserSession us = UserCookieUtil.getUser(request);
		String userId = String.valueOf(us.getId());
		// String userId = request.getParameter("userId");

		logger.info(LogUtil.getStart("topUp", "方法开始执行", "TradeAction",
				getProjetUrl(request)));
		logger.info("userId=" + userId);

		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询银行卡加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		
		String resultMsg = "";
		String bankCode = request.getParameter("bankCode");
		String moneyOrder = request.getParameter("moneyOrder");
		BigDecimal moneyOrderAmount = new BigDecimal(moneyOrder == null ? "0"
				: moneyOrder);
		if (moneyOrderAmount.compareTo(BigDecimal.ZERO) != 1) {
			resultMap.put("msg", "输入充值金额不正确，请修改后再进行提现！");
			String req_data = JSON.toJSONString(resultMap);
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
			printWriter.write(req_data);
			printWriter.flush();
			printWriter.close();
			return;
		}
		// 拼接提交连连支付的参数
		PaymentInfo payInfo = new PaymentInfo();
		// 版本号
		payInfo.setVersion(LLPayHostingEnvUtil.getValue("version"));
		// 参数字符编码集
		payInfo.setCharset_name(LLPayHostingEnvUtil.getValue("charset_name"));
		// 商户编号
		payInfo.setOid_partner(LLPayHostingEnvUtil.getValue("oid_partner"));

		// 商户业务类型
		payInfo.setBusi_partner(LLPayHostingEnvUtil
				.getValue("busi_partner_virtual"));

		// 获取用户实名认证信息
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/user/getIdcardInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户认证信息解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		
		
		
		Map<String, Object> userIdCardMap = parseJsonForMap(resultMsg);
		
		String idcardValidate = userIdCardMap.get("idcardValidate").toString();
		if (idcardValidate.equals("N")) {
			resultMap.put("msg", "当前登录用户还没有进行实名认证，请实名认证之后再进行充值！");
		}else{
			// 查询卡bin信息是否存在
			String utmSource = userIdCardMap.get("utmSource").toString();
			// 如果是迁移用户则传入迁移用户ID
			if (utmSource.equals("90")) {
				String depositDockUserId = userIdCardMap.get("depositDockUserId")
						.toString();
				payInfo.setUser_id(depositDockUserId);
			} else {
				payInfo.setUser_id(userId);
			}
			payInfo.setTimestamp(LLPayUtil.getCurrentDateTimeStr());
			// 商户订单时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String dtOrder = sdf.format(new Date());
			payInfo.setDt_order(dtOrder);
			payInfo.setUserreq_ip(LLPayUtil.getIpAddr(request).replaceAll("\\.",
					"_"));
			payInfo.setSign_type(LLPayHostingEnvUtil
					.getValue("sign_type"));
			// 判断用户是否已绑定卡 如果未绑定卡 则保存银行卡信息
			String noOrder = createNoOrder();
			payInfo.setNo_order(noOrder);
			// 商品名称
			payInfo.setName_goods("联璧网银支付充值");
			// 订单描述
			payInfo.setInfo_order("用户" + userId + "进行联璧网银充值操作");
			// 订单金额
			payInfo.setMoney_order(moneyOrder);
			// 服务器异步通知地址
			payInfo.setNotify_url(LLPayHostingEnvUtil.getValue("notify_wy_url"));
			// 订单地址
			payInfo.setUrl_return(LLPayHostingEnvUtil.getValue("url_wy_return"));
			// 订单有效时间
			payInfo.setValid_order("10080");
			// 银行编码
			payInfo.setBank_code(bankCode);
			// 风控参数
			payInfo.setRisk_item(this.getTopRiskItem(userIdCardMap, userId));

			String sign = LLPayUtil.addSign(
					JSON.parseObject(JSON.toJSONString(payInfo)),
					LLPayHostingEnvUtil.getValue("trader_pri_key"),
					LLPayHostingEnvUtil.getValue("md5_key"));
			payInfo.setSign(sign);

			resultMap.put("payInfo", JSON.toJSONString(payInfo));

			Map<String, Object> dealMap = new HashMap<String, Object>();
			// 保存订单信息
			String signSrc = JSON.toJSONString(payInfo).replaceAll("=", "||");
			dealMap.put("userId", userId);
			dealMap.put("moneyOrder", moneyOrder);
			dealMap.put("signSrc", signSrc);
			dealMap.put("noOrder", noOrder);
			dealMap.put("oidPartner", LLPayHostingEnvUtil.getValue("oid_partner"));
			dealMap.put("dtOrder", dtOrder);
			dealMap.put("rechargeChannel", "WEB");
			dealMap.put("platform", "LIANLIANPAY");
			
			param = CommonUtil.getParam(dealMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("保存订单信息加密失败:" + CommonUtil.printStackTraceToString(e));
				e.printStackTrace();
			}

			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
					+ "/userCard/dealLLPayFastPayToken", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("保存订单信息加密失败:" + CommonUtil.printStackTraceToString(e));
				e.printStackTrace();
			}

			Map<String, Object> dealResultMap = parseJsonForMap(resultMsg);

			String rescode = dealResultMap.get("rescode").toString();
			if (!(rescode.equals(Consts.SUCCESS_CODE))) {
				resultMap.put("msg", "保存订单信息失败！");
			} else {
				resultMap.put("msg", "success");
				resultMap.put("payInfo", JSON.toJSONString(payInfo));
				resultMap.put("payInfoObj", payInfo);
			}
		}
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		System.out
				.println("===================================LLPay Date Print========================================");
		System.out.println(req_data);
		logger.info("发送连连支付的请求参数：" + req_data);
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();
	}

	/**
	 * 支付成功界面
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/topUpSucceed")
	public String topUpSucceed(HttpServletRequest request,
			HttpServletResponse res, HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null != us) {
			userId = String.valueOf(us.getId());
		}
		String resData = request.getParameter("res_data");
		String noOrder = request.getParameter("no_order");
		

		boolean isSucceed = false;
		if(StringUtils.isNotBlank(resData)){
			isSucceed = true;
		}else if(StringUtils.isNotBlank(noOrder)){
			isSucceed = true;
		}
		
		request.setAttribute("isSucceed", isSucceed);
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId", userId);

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("更新订单状态加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/userCard/topUpSuccessd", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("更新订单状态加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
//		// 判断是否是从我的资产页面进入，如果是，则不显示继续理财
//		Object topUpBackUrlObj = session.getAttribute("topUpBackUrl");
//		if (topUpBackUrlObj == null) {
//			session.setAttribute("topUpBackUrl",
//					LLPayHostingEnvUtil.getValue("topUpBackUrl"));
//			session.setAttribute("continueButtonFalg", false);
//		} else {
//			String topUpBackUrl = topUpBackUrlObj.toString();
//			int myAssetIndex = topUpBackUrl.indexOf("/wxtrade/goMyAsset");
//			if (myAssetIndex > 0) {
//				session.setAttribute("continueButtonFalg", false);
//			} else {
//				session.setAttribute("continueButtonFalg", true);
//			}
//		}

		return "web/accout/topUpSucceed";
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
	public void llpayNotify(PrintWriter printWriter,
			HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("====================WebService进入连连网银支付成功回调接口=======================");
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
				logger.info("异步通知报文解析异常!" + CommonUtil.printStackTraceToString(e));
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
			reqJavaMap.put("payFlag", "wy");

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				ret_code = "0001";
				logger.info("更新用户银行卡信息加密失败:" + CommonUtil.printStackTraceToString(e));
				e.printStackTrace();
			}

			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/userCard/updateUserBankCardByReqNum", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
				logger.info("/userCard/updateUserBankCardByReqNum方法返回结果:"
						+ resultMsg);
				JSONObject resJson = JSONObject.fromObject(resultMsg);
				String rescode = resJson.getString("rescode");
				if (rescode.equals(Consts.SUCCESS_CODE)) {
					ret_code = "0000";
				} else {
					ret_code = rescode;
					ret_msg = resJson.getString("resmsg");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ret_code = "0001";
				logger.info("更新用户银行卡信息解密失败:" + CommonUtil.printStackTraceToString(e));
				e.printStackTrace();
			}

		} else {
			ret_code = "0001";
		}

		logger.info("====================WebService连连网银支付成功回调接口接口=======================");
		resultMap.put("ret_code", ret_code);
		resultMap.put("ret_msg", ret_msg);
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();

	}
	/**
	 * 跳转到我的资产页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goMyAsset", method = RequestMethod.GET)
	public String goMyAsset(HttpServletResponse res,HttpServletRequest request) {
		// 页面跳转标志
		request.setAttribute("pageTag", "myAsset");
		
		return "web/accout/myAsset";
	}
	
	/**
	 * 我的资产总览
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showAssets", method = RequestMethod.POST)
	public void showAssets(HttpServletRequest request,HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("showAssets", "方法开始执行",
				"TradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("我的资产总览加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/assets/showAssets", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("我的资产总览解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}
	/**
	 * 投资详情
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goInvestDetail", method = RequestMethod.POST)
	public String goInvestDetail(HttpServletRequest request, HttpSession session) {
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
		
		return "web/accout/investDetail";
	}
	
	/**
	 * 订单列表
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPurchaseDetailList", method = RequestMethod.POST)
	public void getPurchaseDetailList(HttpServletRequest request,HttpServletResponse res,
			PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		
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
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("current", current);
		map.put("pageSize", pageSize);
		// map.put("inOrOut", inOrOut);
		map.put("type", type);

		logger.info(LogUtil.getStart("getPurchaseDetailList", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId + "&current=" + current + "&pageSize="
				+ pageSize + "&type=" + type);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/order/getOrderInfoList",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("订单列表解密失败:" + CommonUtil.printStackTraceToString(e));
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
				pageObject = PageUtil.execsPage(Integer.parseInt(current),
						Integer.parseInt(size), 5, intPageSize);
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
	 * 交易记录
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getFundFlowList", method = RequestMethod.POST)
	public void getFundFlowList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
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
		
		// Modify by Tangchenbing in 2017-7-13 begin
		//分类类型（空：全部；1：充值；2：提现；3：活期投资；4：定期投资；5：活期赎回；6：定期赎回；7：奖励；8：代金券使用）
		String type = "";
		// Modify by Tangchenbing in 2017-7-13 end
		
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("current", current);
		map.put("pageSize", pageSize);
		map.put("inOrOut", inOrOut);
		
		// Modify by Tangchenbing in 2017-7-13 begin
		map.put("type", type);
		// Modify by Tangchenbing in 2017-7-13 end

		logger.info(LogUtil.getStart("getFundFlowList", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户流水加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wallet/getFundFlowList",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("用户流水解密失败:" + CommonUtil.printStackTraceToString(e));
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
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), 5, 10);
		}
		resultMap.put("sysdate", DateUtil.getCurrentDateStr());
		resultMap.put("pageObject", pageObject);
		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);
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
		List<UserRecePayCard> list = (List<UserRecePayCard>) JSONArray
				.toCollection(userCardArray, UserRecePayCard.class);
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

		String requestNo = "LB" + strReq + result;
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
		riskObject.put("user_info_bind_phone", paramMap.get("phoneNo")
				.toString());
		// 商户用户分类
		riskObject.put("user_info_mercht_usertype", "1");
		// 用户注册时间
		// 转换时间
		String regTimeString = paramMap.get("regTime").toString();
		riskObject.put("user_info_dt_register", regTimeString);
		// 用户注册姓名：真实姓名
		riskObject.put("user_info_full_name", paramMap.get("identityName")
				.toString());
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
	@SuppressWarnings("unchecked")
	public List<Bank> parseBankObject(String resultMsg) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		JSONArray bankArray = jsonObj.getJSONArray("bankList");
		List<Bank> bankList = (List<Bank>) JSONArray.toCollection(bankArray, Bank.class);
		return bankList;
	}
	
	/**
	 * 跳转到 个人中心账户总览页面
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goAccoutOverview"/*, method = RequestMethod.GET*/)
	public String goAccountOverview(HttpServletRequest request, HttpSession session) {
		logger.info("======进入方法：trade/goAccoutOverview====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId = String.valueOf(us.getId());
//		userId = "928648719";
		// 如果是连连回调过来 则调用topUpSuccessd接口
		String oid_partner = request.getParameter("oid_partner");
		if (StringUtils.isNotBlank(oid_partner)) {
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("userId", userId);
			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("更新订单状态加密失败:" + CommonUtil.printStackTraceToString(e));
			}

			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userCard/topUpSuccessd", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("更新订单状态加密失败:" + CommonUtil.printStackTraceToString(e));
			}
		}
		
		// 定期资产
		request.setAttribute("investAmountSum", "0.00");
		// 活期资产
		request.setAttribute("currentAmount", "0.00");
		// 优享资产
		request.setAttribute("enjoyPlanAmount", "0.00");
		// 优享收益
		request.setAttribute("enjoyYesterdayIncome", "0.00");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("version", "1.0.0");//默认设置为1.0.0
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/queryMyAccountDetail", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");

			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			if(Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))){
				JSONObject accObj = jsonObject.getJSONObject("accountDetail");
				String investAmountSum = accObj.getString("investAmountSum");
				String currentAmount = accObj.getString("currentAmount");
				String enjoyPlanAmount = accObj.getString("enjoyPlanAmount");
				String enjoyYesterdayIncome = accObj.getString("enjoyYesterdayIncome");
				request.setAttribute("investAmountSum", investAmountSum);
				request.setAttribute("currentAmount", currentAmount);
				request.setAttribute("curHelpNub", accObj.getString("currentAmount"));
				request.setAttribute("enjoyPlanAmount", enjoyPlanAmount);
				request.setAttribute("enjoyYesterdayIncome", enjoyYesterdayIncome);
				request.setAttribute("isValidYxjh", accObj.getString("isValidYxjh"));
				request.setAttribute("isValidDq", accObj.getString("isValidDq"));
				request.setAttribute("isValidHq", accObj.getString("isValidHq"));
			}
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		return "web/accout/accoutOverview";
	}
	
	
	/**
	 * 订单确认
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goOrderConfirmation", method = RequestMethod.POST)
	public String goOrderConfirmation(HttpServletRequest request,
			HttpSession session) {
		// 标的id
		String loanId = request.getParameter("loanId");
		request.setAttribute("loanId", loanId);
		// 上架表Id
		String sloanId = request.getParameter("sloanId");
		request.setAttribute("sloanId", sloanId);
		// 预期年化收益
		String earnings = request.getParameter("earnings");
		request.setAttribute("earnings", earnings);
		// 输入金额
		String inputAmount = request.getParameter("inputAmount");
		request.setAttribute("inputAmount", inputAmount);
		// 计划id
		String planId = request.getParameter("planId");
		request.setAttribute("planId", planId);
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/orderConfirmation";
	}
	
	/**
	 * 跳转到修改交易密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goChangeExchangePassword", method = RequestMethod.GET)
	public String goChangeExchangePassword(HttpServletRequest request) {
		// 1:修改密码 2：设置密码
		request.setAttribute("passwordState", 1);
		return WEIXIN_CHANGPASS;
	}
	
	/**
	 * 跳转到修改交易密码成功页面
	 */
	@RequestMapping(value = "/goChangePasswordCashSuccess", method = RequestMethod.GET)
	public String goWithdrawSuccess(HttpServletRequest request,
			HttpSession session) {
		return "weixin/accout/changePasswordCashSuccess";
	}
	
	/**
	 * 跳转绑卡显示页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goHadBankCard", method = RequestMethod.GET)
	public String goHadBankCard(HttpServletRequest request) {
		return "weixin/accout/hadBankCard";
	}
	
	/**
	 * 跳转到设置邀请码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goSetInvitationCd", method = RequestMethod.GET)
	public String goSetInvitationCd(HttpServletRequest request) {
		return "weixin/accout/setInvitationCd";
	}
	
	/**
	 * 设置邀请码
	 */
	@RequestMapping(value = "/setInvitationCd", method = RequestMethod.POST)
	public void setInvitationCd(String invitationCd,
			HttpServletRequest request,HttpServletResponse res,  PrintWriter out) {
		logger.info("=======进入setInvitationCd方法 接受参数invitationCd："+ invitationCd);
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
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("invitationCode", invitationCd);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxuser/updateInvitationCode", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		// 交易密码正确
		if (!Consts.SUCCESS_CODE.equals(jsonObject.get("rescode"))) {
			reqJson.put("rescode", Consts.CHECK_CODE);
			reqJson.put("resmsg_cn", "设置邀请码失败,请稍后重试！");
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
	public void findInvitationCd(HttpServletRequest request,HttpServletResponse res, PrintWriter out) {
		JSONObject reqJson = new JSONObject();
		
		UserSession us = UserCookieUtil.getUser(request);
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		
		String phoneNum="";
		if(null != us){
			phoneNum = us.getMobile();
		}
		
		userMap.put("phoneNum",phoneNum);
		
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		JSONObject user = jsonObject.getJSONObject("user");
		//用户存在
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
	public void changeExchangePassword(String passwordCashOld,
			String passwordCashNew, HttpServletRequest request,HttpServletResponse res,
			HttpSession session, PrintWriter out) {
		logger.info("=======进入changeExchangePassword方法");
		JSONObject reqJson = new JSONObject();
		// 原始密码验证
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum="";
		if(null != us){
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
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxuser/checkPasswordCash",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
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
			logger.info("加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String rsetMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxuser/resetPassword",
				paramRset);
		try {
			rsetMsg = java.net.URLDecoder.decode(DES3Util.decode(rsetMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + CommonUtil.printStackTraceToString(e));
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
	@RequestMapping(value = "/goWithdraw", method = RequestMethod.GET)
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String goWithdraw(HttpServletRequest request,HttpServletResponse res,
			HttpSession session) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null != us){
			mobile = us.getMobile();
		}
		request.setAttribute("mobile", mobile);
		return "web/accout/withdraw";
	}

	/**
	 * 提现申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "/withdrawDeposit", method = RequestMethod.POST)
	public void withdrawDeposit(HttpServletRequest request,HttpServletResponse res,
			HttpSession session, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		
		String extractMoney = request.getParameter("extractMoney");
		String cardNumber = request.getParameter("cardNumber");
		String cardId = request.getParameter("cardId");
		String setupFlag = "2";
		// 根据回款卡号查询回款卡号ID
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", userId);
		map.put("extractMoney", extractMoney);
		map.put("cardNumber", cardNumber);
		map.put("setupFlag", setupFlag);
		map.put("recePayCardId", cardId);
		map.put("passwordCash", request.getParameter("passwordCash"));

		logger.info(LogUtil.getStart("withdrawDeposit", "方法开始执行",
				"WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("申请提现加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath
						+ "/withdrawals/dealWithdrawal", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
			JSONObject msgJson = JSONObject.fromObject(resultMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 申请提现解密失败:" + CommonUtil.printStackTraceToString(e));
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
	public String withdrawDepositSuccess(HttpServletRequest request,
			HttpSession session) {
		return "weixin/accout/withdrawDepositSucceed";
	}

	/**
	 * 连连支付-提现回调接口
	 */
	@RequestMapping(value = "/withdrawDepositNotify", method = RequestMethod.POST)
	public void withdrawDepositNotify(PrintWriter printWriter,
			HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ret_code = "0000";
		String ret_msg = "交易成功";
		String reqStr = LLPayUtil.readReqStr(request);
		if (!LLPayUtil.isnull(reqStr)) {
			System.out.println("接收支付异步通知数据：【" + reqStr + "】");
			logger.info("进入连连支付-提现回调接口");
			logger.info("接收支付异步通知数据：【" + reqStr + "】");
			try {
				if (!LLPayUtil.checkSign(reqStr,
						LLPayHostingEnvUtil.getValue("yt_pub_key"),
						LLPayHostingEnvUtil.getValue("md5_key"))) {
					logger.info("提现异步通知验签失败!");
					ret_code = "0001";
				}
			} catch (Exception e) {
				logger.info("异步通知报文解析异常!" + CommonUtil.printStackTraceToString(e));
				ret_code = "0001";
			}
			/**
			 * 封装接口参数
			 * */
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
				logger.info("提现回调加密失败:" + CommonUtil.printStackTraceToString(e));
				ret_code = "0001";
				e.printStackTrace();
			}

			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/userCard/llpayWithdrawalsNotify", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("提现回调解密失败:" + CommonUtil.printStackTraceToString(e));
				ret_code = "0001";
				e.printStackTrace();
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
	 * 跳转设置页面
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/goSetting", method = RequestMethod.GET)
	public String goSetting(HttpServletRequest request, HttpSession session) {
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
	public void getRecePayBank(HttpServletRequest request, HttpServletResponse res,PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", userId);

		logger.info(LogUtil.getStart("getRecePayBank", "方法开始执行",
				"WeiXinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("绑定银行卡加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/investmentCenter/getRecePayBank", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 获取支持的银行列表解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		// 转换驼峰命名
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		String id = jsonObj.getString("id");
		String bankId = jsonObj.getString("bank_id");
		String resmsg = jsonObj.getString("resmsg");
		String bankName = jsonObj.getString("bank_name");
		String picturePath = jsonObj.getString("picturePath");
		String cardNumber = jsonObj.getString("card_number");
		String phoneNo = jsonObj.getString("phone_no");
		String cityName = jsonObj.getString("cityName");
		String rescode = jsonObj.getString("rescode");
		String resmsgCn = jsonObj.getString("resmsg_cn");
		String accountName = jsonObj.getString("account_name");

		JSONObject reqJson = new JSONObject();
		reqJson.put("id", id);
		reqJson.put("bankId", bankId);
		reqJson.put("resmsg", resmsg);
		reqJson.put("bankName", bankName);
		reqJson.put("picturePath", picturePath);
		reqJson.put("cardNumber", cardNumber);
		reqJson.put("phoneNo", phoneNo);
		reqJson.put("cityName", cityName);
		reqJson.put("rescode", rescode);
		reqJson.put("resmsgCn", resmsgCn);
		reqJson.put("accountName", accountName);
		
		String quickWithdrawTip = jsonObj.getString("quickWithdrawTip");
		String systemQuickAmount = jsonObj.getString("systemQuickAmount");
		String quickAmountDesc = jsonObj.getString("extractTipForWeb");
		String quickWithdrawFlag = jsonObj.getString("quickWithdrawFlag");
		String normalWithdrawTip = jsonObj.getString("normalWithdrawTip");
		
		reqJson.put("systemQuickAmount", systemQuickAmount);
		reqJson.put("quickWithdrawFlag", quickWithdrawFlag); // 快速提现开关
		reqJson.put("quickWithdrawTip", quickWithdrawTip); // 快速提现提示
		reqJson.put("normalWithdrawTip", normalWithdrawTip); // 普通提现提示
		reqJson.put("quickAmountDesc", quickAmountDesc); // 提现文案
		
		setResposeMsg(reqJson.toString(), out);
	}
	
	/**
	 * 理财人的累计收益--从投资开始计算人的累计收益 用户需要登录以后，查看的累计收益
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accumulatedIncome", method = RequestMethod.POST)
	public @ResponseBody
	String accumulatedIncome(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("accumulatedIncome", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("累计收益加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/assets/getAccumulatedIncome", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("累计收益解密失败:" + CommonUtil.printStackTraceToString(e));
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
	public @ResponseBody
	String accountBalance(HttpServletRequest request,HttpServletResponse res) {

		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("accountBalance", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("帐户余额加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/investmentCenter/getAccountBalance", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("帐户余额解密失败:" + CommonUtil.printStackTraceToString(e));
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
	 * 资产明细
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showAssetDetail", method = RequestMethod.POST)
	public void showAssetDetail(HttpServletRequest request,HttpServletResponse res, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		logger.info(LogUtil.getStart("buyLoanByBalance", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("资产明细加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/assets/showAssetDetail",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("资产明细解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}


	/**
	 * 跳转到福利页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goWelfare", method = RequestMethod.GET)
	public String goWelfare(HttpServletRequest request) {
		return "weixin/accout/Welfare";
	}


	/**
	 * 
	 * @Description: 根据订单id或者标的id查询订单信息
	 * @param request
	 * @param out
	 * @return void
	 * @throws
	 * @author qibo
	 * @date 2015-9-4 下午4:22:59
	 */
	@RequestMapping(value = "/getOrderFormDetails", method = RequestMethod.POST)
	public void getOrderFormDetails(HttpServletRequest request, PrintWriter out) {
		// 订单号
		String orderId = request.getParameter("orderId");
		// 标的id
		String loanId = request.getParameter("loanId");
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("loanId", loanId);

		logger.info(LogUtil.getStart("getOrderFormDetails", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("orderId=" + orderId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/order/getOrderFormDetails",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("订单列表解密失败:" + CommonUtil.printStackTraceToString(e));
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
	 * @throws
	 * @author qibo
	 * @date 2015-9-4 下午2:43:31
	 */
	@RequestMapping(value = "/getBorrowerCompanyInfo", method = RequestMethod.POST)
	public void getBorrowerCompanyInfo(HttpServletRequest request,
			PrintWriter out) {
		String sloanId = request.getParameter("sloanId"); // 当前页数
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sloanId", sloanId);
		logger.info(LogUtil.getStart("getBorrowerCompanyInfo", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("sloanId=" + sloanId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("订单列表加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/borrowerCompany/getBorrowerCompanyInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("订单列表解密失败:" + CommonUtil.printStackTraceToString(e));
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
	public void buyLoanByBalance(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		
		String buyCount = request.getParameter("buyCount"); // 购买份数
		String scatteredLoanId = request.getParameter("scatteredLoanId"); // 购买散标id
		String setupFlag = ConstantUtil.setup_Flag; // 发送标识（是来自网站用户，还是手机用户）:1：为来自网站的；2：来自手机的
		String planId = request.getParameter("planId"); // 计划id
		String cycleMatchType = request.getParameter("cycleMatchType"); // 循环匹配类型1、本息循环2、本金循环3、不循环
		String loanId = request.getParameter("loanId");
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("buyCount", buyCount);
		map.put("scatteredLoanId", scatteredLoanId);
		map.put("setupFlag", setupFlag);
		map.put("planId", planId);
		map.put("cycleMatchType", "3");
		map.put("loanId", loanId);

		logger.info(LogUtil.getStart("buyLoanByBalance", "方法开始执行",
				"WeixinTradeAction", getProjetUrl(request)));
		logger.info("userId=" + userId + "&buyCount=" + buyCount
				+ "&scatteredLoanId=" + scatteredLoanId + "&setupFlag="
				+ setupFlag + "&planId=" + planId + "&cycleMatchType="
				+ cycleMatchType);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("购买加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/investmentCenter/buyLoanByBalance", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("购买解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
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
		return "accout/transaction";
	}


	/**
	 * 跳转实名认证
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goAuthentication", method = RequestMethod.GET)
	public String goAuthentication(HttpServletRequest request) {
		// 保存原路径
		String backUrl = request.getHeader("Referer");
		request.setAttribute("backUrl", backUrl);
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
	public void authentication(HttpServletRequest request, HttpSession session,HttpServletResponse res,
			PrintWriter out, HttpSession session1) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null != us){
			userId = String.valueOf(us.getId());
		}
		
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
			logger.info("实名认证失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/user/getAuth", param);

		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("实名认证失败:" + CommonUtil.printStackTraceToString(e));
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
	public String authenticationSuccess(HttpServletRequest request,
			HttpSession session) {
		String redUrl = request.getParameter("redUrl");
		request.setAttribute("redUrl", redUrl);
		
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
	public void checkPasswordCash(String passwordCash,
			HttpServletRequest request, PrintWriter out,HttpServletResponse res)
			throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		UserSession us = UserCookieUtil.getUser(request);
		
		String phoneNum="";
		if(null != us){
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
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxuser/checkPasswordCash",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
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
			resultMap.put("message", "交易密码错误！");
		}
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
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
	public String goInviteFriend(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum="";
		if(null != us){
			phoneNum = us.getMobile();
		}
		
		request.setAttribute("mobile",phoneNum);
		return "weixin/accout/inviteFriend";
	}

	/**
	 * 银行限额
	 * 
	 * @return
	 */
	@RequestMapping(value = "/limitTable", method = RequestMethod.GET)
	public String limitTable(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		
		String phoneNum="";
		if(null != us){
			phoneNum = us.getMobile();
		}
		request.setAttribute("mobile", phoneNum);
		return "weixin/accout/limitTable";
	}

	/**
	 * 交易密码控件页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dealPWControls", method = RequestMethod.GET)
	public String dealPWControls(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum="";
		if(null != us){
			phoneNum = us.getMobile();
		}
		request.setAttribute("mobile", phoneNum);
		return "weixin/accout/dealPWControls";
	}

	/**
	 * 实名认证成功跳转
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/voucher", method = RequestMethod.GET)
	public String voucher(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		
		UserSession us = UserCookieUtil.getUser(request);

		String userId = String.valueOf(us.getId());
		
		request.setAttribute("userId", userId);
		return "web/accout/voucher";
	}
}
