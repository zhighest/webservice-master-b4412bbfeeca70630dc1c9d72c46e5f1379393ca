package com.web.actions.weixin.about;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.actions.weixin.common.BaseAction;
import com.web.util.CommonUtil;
import com.web.util.ConstantUtil;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;

@Controller
@RequestMapping("/base")
public class MetadataAction extends BaseAction implements Serializable {
	private static final Log logger = LogFactory.getLog(MetadataAction.class);

	/**
	 * 查询签到管理信息
	 * 
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getMetaInfo")
	public void getTaskList(HttpServletRequest request, PrintWriter out, HttpServletResponse res) {
		String logInfo = "获取元数据信息";
		logger.info(logInfo + "开始");
		String checkCaptchatMsg = null;
		try {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("name", request.getParameter("name"));
			String param = CommonUtil.getParam(map);
			param = DES3Util.encode(param);
			// 获取签到管理信息
			checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/base/getMetaInfo", param);

			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
		} catch (Exception e) {
			logger.error(logInfo + "解密失败", e);
			e.printStackTrace();
		}

		logger.info(logInfo + "查询结果：" + checkCaptchatMsg);
		logger.info(logInfo + "结束");

		setResposeMsg(checkCaptchatMsg, out);
	}

}
