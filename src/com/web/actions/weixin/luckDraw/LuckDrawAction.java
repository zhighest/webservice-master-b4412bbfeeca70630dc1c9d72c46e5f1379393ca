package com.web.actions.weixin.luckDraw;

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

import com.alibaba.fastjson.JSON;
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
@RequestMapping("/wxluckdraw")
public class LuckDrawAction extends BaseAction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(LuckDrawAction.class);

	/**
	 * @Description: 特殊奖项
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-9-2
	 */
	@RequestMapping(value = "/showSpecLuckDraw", method = RequestMethod.GET)
	public void showSpecLuckDraw(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		logger.info("查询特殊奖项参数：" + request);
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
//		mobile = "15921471241";
//		String channel = parmMap.get("channel");
//		channel = "LBWX";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("showSpecLuckDraw--mobile:" + request);
		if (null== mobile||"".equals( mobile)||"null".equalsIgnoreCase(mobile)) {
			mobile = parmMap.get("mobile");
		}
		
//		if (StringUtils.isBlank(mobile)) {
//			logger.info("查询不到用户数据！  mobile is null");
//			resultMap.put("rescode", Consts.ERROR_CODE);
//			resultMap.put("resmsg_cn", "查询不到用户数据！");
//		} else {
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("mobile", mobile);
//			reqJavaMap.put("channel", channel);

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("加密失败:" + e);
				e.printStackTrace();
			}
			String resultMsg = "";
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/luckdraw/showSpecLuckDraw", param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.error("解密失败:" + e);
				e.printStackTrace();
			}
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			logger.info("接口返回信息："+jsonObjRtn);
			String rescode = jsonObjRtn.getString("rescode");
			resultMap.put("rescode", rescode);
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			if (rescode.equals("00000")) {
				String size = jsonObjRtn.getString("listSize");
				List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
				resultMap.put("listSize", size);
				resultMap.put("list", listDetail);
				resultMap.put("restLuckDrawTimes",
						jsonObjRtn.getString("restLuckDrawTimes"));
				resultMap.put("activityDesc",
						jsonObjRtn.getString("activityDesc"));
			}
//		}
		String req_data = JSON.toJSONString(resultMap);
		try {
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("转码失败:" + e);
			e.printStackTrace();
		}
		out.write(req_data);
		out.flush();
		out.close();
	}

	/**
	 * @Description: 用户特殊抽奖
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-9-2
	 */
	@RequestMapping(value = "/userSpecLuckDraw", method = RequestMethod.GET)
	public void userLuckDraw(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		logger.info("用户特殊抽奖参数：" + request);
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		logger.info("userSpecLuckDraw：parmMap--mobile:" + parmMap.get("mobile"));
		if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
			mobile=parmMap.get("mobile");
			if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
				resultMap.put("rescode", Consts.ERROR_CODE);
				resultMap.put("resmsg_cn", "对不起,请您登录后再参加抽奖！");
				String req_data = JSON.toJSONString(resultMap);
				try {
					req_data = new String(req_data.toString().getBytes("utf-8"),
							"iso8859-1");
				} catch (UnsupportedEncodingException e) {
					logger.error("转码失败："+e);
					e.printStackTrace();
				}
				out.write(req_data);
				out.flush();
				out.close();
				return;
			}
		}
		if (StringUtils.isBlank(mobile)) {
			mobile = parmMap.get("mobile");
		}
