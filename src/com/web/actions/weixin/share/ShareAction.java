package com.web.actions.weixin.share;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.StringUtil;
import com.web.util.UserCookieUtil;

@Controller
@RequestMapping("/wxshare")

public class ShareAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(ShareAction.class);

	/**
	 * @Description: 获取分享文案信息
	 * @param @param request
	 * @param @param response
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-8-10
	 */
	@RequestMapping(value = "/getShareSub")
	public void getShareSub(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxshare/getShareSub====");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		String from = request.getParameter("from");
		if(StringUtil.isEmpty(from)){
			logger.info("获取分享文案信息from参数为空！");
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
			setResposeMap(resultMap, out);
			return;
		}
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("from", from);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取分享文案信息加密失败:" + e);
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/share/getShareSub", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取分享文案信息解密失败:" + e);
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	
}
