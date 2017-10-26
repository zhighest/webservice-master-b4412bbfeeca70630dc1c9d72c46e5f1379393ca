package com.web.actions.weixin.goods;

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
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;

@Controller
@RequestMapping("/channel")
public class ChannelAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(ChannelAction.class);

	/**
	 * 
	 * @Description: 获取渠道图片
	 * @param request
	 * @param res 
	 * @author guodong
	 * @date 2016-8-30
	 */
	@RequestMapping(value="/channelDetailImg")
	public void channelDetailImg(HttpServletRequest request, HttpServletResponse res,PrintWriter out){
		logger.info("======进入方法：channel/channelDetailImg====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String channelId = request.getParameter("channelId");
		String channelEnName = request.getParameter("channelEnName");
//		channelId="413";
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("channelId", channelId == null ? "" : channelId);
		userMap.put("channelEnName", channelEnName == null ? "" : channelEnName);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error("获取渠道图片失败:" + e);
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePathForShop + "/channelshop/channelDetailImg", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error("获取渠道图片解密失败:" + e);
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String rescode = jsonObject.getString("rescode");
		if(!"00000".equals(rescode)){
			jsonObject.put("wxPicLink", "");
			jsonObject.put("webPicLink", "");
			jsonObject.put("channelDesc", "");
			jsonObject.put("channelPicTextDesc", "");
		}
		setResposeMsg(jsonObject.toString(), out);
	}
	
}
