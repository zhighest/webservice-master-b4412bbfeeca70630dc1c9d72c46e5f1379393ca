package com.web.actions.web.accout;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;
import com.web.domain.UserSession;
import com.web.util.UserCookieUtil;

@Controller
@RequestMapping("/coupons")
@Scope("prototype")
public class CouponsAction extends BaseAction implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory
			.getLog(CouponsAction.class);
	
	/**
	 * 跳转到 我的加息券
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/goMyInterest", method = RequestMethod.GET)
	public String goMyInterest(HttpServletRequest request,HttpServletResponse res) throws Exception {
		UserSession us = UserCookieUtil.getUser(request);
		String userId = String.valueOf(us.getId());
		request.setAttribute("userId", userId);
		String errorMsg = request.getParameter("errorMsg");
		if(StringUtils.isNotBlank(errorMsg)){
			request.setAttribute("errorMsg", errorMsg);
		}
		
		String sloanId =  request.getParameter("sloanId");
		String loanId =  request.getParameter("loanId");
		
		String product = request.getParameter("product");
		
		request.setAttribute("product", product);
		request.setAttribute("loanId", loanId);
		request.setAttribute("sloanId", sloanId);
		return "web/accout/myInterest";
	}
	
}
