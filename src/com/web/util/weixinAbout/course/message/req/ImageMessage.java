package com.web.util.weixinAbout.course.message.req;

/**
 * 图片消息
 * 
 * @author wangenlai
 * @date 2015-01-15
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
