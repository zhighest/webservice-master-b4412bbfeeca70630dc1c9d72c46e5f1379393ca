package com.web.actions.weixin.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.CouponsLuckDraw;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxassign")
@Scope("prototype")
public class WeixinAssignAction extends BaseAction implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeixinAssignAction.class);
	
	/**
	 * 跳转到 债权转让申请页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/goAssignApply", method = RequestMethod.GET)
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
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		//"weixin/accout/assignApply"
		return "weixin/product/transferApplication";
	}
	
	/**
	 * 处理债权转让申请
	 * @param res
	 * @param request
	 * @return
	 */
	@RequestMapping("/handleAssignApply")
	public void requestDebtAssignment(HttpServletResponse res,HttpServletRequest request, PrintWriter out){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//订单ID
		String orderId = request.getParameter("orderId");
		//客户种类(1:外部客户 2:内部客户)
		String customerType = request.getParameter("customerType");
		//applyFlag：('Y'：申请 ; 'N'：取消)
		String applyFlag = request.getParameter("applyFlag");
		if(StringUtils.isEmpty(applyFlag)){	
			resultMap.put(Consts.RES_CODE, Consts.CHECK_CODE);
			resultMap.put(Consts.RES_MSG_CN, "applyFlag不能为空!");
			setResposeMap(resultMap, out);
			return;
		}
		if(StringUtils.isEmpty(orderId)){	
			resultMap.put(Consts.RES_CODE, Consts.CHECK_CODE);
			resultMap.put(Consts.RES_MSG_CN, "订单ID不能为空!");
			setResposeMap(resultMap, out);
			return;
		}
		
		if(StringUtils.isEmpty(customerType)){
			resultMap.put(Consts.RES_CODE, Consts.CHECK_CODE);
			resultMap.put(Consts.RES_MSG_CN, "客户种类不能为空!");
			setResposeMap(resultMap, out);
			return;
		}
		
		String userId="";
		
		UserSession us = UserCookieUtil.getUser(request);
		
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		userMap.put("orderId", orderId);
		userMap.put("customerType", customerType);
		userMap.put("applyFlag", applyFlag);
		
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("处理债权转让申请/取消加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = "";
		try {
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/debt/handleDebtAssignment", param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("处理债权转让申请/取消出现异常:" + e.getMessage());
			
			resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			resultMap.put(Consts.RES_MSG_CN, "网络或服务器连接异常,请稍后再试!");
			setResposeMap(resultMap, out);
			e.printStackTrace();
			return;
		}
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("处理债权转让申请返回结果解码出现异常:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		
		if(jsonObjRtn == null){
			resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
			resultMap.put(Consts.RES_MSG, jsonObjRtn.getString(Consts.RES_MSG));
			resultMap.put(Consts.RES_MSG_CN, jsonObjRtn.getString(Consts.RES_MSG_CN));
			setResposeMap(resultMap, out);
			return;
		}
		
		String rescode = jsonObjRtn.getString("rescode");
		
		resultMap.put(Consts.RES_CODE, rescode);
		resultMap.put(Consts.RES_MSG, jsonObjRtn.getString(Consts.RES_MSG));
		resultMap.put(Consts.RES_MSG_CN, jsonObjRtn.getString(Consts.RES_MSG_CN));
		
		setResposeMap(resultMap, out);		
	}
	
	
	/**
	 * 查询债权转让利率信息
	 * @param res
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDebtAssignmentRate")
	public void getDebtAssignmentRate(HttpServletResponse res,HttpServletRequest request, PrintWriter out){
		
		JSONObject rtnJson = new JSONObject();
		
		//起息日期
		String valueDate = request.getParameter("valueDate");
		//客户种类(1:外部客户 2:内部客户)
		String customerType = request.getParameter("customerType");
		//投资本金
		String investAmount = request.getParameter("investAmount");
		if(StringUtils.isEmpty(valueDate)){	
			rtnJson.put(Consts.RES_CODE, Consts.CHECK_CODE);
			rtnJson.put(Consts.RES_MSG_CN, "起息日期不能为空!");
			setResposeMsg(rtnJson.toString(), out);
			return;
		}
		if(StringUtils.isEmpty(investAmount)){	
			rtnJson.put(Consts.RES_CODE, Consts.CHECK_CODE);
			rtnJson.put(Consts.RES_MSG_CN, "投资本金不能为空!");
			setResposeMsg(rtnJson.toString(), out);
			return;
		}
		
		if(StringUtils.isEmpty(customerType)){
			rtnJson.put(Consts.RES_CODE, Consts.CHECK_CODE);
			rtnJson.put(Consts.RES_MSG_CN, "客户种类不能为空!");
			setResposeMsg(rtnJson.toString(), out);
			return;
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("valueDate", valueDate);
		userMap.put("customerType", customerType);
		userMap.put("investAmount", investAmount);
		
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("查询债权转让利率信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		// 调用service接口
		String resultMsg = "";
		try {
			resultMsg = HttpRequestParam.sendGet(
					LoginRedirectUtil.interfacePath + "/debt/getDebtAssignmentRate", param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("查询债权转让利率信息出现异常:" + e.getMessage());
			
			rtnJson.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnJson.put(Consts.RES_MSG_CN, "网络或服务器连接异常,请稍后再试!");
			setResposeMsg(rtnJson.toString(), out);
			e.printStackTrace();
			return;
		}
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("查询债权转让利率信息返回数据解码出现异常:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		
		if(jsonObjRtn == null){
			rtnJson.put(Consts.RES_CODE, Consts.ERROR_CODE);
			rtnJson.put(Consts.RES_MSG, jsonObjRtn.getString(Consts.RES_MSG));
			rtnJson.put(Consts.RES_MSG_CN, jsonObjRtn.getString(Consts.RES_MSG_CN));
			setResposeMsg(rtnJson.toString(), out);
			return;
		}
		
		String rescode = jsonObjRtn.getString("rescode");
		
		if(Consts.SUCCESS_CODE.equals(jsonObjRtn.getString(Consts.RES_CODE))){		
			rtnJson.put(Consts.LIST, jsonObjRtn.getString(Consts.LIST));			
		}
		rtnJson.put(Consts.RES_CODE, rescode);
		rtnJson.put(Consts.RES_MSG, jsonObjRtn.getString(Consts.RES_MSG));
		rtnJson.put(Consts.RES_MSG_CN, jsonObjRtn.getString(Consts.RES_MSG_CN));
		
		setResposeMsg(rtnJson.toString(), out);
	
	}

	/**
	 * 
	 * @param request
	 * @param res
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	
	@RequestMapping(value = "/showCouponsLuckDraw", method = RequestMethod.GET)
	public void showCouponsLuckDraw(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(userId ==null){
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg_cn", "查询不到用户数据！");
		}else{
			Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
			reqJavaMap.put("userId", userId);

			String param = CommonUtil.getParam(reqJavaMap);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.info("查询用户抽奖表信息加密失败:" + e.getMessage());
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
				logger.info("查询用户抽奖表信息解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			
			
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			String rescode = jsonObjRtn.getString("rescode");
			resultMap.put("rescode", rescode);
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			if(rescode.equals("00000")){
				JSONObject couponsJson = jsonObjRtn.getJSONObject("couponsLuckDraw");
				CouponsLuckDraw couponsLuckDraw =(CouponsLuckDraw) JSONObject.toBean(couponsJson, CouponsLuckDraw.class);
				resultMap.put("couponsLuckDraw", couponsLuckDraw);
			}
		}
				
		//setResposeMap(resultMap, out);
		String req_data = JSON.toJSONString(resultMap);
		req_data = new String(req_data.toString().getBytes("utf-8"),
				"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}
	
}
