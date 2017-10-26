package com.web.util.weixinAbout.course.menu;


/**
 * click类型的按钮
 * 
 * @author wangenlai
 * @date 2015-01-15
 */
public class ClickButton extends Button {
	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}