//		mobile = "15921471241";
//		String channel = parmMap.get("channel");
//		channel = "LBWX";
		if (StringUtils.isBlank(mobile)) {
			logger.info("查询不到用户数据！  mobile is null");
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "查询不到用户数据！");
		} else {
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("mobile", mobile);
//			reqJavaMap.put("channel", channel);
			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("加密失败:" + e);
				e.printStackTrace();
			}
			String resultMsg = "";
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/luckdraw/userSpecLuckDraw",
					param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.error("解密失败:" + e.getMessage());
				e.printStackTrace();
			}

			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			logger.info("接口返回信息："+jsonObjRtn);
			String rescode = jsonObjRtn.getString("rescode");
			resultMap.put("rescode", rescode);
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			if (rescode.equals("00000")) {
				resultMap.put("restLuckDrawTimes",
						jsonObjRtn.getString("restLuckDrawTimes"));
				resultMap.put("id", jsonObjRtn.getString("id"));
				resultMap.put("awardName", jsonObjRtn.getString("awardName"));
				resultMap.put("awardType", jsonObjRtn.getString("awardType"));
				resultMap.put("tipsMessage", jsonObjRtn.getString("tipsMessage"));
			} else {
				JSONObject errorMessage = jsonObjRtn
						.getJSONObject("errorMessage");
				resultMap.put("errorMessage", errorMessage);
			}
		}

		String req_data = JSON.toJSONString(resultMap);
		try {
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("转码失败："+e);
			e.printStackTrace();
		}
		out.write(req_data);
		out.flush();
		out.close();
	}
	/**
	 * @Description: 显示双旦奖项
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-12-8
	 */
	@RequestMapping(value = "/showSDLuckDraw", method = RequestMethod.GET)
	public void showSDLuckDraw(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		logger.info("查询特殊奖项参数：" + request);
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info("showSDLuckDraw--mobile:" + request);
		if (null== mobile||"".equals( mobile)||"null".equalsIgnoreCase(mobile)) {
			mobile = parmMap.get("mobile");
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("mobile", mobile);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:" ,e);
			e.printStackTrace();
		}
		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
				+ "/luckdraw/showSDLuckDraw", param);
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error("解密失败:" ,e);
			e.printStackTrace();
		}
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		logger.info("接口返回信息："+jsonObjRtn);
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
		if (rescode.equals("00000")) {
			String size = jsonObjRtn.getString("listSize");
			List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
			resultMap.put("listSize", size);
			resultMap.put("list", listDetail);
			resultMap.put("restLuckDrawTimes",
					jsonObjRtn.getString("restLuckDrawTimes"));
			resultMap.put("useAmount",
					jsonObjRtn.getString("useAmount"));
			resultMap.put("activityDesc",
					jsonObjRtn.getString("activityDesc"));
		}
		String req_data = JSON.toJSONString(resultMap);
		try {
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("转码失败:" ,e);
			e.printStackTrace();
		}
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	/**
	 * @Description: 双旦抽奖
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-12-8
	 */
	@RequestMapping(value = "/userSDLuckDraw", method = RequestMethod.GET)
	public void userSDLuckDraw(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		logger.info("用户双旦抽奖参数：" + request);
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		logger.info("userSDLuckDraw：parmMap--mobile:" + parmMap.get("mobile"));
		if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
			mobile=parmMap.get("mobile");
			if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
				resultMap.put("rescode", Consts.ERROR_CODE);
				resultMap.put("resmsg_cn", "对不起,请您登录后再参加抽奖！");
				String req_data = JSON.toJSONString(resultMap);
				try {
					req_data = new String(req_data.toString().getBytes("utf-8"),
							"iso8859-1");
				} catch (UnsupportedEncodingException e) {
					logger.error("转码失败："+e);
					e.printStackTrace();
				}
				out.write(req_data);
				out.flush();
				out.close();
				return;
			}
		}
		if (StringUtils.isBlank(mobile)) {
			mobile = parmMap.get("mobile");
		}
		if (StringUtils.isBlank(mobile)) {
			logger.info("查询不到用户数据！  mobile is null");
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "查询不到用户数据！");
		} else {
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("mobile", mobile);
			reqJavaMap.put("drawFlag", parmMap.get("drawFlag"));
			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("加密失败:" + e);
				e.printStackTrace();
			}
			String resultMsg = "";
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/luckdraw/userSDLuckDraw",
					param);
			try {
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.error("解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			logger.info("接口返回信息："+jsonObjRtn);
			String rescode = jsonObjRtn.getString("rescode");
			resultMap.put("rescode", rescode);
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			if (rescode.equals("00000")) {
				resultMap.put("restLuckDrawTimes",
						jsonObjRtn.getString("restLuckDrawTimes"));
				resultMap.put("id", jsonObjRtn.getString("id"));
				resultMap.put("awardName", jsonObjRtn.getString("awardName"));
				resultMap.put("awardType", jsonObjRtn.getString("awardType"));
				resultMap.put("tipsMessage", jsonObjRtn.getString("tipsMessage"));
				resultMap.put("showImg", jsonObjRtn.getString("showImg"));
			} else {
				JSONObject errorMessage = jsonObjRtn
						.getJSONObject("errorMessage");
				resultMap.put("errorMessage", errorMessage);
			}
		}
		
		String req_data = JSON.toJSONString(resultMap);
		try {
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error("转码失败："+e);
			e.printStackTrace();
		}
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	/**
	 * @Description: 获取中奖记录
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-12-12
	 */
	@RequestMapping(value = "/getSDLuckDrawResult", method = RequestMethod.GET)
	public void getSDLuckDrawResult(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		logger.info("查询获奖记录参数：" + request);
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		logger.info("getSDLuckDrawResult--mobile:" + request);
		if (null== mobile||"".equals( mobile)||"null".equalsIgnoreCase(mobile)) {
			mobile = parmMap.get("mobile");
		}
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("current", current);
		reqJavaMap.put("pageSize", pageSize);
		reqJavaMap.put("mobile", mobile);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:" ,e);
			e.printStackTrace();
		}
		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
				+ "/luckdraw/getSDLuckDrawResult", param);
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error("解密失败:" ,e);
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
//		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
//		logger.info("接口返回信息："+jsonObjRtn);
//		String rescode = jsonObjRtn.getString("rescode");
//		resultMap.put("rescode", rescode);
//		resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
//		if (rescode.equals("00000")) {
//			String size = jsonObjRtn.getString("listSize");
//			List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
//			resultMap.put("listSize", size);
//			resultMap.put("list", listDetail);
//			resultMap.put("restLuckDrawTimes",
//					jsonObjRtn.getString("restLuckDrawTimes"));
//			resultMap.put("activityDesc",
//					jsonObjRtn.getString("activityDesc"));
//		}
//		String req_data = JSON.toJSONString(resultMap);
//		try {
//			req_data = new String(req_data.toString().getBytes("utf-8"),
//					"iso8859-1");
//		} catch (UnsupportedEncodingException e) {
//			logger.error("转码失败:" ,e);
//			e.printStackTrace();
//		}
//		out.write(req_data);
//		out.flush();
//		out.close();
	}
	
	/**
	 * @Description: 福利日积分抽奖奖项列表
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author @author zhouyajie
	 * @date 2017-4-10
	 */
	@RequestMapping(value = "/pointLuckDrawAwardList", method = RequestMethod.GET)
	public void pointLuckDrawAwardList(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (null== mobile||"".equals( mobile)||"null".equalsIgnoreCase(mobile)) {
			mobile = parmMap.get("mobile");
			if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
				resultMap.put("rescode", Consts.ERROR_CODE);
				resultMap.put("resmsg_cn", "对不起,请您登录后再参加抽奖！");
				String req_data = JSON.toJSONString(resultMap);
				try {
					req_data = new String(req_data.toString().getBytes("utf-8"),
							"iso8859-1");
				} catch (UnsupportedEncodingException e) {
					logger.error("转码失败："+e);
					e.printStackTrace();
				}
				out.write(req_data);
				out.flush();
				out.close();
				return;
			}
		}
		String logInfo = "福利日积分抽奖奖项列表pointLuckDrawAwardList-"+mobile;
		logger.info(logInfo);
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("mobile", mobile);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info(logInfo+"加密失败:" + e);
			e.printStackTrace();
		}
		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
				+ "/luckdraw/pointLuckDrawAwardList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error(logInfo+"解密失败:" + e);
			e.printStackTrace();
		}
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		logger.info(logInfo+"javaservice接口返回信息："+jsonObjRtn);
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
		if (rescode.equals("00000")) {
			String size = jsonObjRtn.getString("listSize");
			List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
			resultMap.put("listSize", size);
			resultMap.put("list", listDetail);
			resultMap.put("usablePoint",jsonObjRtn.getString("usablePoint"));
			resultMap.put("pointLuckDrawDay",jsonObjRtn.getString("pointLuckDrawDay"));
			resultMap.put("needPoint",jsonObjRtn.getString("needPoint"));
			
		}else {
			JSONObject errorMessage = jsonObjRtn
					.getJSONObject("errorMessage");
			resultMap.put("errorMessage", errorMessage);
		}
		String req_data = JSON.toJSONString(resultMap);
		try {
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(logInfo+"转码失败:" + e);
			e.printStackTrace();
		}
		out.write(req_data);
		out.flush();
		out.close();
	}

	/**
	 * @Description: 福利日积分抽奖
	 * @param @param request
	 * @param @param res
	 * @param @param session
	 * @param @param out   
	 * @return void  
	 * @throws
	 * @author zhouyajie
	 * @date 2017-4-10
	 */
	@RequestMapping(value = "/pointLuckDraw", method = RequestMethod.GET)
	public void pointLuckDraw(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		
		if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
			mobile=parmMap.get("mobile");
			if (null== mobile||"".equals(mobile.trim())||"null".equalsIgnoreCase(mobile.trim())) {
				resultMap.put("rescode", Consts.ERROR_CODE);
				resultMap.put("resmsg_cn", "对不起,请您登录后再参加抽奖！");
				String req_data = JSON.toJSONString(resultMap);
				try {
					req_data = new String(req_data.toString().getBytes("utf-8"),
							"iso8859-1");
				} catch (UnsupportedEncodingException e) {
					logger.error("转码失败："+e);
					e.printStackTrace();
				}
				out.write(req_data);
				out.flush();
				out.close();
				return;
			}
		}
		
		String logInfo = "福利日积分抽奖pointLuckDraw-"+mobile;
		logger.info(logInfo);

		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("mobile", mobile);
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info(logInfo+"加密失败:" + e);
			e.printStackTrace();
		}
		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/luckdraw/pointLuckDraw",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error(logInfo+"解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		logger.info(logInfo+"javaservice接口返回信息："+jsonObjRtn);
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
		if (rescode.equals("00000")) {
			resultMap.put("id", jsonObjRtn.getString("id"));
			resultMap.put("awardName", jsonObjRtn.getString("awardName"));
			resultMap.put("awardType", jsonObjRtn.getString("awardType"));
			resultMap.put("awardImg", jsonObjRtn.getString("awardImg"));
			resultMap.put("usablePoint", jsonObjRtn.getString("usablePoint"));
			
			
		} else {
			JSONObject errorMessage = jsonObjRtn
					.getJSONObject("errorMessage");
			resultMap.put("errorMessage", errorMessage);
		}
		String req_data = JSON.toJSONString(resultMap);
		try {
			req_data = new String(req_data.toString().getBytes("utf-8"),
					"iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(logInfo+"转码失败："+e);
			e.printStackTrace();
		}
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/pointLuckDrawRecord", method = RequestMethod.GET)
	public void pointLuckDrawRecord(HttpServletRequest request,
			HttpServletResponse res, HttpSession session, PrintWriter out) {
		String logoInfo = "福利日积分抽奖获奖列表pointLuckDrawRecord-";
		logger.info(logoInfo + "开始");
		UserSession us = UserCookieUtil.getUser(request);
		String mobile = "";
		if (null == us || null == us.getMobile()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction
					.getUserInfoByWeixinUidByLogin(request, res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = us.getMobile();
		}
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		logger.info(logoInfo + request);
		if (null== mobile||"".equals( mobile)||"null".equalsIgnoreCase(mobile)) {
			mobile = parmMap.get("mobile");
		}
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		reqJavaMap.put("current", current);
		reqJavaMap.put("pageSize", pageSize);
		reqJavaMap.put("mobile", mobile);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info(logoInfo +"加密失败:" ,e);
			e.printStackTrace();
		}
		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
				+ "/luckdraw/pointLuckDrawRecord", param);
		try {
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.error(logoInfo +"解密失败:" ,e);
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String size = jsonObject.getString("listSize");
		List<JSONObject> listDetail = jsonObject.getJSONArray("list");
		String totalNum = jsonObject.getString("totalNum");
		//公共分页
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPageNew(Integer.parseInt(current),Integer.parseInt(size),
					Integer.parseInt(totalNum), 5, intPageSize);
		}
		

		jsonObject.put("pageObject", pageObject);
		
		setResposeMsg(jsonObject.toString(), out);
	}
	
}
