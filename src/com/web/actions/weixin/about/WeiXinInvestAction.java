package com.web.actions.weixin.about;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/wxInvest")

public class WeiXinInvestAction extends BaseAction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3296946260493345914L;
	private static final Log logger = LogFactory.getLog(WeiXinInvestAction.class);
	
	//转出页面
	@RequestMapping(value="/getRolloutIndex",method=RequestMethod.GET)
	public String getRolloutIndex(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/rolloutIndex";
	}
	
	
	//投资详情页面
	@RequestMapping(value="/getInvestConfirmation",method=RequestMethod.GET)
	public String getInvestConfirmation(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		String mobile="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
			mobile= userInfoMap.get("mobile");
		}else{
			userId = String.valueOf(us.getId());
			mobile=String.valueOf(us.getMobile());
		}
		
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/investConfirmation";
	}
	
	//抵用券列表
	@RequestMapping(value="/getVoucherList",method=RequestMethod.GET)
	public String getVoucherList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/voucherList";
	}
	
	//投资标的列表
	@RequestMapping(value="/getAssertList",method=RequestMethod.GET)
	public String getAssertList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/assertList";
	}
	
	//投资标的列表
	@RequestMapping(value="/getInvestSucceed",method=RequestMethod.GET)
	public String getInvestSucceed(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/investSucceed";
	}
	
	//投资人记录页面
	@RequestMapping(value="/getInvestorsList",method=RequestMethod.GET)
	public String getInvestorsList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/investorsList";
	}
	
	//债权列表
	@RequestMapping(value="/getCreditorList",method=RequestMethod.GET)
	public String getCreditorList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/creditorList";
	}
	

	//转出记录
	@RequestMapping(value="/getRolloutList",method=RequestMethod.GET)
	public String getRolloutList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/rolloutList";
	}
	
	
	//转出成功页面
	@RequestMapping(value="/getRolloutSucceed",method=RequestMethod.GET)
	public String getRolloutSucceed(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/rolloutSucceed";
	}
	
	//投资标的列表
	@RequestMapping(value="/getInvestAssertList",method=RequestMethod.GET)
	public String getInvestAssertList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/investAssertList";
	}
	
	//优享计划首页
	@RequestMapping(value="/getPlanProductIndex",method=RequestMethod.GET)
	public String getPlanProductIndex(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/planProductIndex";
	}
	
	/**
	 * 跳转到 优享计划的投资人的列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/goEnjoyTodayUserInfoList",method=RequestMethod.GET)
	public String goEnjoyTodayUserInfoList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/enjoyTodayUserInfoList";
	}

	@RequestMapping(value="/goLine",method=RequestMethod.GET)
	public String goLine(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";	
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/invest/line";
	}

}
