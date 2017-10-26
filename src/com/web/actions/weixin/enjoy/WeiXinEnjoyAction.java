package com.web.actions.weixin.enjoy;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.Interceptor.AvoidDuplicateSubmission;
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
import com.web.util.UserCookieUtil;

@Controller
@RequestMapping("/wxenjoy")

public class WeiXinEnjoyAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeiXinEnjoyAction.class);

	/**
	 * 获取债券列表	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEnjoyCreditList")
	public void getEnjoyCreditList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxenjoy/getEnjoyCreditList====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		String orderId = request.getParameter("orderId");
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("userId", userId);
		userMap.put("orderId", orderId == null ? "" : orderId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取债券列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxenjoy/getEnjoyCreditList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取债券列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String listSize = jsonObject.getString("listSize");
		String totalSize = jsonObject.getString("totalNum");
		List<JSONObject> listDetail = jsonObject.getJSONArray("list");
		//公共分页
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPageNew(Integer.parseInt(current),Integer.parseInt(listSize),
					Integer.parseInt(totalSize), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	
	/**
	 * 查询优享计划投资详情
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getEnjoyBuyDetail")
	public void getEnjoyBuyDetail(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) {

		JSONObject json = new JSONObject();
		Map<String, Object> reqJavaMap = new HashMap<String, Object>(); 
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		// 产品ID
		String productId = request.getParameter("productId");
		
		reqJavaMap.put("productId", productId == null ? "107" : productId);
		reqJavaMap.put("userId", userId == null ? "" : userId);
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("投资详情信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxenjoy/getEnjoyBuyDetail", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("投资详情信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}
	
	
	/**
	 * @Description: 获取投资列表
	 * @param @param request
	 * @param @param response
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-6-7
	 */
	@RequestMapping(value = "/getInvestList")
	public void getInvestList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxenjoy/getInvestList====");
		
