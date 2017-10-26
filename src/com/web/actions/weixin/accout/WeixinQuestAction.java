package com.web.actions.weixin.accout;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxquest")
@Scope("prototype")
public class WeixinQuestAction extends BaseAction implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeixinQuestAction.class);
	
	/**
	 * 跳转到 每日任务页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/goDailyQuest", method = RequestMethod.GET)
	public String goMyInterest(HttpServletRequest request,HttpServletResponse res) throws Exception {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		String mobile="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
			mobile=userInfoMap.get("mobile");
		}else{
			userId = String.valueOf(us.getId());
			mobile=us.getMobile();
		}
		//分享验签机制
		request.setAttribute("mobile", mobile);
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/dailyQuest";
	}
}
