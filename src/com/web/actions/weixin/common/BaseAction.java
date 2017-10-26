package com.web.actions.weixin.common;

import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseAction {

    private static final Log logger = LogFactory.getLog(BaseAction.class);

    //登录页面
    public static final String WEIXIN_LOGIN = "weixin/accout/login";
    //注册页面
    public static final String WEIXIN_REG = "weixin/accout/register";
    //设置密码页面
    public static final String WEIXIN_SETPASS = "weixin/accout/setExchangePassword";
    //修改密码页面
    public static final String WEIXIN_CHANGPASS = "weixin/accout/changeExchangePassword";

    //注册页面
    public static final String WEIXIN_REG_PASSWORD = "weixin/accout/register";
    //密码登录页面
    public static final String WEIXIN_LOGIN_PASSWORD = "weixin/passwordUpdate/passwordLogin";

    /**
     * 下面方法是取得项目路径的方法具体如下
     * request.getServerName() 通过这个方法可以得到主机的地址 例如：http://127.0.0.1
     * request.getContextPath() 通过这个方法可以得到项目名称 例如：javaservice
     *
     * @param request
     * @return
     */
    public String getProjetUrl(HttpServletRequest request) {
        String strBackUrl = "http://" + request.getServerName() + request.getContextPath();

        return strBackUrl;

    }

    /**
     * 获取验证码
     * 验证码类别：注册验证码(REGISTER), 重置登陆密码(FORGET_PASSWORD), 解绑手机(MODIFY_MOBILE),绑定手机(BINDING_MOBILE), 设置回款卡号(RECEPAY_CARD),
     * FINDREBACK_PASSWORD:找回登陆密码	MODIFY_CASHDRAW_MOBILE:修改提现密码	CASHDRAW:提现	其他情况为OTHER
     *
     * @param request
     * @return response
     * @throws Exception
     */
    public Map<String, String> getCaptcha(String phoneNum, String userId, String type, String token_id, String ipInfo) {
        logger.info("接收的参数为：phoneNum=" + phoneNum + "&type=" + type + "&userId=" + userId);

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("phoneNum", phoneNum);
        map.put("type", type);
        map.put("setupFlag", ConstantUtil.setup_Flag);
        map.put("userId", userId);
        map.put("blackBox", token_id);
        map.put("ipInfo", ipInfo);
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }


//		String resultMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/judgeCaptchaTime", param);
//		try {
//			resultMsg1 = java.net.URLDecoder.decode(DES3Util.decode(resultMsg1),"UTF-8");
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.info("发送解密失败:" + e.getMessage());
//			e.printStackTrace();
//		}
        Map<String, String> responseMap = new HashMap<String, String>();
//		
//		JSONObject jsonObject1 = JSONObject.fromObject(resultMsg1);
//		if (Consts.SUCCESS_CODE.equals(jsonObject1.getString("rescode"))) {
        String resultMsg2 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/captcha", param);
        try {
            resultMsg2 = java.net.URLDecoder.decode(DES3Util.decode(resultMsg2),
                    "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info("发送解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonObject2 = JSONObject.fromObject(resultMsg2);
        if (Consts.SUCCESS_CODE.equals(jsonObject2.getString("rescode"))) {
            logger.info("发送成功！");
            responseMap.put("rescode", Consts.SUCCESS_CODE);
        } else {
            logger.info("发送失败！");
            responseMap.put("rescode", jsonObject2.getString("rescode"));
            responseMap.put("resmsg_cn", jsonObject2.getString("resmsg_cn"));
        }

        logger.info(LogUtil.getEnd("getCaptcha", "方法结束运行", "BaseAction"));
        return responseMap;
//		} else {
//			logger.info("发送失败！");
//			responseMap.put("rescode",jsonObject1.getString("rescode"));
//			responseMap.put("resmsg_cn",jsonObject1.getString("resmsg_cn"));
//		}
//		logger.info(LogUtil.getEnd("getCaptcha", "方法结束运行", "BaseAction"));
//		return responseMap;

    }


    /**
     * 输入手机号码、手机验证码、来源标志setupFlag，来检验验证码是否正解
     * phoneNum 手机号码
     * checkCode 手机验证码
     * setupFlag 来源标志
     *
     * @param request
     * @param response
     */
    public Map<String, String> getCheckAllCode(HttpServletRequest request, HttpServletResponse response) {
        String phoneNum = request.getParameter("phoneNum");
        String checkCode = request.getParameter("checkCode");
        //String setupFlag = request.getParameter("setupFlag");

        logger.info(LogUtil.getStart("getCheckAllCode", "方法开始执行", "BaseAction", getProjetUrl(request)));
        logger.info("接收的参数为：phoneNum=" + phoneNum + "&setupFlag=1&checkCode=" + checkCode);

        //String param = "phoneNum="+"18701355386"+"&setupFlag=1&checkCode=470562";

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("phoneNum", phoneNum);
        map.put("setupFlag", ConstantUtil.setup_Flag);
        map.put("checkCode", checkCode);
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            System.out.println("发送成功！");
        }
        Map<String, String> resultmap = new HashMap<String, String>();
        resultmap.put("rescode", jsonObject.getString("rescode"));
        resultmap.put("message", jsonObject.getString("resmsg_cn"));
        logger.info(LogUtil.getEnd("getCheckAllCode", "方法结束运行", "BaseAction"));
        return resultmap;
    }

    public void outWriter(HttpServletResponse response, String result) {
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("outWriter异常" + e.getMessage());
        }
    }

    /**
     * 将返回结果传递到页面中的共有方法
     *
     * @param resultMsg
     */
    public void setResposeMsg(String resultMsg, PrintWriter out) {

        /**
         * 向页面返回值 Consts.SUCCESS_CODE 为正确的， 其它为错误的
         */
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            // System.out.println("登陆成功！");
            String result = "";
            try {
                result = new String(jsonObject.toString().getBytes("utf-8"),
                        "iso8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.write(result);
            out.flush();
            out.close();
        } else {
//			Map<String, String> resultMap = new HashMap<String, String>();
//			resultMap.put("rescode", jsonObject.getString("rescode"));
//			resultMap.put("resmsg_cn", jsonObject.getString("resmsg_cn"));
//			JSONObject jsonObject1 = JSONObject.fromObject(resultMap);
            String result = "";
            try {
                result = new String(jsonObject.toString().getBytes("utf-8"), "iso8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.write(result);
            out.flush();
            out.close();
        }
    }

    /**
     * 将返回结果传递到页面中的共有方法
     *
     * @param resultMsg
     * @author wangenlai
     */
    public void setResposeMap(Map resultMag, PrintWriter out) {
        /**
         * 向页面返回值
         * Consts.SUCCESS_CODE 为正确的，
         * 其它为错误的
         */
        JSONObject jsonObject = JSONObject.fromObject(resultMag);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            //System.out.println("登陆成功！");
            String result = "";
            try {
                result = new String(jsonObject.toString().getBytes("utf-8"), "iso8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.write(result);
            out.flush();
            out.close();
        } else {
//			   Map<String,String> resultMap = new HashMap<String,String>();
//			   resultMap.put("rescode", "00002");
            JSONObject jsonObject1 = JSONObject.fromObject(resultMag);
            String result = "";
            try {
                result = new String(jsonObject1.toString().getBytes("utf-8"), "iso8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            out.write(result);
            out.flush();
            out.close();
        }
    }


    /**
     * @param jsonObject
     * @param out
     * @Description: 将返回结果返回至页面中的公共方法
     * @author ZuoJun
     * @date 2016-6-12 11:46:39
     */
    public void setResposeJson(JSONObject jsonObject, PrintWriter out) {

        String result = "";
        try {

            result = new String(jsonObject.toString().getBytes("utf-8"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.info("BaseAction setResposeJson() 出现异常 " + e.getMessage());
            e.printStackTrace();
        }

        out.write(result);
        out.flush();
        out.close();
    }

//	public String autoOut(HttpServletRequest request){
//		UserSession us = null;
//		String logout="";
//		//获取Session
//		try {
//			us = UserCookieUtil.getUser(request);
//		} catch (NoSessionException e) {
//			us = null;
//			logout = "redirect:"+"/wxuser/goLogin";
//		}
//		
//		if(us == null){
//			logout = "redirect:"+"/wxuser/goLogin";
//		}
//		// return "redirect:"+"/user+goLogin";
//		return logout;
//	}

    public UserSession getUserSession(HttpServletRequest request, HttpServletResponse response) throws Exception {


        UserSession us = UserCookieUtil.getUser(request);
        if (null == us || null == us.getId() || us.getMobile() == null) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
            String userId = userInfoMap.get("userId") == null ? "0" : userInfoMap.get("userId");
            String mobile = String.valueOf(userInfoMap.get("mobile"));
            us = new UserSession();
            us.setMobile(mobile);
            us.setId(Integer.parseInt(userId));
        }

        return us;

    }
    
    /**
     * 调用shop系统获取公众账号信息
     * @param acctName 账号名称（预定义）
     * @return
     */
    public Map<String, String> getWxPubAcctInfo(String acctName) {
    	String logInfo = "调用shop系统获取公众账号信息";
    	
        logger.info(logInfo+"接收的参数为：acctName=" + acctName);

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("acctName", acctName);
        String param = CommonUtil.getParam(map);
        Map<String, String> responseMap = new HashMap<String, String>();
        responseMap.put("rescode", Consts.ERROR_CODE);
        responseMap.put("resmsg_cn", Consts.ERROR_DESC);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info(logInfo+"加密失败:" + e.getMessage());
            e.printStackTrace();
            return responseMap;
        }


        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePathForShop + "/wxpush/getPushPubAcctInfo", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                    "UTF-8");
        } catch (Exception e) {
            logger.info(logInfo+"发送结果解密失败:" + e.getMessage());
            e.printStackTrace();
            return responseMap;
        }

        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            logger.info("发送成功！");
            responseMap.put("rescode", Consts.SUCCESS_CODE);
            responseMap.put("acctAppid", jsonObject.getString("acctAppid"));
            responseMap.put("acctAppsecret", jsonObject.getString("acctAppsecret"));
            responseMap.put("acctHosturl", jsonObject.getString("acctHosturl"));
            responseMap.put("acctNo", jsonObject.getString("acctNo"));
            responseMap.put("acctName", jsonObject.getString("acctName"));
        }

        logger.info(LogUtil.getEnd("getWxPubAcctInfo", "方法结束运行", "BaseAction"));
        return responseMap;

    }
}
