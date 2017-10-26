package com.web.util;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SendSMSUtil {
	private static final Logger logger = Logger.getLogger(JsonUtil.class);

	/**
	 * 充值、提现、活期赎回、投资、回款等成功后，发送短信。
	 * 
	 * @param f
	 * @param userId
	 * @param methodName
	 * @param tsje
	 * @param cpType
	 */
	public static void sendSMS(String userId, String smsNo,
			String tsje, String cpType) {
		Map<String, Object> smsMap = new LinkedHashMap<String, Object>();
		smsMap.put("userId", userId==null?"":userId);
		smsMap.put("smsNo", smsNo);// 根据类型名，匹配短信模版。
		smsMap.put("tsje", tsje==null?"":tsje);//提示金额
		if (!StringUtils.isBlank(cpType)) {
			smsMap.put("cpType", cpType);//提示产品名称
		}
		String paramSms = CommonUtil.getParam(smsMap);
		try {
			paramSms = DES3Util.encode(paramSms);
		} catch (Exception e) {
			logger.info("推送提示短信加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String resultSms = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/sendSMS/getSMS", paramSms);
		try {
			resultSms = java.net.URLDecoder.decode(DES3Util.decode(resultSms),
					"UTF-8");
			logger.info("/sendSMS/getSMS方法返回结果:" + resultSms);
			// JSONObject resJson = JSONObject.fromObject(resultSms);
			// String rescode = resJson.getString("rescode");
			// if (rescode.equals(Consts.SUCCESS_CODE)) {
			// ret_code = "0000";
			// } else {
			// ret_code = rescode;
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// ret_code = "0001";
			logger.info("推送提示短信解密失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param f
	 * @param userId
	 * @param methodName
	 * @param tsje
	 * @param cpType
	 */
	public static void sendSMSKz(String userId, String smsNo,
			String parm1, String parm2,String orderId) throws Exception{
		Map<String, Object> smsMap = new LinkedHashMap<String, Object>();
		smsMap.put("userId", userId==null?"":userId);
		if (!StringUtils.isBlank(orderId)) {
			smsMap.put("orderIdPm", orderId);;//连连回调传过来订单ＩＤ
		}
		smsMap.put("smsNo", smsNo);// 根据类型名，匹配短信模版。
		smsMap.put("parm1", parm1==null?"":parm1);//提示金额
		if (!StringUtils.isBlank(parm2)) {
			smsMap.put("parm2", parm2);//提示产品名称
		}
		String paramSms = CommonUtil.getParam(smsMap);
		try {
			paramSms = DES3Util.encode(paramSms);
		} catch (Exception e) {
			logger.info("推送提示短信加密失败:" + e.getMessage()); 
			e.printStackTrace();
		}
		String resultSms = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/sendSMS/getSMSKz", paramSms);
		try {
			resultSms = java.net.URLDecoder.decode(DES3Util.decode(resultSms),
					"UTF-8");
			logger.info("/sendSMS/sendSMSKz方法返回结果:" + resultSms);
			// JSONObject resJson = JSONObject.fromObject(resultSms);
			// String rescode = resJson.getString("rescode");
			// if (rescode.equals(Consts.SUCCESS_CODE)) {
			// ret_code = "0000";
			// } else {
			// ret_code = rescode;
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// ret_code = "0001";
			logger.info("推送提示短信解密失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 推送个推消息
	 * @param paramMap
	 */
	public static void sendGetuiMSG(Map<String, Object> paramMap) {
		String paramSms = CommonUtil.getParam(paramMap);
		try {
			paramSms = DES3Util.encode(paramSms);
		} catch (Exception e) {
			logger.info("推送个推消息加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String resultSms = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/sendSMS/sendGetuiMSG", paramSms);
		try {
			resultSms = java.net.URLDecoder.decode(DES3Util.decode(resultSms),"UTF-8");
			logger.info("/sendSMS/sendGetuiMSG方法返回结果:" + resultSms);
		} catch (Exception e) {
			logger.info("推送个推消息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
