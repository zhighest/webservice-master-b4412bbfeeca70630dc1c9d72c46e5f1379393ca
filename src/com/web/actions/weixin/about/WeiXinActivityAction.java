package com.web.actions.weixin.about;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.common.WeiXinUserDto;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.CommonUtil;
import com.web.util.ConstantUtil;
import com.web.util.Consts;
import com.web.util.DES3Util;
import com.web.util.HttpRequestParam;
import com.web.util.LogUtil;
import com.web.util.LoginRedirectUtil;
import com.web.util.PageUtils;
import com.web.util.StringUtil;
import com.web.util.Tools;
import com.web.util.UserCookieUtil;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/wxactivity")

public class WeiXinActivityAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(WeiXinActivityAction.class);
	
	
	
	/**
	 * 用途： 518活动页
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/activity_20170518")
	public String activity_20170518(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activity_20170518";
	} 

	
	/**
	 * 跳转活动分享页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activitySharing", method = RequestMethod.GET)
	public String activitySharing(HttpServletRequest request,HttpServletResponse res) {
		//不跳转登录后验证分享机制
		Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activitySharing";
	} 
	/**
	 *  跳转抽奖
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/goDiscover")
	public String goDiscover(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/discover";
	} 
	
	/**
	 *  跳转商品详情

	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/goJump")
	public String goJump(HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/jump";
	} 


	/**
	 * 跳转活动页20151212
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activity_20151212", method = RequestMethod.GET)
	public String activity_20151212(HttpServletRequest request,HttpServletResponse res) {
		//不跳转登录后验证分享机制
		/*Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);*/
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(userId==null){
			userId = request.getParameter("userId");
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		return "weixin/activity/activity_20151212";
	}
	
	/**
	 * 跳转活动页20151008
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activity_20151008", method = RequestMethod.GET)
	public String activity_20151008(HttpServletRequest request,HttpServletResponse res) {
		//不跳转登录后验证分享机制
		Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activity_20151008";
	}
	
	/**
	 * 跳转活动页activity_20151202
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activity_20151202", method = RequestMethod.GET)
	public String activity_20151202(HttpServletRequest request,HttpServletResponse res) {
		//不跳转登录后验证分享机制
		Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activity_20151202";
	}
	/**
	 * 跳转活动页activity_20151202Share
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activity_20151202Share", method = RequestMethod.GET)
	public String activity_20151202Share(String sharePhone,HttpServletRequest request,HttpServletResponse res) {
		//分享验签机制
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum="";
		if(null == us || null == us.getMobile()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			phoneNum  = userInfoMap.get("mobile");
		}else{
			phoneNum = us.getMobile();
		}
		if(null==sharePhone || "null".equalsIgnoreCase(sharePhone)){
			sharePhone ="";
		}
		if("".equalsIgnoreCase(phoneNum) || null==phoneNum ||"null".equalsIgnoreCase(phoneNum)){
			phoneNum="";
			phoneNum = sharePhone;
		}
		//获取当前分享时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("shareMobile", phoneNum);
		map.put("shareDate", date);
		String shareMobileDateJM = CommonUtil.getParam(map);
		try {
			shareMobileDateJM = DES3Util.encode(shareMobileDateJM);
		} catch (Exception e) {
			logger.info("[跳转到邀请好友]时间，分享人手机号加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String goUrl="";
		try {
			goUrl = Base64.encodeBase64URLSafeString(DES3Util.encode("/wxuser/checkShareRateRises?shareMobileDateJM="+shareMobileDateJM+"&lootMobile=").getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("goUrl", goUrl);
		logger.info("*********加息分享出去的链接："+goUrl);
		//微信手机标识
		request.setAttribute("mobileWXFlag",request.getParameter("mobileWXFlag"));
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activity_20151202Share";
	}

	/**
	 * 获取banner数据
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBannerList")
	public void getBannerList(HttpServletRequest request, HttpServletResponse response,PrintWriter out){
		logger.info("======进入方法：wxactivity/getBannerList====");
		String resultMsg = "";
		try {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("platform", "wechat");//查询banner的标示渠道
			String param = CommonUtil.getParam(map);
			param = DES3Util.encode(param);
			// 调用service接口
			resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/activity/getBannerList", param);
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("获取banner数据解密失败:resultMsg=[" + resultMsg +"]"+ e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		setResposeMsg(jsonObject.toString(), out);
	}
	
	
	/**
	 * 跳转活动页20151103
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activity_20151103", method = RequestMethod.GET)
	public String activity_20151103(String parm,HttpServletRequest request,HttpServletResponse res) {
		
		WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
		if(null==userDto){
			userDto = new WeiXinUserDto();
		}
		String backUrl = "/wxtrade/goMyAsset";
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		request.setAttribute("userId", userId);
		
		// 为自动填手机号添加
		String mobileNumber = request.getSession().getAttribute("mobileNumber") == null ? ""
				: (String) request.getSession().getAttribute("mobileNumber");
		if (StringUtils.isNotBlank(mobileNumber)) {
			request.setAttribute("mobileNumber", mobileNumber);
		}
		
		logger.info("######################activity_20151103获取的参数：["+parm+"]");
		if (parm!=null && parm.length() > 0) {
			try {
				parm = java.net.URLDecoder.decode(DES3Util.decode(parm.replaceAll(" ", "+")),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + e.getMessage());
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(parm);
			backUrl = jsonObj.getString("backUrl");
			
			String invitationCd = jsonObj.getString("invitationCd");
			logger.info("######################activity_20151103获取的invitationCd参数：["+invitationCd+"]");
			if(invitationCd != null && invitationCd.length() > 0){
				userDto.setInvitationCd(invitationCd);
			}
		} else {
			//暂定默认跳转到 实名认证页面
			backUrl = "/wxtrade/goMyAsset";
		}
		request.getSession().setAttribute("backUrl", backUrl);
		request.getSession().setAttribute("userDto", userDto);
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activity_20151103";
	}
	
	private String getEncodeString(String str) {
		String parm = "";
		try {
			parm = URLEncoder.encode(DES3Util.encode(str),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		return parm;
	}

	/**
	 * 发送手机验证码 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCaptcha")
	public void goGetCaptcha(HttpServletRequest request, HttpServletResponse response) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		String parm = request.getQueryString();
		
		
		String token_id = (String)request.getSession().getAttribute("token_id");
		logger.info("/wxactivity/getCaptcha-token_id="+token_id);
		
		if (parm!=null && parm.length() > 0) {
			String resultMsg = "";
			try {
				parm = java.net.URLDecoder.decode(DES3Util.decode(parm.replace(" ", "+")), "UTF-8");
				JSONObject jsonObj = JSONObject.fromObject(parm);
				String type=jsonObj.getString("type");
				if(StringUtils.isBlank(type)){
					type="WXLOGIN";
				}else{
					if(type.equalsIgnoreCase("resetTradePassword"))
						type="RESET_TRADE_PASSWORD";
					else
						type=type.toUpperCase();
				}
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("phoneNum", jsonObj.getString("mobileNumber"));
				map.put("type", type);
				map.put("setupFlag", ConstantUtil.setup_Flag);
				map.put("userId", jsonObj.getString("mobileNumber"));
				map.put("blackBox", token_id);
				map.put("ipInfo",  CommonUtil.getClientIP(request));
				String param = CommonUtil.getParam(map);
				param = DES3Util.encode(param);
				resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/captcha", param);
				resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				String result = CommonUtil.setResultStringCn(
						new HashMap<String, Object>(), Consts.ERROR_CODE, "", "网络或服务器连接异常,请稍后再试!");
				// 处理返回结果
				CommonUtil.responseJson(result, response);
				return;
			}
			// 处理返回结果
			CommonUtil.responseJson(resultMsg, response);
			return;
		} else {
			String result = CommonUtil.setResultStringCn(
					new HashMap<String, Object>(), Consts.ERROR_CODE, "", "参数异常!");
			// 处理返回结果
			CommonUtil.responseJson(result, response);
			return;
		}
	}
	
	/**
	 * 跳转到 激活K码页面
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/goLDActivityStep",method=RequestMethod.GET)
	public String goLDActivityStep(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidLD(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}

		String parm = request.getQueryString();
		request.removeAttribute("message");
		String channel = "";
		if (parm!=null && parm.length() > 0) {
			String[] parmList = parm.split("&");
			if (parmList.length > 1) {
				parm = parmList[0];
			}
			request.setAttribute("parm", parm);

			try {
				String urlparm = URLDecoder.decode(parm,"UTF-8").replace(" ", "+");
				parm = DES3Util.decode(urlparm);
				JSONObject jsonObj = JSONObject.fromObject(parm);
				channel = jsonObj.getString("channel");
				logger.debug("======goLDActivityDetal接受参数！======");
			} catch (Exception e) {
				request.setAttribute("message", "网络或服务器连接异常,请稍后再试!");
				logger.debug("======goLDActivityDetal接受参数异常！======");
			}
		}
		request.getSession().setAttribute("mobile", mobile);
		request.getSession().setAttribute("channel", channel);
		request.setAttribute("mobile", mobile);
		request.setAttribute("channel", channel);
		
		JSONObject json = new JSONObject();
		json.put("mobileNumber", mobile);
		json.put("channel", channel);
		String parm2 = "";
		try {
			parm2 = DES3Util.encode(json.toString());
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			request.setAttribute("message", "网络或服务器连接异常,请稍后再试!");
		}
		// 调用service接口
		String retMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/findUserActivityInfo", parm2);
		String activityKCodeFlg = "N";
		String activityShareFlg = "N";
		String activityPraiseFlg = "N";
		try {
			retMsg = java.net.URLDecoder.decode(DES3Util.decode(retMsg),"UTF-8");
			JSONObject jsonObj = JSONObject.fromObject(retMsg);
			
			activityKCodeFlg = jsonObj.getString("activityKCodeFlg");
			activityShareFlg = jsonObj.getString("activityShareFlg");
			activityPraiseFlg = jsonObj.getString("activityPraiseFlg");
		} catch (Exception e) {
			request.setAttribute("message", "网络或服务器连接异常,请稍后再试!");
			logger.info("检察用户功能解密失败:" + e.getMessage());
		}

		request.setAttribute("activityKCodeFlg", activityKCodeFlg);
		request.setAttribute("activityShareFlg", activityShareFlg);
		request.setAttribute("activityPraiseFlg", activityPraiseFlg);
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/ldActivityStep";
	}
	
	/**
	 * 活动分享
	 * @param kcode
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/activitiyShareNotify",method=RequestMethod.POST)
	public void activitiyShareNotify(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out)
			throws UnsupportedEncodingException {
		JSONObject retJson = new JSONObject();

		String mobileNumber = (String) request.getSession().getAttribute("mobile");
		String channel = (String) request.getSession().getAttribute("channel");
		
		JSONObject jsObj = new JSONObject();
		jsObj.put("mobileNumber", mobileNumber);
		jsObj.put("channel", channel);
		String regparam = "";
		try {
			regparam = DES3Util.encode(jsObj.toString());
		} catch (Exception e) {
			logger.info("活动分享加密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数加密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}
		
		String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/shareNotify", regparam);
		try {
			regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("活动分享回调功能解密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}
		String req_data = new String(regMsg.getBytes("utf-8"),"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}

	/**
	 * 活动好评上传 APP
	 * @param kcode
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/activitiyPraiseUpload",method=RequestMethod.POST)
	public void activitiyPraiseUpload(String imgData, HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidLD(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		
		// 获取文件上传需要保存的路径，upload文件夹需存在。
		String path = request.getSession().getServletContext().getRealPath("/upload/");
		String uploadImgUrl = path;

		// 获取扩展名
		String picType = "jpeg";
		String filename = mobile+"."+picType;
		uploadImgUrl = path +"/"+ filename;
		if (StringUtils.isNotBlank(imgData)) {
			imgData = imgData.split(",")[1];
		}
		CommonUtil.GenerateImage(imgData, path +"/"+ filename);
		// 图片压缩
//		try {
//			Thumbnails.of(uploadImgUrl).scale(1f).outputQuality(0.25f).toFile(uploadImgUrl);
//		} catch (IOException e1) {
//			logger.debug("========图片上传失败："+e1.getMessage());
//		}

//		// 获得磁盘文件条目工厂。
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		// 获取文件上传需要保存的路径，upload文件夹需存在。
//		String path = request.getSession().getServletContext().getRealPath("/upload/");
//		
//		logger.debug("========文件暂时存放位置："+path);
//		String uploadImgUrl = path;
//		// 设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
//		factory.setRepository(new File(path));
//		// 设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
//		factory.setSizeThreshold(1024 * 1024);
//		// 上传处理工具类（高水平API上传处理？）
//		ServletFileUpload upload = new ServletFileUpload(factory);
//
//		try {
//			// 调用 parseRequest（request）方法 获得上传文件 FileItem 的集合list 可实现多文件上传。
//			Boolean flg = upload.isMultipartContent(request);
//			System.out.println(flg);
//			//springmvc
//			//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
//		    //MultipartFile file = multipartRequest.getFile("imgFile");
//		    //String fileName = file.getOriginalFilename();
//			//MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) request;//struts2处理
//			List<FileItem> fileItems = (List<FileItem>) upload.parseRequest(request);
//			logger.debug("========获取文件数量："+fileItems.size());
//			
//			for (FileItem item : fileItems) {
//				// 获取表单属性名字。
//				String name = item.getFieldName();
//				// 如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
//				if (item.isFormField()) {
//					// 获取用户具体输入的字符串，
//					String value = item.getString();
//					request.setAttribute(name, value);
//				}
//				// 如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
//				else {
//					String newUploadImgUrl = "";
//					// 获取路径名
//					String value = item.getName();
//					// 取到最后一个反斜杠。
//					int start = value.lastIndexOf("\\");
//					// 截取上传文件的 字符串名字。+1是去掉反斜杠。
//					String filename = value.substring(start + 1);
//					// 获取扩展名
//					String picType = filename.substring(filename.lastIndexOf(".") + 1);
//					filename = mobile+"."+picType;
//					request.setAttribute(name, filename);
//					
//					uploadImgUrl = path +"/"+ filename;
//					
//					/*
//					 * 第三方提供的方法直接写到文件中。 item.write(new File(path,filename));
//					 */
//					// 收到写到接收的文件中。
//					OutputStream outs = new FileOutputStream(new File(path,filename));
//					InputStream in = item.getInputStream();
//
//					int length = 0;
//					byte[] buf = new byte[1024];
//					System.out.println("获取文件总量的容量:" + item.getSize());
//
//					while ((length = in.read(buf)) != -1) {
//						outs.write(buf, 0, length);
//					}
//					in.close();
//					outs.close();
//					Thumbnails.of(uploadImgUrl).scale(1f).outputQuality(0.25f).toFile(uploadImgUrl);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.debug("========获取文件数量："+e.getMessage());
//		}
		
		JSONObject retJson = new JSONObject();

		String mobileNumber = (String) request.getSession().getAttribute("mobile");
		String channel = (String) request.getSession().getAttribute("channel");
		
		JSONObject jsObj = new JSONObject();
		jsObj.put("mobileNumber", mobileNumber);
		jsObj.put("channel", channel);
		jsObj.put("uploadImgUrl", uploadImgUrl);
		
		String regparam = "";
		try {
			regparam = DES3Util.encode(jsObj.toString());
		} catch (Exception e) {
			logger.info("活动好评上传加密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数加密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}

		String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/praise", regparam);
		try {
			regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("活动分享回调功能解密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}
		String req_data = new String(regMsg.getBytes("utf-8"),"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}
	
	/**
	 * 活动好评上传 微信
	 * @param kcode
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/activitiyPraiseUploadByWX",method=RequestMethod.POST)
	public void activitiyPraiseUploadByWX(String imgName, String serverId, HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws UnsupportedEncodingException {
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidLD(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		
		// 获取文件上传需要保存的路径，upload文件夹需存在。
		String path = request.getSession().getServletContext().getRealPath("/upload/");
		String uploadImgUrl = path;

		// 获取扩展名
		String picType = "jpg";
		String filename = mobile+"."+picType;
		uploadImgUrl = path +"/"+ filename;
		
		// 图片上传压缩
		try {
			WeixinRquestUtil.saveImageToDisk(request,uploadImgUrl,serverId);
			Thumbnails.of(uploadImgUrl).scale(1f).outputQuality(0.25f).toFile(uploadImgUrl);
		} catch (Exception e1) {
			logger.debug("========图片上传失败："+e1.getMessage());
		}
		JSONObject retJson = new JSONObject();

		String mobileNumber = (String) request.getSession().getAttribute("mobile");
		String channel = (String) request.getSession().getAttribute("channel");
		
		JSONObject jsObj = new JSONObject();
		jsObj.put("mobileNumber", mobileNumber);
		jsObj.put("channel", channel);
		jsObj.put("uploadImgUrl", uploadImgUrl);
		
		String regparam = "";
		try {
			regparam = DES3Util.encode(jsObj.toString());
		} catch (Exception e) {
			logger.info("活动好评上传加密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数加密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}

		String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/praise", regparam);
		try {
			regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("活动分享回调功能解密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			String req_data = new String(retJson.toString().getBytes("utf-8"),"iso8859-1");
			out.write(req_data);
			out.flush();
			out.close();
			return;
		}
		String req_data = new String(regMsg.getBytes("utf-8"),"iso8859-1");
		out.write(req_data);
		out.flush();
		out.close();
	}

	/**
	 * 跳转到登陆页面(o2o对接用)
	 * @return
	 */
	@RequestMapping(value="/goLoginByLd",method=RequestMethod.GET)
	public String goLoginByLd(String parm, HttpServletRequest request,HttpServletResponse response){
		WeiXinUserDto userDto = (WeiXinUserDto) request.getSession().getAttribute("userDto");
		if(null==userDto){
			userDto = new WeiXinUserDto();
		}
		String backUrl = "/wxactivity/goLDActivityStep?"+parm;
		String mobileNumber = "";
		String channel = "";
		
		logger.info("######################goLoginByLd获取的参数：["+parm+"]");
		if (parm!=null && parm.length() > 0) {
			try {
				parm = java.net.URLDecoder.decode(DES3Util.decode(parm.replaceAll(" ", "+")),"UTF-8");
				JSONObject jsonObj = JSONObject.fromObject(parm);
				mobileNumber = jsonObj.getString("mobileNumber");
				channel = jsonObj.getString("channel");
				backUrl = jsonObj.getString("backUrl");
				userDto.setPhoneNum(mobileNumber);
			} catch (Exception e) {
				logger.debug("===参数异常：="+e.getMessage());
				e.printStackTrace();
			}
			if (StringUtils.isNotBlank(mobileNumber)) {
				Map<String,Object> userMap = new LinkedHashMap<String,Object>();
				userMap.put("phoneNum", mobileNumber);
				String param = CommonUtil.getParam(userMap);
				try {
					param = DES3Util.encode(param);
				} catch (Exception e) {
					logger.info("检察用户加密失败:" + e.getMessage());
					e.printStackTrace();
				}
				// 调用service接口
				String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
				try {
					resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
					
					JSONObject jsonObject = JSONObject.fromObject(resultMsg);
					JSONObject user = jsonObject.getJSONObject("user");
					//用户不存在 则注册
					if (user == null || "null".equals(user.toString())) {
						JSONObject json = new JSONObject();
						json.put("mobileNumber", mobileNumber);
						json.put("channel", channel);
						String regparam = "";
						try {
							regparam = DES3Util.encode(json.toString());
						} catch (Exception e) {
							logger.info("检察用户加密失败:" + e.getMessage());
							e.printStackTrace();
						}
						// 调用service接口
						String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/register", regparam);
						try {
							regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
						} catch (Exception e) {
							logger.info("检察用户功能解密失败:" + e.getMessage());
							e.printStackTrace();
						}
						JSONObject regObject = JSONObject.fromObject(regMsg);
						if (!Consts.SUCCESS_CODE.equals(regObject.getString("rescode"))) {
							request.setAttribute("message",regObject.getString("resmsg_cn"));
						}
					}
				} catch (Exception e) {
					logger.info("检察用户功能失败:" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		request.getSession().setAttribute("backUrl", backUrl);
		request.getSession().setAttribute("userDto", userDto);
		request.setAttribute("mobileNumber", mobileNumber);
		return WEIXIN_LOGIN;
	}
	
	/**
	 * 激活Ｋ码 送理财
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/activitiyKCodeUse",method=RequestMethod.POST)
	public void activitiyKCodeUse(String kCode, PrintWriter out,HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		JSONObject retJson = new JSONObject();

		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		String salemanMobile=null;//销售人员手机号码
		String unionMobile=null;//联通手机号码
		String checkCode=null;
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		if (StringUtils.isNotBlank(request.getParameter("mobile"))) {
			mobile = request.getParameter("mobile");
		}
		
		if(StringUtils.isNotBlank(request.getParameter("salemanMobile"))){
			salemanMobile=request.getParameter("salemanMobile");
		}
		
		if(StringUtils.isNotBlank(request.getParameter("unionMobile"))){
			unionMobile=request.getParameter("unionMobile");
		}

		if(StringUtils.isNotBlank(request.getParameter("checkCode"))){
			checkCode=request.getParameter("checkCode");
		}
		
		String verifycode = request.getParameter("verifycode");
		if (StringUtils.isBlank(mobile)) {
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "您未登录，请登录后操作！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		if (StringUtils.isBlank(kCode)) {
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "K码不能为空！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		String randomCode = String.valueOf(session.getAttribute("randomCode"));
		session.removeAttribute("randomCode");
		
		/**
		 * 验证码进行拦截
		 */
		if (StringUtils.isBlank(verifycode)){
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "验证码不能为空！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		
		//非弹框输入销售人员手机号码或联通手机号码的需校验验证码
		if(StringUtils.isBlank(salemanMobile)&&StringUtils.isBlank(unionMobile)){
			if (!randomCode.toUpperCase().equals(verifycode.toUpperCase())){
				retJson.put("rescode", Consts.CHECK_CODE);
				retJson.put("resmsg_cn", "验证码不正确！");
				setResposeMsg(retJson.toString(), out);
				return;
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("mobileNumber", mobile);
		json.put("kCode", kCode);
		json.put("channel", "WEBSITE");
		json.put("ip", CommonUtil.getClientIP(request));
		json.put("salemanMobile", salemanMobile);
		json.put("unionMobile", unionMobile);
		//TODO k3新增语音验证码参数
		json.put("checkCode", checkCode);
		json.put("setupFlag", "1");
		
		//用于同盾获取ip具体地址

		String token_id = request.getParameter("token_id");
		if(StringUtils.isBlank(token_id)){
			 
			 token_id = (String)request.getSession().getAttribute("token_id");
		}
		json.put("blackBox", token_id);
		
		String parm = "";
		try {
			parm = DES3Util.encode(json.toString());
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数加密异常!");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		// 调用service接口
		String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/activitiyKCodeUse", parm);
		try {
			regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + e.getMessage());
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		setResposeMsg(regMsg, out);
	}
	
	/**
	 * 同步登录接口
	 */
	@RequestMapping(value = "/activityLogin")
	public void activityLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session,PrintWriter out) {
		logger.info("######################activityLogin获取的参数：["+request.getQueryString()+"]");
		JSONObject retJson = new JSONObject();
		String token_id = request.getParameter("token_id");
		//设置smDeviceId
        request.setAttribute("smDeviceId", Tools.getCookieValueByName(request, "smDeviceId"));
		if(StringUtils.isBlank(token_id)){
			 
			 token_id = (String)request.getSession().getAttribute("token_id");
		}
	    logger.info("同步登录/wxactivity/activityLogin|token_id=" + token_id);
	    
		String mobileNumber = request.getParameter("mobile");
		String checkCode = request.getParameter("checkCode");
		String channel = "KCODEREG";
		// 校验手机号不能为空
		if (StringUtils.isBlank(mobileNumber)) {
			logger.error("数据检查失败： 用户名为空");
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "手机号不能为空！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		// 非法手机号检查
		boolean flag = mobileNumber.matches(Consts.PHONECHECK);
		if (!flag) {
			logger.error("数据检查失败：  手机号格式不对");
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "请输入规范的手机号码！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		// 验证码不能为空
		if (StringUtils.isBlank(checkCode)) {
			logger.error("数据检查失败： 验证码为空");
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "验证码不能为空！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		
		//验证码检查
		Map<String,Object> captchaMap = new LinkedHashMap<String,Object>();
		captchaMap.put("phoneNum", mobileNumber);
		captchaMap.put("setupFlag", "1");
		captchaMap.put("checkCode", checkCode);
		String captchaParam = CommonUtil.getParam(captchaMap);
		try {
			captchaParam = DES3Util.encode(captchaParam);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
		}
		
		String checkCaptchatMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/checkCaptcha", captchaParam);
		try {
			checkCaptchatMsg = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject checkCaptchatObject = JSONObject.fromObject(checkCaptchatMsg);
		if (!Consts.SUCCESS_CODE.equals(checkCaptchatObject.getString("rescode"))) {
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "短信验证码输入错误!");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		
		// 检查用户是否注册
		Map<String,Object> userMap = new LinkedHashMap<String,Object>();
		userMap.put("phoneNum", mobileNumber);
		String param = CommonUtil.getParam(userMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
		}
		// 调用service接口
		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/findWeixinUserByPhoneNum", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		JSONObject user = jsonObject.getJSONObject("user");
		//用户不存在 则注册
		if (user == null || "null".equals(user.toString())) {
			JSONObject json = new JSONObject();
			json.put("mobileNumber", mobileNumber);
			json.put("channel", channel);
			json.put("blackBox", token_id);
			json.put("ipInfo",  CommonUtil.getClientIP(request));
			String regparam = "";
			try {
				regparam = DES3Util.encode(json.toString());
			} catch (Exception e) {
				logger.info("检察用户加密失败:" + CommonUtil.printStackTraceToString(e));
			}
			// 调用service接口
			String regMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/register", regparam);
			try {
				regMsg = java.net.URLDecoder.decode(DES3Util.decode(regMsg),"UTF-8");
			} catch (Exception e) {
				logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			}
			JSONObject regObject = JSONObject.fromObject(regMsg);
			if (!Consts.SUCCESS_CODE.equals(regObject.getString("rescode"))) {
				retJson.put("rescode", Consts.CHECK_CODE);
				retJson.put("resmsg_cn", regObject.getString("resmsg_cn"));
				setResposeMsg(retJson.toString(), out);
				return;
			}
		}
		Map<String, Object> loginMap = new LinkedHashMap<String, Object>();
		loginMap.put("userName", mobileNumber);
		loginMap.put("setupFlag", "1");
		loginMap.put("type", "mobile");
		loginMap.put("checkCode", checkCode);
		loginMap.put("blackBox", token_id);
        loginMap.put("ipInfo",  CommonUtil.getClientIP(request));
		String loginParam = CommonUtil.getParam(loginMap);
		try {
			loginParam = DES3Util.encode(loginParam);
		} catch (Exception e) {
			logger.info("登陆加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		String loginMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/login",loginParam);
		try {
			loginMsg = java.net.URLDecoder.decode(DES3Util.decode(loginMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("登陆解密失败:" + e.getMessage());
		}

		JSONObject loginObject = JSONObject.fromObject(loginMsg);
		if (Consts.SUCCESS_CODE.equals(loginObject.getString("rescode"))) {
			System.out.println("登陆成功！");
			UserSession us = new UserSession();
			us.setId(Integer.valueOf(loginObject.getString("userId")));
			us.setUsername(loginObject.getString("userName"));
			us.setMobile(loginObject.getString("mobile"));
			//登录
			UserCookieUtil.putUser(request, response, us);
		}
		retJson.put("rescode", Consts.SUCCESS_CODE);
		retJson.put("resmsg_cn", "登录成功！");
		setResposeMsg(retJson.toString(), out);
	}

	/**
	 * 跳转到 LD活动细则页面 bannal
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/goLDActivityDetal",method=RequestMethod.GET)
	public String goLDActivityDetal(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		String mobile = parmMap.get("mobile");
		String channel = parmMap.get("channel");
		request.setAttribute("mobile", mobile == null ? "" : mobile);
		request.setAttribute("channel", channel == null ? "" : channel);
		return "weixin/activity/ldActivityDetal";
	}

	/**
	 * 跳转到K码活动登录激活页
	 * @return
	 */
	@RequestMapping(value="/goActivityKCodeLogin",method=RequestMethod.GET)
	public String goActivityKCodeLogin(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		request.setAttribute("isLogin", "N");
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		request.setAttribute("mobile", parmMap.get("mobile") == null ? "" : parmMap.get("mobile"));
		request.setAttribute("channel", parmMap.get("channel") == null ? "" : parmMap.get("channel"));

		// 登录状态 判断是否激活K码 跳转成功页面
		if (StringUtils.isNotBlank(mobile)) {
			request.setAttribute("isLogin", "Y");
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityKCodeLogin";
	}

	/**
	 * 跳转到K码活动激活成功页
	 * @return
	 */
	@RequestMapping(value="/goActivityKCodeSuccess",method=RequestMethod.GET)
	public String goActivityKCodeSuccess(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobile = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		if (StringUtils.isNotBlank(request.getParameter("mobile"))) {
			mobile = request.getParameter("mobile");
		}
		request.setAttribute("mobile", request.getParameter("mobile") == null ? "" : request.getParameter("mobile"));

		if (StringUtils.isBlank(mobile)) {
			return "redirect:/wxactivity/goActivityKCodeLogin";
		}
		
		JSONObject json = new JSONObject();
		json.put("mobileNumber", mobile);
		json.put("channel", "WEBSITE");
		String parm2 = "";
		try {
			parm2 = DES3Util.encode(json.toString());
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			request.setAttribute("message", "网络或服务器连接异常,请稍后再试!");
		}
		String keyCode = "";
		String keyCodeExchangeAmt = "0";
		String keyCodeExchangeLbAmt = "0";
		// 调用service接口
		String retMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/findUserActivityInfo", parm2);
		try {
			retMsg = java.net.URLDecoder.decode(DES3Util.decode(retMsg),"UTF-8");
			JSONObject jsonObj = JSONObject.fromObject(retMsg);
			keyCode = jsonObj.getString("keyCode");
			keyCodeExchangeAmt = jsonObj.getString("keyCodeExchangeAmt");
			keyCodeExchangeLbAmt = jsonObj.getString("keyCodeExchangeLbAmt");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
		}
		request.setAttribute("keyCode", keyCode);
		if (StringUtils.isBlank(keyCodeExchangeAmt)) {
			keyCodeExchangeAmt = "0";
		}
		if (StringUtils.isBlank(keyCodeExchangeLbAmt)) {
			keyCodeExchangeLbAmt = "0";
		}
		request.setAttribute("keyCodeExchangeAmt", new BigDecimal(keyCodeExchangeAmt).add(new BigDecimal(keyCodeExchangeLbAmt)));
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityKCodeSuccess";
	}

	/**
	 * 跳转到K码活动详情页
	 * @return
	 */
	@RequestMapping(value="/goActivityKCodeDetail",method=RequestMethod.GET)
	public String goActivityKCodeDetail(HttpServletRequest request,HttpServletResponse response){
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityKCodeDetail";
	}

	/**
	 * 跳转到K码活动失败页
	 * @return
	 */
	@RequestMapping(value="/goActivityKCodeFailed",method=RequestMethod.GET)
	public String goActivityKCodeFailed(HttpServletRequest request,HttpServletResponse response){
		
		String resmsg_cn = request.getParameter("resmsg_cn");
		request.setAttribute("resmsg_cn", resmsg_cn);
		 
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityKCodeFailed";
	}

	/**
	 * 跳转到K码激活资格查询页
	 * @return
	 */
	@RequestMapping(value="/goActivityKCodeInfo",method=RequestMethod.GET)
	public String goActivityKCodeInfo(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		
		request.setAttribute("isLogin", "N");
		// 登录状态 判断是否激活K码 跳转成功页面
		if (StringUtils.isNotBlank(mobile)) {
			request.setAttribute("isLogin", "Y");
		}
			
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityKCodeInfo";
	}
	
	/**
	 * 获取K码激活资格数据
	 * @return
	 */
	@RequestMapping(value="/findUseCompetency",method=RequestMethod.POST)
	public void findUseCompetency(String mobile, PrintWriter out,HttpSession session,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		JSONObject retJson = new JSONObject();
		UserSession us = UserCookieUtil.getUser(request);
		String mobileNumber = "";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobileNumber  = userInfoMap.get("mobile");
		}else{
			mobileNumber = us.getMobile();
		}
		if (StringUtils.isNotBlank(mobile)) {
			mobileNumber = mobile;
		}

		JSONObject json = new JSONObject();
		json.put("mobileNumber", mobileNumber);
		String parm2 = "";
		try {
			parm2 = DES3Util.encode(json.toString());
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
		}
		
		// 调用service接口
		String retMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/ldActivity/findUseCompetency", parm2);
		try {
			retMsg = java.net.URLDecoder.decode(DES3Util.decode(retMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		
		setResposeMsg(retMsg, out);
	}

	/**
	 * 跳转到K码活动介绍页
	 * @return
	 */
	@RequestMapping(value="/goActivityKCodeIntroduce",method=RequestMethod.GET)
	public String goActivityKCodeIntroduce(HttpServletRequest request,HttpServletResponse response){
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityKCodeIntroduce";
	}

	/**
	 * 跳转到签到页
	 * @return
	 */
	@RequestMapping(value="/goSignIn",method=RequestMethod.GET)
	public String goSignIn(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/signIn";
	}
	
	
	/**
	 * 跳转活动页20160501
	 * 
	 * @return
	 */
	@RequestMapping(value = "/activity_20160501", method = RequestMethod.GET)
	public String activity_20160501(HttpServletRequest request,HttpServletResponse res) {
		//不跳转登录后验证分享机制
		/*Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,res);*/
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(userId==null){
			userId = request.getParameter("userId");
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		String parm = request.getParameter("parm");
		String mobile = request.getParameter("mobile");
		String channel = request.getParameter("channel");
		String from = request.getParameter("from");
		
		request.setAttribute("parm", parm);
		request.setAttribute("mobile", mobile);
		request.setAttribute("channel", channel);
		request.setAttribute("from", from);
		
		
		return "weixin/activity/activity_20160501";
	}
	
	@RequestMapping(value="/goActivityList",method=RequestMethod.GET)
	public String goActivityList(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityList";
	}
	
	/**
	 * 活动列表查询
	 * @param request
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getAcList",method=RequestMethod.GET)
	public void getAcList(HttpServletRequest request, PrintWriter out,HttpServletResponse res) {
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		String current = request.getParameter("current");
		String pageSize = request.getParameter("pageSize");

		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}

		/**
		 * 封装接口参数
		 * */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("current", current);
		map.put("pageSize", pageSize);
		map.put("userId", userId);
		logger.info(LogUtil.getStart("getAcList", "方法开始执行", "WeiXinActivityAction", getProjetUrl(request)));
		logger.info("current=" + current + "&pageSize=" + pageSize + "&userId="+ userId);

		String param = CommonUtil.getParam(map);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("活动列表查询加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/activity/getAcList", param);
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg), "UTF-8");
		} catch (Exception e) {
			logger.info("活动列表查询解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(resultMsg);
		String size = jsonObject.getString("totalNum");
		//公共分页
		PageUtils pageObject = new PageUtils();
		int totlePages=pageObject.getTotlePages(Integer.parseInt(size), Integer.parseInt(pageSize));
		jsonObject.accumulate("totlePages", totlePages);
		jsonObject.accumulate("pageNum", Integer.valueOf(current));	
		setResposeMsg(jsonObject.toString(), out);
	}
	
	//领取加息券页面
	@RequestMapping(value="/getInterestRates",method=RequestMethod.GET)
	public String getInterestRates(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/getInterestRates";
	}
	
	//活动期疯抢加息券-库存量查询
	@RequestMapping(value="/queryWxCouponInfoByActivity",method=RequestMethod.GET)
	public void queryWxCouponInfoByActivity(HttpServletRequest request,HttpServletResponse response,PrintWriter out){
		// 调用service接口
		JSONObject retJson = new JSONObject();
		String retMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/queryWxCouponInfoByActivity",null);
		try {
			retMsg = java.net.URLDecoder.decode(DES3Util.decode(retMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("检察用户功能解密失败:" + CommonUtil.printStackTraceToString(e));
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		setResposeMsg(retMsg, out);
	}
	
	//活动期疯抢加息券
	@RequestMapping(value="/gainWxCouponByActivity",method=RequestMethod.GET)
	public void gainWxCouponByActivity(HttpServletRequest request,HttpServletResponse response,PrintWriter out){
		JSONObject retJson = new JSONObject();
		UserSession us = UserCookieUtil.getUser(request);
		String mobile="";
		if(null == us || null == us.getMobile()){
			//不跳转登录后验证分享机制
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			mobile  = userInfoMap.get("mobile");
		}else{
			mobile = us.getMobile();
		}
		request.setAttribute("isLogin", "N");
		Map<String, String> parmMap = CommonUtil.decryptParaToMap(request);
		request.setAttribute("mobile", parmMap.get("mobile") == null ? "" : parmMap.get("mobile"));
		request.setAttribute("channel", parmMap.get("channel") == null ? "" : parmMap.get("channel"));

		// 登录状态
		if (StringUtils.isNotBlank(mobile)) {
			request.setAttribute("isLogin", "Y");
		}
		if (StringUtils.isBlank(mobile)) {
			mobile = parmMap.get("mobile");
		}
		if (StringUtils.isBlank(mobile)) {
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "您未登录，请登录后操作！");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		// 调用service接口
		String param="";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		logger.info("疯抢加息券请求参数:" + params.toString());
		try {
			param = DES3Util.encode(CommonUtil.getParam(params));
		} catch (Exception e) {
			logger.info("疯抢加息券用户加密失败:" + e.getMessage());
		}
		
		String retMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + "/rateRises/gainWxCouponByActivity", param);
		try {
			retMsg = java.net.URLDecoder.decode(DES3Util.decode(retMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("疯抢加息券返回解密失败:" + CommonUtil.printStackTraceToString(e));
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "参数解密异常!");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		setResposeMsg(retMsg, out);
	}
	
	//跳转活动页
	@RequestMapping(value="/goActivityK2",method=RequestMethod.GET)
	public String goActivityK2(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(StringUtils.isNotBlank(userId)){
			request.setAttribute("userId", userId);
		}
		
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activity_k2";
	}
	
	//跳转活动页
		@RequestMapping(value="/goSeedetails",method=RequestMethod.GET)
		public String goSeedetails(HttpServletRequest request,HttpServletResponse response){
			UserSession us = UserCookieUtil.getUser(request);
			String userId="";
			if(null == us || null == us.getId()){
				//用户表示验证机制（通过微信标识OPENID）
				Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
				userId = userInfoMap.get("userId");
			}else{
				userId = String.valueOf(us.getId());
			}
			if(StringUtils.isNotBlank(userId)){
				request.setAttribute("userId", userId);
			}
			
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/seedetails";
		}

		//跳转抽奖页面
		@RequestMapping(value="/goLuckyDraw",method=RequestMethod.GET)
		public String goLuckyDraw(HttpServletRequest request,HttpServletResponse response){
			UserSession us = UserCookieUtil.getUser(request);
			String userId="";
			if(null == us || null == us.getId()){
				//用户表示验证机制（通过微信标识OPENID）
				Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,response);
				userId = userInfoMap.get("userId");
			}else{
				userId = String.valueOf(us.getId());
			}
			if(StringUtils.isNotBlank(userId)){
				request.setAttribute("userId", userId);
			}
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/choujiang";
		}
		
		//跳转"免费领取688元大礼包_详情页"页面
		@RequestMapping(value="/goGiftpacks",method=RequestMethod.GET)
		public String goGiftpacks(HttpServletRequest request,HttpServletResponse response){
			UserSession us = UserCookieUtil.getUser(request);
			String userId="";
			if(null == us || null == us.getId()){
				//用户表示验证机制（通过微信标识OPENID）
				Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
				userId = userInfoMap.get("userId");
			}else{
				userId = String.valueOf(us.getId());
			}
			if(StringUtils.isNotBlank(userId)){
				request.setAttribute("userId", userId);
			}

			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/giftpacks";
		}
		
		//周年庆抽奖页面
		@RequestMapping(value="/goRotaryTable",method=RequestMethod.GET)
		public String goRotaryTable(HttpServletRequest request,HttpServletResponse response){
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/rotaryTable";
		}
		
		//周年庆抽奖页面
		@RequestMapping(value="/goPrivateRule")
		public String goPrivateRule(HttpServletRequest request,HttpServletResponse response){
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/privateRule";
		}
		
		//规则说明
		@RequestMapping(value="/goPurchaseRule")
		public String goPurchaseRule(HttpServletRequest request,HttpServletResponse response){
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/purchaseRule";
		}

		@RequestMapping(value = "/goAnnualActivity")
		public String goAnnualActivity(HttpServletRequest request) {
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/annualActivity";
		}
		//双旦活动
		@RequestMapping(value = "/godoubleFestival")
		public String godoubleFestival(HttpServletRequest request) {
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/doubleFestival";
		}
		//双旦活动
		@RequestMapping(value = "/goPunchEggs")
		public String goPunchEggs(HttpServletRequest request) {
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/punchEggs";
		}
		
		//福利日活动
		@RequestMapping(value = "/goWelfareExclusive")
		public String goWelfareExclusive(HttpServletRequest request) {
			//分享验签机制
			WeixinRquestUtil.getSignature(request);
			return "weixin/activity/welfareExclusive";
		}
		
	/**
	 * 年报页面
	 * @return
	 */
	@RequestMapping(value="/goActivityNewYear",method=RequestMethod.GET)
	public String goActivityNewYear(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		String mobile="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
			mobile = userInfoMap.get("mobile");
		}else{
			userId = String.valueOf(us.getId());
			mobile = us.getMobile();
		}
		if(StringUtil.isNotEmpty(userId)){
			request.setAttribute("userId", userId);
		}
		if(StringUtil.isNotEmpty(mobile)){
			request.setAttribute("mobile", mobile);
		}
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityNewYear";
	}
	
	/**
	 * 年报分享页面
	 * @return
	 */
	@RequestMapping(value="/goActivityNewYearShare",method=RequestMethod.GET)
	public String goActivityNewYearShare(HttpServletRequest request,HttpServletResponse response){
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request,response);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		if(StringUtil.isNotEmpty(userId)){
			request.setAttribute("userId", userId);
		} 
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityNewYearShare";
	}
	
	//摇钱树
	@RequestMapping(value = "/goShakeMoneyTree")
	public String goShakeMoneyTree(HttpServletRequest request, HttpServletResponse res) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		UserSession us = UserCookieUtil.getUser(request);
		String phoneNum = "";
		if (null == us || null == us.getMobile()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request, res);
			phoneNum = userInfoMap.get("mobile");
		} else {
			phoneNum = us.getMobile();
		}
		String mobile = request.getParameter("mobile");
		if(StringUtil.isEmpty(phoneNum)){
			phoneNum = mobile;
		}
//		request.setAttribute("mobile", phoneNum);
		
		// login接收的数据
		JSONObject json = new JSONObject();
		json.put("invitationCd", phoneNum);
		json.put("backUrl", "/webabout/download");
		String loginParm = "";
		try {
			// loginParm = DES3Util.encode(json.toString());
			loginParm = getEncodeString(json.toString());
		} catch (Exception e) {
			logger.info("检察用户加密失败:" + e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("loginParm", loginParm);
		String goUrl = "";
		try {
			goUrl = Base64.encodeBase64URLSafeString(("/wxactivity/activity_20151103?parm=" + loginParm).getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("goUrl", goUrl);
		return "weixin/activity/goShakeMoneyTree";
	}
	//摇钱树
	@RequestMapping(value = "/shakeMoneyTree")
	public String shakeMoneyTree(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/shakeMoneyTree";
	}
	
	//妇女节
	@RequestMapping(value = "/womanActivity")
	public String womanActivity(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/womanActivity";
	}
	
	// K3语音验证
	@RequestMapping(value = "/voiceKCodeLogin")
	public String voiceKCodeLogin(HttpServletRequest request) {
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/voiceKCodeLogin";
	}
	
	
	/**
	 * 查询用户K码剩余可激活K码台数 （按渠道）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getKcodeSurplusTimesList")
	public void getKcodeSurplusTimesList(PrintWriter out, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		JSONObject retJson = new JSONObject();
		UserSession us = UserCookieUtil.getUser(request);
		String logInfo = "/wxactivity/getKcodeSurplusTimesList查询用户剩余可激活可激活K码次数";
		logger.info(logInfo + "开始");
		String userId = "";

		if (null == us || null == us.getId()) {
			// 不跳转登录后验证分享机制
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
			userId = userInfoMap.get("userId");
		} else {
			userId = us.getId() + "";
		}
		if(StringUtils.isBlank(userId)){
			logger.info(logInfo + "userId未获取到请重新登陆");
			retJson.put("rescode", Consts.CHECK_CODE);
			retJson.put("resmsg_cn", "未登陆,请重新登陆");
			setResposeMsg(retJson.toString(), out);
			return;
		}
		
		logInfo = logInfo + "userId:" + userId;
		logger.info(logInfo);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		String parm = "";
		try {
			String param = CommonUtil.getParam(params);
			parm = DES3Util.encode(param);
			logger.info(logInfo+"请求参数param："+param);
			// 调用service接口
			String retMsg = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/kcode/getKcodeSurplusTimesList", parm);
			retMsg = java.net.URLDecoder.decode(DES3Util.decode(retMsg), "UTF-8");
			setResposeMsg(retMsg, out);
		} catch (Exception e) {
			logger.info(logInfo + "异常", e);
			retJson.put("rescode", Consts.ERROR_CODE);
			retJson.put("resmsg_cn", Consts.ERROR_DESC);
			setResposeMsg(retJson.toString(), out);
			return;
		}

	}

	@RequestMapping(value = "/activityZeroBuyForApp")
	public String activityZeroBuyForApp(HttpServletRequest request) {
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/activityZeroBuyForApp";
	}

}
