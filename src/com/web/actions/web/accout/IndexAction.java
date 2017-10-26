package com.web.actions.web.accout;

import com.web.actions.weixin.common.BaseAction;
import com.web.domain.UserSession;
import com.web.util.*;
import com.web.util.llpay.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webindex")

public class IndexAction extends BaseAction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(IndexAction.class);

    @RequestMapping(value = "/goIndex", method = RequestMethod.GET)
    public String goTopUpcz(HttpServletRequest request,
                            HttpServletResponse res, HttpSession session) {
        UserSession us = UserCookieUtil.getUser(request);

        if (us != null && us.getId() != null) {

            String userId = String.valueOf(us.getId());
            /**
             * 封装接口参数
             * */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userId);

            logger.info(LogUtil.getStart("showAssets", "方法开始执行",
                    "WeixinTradeAction", getProjetUrl(request)));
            logger.info("userId=" + userId);

            String param = CommonUtil.getParam(map);
            try {
                param = DES3Util.encode(param);
            } catch (Exception e) {
                logger.info("我的资产总览加密失败:" + e.getMessage());
                e.printStackTrace();
            }

            String resultMsg = HttpRequestParam.sendGet(
                    LoginRedirectUtil.interfacePath + "/assets/showAssets", param);
            try {
                resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
                        "UTF-8");
            } catch (Exception e) {
                logger.info("我的资产总览解密失败:" + e.getMessage());
                e.printStackTrace();
            }
            Map<String, Object> jsonMap = CommonUtil.jsonObjToHashMap(resultMsg);
            request.setAttribute("totalAssets", jsonMap.get("totalAssets"));
            request.setAttribute("incomeAmount", jsonMap.get("incomeAmount"));
            request.setAttribute("balanceMoney", jsonMap.get("balanceMoney"));
            request.setAttribute("realName", jsonMap.get("realName"));
            request.setAttribute("phoneNo", jsonMap.get("phoneNo"));
        }

        return "web/index";
    }

    /**
     * 获取我的账户信息
     *
     * @param request
     * @return response
     * @throws Exception
     */
    @RequestMapping(value = "/queryMyAccountDetail")
    public void queryMyAccountDetail(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        logger.info("======进入方法：webindex/queryMyAccountDetail====");
        UserSession us = UserCookieUtil.getUser(request);
        String userId = String.valueOf(us.getId());
        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("userId", userId);
        userMap.put("version", "1.0.0");//默认设置为1.0.0
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("获取我的账户信息加密失败:" + e.getMessage());
        }
        // 调用service接口
        String resultMsg = HttpRequestParam.sendGet(
                LoginRedirectUtil.interfacePath + "/deposit/queryMyAccountDetail", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("获取我的账户信息解密失败:" + e.getMessage());
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        setResposeMsg(jsonObject.toString(), out);
    }

    @RequestMapping(value = "/goHelpCenterQA", method = RequestMethod.GET)
    public String goHelpCenterQA(HttpServletRequest request,
                                 HttpServletResponse res, HttpSession session) {
        return "web/help/helpCenterQA";
    }

    @RequestMapping(value = "/goHelpCenter", method = RequestMethod.GET)
    public String goHelpCenter(HttpServletRequest request,
                               HttpServletResponse res, HttpSession session) {
        return "web/help/helpCenter";
    }

    /**
     * 用户获取收益率查询
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/getYield", method = RequestMethod.GET)
    public void getYield(PrintWriter out, String current, HttpServletRequest request, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId = String.valueOf(us.getId());
        String dayNum = request.getParameter("dayNum");
        if (null == dayNum || "".equalsIgnoreCase(dayNum)) {
            dayNum = "7";
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userId", userId);
        map.put("dayNum", dayNum);

        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("加密失败:" + e.getMessage());
            e.printStackTrace();
        }

        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/chart/getYield", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("解密失败:" + e.getMessage());
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);

        setResposeMsg(jsonObject.toString(), out);

    }

    @RequestMapping(value = "/goDemandProperty", method = RequestMethod.GET)
    public String goDemandProperty(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
        UserSession us = UserCookieUtil.getUser(request);
        if (us == null) {
            request.setAttribute("userIdFlag", "N");
            return "web/accout/demandProperty";
        }
        // 页面跳转标志
        request.setAttribute("pageTag", "productIndex");
        String userId = String.valueOf(us.getId());
        String mobile = String.valueOf(us.getMobile());
        request.setAttribute("mobile", mobile);

        // 昨日收益
        request.setAttribute("yesterdayGain", "0.00");
        // 在投收益
        request.setAttribute("currentIncome", "0.00");
        // 在投金额
        request.setAttribute("accountAmount", "0.00");
        // 累计收益
        request.setAttribute("incomeTotal", "0.00");
        // 年化收益率
        request.setAttribute("annualizedReturnRate", "0");
        //浮动加息
        request.setAttribute("adjustRate", "0");
        // 定期资产总额
        request.setAttribute("investmentAssets", "0.00");
        // 定期预期收益
        request.setAttribute("expectAmount", "0.00");
        // 定期累计收益
        request.setAttribute("termIncomeAmount", "0.00");
        //当日加息利率
        request.setAttribute("couponsRateRises", "0");
        //签到加息利率（新增与加息券拆分开）
        request.setAttribute("signInRateRises", "0");

        if (StringUtils.isBlank(userId)) {
            request.setAttribute("userIdFlag", "N");
            return "web/accout/demandProperty";
        } else {
            request.setAttribute("userIdFlag", "Y");
        }
        logger.info("调用零钱计划service接口");
        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("userId", userId == null ? "" : userId);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
        }

        // 调用活期service接口
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/deposit/showIndex", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        if (Consts.SUCCESS_CODE.equals(jsonObject.getString("rescode"))) {
            // 昨日收益
            request.setAttribute("yesterdayGain", jsonObject.getString("yesterdayGain"));
            // 在投收益
            request.setAttribute("currentIncome", jsonObject.getString("currentIncome"));
            // 在投金额
            request.setAttribute("accountAmount", jsonObject.getString("accountAmount"));
            // 累计收益
            request.setAttribute("incomeTotal", jsonObject.getString("incomeTotal"));
            // 年化收益率
            request.setAttribute("annualizedReturnRate", jsonObject.getString("annualizedReturnRate"));
            //当日加息利率
            request.setAttribute("couponsRateRises", jsonObject.getString("couponsRateRises"));
            //签到加息利率
            request.setAttribute("signInRateRises", jsonObject.getString("signInRateRises"));
            //浮动加息
            request.setAttribute("adjustRate", jsonObject.getString("adjustRate"));
        }
        return "web/accout/demandProperty";
    }

    /**
     * 用户获取收益率查询
     *
     * @param out
     * @param current
     * @param request
     * @param res
     */
    @RequestMapping(value = "/historyAmount", method = RequestMethod.POST)
    public void historyAmount(PrintWriter out, HttpServletRequest request, HttpServletResponse res) {
        UserSession us = UserCookieUtil.getUser(request);
        String userId = String.valueOf(us.getId());
        logger.info("用户获取收益率:userId=" + userId);
        String pageSize = request.getParameter("pageSize");
        String currentPage = request.getParameter("current");
        if (pageSize == null || StringUtils.isBlank(pageSize) || "0".equals(pageSize)) {
            pageSize = "10";
        }
        if (currentPage == null || StringUtils.isBlank(currentPage) || "0".equals(currentPage)) {
            currentPage = "1";
        }
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("userId", userId);
        map.put("current", currentPage);
        map.put("pageSize", pageSize);
        String param = CommonUtil.getParam(map);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.error("userId=" + userId + ",加密失败:", e);
            e.printStackTrace();
        }
        String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/chart/historyAmount", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
            logger.error("/chart/historyAmount返回结果" + resultMsg);
        } catch (Exception e) {
            logger.error("userId=" + userId + ",解密失败:", e);
            e.printStackTrace();
        }
        JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
        String totalNum = jsonObjRtn.getString("totalNum");
        List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PageUtils pageObject = new PageUtils();
        if (null != listDetail && listDetail.size() != 0) {
            pageObject = PageUtil.execsPage(Integer.parseInt(currentPage), Integer.parseInt(totalNum), 5, Integer.parseInt(pageSize));
        }
        resultMap.put("sysdate", DateUtil.getCurrentDateStr());
        resultMap.put("pageObject", pageObject);
        resultMap.put("list", listDetail);
        resultMap.put("rescode", jsonObjRtn.getString("rescode"));
        setResposeMap(resultMap, out);
    }

    @RequestMapping(value = "/goFixedProperty", method = RequestMethod.GET)
    public String goFixedProperty(HttpServletRequest request, HttpServletResponse res, HttpSession session) {
        UserSession us = UserCookieUtil.getUser(request);
        if (us == null) {
            request.setAttribute("userIdFlag", "N");
            return "web/accout/fixedProperty";
        }
        String userId = "";
        String mobile = "";
        userId = String.valueOf(us.getId());
        mobile = String.valueOf(us.getMobile());
        request.setAttribute("userId", userId);
        request.setAttribute("mobile", mobile);
        // 定期资产总额
        request.setAttribute("investmentAssets", "0.00");
        // 定期预期收益
        request.setAttribute("expectAmount", "0.00");
        // 定期累计收益
        request.setAttribute("termIncomeAmount", "0.00");

        if (StringUtils.isBlank(userId)) {
            request.setAttribute("userIdFlag", "N");
            return "web/accout/fixedProperty";
        } else {
            request.setAttribute("userIdFlag", "Y");
        }
        Map<String, Object> userMap = new LinkedHashMap<String, Object>();
        userMap.put("userId", userId == null ? "" : userId);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
        }

        // 调用定期service接口
        String termResultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/deposit/showTermIndex", param);
        try {
            termResultMsg = java.net.URLDecoder.decode(DES3Util.decode(termResultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
        }
        JSONObject termJsonObject = JSONObject.fromObject(termResultMsg);
        if (Consts.SUCCESS_CODE.equals(termJsonObject.getString("rescode"))) {
            // 定期资产总额
            request.setAttribute("investmentAssets", termJsonObject.getString("investmentAssets"));
            // 定期预期收益
            request.setAttribute("expectAmount", termJsonObject.getString("expectAmount"));
            // 定期累计收益
            request.setAttribute("termIncomeAmount", termJsonObject.getString("termIncomeAmount"));
        }
        return "web/accout/fixedProperty";
    }

    @RequestMapping(value = "/goRealnameApprove", method = RequestMethod.GET)
    public String goRealnameApprove(HttpServletRequest request,
                                    HttpServletResponse res, HttpSession session) {
        return "web/accout/realnameApprove";
    }

    @RequestMapping(value = "/goMediaDetail", method = RequestMethod.GET)
    public String goMediaDetial(String noticeId, HttpServletRequest request,
                                HttpServletResponse res, HttpSession session) {

        request.setAttribute("noticeId", noticeId);
        return "web/media/mediaDetail";
    }

    @RequestMapping(value = "/goActivityDetail", method = RequestMethod.GET)
    public String goActivityDetail(String noticeId, HttpServletRequest request,
                                   HttpServletResponse res, HttpSession session) {

        request.setAttribute("noticeId", noticeId);
        return "web/activity/activityDetail";
    }

    @RequestMapping(value = "/goContactUs", method = RequestMethod.GET)
    public String goContactUs(HttpServletRequest request,
                              HttpServletResponse res, HttpSession session) {
        return "web/contactUs";
    }

    @RequestMapping(value = "/goTopUp", method = RequestMethod.GET)
    public String goTopUp(HttpServletRequest request,
                          HttpServletResponse res, HttpSession session) {
        return "web/help/topUp";
    }

    @RequestMapping(value = "/goTransferOut", method = RequestMethod.GET)
    public String goTransferOut(HttpServletRequest request,
                                HttpServletResponse res, HttpSession session) {
        return "web/help/transferOut";
    }

    @RequestMapping(value = "/goDemandProduct", method = RequestMethod.GET)
    public String goDemandProduct(HttpServletRequest request,
                                  HttpServletResponse res, HttpSession session) {
        return "web/help/demandProduct";
    }

    @RequestMapping(value = "/goFixProduct", method = RequestMethod.GET)
    public String goFixProduct(HttpServletRequest request,
                               HttpServletResponse res, HttpSession session) {
        return "web/help/fixProduct";
    }

    @RequestMapping(value = "/goWithdraw", method = RequestMethod.GET)
    public String goWithdraw(HttpServletRequest request,
                             HttpServletResponse res, HttpSession session) {
        return "web/help/withdraw";
    }

    @RequestMapping(value = "/goChangePayPassword", method = RequestMethod.GET)
    public String goChangePayPassword(HttpServletRequest request,
                                      HttpServletResponse res, HttpSession session) {
        return "web/help/changePayPassword";
    }

    @RequestMapping(value = "/goChangeHeadPortraits", method = RequestMethod.GET)
    public String goChangeHeadPortraits(HttpServletRequest request,
                                        HttpServletResponse res, HttpSession session) {
        return "web/help/changeHeadPortraits";
    }

    @RequestMapping(value = "/goEbanktopUp", method = RequestMethod.GET)
    public String goEbanktopUp(HttpServletRequest request,
                               HttpServletResponse res, HttpSession session) {
        return "web/help/E-banktopUp";
    }

    @RequestMapping(value = "/goInvestDetail", method = RequestMethod.GET)
    public String goInvestDetail(HttpServletRequest request,
                                 HttpServletResponse res, HttpSession session) {
        return "web/accout/investDetail";
    }

    @RequestMapping(value = "/goNameBindCard", method = RequestMethod.GET)
    public String goNameBindCard(HttpServletRequest request,
                                 HttpServletResponse res, HttpSession session) {
        return "web/help/NameBindCard";
    }

    @RequestMapping(value = "/goKcodeActivation", method = RequestMethod.GET)
    public String goKcodeActivation(HttpServletRequest request,
                                    HttpServletResponse res, HttpSession session) {
        return "web/help/KcodeActivation";
    }

    @RequestMapping(value = "/goSignIn", method = RequestMethod.GET)
    public String goSignIn(HttpServletRequest request,
                           HttpServletResponse res, HttpSession session) {
        return "web/help/signIn";
    }

    @RequestMapping(value = "/goEnjoyPlayIndex", method = RequestMethod.GET)
    public String goEnjoyPlayIndex(HttpServletRequest request,
                                   HttpServletResponse res, HttpSession session) {
        return "web/help/enjoyPlayIndex";
    }

    @RequestMapping(value = "/goEnjoyTheTransfer", method = RequestMethod.GET)
    public String goEnjoyTheTransfer(HttpServletRequest request,
                                     HttpServletResponse res, HttpSession session) {
        return "web/help/enjoyTheTransfer";
    }

    //跳转到活期资产页面
    @RequestMapping(value = "/goCurrentAssets")
    public String goCurrentAssets(HttpServletRequest request,
                                  HttpServletResponse res, HttpSession session) {
        return "web/accout/currentAssets";
    }

    @RequestMapping(value = "/bindCardError", method = RequestMethod.GET)
    public String bindCardError(HttpServletRequest request,
                                 HttpServletResponse res, HttpSession session) {
        return "web/help/bindCardError";
    }
}
