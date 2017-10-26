package com.web.actions.weixin.userrank;

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

import net.sf.json.JSONObject;

/**
 * 
 * 排行榜 action
 * @author wuhan
 *
 */
@Controller
@RequestMapping("/userRank")
public class UserRankAction extends BaseAction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(UserRankAction.class);
	
	
	
	@RequestMapping(value = "/queryUserRankList")
	public void queryUserRankList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		
		String loggerInfo="查询排行榜接口";
		logger.info(loggerInfo+"开始 ####");
		
		//初始化
		String userRankResultStr=null;
		JSONObject resultMap = new JSONObject();
		resultMap.put("list", null);
		resultMap.put("pageObject", null);
		JSONObject jsonObjRtn =null;
		
		try {
			//分页参数
			String pageSize = request.getParameter(Consts.Page.PAGE_SIZE_NAME);// 每页行数
			String current =  request.getParameter(Consts.Page.PAGE_CURRENT_NAME);// 当前页
			//请求参数
			String rankId = request.getParameter("rankId");
			
			if (StringUtils.isBlank(pageSize) || "0".equals(pageSize)) {
				pageSize = "100";
			}
			if (StringUtils.isBlank(current) || "0".equals(current)) {
				current = "1";
			}
			
			int pSize=Integer.parseInt(pageSize);
			Map<String,Object> userMap = new LinkedHashMap<String,Object>();
			userMap.put("pageSize",pSize);
			userMap.put("current",current);
			userMap.put("rankId", rankId);
			String param  = DES3Util.encode(CommonUtil.getParam(userMap));
			
			userRankResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/userRank/queryUserRankList", param);
			userRankResultStr = java.net.URLDecoder.decode(DES3Util.decode(userRankResultStr),"UTF-8");
			
			if (null==userRankResultStr) {
				resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode",Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(loggerInfo+" javaservice接口返回结果:"+userRankResultStr);
			
			try {
					jsonObjRtn=JSONObject.fromObject(userRankResultStr);
			  	} catch (Exception e) {
					resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
					resultMap.put("rescode", Consts.ERROR_CODE);
					setResposeMsg(resultMap.toString(), out);
					logger.error(loggerInfo+" 返回异常",e);
					logger.info(loggerInfo+"  结束");
				    return;
			  	}
			
			if (!Consts.SUCCESS_CODE.equals(jsonObjRtn.getString("rescode"))) {
				jsonObjRtn.put("pageObject",null);
				setResposeMsg(jsonObjRtn.toString(), out);
				return;
			}
			
			String size = jsonObjRtn.getString("totalNum");
			List<JSONObject> userRanklistDetail =jsonObjRtn.getJSONArray("list");
			
			//封装pageObject 对象
			PageUtils pageObject = new PageUtils();
			if (null != userRanklistDetail && userRanklistDetail.size() != 0) {
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
