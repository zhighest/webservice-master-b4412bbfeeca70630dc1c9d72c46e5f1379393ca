package com.web.actions.weixin.advert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

/**
 * 
 * banner模板 Action
 * 
 * @author wuhan
 *
 */
@Controller
@RequestMapping("/bannerTemplate")

public class BannerTemplateAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 7811037561595714791L;

	private static final Log logger = LogFactory.getLog(BannerTemplateAction.class);

	// 模板资源根目录
	private static final String TEMPLATEDIRECTORY = "/static/template/";

	// banner图片前缀名字
	private static final String BANNERIMGNAME = "banner";

	/**
	 * 签到后跳转h5模板页
	 * 
	 * @param request
	 * @param childBanner
	 *            模板资源子目录名称
	 * @param picNum
	 *            图片张数
	 * @return
	 */
	@RequestMapping(value = "/h5Template/{childBanner}/{picNum}")
	public String h5Template(HttpServletRequest request, HttpServletResponse res, @PathVariable String childBanner,
			@PathVariable int picNum) {
		String logInfo = "h5Template-webservice ";
		logger.info(logInfo + "开始");

		
		Map<String,Object> resMap= getBannerImgListAndTemplaTeDirectoryAll(request, picNum, childBanner, logInfo);
		List<String> bannerImgList = (List<String>) resMap.get("bannerImgList");
		String templateDirectoryAll=(String) resMap.get("templateDirectoryAll");

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		request.setAttribute("templateDirectory", templateDirectoryAll);
		request.setAttribute("bannerImgList", bannerImgList);
		// 渠道
		request.setAttribute("channel", request.getParameter("channel"));
		logger.info(logInfo + "结束");

		return "weixin/template/h5Template";
	}

	/**
	 * 跳转带签到模板
	 * 
	 * @param request
	 * @param childBanner
	 *            模板资源子目录名称
	 * @param picNum
	 *            图片张数
	 * @return
	 */
	@RequestMapping(value = "/signTemplate/{childBanner}/{picNum}")
	public String signTemplate(HttpServletRequest request, HttpServletResponse res, @PathVariable String childBanner,
			@PathVariable int picNum) {
		String logInfo = "signTemplate-webservice ";

		logger.info(logInfo + "开始");

	
		Map<String,String> userMap=this.getUserInfo(request, res);
		String userId = userMap.get("userId"); // 用户id
		String mobile = userMap.get("mobile"); // 手机号码

		logger.info(logInfo + "params {userId:" + userId + ",mobile:" + mobile + "}");

		Map<String,Object> resMap= getBannerImgListAndTemplaTeDirectoryAll(request, picNum, childBanner, logInfo);
		List<String> bannerImgList = (List<String>) resMap.get("bannerImgList");
		String templateDirectoryAll=(String) resMap.get("templateDirectoryAll");
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		request.setAttribute("templateDirectory", templateDirectoryAll);
		request.setAttribute("bannerImgList", bannerImgList);
		// 渠道
		request.setAttribute("channel", request.getParameter("channel"));
		logger.info(logInfo + "结束");

		return "weixin/template/signTemplate";
	}

	/**
	 * 跳转带签到模板-问卷调查页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/questionnaireTemplate/{childBanner}/{picNum}")
	public String questionnaireTemplate(HttpServletRequest request, HttpServletResponse res,
			@PathVariable String childBanner, @PathVariable int picNum) {
		String logInfo = "questionnaireTemplate-webservice ";

		logger.info(logInfo + "开始");


		Map<String,String> userMap=this.getUserInfo(request, res);
		String userId = userMap.get("userId"); // 用户id
		String mobile = userMap.get("mobile"); // 手机号码


		logger.info(logInfo + "params {userId:" + userId + ",mobile:" + mobile + "}");

		Map<String,Object> resMap= getBannerImgListAndTemplaTeDirectoryAll(request, picNum, childBanner, logInfo);
		List<String> bannerImgList = (List<String>) resMap.get("bannerImgList");
		String templateDirectoryAll=(String) resMap.get("templateDirectoryAll");

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		request.setAttribute("templateDirectory", templateDirectoryAll);
		request.setAttribute("bannerImgList", bannerImgList);
		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);
		// 渠道
		request.setAttribute("channel", request.getParameter("channel"));
		logger.info(logInfo + "结束");

		return "weixin/template/questionnaireTemplate";
	}

	/**
	 * 广告签到模板
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/adTemplate")
	public String adTemplate(HttpServletRequest request, HttpServletResponse res) {
		String logInfo = "adTemplate-webservice ";

		logger.info(logInfo + "开始");

		// 模板资源目录
//		String domainUrl = getDomainUrl(request);

		String templateDirectoryAll =  TEMPLATEDIRECTORY;

		Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
		paramsMap.put("type", "6");
		String param1 = CommonUtil.getParam(paramsMap);
		try {
			param1 = DES3Util.encode(param1);
		} catch (Exception e) {
			logger.error(logInfo + "加密失败:", e);
			request.setAttribute("imageTitle", "");
			request.setAttribute("imageUrl", "");
			request.setAttribute("url", "");
			request.setAttribute("adTitle", "");
			request.setAttribute("adTime", "");
			return "weixin/template/adTemplate";
		}

		// 查询广告信息
		String checkCaptchatMsg = HttpRequestParam
				.sendGet(LoginRedirectUtil.interfacePath + "/signManage/queryAdBanner", param1);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg), "UTF-8");
		} catch (Exception e) {
			logger.error("查询广告信息解密失败", e);
			request.setAttribute("imageTitle", "");
			request.setAttribute("imageUrl", "");
			request.setAttribute("url", "");
			request.setAttribute("adTitle", "");
			request.setAttribute("adTime", "");
			return "weixin/template/adTemplate";
		}

		JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);

		logger.info(logInfo + "查询结果：" + checkCaptchatObject.toString());

		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		if (!checkCaptchatObject.get("rescode").toString().equals("00000")) {
			request.setAttribute("imageTitle", "");
			request.setAttribute("imageUrl", "");
			request.setAttribute("url", "");
			request.setAttribute("adTitle", "");
			request.setAttribute("adTime", "");
		} else {
			request.setAttribute("imageTitle", checkCaptchatObject.get("imageTitle"));
			request.setAttribute("imageUrl", request.getScheme() + "://" + checkCaptchatObject.get("imageUrl"));
			request.setAttribute("url", checkCaptchatObject.get("url"));
			request.setAttribute("adTitle", checkCaptchatObject.get("adTitle"));
			request.setAttribute("adTime", checkCaptchatObject.get("adTime"));
		}
		// 渠道
		request.setAttribute("templateParentDirectory", templateDirectoryAll);
		request.setAttribute("channel", request.getParameter("channel"));
		logger.info(logInfo + "结束");

		return "weixin/template/adTemplate";
	}

	private String getDomainUrl(HttpServletRequest request) {
		int serverPort = request.getServerPort();
		if (serverPort != 80) {
			return  request.getServerName() + ":" + serverPort + request.getContextPath();
		} else {
			return  request.getServerName();
		}
	}

	/**
	 * 获得用户Id 和 mobile
	 * @param request
	 * @param res
	 * @param userId
	 * @param mobile
	 */
	private Map<String ,String > getUserInfo(HttpServletRequest request, HttpServletResponse res) {
		Map<String,String> resMap= new HashMap<String, String>();
		UserSession us = UserCookieUtil.getUser(request);
		String userId=null;
		String mobile=null;
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
			userId = userInfoMap.get("userId");
			mobile = userInfoMap.get("mobile");
		} else {
			userId = String.valueOf(us.getId());
			mobile = String.valueOf(us.getMobile());
		}

		if (userId == null) {
			userId = request.getParameter("userId");
		}

		if (mobile == null) {
			mobile = request.getParameter("mobile");
		}
		resMap.put("userId", userId);
		resMap.put("mobile", mobile);
		return resMap;
	}
	
	/**
	 * 设置 bannerImgList  and templateDirectoryAll
	 * @param request
	 * @param picNum
	 * @param childBanner
	 * @param bannerImgList
	 * @param templateDirectoryAll
	 * @param logInfo
	 */
	private Map<String,Object> getBannerImgListAndTemplaTeDirectoryAll(HttpServletRequest request,int picNum,String childBanner,String logInfo){
		
		Map<String,Object> resMap= new HashMap<String, Object>();
		// 模板资源目录
		List<String> bannerImgList=new ArrayList<String>();
//		String domainUrl = getDomainUrl(request);

		String templateDirectoryAll = TEMPLATEDIRECTORY + childBanner;

		logger.info(
				logInfo + "params {templateDirectory:" + TEMPLATEDIRECTORY + ",bannerImgName:" + BANNERIMGNAME + "}");

		if (picNum < 1) {
			bannerImgList.add(templateDirectoryAll + "/" + BANNERIMGNAME + "0.jpg");
		} else {
			for (int i = 0; i < picNum; i++) {
				bannerImgList.add(templateDirectoryAll + "/" + BANNERIMGNAME + i + ".jpg");
			}
		}

		logger.info(logInfo + " {templateDirectory:" + templateDirectoryAll + ",bannerImgList:"
				+ bannerImgList.toString() + "}");
		
		resMap.put("templateDirectoryAll", templateDirectoryAll);
		resMap.put("bannerImgList", bannerImgList);
		return resMap;
	}
}
