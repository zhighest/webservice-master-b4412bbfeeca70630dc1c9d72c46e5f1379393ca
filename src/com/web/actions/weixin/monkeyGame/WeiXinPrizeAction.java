package com.web.actions.weixin.monkeyGame;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wxprize")
@Scope("prototype")
public class WeiXinPrizeAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(WeiXinPrizeAction.class);
	
	/**
	 * 跳转到活动详情页面
	 * @return
	 */
	@RequestMapping(value="/goGameDetails",method=RequestMethod.GET)
	public String goGameDetails(HttpServletRequest request, HttpServletResponse res){

		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		String mobile = parmMap.get("mobile");
		String channel = parmMap.get("channel");
		request.setAttribute("mobile", mobile == null ? "" : mobile);
		request.setAttribute("channel", channel == null ? "" : channel);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/monkeyGame/gameDetails";
	}
	
	/**
	 * 跳转到奖品信息页面
	 * @return
	 */
	@RequestMapping(value="/goPrizeList",method=RequestMethod.GET)
	public String goPrizeList(HttpServletRequest request, HttpServletResponse res){
		
		String mobile = request.getParameter("mobile");
		request.setAttribute("mobile", mobile);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/monkeyGame/userInfo";
	}
	
	/**
	 * 跳转到兑奖页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goGetPrize", method = RequestMethod.GET)
	public String goGetPrize(HttpServletRequest request,HttpServletResponse response) {
		
		// 类型
		String kind = request.getParameter("kind");
		// 奖参
		String prizeMsg = request.getParameter("prize_msg");
		// 名次
		String sort = request.getParameter("sort");
		// 奖品
		String prize = request.getParameter("prize");
		// 期数
		String installment = request.getParameter("installment");
		// 手机号
		String mobile = request.getParameter("mobile");
		request.setAttribute("kind", kind);
		request.setAttribute("prize_msg", prizeMsg);
		request.setAttribute("prize", prize);
		request.setAttribute("sort", sort);
		request.setAttribute("installment", installment);
		request.setAttribute("mobile", mobile);

		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/monkeyGame/login";
	}
	
	/**
	 * 跳转到兑奖成功页面
	 * 
	 * @param request
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goPrizeSuccessd", method = RequestMethod.GET)
	public String goPrizeSuccessd(HttpServletRequest request,HttpServletResponse response) {
		
		String prize = request.getParameter("prize");
		String mobile = request.getParameter("mobile");
		request.setAttribute("prize", prize);
		request.setAttribute("mobile", mobile);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/monkeyGame/prizeSuccessd";
	}
	
	/**
	 * 查询我的兑奖信息
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/prizeList", method = RequestMethod.POST)
	public void prizeList(HttpServletRequest request, HttpServletResponse res,HttpSession session,PrintWriter out) throws UnsupportedEncodingException {

		JSONObject json = new JSONObject();
		Map<String, Object> reqJavaMap = new HashMap<String, Object>(); 
		// 手机
		String mobile = request.getParameter("mobile");

		if(StringUtils.isNotBlank(mobile)){
			reqJavaMap.put("mobile", mobile);
		} else {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "手机号不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户兑奖信息加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/prize/prizeList", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			logger.info("用户兑奖信息解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}

	/**
	 * 兑换奖品
	 */
	@RequestMapping(value = "/getPrize")
	public void getPrize(HttpServletRequest request,HttpServletResponse res, PrintWriter out)  throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);

		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> reqJavaMap = new HashMap<String, Object>();
		JSONObject json = new JSONObject();
		
		reqJavaMap.put("userId", userId);
		
		String putMobile = request.getParameter("putMobile");
		String kind = request.getParameter("kind");
		String prizeMsg = request.getParameter("prize_msg");
		String sort = request.getParameter("sort");
		String installment = request.getParameter("installment");
		String prize = request.getParameter("prize");
		String mobile = request.getParameter("mobile");

		// 审查
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		String sysDate = sdf.format(new Date());
		if ((Integer.parseInt(sysDate) < 2016050212 && "1".equals(installment))
				|| (Integer.parseInt(sysDate) < 2016050912 && "2".equals(installment))
				|| (Integer.parseInt(sysDate) < 2016051612 && "3".equals(installment))
				|| (Integer.parseInt(sysDate) < 2016052312 && "4".equals(installment))) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "您好,未到兑奖时间!");
			setResposeMsg(json.toString(), out);
			return;
		}
		
		if (StringUtils.isBlank(kind)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "类型不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(prizeMsg)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "奖参不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(sort)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "名次不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(installment)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "期数不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(prize)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "奖品内容不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(mobile)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "获奖手机号不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		if (StringUtils.isBlank(putMobile)) {
			json.put("rescode", Consts.CHECK_CODE);
			json.put("resmsg_cn", "兑奖手机号不能为空!");
			setResposeMsg(json.toString(), out);
			return;
		}
		reqJavaMap.put("putMobile", putMobile);
		reqJavaMap.put("prizeMsg", prizeMsg);
		reqJavaMap.put("sort", sort);
		reqJavaMap.put("installment", installment);
		reqJavaMap.put("prize", prize);
		reqJavaMap.put("mobile", mobile);
		
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户兑奖加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		if (kind != null) {
			// 用户获取加息券
			if ("K".equals(kind)) {
				resultMsg = HttpRequestParam.sendGet(
						LoginRedirectUtil.interfacePath
								+ "/prize/addRateRisesCoupons", param);
				// 用户获取手机流量
			} else {
				resultMsg = HttpRequestParam.sendGet(
						LoginRedirectUtil.interfacePath
						+ "/prize/addMobileFlows", param);
			}
		}
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("用户兑奖解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		setResposeMsg(resultMsg, out);
	}
}