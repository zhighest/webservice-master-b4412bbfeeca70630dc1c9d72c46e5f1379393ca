package com.web.util.tongdun;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.util.RSA;
import com.web.util.tongdun.CommonUtilZ;

public class TdRequestUtil {
	private static final Log logger = LogFactory.getLog(TdRequestUtil.class);
	/**
	 * 调用同盾
	 * @author wangenlai
	 */
	public static String sendTd(Map<String,String> paraMap) throws Exception {

		String requestParam = "keyRandom=KEY_RANDOM";
		String publicKeyParam="";
		try {
			publicKeyParam = RSA.getPublick_Key_Encrypt(paraMap);
			requestParam = requestParam.replace("KEY_RANDOM", URLEncoder.encode(publicKeyParam, "utf-8"));
		} catch (Exception e) {
			logger.debug("公钥加密串："+e);
		}
		String jsonData = CommonUtilZ.httpRequestMap(TongdunProperUtil.url, "POST",requestParam);
		
		return jsonData;
	}
	
}
