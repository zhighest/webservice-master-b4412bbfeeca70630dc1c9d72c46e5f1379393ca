package com.web.actions.weixin.advert;

import java.io.PrintWriter;
import java.io.Serializable;
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
import com.web.util.UserCookieUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/question")
public class QuestionnaireAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(QuestionnaireAction.class);

	/**
	 * 问卷提交
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/submit",method=RequestMethod.POST)
	public void submitQuestionnaire(HttpServletRequest request, PrintWriter out, HttpServletResponse response) {
		JSONObject resultMap = new JSONObject();
		String logInfo = "问卷提交WebService";
		logger.info(logInfo + "开始");

		// 获取签到管理信息
		try {
			UserSession us = UserCookieUtil.getUser(request);
			String userId = null; // 用户id
			String mobile = null; // 手机号码
			userId = request.getParameter("userId");
			mobile = request.getParameter("mobile");
			if(StringUtils.isBlank(userId)||StringUtils.isBlank(mobile)){
				logger.info(logInfo + "用户未登录");
				resultMap.put("resmsg_cn", "未登录，请先登录。");
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			String answers = request.getParameter("answers");
			String url = request.getParameter("url");
			logInfo = logInfo + "userId:" + userId + ",mobile:" + mobile;
			Map<String, Object> userMap = new LinkedHashMap<String, Object>();
			userMap.put("userId", userId);
			userMap.put("answers", answers);
			userMap.put("url", url);
			userMap.put("mobile", mobile);
			String param = CommonUtil.getParam(userMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info(logInfo + "调用Javaservice加密失败", e);
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}

			logger.info(logInfo + "调用javaservice：/question/submit  开始");

			String res = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/question/submit", param);
			res = java.net.URLDecoder.decode(DES3Util.decode(res), "UTF-8");
			logger.info(logInfo + "调用javaservice：/question/submit  结束返回checkCaptchatMsg" + res);
			resultMap = JSONObject.fromObject(res);
		} catch (Exception e) {
			logger.info(logInfo + "问卷提交调用javaService异常:", e);
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			return;
		}

		logger.info(logInfo + "查询结果：" + resultMap.toString());
		logger.info(logInfo + "结束");

		setResposeMsg(resultMap.toString(), out);

	}

}
