package com.web.actions.weixin.activity;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.actions.weixin.trigger.WeiXinTriggerAction;
import com.web.domain.UserSession;
import com.web.util.weixinAbout.WeixinRquestUtil;

import net.sf.json.JSONObject;

/**
 * 组队投资相关的
 *
 * @author rxc
 */
@Controller
@RequestMapping("/team")
public class TeamAction extends BaseAction {

	private static final Log logger = LogFactory.getLog(TeamAction.class);

	// 跳转活动中间页
	@RequestMapping(value = "/goActivityConnect", method = RequestMethod.GET)
	public String goActivityConnect(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/team/activityConnect";
	}

	// 跳转组队详情页面
	@RequestMapping(value = "/goTeamDetail", method = RequestMethod.GET)
	public String goTeamDetail(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/team/teamDetail";
	}

	// 跳转组队首页页面
	@RequestMapping(value = "/teamIndex", method = RequestMethod.GET)
	public String goTeamIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		try {
			String userId = getUserId(request, response);
			model.addAttribute("userId", userId);
		} catch (Exception e) {
			logger.error("去组队投资首页异常", e);
			model.addAttribute("userId", "");
		}

		return "weixin/activity/team/teamIndex";
	}

	// 跳转战队排名页面
	@RequestMapping(value = "/teamRank", method = RequestMethod.GET)
	public String goTeamRank(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/team/teamRank";
	}

	// 跳转teamCorps页面
	@RequestMapping(value = "/teamCorps", method = RequestMethod.GET)
	public String goTeamCorps(HttpServletRequest request, HttpServletResponse res) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		UserSession us = UserCookieUtil.getUser(request);
		String userId = "";
		String mobile = "";
		if (null == us || null == us.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, res);
			if (userInfoMap.containsKey("userId")) {
				userId = userInfoMap.get("userId");
			}

			if (userInfoMap.containsKey("mobile")) {
				mobile = userInfoMap.get("mobile");
			}

		} else {
			userId = String.valueOf(us.getId());
			mobile = us.getMobile();
		}

		request.setAttribute("userId", userId);
		request.setAttribute("mobile", mobile);

		return "weixin/activity/team/teamCorps";
	}

	// 跳转战队奖励页面
	@RequestMapping(value = "/teamReward", method = RequestMethod.GET)
	public String goTeamReward(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);

		return "weixin/activity/team/teamReward";
	}

	// 跳转战队登录页面
	@RequestMapping(value = "/teamLogin", method = RequestMethod.GET)
	public String goTeamLogin(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/team/teamLogin";
	}

	// 跳转战队注册页面
	@RequestMapping(value = "/teamRegister", method = RequestMethod.GET)
	public String goTeamRegister(HttpServletRequest request) {
		// 分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/activity/team/teamRegister";
	}

	/**
	 * 组队投资创建队伍
	 *
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatTeam")
	public void creatTeam(HttpServletRequest request, PrintWriter out, HttpServletResponse response) throws Exception {
		String logInfo = null;
		String result = null;
		// 初始化
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointSwitch", "Y");
		resultMap.put("usablePoint", 0);
		resultMap.put("pointGrade", 1);

		try {
			String userId = getUserId(request, response);

			logInfo = "创建队伍userId：" + userId;
			logger.info(logInfo);

			if (StringUtils.isBlank(userId)) {
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn", "您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}

			logger.info(logInfo + " param userId:" + userId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			String param = DES3Util.encode(CommonUtil.getParam(params));
			String interfacePath = LoginRedirectUtil.interfacePath + "/team/creatTeam";
			result = HttpRequestParam.sendGet(interfacePath, param);
			result = java.net.URLDecoder.decode(DES3Util.decode(result), "UTF-8");
			if (StringUtils.isBlank(result)) {
				logger.info(logInfo + interfacePath + "失败  ####");
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(logInfo + " 返回结果:" + result + "####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo + " error: 系统异常 ####", e);
			return;
		}

		logger.info(logInfo + " 结束 ####");
		setResposeMsg(result, out);
	}

	/**
	 * 组队投资创建队伍
	 *
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/joinTeam")
	public void joinTeam(HttpServletRequest request, PrintWriter out, HttpServletResponse response) throws Exception {
		String logInfo = null;
		String result;
		// 初始化
		JSONObject resultMap = new JSONObject();
		resultMap.put("pointSwitch", "Y");
		resultMap.put("usablePoint", 0);
		resultMap.put("pointGrade", 1);
		String teamId = request.getParameter("teamId");
		String teamName = request.getParameter("teamName");
		try {
			String userId = getUserId(request, response);
			logInfo = "加入队伍userId：" + userId;
			logger.info(logInfo);

			if (StringUtils.isBlank(userId)) {
				logger.info(logInfo + "获得不到userId" + userId);
				resultMap.put("resmsg_cn", "您还未登录，请登录后操作");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}

			logger.info(logInfo + " param userId:" + userId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("teamId", teamId);
			params.put("teamName", teamName);
			String param = DES3Util.encode(CommonUtil.getParam(params));
			String interfacePath = LoginRedirectUtil.interfacePath + "/team/joinTeam";
			result = HttpRequestParam.sendGet(interfacePath, param);
			result = java.net.URLDecoder.decode(DES3Util.decode(result), "UTF-8");
			if (StringUtils.isBlank(result)) {
				logger.info(logInfo + interfacePath + "失败  ####");
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(logInfo + " 返回结果:" + result + "####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo + " error: 系统异常 ####", e);
			return;
		}

		logger.info(logInfo + " 结束 ####");
		setResposeMsg(result, out);
	}

	/**
	 * 组队列表
	 *
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTeamList")
	public void queryTeamList(HttpServletRequest request, PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo = "组队列表 ";
		JSONObject jsonObjRtn = null;
		// 初始化
		JSONObject resultMap = new JSONObject();
		String current = request.getParameter("current");// 当前页数
		String pageSize = request.getParameter("pageSize");// 显示条数

		if (StringUtils.isBlank(current)) {
			current = "1";
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "20";
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", current);
		params.put("pageSize", pageSize);
		String param = DES3Util.encode(CommonUtil.getParam(params));
		try {
			logger.info(logInfo);
			String interfacePath = LoginRedirectUtil.interfacePath + "/team/queryTeamList";
			String result = HttpRequestParam.sendGet(interfacePath, param);
			result = java.net.URLDecoder.decode(DES3Util.decode(result), "UTF-8");

			if (StringUtils.isBlank(result)) {
				logger.info(logInfo + interfacePath + "失败  ####");
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}

			jsonObjRtn = JSONObject.fromObject(result);
			String size = jsonObjRtn.getString("totalNum");

			PageUtils pageObject = new PageUtils();
			if (Integer.parseInt(size) > 0) {
				pageObject = PageUtil.execsPage(Integer.parseInt(current), Integer.parseInt(size), 5,
						Integer.parseInt(pageSize));
			}

			jsonObjRtn.put("pageObject", pageObject);
			logger.info(logInfo + " 返回结果:" + jsonObjRtn.toString() + "####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo + " error: 系统异常 ####", e);
			return;
		}

		logger.info(logInfo + " 结束 ####");
		setResposeMsg(jsonObjRtn.toString(), out);
	}

	/**
	 * 列表详情
	 *
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTeamInfo")
	public void queryTeamInfo(HttpServletRequest request, PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo = "组队详情 ";
		String result;
		// 初始化
		JSONObject resultMap = new JSONObject();
		try {
			logger.info(logInfo);
			String teamId = request.getParameter("teamId");
			String type = request.getParameter("type");
			if (StringUtils.isBlank(teamId)) {
				logger.info(logInfo + "请求参数不能为空！");
				resultMap.put("resmsg_cn", "请求参数不能为空！");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			String interfacePath = LoginRedirectUtil.interfacePath + "/team/queryTeamInfo";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("teamId", teamId);
			params.put("type", type);
			String param = DES3Util.encode(CommonUtil.getParam(params));

			result = HttpRequestParam.sendGet(interfacePath, param);
			result = java.net.URLDecoder.decode(DES3Util.decode(result), "UTF-8");

			if (StringUtils.isBlank(result)) {
				logger.info(logInfo + interfacePath + "失败  ####");
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(logInfo + " 返回结果:" + result + "####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo + " error: 系统异常 ####", e);
			return;
		}

		logger.info(logInfo + " 结束 ####");
		setResposeMsg(result, out);
	}

	/**
	 * 查询用户是否已组队
	 *
	 * @param request
	 * @param out
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserIsJoinTeam")
	public void queryUserIsJoinTeam(HttpServletRequest request, PrintWriter out, HttpServletResponse response)
			throws Exception {
		String logInfo = "查询用户是否已组队";
		String result;
		String mobile = request.getParameter("mobile");
		// 初始化
		JSONObject resultMap = new JSONObject();
		try {

			String userId = getUserId(request, response);

			logInfo = logInfo + "userId：" + userId;
			logger.info(logInfo);

			if (StringUtils.isBlank(userId)) {
				logger.info(logInfo + "获得不到userId");
				resultMap.put("resmsg_cn", "登录后组队投资可赚奖励哟");
				resultMap.put("rescode", Consts.CHECK_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}

			String interfacePath = LoginRedirectUtil.interfacePath + "/team/queryUserIsJoinTeam";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			String param = DES3Util.encode(CommonUtil.getParam(params));

			result = HttpRequestParam.sendGet(interfacePath, param);
			result = java.net.URLDecoder.decode(DES3Util.decode(result), "UTF-8");
			if (StringUtils.isBlank(result)) {
				logger.info(logInfo + interfacePath + "失败  ####");
				resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
				resultMap.put("rescode", Consts.ERROR_CODE);
				setResposeMsg(resultMap.toString(), out);
				return;
			}
			logger.info(logInfo + " 返回结果:" + result + "####");
		} catch (Exception e) {
			resultMap.put("resmsg_cn", Consts.ErrorMsg.MSG00001);
			resultMap.put("rescode", Consts.ERROR_CODE);
			setResposeMsg(resultMap.toString(), out);
			logger.error(logInfo + " error: 系统异常 ####", e);
			return;
		}

		logger.info(logInfo + " 结束 ####");
		setResposeMsg(result, out);
	}

	public String getUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String userId = request.getParameter("userId");
		if (StringUtils.isNotBlank(userId)) {
			return userId;
		}
		String mobile = request.getParameter("mobile");
		UserSession userSession = this.getUserSession(request, response);
		if (null == userSession || null == userSession.getId() || 0 == userSession.getId()) {
			// 用户表示验证机制（通过微信标识OPENID）
			Map<String, String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUidByLogin(request, response);
			if (userInfoMap != null && StringUtils.isNotBlank(userInfoMap.get("userId"))) {
				userId = userInfoMap.get("userId");
			}
		} else {
			userId = String.valueOf(userSession.getId());
		}

		logger.info("getUserId-byWx:" + userId);

		if (StringUtils.isBlank(userId) || "0".equals(userId)) {
			if (StringUtils.isBlank(mobile) || mobile == null || mobile.equals("null")) {
				return null;
			}

			Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
			paramsMap.put("mobile", mobile);
			String param1 = CommonUtil.getParam(paramsMap);
			param1 = DES3Util.encode(param1);
			// 获取用户id
			String checkCaptchatMsg1 = HttpRequestParam
					.sendGet(LoginRedirectUtil.interfacePath + "/wxuser/getUserIdByMobile", param1);
			checkCaptchatMsg1 = java.net.URLDecoder.decode(DES3Util.decode(checkCaptchatMsg1), "UTF-8");
			JSONObject checkCaptchatObject1 = JSONObject.fromObject(checkCaptchatMsg1);
			userId = checkCaptchatObject1.getString("userId");

			logger.info("getUserId-byMobile:" + userId);
		}

		return userId;
	}
}
