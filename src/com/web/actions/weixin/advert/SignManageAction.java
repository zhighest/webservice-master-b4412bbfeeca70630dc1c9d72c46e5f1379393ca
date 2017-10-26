package com.web.actions.weixin.advert;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;

import net.sf.json.JSONObject;

/**
 * 
 * 签到管理Action
 * 
 * @author wuhan
 *
 */
@Controller
@RequestMapping("/signManage")

public class SignManageAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 7811037561595714791L;

	private static final Log logger = LogFactory.getLog(SignManageAction.class);

	/**
	 * 查询签到管理信息
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/querySign", method = RequestMethod.GET)
	public void getTaskList(HttpServletRequest request, PrintWriter out, HttpServletResponse res) {
		String logInfo = "querySign-webservice ";

		logger.info(logInfo + "开始");

		// 获取签到管理信息
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/signManage/querySign",
				null);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
		} catch (Exception e) {
			logger.error("查询签到管理信息解密失败", e);
			e.printStackTrace();
		}

		JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);

		logger.info(logInfo + "查询结果：" + checkCaptchatObject.toString());
		logger.info(logInfo + "结束");

		setResposeMsg(checkCaptchatObject.toString(), out);
	}

}
