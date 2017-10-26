package com.web.actions.weixin.anniversary;

import com.alibaba.fastjson.JSONObject;
import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.*;
import com.web.util.weixinAbout.WeixinRquestUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wxAnniversary")
public class AnniversaryAction extends BaseAction {

    private static final Log logger = LogFactory.getLog(AnniversaryAction.class);

    @RequestMapping(value = "/toAnniversary")
    public String toAnniversary(HttpServletRequest request, HttpServletResponse response) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId, mobile;
        if (null == us || null == us.getId()) {
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
            userId = userInfoMap.get("userId");
            mobile = userInfoMap.get("mobile");
        } else {
            userId = String.valueOf(us.getId());
            mobile = us.getMobile();
        }
        if (StringUtil.isNotEmpty(userId)) {
            request.setAttribute("userId", userId);
        }
        if (StringUtil.isNotEmpty(mobile)) {
            request.setAttribute("mobile", mobile);
        }
        WeixinRquestUtil.getSignature(request);
        return "weixin/anniversary/anniversary";
    }

    @RequestMapping(value = "/toAnniversaryShared")
    public String toAnniversaryShared(HttpServletRequest request, HttpServletResponse response) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId;
        if (null == us || null == us.getId()) {
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }
        if (StringUtil.isNotEmpty(userId)) {
            request.setAttribute("userId", userId);
        }
        WeixinRquestUtil.getSignature(request);
        return "weixin/anniversary/anniversaryShared";
    }

    @RequestMapping(value = "/anniversary")
    public void anniversary(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logInfo = "/wxAnniversary/anniversary";
        logger.info(logInfo + "=======test=====");
        JSONObject resultMap = new JSONObject();
        try {
            String mobile = request.getParameter("mobile");
            HashMap<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("mobile", mobile);
            String param = CommonUtil.getParam(userMap);
            logger.info(logInfo + param);
            try {
                param = DES3Util.encode(param);
            } catch (Exception e) {
                logger.info("参数加密失败", e);
            }
            String result = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/anniversary/anniversary", param);
            if (StringUtils.isBlank(result)) {
                logger.info(logInfo + " /anniversary/anniversary调用失败  ####");
                resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
                resultMap.put("rescode", Consts.ERROR_CODE);
                outWriter(response, resultMap.toString());
                return;
            }
            try {
                result = java.net.URLDecoder.decode(
                        DES3Util.decode(result), "UTF-8");
            } catch (Exception e) {
                logger.error("解密失败:" + e);
                e.printStackTrace();
            }
            logger.info(logInfo + " 返回结果" + result);
            outWriter(response, result);
        } catch (Exception e) {
            resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
            resultMap.put("rescode", Consts.ERROR_CODE);
            outWriter(response, resultMap.toString());
        }
    }
}

	
