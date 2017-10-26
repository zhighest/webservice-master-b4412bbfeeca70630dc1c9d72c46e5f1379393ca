package com.web.actions.weixin.accout;

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

import net.sf.json.JSONArray;
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
import com.web.domain.RateRisesCoupons;
import com.web.domain.Scattered;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxcoupons")

public class WeixinCouponsAction extends BaseAction implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(WeixinCouponsAction.class);
	
	/**
	 * 查询用户抽奖表信息
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	
	@RequestMapping(value = "/showCouponsLuckDraw", method = RequestMethod.GET)
	public void showCouponsLuckDraw(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
        request.setAttribute("mobile", parmMap.get("mobile") == null ? "" : parmMap.get("mobile"));
        request.setAttribute("channel", parmMap.get("channel") == null ? "" : parmMap.get("channel"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		mobile = "15921471241";
		if (StringUtils.isBlank(mobile)) {
			mobile = parmMap.get("mobile");
		}
		if (StringUtils.isBlank(mobile)) {
            resultMap.put("rescode", Consts.ERROR_CODE);
            resultMap.put("resmsg_cn", "查询不到用户数据！");
        }else{
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("mobile", mobile);
			reqJavaMap.put("selectType", parmMap.get("selectType") == null ? "1" : parmMap.get("selectType"));//查询奖项类型（1-活期2-定期）

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询用户抽奖表信息加密失败:" ,e);
				e.printStackTrace();
			}

			String resultMsg = "";
			
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
					+ "/rateRises/showCouponsLuckDraw", param);
			
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("查询用户抽奖表信息解密失败:" ,e);
				e.printStackTrace();
			}
			
			
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			String rescode = jsonObjRtn.getString("rescode");
			resultMap.put("rescode", rescode);
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			if(rescode.equals("00000")){
				String size = jsonObjRtn.getString("listSize");
				List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
				resultMap.put("listSize", size);
				resultMap.put("list", listDetail);
				resultMap.put("restLuckDrawTimes", jsonObjRtn.getString("restLuckDrawTimes"));
				resultMap.put("activityDesc", jsonObjRtn.getString("activityDesc"));
			}
		}
		
		
//		setResposeMap(resultMap, out);
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	/**
	 * 用户抽奖
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/userCouponsLuckDraw", method = RequestMethod.GET)
	public void userCouponsLuckDraw(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
        request.setAttribute("mobile", parmMap.get("mobile") == null ? "" : parmMap.get("mobile"));
        request.setAttribute("channel", parmMap.get("channel") == null ? "" : parmMap.get("channel"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(mobile)) {
			mobile = parmMap.get("mobile");
		}
//		mobile = "15921471241";
		String selectType = request.getParameter("selectType");
		String times = request.getParameter("times");
		logger.info("=================mobile:"+mobile);
		if (StringUtils.isBlank(mobile)) {
            resultMap.put("rescode", Consts.ERROR_CODE);
            resultMap.put("resmsg_cn", "查询不到用户数据！");
        }else{
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("mobile", mobile);
			reqJavaMap.put("selectType", selectType);
			reqJavaMap.put("times", times);
			
			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("用户抽奖加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			String resultMsg = "";
			
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
					+ "/rateRises/userCouponsLuckDraw", param);
			
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
						"UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("用户抽奖解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			
			
			String rescode = jsonObjRtn.getString("rescode");
			resultMap.put("rescode", rescode);
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			if(rescode.equals("00000")){
				String size = jsonObjRtn.getString("listSize");
				List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
				resultMap.put("listSize", size);
				resultMap.put("list", listDetail);
				resultMap.put("restLuckDrawTimes", jsonObjRtn.getString("restLuckDrawTimes"));
			}else{
				JSONObject errorMessage = jsonObjRtn.getJSONObject("errorMessage");
				resultMap.put("errorMessage", errorMessage);
			}
		}
		setResposeMap(resultMap, out);
//		String req_data = JSON.toJSONString(resultMap);
//		req_data = new String(req_data.toString().getBytes("utf-8"),
//				"iso8859-1");
//		out.write(req_data);
//		out.flush();
//		out.close();
	}
	/**
	 * 查询我的加息券
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/myRateCoupons", method = RequestMethod.GET)
	public void myRateCoupons(HttpServletRequest request, HttpServletResponse res,
			HttpSession session,PrintWriter out) 
		throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>(); 
		
		reqJavaMap.put("userId", userId);
		//查询状态
		String status = request.getParameter("status");
		//产品
		String product = request.getParameter("product");
		//分页信息
		String current = request.getParameter("current"); // 当前页数
		String pageSize = request.getParameter("pageSize"); // 页大小
		//加排序和不加排序版本的区别标识：0-老版本，1-新版本
		String vsFlag  = request.getParameter("vsFlag");
		String sortType = request.getParameter("sortType");//排序方式
		String orderBy = request.getParameter("orderBy");//升序、降序
		
		if(StringUtils.isNotBlank(vsFlag)){
			reqJavaMap.put("vsFlag", vsFlag);
		}
		if(StringUtils.isNotBlank(sortType)){
			reqJavaMap.put("sortType", sortType);
		}
		if(StringUtils.isNotBlank(orderBy)){
			reqJavaMap.put("orderBy", orderBy);
		}
		if(StringUtils.isNotBlank(status)){
			reqJavaMap.put("status", status);
		}
		if(StringUtils.isNotBlank(product)){
			reqJavaMap.put("product", product);
		}
		if(StringUtils.isNotBlank(current)){
			reqJavaMap.put("current", current);
		}else{
			current = "1";
		}
		if(StringUtils.isNotBlank(pageSize)){
			reqJavaMap.put("pageSize", pageSize);
		}else{
			pageSize = "10";
		}
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户抽奖加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/rateRises/myRateCoupons", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("用户抽奖解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		
		if(rescode.equals("00000")){
			String listSize = jsonObjRtn.getString("listSize");
			JSONArray userCardArray = jsonObjRtn.getJSONArray("rateCouponsList");
			List<RateRisesCoupons> rateCouponsList = (List<RateRisesCoupons>) JSONArray
					.toCollection(userCardArray, RateRisesCoupons.class);
			resultMap.put("rateCouponsList", rateCouponsList);
			String totalNum = jsonObjRtn.getString("totalNum");
			resultMap.put("totalNum", totalNum);
			PageUtils pageObject = new PageUtils();
			if (null != rateCouponsList && rateCouponsList.size() != 0) {
				int intPageSize = 0;
				if (null != pageSize && !"".equals(pageSize)) {
					intPageSize = Integer.parseInt(pageSize);
				}
				pageObject = PageUtil.execsPage(Integer.parseInt(current),
						Integer.parseInt(totalNum), 5, intPageSize);
			}
			resultMap.put("pageObject", pageObject);
			
		}
		setResposeMap(resultMap, out);
	}
	
	/**
	 * 跳转到 我的加息券
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/goMyInterest", method = RequestMethod.GET)
	public String goMyInterest(HttpServletRequest request,HttpServletResponse res) throws Exception {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		request.setAttribute("userId", userId);
//		String errorMsg = request.getParameter("errorMsg");
		String errorMsg = String.valueOf(request.getSession().getAttribute("errorMsg"));
		request.getSession().removeAttribute("errorMsg");
		if(StringUtils.isNotBlank(errorMsg)){
			request.setAttribute("errorMsg", errorMsg);
		}
		
		String sloanId =  request.getParameter("sloanId");
		String loanId =  request.getParameter("loanId");
		
		String product = request.getParameter("product");
		
		request.setAttribute("product", product);
		request.setAttribute("loanId", loanId);
		request.setAttribute("sloanId", sloanId);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/myInterest";
	}
	
	/**
	 * 使用加息券
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/useRateRises", method = RequestMethod.GET)
	public void useRateRises(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>(); 
		
		reqJavaMap.put("userId", userId);
		//加息券ID列表
		String rateIds = request.getParameter("rateIds");
		String productId = request.getParameter("productId");
		
		reqJavaMap.put("rateIds", rateIds);
		reqJavaMap.put("productId", productId);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户使用加息券加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/rateRises/useRateRises", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("用户使用加息券解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		resultMap.put("resmsg_cn",jsonObjRtn.getString("resmsg_cn"));
		
		//setResposeMap(resultMap, out);
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	/**
	 * 查询符合加息券的产品
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/queryScattered", method = RequestMethod.GET)
	public void queryScattered(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>(); 
		
		reqJavaMap.put("userId", userId);
		//加息券ID列表
		String rateId = request.getParameter("rateId");
		String loanId = request.getParameter("loanId");
		
		if(StringUtils.isNotBlank(rateId)){
			reqJavaMap.put("rateId", rateId);
		}
		
		if(StringUtils.isNotBlank(loanId)){
			reqJavaMap.put("loanId", loanId);
		}
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询符合加息券的产品加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/rateRises/queryScattered", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询符合加息券的产品解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		
		if(rescode.equals("00000")){
			JSONObject scatteredJson = jsonObjRtn.getJSONObject("scattered");
			Scattered scattered =(Scattered) JSONObject.toBean(scatteredJson, Scattered.class);
			resultMap.put("scattered", scattered);
		}
		setResposeMap(resultMap, out);
	}
	
	
	/**
	 * 调查问卷页面
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/survey", method = RequestMethod.GET)
	public String survey(HttpServletRequest request, HttpServletResponse res,HttpSession session){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(userId==null){
			userId = request.getParameter("userId");
		}
		return "drawer/survey";
	}
	
	/**
	 *  抽奖
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/activitydraw", method = RequestMethod.GET)
	public String activitydraw(HttpServletRequest request, HttpServletResponse res,HttpSession session){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(userId==null){
			userId = request.getParameter("userId");
		}
		return "drawer/activitydraw";
	}
	
	/**
	 * 投票成功页面
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/voteSucceed", method = RequestMethod.GET)
	public String voteSucceed(HttpServletRequest request, HttpServletResponse res,HttpSession session){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(userId==null){
			userId = request.getParameter("userId");
		}
		return "drawer/voteSucceed";
	}
	
	/**
	 * 查看中奖者页面
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/winningView", method = RequestMethod.GET)
	public String winningView(HttpServletRequest request, HttpServletResponse res,HttpSession session){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(userId==null){
			userId = request.getParameter("userId");
		}
		return "drawer/winningView";
	}
	
	/**
	 * 用户微信签到方法
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/userWechatSign", method = RequestMethod.POST)
	public void userWechatSign(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		String param  = "";
		String resultMsg = "";
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		String signFlag = request.getParameter("signFlag");
		
		if(StringUtils.isBlank(userId)){
			String mobile = request.getParameter("mobile");
			reqJavaMap.put("phoneNum", mobile);
			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("签到获取加息券加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/wxuser/findWeixinUserByPhoneNum", param);
			
			try{
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
				logger.info("/wxuser/findWeixinUserByPhoneNum方法返回结果:"
						+ resultMsg);
				
			}catch(Exception e){
				logger.info("/wxuser/findWeixinUserByPhoneNum查询用户信息解密失败");
			}
			
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			
			if (user != null && !"null".equals(user.toString())) {
				userId = user.getString("id");
			}else{
				JSONObject resultJson = new JSONObject();
				resultJson.put("rescode", Consts.ERROR_CODE);
				resultJson.put("resmsg_cn", "用户不存在！");
				resultMsg = resultJson.toString();
				setResposeMsg(resultMsg, out);
				return;
			}
			if("LBIOS".equalsIgnoreCase(signFlag)||"LBANDROID".equalsIgnoreCase(signFlag)){
				reqJavaMap.put("type", 10);
			}
			
		} else {
			reqJavaMap.put("type", 9);
		}
		reqJavaMap.put("userId", userId);
		
		//签到获取加息券
		param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("签到获取加息券加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/rateRises/addRateRisesCoupons", param);
		
		try{
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
			logger.info("/rateRises/addRateRisesCoupons方法返回结果:"
					+ resultMsg);
			
		}catch(Exception e){
			logger.info("/rateRises/addRateRisesCoupons签到获取加息券解密失败");
		}
		
		JSONObject resJson = JSONObject.fromObject(resultMsg);
		String rescode = resJson.getString("rescode");
		
		setResposeMsg(resultMsg, out);
		
	}
	
	/**
	 * 查询用户当日签到信息
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/queryUserCurrentSign", method = RequestMethod.GET)
	public void queryUserCurrentSign(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		String param  = "";
		String resultMsg = "";
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
		
		if(StringUtils.isBlank(userId)){
			String mobile = request.getParameter("mobile");
			reqJavaMap.put("phoneNum", mobile);
			param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("签到获取加息券加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath
							+ "/wxuser/findWeixinUserByPhoneNum", param);
			
			try{
				resultMsg = java.net.URLDecoder.decode(
						DES3Util.decode(resultMsg), "UTF-8");
				logger.info("/wxuser/findWeixinUserByPhoneNum方法返回结果:"
						+ resultMsg);
				
			}catch(Exception e){
				logger.info("/wxuser/findWeixinUserByPhoneNum查询用户信息解密失败");
			}
			
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject user = jsonObject.getJSONObject("user");
			
			if (user != null && !"null".equals(user.toString())) {
				userId = user.getString("id");
			}else{
				JSONObject resultJson = new JSONObject();
				resultJson.put("rescode", Consts.ERROR_CODE);
				resultJson.put("resmsg_cn", "用户不存在！");
				resultMsg = resultJson.toString();
				setResposeMsg(resultMsg, out);
				return;
			}
			
		}
		
		reqJavaMap.put("userId", userId);
		
		//查询用户当日签到信息
		param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询用户当日签到信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/rateRises/getTodayUserSignInfo", param);
		
		try{
			resultMsg = java.net.URLDecoder.decode(
					DES3Util.decode(resultMsg), "UTF-8");
			logger.info("/rateRises/getTodayUserSignInfo方法返回结果:"
					+ resultMsg);
			
		}catch(Exception e){
			logger.info("/rateRises/getTodayUserSignInfo查询用户当日签到信息解密失败");
		}
		
		JSONObject resJson = JSONObject.fromObject(resultMsg);
		String rescode = resJson.getString("rescode");
		
		setResposeMsg(resultMsg, out);
	}
	
	//福利礼包
	@RequestMapping(value = "/giftBag")
	public String goGiftBag(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/newGiftBag";
	}
	
	//兑换券
	@RequestMapping(value = "/changeVoucher")
	public String goChangeVoucher(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/changeVoucher";
	}
	
	//已使用兑换券
	@RequestMapping(value = "/usedVoucher")
	public String goUsedVoucher(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/usedVoucher";
	}
	
	//增加兑换券
	@RequestMapping(value = "/useCashCoupon")
	public String goUseCashCoupon(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/useCashCoupon";
	}

	//查看兑换券
	@RequestMapping(value = "/cashCouponUsedDetails")
	public String goCashCouponUsedDetails(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/cashCouponUsedDetails";
	}
		
	//获取加速券
	@RequestMapping(value = "/jsVoucher")
	public String goJsVoucher(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/jsVoucher";
	}
	
	//加速券
	@RequestMapping(value = "/soonVoucher")
	public String goSoonVoucher(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/soonVoucher";
	}
	//获取台数券
	@RequestMapping(value = "/tsVoucher")
	public String goTsVoucher(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/tsVoucher";
	}

	//第三方
	@RequestMapping(value = "/thirdVoucher")
	public String goThirdVoucher(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/thirdVoucher";
	}

	//查看帮助
	@RequestMapping(value = "/showZQHHelp")
	public String goShowZQHHelp(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/showZQHHelp";
	}
}
