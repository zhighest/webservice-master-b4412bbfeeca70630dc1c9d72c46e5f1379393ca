package com.web.actions.weixin.accout;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.llpay.LLPayHostingEnvUtil;
import com.web.util.llpay.LLPayUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;
import com.web.vo.PayDataBean;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/wxpay")
public class WeixinPayAction  extends BaseAction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(WeixinPayAction.class);
	
    @RequestMapping(value = "/cjSinglePayReturn")
	public String cjSinglePayReturn(HttpServletRequest request) {
    	
    	boolean isSucceed = true;
    	
    	request.setAttribute("isSucceed", isSucceed);
		return "weixin/accout/singlePayReturn";
	} 
	
    
    @RequestMapping(value = "/llSinglePayReturn")
   	public String llSinglePayReturn(HttpServletRequest request) {
    	String resData = request.getParameter("res_data");

		boolean isSucceed = StringUtils.isNotBlank(resData);

		request.setAttribute("isSucceed", isSucceed);
		
   		return "weixin/accout/singlePayReturn";
   	} 
    
	@RequestMapping(value = "/singlePay", method = RequestMethod.POST)
	public void singlePay(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException{
		//调用后台javaservice进行支付
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		
		String userId = request.getParameter("userId");
		String bankCardNumber = request.getParameter("bankCardNumber");
		String tradeAmount = request.getParameter("tradeAmount");
		String mobile = request.getParameter("mobile");
		String payFlag = request.getParameter("payFlag");
		
		reqJavaMap.put("cardNumber", bankCardNumber);
		reqJavaMap.put("userId", userId);
		reqJavaMap.put("tradeAmount", tradeAmount);
		reqJavaMap.put("payFlag", payFlag);
		reqJavaMap.put("mobile", mobile);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("单笔支付信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/thirdRoute/singlePay", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		
		String req_data = jsonObj.toString();
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
		
	}
	
	@RequestMapping(value = "/goPay", method = RequestMethod.GET)
	public String goPay(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		//调用获取数据 
		String mobile = request.getParameter("mobile");
		String userId = request.getParameter("userId");
		String payFlag = request.getParameter("payFlag");
		
		if(StringUtils.isBlank(userId)){
			request.setAttribute("rescode", Consts.CHECK_CODE);
			request.setAttribute("resmsg", "用户Id不能为空");
			return "weixin/accout/goPay";
		}
		if(StringUtils.isBlank(mobile)){
			request.setAttribute("rescode", Consts.CHECK_CODE);
			request.setAttribute("resmsg", "用户手机号不能为空");
			
			return "weixin/accout/goPay";
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("userId",userId);
		reqJavaMap.put("mobile",mobile);
		
		
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取进入充值页面需要的参数加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/thirdRoute/getGoPayAllInfo", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		
		String rescode = jsonObj.getString("rescode");
		
		if(!rescode.equals(Consts.SUCCESS_CODE)){
			String resmsg = jsonObj.getString("resmsg_cn");
			
			request.setAttribute("rescode", rescode);
			request.setAttribute("resmsg", resmsg);
			
			return "weixin/accout/goPay";
		}
		
		String idcardValidate = jsonObj.getString("idcardValidate");
		String isBindCard = jsonObj.getString("isBindCard");
		String acctBalance = jsonObj.getString("acctBalance");
		
		if(isBindCard.equals("Y")){
			String bankMinLimit = jsonObj.getString("bankMinLimit");
			String bankPicPath = jsonObj.getString("bankPicPath");
			String bankDayLimit = jsonObj.getString("bankDayLimit");
			String bankMonthLimit = jsonObj.getString("bankMonthLimit");
			String bankName = jsonObj.getString("bankName");
			String bankCardNumber = jsonObj.getString("bankCardNumber");
			String bankLimit = jsonObj.getString("bankLimit");
			
			request.setAttribute("bankMinLimit", bankMinLimit);
			request.setAttribute("bankPicPath", bankPicPath);
			request.setAttribute("bankDayLimit", bankDayLimit);
			request.setAttribute("bankMonthLimit", bankMonthLimit);
			request.setAttribute("bankName", bankName);
			request.setAttribute("bankCardNumber", bankCardNumber);
			request.setAttribute("bankLimit", bankLimit);
			
		}
		request.setAttribute("rescode", rescode);
		request.setAttribute("idcardValidate", idcardValidate);
		request.setAttribute("isBindCard", isBindCard);
		request.setAttribute("acctBalance", acctBalance);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		request.setAttribute("payFlag", payFlag);
		return "weixin/accout/goPay";
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
		String logInfo = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String ret_code = "0001";
		String ret_msg = "交易失败";
		String reqStr = LLPayUtil.readReqStr(request);
		logger.info("连连接收支付异步通知数据：" + reqStr);
		if (!LLPayUtil.isnull(reqStr)) {
			try {
				if (!LLPayUtil.checkSign(reqStr,
						LLPayHostingEnvUtil.getValue("yt_pub_key"),
						LLPayHostingEnvUtil.getValue("md5_key"))) {
					logger.info("支付异步通知验签失败!");
					ret_code = "0001";
				}
			} catch (Exception e) {
				logger.error(logInfo + "异常", e);
				logger.info("异步通知报文解析异常!" + e.getMessage());
				ret_code = "0001";
			}

			// 解析异步通知对象
			PayDataBean payDataBean = JSON.parseObject(reqStr,
					PayDataBean.class);
			String noOrder = payDataBean.getNo_order();

			// 根据商户唯一订单号查询用户银行卡信息
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("reqOrderId", noOrder);
			reqJavaMap.put("tradeAmount", payDataBean.getMoney_order());
			reqJavaMap.put("resOrderId", payDataBean.getOid_paybill());
			reqJavaMap.put("receiveMessage", reqStr.replace("=", "||"));
			reqJavaMap.put("payFlag", "WX");
			logInfo = "rechargeNotify" + noOrder + "-连连-";
			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.error(logInfo + "异常", e);
				ret_code = "0001";
				logger.info("更新用户银行卡信息加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			logger.info(logInfo + "调用javaservice 参数：" + reqJavaMap.toString());
			logger.info(logInfo + "调用javaservicer url：" + LoginRedirectUtil.interfacePath + "/thirdRoute/singlePayNotify");
			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/thirdRoute/singlePayNotify", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
				JSONObject resJson = JSONObject.fromObject(resultMsg);
				logger.info(logInfo + "调用javaservice 结果：" + resJson.toString());
				String rescode = resJson.getString("rescode");
				if (rescode.equals(Consts.SUCCESS_CODE)) {
					ret_code = "0000";
					ret_msg = "交易成功";
				} else {
					ret_code = rescode;
					ret_msg = resJson.getString("resmsg_cn");
				}
			} catch (Exception e) {
				logger.error(logInfo + "异常", e);
				ret_code = "0001";
				logger.info("更新用户银行卡信息解密失败:" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			ret_code = "0001";
		}
		
		resultMap.put("ret_code", ret_code);
		resultMap.put("ret_msg", ret_msg);
		String req_data = JSON.toJSONString(resultMap);
		logger.info(logInfo + "连连充值通知商户结果：" + req_data);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		printWriter.write(req_data);
		printWriter.flush();
		printWriter.close();

	}
	
	@RequestMapping(value = "/cjpayNotify", method = RequestMethod.POST)
	public void cjpayNotify(PrintWriter printWriter,
			HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws IOException{
		String ret_msg = "faild";
		
		String reqOrderId = request.getParameter("outer_trade_no"); 
		String tradeAmount = request.getParameter("trade_amount");  
		String receiveMessage = JSON.toJSONString(request.getParameterMap());
		String resOrderId =  request.getParameter("inner_trade_no"); 
		
		// 根据商户唯一订单号查询用户银行卡信息
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("reqOrderId", reqOrderId);
		reqJavaMap.put("tradeAmount", tradeAmount);
		reqJavaMap.put("resOrderId", resOrderId);
		reqJavaMap.put("receiveMessage", receiveMessage.replace("=", "||"));
		reqJavaMap.put("payFlag", "WX");
		
		String logInfo = "rechargeNotify" + reqOrderId + "-畅捷-";

		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error(logInfo + "异常", e);
			logger.info("更新用户银行卡信息加密失败:" + e.getMessage());
			ret_msg = "faild";
		}
		logger.info(logInfo + "调用javaservice 参数：" + reqJavaMap.toString());
		logger.info(logInfo + "调用javaservicer url：" + LoginRedirectUtil.interfacePath + "/thirdRoute/singlePayNotify");
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/thirdRoute/singlePayNotify", param);
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
			JSONObject resJson = JSONObject.fromObject(resultMsg);
			logger.info(logInfo + "调用javaservice 结果：" + resJson.toString());
			String rescode = resJson.getString("rescode");
			if (rescode.equals(Consts.SUCCESS_CODE)) {
				ret_msg = "success";
			}else if(rescode.equals("99998")){
				ret_msg = "success";
			}else {
				ret_msg = "faild";
			}
		} catch (Exception e) {
			logger.error(logInfo + "异常", e);
			logger.info("更新用户银行卡信息解密失败:" + e.getMessage());
			ret_msg = "faild";
		}
		
		logger.info(logInfo + "畅捷充值通知商户结果：" + ret_msg);
		response.getWriter().write(ret_msg);
	}
	
	//跳转到充值页面
	@RequestMapping(value = "/goTopUpNew")
	public String goTopUpNew(HttpServletRequest request,
			HttpServletResponse res, HttpSession session) {
		return "weixin/accout/topUpNew";
	}
	
	//跳转到订单详情页面
	@RequestMapping(value = "/goDealDetail")
	public String dealDetail(HttpServletRequest request,
			HttpServletResponse res, HttpSession session) {
		return "weixin/accout/dealDetail";
	}

	/**
	 * 查询银行卡信息
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/queryCardAllInfo")
	public void queryRouteBankList(HttpServletRequest request, PrintWriter out) {
		logger.info("======进入方法：wxpay/queryCardAllInfo====");
		String userId = request.getParameter("userId");
		String mobile = request.getParameter("mobile");
		String cardNumber = request.getParameter("cardNumber");

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("mobile", mobile);
		userMap.put("cardNumber", cardNumber);

		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取银行列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/thirdRoute/queryCardAllInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询银行卡信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 重定向
	 * @param request
	 * @param session
	 * @param response
	 */
	@RequestMapping(value = "/redirectPay", method = RequestMethod.GET)
	public String redirectPay(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		/**
		 * 查询用户已绑定银行卡信息（页面显示）
		 */
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String mobile = us.getMobile();
		String payFlag = request.getParameter("payFlag");
		
		if(StringUtils.isBlank(payFlag)){
			payFlag = "WX";
		}
		
		String payUrl = "";
		if(payFlag.equals("WX")){
			 payUrl = "/wxpay/goPay?mobile="+mobile+"&userId="+userId+"&payFlag=" + payFlag;
		}else{
			payUrl = "/trade/goTopUpcz?mobile="+mobile+"&userId="+userId+"&payFlag=" + payFlag;
		}
		
		return "redirect:"+payUrl;
		
	}

}
