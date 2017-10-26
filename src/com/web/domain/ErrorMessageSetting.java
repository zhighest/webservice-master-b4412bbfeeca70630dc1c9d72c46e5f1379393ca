package com.web.domain;

import java.io.Serializable;
import java.util.Date;

public class ErrorMessageSetting implements Serializable{

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage_name() {
		return message_name;
	}
	public void setMessage_name(String message_name) {
		this.message_name = message_name;
	}
	public String getMessage_desc() {
		return message_desc;
	}
	public void setMessage_desc(String message_desc) {
		this.message_desc = message_desc;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getMessage_level() {
		return message_level;
	}
	public void setMessage_level(String message_level) {
		this.message_level = message_level;
	}
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String message_name;
	private String message_desc;
	private String message_content;
	private String message_level;
	
	
	
}
