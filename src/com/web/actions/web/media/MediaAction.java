package com.web.actions.web.media;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.web.util.PageUtil;
import com.web.util.PageUtils;

@Controller
@RequestMapping("/webmedia")
@Scope("prototype")
public class MediaAction extends BaseAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(MediaAction.class);

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mediaList", method = RequestMethod.GET)
	public String mediaList(HttpServletRequest request) {
		return "web/media/mediaList";
	} 
	
	@RequestMapping(value = "/getMedia")
	public void getMedia(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		//Map<String, String> paramsMap = CommonUtil.decryptParamters(request);
		
		String current = request.getParameter("current");
		String pageSize = request.getParameter("pageSize");

		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		
		String sign = request.getParameter("sign");
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("current", current);
		map.put("pageSize",pageSize);
		map.put("sign", sign);
		
		logger.info(LogUtil.getStart("getMedia", "方法开始执行", "MediaAction",
				getProjetUrl(request)));
		logger.info("current=" + map.get("current") + "&pageSize=" + map.get("pageSize"));

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("媒体列表加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/media/getMedia", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("媒体列表解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<JSONObject> listDetail = jsonObjRtn.getJSONArray("list");
		String size = jsonObjRtn.getString("totalNum");
		
		resultMap.put("list", listDetail);
		resultMap.put("rescode", jsonObjRtn.getString("rescode"));
		resultMap.put("resmsg", jsonObjRtn.getString("resmsg"));
		
		PageUtils pageObject = new PageUtils();
		if (null != listDetail && listDetail.size() != 0) {
			int intPageSize = 0;
			if (null != pageSize && !"".equals(pageSize)){
				intPageSize = Integer.parseInt(pageSize);
			}
			pageObject = PageUtil.execsPage(Integer.parseInt(current),
					Integer.parseInt(size), 5, intPageSize);
		}
		resultMap.put("pageObject", pageObject);
		
		setResposeMap(resultMap, out);
	}
	
	@RequestMapping(value = "/getMediaContent")
	public void getMediaContent(HttpServletRequest request, HttpServletResponse response, PrintWriter out){
		String noticeId = request.getParameter("noticeId");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("noticeId", noticeId);
		
		logger.info(LogUtil.getStart("getMediaContent", "方法开始执行", "MediaAction",
				getProjetUrl(request)));
		logger.info("noticeId=" + map.get("noticeId"));
		
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("服务协议加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String resultMsg = HttpRequestParam.sendGet(
				LoginRedirectUtil.interfacePath + "/media/getMediaContent", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("服务协议解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		try {	
			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
			
			
			resultMap.put("id", jsonObjRtn.getString("id"));
			resultMap.put("title", jsonObjRtn.getString("title"));
			resultMap.put("afficheContent", jsonObjRtn.getString("afficheContent"));
			resultMap.put("createPerson", jsonObjRtn.getString("createPerson"));
			resultMap.put("createTime", jsonObjRtn.getString("createTime"));
			resultMap.put("createPersonNam", jsonObjRtn.getString("createPersonNam"));
			resultMap.put("intro", jsonObjRtn.getString("intro"));
			resultMap.put("smallIconUrl", jsonObjRtn.getString("smallIconUrl"));
			resultMap.put("htmlUrl", jsonObjRtn.getString("htmlUrl"));
			// 处理返回结果
			
			resultMap.put("rescode", jsonObjRtn.getString("rescode"));
			resultMap.put("resmsg", jsonObjRtn.getString("resmsg"));
			resultMap.put("resmsg_cn", jsonObjRtn.getString("resmsg_cn"));
			
			setResposeMap(resultMap, out);			
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("/media/getMediaContent 媒体详情 异常：" + e.getCause());
			
			resultMap.put("rescode", Consts.ERROR_CODE);
			resultMap.put("resmsg", Consts.ErrorMsg.MSG00001);
			resultMap.put("resmsg_cn", "媒体详情获取异常");
			
			setResposeMap(resultMap, out);
		}
	}
}
