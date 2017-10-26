package com.web.util.weixinAbout.course.message.resp;


/**
 * 语音消息
 * 
 * @author wangenlai
 * @date 2015-01-15
 */
public class VoiceMessage extends BaseMessage {
	// 语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
