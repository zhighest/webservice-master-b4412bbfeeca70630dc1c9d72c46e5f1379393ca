package com.web.actions.weixin.product;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.Interceptor.AvoidDuplicateSubmission;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.RateRisesCoupons;
import com.web.domain.Scattered;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wxproduct")

public class WeiXinProductList extends BaseAction {
	private static final Log logger = LogFactory
			.getLog(WeiXinProductList.class);

	/**
	 * 产品列表
	 *  
	 * @return
	 */
	@RequestMapping(value = "/goProductList", method = RequestMethod.GET)
	public String goProductList(HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);
		// 页面跳转标志
		request.setAttribute("pageTag", "productList");
		//不跳转登录后验证分享机制
		Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/productList";
	}

	/**
	 * 产品内容
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/goProductBuy")
	public String goProductDetails(HttpServletRequest request,HttpServletResponse res) throws UnsupportedEncodingException {
		// http://114.141.172.14:7080/webservice/wxproduct/goProductDetails?loanId=209961&productType=%E5%85%B6%E4%BB%96%E7%94%9F%E6%B4%BB%E7%94%A8%E9%80%94&loadTotalAmount=110000&surplusMonth=6&timeInterestRate=9&surplusMoney=110000&investmentProgress=0&planId=108&bidId=299&perServingAmount=100
		try {
			String mobile="";
			UserSession us = UserCookieUtil.getUser(request);
			if (us != null) {
				request.setAttribute("flag", "1"); // 已经登陆
				request.setAttribute("username", us.getUsername());
				mobile= us.getMobile();
			} else {
				Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
				String nickName  = userInfoMap.get("nickName");
				if(nickName == null || "".equalsIgnoreCase(nickName)){
					request.setAttribute("flag", "0"); // 没有登陆
				}else{
					request.setAttribute("flag", "1"); // 没有登陆
					request.setAttribute("username", nickName);
				}
				mobile= userInfoMap.get("mobile");
			}
			request.setAttribute("mobile",mobile );
			
		} catch (Exception e) {
			request.setAttribute("flag", "0"); // 没有登陆
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//未登录时 设定登录用参数(为了跳回)
		if ("0".equals(request.getAttribute("flag"))) {
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path;
			String url = request.getServletPath();
			String query = request.getQueryString();
			String backUrl = "";
			if (query != null) {
				backUrl = basePath + url + "?" + query;
			} else {
				backUrl = basePath + url;
			}
			logger.info("#######加密前backUrl：" + backUrl + "#####");
			// login接收的数据
			JSONObject json = new JSONObject();
			json.put("invitationCd", "");
			json.put("backUrl", backUrl);
			String loginParm = "";
			try {
//				loginParm = DES3Util.encode(json.toString());
				loginParm = getEncodeString(json.toString());
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}
			request.setAttribute("loginParm", loginParm);
			
			String goUrl="";
			try {
				goUrl = Base64.encodeBase64URLSafeString(("/wxuser/goLogin?parm="+loginParm).getBytes("UTF-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("goUrl", goUrl);
		}
		
		// 标的id
		String loanId = request.getParameter("loanId");
		// 上架表id
		String sloanId = request.getParameter("sloanId");
		// 合同金额
		String contactAmount = request.getParameter("contactAmount");
		// 计划id
		String planId = request.getParameter("planId");
		// 产品名称
		String cpType = request.getParameter("cpType");
		//加息券ID 
		String rateIds = request.getParameter("rateIds");
		//产品类型
		String product = request.getParameter("product");
		//加息券对象
		RateRisesCoupons rateRisesCoupons = null;
		
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>(); 
		if(StringUtils.isNotBlank(rateIds)){
			Map<String, Object> rateMap = new LinkedHashMap<String, Object>();
			if(StringUtils.isNotBlank(sloanId)&&(!sloanId.equals("undefined"))){
				//上架表不为空 判断标的表是否为空 为空则不坐任何操作
				if(StringUtils.isNotBlank(loanId)){
					reqJavaMap.put("loanId", sloanId);
				}
			}else{
				//如果上架表ID为空，根据加息券ID查询符合条件的上架表进行put进mobileView
				reqJavaMap.put("rateId", rateIds);
			}
			rateMap.put("rateIds", rateIds);
			rateMap.put("loanId", sloanId);
			String param = CommonUtil.getParam(rateMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + e.getMessage());
				e.printStackTrace();
			}

			// 调用service接口
			String resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/rateRises/queryRateRisesByRateId", param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			JSONObject rateRisesJson = jsonObject.getJSONObject("rateRisesCoupons");
			rateRisesCoupons =(RateRisesCoupons) JSONObject.toBean(rateRisesJson, RateRisesCoupons.class);
			
		}else if(StringUtils.isNotBlank(sloanId)&&(!sloanId.equals("undefined"))){
			if(StringUtils.isNotBlank(loanId)){
				reqJavaMap.put("loanId", sloanId);
			}
		}
		
		if(reqJavaMap.size()>0){
			
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
				//resultMap.put("scattered", scattered);
				if(scattered!=null){
					loanId = scattered.getBidId().toString();
					sloanId = scattered.getId().toString();
					contactAmount = scattered.getContactAmount().toString();
					planId = scattered.getPlanId().toString();
					cpType = scattered.getProductName();
				}
			}else{
				request.getSession().setAttribute("errorMsg", "notFundScattered");
				return "redirect:/wxcoupons/goMyInterest";
			}
		}
		try {
			cpType =  URLDecoder.decode(cpType,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("loanId", loanId);
		request.setAttribute("sloanId", sloanId);
		request.setAttribute("contactAmount", contactAmount);
		request.setAttribute("planId", planId);
		request.setAttribute("cpType",cpType );
		request.setAttribute("rateIds",rateIds );
		request.setAttribute("rateRisesCoupons",rateRisesCoupons );
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/productDetails";
	}

	/**
	 * 产品列表数据
	 * 
	 * @author wangenlai
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productList", method = RequestMethod.POST)
	public void goProductListInfo(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
		String pageSize = request.getParameter("pageSize");

		if (StringUtils.isBlank(current)) {
			current = "1";
		}

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("current", current);
		map.put("pageSize", pageSize);
		//上架类别
		map.put("sellingType", "1");
		map.put("userId", userId);

		logger.info(LogUtil.getStart("productList", "方法开始执行", "ProductAction",
				getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("产品列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/loan/getLoanList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("产品列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		String size = jsonObjRtn.getString("totalNum");
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		// String size = jsonObjRtn.getString("totalNum");
		System.out.println("#####################" + listDetail.size());
		Map<String, Object> resultMap = new HashMap<String, Object>();

		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), 5, intPageSize);
		}
		resultMap.put("pageObject", pageObject);
		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);
	}
	
	/**
	 * 产品列表数据
	 * 
	 * @author wangenlai
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productListInfo", method = RequestMethod.POST)
	public void getProductListInfo(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
		String pageSize = request.getParameter("pageSize");

		if (StringUtils.isBlank(current)) {
			current = "1";
		}

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("current", current);
		map.put("pageSize", pageSize);
		//上架类别
		map.put("sellingType", "1");
		map.put("userId", userId);

		logger.info(LogUtil.getStart("productList", "方法开始执行", "ProductAction",
				getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("产品列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/loan/getLoanListInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("产品列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		String size = jsonObjRtn.getString("totalNum");
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		// String size = jsonObjRtn.getString("totalNum");
		System.out.println("#####################" + listDetail.size());
		Map<String, Object> resultMap = new HashMap<String, Object>();

		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), 5, intPageSize);
		}
		resultMap.put("pageObject", pageObject);
		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);
	}
	
	/**
	 * 定期产品列表(扩展条件:(1)只显示可以购买的产品(2)只显示不同的标)
	 * 
	 * @author wangenlai
	 * @modify ZuoJun
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductListInfo", method = RequestMethod.POST)
	public void getRegularProductListInfo(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
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
			userId = request.getParameter("userId");
		}

		
		String current = request.getParameter("current");
		String pageSize = request.getParameter("pageSize");

		if (StringUtils.isBlank(current)) {
			current = "1";
		}

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("current", current);
		map.put("pageSize", pageSize);
		//上架类别
		map.put("sellingType", "1");
		map.put("userId", userId);

		logger.info(LogUtil.getStart("productList", "方法开始执行", "ProductAction",
				getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取定期产品列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/loan/getLoanListInfo", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取定期产品列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		String size = jsonObjRtn.getString("totalNum");
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		// String size = jsonObjRtn.getString("totalNum");
		System.out.println("#####################" + listDetail.size());
		Map<String, Object> resultMap = new HashMap<String, Object>();

		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), 5, intPageSize);
		}
		resultMap.put("pageObject", pageObject);
		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));

		setResposeMap(resultMap, out);
	}

	/**
	 * 产品详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productDetails", method = RequestMethod.POST)
	public String goProductInfo(HttpServletRequest request) {
		// request 值
		String loanId = request.getParameter("loanId"); // 标的ID
		String productType = request.getParameter("productType"); // 购车贷
																	// 2015-07-08
		String loadTotalAmount = request.getParameter("loadTotalAmount"); // 借款总额
		String surplusMonth = request.getParameter("surplusMonth"); // 剩余期限
		String timeInterestRate = request.getParameter("timeInterestRate"); // 年化利率
		String surplusMoney = request.getParameter("surplusMoney"); // 剩余金额
		String investmentProgress = request.getParameter("investmentProgress"); // 投资进度
		String planId = request.getParameter("planId"); // 计划ID
		String perServingAmount = request.getParameter("perServingAmount"); // 起设金额

		// response 值
		request.setAttribute("loanId", loanId);
		request.setAttribute("productType", productType);
		request.setAttribute("loadTotalAmount", loadTotalAmount);
		request.setAttribute("surplusMonth", surplusMonth);
		request.setAttribute("timeInterestRate", timeInterestRate);
		request.setAttribute("surplusMoney", surplusMoney);
		request.setAttribute("investmentProgress", investmentProgress);
		request.setAttribute("planId", planId);
		request.setAttribute("perServingAmount", perServingAmount);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "product/productDetails";

	}

	/**
	 * 获取标的借款信息
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getLoanDetailInfo", method = RequestMethod.POST)
	public void getLoanDetailInfo(HttpServletRequest request, PrintWriter out) {

		String loanId = request.getParameter("loanId");

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scatteredLoanId", loanId);

		logger.info(LogUtil.getStart("getLoanDetailInfo", "方法开始执行",
				"ProductAction", getProjetUrl(request)));
		logger.info("loanId=" + loanId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取标的借款信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/loan/getLoanDetailInfo",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取标的借款信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}

	/**
	 * 散标投资人列表
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserListByLoanId", method = RequestMethod.POST)
	public void getUserListByLoanId(HttpServletRequest request, PrintWriter out) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 标的类别 108：定期，109：活期
		String productType = request.getParameter("productType");
		//标的ID(key)
		String loanId = request.getParameter("loanId");
		// 当前页数
		String current = request.getParameter("current"); 
		// 页大小
		String pageSize = request.getParameter("pageSize"); 
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		//标的id(key) 定期用
		map.put("scatteredLoanId", loanId==null?"":loanId);
		//标的id(key) 活期用
		map.put("loanId", loanId==null?"":loanId);
		map.put("current", current);
		map.put("pageSize", pageSize);

		logger.info(LogUtil.getStart("getUserListByLoanId", "方法开始执行", "ProductAction", getProjetUrl(request)));
		logger.info("loanId=" + loanId + "current=" + current + "pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("散标投资人列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String resultMsg = "";
		// 获取定期投资人列表
		if ("108".equals(productType)) {
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/loan/getUserListByLoanIdForWx",param);
		}
		// 获取活期投资人列表
		if ("109".equals(productType)) {
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/loan/getUserListByLoanId",param);
		}
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("散标投资人列表解密失败:" + e.getMessage());
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
		}
		if (StringUtils.isNotBlank(resultMsg)) {
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
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
			resultMap.put("pageObject", pageObject);
			resultMap.put("list", listDetail);
			resultMap.put("rescode", jsonObjRtn.getString("rescode"));
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
		} else {
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
		}

		setResposeMap(resultMap, out);
	}

	/**
	 * 获取 活期+定期  排序后的产品标的列表
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/getProductLoanList", method = RequestMethod.POST)
	public void getProductLoanList(HttpServletRequest request, HttpServletResponse res, PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		if (StringUtils.isBlank(userId)) {
			userId = "";
		}
//		userId = "928648256";
		JSONObject retJson = new JSONObject();

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("余额宝历史收益列表加密失败:" + e.getMessage());
		}
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/product/getProductLoanList",param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("产品列表解密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数加密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}
		String req_data = new String(resultMsg.getBytes("utf-8"),"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	/**
	 * @Description:  活期+定期 +优享 网站
	 * @param @param request
	 * @param @param res
	 * @param @param out
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @throws
	 * @author chenshufeng
	 * @date 2016-9-5
	 */
	@RequestMapping(value = "/getProductLoanListWeb", method = RequestMethod.POST)
	public void getProductLoanListWeb(HttpServletRequest request, HttpServletResponse res, PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		if (StringUtils.isBlank(userId)) {
			userId = "";
		}
//		userId = "928648256";
		JSONObject retJson = new JSONObject();
		
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("余额宝历史收益列表加密失败:" + e.getMessage());
		}
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/product/getProductLoanListWeb",param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("产品列表解密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数加密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}
		String req_data = new String(resultMsg.getBytes("utf-8"),"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	private String getEncodeString(String str) {
		String parm = "";
		try {
			parm = URLEncoder.encode(DES3Util.encode(str),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		return parm;
	}
	
	/**
	 * 跳转到活期首页（包括定期）
	 * @return
	 */
	@RequestMapping(value = "/goDemandProductIndex", method = RequestMethod.GET)
	public String goDemandProductIndex(HttpServletRequest request,HttpServletResponse response) {
		logger.info("======进入方法：wxproduct/goDemandProductIndex====");
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		UserSession us = UserCookieUtil.getUser(request);

		// 页面跳转标志
		request.setAttribute("pageTag", "productIndex");
		String userId="";
		String mobile="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
			mobile= userInfoMap.get("mobile");
		} else {
			userId = String.valueOf(us.getId());
			mobile = String.valueOf(us.getMobile());
		}
		request.setAttribute("mobile", mobile);

		// 昨日收益
		request.setAttribute("yesterdayGain", "0.00");
		// 在投收益
		request.setAttribute("currentIncome", "0.00");
		// 在投金额
		request.setAttribute("accountAmount", "0.00");
		// 累计收益
		request.setAttribute("incomeTotal", "0.00");
		// 年化收益率
		request.setAttribute("annualizedReturnRate", "0");
		//浮动加息
		request.setAttribute("adjustRate", "0");
		// 定期资产总额
		request.setAttribute("investmentAssets", "0.00");
		// 定期预期收益
		request.setAttribute("expectAmount", "0.00");
		// 定期累计收益
		request.setAttribute("termIncomeAmount", "0.00");
		//当日加息利率
		request.setAttribute("couponsRateRises", "0");
		//签到加息利率（新增与加息券拆分开）
		request.setAttribute("signInRateRises", "0");

		if (StringUtils.isBlank(userId)) {
			request.setAttribute("userIdFlag", "N");
			return "weixin/product/demandProductIndex";
		}else{
			request.setAttribute("userIdFlag", "Y");
		}
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId == null ? "" : userId);
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
			// 昨日收益
			request.setAttribute("yesterdayGain", jsonObject.getString("yesterdayGain"));
			// 在投收益
			request.setAttribute("currentIncome", jsonObject.getString("currentIncome"));
			// 在投金额
			request.setAttribute("accountAmount", jsonObject.getString("accountAmount"));
			// 累计收益
			request.setAttribute("incomeTotal", jsonObject.getString("incomeTotal"));
			// 年化收益率
			request.setAttribute("annualizedReturnRate", jsonObject.getString("annualizedReturnRate"));
			//当日加息利率
			request.setAttribute("couponsRateRises", jsonObject.getString("couponsRateRises"));
			//签到加息利率
			request.setAttribute("signInRateRises", jsonObject.getString("signInRateRises"));
			//浮动加息
			request.setAttribute("adjustRate", jsonObject.getString("adjustRate"));
		}


		// 调用定期service接口
		String termResultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/showTermIndex", param);
		try {
			termResultMsg = java.net.URLDecoder.decode(DES3Util.decode(termResultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject termJsonObject = JSONObject.fromObject(termResultMsg);
		if (Consts.SUCCESS_CODE.equals(termJsonObject.getString("rescode"))) {
			// 定期资产总额
			request.setAttribute("investmentAssets", termJsonObject.getString("investmentAssets"));
			// 定期预期收益
			request.setAttribute("expectAmount", termJsonObject.getString("expectAmount"));
			// 定期累计收益
			request.setAttribute("termIncomeAmount", termJsonObject.getString("termIncomeAmount"));
		}
		// 公众号链接过来的传值
		request.setAttribute("channel", request.getParameter("channel"));

		return "weixin/product/demandProductIndex";
	}
	

	/**
	 * 跳转到活期列表
	 * @return
	 */
	@RequestMapping(value = "/goDemandProductList", method = RequestMethod.GET)
	public String goDemandProductList(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/demandProductList";
	}
	
	/**
	 * 跳转到投资人列表页(活期)
	 * @return
	 */
	@RequestMapping(value = "/goDemandInvestorList", method = RequestMethod.GET)
	public String goDemandInvestorList(String loanId, String productType, 
				HttpServletRequest request,HttpServletResponse res) {
		UserSession userSession = UserCookieUtil.getUser(request);

		request.setAttribute("loanId", loanId);
		request.setAttribute("productType", productType);
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/demandInvestorList";
	}
	
	/**
	 * 跳转到活期购买确认
	 * @return
	 */
	@RequestMapping(value = "/goDemandOrderConfirmation", method = RequestMethod.GET)
	@AvoidDuplicateSubmission(needSaveToken = true)
	public String goDemandOrderConfirmation(HttpServletRequest request,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		String mobile = "";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
			mobile = userInfoMap.get("mobile");
		} else {
			userId = String.valueOf(us.getId());
			mobile = us.getMobile();
		}
		//标的表KEY
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		//上架名称
		String productName = request.getParameter("productName");
		try {
			request.setAttribute("productName", URLDecoder.decode(productName,"UTF-8"));
		} catch (Exception e) {
			logger.info("productName解密异常"+e.getMessage());
		}

		if(null==mobile || "".equals(mobile)){
			mobile = request.getParameter("mobile");
		}
		request.setAttribute("mobile",mobile);
		
		//剩余金额
		request.setAttribute("remanAmount", request.getParameter("remanAmount"));
		//当日年化利率
		request.setAttribute("annualRate", request.getParameter("annualRate"));
		//起投金额
		request.setAttribute("investMinAmount", request.getParameter("investMinAmount"));
		//账户余额
		String acctBalance = "0.00";
		
		Map<String, Object> loanMap = new LinkedHashMap<String, Object>();
		loanMap.put("scatteredLoanId", id);
		String loanParam = CommonUtil.getParam(loanMap);
		try {
			loanParam = DES3Util.encode(loanParam);
		} catch (Exception e) {
			logger.info("获取标的详细信息加密失败:" + e.getMessage());
		}

		// 调用service接口
		String loanResultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/loan/getLoanDetailById", loanParam);
		try {
			loanResultMsg = java.net.URLDecoder.decode(DES3Util.decode(loanResultMsg), "UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(loanResultMsg);
			if (jsonObject!=null && Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
				JSONObject loanDetail = jsonObject.getJSONObject("loanDetail");
				//上架名称
				request.setAttribute("productName", loanDetail.getString("productName"));
				//剩余金额
				request.setAttribute("remanAmount", loanDetail.getString("remanAmount"));
				//当日年化利率
				request.setAttribute("annualRate", loanDetail.getString("annualRate"));
				//起投金额
				request.setAttribute("investMinAmount", loanDetail.getString("investMinAmount"));
			}
		} catch (Exception e) {
			logger.info("获取标的信息解密失败:" + e.getMessage());
		}

		/*//查询用户当日加息利率
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>(); 
		reqJavaMap.put("userId", userId);
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询符合加息券的产品加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/rateRises/queryUserTodayRateRises", param);
		
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
		
		request.setAttribute("couponsRateRises", jsonObjRtn.getString("couponsRateRises"));
		*/
		
		
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("userId", userId);
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
			JSONObject jsonObject = JSONObject.fromObject(resultMsg);
			if (jsonObject!=null && Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
				JSONObject accountDetail = jsonObject.getJSONObject("accountDetail");
				acctBalance = accountDetail.getString("acctBalance");
				if (StringUtils.isBlank(acctBalance)) {
					acctBalance =  "0.00";
				}
			}
		} catch (Exception e) {
			logger.info("获取我的账户信息解密失败:" + e.getMessage());
		}
		//账户余额
		request.setAttribute("acctBalance", acctBalance);
		
		// 获取活期利率
		String adjustRate = "0";
		String rateMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/deposit/findCurrentRate", param);
		try {
			rateMsg = java.net.URLDecoder.decode(DES3Util.decode(rateMsg), "UTF-8");
			JSONObject rateObject = JSONObject.fromObject(rateMsg);
			if (rateObject!=null && Consts.SUCCESS_CODE.equals(rateObject.getString("rescode"))) {
				adjustRate = rateObject.getString("adjustRate");
			}
		} catch (Exception e) {
			logger.info("获取零钱利率解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		// 调整利率
		request.setAttribute("adjustRate", adjustRate);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/demandOrderConfirmation";
	}
	
	
	/**
	 * 跳转到定期资产列表
	 * @return
	 */
	@RequestMapping(value = "/goFixAssetList", method = RequestMethod.GET)
	public String goFixAssetList(HttpServletRequest request,HttpServletResponse res) {
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
		return "weixin/product/fixAssetList";
	}

	/**
	 * 跳转到定期资产列表
	 * @return
	 */
	@RequestMapping(value = "/goTransferApplication", method = RequestMethod.GET)
	public String goTransferApplication(HttpServletRequest request,HttpServletResponse res) {
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
		return "weixin/product/transferApplication";
	}
	
	@RequestMapping(value = "/goFixProductIndex", method = RequestMethod.GET)
	public String goFixProductIndex(HttpServletRequest request,HttpServletResponse res) {
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
		return "weixin/product/fixProductIndex";
	}

	
	@RequestMapping(value = "/goProductIndex", method = RequestMethod.GET)
	public String goProductIndex(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/productIndex";
	} 
	
	
	/**
	 * 查询今日投资人数（活期）
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/currentInvestNum", method = RequestMethod.POST)
	public void currentInvestNum(HttpServletRequest request, PrintWriter out) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//标的ID(key)
		String loanId = request.getParameter("loanId");
		
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		//标的id(key) 活期用
		map.put("loanId", loanId==null?"":loanId);
		logger.info(LogUtil.getStart("currentInvestNum", "方法开始执行", "ProductAction", getProjetUrl(request)));
		logger.info("loanId=" + loanId );

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询今日投资人数（零钱计划）:" + e.getMessage());
			e.printStackTrace();
		}
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/loan/currentInvestNum",param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询今日投资人数（零钱计划）解密失败:" + e.getMessage());
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
			setResposeMap(resultMap, out);
			return;
		}
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		if (jsonObjRtn!=null && Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
			resultMap.put("peoplenum", jsonObjRtn.getString("peoplenum"));
			resultMap.put("rescode", jsonObjRtn.getString("rescode"));
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			setResposeMap(resultMap, out);
			return;
		} else {
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
		}
		setResposeMap(resultMap, out);
	}
	
	
	/**
	 * 查询今日投资列表（活期）
	 * 
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/currentInvestList", method = RequestMethod.POST)
	public void currentInvestList(HttpServletRequest request, PrintWriter out) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//标的ID(key)
//		String loanId = request.getParameter("loanId");
		// 当前页数
		String current = request.getParameter("current"); 
		// 页大小
		String pageSize = request.getParameter("pageSize"); 
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		//标的id(key) 活期用
//		map.put("loanId", loanId==null?"":loanId);
		map.put("current", current);
		map.put("pageSize", pageSize);

		logger.info(LogUtil.getStart("currentInvestList", "方法开始执行", "ProductAction", getProjetUrl(request)));
		logger.info("current=" + current + "pageSize=" + pageSize);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询今日投资列表（零钱计划）" + e.getMessage());
			e.printStackTrace();
		}
		String resultMsg= HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/loan/currentInvestList",param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询今日投资列表（零钱计划）解密失败:" + e.getMessage());
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "网络或服务器连接异常,请稍后再试!");
			setResposeMap(resultMap,out);
			return;
		}
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
				setResposeMap(resultMap,out);
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
			setResposeMap(resultMap,out);
			return;
		}
	}
	
	/**
	 * 今日活期投资人数列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goCurrentInvestList", method = RequestMethod.GET)
	public String goCurrentInvestList(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/product/currentInvestList";
	} 

}
