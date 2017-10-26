package com.web.actions.weixin.qrcode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.actions.weixin.common.BaseAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/qrcode")

public class QrCodeAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(QrCodeAction.class);

	@RequestMapping(value = "/deleteQrCodeForLogin", produces = "text/event-stream")
	@ResponseBody
	public String deleteQrCodeForLogin(HttpServletRequest request, HttpServletResponse res) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		String randomCode = request.getParameter("qrCode");

		String loggerStr = "deleteQrCodeForLogin " + randomCode;
		logger.info(loggerStr + " start！");
		reqMap.put("qrCode", randomCode);
		String param = CommonUtil.getParam(reqMap);
		logger.info("deleteQrCodeForLogin 请求参数：randomCode" + randomCode);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error("deleteQrCodeForLogin失败:", e);
			return "data: FAIL\n\n";
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/qrcode/deleteQrCodeForLogin",
				param);

		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			JSONObject resultObject = JSONObject.fromObject(resultMsg);
			if (Consts.SUCCESS_CODE.equals(resultObject.getString("rescode"))) {
				// 登录 cookie设置
				UserSession us = new UserSession();
				us.setId(Integer.valueOf(resultObject.getString("userId")));
				us.setUsername(resultObject.getString("userName"));
				us.setMobile(resultObject.getString("mobile"));
				UserCookieUtil.putUser(request, res, us);

				logger.info(loggerStr + "登录 cookie设置成功！");
				logger.info(loggerStr + " end！");
				return "data: SUCCESS\n\n";
			} else {
				logger.info(loggerStr + resultObject.getString("resmsg_cn"));
				logger.info(loggerStr + " end！");
				return "data: FAIL\n\n";
			}
		} catch (Exception e) {
			logger.error("deleteQrCodeForLogin失败:", e);
			logger.info(loggerStr + " end！");
			return "data: FAIL\n\n";
		}
	}
}
