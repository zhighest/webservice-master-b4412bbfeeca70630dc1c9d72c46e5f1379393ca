package com.web.actions.weixin.giftPackage;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.web.util.StringUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

/**
 * 礼包
 * Created by haoyx on 2016/11/15.
 */
@Controller
@RequestMapping("/wxGiftPackage")
public class GiftPackageAction extends BaseAction {

    private static final Logger logger = Logger.getLogger(GiftPackageAction.class);


    /**
     * 礼包兑换
     * @param request
     * @param response
     * @param out
     */
    @RequestMapping(value = "/giftPackageExchange")
    public void giftPackageExchange(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        String loggerInfo="####礼包兑换接口";
        logger.info(loggerInfo+"开始 ####");
        String orderId = request.getParameter("orderId");
        String result;
        JSONObject resultMap = new JSONObject();
        if(StringUtils.isBlank(orderId)){
            resultMap.put("resmsg_cn","订单id为空！");
            resultMap.put("rescode", Consts.CHECK_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.info(loggerInfo+" orderId is null####");
            return;
        }
        logger.info(loggerInfo+" param orderId:"+orderId+"####");
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            String mobile = userSession.getMobile();//手机号(必填)
            if(userId==0||StringUtils.isBlank(mobile)){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            logger.info(loggerInfo+" param userId:"+userId+"|mobile:"+mobile+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("mobile",mobile);
            params.put("orderId",orderId);
            String param  = DES3Util.encode(CommonUtil.getParam(params));

            //礼包兑换
            String interfacePath = LoginRedirectUtil.interfacePath + "/giftPackage/giftPackageExchange";
            result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode",Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }
            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
            resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.error(loggerInfo+" error: 系统异常 ####",e);
            return;
        }

        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(result, out);
    }

    /**
     * 查询礼包列表
     * @param request
     * @param response
     * @param out
     */
    @RequestMapping(value = "/queryGiftPackageList")
    public void queryGiftPackageList(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        String loggerInfo="####查询礼包列表接口";
        logger.info(loggerInfo+"开始 ####");
        String result;
        JSONObject resultMap = new JSONObject();

//        String packageType = request.getParameter("packageType");
//        if(StringUtils.isBlank(packageType)){
//            resultMap.put("resmsg_cn","礼包类型为空！");
//            resultMap.put("rescode", Consts.CHECK_CODE);
//            setResposeMsg(resultMap.toString(), out);
//            logger.info(loggerInfo+" orderId is null####");
//            return;
//        }
//        logger.info(loggerInfo+" param packageType:"+packageType+"####");
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            String mobile = userSession.getMobile();//手机号(必填)
            if(userId==0||StringUtils.isBlank(mobile)){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            Integer current = 1;
            if(!StringUtil.isEmpty(request.getParameter(Consts.Page.PAGE_CURRENT_NAME))){
                current = Integer.valueOf(request.getParameter(Consts.Page.PAGE_CURRENT_NAME));
            }
            Integer pageSize = 10;
            if(!StringUtil.isEmpty(request.getParameter(Consts.Page.PAGE_SIZE_NAME))){
                pageSize = Integer.valueOf(request.getParameter(Consts.Page.PAGE_SIZE_NAME));
            }
            logger.info(loggerInfo+" param userId:"+userId+"|mobile:"+mobile+"|current:"+current+"|pageSize:"+pageSize+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("mobile",mobile);
//            params.put("packageType",packageType);
            params.put("current",current);
            params.put("pageSize",pageSize);
            String type=request.getParameter("type");
            if(StringUtils.isNotBlank(type)){
            params.put("type",type);
            }
            String param  = DES3Util.encode(CommonUtil.getParam(params));

            //礼包兑换
            String interfacePath = LoginRedirectUtil.interfacePath + "/giftPackage/queryGiftPackageList";
            result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode",Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }

            resultMap = JSONObject.fromObject(result);
            if(Consts.SUCCESS_CODE.equals(resultMap.get(Consts.RES_CODE))){

                int totalNum = resultMap.getInt(Consts.TOTAL_NUM);
                PageUtils pageObject = PageUtil.execsPage(current,
                        totalNum, 5, pageSize);
                resultMap.put(Consts.PAGE_OBJECT, pageObject);
            }

            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
            resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.error(loggerInfo+" error: 系统异常 ####",e);
            return;
        }

        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
    }

    /**
     * 查询礼包详情
     * @param request
     * @param response
     * @param out
     */
    @RequestMapping(value = "/queryGiftPackageGiftList")
    public void queryGiftPackageGiftList(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        String loggerInfo="####查询礼包礼品列表接口";
        logger.info(loggerInfo+"开始 ####");
        String packageId = request.getParameter("packageId");
        String result;
        JSONObject resultMap = new JSONObject();
        if(StringUtils.isBlank(packageId)){
            resultMap.put("resmsg_cn","礼包为空！");
            resultMap.put("rescode", Consts.CHECK_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.info(loggerInfo+" orderId is null####");
            return;
        }
        logger.info(loggerInfo+" param packageId:"+packageId+"####");
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            String mobile = userSession.getMobile();//手机号(必填)
            if(userId==0||StringUtils.isBlank(mobile)){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            Integer current = 1;
            if(!StringUtil.isEmpty(request.getParameter("current"))){
                current = Integer.valueOf(request.getParameter("current"));
            }
            Integer pageSize = 10;
            if(!StringUtil.isEmpty(request.getParameter("pageSize"))){
                pageSize = Integer.valueOf(request.getParameter("pageSize"));
            }
            logger.info(loggerInfo+" param userId:"+userId+"|mobile:"+mobile+"|current:"+current+"|pageSize:"+pageSize+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("mobile",mobile);
            params.put("packageId",packageId);
            params.put("current",current);
            params.put("pageSize",pageSize);
            String param  = DES3Util.encode(CommonUtil.getParam(params));

            //礼包兑换
            String interfacePath = LoginRedirectUtil.interfacePath + "/giftPackage/queryGiftPackageGiftList";
            result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode",Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }

            resultMap = JSONObject.fromObject(result);
            if(Consts.SUCCESS_CODE.equals(resultMap.get(Consts.RES_CODE))){

                int totalNum = resultMap.getInt(Consts.TOTAL_NUM);
                PageUtils pageObject = PageUtil.execsPage(current,
                        totalNum, 5, pageSize);
                resultMap.put(Consts.PAGE_OBJECT, pageObject);
            }

            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
            resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.error(loggerInfo+" error: 系统异常 ####",e);
            return;
        }

        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
    }
    
    /**
	 * 分页查询台数券
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getQuantityCouponList")	
	public void getQuantityCouponList(HttpServletRequest request, HttpServletResponse response ,PrintWriter out){
		String loggerInfo="####分页查询台数券接口";
        logger.info(loggerInfo+"开始 ####");
        JSONObject resultMap = new JSONObject();
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            if(userId==0){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            Integer current = 1;
            Integer pageSize = 10;
            if(!StringUtil.isEmpty(request.getParameter("current"))){
                current = Integer.valueOf(request.getParameter("current"));
            }
            if(!StringUtil.isEmpty(request.getParameter("pageSize"))){
                pageSize = Integer.valueOf(request.getParameter("pageSize"));
            }
            logger.info(loggerInfo+" param userId:"+userId+"|current:"+current+"|pageSize:"+pageSize+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("current",current);
            params.put("pageSize",pageSize);
            String param  = DES3Util.encode(CommonUtil.getParam(params));
            //查询台数券
            String interfacePath = LoginRedirectUtil.interfacePath + "/kcode/getQuantityCouponList";
            String result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            logger.info(loggerInfo+interfacePath + "result="+result);
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }
            resultMap = JSONObject.fromObject(result);
            if(Consts.SUCCESS_CODE.equals(resultMap.get(Consts.RES_CODE))){
                int totalNum = resultMap.getInt(Consts.TOTAL_NUM);
                PageUtils pageObject = PageUtil.execsPage(current, totalNum, 5, pageSize);
                resultMap.put(Consts.PAGE_OBJECT, pageObject);
            }
            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
        	  resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
              resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
              resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
              setResposeMsg(resultMap.toString(), out);
              logger.error(loggerInfo+" error: 系统异常 ####",e);
              return;
        }
        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
	}
	
	/**
	 * 分页查询台数券使用记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getQuantityCouponRecordList")	
	public void getQuantityCouponRecordList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String loggerInfo="####分页查询台数券使用记录接口";
        logger.info(loggerInfo+"开始 ####");
        JSONObject resultMap = new JSONObject();
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            if(userId==0){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            Integer current = 1;
            Integer pageSize = 10;
            if(!StringUtil.isEmpty(request.getParameter("current"))){
                current = Integer.valueOf(request.getParameter("current"));
            }
            if(!StringUtil.isEmpty(request.getParameter("pageSize"))){
                pageSize = Integer.valueOf(request.getParameter("pageSize"));
            }
            logger.info(loggerInfo+" param userId:"+userId+"|current:"+current+"|pageSize:"+pageSize+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("current",current);
            params.put("pageSize",pageSize);
            String param  = DES3Util.encode(CommonUtil.getParam(params));
            //查询台数券
            String interfacePath = LoginRedirectUtil.interfacePath + "/kcode/getQuantityCouponRecordList";
            String result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            logger.info(loggerInfo+interfacePath + "result="+result);
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }
            resultMap = JSONObject.fromObject(result);
            if(Consts.SUCCESS_CODE.equals(resultMap.get(Consts.RES_CODE))){
                int totalNum = resultMap.getInt(Consts.TOTAL_NUM);
                PageUtils pageObject = PageUtil.execsPage(current, totalNum, 5, pageSize);
                resultMap.put(Consts.PAGE_OBJECT, pageObject);
            }
            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
        	  resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
              resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
              resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
              setResposeMsg(resultMap.toString(), out);
              logger.error(loggerInfo+" error: 系统异常 ####",e);
              return;
        }
        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
	}
	
	/**
	 * 使用台数券
	 * @param request           请求
	 * @param response          响应
	 */
	@RequestMapping(value = "/useQuantityCoupon")
	public void useQuantityCoupon(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String loggerInfo="#### 使用加速券接口";
        logger.info(loggerInfo+"开始 ####");
        JSONObject resultMap = new JSONObject();
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            if(userId==0){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            String couponNo=request.getParameter("couponNo");
            if(StringUtil.isEmpty(couponNo)){
            	 logger.info(loggerInfo+"参数couponNo不能为空");
                 resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                 resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                 resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                 setResposeMsg(resultMap.toString(), out);
                 return;
            }
            logger.info(loggerInfo+" param userId:"+userId+"|couponNo:"+couponNo+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("couponNo",couponNo);
            String param  = DES3Util.encode(CommonUtil.getParam(params));
            //使用台数券
            String interfacePath = LoginRedirectUtil.interfacePath + "/kcode/useQuantityCoupon";
            String result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            logger.info(loggerInfo+interfacePath + "result="+result);
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }
            resultMap = JSONObject.fromObject(result);
            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
        	  resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
              resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
              resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
              setResposeMsg(resultMap.toString(), out);
              logger.error(loggerInfo+" error: 系统异常 ####",e);
              return;
        }
        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
	}
	
	/**
	 * 使用加速券
	 * @param request           请求
	 * @param response          响应
	 */
	@RequestMapping(value = "/useQuickCoupon")
	public void useQuickCoupon(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String loggerInfo="#### 使用加速券接口";
        logger.info(loggerInfo+"开始 ####");
        JSONObject resultMap = new JSONObject();
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            if(userId==0){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            String couponNo=request.getParameter("couponNo");
            String giftPackageOrderId = request.getParameter("giftPackageOrderId");
            if(StringUtil.isEmpty(couponNo)){
            	 logger.info(loggerInfo+"参数couponNo不能为空");
                 resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                 resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                 resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                 setResposeMsg(resultMap.toString(), out);
                 return;
            }
            if(StringUtil.isEmpty(giftPackageOrderId)){
           	 logger.info(loggerInfo+"参数giftPackageOrderId不能为空");
             resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
             resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
             resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
             setResposeMsg(resultMap.toString(), out);
             return;
            }
            logger.info(loggerInfo+" param userId:"+userId+"|couponNo:"+couponNo+"|giftPackageOrderId:"+giftPackageOrderId+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("couponNo",couponNo);
            params.put("giftPackageOrderId",giftPackageOrderId);
            String param  = DES3Util.encode(CommonUtil.getParam(params));
            //使用台数券
            String interfacePath = LoginRedirectUtil.interfacePath + "/kcode/useQuickCoupon";
            String result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            logger.info(loggerInfo+interfacePath + "result="+result);
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }
            resultMap = JSONObject.fromObject(result);
            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
        	  resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
              resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
              resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
              setResposeMsg(resultMap.toString(), out);
              logger.error(loggerInfo+" error: 系统异常 ####",e);
              return;
        }
        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
	}
	
	/**
	 *  查询加速券列表，可查以下3个情况：
		1，所有未使用的加速券列表
		2，所有已使用的加速券列表
		3，某礼包可用的加速券列表。
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getKcodeQuickCouponList")	
	public void getKcodeQuickCouponList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		String loggerInfo="####分页查询加速券列表接口";
        logger.info(loggerInfo+"开始 ####");
        JSONObject resultMap = new JSONObject();
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            if(userId==0){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            Integer current = 1;
            Integer pageSize = 10;
            if(!StringUtil.isEmpty(request.getParameter("current"))){
                current = Integer.valueOf(request.getParameter("current"));
            }
            if(!StringUtil.isEmpty(request.getParameter("pageSize"))){
                pageSize = Integer.valueOf(request.getParameter("pageSize"));
            }
			String status=request.getParameter("status");//状态  0未使用 1已使用 2删除
			String giftPackageOrderId = request.getParameter("giftPackageOrderId");
            logger.info(loggerInfo
            		+" param userId:"+userId
            		+"|current:"+current
            		+"|pageSize:"+pageSize
            		+"|status:"+status
            		+"|giftPackageOrderId:"+giftPackageOrderId
            		+"####");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("current",current);
            params.put("pageSize",pageSize);
            params.put("status",status);
            params.put("giftPackageOrderId",giftPackageOrderId);
            String param  = DES3Util.encode(CommonUtil.getParam(params));
            //查询台数券
            String interfacePath = LoginRedirectUtil.interfacePath + "/kcode/getKcodeQuickCouponList";
            String result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            logger.info(loggerInfo+interfacePath + "result="+result);
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
                resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
                resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }
            resultMap = JSONObject.fromObject(result);
            if(Consts.SUCCESS_CODE.equals(resultMap.get(Consts.RES_CODE))){
                int totalNum = resultMap.getInt(Consts.TOTAL_NUM);
                PageUtils pageObject = PageUtil.execsPage(current, totalNum, 5, pageSize);
                resultMap.put(Consts.PAGE_OBJECT, pageObject);
            }
            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
        	  resultMap.put(Consts.RES_MSG, Consts.ErrorMsg.MSG00001);
              resultMap.put(Consts.RES_MSG_CN, Consts.ERROR_DESC);
              resultMap.put(Consts.RES_CODE, Consts.ERROR_CODE);
              setResposeMsg(resultMap.toString(), out);
              logger.error(loggerInfo+" error: 系统异常 ####",e);
              return;
        }
        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
	}
	
	 /**
     * 查询礼包兑换计划
     * @param request
     * @param response
     * @param out
     */
    @RequestMapping(value = "/giftExchangePlan")
    public void queryGiftExchangePlan(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
        String loggerInfo="查询礼包兑换计划";
        logger.info(loggerInfo+"开始 ####");
        String orderId = request.getParameter("orderId");
        String result;
        JSONObject resultMap = new JSONObject();
        if(StringUtils.isBlank(orderId)){
            resultMap.put("resmsg_cn","礼包订单Id为空！");
            resultMap.put("rescode", Consts.CHECK_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.info(loggerInfo+" orderId is null####");
            return;
        }
        logger.info(loggerInfo+" param orderId:"+orderId+"####");
        try {
            UserSession userSession=this.getUserSession(request, response);
            int userId=userSession.getId();//用户ID(必填)
            String mobile = userSession.getMobile();//手机号(必填)
            if(userId==0||StringUtils.isBlank(mobile)){
                resultMap.put("resmsg_cn","您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return ;
            }
            logger.info(loggerInfo+" param userId:"+userId+"|mobile:"+mobile);
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("userId",userId);
            params.put("mobile",mobile);
            params.put("orderId",orderId);
            String param  = DES3Util.encode(CommonUtil.getParam(params));

            //礼包兑换计划
            String interfacePath = LoginRedirectUtil.interfacePath + "/giftPackage/giftExchangePlan";
            result = HttpRequestParam.sendGet(interfacePath, param);
            result = java.net.URLDecoder.decode(DES3Util.decode(result),"UTF-8");
            if (StringUtils.isBlank(result)) {
                logger.info(loggerInfo+interfacePath + "失败  ####");
                resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode",Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }

            resultMap = JSONObject.fromObject(result);

            logger.info(loggerInfo+" 返回结果:"+result+"####");
        }catch (Exception e){
            resultMap.put("resmsg_cn",Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.error(loggerInfo+" error: 系统异常 ####",e);
            return;
        }

        logger.info(loggerInfo+" 结束 ####");
        setResposeMsg(resultMap.toString(), out);
    }
    
    
	//查看帮助
	@RequestMapping(value = "/usedPagList")
	public String goUsedPagList(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/usedPagList";
	}
}
