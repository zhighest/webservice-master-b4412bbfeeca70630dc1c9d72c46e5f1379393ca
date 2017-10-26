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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
import com.web.util.weixinAbout.WeixinRquestUtil;

/**
 * 
 * 销售人员管理action
 * 
 * @author wuhan
 *
 */
@Controller
@RequestMapping("/saleManager")

public class SaleManagerAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = -4660733781531410744L;
	private static final Log logger = LogFactory.getLog(SaleManagerAction.class);
	
	
	/**
	 * 销售人员管理页面
	 * @return
	 */
	@RequestMapping(value = "/salesManagement", method = RequestMethod.GET)
	public String salesManagement(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/salesManagement";
	}
	
	
	/**
	 * 销售人员管理详细页面
	 * @return
	 */
	@RequestMapping(value = "/salesManagementDetails", method = RequestMethod.GET)
	public String salesManagementDetails(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/salesManagementDetails";
	}
	
	/**
	 * 销售管理记录数据
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/querySaleManRecord",method=RequestMethod.POST)
	public void querySaleManRecord(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";//手机号码
		if(null == us || null == us.getMobile()){
			//(不跳转到登录)根据微信唯一标识获取手机号Mobile，userId，nickName
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = String.valueOf(us.getMobile());
		}
		if(null==mobile||mobile.equals("")){
			mobile=request.getParameter("mobileNumber");
		}
		String current=request.getParameter("current");//当前页数
		String pageSize=request.getParameter("pageSize");//显示条数
		
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		JSONObject resultMap = new JSONObject();
		if(null==mobile||"".equals(mobile)){
			resultMap.put("resmsg_cn","手机号码为空！");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("list",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		logger.info("######################querySaleManRecord获取的参数：["+mobile+"]");
		
		Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
		paramsMap.put("mobileNumber",mobile);
		paramsMap.put("current",current);
		paramsMap.put("pageSize",pageSize);
		String param1 = CommonUtil.getParam(paramsMap);
		try {
			param1 = DES3Util.encode(param1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表加密失败:" + e.getMessage());
		}
		
		//获取用户id
		String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/querySaleManRecord", param1);
		try {
			checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表解密失败:" + e.getMessage());
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(checkCaptchatMsg1);
		if (!Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
			resultMap.put("resmsg_cn",jsonObjRtn.getString("resmsg_cn"));
			resultMap.put("list",null);
			resultMap.put("rescode",jsonObjRtn.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
		String size = jsonObjRtn.getString("totalNum");
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		
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
		resultMap.put("rescode", Consts.SUCCESS_CODE);
		resultMap.put("resmsg_cn",Consts.SUCCESS_DESCRIBE);
		
		setResposeMsg(resultMap.toString(), out);
		return ;
	}
	
	/**
	 * 销售管理记录详情数据
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/querySaleManRecordByDate",method=RequestMethod.POST)
	public void querySaleManRecordByDate(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";//手机号码
		if(null == us || null == us.getMobile()){
			//(不跳转到登录)根据微信唯一标识获取手机号Mobile，userId，nickName
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			mobile = userInfoMap.get("mobile");
		} else {
			mobile = String.valueOf(us.getMobile());
		}
		
		if(null==mobile||mobile.equals("")){
			mobile=request.getParameter("mobileNumber");
		}
		
		String current=request.getParameter("current");//当前页数
		String pageSize=request.getParameter("pageSize");//显示条数
		String saleDate=request.getParameter("saleDate");//销售日期
		
		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		JSONObject resultMap = new JSONObject();
		if(null==mobile||"".equals(mobile)){
			resultMap.put("resmsg_cn","手机号码为空！");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("list",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		logger.info("######################querySaleManRecord获取的参数：["+mobile+"]");
		
		Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
		paramsMap.put("mobileNumber",mobile);
		paramsMap.put("current",current);
		paramsMap.put("pageSize",pageSize);
		paramsMap.put("saleDate",saleDate);
		String param1 = CommonUtil.getParam(paramsMap);
		try {
			param1 = DES3Util.encode(param1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表加密失败:" + e.getMessage());
		}
		
		//获取用户id  //querySetSaleManRecordByDate
		String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/querySaleManRecordByDate", param1);
		try {
			checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表解密失败:" + e.getMessage());
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(checkCaptchatMsg1);
		if (!Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
			resultMap.put("resmsg_cn",jsonObjRtn.getString("resmsg_cn"));
			resultMap.put("list",null);
			resultMap.put("rescode",jsonObjRtn.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
		String size = jsonObjRtn.getString("totalNum");
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		
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
		resultMap.put("rescode", Consts.SUCCESS_CODE);
		resultMap.put("resmsg_cn",Consts.SUCCESS_DESCRIBE);
		
		setResposeMsg(resultMap.toString(), out);
		return ;
	}
}
