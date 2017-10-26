package com.web.actions.weixin.share;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@RequestMapping("/platformData")

public class PlatformDataAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(PlatformDataAction.class);
		
	/**
	 * 查询用户注册信息
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	
	@RequestMapping(value = "/getUserCount")
	public void getUserCount(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		String mobile = request.getParameter("mobile");
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		if (StringUtil.isNotEmpty(userId)) {
			reqJavaMap.put("userId", userId);
		}
		if (StringUtil.isNotEmpty(mobile)) {
			reqJavaMap.put("mobile", mobile);
		}
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户注册信息加密失败:", e);
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/platformData/getUserCount", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户注册信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 查询用户投资信息
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/getInvestData")
	public void getInvestData(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
//		userId = "28645287";
		String mobile = request.getParameter("mobile");
		if (StringUtil.isEmpty(userId) && StringUtil.isEmpty(mobile)) {
			JSONObject json = new JSONObject();
			json.put("rescode", Consts.ERROR_CODE);
			json.put("resmsg_cn", "查询不到用户数据！");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		if (StringUtil.isNotEmpty(userId)) {
			reqJavaMap.put("userId", userId);
		}
		if (StringUtil.isNotEmpty(mobile)) {
			reqJavaMap.put("mobile", mobile);
		}
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户投资信息加密失败:", e);
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/platformData/getInvestData", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户投资信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 查询用户收益信息
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/getIncomeData")
	public void getIncomeData(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		String shareChannel = request.getParameter("shareChannel");//分享页区分
		String mobile = request.getParameter("mobile");
//		userId = "28645287";
		if (StringUtil.isEmpty(userId) && StringUtil.isEmpty(mobile)) {
			JSONObject json = new JSONObject();
			json.put("rescode", Consts.ERROR_CODE);
			json.put("resmsg_cn", "对不起,请您登录后再查看！");
			setResposeMsg(json.toString(), out);
			return;
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		if (StringUtil.isNotEmpty(userId)) {
			reqJavaMap.put("userId", userId);
		}
		if (StringUtil.isNotEmpty(mobile)) {
			reqJavaMap.put("mobile", mobile);
		}
		if (StringUtil.isNotEmpty(shareChannel)) {
			reqJavaMap.put("shareChannel", shareChannel);
		}
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户收益信息加密失败:", e);
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/platformData/getIncomeData", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询用户收益信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 查询用户收益信息
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/getVoucherAndProfitStatisInfo")
	public void getVoucherAndProfitStatisInfo(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
//		userId = "28645287";
		String mobile = request.getParameter("mobile");
		if (StringUtil.isEmpty(userId) && StringUtil.isEmpty(mobile)) {
			JSONObject json = new JSONObject();
			json.put("rescode", Consts.ERROR_CODE);
			json.put("resmsg_cn", "查询不到用户数据！");
			setResposeMsg(json.toString(), out);
			return;
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		if (StringUtil.isNotEmpty(userId)) {
			reqJavaMap.put("userId", userId);
		}
		if (StringUtil.isNotEmpty(mobile)) {
			reqJavaMap.put("mobile", mobile);
		}
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户收益信息加密失败:", e);
			e.printStackTrace();
		}
		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
						+ "/platformData/getVoucherAndProfitStatisInfo", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("查询用户收益信息解密失败:" + e);
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
}
