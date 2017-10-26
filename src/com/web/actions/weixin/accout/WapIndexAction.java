package com.web.actions.weixin.accout;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.util.weixinAbout.WeixinRquestUtil;

@Controller
@RequestMapping("/wap")

public class WapIndexAction {
	@RequestMapping(method=RequestMethod.GET)
	public String weixinauthor(HttpServletRequest request){
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "redirect:/wxproduct/goProductList";
	}
	//跳转确认投资页面
	@RequestMapping(value = "/investeensure", method=RequestMethod.GET)
	public String investeensure(HttpServletRequest request){
		//分享验签机制
		WeixinRquestUtil.getSignature(request);
		return "weixin/accout/investeEnsure";
	}
}
