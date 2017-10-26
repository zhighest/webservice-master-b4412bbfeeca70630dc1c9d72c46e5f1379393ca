package com.web.actions.weixin.accout;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.common.WeiXinUserDto;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.JsonUtil;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.StringUtil;
import com.web.util.Tools;
import com.web.util.UserCookieUtil;
import com.web.util.llpay.Validator;
import com.web.util.weixinAbout.WeixinRateUrlConfig;
import com.web.util.weixinAbout.WeixinRquestUtil;
import com.web.util.yundun.YunDunCaptcha;

import net.sf.json.JSONObject;

/**
 * ClassName: WeiXinUserAction
 *
 * @author qibo
 * @Description: 用户相关信息
 * @date 2015-9-7 下午3:36:35
 */
@Controller
@RequestMapping("/wxuser")

public class WeiXinUserAction extends BaseAction implements Serializable {

    private static final long serialVersionUID = -4660733781531410744L;
    private static final Log logger = LogFactory.getLog(WeiXinUserAction.class);

    /**
     * 跳转到登陆页面
     * wxproduct/goDemandOrderConfirmation?id=3086&abc=1&encrypt_key=
     *
     * @return
     */
    @RequestMapping(value = "/goLogin", method = RequestMethod.GET)
    public String goLogin(String parm, HttpServletRequest request, HttpServletResponse response, Model model) {
        WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
        if (null == userDto) {
            userDto = new WeiXinUserDto();
        }
        String backUrl = "";

        logger.info("######################goLogin获取的参数：[" + parm + "]");
        if (parm != null && parm.length() > 0) {
            try {
                parm = java.net.URLDecoder.decode(DES3Util.decode(parm.replaceAll(" ", "+")), "UTF-8");
            } catch (Exception e) {
                logger.info("检察用户功能解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject jsonObj = JSONObject.fromObject(parm);
            backUrl = jsonObj.getString("backUrl");

            String invitationCd = jsonObj.getString("invitationCd");
            if (invitationCd != null && invitationCd.length() > 0) {
                userDto.setInvitationCd(invitationCd);
            }
        } else {
            //暂定默认跳转到 实名认证页面
            backUrl = "/wxtrade/goMyAsset";
        }
        request.getSession().setAttribute("backUrl", backUrl);
        request.getSession().setAttribute("userDto", userDto);
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        //已绑定用户如果输入手机号登录，判断只能登录绑定的手机号
        Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
        String mobile = userInfoMap.get("mobile");
        if (null != userInfoMap && !"".equalsIgnoreCase(mobile) && null != mobile) {
            return "redirect:" + backUrl;
        }
        return WEIXIN_LOGIN;
    }

    /**
     * 登陆
     *
     * @param request 接收网站传过来的数据
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String phoneNum, String checkCode, String password, String loginFlag, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session) {

        
        String token_id = (String) request.getSession().getAttribute("token_id");
        logger.info("网站登录token_id=" + token_id);
        //分享验签机制
        WeixinRquestUtil.getSignature(request);
        logger.info("登录画面传过来的值:phoneNum=" + phoneNum + "checkCode=" + checkCode + "password=");
        //TODO
        //一、
        //1、判断当前微信唯一标示openid是否已绑定过平台用户userid,如果已绑定提示用户已绑定平台用户
        //2、如果未绑定判断用户是否已注册（1）已注册未实名用户，做实名认证后实现绑定(2)未注册说明未实名认证，做实名认证 ，实现绑定功能(3)对于已经实名认证的用户登录，直接实现绑定功能
        //二.判断手机号是否已被微信绑定，如果该手机号已绑定通知用户该手机号已绑定其他微信用户

        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            userDto = new WeiXinUserDto();
        }
        //设置smDeviceId
        request.setAttribute("smDeviceId", Tools.getCookieValueByName(request, "smDeviceId"));
        String mobileNumber = request.getParameter("mobileNumber") == null ? "" : request.getParameter("mobileNumber");
        String channel = request.getParameter("channel") == null ? "" : request.getParameter("channel");
        request.setAttribute("mobileNumber", mobileNumber);
        request.setAttribute("channel", channel);
        //验证码进行拦截
        if (null == loginFlag || "".equalsIgnoreCase(loginFlag) || !"1".equalsIgnoreCase(loginFlag)) {
            if (null == checkCode || "".equals(checkCode)) {
                request.setAttribute("message", "请输入短信验证码！");
                return WEIXIN_LOGIN;
            }
            userDto.setPhoneNum(phoneNum);
            userDto.setCheckCode(checkCode);

            Map<String, Object> userMap = new LinkedHashMap<String, Object>();
            userMap.put("phoneNum", phoneNum);
            userMap.put("setupFlag", "1");
            userMap.put("checkCode", checkCode);
            String param = CommonUtil.getParam(userMap);
            try {
                param = DES3Util.encode(param);
            } catch (Exception e) {
                logger.info("检察用户加密失败:" + e.getMessage());
                e.printStackTrace();
            }

            //验证码检查
            String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
            try {
                checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
            } catch (Exception e) {
                logger.info("检察用户功能解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
            if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
                request.setAttribute("message", checkCaptchatObject.getString("resmsg_cn"));
                return WEIXIN_LOGIN;
            }

            //已绑定用户如果输入手机号登录，判断只能登录绑定的手机号
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
            String mobile = userInfoMap.get("mobile");
            if (null != userInfoMap && !"".equalsIgnoreCase(mobile) && null != mobile) {
                if (!phoneNum.equalsIgnoreCase(mobile)) {
                    request.setAttribute("message", "请登录已绑定手机号");
                    return WEIXIN_LOGIN;
                }
            }
            //未绑定用户如果输入手机号登录，判断当前手机号是否已被绑定其他微信
            Map<String, String> mediaUidMap = WeiXinTriggerAction.getWeixinUidBindingByMobile(phoneNum, request, response);
            String mediaUid = mediaUidMap.get("mediaUid");
            if (null != mediaUidMap && !"".equalsIgnoreCase(mediaUid) && null != mediaUid) {
                Map mediaUidMap1 = UserCookieUtil.getWeixinOpenid(request);
                String mediaUidInfo = "";
                if (null != mediaUidMap1) {
                    mediaUidInfo = String.valueOf(mediaUidMap1.get("mediaUid"));
                } else {
                    try {
                        request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!mediaUidInfo.equalsIgnoreCase(mediaUid)) {
                    request.setAttribute("message", "您输入的手机号已绑定其他微信号");
                    return WEIXIN_LOGIN;
                }
            }

            // 调用service接口
            String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
            try {
                resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
            } catch (Exception e) {
                logger.info("检察用户功能解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject jsonObject = JSONObject.fromObject(resultMsg);
            JSONObject user = jsonObject.getJSONObject("user");
            //用户存在
            if (user != null && !"null".equals(user.toString())) {
                String passwordCash = user.getString("password_cash");
                //非首次登录
                if (passwordCash != null && passwordCash.length() > 0) {
                    logger.info(LogUtil.getStart("goLogin", "方法开始执行", "LoginAction", getProjetUrl(request)));

                    Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
                    loginMap.put("userName", userDto.getPhoneNum());
                    loginMap.put("setupFlag", "1");
                    loginMap.put("type", "mobile");
                    loginMap.put("checkCode", userDto.getCheckCode());
                    loginMap.put("blackBox", token_id);
                    loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
                    String loginParam = CommonUtil.getParam(loginMap);
                    try {
                        loginParam = DES3Util.encode(loginParam);
                    } catch (Exception e) {
                        logger.info("登陆加密失败:" + e.getMessage());
                        e.printStackTrace();
                    }

                    String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login", loginParam);
                    logger.info("接受的javaservice login url：[" + LoginRedirectUtil.interfacePath + "/wxuser/login" + "]");
                    logger.info("接受登录参数：[" + loginMsg + "]");
                    try {
                        loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        logger.info("登陆解密失败:" + e.getMessage());
                        e.printStackTrace();
                    }

                    logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));

                    JSONObject loginObject = JSONObject.fromObject(loginMsg);
                    if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
                        System.out.println("登陆成功！");
                        UserSession us = new UserSession();
                        us.setId(Integer.valueOf(loginObject.getString("userId")));
                        us.setUsername(loginObject.getString("userName"));
                        us.setMobile(loginObject.getString("mobile"));
                        //登录
                        UserCookieUtil.putUser(request, response, us);

                        String backUrl = (String) session.getAttribute("backUrl");
                        if (backUrl == null) {
                            backUrl = "/wxtrade/goMyAsset";
                        }
                        //绑定微信操作
                        WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);

                        session.removeAttribute("userDto");
                        
                        boolean needUpdatePassword = loginObject.getBoolean("needUpdatePassword");
                        if(needUpdatePassword){
                        	session.setAttribute("passwordMessage",loginObject.getString("passwordMessage"));
                        }
                        session.setAttribute("needUpdatePassword", needUpdatePassword);

                        logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
                        return "redirect:" + backUrl;
                    } else {
                        request.setAttribute("message", jsonObject.getString("resmsg_cn"));
                        return WEIXIN_LOGIN;
                    }
                } else {
                    //绑定微信操作
                    WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);

                    return "redirect:goSetExchangePasswordByLogin";
                }
                //不存在的时候跳转到注册页面
            } else {
                request.setAttribute("message", "您还没有注册，请先进行注册！");
                return WEIXIN_LOGIN;
            }
        }
        //密码进行拦截
        if (null != loginFlag && !"".equalsIgnoreCase(loginFlag) && "1".equalsIgnoreCase(loginFlag)) {
            //图片验证码
            String verifycode = request.getParameter("verifycode");
            String randomCode = String.valueOf(session.getAttribute("randomCode"));
            session.removeAttribute("randomCode");

            session.removeAttribute("userDto");
            session.removeAttribute("password");
            userDto.setPhoneNum(phoneNum);
            userDto.setCheckCode(checkCode);
            request.getSession().setAttribute("userDto", userDto);
            request.getSession().setAttribute("password", password);
            /**
             * 验证码进行拦截
             */
            if (StringUtils.isBlank(verifycode)) {
                request.setAttribute("message", "请输入图形验证码！");
                request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                return WEIXIN_LOGIN_PASSWORD;
            }
            if (!randomCode.toUpperCase().equals(verifycode.toUpperCase())) {
                request.setAttribute("message", "请输入正确的图形验证码！");
                request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                return WEIXIN_LOGIN_PASSWORD;
            }
            if (null == password || "".equalsIgnoreCase(password)) {
                request.setAttribute("message", "请输入登录密码！");
                request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                return WEIXIN_LOGIN_PASSWORD;
            }

            Map<String, Object> userMap = new LinkedHashMap<String, Object>();
            userMap.put("phoneNum", phoneNum);
            userMap.put("setupFlag", "1");
            userMap.put("password", password);
            String param = CommonUtil.getParam(userMap);
            try {
                param = DES3Util.encode(param);
            } catch (Exception e) {
                logger.info("检察用户加密失败:" + e.getMessage());
                e.printStackTrace();
            }
            //密码检查
            String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkPassword", param);
            try {
                checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
            } catch (Exception e) {
                logger.info("检察用户功能解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
            if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
                request.setAttribute("message", checkCaptchatObject.getString("resmsg_cn"));
                request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                return WEIXIN_LOGIN_PASSWORD;
            }

            //已绑定用户如果输入手机号登录，判断只能登录绑定的手机号
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
            String mobile = userInfoMap.get("mobile");
            if (null != userInfoMap && !"".equalsIgnoreCase(mobile) && null != mobile) {
                if (!phoneNum.equalsIgnoreCase(mobile)) {
                    request.setAttribute("message", "请登录已绑定手机号");
                    request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                    return WEIXIN_LOGIN_PASSWORD;
                }
            }

            //未绑定用户如果输入手机号登录，判断当前手机号是否已被绑定其他微信
            Map<String, String> mediaUidMap = WeiXinTriggerAction.getWeixinUidBindingByMobile(phoneNum, request, response);
            String mediaUid = mediaUidMap.get("mediaUid");

            if (null != mediaUidMap && !"".equalsIgnoreCase(mediaUid) && null != mediaUid) {
                Map mediaUidMap1 = UserCookieUtil.getWeixinOpenid(request);
                String mediaUidInfo = "";
                if (null != mediaUidMap1) {
                    mediaUidInfo = String.valueOf(mediaUidMap1.get("mediaUid"));
                } else {
                    try {
                        request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu").forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (!mediaUidInfo.equalsIgnoreCase(mediaUid)) {
                    request.setAttribute("message", "您输入的手机号已绑定其他微信号");
                    return WEIXIN_LOGIN;
                }
            }


            // 调用service接口
            String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
            try {
                resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
            } catch (Exception e) {
                logger.info("检察用户功能解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject jsonObject = JSONObject.fromObject(resultMsg);
            JSONObject user = jsonObject.getJSONObject("user");
            //用户存在
            if (user != null && !"null".equals(user.toString())) {
                String passwordCash = user.getString("password_cash");
                //非首次登录
                if (passwordCash != null && passwordCash.length() > 0) {
                    logger.info(LogUtil.getStart("goLogin", "方法开始执行", "LoginAction", getProjetUrl(request)));

                    Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
                    loginMap.put("userName", userDto.getPhoneNum());
                    loginMap.put("setupFlag", "1");
                    loginMap.put("type", "mobile");
//					loginMap.put("checkCode", userDto.getCheckCode());
                    loginMap.put("password", password);
                    loginMap.put("loginFlag", "1");
                    loginMap.put("blackBox", token_id);
                    loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
                    String loginParam = CommonUtil.getParam(loginMap);
                    try {
                        loginParam = DES3Util.encode(loginParam);
                    } catch (Exception e) {
                        logger.info("登陆加密失败:" + e.getMessage());
                        e.printStackTrace();
                    }

                    String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login", loginParam);
                    logger.info("接受的javaservice login url：[" + LoginRedirectUtil.interfacePath + "/wxuser/login" + "]");
                    logger.info("接受登录参数：[" + loginMsg + "]");
                    try {
                        loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        logger.info("登陆解密失败:" + e.getMessage());
                        e.printStackTrace();
                    }

                    logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));

                    JSONObject loginObject = JSONObject.fromObject(loginMsg);
                    if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
                        System.out.println("登陆成功！");
                        UserSession us = new UserSession();
                        us.setId(Integer.valueOf(loginObject.getString("userId")));
                        us.setUsername(loginObject.getString("userName"));
                        us.setMobile(loginObject.getString("mobile"));
                        //登录
                        UserCookieUtil.putUser(request, response, us);

                        String backUrl = (String) session.getAttribute("backUrl");
                        if (backUrl == null) {
                            backUrl = "/wxtrade/goMyAsset";
                        }
                        //绑定微信操作
                        WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);

                        session.removeAttribute("userDto");
                        
                        boolean needUpdatePassword = loginObject.getBoolean("needUpdatePassword");
                        if(needUpdatePassword){
                        	session.setAttribute("passwordMessage",loginObject.getString("passwordMessage"));
                        }
                        session.setAttribute("needUpdatePassword", needUpdatePassword);
                        
                        session.removeAttribute("channel");
                        session.removeAttribute("mobileNumber");

                        logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
                        return "redirect:" + backUrl;
                    } else {
                        request.setAttribute("message", loginObject.getString("resmsg_cn"));
                        request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                        return WEIXIN_LOGIN_PASSWORD;
                    }
                } else {
                    //绑定微信操作
                    WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);

                    return "redirect:goSetExchangePasswordByLogin_Reg";
                }
                //不存在的时候跳转到注册页面
            } else {
                return "redirect:" + "/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu";
            }
        }
        request.setAttribute("message", "该用户非正常登录！！！");
        return WEIXIN_LOGIN;
    }

    /**
     * 跳转到设置交易密码页面（注册）原密码盘页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/goSetExchangePasswordByReg", method = RequestMethod.GET)
    public String goSetExchangePasswordByReg(String invitationCd, HttpServletRequest request, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "redirect:goLogin";
        }
        request.setAttribute("actionName", "register");
        //1:修改密码 2：设置密码
        request.setAttribute("passwordState", 2);
        return WEIXIN_SETPASS;
    }


    /**
     * 跳转到设置交易密码页面(登录) 原密码盘页面
     *
     * @return
     */
    @RequestMapping(value = "/goSetExchangePasswordByLogin")
    public String goSetExchangePasswordByLogin(HttpServletRequest request, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        request.setAttribute("actionName", "setExchangePassword");
        //1:修改密码 2：设置密码
        request.setAttribute("passwordState", 2);
        return WEIXIN_SETPASS;
    }

    /**
     * 设置交易密码
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/setExchangePassword", method = RequestMethod.POST)
    public String setExchangePassword(String phoneNum, String passwordCash,
                                      HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        
		String token_id = (String)request.getSession().getAttribute("token_id");
		logger.info("/wxuser/setExchangePassword-token_id="+token_id);

        logger.info("=======进入setExchangePassword方法");
        //phoneNum取不到时跳回登录页面
        if (phoneNum == null || phoneNum.length() == 0) {
            return "redirect:goLogin";
        }
        //交易密码验证
        if (passwordCash == null || passwordCash.length() == 0) {
            request.setAttribute("message", "请输入交易密码！");
            return WEIXIN_SETPASS;
        }
        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "redirect:goLogin";
        }
        userDto.setPasswordCash(passwordCash);

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userName", phoneNum);
        map.put("passwordCash", passwordCash);
        map.put("checkCode", userDto.getCheckCode());
        map.put("setupFlag", "1");
        map.put("type", "mobile");
        map.put("blackBox", token_id);
        map.put("ipInfo",  CommonUtil.getClientIP(request));
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/resetPassword", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            logger.info("交易密码设置成功!");
        } else {
            logger.info("交易密码设置失败!");
            return WEIXIN_CHANGPASS;
        }
        UserSession userSession = UserCookieUtil.getUser(request);
        String backUrl = (String) session.getAttribute("backUrl");
        if (userSession == null) {
            //登录
            String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login", param);
            try {
                loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
            } catch (Exception e) {
                logger.info("登陆解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject loginObject = JSONObject.fromObject(loginMsg);
            if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
                System.out.println("登陆成功！");
                UserSession us = new UserSession();
                us.setId(Integer.valueOf(loginObject.getString("userId")));
                us.setUsername(loginObject.getString("userName"));
                us.setMobile(loginObject.getString("mobile"));
                //登录
                UserCookieUtil.putUser(request, response, us);

                if (backUrl == null || backUrl.length() == 0) {
                    backUrl = "/wxtrade/goMyAsset";
                }

                //绑定微信操作
                WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);
                session.removeAttribute("userDto");
                return "redirect:" + backUrl;
            } else {
                request.setAttribute("message", jsonObject.getString("resmsg_cn"));
                return WEIXIN_SETPASS;
            }
        } else {
            session.removeAttribute("backUrl");
            session.removeAttribute("userDto");
            return "redirect:" + backUrl;
        }
    }

    /**
     * 用户注册
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String passwordCash, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
        if (userDto == null) {
            return "redirect:goLogin";
        }
        userDto.setPasswordCash(passwordCash);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (userDto.getPhoneNum() == null || "".equals(userDto.getPhoneNum())) {
            return "redirect:goLogin";
        }
        //交易密码验证
        if (userDto.getPasswordCash() == null || userDto.getPasswordCash().length() == 0) {
            request.setAttribute("message", "交易密码不能为空！");
            return WEIXIN_SETPASS;
        }

        map.put("userName", userDto.getPhoneNum());
        map.put("passwordCash", userDto.getPasswordCash());
        map.put("checkCode", userDto.getCheckCode());
        map.put("channel", "CHANNEL");
        map.put("setupFlag", "1");
        map.put("invitationCode", userDto.getInvitationCd() == null ? "" : userDto.getInvitationCd());
        map.put("weixinId", userDto.getWeixinId() == null ? "" : userDto.getWeixinId());
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("用户注册加密失败:" + e.getMessage());
            e.printStackTrace();
        }
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/register", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("用户注册解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info(LogUtil.getEnd("register", "方法结束运行", "LoginAction"));

        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        //注册成功后
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            logger.info("******************************登录成功！");

            //登录记录用户Cookie
            UserSession us = new UserSession();
            us.setId(Integer.valueOf(jsonObject.getString("userId")));
            us.setUsername(jsonObject.getString("userName"));
            us.setMobile(jsonObject.getString("mobile"));
            //登录
            UserCookieUtil.putUser(request, response, us);
            //设置登录后返回《记录页面》
            String backUrl = (String) session.getAttribute("backUrl");
            //绑定微信操作
            WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);
            session.removeAttribute("userDto");
            return "redirect:" + backUrl;
        } else {
            request.setAttribute("message", jsonObject.getString("resmsg_cn"));
            return WEIXIN_LOGIN;
        }
    }

    /**
     * 获取的验证码
     *
     * @param request
     * @return response
     * @throws Exception
     */
    @RequestMapping(value = "/goGetCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> goGetCaptcha(HttpServletRequest request,
                                            HttpServletResponse response, HttpSession session) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        //图片验证码
        if ("Y".equals(LoginRedirectUtil.YUNDUN_CAPTCHA_SWITCH)) {
            String captchaId = request.getParameter("captchaId");
            String captchaValidate = request.getParameter("NECaptchaValidate");
            boolean validate = YunDunCaptcha.validate(captchaId, captchaValidate);
            if (!validate) {
                modelMap.put("rescode", Consts.CHECK_CODE);
                modelMap.put("resmsg_cn", "请完成图形验证码！");
                modelMap.put("error", "true");
                return modelMap;
            }
        } else {
            String verifycode = request.getParameter("verifycode");
            String randomCode = String.valueOf(session.getAttribute("randomCode"));
            session.removeAttribute("randomCode");
            if (!randomCode.toUpperCase().equals(verifycode.toUpperCase())) {
                modelMap.put("rescode", Consts.CHECK_CODE);
                modelMap.put("resmsg_cn", "请完成图形验证码！");
                modelMap.put("error", "true");
                return modelMap;
            }
        }

        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        String phoneNum = request.getParameter("phoneNum");
        logger.info("=========goGetCaptcha-phoneNum:" + phoneNum);

        String type = request.getParameter("type");
        if(StringUtils.isBlank(type)){
			type="WXLOGIN";
		}else{
			if(type.equalsIgnoreCase("resetTradePassword"))
				type="RESET_TRADE_PASSWORD";
			else
				type=type.toUpperCase();
		}

        
    	String token_id = (String) request.getSession().getAttribute("token_id");
    	String ipInfo=CommonUtil.getClientIP(request);
    	logger.info("网站登录token_id="+token_id+"ipInfo="+ipInfo);



        Map<String, String> map = getCaptcha(phoneNum, phoneNum, type,token_id,ipInfo);
        if (map.get("rescode").equals(Consts.SUCCESS_CODE)) {
            modelMap.put("rescode", Consts.SUCCESS_CODE);
            modelMap.put("resmsg_cn", "");
            modelMap.put("success", "true");
        } else {
            modelMap.put("rescode", Consts.CHECK_CODE);
            modelMap.put("resmsg_cn", map.get("resmsg_cn"));
            modelMap.put("error", "true");
        }
        return modelMap;
    }


    /**
     * 邀请码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/regSucceed")
    public String regSucceed(HttpServletRequest request) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        return "weixin/accout/regSucceed";
    }

    /**
     * 退出
     *
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, response);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
        reqJavaMap.put("userId", userId);

        logger.info(LogUtil.getStart("deleteWeixinBingding", "方法开始执行", "WeiXinUserAction", getProjetUrl(request)));
        logger.info("userId=" + userId);

        String param = CommonUtil.getParam(reqJavaMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("退出解绑微信加密失败:" + e.getMessage());
            e.printStackTrace();
        }
        //删除微信userid绑定关系
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/user/deleteWeixinBingding", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                    "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info("删除微信userid绑定关系失败:" + e.getMessage());
            e.printStackTrace();
        }

        UserCookieUtil.invalidUser(request, response);

        String actionScope = Base64.encodeBase64URLSafeString("/wxuser/goLogin".getBytes());
        logger.info("actionScope" + actionScope);
        return "redirect:" + "/wxTrigger/getWxCode?actionScope=" + actionScope;
//		return null;
    }

    /**
     * 邀请人列表
     *
     * @param request
     * @param out
     * @return
     */
    @RequestMapping(value = "/getMyInvitationList", method = RequestMethod.POST)
    public void getMyInvitationList(HttpServletRequest request, PrintWriter out, HttpServletResponse res) {
        // String loanId = request.getParameter("loanId");
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
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
        map.put("userId", userId);
        logger.info(LogUtil.getStart("getMyInvitationList", "方法开始执行", "WeiXinUserAction", getProjetUrl(request)));
        logger.info("current=" + current + "&pageSize=" + pageSize + "&userId=" + userId);

        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("邀请人列表加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/invitation/myInvitationList", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("邀请人列表解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        Map<String, Object> jsonMap = JsonUtil.getMapFromJsonString(resultMsg);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (Consts.SUCCESS_CODE.equals(jsonMap.get("rescode"))) {
            String size = (String) jsonMap.get("count");
            List<JSONObject> listDetail = (List<JSONObject>) jsonMap.get("list");
            // String size = jsonObjRtn.getString("totalNum");
            System.out.println("#####################" + listDetail.size());

            PageUtils pageObject = new PageUtils();
            if (null != listDetail && listDetail.size() != 0) {
                int intPageSize = 0;
                if (null != pageSize && !"".equals(pageSize)) {
                    intPageSize = Integer.parseInt(pageSize);
                }
                pageObject = PageUtil.execsPage(Integer.parseInt(current), Integer.parseInt(size), 5, intPageSize);
            }
            resultMap.put("pageObject", pageObject);
            resultMap.put("list", listDetail);
        }
        resultMap.put("rescode", jsonMap.get("rescode"));

        setResposeMap(resultMap, out);
    }

    /**
     * 查询用户实名认证信息
     *
     * @param request
     * @param out
     */
    @RequestMapping(value = "/getIdcardInfo", method = RequestMethod.POST)
    public void getIdcardInfo(HttpServletRequest request, PrintWriter out, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>();
        reqJavaMap.put("userId", userId);

        logger.info(LogUtil.getStart("getIdcardInfo", "方法开始执行", "WeiXinUserAction", getProjetUrl(request)));
        logger.info("userId=" + userId);

        String param = CommonUtil.getParam(reqJavaMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("查询用户实名认证加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        //获取用户实名认证信息
        String resultMsg = HttpRequestParam.sendGet(
                LoginRedirectUtil.interfacePath + "/user/getIdcardInfo", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                    "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info("查询用户认证信息解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        setResposeMsg(resultMsg.toString(), out);
    }

    /**
     * 跳转邀请好友--app专用
     *
     * @return
     */
    @RequestMapping(value = "/goInviteFriendForApp", method = RequestMethod.GET)
    public void goInviteFriendForApp(String mobile, HttpServletRequest request, HttpServletResponse res) {
        // login接收的数据
        JSONObject json = new JSONObject();
        if ("".equalsIgnoreCase(mobile) || null == mobile) {
            mobile = "";
        }
        mobile = mobile.split(",")[0];
        json.put("invitationCd", mobile);
        json.put("backUrl", "/webabout/download");
        String loginParm = "";
        try {
            loginParm = getEncodeString(json.toString());
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String goUrl = "";
        try {
            goUrl = Base64.encodeBase64URLSafeString(("/wxactivity/activity_20151103?parm=" + loginParm).getBytes("UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=" + goUrl).forward(request, res);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;
//		String url = WeixinRateUrlConfig.getValue("weixin.hostUrl")+"/wxTrigger/getWxCode?actionScope="+goUrl;
//		request.setAttribute("url",url);
//		WeixinRquestUtil.getSignature(request);
//		return "weixin/activity/activitySharingForApp";
    }

    private String getEncodeString(String str) {
        String parm = "";
        try {
            parm = URLEncoder.encode(DES3Util.encode(str), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + e.getMessage());
            e.printStackTrace();
        }
        return parm;
    }

    /***
     * 分享出去的链接
     * @param request
     * @param res
     * @return
     */

    @RequestMapping(value = "/checkShareRateRises")
    public String checkShareRateRises(String shareMobileDateJM, String lootMobile, String current, HttpServletRequest request, HttpServletResponse res) {
        String ua = request.getHeader("user-agent").toLowerCase();
        //解密分享时间，分享人
        String shareMobileDateJMData = "";
        shareMobileDateJM = shareMobileDateJM.replaceAll(" ", "+");
        try {
            shareMobileDateJMData = java.net.URLDecoder.decode(DES3Util.decode(shareMobileDateJM), "UTF-8");
        } catch (Exception e) {
            logger.error("[解密分享时间，分享人]DES解密失败");
            e.printStackTrace();
        }
        Map<String, String> paramsMap = CommonUtil.decryptParam(shareMobileDateJMData);
        String shareMobile = paramsMap.get("shareMobile");
        String shareDate = paramsMap.get("shareDate");
        String count = "0";
        request.setAttribute("shareMobileDateJM", shareMobileDateJM);
        String mediaUid = "";
        if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
            //分享加签
            WeixinRquestUtil.getSignature(request);
            // login接收的数据
            UserSession us = UserCookieUtil.getUser(request);

            //分享人手机号，用户Id
            String userId = "";
            String mobile = "";
            Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
            mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));

            if (null == us || null == us.getId()) {
                //不执行跳转操作
                Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
                userId = userInfoMap.get("userId");
                mobile = userInfoMap.get("mobile");
                mediaUid = userInfoMap.get("mediaUid");
                logger.info("****************（抢券人）当前已绑定用户手机号mobile：" + mobile);
            } else {
                userId = String.valueOf(us.getId());
                mobile = us.getMobile();
                logger.info("****************（抢券人）session当前老用户已登录手机号mobile：" + mobile + "&&&&&&&&微信标识名：mediaUid" + mediaUid);
            }
            //查询当前分享人的抢券人数
            String couponsResultMsg4 = queryLootShareInfo(shareMobile);
            JSONObject couponsResultMsgObject4 = JSONObject.fromObject(couponsResultMsg4);
            if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject4.getString("rescode"))) {
                List<JSONObject> listDetail4 = couponsResultMsgObject4.getJSONArray("lootShareList");
                if (null != listDetail4 && 0 <= listDetail4.size()) {
                    count = String.valueOf(listDetail4.size());
                }
            }

            //抢券人未登录
            if (null == mobile || "".equalsIgnoreCase(mobile)) {
                if (null == lootMobile || "".equalsIgnoreCase(lootMobile)) {
                    String goUrl = "";
                    try {
                        goUrl = Base64.encodeBase64URLSafeString(("/wxuser/checkShareRateRises?shareMobileDateJM=" + shareMobileDateJM + "&lootMobile=").getBytes("UTF-8"));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //分享出去的加息券链接
                    String shareUrl = WeixinRateUrlConfig.getValue("weixin.hostUrl") + "/wxTrigger/getWxCode?actionScope=" + goUrl;
                    request.setAttribute("shareUrl", shareUrl);//分享出去的链接
                    request.setAttribute("shareMobileDateJM", shareMobileDateJM);//分享时间戳
                    request.setAttribute("count", count);
                    request.setAttribute("resultFlag", "22222");
                    logger.info("***********************进入页面手动输入手机号，进行抢券中*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile
                            + "&&&&分享出去的链接：" + shareUrl);
                    return "weixin/activity/acctivityLootShareT";
                } else {
                    mobile = lootMobile;
                }
            }
            //获取当前分享时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            //分享出去的加息券链接
            String goUrl = "";
            try {
                goUrl = Base64.encodeBase64URLSafeString(("/wxuser/checkShareRateRises?shareMobileDateJM=" + shareMobileDateJM + "&lootMobile=").getBytes("UTF-8"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String shareUrl = WeixinRateUrlConfig.getValue("weixin.hostUrl") + "/wxTrigger/getWxCode?actionScope=" + goUrl;
            request.setAttribute("shareUrl", shareUrl);//分享出去的链接
            logger.info("****************抢券人已登录mobile：" + mobile
                    + "&&&&分享出去的链接：" + shareUrl);

            logger.info("******************分享人手机号shareMobile：" + shareMobile);
            logger.info("******************分享时间shareDate：" + shareDate);

            //分享人不可以抢自己的券
            if (null != shareDate && shareDate.equalsIgnoreCase(date)) {
                if (null != shareMobile && !mobile.equalsIgnoreCase(shareMobile)) {
                    Map<String, String> resultMap = new HashMap<String, String>();//json响应数据
                    try {
                        //查看他人分享加息
                        Map<String, String> result = checkShareRateRises("4", mobile, "", shareMobile, mediaUid);//
                        logger.info("*******************用户：" + mobile + "的查看他人分享链接结果：" + result);
                        logger.info("***********************非分享人已成功抢到加息券*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                        //查询当前分享人的抢券人数
                        if (!"success".equalsIgnoreCase(result.get("result"))) {
                            //resultFlag:  1:已抢过  2:已加满
                            if (null != result.get("resultFlag") && "1".equalsIgnoreCase(result.get("resultFlag"))) {
                                request.setAttribute("resultFlag", "44444");
                                logger.info("***********************对不起，已经抢过该分享的加息券！*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                            } else {
                                logger.info("***********************抢券人今天加息已经满9次，您还可以分享获取一次加息*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                                Map<String, String> result2 = couponsAdd("3", mobile, "", shareMobile, mediaUid);
                                logger.info("*******************用户：" + shareMobile + "的被他人查看分享结果：" + result2);
                                //查看他人分享加息
                                Map<String, String> result3 = couponsAdd("4", mobile, "", shareMobile, mediaUid);//
                                logger.info("*******************用户：" + mobile + "的查看他人分享链接结果：" + result);
                                logger.info("***********************非分享人已成功抢到加息券*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                                //查询当前分享人的抢券人数
                                if (!"success".equalsIgnoreCase(result3.get("result"))) {
                                    //resultFlag:  1:已抢过  2:已加满
                                    if (null != result3.get("resultFlag") && "1".equalsIgnoreCase(result3.get("resultFlag"))) {
                                        request.setAttribute("resultFlag", "44444");
                                        logger.info("***********************对不起，已经抢过该分享的加息券！*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                                    } else {
                                        request.setAttribute("resultFlag", "33333");
                                        logger.info("***********************抢券人今天加息已经满9次，您还可以分享获取一次加息*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                                    }
                                }
                            }
                        } else {
                            request.setAttribute("resultFlag", "66666");
                            request.setAttribute("lootMobile", mobile);//抢券人手机号
                            logger.info("***********************可以点击按钮领取，已登录*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                        }
                    } catch (Exception e) {
                        logger.info("根据加息券获取途径查询加息券解密失败:" + e.getMessage());
                        e.printStackTrace();
                        request.setAttribute("resultFlag", "44444");
                        logger.info("***********************对不起，已经抢过该分享的加息券！*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                    }
                } else {
                    if (null != shareDate && shareDate.equalsIgnoreCase(date)) {
                        request.setAttribute("resultFlag", "11111");
                        logger.info("***********************用户不能抢自己分享出去的加息券*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                    }
                }
            } else {
                request.setAttribute("resultFlag", "99999");
                logger.info("***********************该用户抢的加息券非当天分享，已失效*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);

            }
        } else {
            request.setAttribute("resultFlag", "55555");
            logger.info("***********************该加息功能，只限微信浏览器打开使用*********" + "&&&抢券人手机号lootMobile" + lootMobile + "****分享人手机号shareMobile" + shareMobile);
        }
        //查询当前分享人的抢券人数
        String couponsResultMsg5 = queryLootShareInfo(shareMobile);
        JSONObject couponsResultMsgObject5 = JSONObject.fromObject(couponsResultMsg5);
        if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject5.getString("rescode"))) {
            List<JSONObject> listDetail5 = couponsResultMsgObject5.getJSONArray("lootShareList");
            if (null != listDetail5 && 0 <= listDetail5.size()) {
                count = String.valueOf(listDetail5.size());
            }
        }
        request.setAttribute("count", count);
        return "weixin/activity/acctivityLootShareT";
    }

    /**
     * 领取分享的加息券
     * //1.新用户注册，2.用户每日分享，3.被他人查看分享，4.查看他人分接享链，5、活期收益满五元，自动生成加息
     *
     * @return
     */
    @RequestMapping(value = "/lootShareIncreaseInterest")
    public String lootShareIncreaseInterest(String shareMobileDateJM, String lootMobile, String current, HttpServletRequest request, HttpServletResponse res) {
        String ua = request.getHeader("user-agent").toLowerCase();
        //解密分享时间，分享人
        String shareMobileDateJMData = "";
        shareMobileDateJM = shareMobileDateJM.replaceAll(" ", "+");
        try {
            shareMobileDateJMData = java.net.URLDecoder.decode(DES3Util.decode(shareMobileDateJM), "UTF-8");
        } catch (Exception e) {
            logger.error("[解密分享时间，分享人]DES解密失败");
            e.printStackTrace();
        }
        Map<String, String> paramsMap = CommonUtil.decryptParam(shareMobileDateJMData);
        String shareMobile = paramsMap.get("shareMobile");
        String shareDate = paramsMap.get("shareDate");
        String count = "0";
        request.setAttribute("shareMobileDateJM", shareMobileDateJM);
        String mediaUid = "";
        if (ua.indexOf("micromessenger") > 0) {// 微信浏览器
            WeixinRquestUtil.getSignature(request);

            // login接收的数据
            UserSession us = UserCookieUtil.getUser(request);
            //分享人手机号，用户Id
            String userId = "";
            String mobile = "";
            Map mediaUidMap = UserCookieUtil.getWeixinOpenid(request);
            mediaUid = String.valueOf(mediaUidMap.get("mediaUid"));

            if (null == us || null == us.getId()) {
                //不执行跳转操作
                Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
                userId = userInfoMap.get("userId");
                mobile = userInfoMap.get("mobile");
                mediaUid = userInfoMap.get("mediaUid");
                logger.info("****************（抢券人）当前已绑定用户手机号mobile：" + mobile);
            } else {
                userId = String.valueOf(us.getId());
                mobile = us.getMobile();
                logger.info("****************（抢券人）session当前老用户已登录手机号mobile：" + mobile + "&&&&&&&&微信标识名：mediaUid" + mediaUid);
            }
            //查询当前分享人的抢券人数
            String couponsResultMsg4 = queryLootShareInfo(shareMobile);
            JSONObject couponsResultMsgObject4 = JSONObject.fromObject(couponsResultMsg4);
            if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject4.getString("rescode"))) {
                List<JSONObject> listDetail4 = couponsResultMsgObject4.getJSONArray("lootShareList");
                if (null != listDetail4 && 0 <= listDetail4.size()) {
                    count = String.valueOf(listDetail4.size());
                }
            }

            //抢券人未登录
            if (null == mobile || "".equalsIgnoreCase(mobile)) {
                if (null == lootMobile || "".equalsIgnoreCase(lootMobile)) {
                    //获取当前分享时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                    String date = sdf.format(new Date());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("shareMobile", shareMobile);
                    map.put("shareDate", date);
                    String shareMobileDate = CommonUtil.getParam(map);
                    try {
                        shareMobileDate = DES3Util.encode(shareMobileDate);
                    } catch (Exception e) {
                        logger.info("[跳转到邀请好友]时间，分享人手机号加密失败:" + e.getMessage());
                        e.printStackTrace();
                    }

                    String goUrl = "";
                    try {
                        goUrl = Base64.encodeBase64URLSafeString(("/wxuser/checkShareRateRises?shareMobileDateJM=" + shareMobileDate + "&lootMobile=").getBytes("UTF-8"));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String shareUrl = WeixinRateUrlConfig.getValue("weixin.hostUrl") + "/wxTrigger/getWxCode?actionScope=" + goUrl;
                    request.setAttribute("shareUrl", shareUrl);//分享出去的链接
                    request.setAttribute("shareMobileDate", shareMobileDate);//分享时间戳
                    request.setAttribute("count", count);
                    request.setAttribute("resultFlag", "22222");
                    logger.info("***********************进入页面手动输入手机号，进行抢券中*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile
                            + "&&&&分享出去的链接：" + shareUrl);
                    return "weixin/activity/acctivityLootShareT";
                } else {
                    mobile = lootMobile;

                }
            }

            //获取当前分享时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("shareMobile", shareMobile);
            map.put("shareDate", date);
            String shareMobileDate = CommonUtil.getParam(map);
            try {
                shareMobileDate = DES3Util.encode(shareMobileDate);
            } catch (Exception e) {
                logger.info("[跳转到邀请好友]时间，分享人手机号加密失败:" + e.getMessage());
                e.printStackTrace();
            }

            String goUrl = "";
            try {
                goUrl = Base64.encodeBase64URLSafeString(("/wxuser/checkShareRateRises?shareMobileDateJM=" + shareMobileDate + "&lootMobile=").getBytes("UTF-8"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String shareUrl = WeixinRateUrlConfig.getValue("weixin.hostUrl") + "/wxTrigger/getWxCode?actionScope=" + goUrl;
            request.setAttribute("shareUrl", shareUrl);//分享出去的链接
            request.setAttribute("shareMobileDate", shareMobileDate);//分享时间戳
            logger.info("****************抢券人已登录mobile：" + mobile
                    + "&&&&分享出去的链接：" + shareUrl);

            logger.info("******************分享人手机号shareMobile：" + shareMobile);
            logger.info("******************分享时间shareDate：" + shareDate);

            //分享人不可以抢自己的券
            if (null != shareDate && shareDate.equalsIgnoreCase(date)) {
                if (null != shareMobile && !mobile.equalsIgnoreCase(shareMobile)) {
                    Map<String, String> resultMap = new HashMap<String, String>();//json响应数据
                    try {
                        //查看抢券人当天被动，已使用的加息券情况
                        String couponsResultMsg = getTodayRateRisesCoupons("", "1", "2", "20", current, mobile, "");
                        logger.info("根据加息券获取途径查询加息券解密信息:" + couponsResultMsg);

                        JSONObject couponsResultMsgObject = JSONObject.fromObject(couponsResultMsg);
                        if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject.getString("rescode"))) {
                            //被他人查看（分享人）加息
                            String couponsResultMsg2 = getTodayRateRisesCoupons("", "1", "2", "20", current, shareMobile, "");
                            JSONObject couponsResultMsgObject2 = JSONObject.fromObject(couponsResultMsg2);
                            if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject2.getString("rescode"))) {
                                List<JSONObject> listDetail2 = couponsResultMsgObject2.getJSONArray("rateCouponsList");
                                BigDecimal rateRiseSum2 = new BigDecimal(0.00);
                                for (int j = 0; j < listDetail2.size(); j++) {
                                    rateRiseSum2 = rateRiseSum2.add(new BigDecimal(String.valueOf(listDetail2.get(j).get("rate_rises"))));
                                }
                                if (rateRiseSum2.compareTo(new BigDecimal(0.9)) == -1) {
                                    Map<String, String> result2 = couponsAdd("3", mobile, "", shareMobile, mediaUid);
                                    logger.info("*******************用户：" + shareMobile + "的被他人查看分享结果：" + result2);
                                }

                                //给分享回掉失败的用户获取途径是2的加息券
                                String couponsResultMsg3 = getTodayRateRisesCoupons("2", "1", "2", "20", current, shareMobile, "");
                                logger.info("根据加息券获取途径查询加息券解密信息:" + couponsResultMsg2);

                                JSONObject couponsResultMsgObject3 = JSONObject.fromObject(couponsResultMsg3);
                                if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject3.getString("rescode"))) {
                                    List<JSONObject> listDetail3 = couponsResultMsgObject3.getJSONArray("rateCouponsList");
                                    if (listDetail3.size() == 0) {
                                        Map<String, String> result2 = couponsAdd("2", shareMobile, "", "", mediaUid);
                                        logger.info("*******************用户：" + shareMobile + "的如果分享人分享不能功能，通过抢券人给分享人加标识是2的获取路径，结果是：" + result2);
                                    }
                                }
                            }

                            //抢券人已使用，被动的加息券
                            List<JSONObject> listDetail = couponsResultMsgObject.getJSONArray("rateCouponsList");
                            BigDecimal rateRiseSum = new BigDecimal(0.00);
                            for (int i = 0; i < listDetail.size(); i++) {
                                rateRiseSum = rateRiseSum.add(new BigDecimal(String.valueOf(listDetail.get(i).get("rate_rises"))));
                            }
                            //查看他人分享加息
                            Map<String, String> result = couponsAdd("4", mobile, "", shareMobile, mediaUid);//
                            logger.info("*******************用户：" + mobile + "的查看他人分享链接结果：" + result);
                            logger.info("***********************非分享人已成功抢到加息券*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                            //查询当前分享人的抢券人数
                            if (!"success".equalsIgnoreCase(result.get("result"))) {
                                //resultFlag:  1:已抢过  2:已加满
                                if (null != result.get("resultFlag") && "2".equalsIgnoreCase(result.get("resultFlag"))) {
                                    request.setAttribute("resultFlag", "33333");
                                    logger.info("***********************抢券人今天加息已经满9次，您还可以分享获取一次加息*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                                } else {
                                    request.setAttribute("resultFlag", "44444");
                                    logger.info("***********************对不起，已经抢过该分享的加息券！*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                                }
                            } else {
                                request.setAttribute("resultFlag", "00000");
                            }
                        }
                    } catch (Exception e) {
                        logger.info("根据加息券获取途径查询加息券解密失败:" + e.getMessage());
                        e.printStackTrace();
                        //查询当前分享人的抢券人数
                        String couponsResultMsg5 = queryLootShareInfo(shareMobile);
                        JSONObject couponsResultMsgObject5 = JSONObject.fromObject(couponsResultMsg5);
                        if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject5.getString("rescode"))) {
                            List<JSONObject> listDetail5 = couponsResultMsgObject5.getJSONArray("lootShareList");
                            if (null != listDetail5 && 0 <= listDetail5.size()) {
                                count = String.valueOf(listDetail5.size());
                            }
                        }
                        request.setAttribute("count", count);
                        request.setAttribute("resultFlag", "44444");
                        logger.info("***********************对不起，已经抢过该分享的加息券！*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                        return "weixin/activity/acctivityLootShareT";
                    }
                } else {
                    request.setAttribute("resultFlag", "11111");
                    logger.info("***********************用户不能抢自己分享出去的加息券*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
                }
            } else {
                request.setAttribute("resultFlag", "99999");
                logger.info("***********************该用户抢的加息券非当天分享，已失效*********" + "&&&抢券人手机号lootMobile" + mobile + "****分享人手机号shareMobile" + shareMobile);
            }
        } else {
            request.setAttribute("resultFlag", "55555");
            logger.info("***********************该加息功能，只限微信浏览器打开使用*********" + "&&&抢券人手机号lootMobile" + lootMobile + "****分享人手机号shareMobile" + shareMobile);
        }
        //查询当前分享人的抢券人数
        String couponsResultMsg5 = queryLootShareInfo(shareMobile);
        JSONObject couponsResultMsgObject5 = JSONObject.fromObject(couponsResultMsg5);
        if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject5.getString("rescode"))) {
            List<JSONObject> listDetail5 = couponsResultMsgObject5.getJSONArray("lootShareList");
            if (null != listDetail5 && 0 <= listDetail5.size()) {
                count = String.valueOf(listDetail5.size());
            }
        }
        request.setAttribute("count", count);
        return "weixin/activity/acctivityLootShareT";
    }


    /**
     * 分享人加息(异步)
     *
     * @return
     */
    @RequestMapping(value = "/shareIncreaseInterest")
    public void shareIncreaseInterest(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        // login接收的数据
        UserSession us = UserCookieUtil.getUser(request);
        //分享人手机号，用户Id
        String userId = "";
        String mobile = "";
        if (null == us || null == us.getId()) {
            //不执行跳转操作
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
            userId = userInfoMap.get("userId");
            mobile = userInfoMap.get("mobile");
            logger.info("****************（分享人）当前已绑定用户手机号mobile：" + mobile);
        } else {
            userId = String.valueOf(us.getId());
            mobile = us.getMobile();
            logger.info("****************（分享人）session当前老用户已登录手机号mobile：" + mobile);
        }

        Map<String, String> resultMap = new HashMap<String, String>();//json响应数据

        try {
            if (null == current || "".equalsIgnoreCase(current)) {
                current = "1";
            }
            Map<String, Object> couponsMap = new LinkedHashMap<String, Object>();
            couponsMap.put("getWay", "2");//加息券获取途径
            couponsMap.put("status", "1");//1、已使用 2、未使用
            couponsMap.put("useFlag", "2");//1、主动使用 2、被动使用
            couponsMap.put("pageSize", 10);
            couponsMap.put("current", current);//当前页
            couponsMap.put("mobile", mobile);
            couponsMap.put("userId", userId);
            couponsMap.put("productId", WeixinRateUrlConfig.getValue("weixin_product_id"));


            //封装加密请求数据
            String couponsParam = CommonUtil.getParam(couponsMap);
            try {
                couponsParam = DES3Util.encode(couponsParam);
                logger.info("&&&&&&&&&&&&&&&&&&" + couponsParam);
            } catch (Exception e) {
                logger.info("【请求加密失败】查询加息券:" + e.getMessage());
                e.printStackTrace();
            }
            String couponsResultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/getTodayRateRisesCoupons", couponsParam);
            //解密响应结果
            couponsResultMsg = java.net.URLDecoder.decode(DES3Util.decode(couponsResultMsg), "UTF-8");
            logger.info("根据加息券获取途径查询加息券解密信息:" + couponsResultMsg);

            JSONObject couponsResultMsgObject = JSONObject.fromObject(couponsResultMsg);
            if (Consts.SUCCESS_CODE.equals(couponsResultMsgObject.getString("rescode"))) {
                List<JSONObject> listDetail = couponsResultMsgObject.getJSONArray("rateCouponsList");
                /***
                 * @authtor wel
                 * 每日任务分享赠送加息券
                 */
                if (null == listDetail || 0 == listDetail.size()) {
                    try {
                        Map<String, Object> couponsMapAdd = new LinkedHashMap<String, Object>();
                        couponsMapAdd.put("type", "2");//1.新用户注册，2.用户每日分享，3.被他人查看分享，4.查看他人分享链接，5、活期收益满五元，自动生成加息
                        couponsMapAdd.put("mobile", mobile);
                        couponsMapAdd.put("userId", userId);

                        //封装加密请求数据
                        String couponsAddParam = CommonUtil.getParam(couponsMapAdd);
                        try {
                            couponsAddParam = DES3Util.encode(couponsAddParam);
                        } catch (Exception e) {
                            logger.info("【请求加密失败】生成加息券::" + e.getMessage());
                            e.printStackTrace();
                        }
                        String couponsResultAddMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/addRateRisesCoupons", couponsAddParam);
                        //解密响应结果
                        couponsResultAddMsg = java.net.URLDecoder.decode(DES3Util.decode(couponsResultAddMsg), "UTF-8");
                        logger.info("每日任务赠送加息券解密信息:" + couponsResultAddMsg);
                        JSONObject couponsResultMsgAddObject = JSONObject.fromObject(couponsResultAddMsg);
                        if (Consts.SUCCESS_CODE.equals(couponsResultMsgAddObject.getString("rescode"))) {
                            resultMap.put("msg", "success");
                        } else {
                            resultMap.put("msg", "error");
                        }
                    } catch (Exception e) {
                        logger.info("每日任务赠送加息券解密失败:" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                resultMap.put("msg", "error");
            }
        } catch (Exception e) {
            logger.info("根据加息券获取途径查询加息券解密失败:" + e.getMessage());
            e.printStackTrace();

        }
        try {
            String req_data = JSON.toJSONString(resultMap);
            req_data = new String(req_data.toString().getBytes("utf-8"), "iso8859-1");

            out.write(req_data);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.info("【响应数据】分享加息失败:" + e.getMessage());
            e.printStackTrace();
        }
        return;
    }

    /**
     * 抢券check
     *
     * @param type
     * @param mobile
     * @param userId
     * @return
     */
    public static Map<String, String> checkShareRateRises(String type, String mobile, String userId, String shareMobile, String mediaUid) {

        Map<String, Object> couponsMapAdd = new LinkedHashMap<String, Object>();
        couponsMapAdd.put("type", "4");//1.新用户注册，2.用户每日分享，3.被他人查看分享，4.查看他人分享链接，5、活期收益满五元，自动生成加息
        couponsMapAdd.put("mobile", mobile);
        couponsMapAdd.put("userId", userId);
        couponsMapAdd.put("shareMobile", shareMobile);
        couponsMapAdd.put("mediaUid", mediaUid);
        if ("2".equalsIgnoreCase(type)) {
            couponsMapAdd.put("productId", WeixinRateUrlConfig.getValue("weixin_product_id"));
        }

        //封装加密请求数据
        String couponsAddParam = CommonUtil.getParam(couponsMapAdd);
        try {
            couponsAddParam = DES3Util.encode(couponsAddParam);
            logger.info("******************" + couponsAddParam);
        } catch (Exception e) {
            logger.info("【请求加密失败】生成加息券::" + e.getMessage());
            e.printStackTrace();
        }
        String couponsResultAddMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/checkShareRateRises", couponsAddParam);
        //解密响应结果 分享成功
        try {
            couponsResultAddMsg = java.net.URLDecoder.decode(DES3Util.decode(couponsResultAddMsg), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("每日任务赠送加息券解密信息:" + couponsResultAddMsg);
        Map<String, String> map = new HashMap<String, String>();
        JSONObject couponsResultMsgAddObject = JSONObject.fromObject(couponsResultAddMsg);
        if (Consts.SUCCESS_CODE.equals(couponsResultMsgAddObject.getString("rescode"))) {
            map.put("result", "success");
            //map.put("resultFlag", couponsResultMsgAddObject.getString("lootFlag"));
            return map;
        }
        map.put("result", "error");
        //lootFlag:1:已抢过  2:已加满
        if (null == couponsResultMsgAddObject.get("lootFlag") || "".equalsIgnoreCase(couponsResultMsgAddObject.getString("lootFlag")) || "null".equalsIgnoreCase(couponsResultMsgAddObject.getString("lootFlag"))) {
            map.put("resultFlag", "1");
            logger.info("**************添加加息券type：" + type + "&&lootFlag返回无值:");
        } else {
            map.put("resultFlag", couponsResultMsgAddObject.getString("lootFlag"));
            logger.info("**************lootFlag返回有值:" + couponsResultMsgAddObject.getString("lootFlag"));
        }

        return map;
    }

    /**
     * 主动，被动加息方法
     *
     * @param type
     * @param mobile
     * @param userId
     * @return
     */
    public static Map<String, String> couponsAdd(String type, String mobile, String userId, String shareMobile, String mediaUid) {

        Map<String, Object> couponsMapAdd = new LinkedHashMap<String, Object>();
        couponsMapAdd.put("type", type);//1.新用户注册，2.用户每日分享，3.被他人查看分享，4.查看他人分享链接，5、活期收益满五元，自动生成加息
        couponsMapAdd.put("mobile", mobile);
        couponsMapAdd.put("userId", userId);
        couponsMapAdd.put("shareMobile", shareMobile);
        couponsMapAdd.put("mediaUid", mediaUid);
        if ("2".equalsIgnoreCase(type)) {
            couponsMapAdd.put("productId", WeixinRateUrlConfig.getValue("weixin_product_id"));
        }

        //封装加密请求数据
        String couponsAddParam = CommonUtil.getParam(couponsMapAdd);
        try {
            couponsAddParam = DES3Util.encode(couponsAddParam);
            logger.info("******************" + couponsAddParam);
        } catch (Exception e) {
            logger.info("【请求加密失败】生成加息券::" + e.getMessage());
            e.printStackTrace();
        }
        String couponsResultAddMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/addRateRisesCoupons", couponsAddParam);
        //解密响应结果 分享成功
        try {
            couponsResultAddMsg = java.net.URLDecoder.decode(DES3Util.decode(couponsResultAddMsg), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("每日任务赠送加息券解密信息:" + couponsResultAddMsg);
        Map<String, String> map = new HashMap<String, String>();
        JSONObject couponsResultMsgAddObject = JSONObject.fromObject(couponsResultAddMsg);
        if (Consts.SUCCESS_CODE.equals(couponsResultMsgAddObject.getString("rescode"))) {
            map.put("result", "success");
            //map.put("resultFlag", couponsResultMsgAddObject.getString("lootFlag"));
            return map;
        }
        map.put("result", "error");
        //lootFlag:1:已抢过  2:已加满
        if (null == couponsResultMsgAddObject.get("lootFlag") || "".equalsIgnoreCase(couponsResultMsgAddObject.getString("lootFlag")) || "null".equalsIgnoreCase(couponsResultMsgAddObject.getString("lootFlag"))) {
            map.put("resultFlag", "1");
            logger.info("**************添加加息券type：" + type + "&&lootFlag返回无值:");
        } else {
            map.put("resultFlag", couponsResultMsgAddObject.getString("lootFlag"));
            logger.info("**************lootFlag返回有值:" + couponsResultMsgAddObject.getString("lootFlag"));
        }

        return map;
    }

    /**
     * 查询不同获取路径（或者被动，主动，使用，未使用）的加息券情况
     *
     * @param type
     * @param mobile
     * @param userId
     * @return
     */
    public static String getTodayRateRisesCoupons(String getWay, String status, String useFlag, String pageSize, String current, String mobile, String userId) {
        Map<String, Object> couponsMap = new LinkedHashMap<String, Object>();
        if (null != getWay && !"".equalsIgnoreCase(getWay)) {
            couponsMap.put("getWay", getWay);//加息券获取途径
        }
        if (null == current || "".equalsIgnoreCase(current)) {
            current = "1";
        }
        couponsMap.put("status", status);//1、已使用 2、未使用
        couponsMap.put("useFlag", useFlag);//1、主动使用 2、被动使用
        couponsMap.put("pageSize", pageSize);
        couponsMap.put("current", current);//当前页
        couponsMap.put("mobile", mobile);//查看人手机号
        couponsMap.put("userId", userId);

        //封装加密请求数据
        String couponsParam = CommonUtil.getParam(couponsMap);
        try {
            couponsParam = DES3Util.encode(couponsParam);
        } catch (Exception e) {
            logger.info("【请求加密失败】查询加息券:" + e.getMessage());
            e.printStackTrace();

        }
        String couponsResultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/getTodayRateRisesCoupons", couponsParam);
        //解密响应结果
        try {
            couponsResultMsg = java.net.URLDecoder.decode(DES3Util.decode(couponsResultMsg), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("根据加息券获取途径查询加息券解密信息:" + couponsResultMsg);
        return couponsResultMsg;
    }


    /**
     * 查询用户每日分享信息（查看人信息）被人抢的信息
     *
     * @param type
     * @param mobile
     * @param userId
     * @return
     */
    public static String queryLootShareInfo(String shareMobile) {
        Map<String, Object> couponsMap = new LinkedHashMap<String, Object>();
        couponsMap.put("mobile", shareMobile);//分享人手机号

        //封装加密请求数据
        String couponsParam = CommonUtil.getParam(couponsMap);
        try {
            couponsParam = DES3Util.encode(couponsParam);
        } catch (Exception e) {
            logger.info("【请求加密失败】查询加息券:" + e.getMessage());
            e.printStackTrace();

        }
        String couponsResultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/queryLootShareInfo", couponsParam);
        //解密响应结果
        try {
            couponsResultMsg = java.net.URLDecoder.decode(DES3Util.decode(couponsResultMsg), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("查询用户每日分享信息（查看人信息）被人抢的解密信息:" + couponsResultMsg);
        return couponsResultMsg;
    }


    /**
     * 发送模板（充值，提现申请中，购买，加息券）消息
     *
     * @return
     */
    @RequestMapping(value = "/sendWeixinModelInfo", method = RequestMethod.GET)
    public void sendWeixinModelInfo(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        //发送模板消息
        String resultInfo = WeixinRquestUtil.sendWeixinModelMessage(request);
        try {
            int result = Integer.parseInt(resultInfo);
            if (result == 0) {
                resultInfo = CommonUtil.setResultStringCn(new HashMap(), Consts.SUCCESS_CODE, Consts.SUCCESS_DESCRIBE, "");
            } else {
                resultInfo = CommonUtil.setResultStringCn(new HashMap(), Consts.CHECK_CODE, "error", "");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            resultInfo = CommonUtil.setResultStringCn(new HashMap(), Consts.CHECK_CODE, "error", "");
            e.printStackTrace();
        }

        CommonUtil.responseJson(resultInfo, res);
    }


    /**
     * 客服系统
     *
     * @return
     */
    @RequestMapping(value = "/aikf", method = RequestMethod.GET)
    public String aikf(String shareMobileDateJM, String lootMobile, String current, HttpServletRequest request, HttpServletResponse res) {
        return "weixin/activity/aikf";
    }

    /**
     * 根据身份证号和手机号查询是否存在信息
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/queryUserInfoByIDCardAndMobile", method = RequestMethod.GET)
    public void queryUserInfoByIDCardAndMobile(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String mobile = us.getMobile();
        String idCard = request.getParameter("idCard");

        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("mobile", mobile);
        requestMap.put("idCard", idCard);

        String param = CommonUtil.getParam(requestMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("根据身份证号和手机号查询是否存在信息失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam
                .sendGet(LoginRedirectUtil.interfacePath
                        + "/user/queryUserInfoByIDCardAndMobile", param);
        JSONObject msgJson = null;
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
            msgJson = JSONObject.fromObject(resultMsg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.info("根据身份证号和手机号查询是否存在信息解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        setResposeMsg(msgJson.toString(), out);
    }

    /**
     * 重置密码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public void resetPassword(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        //String phoneNum, String passwordCash,
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String mobile = us.getMobile();
        String passwordCash = request.getParameter("passwordCash");

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userName", mobile);
        map.put("passwordCash", passwordCash);
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/resetPassword", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);

        setResposeMsg(jsonObject.toString(), out);

    }

    /**
     * 密码登录
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/passwordLogin", method = RequestMethod.GET)
    public String passwordLogin(String parm, HttpServletRequest request, HttpServletResponse response, Model model) {
        WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
        if (null == userDto) {
            userDto = new WeiXinUserDto();
        }
        String backUrl = "";

        request.setAttribute("mobileNumber", request.getParameter("mobileNumber"));
        request.setAttribute("channel", request.getParameter("channel"));

        logger.info("######################passwordLogin获取的参数：[" + parm + "]");
        if (parm != null && parm.length() > 0) {
            try {
                parm = java.net.URLDecoder.decode(DES3Util.decode(parm.replaceAll(" ", "+")), "UTF-8");
            } catch (Exception e) {
                logger.info("检察用户功能解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject jsonObj = JSONObject.fromObject(parm);
            backUrl = jsonObj.getString("backUrl");

//			String invitationCd = jsonObj.getString("invitationCd");
//			if(null != invitationCd && invitationCd.length() > 0){
//				userDto.setInvitationCd(invitationCd);
//			}
        } else {
            //暂定默认跳转到 实名认证页面
            backUrl = "/wxtrade/goMyAsset";
        }
        request.getSession().setAttribute("backUrl", backUrl);
        request.getSession().setAttribute("userDto", userDto);
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        //已绑定用户如果输入手机号登录，判断只能登录绑定的手机号
        Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
        String mobile = userInfoMap.get("mobile");
        if (null != userInfoMap && !"".equalsIgnoreCase(mobile) && null != mobile) {
            return "redirect:/wxtrade/goMyAsset";
        }
//		return WEIXIN_LOGIN;
        request.setAttribute("loginFlag", "1");//登录验证码，密码标识
        return WEIXIN_LOGIN_PASSWORD;
    }

    /**
     * 修改密码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(HttpServletRequest request, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String mobile = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            mobile = userInfoMap.get("mobile");
        } else {
            mobile = us.getMobile();
        }

        request.setAttribute("mobile", mobile);
        return "weixin/passwordUpdate/changePassword";
    }

    /**
     * 旧密码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/oldPassword", method = RequestMethod.GET)
    public String oldPassword(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/oldPassword";
    }

    /**
     * 密码确认
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/PWConfirm", method = RequestMethod.GET)
    public String PWConfirm(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/PWConfirm";
    }


    /**
     * 投资前准备第一步
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/preinvestmentStepOne", method = RequestMethod.GET)
    public String preinvestmentStepOne(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/preinvestmentStepOne";
    }


    /**
     * 投资前准备第二步
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/preinvestmentStepTwo", method = RequestMethod.GET)
    public String preinvestmentStepTwo(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/preinvestmentStepTwo";
    }


    /**
     * 投资前准备第三步
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/preinvestmentStepThree", method = RequestMethod.GET)
    public String preinvestmentStepThree(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/preinvestmentStepThree";
    }


    /**
     * 忘记登录密码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/forgotPW", method = RequestMethod.GET)
    public String forgotPW(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/forgotPW";
    }

    /**
     * 修改交易密码用验证码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/changePasswordVerCode", method = RequestMethod.GET)
    public String changePasswordVerCode(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/changePasswordVerCode";
    }

    /**
     * 密码升级页面
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/passwordUpdate", method = RequestMethod.GET)
    public String passwordUpdate(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/passwordUpdate";
    }

    /**
     * 交易密码更新相关
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */

    @RequestMapping(value = "/checkUserAndPasswordCash", method = RequestMethod.GET)
    public void checkUserAndPasswordCash(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String mobile = us.getMobile();
        String passwordCash = request.getParameter("passwordCash");
        String checkFlag = request.getParameter("checkFlag");

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("passwordCash", passwordCash);
        map.put("checkFlag", checkFlag);


        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/user/checkUserAndPasswordCash", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);

        setResposeMsg(jsonObject.toString(), out);

    }

    /**
     * 设置登录密码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/resetLoginPassword", method = RequestMethod.GET)
    public void resetLoginPassword(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        //String phoneNum, String passwordCash,
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");
        String setupFlag = "1";

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userName", userId);
        map.put("password", password);
        map.put("checkCode", checkCode);
        map.put("setupFlag", setupFlag);
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/user/resetPassword", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);

        setResposeMsg(jsonObject.toString(), out);

    }


    /**
     * 跳转到注册页面
     *
     * @return
     */
    @RequestMapping(value = "/goRegister", method = RequestMethod.GET)
    public String goRegister(HttpServletRequest request, HttpServletResponse res) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);
        return "weixin/accout/register";
    }

    /**
     * 修改登录密码
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/updateLoginPassword", method = RequestMethod.GET)
    public void updateLoginPassword(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        //String phoneNum, String passwordCash,
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String password = request.getParameter("password");
        String passwordOld = request.getParameter("passwordOld");
        String setupFlag = "2";

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userName", userId);
        map.put("password", password);
        map.put("setupFlag", setupFlag);
        map.put("passwordOld", passwordOld);
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/user/updatePassword", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);

        setResposeMsg(jsonObject.toString(), out);
    }


    /**
     * 密码盘设置接口初始化(新注册页面初始化)
     *
     * @param request
     * @param out
     * @param session
     * @return
     */
    @RequestMapping(value = "/goRegisterPassword", method = RequestMethod.POST)
    public String goRegisterPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        String mobileNumber = request.getParameter("mobileNumber") == null ? "" : request.getParameter("mobileNumber");
        String appMobileNumber = request.getParameter("appMobileNumber") == null ? "" : request.getParameter("appMobileNumber");
        String channel = request.getParameter("channel") == null ? "" : request.getParameter("channel");

        request.setAttribute("mobileNumber", mobileNumber);
        request.setAttribute("channel", channel);

        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            userDto = new WeiXinUserDto();
        }
        String phoneNum = request.getParameter("phoneNum");//手机号
        String checkCode = request.getParameter("checkCode");//验证码
        String password = request.getParameter("password");//登录密码

        if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
            logger.error("注册异常:请输入手机号");
            request.setAttribute("message", "请输入手机号");
            return WEIXIN_REG_PASSWORD;
        }

        request.setAttribute("phoneNum", phoneNum);

        if (null == checkCode || "".equalsIgnoreCase(checkCode)) {
            logger.error("注册异常:请输入短信验证码");
            request.setAttribute("message", "请输入短信验证码");
            return WEIXIN_REG_PASSWORD;
        }
        //登录 密码不能为空
        if (null == password || "".equalsIgnoreCase(password)) {
            logger.error("注册异常:请输入登录密码");
            request.setAttribute("message", "请输入登录密码");
            return WEIXIN_REG_PASSWORD;
        }

        Boolean checkPassword = Validator.isPassword(password);
        if (!checkPassword) {
            logger.error("注册异常:登录密码为8~16位字母数字组合");
            request.setAttribute("message", "登录密码为8~16位字母数字组合");
            return WEIXIN_REG_PASSWORD;
        }

        userDto.setPhoneNum(phoneNum);
        userDto.setCheckCode(checkCode);
        userDto.setPassword(password);
        if (!"".equals(appMobileNumber.trim()) && !"null".equalsIgnoreCase(appMobileNumber.trim())) {
            userDto.setInvitationCd(appMobileNumber);
        }
        request.getSession().setAttribute("userDto", userDto);

        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("phoneNum", phoneNum);
        userMap.put("setupFlag", "1");
        userMap.put("checkCode", checkCode);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        //验证码检查
        String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
        try {
            checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
        if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
            request.setAttribute("message", checkCaptchatObject.getString("resmsg_cn"));
            return WEIXIN_REG_PASSWORD;
        }

        //已绑定用户如果输入手机号登录，判断只能登录绑定的手机号
        Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
        String mobile = userInfoMap.get("mobile");
        if (null != userInfoMap && !"".equalsIgnoreCase(mobile) && null != mobile) {
            if (!phoneNum.equalsIgnoreCase(mobile)) {
                request.setAttribute("message", "请登录已绑定手机号");
                return WEIXIN_REG_PASSWORD;
            }
        }
        //未绑定用户如果输入手机号登录，判断当前手机号是否已被绑定其他微信
        Map<String, String> mediaUidMap = WeiXinTriggerAction.getWeixinUidBindingByMobile(phoneNum, request, response);
        String mediaUid = mediaUidMap.get("mediaUid");
        if (null != mediaUidMap && !"".equalsIgnoreCase(mediaUid) && null != mediaUid) {
            Map mediaUidMap1 = UserCookieUtil.getWeixinOpenid(request);
            String mediaUidInfo = "";
            if (null != mediaUidMap1) {
                mediaUidInfo = String.valueOf(mediaUidMap1.get("mediaUid"));
            } else {
                try {
                    request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!mediaUidInfo.equalsIgnoreCase(mediaUid)) {
                request.setAttribute("message", "您输入的手机号已绑定其他微信号");
                return WEIXIN_REG_PASSWORD;
            }
        }

        // 调用service接口
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        JSONObject user = jsonObject.getJSONObject("user");
        //用户存在
        if (user != null && !"null".equals(user.toString())) {
            request.setAttribute("message", "该手机已经注册，请直接登录");
            return WEIXIN_REG_PASSWORD;
        } else {//不存在的时候跳转到注册页面
            if ("PHICOMM".equals(channel) && StringUtils.isNotBlank("mobileNumber")) {
                session.setAttribute("channel", channel);
            }
            return "redirect:goSetExchangePasswordByReg_Reg";
        }

//		return WEIXIN_REG_PASSWORD;
    }

    /**
     * 用户密码注册
     *
     * @param request
     * @param out
     * @param session
     * @return
     */
    @RequestMapping(value = "/registerPassword", method = RequestMethod.POST)
    public String registerPassword(String passwordCash, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
        
		String token_id = (String) request.getSession().getAttribute("token_id");
		logger.info("注册token_id="+token_id);
		
		//设置设备号id
		request.setAttribute("smDeviceId", Tools.getCookieValueByName(request, "smDeviceId"));
		        
        if (userDto == null) {
            userDto = new WeiXinUserDto();
        }
        String phoneNum = userDto.getPhoneNum();//手机号
        String checkCode = userDto.getCheckCode();//短信验证码
        String password = userDto.getPassword();//登录密码

        if (null == phoneNum || "".equalsIgnoreCase(phoneNum)) {
            logger.error("注册异常:请输入手机号");
            request.setAttribute("message", "请输入手机号");
            return WEIXIN_REG_PASSWORD;
        }

        if (null == passwordCash || "".equalsIgnoreCase(passwordCash)) {
            logger.error("注册异常:请输入交易密码");
            request.setAttribute("message", "请输入交易密码");
            return WEIXIN_REG_PASSWORD;
        }


        request.setAttribute("phoneNum", phoneNum);

        if (null == checkCode || "".equalsIgnoreCase(checkCode)) {
            logger.error("注册异常:请输入短信验证码");
            request.setAttribute("message", "请输入短信验证码");
            return WEIXIN_REG_PASSWORD;
        }
        //登录 密码不能为空
        if (null == password || "".equalsIgnoreCase(password)) {
            logger.error("注册异常:请输入登录密码");
            request.setAttribute("message", "请输入登录密码");
            return WEIXIN_REG_PASSWORD;
        }

        Boolean checkPassword = Validator.isPassword(password);
        if (!checkPassword) {
            logger.error("注册异常:登录密码为8~16位字母数字组合");
            request.setAttribute("message", "登录密码为8~16位字母数字组合");
            return WEIXIN_REG_PASSWORD;
        }

        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("phoneNum", phoneNum);
        userMap.put("setupFlag", "1");
        userMap.put("checkCode", checkCode);

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("userName", phoneNum);//手机号
        map.put("passwordCash", passwordCash);//交易密码
        map.put("password", password);//登录密码
        map.put("passwordConfirm", password);//确认密码

        // 渠道
        String channel = request.getParameter("channel");
        if (StringUtil.isEmpty(channel)) {
            channel = "LBWX";
        }
        if ("PHICOMM".equals(session.getAttribute("channel"))) {
            channel = "PHICOMM";
        }

        map.put("checkCode", checkCode);//短信验证码
        map.put("channel", channel);//渠道
        map.put("setupFlag", "1");
        map.put("invitationCode", userDto.getInvitationCd() == null ? "" : userDto.getInvitationCd());
        map.put("weixinId", userDto.getWeixinId() == null ? "" : userDto.getWeixinId());
        map.put("blackBox", token_id);
        map.put("ipInfo",  CommonUtil.getClientIP(request));
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("用户注册加密失败:" + e.getMessage());
            e.printStackTrace();
        }
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/registerLoginPassword", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("用户注册解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info(LogUtil.getEnd("register", "方法结束运行", "LoginAction"));

        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        //注册成功后
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            logger.info("******************************登录成功！");
            //登录记录用户Cookie
            UserSession us = new UserSession();
            us.setId(Integer.valueOf(jsonObject.getString("userId")));
            us.setUsername(jsonObject.getString("userName"));
            us.setMobile(jsonObject.getString("mobile"));
            //登录
            UserCookieUtil.putUser(request, response, us);
            //设置登录后返回《记录页面》
            String backUrl = (String) session.getAttribute("backUrl");
            //绑定微信操作
            WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);
            session.removeAttribute("userDto");
            session.removeAttribute("channel");
            return "redirect:/wxtrade/goMyAsset";
        } else {
            request.setAttribute("message", jsonObject.getString("resmsg_cn"));
            return WEIXIN_REG_PASSWORD;
        }
    }

    /**
     * 跳转到设置交易密码页面(登录)(用于密码登录设置交易密码，与原逻辑区分开)
     *
     * @return
     */
    @RequestMapping(value = "/goSetExchangePasswordByLogin_Reg")
    public String goSetExchangePasswordByLogin_Reg(HttpServletRequest request, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            logger.error("注册异常:登录异常，因dto值为空");
            request.setAttribute("message", "非法登录，请重新登录");
            request.setAttribute("loginFlag", "1");//登录验证码，密码标识
            return WEIXIN_LOGIN_PASSWORD;
        }
        String password = String.valueOf(request.getSession().getAttribute("password"));
        request.getSession().removeAttribute("password");

        request.setAttribute("password", password);

        request.setAttribute("actionName", "setExchangePassword_reg");
        //1:修改密码 2：设置密码
        request.setAttribute("passwordState", 2);
        return WEIXIN_SETPASS;
    }

    /**
     * 跳转到设置交易密码页面（注册）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/goSetExchangePasswordByReg_Reg", method = RequestMethod.GET)
    public String goSetExchangePasswordByReg_Reg(String invitationCd, HttpServletRequest request, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            logger.error("注册异常:请重新注册");
            request.setAttribute("message", "注册异常:请重新注册");
            return WEIXIN_REG_PASSWORD;
        }
        request.setAttribute("actionName", "registerPassword");
        //1:修改密码 2：设置密码
        request.setAttribute("passwordState", 2);
        return WEIXIN_SETPASS;
    }

    /**
     * 设置交易密码(用于密码登录设置交易密码，与原逻辑区分开)
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/setExchangePassword_reg", method = RequestMethod.POST)
    public String setExchangePassword_reg(String phoneNum, String passwordCash, String password,
                                          HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        
        String token_id = (String)request.getSession().getAttribute("token_id");
        logger.info("/wxuser/setExchangePassword_reg-token_id="+token_id);
        logger.info("=======进入setExchangePassword方法");
        //phoneNum取不到时跳回登录页面
        if (phoneNum == null || phoneNum.length() == 0) {
            logger.error("注册异常:手机号不能为空");
            request.setAttribute("message", "[设置交易密码]手机号不能为空");
            return WEIXIN_REG_PASSWORD;
        }
        //交易密码验证
        if (passwordCash == null || passwordCash.length() == 0) {
            logger.error("注册异常:[设置交易密码]交易密码不能为空！");
            request.setAttribute("message", "[设置交易密码]交易密码不能为空！");
            return WEIXIN_REG_PASSWORD;
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userName", phoneNum);
        map.put("passwordCash", passwordCash);
//		map.put("checkCode", userDto.getCheckCode());
        map.put("setupFlag", "1");
        map.put("password", password);
        map.put("type", "mobile");
        map.put("loginFlag", "1");
    	map.put("blackBox", token_id);
    	map.put("ipInfo",  CommonUtil.getClientIP(request));
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/resetPassword", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            logger.info("交易密码设置成功!");
        } else {
            logger.info("交易密码设置 失败!");
            return WEIXIN_CHANGPASS;
        }
        UserSession userSession = UserCookieUtil.getUser(request);
        String backUrl = (String) session.getAttribute("backUrl");
        if (backUrl == null || backUrl.length() == 0) {
            backUrl = "/wxtrade/goMyAsset";
        }
        if (userSession == null) {
            //登录
            String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login", param);
            try {
                loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
            } catch (Exception e) {
                logger.info("登陆解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            JSONObject loginObject = JSONObject.fromObject(loginMsg);
            if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
                System.out.println("登陆成功！");
                UserSession us = new UserSession();
                us.setId(Integer.valueOf(loginObject.getString("userId")));
                us.setUsername(loginObject.getString("userName"));
                us.setMobile(loginObject.getString("mobile"));
                //登录
                UserCookieUtil.putUser(request, response, us);

                WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
                if (null == userDto) {
                    userDto = new WeiXinUserDto();
                }
                userDto.setPhoneNum(phoneNum);
                //绑定微信操作
                WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);
                session.removeAttribute("userDto");
                return "redirect:" + backUrl;
            } else {
                request.setAttribute("message", jsonObject.getString("resmsg_cn"));
                request.setAttribute("loginFlag", "1");//登录验证码，密码标识
                return WEIXIN_LOGIN_PASSWORD;
            }
        } else {
            session.removeAttribute("backUrl");
            session.removeAttribute("userDto");
            return "redirect:" + backUrl;
        }
    }

    /**
     * 旧的登录密码
     *
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/goOldLoginPassword", method = RequestMethod.GET)
    public String goOldLoginPassword(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/oldLoginPassword";
    }

    /**
     * 修改登录密码页面
     *
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/goAmendLoginPassword", method = RequestMethod.GET)
    public String goAmendLoginPassword(HttpServletRequest request, HttpServletResponse res) {
        return "weixin/passwordUpdate/amendLoginPassword";
    }

    /**
     * 设置登录密码页面
     *
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/goSetLoginPassword", method = RequestMethod.GET)
    public String goSetLoginPassword(HttpServletRequest request, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String mobile = "";
        if (null == us || null == us.getMobile()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            mobile = userInfoMap.get("mobile");
        } else {
            mobile = us.getMobile();
        }
        request.setAttribute("mobile", mobile);

        return "weixin/passwordUpdate/setLoginPassword";
    }


    /**
     * 调用发送验证码接口
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/goResetPasswordCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> goResetPasswordCaptcha(HttpServletRequest request,
                                                      HttpServletResponse response, HttpSession session) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);

        if ("Y".equals(LoginRedirectUtil.YUNDUN_CAPTCHA_SWITCH)) {
            String captchaId = request.getParameter("captchaId");
            String captchaValidate = request.getParameter("NECaptchaValidate");
            boolean validate = YunDunCaptcha.validate(captchaId, captchaValidate);
            if (!validate) {
                modelMap.put("rescode", Consts.CHECK_CODE);
                modelMap.put("resmsg_cn", "请完成图形验证码！");
                modelMap.put("error", "true");
                return modelMap;
            }
        }
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        String phoneNum = request.getParameter("phoneNum");

        String type = request.getParameter("type");
        if(StringUtils.isBlank(type)){
			type="RESETPASSWORD";
		}else{
			if(type.equalsIgnoreCase("resetTradePassword"))
				type="RESET_TRADE_PASSWORD";
			else
				type=type.toUpperCase();
		}

        
    	String token_id = (String) request.getSession().getAttribute("token_id");
    	String ipInfo=CommonUtil.getClientIP(request);
    	logger.info("网站注册token_id="+token_id+"ipInfo="+ipInfo);

        logger.info("=========go-phoneNum:" + phoneNum);
        Map<String, String> map = getCaptcha(phoneNum, phoneNum, type,token_id,ipInfo);
        if (map.get("rescode").equals(Consts.SUCCESS_CODE)) {
            modelMap.put("rescode", Consts.SUCCESS_CODE);
            modelMap.put("resmsg_cn", "");
            modelMap.put("success", "true");
        } else {
            modelMap.put("rescode", Consts.CHECK_CODE);
            modelMap.put("resmsg_cn", map.get("resmsg_cn"));
            modelMap.put("error", "true");
        }
        return modelMap;
    }


    /**
     * 登陆
     *
     * @param request 接收网站传过来的数据
     * @return
     */
    @RequestMapping(value = "/inviteLogin", method = RequestMethod.POST)
    public String inviteLogin(String phoneNum, String checkCode, String password, String loginFlag, HttpServletRequest request,
                              HttpServletResponse response, HttpSession session) {

    	 
    	 String token_id = (String)request.getSession().getAttribute("token_id");
    	 logger.info("/wxuser/inviteLogin-token_id="+token_id);



        //分享验签机制
        WeixinRquestUtil.getSignature(request);
        logger.info("登录画面传过来的值:phoneNum=" + phoneNum + "checkCode=" + checkCode + "password=");
        //TODO
        //一、
        //1、判断当前微信唯一标示openid是否已绑定过平台用户userid,如果已绑定提示用户已绑定平台用户
        //2、如果未绑定判断用户是否已注册（1）已注册未实名用户，做实名认证后实现绑定(2)未注册说明未实名认证，做实名认证 ，实现绑定功能(3)对于已经实名认证的用户登录，直接实现绑定功能
        //二.判断手机号是否已被微信绑定，如果该手机号已绑定通知用户该手机号已绑定其他微信用户

        WeiXinUserDto userDto = (WeiXinUserDto) session.getAttribute("userDto");
        if (userDto == null) {
            userDto = new WeiXinUserDto();
        }
        if (null == checkCode || "".equals(checkCode)) {
            request.setAttribute("message", "验证码不能为空！");
            return WEIXIN_LOGIN;
        }
        userDto.setPhoneNum(phoneNum);
        userDto.setCheckCode(checkCode);

        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("phoneNum", phoneNum);
        userMap.put("setupFlag", "1");
        userMap.put("checkCode", checkCode);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        //验证码检查
        String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
        try {
            checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
        if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
            request.setAttribute("message", checkCaptchatObject.getString("resmsg_cn"));
            return WEIXIN_LOGIN;
        }

        //已绑定用户如果输入手机号登录，判断只能登录绑定的手机号
        Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
        String mobile = userInfoMap.get("mobile");
        if (null != userInfoMap && !"".equalsIgnoreCase(mobile) && null != mobile) {
            if (!phoneNum.equalsIgnoreCase(mobile)) {
                request.setAttribute("message", "请登录已绑定手机号");
                return WEIXIN_LOGIN;
            }
        }
        //未绑定用户如果输入手机号登录，判断当前手机号是否已被绑定其他微信
        Map<String, String> mediaUidMap = WeiXinTriggerAction.getWeixinUidBindingByMobile(phoneNum, request, response);
        String mediaUid = mediaUidMap.get("mediaUid");
        if (null != mediaUidMap && !"".equalsIgnoreCase(mediaUid) && null != mediaUid) {
            Map mediaUidMap1 = UserCookieUtil.getWeixinOpenid(request);
            String mediaUidInfo = "";
            if (null != mediaUidMap1) {
                mediaUidInfo = String.valueOf(mediaUidMap1.get("mediaUid"));
            } else {
                try {
                    request.getRequestDispatcher("/wxTrigger/getWxCode?actionScope=L3d4dXNlci9nb0xvZ2lu").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!mediaUidInfo.equalsIgnoreCase(mediaUid)) {
                request.setAttribute("message", "您输入的手机号已绑定其他微信号");
                return WEIXIN_LOGIN;
            }
        }

        // 调用service接口
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        JSONObject user = jsonObject.getJSONObject("user");
        //用户存在
        if (user != null && !"null".equals(user.toString())) {
            String passwordCash = user.getString("password_cash");
            //非首次登录
            if (passwordCash != null && passwordCash.length() > 0) {
                logger.info(LogUtil.getStart("goLogin", "方法开始执行", "LoginAction", getProjetUrl(request)));

                Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
                loginMap.put("userName", userDto.getPhoneNum());
                loginMap.put("setupFlag", "1");
                loginMap.put("type", "mobile");
                loginMap.put("checkCode", userDto.getCheckCode());
                loginMap.put("blackBox", token_id);
                loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
                String loginParam = CommonUtil.getParam(loginMap);
                try {
                    loginParam = DES3Util.encode(loginParam);
                } catch (Exception e) {
                    logger.info("登陆加密失败:" + e.getMessage());
                    e.printStackTrace();
                }

                String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login", loginParam);
                logger.info("接受的javaservice login url：[" + LoginRedirectUtil.interfacePath + "/wxuser/login" + "]");
                logger.info("接受登录参数：[" + loginMsg + "]");
                try {
                    loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.info("登陆解密失败:" + e.getMessage());
                    e.printStackTrace();
                }

                logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));

                JSONObject loginObject = JSONObject.fromObject(loginMsg);
                if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
                    System.out.println("登陆成功！");
                    UserSession us = new UserSession();
                    us.setId(Integer.valueOf(loginObject.getString("userId")));
                    us.setUsername(loginObject.getString("userName"));
                    us.setMobile(loginObject.getString("mobile"));
                    //登录
                    UserCookieUtil.putUser(request, response, us);

                    String backUrl = (String) session.getAttribute("backUrl");
                    if (backUrl == null) {
                        backUrl = "/wxtrade/goMyAsset";
                    }
                    //绑定微信操作
                    WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);

                    session.removeAttribute("userDto");
                    logger.info(LogUtil.getEnd("goLogin", "方法结束运行", "LoginAction"));
                    return "redirect:" + backUrl;
                } else {
                    request.setAttribute("message", jsonObject.getString("resmsg_cn"));
                    return WEIXIN_LOGIN;
                }
            } else {
                //绑定微信操作
                WeiXinTriggerAction.binDingWeiXinUidUserId(request, response, userDto);
                return "redirect:goSetExchangePasswordByLogin";
            }
            //不存在的时候跳转到注册页面
        } else {
            return "redirect:goSetExchangePasswordByReg";
        }
    }

    @RequestMapping(value = "/goVoucher", method = RequestMethod.GET)
    public String goVoucher(HttpServletRequest request, HttpServletResponse res) {
        //分享验签机制
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }
        WeixinRquestUtil.getSignature(request);
        String acctBalance = request.getParameter("acctBalance");
        String adjustRate = request.getParameter("adjustRate");

        request.setAttribute("acctBalance", acctBalance);
        request.setAttribute("adjustRate", adjustRate);
        return "weixin/accout/voucher";
    }

    /**
     * 手机k码获取验证码
     *
     * @param request
     * @return response
     * @throws Exception
     */
    @RequestMapping(value = "/goGetCaptchaByKcode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> goGetCaptchaByKcode(HttpServletRequest request,
                                                   HttpServletResponse response, HttpSession session) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        logger.info("=========进入webservice goGetCaptchaByKcode方法：");
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        String type=request.getParameter("type");
        if(StringUtils.isBlank(type)){
			type="WXLOGIN";
		}else{
			if(type.equalsIgnoreCase("resetTradePassword"))
				type="RESET_TRADE_PASSWORD";
			else
				type=type.toUpperCase();
		}

        String phoneNum = request.getParameter("phoneNum");
        String setupFlag = request.getParameter("setupFlag");
        String smsType = request.getParameter("smsType");//发送验证码类别1:短信，2：语音
        logger.info("=========goGetCaptchaByKcode-phoneNum：" + phoneNum);

        Map<String, Object> paraMap = new HashMap<String, Object>(3);
        paraMap.put("phoneNum", phoneNum);
        paraMap.put("type", type);
        paraMap.put("setupFlag", setupFlag);
        paraMap.put("smsType", smsType);
        String param = CommonUtil.getParam(paraMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        //手机号码验证检查
        String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/captchaByKcode", param);
        try {
            checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
        if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
            modelMap.put("rescode", Consts.CHECK_CODE);
            modelMap.put("resmsg_cn", checkCaptchatObject.getString("resmsg_cn"));
            return modelMap;
        } else {
            modelMap.put("rescode", Consts.SUCCESS_CODE);
            modelMap.put("resmsg_cn", checkCaptchatObject.getString("resmsg_cn"));
        }
        return modelMap;
    }


    /**
     * 校验手机号码
     *
     * @param request
     * @return response
     * @throws Exception
     */
    @RequestMapping(value = "/checkMoblie", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkMoblie(HttpServletRequest request,
                                           HttpServletResponse response, HttpSession session) {
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        logger.info("=========进入webservice checkMoblie方法：");
        //分享验签机制
        WeixinRquestUtil.getSignature(request);

        String phoneNum = request.getParameter("phoneNum");
        String checkCode = request.getParameter("checkCode");

        logger.info("=========checkMoblie-phoneNum:" + phoneNum);
        logger.info("=========checkMoblie-checkCode:" + checkCode);

        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("phoneNum", phoneNum);
        userMap.put("setupFlag", "1");
        userMap.put("checkCode", checkCode);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        //验证码检查
        String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", param);
        try {
            checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info("========验证码检查返回result:" + checkCaptchatMsg);

        JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
        if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
            modelMap.put("rescode", Consts.CHECK_CODE);
            modelMap.put("resmsg_cn", checkCaptchatObject.getString("resmsg_cn"));
            return modelMap;
        }

        //手机号码检查
        String checkPhoneMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/checkMoblie", param);
        try {
            checkPhoneMsg = java.net.URLDecoder.decode(DES3Util.decode(checkPhoneMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        logger.info("========手机号码检查返回result:" + checkPhoneMsg);

        JSONObject checkPhoneObject = JSONObject.fromObject(checkPhoneMsg);
        if (!Consts.SUCCESS_CODE.equals(checkPhoneObject.getString("rescode"))) {
            modelMap.put("rescode", Consts.CHECK_CODE);
            modelMap.put("resmsg_cn", checkPhoneObject.getString("resmsg_cn"));
            return modelMap;
        }

        modelMap.put("rescode", Consts.SUCCESS_CODE);
        modelMap.put("resmsg_cn", "");
        return modelMap;
    }

    /**
     * 活动文案接口
     *
     * @param request
     * @param res
     * @param session
     * @param out
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/activityRecord")
    public void activityRecord(HttpServletRequest request, HttpServletResponse res, HttpSession session, PrintWriter out) {

        JSONObject json = new JSONObject();
        Map<String, Object> reqJavaMap = new HashMap<String, Object>();

        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        reqJavaMap.put("userId", userId);
        String param = CommonUtil.getParam(reqJavaMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("活动文案信息加密失败:" + e);
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/invitation/getActivityRecord", param);

        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                    "UTF-8");
        } catch (Exception e) {
            logger.info("活动文案信息解密失败:" + e);
            e.printStackTrace();
        }
        setResposeMsg(resultMsg, out);
    }


    /**
     * 提示信息接口
     *
     * @param request
     * @param out
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/promptInfo")
    public void promptInfo(HttpServletRequest request, PrintWriter out, HttpServletResponse response)
            throws Exception {
        String logInfo = null;
        String pointResultStr = null;
        //初始化
        JSONObject resultMap = new JSONObject();

        try {

            UserSession userSession = this.getUserSession(request, response);
            int userId = userSession.getId();//用户ID(必填)
            String phone = userSession.getMobile();//手机号
            logInfo = "提示信息接口phone=" + phone + "userID=" + userId;
            logger.info(logInfo);

            if (userId == 0 || StringUtils.isEmpty(phone)) {
                logger.info(logInfo + "获得不到userId" + userId);
                resultMap.put("resmsg_cn", "您还未登录，请登录后操作");
                resultMap.put("rescode", Consts.CHECK_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }

            logger.info(logInfo + "调用shop系统");
            Map<String, Object> userMap = new LinkedHashMap<String, Object>();
            userMap.put("userId", userId);
            userMap.put("mobile", phone);


            String param = DES3Util.encode(CommonUtil.getParam(userMap));

            pointResultStr = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/promptInfo", param);
            pointResultStr = java.net.URLDecoder.decode(DES3Util.decode(pointResultStr), "UTF-8");

            logger.info(logInfo + " shop系统返回结果:" + pointResultStr);


            if (null == pointResultStr) {
                resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode", Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                return;
            }

            try {

                JSONObject.fromObject(pointResultStr);

            } catch (Exception e) {
                resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode", Consts.ERROR_CODE);
                setResposeMsg(resultMap.toString(), out);
                logger.error(logInfo + " shop系统返回异常pointResultStr=" + pointResultStr);
                logger.info(logInfo + "  结束");
                return;

            }

            logger.info(logInfo + " 正常返回" + pointResultStr);

        } catch (Exception e) {

            resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            setResposeMsg(resultMap.toString(), out);
            logger.error(logInfo + "接口  error: 系统异常", e);
            logger.info(logInfo + "  结束");
            return;
        }
        setResposeMsg(pointResultStr, out);
    }


    /**
     * 修改交易密码（新版）
     *
     * @param request
     * @param res
     * @param session
     * @param out
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/updateTradePwd")
    public void updateTradePwd(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        String oldTradePwd = null;
        String newTradePwd = null;
        String channel = null;
        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }
        oldTradePwd = request.getParameter("oldTradePwd");
        newTradePwd = request.getParameter("newTradePwd");
        channel = request.getParameter("channel");
        reqMap.put("userId", userId);
        reqMap.put("oldTradePwd", oldTradePwd);
        reqMap.put("newTradePwd", newTradePwd);
        reqMap.put("channel", channel);
        String param = CommonUtil.getParam(reqMap);
        logger.info("updateTradePwd 请求参数：" + userId + "|" + channel);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("活动文案信息加密失败:" + e);
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/updateTradePwd", param);

        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                    "UTF-8");
        } catch (Exception e) {
            logger.info("活动文案信息解密失败:" + e);
            e.printStackTrace();
        }
        setResposeMsg(resultMsg, out);
    }


    /**
     * 修改交易密码（新版）
     *
     * @param request
     * @param res
     * @param session
     * @param out
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/resetTradePwd")
    public void resetTradePwd(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        UserSession us = UserCookieUtil.getUser(request);
        String userId = "";
        String idCard = null;
        String newTradePwd = null;
        String checkCode = null;
        String channel = null;
        String setupFlag = "1";

        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        idCard = request.getParameter("idCard");
        newTradePwd = request.getParameter("newTradePwd");
        checkCode = request.getParameter("checkCode");
        channel = request.getParameter("channel");

        reqMap.put("userId", userId);
        reqMap.put("newTradePwd", newTradePwd);
        reqMap.put("idCard", idCard);
        reqMap.put("checkCode", checkCode);
        reqMap.put("channel", channel);
        reqMap.put("setupFlag", setupFlag);

        String param = CommonUtil.getParam(reqMap);
        logger.info("updateTradePwd 请求参数：" + userId + "|" + channel + "|" + idCard + "|" + checkCode);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("活动文案信息加密失败:" + e);
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/resetTradePwd", param);

        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                    "UTF-8");
        } catch (Exception e) {
            logger.info("活动文案信息解密失败:" + e);
            e.printStackTrace();
        }
        setResposeMsg(resultMsg, out);
    }
    
    @RequestMapping(value = "/loginPasswordCheck")
	public void loginPasswordCheck(String phoneNum,String password,HttpServletRequest request, HttpServletResponse response,
			HttpSession session, PrintWriter out) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");

		String token_id = (String) request.getSession().getAttribute("token_id");
		logger.info("注册密码验证token_id=" + token_id);
		// 设置设备号id
		request.setAttribute("smDeviceId", Tools.getCookieValueByName(request, "smDeviceId"));

		if (userDto == null) {
			userDto = new WeiXinUserDto();
		}

		JSONObject jsonObject = new JSONObject();
		if (!StringUtils.isNotBlank(phoneNum)) {
			logger.error("注册密码验证:请输入手机号");
			jsonObject.put("rescode",  Consts.ERROR_CODE);
			jsonObject.put("resmsg_cn", "请输入手机号");
			
			setResposeMsg(jsonObject.toString(), out);
			return;
		}


		request.setAttribute("phoneNum", phoneNum);

		// 登录 密码不能为空
		if (!StringUtils.isNotBlank(password)) {
			logger.error("注册密码验证:请输入登录密码");
			jsonObject.put("rescode",  Consts.ERROR_CODE);
			jsonObject.put("resmsg_cn", "请输入登录密码");
			
			setResposeMsg(jsonObject.toString(), out);
			return;
		}

		Boolean checkPassword = Validator.isPassword(password);
		if (!checkPassword) {
			logger.error("注册密码验证:登录密码为8~16位字母数字组合");
			jsonObject.put("rescode",  Consts.ERROR_CODE);
			jsonObject.put("resmsg_cn", "登录密码为8~16位字母数字组合");
			
			setResposeMsg(jsonObject.toString(), out);
			return;
		}

		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		userMap.put("phoneNum", phoneNum);
		userMap.put("setupFlag", "1");

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		map.put("userName", phoneNum);// 手机号
		map.put("password", password);// 登录密码
		map.put("passwordConfirm", password);// 确认密码

		// 渠道
		String channel = request.getParameter("channel");
		if (StringUtil.isEmpty(channel)) {
			channel = "LBWX";
		}
		if ("PHICOMM".equals(session.getAttribute("channel"))) {
			channel = "PHICOMM";
		}

		map.put("channel", channel);// 渠道
		map.put("setupFlag", "1");
		map.put("invitationCode", userDto.getInvitationCd() == null ? "" : userDto.getInvitationCd());
		map.put("weixinId", userDto.getWeixinId() == null ? "" : userDto.getWeixinId());
		map.put("blackBox", token_id);
		map.put("ipInfo", CommonUtil.getClientIP(request));
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户注册加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/loginPasswordCheckBefore",
				param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("用户注册解密失败:" + e.getMessage());
			e.printStackTrace();
		}

		logger.info(LogUtil.getEnd("register", "方法结束运行", "LoginAction"));

		setResposeMsg(resultMsg, out);
	}
}
