package com.web.actions.web.activity;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/webactivity")

public class ActivityAction implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(ActivityAction.class);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityList", method = RequestMethod.GET)
	public String activityList(HttpServletRequest request) {
		return "web/activity/activityList";
	} 
	
	/**
	 * 跳转到activityHelper 网站模板 金融小助手  页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/activityHelper")
	public String activityHelper(HttpServletRequest request) {
		return "web/activity/activityHelper";
	}
}
