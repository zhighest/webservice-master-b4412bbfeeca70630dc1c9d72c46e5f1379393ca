package com.web.util.weixinAbout.course.message.resp;


/**
 * 视频消息
 * 
  * @author wangenlai
 * @date 2015-01-15
 */
public class VideoMessage extends BaseMessage {
	// 视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
