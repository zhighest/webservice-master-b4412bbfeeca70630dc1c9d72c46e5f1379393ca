package com.web.util.weixinAbout.course.message.resp;


/**
 * 图片消息
 * 
 * @author wangenlai
 * @date 2015-01-15
 */
public class ImageMessage extends BaseMessage {
	// 图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
