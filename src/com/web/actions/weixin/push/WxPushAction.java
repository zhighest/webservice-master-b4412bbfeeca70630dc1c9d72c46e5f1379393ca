package com.web.actions.weixin.push;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.actions.weixin.common.BaseAction;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.StringUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @Title: 微信推送
 * @Description: 微信推送相关接口
 * @author zhouyajie  
 * @date 2017/09/04
 */
@Controller
@RequestMapping("/wxPush")
public class WxPushAction extends BaseAction {

    private static final Log logger = LogFactory.getLog(WxPushAction.class);
    
    /**
	 * 跳转活动分享页
	 * @return
	 */
	@RequestMapping(value = "/goBindingPage", method = RequestMethod.GET)
	public String goBindingPage(HttpServletRequest request,HttpServletResponse res) {
		String logInfo = "/wxPushAction/goBindingPage-";
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		//查询是否已绑定，已绑定跳转绑定成功页，未绑定跳转绑定页面
		//获取用户微信openId
		String  mediaUid = "";
		Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
		if(null == mediaUidMap){
			logger.info(logInfo + "获取微信openId失败！");
			return "weixin/bindingPhone/bindingPhone";
		}
		mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
        if(StringUtil.isEmpty(mediaUid)){
        	logger.info(logInfo + "获取微信openId失败！");
			return "weixin/bindingPhone/bindingPhone";
        }
        
        try {
        	Map<String, Object> map = new LinkedHashMap<String, Object>();
        	map.put("openId", mediaUid);
        	String param = CommonUtil.getParam(map);
        	
            param = DES3Util.encode(param);
            String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/wxpush/checkWxIsBingding", param);
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
            		"UTF-8");
            
            JSONObject jsonObject = JSONObject.fromObject(resultMsg);
            if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))&&"Y".equals(jsonObject.getString("isBingding"))) {
            	logger.info(logInfo + "该微信号已绑定！");
            	return "weixin/bindingPhone/success";
            }
        } catch (Exception e) {
            logger.info(logInfo+"查询shopservice获取微信号是否绑定失败:" + e.getMessage());
            e.printStackTrace();
        }

		return "weixin/bindingPhone/bindingPhone";
	} 
	
	/**
	 * 跳转活动分享成功页
	 * @return
	 */
	@RequestMapping(value = "/goBindingSuccessPage", method = RequestMethod.GET)
	public String goBindingSuccessPage(HttpServletRequest request,HttpServletResponse res) {
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/bindingPhone/success";
	} 
	

    /**
     * 获取所有省份
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getAllProvince")
    public void getAllProvince(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logInfo = "/wxPushAction/getAllProvince-";
        JSONObject resultMap = new JSONObject();
        try {
            String reqString = request.getQueryString();
            logger.info(logInfo + reqString);
            String result = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/options/getAllProvinceNotEncrypt", reqString);
            if (StringUtils.isBlank(result)) {
                logger.info(logInfo + "调用javaservice返回为空  ####");
                resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode", Consts.ERROR_CODE);
                outWriter(response, resultMap.toString());
                return;
            }
            logger.info(logInfo + " 返回结果:" + result + "####");
            outWriter(response, result);
        } catch (Exception e) {
            resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            outWriter(response, resultMap.toString());
        }
    }
    
    /**
     * 根据省份id获取城市列表
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getCityListByProvinceId")
    public void getCityListByProvinceId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logInfo = "/wxPushAction/getCityListByProvinceId-";
        JSONObject resultMap = new JSONObject();
        try {
            String reqString = request.getQueryString();
            logger.info(logInfo + reqString);
            String result = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/options/getCityListByProvinceIdNotEncrypt", reqString);
            if (StringUtils.isBlank(result)) {
                logger.info(logInfo + "调用javaservice返回为空  ####");
                resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode", Consts.ERROR_CODE);
                outWriter(response, resultMap.toString());
                return;
            }
            logger.info(logInfo + " 返回结果:" + result + "####");
            outWriter(response, result);
        } catch (Exception e) {
            resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            outWriter(response, resultMap.toString());
        }
    }
    
    /**
     * 获取验证码
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCaptcha")
    @ResponseBody
	public Map<String, Object> getCaptcha(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception {
        String logInfo = "/wxPushAction/getCaptcha-";
        
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		String phoneNum = request.getParameter("phoneNum");

		String token_id = (String) request.getSession().getAttribute("token_id");
		String ipInfo = CommonUtil.getClientIP(request);
		logger.info(logInfo+"-phoneNum:" + phoneNum + ",token_id=" + token_id + ",ipInfo=" + ipInfo);

		Map<String, String> map = getCaptcha(phoneNum, phoneNum, "KCODECAPTCH", token_id, ipInfo);
		if (map.get("rescode").equals(Consts.SUCCESS_CODE)) {
			modelMap.put("rescode", Consts.SUCCESS_CODE);
			modelMap.put("resmsg_cn", "");
			modelMap.put("success", "true");
		} else {
			modelMap.put("rescode", Consts.CHECK_CODE);
			modelMap.put("resmsg_cn", map.get("resmsg_cn"));
			modelMap.put("error", "true");
		}
		logger.info(logInfo+"-result:" + modelMap);
		return modelMap;
    }

    /**
     * 手机绑定微信推送公众号
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/phoneBinding")
    public void phoneBinding(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logInfo = "/wxPushAction/phoneBinding-";
        JSONObject resultMap = new JSONObject();
        try {
        	String ua = request.getHeader("user-agent").toLowerCase();
    		if (ua.indexOf("micromessenger") < 0) {// 微信浏览器
    			resultMap.put("resmsg", "请使用微信浏览器进行绑定操作！");
                resultMap.put("rescode", Consts.ERROR_CODE);
                outWriter(response, resultMap.toString());
                return;
    		}
    		//获取用户微信openId
			Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
			String  mediaUid = "";
			if(null == mediaUidMap){
				resultMap.put("resmsg", "获取微信openId失败！");
				resultMap.put("rescode", Consts.ERROR_CODE);
				outWriter(response, resultMap.toString());
				return;
			}
			mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));
        	
            if(StringUtil.isEmpty(mediaUid)){
            	resultMap.put("resmsg", "获取微信openId失败！");
				resultMap.put("rescode", Consts.ERROR_CODE);
				outWriter(response, resultMap.toString());
				return;
            }
            
            String reqString = request.getQueryString();
            reqString += "&openId="+mediaUid;
            logger.info(logInfo + reqString);
            String result = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxPush/phoneBinding", reqString);
            if (StringUtils.isBlank(result)) {
                logger.info(logInfo + "调用javaservice返回为空  ####");
                resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode", Consts.ERROR_CODE);
                outWriter(response, resultMap.toString());
                return;
            }
            logger.info(logInfo + " 返回结果:" + result + "####");
            outWriter(response, result);
        } catch (Exception e) {
            resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            outWriter(response, resultMap.toString());
        }
    }
    
    public static void main(String[] args) {
    	String yuanUrl = "/wxPush/goBindingPage";
    	yuanUrl = Base64.encodeBase64String(yuanUrl.getBytes());
    	logger.info("######yuanUrl串加密："+yuanUrl);
    	
    	String actionUrl = "";
		try {
			actionUrl = new String(Base64.decodeBase64("L3d4dXNlci9nb0xvZ2lu"),"UTF-8");
		} catch (Exception e) {
			logger.error("[跳转作用域]DES解密失败");
			e.printStackTrace();
		}
		logger.info("######actionUrl原来的串："+actionUrl);
	}
    
}