//		UserSession us = UserCookieUtil.getUser(request);
//		String userId="";
//		if(null == us || null == us.getId()){
//			//用户表示验证机制（通过微信标识OPENID）
//			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
//			userId = userInfoMap.get("userId");
//		} else {
//			userId = String.valueOf(us.getId());
//		}
		
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("version", "1.0.0");//微信默认
//		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取投资列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxenjoy/getInvestList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取投资列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String size = jsonObject.getString("listSize");
		List<JSONObject> listDetail = jsonObject.getJSONArray("list");
		//公共分页
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);
		
		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * @Description: 优享计划首页
	 * @param @param request
	 * @param @param res
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-6-15
	 */
	@RequestMapping(value = "/enjoyPlanProductIndex")
	public void enjoyPlanProductIndex(HttpServletRequest request, HttpServletResponse res,PrintWriter out) {

		Map<String, Object> reqJavaMap = new HashMap<String, Object>(); 
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String productId = request.getParameter("productId");
		reqJavaMap.put("productId", productId);
		reqJavaMap.put("userId", userId);
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error("优享计划首页参数加密失败:" , e);
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxenjoy/enjoyPlanProductIndex", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.error("优享计划首页信息解密失败:" , e);
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}
	
	/**
	 * 获取标的列表	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEnjoyLoanList")
	public void getEnjoyLoanList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxenjoy/getEnjoyLoanList====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取债券列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxenjoy/getEnjoyLoanList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取标的列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String listSize = jsonObject.getString("listSize");
		String totalSize = jsonObject.getString("totalNum");
		List<JSONObject> listDetail = jsonObject.getJSONArray("list");
		//公共分页
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPageNew(Integer.parseInt(current),Integer.parseInt(listSize),
					Integer.parseInt(totalSize), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 优享计划投资人列表信息
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEnjoyTodayUserInfoList",method = RequestMethod.POST)
	public void queryEnjoyTodayUserInfoList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxenjoy/queryEnjoyTodayUserInfoList====");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("优享计划投资人列表信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxenjoy/queryEnjoyTodayUserInfoList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			if (StringUtils.isNotBlank(resultMsg)) {
				JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
				if (Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
					String size = jsonObjRtn.getString("totalNum");
					List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
					System.out.println("#####################" + listDetail.size());
	
					PageUtils pageObject = new PageUtils();
					if (null != listDetail && listDetail.size() != 0) {
						int intPageSize = 0;
						if (null != pageSize && !"".equals(pageSize)){
							intPageSize = Integer.parseInt(pageSize);
						}
						pageObject = PageUtil.execsPage(Integer.parseInt(current),
								Integer.parseInt(size), 5, intPageSize);
					}
					resultMap.put("pageObject",pageObject);
					resultMap.put("list", listDetail);
					resultMap.put("rescode", jsonObjRtn.getString("rescode"));
					resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
					setResposeMap(resultMap, out);
					return;
				}else{
					resultMap.put("rescode", jsonObjRtn.getString("rescode"));
					resultMap.put("resmsg_cn",jsonObjRtn.getString("resmsg_cn"));
					setResposeMap(resultMap,out);
					return;
				}	
			} else {
				resultMap.put("rescode", Consts.ERROR_CODE);
				resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
				setResposeMap(resultMap, out);
				return;
			}
		} catch (Exception e) {
			logger.info("优享计划投资人列表信息解密失败:" + e.getMessage());
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
			setResposeMap(resultMap, out);
			return;
		}
		
	}
	/**
	 * 优享计划转出记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getEnjoyPlanMoneyFundFlow")
	public void getEnjoyPlanMoneyFundFlow(PrintWriter out,String current,HttpServletRequest request,HttpServletResponse res){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		String pageSize = request.getParameter("pageSize");
		String current1 = request.getParameter("current");

		Map<String,Object> map =new LinkedHashMap<String,Object>();		
		map.put("userId", userId);
		map.put("current", StringUtils.isNotBlank(current1)?current1:1);
		map.put("pageSize",StringUtils.isNotBlank(pageSize)?pageSize:5);
		
		String param = CommonUtil.getParam(map);
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:"+e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath+"/wxenjoy/getEnjoyPlanMoneyFundFlow", param);	   
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String size = jsonObject.getString("totalNum");
		List<JSONObject> listDetail = jsonObject.getJSONArray("list");		
		//公共分页
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), intPageSize, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);			
		setResposeMsg(jsonObject.toString(), out);
		
	}
	/***
	 * 优享计划-获取手续费抵用券
	 * fanping
	 */
	@RequestMapping(value = "/getCouponList", method = RequestMethod.GET)
	public void getCouponList(PrintWriter out,String current,HttpServletRequest request,HttpServletResponse res){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		String pageSize = request.getParameter("pageSize");
		String current1 = request.getParameter("current");
		String status = request.getParameter("status");
//		userId="928649136";
//		status="0";
		
		Map<String,Object> map =new LinkedHashMap<String,Object>();
		map.put("userId", userId);
		map.put("current", StringUtils.isNotBlank(current1)?current1:1);
		map.put("pageSize",StringUtils.isNotBlank(pageSize)?pageSize:5);
		map.put("status",status);	
		String param = CommonUtil.getParam(map);
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:"+e.getMessage());
			e.printStackTrace();
		}
		//调用javaservice
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath+"/wxenjoy/getCouponList", param);	   
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String size = jsonObject.getString("totalNum");
		List<JSONObject> listDetail = jsonObject.getJSONArray("list");
		//公共分页
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), intPageSize, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);		
		setResposeMsg(jsonObject.toString(), out);
	} 
	
	/**
	 * 查询转出页面记录
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/turnOutDetail")
	public void turnOutDetail(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) {

		JSONObject json = new JSONObject();
		Map<String, Object> reqJavaMap = new HashMap<String, Object>(); 
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		reqJavaMap.put("userId", userId == null ? "" : userId);
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询转出页面记录信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxenjoy/turnOutDetail", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("查询转出页面记录信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}
	
	/**
	 * 优享计划购买	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/buyEnjoyPlan")
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void buyEnjoyPlan(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：/wxenjoy/buyEnjoyPlan====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String buyAmount = request.getParameter("buyAmount");
		String finaceId = request.getParameter("finaceId");
		String cycleMatchType = request.getParameter("cycleMatchType");
		String invitationCd = request.getParameter("invitationCd");
		String passwordCash = request.getParameter("passwordCash");
		String voucherId  = request.getParameter("voucherId");
		//String rateIds = request.getParameter("rateIds");
		
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("buyAmount", buyAmount);
		userMap.put("finaceId", finaceId);
		userMap.put("userId", userId);
		userMap.put("cycleMatchType", cycleMatchType);
		userMap.put("invitationCd", invitationCd);
		userMap.put("passwordCash", passwordCash);
		userMap.put("voucherId", voucherId);
		/*if(StringUtils.isNotBlank(rateIds)){
			userMap.put("rateIds", rateIds);
		}*/
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("优享计划购买加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath +  "/wxenjoy/buyEnjoyPlan", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("优享计划购买解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 优享计划转出接口	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/turnOut")
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void turnOut(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：/wxenjoy/turnOut====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String turnOutAmount = request.getParameter("turnOutAmount");
		String passwordCash = request.getParameter("passwordCash");
		String payPwd = request.getParameter("payPwd");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("turnOutAmount", turnOutAmount);
		userMap.put("turnOutFee", request.getParameter("turnOutFee"));
		userMap.put("finaceId", "1");
		userMap.put("userId", userId);
		userMap.put("passwordCash", passwordCash);
		userMap.put("payPwd", payPwd);
		userMap.put("helpId", request.getParameter("helpId"));
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("优享计划转出接口加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/wxenjoy/turnOut", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("优享计划转出接口解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	/**
	 * 优享计划用户获取收益率查询
	 * @param out
	 * @param current
	 * @param request
	 * @param res
	 */
	@RequestMapping(value = "/historyAmount",method = RequestMethod.GET)
	public void historyAmount(PrintWriter out,String current,HttpServletRequest request,HttpServletResponse res){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}

		String pageSize = request.getParameter("pageSize");
		String current1 = request.getParameter("current");

		Map<String,Object> map =new LinkedHashMap<String,Object>();
		map.put("userId", userId);
		map.put("current", StringUtils.isNotBlank(current1)?current1:1);
		map.put("pageSize",StringUtils.isNotBlank(pageSize)?pageSize:10);			
	
		String param = CommonUtil.getParam(map);
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:"+e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath+"/wxenjoy/historyAmount", param);	   
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	/**
	 * 优享计划获取用户7天收益率查询
	 * @param out
	 * @param current
	 * @param request
	 * @param res
	 */
	@RequestMapping(value = "/getYield",method = RequestMethod.GET)
	public void getYield(PrintWriter out,String current,HttpServletRequest request,HttpServletResponse res){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}

		String dayNum = request.getParameter("dayNum");
		if(StringUtils.isBlank(dayNum)){
			dayNum="7";
		}

		Map<String,Object> map =new LinkedHashMap<String,Object>();
		map.put("userId", userId);
		map.put("dayNum", dayNum);

		String param = CommonUtil.getParam(map);
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:"+e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath+"/wxenjoy/getYield", param);	   
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);

	}
	
}
