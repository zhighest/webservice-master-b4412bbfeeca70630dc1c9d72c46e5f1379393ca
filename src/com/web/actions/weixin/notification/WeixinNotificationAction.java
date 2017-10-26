/**
 * Copyright (c) 2012, lincomb Technology CO,LDT. All rights reserved.
 * Module: App推送
 * File: WeixinNotificationAction.java
 * Purpose: 处理App推送消息Action层
 * Date: 2016-6-6
 */
package com.web.actions.weixin.notification;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
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
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.StringUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;
/**
 * ClassName: WeixinNotificationAction 
 * @Description: 处理App推送消息Action类
 * @author ZuoJun
 * @date 2016-6-6 16:09:29
 */
@Controller
@RequestMapping("/wxpush")
@Scope("prototype")
public class WeixinNotificationAction extends BaseAction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(WeixinNotificationAction.class);
			
	/**
	 * @Description: 根据用户ID、消息类别查询消息或公告信息
	 * @param request
	 * @param response
	 * @param out
	 * @author ZuoJun
	 * @date 2016-6-6 16:10:39
	 */
	@RequestMapping("/queryMessage")
	public void queryMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		logger.info("------------------------------------------ /wxpush/queryMessage WeixinNotificationAction queryMessage() start ------------------------------------------------");
		
		String userId = "";
		
		UserSession us = UserCookieUtil.getUser(request);
		
		if(null == us || null == us.getId()){
			
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, response);
			
			userId = userInfoMap.get("userId");
			
		}else{
			
			userId = String.valueOf(us.getId());
			
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		//消息类别(0.消息，1.公告)(服务端设置默认值0)
		String type = request.getParameter("type");
		
		//当前页数
		String current = request.getParameter("current");
		
		if(StringUtil.isEmpty(current)){
			current = "1";
		}
		
		//页面大小
		String pageSize = request.getParameter("pageSize");
		
		if(StringUtil.isEmpty(pageSize)){
			pageSize = "10";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userId", userId);
		params.put("type", type);
		params.put("current", current);
		params.put("pageSize", pageSize);
		
		logger.info("------------------------------------------ /wxpush/queryMessage WeixinNotificationAction queryMessage() type=" + type 
				+ " , current=" + current + " , pageSize=" + pageSize + " , userId=" + userId + " ------------------------------------------------");
		
		String param = CommonUtil.getParam(params);
		
		try {
			
			param = DES3Util.encode(param);
			
		} catch (Exception e) {
			
			logger.info("----------------------------------------------------处理根据用户ID、消息类别查询消息或公告信息加密出现异常:" + e.getMessage() + "----------------------------------------------");
			
			e.printStackTrace();
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		// 调用service接口
		String resultMsg = "";
		
		try {
			
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/push/queryMessage", param);
			
		} catch (Exception e) {
			
			logger.info("---------------------------------------------------处理根据用户ID、消息类别查询消息或公告信息出现异常:" + e.getMessage() + "--------------------------------------------");	
			
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
			
			e.printStackTrace();
			
			return;
		}
		
		try {
			
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			
		} catch (Exception e) {
			
			logger.info("-------------------------------------------------------处理根据用户ID、消息类别查询消息或公告信息返回结果解密出现异常:" + e.getMessage() + "-------------------------------------------------------");
			
			e.printStackTrace();
		}
		
		JSONObject respJson = JSONObject.fromObject(resultMsg);
		
		if(respJson == null){
			
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG, Consts.ERROR_DESC);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			
			setResposeMap(rtnMap, out);
			
			return;
		}
		
		if(Consts.SUCCESS_CODE.equals(respJson.get(Consts.RES_CODE))){
			
			int totalNum = respJson.getInt(Consts.TOTAL_NUM);
			
			//公共分页
			PageUtils pageObject = PageUtil.execsPage(Integer.parseInt(current),
					totalNum, 5, Integer.parseInt(pageSize));
			
			respJson.put(Consts.PAGE_OBJECT, pageObject);
			
		}
		setResposeJson(respJson, out);	
		
		logger.info("------------------------------------------ /wxpush/queryMessage WeixinNotificationAction queryMessage() respJson=" + respJson.toString() + " ------------------------------------------------");
		
	}

	
	

	/**
	 * @Description: 查询媒体报道信息
	 * @param request
	 * @param response
	 * @param out
	 * @author ZuoJun
	 * @date 2016-6-6 16:11:39
	 */
	@RequestMapping("/queryMediaReport")
	public void queryMediaReport(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
			
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		//当前页数
		String current = request.getParameter("current");
		if(StringUtil.isEmpty(current)){
			current = "1";
		}
		
		//页面大小
		String pageSize = request.getParameter("pageSize");
		if(StringUtil.isEmpty(pageSize)){
			pageSize = "10";
		}
		
		//消息类别
		String sign=null;
		if(!StringUtil.isEmpty(request.getParameter("sign"))){
			sign=(String)request.getParameter("sign");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("current", current);
		params.put("pageSize", pageSize);
		params.put("sign", sign);
		
		logger.info("------------------------------------------ /wxpush/queryMediaReport WeixinNotificationAction queryMediaReport() current=" + current + " , pageSize=" + pageSize + " ------------------------------------------------");
		
		String param = CommonUtil.getParam(params);
		
		try {
			
			param = DES3Util.encode(param);
			
		} catch (Exception e) {
			
			logger.info("----------------------------------------------------处理查询媒体报道信息加密出现异常:" + e.getMessage() + "----------------------------------------------");
			
			e.printStackTrace();
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		// 调用service接口
		String resultMsg = "";
		
		try {
			
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/push/queryMediaReport", param);
			
		} catch (Exception e) {
			
			logger.info("---------------------------------------------------处理查询媒体报道信息出现异常:" + e.getMessage() + "--------------------------------------------");	
			
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
			
			e.printStackTrace();
			
			return;
		}
		
		try {
			
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			
		} catch (Exception e) {
			
			logger.info("-------------------------------------------------------处理查询媒体报道信息返回结果解密出现异常:" + e.getMessage() + "-------------------------------------------------------");
			
			e.printStackTrace();
		}
		
		JSONObject respJson = JSONObject.fromObject(resultMsg);
		
		if(respJson == null){
			
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG, Consts.ERROR_DESC);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			
			setResposeMap(rtnMap, out);
			
			return;
		}
		
		if(Consts.SUCCESS_CODE.equals(respJson.get(Consts.RES_CODE))){
			
			int totalNum = respJson.getInt(Consts.TOTAL_NUM);
			
			//公共分页
			PageUtils pageObject = PageUtil.execsPage(Integer.parseInt(current),
					totalNum, 5, Integer.parseInt(pageSize));
			
			respJson.put(Consts.PAGE_OBJECT, pageObject);
		}
		
		setResposeJson(respJson, out);
		
		logger.info("------------------------------------------ /wxpush/queryMediaReport WeixinNotificationAction queryMediaReport() respJson=" + respJson.toString() + " ------------------------------------------------");
	}
	
	
	/**
	 * @Description: 查询未读信息数量
	 * @param request
	 * @param response
	 * @param out
	 * @author wuhan
	 * @date 2016-6-6 19:41:39
	 */
	@RequestMapping("/unread")
	public void unread(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		String userId = "";
		UserSession us = UserCookieUtil.getUser(request);
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String param = CommonUtil.getParam(params);
		
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("----------------------------------------------------处理查询未读信息数量加密出现异常:" + e.getMessage() + "----------------------------------------------");
			e.printStackTrace();
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		// 调用service接口
		String resultMsg = "";
		try {
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/push/unread", param);
		} catch (Exception e) {
			logger.info("---------------------------------------------------处理查询媒体报道信息出现异常:" + e.getMessage() + "--------------------------------------------");	
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
			e.printStackTrace();
			return;
		}
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("-------------------------------------------------------处理查询未读信息数量返回结果解密出现异常:" + e.getMessage() + "-------------------------------------------------------");
			e.printStackTrace();
		}
		
		JSONObject rtnJson = JSONObject.fromObject(resultMsg);
		if(rtnJson == null){
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG, Consts.ERROR_DESC);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
			return;
		}
		
		String resCode = rtnJson.getString("rescode");
		String resMSG = rtnJson.getString(Consts.RES_MSG);
		String resMSGCN = rtnJson.getString(Consts.RES_MSG_CN);
		rtnMap.put(Consts.RES_CODE, resCode);
		rtnMap.put(Consts.RES_MSG, resMSG);
		rtnMap.put(Consts.RES_MSG_CN, resMSGCN);
		rtnMap.put("list", rtnJson.getJSONArray("list"));
		rtnMap.put("totalMsgNum", rtnJson.get("totalMsgNum"));
		setResposeMap(rtnMap, out);	
		
		logger.info("------------------------------------------ /wxpush/unread WeixinNotificationAction unread() " + Consts.RES_CODE + "=" + resCode 
				+ " , " + Consts.RES_MSG +"=" + resMSG + " , " + Consts.RES_MSG_CN +"=" + resMSGCN + " ------------------------------------------------");
	}
	
	
	/**
	 * @Description: 清除未读消息数
	 * @param request
	 * @param response
	 * @param out
	 * @author wuhan
	 * @date 2016-6-6 19:51:39
	 */
	@RequestMapping("/clearUnread")
	public void clearUnread(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		String userId = "";
		String message_type=request.getParameter("message_type");
		UserSession us = UserCookieUtil.getUser(request);
		String mobile=request.getParameter("mobile");
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		//如果userId为空，通过mobile来获取userId
		if(StringUtil.isEmpty(userId)){
			if(StringUtil.isNotEmpty(mobile)){
				JSONObject resultMap = new JSONObject();
				Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
				paramsMap.put("mobile",mobile);
				String param1 = CommonUtil.getParam(paramsMap);
				try {
					param1 = DES3Util.encode(param1);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("查询任务列表加密失败:" + e.getMessage());
				}
				//获取用户id
				String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUserIdByMobile", param1);
				try {
					checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("查询任务列表解密失败:" + e.getMessage());
				}
				
				JSONObject checkCaptchatObject1 = JSONObject.fromObject(checkCaptchatMsg1);
				if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject1.getString("rescode"))) {
					resultMap.put("resmsg_cn",checkCaptchatObject1.getString("resmsg_cn"));
					resultMap.put("resmsg", checkCaptchatObject1.getString("resmsg_cn"));
					resultMap.put("rescode",checkCaptchatObject1.getString("rescode"));
					setResposeMsg(resultMap.toString(), out);
					return;
				}
				userId=checkCaptchatObject1.get("userId")+"";
				
				logger.info("-----------------------------------------clearUnread获取userId："+userId);
			}
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("message_type", message_type);
		String param = CommonUtil.getParam(params);
		
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("----------------------------------------------------处理清除未读消息数加密出现异常:" + e.getMessage() + "----------------------------------------------");
			e.printStackTrace();
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		// 调用service接口
		String resultMsg = "";
		try {
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/push/clearUnread", param);
		} catch (Exception e) {
			logger.info("---------------------------------------------------处理清除未读消息数出现异常:" + e.getMessage() + "--------------------------------------------");	
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
//			e.printStackTrace();
			return;
		}
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("-------------------------------------------------------处理清除未读消息数返回结果解密出现异常:" + e.getMessage() + "-------------------------------------------------------");
			e.printStackTrace();
		}
		
		JSONObject rtnJson = JSONObject.fromObject(resultMsg);
		
		if(rtnJson == null){
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG, Consts.ERROR_DESC);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
			return;
		}
		
		String resCode = rtnJson.getString("rescode");
		String resMSG = rtnJson.getString(Consts.RES_MSG);
		String resMSGCN = rtnJson.getString(Consts.RES_MSG_CN);
		
		rtnMap.put(Consts.RES_CODE, resCode);
		rtnMap.put(Consts.RES_MSG, resMSG);
		rtnMap.put(Consts.RES_MSG_CN, resMSGCN);
		
		setResposeMap(rtnMap, out);	
		logger.info("------------------------------------------ /wxpush/clearUnread WeixinNotificationAction clearUnread() " + Consts.RES_CODE + "=" + resCode 
				+ " , " + Consts.RES_MSG +"=" + resMSG + " , " + Consts.RES_MSG_CN +"=" + resMSGCN + " ------------------------------------------------");
		
	}
	
	/**
	 * @Description: 查询媒体报道详细信息
	 * @param request
	 * @param response
	 * @param out
	 * @author wuhan
	 * @date 2016-6-28 19:41:39
	 */
	@RequestMapping("/queryMediaReportDetail")
	public void queryMediaReportDetail(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		
		String id =request.getParameter("id");
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		String param = CommonUtil.getParam(params);
		
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("----------------------------------------------------处理查询媒体报道详细信息加密出现异常:" + e.getMessage() + "----------------------------------------------");
			e.printStackTrace();
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		// 调用service接口
		String resultMsg = "";
		try {
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/push/queryMediaReportDetail", param);
		} catch (Exception e) {
			logger.info("---------------------------------------------------处理查询媒体报道详细信息出现异常:" + e.getMessage() + "--------------------------------------------");	
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
//			e.printStackTrace();
			return;
		}
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("-------------------------------------------------------处理查询媒体报道详细信息返回结果解密出现异常:" + e.getMessage() + "-------------------------------------------------------");
			e.printStackTrace();
		}
		
		JSONObject rtnJson = JSONObject.fromObject(resultMsg);
		if(rtnJson == null){
			rtnMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnMap.put(Consts.RES_MSG, Consts.ERROR_DESC);
			rtnMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
			setResposeMap(rtnMap, out);
			return;
		}
		
		String resCode = rtnJson.getString("rescode");
		String resMSG = rtnJson.getString(Consts.RES_MSG);
		String resMSGCN = rtnJson.getString(Consts.RES_MSG_CN);
		rtnMap.put(Consts.RES_CODE, resCode);
		rtnMap.put(Consts.RES_MSG, resMSG);
		rtnMap.put(Consts.RES_MSG_CN, resMSGCN);
		rtnMap.put("result", rtnJson.get("result"));
		setResposeMap(rtnMap, out);	
		
		logger.info("------------------------------------------ /wxpush/queryMediaReportDetail WeixinNotificationAction queryMediaReportDetail() " + Consts.RES_CODE + "=" + resCode 
				+ " , " + Consts.RES_MSG +"=" + resMSG + " , " + Consts.RES_MSG_CN +"=" + resMSGCN + " ------------------------------------------------");
	}
	
}
