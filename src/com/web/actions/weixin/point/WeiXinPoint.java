package com.web.actions.weixin.point;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.actions.weixin.common.BaseAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wxPoint")

public class WeiXinPoint extends BaseAction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(WeiXinPoint.class);
	
	
	@RequestMapping(value = "/colletScore")
	public String colletScore(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/collectScore/colletScore";
	} 
	
	
	@RequestMapping(value = "/exchangeDetail")
	public String exchangeDetail(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/collectScore/exchangeDetail";
	} 

	
	@RequestMapping(value = "/outdateScore")
	public String creditoTranAgreement(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/collectScore/outdateScore";
	} 

	
	@RequestMapping(value = "/inOutDetail")
	public String baiduStatistics(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/collectScore/inOutDetail";
	} 
	
	@RequestMapping(value = "/scoreRule")
	public String scoreRule(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/collectScore/scoreRule";
	}
	
	/**
	 * 跳转到goExchangeSuccess这个页面
	 * @return
	 */
	@RequestMapping(value = "/goExchangeSuccess")
	public String goExchangeSuccess(HttpServletRequest request,HttpServletResponse res) {
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/collectScore/exchangeSuccess";
	}
	
	/**
	 * 
	 * @Description: 积分首页接口
	 * @param request
	 * @param response
	 * @return   
	 * @author wuhan
	 * @date 2016-10-12
	 */
	@RequestMapping(value = "/index")
	public void index(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		
		//初始化
		String loggerInfo="####积分首页接口";
		logger.info(loggerInfo+"开始 ####");
		
		String pointResultStr=null;
		JSONObject resultMap = new JSONObject();
		resultMap.put("bannersList", null);
		resultMap.put("useAmount", 0);
		resultMap.put("invalidAmount", 0);
		resultMap.put("pruductList", null);
		
		try {

			UserSession userSession=this.getUserSession(request, res);
			int userId=userSession.getId();//用户ID(必填)
			String mobile = userSession.getMobile();//手机号(必填)
			if(userId==0||StringUtils.isBlank(mobile)){
				logger.info(loggerInfo+"用户未登陆 ####");
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			logger.info(loggerInfo+" param userId:"+userId+"|mobile:"+mobile+"####");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("mobile",mobile);
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			//获取积分首页数据
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/index", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			if (StringUtils.isBlank(pointResultStr)) {
				logger.info(loggerInfo+" /point/index 积分接口调用失败  ####");
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(loggerInfo+" 返回结果:"+pointResultStr+"####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(loggerInfo+" error: 系统异常 ####",e);
			return;
		}
		logger.info(loggerInfo+" 结束 ####");
		setResposeMsg(pointResultStr, out);
	}
	
	
	/**
	 * 
	 * @Description: 查询积分产品详情接口
	 * @param request
	 * @param response
	 * @return   
	 * @author wuhan
	 * @date 2016-10-12
	 */
	@RequestMapping(value = "/queryPointProductDetail")
	public void queryPointProductDetail(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		
		String loggerInfo="####查询积分产品详情接口";
		logger.info(loggerInfo+"开始 ####");
		String productId=request.getParameter("productId");
		//初始化
		String pointResultStr=null;
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointProduct", null);
		
		if(StringUtils.isBlank(productId)){
			resultMap.put("resmsg_cn","商品id为空！");
			resultMap.put("rescode", Consts.CHECK_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.info(loggerInfo+" productId is null####");
			return;
		}
		logger.info(loggerInfo+" param productId:"+productId+"####");
		
		try {
			UserSession userSession=this.getUserSession(request, res);
			int userId=userSession.getId();//用户ID(必填)
			String mobile = userSession.getMobile();//手机号(必填)
			if(userId==0||StringUtils.isBlank(mobile)){
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			logger.info(loggerInfo+" param userId:"+userId+"|mobile:"+mobile+"####");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("mobile",mobile);
			userMap.put("productId",productId);
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			//获取积分首页数据
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/queryPointProductDetail", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			if (StringUtils.isBlank(pointResultStr)) {
				logger.info(loggerInfo+" /point/queryPointProductDetail 积分接口调用失败 ####");
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(loggerInfo+" 返回结果:"+pointResultStr+"####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(loggerInfo+" error: 系统异常 ####",e);
			return;
		}
		logger.info(loggerInfo+" 结束 ####");
		setResposeMsg(pointResultStr, out);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @Description: 获取积分说明
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getPointInfo")
	public void getPointInfo(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointDeclare", null);
		
		try {
			
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/getPointInfo", "");
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
			JSONObject.fromObject(pointResultStr);
		  
			} catch (Exception e) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
				logger.info(logInfo+"  结束");
			    return;

			}
			 logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"积分说明接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	
	
	
	/**
	 * 
	 * @Description: 获取积分开关1.是否隐藏积分（Y显示N隐藏）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/pointSwitch")
	public void pointSwitch(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointSwitch", "Y");
		resultMap.put("usablePoint", 0);
		resultMap.put("pointGrade", 1);
		
		try {
			
			
			
			UserSession userSession=this.getUserSession(request, response);
			
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="获取积分开关{是否隐藏积分（Y显示N隐藏）}参数phone="+phone;
			  logger.info(logInfo );
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			
			 logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("phone",phone);
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/pointSwitch", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
				} catch (Exception e) {
					resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
					resultMap.put("rescode", Consts.ERROR_CODE);
					setResposeMsg(resultMap.toString(), out);
					logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
					logger.info(logInfo+"  结束");
				    return;

				}
			
		
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	
	/**
	 * 
	 * @Title: getPointPaymentDetails
	 * @Description: 获取积分收支明细
	 * @param request
	 * @param response
	 * @return    
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value = "/getPointDetails")
	public void getPointDetails(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		resultMap.put("list", null);
		resultMap.put("pageObject", null);
		JSONObject jsonObjRtn =null;
		
		try {
			
		
			String flowType =request.getParameter("flowType");//类型 1收入 2支出 3过期 4全部
			
			if(StringUtils.isEmpty(flowType)){
				resultMap.put("resmsg_cn","参数检查失败，有null值。");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
		        return;
			}
			
			//分页参数
			String pageSize = request.getParameter(Consts.Page.PAGE_SIZE_NAME);// 每页行数
			String current =  request.getParameter(Consts.Page.PAGE_CURRENT_NAME);// 当前页
			
			if (pageSize == null || StringUtils.isBlank(pageSize) || "0".equals(pageSize)) {
				pageSize = "10";
			}
			if (current == null || StringUtils.isBlank(current) || "0".equals(current)) {
				current = "1";
			}
			
			int pSize=Integer.parseInt(pageSize);
			pSize=pSize>Consts.Page.PAGE_SIZE_MAX?Consts.Page.PAGE_SIZE_MAX:pSize;
			
			
			
			UserSession userSession=this.getUserSession(request, response);
			
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="获取用户等级参数phone="+phone+"useriD="+userId;
			  logger.info(logInfo );
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			
			
			 logger.info(logInfo +"调用shop系统");
			 
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("phone",phone);
			userMap.put("flowType",flowType);
			userMap.put("pageSize",pSize);
			userMap.put("current",current);
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/getPointDetails", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
	
			try {
				
				jsonObjRtn=JSONObject.fromObject(pointResultStr);
			  
				} catch (Exception e) {
					resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
					resultMap.put("rescode", Consts.ERROR_CODE);
					setResposeMsg(resultMap.toString(), out);
					logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
					logger.info(logInfo+"  结束");
				    return;

				}
			
			if (!Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
				jsonObjRtn.put("pageObject",null);
				setResposeMsg(jsonObjRtn.toString(), out);
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
			
			jsonObjRtn.put("pageObject",pageObject);

			
			
		
		   logger.info(logInfo+" 正常返回"+pageObject.toString() );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(jsonObjRtn.toString(), out);
	}
	
	
	
	/**
	 * 
	 * @Title: getUserGrade
	 * @Description: 根据用户ID 获得用户等级
	 * @param request
	 * @param response
	 * @return    
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value = "/getUserGrade")
	public void getUserGrade(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointGrade", null);
		
		try {
			

			UserSession userSession=this.getUserSession(request, response);
			
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="获取用户等级参数phone="+phone+"useriD="+userId;
			  logger.info(logInfo );
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			 logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("phone",phone);
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/getUserGrade", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
				} catch (Exception e) {
					resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
					resultMap.put("rescode", Consts.ERROR_CODE);
					setResposeMsg(resultMap.toString(), out);
					logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
					logger.info(logInfo+"  结束");
				    return;

				}
		
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	
	/**
	 * 
	 * @Title: getExpirePoint
	 * @Description:根据用户ID获得即将过期的用户积分按月求和
	 * @param request
	 * @param response
	 * @return    
	 * @return String    返回类型
	 * @throws
	 */
	@RequestMapping(value = "/getExpirePoint")
	public void getExpirePoint(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointExpireList", null);
		resultMap.put("amountSum", 0);
		
		try {
			
			UserSession userSession=this.getUserSession(request, response);
			
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="获得即将过期的用户积分按月求和phone="+phone+"useriD="+userId;
			  logger.info(logInfo );
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			 logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("phone",phone);
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/getExpirePoint", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
				} catch (Exception e) {
					resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
					resultMap.put("rescode", Consts.ERROR_CODE);
					setResposeMsg(resultMap.toString(), out);
					logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
					logger.info(logInfo+"  结束");
				    return;

				}
		
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	/**
	 * 兑换积分商品
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/exchangeProduct")
	public void exchangeProduct(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		
		String productId =request.getParameter("productId");//产品id
		try {
			
			UserSession userSession=this.getUserSession(request, response);
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="兑换积分商品phone="+phone+"userID="+userId;
			  logger.info(logInfo);
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("mobile",phone);
			userMap.put("productId",productId);
			
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/pointpruduct/exchangeProduct", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
			} catch (Exception e) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
				logger.info(logInfo+"  结束");
			    return;

			}
	
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	/**
	 * 
	 * @Description: 积分兑换产品列表
	 * @param request
	 * @param response
	 * @return   
	 * @author wuhan
	 * @date 2016-10-19
	 */
	@RequestMapping(value = "/pointProductList")
	public void pointProductList(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		
		String loggerInfo="####积分兑换产品列表接口";
		//初始化
		String pointResultStr=null;
		JSONObject resultMap = new JSONObject();
		resultMap.put("pruductList", null);
		resultMap.put("pageObject", null);
		JSONObject jsonObjRtn =null;
		
		try {
			//分页参数
			String pageSize = request.getParameter(Consts.Page.PAGE_SIZE_NAME);// 每页行数
			String current =  request.getParameter(Consts.Page.PAGE_CURRENT_NAME);// 当前页
			String typeIds = request.getParameter("typeIds");// 筛选类型ids
			
			if (StringUtils.isBlank(pageSize) || "0".equals(pageSize)) {
				pageSize = "10";
			}
			if (StringUtils.isBlank(current) || "0".equals(current)) {
				current = "1";
			}
			
			int pSize=Integer.parseInt(pageSize);
			pSize=pSize>Consts.Page.PAGE_SIZE_MAX?Consts.Page.PAGE_SIZE_MAX:pSize;
			
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("pageSize",pSize);
			userMap.put("current",current);
			userMap.put("typeIds",typeIds);
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/point/pointProductList", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(loggerInfo+" shop系统返回结果:"+pointResultStr);
			
			try {
					jsonObjRtn=JSONObject.fromObject(pointResultStr);
			  	} catch (Exception e) {
					resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
					resultMap.put("rescode", Consts.ERROR_CODE);
					setResposeMsg(resultMap.toString(), out);
					logger.error(loggerInfo+" shop系统返回异常pointResultStr="+pointResultStr,e);
					logger.info(loggerInfo+"  结束");
				    return;
			  	}
			
			if (!Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
				jsonObjRtn.put("pageObject",null);
				setResposeMsg(jsonObjRtn.toString(), out);
				return;
			}
			
			String size = jsonObjRtn.getString("totalNum");
			List<JSONObject> listDetail = jsonObjRtn.getJSONArray("pruductList");
			
			//封装pageObject 对象
			PageUtils pageObject = new PageUtils();
			if (null != listDetail && listDetail.size() != 0) {
				int intPageSize = 0;
				if (null != pageSize && !"".equals(pageSize)){
					intPageSize = Integer.parseInt(pageSize);
				}
				pageObject = PageUtil.execsPage(Integer.parseInt(current),
						Integer.parseInt(size), 5, intPageSize);
			}
			
			jsonObjRtn.put("pageObject",pageObject);

			logger.info(loggerInfo+" 返回  "+pageObject.toString() );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(loggerInfo+"接口  error: 系统异常",e);
	        logger.info(loggerInfo+"  结束");
	        return;
		}
		logger.info(loggerInfo+"  结束");
		setResposeMsg(jsonObjRtn.toString(), out);
	}
	
	/**
	 * 积分等级首页
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pointGradeIndex")
	public void pointGradeIndex(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		
		try {
			
			UserSession userSession=this.getUserSession(request, response);
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="积分等级首页phone="+phone+"userID="+userId;
			  logger.info(logInfo);
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("mobile",phone);
			
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/pointaccount/pointGradeIndex", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
			} catch (Exception e) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
				logger.info(logInfo+"  结束");
			    return;

			}
	
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	/**
	 * 等级切换
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeGrade")
	public void changeGrade(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		
		String gradeId =request.getParameter("gradeId");//等级id
		
		try {
			
			UserSession userSession=this.getUserSession(request, response);
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="changeGrade,phone="+phone+"userID="+userId+"gradeId="+gradeId;
			logger.info(logInfo);
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("mobile",phone);
			userMap.put("gradeId",gradeId);
			
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/pointaccount/changeGrade", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
			} catch (Exception e) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
				logger.info(logInfo+"  结束");
			    return;

			}
	
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	/**
	 * 特权详情
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/priviDetail")
	public void priviDetail(HttpServletRequest request,PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo=null;
		String pointResultStr=null;
		//初始化
		JSONObject resultMap = new JSONObject();
		
		String priviId =request.getParameter("priviId");//特权详情id
		try {
			
			UserSession userSession=this.getUserSession(request, response);
			int userId =userSession.getId();//用户ID(必填)
			String phone = userSession.getMobile();//手机号
			logInfo="特权详情phone="+phone+"userID="+userId+"priviId="+priviId;
			  logger.info(logInfo);
			
			if(userId==0||StringUtils.isEmpty(phone)){
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn","您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return ;
			}
			
			logger.info(logInfo +"调用shop系统");
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("userId",userId);
			userMap.put("mobile",phone);
			userMap.put("priviId",priviId);
			
			
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/pointaccount/priviDetail", param);
			pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr),"UTF-8");
			
			logger.info(logInfo+" shop系统返回结果:"+pointResultStr);
			
		
			if (null==pointResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			
			try {
				
				JSONObject.fromObject(pointResultStr);
			  
			} catch (Exception e) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				logger.error(logInfo+" shop系统返回异常pointResultStr="+pointResultStr );
				logger.info(logInfo+"  结束");
			    return;

			}
	
		   logger.info(logInfo+" 正常返回"+pointResultStr );
	
		} catch (Exception e) {
			
			resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo+"接口  error: 系统异常",e);
	        logger.info(logInfo+"  结束");
	        return;
		}
		setResposeMsg(pointResultStr, out);
	}
	
	
	/**
	 * 
	 * @Title: isMobileNO
	 * @Description: 校验手机号
	 * @param mobiles
	 * @return    
	 * @return boolean    返回类型
	 * @throws
	 */
	   private  boolean isMobileNO(String mobiles)
	    {
	        Pattern p = Pattern
	                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
	        Matcher m = p.matcher(mobiles);
	        return m.matches();
	    }
	   

	   
}
