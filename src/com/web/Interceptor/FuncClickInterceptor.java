package com.web.Interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;

public class FuncClickInterceptor extends HandlerInterceptorAdapter  {

	/**
	 * 功能点限制
	 * 
	 * @param req
	 * @param res
	 * @param handler
	 * @return boolean
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object handler) throws Exception {
		
		UserSession us = UserCookieUtil.getUser(req);
		
		Map<String, String> paramsMap = CommonUtil.decryptParaToMap(req);

		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		
		String funcId = paramsMap.get("funcId"); // 功能id
		String interfaceName=getInterfaceName(req); // 接口名称
		String channel = paramsMap.get("from"); // 渠道
		String phoneNum = paramsMap.get("phoneNum");
		String mobile = paramsMap.get("mobile");
		if (mobile == null && phoneNum != null)
			mobile = phoneNum;
		try {
			if (mobile == null) {
				if (null == us || null == us.getId()) {
					// 用户表示验证机制（通过微信标识OPENID）
					Map<String, String> userInfoMap = WeiXinTriggerAction
							.getUserInfoByWeixinUid(req, res);
					mobile = userInfoMap.get("mobile");
				} else {
					mobile = us.getMobile();
				}
			}
			
			if (StringUtils.isBlank(channel)) {
				channel = "LBWX";
			}

			if (StringUtils.isBlank(funcId)) {
				if (StringUtils.isBlank(interfaceName)) {
					return true;
				}
			}
			
			userMap.put("funcId", funcId);
			userMap.put("interfaceName", interfaceName);
			userMap.put("channel", channel);
			userMap.put("mobile", mobile);

			String param = CommonUtil.getParam(userMap);

			param = DES3Util.encode(param);

			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/click/getClickInfo",
					param);

			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
			String clickRescode = "";
			if (resultMsg != null && !"".equals(resultMsg)) {
				JSONObject jsonObject = JSONObject.fromObject(resultMsg);
				clickRescode = jsonObject.getString("clickRescode");
			}
			// 判断返回
			if ("00004".equals(clickRescode)) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获取功能对应接口名
	 * 
	 * @param req
	 * @return result
	 */
	public String getInterfaceName(HttpServletRequest req)
	{
		String result="";
		String url=req.getRequestURL().toString();
		String[] temp=url.split("/");
		int length=temp.length;
		if (length<2)
			return result;
		result="/"+temp[length-2]+"/"+temp[length-1];
		return result;
	}
	
}
