package com.web.actions.web.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LoginRedirectUtil;
import com.web.util.UserCookieUtil;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * 功能描述：网站个人中心增加活期资产、定期资产、代金券展示页面，后台提供查询活期资产、定期资产、代金券等相应接口
 * 
 * @author gongyl
 * @date 2016-12-08
 */
@Controller
@RequestMapping("/asset")
public class AssetAction extends BaseAction implements Serializable {
	private static final long serialVersionUID = -2600471416180878188L;
	private static final Log logger = LogFactory.getLog(AssetAction.class);

	/**
	 * 功能描述：定期资产-合同展示-查看按钮
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAgreementDetail", method = RequestMethod.POST)
	public void getAgreementDetail(HttpServletRequest request, HttpServletResponse response,PrintWriter out) throws Exception {
		logger.info("======进入方法：asset/getAgreementDetail====");
		UserSession us = UserCookieUtil.getUser(request);
		String userId = String.valueOf(us.getId());
		String orderId = request.getParameter("orderId");
		logger.info("userId=" + userId + "&orderId=" + orderId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("orderId", orderId);
		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.error("加密失败:", e);
		}
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/order/getPDFPathByOrderId",param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			logger.info("返回结果："+resultMsg);
			Map<String, Object> jsonMapRst = CommonUtil.jsonObjToHashMap(resultMsg);
			if (Consts.SUCCESS_CODE.equals(jsonMapRst.get(Consts.RES_CODE))) {
				//如果没有pdf合同，则重新生成html合同
				if (!jsonMapRst.containsKey("pdfFilePath")
						||JSONNull.getInstance().equals(jsonMapRst.get("pdfFilePath"))||"".equals(jsonMapRst.get("pdfFilePath"))) {
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("userId", userId);
					map2.put("orderId", orderId);
					map2.put("type", "loanAgreement");
					String param1 = CommonUtil.getParam(map2);
					try {
						param1 = DES3Util.encode(param1);
					} catch (Exception e) {
						logger.error("加密失败:", e);
					}
					String resultMsg1 = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/agreement/getServAgreementByType", param1);
					resultMsg1 = java.net.URLDecoder.decode(DES3Util.decode(resultMsg1), "UTF-8");
					logger.info("重新生成html文件合同返回结果：" + resultMsg1);
					Map<String, Object> jsonMap = CommonUtil.jsonObjToHashMap(resultMsg1);
					if (Consts.SUCCESS_CODE.equals(jsonMap.get(Consts.RES_CODE))) {
						String serAgreementUrl = (String) jsonMap.get("serAgreementUrl");
						String protocol=(String) jsonMap.get("protocol");//http协议替换成https协议
						if (null!=protocol&&!"".equals(protocol)) {
							serAgreementUrl=serAgreementUrl.replace("http",protocol);
						}
						Map<String, Object> mapResult = new HashMap<String, Object>();
						mapResult.put(Consts.RES_CODE, Consts.SUCCESS_CODE);// 成功
						mapResult.put(Consts.RES_MSG, Consts.SUCCESS_DESCRIBE);
						mapResult.put("htmlFilePath", serAgreementUrl);
						resultMsg=JSONObject.fromObject(mapResult).toString();
					}else{
						resultMsg=resultMsg1;
					}
				}
			}			
		} catch (Exception e) {
			logger.error("合同展示-查看失败:", e);
		}
		setResposeMsg(resultMsg,out);
	}

	/**
	 * 功能描述：定期资产-合同下载按钮
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(value = "/downloadAgreementFile", method = RequestMethod.GET)
	public void downloadAgreementFile(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws Exception {
//		try {
			logger.info("======进入方法：asset/downloadAgreementFile====");
			UserSession us = UserCookieUtil.getUser(request);
			String userId = String.valueOf(us.getId());
			String orderId = request.getParameter("orderId");
			logger.info("userId=" + userId + "&orderId=" + orderId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("orderId", orderId);
			String param = CommonUtil.getParam(map);
			try {
				param = DES3Util.encode(param);
			} catch (Exception e) {
				logger.error("加密失败:", e);
			}
			String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/order/getPDFPathByOrderId",param);
			try {
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
				logger.info("返回结果："+resultMsg);
			} catch (Exception e) {
				logger.error("解密失败:", e);
			}
			setResposeMsg(resultMsg,out);
//			JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
//			String pdfFilePath = jsonObjRtn.getString("pdfFilePath");
//			String rescode = jsonObjRtn.getString("rescode");
//			String resmsg = jsonObjRtn.getString("resmsg");
//			logger.info("#####################" + rescode + "||" + pdfFilePath+"||"+resmsg);
//			if ("00000".equals(rescode)) {
//			String pdfFilePath="M00/03/D4/wKgvaVhJLgeAK34oAAHIeI05dE0195.pdf";
//				String fileName = "借款合同";
//				byte[] data = FileManager.downloadByte(pdfFilePath);
//				fileName = URLEncoder.encode(fileName, "UTF-8");
//				response.reset();
//				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//				response.addHeader("Content-Length", "" + data.length);
//				response.setContentType("application/octet-stream;charset=UTF-8");
//				OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//				outputStream.write(data);
//				outputStream.flush();
//				outputStream.close();
//				if (null!=pdfFilePath&&!"".equals(pdfFilePath)) {
//					out.write(pdfFilePath);
//				}else{
//					out.write("合同正在生成中，请稍等再试！");
//				}
//				out.flush();
//				out.close();
//			} else {
//				logger.error("合同下载失败:" + resmsg);
//				throw new Exception("合同下载失败,请稍后重试！");
//			}
//		} catch (FileNotFoundException e) {
//			logger.error("合同下载失败:" + e.getMessage());
//			e.printStackTrace();
//			throw new Exception("合同下载失败,请稍后重试！");
//		} catch (IOException e) {
//			logger.error("合同下载失败:" + e.getMessage());
//			e.printStackTrace();
//			throw new Exception("合同下载失败,请稍后重试！");
//		}
	}

}
