package com.web.domain;

import java.io.Serializable;
import java.util.Date;

public class CouponsLuckDraw  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long user_id;
	private String mobile;
	private Date create_time;
	private Date update_time;
	private Integer draw_total_count;
	private Integer draw_use_count;
	private Integer draw_rest_count;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Integer getDraw_total_count() {
		return draw_total_count;
	}
	public void setDraw_total_count(Integer draw_total_count) {
		this.draw_total_count = draw_total_count;
	}
	public Integer getDraw_use_count() {
		return draw_use_count;
	}
	public void setDraw_use_count(Integer draw_use_count) {
		this.draw_use_count = draw_use_count;
	}
	public Integer getDraw_rest_count() {
		return draw_rest_count;
	}
	public void setDraw_rest_count(Integer draw_rest_count) {
		this.draw_rest_count = draw_rest_count;
	}
	
	
}
