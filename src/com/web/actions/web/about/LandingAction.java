package com.web.actions.web.about;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.util.CommonUtil;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/weblanding")

public class LandingAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LandingAction.class);

	/**
	 * 跳转活动分享页
	 * @return
	 */
	@RequestMapping(value = "/goLandingPage1", method = RequestMethod.GET)
	public String goLoginDare(HttpServletRequest request,HttpServletResponse res) {
		
		return "web/landingPage/landingPage1";
	}
	
	@RequestMapping(value = "/goLoginLiuliangbao", method = RequestMethod.GET)
	public String goLoginLiuliangbao(HttpServletRequest request,HttpServletResponse res) {
		
		return "weixin/landingPage/loginLiuliangbao";
	}
	
	@RequestMapping(value = "/goLandingPageZhizhuwang", method = RequestMethod.GET)
	public String goLandingPageZhizhuwang(HttpServletRequest request,HttpServletResponse res) {
		return "web/landingPage/landingPagezhizhuwang";
	} 
	
	
	/**
	 * 渠道注册2
	 * @return
	 */
	@RequestMapping(value = "/goLandingPageWojiatisu", method = RequestMethod.GET)
	public String goLandingPageWojiatisu(HttpServletRequest request,HttpServletResponse res) {
		
		return "web/landingPage/landingPageWojiatisu";
	}
	
	/**
	 * 沃助手
	 * @return
	 */
	@RequestMapping(value = "/goLandingPageWozhushou", method = RequestMethod.GET)
	public String goLandingPageWozhushou(HttpServletRequest request,HttpServletResponse res) {
		
		return "web/landingPage/landingPageWozhushou";
	}
	
	@RequestMapping(value = "/goLandingPageGewala", method = RequestMethod.GET)
	public String goLandingPageGewala(HttpServletRequest request,HttpServletResponse res) {
		return "web/landingPage/landingPageGewala";
	}
	
	/**
	 * 网站端落地页模板
	 * @Description: TODO
	 * @param @param request
	 * @param @param res
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author wuhan
	 * @date 2016-9-6
	 */
	@RequestMapping(value = "/goLandingPageWeb", method = RequestMethod.GET)
	public String goLandingPageWeb(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
				WeixinRquestUtil.getSignature(request);
				String channelEnName = request.getParameter("channel");
//				channelId="413";
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("channelId","");
				userMap.put("channelEnName", channelEnName == null ? "" : channelEnName);
				String param = CommonUtil.getParam(userMap);
				try {
					param = DES3Util.encode(param);
				} catch (Exception e) {
					logger.error("获取渠道图片失败:" + e);
					e.printStackTrace();
				}

				// 调用service接口
				String resultMsg = HttpRequestParam.sendGet(
						LoginRedirectUtil.interfacePathForShop + "/channelshop/channelDetailImg", param);
				try {
					resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
				} catch (Exception e) {
					logger.error("获取渠道图片解密失败:" + e);
					e.printStackTrace();
				}
				JSONObject jsonObject = JSONObject.fromObject(resultMsg);
				request.setAttribute("webPicLink", jsonObject.getString("webPicLink"));
				return "web/landingPage/landingPageWeb";
	}
	
	/**
	 * 蜘蛛网2
	 * @return
	 */
	@RequestMapping(value = "/goLandingPageZhiZhuWang2", method = RequestMethod.GET)
	public String goLandingPageZhiZhuWang2(HttpServletRequest request,HttpServletResponse res) {
		
		return "web/landingPage/landingPageZhiZhuWang2";
	}
}
