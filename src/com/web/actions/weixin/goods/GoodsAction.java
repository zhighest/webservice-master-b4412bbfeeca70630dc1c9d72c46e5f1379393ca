package com.web.actions.weixin.goods;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
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
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxgoods")
public class GoodsAction extends BaseAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(GoodsAction.class);

    /**
     * @param @param  request
     * @param @param  res
     * @param @return
     * @return String
     * @throws
     * @Description: 跳转到商品图片详情页
     * @author chenshufeng
     * @date 2016-8-22
     */
    @RequestMapping(value = "/goGoodsDetailImg", method = RequestMethod.GET)
    public String goGoodsDetailImg(HttpServletRequest request, HttpServletResponse res) {

        logger.info("======进入方法：wxgoods/goodsDetailImg====");
        String userId;
        Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
        UserSession us = UserCookieUtil.getUser(request);

        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String goodsId = parmMap.get("commodityID");
        logger.info("==========================goodsId:" + goodsId);
        request.setAttribute("goodsId", goodsId == null ? "" : goodsId);

        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("userId", userId);
        userMap.put("goodsId", goodsId == null ? "" : goodsId);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("获取商品图片详情失败:" + e);
            e.printStackTrace();
        }

        // 调用service接口
        String resultMsg = HttpRequestParam.sendGet(
                LoginRedirectUtil.interfacePath + "/shopbuy/goodsDetailImg", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("获取商品图片详情解密失败:" + e);
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        String rescode = jsonObject.getString("rescode");
        if (!"00000".equals(rescode)) {
            request.setAttribute("picDetailLink", "");
            request.setAttribute("goodsDetailImgHead", "");
            request.setAttribute("goodsDetailImgBottom", "");
        } else {
            request.setAttribute("picDetailLink", jsonObject.get("picDetailLink"));
            request.setAttribute("goodsDetailImgHead", jsonObject.get("goodsDetailImgHead"));
            request.setAttribute("goodsDetailImgBottom", jsonObject.get("goodsDetailImgBottom"));
        }
        //分享验签机制
        WeixinRquestUtil.getSignature(request);
        return "weixin/goods/goodsDetailImg";
    }

    /**
     * 获取商品图片详情
     *
     * @param request
     * @param res
     * @return
     */
    @RequestMapping(value = "/goodsDetailImg")
    public void goodsDetailImg(HttpServletRequest request, HttpServletResponse res, PrintWriter out) {
        logger.info("======进入方法：wxgoods/goodsDetailImg====");
        String userId = "";
        Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
        UserSession us = UserCookieUtil.getUser(request);

        if (null == us || null == us.getId()) {
            //用户表示验证机制（通过微信标识OPENID）
            Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
            userId = userInfoMap.get("userId");
        } else {
            userId = String.valueOf(us.getId());
        }

        String goodsId = parmMap.get("goodsId");
//		goodsId="1010";
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("userId", userId);
        userMap.put("goodsId", goodsId == null ? "" : goodsId);
        String param = CommonUtil.getParam(userMap);
        try {
            param = DES3Util.encode(param);
        } catch (Exception e) {
            logger.info("获取商品图片详情失败:" + e);
            e.printStackTrace();
        }

        // 调用service接口
        String resultMsg = HttpRequestParam.sendGet(
                LoginRedirectUtil.interfacePath + "/shopbuy/goodsDetailImg", param);
        try {
            resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
        } catch (Exception e) {
            logger.info("获取商品图片详情解密失败:" + e);
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(resultMsg);
        String rescode = jsonObject.getString("rescode");
        if (!"00000".equals(rescode)) {
            jsonObject.put("picDetailLink", "");
            jsonObject.put("goodsDetailImgHead", "");
            jsonObject.put("goodsDetailImgBottom", "");
        }
        setResposeMsg(jsonObject.toString(), out);
    }


}
