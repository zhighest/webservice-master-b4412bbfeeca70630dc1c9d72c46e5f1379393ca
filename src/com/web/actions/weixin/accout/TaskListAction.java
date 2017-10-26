package com.web.actions.weixin.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.web.util.UserCookieUtil;

/**
 * 
 * 任务列表action
 * @author wuhan
 *
 */
@Controller
@RequestMapping("/tasklist")

public class TaskListAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = -4660733781531410744L;
	private static final Log logger = LogFactory.getLog(TaskListAction.class);
	
	
	
	/**
	 * 查询任务列表
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getTaskList",method=RequestMethod.POST)
	public void getTaskList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		String mobile = request.getParameter("mobile");
		String userId=null;
		JSONObject resultMap = new JSONObject();
		if(null==mobile||"".equals(mobile)){
			resultMap.put("resmsg_cn","手机号码为空！");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("list",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		logger.info("######################getTaskList获取的参数：["+mobile+"]");
		
		Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
		paramsMap.put("mobile",mobile);
		String param1 = CommonUtil.getParam(paramsMap);
		try {
			param1 = DES3Util.encode(param1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表加密失败:" + e.getMessage());
		}
		
		//获取用户id
		String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUserIdByMobile", param1);
		try {
			checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表解密失败:" + e.getMessage());
		}
		
		JSONObject checkCaptchatObject1 = JSONObject.fromObject(checkCaptchatMsg1);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject1.getString("rescode"))) {
			resultMap.put("resmsg_cn",checkCaptchatObject1.getString("resmsg_cn"));
			resultMap.put("list",null);
			resultMap.put("rescode",checkCaptchatObject1.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		userId=checkCaptchatObject1.get("userId")+"";
		if(null==userId||userId.equals("")){
			resultMap.put("resmsg_cn","您还未登录，请登录后操作");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("list",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("userId",userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表加密失败:" + e.getMessage());
		}
		//获取任务列表
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getTaskList", param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询任务列表解密失败:" + e.getMessage());
		}
		
		
		
		JSONObject checkCaptchatObject2 = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject2.getString("rescode"))) {
			resultMap.put("resmsg_cn",checkCaptchatObject2.getString("resmsg_cn"));
			resultMap.put("list",null);
			resultMap.put("rescode",checkCaptchatObject2.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		resultMap.put("rescode", checkCaptchatObject2.getString("rescode"));
		resultMap.put("list", checkCaptchatObject2.get("list"));
		resultMap.put("resmsg_cn",Consts.SUCCESS_DESCRIBE);
		
		setResposeMsg(resultMap.toString(), out);
	}
	
	
	/**
	 * 查询总菜单功能定制表数据
	 * @param request
	 * @param out
	 * @return
	 * @author wangdanqing 自定义菜单优化
	 */
	@RequestMapping(value = "/getAllIcoFuncList",method=RequestMethod.POST)
	public void getAllIcoFuncList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId=null;
		JSONObject resultMap = new JSONObject();
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		logger.info("######################getAllIcoFuncList获取的参数：["+userId+"]");

		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("userId",userId==null?"":userId);
		userMap.put("viewFlag","1");
		userMap.put("flag","1");
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询总菜单功能定制表加密失败:" + e.getMessage());
		}
		//获取快捷菜单数据
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getAllIcoFuncList", param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询总菜单功能定制表解密失败:" + e.getMessage());
		}
		
		System.out.println("=========================checkCaptchatMsg:"+checkCaptchatMsg);
		
		JSONObject checkCaptchatObject2 = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject2.getString("rescode"))) {
			resultMap.put("resmsg_cn",checkCaptchatObject2.getString("resmsg_cn"));
			resultMap.put("list",null);
			resultMap.put("rescode",checkCaptchatObject2.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
		resultMap.put("rescode", checkCaptchatObject2.getString("rescode"));
		resultMap.put("list", checkCaptchatObject2.get("list"));
		resultMap.put("backIcon", checkCaptchatObject2.get("backIcon"));
		resultMap.put("resmsg_cn",Consts.SUCCESS_DESCRIBE);
		
		setResposeMsg(resultMap.toString(), out);
	}

	/**
	 * 发布个人菜单定制表
	 * @param request
	 * @param out
	 * @return
	 * @author wangdanqing 自定义菜单优化
	 */
	@RequestMapping(value = "/addUserIcoFuncList",method=RequestMethod.POST)
	public void addUserIcoFuncList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String icoFuncId=request.getParameter("icoFuncId");
		String userId=null;
		JSONObject resultMap = new JSONObject();
		
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		} else {
			userId = String.valueOf(us.getId());
		}
		if(null==userId||"".equals(userId)){
			resultMap.put("resmsg_cn","您还未登录，请登录后操作");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("list",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
//		if(null==icoFuncId||"".equals(icoFuncId)){
//			resultMap.put("resmsg_cn","网络或服务器连接异常,请稍后再试!");
//			resultMap.put("rescode", Consts.CHECK_CODE);
//			resultMap.put("list",null);
//			setResposeMsg(resultMap.toString(), out);
//			return;
//		}
		logger.info("######################addUserIcoFuncList获取的参数：["+userId+"]");

		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("userId",userId);
		userMap.put("icoFuncId", icoFuncId==null?"":icoFuncId);
		userMap.put("viewFlag","1");
		userMap.put("flag","1");
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发布个人菜单定制表加密失败:" + e.getMessage());
		}
		
		//定制快捷菜单
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/addUserIcoFuncList", param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发布个人菜单定制表解密失败:" + e.getMessage());
		}
		
		
		
		JSONObject checkCaptchatObject2 = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject2.getString("rescode"))) {
			resultMap.put("resmsg_cn",checkCaptchatObject2.getString("resmsg_cn"));
			resultMap.put("list",null);
			resultMap.put("rescode",checkCaptchatObject2.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
		resultMap.put("rescode", checkCaptchatObject2.getString("rescode"));
		resultMap.put("resmsg_cn",Consts.SUCCESS_DESCRIBE);
		
		setResposeMsg(resultMap.toString(), out);
	}
	
	/**
	 * 查询是否完成任务
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/isCompleteTask",method=RequestMethod.POST)
	public void isCompleteTask(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		String mobile = request.getParameter("mobile");
		String userId=null;
		JSONObject resultMap = new JSONObject();
		if(null==mobile||"".equals(mobile)){
			resultMap.put("resmsg_cn","手机号码为空！");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("isCompleteTask",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		logger.info("######################isCompleteTask获取的参数：["+mobile+"]");
		
		Map<String,Object> paramsMap = new LinkedHashMap<String,Object>();
		paramsMap.put("mobile",mobile);
		String param1 = CommonUtil.getParam(paramsMap);
		try {
			param1 = DES3Util.encode(param1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询是否完成任务加密失败:" + e.getMessage());
		}
		
		//获取用户id
		String checkCaptchatMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUserIdByMobile", param1);
		try {
			checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询是否完成任务解密失败:" + e.getMessage());
		}
		
		JSONObject checkCaptchatObject1 = JSONObject.fromObject(checkCaptchatMsg1);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject1.getString("rescode"))) {
			resultMap.put("resmsg_cn",checkCaptchatObject1.getString("resmsg_cn"));
			resultMap.put("isCompleteTask",null);
			resultMap.put("rescode",checkCaptchatObject1.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		userId=checkCaptchatObject1.get("userId")+"";
		if(null==userId||userId.equals("")){
			resultMap.put("resmsg_cn","用户id为空！");
			resultMap.put("rescode", Consts.CHECK_CODE);
			resultMap.put("isCompleteTask",null);
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("userId",userId);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询是否完成任务密失败:" + e.getMessage());
		}
		//获取任务列表
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/isCompleteTask", param);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询是否完成任务失败:" + e.getMessage());
		}
		
		JSONObject checkCaptchatObject2 = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject2.getString("rescode"))) {
			resultMap.put("resmsg_cn",checkCaptchatObject2.getString("resmsg_cn"));
			resultMap.put("isCompleteTask",null);
			resultMap.put("rescode",checkCaptchatObject2.getString("rescode"));
			setResposeMsg(resultMap.toString(), out);
			return;
		}
		resultMap.put("rescode", checkCaptchatObject2.getString("rescode"));
		resultMap.put("isCompleteTask", checkCaptchatObject2.get("isCompleteTask"));
		resultMap.put("resmsg_cn",Consts.SUCCESS_DESCRIBE);
		
		setResposeMsg(resultMap.toString(), out);
	}
}
