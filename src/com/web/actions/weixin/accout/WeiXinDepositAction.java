package com.web.actions.weixin.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
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
import com.web.util.StringUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

/**
 * 活期新加接口
 * @author lbs-pc
 *
 */
@Controller
@RequestMapping("/wxdeposit")

public class WeiXinDepositAction extends BaseAction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeiXinDepositAction.class);


	/**
	 * 跳转到收益明细页面
	 * @return
	 */
	@RequestMapping(value = "/goEarningsDetails", method = RequestMethod.GET)
	public String goEarningsDetails(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		// 累计收益
		request.setAttribute("incomeTotal", "0.00");

		if (StringUtils.isBlank(userId)) {
			return "weixin/accout/earningsDetails";
		}
		
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
		}
		
		// 调用活期service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/showIndex", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
			// 累计收益
			request.setAttribute("incomeTotal", jsonObject.getString("incomeTotal"));
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/earningsDetails";
	}

	/**
	 * 跳转到收转出记录页面
	 * @return
	 */
	@RequestMapping(value = "/goTransferRecord", method = RequestMethod.GET)
	public String goTransferRecord(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/transferRecord";
	}

	/**
	 * 跳转到在投债权页面
	 * @return
	 */
	@RequestMapping(value = "/goInCreditor", method = RequestMethod.GET)
	public String goInCreditor(String accountAmount, HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		request.setAttribute("accountAmount", accountAmount);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/inCreditor";
	}
	
	/**
	 * 跳转到债权详情
	 * @return
	 */
	@RequestMapping(value = "/goInCreditorDetails", method = RequestMethod.GET)
	public String goInCreditorDetails(String sloanId, HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);
		
		request.setAttribute("sloanId", sloanId);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/inCreditorDetails";
	}


	/**
	 * 跳转到交易记录
	 * @return
	 */
	@RequestMapping(value = "/goTradingRecord", method = RequestMethod.GET)
	public String goTradingRecord(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/tradingRecord";
	}
	
	/**
	 * 跳转到转出页面
	 * @return
	 */
	@RequestMapping(value = "/goTransfer", method = RequestMethod.GET)
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String goTransfer(HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/transfer";
	}

	/**
	 * 余额宝历史收益列表接口	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/historyProfit")
	public void historyProfit(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/historyProfit====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
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

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("finaceId", "1");
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("余额宝历史收益列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/historyProfit", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("余额宝历史收益列表解密失败:" + e.getMessage());
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
					Integer.parseInt(size), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 拉取上架表	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/showInvestmentList")
	public void showInvestmentList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/showInvestmentList====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		if (StringUtils.isBlank(userId)) {
			userId = "";
		}
		
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		String planId = request.getParameter("planId");
		String sellingType = request.getParameter("sellingType");
		String loanId = request.getParameter("loanId");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("sellingType", sellingType==null?"":sellingType);
		userMap.put("loanId", loanId==null?"":planId);
		userMap.put("planId", planId==null?"":planId);
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("拉取上架表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/showInvestmentList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("拉取上架表解密失败:" + e.getMessage());
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
					Integer.parseInt(size), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 拉取上架表(根据在售>预售>售罄 时间降序获取1条活期标)	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/showInvestmentListInfo")
	public void showInvestmentListInfo(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/showInvestmentListInfo====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		if (StringUtils.isBlank(userId)) {
			userId = "";
		}
		
		String current = request.getParameter("current");
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		String planId = request.getParameter("planId");
		String sellingType = request.getParameter("sellingType");
		String loanId = request.getParameter("loanId");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("sellingType", sellingType==null?"":sellingType);
		userMap.put("loanId", loanId==null?"":planId);
		userMap.put("planId", planId==null?"":planId);
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("拉取上架表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/showInvestmentListInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("拉取上架表解密失败:" + e.getMessage());
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
					Integer.parseInt(size), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 立即加入	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/join")
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void join(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/join====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
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
			logger.info("立即加入加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/join", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("立即加入解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 转出接口	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/trunOut")
	@AvoidDuplicateSubmission(needRemoveToken = true)
	public void trunOut(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/trunOut====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		String turnOutAmount = request.getParameter("turnOutAmount");
		String passwordCash = request.getParameter("passwordCash");
		String payPwd = request.getParameter("payPwd");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("turnOutAmount", turnOutAmount);
		userMap.put("finaceId", "1");
		userMap.put("userId", userId);
		userMap.put("passwordCash", passwordCash);
		userMap.put("payPwd", payPwd);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("转出接口加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/trunOut", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("转出接口解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 获取当前在投债权及借款人信息	
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrderCredit")
	public void getOrderCredit(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/getOrderCredit====");
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
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

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取当前在投债权及借款人信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/getOrderCredit", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取当前在投债权及借款人信息解密失败:" + e.getMessage());
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
					Integer.parseInt(size), 5, intPageSize);
		}
		jsonObject.put("pageObject", pageObject);

		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 获取指定产品的服务协议(签约前)
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getServAgreementByFid")
	public void getServAgreementByFid(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/getServAgreementByFid====");
		
		String financeID = request.getParameter("financeID");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("financeID", financeID);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取指定产品的服务协议(签约前)加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/agreement/getServAgreementByFid", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取指定产品的服务协议(签约前)解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 获取我的账户信息
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMyAccountDetail")
	public void queryMyAccountDetail(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/queryMyAccountDetail====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("version", "1.0.0");//默认设置为1.0.0
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取我的账户信息加密失败:" + e.getMessage());
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/queryMyAccountDetail", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取我的账户信息解密失败:" + e.getMessage());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	
	/**
	 * 查询余额宝交易记录
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMoneyFundFlow")
	public void getMoneyFundFlow(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxdeposit/getMoneyFundFlow====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
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
		//资金类型。 不传则全部  1 : 加入 2 : 转出
		String fundType = request.getParameter("fundType");

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("current", current);
		userMap.put("pageSize", pageSize);
		userMap.put("fundType", fundType==null?"":fundType);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询余额宝交易记录加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/getMoneyFundFlow", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询余额宝交易记录解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String totalSize = jsonObject.getString("totalNum");
		if(StringUtil.isEmpty(totalSize)){
			totalSize="0";
		}
		String listSize = jsonObject.getString("listSize");
		if(StringUtil.isEmpty(listSize)){
			listSize="0";
		}
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
	 * 已售罄页面
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/goSoldOut", method = RequestMethod.GET)
	public String goSoldOut(HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/soldOut";
	}
	
	
	/**
	 *  专享加息说明页面
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/goRatesExplain", method = RequestMethod.GET)
	public String goRatesExplain(HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/ratesExplain";
	}
	/**
	 * 跳转VIP页面
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/goVipGrade", method = RequestMethod.GET)
	public String goVipGrade(HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/vipGrade";
	}
}
