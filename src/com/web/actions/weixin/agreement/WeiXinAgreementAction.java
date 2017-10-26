package com.web.actions.weixin.agreement;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

/**
 * 功能：获取各种协议的url
 * 
 * @author jiangbo
 * @date   2015-10-27
 * 
 * */
@Controller
@RequestMapping("/wxagreement")

public class WeiXinAgreementAction  extends BaseAction {
	private static final Log logger = LogFactory.getLog(WeiXinAgreementAction.class);

	
	/**
	 * 根据类型获取指定协议
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getServAgreementByType")
	public void getServAgreementByType(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		
		String from = request.getParameter("from");
		String type = request.getParameter("type");
		String orderId = request.getParameter("orderId");
		String loanMonths = request.getParameter("loanMonths");
		String productId = request.getParameter("productId");
		String sloanId = request.getParameter("sloanId");
		
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("type", type);
		map.put("sloanId", sloanId);
		map.put("orderId", orderId);
		
		if(type.equals("loanAgreement") || type.equals("loanAdvisoryServiceAgreement")) {
			if(type.equals("loanAdvisoryServiceAgreement")) {
				String userId="";
				UserSession us = UserCookieUtil.getUser(request);
				if(null == us || null == us.getId()){
					//用户表示验证机制（通过微信标识OPENID）
					Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
					userId = userInfoMap.get("userId");
				}else{
					userId = String.valueOf(us.getId());
				}
				map.put("userId", userId);
			}
			map.put("loanMonths", loanMonths);
			map.put("productId", productId);
		}
		if("enjoy".equals(type)){
			String  creditAcctCid=request.getParameter("creditAcctCid");
			if(creditAcctCid==null){
				creditAcctCid="";
			}
			map.put("creditAcctCid",creditAcctCid);
		}
		Map<String, Object> resultMap = this.getAgreementInfo(map, request);

		setResposeMap(resultMap, out);
	}
	
	/**
	 * 通过javaservice获取协议url
	 * 
	 * */
	public Map<String, Object> getAgreementInfo(Map<String, Object> map, HttpServletRequest request) {
		
		logger.info(LogUtil.getStart("getServAgreementByType", "方法开始执行", "WeiXinAgreementAction",
				getProjetUrl(request)));
		logger.info("type=" + map.get("type") + "&orderId=" + map.get("orderId") + "&userId=" + map.get("userId"));

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("服务协议加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/agreement/getServAgreementByType", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("服务协议解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		String serAgreementUrl = jsonObjRtn.getString("serAgreementUrl");
		String serAgreementName = jsonObjRtn.getString("serAgreementName");
		System.out.println("#####################" + serAgreementUrl+ "||" + serAgreementName);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("serAgreementUrl", serAgreementUrl);
		resultMap.put("lisserAgreementNamet", serAgreementName);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));
		resultMap.put("resmsg", jsonObjRtn.getString("resmsg"));
		
		return resultMap;
	}
	
	
	@RequestMapping(value = "/goAssignmentOfRegularAgreement", method = RequestMethod.GET)
	public String goAssignmentOfRegularAgreement(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/about/assignmentOfRegularAgreement";
	} 
	
}
