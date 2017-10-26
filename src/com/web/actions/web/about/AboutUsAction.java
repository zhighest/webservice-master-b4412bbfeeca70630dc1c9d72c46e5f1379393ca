package com.web.actions.web.about;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;

@Controller
@RequestMapping("/webaboutUs")
@Scope("prototype")
public class AboutUsAction extends BaseAction implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(AboutUsAction.class);
	

	@RequestMapping(value = "/goServicesAgreement", method = RequestMethod.GET)
	public String goServicesAgreement(HttpServletRequest request) {
		
		return "web/aboutUs/servicesAgreement";
	}

	@RequestMapping(value = "/goPlatformOverview", method = RequestMethod.GET)
	public String goPlatformOverview(HttpServletRequest request) {
		
		return "web/aboutUs/platformOverview";
	}
	
	@RequestMapping(value = "/goMenu", method = RequestMethod.GET)
	public String goMenu(HttpServletRequest request) {
		
		return "web/aboutUs/menu";
	}

}
