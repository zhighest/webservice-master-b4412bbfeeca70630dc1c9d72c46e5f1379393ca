package com.web.util.weixinAbout.course.message.req;

/**
 * 文本消息
 * 
 * @author wangenlai
 * @date 2015-01-15
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
