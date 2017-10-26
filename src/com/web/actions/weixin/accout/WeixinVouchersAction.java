package com.web.actions.weixin.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.RateRisesCoupons;
import com.web.domain.Scattered;
import com.web.domain.UserSession;
import com.web.domain.UserVouchers;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.UserCookieUtil;
import com.web.vo.UserVouchersVo;

@Controller
@RequestMapping("/wxvouchers")

public class WeixinVouchersAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Log logger = LogFactory
			.getLog(WeixinVouchersAction.class);
	
	/**
	 * 代金券管理
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/myVouchers", method = RequestMethod.GET)
	public void myRateCoupons(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
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
		String loanId = request.getParameter("loanId");
		//分页信息
		String current = request.getParameter("current"); // 当前页数
		String pageSize = request.getParameter("pageSize"); // 页大小
		String investmentAmount = request.getParameter("investmentAmount");//投资金额
		if(StringUtils.isNotBlank(investmentAmount)){
			reqJavaMap.put("investmentAmount", investmentAmount);
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
		
		if(StringUtils.isNotBlank(loanId)){
			reqJavaMap.put("loanId", loanId);
		}
		String vsFlag = request.getParameter("vsFlag");
		String sortType = request.getParameter("sortType");
		String orderBy = request.getParameter("orderBy");
		String remanPeriods = request.getParameter("remanPeriods");
		String productType = request.getParameter("productType");
	
		if(StringUtils.isNotBlank(vsFlag)){
			reqJavaMap.put("vsFlag",vsFlag);
		}
		if(StringUtils.isNotBlank(sortType)){
			reqJavaMap.put("sortType",sortType);
		}
		if(StringUtils.isNotBlank(orderBy)){
			reqJavaMap.put("orderBy",orderBy);
		}
		if(StringUtils.isNotBlank(remanPeriods)){
			reqJavaMap.put("remanPeriods",remanPeriods);
		}
		if(StringUtils.isNotBlank(productType)){
			reqJavaMap.put("productType",productType);
		}
	
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户代金券查询加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/userVochers/myVouchers", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("用户代金券查询解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		
		if(rescode.equals(Consts.SUCCESS_CODE)){
			String listSize = jsonObjRtn.getString("listSize");
			JSONArray listArray = jsonObjRtn.getJSONArray("list");
			List<UserVouchersVo> userVoucherVoList = (List<UserVouchersVo>) JSONArray.toCollection(listArray,UserVouchersVo.class);
			resultMap.put("list", userVoucherVoList);
			String totalNum = jsonObjRtn.getString("totalNum");
			resultMap.put("totalNum", totalNum);
			PageUtils pageObject = new PageUtils();
			if (null != userVoucherVoList && userVoucherVoList.size() != 0) {
				int intPageSize = 0;
				if (null != pageSize && !"".equals(pageSize)) {
					intPageSize = Integer.parseInt(pageSize);
				}
				pageObject = PageUtil.execsPage(Integer.parseInt(current),
						Integer.parseInt(totalNum), 5, intPageSize);
			}
			resultMap.put("pageObject", pageObject);
			
		}
		resultMap.put("rescode", jsonObjRtn.get("rescode"));
		setResposeMap(resultMap, out);
	}
	
	
	/**
	 * 根据代金券ID查询相应产品
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	
	@RequestMapping(value = "/getLoanByVochers", method = RequestMethod.GET)
	public void getLoanByVochers(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
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
		String voucherId = request.getParameter("voucherId");
		reqJavaMap.put("userId", userId);
		reqJavaMap.put("voucherId", voucherId);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("根据代金券ID查询相应产品加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/userVochers/getScatteredInfoByVoucherId", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("根据代金券ID查询相应产品解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		if(rescode.equals("00000")){
			String listSize = jsonObjRtn.getString("listSize");
			JSONArray listArray = jsonObjRtn.getJSONArray("list");
			List<Scattered> scatteredList = (List<Scattered>) JSONArray.toCollection(listArray, Scattered.class);
			String totalNum = jsonObjRtn.getString("totalNum");
			resultMap.put("totalNum", totalNum);
			resultMap.put("list", scatteredList);
		}
		resultMap.put("rescode", jsonObjRtn.get("rescode"));
		setResposeMap(resultMap, out);
	}
	
	/**
	 * 查询相关券列表
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/getMyCouponsList", method = RequestMethod.GET)
	public void getMyCouponsList(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
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
		String productType = request.getParameter("productType");
		String remanPeriods = request.getParameter("remanPeriods");
		String remanFlag = request.getParameter("remanFlag");
		
		reqJavaMap.put("productType", productType);
		reqJavaMap.put("remanPeriods", StringUtils.isNotBlank(remanPeriods)?remanPeriods:0);
		reqJavaMap.put("remanFlag", remanFlag);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询相关券列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/userVochers/getMyCouponsList", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询相关券列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		if(rescode.equals("00000")){
			String rateRisesCouponsListCount = jsonObjRtn.getString("rateRisesCouponsListCount");
			JSONArray rateRisesCouponsListArray = jsonObjRtn.getJSONArray("rateRisesCouponsList");
			List<RateRisesCoupons> rateRisesCouponsList = (List<RateRisesCoupons>) JSONArray.toCollection(rateRisesCouponsListArray, RateRisesCoupons.class);
			
			String vouchersListCount = jsonObjRtn.getString("vouchersListCount");
			JSONArray vouchersListArray = jsonObjRtn.getJSONArray("vouchersList");
			List<UserVouchers> vouchersList = (List<UserVouchers>) JSONArray.toCollection(vouchersListArray, UserVouchers.class);
			
			resultMap.put("rateRisesCouponsListCount", rateRisesCouponsListCount);
			resultMap.put("rateRisesCouponsList", rateRisesCouponsList);
			resultMap.put("vouchersListCount", vouchersListCount);
			resultMap.put("vouchersList", vouchersList);
		}
		resultMap.put("rescode", jsonObjRtn.get("rescode"));
		setResposeMap(resultMap, out);
	}
	
	
	/**
	 * 根据产品ID查询代金券列表
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/queryVouchersByLoanId", method = RequestMethod.GET)
	public void queryVouchersByLoanId(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out){
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
		String loanId = request.getParameter("loanId");
		reqJavaMap.put("loanId", loanId);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("根据产品ID查询代金券列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/userVochers/queryVouchersByLoanId", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询相关券列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap;
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		resultMap = new HashMap<String, Object>();
		
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		if(rescode.equals("00000")){
			String vouchersListCount = jsonObjRtn.getString("totalNum");
			JSONArray vouchersListArray = jsonObjRtn.getJSONArray("list");
			List<UserVouchers> vouchersList = (List<UserVouchers>) JSONArray.toCollection(vouchersListArray, UserVouchers.class);
			
			resultMap.put("vouchersListCount", vouchersListCount);
			resultMap.put("vouchersList", vouchersList);
		}
		resultMap.put("rescode", jsonObjRtn.get("rescode"));
		setResposeMap(resultMap, out);
	}
}
