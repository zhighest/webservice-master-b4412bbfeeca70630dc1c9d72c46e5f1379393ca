package com.web.actions.weixin.shop;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/shopVochers")

public class WeixinShopVouchersAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory.getLog(WeixinShopVouchersAction.class);
	
	/**
	 * @Description: 跳转到兑换券列表
	 * @param @param request
	 * @param @param res
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author wangdanqing
	 * @date 2016-10-22
	 */
	@RequestMapping(value="/goShopVouchers",method=RequestMethod.GET)
	public String goShopVouchers(HttpServletRequest request, HttpServletResponse res){
		//分享验签机制
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/oneYuanBuy";
	}
	
	/**
	 * 查询我的兑换券列表
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/queryShopVouchers")
	public void queryShopVouchers(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
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
		// flag=0：未使用的兑换券；flag=1：已使用的兑换券；flag=2：过期的兑换券；flag=3：已使用的和过期的兑换券
		String flag = request.getParameter("flag");
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
		userMap.put("flag", flag);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询兑换券列表加密失败:" + e);
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/shopVochers/queryShopVouchers", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询兑换券列表解密失败:" + e);
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
			/*pageObject = PageUtil.execsPageNew(Integer.parseInt(current),Integer.parseInt(listSize),
					Integer.parseInt(totalSize), 5, intPageSize);*/
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(totalSize), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	

	/**
	 * 跳转电商一元购平台
	 * @param request
	 * @param res
	 * @param out
	 */
	@RequestMapping(value = "/goUseShopVouchers")
	public void goUseShopVouchers(HttpServletRequest request,HttpServletResponse res,PrintWriter out) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		String gourl = "";
		String userId="";
		if(null == us || null == us.getMobile()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			mobile  = userInfoMap.get("mobile");
			userId  = userInfoMap.get("userId");
		}else{
			mobile = us.getMobile();
			userId = String.valueOf(us.getId());
		}
		String voucherId=request.getParameter("voucherId");
		// 参数
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("mobile", mobile);
		userMap.put("userId", userId);
		userMap.put("voucherId", voucherId);
		String param = CommonUtil.getParam(userMap);
		String loginParm = "";
		try {
			loginParm = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error("跳转电商参数加密失败:" + e);
			e.printStackTrace();
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/shopVochers/goUseShopVouchers", loginParm);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("跳转电商解密失败:" + e);
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String rescode = jsonObject.getString("rescode");
		if("00000".equals(rescode)){
			gourl = jsonObject.getString("yygurl");
		}
		logger.info("兑换券跳转页面url:" + gourl);
		jsonObject.put("gourl", gourl);
		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 使用第三方优惠券
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/useThirdCoupon")
	public void useThirdCoupon(HttpServletRequest request,PrintWriter out){
		String queryString = request.getQueryString();
		logger.info("useThirdCoupon-queryString" + queryString);

		//放入手机号
		String useMobile = request.getParameter("useMobile");
		logger.info("useThirdCoupon-useMobile" + useMobile);

		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("useMobile",useMobile);
		String param = CommonUtil.getParam(paramMap);

		String resultMsg = HttpRequestParam.sendPost(
				LoginRedirectUtil.interfacePath + "/shopVochers/useThirdCoupon?"+queryString,param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("使用第三方优惠券解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 获取优惠券详情
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/queryVochersDetails")
	public void queryVochersDetails(HttpServletRequest request,PrintWriter out) {
		String queryString = request.getQueryString();
		logger.info("useThirdCoupon-queryString" + queryString);
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/shopVochers/queryVochersDetails?"+queryString, null);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取优惠券详情解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
}
