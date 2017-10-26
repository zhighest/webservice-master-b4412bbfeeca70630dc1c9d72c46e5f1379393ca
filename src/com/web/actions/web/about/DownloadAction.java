package com.web.actions.web.about;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.web.actions.weixin.common.BaseAction;

@Controller
@RequestMapping("/webdownload")
@Scope("prototype")
public class DownloadAction extends BaseAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(DownloadAction.class);

	/**
	 * 跳转到下载页
	 * @return
	 */
	@RequestMapping(value = "/goDownload", method = RequestMethod.GET)
	public String goDownload(HttpServletRequest request,HttpServletResponse res) {
		
		return "web/download/download";
	}

}
