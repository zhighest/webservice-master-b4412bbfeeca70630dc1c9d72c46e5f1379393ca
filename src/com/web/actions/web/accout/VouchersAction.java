package com.web.actions.web.accout;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.web.util.PageUtil;
import com.web.util.PageUtils;
import com.web.util.UserCookieUtil;
import com.web.vo.UserVouchersVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/vouchers")

/**
 * 网站个人中心 代金券
 * @author gaojing 2017-02-21
 *
 */
public class VouchersAction extends BaseAction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5353283380749020685L;
	private static final Log logger = LogFactory.getLog(VouchersAction.class);
	
	/**
	 * 跳转到 我的代金券
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/goMyVoucher", method = RequestMethod.GET)
	public String goMyInterest(HttpServletRequest request,HttpServletResponse res) throws Exception {

		UserSession us = UserCookieUtil.getUser(request);

		String userId = String.valueOf(us.getId());
		
		request.setAttribute("userId", userId);
		return "web/accout/voucher";
	}
	
	/**
	 * 网站 个人中心 代金券管理 
	 * @param request
	 * @param res
	 * @param session
	 * @param out
	 */
	@RequestMapping(value = "/myVouchers", method = RequestMethod.GET)
	public void myRateCoupons(HttpServletRequest request, HttpServletResponse res,
			HttpSession session,PrintWriter out){
		
		UserSession us = UserCookieUtil.getUser(request);
		String userId="";
		if(null == us || null == us.getId()){
			//用户表示验证机制（通过微信标识OPENID）
			Map<String,String> userInfoMap = WeiXinTriggerAction.getUserInfoByWeixinUid(request,res);
			userId = userInfoMap.get("userId");
		}else{
			userId = String.valueOf(us.getId());
		}
		Map<String, Object> reqJavaMap = new LinkedHashMap<String, Object>(); 
		
		reqJavaMap.put("userId", userId);
		String loanId = request.getParameter("loanId");
		String productType = request.getParameter("product");
		String vsFlag = request.getParameter("vsFlag");
		//分页信息
		String current = request.getParameter("current"); // 当前页数
		String pageSize = request.getParameter("pageSize"); // 页大小
		
		if(StringUtils.isNotBlank(current)){
			reqJavaMap.put("current", current);
		}else{
			current = "1";
		}
		if(StringUtils.isNotBlank(pageSize)){
			reqJavaMap.put("pageSize", pageSize);
		}else{
			pageSize = "10";
		}
		if(StringUtils.isNotBlank(loanId)){
			reqJavaMap.put("loanId", loanId);
		}
		if(StringUtils.isNotBlank(vsFlag)){
			reqJavaMap.put("vsFlag",vsFlag);
		}
		if(StringUtils.isNotBlank(productType)){
			reqJavaMap.put("productType",productType);
		}
	
		String param = CommonUtil.getParam(reqJavaMap);
		try {
			param = DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("用户代金券查询加密失败:" + e.getMessage());
			e.printStackTrace();
		}

		String resultMsg = "";
		resultMsg = HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath
				+ "/userVochers/myVouchers", param);
		
		try {
			resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),
					"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("用户代金券查询解密失败:" + e.getMessage());
			e.printStackTrace();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();;
		JSONObject jsonObjRtn = JSONObject.fromObject(resultMsg);
		String rescode = jsonObjRtn.getString("rescode");
		resultMap.put("rescode", rescode);
		
		if(rescode.equals(Consts.SUCCESS_CODE)){
			//String listSize = jsonObjRtn.getString("listSize");
			JSONArray listArray = jsonObjRtn.getJSONArray("list");
			List<UserVouchersVo> userVoucherVoList = (List<UserVouchersVo>) JSONArray.toCollection(listArray,UserVouchersVo.class);
			resultMap.put("list", userVoucherVoList);
			String totalNum = jsonObjRtn.getString("totalNum");
			resultMap.put("totalNum", totalNum);
			PageUtils pageObject = new PageUtils();
			if (null != userVoucherVoList && userVoucherVoList.size() != 0) {
				int intPageSize = 0;
				if (null != pageSize && !"".equals(pageSize)) {
					intPageSize = Integer.parseInt(pageSize);
				}
				pageObject = PageUtil.execsPage(Integer.parseInt(current),
						Integer.parseInt(totalNum), 5, intPageSize);
			}
			resultMap.put("pageObject", pageObject);
			
		}
		resultMap.put("rescode", rescode);
		setResposeMap(resultMap, out);
	}
	
}
