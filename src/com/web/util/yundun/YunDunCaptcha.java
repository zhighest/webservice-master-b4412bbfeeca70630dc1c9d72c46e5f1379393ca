package com.web.util.yundun;

import com.alibaba.fastjson.JSONObject;
import com.web.util.*;
import com.web.util.weixinAbout.URLEncodeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易云盾验证码
 * Created by haoyx on 2017/4/11.
 */
public class YunDunCaptcha {

    private static final String logInfo = "云盾验证码-";
    private static final Logger logger = Logger.getLogger(YunDunCaptcha.class);

    public static boolean validate(String captchaId, String captchaValidate) {
        if (StringUtils.isBlank(captchaId) || StringUtils.isBlank(captchaValidate))
            return false;
        captchaValidate = URLEncodeUtils.encodeURL(captchaValidate);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("captchaId", captchaId);
        paramMap.put("NECaptchaValidate", captchaValidate);
        logger.info(logInfo + "captchaId" + captchaId + "NECaptchaValidate" + captchaValidate);
        String param = CommonUtil.getParam(paramMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info(logInfo + "加密失败", e);
            return false;
        }
        String result = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath +
                "/wxuser/yunDunCaptchaVerifier", param);
        try {
            result = java.net.URLDecoder.decode(DES3Util.decode(result), "UTF-8");
            logger.info(logInfo + "result" + result);
        } catch (Exception e) {
            logger.info(logInfo + "解密失败", e);
            return false;
        }
        return verifyRet(result);
    }

    public static boolean validate(HttpServletRequest request) {
        String captchaId = request.getParameter("captchaId");
        String captchaValidate = request.getParameter("NECaptchaValidate");
        return validate(captchaId, captchaValidate);
    }

    private static boolean verifyRet(String resp) {
        if (StringUtils.isEmpty(resp)) {
            return false;
        }
        try {
            JSONObject result = JSONObject.parseObject(resp);
            return Consts.SUCCESS_CODE.equals(result.getString("rescode"));
        } catch (Exception e) {
            return false;
        }
    }
}
