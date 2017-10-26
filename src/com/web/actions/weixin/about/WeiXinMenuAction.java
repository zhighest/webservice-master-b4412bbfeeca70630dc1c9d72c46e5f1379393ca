package com.web.actions.weixin.about;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxmenu")
public class WeiXinMenuAction {
	/**
	 * 跳转到自定义菜单页面
	 * @return
	 */
	@RequestMapping(value = "/goCustomizeMenus", method = RequestMethod.GET)
	public String goLoginDare(HttpServletRequest request,HttpServletResponse res) {
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		String mobile="";

		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
			mobile =userInfoMap.get("mobile");
			
		}else{
			userId = String.valueOf(us.getId());
			mobile = String.valueOf(us.getMobile());
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		return "weixin/about/customizeMenus";
	} 

}
