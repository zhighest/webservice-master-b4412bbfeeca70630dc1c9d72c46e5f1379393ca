package com.web.actions.weixin.about;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.City;
import com.web.domain.Province;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.JsonUtil;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxabout")

public class WeiXinAboutAction extends BaseAction implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(WeiXinAboutAction.class);
	
	/**
	 * 注册协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "/regAgreement", method = RequestMethod.GET)
	public String dealPWControls(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/regAgreement";
	} 
	
	/**
	 * 债权转让协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bondTranAgreement", method = RequestMethod.GET)
	public String bondTranAgreement(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/bondTranAgreement";
	} 

	/**
	 * 债权转让合同
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goCreditoTranAgreement", method = RequestMethod.GET)
	public String creditoTranAgreement(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/creditoTranAgreement";
	} 

	/**
	 * 跳转百度统计页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/baiduStatistics", method = RequestMethod.GET)
	public String baiduStatistics(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/baiduStatistics";
	} 
	
	/**
	 * 微信主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goIndex", method = RequestMethod.GET)
	public String goIndex(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("pageTag", "index");
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		String userId="";
		if(null == us || null == us.getMobile()){
			//(不跳转到登录)根据微信唯一标识获取手机号Mobile，userId，nickName
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobile = userInfoMap.get("mobile");
			userId = userInfoMap.get("userId");
		} else {
			mobile = String.valueOf(us.getMobile());
			userId = String.valueOf(us.getId());
		}
		request.setAttribute("mobile", mobile);
		request.setAttribute("userId", userId);
		// 公众号链接过来的传值
		request.setAttribute("channel", request.getParameter("channel"));
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/index";
	} 
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goGuaranteeLetter", method = RequestMethod.GET)
	public String goGuaranteeLetter(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/guaranteeLetter";
	} 


	/**
	 * 查询所有省份信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllProvince", method = RequestMethod.GET)
	public void getAllProvince(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		logger.info(LogUtil.getStart("getAllProvince", "方法开始执行",
				"WeixinAboutAction", getProjetUrl(request)));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询所有省份信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/options/getAllProvince", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 查询所有省份信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		JSONArray provinceArray = jsonObj.getJSONArray("list");
		List<Province> list = (List<Province>) JSONArray
				.toCollection(provinceArray, Province.class);
		String listSize = jsonObj.getString("listSize");
		JSONObject reqJson = new JSONObject();
		reqJson.put("provinceList", list);
		reqJson.put("provinceListSize", listSize);
		reqJson.put("rescode", jsonObj.getString("rescode"));
		reqJson.put("resmsg_cn", jsonObj.getString("resmsg_cn"));
		setResposeMsg(reqJson.toString(), out);
		
	}
	
	
	/**
	 * 根据省份查询城市信息
	 * @param request
	 * @param response
	 * @param out
	 */
	@RequestMapping(value = "/getCityListByProvinceId", method = RequestMethod.GET)
	public void getCityListByProvinceId(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		logger.info(LogUtil.getStart("getCityListByProvinceId", "方法开始执行",
				"WeixinAboutAction", getProjetUrl(request)));
		
		String provinceId = request.getParameter("provinceId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("provinceId", provinceId);
		
		
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("根据省份查询城市信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/options/getCityListByProvinceId", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 根据省份查询城市信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		JSONArray cityArray = jsonObj.getJSONArray("list");
		List<City> list = (List<City>) JSONArray
				.toCollection(cityArray, City.class);
		String listSize = jsonObj.getString("listSize");
		JSONObject reqJson = new JSONObject();
		reqJson.put("cityList", list);
		reqJson.put("cityListSize", listSize);
		reqJson.put("rescode", jsonObj.getString("rescode"));
		reqJson.put("resmsg_cn", jsonObj.getString("resmsg_cn"));
		setResposeMsg(reqJson.toString(), out);
		
	}
	
	
	/**
	 * 获取全部省份城市信息
	 * @param request
	 * @param response
	 * @param out
	 */
	@RequestMapping(value = "/getProvinceAndCity", method = RequestMethod.GET)
	public void getProvinceAndCity(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		logger.info(LogUtil.getStart("getProvinceAndCity", "方法开始执行",
				"WeixinAboutAction", getProjetUrl(request)));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取全部省份城市信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/options/getProvinceAndCity", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 获取全部省份城市信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		JSONArray provinceArray = jsonObj.getJSONArray("list");
		List<Province> list = (List<Province>) JSONArray
				.toCollection(provinceArray, Province.class);
		String listSize = jsonObj.getString("listSize");
		JSONObject reqJson = new JSONObject();
		reqJson.put("provinceList", list);
		reqJson.put("provinceListSize", listSize);
		reqJson.put("rescode", jsonObj.getString("rescode"));
		reqJson.put("resmsg_cn", jsonObj.getString("resmsg_cn"));
		setResposeMsg(reqJson.toString(), out);
	}
	
	
	/**
	 * 判断用户是否需要省份信息
	 * @param request
	 * @param response
	 * @param out
	 */
	@RequestMapping(value = "/withdrawNeedProvinceAndCity", method = RequestMethod.GET)
	public void withdrawNeedProvinceAndCity(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		logger.info(LogUtil.getStart("withdrawNeedProvinceAndCity", "方法开始执行",
				"WeixinAboutAction", getProjetUrl(request)));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("判断用户是否需要省份信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath
						+ "/options/withdrawNeedProvinceAndCity", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(" 判断用户是否需要省份信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(resultMsg);
		
		boolean needProvinceAndCity = jsonObj.getBoolean("needProvinceAndCity");
		JSONObject reqJson = new JSONObject();
		reqJson.put("rescode", jsonObj.getString("rescode"));
		reqJson.put("resmsg_cn", jsonObj.getString("resmsg_cn"));
		reqJson.put("needProvinceAndCity", needProvinceAndCity);
		setResposeMsg(reqJson.toString(), out);
		
	}
	
	/**
	 * app滚动公告信息取信息system_setting
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSystemSeetingInfo")
	public void getSystemSeetingInfo(PrintWriter out,HttpServletRequest request, HttpServletResponse response){
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", "1");
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("滚动公告信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getSystemSeetingInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("滚动公告信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		Map<String, Object> jsonMap = JsonUtil.getMapFromJsonString(resultMsg);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (Consts.SUCCESS_CODE.equals(jsonMap.get("rescode"))) {
			resultMap.put("commet",jsonMap.get("commet"));
			resultMap.put("message_level",jsonMap.get("message_level"));
			resultMap.put("message_title",jsonMap.get("message_title"));
		}
		resultMap.put("rescode", jsonMap.get("rescode"));
		setResposeMap(resultMap, out);
	}

	
	/**
	 * 跳转到我的二维码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goMyQuickMark", method = RequestMethod.GET)
	public String goMyQuickMark(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		if(StringUtils.isBlank(mobile)){
			mobile = request.getParameter("mobile");
		}
		
		request.setAttribute("mobile", mobile);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/myQuickMarkNew";
	}

	/**
	 * 超享计划债权转让协议
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goUTTranAgreement", method = RequestMethod.GET)
	public String goUTTranAgreement(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/UTTranAgreement";
	} 
	
	@RequestMapping(value = "/goMediaList", method = RequestMethod.GET)
	public String goMediaList(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/media/mediaList";
	} 
	
	@RequestMapping(value = "/goMessage", method = RequestMethod.GET)
	public String goMessage(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/message";
	} 
	
	@RequestMapping(value = "/goNotice", method = RequestMethod.GET)
	public String goNotice(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/notice";
	} 
	/**
	 * 优享计划服务协议
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goEnjoyPlanAgreement", method = RequestMethod.GET)
	public String goEnjoyPlanAgreement(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/enjoyPlanAgreement";
	} 
	
	@RequestMapping(value = "/goDetail", method = RequestMethod.GET)
	public String goDetail(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/detail";
	} 
	
	
	@RequestMapping(value = "/goPlanning", method = RequestMethod.GET)
	public String goPlanning(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/UShareEarningsDetails";
	} 
	
}
