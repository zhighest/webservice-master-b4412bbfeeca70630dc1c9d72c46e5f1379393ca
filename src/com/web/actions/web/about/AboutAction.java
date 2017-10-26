package com.web.actions.web.about;

import java.io.PrintWriter;
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
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;

@Controller
@RequestMapping("/webabout")
@Scope("prototype")
public class AboutAction extends BaseAction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(AboutAction.class);
	
	/**
	 * 获取app版本信息
	 * @param request
	 * @param response
	 * @param out
	 */
	@RequestMapping("/findAppVersionInfo")
	public void findAppResultInfo(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		JSONObject respResult = new JSONObject();
		String appType = request.getParameter("appType");//渠道
		String currentPage = request.getParameter("current"); // 当前页数
		String pageSize = request.getParameter("pageSize"); // 页大小
		/**
		 * 封装接口参数
		 * */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appType", appType);
		params.put("currentPage", currentPage);
		params.put("pageSize", pageSize);
	
		//检查请求参数
		if(appType == null || "".equals(appType)){
			respResult.put("rescode", Consts.CHECK_CODE);
			respResult.put("resmsg_cn", "app类型不能为空!");
			setResposeMsg(respResult.toString(), out);
			return;
		}
		
		if(currentPage == null || "".equals(currentPage) || "0".equals(currentPage)){
			params.put("currentPage", "1");
		}
		
		if(pageSize == null || "".equals(pageSize) || "0".equals(pageSize)){
			params.put("pageSize", "10");
		}
		
		String resultMsg = "";
		
		logger.info(LogUtil.getStart("findAppResultInfo", "方法开始执行", "AboutAction",
				"http://" + request.getServerName()+request.getContextPath()));
		logger.info("channel=" + appType + "&currentPage=" + currentPage + "&pageSize="
				+ pageSize);
		
		String param = CommonUtil.getParam(params);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("获取app版本信息加密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		
		// 获取app版本信息
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/activity/getAppVersionList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("获取app版本信息解密失败:" + CommonUtil.printStackTraceToString(e));
			e.printStackTrace();
		}
		
		setResposeMsg(resultMsg, out);
	}
	

	/**
	 * app下载页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String dealPWControls(HttpServletRequest request) {

		return "web/about/download";
	} 
	
	/**
	 * 跳转到下载页
	 * @return
	 */
	@RequestMapping(value = "/goDownloadDetails", method = RequestMethod.GET)
	public String goDownloadDetails(HttpServletRequest request,HttpServletResponse res) {
		
		return "web/about/downloadDetails";
	}

	@RequestMapping(value = "/focus", method = RequestMethod.GET)
	public String focus(HttpServletRequest request) {

		return "web/about/focus";
	} 
	
	@RequestMapping(value = "/qrcode", method = RequestMethod.GET)
	public String qrcode(HttpServletRequest request) {

		return "web/about/qrcode";
	} 

	@RequestMapping(value = "/helpCenter", method = RequestMethod.GET)
	public String helpCenter(HttpServletRequest request) {
		
		return "web/about/helpCenter";
	} 
	
	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request) {
		
		return "web/about/aboutUs";
	}
	
	/**
	 * 跳转到 平台背景页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goOurBackground", method = RequestMethod.GET)
	public String goOurBackground(HttpServletRequest request) {
		
		return "web/aboutUs/ourBackground";
	}
	
	/**
	 * 跳转到 资产介绍 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goPropertyInfo", method = RequestMethod.GET)
	public String goPropertyInfo(HttpServletRequest request) {
		
		return "web/aboutUs/propertyInfo";
	}

	/**
	 * 跳转到 安全保障 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goSafetyGuarantee", method = RequestMethod.GET)
	public String goSafetyGuarantee(HttpServletRequest request) {
		
		return "web/aboutUs/safetyGuarantee";
	}

	@RequestMapping(value = "/goMobileLanding", method = RequestMethod.GET)
	public String goQrcode(HttpServletRequest request) {
		
		return "web/about/mobileLanding";
	}
	
	/**
	 * 跳转到网站地图页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goWebsiteMap", method = RequestMethod.GET)
	public String goWebsiteMap(HttpServletRequest request) {
		
		return "web/about/websiteMap";
	}


}
